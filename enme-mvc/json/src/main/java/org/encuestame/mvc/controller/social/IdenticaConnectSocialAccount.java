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
 * Identica connect support.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
@Controller
public class IdenticaConnectSocialAccount extends AbstractAccountConnect {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Constructor.
     */
    @Inject
    public IdenticaConnectSocialAccount(
            @Value("${identica.consumer.key}") String apiKey,
            @Value("${identica.consumer.secret}") String consumerSecret,
            @Value("${identica.authorizeUrl}") String authorizeUrl,
            @Value("${identica.requestToken}") String requestTokenUrl,
            @Value("${identica.accessToken}") String accessToken) {
        super(apiKey, consumerSecret, authorizeUrl, requestTokenUrl, accessToken, SocialProvider.IDENTICA);
    }

    /**
     * Identi.ca connect Get.
     * @return settings redirect view.
     */
    @RequestMapping(value = "/connect/identica", method = RequestMethod.GET)
    public String identicaConnectPost() {
        return "redirect:/settings/social";
    }

    /**
     *
     * @param scope
     * @param request
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/connect/identica", method = RequestMethod.POST)
    public String connectIdenticaSocialAccount(@RequestParam(required = false) String scope,
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
     * @param error
     * @param httpRequest
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/social/back/identica", method = RequestMethod.GET, params = "oauth_problem")
    public String oauth1Callback(
            @RequestParam("oauth_problem") String error,
            HttpServletRequest httpRequest,
            WebRequest request) throws Exception {
        log.fatal("OAuth Error:{"+error);
        RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
        return "redirect:/settings/social";
    }

    /**
     * Process the authorization callback from an OAuth 1 identi.ca provider.
     * @throws Exception
     */
    @RequestMapping(value = "/social/back/identica", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            HttpServletRequest httpRequest,
            WebRequest request,
            final UserAccount account) throws Exception {
        try {
             final OAuth1Token accessToken = auth1RequestProvider.getAccessToken(verifier, request);
             log.debug("OAUTH 1 ACCESS TOKEN " + accessToken.toString());
             this.checkOAuth1SocialAccount(SocialProvider.IDENTICA, accessToken);
        } catch (EnMeOAuthSecurityException e1) {
               RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
        } catch (EnMeExistPreviousConnectionException e1) {
               RequestSessionMap.setErrorMessage(getMessage("social.repeated.account", httpRequest, null));
        } catch (Exception e) {
               RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
        }
        return this.redirect+"#provider="+SocialProvider.IDENTICA.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}