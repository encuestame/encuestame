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
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * MySpace connect support.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
@Controller
public class MySpaceConnectSocialAccount extends AbstractAccountConnect {

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
    public MySpaceConnectSocialAccount(
            @Value("${myspace.consumer.key}") String apiKey,
            @Value("${myspace.consumer.secret}") String consumerSecret,
            @Value("${myspace.authorizeUrl}") String authorizeUrl,
            @Value("${myspace.requestToken}") String requestTokenUrl,
            @Value("${myspace.accessToken}") String accessToken) {
        super(apiKey, consumerSecret, authorizeUrl, requestTokenUrl, accessToken, SocialProvider.MYSPACE);
    }

    @RequestMapping(value = "/connect/myspace", method = RequestMethod.GET)
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
    @RequestMapping(value = "/connect/myspace", method = RequestMethod.POST)
    public String signinMyspacePost(
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


    /**
     *
     * @param token
     * @param verifier
     * @param request
     * @return
     */
    @RequestMapping(value = "/social/back/myspace", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            WebRequest request) {
        OAuth1Token accessToken;
        try {
            accessToken = auth1RequestProvider.getAccessToken(verifier, request);
            log.error("OAUTH 1 ACCESS TOKEN " + accessToken.toString());
            return "connect/account";
        } catch (EnMeOAuthSecurityException e) {
             log.error(e);
           return "connect/account";
        }
    }
}
