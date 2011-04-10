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
import org.encuestame.mvc.controller.json.survey.TweetPollJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.security.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link TweetPollJsonController}.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
public class TweetPollJsonControllerTestCase extends AbstractJsonMvcUnitBeans{


    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /**
     * Init.
     */
    @Before
    public void initJsonService(){
        this.userAccount = createUserAccount("jota", createAccount(true));
        createFakesTweetPoll(this.userAccount);
    }

    /**
     * Test /api/survey/tweetpoll/search.json.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testTweetPollJsonService() throws ServletException, IOException{
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "ALL");
        setParameter("keyword", "a");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response = callJsonService();
        System.out.println(response);
        final JSONObject sucess = getSucess(response);
        Assert.assertNotNull(sucess.get("tweetPolls"));
        final JSONArray array = (JSONArray) sucess.get("tweetPolls");
        Assert.assertEquals(array.size(), 0);
    }
}
