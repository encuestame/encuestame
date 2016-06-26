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
package org.encuestame.rest.api.v1.notifications;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.DateClasificatedEnum;
import org.encuestame.utils.web.notification.UtilNotification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      * @api {get} /api/status-notifications.json Status Notification
      * @apiName GetNotificationStatus
      * @apiGroup Notifications
      * @apiDescription This service returns the total number of notifications per user and how many of them have not been read.
      * @apiVersion 1.0.0
      * @apiSampleRequest http://www.encuestame.org/demo/api/status-notifications.json
      * @apiPermission ENCUESTAME_USER
      * @apiSuccessExample
			{
				"error": {

				},
    			"success": {
        			"t": 764,
        			"n": 764
    			}
			}
      * @apiSuccess {Number} t Total number of notifications.
      * @apiSuccess {Number} t Total number of new notifications.
      * @apiErrorExample
			{
				"error":{
					"message":"Access is denied",
					"session":true,"status":403,
					"description":"You do not have access to this resource,
					"anonymousUser":true
				}
			}
      */

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/status-notifications.json", method = RequestMethod.GET)
    @Transactional
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
     * @api {get} /api/notifications/list.json Get Notifications
     * @apiName GetNotifications
     * @apiGroup Notifications
     * @apiDescription Retrieve all last notifications.
     * @apiParam {Number} limit The maximum number of notifications to include in the response.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/notifications/list.json
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
			{
  				"error": {

  				},
    			"success": {
        				"notifications": [
							{
                				"id": 764,
                				"type": "POLL_PUBLISHED",
                				"date": "2012-09-12",
                				"description": null,
                				"url": "/poll/abbfef5c71778e69dc84f892a0ced544/28/mi-second-poll",
                				"icon": null,
                				"additionalDescription": "Your Poll has been published",
                				"hour": "10:44:07"
            				}
        				]
    			}
			}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notifications/list.json", method = RequestMethod.GET)
    @Transactional
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
     * @api {get} /api/notifications/all/list.json Categorized Notifications
     * @apiName GetCategorizedNotifications
     * @apiGroup Notifications
     * @apiDescription Return all notifications categorized by a range date.
     * @apiParam {Number} [limit] The maximum number of notifications to include in the response
     * @apiParam {Number} [start] The minimum number of notifications to show in the response.
     * @apiParam {Boolean} categorized Indicate whether the notification list will be displayed sorted by date range
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/notifications/all/list.json
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
			{
    			"error": {

    			},
    			"success": {
        			"notifications": {
            				"THIS_MONTH": [
            					{
                    				"id": 763,
                    				"type": "POLL_PUBLISHED",
                    				"date": "2015-03-24",
                    				"description": null,
                    				"url": "/poll/4c04386ca0562ffe2cfc2f6f5e18b0ef/27/mi-poll-question",
                    				"icon": null,
                    				"additionalDescription": "Your Poll has been published",
                    				"hour": "10:09:34"
                			}

            				],
            				"LAST_MONTH": [ ],
            				"THIS_WEEK": [ ],
            				"LONG_TIME_AGO": [ ],
            				"LAST_YEAR": [ ],
            				"FEW_MONTHS_AGO": [ ],
            				"TODAY": [
                				{
                    				"id": 764,
                    				"type": "POLL_PUBLISHED",
                    				"date": "2015-04-24",
                    				"description": null,
									"url": "/poll/abbfef5c71778e69dc84f892a0ced544/28/mi-second-poll",
                    				"icon": null,
                    				"additionalDescription": "Tu sondeo ha sido publicado",
                    				"hour": "10:44:07"
                				},
                   			]
        			}
    			}
			}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notifications/all/list.json", method = RequestMethod.GET)
    @Transactional
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
     * @api {get} /api/notifications/readed.json Change Status
     * @apiName GetReadedNotifications
     * @apiGroup Notifications
     * @apiDescription Change the status of the notification when it has been read.
     * @apiParam {Number} id This is the id(unique identifier) of the notification that will be changed
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/notifications/readed.json?id=1
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
			{
    			"error": {

    			},
    			"success": {
					"r": 0
    			}
			}
		@apiSuccess {r} Default answer to define a satisfactory response.
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notifications/readed.json", method = RequestMethod.GET)
    @Transactional
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
     * @api {delete} /api/notification/remove.json Remove Notification
     * @apiName DeleteNotification
     * @apiGroup Notifications
     * @apiDescription Remove activity notification..
     * @apiParam {Number} notificationId This is the id(unique identifier ) of the notification that will be deleted.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/remove-notification.json?notificationId=1
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
			{
    			"error": {

    			},
    			"success": {
					"r": 0
    			}
			}
		@apiSuccess {r} Default answer to define a satisfactory response.
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/notification/remove.json", method = RequestMethod.DELETE)
    @Transactional
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
