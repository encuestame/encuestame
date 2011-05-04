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
package org.encuestame.mvc.controller.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.exception.EnMeFailSendSocialTweetException;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller to Get OAuth credentias with Facebook Account.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 4:46:18 PM
 */
@Controller
public class FacebookConnectSocialAccount extends AbstractAccountConnect{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param appId
     * @param accessTokenUrl
     * @param authorizeUrl
     * @param clientSecret
     * @param clientId
     */
    @Inject
    public FacebookConnectSocialAccount(
            @Value("${facebook.api.id}") String appId,
            @Value("${facebook.oauth.accesToken}") String accessTokenUrl,
            @Value("${facebook.oauth.authorize}") String authorizeUrl,
            @Value("${facebook.api.secret}") String clientSecret,
            @Value("${facebook.api.key}") String clientId) {
       super(clientId, clientSecret,Long.valueOf(appId), accessTokenUrl, authorizeUrl, SocialProvider.FACEBOOK);
    }


    /**
     * To add account
     * @return
     */
    @RequestMapping(value="/connect/facebook", method = RequestMethod.GET)
    public String signinTwitterGet(){
        return "redirect:/settings/social";
    }

    /**
     *
     * @param scope
     * @param httpRequest
     * @return
     */
    @RequestMapping(value="/connect/facebook", method = RequestMethod.POST)
    public String connectFacebookPost(
            @RequestParam(required=false) String scope,
            HttpServletRequest httpRequest){
           log.debug("CONNECT POST FACEBOOK");
           return this.auth2RequestProvider.buildOAuth2AuthorizeUrl(SocialUtils.FACEBOOK_SCOPE,
                  httpRequest, true);
    }


    /**
     *
     * @param code
     * @param httpRequest
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/social/back/facebook", method=RequestMethod.GET, params="code")
    public String oauth2Callback(
            @RequestParam("code") String code,
            HttpServletRequest httpRequest,
            WebRequest request) throws Exception {
        final AccessGrant accessGrant = auth2RequestProvider.getAccessGrant(code, httpRequest);
        log.debug(accessGrant.getAccessToken());
        log.debug(accessGrant.getRefreshToken());
        checkOAuth2SocialAccount(SocialProvider.FACEBOOK, accessGrant);
        return this.redirect+"#provider="+SocialProvider.FACEBOOK.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}