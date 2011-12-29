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

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.encuestame.business.service.FrontEndService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.ProfileRatedTopBean;
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
				hashtag1.getHashTagId(), 0, 10, "hashtag");

        Assert.assertEquals("Should be equals", 4, totalUsage.intValue());
		
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
