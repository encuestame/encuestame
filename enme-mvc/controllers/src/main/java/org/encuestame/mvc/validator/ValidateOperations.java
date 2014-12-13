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
package org.encuestame.mvc.validator;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.ValidationUtils;
import org.encuestame.utils.captcha.ReCaptchaResponse;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Controller Validation.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 13, 2010 7:48:27 PM
 */
public class ValidateOperations {

    /**
     * Email Pattern.
     */
    private static final Pattern emailPattern = Pattern.compile(ValidationUtils.EMAIL_REGEXP, Pattern.CASE_INSENSITIVE);

    /**
     * Minimum username length.
     */
    public static final Integer MIN_USERNAME_LENGTH = 3;

    /**
     *
     */
    public static final Integer LENGTH_RANDOM_VALUE = 5;

    /**
     * Minimum email length.
     */
    public static final Integer MIN_EMAIL_LENGTH = 5;

    /**
     *
     */
    private HashMap<String, String> messages = new HashMap<String, String>();

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     */
    private UserAccount userAccount;

    /**
     *
     * @param securityService
     */
    public ValidateOperations(final SecurityOperations securityService) {
        this.securityService = securityService;
    }

    /**
     *
     * @param securityService
     * @param currentUser
     */
    public ValidateOperations(final SecurityOperations securityService, final UserAccount currentUser) {
        this.securityService = securityService;
        this.userAccount = currentUser;
    }

    /**
     * {@link SecurityOperations}
     */
    private SecurityOperations securityService;

    /**
     *
     * @param username
     * @return
     */
    public Boolean validateUsername(final String username, final UserAccount userLogged) {
        final UserAccount user = getUser(username);
        return this.validateUsername(username, user, userLogged);
    }

    /**
     * Validate a user on signup process
     * @param username the username
     * @param password password
     * @param email email
     * @return
     */
    public Boolean validateSignUpForm(
            final String username,
            final String email,
            final String password) {
        log.debug("validate validateSignUpForm process");
        log.debug("username " + username);
        final Boolean validUsername = this.validateUsername(username, null);
        log.debug("is valid ? username " + validUsername);
        final Boolean validEmail = this.validateUserEmail(email, null);
        log.debug("email " + email);
        log.debug("is valid ? email " + email);
        //TODO :: validate password
        return (validUsername && validEmail) ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Validate Username.
     * @param username username
     * @param user
     * @param userLogged
     * @return
     */
    public Boolean validateUsername(final String username, final UserAccount user, final UserAccount userLogged){
        log.debug("validating username... ");
        boolean valid = false;
        if (username == null) {
            log.debug("username is null");
            valid = false;
        } else if(username.length() >= MIN_USERNAME_LENGTH) {
            log.debug("fect user by username: "+user);
            if (user == null) {
                log.debug("username is valid..");
                getMessages().put("username", "username is available");
                valid = true;
            } else if(userLogged != null && userLogged.getUsername().equals(username)){
                log.debug("it's your current username");
                getMessages().put("username", "it's your current username");
                valid = true;
            } else if (username.equals(user.getUsername())) {
                log.debug("username already exist");
                getMessages().put("username", "username already exist");
            } else {
                log.debug("username not valid");
                getMessages().put("username", "username not valid");
            }
        } else {
            log.debug("username is not valid");
            getMessages().put("username", "username not valid");
        }
        return valid;
    }

    /**
     * Find a user in the database.
     * @param username user to find
     * @return {@link UserAccount} if exist, if not, return null
     */
    private UserAccount getUser(final String username) {
        final UserAccount user = getSecurityService().findUserByUserName(username);
        return user;
    }


    /**
     * Validate user email.
     * @param email email as string to validate
     * @param userLogged
     * @param username
     * @return
     */
    public Boolean validateUserEmail(final String email, final UserAccount userLogged) {
        log.debug("validating email... ->"+email);
        if (this.validateEmail(email)) {
            // try to find a user with the same email
            final UserAccount user = getSecurityService().findUserAccountByEmail(email);
            return this.validateUserEmail(email, user, userLogged);
        } else {
             getMessages().put("email", "email wrong format");
            return false;
        }
    }

    /**
     *
     * @param email
     * @return
     */
    public UserAccount checkifEmailExist(String email) {
        log.debug("checkifEmailExist email... -> " + email);        //
        email = email == null ? "" : email;
        final UserAccount user = getSecurityService().findUserAccountByEmail(email);
        return user;
    }

    /**
     * Validate user email.
     * @param email the email to validate
     * @param user the possible account to validate, if is null the user not exist
     * @param userLogged user try to create new validate the email
     * @param username
     * @return
     */
    public Boolean validateUserEmail(final String email, final UserAccount user, final UserAccount userLogged) {
        log.debug("validating email... ->"+email);
        log.debug("validating email UserAccount... ->"+user);
        boolean valid = false;
        // user null, email available
        if (user == null) {
            log.debug("email is valid.. 1 ");
            getMessages().put("email", "email is available");
            valid = true;
        // if the user logged try to validate own email
        } else if (userLogged != null && userLogged.getUserEmail().equals(email)) {
            log.debug("email is valid.. 2 ");
            getMessages().put("email", "it's your email");
            valid = true;
         // email previous exist, return error
        } else if(email.equals(user.getUserEmail())) {
            log.debug("email not valid.. 3 ");
            getMessages().put("email", "email already exist");
        } else {
            // any other option is not valid
            log.debug("email not valid.. 4 ");
            getMessages().put("email", "email not valid");
        }
        log.debug("validateUserEmail valid ->>>>" + valid);
        return valid;
    }

    /**
     *
     * @param email
     * @return
     */
    public Boolean validateEmail(final String email) {
        log.debug("email validateEmail "+email);
        Boolean valid = false;
        if (emailPattern.matcher(email).matches() && StringUtils.hasLength(email)) {
            log.warn("email valid");
            getMessages().put("email", "email good format");
            valid = true;
        } else {
            log.debug("email format not valid");
            getMessages().put("email", "email wrong valid");
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
    public SecurityOperations getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(final SecurityOperations securityService) {
        this.securityService = securityService;
    }

    /**
     * @return the messages
     */
    public HashMap<String, String> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(HashMap<String, String> messages) {
        this.messages = messages;
    }
}
