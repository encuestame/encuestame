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
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.core.service.SecurityOperations;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sign Up Form.
 * @author Picado, Juan juanATencuestame.org
 * @since 26/04/2009
 */
@Controller
@SessionAttributes(types = SignUpBean.class)
public class SignUpController extends AbstractViewController {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

            /**
             *
             */
    private final Integer PASSWORD_LENGHT = 8;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/signup", method = RequestMethod.GET)
    public String addHandler(
            final ModelMap model,
            HttpServletResponse response,
            final HttpServletRequest request) {
        final Boolean privateHome = EnMePlaceHolderConfigurer.getBooleanProperty("application.signup.enabled");
        setCss(model, "user");
        addi18nProperty(model, "m_011", request, response);
        addi18nProperty(model, "e_013", request, response);
        addi18nProperty(model, "m_012", request, response);
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
     * @param response
     * @return
     */
    @RequestMapping(value = "/user/signup/create", method = RequestMethod.POST)
    public String processSubmit(final ModelMap model,
                                final HttpServletRequest request,
                                HttpServletResponse response,
            @RequestParam(value = "realName", required = true, defaultValue = "") String realName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "username", required = true) String usernameForm,
            @RequestParam(value = "email", required = true) String emailForm,
            final HttpServletRequest req) {
        setCss(model, "user");
        final SignUpBean user = new SignUpBean();
        String finalPath = "/user/created";
        user.setEmail(filterValue(emailForm));
        user.setPassword(password);
        user.setFullName(filterValue(realName));
        user.setUsername(filterValue(usernameForm));
        final SecurityOperations _service = getSecurityService();
        final ValidateOperations validation = new ValidateOperations(_service);
        if (validation.validateSignUpForm(usernameForm, emailForm, password)) {
            log.debug(" the signup process successfull");
            try {
                _service.signupUser(user, false);
            } catch (Exception e) {
                 RequestSessionMap.getCurrent(req).put("signupError", Boolean.TRUE);
                 finalPath = "redirect:/user/signup";
                 log.error("error on signup : " + e.getMessage());
                 //e.printStackTrace();
            }
        } else {
            log.warn(" the signup not valid");
            RequestSessionMap.getCurrent(req).put("signupError", Boolean.TRUE);
            finalPath = "redirect:/user/signup";
        }
        //i18n
        addi18nProperty(model, "m_012", request, response);
        addi18nProperty(model, "m_011", request, response);

        return finalPath;
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
        UserAccountBean userAccountBean;
        setCss(model, "user");
        try {
            userAccountBean = getSecurityService().getUserAccountbyCode(inviteCode);
        } catch (EnMeNoResultsFoundException e) {
            log.error(e.getMessage());
            return "redirect:/user/signin";
        }
        if (userAccountBean == null) {
            return "redirect:/user/signin";
        } else {
             model.put("userAccount", userAccountBean);
        }
        log.debug("confirmation Account");
        return "user/confirm/";
    }


    /**
     *
     * @param model
     * @param response
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/confirm/email/refresh/code", method = RequestMethod.GET)
    public String refreshCodeController(
            final ModelMap model,
            HttpServletResponse response,
            HttpServletRequest request) {
        try {
            setCss(model, "user");
            getSecurityService().refreshInviteCode();
        } catch (EnMeNoResultsFoundException e) {
            log.error(e.getMessage());
            return "user/dashboard";
        }
        return "redirect:/user/dashboard";
    }
}
