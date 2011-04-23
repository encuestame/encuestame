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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.service.social.connect.ITwitterSocialProvider;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.core.util.OAuthUtils;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
public class TwitterConnectSocialAccount extends AbstractSocialController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Default Base CallBack.
     */
    private String baseCallbackUrl;

    /**
     * Twitter Provider Name.
     */
    public final String TWITTER_PROVIDER_NAME = "twitter";

    /**
     * Constructor.
     */
    public TwitterConnectSocialAccount() {
        this.baseCallbackUrl = "/signin/";
    }

    /**
     * Twitter Service Connect.
     */
    private ITwitterSocialProvider twitterServiceConnect;

    /**
     * Render the signin form for the service provider.
     */
    @RequestMapping(value="/connect/twitter", method = RequestMethod.GET)
    public String signinTwitterGet(){
        String baseViewPath = "signin/twitter";
        if (this.twitterServiceConnect.isConnected(1L)) {
            return baseViewPath + "Connected";
        } else {
            return baseViewPath + "Connect";
        }
    }

    /**
     * Disconnect from the provider.
     * The member has decided they no longer wish to use the service provider from this application.
     * @throws EnMeNoResultsFoundException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value="/signin/twitter", method=RequestMethod.DELETE)
    public String disconnect(@PathVariable String name) throws EnMeNoResultsFoundException {
        this.twitterServiceConnect.disconnect(getUserAccount().getUid());
        return "redirect:/signin/" + name;
    }

    /**
     * Process a connect form submission by commencing the process of establishing a connection to the provider
     * on behalf of the user account.
     * Fetches a new request token from the provider, temporarily stores it in the session,
     * then redirects the member to the provider's site for authorization.
     */
    @RequestMapping(value="/signin/twitter", method = RequestMethod.POST)
    public String signinTwitterPost(
            final WebRequest request,
            final HttpServletRequest requestHttp,
            final HttpServletResponse response){
        ITwitterSocialProvider provider = this.twitterServiceConnect;
        log.debug("api key "+provider.getApiKey());
        //preConnect(provider, request);
        log.debug(InternetUtils.getDomain(requestHttp)+baseCallbackUrl + "twitter");
        final OAuth1Token requestToken =
                       provider.fetchNewRequestToken(InternetUtils.getDomain(requestHttp)+baseCallbackUrl + "twitter");
        log.debug("RequesToken "+requestToken.toString());
        request.setAttribute(OAuthUtils.OAUTH_TOKEN_ATTRIBUTE, requestToken, WebRequest.SCOPE_SESSION);
        //stored in the session
        return "redirect:" + provider.buildAuthorizeUrl(requestToken.getValue());
    }

    /**
     * Authorize Callback.
     * @param token
     * @param verifier
     * @param request
     * @return
     */
    @RequestMapping(value="/signin/twitter", method = RequestMethod.GET, params = "oauth_token")
    public String authorizeCallback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value="oauth_verifier", defaultValue = "verifier")
            final String verifier,
            final HttpServletRequest request2,
            final HttpServletResponse response,
            final WebRequest request) {
        //get token value from user session.
        OAuth1Token requestToken = (OAuth1Token) request.getAttribute(
                   OAuthUtils.OAUTH_TOKEN_ATTRIBUTE, WebRequest.SCOPE_SESSION);
        //log.debug("requestToken "+requestToken.toString());
        if (requestToken != null) {
            //remove attribute
            request.removeAttribute(OAuthUtils.OAUTH_TOKEN_ATTRIBUTE, WebRequest.SCOPE_SESSION);
            ITwitterSocialProvider provider = this.twitterServiceConnect;
            //authorize token.
            log.debug("oauth_token "+token);
            log.debug("oauth_verifier "+verifier);
            log.debug("requestToken "+requestToken);
            UserAccount account = null;
            /*final AuthorizedRequestToken d = new AuthorizedRequestToken(requestToken, verifier);
            try {
                log.debug("User token:"+token);
                //search currently user connection
                //account = provider.connect(account.getUid(), d); //if connection not exist, throw exception
                //TODO: don't works with twitter.
                //if exist, the user is authenticated.
                authenticate(account);
                //authenticate.
                return "redirect:/account/dashboard";
            } catch (EnMeDomainNotFoundException e) {

               WebUtils.setSessionAttribute(request2, "test", "test2");
               final AccountConnection connection = provider.connect(null, d);
               //we need store connectio for complete registrer to new account.
               WebUtils.setSessionAttribute(request2, "connection", connection);
               return "redirect:/user/signup";
            }*/
        } else {
            log.error("Request token not found");
            return "redirect:/signin/" + "twitter";
        }
        //postConnect(provider, 1, request);
        //FlashMap.setSuccessMessage("Your Greenhouse account is now connected to your " + provider.getDisplayName() + " account!");
        return "";
    }

    /**
     * @return the twitterServiceConnect
     */
    public ITwitterSocialProvider getTwitterServiceConnect() {
        return twitterServiceConnect;
    }

    /**
     * @param twitterServiceConnect the twitterServiceConnect to set
     */
    public void setTwitterServiceConnect(final ITwitterSocialProvider twitterServiceConnect) {
        this.twitterServiceConnect = twitterServiceConnect;
    }
}
