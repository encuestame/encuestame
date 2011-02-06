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
package org.encuestame.mvc.validator;

import java.util.regex.Pattern;

import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.ISecurityService;
import org.encuestame.core.util.ValidationUtils;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Controller Validation.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 13, 2010 7:48:27 PM
 * @version $Id:$
 */
public class ValidateOperations {

    /**
     * Email Pattern.
     */
    private static final Pattern emailPattern = Pattern.compile(ValidationUtils.EMAIL_REGEXP, Pattern.CASE_INSENSITIVE);

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param securityService
     */
    public ValidateOperations(final ISecurityService securityService) {
        this.securityService = securityService;
    }

    private ISecurityService securityService;

    /**
     * Validate Username.
     * @param username username
     * @return
     */
    public Boolean validateUsername(final String username){
        log.debug("validating username... ");
        Boolean valid = false;
        final UserAccount user = getSecurityService().findUserByUserName(username);
        if(user == null){
            log.debug("username is valid..");
            valid = true;
        } else if (username.equals(user.getUsername())){
            log.debug("username nothing to update");
            valid = true;
        }
        return valid;
    }

    /**
     *
     * @param username
     * @return
     */
    private UserAccount getUser(final String username){
        final UserAccount user = getSecurityService().findUserByUserName(username);
        return user;
    }

    /**
     * Validate user email.
     * @param username
     * @return
     */
    public Boolean validateUserEmail(final String email){
        log.debug("validating email... ->"+email);
        Boolean valid = false;
        final UserAccount user = getSecurityService().findUserAccountByEmail(email);
        if(user == null){
            log.debug("email is valid..");
            valid = true;
        }
        return valid;
    }

    /**
     * Validate User By Email.
     * @param email email
     * @return
     */
    public UserAccountBean validateUserByEmail(final String email){
        UserAccountBean unitUserBean = null;
        log.debug("validating email... ");
        if(this.validateEmail(email)) {
            log.debug("fetch by email...");
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
        log.debug("email validateEmail "+email);
        Boolean valid = false;
        if(emailPattern.matcher(email).matches() && StringUtils.hasLength(email)) {
            log.warn("email not valid");
            valid = true;
        } else {
            log.debug("email format valid");
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
        } else {
            log.info("Captcha VALID");
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
