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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.core.service.imp.IStatisticsService;
import org.encuestame.core.util.EnMeUtils;
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
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.categories.test.PerformanceTest;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.encuestame.utils.web.stats.HashTagListGraphData;
import org.encuestame.utils.web.stats.ItemStatDetail;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test Statistics Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2012
 */
@Category(DefaultTest.class)
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
    //private Calendar pollingDate = Calendar.getInstance();

    /** **/
    private String[] answers = {"yes", "no", "maybe", "impossible", "never"};

    private DateTime creationDate = new DateTime();

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
        this.initHashTag = createHashTag("futbol");
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
        survey.setCreateDate(new Date());
        getSurveyDaoImp().saveOrUpdate(survey);
        final Survey survey2 = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
                .getAccount());
        survey2.getHashTags().add(tag);
        survey2.setCreateDate(new Date());
        getSurveyDaoImp().saveOrUpdate(survey2);

        final Survey survey3 = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
                .getAccount());
        survey3.getHashTags().add(tag);
        survey3.setCreateDate(myDate.getTime());
        getSurveyDaoImp().saveOrUpdate(survey3);

        myDate.add(Calendar.MONTH, +6);
        final Survey survey4 = createDefaultSurvey(getSpringSecurityLoggedUserAccount()
                    .getAccount());
        survey4.getHashTags().add(tag);
        survey4.setCreateDate(myDate.getTime());
        getSurveyDaoImp().saveOrUpdate(survey4);

        final List<HashTagDetailStats> stats = getStatisticsService()
                .getTotalUsagebyHashTagAndDateRange(tag.getHashTag(), SearchPeriods.ONEYEAR, this.request);
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
        //FIXME: methods not longer exist
