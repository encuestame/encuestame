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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class PlurkConnectSocialAccount extends AbstractAccountConnect{

  	/**
  	 *  Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Constructor.
     */
    @Inject
    public PlurkConnectSocialAccount(
            @Value("${plurk.consumer.key}") String apiKey,
            @Value("${plurk.consumer.secret}") String consumerSecret,
            @Value("${plurk.authorizeUrl}") String authorizeUrl,
            @Value("${plurk.requestToken}") String requestTokenUrl,
            @Value("${plurk.accessToken}") String accessToken) {
        super(apiKey, consumerSecret, authorizeUrl, requestTokenUrl, accessToken, SocialProvider.PLURK);
    }

    /**
     * Identi.ca connect Get.
     * @return settings redirect view.
     */
    @RequestMapping(value = "/connect/plurk", method = RequestMethod.GET)
    public String plurkConnectPost() {
        return "redirect:/settings/social";
    }

    /**
     *
     * @param scope
     * @param request
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/connect/plurk", method = RequestMethod.POST)
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
    @RequestMapping(value = "/social/back/plurk", method = RequestMethod.GET, params = "oauth_problem")
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
    @RequestMapping(value = "/social/back/plurk", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            HttpServletRequest httpRequest,
            WebRequest request,
            final UserAccount account) throws Exception {
        OAuth1Token accessToken;
        try {
            Assert.notNull(httpRequest);
            accessToken = auth1RequestProvider.getAccessToken(verifier, request);
            log.debug("OAUTH 1 ACCESS TOKEN:{ " + accessToken.toString());
            this.checkOAuth1SocialAccount(SocialProvider.PLURK, accessToken);
        } catch (EnMeOAuthSecurityException e1) {
            RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
            e1.printStackTrace();
        } catch (EnMeExistPreviousConnectionException e1) {
            RequestSessionMap.setErrorMessage(getMessage("social.repeated.account", httpRequest, null));
            e1.printStackTrace();
        } catch (Exception e) {
            RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
            //e.printStackTrace();
        }
        return this.redirect+"#provider="+SocialProvider.PLURK.toString().toLowerCase()+"&refresh=true&successful=true";
    }
}
