package org.encuestame.mvc.controller.social;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.business.service.social.api.BuzzAPITemplate;
import org.encuestame.core.social.oauth1.OAuth1RestOperations;
import org.encuestame.core.social.oauth1.OAuth1Support;
import org.encuestame.core.social.oauth2.OAuth2RestOperations;
import org.encuestame.core.social.oauth2.OAuth2Support;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LinkedInConnectSocialAccount extends AbstractSocialController {

    final String clientId = "287300901667.apps.googleusercontent.com";

    final String clientSecret = "oIJZgke5nPDSEEeNOy0lBrxv";

    OAuth1RestOperations tm;

    /**
     * Consumer Key.
     */
    public @Value("${linkedIn.oauth.api.key}")
    String apiKey;

    /**
     * Consumer Secret.
     */
    public @Value("${linkedIn.oauth.api.secret}")
    String consumerSecret;

    /**
     * Authorize Url.
     */
    public @Value("${linkedIn.oauth.authorize.url}")
    String authorizeUrl;

    /**
     * Request Token Url.
     */
    public @Value("${linkedIn.oauth.request.token}")
    String requestTokenUrl;

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/connect/linkedin", method = RequestMethod.GET)
    public String signinTwitterGet() {
        return "connect/account";
    }

    private static final String OAUTH_TOKEN_ATTRIBUTE = "oauthToken";

    @RequestMapping(value = "/connect/linkedin", method = RequestMethod.POST)
    public String signinTwitterGet(
            @RequestParam(required = false) String scope, WebRequest request) {
        tm = new OAuth1Support(
                apiKey,
                consumerSecret,
                "https://api.linkedin.com/uas/oauth/requestToken",
                "https://www.linkedin.com/uas/oauth/authorize?oauth_token={requestToken}",
                "https://api.linkedin.com/uas/oauth/accessToken");
        OAuth1Token requestToken = tm
                .fetchNewRequestToken("http://localhost:8080/encuestame/social/back/linkedin");
        request.setAttribute(OAUTH_TOKEN_ATTRIBUTE, requestToken,
                WebRequest.SCOPE_SESSION);
        return "redirect:"
                + tm.buildAuthorizeUrl(requestToken.getValue(),
                        "http://localhost:8080/encuestame/social/back/linkedin");
    }

    /**
     * Process the authorization callback from an OAuth 2 service provider.
     * Called after the member authorizes the connection, generally done by
     * having he or she click "Allow" in their web browser at the provider's
     * site. On authorization verification, connects the member's local account
     * to the account they hold at the service provider.
     */
    @RequestMapping(value = "/social/back/linkedin", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            WebRequest request) {
        log.debug("Verifier "+verifier);
        log.debug("token "+token);
        OAuth1Token accessToken = tm
                .exchangeForAccessToken(new org.encuestame.core.social.oauth1.AuthorizedRequestToken(
                        extractCachedRequestToken(request), verifier));
        log.debug(accessToken.toString());
        System.out.println("OAUTH 1 ACCESS TOKEN " + accessToken.toString());
        return "connect/account";
    }

    private OAuth1Token extractCachedRequestToken(
            WebRequest request) {
        OAuth1Token requestToken = (OAuth1Token) request
                .getAttribute(OAUTH_TOKEN_ATTRIBUTE, WebRequest.SCOPE_SESSION);
        request.removeAttribute(OAUTH_TOKEN_ATTRIBUTE, WebRequest.SCOPE_SESSION);
        return requestToken;
    }

}