//        final List<HashTagDetailStats> totalSocialLinksUsagebyHashTagAndTweetPoll = getStatisticsService()
//                .getTweetPollSocialNetworkLinksbyTagAndDateRange(
//                        hashtag1.getHashTag(), 0,
//                        10, TypeSearchResult.TWEETPOLL, 365);
//       Assert.assertEquals("Should be equals", 3, totalSocialLinksUsagebyHashTagAndTweetPoll.size());

    }

    /**
     * Test
     * @throws EnMeSearchException
     */
    @Test
    public void testGetTotalVotesbyHashTagUsageAndDateRange() throws EnMeSearchException{

        DateTime updateDate = new DateTime();

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

        updateDate = this.creationDate.minusMonths(3);

        createTweetPollResultWithPollingDate(tpSwichtMadrid, "192.168.0.5", updateDate.toDate());
        updateDate = this.creationDate.minusMonths(5);

        createTweetPollResultWithPollingDate(tpSwichtBarsa, "192.168.0.6", updateDate.toDate());

        createTweetPollResultWithPollingDate(tpSwichtBarsa, "192.168.0.7", updateDate.toDate());


        final List<HashTagDetailStats> itemStatListbyYear = getStatisticsService()
                     .getTotalVotesbyHashTagUsageAndDateRangeGraph(
                               this.initHashTag.getHashTag(), SearchPeriods.ONEYEAR, this.request);

        Assert.assertEquals("Should be equals", 3, itemStatListbyYear.size());
    }

    /**
     * Test
     * @throws EnMeSearchException
     * @throws EnMeNoResultsFoundException
     */
    @Test
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


        final List<HashTagDetailStats> tagHitsDetailList = getStatisticsService().getTotalHitsUsagebyHashTagAndDateRange(hashTag1.getHashTag(), SearchPeriods.SEVENDAYS, this.request);
        Assert.assertEquals("Should be equals", 2, tagHitsDetailList.size());

    }

    /**
     * Test
     * @throws EnMeSearchException
     */
    @Test
    public void testGetTotalVotesbyHashTagUsageByMonthDateRange()
            throws EnMeSearchException {
        final Calendar pollingDate = Calendar.getInstance();
        pollingDate.add(Calendar.DATE, -2);

        createTweetPollResultWithPollingDate(this.initTweetPollSwicht,
                "192.168.0.11", pollingDate.getTime());
        pollingDate.add(Calendar.DATE, -2);

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
                        this.initHashTag.getHashTag(), SearchPeriods.THIRTYDAYS, this.request);
        Assert.assertEquals("Should be equals", 5, itemStatListbyMonth.size());
    }

    /**
     * Test
     * @throws EnMeSearchException
     */
    @Test
    public void testGetTotalSocialLinksbyHashTagUsageByYearDateRange() throws EnMeSearchException {
        final Calendar pollingDate = Calendar.getInstance();
        final String tweetContent = "Tweet content text";
        // Tweetpoll 1
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved.setApiType(SocialProvider.TWITTER);
        tpSaved.setPublicationDateTweet(pollingDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved);
        // TweetPoll 2
        final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        pollingDate.add(Calendar.MONTH, -3);
        tpSaved2.setPublicationDateTweet(pollingDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved2);

        final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved3.setApiType(SocialProvider.TWITTER);
        // This tweetpoll is ---> Out of range.
        pollingDate.add(Calendar.MONTH, -15);
        tpSaved3.setPublicationDateTweet(pollingDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved3);

        final List<HashTagDetailStats> detailStatsByYear = getStatisticsService().getTotalSocialLinksbyHashTagUsageAndDateRange(this.initHashTag.getHashTag(), SearchPeriods.ONEYEAR, this.request);
        Assert.assertEquals("Should be equals", 2, detailStatsByYear.size());
    }

    /**
     *
     * @throws EnMeSearchException
     */
     @Test
    public void testGetTotalSocialLinksbyHashTagUsageByWeekDateRange() throws EnMeSearchException {
        final Calendar pollingDate = Calendar.getInstance();
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
        pollingDate.add(Calendar.DATE, -3);
        tpSaved2.setPublicationDateTweet(pollingDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved2);

        final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved3.setApiType(SocialProvider.TWITTER);
        // Out of range.
        pollingDate.add(Calendar.DATE, -1);
        tpSaved3.setPublicationDateTweet(pollingDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved3);

        final TweetPollSavedPublishedStatus tpSaved4 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved4.setApiType(SocialProvider.LINKEDIN);
        tpSaved4.setPublicationDateTweet(pollingDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved4);
        final List<HashTagDetailStats> detailStatsByWeek = getStatisticsService()
                .getTotalSocialLinksbyHashTagUsageAndDateRange(
                        this.initHashTag.getHashTag(), SearchPeriods.SEVENDAYS, this.request);
        Assert.assertEquals("Should be equals", 3, detailStatsByWeek.size());
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
      //FIXME: methods not longer exist
        // Total usage TweetPoll, Poll and Survey by tagId
//        final Long totalUsage = getStatisticsService().getTotalUsageByHashTag(
//               hashtag1.getHashTag(), 0, 10, TypeSearchResult.HASHTAG);
//
//        Assert.assertEquals("Should be equals", 4, totalUsage.intValue());

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
        //FIXME: methods not longer exist
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
        //FIXME: methods not longer exist
         //final Long totalTweetPollsVoted = getFrontEndService().getHashTagUsedOnItemsVoted(hashtag1.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS);
         //Assert.assertEquals("Should be equals", 6, totalTweetPollsVoted.intValue());
    }

    /**
     * Get all items voted by hashtag in 7 days.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
  	@Category(PerformanceTest.class)
	@Test
    public void testGetHashTagUsedOnItemsVotedbySevenDaysPeriod()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final HashTag tag = createHashTag("season");
        this.createTweetPollsItemsVote(tag);
     //   this.createPollsItemsVote(tag);
        final HashTagDetailStats detail = statisticsService
                .getHashTagUsedOnItemsVoted(tag.getHashTag(), 0, 100, request,
                        SearchPeriods.SEVENDAYS);

//		Assert.assertEquals("Should be equals", 35, detail.getValue()
//				.intValue());

    }

    /**
     * Get all items voted by hashtag in 30 days.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    @Category(PerformanceTest.class)
   // @Test
    public void testGetHashTagUsedOnItemsVotedbyThirtyDayPeriod()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final HashTag tag = createHashTag("season");
        this.createTweetPollsItemsVote(tag);
        this.createPollsItemsVote(tag);
        HashTagDetailStats detail = statisticsService
                .getHashTagUsedOnItemsVoted(tag.getHashTag(), 0, 100, request,
                        SearchPeriods.THIRTYDAYS);
		Assert.assertEquals("Should be equals", 54, detail.getValue()
				.intValue());
    }

    /**
     * Get all items voted by hashtag in one year period.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    @Category(PerformanceTest.class)
  //  @Test
    public void testGetHashTagUsedOnItemsVotedbyOneYearPeriod()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final HashTag tag = createHashTag("season");
        this.createTweetPollsItemsVote(tag);
        this.createPollsItemsVote(tag);
        HashTagDetailStats detail = statisticsService
                .getHashTagUsedOnItemsVoted(tag.getHashTag(), 0, 150, request,
                        SearchPeriods.ONEYEAR);

		Assert.assertEquals("Should be equals", 90, detail.getValue()
				.intValue());
    }

    /**
     * Get all items voted by hashtag.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    @Category(PerformanceTest.class)
	@Test
    public void testGetHashTagUsedOnItemsVotedbyAllPeriod()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final HashTag tag = createHashTag("season");
        this.createTweetPollsItemsVote(tag);
        this.createPollsItemsVote(tag);
        HashTagDetailStats detail = statisticsService
                .getHashTagUsedOnItemsVoted(tag.getHashTag(), 0, 100, request,
                        SearchPeriods.ALLTIME);
//		Assert.assertEquals("Should be equals", 91, detail.getValue()
//				.intValue());
    }

    /**
     * Get items voted by hashtag in 24 hours.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	@Category(PerformanceTest.class)
	@Test
    public void testGetHashTagUsedOnItemsVotedbyTwentyFourHoursPeriod()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final HashTag tag = createHashTag("season");
        this.createTweetPollsItemsVote(tag);
        this.createPollsItemsVote(tag);
        HashTagDetailStats detail = statisticsService
                .getHashTagUsedOnItemsVoted(tag.getHashTag(), 0, 100, request,
                        SearchPeriods.TWENTYFOURHOURS);
//        Assert.assertEquals("Should be equals", 19, detail.getValue().intValue());
    }

    /**
     *
     * @param tag
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private void createPollsItemsVotebyHours(final HashTag tag) throws NoSuchAlgorithmException, UnsupportedEncodingException{

   	 /* ************************* Current Date *****************************1 -- V=1 - NV=0 */
		creationDate = new DateTime();
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

		 /* ************************* Minus 3 Hours ************************** 5 -- V=2 - NV=3 */
        /*   -------- POLL VOTED: 2  -------- */
        /*   -------- POLL NOT VOTED: 3  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusHours(3);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

		 /* ************************* Minus 5 Hours ************************** 4 -- V=3 - NV=1 */
        /*   -------- POLL VOTED: 3  -------- */
        /*   -------- POLL NOT VOTED: 1  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusHours(5);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

		 /* ************************* Minus 8 Hours ************************** 6 -- V=3 - NV=3 */
        /*   -------- POLL VOTED: 3  -------- */
        /*   -------- POLL NOT VOTED: 3  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusHours(8);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

		/* ************************* Minus 9 Hours ************************** 7-- V=5 - NV=2 */
        /*   -------- POLL VOTED: 5  -------- */
        /*   -------- POLL NOT VOTED: 2  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusHours(9);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
	}

	private void createPollsItemsVotebyDays(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {


		/* ************************* Minus 3 Hours ************************** 3 -- V=1 - NV=2 */
		/*   -------- POLL VOTED: 1  -------- */
		/*   -------- POLL NOT VOTED: 2  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusDays(3);

		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

		/* ************************* Minus 5 Hours ************************** 4-- V=3 - NV=1 */
		/*   -------- POLL VOTED: 3  -------- */
		/*   -------- POLL NOT VOTED: 1  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusDays(5);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
	}

	private void createPollsItemsVotebyMonths(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		/* ************************* Minus 3 Months  ************************** 2 -- V=1 - NV=1 */
		/*   -------- POLL VOTED: 1  -------- */
		/*   -------- POLL NOT VOTED: 1  -------- */
		creationDate = new DateTime();
		final DateTime creationDate2 = creationDate.minusMonths(3);
		this.createPollItems(tag, creationDate2.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate2.toDate(), answers, Boolean.FALSE);

		/* ************************* Minus 5 Months  ************************** 7 -- V=4 - NV=3 */
		/*   -------- POLL VOTED: 4  -------- */
		/*   -------- POLL NOT VOTED: 3  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusMonths(5);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

		/* ************************* Minus 8 Months  ************************** 6 -- V=3 - NV=3 */
		/*   -------- POLL VOTED: 3  -------- */
		/*   -------- POLL NOT VOTED: 3  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusMonths(8);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

		/* ************************* Minus 10 Months  ************************** 4 -- V=2 - NV=2 */
		/*   -------- POLL VOTED: 2  -------- */
		/*   -------- POLL NOT VOTED: 2  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusMonths(10);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

		/* ************************* Minus 11 Months  ************************** 2 -- V=1 - NV=1 */
		/*   -------- POLL VOTED: 1  -------- */
		/*   -------- POLL NOT VOTED:1  -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusMonths(11);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
	}

	private void createPollsItemsVotebyYear(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		/* ************************* Minus 1 Year ************************** 5 -- V=2 - NV=3 */
		/* -------- POLL VOTED: 2 -------- */
		/* -------- POLL NOT VOTED: 3 -------- */
		creationDate = new DateTime();

		creationDate = creationDate.minusYears(1);
		creationDate = creationDate.plusHours(1);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

		/* ************************* Plus 1 Year ************************** 1 -- V=1 - NV=1 */
		/* -------- POLL VOTED: 1 -------- */
		/* -------- POLL NOT VOTED: 1 -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusYears(2);
		creationDate = creationDate.plusHours(1);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
		this.createPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

	}

    /**
     * Create A poll item with votes.
     * @param tag
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	private void createPollsItemsVote(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.createPollsItemsVotebyHours(tag);
		this.createPollsItemsVotebyDays(tag);
		this.createPollsItemsVotebyMonths(tag);
		this.createPollsItemsVotebyYear(tag);
	}

    private void createTweetpollsItemsbyHours(final HashTag tag) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	  DateTime creationDate = new DateTime();

    	  /* ************************* Current date  ************************** 1*/
          this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

          /* ************************* Minus 3 Hours ************************** 2 -- V=1- NV=1*/
          /*   -------- TP VOTED: 1  -------- */
          /*   -------- TP NOT VOTED: 1  -------- */

          creationDate = creationDate.minusHours(3);
          this.createTweetPollItems(tag, creationDate.toDate(), answers,
                  Boolean.TRUE); // Voted

          this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

          /* ************************* Minus 5 Hours ************************** 4 -- V=2 - NV=2 */
          /*   -------- TP VOTED: 2    -------- */
          /*   -------- TP NOT VOTED: 2 -------- */

          creationDate = new DateTime();
          creationDate = creationDate.minusHours(5);
          this.createTweetPollItems(tag, creationDate.toDate(), answers,
                  Boolean.TRUE); // Voted

          this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
          this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

          this.createTweetPollItems(tag, creationDate.toDate(), answers,
                  Boolean.TRUE); // Voted

          /* ************************* Minus 8 Hours ************************** 1 -- V=1 - NV=0 */
          /*   -------- TP VOTED: 1    -------- */
          /*   -------- TP NOT VOTED: 0 -------- */
          creationDate = new DateTime();
          creationDate = creationDate.minusHours(8);
          this.createTweetPollItems(tag, creationDate.toDate(), answers,
                  Boolean.TRUE); // Voted

          /* ************************* Minus 9 Hours **************************1 -- V=1 - NV=0 */
          /*   -------- TP VOTED: 1    -------- */
          /*   -------- TP NOT VOTED: 0 -------- */
          creationDate = new DateTime();
          creationDate = creationDate.minusHours(9);
          this.createTweetPollItems(tag, creationDate.toDate(), answers,
                  Boolean.TRUE); // Voted

    }

	private void createTweetpollsItemsbyDays(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		/* ************************* Minus 2 Days ************************** 6 -- V=3 - NV=3 */
        /*   -------- TP VOTED: 3    -------- */
        /*   -------- TP NOT VOTED: 3 -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusDays(2);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE);// Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		/* ************************* Minus 5 Days ************************** 16 -- V=9 - NV=7 */
        /*   -------- TP VOTED: 9    -------- */
        /*   -------- TP NOT VOTED: 7 -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusDays(5);
		// Minus 1 Hours
		//creationDate = creationDate.minusHours(1);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
		Boolean.TRUE);
		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE);


		/* ************************* Minus 11 Days ************************** 5 -- V=5 - NV=0 */
        /*   -------- TP VOTED: 5    -------- */
        /*   -------- TP NOT VOTED: 0 -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusDays(11);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		/* ************************* Minus 19 Days ************************** 5 -- V=5 - NV=0 */
        /*   -------- TP VOTED: 5    -------- */
        /*   -------- TP NOT VOTED: 0 -------- */

		creationDate = new DateTime();
		creationDate = creationDate.minusDays(19);
		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted


		/* ************************* Minus 24 Days ************************** 4 -- V=4 - NV=0 */
        /*   -------- TP VOTED: 4    -------- */
        /*   -------- TP NOT VOTED: 0 -------- */

		creationDate = new DateTime();
		creationDate = creationDate.minusDays(24);
		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted


		/* ************************* Minus 29 Days ************************** 5 -- V=5 - NV=0 */
        /*   -------- TP VOTED: 5    -------- */
        /*   -------- TP NOT VOTED: 0 -------- */

		creationDate = new DateTime();
		creationDate = creationDate.minusDays(29);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		/* ************************* Minus 32 Days ************************** 8 -- V=3 - NV=5 */
        /*   -------- TP VOTED: 3    -------- */
        /*   -------- TP NOT VOTED: 5 -------- */

		creationDate = new DateTime();
		creationDate = creationDate.minusDays(32);
		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);
		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);
		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);
	}

	private void createTweetpollsItemsbyMonths(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		/* ************************* Minus 1 Month ************************** 5 -- V=2 - NV=3 */
        /*   -------- TP VOTED: 2  -------- */
        /*   -------- TP NOT VOTED: 3  -------- */
		creationDate = new DateTime();
        creationDate = creationDate.minusMonths(1);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

    	/* ************************* Minus 4 Month ************************** 5 -- V=3 - NV=2 */
        /*   -------- TP VOTED: 3  -------- */
        /*   -------- TP NOT VOTED: 2  -------- */
    	creationDate = new DateTime();
        creationDate = creationDate.minusMonths(4);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag,
                creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        /* ************************* Minus 6 Month ************************** 7 -- V=4 - NV=3 */
        /*   -------- TP VOTED: 4  -------- */
        /*   -------- TP NOT VOTED: 3 -------- */
    	creationDate = new DateTime();
        creationDate = creationDate.minusMonths(6);

        this.createTweetPollItems(tag,
                creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        /* ************************* Minus 9 Month ************************** 14 -- V=5 - NV=9 */
        /*   -------- TP VOTED: 5  -------- */
        /*   -------- TP NOT VOTED: 9  -------- */
        creationDate = new DateTime();
        creationDate = creationDate.minusMonths(9);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        /* ************************* Minus 10 Month ************************** 6 -- V=3 - NV=3 */
        /*   -------- TP VOTED: 3  -------- */
        /*   -------- TP NOT VOTED: 3  -------- */
        creationDate = new DateTime();
        creationDate = creationDate.minusMonths(10);


        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);
        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.FALSE);

        this.createTweetPollItems(tag, creationDate.toDate(), answers, Boolean.TRUE); // Voted 56
	}

	private void createTweetpollsItemsbyYear(final HashTag tag)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		/* ************************* Minus 1 Yeear ************************** */
		/* -------- TP VOTED: 3 -------- */
		/* -------- TP NOT VOTED: 2 -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusYears(1);
		creationDate = creationDate.plusHours(3);


		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.TRUE); // Voted

		/* ************************* Minus 2 Yeear ************************** */
		/* -------- TP VOTED: 3 -------- */
		/* -------- TP NOT VOTED: 2 -------- */
		creationDate = new DateTime();
		creationDate = creationDate.minusYears(2);
		creationDate = creationDate.plusHours(3);


		this.createTweetPollItems(tag, creationDate.toDate(), answers,
				Boolean.FALSE);
	}

	/**
     * Create tweetPoll items vote.
     * @param tag
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private void createTweetPollsItemsVote(final HashTag tag)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.createTweetpollsItemsbyHours(tag);
		this.createTweetpollsItemsbyDays(tag);
//		this.createTweetpollsItemsbyMonths(tag);
//		this.createTweetpollsItemsbyYear(tag);

    }

    /**
     * Create {@link TweetPoll}
     * @param tag
     * @param randomDate
     * @param answers
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private void createTweetPollItems(final HashTag tag,
            final Date randomDate, final String[] answers, final Boolean voteItem)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int j = 0;
        List<TweetPollSwitch> tpollSwitchList = new ArrayList<TweetPollSwitch>();
        // Creating question ...
        final Question question =  this.createRandomQuestion();

        // Creating tweetpoll...

        final TweetPoll myTweet = createPublishedTweetPoll(question,
                getSpringSecurityLoggedUserAccount());
        myTweet.getHashTags().add(tag);
        myTweet.setCreateDate(randomDate);
        getTweetPoll().saveOrUpdate(myTweet);

        // Add 2 answers by question
        for (j = 0; j < 2; j++) {
            // Creating answers... - Select answers = "yes", "no", "maybe", "impossible", "never"
            final QuestionAnswer qAnswers = this.createRandomQuestionAnswer(j, question, answers);

            // Creating TweetPoll switch.
            TweetPollSwitch tpollSwitch = createTweetPollSwitch(qAnswers, myTweet);
            tpollSwitchList.add(tpollSwitch);
        }
        if(voteItem){
            this.voteTweetPollSwitch(EnMeUtils.ipGenerator(),
                    tpollSwitchList.get(1));
        }
    }

    /**
     * Create random question for tweetpolls and polls.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private Question createRandomQuestion() throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        final Question randomQuestion = createQuestion(
                "What is your favorite season? "
                        + MD5Utils
                                .md5(RandomStringUtils.randomAlphanumeric(15)),
                "");
        return randomQuestion;
    }

    /**
     * Create {@link QuestionAnswer}
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private QuestionAnswer createRandomQuestionAnswer(final Integer j,
            final Question question, final String answers[])
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final QuestionAnswer qAnswers = createQuestionAnswer(
                answers[1], question,
                MD5Utils.md5(RandomStringUtils.randomAlphanumeric(4) + j));
        return qAnswers;
    }

    /**
     * Vote tweetpoll switch.
     * @param ip
     * @param tpSwitch
     */
    private void voteTweetPollSwitch(final String ip,
            final TweetPollSwitch tpSwitch) {
        createTweetPollResult(tpSwitch, ip);
    }


    /**
     * Create {@link Poll} and {@link QuestionAnswer}
     * @param tag
     * @param randomDate
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private void createPollItems(final HashTag tag, final Date randomDate,
            final String[] answers, final Boolean voteItem)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int j = 0;
        List<QuestionAnswer> qAnswersList = new ArrayList<QuestionAnswer>();

        final Question pollQuestion = this.createRandomQuestion();
        final Poll myPoll = createPoll(randomDate, pollQuestion,
                MD5Utils.md5(RandomStringUtils.randomAlphanumeric(4)),
                getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
        myPoll.getHashTags().add(tag);
        getPollDao().saveOrUpdate(myPoll);
        for (j = 0; j < 2; j++) {
            // Creating answers...
            final QuestionAnswer qAnswers = this.createRandomQuestionAnswer(j,
                    pollQuestion, answers);
            qAnswersList.add(qAnswers);
        }
        if (voteItem) {
            this.votePollResult(EnMeUtils.ipGenerator(),
                    qAnswersList.get(getRandomNumberRange(1, 0)), myPoll);
        }
    }

    /**
     * Create poll result vote.
     * @param ip
     * @param qAnswers
     * @param poll
     */
    private void votePollResult(final String ip, final QuestionAnswer qAnswers,
            final Poll poll) {
        createPollResults(qAnswers, poll);
    }


    /**
     *
     * @param max
     * @param min
     * @return
     */
    private int getRandomNumberRange(int max, int min){
        return (int) (Math.random() * (max - min + 1) ) + min;
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

    //// /////////////////////////////////////// TEST  NEW HASHTAG CHART ///////////////////////////////////////
    @Test
    public void testGetTotalHashTagHitsbyDateRange2() throws EnMeNoResultsFoundException, EnMeSearchException{
        final Question question = createQuestion("What is your favorite type of song?", "");
        final HashTag mytag = createHashTag("romantic");
        DateTime createdAt = new DateTime();

        // TweetPoll
        final TweetPoll tpoll = createPublishedTweetPoll(5L, question,
                getSpringSecurityLoggedUserAccount());
        tpoll.getHashTags().add(mytag);
        tpoll.setCreateDate(createdAt.toDate());
        getTweetPoll().saveOrUpdate(tpoll);

        // Tweetpoll 2
        final TweetPoll tpoll2 = createPublishedTweetPoll(5L, question,
                getSpringSecurityLoggedUserAccount());
        tpoll2.getHashTags().add(mytag);
        tpoll.setCreateDate(createdAt.toDate());
        getTweetPoll().saveOrUpdate(tpoll2);
        createdAt = this.creationDate.minusMonths(2);

        // Tweetpoll 3
        final TweetPoll tpoll3 = createPublishedTweetPoll(6L, question,
                getSpringSecurityLoggedUserAccount());
        tpoll3.getHashTags().add(mytag);
        tpoll3.setCreateDate(createdAt.toDate());
        getTweetPoll().saveOrUpdate(tpoll3);
        //myDate.add(Calendar.MONTH, -2);
        createdAt = this.creationDate.minusMonths(4);

         final Poll poll4 = createDefaultPoll(question,
                 getSpringSecurityLoggedUserAccount());
         poll4.getHashTags().add(mytag);
         poll4.setCreateDate(createdAt.toDate());
         getPollDao().saveOrUpdate(poll4);


         createdAt = this.creationDate.minusMonths(6);
         final Survey survey5 = createDefaultSurvey(
                getSpringSecurityLoggedUserAccount().getAccount(),
                "survey name", createdAt.toDate());
        survey5.getHashTags().add(mytag);
        getSurveyDaoImp().saveOrUpdate(survey5);


        final List<HashTagDetailStats> stats = getStatisticsService()
                .getTotalUsagebyHashtagAndDateRangeGraph(mytag.getHashTag(), SearchPeriods.ONEYEAR, this.request);

        Assert.assertEquals("Should be equals", 4, stats.size());
    }

    /**
     *
     * @throws EnMeSearchException
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetTotalUsagebyHashtagAndDateRangeGraph() throws EnMeSearchException,
            EnMeNoResultsFoundException {
        final HashTag myHashTag = createHashTag("preferences");
        final Calendar releaseDate = Calendar.getInstance();

        final Question myFirstQuestion = createQuestion(
                "What is your favorite kind of movie?", secondary.getAccount());
        final Question mySecondQuestion = createQuestion(
                "What is your favorite kind of song?", secondary.getAccount());
        // TP 1
        final TweetPoll tp1 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp1.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp1);
        assertNotNull(tp1);

        // TP 2
        releaseDate.add(Calendar.HOUR, -1);
        final TweetPoll tp2 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp2.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp2);
        assertNotNull(tp2);

        // TP 3
        releaseDate.add(Calendar.HOUR, -3);
        final TweetPoll tp3 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp3.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp3);
        assertNotNull(tp3);

        // TP 4
        releaseDate.add(Calendar.DATE, -1);
        releaseDate.add(Calendar.HOUR, -5);
        final TweetPoll tp4 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp4.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp4);
        assertNotNull(tp4);

        // TP 5
        releaseDate.add(Calendar.DATE, -1);
        final TweetPoll tp5 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp5.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp5);
        assertNotNull(tp5);

        // TP 6
        releaseDate.add(Calendar.DATE, -1);
        releaseDate.add(Calendar.HOUR, -2);
        final TweetPoll tp6 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp6.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp6);
        assertNotNull(tp6);

        // TP 7
        releaseDate.add(Calendar.YEAR, -1);
        final TweetPoll tp7 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp7.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp7);
        assertNotNull(tp7);

        // TP 8
        releaseDate.add(Calendar.MONTH, -1);
        final TweetPoll tp8 = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tp8.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tp8);
        assertNotNull(tp8);

        final List<ItemStatDetail> itemStatDetail = new ArrayList<ItemStatDetail>();
        final ItemStatDetail isd1 = this.createItemStatDetail(tp1.getTweetPollId(),
                tp1.getCreateDate());
        final ItemStatDetail isd2 = this.createItemStatDetail(tp2.getTweetPollId(),
                tp2.getCreateDate());
        final ItemStatDetail isd3 = this.createItemStatDetail(tp3.getTweetPollId(),
                tp3.getCreateDate());
        final ItemStatDetail isd4 = this.createItemStatDetail(tp4.getTweetPollId(),
                tp4.getCreateDate());
        final ItemStatDetail isd5 = this.createItemStatDetail(tp5.getTweetPollId(),
                tp5.getCreateDate());
        final ItemStatDetail isd6 = this.createItemStatDetail(tp6.getTweetPollId(),
                tp6.getCreateDate());
        final ItemStatDetail isd7 = this.createItemStatDetail(tp7.getTweetPollId(),
                tp7.getCreateDate());
        final ItemStatDetail isd8 = this.createItemStatDetail(tp7.getTweetPollId(),
                tp8.getCreateDate());

        itemStatDetail.add(isd1);
        itemStatDetail.add(isd2);
        itemStatDetail.add(isd3);
        itemStatDetail.add(isd4);
        itemStatDetail.add(isd5);
        itemStatDetail.add(isd6);
        itemStatDetail.add(isd7);
        itemStatDetail.add(isd8);

        final List<HashTagDetailStats> compareHashtagListGraph = getStatisticsService()
                .compareHashtagListGraph(itemStatDetail, SearchPeriods.ALLTIME,
                        this.request);

        List<HashTagDetailStats> getHashtagAndDateRangeGraphList = getStatisticsService()
                .getTotalUsagebyHashtagAndDateRangeGraph(
                        myHashTag.getHashTag(), SearchPeriods.ALLTIME,
                        this.request);
    }

    /**
     *
     * @throws EnMeSearchException
     */
    @Test
    public void testGetTotalSocialLinksbyHashTagUsageByWeekDateRangeGraph()
            throws EnMeSearchException {
        DateTime newCreationDate = new DateTime();
        final String tweetContent = "social content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved.setApiType(SocialProvider.TWITTER);
        newCreationDate = this.creationDate.minusDays(3);
        tpSaved.setPublicationDateTweet(newCreationDate.toDate());
        getTweetPoll().saveOrUpdate(tpSaved);

        // TweetPoll 2

        final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        newCreationDate = this.creationDate.minusDays(4);
        tpSaved2.setPublicationDateTweet(newCreationDate.toDate());
        getTweetPoll().saveOrUpdate(tpSaved2);

        // TweetPoll 3
        final TweetPollSavedPublishedStatus tpSaved3 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved3.setApiType(SocialProvider.TWITTER);
        tpSaved3.setPublicationDateTweet(newCreationDate.toDate());
        getTweetPoll().saveOrUpdate(tpSaved3);

        // Out of range.

        final TweetPollSavedPublishedStatus tpSaved4 = createTweetPollSavedPublishedStatus(
                this.initTweetPoll, " ", this.initSocialAccount, tweetContent);
        tpSaved4.setApiType(SocialProvider.LINKEDIN);
        newCreationDate = this.creationDate.minusDays(9);
        tpSaved4.setPublicationDateTweet(newCreationDate.toDate());
        getTweetPoll().saveOrUpdate(tpSaved4);

        final List<HashTagDetailStats> detailStatsByWeek = getStatisticsService()
                .getTotalSocialLinksbyHashTagUsageAndDateRangeGraph(
                        this.initHashTag.getHashTag(), SearchPeriods.SEVENDAYS,
                        this.request);
        Assert.assertEquals("Should be equals", 2, detailStatsByWeek.size());
    }

    /**
     * Testing GetTotalUsagebyHashtagAndDateRangeListGraph.
     * @throws EnMeSearchException
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetTotalUsagebyHashtagAndDateRangeListGraph()
            throws EnMeSearchException, EnMeNoResultsFoundException {
        final Calendar currentDate = Calendar.getInstance();
        final List<HashTagListGraphData> list = getStatisticsService().getTotalUsagebyHashtagAndDateRangeListGraph( this.initHashTag.getHashTag(),
                    SearchPeriods.ONEYEAR, request);
        Assert.assertEquals("Should be equals", 1, list.size());
        final Question question = createQuestion("What is your favorite season?", "");
        final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
        tp.getHashTags().add(this.initHashTag);
        getTweetPoll().saveOrUpdate(tp);
        // Item 2
        final Question question2 = createQuestion("What is your favorite holidays?", "");
        final TweetPoll tp2 = createPublishedTweetPoll(question2, this.secondary);
        tp2.getHashTags().add(this.initHashTag);
        getTweetPoll().saveOrUpdate(tp2);
        final List<HashTagListGraphData> list2 = getStatisticsService().getTotalUsagebyHashtagAndDateRangeListGraph( this.initHashTag.getHashTag(),
                SearchPeriods.ONEYEAR, request);
        Assert.assertEquals("Should be equals", 1, list2.size());
        Assert.assertEquals("Should be equals", Long.valueOf(3), list2.get(0).getValue());
        // poll 1
        final Poll poll1 = createPoll(currentDate.getTime(), question, this.secondary, true, true);
        poll1.getHashTags().add(initHashTag);
        getPollDao().saveOrUpdate(poll1);
        final List<HashTagListGraphData> list3 = getStatisticsService().getTotalUsagebyHashtagAndDateRangeListGraph( this.initHashTag.getHashTag(),
                SearchPeriods.ONEYEAR, request);
        Assert.assertEquals("Should be equals", 1, list3.size());
        Assert.assertEquals("Should be equals", Long.valueOf(4), list3.get(0).getValue());
        // poll 2
        currentDate.add(Calendar.YEAR, -4);
        final Poll poll2 = createPoll(currentDate.getTime(), question, this.secondary, true, true);
        poll2.getHashTags().add(initHashTag);
        getPollDao().saveOrUpdate(poll2);
        final List<HashTagListGraphData> list4 = getStatisticsService().getTotalUsagebyHashtagAndDateRangeListGraph( this.initHashTag.getHashTag(),
                SearchPeriods.ONEYEAR, request);
        Assert.assertEquals("Should be equals", 1, list4.size());
        Assert.assertEquals("Should be equals", Long.valueOf(4), list4.get(0).getValue());

        //FUTURE: should be increased with SURVEY items
    }

    /**
     * Create {@link ItemStatDetail}.
     * @param itemId
     * @param creationDate
     * @return
     */
    private ItemStatDetail createItemStatDetail(final Long itemId, final Date creationDate) {
        final ItemStatDetail isd = new ItemStatDetail();
        isd.setDate(creationDate);
        isd.setItemId(itemId);
        return isd;
    }
}
