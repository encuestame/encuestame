/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.social;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.persistence.domain.security.SecUserTwitterAccounts.TypeAuth;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.web.beans.MasterBean;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * Twitter Account Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 26, 2010 4:13:11 PM
 * @version $Id:$
 */
public class TwitterAccountsBean extends MasterBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -211208809331131194L;

    /**
     * {@link UnitTwitterAccountBean}.
     * **/
    private UnitTwitterAccountBean unitTwitterAccountBean = new UnitTwitterAccountBean();

    private String twitterAuthUrl;

    private TypeAuth typeAuth = null;

    private TypeAuth PASSWORD = TypeAuth.PASSWORD;

    private TypeAuth OAUTH = TypeAuth.OAUTH;

    /**
     * Access Token.
     */
    private AccessToken accessToken = null;

    /**
     * Request Token.
     */
    private RequestToken requestToken = null;

    /**
     * Twitter Instance.
     */
    private Twitter twitter = null;

    /**
     * Password.
     */
    private String currentPassword;

    /**
     * Constructor.
     */
    public TwitterAccountsBean() {}

    /**
     * Load Twitter Account Info.
     *
     * @param twitterAccountId
     */
    public void loadTwitterAccountInfo(final Long twitterAccountId) {
        setUnitTwitterAccountBean(getSecurityService().getTwitterAccount(
                twitterAccountId));
    }

    /**
     * @return the unitTwitterAccountBean
     */
    public UnitTwitterAccountBean getUnitTwitterAccountBean() {
        return unitTwitterAccountBean;
    }

    /**
     * @param unitTwitterAccountBean
     *            the unitTwitterAccountBean to set
     */
    public void setUnitTwitterAccountBean(
            UnitTwitterAccountBean unitTwitterAccountBean) {
        log.info("setUnitTwitterAccountBean SETTER " + unitTwitterAccountBean);
        if(this.typeAuth == null){
            this.typeAuth = ConvertDomainBean.convertStringToEnum(unitTwitterAccountBean.getType());
        }
        this.unitTwitterAccountBean = unitTwitterAccountBean;
    }

    /**
     * Save Twitter Credentials.
     */
    @SuppressWarnings("deprecation")
    public final void saveTwitterCredentials() {
        log.debug("Updating Credentials");
        try {
            // verifing credentials
            if (Integer.valueOf(getSurveyService().getTwitterService()
                    .verifyCredentials(
                            getUnitTwitterAccountBean().getAccount(),
                            getCurrentPassword()).getId()) != null) {
                // after verify credentials
                getSecurityService().updateTwitterAccount(
                        getUnitTwitterAccountBean(), getCurrentPassword(), Boolean.TRUE);
                addInfoMessage("Credentials Updated",
                        "Twitter Credentials Updated");
            } else {
                log.warn("BAD CREDENTIALS");
                addErrorMessage("Bad Credentials", "Bad Credentials");
            }
        } catch (Exception e) {
            log.error(e);
            addErrorMessage("Error on Update Twitter Credentials", e
                    .getMessage());
        }
    }

    /**
     * Save Secret Credentials.
     */
    public final void saveSecretCredentials() {
        try {
            this.updateSecreTwitterCredentials();
            //reset auth url.
            this.setTwitterAuthUrl(null);
            addInfoMessage("Saved Twitter Secret Credentials",
            "You can request your Pin Now.");
        } catch (Exception e) {
            log.error(e);
            addErrorMessage("Error on Update OAuth Twitter Credentials", e
                    .getMessage());
            this.getTwitterAuthUrl();
        }
    }

    /**
     * Update Secret Twitter Credentials.
     * @throws EnMeExpcetion
     */
    private void updateSecreTwitterCredentials() throws EnMeExpcetion{
        if(this.typeAuth.equals(OAUTH)){
            log.debug("Is OAth, confirm pin");
            if(getUnitTwitterAccountBean().getPin() == null || getUnitTwitterAccountBean().getPin().isEmpty()){
                log.debug("Pin not found");
                this.getTwitterPinUrl();
            } else {
                log.debug("Confirm Pin");
                this.confirmOAuthPin();
            }
        }
        log.debug("Saving credentials.");
        getSecurityService().updateSecretTwitterCredentials(
                getUnitTwitterAccountBean(), getUserPrincipalUsername());
    }

    /**
     * Update Authentication.
     */
    public final void updateAuth(ActionEvent actionEvent){
        try {
            this.updateSecreTwitterCredentials();
        } catch (EnMeExpcetion e) {
            log.error(e);
             addErrorMessage("Error on Update OAuth Twitter Credentials", e
                        .getMessage());
        }
    }

    /**
     * Request Twitter Pin.
     */
    public final void getTwitterPinUrl() {
        log.debug("Get getTwitterPinUrl");
        try {
            //pin should be null
            if (getUnitTwitterAccountBean().getPin() == null || getUnitTwitterAccountBean().getPin().isEmpty()) {
                //reset request and access token.
                this.requestToken = null;
                this.twitter = null;
                //secret key and key should be different null
                if (getUnitTwitterAccountBean().getKey() != null  && getUnitTwitterAccountBean().getSecret() != null) {
                    //new twitter instance
                    this.twitter = new TwitterFactory().getInstance();
                    //set twitter consumer keys
                    this.twitter.setOAuthConsumer(getUnitTwitterAccountBean().getKey(),  getUnitTwitterAccountBean().getSecret());
                    this.requestToken = twitter.getOAuthRequestToken();
                    log.debug("Setting Request Token "+this.requestToken);
                    final String url = this.requestToken.getAuthorizationURL();
                    log.debug("Request Token {"+requestToken.getToken());
                    log.debug("Request Secret Token {"+requestToken.getTokenSecret());
                    log.debug("OAuth url {"+url);
                    setTwitterAuthUrl(url);
                    log.debug("TwitterPin Url Generated");
                }
            } else {
                log.info("Pin found, reset to get new OAuth Url");
            }

        } catch (Exception e) {
            log.error("Error Getting URL Twitter Authentication "+e.getMessage());
            setTwitterAuthUrl("");
            addErrorMessage("Error Getting URL Twitter Authentication", e.getMessage());
        }
    }

    /**
     * Confirm Pin and Save Token/Secret Token.
     */
    public void confirmOAuthPin(){
        log.debug("confirmOAuthPin");
        if (getUnitTwitterAccountBean().getPin() != null || !getUnitTwitterAccountBean().getPin().isEmpty()) {
            try{
                if(this.requestToken == null){
                    log.error("Request Token is null");
                }
                else{
                    log.debug("confirmOAuthPin PIN: "+getUnitTwitterAccountBean().getPin());
                    this.accessToken = this.twitter.getOAuthAccessToken(this.requestToken, getUnitTwitterAccountBean().getPin());
                    //Testing send twitter, WORKS !!
                    //this.twitter.setOAuthAccessToken(accessToken);
                    //this.twitter.updateStatus("test accessToken");

                    //set new access token and secret token.
                    getUnitTwitterAccountBean().setToken(this.accessToken.getToken());
                    getUnitTwitterAccountBean().setSecretToken(this.accessToken.getTokenSecret());
                     log.debug("Access Token {"+this.accessToken);
                     log.debug("New Token {"+this.accessToken.getToken());
                     log.debug("New Secret Token {"+this.accessToken.getTokenSecret());
                     getSecurityService().updateOAuthTokenSocialAccount(getUnitTwitterAccountBean().getAccountId(),
                             this.accessToken.getToken(),
                             this.accessToken.getTokenSecret(),
                             getUserPrincipalUsername());
                }
            } catch (Exception e) {
                log.error(e);
                addErrorMessage("Error Getting URL Twitter Authentication", e
                        .getMessage());
                //If this process fail, reset pin.
                getUnitTwitterAccountBean().setPin(null);
            }
        }
    }

    /**
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword
     *            the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * @return the twitterAuthUrl
     */
    public String getTwitterAuthUrl() {
        return twitterAuthUrl;
    }

    /**
     * @param twitterAuthUrl
     *            the twitterAuthUrl to set
     */
    public void setTwitterAuthUrl(String twitterAuthUrl) {
        this.twitterAuthUrl = twitterAuthUrl;
    }

    /**
     * @return the typeAuth
     */
    public TypeAuth getTypeAuth() {
        return typeAuth;
    }

    /**
     * @param typeAuth the typeAuth to set
     */
    public void setTypeAuth(TypeAuth typeAuth) {
        getUnitTwitterAccountBean().setType(typeAuth.name());
        this.typeAuth = typeAuth;
    }

    /**
     * @return the pASSWORD
     */
    public TypeAuth getPASSWORD() {
        return PASSWORD;
    }

    /**
     * @param pASSWORD the pASSWORD to set
     */
    public void setPASSWORD(TypeAuth pASSWORD) {
        PASSWORD = pASSWORD;
    }

    /**
     * @return the oAUTH
     */
    public TypeAuth getOAUTH() {
        return OAUTH;
    }

    /**
     * @param oAUTH the oAUTH to set
     */
    public void setOAUTH(TypeAuth oAUTH) {
        OAUTH = oAUTH;
    }
}
