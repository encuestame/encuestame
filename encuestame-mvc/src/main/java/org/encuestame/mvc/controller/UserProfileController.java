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
package org.encuestame.mvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User profile view controller.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 2, 2011
 */
@Controller
public class UserProfileController extends AbstractBaseOperations {

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
    public String tweetPollController(
            final ModelMap model,
            @PathVariable String username) {
        username = filterValue(username);
        final UserAccountBean accountBean = getSecurityService().searchUserByUsername(username);
        if (accountBean == null) {
            return "404";
        } else {
            log.debug("user "+accountBean);
            model.put("profile", accountBean);
            return "profile/view";
        }
    }
}
