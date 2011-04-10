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

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.controller.search.json.QuickSearchJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link QuickSearchJsonController}.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
public class QuickSearchJsonControllerTestCase extends AbstractJsonMvcUnitBeans{

    /**
     * Init Test.
     */
    @Before
    public void initTest() {

    }

    /**
     * Test api/search/quick-suggest.json.
     * @throws Exception
     */
    @Test
    public void quickSearchTest() throws Exception {
        flushIndexes();
        initService("/api/search/quick-suggest.json", MethodJson.GET);
        setParameter("keyword", "a");
        final JSONObject response = callJsonService();
        System.out.println(response);
        final JSONObject sucess = getSucess(response);
        Assert.assertNotNull(sucess.get("items"));
    }
}
