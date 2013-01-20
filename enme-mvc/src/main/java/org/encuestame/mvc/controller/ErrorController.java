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

package org.encuestame.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Error Controller
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 11, 2010 9:21:37 PM
 * @version $Id: $
 */
@Controller
public class ErrorController  extends AbstractBaseOperations {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/error")
    public String errorController(final ModelMap model, final HttpServletRequest request, final HttpServletResponse  response) {
        // if the error message is missing, nothing to display, it's redirected to home
        if (request.getAttribute("message") == null) {
             log.debug("no error message, redirect to home");
             return "redirect:/home";
        } else {
            return "error";
        }
    }

    /**
     * Display a user denied view, used by spring security, see the main configuration.
     * @param model
     * @return
     */
    @RequestMapping("/user/denied")
    public String errorDeniedController(final ModelMap model) {
        return "error/denied";
    }


}
