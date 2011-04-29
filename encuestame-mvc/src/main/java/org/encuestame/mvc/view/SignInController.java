
package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.business.service.social.OAuth1RequestFlow;
import org.encuestame.business.service.social.OAuth2RequestFlow;
import org.encuestame.business.service.social.signin.FacebookSignInSocialSupport;
import org.encuestame.business.service.social.signin.GoogleSignInSocialService;
import org.encuestame.core.social.oauth.OAuth2Parameters;
import org.encuestame.mvc.controller.social.AbstractSocialController;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.AccessGrant;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IAccountDao accountDao;

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
            if (SocialProvider.GOOGLE.equals(providerEnum)) {
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
           @PathVariable String provider,
           final ModelMap model,
           HttpServletRequest httpRequest,
           WebRequest request) throws Exception {
           //get AccesGrant.
           final AccessGrant accessGrant = auth2RequestProvider.getAccessGrant(code, httpRequest);
           log.debug(accessGrant.getAccessToken());
           log.debug(accessGrant.getRefreshToken());
           if (SocialProvider.getProvider(provider).equals(SocialProvider.GOOGLE)) {
               getSecurityService().connectSignInAccount(new GoogleSignInSocialService(
                       getAccountDao(), accessGrant), accessGrant);
           } else if(SocialProvider.getProvider(provider).equals(SocialProvider.FACEBOOK)) {
               getSecurityService().connectSignInAccount(new FacebookSignInSocialSupport(
                       getAccountDao(), accessGrant), accessGrant);
           }
           return "signin/provider/register";
   }

    /**
     * @return the accountDao
     */
    public IAccountDao getAccountDao() {
        return accountDao;
    }

    /**
     * @param accountDao the accountDao to set
     */
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }
}