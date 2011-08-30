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
package org.encuestame.mvc.controller.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String settingsAccountController(ModelMap model, final UserAccount userAccount) {
        log.debug("settingsAccountController account: "+userAccount);
        log.debug("settingsAccountController account: "+userAccount.toString());
        model.put("username", getUserPrincipalUsername());
        model.put("userAccount", ConvertDomainBean.convertBasicSecondaryUserToUserBean(userAccount));
        return "settings/account";
    }

    /**
    * Social Settings Accounts.
    * @param model
    * @return
    */
   @RequestMapping(value = "/settings/social", method = RequestMethod.GET)
   public String socialSettingsController(ModelMap model) {
       log.debug("social");
       return "settings/social";
   }
}
