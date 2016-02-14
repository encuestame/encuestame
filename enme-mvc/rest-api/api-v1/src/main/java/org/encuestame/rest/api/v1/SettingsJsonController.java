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
package org.encuestame.rest.api.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.encuestame.core.service.SecurityOperations;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.Profile;
import org.encuestame.utils.json.ProfileUserAccount;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Settings Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 29, 2011 11:37:56 AM
 * @version $Id:$
 */
@Controller
public class SettingsJsonController extends AbstractJsonControllerV1{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());


    /**
     *
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/settings/profile/my", method = RequestMethod.GET)
    public @ResponseBody ModelMap myAccountProfile(HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        ProfileUserAccount user;
        try {
            user = getProfileUserInfo();
            jsonResponse.put("account", user);
            setItemResponse(jsonResponse);
        } catch (EnMeNoResultsFoundException e) {
            //e.printStackTrace();
            setError(e, response);
        }
        return returnData();
    }

    /**
     * Upgrade profile settings.
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/settings/profile/{type}/update.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap upgradeProfile(HttpServletRequest request,
            @PathVariable String type,
            @RequestParam(value = "data", required = false) String data,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {

        log.debug("update profile type:" + type);
        log.debug("update profile data:" + data);
        try {
            final SecurityOperations security = getSecurityService();
            final ValidateOperations operations = new ValidateOperations(security);
            final HashMap<String, Object> listError = new HashMap<String, Object>();
            //filter data
            data = filterValue(data);
            type = type.toUpperCase();
            if (type.equals(Profile.EMAIL.toString())) {
                //TODO: review pattern email format validator.
                log.debug("update email");
                final UserAccount account = getSecurityService().getUserAccount(getUserPrincipalUsername());
                if (operations.validateUserEmail(data, account)) {
                    security.updateAccountProfile(Profile.EMAIL, data);
                    setSuccesResponse();
                } else {
                    listError.put(type, getMessage("e_005", request, null));
                }
            } else if(type.equals(Profile.USERNAME.toString())) {
                log.debug("update username");
                final UserAccount account = getSecurityService().getUserAccount(getUserPrincipalUsername());
                if (operations.validateUsername(data, account)) {
                    security.updateAccountProfile(Profile.USERNAME, data);
                    setSuccesResponse(getMessage("settings_config_profile_success", request, null));
                } else {
                    listError.put(type, getMessage("e_018", request, null));
                }
            } else if(type.equals(Profile.PICTURE.toString())) {
                 log.debug("update PICTURE");
                 security.updateAccountProfile(Profile.PICTURE, data);
                 setSuccesResponse(getMessage("settings_config_picture_success", request, null));
            }  else if(type.equals(Profile.WELCOME.toString())) {
                log.debug("update WELCOME");
                security.updateAccountProfile(Profile.WELCOME, data);
                setSuccesResponse(getMessage("settings_welcome", request, null));
            }  else if(type.equals(Profile.PAGE_INFO.toString())) {
                log.debug("update PAGE_INFO");
                security.updateAccountProfile(Profile.PAGE_INFO, data);
                setSuccesResponse(getMessage("settings_page_info", request, null));
            } else {
                setError(getMessage("e_023", request, null), response);
            }
            if (!listError.isEmpty()) {
                log.debug("list errors " + listError.size());
                setError(listError, response);
            }
        } catch (Exception e) {
            log.error(e);
            //e.printStackTrace();
            setError(getMessage("e_023", request, null), response);
            //throw new JsonGenerationException(e.getMessage());
        }
        return returnData();
    }

    /**
     * Upgrade profile settings.
     * @param request
     * @param email
     * @param username
     * @param completeName
     * @param language
     * @param bio
     * @param response
     * @param model
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/settings/profile/update.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap upgradePostProfile(HttpServletRequest request,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "completeName", required = false) String completeName,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "bio", required = false) String bio,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        try {
            final SecurityOperations security = getSecurityService();
            // get the user account of previous user logged.
            final UserAccount account = getUserAccount();
            final ValidateOperations operations = new ValidateOperations(security);
            final HashMap<String, Object> listError = new HashMap<String, Object>();
            //filter values.
            log.debug("email " +email);
            log.debug("username " +username);
            log.debug("completeName " +completeName);
            log.debug("language " +language);
            log.debug("bio " +bio);
            email = email != null  ? filterValue(email) : null;
            username = username != null ? filterValue(username) : null;
            completeName = completeName != null ? filterValue(completeName) : null;
            language = language != null ? filterValue(language) : language;
            bio = bio != null ? filterValue(bio) : null;
            //valid flag.
            boolean valid = true;
            boolean emailValid = operations.validateUserEmail(email, account);
            log.debug("emailValid " +emailValid);
            if (!emailValid) {
                listError.put("username", getMessage("e_018", request, null));
                valid = false;
            }
            boolean usernameValid = operations.validateUsername(username, account);
            log.debug("usernameValid " +usernameValid);
            if (!usernameValid) {
                listError.put("email", getMessage("e_005", request, null));
               valid = false;
            }
            log.debug("this form is valid? " +valid);
            if (!valid) {
                log.debug("list errors->" + listError.size());
                setError(listError, response);
                valid = false;
            } else {
                log.debug("updating profile ....");
                //setError("invalid type", response);
                // check if the locale is vlaid
                new Locale.Builder().setLanguage(language).build();
                getSecurityService().updateAccountProfile(bio, language, completeName, username, email);
                setSuccesResponse(getMessage("settings_config_profile_success", request, null));
            }
        } catch (Exception e) {
            log.error(e);
            //e.printStackTrace();
            setError(getMessage("e_023", request, null), response);
        }
        return returnData();
    }
}
