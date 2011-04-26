package org.encuestame.mvc.controller.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.OAuth1Token;
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
        return auth1RequestProvider.buildOAuth1AuthorizeUrl(scope, request, httpRequest);
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
            WebRequest request, final UserAccount account) throws Exception {
         final OAuth1Token accessToken = auth1RequestProvider.getAccessToken(verifier, request);
         this.checkOAuth1SocialAccount(SocialProvider.LINKEDIN, accessToken);
         return this.redirect+"#provider="+SocialProvider.LINKEDIN.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}
