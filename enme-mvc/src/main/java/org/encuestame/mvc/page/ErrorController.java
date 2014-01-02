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

package org.encuestame.mvc.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.utils.web.frontEnd.WebMessage;
import org.encuestame.utils.web.frontEnd.WebMessage.WebInfoType;
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
public class ErrorController  extends AbstractViewController {

    /**
     *
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "/missing")
    public String missingPage(final ModelMap model, final HttpServletRequest request, final HttpServletResponse  response) {
        return "404";
    }


    @RequestMapping( value= "/400")
    public String badStatu2s(final ModelMap model, final HttpServletRequest request, final HttpServletResponse  response) {
        return "error";
    }

    /**
     * Catch the error redirected from web.xml <error-page> tag, this controller display a error page with pretty
     * error message if
     * @param model
     * @return
     */
    @RequestMapping({"/error"})
    public String errorController(final ModelMap model, final HttpServletRequest request, final HttpServletResponse  response) {
        if (EnMePlaceHolderConfigurer.getBooleanProperty("encuestame.error.debug")) {
        	log.debug("model error" + model.toString());
        	return "500";
        } else if (request.getAttribute("message") == null) {
        	 // if the error message is missing, nothing to display, it's redirected to home
             log.error("*********************************************************************************************");
             log.error("redirect to home no error message, redirect to home");
             log.error("*********************************************************************************************");
             return "redirect:/home";
        } else {
            log.error("*********************************************************************************************");
            log.error("error no error message, redirect to home");
            log.error("*********************************************************************************************");
            WebMessage emptyError = new WebMessage(WebInfoType.ERROR, getMessage("e_023", request, null), "", this.errorLevel, this.bugTracking);
            request.setAttribute("message", emptyError);
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
