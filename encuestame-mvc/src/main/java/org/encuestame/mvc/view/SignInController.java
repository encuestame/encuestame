
package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.mvc.controller.social.AbstractSocialController;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    /**
     * Sign in by {@link SocialProvider} account.
     * @param provider
     * @return
     */
    @RequestMapping(value="/signin/{provider}", method = RequestMethod.POST)
    public String signinFacebookGet(
        @PathVariable String provider){
        final StringBuilder url = new StringBuilder();
        final SocialProvider providerEnum = SocialProvider.getProvider(provider);
        if (providerEnum == null) {
            url.append("404");
        } else {
            url.append("signin/provider/register");
            if (SocialProvider.TWITTER.equals(providerEnum)) {

            } else if (SocialProvider.GOOGLE.equals(providerEnum)) {

            } else if (SocialProvider.FACEBOOK.equals(providerEnum)) {

            } else if (SocialProvider.IDENTICA.equals(providerEnum)) {

            } else if (SocialProvider.LINKEDIN.equals(providerEnum)) {

            }
        }
        return url.toString();
    }
}
