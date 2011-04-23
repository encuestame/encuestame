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

import org.apache.log4j.Logger;
import org.encuestame.core.social.oauth2.OAuth2RestOperations;
import org.encuestame.core.social.oauth2.OAuth2Support;
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
public class FacebookConnectSocialAccount extends AbstractSocialController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Consumer Key.
     */
    public @Value("${facebook.api.key}") String apiKey;

    /**
     * Consumer Secret.
     */
    public @Value("${facebook.api.secret}") String consumerSecret;


    public @Value("${facebook.api.id}") String appID;

    /**
     * Authorize Url.
     */
    public @Value("${facebook.oauth.authorize}") String authorizeUrl;

    /**
     * Request Token Url.
     */
    public @Value("${facebook.oauth.accesToken}") String accessTokenUrl;

    OAuth2RestOperations tm;


    /**
     * To add account
     * @return
     */
    @RequestMapping(value="/connect/facebook", method = RequestMethod.GET)
    public String signinTwitterGet(){
        return "connect/account";
    }

    @RequestMapping(value="/connect/facebook", method = RequestMethod.POST)
    public String signinTwitterGet( @RequestParam(required=false) String scope){
        this.tm = new OAuth2Support(this.appID, this.consumerSecret,
                this.authorizeUrl,
                this.accessTokenUrl);
        final String urlFracebook = tm.buildAuthorizeUrl("http://localhost:8080/encuestame/social/back/facebook", "email,read_stream,publish_stream,user_status,user_location");
        log.debug("FACEBOOKKKKK "+urlFracebook);
                return "redirect:" + urlFracebook+"&scope=email,read_stream,publish_stream,user_status,user_location";
    }

    @RequestMapping(value="/social/back/facebook", method=RequestMethod.GET, params="code")
    public String oauth2Callback(
            @RequestParam("code") String code,
            WebRequest request) {
        log.debug("***********************************************************");
        log.debug(code);
        AccessGrant accessGrant = tm.exchangeForAccess(code, "http://localhost:8080/encuestame/social/back/facebook");
        log.debug(accessGrant.getAccessToken());
        log.debug(accessGrant.getRefreshToken());
        log.debug("***********************************************************");
        return "connect/account";
    }
}
