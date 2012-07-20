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
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.categories.test.JSONTest;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.Repeat;

/**
 * {@link HashTagsJsonController} Test Case.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 13, 2011
 */
@Category(DefaultTest.class)
public class HashTagsJsonServiceTestCase extends AbstractJsonMvcUnitBeans{

    /**
     * Test /api/common/hashtags.json.
     * @throws IOException
     * @throws ServletException
     */
    @Test
    @Repeat(5)
    public void testgetHashTags() throws ServletException, IOException{
         createHashTag("Nicaragua");
         createHashTag("Spain");
         createHashTag("Java");
         createHashTag("Condega");
         createHashTag("Esteli");
         createHashTag("Pozuelo de Alarcon");
         flushIndexes();
         Assert.assertEquals(testHashTags("10", "j*").intValue(), 1);
         createHashTag("JavaScript");
         createHashTag("Javasian");
         flushIndexes();
         Assert.assertEquals(testHashTags("10", "j*").intValue(), 3);
         Assert.assertEquals(testHashTags("10", "n*").intValue(), 1);
         Assert.assertEquals(testHashTags("10", "s*").intValue(), 1);
         Assert.assertEquals(testHashTags("10", "spa*").intValue(), 1);
         Assert.assertEquals(testHashTags("10", "nicaragua").intValue(), 1);
    }

    /**
     * Run hashtags json service.
     * @return
     * @throws ServletException exception
     * @throws IOException io exception.
     */
    private Integer testHashTags(final String limit, final String keyword) throws ServletException, IOException{
        initService("/api/common/hashtags.json", MethodJson.GET);
        setParameter("limit", limit);
        setParameter("keyword", keyword);
        final JSONObject response = callJsonService();
        //{"error":{},"success":{"items":[],"label":"hashTagName","identifier":"id"}}
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("items");
        final String label = (String) success.get("label");
        final String identifier = (String) success.get("identifier");
        Assert.assertNotNull(items);
        Assert.assertEquals(label , "hashTagName");
        Assert.assertEquals(identifier , "id");
        return items.size();
    }

    /**
     * Test Cloud Json Service.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    @Repeat(5)
    public void testgetCloud() throws ServletException, IOException{
         createHashTag("DELL", 500L, 10L);
         createHashTag("Spain", 2345L, 23L);
         createHashTag("Pozuelo de Alarcon", 2434L, 43L);
         createHashTag("Pozuelo", 432432L, 23L);
         createHashTag("Condega", 2L, 23L);
         createHashTag("Esteli", 4L, 23L);
         flushIndexes();
         Assert.assertEquals(testCloudHashTags("10").intValue(), 5);
         createHashTag("Esteli", 4L, 0L);
         Assert.assertEquals(testCloudHashTags("10").intValue(), 5);
         createHashTag("Jota", 44L, 10L);
         Assert.assertEquals(testCloudHashTags("4").intValue(), 4);
         Assert.assertEquals(testCloudHashTags("2").intValue(), 2);
         Assert.assertEquals(testCloudHashTags("1").intValue(), 1);
         Assert.assertEquals(testCloudHashTags("0").intValue(), 5);
    }

    /**
     * Call to Cloud HashTag JSON Service.
     * @param limit limit of results.
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private Integer testCloudHashTags(final String limit) throws ServletException, IOException {
        initService("/api/common/hashtags/cloud.json", MethodJson.GET);
        setParameter("limit", limit);
        final JSONObject response = callJsonService();
        // {"error":{},"success":{"cloud":[]}}
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("cloud");
        Assert.assertNotNull(items);
        return items.size();
    }

    /**
     * Test cloud json service whith out limit param.
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCloudHashTagsWithoutLimit() throws ServletException, IOException {
        createHashTag("DELL", 500L, 10L);
        createHashTag("Spain", 2345L, 23L);
        createHashTag("Pozuelo de Alarcon", 2434L, 43L);
        flushIndexes();
        initService("/api/common/hashtags/cloud.json", MethodJson.GET);
        final JSONObject response = callJsonService();
        // {"error":{},"success":{"cloud":[]}}
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("cloud");
        Assert.assertNotNull(items);
        Assert.assertEquals(items.size(), 2);
    }
}