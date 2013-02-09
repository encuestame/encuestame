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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.mvc.controller.AbstractViewController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Admon View Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */

@Controller
public class AdmonController extends AbstractViewController {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/admon/location", method = RequestMethod.GET)
    public String admonLocation(ModelMap model) {
        log.debug("LOCATION");
        return "location";
    }

    /**
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/admon/members", method = RequestMethod.GET)
    public String membersLocation(ModelMap model) {
        log.debug("MEMBERS");
        addi18nProperty(model, "admon_create_user", getMessage("admon_create_user"));
        addi18nProperty(model, "admon_username", getMessage("admon_username"));
        addi18nProperty(model, "admon_group", getMessage("admon_group"));
        addi18nProperty(model, "admon_user_from", getMessage("admon_user_from"));
        addi18nProperty(model, "admon_status", getMessage("admon_status"));
        addi18nProperty(model, "admon_tweetpoll_count", getMessage("admon_tweetpoll_count"));
        addi18nProperty(model, "admon_poll_count", getMessage("admon_poll_count"));
        addi18nProperty(model, "admon_survey_count", getMessage("admon_survey_count"));
        addi18nProperty(model, "admon_last_logged", getMessage("admon_last_logged"));
        addi18nProperty(model, "followers", getMessage("followers"));
        return "members";
    }

    /**
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/admon/project", method = RequestMethod.GET)
    public String admonProject(ModelMap model) {
        log.debug("PROJECT");
        return "project";
    }
}
