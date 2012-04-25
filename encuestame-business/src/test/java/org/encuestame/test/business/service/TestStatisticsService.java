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

import junit.framework.Assert;
 
import org.encuestame.core.service.imp.IStatisticsService;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    private Integer INIT_RESULTS= 0;

    /** **/
    private Integer MAX_RESULTS= 10;
    
    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        
    }

    
	
	@Test
	public void testGetTotalHashTagHitsbyDateRange() throws EnMeNoResultsFoundException{
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

		final List<HashTagDetailStats> stats = getStatisticsService()
				.getTotalUsagebyHashTagAndDateRange(tag.getHashTag(), 365, 0,
						20);
		for (HashTagDetailStats hashTagDetailStats : stats) {
			// System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n ");
			// System.out.println("Label ---> " + hashTagDetailStats.getLabel()
			// + "      Value ---> " +hashTagDetailStats.getValue());
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

		final List<HashTagDetailStats> totalSocialLinksUsagebyHashTagAndTweetPoll = getStatisticsService()
				.getTweetPollSocialNetworkLinksbyTagAndDateRange(
						hashtag1.getHashTag(), this.INIT_RESULTS,
						this.MAX_RESULTS, TypeSearchResult.TWEETPOLL, 365);

		// for (HashTagDetailStats hashTagDetailStats : total) {
		// System.out.println("Label : " + hashTagDetailStats.getLabel() +
		// "-----   Value: " + hashTagDetailStats.getValue());
		// }

		Assert.assertEquals("Should be equals", 3,
				totalSocialLinksUsagebyHashTagAndTweetPoll.size());

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
