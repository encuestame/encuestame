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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.FrontEndDao;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test {@link FrontEndDao}..
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since January 06, 2011
 */
@Category(DefaultTest.class)
public class TestFrontEndDao extends AbstractBase {

    /** {@link HashTag} **/
    private HashTag hashTag;

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** {@link Hit} **/
    private Hit hit;

    final String ipAddress = "192.168.1.1";

    /** **/
	private DateTime initDate = new DateTime();

	/** {@link Question} **/
	private Question initQuestion;

    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        this.hashTag = createHashTag("software");
        final String ipAddress2 = "192.168.1.2";
        final String ipAddress3 = "192.168.1.3";
        this.hit = createHashTagHit(hashTag, ipAddress, this.initDate.toDate());
        createHashTagHit(hashTag, ipAddress2,  this.initDate.minusDays(3).toDate());
        createHashTagHit(hashTag, ipAddress3, this.initDate.minusDays(4).toDate());

        this.initQuestion = createDefaultQuestion("Question example");
    }

    /** Test Get hash tags by ip.**/
   @Test
    public void testGetHashTagsHitByIp() {
        assertNotNull(this.hit);
        flushIndexes();
        final List<Hit> hitsbyIp = getFrontEndDao().getHitsByIpAndType(
                this.ipAddress, this.hashTag.getHashTagId(),
                TypeSearchResult.HASHTAG);
        assertNotNull(hitsbyIp);
        assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(),
                this.ipAddress);
        assertEquals("Should be equals", hitsbyIp.size(), 1);
    }

   @Test
   public void testGetHitsByIpandType(){
       assertNotNull(this.hashTag);
       flushIndexes();
       final List<Hit> hitsbyIp = getFrontEndDao().getHitsByIpAndType(
               ipAddress,
               this.hashTag.getHashTagId(),
               TypeSearchResult.HASHTAG);
       assertNotNull(hitsbyIp);
       assertEquals("Should be equals", hitsbyIp.get(0).getIpAddress(), this.ipAddress);
       final Long totalHits = getFrontEndDao().getTotalHitsbyType(hashTag.getHashTagId(), TypeSearchResult.HASHTAG, null);
       assertEquals("total hits should be equals", 3, totalHits.intValue());
   }

    /**
     * Test get access rateby item.
     */
    @Test
    public void testGetAccessRatebyItem() {
        this.secondary = createUserAccount("jhon", createAccount());
        final Question question = createQuestion("question1", secondary.getAccount());
        final TweetPoll tweet = createPublishedTweetPoll(
                secondary, question, Calendar.getInstance()
                        .getTime());
        final String ipAddress = "192.168.1.19";
        createTweetPollRate(Boolean.TRUE, tweet, ipAddress);
        flushIndexes();
        final List<AccessRate> tpRate = getFrontEndDao().getAccessRatebyItem(
                ipAddress, tweet.getTweetPollId(), TypeSearchResult.TWEETPOLL);
        assertNotNull(tpRate);
        assertEquals("Should be equals", 1, tpRate.size());
    }

    /**
     *
     */
    public void testGetLinksByHomeItem() {
        final TweetPoll tp1 = createTweetPoll(12345L, true, true, true, true, false, null, new Date(),
                true, this.secondary,
                createQuestion("test tp1", this.secondary.getAccount()),
                this.secondary);
        final TweetPoll tp2 = createTweetPoll(162345L, true, true, true, true, false, null, new Date(),
                true, this.secondary,
                createQuestion("test tp2", this.secondary.getAccount()),
                this.secondary);
        tp1.getHashTags().add(this.hashTag);
        getTweetPoll().saveOrUpdate(tp1);
        tp2.getHashTags().add(this.hashTag);
        getTweetPoll().saveOrUpdate(tp2);
        createTweetPollSavedPublishedStatus(tp1, "432432532", null, "test tweettxt dad");
        createTweetPollSavedPublishedStatus(tp2, "43243sa2532", null, "test tweettxt fdsc");
        createTweetPollSavedPublishedStatus(tp1, "4324a1232532", null, "test tweettxt cz xc");
        createTweetPollSavedPublishedStatus(tp2, "432d123432532", null, "test tweettxt c cxz");
    }


    /**
     * Retrieve {@link TweetPollSavedPublishedStatus} (Links ) by {@link HashTag}
     */
    @Test
    public void testGetLinksByHomeItemHashtag(){
    	this.testGetLinksByHomeItem();
    	// TODO: Include Polls to searchlist with hashtag
    	 List<TweetPollSavedPublishedStatus> links = getFrontEndDao()
                 .getLinksByHomeItem(this.hashTag, null, null, null, null,
                         TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME, 0, 100);
         assertEquals("Should be equals", 4, links.size());
    }

    /**
     * Retrieve {@link TweetPollSavedPublishedStatus} (Links ) by {@link TweetPoll}
     */
    @Test
    public void testGetLinksByHomeItemTweetPoll(){
    	  final TweetPoll tp1 = createTweetPoll(12345L, true, true, true, true, false, null, new Date(),
                  true, this.secondary,
                  createQuestion("test tp1", this.secondary.getAccount()),
                  this.secondary);

		tp1.getHashTags().add(this.hashTag);
		getTweetPoll().saveOrUpdate(tp1);
		createTweetPollSavedPublishedStatus(tp1, "432432532", null,
				"test tweettxt dad");
		createTweetPollSavedPublishedStatus(tp1, "432222532", null,
				"test testTweet dad");
		createTweetPollSavedPublishedStatus(tp1, "553322532", null,
				"testTweettxt test dad");


		List<TweetPollSavedPublishedStatus> links = getFrontEndDao()
				.getLinksByHomeItem(this.hashTag, null, tp1, null, null,
						TypeSearchResult.TWEETPOLL, SearchPeriods.ALLTIME, 0,
						100);

         assertEquals("Should be equals", 3, links.size());
    }

    /**
     * Retrieve {@link TweetPollSavedPublishedStatus} (Links ) by {@link Poll}  */
    @Test
    public void testGetLinksByHomeItemPoll(){
    	Question q1 = createDefaultQuestion("Where will the next JJOO");
    	Question q2 = createDefaultQuestion("What is the city with the best quality of life");
    	final Poll poll1 = createDefaultPoll(q1, this.secondary, this.initDate.minusHours(5).toDate());
    	final Poll poll2 = createDefaultPoll(q2, this.secondary, this.initDate.minusHours(5).toDate());

    	createPollSavedPublishedStatus(poll1, "553882532", null,
				"testTweettxt99 test dad");
    	createPollSavedPublishedStatus(poll2, "553332532", null,
				"testTweettxt89 test dad");

     	 List<TweetPollSavedPublishedStatus> links = getFrontEndDao()
                 .getLinksByHomeItem(this.hashTag, null, null, null, poll1,
                         TypeSearchResult.POLL, SearchPeriods.ALLTIME, 0, 100);
         assertEquals("Should be equals", 1, links.size());
    }

    /**
     * Retrieve {@link TweetPollSavedPublishedStatus} (Links ) by {@link Survey}
     */
    @Test
    public void testGetLinksByHomeItemSurvey(){
    	final Survey survey1 = createDefaultSurvey(this.secondary.getAccount(), "survey test", new Date());

    	createSurveySavedPublishedStatus(survey1, "553882532", null,
				"testTweettxt99 test dad");

     	 List<TweetPollSavedPublishedStatus> links = getFrontEndDao()
                 .getLinksByHomeItem(this.hashTag, null, null, survey1, null,
                         TypeSearchResult.SURVEY, SearchPeriods.ALLTIME, 0, 100);
         assertEquals("Should be equals", 1, links.size());
    }


    /**
     * Get total hashTagHits by range.
     */
    @Test
    public void testGetTotalHashTagHitsbyDateRange(){
        final Calendar myDate = Calendar.getInstance();
        final HashTag hashTag1 = createHashTag("software2");

        final String ipAddress1 = "192.168.1.1";
        final String ipAddress2 = "192.168.1.2";

        final Hit hit1 = createHashTagHit(hashTag1, ipAddress1);
        final Hit hit2 = createHashTagHit(hashTag1, ipAddress2);

        hit1.setHitDate(myDate.getTime());
        getTweetPoll().saveOrUpdate(hit1);

        // It created today, setted with minus 5 days. so the new date is between friday or Saturday ago
        // out of range
        myDate.add(Calendar.DATE, -9);
        hit2.setHitDate(myDate.getTime());
        getTweetPoll().saveOrUpdate(hit2);

        // Retrieve hits for tag Id in the last 7 days.
        List<Hit> myHits = getFrontEndDao().getHashTagHitsbyDateRange( hashTag1.getHashTagId(), 7);
        assertEquals("Should be equals", 1, myHits.size());
    }

    /**
	 * Test Retrieve surveys the last 30 days.
	 */
	@Test
	public void testGetSurveyFrontEndLast30Days() {
		this.createSurveys();
		final List<Survey> last30DaysSurveys = getFrontEndDao()
				.getSurveyFrontEndLast30Days(0, 10);
		assertEquals("Should be equals", 9, last30DaysSurveys.size());
	}

	/**
	 * Test Retrieve surveys the last 7 days.
	 */
	@Test
	public void testGetSurveyFrontEndLast7Days() {
		this.createSurveys();
		final List<Survey> last7DaysSurveys = getFrontEndDao().getSurveyFrontEndLast7Days(0, 10);
		assertEquals("Should be equals", 6, last7DaysSurveys.size());
	}

	/**
	 *  Test Retrieve surveys the last 24 hours.
	 */
	@Test
	public void testGetSurveyFrontEndLast24() {
		this.createSurveys();
		final List<Survey> last24Surveys = getFrontEndDao().getSurveyFrontEndLast24(0, 10);
		assertEquals("Should be equals", 2, last24Surveys.size());

	}

	/**
	 * Create survey Helper.
	 */
	private void createSurveys(){
		createDefaultSurvey(this.secondary.getAccount(), "First survey", this.initDate.minusHours(5).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "Second survey", this.initDate.minusHours(15).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "Third survey", this.initDate.minusHours(25).toDate());


		createDefaultSurvey(this.secondary.getAccount(), "Fourth survey", this.initDate.minusDays(3).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "5 survey", this.initDate.minusDays(5).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "6 survey", this.initDate.minusDays(8).toDate());


		createDefaultSurvey(this.secondary.getAccount(), "7 survey", this.initDate.minusDays(3).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "8 survey", this.initDate.minusDays(15).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "9 survey", this.initDate.minusDays(25).toDate());
		createDefaultSurvey(this.secondary.getAccount(), "10 survey", this.initDate.minusDays(35).toDate());
	}

	/**
	 *Create poll Helper.
	 */
	private void createPolls(){
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusHours(5).toDate());

		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusHours(5).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusHours(15).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusHours(25).toDate());


		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(3).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(5).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(8).toDate());


		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(3).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(15).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(25).toDate());
		createDefaultPoll(this.initQuestion, this.secondary, this.initDate.minusDays(35).toDate());
  	}

	/**
	 *Create poll Helper.
	 */
	private void createTweetPolls(){
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusHours(5).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusHours(5).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusHours(15).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusHours(25).toDate());

		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(3).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(5).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(8).toDate());

		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(3).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(15).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(25).toDate());
		createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(35).toDate());
	}

	/**
	 * Test Retrieve poll the last 30 days.
	 */
	@Test
    public void testGetPollFrontEndLast30Days(){
    	this.createPolls();
    	final List<Poll> last30DaysPoll = getFrontEndDao().getPollFrontEndLast30Days(0, 10);
    	assertEquals("Should be equals", 10, last30DaysPoll.size());
    }

	/**
	 * Test Retrieve poll the last 7 days.
	 */
	@Test
    public void testGetPollFrontEndLast7Days(){
    	this.createPolls();
    	final List<Poll> last7DaysPoll = getFrontEndDao().getPollFrontEndLast7Days(0, 10);
    	assertEquals("Should be equals", 7, last7DaysPoll.size());
    }

	/**
	 * Test Retrieve poll the last 24 hours.
	 */
	@Test
    public void testGetPollFrontEndLast24(){
    	this.createPolls();
    	final List<Poll> last24HoursPoll = getFrontEndDao().getPollFrontEndLast24(0, 10);
    	assertEquals("Should be equals", 3, last24HoursPoll.size());
	}

	/**
	 * Test Retrieve Tweetpoll the last 30 Days.
	 */
	@Test
    public void testGetTweetPollFrontEndLast30Days(){
    	this.createTweetPolls();
    	final List<TweetPoll> last30DaysTweetPoll = getFrontEndDao().getTweetPollFrontEndLast30Days(0, 10);
    	assertEquals("Should be equals", 10, last30DaysTweetPoll.size());
    }

	/**
	 * Test Retrieve Tweetpoll the last 7 Days.
	 */
	@Test
    public void testGetTweetPollFrontEndLast7Days(){
    	this.createTweetPolls();
    	final List<TweetPoll> last7DaysTweetPoll = getFrontEndDao().getTweetPollFrontEndLast7Days(0, 10);
    	assertEquals("Should be equals", 7, last7DaysTweetPoll.size());

    }

	/**
	 * Test Retrieve Tweetpoll the last 24 Hours.
	 */
	@Test
    public void testGetTweetPollFrontEndLast24(){
    	this.createTweetPolls();
    	final List<TweetPoll> last24HoursTweetPoll = getFrontEndDao().getTweetPollFrontEndLast24(0, 10);
    	assertEquals("Should be equals", 3, last24HoursTweetPoll.size());
    }

	/**
	 * Test Retrieve HashTag hits by visit.
	 */
	@Test
	public void testGetHashTagHitsRange() {
		final List<Hit> tagHits = getFrontEndDao().getHashTagHitsRange(this.hashTag.getHashTagId(), SearchPeriods.ALLTIME);
		assertEquals("Should be equals", 3, tagHits.size());
	}


	/**
	 * Test Retrieve all {@link Hit} by type.
	 */
	@Test
	public void testGetAllHitsByType() {
		final TweetPoll tpoll1 = createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusHours(5).toDate());
		final TweetPoll tpoll2 = createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(3).toDate());
		final TweetPoll tpoll3 =createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(15).toDate());
  		final TweetPoll tpoll4 = createPublishedTweetPoll(this.secondary, this.initQuestion, this.initDate.minusDays(35).toDate());

		createTweetPollHit(tpoll1, "192.168.1.4");
		createTweetPollHit(tpoll2, "192.168.1.4");
		createTweetPollHit(tpoll3, "192.168.1.6");
		createTweetPollHit(tpoll3, "192.168.1.7");
		createTweetPollHit(tpoll3, "192.168.1.8");
		createTweetPollHit(tpoll4, "192.168.1.9");
		createTweetPollHit(tpoll4, "192.168.1.10");
		createTweetPollHit(tpoll4, "192.168.1.11");
		createTweetPollHit(tpoll4, "192.168.1.12");

		final List<Hit> allHits = getFrontEndDao().getAllHitsByType(tpoll4, null, null);
		assertEquals("Should be equals", 4, allHits.size());

		final List<Hit> allHits2 = getFrontEndDao().getAllHitsByType(tpoll2, null, null);
		assertEquals("Should be equals", 1, allHits2.size());

		final List<Hit> allHits3 = getFrontEndDao().getAllHitsByType(tpoll1, null, null);
		assertEquals("Should be equals", 1, allHits3.size());
	}
}
