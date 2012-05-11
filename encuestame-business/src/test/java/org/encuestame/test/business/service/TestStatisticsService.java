/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
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
import java.util.Locale;

import junit.framework.Assert;

import org.encuestame.core.service.imp.IStatisticsService;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
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
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext; 
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test Statistics Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2012
 * @version $Id$
 */
public class TestStatisticsService extends AbstractSpringSecurityContext{
		
	/** {@link UserAccount}. **/
	private UserAccount secondary; 
	
	/** {@link IStatisticsService} **/
	@Autowired
	private IStatisticsService statisticsService; 
    
    /** **/
    private Question initQuestion;
    
    /** **/
    private HashTag initHashTag;
    
    /** **/
    private TweetPoll initTweetPoll;
    
    /** **/
    private TweetPollSwitch initTweetPollSwicht;
    
    /** **/
    private TweetPollSwitch secondTweetPollSwitch;
    
    /** **/
    private SocialAccount initSocialAccount;
    
    /** **/
    private Calendar pollingDate = Calendar.getInstance();
    
    
    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;
    
    
    /**
     * 
     */
    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount()); 
        this.initQuestion = createQuestion("Who will win  the champions league 2012?", "");
        this.initHashTag = createHashTag("futboll");
    	final QuestionAnswer answerChelsea = createQuestionAnswer("Chelsea", initQuestion, "123457");
    	final QuestionAnswer answerBayern = createQuestionAnswer("Bayern", initQuestion, "123469"); 
    	
        this.initTweetPoll = createPublishedTweetPoll(5L, initQuestion,
				getSpringSecurityLoggedUserAccount());
        initTweetPoll.getHashTags().add(initHashTag);
		getTweetPoll().saveOrUpdate(initTweetPoll);
		this.initTweetPollSwicht = createTweetPollSwitch(answerBayern, initTweetPoll);
		this.secondTweetPollSwitch = createTweetPollSwitch(answerChelsea, initTweetPoll);
		createTweetPollResult(initTweetPollSwicht, "192.168.0.1");
		createTweetPollResult(initTweetPollSwicht, "192.168.0.2");
		this.initSocialAccount = createDefaultSettedSocialAccount(this.secondary);
		
		request = new MockHttpServletRequest();
		request.addPreferredLocale(Locale.ENGLISH);  
    }
     
	/**
	 * 
	 * @throws EnMeNoResultsFoundException
	 * @throws EnMeSearchException 
	 */
	@Test
	public void testGetTotalHashTagHitsbyDateRange() throws EnMeNoResultsFoundException, EnMeSearchException{
		final Question question = createQuestion("What is your favorite type of song?", "");
		final HashTag tag = createHashTag("romantic");
		final Calendar myDate = Calendar.getInstance();
		// TweetPoll
		final TweetPoll tpoll = createPublishedTweetPoll(5L, question,
				getSpringSecurityLoggedUserAccount());
		tpoll.getHashTags().add(tag);
		getTweetPoll().saveOrUpdate(tpoll); 
		
		final TweetPoll tpoll2 = createPublishedTweetPoll(5L, question,
				getSpringSecurityLoggedUserAccount());
		tpoll2.getHashTags().add(tag);
		getTweetPoll().saveOrUpdate(tpoll2); 
		myDate.add(Calendar.MONTH, -2);

		final TweetPoll tpoll3 = createPublishedTweetPoll(6L, question,
				getSpringSecurityLoggedUserAccount());
		tpoll3.getHashTags().add(tag);
		tpoll3.setCreateDate(myDate.getTime());
		getTweetPoll().saveOrUpdate(tpoll3); 
		myDate.add(Calendar.MONTH, -4);
		
		final TweetPoll tpoll4 = createPublishedTweetPoll(6L, question,
				getSpringSecurityLoggedUserAccount());
		tpoll4.getHashTags().add(tag);
		tpoll4.setCreateDate(myDate.getTime());
		getTweetPoll().saveOrUpdate(tpoll4);

		// Polls

		final Poll poll1 = createPoll(myDate.getTime(), question,
				getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
				Boolean.TRUE);
		poll1.getHashTags().add(tag);
		getPollDao().saveOrUpdate(poll1);
		 
		final Poll poll2 = createPoll(new Date(), question,
				getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
				Boolean.TRUE);
		poll2.getHashTags().add(tag);
		getPollDao().saveOrUpdate(poll2); 
		myDate.add(Calendar.MONTH, -10);
		
		// Out of range
		final Poll poll3 = createPoll(myDate.getTime(), question,
				getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
				Boolean.TRUE);
		poll3.getHashTags().add(tag);
		getPollDao().saveOrUpdate(poll3);
		 
		
		// Surveys

		final Survey survey = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
				.getAccount());
		survey.getHashTags().add(tag);
		survey.setCreatedAt(new Date());
		getSurveyDaoImp().saveOrUpdate(survey); 
		final Survey survey2 = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
				.getAccount());
		survey2.getHashTags().add(tag);
		survey2.setCreatedAt(new Date());
		getSurveyDaoImp().saveOrUpdate(survey2); 
		
		final Survey survey3 = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
				.getAccount());
		survey3.getHashTags().add(tag);
		survey3.setCreatedAt(myDate.getTime());
		getSurveyDaoImp().saveOrUpdate(survey3); 

		myDate.add(Calendar.MONTH, +6);
		final Survey survey4 = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
					.getAccount());
		survey4.getHashTags().add(tag);
		survey4.setCreatedAt(myDate.getTime());
		getSurveyDaoImp().saveOrUpdate(survey4); 
			  
		final List<HashTagDetailStats> stats = getStatisticsService()
				.getTotalUsagebyHashTagAndDateRange(tag.getHashTag(), 365, this.request);
		 
		/*for (HashTagDetailStats hashTagDetailStats : stats) {
			System.out
					.println(" ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n ");
			System.out.println("Label ---> " + hashTagDetailStats.getLabel()
					+ "      Value ---> " + hashTagDetailStats.getValue() + "      SubLabel ---> " + hashTagDetailStats.getSubLabel());
		}*/
		Assert.assertEquals("Should be equals", 4, stats.size());
	}

	/**
	 * 
	 */
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
		// calendarDate.add(Calendar.MONTH, -1);
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

	/*	final List<HashTagDetailStats> totalSocialLinksUsagebyHashTagAndTweetPoll = getStatisticsService()
				.getTweetPollSocialNetworkLinksbyTagAndDateRange(
						hashtag1.getHashTag(), this.INIT_RESULTS,
						this.MAX_RESULTS, TypeSearchResult.TWEETPOLL, 365);*/

		// for (HashTagDetailStats hashTagDetailStats : total) {
		// System.out.println("Label : " + hashTagDetailStats.getLabel() +
		// "-----   Value: " + hashTagDetailStats.getValue());
		// }

		// 	Assert.assertEquals("Should be equals", 3,
		// 			totalSocialLinksUsagebyHashTagAndTweetPoll.size());

	}
	
	/**
	 * Test 
	 * @throws EnMeSearchException 
	 */
	//@Test
	public void testGetTotalVotesbyHashTagUsageAndDateRange() throws EnMeSearchException{
		final Calendar pollingDate = Calendar.getInstance();
		final Question question2 = createQuestion("Who will win  the spain league 2012?", ""); 
		final QuestionAnswer answerMadrid = createQuestionAnswer("Real Madrid", question2, "98765");
		final QuestionAnswer answerBarsa = createQuestionAnswer("Barcelon", question2, "765432"); 
		final TweetPoll tweetpoll2 = createPublishedTweetPoll(5L, question2,
				getSpringSecurityLoggedUserAccount());
		tweetpoll2.getHashTags().add(this.initHashTag);
		getTweetPoll().saveOrUpdate(initTweetPoll);
		final TweetPollSwitch tpSwichtMadrid = createTweetPollSwitch(answerMadrid, tweetpoll2);
		final TweetPollSwitch tpSwichtBarsa = createTweetPollSwitch(answerBarsa, tweetpoll2);
		createTweetPollResult(tpSwichtMadrid, "192.168.0.1");
	    createTweetPollResult(tpSwichtBarsa, "192.168.0.4");  
	    
	    pollingDate.add(Calendar.MONTH, -3);
	    createTweetPollResultWithPollingDate(tpSwichtMadrid, "192.168.0.5", pollingDate.getTime());  
	    pollingDate.add(Calendar.MONTH, -2);
	    createTweetPollResultWithPollingDate(tpSwichtBarsa, "192.168.0.6", pollingDate.getTime());  
	    final List<HashTagDetailStats> itemStatListbyYear = getStatisticsService()
	     			.getTotalVotesbyHashTagUsageAndDateRange(
	       					this.initHashTag.getHashTag(), 365, this.request);  
	    Assert.assertEquals("Should be equals", 3,
	    		itemStatListbyYear.size());  
	} 
	
	/**
	 * Test
	 * @throws EnMeSearchException 
	 * @throws EnMeNoResultsFoundException 
	 */ 
	//@Test 
	public void testGetTotalHitsUsagebyHashTagAndDateRange() throws EnMeNoResultsFoundException, EnMeSearchException{
		final Calendar myDate = Calendar.getInstance();
    	final HashTag hashTag1 = createHashTag("software2");  
    	 
    	final Hit hit1 = createHashTagHit(hashTag1, "192.168.1.1");
    	final Hit hit2 = createHashTagHit(hashTag1, "192.168.1.2");
    	 
    	hit1.setHitDate(myDate.getTime());
    	getTweetPoll().saveOrUpdate(hit1); 
     
    	myDate.add(Calendar.DATE, -4);
    	hit2.setHitDate(myDate.getTime());
    	getTweetPoll().saveOrUpdate(hit2); 
     
    	
    	final List<HashTagDetailStats> tagHitsDetailList = getStatisticsService().getTotalHitsUsagebyHashTagAndDateRange(hashTag1.getHashTag(), 7, this.request);
    	Assert.assertEquals("Should be equals", 2, tagHitsDetailList.size());  
		
	}
	 
	/**
	 * Test
	 * @throws EnMeSearchException
	 */
	//@Test
	public void testGetTotalVotesbyHashTagUsageByMonthDateRange()
			throws EnMeSearchException {

		pollingDate.add(Calendar.DATE, -2);

		createTweetPollResultWithPollingDate(this.initTweetPollSwicht,
				"192.168.0.11", pollingDate.getTime());
		pollingDate.add(Calendar.DATE, -5);

		createTweetPollResultWithPollingDate(this.initTweetPollSwicht,
				"192.168.0.12", pollingDate.getTime());
		pollingDate.add(Calendar.DATE, -8);

		createTweetPollResultWithPollingDate(this.initTweetPollSwicht,
				"192.168.0.13", pollingDate.getTime());
		createTweetPollResultWithPollingDate(this.secondTweetPollSwitch,
				"192.168.0.14", pollingDate.getTime());

		pollingDate.add(Calendar.DATE, -10);
		createTweetPollResultWithPollingDate(this.secondTweetPollSwitch,
				"192.168.0.15", pollingDate.getTime());

		final List<HashTagDetailStats> itemStatListbyMonth = getStatisticsService()
				.getTotalVotesbyHashTagUsageAndDateRange(
						this.initHashTag.getHashTag(), 30, this.request);
		Assert.assertEquals("Should be equals", 7, itemStatListbyMonth.size());
	}
	
	/**
	 * Test
	 * @throws EnMeSearchException 
	 */
	//@Test
	public void testGetTotalSocialLinksbyHashTagUsageByYearDateRange() throws EnMeSearchException { 
		final String tweetContent = "Tweet content text"; 
		final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent); 
		tpSaved.setApiType(SocialProvider.TWITTER);
		tpSaved.setPublicationDateTweet(pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved); 
		
		// TweetPoll 2 
		
		final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
		tpSaved2.setApiType(SocialProvider.FACEBOOK);
		this.pollingDate.add(Calendar.MONTH, -3);
		tpSaved2.setPublicationDateTweet(this.pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved2); 
		
		final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
		tpSaved3.setApiType(SocialProvider.GOOGLE_BUZZ);
		// Out of range.
		this.pollingDate.add(Calendar.MONTH, -15);
		tpSaved3.setPublicationDateTweet(this.pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved3);
		
		final List<HashTagDetailStats> detailStatsByYear = getStatisticsService().getTotalSocialLinksbyHashTagUsageAndDateRange(this.initHashTag.getHashTag(), 365, this.request);
		Assert.assertEquals("Should be equals", 7, detailStatsByYear.size());  
	}
	
	/**
	 * 
	 * @throws EnMeSearchException
	 */
	//@Test
	public void testGetTotalSocialLinksbyHashTagUsageByWeekDateRange() throws EnMeSearchException {
		
		final String tweetContent = "social content text"; 
		final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent); 
		tpSaved.setApiType(SocialProvider.TWITTER);
		tpSaved.setPublicationDateTweet(pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved);  
		
		// TweetPoll 2 
		
		final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
		tpSaved2.setApiType(SocialProvider.FACEBOOK);
		this.pollingDate.add(Calendar.DATE, -3);
		tpSaved2.setPublicationDateTweet(this.pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved2);   
		
		final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
		tpSaved3.setApiType(SocialProvider.GOOGLE_BUZZ);
		// Out of range.
		this.pollingDate.add(Calendar.DATE, -1);
		tpSaved3.setPublicationDateTweet(this.pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved3); 
		
		final TweetPollSavedPublishedStatus tpSaved4 = createTweetPollSavedPublishedStatus(
				this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
		tpSaved4.setApiType(SocialProvider.LINKEDIN); 
		tpSaved4.setPublicationDateTweet(this.pollingDate.getTime());
		getTweetPoll().saveOrUpdate(tpSaved4);  
		
		final List<HashTagDetailStats> detailStatsByWeek = getStatisticsService()
				.getTotalSocialLinksbyHashTagUsageAndDateRange(
						this.initHashTag.getHashTag(), 7, this.request);
		Assert.assertEquals("Should be equals", 8, detailStatsByWeek.size());
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
        //final Long totalUsage = getStatisticsService().getTotalUsageByHashTag(
        //       hashtag1.getHashTag(), 0, 10, TypeSearchResult.HASHTAG);

        // Assert.assertEquals("Should be equals", 4, totalUsage.intValue());

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

        // final Long total = getStatisticsService().getSocialNetworkUseByHashTag(hashtag1.getHashTag(), 0, 10);
        // Assert.assertEquals("Should be equals", 3, total.intValue());

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

        //  final Long totalTweetPollsVoted = getFrontEndService().getHashTagUsedOnItemsVoted(hashtag1.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS);
        // Assert.assertEquals("Should be equals", 6, totalTweetPollsVoted.intValue());
    }

    
	/**
	 * @return the statisticsService
	 */
	public IStatisticsService getStatisticsService() {
		return statisticsService;
	}

	/**
	 * @param statisticsService the statisticsService to set
	 */
	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	} 
}
