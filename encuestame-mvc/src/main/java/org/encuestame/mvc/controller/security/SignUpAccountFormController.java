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
package org.encuestame.mvc.controller.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.security.SignUpBean;
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
 * Register Form.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * @version $Id: ServiceManager.java 469 2010-04-01 00:09:12Z juanpicado $
 */
@Controller
@SessionAttributes(types = SignUpBean.class)
public class SignUpAccountFormController extends AbstractSecurityController {

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
    @RequestMapping(method = RequestMethod.GET)
    public String addHandler(Model model) {
        log.info("/register");
        final Boolean privateHome = EnMePlaceHolderConfigurer
        .getBooleanProperty("application.signup.enabled");
        if (privateHome) {
            log.debug("signup is disabled");
            return "redirect:/signin";
        } else {
            final SignUpBean user = new SignUpBean();
            final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
            user.setCaptcha(captcha);
            log.info("username "+user.getCaptcha());
            model.addAttribute(user);
            return "user/signup";
        }
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
    //@RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
        HttpServletRequest req,
        @RequestParam("recaptcha_challenge_field") String challenge,
        @RequestParam("recaptcha_response_field") String response,
        @ModelAttribute SignUpBean user, BindingResult result, SessionStatus status) {
             log.info("recaptcha_challenge_field "+challenge);
             log.info("recaptcha_response_field "+response);
             final String email = filterValue(user.getEmail());
             final String username = filterValue(user.getUsername());
             log.info("username "+username);
             log.info("password "+email);
             final ReCaptchaResponse reCaptchaResponse = getReCaptcha().checkAnswer(req.getRemoteAddr(), challenge, response);
             final ValidateOperations validation = new ValidateOperations(getSecurityService());

             if(validation.validateUserEmail(email, null) != null){
                   log.warn("Email NOT VALID");
                   result.rejectValue("email", "secure.email.notvalid"); //secure.email.notvalid
             }
             if(!validation.validateUsername(username,  null)){
                 log.warn("Username NOT VALID");
                  result.rejectValue("username", "secure.user.notvalid"); //secure.user.notvalid
             }
            //validate captcha
            validation.validateCaptcha(reCaptchaResponse, result);
            log.info("reCaptchaResponse "+reCaptchaResponse.getErrorMessage());
            log.info("reCaptchaResponse "+reCaptchaResponse.isValid());
            log.info("result.hasErrors() "+result.hasErrors());
            if (result.hasErrors()) {
                return "register";
            }
            else {
                final String password = PasswordGenerator.getPassword(PASSWORD_LENGHT);
                user.setPassword(password);
                //create
                final UserAccount unitUserBean = getSecurityService().singupUser(user);
                status.setComplete();
                log.info("password generated "+password);
                log.info("New User with userId: " + unitUserBean.getUid() + " added at " + new Date());
                //authenticate(req, username, password); //TODO: I don't know why we have Lazy here.
                return "redirect:/user/signin";
            }
    }
}
