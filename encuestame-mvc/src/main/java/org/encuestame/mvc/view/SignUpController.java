/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.mvc.controller.security.AbstractSecurityController;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Sign Up Form.
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 */
@Controller
@SessionAttributes(types = SignUpBean.class)
public class SignUpController extends AbstractSecurityController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    private final Integer PASSWORD_LENGHT = 8;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/signup", method = RequestMethod.GET)
    public String addHandler(Model model) {
        final Boolean privateHome = EnMePlaceHolderConfigurer
        .getBooleanProperty("application.signup.enabled");
        if (!privateHome) {
            log.debug("signup is disabled");
            return "redirect:/signin";
        } else {
            final SignUpBean user = new SignUpBean();
            final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
            log.debug(captcha);
            user.setCaptcha(captcha);
            log.info("username "+user);
            model.addAttribute("user",user);
            return "signup";
        }
    }

    /**
     * Sign up process submit.
     * @param req
     * @param challenge
     * @param response
     * @param user
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/user/signup/create", method = RequestMethod.POST)
    public String processSubmit(final ModelMap model,
            @RequestParam("realName") String realName,
            @RequestParam("password") String password,
            @RequestParam("username") String usernameForm,
            @RequestParam("email") String emailForm,
            final HttpServletRequest req) {
        final SignUpBean user = new SignUpBean();
        user.setEmail(filterValue(emailForm));
        user.setPassword(password);
        user.setFullName(filterValue(realName));
        user.setUsername(filterValue(usernameForm));
        final ValidateOperations validation = new ValidateOperations(
                getSecurityService());
        if (validation.validateUserEmail(user.getEmail(), null) != null) {
            log.warn("Email NOT VALID");
            // result.rejectValue("email", "secure.email.notvalid");
            // //secure.email.notvalid
        }
        if (!validation.validateUsername(user.getUsername(), null)) {
            log.warn("Username NOT VALID");
            // result.rejectValue("username", "secure.user.notvalid");
            // //secure.user.notvalid
        }
        if (user.getPassword() == null) {
            password = PasswordGenerator.getPassword(PASSWORD_LENGHT);
            user.setPassword(password);
        }
        getSecurityService().singupUser(user);
        return "redirect:/user/dashboard";
        // }
    }

    /**
     *
     * @param inviteCode
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/confirm/email/{inviteCode}", method = RequestMethod.GET)
    public String confirmAccountController(
            @PathVariable String inviteCode,
            final ModelMap model,
            HttpServletResponse response,
            HttpServletRequest request) {
            log.debug("Invitation Code----->" + inviteCode);
        UserAccountBean userAccountBean;
        try {
            userAccountBean = getSecurityService().getUserAccountbyCode(inviteCode);
        } catch (EnMeNoResultsFoundException e) {
            log.error(e.getMessage());
            return "signin";
        }
        if (userAccountBean == null) {
            return "signin";
        } else {
             model.put("userAccount", userAccountBean);
        }
        log.debug("confirmation Account");
        return "user/confirm/";
    }
}
