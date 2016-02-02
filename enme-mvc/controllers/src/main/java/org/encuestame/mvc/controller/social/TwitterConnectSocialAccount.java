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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.social.SocialProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller to SignIn with Twitter Account.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 4:46:18 PM
 */
@Controller
public class TwitterConnectSocialAccount extends AbstractAccountConnect {

     /**
     * Log.
     */
     private Log log = LogFactory.getLog(this.getClass());

     /**
      * Constructor.
      */
    @Inject
    public TwitterConnectSocialAccount(
            @Value("${twitter.oauth.consumerKey}") String apiKey,
            @Value("${twitter.oauth.consumerSecret}") String consumerSecret,
            @Value("${twitter.oauth.authorize}") String authorizeUrl,
            @Value("${twitter.oauth.request.token}") String requestTokenUrl,
            @Value("${twitter.oauth.access.token}") String accessToken) {
        super(apiKey, consumerSecret, authorizeUrl, requestTokenUrl, accessToken, SocialProvider.TWITTER);
    }

    /**
     * Identi.ca connect Get.
     * @return settings redirect view.
     */
    @RequestMapping(value = "/connect/twitter", method = RequestMethod.GET)
    public String twitterConnectPost() {
        return "redirect:/settings/social";
    }

    /**
     *
     * @param scope
     * @param request
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/connect/twitter", method = RequestMethod.POST)
    public String connectTwitterSocialAccount(@RequestParam(required = false) String scope,
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
     * Process the authorization callback from an OAuth 1 twitter provider.
     * @throws Exception
     */
    @RequestMapping(value = "/social/back/twitter", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            HttpServletRequest httpRequest,
            WebRequest request,
            final UserAccount account) {
        OAuth1Token accessToken;
        try {
            Assert.notNull(httpRequest);
            accessToken = auth1RequestProvider.getAccessToken(verifier, request);
            log.debug("OAUTH 1 ACCESS TOKEN:{ " + accessToken.toString());
            this.checkOAuth1SocialAccount(SocialProvider.TWITTER, accessToken);
        } catch (EnMeOAuthSecurityException e1) {
            RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
            //e1.printStackTrace();
            log.error(e1);
        } catch (EnMeExistPreviousConnectionException e1) {
            RequestSessionMap.setErrorMessage(getMessage("social.repeated.account", httpRequest, null));
            //e1.printStackTrace();
            log.error(e1);
        } catch (Exception e) {
            RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
            //e.printStackTrace();
            log.error(e);
        }
        return this.redirect+"#provider="+SocialProvider.TWITTER.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}
