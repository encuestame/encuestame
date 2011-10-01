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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Notifications Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 27, 2011
 */
@Controller
public class NotificationController extends AbstractBaseOperations{

    /**
        * Log.
        */
        private Log log = LogFactory.getLog(this.getClass());

    /**
     * List all notifications.
     * @param model
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/notifications", method = RequestMethod.GET)
    public String notificationListController(ModelMap model,
            HttpServletRequest request, HttpServletResponse response) {
        //final HashMap<DateClasificatedEnum, List<UtilNotification>> list = classifyNotificationList(convertNotificationList(getSecurityService()
        //        .loadNotificationByUserAndLimit(200), request));
        ///log.debug(list);
        //model.addAttribute("notifications", list);
        return "user/notifications";
    }
}
