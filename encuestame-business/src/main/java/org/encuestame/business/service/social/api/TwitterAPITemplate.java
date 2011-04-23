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

package org.encuestame.business.service.social.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.TwitterAPIOperations;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.social.SocialProvider;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * Twitter Service.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 13, 2010 4:07:03 PM
 */

public class TwitterAPITemplate implements TwitterAPIOperations{

    private Log log = LogFactory.getLog(this.getClass());


    private String consumerSecret;

    private String consumerKey;


    /**
     * Constructor.
     */
    public TwitterAPITemplate() {
    }

    /**
     *
     * @param consumerSecret
     * @param consumerKey
     */
    public TwitterAPITemplate(final String consumerSecret, final String consumerKey){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    /**
     * OAuth Public Tweet.
     * @param socialTwitterAccount
     * @param tweet
     * @return
     * @throws TwitterException
     */
    public Status updateStatus(final SocialAccount socialTwitterAccount, final String tweet) throws TwitterException{
        log.debug("publicTweet "+socialTwitterAccount.getSocialAccountName());
        //Twitter twitter = new TwitterFactory().getInstance();
        log.debug("publicTweet Before  Token  {"+socialTwitterAccount.getAccessToken());
        log.debug("publicTweet Before Secret Token  {"+socialTwitterAccount.getSecretToken());
        final Twitter twitter = this.getTwitterInstance(socialTwitterAccount);
        log.debug("Verify  "+twitter.verifyCredentials());
        log.debug("Update Status "+tweet);
        return twitter.updateStatus(tweet);
    }

    /**
     *
     * @param socialTwitterAccount
     * @return
     */
    private Twitter getTwitterInstance(final SocialAccount socialTwitterAccount){
        final AccessToken accessToken = this.createNewOAuthAccessToken(socialTwitterAccount);
        log.debug("Access Token "+accessToken);
        return this.getOAuthAuthorizedInstance(accessToken);
    }

    /**
     * Get twitter user profile.
     * @param socialTwitterAccount {@link SocialAccount}.
     * @return
     * @throws TwitterException
     */
    public User getUser(final SocialAccount socialTwitterAccount) throws TwitterException{
        if(socialTwitterAccount.getAccounType().equals(SocialProvider.TWITTER)){
            return getTwitterInstance(socialTwitterAccount).verifyCredentials();
        } else {
            return null;
        }
    }

    /**
     * Verify Credentials.
     * @param socialAccount
     * @return
     */
    public Boolean verifyCredentials(final SocialAccount socialAccount){
        final Twitter twitter = this.getTwitterInstance(socialAccount);
        User user;
        try {
            user = twitter.verifyCredentials();
        } catch (TwitterException e) {
            user = null;
            log.error(e);
        }
        log.debug("Twitter Account "+socialAccount.getSocialAccountName()+ " is confirmed? "+user);
        return user == null ? false : true;
    }

    /**
     * Create New OAuth Access Token.
     * @param socialTwitterAccount {@link SocialAccount}.
     * @return {@link AccessToken}.
     */
    private AccessToken createNewOAuthAccessToken(final SocialAccount socialTwitterAccount){
         final AccessToken accessToken = new AccessToken(socialTwitterAccount.getAccessToken(), socialTwitterAccount.getSecretToken());
         return accessToken;
    }

    /**
     * Get OAuthorized Token.
     * @param socialTwitterAccount {@link SocialAccount}.
     * @return {@link Twitter}.
     */
    private Twitter getOAuthAuthorizedInstance(final AccessToken accessToken){
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(this.consumerKey)
          .setOAuthConsumerSecret(this.consumerSecret)
          .setOAuthAccessToken(accessToken.getToken())
          .setOAuthAccessTokenSecret(accessToken.getTokenSecret());
        final TwitterFactory tf = new TwitterFactory(cb.build());
        final Twitter twitter = tf.getInstance();
        return twitter;
    }

    /**
     * Get Twitter Ping.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @return {@link RequestToken}
     * @throws TwitterException exception
     */
    public RequestToken getTwitterPing(String consumerKey, String consumerSecret)
            throws TwitterException {
        if (consumerKey == null) {
            throw new IllegalArgumentException("Consumer key is missing");
        }
        if (consumerSecret == null) {
            throw new IllegalArgumentException("Consumer secret is missing");
        }
        final Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        return twitter.getOAuthRequestToken();
    }
}
