/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.web.beans.survey.tweetpoll;

import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.admon.UnitSessionUserBean;

import twitter4j.TwitterException;

/**
 * TweetPoll Credentials Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 21, 2010 10:25:56 AM
 * @version $Id: $
 */
public class TweetPollCredentialsBean extends MasterBean {

    /**
     * Constructor.
     */
    public TweetPollCredentialsBean() {
    }

    /** Twitter Auth Url. **/
    private String twitterAuthUrl;

    private UnitSessionUserBean sessionUserBean;

    /**
     * Load Credentials.
     */
    public void loadCredentials() {
        try {
            log.info("loading credentials");
            final SecUsers sessionUser = getUsernameByName().getSecUser();
            setSessionUserBean(ConvertDomainBean.convertUserSessionToUserBean(sessionUser));
            log.info("finish loading credentials");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            addErrorMessage("Error Loading Credentuals", e.getMessage());
        }
    }

    /**
     * Save Twitter Credentials.
     */
    public void saveTwitterCredentials() {
        try {
            final ISurveyService survey = getSurveyService();
            survey.getTwitterService().verifyCredentials(getSessionUserBean().getTwitterAccount(),
                    getSessionUserBean().getTwitterPassword());
            getSecurityService().updateTwitterAccount(getSessionUserBean().getTwitterAccount(),
                    getSessionUserBean().getTwitterPassword(), getUsernameByName().getSecUser());
            addInfoMessage("Credentials Updated", "Twitter Credentials Updated");
            this.loadCredentials();
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
    public void saveSecretCredentials() {
        try {
            getSecurityService().updateSecretTwitterCredentials(
                    getSessionUserBean().getConsumerTwitterKey(), getSessionUserBean().getConsumerTwitterSecret(),
                    getSessionUserBean().getTwitterTwitterPing(),
                    getUsernameByName().getSecUser());
            addInfoMessage("Saved Twitter Secret Credentials",
                    "You can request your Ping Now.");
            this.loadCredentials();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            addErrorMessage("Error on Update Twitter Credentials", e
                    .getMessage());
        }
    }

    /**
     * Request Twitter Pin.
     */
    public void twitterPin() {
        try {
            setTwitterAuthUrl(getSurveyService().getTwitterToken(
                    getSessionUserBean().getConsumerTwitterKey(), getSessionUserBean().getConsumerTwitterSecret())
                    .getAuthorizationURL());
        } catch (TwitterException e) {
            e.printStackTrace();
            log.error(e);
            setTwitterAuthUrl("");
            addErrorMessage("Error Getting URL Twitter Authentication", e
                    .getMessage());
        }
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
    public void setTwitterAuthUrl(final String twitterAuthUrl) {
        this.twitterAuthUrl = twitterAuthUrl;
    }

    /**
     * @return the sessionUserBean
     */
    public UnitSessionUserBean getSessionUserBean() {
        if(this.sessionUserBean == null){
           this.loadCredentials();
        }
        return sessionUserBean;
    }

    /**
     * @param sessionUserBean
     *            the sessionUserBean to set
     */
    public void setSessionUserBean(UnitSessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

}
