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
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.survey.TweetPollJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test for {@link TweetPollJsonController}.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
@Category(DefaultTest.class)
public class TweetPollJsonControllerTestCase extends AbstractJsonMvcUnitBeans{


    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /**
     * {@link TweetPoll}.
     */
    private TweetPoll tp1;

    /**
     * Init.
     */
    @Before
    public void initJsonService(){
        this.userAccount = getSpringSecurityLoggedUserAccount();
        final Question question = createQuestion("Real Madrid VS Barcelona?", userAccount.getAccount());
        final Question question1 = createQuestion("Real Madrid or Barcelona?", userAccount.getAccount());
        final Question question2 = createQuestion("Real Madrid or Barcelona?", userAccount.getAccount());
        final Question question3 = createQuestion("Real Madrid or Barcelona?", userAccount.getAccount());
        this.tp1 = createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question1);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question2);
        createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question3);
        this.tp1.setFavourites(true);
        final Calendar ca = Calendar.getInstance();
        ca.add(Calendar.WEEK_OF_YEAR, -1);
        this.tp1.setCreateDate(ca.getTime());
        this.tp1.setScheduleTweetPoll(true);
        getAccountDao().saveOrUpdate(this.tp1);
    }

    /**
     * Test /api/survey/tweetpoll/search.json.
     * @throws ServletException
     * @throws IOException
     */
    //@Test
    public void testTweetPollJsonService() throws ServletException, IOException{
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "ALL");
        setParameter("keyword", "a");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertNotNull(sucess.get("tweetPolls"));
        final JSONArray array = (JSONArray) sucess.get("tweetPolls");

        Assert.assertEquals(array.size(), 4);
        //search by keyword
     initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "KEYWORD");
        setParameter("keyword", "a");
        setParameter("max", "100");
        setParameter("start", "0");
        setParameter("period", "7");

        final JSONObject response2 = callJsonService();
        final JSONObject sucess2 = getSucess(response2);
        Assert.assertNotNull(sucess2.get("tweetPolls"));
        final JSONArray array2 = (JSONArray) sucess2.get("tweetPolls");
        Assert.assertEquals(array2.size(), 4);
        System.out.println("Json Array Controller 2 --> " + array2.size());
        /*  //LASTDAY
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "LASTDAY");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response3 = callJsonService();
        final JSONObject sucess3 = getSucess(response3);
        Assert.assertNotNull(sucess3.get("tweetPolls"));
        final JSONArray array3 = (JSONArray) sucess3.get("tweetPolls");
        Assert.assertEquals(array3.size(), 3);
        //FAVOURITES
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "FAVOURITES");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response4= callJsonService();
        final JSONObject sucess4 = getSucess(response4);
        Assert.assertNotNull(sucess4.get("tweetPolls"));
        final JSONArray array4 = (JSONArray) sucess4.get("tweetPolls");
        Assert.assertEquals(array4.size(), 1);
        //LASTWEEK
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "LASTWEEK");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response5= callJsonService();
        final JSONObject sucess5 = getSucess(response5);
        Assert.assertNotNull(sucess5.get("tweetPolls"));
        final JSONArray array5 = (JSONArray) sucess5.get("tweetPolls");
        Assert.assertEquals(array5.size(), 0);
        //SCHEDULED
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "SCHEDULED");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response6= callJsonService();
        final JSONObject sucess6 = getSucess(response6);
        Assert.assertNotNull(sucess6.get("tweetPolls"));
        final JSONArray array6 = (JSONArray) sucess6.get("tweetPolls");
        Assert.assertEquals(array6.size(), 1);
        //WHAT ELSE
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response7= callJsonService();
        System.out.println(response7);
        final JSONObject sucess7 = getErrors(response7);
        Assert.assertNotNull(sucess7.get("message"));
       final String text = (String) sucess7.get("message");
        Assert.assertEquals(text, "filterTweetPollByItemsByType no type");*/
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
	@Test
	public void testRemoveTweetPollJsonService() throws ServletException,
			IOException {
		final TweetPoll tpollBefore = this.tp1;
		initService("/api/survey/tweetpoll/remove", MethodJson.DELETE);
		setParameter("id", this.tp1.getTweetPollId().toString());

		final JSONObject response = callJsonService();
		assertSuccessResponse(response);
		final TweetPoll tpollAfter = getTweetPoll().getTweetPollById(
				tpollBefore.getTweetPollId());
		Assert.assertNull(tpollAfter);
	}

    /**
     *
     * @param type
     * @throws IOException
     * @throws ServletException
     */
    private void testTweetPollProperties(final String type) throws ServletException, IOException{
         initService("/api/survey/tweetpoll/"+type+"-tweetpoll.json", MethodJson.GET);
         setParameter("tweetPollId", this.tp1.getTweetPollId().toString());
         final JSONObject response = callJsonService();
         assertSuccessResponse(response);
    }


    /**
     * Test /api/survey/tweetpoll/{propertyType}-tweetpoll.json.
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testchangeTweetPollProperties() throws ServletException, IOException{
        testTweetPollProperties("resumeliveResults");
        testTweetPollProperties("change-open-status");
        testTweetPollProperties("captcha");
        testTweetPollProperties("favourite");
        testTweetPollProperties("liveResults");
        testTweetPollProperties("notification");
        testTweetPollProperties("repeated");
        testTweetPollProperties("");
    }
}