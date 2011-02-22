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
package org.encuestame.business.service.social.connect;

import org.encuestame.utils.oauth.OAuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.LinkedInApi;
import org.springframework.social.linkedin.LinkedInApi;
import org.springframework.social.linkedin.LinkedInTemplate;

/**
 * LinkedIn Social Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 * @version $Id:$
 */
public class LinkedInSocialService extends AbstractSocialProvider<LinkedInApi>{

    /**
     * Social Account Provider;
     */
    private SocialAccountProvider parameters = new SocialAccountProvider();

    /**
     * Twitter Template.
     */
    private LinkedInTemplate linkedInTemplate;

    /**
     * Consumer Key.
     */
    public @Value("${linkedIn.oauth.api.key}") String apiKey;

    /**
     * Consumer Secret.
     */
    public @Value("${linkedIn.oauth.api.secret}") String consumerSecret;

    /**
     * Authorize Url.
     */
    public @Value("${linkedIn.oauth.authorize.url}") String authorizeUrl;

    /**
     * Request Token Url.
     */
    public @Value("${linkedIn.oauth.request.token}") String requestTokenUrl;

    /**
     *
     * @param socialProviderName
     */
    public LinkedInSocialService() {
       this.loadParameters();
    }

    @Override
    protected LinkedInApi createServiceOperations(OAuthToken accessToken) {
        log.debug("Twitter Operations createServiceOperations "+accessToken.toString());
        if(this.linkedInTemplate == null){
            log.debug("Creando Provider");
            this.createLinkedInTemplate(accessToken);
        }
        return this.linkedInTemplate;
    }

    /**
     * Create Twitter Template.
     * @param accessToken
     */
    private void createLinkedInTemplate(final OAuthToken accessToken){
        if (accessToken == null) {
            throw new IllegalStateException("Cannot access LinkedIn without an access token");
        }
        this.linkedInTemplate = new LinkedInTemplate(getApiKey(), getSecret(), accessToken.getValue(), accessToken.getSecret());

    }

    /**
     * Fetch Provider Account Id.
     */
    @Override
    public String fetchProviderAccountId(LinkedInApi serviceOperations) {
      log.debug("MEAN fetchNewRequestToken "+serviceOperations.getProfileId());
      return serviceOperations.getProfileId();
    }

    /**
     * Build Provider Profile Url.
     */
    @Override
    protected String buildProviderProfileUrl(String providerAccountId,
            LinkedInApi serviceOperations) {
        return "url-" + providerAccountId;
    }

    /**
     * Load Parameters.
     */
    private void loadParameters(){
        this.parameters.setApiKey(this.apiKey);
        this.parameters.setSecret(this.consumerSecret);
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
        return apiKey;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setConsumerKey(String consumerKey) {
        this.apiKey = consumerKey;
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
