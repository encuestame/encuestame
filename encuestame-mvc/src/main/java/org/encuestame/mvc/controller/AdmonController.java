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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Admon View Controller.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */

@Controller
public class AdmonController extends BaseController {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return template
     */
    @RequestMapping(value = "/admon/location.jspx", method = RequestMethod.GET)
    public String admonLocation(ModelMap model) {
        log.debug("LOCATION");
        return "admon/location";
    }

    @RequestMapping(value = "/admon/members.jspx", method = RequestMethod.GET)
    public String membersLocation(ModelMap model) {
        log.debug("LOCATION");
        return "admon/members";
    }

    @RequestMapping(value = "admon/project.jspx", method = RequestMethod.GET)
    public String admonProject(ModelMap model) {
        log.debug("LOCATION");
        return "admon/project";
    }

}
