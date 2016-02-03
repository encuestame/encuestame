/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.mvc.controller.social.AbstractSocialController;
import org.encuestame.oauth2.support.OAuth2Parameters;
import org.encuestame.oauth2.support.OAuth2RequestFlow;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.social.api.connect.FacebookSignInSocialSupport;
import org.encuestame.social.api.connect.GoogleBuzzSignInSocialService;
import org.encuestame.utils.EnumerationUtils;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.social.SocialProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sign In controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */
@Controller
public class SignInController extends AbstractSocialController{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

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
        setCss(model, "user");
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
//    public String forgotPasswordController(final ModelMap model) {
//        log.debug("forgot password");
//        return "forgot";
//    }

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
        final SocialProvider providerEnum = EnumerationUtils.getEnumFromString(SocialProvider.class, provider);
        //System.out.println("PROVIDER "+providerEnum);
        if (providerEnum == null) {
            url.append("404");
        } else {
            if (SocialProvider.GOOGLE_PLUS.equals(providerEnum)) {
               OAuth2Parameters auth2Parameters = new OAuth2Parameters(
                        EnMePlaceHolderConfigurer.getProperty("google.register.client.id"),
                        EnMePlaceHolderConfigurer.getProperty("google.register.client.secret"),
                        EnMePlaceHolderConfigurer.getProperty("google.accesToken"),
                        EnMePlaceHolderConfigurer.getProperty("google.authorizeURl"),
                        SocialProvider.GOOGLE_PLUS,
                        EnMePlaceHolderConfigurer.getProperty("google.register.client.id"));
                auth2RequestProvider  =  new OAuth2RequestFlow(auth2Parameters);
                auth2RequestProvider.DEFAULT_CALLBACK_PATH = POST_REGISTER_REDIRECT;
                url.append(auth2RequestProvider.buildOAuth2AuthorizeUrl(
                        EnMePlaceHolderConfigurer.getProperty("google.plus.scope"), httpRequest, false));
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
                providerBack = EnumerationUtils.getEnumFromString(SocialProvider.class, state);
            } else {
                providerBack = EnumerationUtils.getEnumFromString(SocialProvider.class, provider);
            }
            if (providerBack == null) {
                throw new EnMeOAuthSecurityException("provider ["+provider+"] not valid");
            }
            if (providerBack.equals(SocialProvider.GOOGLE_BUZZ)) {
                friendsUrl = getConnectOperations().connectSignInAccount(
                        new GoogleBuzzSignInSocialService(accessGrant,
                                getConnectOperations()));
            } else if (EnumerationUtils.getEnumFromString(SocialProvider.class, provider).equals(
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
                  //e.printStackTrace();
                 log.fatal("OAuth Exception:{"+e.getMessage());
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
       setCss(model, "user");
       return "user/friends";
   }
}
