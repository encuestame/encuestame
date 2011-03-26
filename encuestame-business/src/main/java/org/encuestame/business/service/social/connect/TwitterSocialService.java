/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service.social.connect;

import org.encuestame.utils.oauth.OAuthToken;
import org.scribe.builder.api.TwitterApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.TwitterTemplate;

/**
 * Twitter Social Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 * @version $Id:$
 */
public class TwitterSocialService extends AbstractSocialProvider<TwitterApi> implements ITwitterSocialProvider{

    /**
     * Social Account Provider;
     */
    private SocialAccountProvider parameters = new SocialAccountProvider();

    /**
     * Twitter Template.
     */
    private TwitterTemplate twitterTemplate;

    /**
     * Twitter Url.
     */
    private final String TWITTER_URL = "http://www.twitter.com/";

    /**
     * Consumer Key.
     */
     @Value("${twitter.oauth.consumerKey}") private String consumerKey;

    /**
     * Consumer Secret.
     */
    @Value("${twitter.oauth.consumerSecret}") private String consumerSecret;

    /**
     * Authorize Url.
     */
    @Value("${twitter.oauth.authorize}") private String authorizeUrl;

    /**
     * Access Token Url.
     */
    @Value("${twitter.oauth.access.token}") private String accessTokenUrl;

    /**
     * Request Token Url.
     */
    @Value("${twitter.oauth.request.token}") private String requestTokenUrl;

    /**
     *
     * @param socialProviderName
     */
    public TwitterSocialService() {
       this.loadParameters();
    }

    @Override
    protected TwitterApi createServiceOperations(OAuthToken accessToken) {
        log.debug("Twitter Operations createServiceOperations "+accessToken.toString());
        if(this.twitterTemplate == null){
            log.debug("Creando Provider");
            this.createTwitterTemplate(accessToken);
        }
        return null;
    }

    /**
     * Create Twitter Template.
     * @param accessToken
     */
    private void createTwitterTemplate(final OAuthToken accessToken){
        this.twitterTemplate = accessToken != null ?
                new TwitterTemplate(
                        getApiKey(),
                        getSecret(),
                        accessToken.getValue(),
                        accessToken.getSecret())
        : new TwitterTemplate();
    }

    /**
     * Fetch Provider Account Id.
     */
    //@Override
    public String fetchProviderAccountId(TwitterApi serviceOperations) {
        return TWITTER_URL;
      //log.debug("MEAN fetchNewRequestToken "+serviceOperations.getProfileId());
      //return serviceOperations.getProfileId();
    }

    /**
     * Build Provider Profile Url.
     */
    @Override
    protected String buildProviderProfileUrl(String providerAccountId,
            TwitterApi serviceOperations) {
        return this.TWITTER_URL + providerAccountId;
    }

    /**
     * Load Parameters.
     */
    private void loadParameters(){
        this.parameters.setApiKey(this.consumerKey);
        this.parameters.setSecret(this.consumerSecret);
        this.parameters.setAccessTokenUrl(this.accessTokenUrl);
        this.parameters.setAuthorizeUrl(this.authorizeUrl);
        this.parameters.setRequestTokenUrl(this.requestTokenUrl);
    }

    /**
     * Get Parameters.
     */
    @Override
    SocialAccountProvider getParameters() {
        log.error("getParameters ");
        if(this.parameters == null){
             log.info("loadParameters");
            this.loadParameters();
        } else {
            log.debug("Parametros encontrados");
        }
        return this.parameters;
    }

    /**
     * Set Parameters.
     */
    @Override
    SocialAccountProvider setParameters(final SocialAccountProvider accountProvider) {
        log.error("No getParameters "+accountProvider);
        this.parameters = accountProvider;
        return this.parameters;
    }

    /**
     * @return the consumerKey
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * @return the consumerSecret
     */
    public String getConsumerSecret() {
        return consumerSecret;
    }

    /**
     * @param consumerSecret the consumerSecret to set
     */
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }
}
