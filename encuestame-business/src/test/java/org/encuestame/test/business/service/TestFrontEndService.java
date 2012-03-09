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

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.encuestame.business.service.FrontEndService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.ProfileRatedTopBean;
import org.encuestame.utils.web.stats.GenericStatsBean;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.encuestame.utils.web.stats.HashTagRankingBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link FrontEndService}.
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
                null, null, null, this.hashTag, this.ipAddress);
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
        Assert.assertEquals("Should be equals", 4, profiles.get(0)
                .getTopValue().intValue());
        Assert.assertEquals("Should be equals", 0, profiles.get(1)
                .getTopValue().intValue());
    }
    
    /**
     * Test Get total usage by hashTag.
     */
    @Test
	public void testGetTotalUsageByHashTag() {
		final Account account = createAccount();
		final HashTag hashtag1 = createHashTag("romantic");
		 
		final Question question = createQuestion("What is your favorite type of movies?", "");
		final Date myDate = new Date();
		// TweetPoll
		final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
		tp.getHashTags().add(hashtag1);
		getTweetPoll().saveOrUpdate(tp);
		
		// Poll
		final Poll poll = createPoll(myDate, question, this.secondary,
				Boolean.TRUE, Boolean.TRUE);
		poll.getHashTags().add(hashtag1);
		getPollDao().saveOrUpdate(poll);

		// Poll 2
		final Question question2 = createQuestion("What is your favorite type of music?", "");
 		final Poll poll2 = createPoll(myDate, question2, this.secondary,
				Boolean.TRUE, Boolean.TRUE);
		poll2.getHashTags().add(hashtag1);
		getPollDao().saveOrUpdate(poll2);

		// Survey
		final Survey mySurvey = createDefaultSurvey(account, "Survey test",
				myDate);
		mySurvey.getHashTags().add(hashtag1);
		getSurveyDaoImp().saveOrUpdate(mySurvey);
		
		// Total usage TweetPoll, Poll and Survey by tagId
		final Long totalUsage = getFrontEndService().getTotalUsageByHashTag(
				hashtag1.getHashTag(), 0, 10, TypeSearchResult.HASHTAG);

        Assert.assertEquals("Should be equals", 4, totalUsage.intValue());
		
	}
    
    /**
     * Test get social network by hash tag.
     */
    @Test
    public void testGetSocialNetworkUseByHashTag(){
    	final HashTag hashtag1 = createHashTag("romantic");
    	final Question question = createQuestion("What is your favorite type of movies?", "");
    	final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
    	tp.getHashTags().add(hashtag1);
    	getTweetPoll().saveOrUpdate(tp);
    	final TweetPoll tp2 = createPublishedTweetPoll(question, this.secondary);
    	tp2.getHashTags().add(hashtag1);
    	getTweetPoll().saveOrUpdate(tp2);
    	
    	///
    	final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tp, " ", socialAccount, tweetContent);
       
        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);
        
        final TweetPollSavedPublishedStatus tpSaved2= createTweetPollSavedPublishedStatus(
        		tp, " ", socialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        getTweetPoll().saveOrUpdate(tpSaved2);
        assertNotNull(tpSaved2);
    	
        final Poll poll1 = createPoll(new Date(), question,
				"DPMU123", this.secondary, Boolean.TRUE, Boolean.TRUE);
		poll1.getHashTags().add(hashtag1); 
		getPollDao().saveOrUpdate(poll1);
        
		final TweetPollSavedPublishedStatus pollSaved1 = createPollSavedPublishedStatus(
				poll1, " ", socialAccount, tweetContent);
		pollSaved1.setApiType(SocialProvider.TWITTER);
		getPollDao().saveOrUpdate(pollSaved1);
		assertNotNull(pollSaved1); 
        
        final Long total = getFrontEndService().getSocialNetworkUseByHashTag(hashtag1.getHashTag(), 0, 10);
        Assert.assertEquals("Should be equals", 3, total.intValue());
    	
    }
    
    /**
     * Test Get hashTag ranking.
     * @throws EnMeNoResultsFoundException 
     */
	@Test
	public void testGetHashTagRanking() throws EnMeNoResultsFoundException {
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
		
		final List<HashTagRankingBean> getFirstHashTag = getFrontEndService()
				.getHashTagRanking("Oceania");
		Assert.assertEquals("Should be equals", 2, getFirstHashTag.size());
    	
		final List<HashTagRankingBean> getMiddleHashTag = getFrontEndService()
				.getHashTagRanking("Asia");
		Assert.assertEquals("Should be equals", 3, getMiddleHashTag.size());
    	
		final List<HashTagRankingBean> getLastHashTag = getFrontEndService()
				.getHashTagRanking("Europa");
		Assert.assertEquals("Should be equals", 2, getLastHashTag.size()); 
	}
	
	
	/**
	 * Test total hashTag used on items voted.
	 */
	@Test
	public void testGetHashTagUsedOnItemsVoted(){
		final HashTag hashtag1 = createHashTag("season");
    	final Question question = createQuestion("What is your favorite season?", "");
    	final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
    	tp.getHashTags().add(hashtag1);
    	getTweetPoll().saveOrUpdate(tp);
    	
    	// Item 2 
    	final Question question2 = createQuestion("What is your favorite holidays?", "");
    	final TweetPoll tp2 = createPublishedTweetPoll(question2, this.secondary);
    	tp2.getHashTags().add(hashtag1);
    	getTweetPoll().saveOrUpdate(tp2);
    	
    	final QuestionAnswer questionsAnswers1 = createQuestionAnswer("yes", question, "7891011");
    	final QuestionAnswer questionsAnswers2 = createQuestionAnswer("no", question, "7891012");
    	
    	final QuestionAnswer questionsAnswers3 = createQuestionAnswer("yes", question2, "11121314");
    	final QuestionAnswer questionsAnswers4 = createQuestionAnswer("no", question2, "11121315");

    	final TweetPollSwitch tpollSwitch1 = createTweetPollSwitch(questionsAnswers1, tp);
    	final TweetPollSwitch tpollSwitch2 = createTweetPollSwitch(questionsAnswers2, tp); 
          
    	final TweetPollSwitch tpollSwitch3 = createTweetPollSwitch(questionsAnswers3, tp2);
    	final TweetPollSwitch tpollSwitch4 = createTweetPollSwitch(questionsAnswers4, tp2); 
    	
    	// TweetPoll 1 votes.
    	createTweetPollResult(tpollSwitch1, "192.168.0.1");
    	createTweetPollResult(tpollSwitch1, "192.168.0.2");
    	createTweetPollResult(tpollSwitch2, "192.168.0.3");
    	createTweetPollResult(tpollSwitch2, "192.168.0.4");
 
    	// TweetPoll 2 votes.
    	createTweetPollResult(tpollSwitch3, "192.168.0.5");
    	createTweetPollResult(tpollSwitch4, "192.168.0.6");
    	
     	final Long totalTweetPollsVoted = getFrontEndService().getHashTagUsedOnItemsVoted(hashtag1.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS);
     	Assert.assertEquals("Should be equals", 6, totalTweetPollsVoted.intValue());  
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
    	
    	final GenericStatsBean genericTweetPollStats = getFrontEndService().retrieveGenericStats(tpoll.getTweetPollId().toString(), TypeSearchResult.TWEETPOLL);
    	Assert.assertNotNull(genericTweetPollStats);  
    	 
    	final GenericStatsBean genericPollStats = getFrontEndService().retrieveGenericStats(poll.getPollId().toString(), TypeSearchResult.POLL);
    	Assert.assertNotNull(genericPollStats);  
    	
    	//final GenericStatsBean genericSurveyStats = getFrontEndService().retrieveGenericStats(survey.getSid(), TypeSearchResult.SURVEY);
    	//Assert.assertNotNull(genericSurveyStats);  
	}
	
	@Test
	public void testGetTotalHashTagHitsbyDateRange() throws EnMeNoResultsFoundException{
		final Question question = createQuestion("What is your favorite type of song?", "");
		final HashTag tag = createHashTag("romantic");
		final Calendar myDate = Calendar.getInstance();
		// TweetPoll
    	final TweetPoll tpoll = createPublishedTweetPoll(5L, question, getSpringSecurityLoggedUserAccount()); 
      	tpoll.getHashTags().add(tag);
    	getTweetPoll().saveOrUpdate(tpoll); 
    	
    	final TweetPoll tpoll2 = createPublishedTweetPoll(5L, question, getSpringSecurityLoggedUserAccount()); 
     	tpoll2.getHashTags().add(tag);
    	getTweetPoll().saveOrUpdate(tpoll2); 
    	
    	myDate.add(Calendar.MONTH, -2);
    	
    	final TweetPoll tpoll3 = createPublishedTweetPoll(6L, question, getSpringSecurityLoggedUserAccount()); 
    	tpoll3.getHashTags().add(tag);
    	tpoll3.setCreateDate(myDate.getTime());
    	getTweetPoll().saveOrUpdate(tpoll3);
    	
    	myDate.add(Calendar.MONTH, -4);
    	final TweetPoll tpoll4 = createPublishedTweetPoll(6L, question, getSpringSecurityLoggedUserAccount()); 
    	tpoll4.getHashTags().add(tag);
    	tpoll4.setCreateDate(myDate.getTime());
    	getTweetPoll().saveOrUpdate(tpoll4); 
    	 
    	// Polls
    	
    	final Poll poll1 = createPoll(myDate.getTime(), question, getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
    	poll1.getHashTags().add(tag);
    	getPollDao().saveOrUpdate(poll1);
    	
    	final Poll poll2 = createPoll(new Date(), question, getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
    	poll2.getHashTags().add(tag);
    	getPollDao().saveOrUpdate(poll2);
    	
    	// Surveys
    	
    	final Survey survey = createDefaultSurvey(getSpringSecurityLoggedUserAccount().getAccount());
    	survey.getHashTags().add(tag); 
    	survey.setCreatedAt(new Date());
    	getSurveyDaoImp().saveOrUpdate(survey);
    	
    	
    	 final Survey survey2 = createDefaultSurvey(getSpringSecurityLoggedUserAccount().getAccount());
    	survey2.getHashTags().add(tag);
    	survey2.setCreatedAt(new Date());
    	getSurveyDaoImp().saveOrUpdate(survey2); 
    	
    	final Survey survey3 = createDefaultSurvey(getSpringSecurityLoggedUserAccount().getAccount());
    	survey3.getHashTags().add(tag);
    	survey3.setCreatedAt(myDate.getTime());
    	getSurveyDaoImp().saveOrUpdate(survey3); 
    	 
    	
    	 final List<HashTagDetailStats> stats = getFrontEndService().getTotalUsagebyHashTagAndDateRange(tag.getHashTag(), 365, 0, 20); 
    	 for (HashTagDetailStats hashTagDetailStats : stats) {
    		 System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n ");
    		 System.out.println("Label ---> " + hashTagDetailStats.getLabel() + "      Value ---> " +hashTagDetailStats.getValue());  
    	 } 
    	 Assert.assertEquals("Should be equals", 3, stats.size());  
	}
	
	
	@Test
	public void getTweetPollSocialNetworkLinksbyTagAndDateRange() {
		final Calendar calendarDate = Calendar.getInstance();
		final HashTag hashtag1 = createHashTag("romantic");
		final Question question = createQuestion(
				"What is your favorite hobbie?", "");
		// TweetPoll 1
		final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
		tp.getHashTags().add(hashtag1);
		getTweetPoll().saveOrUpdate(tp);
		
		// TweetPoll 2
		final TweetPoll tp2 = createPublishedTweetPoll(question, this.secondary);
		tp2.getHashTags().add(hashtag1);
		getTweetPoll().saveOrUpdate(tp2);
	
		
		// /
		final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
		assertNotNull(socialAccount);
		final String tweetContent = "Tweet content text";
		
		final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
				tp, " ", socialAccount, tweetContent);

		tpSaved.setApiType(SocialProvider.TWITTER);
		tpSaved.setPublicationDateTweet(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved);
		assertNotNull(tpSaved);

		calendarDate.add(Calendar.MONTH, -2);

		final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
				tp, " ", socialAccount, tweetContent);
		tpSaved2.setApiType(SocialProvider.FACEBOOK);
		tpSaved2.setPublicationDateTweet(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved2);
		assertNotNull(tpSaved2);
		
	
		// TweetPoll 3
		//calendarDate.add(Calendar.MONTH, -1);
		final TweetPoll tp3 = createPublishedTweetPoll(question, this.secondary);
		tp3.getHashTags().add(hashtag1);
		tp3.setCreateDate(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tp3);
		
		
	
		calendarDate.add(Calendar.MONTH, -2);
		final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
				tp3, " ", socialAccount, tweetContent);
		tpSaved3.setApiType(SocialProvider.FACEBOOK);
		tpSaved3.setPublicationDateTweet(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved3);
		assertNotNull(tpSaved3);
		
		// TweetPoll 4
		final TweetPoll tp4 = createPublishedTweetPoll(question, this.secondary);
		tp4.getHashTags().add(hashtag1);
		tp4.setCreateDate(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tp4);
		
	
		calendarDate.add(Calendar.MONTH, -1);
		final TweetPollSavedPublishedStatus tpSaved4 = createTweetPollSavedPublishedStatus(
				tp4, " ", socialAccount, tweetContent);
		tpSaved4.setApiType(SocialProvider.FACEBOOK);
		tpSaved4.setPublicationDateTweet(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved4);
		assertNotNull(tpSaved4);
		
		final TweetPollSavedPublishedStatus tpSaved5 = createTweetPollSavedPublishedStatus(
				tp2, " ", socialAccount, tweetContent);
		tpSaved5.setApiType(SocialProvider.FACEBOOK);
		tpSaved5.setPublicationDateTweet(calendarDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved5);
		assertNotNull(tpSaved5);
  
		final List<HashTagDetailStats> totalSocialLinksUsagebyHashTagAndTweetPoll = getFrontEndService()
				.getTweetPollSocialNetworkLinksbyTagAndDateRange(
						hashtag1.getHashTag(), this.INIT_RESULTS,
						this.MAX_RESULTS, TypeSearchResult.TWEETPOLL, 365);
		 
//		 for (HashTagDetailStats hashTagDetailStats : total) {
//		  System.out.println("Label : " + hashTagDetailStats.getLabel() +
//		 "-----   Value: " + hashTagDetailStats.getValue());
//	  }

	 Assert.assertEquals("Should be equals", 3, totalSocialLinksUsagebyHashTagAndTweetPoll.size());

	}
	
	/**
	 * 
	 */
	public void testSearchItemsByTweetPoll(){
		
	}
	
	public void testSearchItemsBySurvey(){
		
	}
	
	public void testSearchItemsByPoll(){
		
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
