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
package org.encuestame.mvc.controller.json.v1.notifications;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.DateClasificatedEnum;
import org.encuestame.utils.web.notification.UtilNotification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Notification Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 5:56:22 PM
 * @version $Id:$
 */
@Controller("notificationsJsonController")
public class NotificationsJsonController extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
     private Log log = LogFactory.getLog(this.getClass());

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
    public @ResponseBody ModelMap status(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> responseJson = new HashMap<String, Object>();
        UserAccount userAccount;
        try {
            userAccount = getByUsername(getUserPrincipalUsername());
            if (userAccount != null) {
                final Long totalNot = getNotificationDao().retrieveTotalNotificationStatus(userAccount.getAccount());
                final Long totalNewNot = getNotificationDao().retrieveTotalNotReadedNotificationStatus(userAccount.getAccount());
                responseJson.put("t", totalNot);
                responseJson.put("n", totalNewNot);
                setItemResponse(responseJson);
            } else {
                setError("account not valid", response);
            }

        } catch (EnMeNoResultsFoundException e) {
             setError(e.getMessage(), response);
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
     * @throws if(secondary == null){
             setError("account not valid", response);
         }
         final Map<String, Object> responseJson = new HashMap<String, Object>();
        final List<Notification> notifications = getNotificationDao()
                .loadNotificationByUserAndLimit(secondary.getAccount(), limit,
                        0, Boolean.TRUE);
        responseJson.put("notifications",
                convertNotificationList(notifications, request));
        setItemResponse(responseJson); IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notifications/list.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap get(
            @RequestParam(value = "limit") Integer limit,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         UserAccount secondary;
        try {
             secondary = getByUsername(getUserPrincipalUsername());
             if (secondary == null){
                 setError("account not valid", response);
             }
             final Map<String, Object> responseJson = new HashMap<String, Object>();
            final List<UtilNotification> streamOperations = getStreamOperations().retrieveLastNotifications(limit, request);
            responseJson.put("notifications", streamOperations);
            setItemResponse(responseJson);
        } catch (EnMeNoResultsFoundException e) {
             setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     *
     * @param limit
     * @param start
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notifications/all/list.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getClassifiedNotifications(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "categorized", required = true) Boolean categorized,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> responseJson = new HashMap<String, Object>();
        if (start == null) {
            start = 0; //TODO: this value should be on encuestame-config.properties.
        }
        if (limit ==  null) {
            limit = 10; //TODO: this value should be on encuestame-config.properties.
        }
        //define if notifications are categorized.
        try{
            if (categorized) {
                List<UtilNotification> stream = getStreamOperations().loadNotificationByUserAndLimit(limit, start, Boolean.FALSE, request);
                final HashMap<DateClasificatedEnum, List<UtilNotification>> list = getStreamOperations().classifyNotificationList(stream, request);
                responseJson.put("notifications", list);
            } else {
                responseJson.put("notifications", getStreamOperations().loadNotificationByUserAndLimit(limit, start, Boolean.FALSE, request));
            }
            setItemResponse(responseJson);
        } catch (EnMeNoResultsFoundException e) {
            log.error(e);
            setError(e, response);
        }

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
    public @ResponseBody ModelMap changeStatus(
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
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notification/remove.json", method = RequestMethod.DELETE)
    public @ResponseBody ModelMap removeNotification(
            @RequestParam(value = "id") Long notificationId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            final Notification notification = getNotificationDao().retrieveNotificationById(notificationId);
            final Map<String, Object> responseJson = new HashMap<String, Object>();
            if (notification == null){
                setError("notification not found", response);
            } else {
                getNotificationDao().delete(notification);
                responseJson.put("removed", "ok");
                setItemResponse(responseJson);
            }
        return returnData();
    }
}
