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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.UserAccount;
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
     * Status Notification.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/status-notifications.json", method = RequestMethod.GET)
    public ModelMap status(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> responseJson = new HashMap<String, Object>();
        final UserAccount userAccount = getByUsername(getUserPrincipalUsername());
        if (userAccount != null) {
            final Long totalNot = getNotificationDao().retrieveTotalNotificationStatus(userAccount.getAccount());
            final Long totalNewNot = getNotificationDao().retrieveTotalNotReadedNotificationStatus(userAccount.getAccount());
            responseJson.put("t", totalNot);
            responseJson.put("n", totalNewNot);
            setItemResponse(responseJson);
        } else {
            setError("account not valid", response);
        }
        return returnData();
    }

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
    @RequestMapping(value = "/api/notifications/list.json", method = RequestMethod.GET)
    public ModelMap get(
            @RequestParam(value = "limit") Integer limit,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         final UserAccount secondary = getByUsername(getUserPrincipalUsername());
         if(secondary == null){
             setError("account not valid", response);
         }
         final Map<String, Object> responseJson = new HashMap<String, Object>();
         final List<Notification> notifications = getNotificationDao().loadNotificationByUserAndLimit(secondary.getAccount(), limit);
         responseJson.put("notifications",convertNotificationList(notifications, request));
         setItemResponse(responseJson);
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
    @RequestMapping(value = "/api/notifications/readed.json", method = RequestMethod.GET)
    public ModelMap changeStatus(
            @RequestParam(value = "id", required = true) Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            final Notification notification = getNotificationDao().retrieveNotificationById(id);
            if (notification == null) {
                setError("nothing to do", response);
            } else {
                notification.setReaded(notification.getReaded() == null ? true : !notification.getReaded());
                getNotificationDao().saveOrUpdate(notification);
                setSuccesResponse();
            }
        return returnData();
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
    @RequestMapping(value = "/api/remove-notification.json", method = RequestMethod.GET)
    public ModelMap removeNotification(
            @RequestParam(value = "notificationId") Long notificationId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            final Notification notification = getNotificationDao().retrieveNotificationById(notificationId);
            final Map<String, Object> responseJson = new HashMap<String, Object>();
            if(notification == null){
                setError("notification not found", response);
            } else {
                getNotificationDao().delete(notification);
                responseJson.put("removed", "ok");
                setItemResponse(responseJson);
            }
        return returnData();
    }
}
