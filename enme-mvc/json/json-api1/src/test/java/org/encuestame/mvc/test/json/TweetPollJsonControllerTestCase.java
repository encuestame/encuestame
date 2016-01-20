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
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.mvc.controller.json.v1.survey.TweetPollJsonController;
import org.encuestame.mvc.test.config.AbstractJsonV1MvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.social.SocialProvider;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test for {@link TweetPollJsonController}.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
@Category(DefaultTest.class)
public class TweetPollJsonControllerTestCase extends AbstractJsonV1MvcUnitBeans{


    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /**
     * {@link TweetPoll}.
     */
    private TweetPoll tp1;

    /** **/
    private DateTime creationDate = new DateTime();

    private Question initQuestion;

    /**
     * Init.
     */
    @Before
    public void initJsonService(){
        this.userAccount = getSpringSecurityLoggedUserAccount();
        this.initQuestion = createQuestion("Bayern  VS Borussia?", userAccount.getAccount());
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
        Assert.assertEquals(array6.size(), 1);

         //WHAT ELSE
        initService("/api/survey/tweetpoll/search.json", MethodJson.GET);
        setParameter("typeSearch", "");
        setParameter("max", "100");
        setParameter("start", "0");
        final JSONObject response7= callJsonService();
        final JSONObject sucess7 = getErrors(response7);
      //  Assert.assertNotNull(sucess7.get("message"));
        final String text = (String) sucess7.get("message");
      //  Assert.assertEquals(text, "filterTweetPollByItemsByType no type");
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
        initService("/api/survey/tweetpoll/" + this.tp1.getTweetPollId(), MethodJson.DELETE);
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
        final TweetPoll tpollAfter = getTweetPoll().getTweetPollById(tpollBefore.getTweetPollId());
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
        final JSONObject newAnswer = (JSONObject) sucess.get("newAnswer");
        final JSONObject answer = (JSONObject) newAnswer.get("answer");
        logPrint("items::"+answer.toJSONString());
        Assert.assertNotNull(answer);
        Assert.assertEquals(newAnswer.get("tweet_poll_id"), tPollId);
        // Remove Answer.
        final Long answerId = (Long) (answer.get("answer_id"));
        initService("/api/survey/tweetpoll/answer/remove.json", MethodJson.GET);
        setParameter("id", tPollId.toString());
        setParameter("answerId", answerId.toString());
        final JSONObject responseRemove = callJsonService();
        logPrint(responseRemove);
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
        String questionString = "Real Madrid VS Barcelona? _ " +  RandomStringUtils.randomAlphabetic(7);
        final Question question = createQuestion(questionString, userAccount.getAccount());
        final TweetPoll tp1 = createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), userAccount, question);

        final Long tPollId = tp1.getTweetPollId();
        final SocialAccount social1 = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
        this.tp1.setPublishTweetPoll(Boolean.FALSE);
        getTweetPoll().saveOrUpdate(tp1);
        initService("/api/survey/tweetpoll/publish", MethodJson.POST);
        setParameter("id", tPollId.toString());
        setParameter("twitterAccounts", social1.getId().toString());
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        final JSONArray items = (JSONArray) sucess.get("socialPublish");
        Assert.assertNotNull(items);
        Assert.assertEquals(items.size(), 1);
        final JSONObject itemsAnswer = (JSONObject) items.get(0);
        Assert.assertEquals(itemsAnswer.get("textTweeted").toString(), questionString);
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
       public void testTweetPollAutosave() throws ServletException, IOException {
           // step - 1: create tweetpoll
           String questionString = "Real Madrid VS Barcelona? _ " +  RandomStringUtils.randomAlphabetic(7);
           tp1.setPublishTweetPoll(Boolean.FALSE);
           getTweetPoll().saveOrUpdate(tp1);
           initService("/api/survey/tweetpoll/autosave", MethodJson.POST);
           //setParameter("tweetPollId", tp1.getTweetPollId().toString());
           setParameter("question", questionString);
           setParameter("scheduled", "false");
           setParameter("liveResults", "false");
           setParameter("captcha", "false");
           setParameter("limitVotes", "false"); 
           setParameter("repeatedVotes", "false");
           setParameter("maxLimitVotes", "100");
           setParameter("maxRepeatedVotes", "10");
           setParameter("resumeLiveResults", "false");
           final JSONObject response = callJsonService();
           final JSONObject sucess = getSucess(response);
           final JSONObject tweetpoll = (JSONObject) sucess.get("tweetPoll");
           Assert.assertNotNull(tweetpoll);
           final Long id = (Long) tweetpoll.get("id");
           Assert.assertNotNull(id);
           final JSONObject question = (JSONObject) tweetpoll.get("question");
           Assert.assertEquals(question.get("question_name"), questionString);
           // step 2 : update the question
           initService("/api/survey/tweetpoll/autosave", MethodJson.POST);
           setParameter("tweetPollId", id.toString());
           setParameter("question", questionString+"_change");
           final JSONObject response2 = callJsonService();
           final JSONObject sucess2 = getSucess(response2);
           final JSONObject tweetpoll2 = (JSONObject) sucess2.get("tweetPoll");
           Assert.assertNotNull(tweetpoll2);
           final JSONObject question2 = (JSONObject) tweetpoll2.get("question");
           Assert.assertEquals(question2.get("question_name"), questionString+"_change");
           // step 3: change some properties
            initService("/api/survey/tweetpoll/autosave", MethodJson.POST);
            setParameter("tweetPollId", id.toString());
            setParameter("question", questionString);
            setParameter("repeatedVotes", "true");
            setParameter("maxLimitVotes", "100");
            setParameter("maxRepeatedVotes", "1000");
            final JSONObject response3 = callJsonService();
            final JSONObject sucess3 = getSucess(response3);
            final JSONObject tweetpoll3 = (JSONObject) sucess3.get("tweetPoll");
            Assert.assertNotNull(tweetpoll3);
            final JSONObject question3 = (JSONObject) tweetpoll3.get("question");
            Assert.assertEquals(question3.get("question_name"), questionString);
            final Long maxRepeatedVotes = (Long) tweetpoll3.get("maxRepeatedVotes");
            final Long limitVotes = (Long) tweetpoll3.get("limitVotes");
            Assert.assertEquals(maxRepeatedVotes.longValue(), 1000);
            Assert.assertEquals(limitVotes.longValue(), 100);

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