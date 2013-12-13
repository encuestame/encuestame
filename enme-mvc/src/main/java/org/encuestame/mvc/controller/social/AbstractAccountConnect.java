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
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.oauth1.support.OAuth1RequestFlow;
import org.encuestame.oauth2.support.OAuth2Parameters;
import org.encuestame.oauth2.support.OAuth2RequestFlow;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.social.api.FacebookAPITemplate;
import org.encuestame.social.api.IdenticaAPITemplate;
import org.encuestame.social.api.LinkedInAPITemplate;
import org.encuestame.social.api.TumblrAPITemplate;
import org.encuestame.social.api.TwitterAPITemplate;
import org.encuestame.social.api.support.FacebookAPIOperations;
import org.encuestame.social.api.support.IdentiCaProfile;
import org.encuestame.social.api.support.IdenticaAPIOperations;
import org.encuestame.social.api.support.LinkedInAPIOperations;
import org.encuestame.social.api.support.LinkedInProfile;
import org.encuestame.social.api.support.TumblrAPIOperations;
import org.encuestame.social.api.support.TwitterAPIOperations;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;

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
    	log.debug("************ OAUTH 1 ***************");
    	log.debug("Api key -->" + apiKey);
    	log.debug("consumerSecret key -->" + consumerSecret);
    	log.debug("authorizeUrl key -->" + authorizeUrl);
    	log.debug("appId key -->" + appId);
    	log.debug("clientSecret key -->" + clientSecret);
    	log.debug("************ OAUTH 1 ***************");
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
     * @throws Exception
     */
    public String checkOAuth1SocialAccount(
            final SocialProvider socialProvider,
            final OAuth1Token accessToken) throws Exception{
            log.trace("OAUTH1AccessToken "+accessToken.toString());
            log.trace("OAUTH1AccessToken Provider "+socialProvider);
            String actionToDo = "";
            // https://github.com/e14n/pump.io
            // on 2013 identi.ca changed  name to pump.io 
            if (socialProvider.equals(SocialProvider.IDENTICA) || socialProvider.equals(SocialProvider.PUMPIO)) {
                IdenticaAPIOperations apiOperations = new IdenticaAPITemplate(
                        apiKey, consumerSecret, accessToken.getValue(),
                        accessToken.getSecret());
                IdentiCaProfile profile = apiOperations.getUserProfile();
                log.debug("identica profile "+profile.toString());
                SocialUserProfile profileAPI = apiOperations.getProfile();
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider,
                        String.valueOf(profile.getId()));
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(
                            accessToken.getValue(), accessToken.getSecret(), null, profileAPI,
                            socialProvider, getUserAccount());
                } else {
                    log.warn("This account already exist");
                    throw new EnMeExistPreviousConnectionException(getMessage("social.repeated.account"));
                }
            } else if (socialProvider.equals(SocialProvider.LINKEDIN)) {
                LinkedInAPIOperations apiOperations = new LinkedInAPITemplate(
                        apiKey, consumerSecret, accessToken.getValue(),
                        accessToken.getSecret());
                LinkedInProfile profile = apiOperations.getUserProfile();
                SocialUserProfile profileAPI = apiOperations.getProfile();
                log.debug("linkedin profile "+profile.toString());
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider, profile.getId());
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(
                            accessToken.getValue(), accessToken.getSecret(), null, profileAPI,
                            socialProvider, getUserAccount());
                } else {
                    log.warn("This account already exist");
                    throw new EnMeExistPreviousConnectionException(getMessage("social.repeated.account"));
                }
            } else if (socialProvider.equals(SocialProvider.TWITTER)) {
                TwitterAPIOperations operations = new TwitterAPITemplate(consumerSecret, apiKey, accessToken.getValue(),
                        accessToken.getSecret());
                SocialUserProfile profile = operations.getProfile();
                log.debug("twitter profile "+profile.toString());
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider, profile.getId());
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(
                            accessToken.getValue(), accessToken.getSecret(), null, profile,
                            socialProvider, getUserAccount());
                } else {
                    log.warn("This account already exist");
                    throw new EnMeExistPreviousConnectionException(getMessage("social.repeated.account"));
                }
                //FUTURE - Issues with OAuth1 request.
            } else if (socialProvider.equals(SocialProvider.YAHOO)) {
                //FUTURE - Only valid on defined domain.
                log.debug("Yahoo provider is disabled");
            } else if (socialProvider.equals(SocialProvider.TUMBLR)) {
            	TumblrAPIOperations apiOperations = new TumblrAPITemplate(
                        apiKey, consumerSecret, accessToken.getValue(),
                        accessToken.getSecret());
                SocialUserProfile profile = apiOperations.getProfile();
                SocialUserProfile profileAPI = apiOperations.getProfile();
                log.debug("linkedin profile "+profile.toString());
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider, profile.getId());
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(
                            accessToken.getValue(), accessToken.getSecret(), null, profileAPI,
                            socialProvider, getUserAccount());
                } else {
                    log.warn("This account already exist");
                    throw new EnMeExistPreviousConnectionException(getMessage("social.repeated.account"));
                }
            } else if (socialProvider.equals(SocialProvider.PLURK)) {
            	//TODO:
            }
            log.info("Saved New Social Account");
            return actionToDo;
    }

    /**
     * Check and save a social account if exist previously.
     * @param socialProvider {@link SocialProvider}
     * @param accessGrant {@link AccessGrant}
     * @return
     * @throws Exception if the social account exist, throw a exception
     */
    public String checkOAuth2SocialAccount(final SocialProvider socialProvider,
            final AccessGrant accessGrant) throws Exception {
        String actionToDo = "";
        if (socialProvider.equals(SocialProvider.FACEBOOK)) {
            final FacebookAPIOperations facebookAPIOperations = new FacebookAPITemplate(accessGrant.getAccessToken());
            final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider, facebookAPIOperations.getProfile().getId(), facebookAPIOperations.getProfile().getUsername());
            if (socialAccount == null) {
                  getSecurityService().addNewSocialAccount(
                          accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpires(),
                          facebookAPIOperations.getProfile(),
                          socialProvider, getUserAccount());
            } else {
                log.warn("This account already exist");
                throw new EnMeExistPreviousConnectionException(getMessage("social.repeated.account"));
            }

        }

        //    else if (socialProvider.equals(SocialProvider.GOOGLE_BUZZ)) {
        //            final BuzzAPIOperations apiOperations = new GoogleBuzzAPITemplate(accessGrant.getAccessToken(), this.apiKey);
        //            log.debug(apiOperations.getProfile());
        //            getSecurityService().addNewSocialAccount(
        //                    accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpires(),
        //                    apiOperations.getProfile(),
        //                    socialProvider, getUserAccount());
        //        }
        return actionToDo;
    }
}