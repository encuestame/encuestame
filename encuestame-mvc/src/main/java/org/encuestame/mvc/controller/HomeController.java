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

package org.encuestame.mvc.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.PictureService.PictureType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home or FrontEnd Controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */

@Controller
public class HomeController extends BaseController {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Home Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/home.jspx", method = RequestMethod.GET)
    public String homeController(ModelMap model) {
        log.debug("HOME");
        return "home";
    }


    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signInController(ModelMap model) {
        log.debug("HOME");
        return "user/signin";
    }

    @RequestMapping(value = "/signin2", method = RequestMethod.GET)
    public String signInController2(ModelMap model) {
        log.debug("HOME222222");
        return "user/signin";
    }

    /**
     * Search.
     * @param model
     * @return
     */
    @RequestMapping(value = "/search.jspx", method = RequestMethod.POST)
    public String searchHomePost(ModelMap model) {
        log.debug("search");
        return "search";
    }

}
