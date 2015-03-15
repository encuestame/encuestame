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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.social.SocialProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * LinkedIn OAuth Connect Support.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
@Controller
public class LinkedInConnectSocialAccount extends AbstractAccountConnect {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param apiKey
     * @param consumerSecret
     * @param authorizeUrl
     * @param requestTokenUrl
     * @param accessToken
     */
    @Inject
    public LinkedInConnectSocialAccount(
            @Value("${linkedIn.oauth.api.key}") String apiKey,
            @Value("${linkedIn.oauth.api.secret}") String consumerSecret,
            @Value("${linkedIn.oauth.authorize.url}") String authorizeUrl,
            @Value("${linkedIn.oauth.request.token}") String requestTokenUrl,
            @Value("${linkedIn.oauth.access.token}") String accessToken) {
        super(apiKey, consumerSecret, authorizeUrl, requestTokenUrl, accessToken, SocialProvider.LINKEDIN);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/connect/linkedin", method = RequestMethod.GET)
    public String signinLinkedInGet() {
        return "redirect:/settings/social";
    }

    /**
     *
     * @param scope
     * @param request
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/connect/linkedin", method = RequestMethod.POST)
    public String signinLinkedInPost(
            @RequestParam(required = false) String scope,
            WebRequest request,
            HttpServletRequest httpRequest) {
        try {
            return auth1RequestProvider.buildOAuth1AuthorizeUrl(scope, request, httpRequest);
        } catch (EnMeOAuthSecurityException e) {
            log.error(e);
            log.fatal("OAuth Exception:{"+e.getMessage());
            RequestSessionMap.setErrorMessage(getMessage("social.bad.credentials", httpRequest, null));
            return "redirect:/settings/social";
        }
    }

    /**
     *
     * @param token
     * @param verifier
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/social/back/linkedin", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            HttpServletRequest httpRequest,
            WebRequest request, final UserAccount account) throws Exception {
         try {
             final OAuth1Token accessToken = auth1RequestProvider.getAccessToken(verifier, request);
             this.checkOAuth1SocialAccount(SocialProvider.LINKEDIN, accessToken);
             log.debug("OAUTH 1 ACCESS TOKEN:{ " + accessToken.toString());
             this.checkOAuth1SocialAccount(SocialProvider.TWITTER, accessToken);
         } catch (EnMeOAuthSecurityException e1) {
             e1.printStackTrace();
             RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
         } catch (EnMeExistPreviousConnectionException e1) {
             e1.printStackTrace();
             RequestSessionMap.setErrorMessage(getMessage("social.repeated.account", httpRequest, null));
         } catch (Exception e) {
             //e.printStackTrace();
             RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
         }
         return this.redirect+"#provider="+SocialProvider.LINKEDIN.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}
