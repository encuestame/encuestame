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

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since 04/08/2011
 */
public class DashboardJsonControllerTestCase  extends AbstractJsonMvcUnitBeans{


    /**
     * @throws IOException
     * @throws ServletException
     *
     */
    @Test
    public void testCreateDashboard() throws ServletException, IOException{
        initService("/api/common/dashboard/create-dashboard.json", MethodJson.POST);
        setParameter("name", "test");
        setParameter("desc", "test");
        setParameter("favorite", "true");
        setParameter("layout", "AAA");
        final JSONObject response = callJsonService();
        //{"error":{},"success":{"dashboard":{"dashboard_name":"test","id":7608,"sequence":null,
        //"layout":"AAA","favorite":true,"favorite_counter":null,"dashboard_description":"test"}}}
        final JSONObject success = getSucess(response);
        final JSONObject dashboard = (JSONObject) success.get("dashboard");
        Assert.assertEquals(dashboard.get("dashboard_name").toString(), "test");
        Assert.assertEquals(dashboard.get("layout").toString(), "AAA");
        Assert.assertEquals(dashboard.get("favorite").toString(), "true");
        Assert.assertEquals(dashboard.get("dashboard_description").toString(), "test");
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testgetGadgets() throws ServletException, IOException{
        initService("/api/common/gadgets/list.json", MethodJson.GET);
        final JSONObject response = callJsonService();
        System.out.println(response);
        final JSONObject success = getSucess(response);
        final JSONArray gadgets = (JSONArray) success.get("gadgets");
        Assert.assertEquals(gadgets.size(), 0);
        createGadgetDefault();
        createGadgetDefault();
        initService("/api/common/gadgets/list.json", MethodJson.GET);
        final JSONObject response2 = callJsonService();
        System.out.println(response2);
        final JSONObject success2 = getSucess(response2);
        final JSONArray gadgets2 = (JSONArray) success2.get("gadgets");
        Assert.assertEquals(gadgets2.size(), 0);
    }
}
