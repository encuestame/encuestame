/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.json.notifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.utils.web.notification.UtilNotification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Notification Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 5:56:22 PM
 * @version $Id:$
 */
@Controller("notificationsJsonController")
public class NotificationsJsonController extends AbstractJsonController {

    /**
     * Get Notifications.
     * @param limit
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notifications.json", method = RequestMethod.GET)
    public ModelMap get(
            @RequestParam(value = "limit") Integer limit,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         final SecUserSecondary secondary = getByUsername(getUserPrincipalUsername());
         if(secondary == null){
             setError("user not found");
         }
         final List<Notification> notifications = getNotificationDao().loadNotificationByUserAndLimit(secondary.getSecUser(), limit);
         final List<UtilNotification> utilNotifications = new ArrayList<UtilNotification>();
         for (Notification notification : notifications) {
             final UtilNotification utilNotification = new UtilNotification();
             utilNotification.setDate(SIMPLE_DATE_FORMAT.format(notification.getCreated()));
             utilNotification.setDescription(convertNotificationMessage(notification.getDescription(), request, null));
             utilNotification.setId(notification.getNotificationId());
             utilNotification.setHour(SIMPLE_TIME_FORMAT.format(notification.getCreated()));
             utilNotification.setIcon(convertNotificationIconMessage(notification.getDescription()));
             utilNotifications.add(utilNotification);
             utilNotification.setType(notification.getDescription().name());
             utilNotification.setAdditionalDescription(notification.getAdditionalDescription());
         }
         setItemResponse("notifications", utilNotifications);
         return returnData();
    }

    /**
     * Change Status.
     * @param limit
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/change-status-notifications.json", method = RequestMethod.GET)
    public ModelMap changeStatus(
            @RequestParam(value = "notificationId") Integer limit,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        return null;
    }

    /**
     * Remove Notification.
     * @param notificationId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_ADMIN')")
    @RequestMapping(value = "/remove-notification.json", method = RequestMethod.GET)
    public ModelMap removeNotification(
            @RequestParam(value = "notificationId") Long notificationId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            final Notification notification = getNotificationDao().retrieveNotificationById(notificationId);
            if(notification == null){
                setError("notification not found");
            } else {
                getNotificationDao().delete(notification);
                setItemResponse("removed", "ok");
            }
        return returnData();
    }
}
