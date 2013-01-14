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
package org.encuestame.mvc.controller.security.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.mvc.validator.ValidateOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Validator json controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 14, 2011
 */
@Controller
public class ValidatorServiceJsonController extends AbstractJsonController {

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/api/public/validator/email.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap validateEmail(
            @RequestParam(value = "context", required = false) String context,
            @RequestParam(value = "email", required = false) String email,
            HttpServletRequest request, HttpServletResponse response){
        try {
            setItemResponse(this.validate(context, "email", email, request));
        } catch (Exception e) {
            log.error(e);

        }
        return returnData();
    }

    @RequestMapping(value = "/api/public/validator/realName.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap validateRealName(
            @RequestParam(value = "context", required = false) String context,
            @RequestParam(value = "real_name", required = false) String name,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            setItemResponse(this.validate(context, "realName", name, request));
        } catch (Exception e) {
            log.error(e);

        }
        return returnData();
    }

    @RequestMapping(value = "/api/public/validator/username.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap validateUsername(
            @RequestParam(value = "context", required = false) String context,
            @RequestParam(value = "username", required = false) String username,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            setItemResponse(this.validate(context, "username", username, request));
        } catch (Exception e) {
            log.error(e);

        }
        return returnData();
    }


    /**
     * Validate items based on context.
     * @param context could be signup, update profile or another one.
     * @param type
     * @param value
     * @return
     */
    private  Map<String, Object> validate(final String context, final String type, String value, final  HttpServletRequest request) {
        value = value == null ? "" : value;
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        final ValidateOperations validateOperations = new ValidateOperations(getSecurityService());
        boolean valid = false;
        if ("email".equals(type)) {
            if (value.isEmpty() || value.length() < ValidateOperations.MIN_EMAIL_LENGTH) {
                 log.debug("validate email emtpy");
                jsonResponse.put("msg", getMessage("secure.email.emtpy", request, null));
            } else {
                valid = validateOperations.validateUserEmail(value, null);
                log.debug("validate EMAIL"+valid);
                if (valid) {
                    jsonResponse.put("msg", getMessage("secure.email.valid", request, null));
                } else {
                    jsonResponse.put("msg", getMessage("secure.email.notvalid", request, null));
                }
            }
        } else if("username".equals(type)) {
            valid = validateOperations.validateUsername(value, null);
            if(value.isEmpty() || value.length() < ValidateOperations.MIN_USERNAME_LENGTH) {
                log.debug("validate username emtpy");
                jsonResponse.put("msg", getMessage("secure.username.empty", request, null));
            } else {
                log.debug("validate username NO emtpy");
                if (!valid) {
                    jsonResponse.put("msg", getMessage("secure.user.notvalid", request, null));
                    final List<String> suggestions = new ArrayList<String>();
                    for (int i = 0; i < 5; i++) {
                        suggestions.add(value+RandomStringUtils.randomAlphabetic(ValidateOperations.LENGTH_RANDOM_VALUE));
                    }
                    jsonResponse.put("suggestions", suggestions);
                } else {
                    jsonResponse.put("msg", getMessage("secure.username.valid", request, null));
                }
                jsonResponse.put("valid", valid);
            }
        } else if("realName".equals(type)) {
            if (value.isEmpty()){
                valid = false;
                jsonResponse.put("msg", getMessage("secure.realName.empty", request, null));
            } else {
                valid = true;
                jsonResponse.put("msg", getMessage("secure.realName.valid", request, null));
            }
        } else {
            jsonResponse.put("msg", getMessage("secure.type.not.valid", request, null));
        }
        jsonResponse.put("valid", valid);
        jsonResponse.put("color", "#RRR");
        return jsonResponse;
    }
}
