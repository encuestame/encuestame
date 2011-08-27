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
package org.encuestame.mvc.test.json;

import java.util.List;

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.controller.json.notifications.NotificationsJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Test for {@link NotificationsJsonController}.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 26, 2010 8:19:49 PM
 * @version $Id:$
 */
public class NotificationJsonServiceTestCase extends AbstractJsonMvcUnitBeans {

    /** Notification. **/
    private Notification notification;

    /**
     * Init Test.
     */
    @Before
    public void initTest() {
       this.notification = createNotification("test notification", getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.PROJECT_CREATED, false);
        createNotification("test notification", getSpringSecurityLoggedUserAccount().getAccount(),
                NotificationEnum.TWEETPOL_CREATED, false);
    }

    /**
     * Test /api/notifications.json.
     * @throws Exception
     */
    @Test
    public void notificationsInJsonFormatTest() throws Exception {
        initService("/api/notifications/list.json", MethodJson.GET);
        setParameter("limit", "10");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        JSONArray listNotifications = (JSONArray) sucess.get("notifications");
        final List<Notification> list = getNotification().loadNotificationByUserAndLimit(getSpringSecurityLoggedUserAccount().getAccount(), 100, 0, true);
        Assert.assertEquals(list.size(), listNotifications.size());
        initService("/api/notifications/list.json", MethodJson.GET);
        setParameter("limit", "1");
        final JSONObject response2 = callJsonService();
        final JSONObject sucess2 = getSucess(response2);
        final JSONArray listNotifications2 = (JSONArray) sucess2.get("notifications");
        Assert.assertEquals(listNotifications2.size(), 1);
    }

    /**
     * Test /api/notifications.json.
     * @throws Exception
     */
    @Test
    public void removeNotificationsInJsonFormatTest() throws Exception {
        initService("/api/remove-notification.json", MethodJson.GET);
        setParameter("notificationId", this.notification.getNotificationId().toString());
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("removed"), "ok");
        initService("/api/remove-notification.json", MethodJson.GET);
        setParameter("notificationId", "12345");
        final JSONObject responseFail = callJsonService();
        final JSONObject error = getErrors(responseFail);
        Assert.assertEquals(error.get("message"), "notification not found");
    }

    /**
     * Test /api/notifications/readed.json
     * @throws Exception
     */
    @Test
    public void changeStatusTest() throws Exception {
        initService("/api/notifications/readed.json", MethodJson.GET);
        setParameter("id", this.notification.getNotificationId().toString());
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
        initService("/api/notifications/readed.json", MethodJson.GET);
        setParameter("id", "12345");
        final JSONObject response2 = callJsonService();
        assertFailedResponse(response2);
    }

    /**
     * Test /api/status-notifications.json.
     * @throws Exception
     */
    @Test
    public void statusTest() throws Exception {
        initService("/api/status-notifications.json", MethodJson.GET);
        setParameter("id", this.notification.getNotificationId().toString());
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("t").toString(), "2");
        Assert.assertEquals(sucess.get("n").toString(), "2");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void listTest() throws Exception {
        initService("/api/notifications/all/list.json", MethodJson.GET);
        setParameter("categorized", "true");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        JSONObject listNotifications = (JSONObject) success.get("notifications");
        JSONArray LONG_TIME_AGO = (JSONArray) listNotifications.get("LONG_TIME_AGO");
        JSONArray FEW_MONTHS_AGO = (JSONArray) listNotifications.get("FEW_MONTHS_AGO");
        JSONArray TODAY = (JSONArray) listNotifications.get("TODAY");
        JSONArray THIS_WEEK = (JSONArray) listNotifications.get("THIS_WEEK");
        JSONArray LAST_YEAR = (JSONArray) listNotifications.get("LAST_YEAR");
        JSONArray THIS_MONTH = (JSONArray) listNotifications.get("THIS_MONTH");
        JSONArray LAST_MONTH = (JSONArray) listNotifications.get("LAST_MONTH");
        Assert.assertNotNull(LONG_TIME_AGO);
        Assert.assertNotNull(FEW_MONTHS_AGO);
        Assert.assertNotNull(TODAY);
        Assert.assertNotNull(THIS_WEEK);
        Assert.assertNotNull(LAST_YEAR);
        Assert.assertNotNull(THIS_MONTH);
        Assert.assertNotNull(LAST_MONTH);
        Assert.assertEquals(TODAY.size(), 2);
        //
        initService("/api/notifications/all/list.json", MethodJson.GET);
        setParameter("categorized", "false");
        final JSONObject response2 = callJsonService();
        final JSONObject success2 = getSucess(response2);
        JSONArray listNotification2 = (JSONArray) success2.get("notifications");
        //System.out.println(listNotification2);
        Assert.assertNotNull(listNotification2);
        Assert.assertEquals(listNotification2.size(), 2);
    }

    /**
    *
    * @throws Exception
    */
   @Test(expected = Exception.class)
   public void listTestWithOutSecContext() throws Exception {
       //clear context
       SecurityContextHolder.clearContext();
       initService("/api/notifications/all/list.json", MethodJson.GET);
       setParameter("categorized", "false");
       final JSONObject response3 = callJsonService();
       final JSONObject success3 = getSucess(response3);
       JSONObject listNotification3 = (JSONObject) success3.get("notifications");
       //System.out.println(listNotification3);
       Assert.assertNotNull(listNotification3);
   }
}
