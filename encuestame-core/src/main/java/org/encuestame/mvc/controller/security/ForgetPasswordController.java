/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaResponse;

import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.mvc.controller.validation.ControllerValidation;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.UnitForgotPassword;
import org.encuestame.utils.web.UnitUserBean;
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
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 14, 2010 8:37:05 PM
 * @version Id:
 */
@Controller
@SessionAttributes(types = UnitForgotPassword.class)
public class ForgetPasswordController extends AbstractSecurityController{

        @RequestMapping(value = "/forgot" , method = RequestMethod.GET)
        public String addHandler(Model model) {
            log.info("/forgot");
            final UnitForgotPassword forgot = new UnitForgotPassword();
            final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
            forgot.setCaptcha(captcha);
            log.info("username "+forgot.getCaptcha());
            model.addAttribute(forgot);
            return "forgot";
        }

        /**
         * Process Submit.
         * @param req
         * @param challenge
         * @param response
         * @param user
         * @param result
         * @param status
         * @return
         */
        @RequestMapping(method = RequestMethod.POST)
        public String processSubmit(
            HttpServletRequest req,
            @RequestParam("recaptcha_challenge_field") String challenge,
            @RequestParam("recaptcha_response_field") String response,
            @ModelAttribute UnitForgotPassword user, BindingResult result, SessionStatus status) {
                 log.info("recaptcha_challenge_field "+challenge);
                 log.info("recaptcha_response_field "+response);
                 final String email = user.getEmail();

                 log.info("password "+email);
                 final ReCaptchaResponse reCaptchaResponse = getReCaptcha().checkAnswer(req.getRemoteAddr(), challenge, response);
                 final ControllerValidation validation = new ControllerValidation(getSecurityService());

                 //TODO: validate password.

                log.info("reCaptchaResponse "+reCaptchaResponse.getErrorMessage());
                log.info("reCaptchaResponse "+reCaptchaResponse.isValid());
                log.info("result.hasErrors() "+result.hasErrors());
                if (result.hasErrors()) {
                    return "forgot";
                }
                else {
                    final String password = PasswordGenerator.getPassword(6);
                    //TODO: send new password
                    //create
                    //final UnitUserBean unitUserBean = getSecurityService().singupUser(user);
                    status.setComplete();
                    log.info("password generated "+password);
                    return "redirect:/singup";
                }
        }

}
