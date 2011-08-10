
package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.mvc.controller.security.ForgetPasswordController;
import org.encuestame.mvc.controller.social.AbstractSocialController;
import org.encuestame.oauth2.support.OAuth2Parameters;
import org.encuestame.oauth2.support.OAuth2RequestFlow;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.social.connect.FacebookSignInSocialSupport;
import org.encuestame.social.connect.GoogleBuzzSignInSocialService;
import org.encuestame.utils.oauth.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
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
     * Post register redirect.
     */
    private final String POST_REGISTER_REDIRECT = "/user/signin/register/";


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
        request.setAttribute("social", isSocialSignInUpEnabled());
        log.debug("login");
        return "signin";
    }

    /**
     * Forgot Password Controller.
     * @param model model
     * @return template
     * check {@link ForgetPasswordController}.
     */
    //@RequestMapping(value = "/user/forgot", method = RequestMethod.GET)
    public String forgotPasswordController(final ModelMap model) {
        log.debug("forgot password");
        return "forgot";
    }

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
            if (SocialProvider.GOOGLE_BUZZ.equals(providerEnum)) {
               OAuth2Parameters auth2Parameters = new OAuth2Parameters(
                        EnMePlaceHolderConfigurer.getProperty("google.register.client.id"),
                        EnMePlaceHolderConfigurer.getProperty("google.register.client.secret"),
                        EnMePlaceHolderConfigurer.getProperty("google.accesToken"),
                        EnMePlaceHolderConfigurer.getProperty("google.authorizeURl"),
                        SocialProvider.GOOGLE_BUZZ,
                        EnMePlaceHolderConfigurer.getProperty("google.register.client.id"));
                auth2RequestProvider  =  new OAuth2RequestFlow(auth2Parameters);
                auth2RequestProvider.DEFAULT_CALLBACK_PATH = POST_REGISTER_REDIRECT;
                url.append(auth2RequestProvider.buildOAuth2AuthorizeUrl(
                        EnMePlaceHolderConfigurer.getProperty("google.buzz.scope"), httpRequest, false));
                url.append("&state=");
                url.append(providerEnum.toString());
            } else if (SocialProvider.FACEBOOK.equals(providerEnum)) {
                OAuth2Parameters auth2Parameters = new OAuth2Parameters(
                        EnMePlaceHolderConfigurer.getProperty("facebook.api.key"),
                        EnMePlaceHolderConfigurer.getProperty("facebook.api.secret"),
                        EnMePlaceHolderConfigurer.getProperty("facebook.oauth.accesToken"),
                        EnMePlaceHolderConfigurer.getProperty("facebook.oauth.authorize"),
                        SocialProvider.FACEBOOK,
                        EnMePlaceHolderConfigurer.getProperty("facebook.api.id"));
                auth2RequestProvider  =  new OAuth2RequestFlow(auth2Parameters);
                auth2RequestProvider.DEFAULT_CALLBACK_PATH = POST_REGISTER_REDIRECT;
                url.append(auth2RequestProvider.buildOAuth2AuthorizeUrl(
                        SocialUtils.FACEBOOK_SCOPE, httpRequest, providerEnum == SocialProvider.FACEBOOK ? true : false));
            } else {
                url.append("404");
            }
        }
        log.debug("PROVIDER SIGNIN url: -->"+url);
        return url.toString();
    }

    /**
     *
     * @param code
     * @param provider
     * @param model
     * @param httpRequest
     * @param request
     * @return
     */
    @RequestMapping(value="/user/signin/register/{provider}", method=RequestMethod.GET, params="error")
    public String oauth2ErrorCallback(
            @RequestParam("error") String error,
            @PathVariable String provider,
            final ModelMap model,
            HttpServletRequest httpRequest,
            WebRequest request){
            log.fatal("OAuth Error:{"+error);
            RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
            return "redirect:/user/signin";
    }

    /**
    *
    * @param code
    * @param httpRequest
    * @param request
    * @return
    */
   @RequestMapping(value="/user/signin/register/{provider}", method=RequestMethod.GET, params="code")
   public String oauth2Callback(
           @RequestParam(value = "code", required = true) String code,
           @RequestParam(value = "state", required = false) String state,
           @PathVariable String provider,
           final ModelMap model,
           HttpServletRequest httpRequest,
           WebRequest request) {
        try {
                  log.debug("httpRequest "+httpRequest.getRequestURI());
                  log.debug("httpRequest "+httpRequest.getQueryString());
               final AccessGrant accessGrant = auth2RequestProvider.getAccessGrant(code, httpRequest);
               if (log.isDebugEnabled()) {
                   log.debug(accessGrant.getAccessToken());
                   log.debug(accessGrant.getRefreshToken());
               }
            String friendsUrl = "redirect:/user/signin/friends";
            final SocialProvider providerBack;
            if ("google".equals(provider) && state != null) {
                providerBack = SocialProvider.getProvider(state);
            } else {
                providerBack = SocialProvider.getProvider(provider);
            }
            if (providerBack == null) {
                throw new EnMeOAuthSecurityException("provider ["+provider+"] not valid");
            }
            if (providerBack.equals(SocialProvider.GOOGLE_BUZZ)) {
                friendsUrl = getConnectOperations().connectSignInAccount(
                        new GoogleBuzzSignInSocialService(accessGrant,
                                getConnectOperations()));
            } else if (SocialProvider.getProvider(provider).equals(
                    SocialProvider.FACEBOOK)) {
                friendsUrl = getConnectOperations().connectSignInAccount(
                        new FacebookSignInSocialSupport(accessGrant,
                                getConnectOperations()));
            }
            if (log.isDebugEnabled()) {
                log.debug("oauth2Callback sign up with social account "
                        + friendsUrl);
            }
               return friendsUrl;
            } catch (EnMeExistPreviousConnectionException e) {
                 log.fatal("OAuth EnMeExistPreviousConnectionException:{"+e);
                 Assert.notNull(httpRequest);
                 RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
                 return "redirect:/user/signin";
            } catch (Exception e) {
                 log.fatal("OAuth Exception:{"+e.getMessage());
                 e.printStackTrace();
                 RequestSessionMap.setErrorMessage(getMessage("errorOauth", httpRequest, null));
                 return "redirect:/user/signin";
            }
   }

   /**
    *
    * @param model
    * @param httpRequest
    * @param request
    * @return
    */
   @RequestMapping(value="/user/signin/friends", method=RequestMethod.GET)
   public String oauth2Callback(
           final ModelMap model,
           HttpServletRequest httpRequest,
           WebRequest request) {
       return "user/friends";
   }
}
