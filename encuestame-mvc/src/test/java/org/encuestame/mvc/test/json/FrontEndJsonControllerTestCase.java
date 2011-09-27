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
package org.encuestame.mvc.test.json;

import java.io.IOException;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.HashTagsJsonController;
import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link HashTagsJsonController} Test Case.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 13, 2011
 */
public class FrontEndJsonControllerTestCase extends AbstractJsonMvcUnitBeans{


    @Before
    public void startFrontEnd(){
        createDashboard("dashboard 1", true, getSpringSecurityLoggedUserAccount());
        createDashboard("dashboard 2", true, getSpringSecurityLoggedUserAccount());
        createDashboard("dashboard 3", true, getSpringSecurityLoggedUserAccount());
    }

    /**
     * @throws IOException
     * @throws ServletException
     *
     */
    @Test
    public void testGetMyDashBoards() throws ServletException, IOException {
        initService("/api/common/dashboard/list.json", MethodJson.GET);
        final JSONObject response = callJsonService();
        // {"error":{},"success":{"cloud":[]}}
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("items");
        final String label = (String) success.get("label");
        final String identifier = (String) success.get("identifier");
        Assert.assertNotNull(items);
        Assert.assertEquals(label , "dashboard_name");
        Assert.assertEquals(identifier , "id");
        Assert.assertEquals(items.size(), 3);
    }


    /**
     * Get list directory.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetDirectory() throws ServletException, IOException {
        initService("/api/common/gadgets/directory.json", MethodJson.GET);
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("gadgets");
        Assert.assertNotNull(items);
        Assert.assertEquals(items.size(), 6);
        final JSONObject gadget = (JSONObject) items.get(0);
        final String gadgetId = (String) gadget.get("id");
        //add gadgets to dashboard
        initService("/api/common/gadgets/add.json", MethodJson.GET);
        setParameter("gadgetId", gadgetId);
        final Dashboard db = createDashboard("dashboard 1", true, getSpringSecurityLoggedUserAccount());
        setParameter("boardId", db.getBoardId().toString());
        final JSONObject response2 = callJsonService();
        final JSONObject success2 = getSucess(response2);
        System.out.println(success2);
        final JSONObject gadgetJson = (JSONObject) success2.get("gadget");
        Assert.assertEquals("1", gadgetJson.get("gadget_position").toString());
        Assert.assertEquals("1", gadgetJson.get("gadget_column").toString());
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testcreateDashBoard() throws ServletException, IOException {
        initService("/api/common/dashboard/create-dashboard.json", MethodJson.POST);
        setParameter("name", "dasboard 1");
        setParameter("desc", "description of my dashboard");
        setParameter("favourite", "true");
        setParameter("layout", "AA");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONObject items = (JSONObject) success.get("dashboard");
        Assert.assertNotNull(items);
        Assert.assertEquals(items.get("dashboard_name"), "dasboard 1");
    }


    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    //@Test
    public void testmoveGadget() throws ServletException, IOException {
        final Dashboard db = createDashboard("dashboard 1", true, getSpringSecurityLoggedUserAccount());
        initService("/api/common/dashboard/move-gadget.json", MethodJson.GET);
        setParameter("gadgetId", "stream");
        setParameter("position", "1");
        setParameter("column", "1");
        setParameter("dashboardId", db.getBoardId().toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        //final JSONObject items = (JONObject) success.get("dashboard");
        //Assert.assertNotNull(items);
        //Assert.assertEquals(items.get("dashboard_name"), "dasboard 1");
    }


}