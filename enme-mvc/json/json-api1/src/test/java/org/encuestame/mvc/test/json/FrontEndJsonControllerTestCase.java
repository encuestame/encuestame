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
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.v1.HashTagsJsonController;
import org.encuestame.mvc.test.config.AbstractJsonV1MvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.enums.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * {@link HashTagsJsonController} Test Case.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 13, 2011
 */
@Category(DefaultTest.class)
public class FrontEndJsonControllerTestCase extends AbstractJsonV1MvcUnitBeans{


    @Before
    public void startFrontEnd(){

    }

    /**
     * Get list directory.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetDirectory() throws ServletException, IOException {
        initService("/api/common/gadgets/directory/list.json", MethodJson.GET);
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("gadgets");
        Assert.assertNotNull(items);
        final JSONObject gadget = (JSONObject) items.get(0);
        final String gadgetId = (String) gadget.get("id");
        //add gadgets to dashboard
        initService("/api/common/" + gadgetId + "/gadget.json", MethodJson.POST);

        final JSONObject response2 = callJsonService();
        final JSONObject success2 = getSucess(response2);
        //System.out.println(success2);
        final JSONObject gadgetJson = (JSONObject) success2.get("gadget");
        Assert.assertEquals("1", gadgetJson.get("gadget_position").toString());
        Assert.assertEquals("1", gadgetJson.get("gadget_column").toString());
    }



    /**
     * Test Get frontend items.
     * @throws IOException
     * @throws ServletException
     *
     */
    @Test
    public void testGetFrontendItems() throws ServletException, IOException{
        final Question question = createQuestion("abcdefg", "pattern");
        final TweetPoll tp = createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), getSpringSecurityLoggedUserAccount(), question);
        final Poll poll = createPoll(new Date(), question, getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
        tp.setRelevance(50L);
        poll.setRelevance(30L);
        getTweetPoll().saveOrUpdate(tp);
        getPollDao().saveOrUpdate(poll);
        initService("/api/common/frontend/stream.json", MethodJson.GET);
        setParameter("period", "all");
        setParameter("maxResults", "10");
        setParameter("start", "0");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("frontendItems");
        Assert.assertNotNull(items);
        Assert.assertEquals(items.size(), 2);
    }

    /**
     * Test get users top rated.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testgetUserRatedTop() throws ServletException, IOException {
        final Question question = createQuestion("abcdefg", "pattern");
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(),
                getSpringSecurityLoggedUserAccount(), question);
        createPoll(new Date(), question, getSpringSecurityLoggedUserAccount(),
                Boolean.TRUE, Boolean.TRUE);
        initService("/api/common/frontend/topusers.json", MethodJson.GET);
        setParameter("status", "1");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray items = (JSONArray) success.get("profile");
        Assert.assertNotNull(items);
        Assert.assertEquals(items.size(), 1);
    }


    @Test
    public void testVote() throws ServletException, IOException {
        final Question question = createQuestion("question test 1", "pattern");
        TweetPoll tp = createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(),
                getSpringSecurityLoggedUserAccount(), question);
        createPoll(new Date(), question, getSpringSecurityLoggedUserAccount(),
                Boolean.TRUE, Boolean.TRUE);
        // active
        initService("/api/frontend/home/tweetpoll/vote", MethodJson.PUT);
        setParameter("id", tp.getTweetPollId().toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final String status = (String) success.get("status");
        Assert.assertEquals(status, Status.ACTIVE.name());
        // inactive
        initService("/api/frontend/home/tweetpoll/vote", MethodJson.PUT);
        setParameter("id", tp.getTweetPollId().toString());
        final JSONObject response2 = callJsonService();
        final JSONObject success2 = getSucess(response2);
        final String status2 = (String) success2.get("status");
        Assert.assertEquals(status2, Status.INACTIVE.name());
        // active
        initService("/api/frontend/home/tweetpoll/vote", MethodJson.PUT);
        setParameter("id", tp.getTweetPollId().toString());
        final JSONObject response3 = callJsonService();
        final JSONObject success3 = getSucess(response3);
        final String status3 = (String) success3.get("status");
        Assert.assertEquals(status3, Status.ACTIVE.name());
        //error
        initService("/api/frontend/home/tweetpoll/vote", MethodJson.PUT);
        setParameter("id", "0");
        final JSONObject response4 = callJsonService();
        final JSONObject success4 = getSucess(response4);
        final String status4 = (String) success4.get("status");
        Assert.assertEquals(status4, Status.FAILED.name());
    }
}