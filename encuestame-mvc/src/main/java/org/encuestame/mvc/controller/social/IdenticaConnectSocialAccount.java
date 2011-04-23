package org.encuestame.mvc.controller.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.business.service.social.OAuth1RequestProvider;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class IdenticaConnectSocialAccount extends AbstractSocialController {

    /**
     *
     */
    public OAuth1RequestProvider auth1RequestProvider;

    /**
     *
     */
    @Inject
    public IdenticaConnectSocialAccount(
            @Value("${identica.consumer.key}") String apiKey,
            @Value("${identica.consumer.secret}") String consumerSecret,
            @Value("${identica.authorizeUrl}") String authorizeUrl,
            @Value("${identica.requestToken}") String requestTokenUrl,
            @Value("${identica.accessToken}") String accessToken) {
        auth1RequestProvider = new OAuth1RequestProvider(apiKey,
                consumerSecret, requestTokenUrl, authorizeUrl, accessToken,
                SocialProvider.IDENTICA);
    }

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/connect/identica", method = RequestMethod.GET)
    public String signinTwitterGet() {
        return "connect/account";
    }

    private static final String OAUTH_TOKEN_ATTRIBUTE = "oauthToken";

    @RequestMapping(value = "/connect/identica", method = RequestMethod.POST)
    public String signinTwitterGet(
            @RequestParam(required = false) String scope,
            WebRequest request,
            HttpServletRequest httpRequest) {
        final OAuth1Token requestToken = auth1RequestProvider.getRequestToken(httpRequest);
        request.setAttribute(OAUTH_TOKEN_ATTRIBUTE, requestToken, WebRequest.SCOPE_SESSION);
        return auth1RequestProvider.buildRequestTokenUrl(httpRequest);
    }

    /**
     * Process the authorization callback from an OAuth 2 service provider.
     * Called after the member authorizes the connection, generally done by
     * having he or she click "Allow" in their web browser at the provider's
     * site. On authorization verification, connects the member's local account
     * to the account they hold at the service provider.
     */
    @RequestMapping(value = "/social/back/identica", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            WebRequest request) {
        log.debug("Verifier "+verifier);
        log.debug("token "+token);
        OAuth1Token accessToken = auth1RequestProvider.getoAuth1RestTemplate()
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