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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.controller.json.notifications.NotificationsJsonController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

/**
 * Test Json Service.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 26, 2010 8:19:49 PM
 * @version $Id:$
 */
public class NotificationJsonServiceTestCase extends AbstractMvcUnitBeans {

    /**
     * Init Test.
     */
    @Before
    public void initTest() {
        createNotification("test notification", getSecondary().getSecUser(),
                NotificationEnum.PROJECT_CREATED);
        createNotification("test notification", getSecondary().getSecUser(),
                NotificationEnum.TWEETPOL_CREATED);
    }

    /**
     * Test /api/notifications.json.
     * @throws Exception
     */
    @Test
    public void notificationsInJsonFormatTest() throws Exception {
        initService("/api/notifications.json", MethodJson.GET);
        setParameter("limit", "10");
        JSONObject response = callJsonService();
        JSONObject sucess = getSucess(response);
        JSONArray listNotifications = (JSONArray) sucess.get("notifications");
        final List<Notification> list = getNotification().loadNotificationByUserAndLimit(getSecondary().getSecUser(), 100);
        Assert.assertEquals(list.size(), listNotifications.size());
        initService("/api/notifications.json", MethodJson.GET);
        setParameter("limit", "1");
        JSONObject response2 = callJsonService();
        JSONObject sucess2 = getSucess(response2);
        JSONArray listNotifications2 = (JSONArray) sucess2.get("notifications");
        Assert.assertEquals(listNotifications2.size(), 1);
    }

}
