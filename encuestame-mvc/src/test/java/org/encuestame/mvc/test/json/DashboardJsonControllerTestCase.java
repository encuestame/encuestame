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

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
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
        initService("/api/common/dashboard/create-dashboard.json", MethodJson.GET);
        setParameter("name", "test");
        setParameter("desc", "test");
        setParameter("favorite", "true");
        setParameter("layout", "AAA");
        final JSONObject response = callJsonService();
        System.out.println(response);
        final JSONObject success = getSucess(response);
    }
}
