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

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.ISecurityService;
import org.encuestame.mvc.controller.BaseController;
import org.encuestame.utils.security.SignUpBean;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RegisterFormController extends BaseController {

     private Log log = LogFactory.getLog(this.getClass());

    private ReCaptcha reCaptcha;

    private ISecurityService securityService;

    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    public String addHandler(Model model) {
        log.info("/register");
        final SignUpBean user = new SignUpBean();
        this.reCaptcha = ReCaptchaFactory.newReCaptcha("6LdyFwUAAAAAAP6p1IeqUM7uMKUYyPazw-haEAUU", "6LdyFwUAAAAAAGB3BsjX-j5EgYzULsR3ftiUvwUd", false);
        String captcha = this.reCaptcha.createRecaptchaHtml(null, null);
        log.info("reCaptcha "+reCaptcha.createRecaptchaHtml(null, null));
        user.setCaptcha(captcha);
        model.addAttribute(user);
        return "registerJSP";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
        HttpServletRequest req,
        @RequestParam("recaptcha_challenge_field") String challenge,
        @RequestParam("recaptcha_response_field") String response,
        @ModelAttribute SignUpBean user, BindingResult result, SessionStatus status) {
             ReCaptchaResponse reCaptchaResponse
                 = reCaptcha.checkAnswer(req.getRemoteAddr(), challenge, response);
            //UserValidator userValidator = new UserValidator(userDao);
             log.info("reCaptchaResponse "+reCaptchaResponse.getErrorMessage());
             log.info("reCaptchaResponse "+reCaptchaResponse.isValid());

            //userValidator.validate(user, result, reCaptchaResponse);
            log.info("result.hasErrors() "+result.hasErrors());
            if (result.hasErrors()) {
                return "registerJSP";
            }
            else {
                final String username = user.getUsername();
                final String password = user.getPassword();
                //user = this.securityService.
                //status.setComplete();
                //log.info("New User with userId: " + user.getId() + " added at " + new Date());
                //authenticate(req, username, password);
                return "redirect:/index.html";
            }
    }

    /**
     * @return the reCaptcha
     */
    public ReCaptcha getReCaptcha() {
        return reCaptcha;
    }

    /**
     * @param reCaptcha
     *            the reCaptcha to set
     */
    @Autowired
    public void setReCaptcha(final ReCaptcha reCaptcha) {
        this.reCaptcha = reCaptcha;
    }

    /**
     * @return the securityService
     */
    public ISecurityService getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    @Autowired
    public void setSecurityService(final ISecurityService securityService) {
        this.securityService = securityService;
    }

}
