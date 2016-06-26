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
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.json.ProfileUserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Settings Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 29, 2011 11:37:56 AM
 * @version $Id:$
 */
@Controller
public class SettingsController extends AbstractBaseOperations{

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Account Settings.
     * @param model
     * @return
     */
    @RequestMapping(value = "/settings/configuration", method = RequestMethod.GET)
    public String settingsAccountController(
    		ModelMap model, 
    		final UserAccount userAccount,
    		HttpServletRequest request,
    		HttpServletResponse response) {
        ProfileUserAccount user;
        try {
            user = getProfileUserInfo();
            model.put("account", user);
            setCss(model, "settings");
            addi18nProperty(model, "settings_config_profile_title", request, response);
            addi18nProperty(model, "settings_config_profile_description", request, response);
            addi18nProperty(model, "settings_config_profile_email", request, response);
            addi18nProperty(model, "settings_config_profile_email_description", request, response);
            addi18nProperty(model, "settings_config_profile_username", request, response);
            addi18nProperty(model, "settings_config_profile_username_description", request, response);
            addi18nProperty(model, "settings_config_profile_complete_name", request, response);
            addi18nProperty(model, "settings_config_profile_language", request, response);
            addi18nProperty(model, "e_005", request, response);
            addi18nProperty(model, "commons_update", request, response);
            // picture
            addi18nProperty(model, "settings_config_picture_title", request, response);
            addi18nProperty(model, "settings_config_picture_description", request, response);
            addi18nProperty(model, "settings_config_picture_own", request, response);
            addi18nProperty(model, "settings_config_picture_restrictions", request, response);
            addi18nProperty(model, "m_023", request, response);
            addi18nProperty(model, "settings_config_profile_form_not_valid", request, response);
            //social settings

            /** *** *** ** Help Guide ** *** *** **/
            addi18nProperty(model, "help_settings_config_options_sidebar", request, response);
            addi18nProperty(model, "help_settings_config_container", request, response);

            log.debug("settingsAccountController user: " + user.toString());
        } catch (EnMeNoResultsFoundException e) {
            log.error(e);
            //e.printStackTrace();
        }
        return "settings/account";
    }

    /**
    * Social Settings Accounts.
    * @param model
    * @return
    */
   @RequestMapping(value = "/settings/social", method = RequestMethod.GET)
   public String socialSettingsController(
		   ModelMap model,
		   HttpServletRequest request,
   		HttpServletResponse response) {
       log.debug("social");
       setCss(model, "settings");
       addi18nProperty(model, "settings_config_profile_email", request, response);
       addi18nProperty(model, "settings_config_profile_complete_name", request, response);
       addi18nProperty(model, "settings_social_tp_published_whith_this_account", request, response);
       addi18nProperty(model, "settings_social_pll_published_whith_this_account", request, response);
       addi18nProperty(model, "settings_social_su_published_whith_this_account", request, response);
       addi18nProperty(model, "button_remove", request, response);
       addi18nProperty(model, "settings_social_set_default", request, response);
       addi18nProperty(model, "settings_social_profile_url", request, response);
       addi18nProperty(model, "settings_social_seted_as_default", request, response);
       addi18nProperty(model, "commons_confirm", request, response);
       addi18nProperty(model, "commons_no", request, response);
       addi18nProperty(model, "commons_yes", request, response);
       /** *** *** ** Help Guide ** *** *** **/
       addi18nProperty(model, "help_settings_accounts_sidebar", request, response);
       addi18nProperty(model, "help_settings_accounts_list_view", request, response);

       return "settings/social";
   }
}
