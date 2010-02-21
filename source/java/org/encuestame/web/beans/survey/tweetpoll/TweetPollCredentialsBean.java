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
import org.encuestame.core.service.ISecurityService;
import org.encuestame.web.beans.MasterBean;

/**
 * TweetPoll Credentials Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 21, 2010 10:25:56 AM
 * @version $Id: $
 */
public class TweetPollCredentialsBean extends MasterBean{

    /**
     * Constructor.
     */
    public TweetPollCredentialsBean() {}


    /** Consumer Key. **/
    private String consumerTwitterKey;

    /** Consumer Secret. **/
    private String consumerTwitterSecret;

    /** Twitter Pin. **/
    private Integer twitterTwitterPing;

    /** Twitter Account. **/
    private String twitterAccount;

    /** Twitter Password. **/
    private String twitterPassword;

    /**
     * @return the consumerTwitterKey
     */
    public String getConsumerTwitterKey() {
        return consumerTwitterKey;
    }

    /**
     * Load Credentials.
     */
    public void loadCredentials(){
        try{
            final SecUsers sessionUser = getUsernameByName().getSecUser();
            setConsumerTwitterKey(sessionUser.getConsumerKey());
            setConsumerTwitterSecret(sessionUser.getConsumerSecret());
            setTwitterAccount(sessionUser.getTwitterAccount());
            setTwitterPassword(sessionUser.getTwitterPassword());
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            addErrorMessage(e.getMessage(), e.getMessage());
        }
    }

    /**
     * @param consumerTwitterKey the consumerTwitterKey to set
     */
    public void setConsumerTwitterKey(String consumerTwitterKey) {
        this.consumerTwitterKey = consumerTwitterKey;
    }

    /**
     * @return the consumerTwitterSecret
     */
    public String getConsumerTwitterSecret() {
        return consumerTwitterSecret;
    }

    /**
     * @param consumerTwitterSecret the consumerTwitterSecret to set
     */
    public void setConsumerTwitterSecret(String consumerTwitterSecret) {
        this.consumerTwitterSecret = consumerTwitterSecret;
    }

    /**
     * @return the twitterTwitterPing
     */
    public Integer getTwitterTwitterPing() {
        return twitterTwitterPing;
    }

    /**
     * @param twitterTwitterPing the twitterTwitterPing to set
     */
    public void setTwitterTwitterPing(Integer twitterTwitterPing) {
        this.twitterTwitterPing = twitterTwitterPing;
    }

    /**
     * @return the twitterAccount
     */
    public String getTwitterAccount() {
        return twitterAccount;
    }

    /**
     * @param twitterAccount the twitterAccount to set
     */
    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    /**
     * @return the twitterPassword
     */
    public String getTwitterPassword() {
        return twitterPassword;
    }

    /**
     * @param twitterPassword the twitterPassword to set
     */
    public void setTwitterPassword(String twitterPassword) {
        this.twitterPassword = twitterPassword;
    }
}
