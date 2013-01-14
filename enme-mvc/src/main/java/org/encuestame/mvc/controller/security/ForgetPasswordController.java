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
package org.encuestame.mvc.controller.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.captcha.ReCaptchaResponse;
import org.encuestame.utils.security.ForgotPasswordBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Forgot Password Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 14, 2010 8:37:05 PM
 */
@Controller
@SessionAttributes(types = ForgotPasswordBean.class)
public class ForgetPasswordController extends AbstractSecurityController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/forgot", method = RequestMethod.GET)
    public String addHandler(Model model) {
        log.info("/forgot");
        final ForgotPasswordBean forgot = new ForgotPasswordBean();
        final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
        forgot.setCaptcha(captcha);
        model.addAttribute(forgot);
        return "forgot";
    }

    /**
     * Process Submit.
     *
     * @param req
     * @param challenge
     * @param response
     * @param user
     * @param result
     * @param status
     * @return
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping(value = "/user/forgot", method = RequestMethod.POST)
    public String forgotSubmitForm(HttpServletRequest req,
            Model model,
            @RequestParam("recaptcha_challenge_field") String challenge,
            @RequestParam("recaptcha_response_field") String response,
            @ModelAttribute ForgotPasswordBean user, BindingResult result,
            SessionStatus status) throws EnMeNoResultsFoundException {
        log.info("recaptcha_challenge_field " + challenge);
        log.info("recaptcha_response_field " + response);
        log.info("result erros  " + result.getAllErrors().size());
        log.info("result erros  " + result.getErrorCount());
        final String email = user.getEmail() == null ? "" : user.getEmail();
        if (!email.isEmpty()) {
                log.debug("email " + email);
                final ReCaptchaResponse reCaptchaResponse = getReCaptcha().checkAnswer(
                        req.getRemoteAddr(), challenge, response);
                final ValidateOperations validation = new ValidateOperations(
                        getSecurityService());
                boolean _isValidEmailFormat = validation.validateEmail(email);
                log.info("EMAIL FORMAT NOT VALID --> " + _isValidEmailFormat);
                if (_isValidEmailFormat) {
                     final UserAccount userValidate = validation.checkifEmailExist(email);
                     if (userValidate == null) {
                         result.rejectValue("email", "secure.email.notvalid", new Object[] { user.getEmail() }, "");
                     }
                     log.info("reCaptchaResponse " + reCaptchaResponse.isValid());
                     //validate reCaptcha
                     validation.validateCaptcha(reCaptchaResponse, result);
                     if(reCaptchaResponse.getErrorMessage() != null) {
                         RequestSessionMap.getCurrent(req).put("resetError", Boolean.TRUE);
                         RequestSessionMap.getCurrent(req).put("resetErrorMessage", reCaptchaResponse.getErrorMessage());
                         log.fatal("reCaptcha Fatal Error: "+reCaptchaResponse.getErrorMessage());
                     }
                    log.info("result.hasErrors() " + result.hasErrors());
                    if (result.hasErrors()) {
                        return "forgot";
                    } else {
                        final String password = PasswordGenerator.getPassword(6);
                        try {
                            /*
                             * Stuffs to change;
                             * 1. user should be to change own password, not auto generate
                             * 2. instead redirect to sign in page, should be to success page.
                             */
                            getSecurityService().renewPassword(
                                            ConvertDomainBean
                                                    .convertBasicSecondaryUserToUserBean(userValidate),
                                            password);
                        } catch (EnMeExpcetion e) {
                            log.error("Error Renewd password " + e.getMessage());
                            return "forgot";
                        }
                        status.setComplete();
                        log.info("password generated: " + password);
                        final ForgotPasswordBean forgot = new ForgotPasswordBean();
                        model.addAttribute("forgotPasswordBean", forgot);
                        return "/user/checkyouremail";
                    }
                } else {
                    log.info("EMAIL FORMAT NOT VALID");
                    result.rejectValue("email", "secure.email.notvalid", new Object[] { user.getEmail() }, "");
                    return "forgot";
                }
        } else {
            result.rejectValue("email", "secure.email.emtpy", null, "");
            return "forgot";
        }
    }
}
