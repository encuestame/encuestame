
package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.business.service.social.OAuth1RequestFlow;
import org.encuestame.business.service.social.OAuth2RequestFlow;
import org.encuestame.business.service.social.api.BuzzAPITemplate;
import org.encuestame.core.social.BuzzAPIOperations;
import org.encuestame.core.social.SocialUserProfile;
import org.encuestame.core.social.oauth.OAuth2Parameters;
import org.encuestame.core.util.OAuthUtils;
import org.encuestame.mvc.controller.social.AbstractSocialController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * Sign In controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */
@Controller
public class SignInController extends AbstractSocialController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Signin Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/user/signin", method = RequestMethod.GET)
    public String signInController(
            final ModelMap model,
            HttpServletResponse response,
            HttpServletRequest request) {
        final Boolean enabledSocialSignIn = EncuestamePlaceHolderConfigurer
                     .getBooleanProperty("application.social.signin.enabled");
        request.setAttribute("social", enabledSocialSignIn);
        log.debug("login");
        return "signin";
    }

    /**
     * Forgot Password Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/user/forgot", method = RequestMethod.GET)
    public String forgotPasswordController(final ModelMap model) {
        log.debug("forgot password");
        return "forgot";
    }

    OAuth1RequestFlow auth1RequestProvider;

    /**
     * Sign in by {@link SocialProvider} account.
     * @param provider
     * @return
     */
    @RequestMapping(value="/signin/{provider}", method = RequestMethod.POST)
    public String signinSocialPost(
        @PathVariable String provider,
        WebRequest request,
        @RequestParam(required = false) String scope,
        HttpServletRequest httpRequest){
        final StringBuilder url = new StringBuilder();
        final SocialProvider providerEnum = SocialProvider.getProvider(provider);
        log.debug("PROVIDER "+providerEnum);
        if (providerEnum == null) {
            url.append("404");
        } else {
            //url.append("signin/provider/register");
            if (SocialProvider.TWITTER.equals(providerEnum)) {
                auth1RequestProvider = new OAuth1RequestFlow(
                         EncuestamePlaceHolderConfigurer.getProperty("twitter.oauth.consumerKey"),
                         EncuestamePlaceHolderConfigurer.getProperty("twitter.oauth.consumerSecret"),
                         EncuestamePlaceHolderConfigurer.getProperty("twitter.oauth.request.token"),
                         EncuestamePlaceHolderConfigurer.getProperty("twitter.oauth.authorize"),
                         EncuestamePlaceHolderConfigurer.getProperty("twitter.oauth.access.token"),
                         SocialProvider.TWITTER);
                auth1RequestProvider.DEFAULT_CALLBACK_PATH = "/signin/register/";
                url.append(auth1RequestProvider.buildOAuth1AuthorizeUrl(scope, request, httpRequest));
            } else if (SocialProvider.GOOGLE.equals(providerEnum)) {
               OAuth2Parameters auth2Parameters = new OAuth2Parameters(
                        EncuestamePlaceHolderConfigurer.getProperty("google.register.client.id"),
                        EncuestamePlaceHolderConfigurer.getProperty("google.register.client.secret"),
                        EncuestamePlaceHolderConfigurer.getProperty("google.accesToken"),
                        EncuestamePlaceHolderConfigurer.getProperty("google.authorizeURl"),
                        SocialProvider.GOOGLE,
                        EncuestamePlaceHolderConfigurer.getProperty("google.register.client.id"));
                auth2RequestProvider  =  new OAuth2RequestFlow(auth2Parameters);
                auth2RequestProvider.DEFAULT_CALLBACK_PATH = "/signin/register/";
                url.append(auth2RequestProvider.buildOAuth2AuthorizeUrl(
                        "https://www.googleapis.com/auth/buzz", httpRequest, false));
            } else if (SocialProvider.FACEBOOK.equals(providerEnum)) {

            } else {

            }
        }
        log.debug("PROVIDER SIGNIN url"+url);
        return url.toString();
    }

    @RequestMapping(value = "/signin/register/{provider}", method = RequestMethod.GET, params = "oauth_token")
    public String oauth1Callback(
            @RequestParam("oauth_token") String token,
            @PathVariable String provider,
            final ModelMap model,
            @RequestParam(value = "oauth_verifier", required = false) String verifier,
            WebRequest request,
            final UserAccount account) throws Exception {
        log.debug("BACK SIGNIN PROVIDER:{ "+provider);
        final OAuth1Token accessToken = auth1RequestProvider.getAccessToken(verifier, request);
        log.debug("OAUTH 1 ACCESS TOKEN:{ " + accessToken.toString());
        //this.checkOAuth1SocialAccount(SocialProvider.TWITTER, accessToken);
        //this.redirect+"#provider="+SocialProvider.TWITTER.toString().toLowerCase()+"&refresh=true&successful=true";
        //request.removeAttribute(OAuthUtils.OAUTH_TOKEN_GET, WebRequest.SCOPE_REQUEST);

        model.put("jota", accessToken);
        return "signin/provider/register";
    }

    /**
    *
    * @param code
    * @param httpRequest
    * @param request
    * @return
    * @throws Exception
    */
   @RequestMapping(value="/signin/register/{provider}", method=RequestMethod.GET, params="code")
   public String oauth2Callback(
           @RequestParam("code") String code,
           final ModelMap model,
           HttpServletRequest httpRequest,
       WebRequest request) throws Exception {
           final AccessGrant accessGrant = auth2RequestProvider.getAccessGrant(code, httpRequest);
           log.debug(accessGrant.getAccessToken());
           log.debug(accessGrant.getRefreshToken());
           final BuzzAPIOperations apiOperations = new BuzzAPITemplate(accessGrant.getAccessToken(),
                 EncuestamePlaceHolderConfigurer.getProperty("google.api.key"));
           final SocialUserProfile d = apiOperations.getProfile();
           log.debug("Social "+d);
           final SignUpBean singUpBean = new SignUpBean();
           singUpBean.setEmail(d.getEmail());
           singUpBean.setUsername(d.getScreenName());
           //singUpBean.set
           final UserAccountBean bean = getSecurityService().singupUser(singUpBean);
           model.put("user", bean);
           //checkOAuth2SocialAccount(SocialProvider.GOOGLE, accessGrant);
           ///return this.redirect+"#provider="+SocialProvider.GOOGLE.toString().toLowerCase()+"&refresh=true&successful=true";
           return "signin/provider/register";
   }
}
