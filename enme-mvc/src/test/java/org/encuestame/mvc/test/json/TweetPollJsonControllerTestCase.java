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
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.web.bind.annotation.RequestParam;

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

    /** **/
    private DateTime creationDate = new DateTime();

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
        DateTime dt = creationDate.minusDays(3);

        createDefaultTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, this.userAccount, question3, dt.toDate());

        dt = creationDate.minusDays(2);
        createDefaultTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, this.userAccount, question3, dt.toDate());

        dt = creationDate.minusDays(9);
        createDefaultTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, this.userAccount, question3, dt.toDate());

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
    @Test
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
        Assert.assertEquals(array.size(), 7);

		// //search by keyword // KEYWORD isnt used
		// initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
		// setParameter("typeSearch", "KEYWORD");
		// setParameter("keyword", "a");
		// setParameter("max", "100");
		// setParameter("start", "0");
		// setParameter("period", "7");
		//
		// final JSONObject response2 = callJsonService();
		// final JSONObject sucess2 = getSucess(response2);
		// // Assert.assertNotNull(sucess2.get("tweetPolls"));
		// final JSONArray array2 = (JSONArray) sucess2.get("tweetPolls");
		// Assert.assertEquals(array2.size(), 4);

         //LASTDAY
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
        Assert.assertEquals(array4.size(), 4);


        //LASTWEEK
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "LASTWEEK");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response5= callJsonService();
        final JSONObject sucess5 = getSucess(response5);
        Assert.assertNotNull(sucess5.get("tweetPolls"));
        final JSONArray array5 = (JSONArray) sucess5.get("tweetPolls");
        Assert.assertEquals(array5.size(), 5);

      //SCHEDULED
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "SCHEDULED");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response6= callJsonService();
        final JSONObject sucess6 = getSucess(response6);
        Assert.assertNotNull(sucess6.get("tweetPolls"));
        final JSONArray array6 = (JSONArray) sucess6.get("tweetPolls");
        Assert.assertEquals(array6.size(), 7);


         //WHAT ELSE
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response7= callJsonService();
        final JSONObject sucess7 = getErrors(response7);
        Assert.assertNotNull(sucess7.get("message"));
        final String text = (String) sucess7.get("message");
        Assert.assertEquals(text, "filterTweetPollByItemsByType no type");
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
     * Test Manager Answer: ADD and REMOVE
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testAddManagerAnswer() throws ServletException, IOException{
    	final Long tPollId = this.tp1.getTweetPollId();
    	this.tp1.setPublishTweetPoll(Boolean.FALSE);
    	getTweetPoll().saveOrUpdate(tp1);

        initService("/api/survey/tweetpoll/answer/add.json", MethodJson.GET);
        setParameter("id", tPollId.toString());
        setParameter("answer", "yesyes");
        //setParameter("answerId", "100"); // AnswerId isn`t necessary when the manage type is ADD
        setParameter("shortUrl", "tinyurl");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);

        final JSONObject items = (JSONObject) sucess.get("newAnswer");
        final JSONObject itemsAnswer = (JSONObject) items.get("answer");

        Assert.assertNotNull(items);
        Assert.assertEquals(items.get("tweet_poll_id"), tPollId);

        // Remove Answer.
		final Long answerId = (Long) (itemsAnswer
				.get("answer_id"));
		initService("/api/survey/tweetpoll/remove", MethodJson.DELETE);
    	setParameter("id", answerId.toString());
    	final JSONObject responseRemove = callJsonService();
    	assertSuccessResponse(responseRemove);
    }

	/**
     * Test get short url.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetShortUrl() throws ServletException, IOException{
    	 initService("/api/short/url/tinyurl.json", MethodJson.GET);
         setParameter("url", "http://www.laprensa.com.ni/");
         final JSONObject response = callJsonService();
         final JSONObject sucess = getSucess(response);
         final String items = (String) sucess.get("url");
         Assert.assertNotNull(items);
    }

	/**
	 * Test publish tweetpoll.
	 * @throws ServletException
	 * @throws IOException
	 */
    @Test
	public void testPublishTweetPoll() throws ServletException, IOException {
		final Long tPollId = this.tp1.getTweetPollId();
	//	this.tp1.setPublishTweetPoll(Boolean.FALSE);
		getTweetPoll().saveOrUpdate(tp1);

		initService("/api/survey/tweetpoll/publish.json", MethodJson.POST);
		setParameter("id", tPollId.toString());
//		setParameter("twitterAccounts", tPollId.toString());
//		setParameter("ip", tPollId.toString());
//		setParameter("limitNumbers", tPollId.toString());
//		setParameter("limitVotes", tPollId.toString());
//		setParameter("repeatedNumbers", tPollId.toString());
//		setParameter("scheduled", tPollId.toString());
//		setParameter("scheduledDate", tPollId.toString());
//		setParameter("scheduledTime", tPollId.toString());

		final JSONObject response = callJsonService();
		final JSONObject sucess = getSucess(response);

		final JSONObject items = (JSONObject) sucess.get("socialPublish");
		final JSONObject itemsAnswer = (JSONObject) items.get("textTweeted");
	}

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
   	public void testTweetPollAutosave() throws ServletException, IOException {
   		final Long tPollId = this.tp1.getTweetPollId();
   		this.tp1.setPublishTweetPoll(Boolean.FALSE);
   		getTweetPoll().saveOrUpdate(tp1);
   		initService("/api/survey/tweetpoll/autosave.json", MethodJson.POST);


   		setParameter("tweetPollId", tPollId.toString());
   		setParameter("question", tPollId.toString());
   		setParameter("scheduled", tPollId.toString());
   		setParameter("liveResults", tPollId.toString());
   		setParameter("scheduledTime", tPollId.toString());
   		setParameter("scheduledDate", tPollId.toString());
   		setParameter("captcha", tPollId.toString());
   		setParameter("limitVotes", tPollId.toString());
   		setParameter("followDashBoard", tPollId.toString());
   		setParameter("repeatedVotes", tPollId.toString());
   		setParameter("maxLimitVotes", tPollId.toString());
   		setParameter("maxRepeatedVotes", tPollId.toString());
   		setParameter("resumeLiveResults", tPollId.toString());

		final JSONObject response = callJsonService();
		final JSONObject sucess = getSucess(response);

		final JSONObject items = (JSONObject) sucess.get("tweetPoll");
		final JSONObject itemsAnswer = (JSONObject) items.get("publishPoll");
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