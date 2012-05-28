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
package org.encuestame.test.business.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.ProfileRatedTopBean;
import org.encuestame.utils.web.stats.GenericStatsBean;
import org.encuestame.utils.web.stats.HashTagRankingBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test for {@link FrontEndCoreService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 12, 2011
 */
public class TestFrontEndService extends AbstractSpringSecurityContext{

    @Autowired
    private IFrontEndService frontEndService;

    /** {@link HashTag} **/
    private HashTag hashTag;

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** ip address. **/
    final String ipAddress = "192.168.1.1";

    /** {@link TweetPoll}. **/
    private TweetPoll tweetPoll;

    /** **/
    private Integer INIT_RESULTS= 0;

    /** **/
    private Integer MAX_RESULTS= 10;
    
    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;  

    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        this.hashTag = createHashTag("hardware",50L);
        createHashTagHit(hashTag, this.ipAddress);
        final Question question = createQuestion("Who I am?", "");
        createQuestionAnswer("yes", question, "12345");
        createQuestionAnswer("no", question, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question);
        final HashTag hashTag2 = createHashTag("programmer",80L);
        this.tweetPoll.getHashTags().add(hashTag);
        this.tweetPoll.getHashTags().add(hashTag2);
        getTweetPoll().saveOrUpdate(this.tweetPoll);
        
    	request = new MockHttpServletRequest();
		request.addPreferredLocale(Locale.ENGLISH);
	       
    }

    /**
     * Test check previous hashtag hits.
     */
    @Test
    public void testCheckPreviousHashTagHit(){
        final String ipAddress2 = "192.168.1.2";
        flushIndexes();
        final Boolean previousRecord = getFrontEndService().checkPreviousHit(this.ipAddress,
                this.hashTag.getHashTagId(),
                TypeSearchResult.HASHTAG);
        Assert.assertTrue(previousRecord);
        final Boolean previousRecord2 = getFrontEndService().checkPreviousHit(
                ipAddress2,
                this.hashTag.getHashTagId(), TypeSearchResult.HASHTAG);
        Assert.assertFalse(previousRecord2);
    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testRegisterHashTagHit() throws EnMeNoResultsFoundException{
        final Boolean registerHit = getFrontEndService().registerHit(
                null, null, null, this.hashTag, this.ipAddress, HitCategory.VISIT);
        Assert.assertTrue(registerHit);
    }

    /**
     * Test Get hash tags
     */
    @Test
    public void testGetHashTags(){

        /** Hash Tags **/
        final HashTag hashTag1 = createHashTag("software",50L);
        final HashTag hashTag2 = createHashTag("holidays",70L);
        final HashTag hashTag3 = createHashTag("futboll",80L);
        final HashTag hashTag4 = createHashTag("championsLeague",90L);
        final HashTag hashTag5 = createHashTag("copaAmerica",150L);

        /** Question 2 **/
        final Question question2 = createQuestion("Question 1", "");
        createQuestionAnswer("yes", question2, "12345");
        createQuestionAnswer("no", question2, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question2);

        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag2);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        /** Question 3 **/
        final Question question3 = createQuestion("Question 2", "");
        createQuestionAnswer("yes", question3, "12345");
        createQuestionAnswer("no", question3, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question3);

        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag2);
        this.tweetPoll.getHashTags().add(hashTag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        /** Question 4 **/
        final Question question4 = createQuestion("Question 3", "");
        createQuestionAnswer("yes", question4, "12345");
        createQuestionAnswer("no", question4, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question4);

        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag4);
        this.tweetPoll.getHashTags().add(hashTag5);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        /** Question 5 **/
        final Question question5 = createQuestion("Question 4", "");
        createQuestionAnswer("yes", question5, "12345");
        createQuestionAnswer("no", question5, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question5);

        this.tweetPoll.getHashTags().add(hashTag4);
        this.tweetPoll.getHashTags().add(hashTag5);
        this.tweetPoll.getHashTags().add(hashTag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        final Question question6 = createQuestion("Question 5", "");
        createQuestionAnswer("yes", question6, "12345");
        createQuestionAnswer("no", question6, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question6);

        this.tweetPoll.getHashTags().add(hashTag3);
        this.tweetPoll.getHashTags().add(hashTag4);
        this.tweetPoll.getHashTags().add(hashTag5);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        final List<HashTagBean> hashBean = getFrontEndService().getHashTags(30, 0, "");
        Assert.assertEquals("Should be equals", hashBean.size(), 7);
    }

    /**
     * Test vote and register access rate.
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    @Test
    public void testRegisterAccessRateVotedLike() throws EnMeNoResultsFoundException, EnMeExpcetion{
         final Question question = createQuestion("Who are you?", "");
         final TweetPoll tp = createPublishedTweetPoll(getSpringSecurityLoggedUserAccount().getAccount(), question);
         final String ipAddress = "192.168.1.81";
         flushIndexes();
         // I like it vote.
         final AccessRate rate = getFrontEndService().registerAccessRate(
                 TypeSearchResult.TWEETPOLL,
                 tp.getTweetPollId(),
                 ipAddress,
                 Boolean.TRUE);
         Assert.assertNotNull(rate);

         // I like it vote again.
         String ipAddress2 = "192.168.1.82";
         final AccessRate rate2 = getFrontEndService().registerAccessRate(
                 TypeSearchResult.TWEETPOLL,
                 tp.getTweetPollId(),
                 ipAddress2,
                 Boolean.TRUE);
         Assert.assertNotNull(rate2);

         // I don't like it vote.
         final AccessRate rate3 = getFrontEndService().registerAccessRate(
                 TypeSearchResult.TWEETPOLL,
                 tp.getTweetPollId(),
                 ipAddress,
                 Boolean.FALSE);
         Assert.assertNotNull(rate3);
    }

    /**
     * Test get user rated top.
     * @throws EnMeNoResultsFoundException
     */
	@Test
	public void testGetUsersRatedTop() throws EnMeNoResultsFoundException {
		final Question question = createQuestion("Who are you ?", "");
		final Question question2 = createQuestion(
				"What is your favorite month of the year", "");
		final Question question3 = createQuestion(
				"What is your favorite quote", "");
		final Question question4 = createQuestion(
				"What is your marital status?", "");
		final Date myDate = new Date();
		createPublishedTweetPoll(question, this.secondary);
		createPublishedTweetPoll(question2, this.secondary);
		createPoll(myDate, question3, this.secondary, Boolean.TRUE,
				Boolean.TRUE);
		createPoll(myDate, question4, this.secondary, Boolean.TRUE,
				Boolean.TRUE);
		final List<ProfileRatedTopBean> profiles = getFrontEndService()
				.getTopRatedProfile(Boolean.TRUE);
		Assert.assertEquals("Should be equals", 2, profiles.size());
	}
 

    /**
     * Test Get hashTag ranking.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetHashTagRanking() throws EnMeNoResultsFoundException { 
        final Calendar myCalDate = Calendar.getInstance();
        
        final HashTag tag = createHashTag("America", 20L);
        final HashTag tag1 = createHashTag("Europa", 30L);
        final HashTag tag2 = createHashTag("Asia", 40L);
        final HashTag tag3 = createHashTag("Oceania", 20L);
        final HashTag tag4 = createHashTag("Africa", 60L);
        final HashTag tag5 = createHashTag("Australia", 55L);
        final HashTag tag6 = createHashTag("Tailandia", 12L);
        final HashTag tag7 = createHashTag("Caribe", 5L);
        final HashTag tag8 = createHashTag("Antartic", 10L);
        final HashTag tag9 = createHashTag("Mediterraneo", 70L);
        final HashTag tag10 = createHashTag("Balcanes", 80L);
        final HashTag tag11 = createHashTag("China", 42L);
        
        createHashTagRank(tag11, myCalDate.getTime(), (double) 97); // China -- 11
        createHashTagRank(tag3, myCalDate.getTime(), (double) 90); // Oceania -- 0
        createHashTagRank(tag4, myCalDate.getTime(), (double) 70); // Africa -- 1 
        createHashTagRank(tag7, myCalDate.getTime(), (double) 58); // Caribe -- 7  
        createHashTagRank(tag1, myCalDate.getTime(), (double) 40); // Europa --4
        createHashTagRank(tag9, myCalDate.getTime(), (double) 38); // Mediterraneo -- 9 
        createHashTagRank(tag2, myCalDate.getTime(), (double) 30); // Asia -- 2
        createHashTagRank(tag5, myCalDate.getTime(), (double) 25); // Australia -- 5  
        createHashTagRank(tag10, myCalDate.getTime(), (double) 16); // Balcanes -- 10
        createHashTagRank(tag, myCalDate.getTime(), (double) 14); // America -- 3   
        createHashTagRank(tag6, myCalDate.getTime(), (double) 12); // Tailandia -- 6 
        createHashTagRank(tag8, myCalDate.getTime(), (double) 10); // Antartic -- 8 
        
        myCalDate.add(Calendar.DATE, -1);
        
        createHashTagRank(tag8, myCalDate.getTime(), (double) 80); //Antartic -- 0 
        createHashTagRank(tag11, myCalDate.getTime(), (double) 68); // China -- 1
        createHashTagRank(tag, myCalDate.getTime(), (double) 56); // America -- 2
        createHashTagRank(tag9, myCalDate.getTime(), (double) 55); // Mediterraneo -- 3 
        createHashTagRank(tag7, myCalDate.getTime(), (double) 39); // Caribe -- 4  
        createHashTagRank(tag4, myCalDate.getTime(), (double) 34); // Africa --5
        createHashTagRank(tag10, myCalDate.getTime(), (double) 31); // Balcanes --6
        createHashTagRank(tag6, myCalDate.getTime(), (double) 28); // Tailandia -- 7 
        createHashTagRank(tag3, myCalDate.getTime(), (double) 25); // Oceania -- 8
        createHashTagRank(tag1, myCalDate.getTime(), (double) 20); // Europa -- 9 
        createHashTagRank(tag2, myCalDate.getTime(), (double) 12); // Asia -- 10 
        createHashTagRank(tag5, myCalDate.getTime(), (double) 10); // Australia -- 11    
 
        final List<HashTagRankingBean> getFirstHashTag = getFrontEndService()
                .getHashTagRanking(tag11.getHashTag());  
        Assert.assertEquals("Should be equals", 2, getFirstHashTag.size());

        final List<HashTagRankingBean> getMiddleHashTag = getFrontEndService()
                .getHashTagRanking(tag9.getHashTag());
        Assert.assertEquals("Should be equals", 3, getMiddleHashTag.size());

        final List<HashTagRankingBean> getLastHashTag = getFrontEndService()
                .getHashTagRanking(tag8.getHashTag());
        Assert.assertEquals("Should be equals", 2, getLastHashTag.size()); 
        
    }



    /**
     * Test Generic data stats.
     * @throws EnMeNoResultsFoundException
     */
	@Test
    public void testGetGenericStats() throws EnMeNoResultsFoundException{
        final Question question = createQuestion("What is your favorite type of song?", "");
        // TweetPoll
        final TweetPoll tpoll = createPublishedTweetPoll(5L, question, getSpringSecurityLoggedUserAccount());
        // Poll
        final Poll poll = createPoll(new Date(), question, "JCPM", getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
        // Survey
        final Survey survey = createDefaultSurvey(getSpringSecurityLoggedUserAccount().getAccount(), "Technology survey", new Date());

		final GenericStatsBean genericTweetPollStats = getFrontEndService()
				.retrieveGenericStats(tpoll.getTweetPollId().toString(),
						TypeSearchResult.TWEETPOLL, this.request);
        Assert.assertNotNull(genericTweetPollStats);

		final GenericStatsBean genericPollStats = getFrontEndService()
				.retrieveGenericStats(poll.getPollId().toString(),
						TypeSearchResult.POLL, this.request);
        Assert.assertNotNull(genericPollStats);

        
        final HashTag hashtag = createHashTag("Continents", 350L); 
	    System.out.println(hashtag.getHashTag());    
		@SuppressWarnings("unused")
		final GenericStatsBean genericHashTagStats = getFrontEndService()
				.retrieveGenericStats(hashtag.getHashTag(),
						TypeSearchResult.HASHTAG, this.request);
      
        //final GenericStatsBean genericSurveyStats = getFrontEndService().retrieveGenericStats(survey.getSid(), TypeSearchResult.SURVEY);
        //Assert.assertNotNull(genericSurveyStats);
	} 

    /**
    * @return the frontEndService
    */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
    * @param frontEndService the frontEndService to set
    */
    public void setFrontEndService(IFrontEndService frontEndService) {
        this.frontEndService = frontEndService;
    }
}
