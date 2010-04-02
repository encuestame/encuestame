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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.security.ISecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login Controller.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since April 30, 2010 21:13:00 PM
 * @version $Id: $
 */

@Controller
public class LoginController {

    private Log log = LogFactory.getLog(this.getClass());

    private ISecurityContext securityContext;

    @RequestMapping("/login")
    public String login(ModelMap model) {
            System.out.println("Juannnnnnnn");
             log.info("1 "+ SecurityContextHolder.getContext().getAuthentication());
            //log.info("1 "+ securityContext.getContext());
            //log.info("2 "+ securityContext.getContext().getAuthentication());
            //log.info("user "+ securityContext.getContext().getAuthentication().getName());
        return "redirect:/login";
    }

    @RequestMapping("/accessDenied")
    public ModelAndView accessDenied() {
        return new ModelAndView("redirect:/index.do");
    }

}
