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

import org.apache.log4j.Logger;
import org.encuestame.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 11, 2010 9:21:55 PM
 * @version $Id:$
 */
@Controller
public class SurveyController  extends BaseController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Survey Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/survey.jspx", method = RequestMethod.GET)
    public String surveyController(final ModelMap model) {
        log.debug("survey");
        return "survey";
    }
}
