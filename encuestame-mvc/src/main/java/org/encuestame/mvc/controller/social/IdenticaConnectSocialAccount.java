package org.encuestame.mvc.controller.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

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
            return null;
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
            WebRequest request,
            final UserAccount account) throws Exception {
        final OAuth1Token accessToken = auth1RequestProvider.getAccessToken(verifier, request);
        log.debug("OAUTH 1 ACCESS TOKEN " + accessToken.toString());
        this.checkOAuth1SocialAccount(SocialProvider.IDENTICA, accessToken);
        return this.redirect+"#provider="+SocialProvider.IDENTICA.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}