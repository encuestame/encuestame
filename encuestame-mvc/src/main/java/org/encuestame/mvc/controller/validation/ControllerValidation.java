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
package org.encuestame.mvc.controller.validation;

import java.util.regex.Pattern;

import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.domain.security.SecUserSecondary;
import org.encuestame.core.service.ISecurityService;
import org.encuestame.core.service.util.ValidationUtils;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Controller Validation.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 13, 2010 7:48:27 PM
 * @version $Id:$
 */
public class ControllerValidation {

    private static final Pattern emailPattern = Pattern.compile(ValidationUtils.EMAIL_REGEXP, Pattern.CASE_INSENSITIVE);

    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param securityService
     */
    public ControllerValidation(final ISecurityService securityService) {
        this.securityService = securityService;
    }

    private ISecurityService securityService;

    /**
     * Validate Username.
     * @param username username
     * @return
     */
    public Boolean validateUsername(final String username){
        Boolean valid = false;
        final SecUserSecondary user = getSecurityService().findUserByUserName(username);
        if(user == null){
            valid = true;
        }
        return valid;
    }

    /**
     * Validate User By Email.
     * @param email email
     * @return
     */
    public UnitUserBean validateUserByEmail(final String email){
        UnitUserBean unitUserBean = null;
        if(this.validateEmail(email)) {
            unitUserBean = getSecurityService().findUserByEmail(email);
        }
        return unitUserBean;
    }

    /**
     *
     * @param email
     * @return
     */
    public Boolean validateEmail(final String email){
        Boolean valid = false;
        if(emailPattern.matcher(email).matches() && StringUtils.hasLength(email)) {
            log.warn("Captcha NOT VALID");
            valid = !valid;
        }
        return valid;
    }

    /**
     *
     */
    public void validateCaptcha(final ReCaptchaResponse reCaptchaResponse, final Errors errors){
        if(!reCaptchaResponse.isValid()){
            log.warn("Captcha NOT VALID");
            errors.rejectValue("captcha", "secure.captcha.invalid");
        }
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
    public void setSecurityService(final ISecurityService securityService) {
        this.securityService = securityService;
    }
}
