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
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * DashBoard Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */

@Controller
public class DashBoardController extends AbstractViewController {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * DashBoard Controller.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/dashboard", method = RequestMethod.GET)
    public String dashBoardController(ModelMap model, UserAccount account,  final HttpServletRequest request, HttpServletResponse response) {
        setCss(model, "dashboard");
        addi18nProperty(model, "dashboard_title", request, response);
        addi18nProperty(model, "dashboard_description", request, response);
        addi18nProperty(model, "dashboard_add_gadget", request, response);
        addi18nProperty(model, "dashboard_add_gadget_tooltip", request, response);
        addi18nProperty(model, "dashboard_change_layout", request, response);
        addi18nProperty(model, "dashboard_change_layout_tooltip", request, response);
        addi18nProperty(model, "dashboard_new_dasboard", request, response);        
        addi18nProperty(model, "dashboard_panel_name", request, response);
        addi18nProperty(model, "dashboard_panel_descri", request, response);
        addi18nProperty(model, "commons_yes", request, response);
        addi18nProperty(model, "commons_no", request, response);
        addi18nProperty(model, "commons_no_results", request, response);
        addi18nProperty(model, "commons_confirm", request, response);
        addi18nProperty(model, "commons_create", request, response);
        addi18nProperty(model, "gadgets_activity_description", request, response);
        addi18nProperty(model, "gadgets_comments_description", request, response);
        addi18nProperty(model, "gadgets_tweetpoll_description", request, response);
        //help links
        addi18nProperty(model, "help_center_search", request, response);
        addi18nProperty(model, "help_menu_search", request, response);
        addi18nProperty(model, "help_home_menu", request, response);
        addi18nProperty(model, "autocomplete", request, response);
        addi18nProperty(model, "help_counter_notification", request, response);
        addi18nProperty(model, "help_profile_menu", request, response);
        addi18nProperty(model, "help_change_layout_button", request, response);
        addi18nProperty(model, "help_change_gadget_button", request, response);
        addi18nProperty(model, "help_dashboard_tool", request, response);
        addi18nProperty(model, "help_dashboards_columns", request, response);
        return "dashboard";
    }
}
