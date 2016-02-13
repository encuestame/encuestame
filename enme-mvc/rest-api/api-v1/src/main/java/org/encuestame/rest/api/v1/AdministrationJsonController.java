/*
 *
 *  * Copyright 2015 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.rest.api.v1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jpicado on 07/06/15.
 */
@Controller
public class AdministrationJsonController extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @api {post} /api/admon/status Check the auth status
     * @apiName GetHomeItems
     * @apiGroup FrontEnd
     * @apiDescription Check if a user is already logged or not
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/admon/status
     * @apiPermission none
     * @apiSuccessExample
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/user/status", method = RequestMethod.POST)
    public @ResponseBody LoginStatus checkAuthStatus(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getName().equals("anonymousUser")) {
                return new LoginStatus(Boolean.FALSE, auth.getName());
            } else {
                return new LoginStatus(auth.isAuthenticated(), auth.getName());
            }
        } catch (BadCredentialsException e) {
            return new LoginStatus(false, null);
        }
    }
}
