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
import org.encuestame.business.service.social.api.IdenticaAPITemplate;
import org.encuestame.core.social.IdentiCaProfile;
import org.encuestame.core.social.IdenticaAPIOperations;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Value;

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
     *
     * @param socialProvider
     * @param accessToken
     * @param account
     */
    public void checkOAuth1SocialAccount(
            final SocialProvider socialProvider,
            final OAuth1Token accessToken,
            final UserAccount account){
            Long socialAccountId = null;
            String username = null;
            if (socialProvider.equals(SocialProvider.IDENTICA)) {
                IdenticaAPIOperations apiOperations = new IdenticaAPITemplate(
                        apiKey, consumerSecret, accessToken.getValue(),
                        accessToken.getSecret());
                IdentiCaProfile profile = apiOperations.getUserProfile();
                log.debug("identica profile "+profile.toString());
                username = profile.getScreenName();
                socialAccountId = profile.getId();
                final SocialAccount socialAccount = getSecurityService().getCurrentSocialAccount(socialProvider,
                      socialAccountId);
                if (socialAccount == null) {
                    getSecurityService().addNewSocialAccount(socialAccountId,
                            accessToken.getValue(), accessToken.getSecret(), username,
                            account, socialProvider);
                } else {
                    log.warn("This account already exist");
                }
            } else if (socialProvider.equals(SocialProvider.LINKEDIN)) {

            } else if (socialProvider.equals(SocialProvider.TWITTER)) {

            } else if (socialProvider.equals(SocialProvider.MYSPACE)) {

            } else if (socialProvider.equals(SocialProvider.YAHOO)) {

            }
            log.info("Saved New Social Account");
    }
}
