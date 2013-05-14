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

import org.encuestame.mvc.controller.search.json.QuickSearchJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test case for {@link QuickSearchJsonController}.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
@Category(DefaultTest.class)
public class QuickSearchJsonControllerTestCase extends AbstractJsonMvcUnitBeans{

    /** {@link Account} **/
    private Account account;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /**
     * Init Test.
     */
    @Before
    public void initTest() {
        this.account = createAccount();
        createFakesQuestions(this.account);
        createHashTag("Nicaragua");
        createHashTag("Spain");
        createHashTag("Java");
        createHashTag("Condega");
    }

    /**
     * Test api/search/quick-suggest.json.
     * @throws Exception
     */
    @Test
    public void quickSearchTest() throws Exception {
        flushIndexes();
        initService("/api/search/quick-suggest.json", MethodJson.GET);
        setParameter("keyword", "S");
        final JSONObject response = callJsonService();
        Assert.assertEquals(this.getSearchRestuls(response, "tags").size(), 0);
        Assert.assertEquals(this.getSearchRestuls(response, "questions").size(), 0);
        Assert.assertEquals(this.getSearchRestuls(response, "profiles").size(), 0);
        Assert.assertEquals(this.getSearchRestuls(response, "attachments").size(), 0);
        initService("/api/search/quick-suggest.json", MethodJson.GET);
        setParameter("keyword", "a*");
        final JSONObject response2 = callJsonService();
        Assert.assertEquals(this.getSearchRestuls(response2, "tags").size(), 0);
        Assert.assertEquals(this.getSearchRestuls(response2, "questions").size(), 2);
        Assert.assertEquals(this.getSearchRestuls(response2, "profiles").size(), 1);
        Assert.assertEquals(this.getSearchRestuls(response2, "attachments").size(), 0);
        initService("/api/search/quick-suggest.json", MethodJson.GET);
        setParameter("keyword", "Nicaragua");
        final JSONObject response3 = callJsonService();
        Assert.assertEquals(this.getSearchRestuls(response3, "tags").size(), 1);
        Assert.assertEquals(this.getSearchRestuls(response3, "questions").size(), 0);
        Assert.assertEquals(this.getSearchRestuls(response3, "profiles").size(), 0);
        Assert.assertEquals(this.getSearchRestuls(response3, "attachments").size(), 0);
    }

    /**
     *
     * @param response
     * @param property
     * @return
     */
    private JSONArray getSearchRestuls(JSONObject response, final String property){
        final JSONObject sucess = getSucess(response);
        Assert.assertNotNull(sucess.get("items"));
        final JSONObject items = (JSONObject) sucess.get("items");
        return (JSONArray) items.get(property);
    }
}