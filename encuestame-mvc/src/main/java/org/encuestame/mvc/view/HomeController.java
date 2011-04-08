/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home or FrontEnd Controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */
@Controller
public class HomeController extends AbstractBaseOperations {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Home Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homeController(
            ModelMap model,
            HttpServletRequest request,
            HttpServletResponse response) {
        final Boolean privateHome = EncuestamePlaceHolderConfigurer.getBooleanProperty("application.private");
        log.debug("HOME");
        if (privateHome) {
            log.debug("signup is disabled");
            return "redirect:/user/signin";
        } else {
            return "home";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(
            ModelMap model,
            HttpServletRequest request,
            HttpServletResponse response) {
            log.debug("INDEX INDEX INDEX INDEX INDEX INDEX");
            return "redirect:/home";
    }
}
