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
package org.encuestame.mvc.controller.json.v1.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
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
public class ValidatorServiceJsonController extends AbstractJsonControllerV1 {

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
}
