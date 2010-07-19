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

import org.encuestame.core.persistence.pojo.SecUserTwitterAccounts.TypeAuth;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.web.beans.MasterBean;
import org.hibernate.cfg.ConfigurationArtefactType;

import twitter4j.TwitterException;

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
     * Password.
     */
    private String currentPassword;

    public TwitterAccountsBean() {
    }

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
        this.getTwitterPinUrl();
        this.unitTwitterAccountBean = unitTwitterAccountBean;
    }

    /**
     * Save Twitter Credentials.
     */
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
            e.printStackTrace();
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
            getSecurityService().updateSecretTwitterCredentials(
                    getUnitTwitterAccountBean(), getUserPrincipalUsername());
            addInfoMessage("Saved Twitter Secret Credentials",
                    "You can request your Ping Now.");
            //reset auth url.
            this.setTwitterAuthUrl(null);
            //getter auth url.
            this.getTwitterAuthUrl();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            addErrorMessage("Error on Update OAuth Twitter Credentials", e
                    .getMessage());
        }
    }

    /**
     * Update Authentication.
     */
    public final void updateAuth(ActionEvent actionEvent){
        getSecurityService().updateSecretTwitterCredentials(
                getUnitTwitterAccountBean(), getUserPrincipalUsername());
    }

    /**
     * Request Twitter Pin.
     */
    public final void getTwitterPinUrl() {
        try {
            //pin should be null
            if (getUnitTwitterAccountBean().getPin()  == null || getUnitTwitterAccountBean().getPin().isEmpty()) {
                //secrey and key should be different null
                if (getUnitTwitterAccountBean().getKey() != null
                        && getUnitTwitterAccountBean().getSecret() != null) {
                    setTwitterAuthUrl(getServicemanager()
                            .getApplicationServices().getSurveyService()
                            .getTwitterToken(
                                    getUnitTwitterAccountBean().getKey(),
                                    getUnitTwitterAccountBean().getSecret())
                            .getAuthorizationURL());
                    log.debug("TwitterPin Url Generated");
                }
                log.debug("TwitterPin Url " + getTwitterAuthUrl());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            log.error(e);
            setTwitterAuthUrl("");
            addErrorMessage("Error Getting URL Twitter Authentication", e
                    .getMessage());
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
