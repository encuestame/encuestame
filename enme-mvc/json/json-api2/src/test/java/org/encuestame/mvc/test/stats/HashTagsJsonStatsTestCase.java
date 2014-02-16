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
package org.encuestame.mvc.test.stats;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.v1.statistics.HashTagStatsJsonController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * {@link HashTagStatsJsonController} Test Case.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 09, 2012.
 */
@Category(DefaultTest.class)
public class HashTagsJsonStatsTestCase extends AbstractJsonMvcUnitBeans {

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    //@Test
    public void testGetHashTagButtonStats() throws ServletException, IOException{
        UserAccount userAcc= getSpringSecurityLoggedUserAccount();
        //1-  Create Tag, Question and TweetPoll
        final Date creationDate = new Date();

        /* HashTag **/
        final HashTag hashtag1 = createHashTag("romantic");

        /* Question **/
        final Question question = createQuestion("What is your favorite type of movies?", "");

        /* TweetPoll **/
        final TweetPoll myTweetPoll = createPublishedTweetPoll(question, userAcc);

        /* Poll **/
        final Poll myPoll = createPoll(creationDate, question, userAcc, Boolean.TRUE, Boolean.TRUE);

        /* Survey **/
        final Survey mySurvey = createDefaultSurvey(userAcc.getAccount(), "My First Encuestame Survey", creationDate);

        // 2- Add HashTag to TweetPoll, Poll or Survey
        myTweetPoll.getHashTags().add(hashtag1);
        getTweetPoll().saveOrUpdate(myTweetPoll);

        myPoll.getHashTags().add(hashtag1);
        getPollDao().saveOrUpdate(myPoll);

        mySurvey.getHashTags().add(hashtag1);
        getSurveyDaoImp().saveOrUpdate(mySurvey);

        // 3- Create TweetPollSavedPublishedStatus -- Create Social network link.

        //Total Usage by Social Networks
        final SocialAccount socialAccount = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                myTweetPoll, " ", socialAccount, tweetContent);

        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);


        // Total Usaged by HashTag (TweetPoll, Poll or Survey)
        // Total Usage by HashTag(Hits)
        createHashTagHit(hashtag1, "192.1.1.1");
        createHashTagHit(hashtag1, "192.1.1.2");
        createHashTagHit(hashtag1, "192.1.1.3");
        createHashTagHit(hashtag1, "192.1.1.4");
        createHashTagHit(hashtag1, "192.1.1.5");

        final Long totalHitsbyTag = getFrontEndDao().getTotalHitsbyType(hashtag1.getHashTagId(),
                    TypeSearchResult.HASHTAG, null);
        Assert.assertEquals("Should be equals ", totalHitsbyTag.intValue(), 5);

        // 4- Get total Usage by tweetpoll voted.

          final QuestionAnswer questionsAnswers1 = createQuestionAnswer("yes", question, "7891011");
          final QuestionAnswer questionsAnswers2 = createQuestionAnswer("no", question, "7891012");

          final TweetPollSwitch tpollSwitch1 = createTweetPollSwitch(questionsAnswers1, myTweetPoll);
        final TweetPollSwitch tpollSwitch2 = createTweetPollSwitch(questionsAnswers2, myTweetPoll);

        createTweetPollResult(tpollSwitch1, "192.168.0.1");
        createTweetPollResult(tpollSwitch1, "192.168.0.2");
        createTweetPollResult(tpollSwitch2, "192.168.0.3");
        createTweetPollResult(tpollSwitch2, "192.168.0.4");

        // 2- Call Json service
        initService("/api/common/hashtags/stats/button.json", MethodJson.GET);
        setParameter("tagName", "romantic");
        setParameter("filter", "HASHTAGRATED");
        setParameter("limit", "30");

        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONObject buttonStats = (JSONObject) success.get("hashTagButtonStats");
       
        final JSONObject usage = (JSONObject) buttonStats.get("usage_by_item");
        final JSONObject social = (JSONObject) buttonStats.get("total_usage_by_social_network");
        final JSONObject visits = (JSONObject) buttonStats.get("total_hits");
        final JSONObject votes = (JSONObject) buttonStats.get("usage_by_votes");
          
        log.debug("Showing stats usage" +  usage.get("value").toString());
        Assert.assertEquals(usage.get("value").toString(), "3");

        log.debug("Showing stats usage by social network links" +  social.get("value").toString());
        Assert.assertEquals(social.get("value").toString(), "1");

        log.debug("Showing stats usage by item(HashTag)" +  visits.get("value").toString());
        Assert.assertEquals(visits.get("value").toString(), "5");

        log.debug("Showing stats usage by item(HashTag)" +  usage.get("value").toString());
        Assert.assertEquals(votes.get("value").toString(), "4");
    }

    /**
     * Test HashTag ranking stats.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGetHashTagRankingStats() throws ServletException, IOException{
        final Date myDate = new Date();
        final HashTag tag = createHashTag("America", 20L);
        final HashTag tag1 = createHashTag("Europa", 20L);
        final HashTag tag2 = createHashTag("Asia", 20L);
        final HashTag tag3 = createHashTag("Oceania", 20L);
        final HashTag tag4 = createHashTag("Africa", 20L);

        createHashTagRank(tag3, myDate, (double) 90); // Oceania -- 0
        createHashTagRank(tag4, myDate, (double) 70); // Africa -- 1
        createHashTagRank(tag2, myDate, (double) 30); // Asia -- 2
        createHashTagRank(tag, myDate, (double) 20); // America -- 3
        createHashTagRank(tag1, myDate, (double) 10); // Europa --4

        // Call json service.
        initService("/api/common/hashtags/stats/ranking.json", MethodJson.GET);
        setParameter("tagName", "asia");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray hashTagsRanking2 = (JSONArray) success.get("hashTagRankingStats");
        Assert.assertEquals(hashTagsRanking2.size(), 3);
    }

    /**
     * Test retrieve generic stats.
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testRetrieveGenericStats() throws ServletException, IOException{
        final Question question = createQuestion("Black or White?", "");
        final TweetPoll tweetpoll = createPublishedTweetPoll(5L, question, getSpringSecurityLoggedUserAccount());
        // TWEETPOLL
        initService("/api/common/stats/generic.json", MethodJson.GET);
        setParameter("id", tweetpoll.getTweetPollId().toString());
        setParameter("filter", "TWEETPOLL");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONObject genericStatsData = (JSONObject) success.get("generic");
        Assert.assertEquals(genericStatsData.get("hits").toString(), tweetpoll.getHits().toString());

        // HASHTAG
        final HashTag hashtag = createHashTag("software");
        hashtag.setHits(20L);
        getHashTagDao().saveOrUpdate(hashtag);
        //System.out.println("HASHTAG HITS --->" + hashtag.getHits());
        initService("/api/common/stats/generic.json", MethodJson.GET);
        setParameter("id", hashtag.getHashTag());
        setParameter("filter", "HASHTAG");
        final JSONObject responseHashTag = callJsonService();
        final JSONObject successHashTag = getSucess(responseHashTag);
        final JSONObject genericHashStatsData = (JSONObject) successHashTag.get("generic");

        Assert.assertEquals(genericHashStatsData.get("hits").toString(), hashtag.getHits().toString());  
    }
    
    /**
     * 
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testHashTagStatByRange() throws ServletException, IOException{
    	final Calendar myDate = Calendar.getInstance();
    	final HashTag hashTag1 = createHashTag("software2");  
    	 
    	final Hit hit1 = createHashTagHit(hashTag1, "192.168.1.1");
    	final Hit hit2 = createHashTagHit(hashTag1, "192.168.1.2");
    	 
    	hit1.setHitDate(myDate.getTime());
    	getTweetPoll().saveOrUpdate(hit1); 
     
    	myDate.add(Calendar.DATE, -4);
    	hit2.setHitDate(myDate.getTime());
    	getTweetPoll().saveOrUpdate(hit2); 
    	
    	initService("/api/common/hashtags/stats/button/range.json", MethodJson.GET);
		setParameter("tagName", hashTag1.getHashTag());
		setParameter("period", "7");
		setParameter("filter", "HITS");
		final JSONObject response = callJsonService();
		final JSONObject success = getSucess(response);
		final JSONArray hashTagHitsData = (JSONArray) success.get("statsByRange"); 
		Assert.assertEquals(hashTagHitsData.size(), 2);   
    }  
}
