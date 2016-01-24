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
 * Yahoo OAuth Connect Support.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
@Controller
public class YahooConnectSocialAccount extends AbstractAccountConnect {

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(YahooConnectSocialAccount.class);

            /**
             *
             * @param apiKey
             * @param consumerSecret
             * @param authorizeUrl
             * @param requestTokenUrl
             * @param accessToken
             */

    @Inject
   public YahooConnectSocialAccount(
           @Value("${yahoo.consumer.key}") String apiKey,
           @Value("${yahoo.consumer.secret}") String consumerSecret,
           @Value("${yahoo.authorizeUrl}") String authorizeUrl,
           @Value("${yahoo.requestToken}") String requestTokenUrl,
           @Value("${yahoo.accessToken}") String accessToken) {
       super(apiKey, consumerSecret, authorizeUrl, requestTokenUrl, accessToken, SocialProvider.YAHOO);
   }

       /**
        *
        * @return
        */
    @RequestMapping(value = "/connect/yahoo", method = RequestMethod.GET)
    public String signinTwitterGet() {
         return "redirect:/settings/social";
    }

    /**
     *
     * @param scope
     * @param request
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/connect/yahoo", method = RequestMethod.POST)
    public String signinYahooPost(
            @RequestParam(required = false) String scope,
            final WebRequest request,
            final HttpServletRequest httpRequest) {
        try {
            return auth1RequestProvider.buildOAuth1AuthorizeUrl(scope, request, httpRequest);
        } catch (EnMeOAuthSecurityException e) {
               log.error(e);
               return null;
        }
    }



    @RequestMapping(value = "/social/back/yahoo", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            WebRequest request) {
        OAuth1Token accessToken;
        try {
            accessToken = auth1RequestProvider.getAccessToken(verifier, request);
            //System.out.println("OAUTH 1 ACCESS TOKEN " + accessToken.toString());
            return "connect/account";
        } catch (EnMeOAuthSecurityException e) {
            log.debug(e);
               return null;
        }
    }
}
