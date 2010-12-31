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
package org.encuestame.business.social;

import org.encuestame.persistence.domain.security.SocialAccountProvider;
import org.encuestame.utils.oauth.OAuthToken;
import org.springframework.social.twitter.TwitterOperations;
import org.springframework.social.twitter.TwitterTemplate;

/**
 * Twitter Social Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 * @version $Id:$
 */
public class TwitterSocialService extends AbstractSocialProvider<TwitterOperations> implements ITwitterSocialProvider{

    /**
     * Social Account Provider;
     */
    private SocialAccountProvider parameters;

    /**
     * Twitter Template.
     */
    private TwitterTemplate twitterTemplate;

    /**
     * Twitter Url.
     */
    private final String TWITTER_URL = "http://www.twitter.com/";

    /**
     *
     * @param socialProviderName
     */
    public TwitterSocialService() {
       this.loadParameters();
    }

    @Override
    protected TwitterOperations createServiceOperations(OAuthToken accessToken) {
        log.debug("Twitter Operations createServiceOperations "+accessToken.toString());
        if(this.twitterTemplate == null){
            log.debug("Creando Provider");
            this.createTwitterTemplate(accessToken);
        }
        return this.twitterTemplate;
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
    @Override
    public String fetchProviderAccountId(TwitterOperations serviceOperations) {
      log.debug("MEAN fetchNewRequestToken "+serviceOperations.getProfileId());
      return serviceOperations.getProfileId();
    }

    /**
     * Build Provider Profile Url.
     */
    @Override
    protected String buildProviderProfileUrl(String providerAccountId,
            TwitterOperations serviceOperations) {
        return this.TWITTER_URL + providerAccountId;
    }

    /**
     * Load Parameters.
     */
    private void loadParameters(){
        if(getSocialProviderDao() != null){
            final SocialAccountProvider parameters = getSocialProviderDao().getSocialAccountProviderId(1L);
            if(parameters == null){
                log.error("NOT SOCIAL PROVIDER FOUND");
            }
            setParameters(parameters);
        } else {
            log.error("No Provider Dao");
        }
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
}
