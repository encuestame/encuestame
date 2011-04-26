/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.social;

import org.apache.log4j.Logger;
import org.encuestame.business.service.social.OAuth1RequestFlow;
import org.encuestame.business.service.social.OAuth2RequestFlow;
import org.encuestame.business.service.social.api.FacebookAPITemplate;
import org.encuestame.business.service.social.api.IdenticaAPITemplate;
import org.encuestame.business.service.social.api.LinkedInAPITemplate;
import org.encuestame.core.exception.EnMeFailSendSocialTweetException;
import org.encuestame.core.social.FacebookAPIOperations;
import org.encuestame.core.social.FacebookProfile;
import org.encuestame.core.social.IdentiCaProfile;
import org.encuestame.core.social.IdenticaAPIOperations;
import org.encuestame.core.social.LinkedInAPIOperations;
import org.encuestame.core.social.LinkedInProfile;
import org.encuestame.core.social.oauth.OAuth2Parameters;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.oauth.OAuth1Token;

/**
 * Layer to define pareters to initialize OAuth flows.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public abstract class AbstractAccountConnect extends AbstractSocialController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    protected String apiKey;
    protected String consumerSecret;
    protected String clientId;
    protected Long appId;
    protected String clientSecret;
    protected String redirect = "redirect:/settings/social";

    /**
     * Constructor for OAuth1 protocol.
     * @param apiKey
     * @param consumerSecret
     * @param authorizeUrl
     * @param requestTokenUrl
     * @param accessToken
     */
    public AbstractAccountConnect(
            final String apiKey,
            final String consumerSecret,
            final String authorizeUrl,
            final String requestTokenUrl,
            final String accessToken,
            final SocialProvider provider) {
        this.apiKey = apiKey;
        this.consumerSecret = consumerSecret;
        this.auth1RequestProvider = new OAuth1RequestFlow(apiKey,
                consumerSecret, requestTokenUrl, authorizeUrl, accessToken,
                provider);
    }

    /**
     * Constructor for OAuth2 Protocol.
     * @param clientId
     * @param clientSecret
     * @param appId
     * @param accessTokenUrl
     * @param authorizeUrl
     * @param socialProvider
     */
    public AbstractAccountConnect(
            final String clientId,
            final String clientSecret,
            final Long appId,
            final String accessTokenUrl,
            final String authorizeUrl,
            final SocialProvider socialProvider) {
        this.clientId = clientId;
        this.appId = appId;
        this.clientSecret = clientSecret;
        this.auth2RequestProvider = new OAuth2RequestFlow(new OAuth2Parameters(clientId, clientSecret, accessTokenUrl, authorizeUrl, socialProvider, appId));
    }

    /**
     *
     * @param auth2Parameters
     */
    public AbstractAccountConnect(final OAuth2Parameters auth2Parameters) {
        this.clientId = auth2Parameters.getClientId();
        this.apiKey = auth2Parameters.getApiKey();
        this.clientSecret = auth2Parameters.getClientSecret();
        this.auth2RequestProvider = new OAuth2RequestFlow(auth2Parameters);
    }


    /**
     *
     * @param socialProvider
     * @param accessToken
     * @param account
     * @throws EnMeNoResultsFoundException
     */
    public String checkOAuth1SocialAccount(
            final SocialProvider socialProvider,
            final OAuth1Token accessToken) throws EnMeNoResultsFoundException{
            String socialAccountId = null;
            String username = null;
            String actionToDo = "";
            if (socialProvider.equals(SocialProvider.IDENTICA)) {
                IdenticaAPIOperations apiOperations = new IdenticaAPITemplate(
                        apiKey, consumerSecret, accessToken.getValue(),
                        accessToken.getSecret());
                IdentiCaProfile profile = apiOperations.getUserProfile();
                log.debug("identica profile "+profile.toString());
                username = profile.getScreenName();
                socialAccountId = String.valueOf(profile.getId());
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider,
                      socialAccountId);
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(socialAccountId,
                            accessToken.getValue(), accessToken.getSecret(), username,
                            socialProvider);
                } else {
                    log.warn("This account already exist");
                }
            } else if (socialProvider.equals(SocialProvider.LINKEDIN)) {
                LinkedInAPIOperations apiOperations = new LinkedInAPITemplate(
                        apiKey, consumerSecret, accessToken.getValue(),
                        accessToken.getSecret());
                LinkedInProfile profile = apiOperations.getUserProfile();
                log.debug("identica profile "+profile.toString());
                username = profile.getLastName();
                socialAccountId = profile.getId();
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider,
                      socialAccountId);
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(socialAccountId,
                            accessToken.getValue(), accessToken.getSecret(), username,
                            socialProvider);
                } else {
                    log.warn("This account already exist");
                }
            } else if (socialProvider.equals(SocialProvider.TWITTER)) {

            } else if (socialProvider.equals(SocialProvider.MYSPACE)) {

            } else if (socialProvider.equals(SocialProvider.YAHOO)) {

            }
            log.info("Saved New Social Account");
            return actionToDo;
    }

    /**
     *
     * @param socialProvider
     * @param accessGrant
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeFailSendSocialTweetException
     */
    public String checkOAuth2SocialAccount(final SocialProvider socialProvider,
            final AccessGrant accessGrant) throws EnMeNoResultsFoundException, EnMeFailSendSocialTweetException {
        String socialAccountId = null;
        String username = null;
        String actionToDo = "";
        if (socialProvider.equals(SocialProvider.FACEBOOK)) {
            FacebookAPIOperations facebookAPIOperations = new FacebookAPITemplate(accessGrant.getAccessToken());
            final FacebookProfile profile = facebookAPIOperations.getUserProfile();
            socialAccountId = String.valueOf(profile.getId());
            username = profile.getFirstName() + " "+profile.getLastName();
            getSecurityService().addNewSocialAccount(socialAccountId,
                    accessGrant.getAccessToken(), accessGrant.getRefreshToken(), username,
                    socialProvider);
            facebookAPIOperations.updateStatus("12345 @encuestame");
        } else if (socialProvider.equals(SocialProvider.GOOGLE)) {

        }
        return actionToDo;
    }
}