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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.RelativeTimeEnum;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test {@link TweetPollDao}..
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 13, 2010 11:57:17 PM
 */
@Category(DefaultTest.class)
public class TestTweetPollDao extends AbstractBase {

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** {@link QuestionAnswer}. **/
    private QuestionAnswer questionsAnswers1;

    /** {@link QuestionAnswer}. **/
    private QuestionAnswer questionsAnswers2;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch1;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch2;

    /** {@link TweetPoll}. **/
    private TweetPoll tweetPoll;

    /** {@link TweetPollFolder}. **/
    private TweetPollFolder tweetPollFolder;

    private HashTag hashTag1;

    /** Maximum results query. **/
    private Integer MAX_RESULTS = 30;

    /** Init results query. **/
    private Integer INIT_RESULTS = 0;

    /** {@link Question} **/
    private Question question;

    private Boolean defaultFalseValue = Boolean.FALSE;

    /**
     * {@link Account}
     */
    private Account account;

    /**
     *
     */
    private DateTime initDate = new DateTime();

    private SocialAccount socialAccount;

    /**
     * Before.
     */
    @Before
    public void initData() {
        this.account = createAccount();
        this.secondary = createUserAccount("jhonny", account);
        this.question = createQuestion("Who I am?", "");
        this.questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
        this.questionsAnswers2 = createQuestionAnswer("no", question, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary,
                this.question);
        final DateTime dt = new DateTime();
        final DateTime minusDate = dt.minusDays(3);
        tweetPoll.setCompleted(Boolean.TRUE);
        this.tweetPoll.setCreateDate(minusDate.toDate());
        this.hashTag1 = createHashTag("hash1");
        final HashTag hashTag2 = createHashTag("hash2");
        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag2);
        getTweetPoll().saveOrUpdate(this.tweetPoll);
        this.pollSwitch1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
        this.pollSwitch2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
        createTweetPollResult(pollSwitch1, "192.168.0.1");
        createTweetPollResult(pollSwitch1, "192.168.0.2");
        createTweetPollResult(pollSwitch2, "192.168.0.3");
        createTweetPollResult(pollSwitch2, "192.168.0.4");
        this.tweetPollFolder = createTweetPollFolder("First TweetPoll Folder",
                secondary);
        tweetPoll.setNumbervotes(65L);
        this.socialAccount = createDefaultSettedSocialAccount(this.secondary);
    }

    /**
     * Test retrieveTweetsPollSwitch.
     */
    @Test
    public void testRetrieveTweetsPollSwitch() {
        final TweetPollSwitch pollSwitch = getTweetPoll()
                .retrieveTweetsPollSwitch(this.pollSwitch1.getCodeTweet());
        assertNotNull(pollSwitch);
    }

    /**
     * Test getResultsByTweetPoll.
     */
    @Test
    public void testgetResultsByTweetPoll() {
        final List<Object[]> results = getTweetPoll().getResultsByTweetPoll(
                tweetPoll, this.questionsAnswers1);
        assertEquals("Should be equals", 1, results.size());
        assertEquals("Should be equals", "yes", results.get(0)[0]);
        assertEquals("Should be equals", "2", results.get(0)[1].toString());
    }

    /**
     * Test Get Total Votes by TweetPoll
     */
    @Test
    public void testgetTotalVotesByTweetPoll() {
        final List<Object[]> pollSwitchs = getTweetPoll()
                .getTotalVotesByTweetPoll(this.tweetPoll.getTweetPollId());
        assertEquals("Should be equals", 2, pollSwitchs.size());
    }

    @Test
    public void testgetVotesByTweetPollId() {
        final Long totalVotes = getTweetPoll().getTotalVotesByTweetPollId(
                this.tweetPoll.getTweetPollId());
        assertEquals("Should be equals", 4, totalVotes.intValue());
    }

    /**
     * Test to get total votes by tweetpoll and specific date range.
     */
    @Test
    public void testGetTotalVotesByTweetPollIdAndDateRange() {
        final Question myQuestion = createQuestion("Where are you from?", "");
        final QuestionAnswer qaAmerica = createQuestionAnswer("America",
                myQuestion, "123457");
        final QuestionAnswer qaEurope = createQuestionAnswer("Europa",
                myQuestion, "123469");
        final TweetPoll myTweetPoll = createPublishedTweetPoll(
                secondary, myQuestion);
        HashTag htCitizen = createHashTag("citizen");
        HashTag htCitizenShip = createHashTag("citizenship");
        myTweetPoll.getHashTags().add(htCitizen);
        myTweetPoll.getHashTags().add(htCitizenShip);
        getTweetPoll().saveOrUpdate(myTweetPoll);

        TweetPollSwitch pollSwitchAmerica = createTweetPollSwitch(qaAmerica,
                myTweetPoll);
        TweetPollSwitch pollSwitchEurope = createTweetPollSwitch(qaEurope,
                myTweetPoll);

        final Calendar pollingDate = Calendar.getInstance();
        pollingDate.add(Calendar.MONTH, -1);

        final TweetPollResult tpResultAmerica = createTweetPollResultWithPollingDate(
                pollSwitchAmerica, "192.168.0.1", pollingDate.getTime());

        pollingDate.add(Calendar.MONTH, -5);
        final TweetPollResult tpResultAmerica2 = createTweetPollResultWithPollingDate(
                pollSwitchAmerica, "192.168.0.2", pollingDate.getTime());

        final TweetPollResult tpResultEurope = createTweetPollResultWithPollingDate(
                pollSwitchEurope, "192.168.0.2", pollingDate.getTime());

        final Long totalVotes = getTweetPoll()
                .getTotalVotesByTweetPollIdAndDateRange(
                        myTweetPoll.getTweetPollId(),
                        SearchPeriods.ONEYEAR.toString());
        assertEquals("Should be equals", 3, totalVotes.intValue());

    }

    /**
     * Test get all {@link TweetPollResult} by {@link TweetPollSwitch}
     */
    @Test
    public void testGetTweetPollResultsByTweetPollSwitch() {
        final List<TweetPollResult> tpResults = getTweetPoll()
                .getTweetPollResultsByTweetPollSwitch(this.pollSwitch1);
        assertEquals("Should be equals", 2, tpResults.size());
    }

    /**
     * Test retrieve counter value from {@link TweetPollResult} by
     * {@link TweetPollSwitch}.
     */
    @Test
    public void testGetTotalTweetPollResultByTweetPollSwitch() {
        final Long myvalue = this.getTweetPoll()
                .getTotalTweetPollResultByTweetPollSwitch(pollSwitch1,
                        SearchPeriods.ONEYEAR);
        // See @Before on the top
        assertEquals("Should be equals", 2, myvalue.intValue());
    }

    /**
     * Test Get TweetPoll by TweetPoll Id and User.
     */
    @Test
    public void testGetTweetPollByIdandUserId() {
        assertNotNull(tweetPoll);
        assertNotNull(secondary);
        final TweetPoll tp = getTweetPoll().getTweetPollByIdandUserId(
                this.tweetPoll.getTweetPollId(),
                secondary.getAccount().getUid());
        assertEquals("Should be equals", 1, 1);
        assertEquals("Should be equals", this.tweetPoll.getTweetPollId(),
                tp.getTweetPollId());
    }

    /**
     * Test Get TweetPoll Folder by FolderId and User.
     */
    @Test
    public void testGetTweetPollFolderByIdandUser() {
        assertNotNull(this.tweetPollFolder);
        final TweetPollFolder tpf = getTweetPoll()
                .getTweetPollFolderByIdandUser(this.tweetPollFolder.getId(),
                        secondary.getAccount());
        assertEquals("Should be equals", this.tweetPollFolder.getId(),
                tpf.getId());
    }

    /**
     * Test Get TweetPoll Folder by folderId.
     */
    @Test
    public void testGetTweetPollFolderById() {
        assertNotNull(tweetPollFolder);
        final TweetPollFolder tpf = getTweetPoll().getTweetPollFolderById(
                this.tweetPollFolder.getId());
        assertNotNull(tpf);
    }

    /**
     * Test Retrieve TweetPoll Folder by User.
     */
    @Test
    public void testRetrieveTweetPollFolderByUserId() {
        assertNotNull(tweetPollFolder);
        assertNotNull(secondary);
        final List<TweetPollFolder> tpfu = getTweetPoll()
                .retrieveTweetPollFolderByAccount(this.secondary.getAccount());
        assertEquals("Should be equals", 1, tpfu.size());
    }

    /**
     * Test Retrieve TweetPoll by Folder.
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testRetrieveTweetPollByFolder()
            throws EnMeNoResultsFoundException {
        final Long user = this.secondary.getAccount().getUid();
        assertNotNull(tweetPollFolder);
        assertNotNull(tweetPoll);
        final TweetPoll addTweetPoll = addTweetPollToFolder(
                this.tweetPollFolder.getId(), user,
                this.tweetPoll.getTweetPollId());
        assertNotNull(addTweetPoll);
        final List<TweetPoll> tpfolder = getTweetPoll()
                .retrieveTweetPollByFolder(user, this.tweetPollFolder.getId());
        assertEquals("Should be equals", 1, tpfolder.size());
    }

    /**
     * Test Retrieve Tweets By User.
     */
    @Test
    public void testRetrieveTweetsByUserId() {
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final String peri = "7";
        final List<TweetPoll> tweets = getTweetPoll().retrieveTweetsByUserId(" ", userId, 10, 0, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, peri);
        assertEquals("Should be equals", 1, tweets.size());
    }

    /**
     * Test Retrieve Tweets by
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
   @Test
    public void testRetrieveTweetsByQuestionName()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        final Long userId = this.secondary.getAccount().getUid();
        final String keyword = "What";
        // Completed - Favourites - Scheduled - Published
        DateTime creationDate = new DateTime();
        creationDate = creationDate.minusHours(3);

        // Completed - Favourites - Scheduled - Published
        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.TRUE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.TRUE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.TRUE, Boolean.TRUE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.FALSE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        // 24 hours
        creationDate = creationDate.minusDays(3);
        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.FALSE, Boolean.FALSE,
                Boolean.FALSE, Boolean.TRUE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        creationDate = creationDate.minusDays(2);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        creationDate = creationDate.minusDays(4);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        this.createTweetPollItems(creationDate.toDate(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.FALSE);

        // Search Tweetpolls by keyword - Period 24
        // Completed - Scheduled - Favourite - Published
        final List<TweetPoll> tweetpollsResults = getTweetPoll()
                .retrieveTweetsByQuestionName(keyword, userId,
                        this.MAX_RESULTS, this.INIT_RESULTS, Boolean.TRUE,
                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "24");
        assertEquals("Should be equals", 11, tweetpollsResults.size());

   }

    /**
     * Test Retrieve TweetPoll Today.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testRetrieveTweetPollToday() throws NoSuchAlgorithmException, UnsupportedEncodingException {

         DateTime creationDate = new DateTime();
         creationDate = creationDate.plusMinutes(3);
        assertNotNull(this.secondary);

        // Compeleted - Favourite - Scheduled -Published.
        final TweetPoll tp2 =   createTweetPollItems(new Date(),
                this.secondary, Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, Boolean.TRUE);

        // Completed - Scheduled - Favourite - Published
        final List<TweetPoll> tweetsToday = getTweetPoll()
                .retrieveTweetPollToday(this.secondary.getAccount(), 10, 0,
                        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE,
                        Boolean.TRUE, null, "24");
        assertEquals("Should be equals", 1, tweetsToday.size());
    }

    /**
     * Test Retrieve tweetpoll by date.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    //@Test
    public void testRetrieveTweetPollByDate() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertNotNull(this.secondary);
        final DateTime dt = new DateTime();
        final DateTime dtplus =dt.plusMinutes(5);

        // Completed - Favourites - Scheduled - Published
        final TweetPoll tp =  createTweetPollItems(dtplus.toDate(), this.secondary, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);

        // Completed - Scheduled - Favourite - Published
        final List<TweetPoll> tweetsByDate = getTweetPoll()
                .retrieveTweetPollByDate(this.secondary.getAccount(), 5, 0,
                        defaultFalseValue, defaultFalseValue, defaultFalseValue,
                        defaultFalseValue, null, "30", new Date());
        assertEquals("Should be equals", 1, tweetsByDate.size());
    }

    /**
     * Retrieve only scheduled Tweetpoll
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     *
     */
    @Test
    public void testRetrieveScheduledTweetPoll()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final DateTime dt = new DateTime();

        final TweetPoll tp = createTweetPollItems(dt.toDate(),
                this.secondary, this.defaultFalseValue,
                this.defaultFalseValue, Boolean.TRUE, Boolean.TRUE);
        tp.setCreateDate(dt.minusDays(1091).toDate());
        getTweetPoll().saveOrUpdate(tp);

        final Long userId = this.secondary.getAccount().getUid();
        // To retrieve all tweetpolls scheduled, period should be 1095(ALLTIME)
        final List<TweetPoll> scheduledTweets = this.retrieveTweetPolls(userId,
                this.MAX_RESULTS, this.INIT_RESULTS, Boolean.FALSE,
                Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, null, "1095");

        assertEquals("Should be equals", 2, scheduledTweets.size());
    }

    /**
     * Test Retrieve scheduled tweetpoll with keyword
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testRetrieveScheduledTweetPollWithAdvancedSearch()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final DateTime dt = new DateTime();
        final TweetPoll tp = createTweetPollItems(
                dt.toDate(),
                this.secondary,
                this.defaultFalseValue, //is completed
                Boolean.TRUE, //is favorite
                Boolean.TRUE,
                Boolean.TRUE);
        tp.setCreateDate(dt.minusDays(5).toDate());
        getTweetPoll().saveOrUpdate(tp);

        final TweetPoll tp2 = createTweetPollItems(
                dt.minusDays(4).toDate(),
                this.secondary,
                this.defaultFalseValue,
                this.defaultFalseValue,
                Boolean.TRUE,
                Boolean.TRUE);

        final Long userId = this.secondary.getAccount().getUid();
        // To retrieve all tweetpolls scheduled, period should be 1095(ALLTIME)
        final List<TweetPoll> scheduledTweets = this.retrieveTweetPolls(userId,
                this.MAX_RESULTS, this.INIT_RESULTS, Boolean.FALSE,
                Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, "What", "7");
        assertEquals("Should be equals", 2, scheduledTweets.size());
        // To retrieve all tweetpolls scheduled, period should be 1095(ALLTIME)

        final List<TweetPoll> scheduledTweets2 = this.retrieveTweetPolls(userId,
                1, this.INIT_RESULTS,
                Boolean.FALSE,
                Boolean.TRUE,
                Boolean.FALSE,
                Boolean.TRUE, "What", "1");
        assertEquals("Should be equals", 1, scheduledTweets2.size());

        final List<TweetPoll> scheduledTweets3 = this.retrieveTweetPolls(userId,
                1, 1,
                Boolean.FALSE,
                Boolean.TRUE,
                Boolean.FALSE,
                Boolean.TRUE, "What", "1");
        assertEquals("Should be equals", 1, scheduledTweets3.size());

        final List<TweetPoll> scheduledTweets4 = this.retrieveTweetPolls(userId,
                0, 0,
                Boolean.FALSE,
                Boolean.TRUE,
                Boolean.FALSE,
                Boolean.TRUE, "What", "1");
        assertEquals("Should be equals", 0, scheduledTweets4.size());


        final List<TweetPoll> scheduledTweets5 = this.retrieveTweetPolls(userId,
                this.MAX_RESULTS, this.INIT_RESULTS, Boolean.FALSE,
                Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, "What12345", "7");
        assertEquals("Should be equals", 0, scheduledTweets5.size());

        final List<TweetPoll> scheduledTweets6 = this.retrieveTweetPolls(userId,
                this.MAX_RESULTS, this.INIT_RESULTS, Boolean.FALSE,
                Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, "What", "1");
        assertEquals("Should be equals", 2, scheduledTweets6.size());
    }
    /**
     *
     * @param userId
     * @param maxResults
     * @param initResults
     * @param isCompleted
     * @param isScheduled
     * @param isFavourite
     * @param isPublished
     * @param keyword
     * @param period
     * @return
     */
    private List<TweetPoll> retrieveTweetPolls(final Long userId,
            final Integer maxResults, final Integer initResults,
            final Boolean isCompleted, final Boolean isScheduled,
            final Boolean isFavourite, final Boolean isPublished,
            final String keyword, final String period
            ) {
        // To retrieve all tweetpolls scheduled, period should be ALLTIME
        final List<TweetPoll> tweetpolls = getTweetPoll()
                .retrieveScheduledTweetPoll(userId, maxResults, initResults,
                        isCompleted, isScheduled, isFavourite, isPublished,
                        keyword, period);
        return tweetpolls;
    }

    /**
     *
     */
    @Test
    public void testgetTweetpollByHashTagName() {
        assertNotNull(this.tweetPoll);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, -15);
        final Calendar calendar2 = Calendar.getInstance();
        final List<TweetPoll> tweetPolls = getTweetPoll()
                .getTweetpollByHashTagName(this.hashTag1.getHashTag(),
                        this.INIT_RESULTS, this.MAX_RESULTS,
                        TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        assertEquals("Should be equals", 1, tweetPolls.size());
        final HashTag hashtag2 = createHashTag("paola");
        final HashTag hashtag3 = createHashTag("juan");
        this.tweetPoll.getHashTags().add(hashtag2);
        this.tweetPoll.getHashTags().add(hashtag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                secondary,
                createQuestion("question1", secondary.getAccount()),
                calendar.getTime());
        tweetPoll1.getHashTags().add(this.hashTag1);
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                secondary,
                createQuestion("question2", secondary.getAccount()),
                calendar2.getTime());
        tweetPoll2.getHashTags().add(this.hashTag1);

        getTweetPoll().saveOrUpdate(tweetPoll1);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        final Calendar calendar3 = Calendar.getInstance();

        final HashMap<Integer, RelativeTimeEnum> hm3 = DateUtil
                .getRelativeTime(tweetPoll1.getCreateDate());

        final List<TweetPoll> tweetPolls2 = getTweetPoll()
                .getTweetpollByHashTagName(this.hashTag1.getHashTag(),
                        this.INIT_RESULTS, this.MAX_RESULTS,
                        TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);

        final Calendar calendar4 = Calendar.getInstance();

        final HashMap<Integer, RelativeTimeEnum> hm4 = DateUtil
                .getRelativeTime(tweetPoll2.getCreateDate());
        assertEquals("Should be equals", 3, tweetPolls2.size());
    }

    @Test
    public void testgetTweetpollByHashTagName2() {

        final Calendar calendar = Calendar.getInstance();

        final HashTag hashtag2 = createHashTag("paola");
        final HashTag hashtag3 = createHashTag("juan");

        getTweetPoll().saveOrUpdate(this.tweetPoll);
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                secondary,
                createQuestion("question1", secondary.getAccount()),
                calendar.getTime());
        tweetPoll1.getHashTags().add(this.hashTag1);

        getTweetPoll().saveOrUpdate(tweetPoll1);

        final TweetPoll tweetPollsbyTag = getTweetPoll()
                .checkIfTweetPollHasHashTag(this.hashTag1.getHashTag(),
                        SearchPeriods.ALLTIME, tweetPoll1.getTweetPollId());

        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                secondary,
                createQuestion("question2", secondary.getAccount()),
                calendar.getTime());

        tweetPoll2.getHashTags().add(hashtag3);
        tweetPoll2.getHashTags().add(this.hashTag1);

        getTweetPoll().saveOrUpdate(tweetPoll2);

        final TweetPoll tweetPollsbyTag2 = getTweetPoll()
                .checkIfTweetPollHasHashTag(this.hashTag1.getHashTag(),
                        SearchPeriods.ALLTIME, tweetPoll2.getTweetPollId());
    }

    @Test
    public void testgetTweetpollByTop() {
        assertNotNull(this.tweetPoll);
        final Integer limit = 4;

        final Calendar calendar = Calendar.getInstance();
        // Create hashtags
        final HashTag hashtag2 = createHashTag("paola");
        final HashTag hashtag3 = createHashTag("juan");
        // Add hashtags to tweetPoll
        this.tweetPoll.getHashTags().add(hashtag2);
        this.tweetPoll.getHashTags().add(hashtag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);
        // Publish tweetpoll
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                secondary,
                createQuestion("question1", secondary.getAccount()),
                calendar.getTime());
        tweetPoll1.setNumbervotes(25L);
        tweetPoll1.getHashTags().add(this.hashTag1);

        final Calendar calendar2 = Calendar.getInstance();

        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                secondary,
                createQuestion("question2", secondary.getAccount()),
                calendar2.getTime());
        tweetPoll2.setNumbervotes(45L);
        tweetPoll2.getHashTags().add(this.hashTag1);

        getTweetPoll().saveOrUpdate(tweetPoll1);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        final List<TweetPoll> tweetPolls2 = getTweetPoll()
                .getTweetpollByHashTagName(this.hashTag1.getHashTag(),
                        this.INIT_RESULTS, this.MAX_RESULTS,
                        TypeSearchResult.HASHTAGRATED, SearchPeriods.ALLTIME);
        assertEquals("Should be equals", 3, tweetPolls2.size());
    }

    /**
     *
     */
    @Test
    public void testGetMaxTweetPollLikeVotes() {

        final Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);

        final Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -2);

        final Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE, -8);

        final Calendar todayDate = Calendar.getInstance();

        final Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DATE, -5);

        // **** First tweetPoll **//
        this.secondary = createUserAccount("jhon", createAccount());
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("question1", secondary.getAccount()),
                calendar1.getTime());
        tweetPoll1.setLikeVote(25L);
        tweetPoll1.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll1);

        // **** Second tweetPoll **//
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("question2", secondary.getAccount()),
                calendar2.getTime());
        tweetPoll2.setLikeVote(35L);
        tweetPoll2.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        // **** Third tweetPoll **//
        final TweetPoll tweetPoll3 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("question3", secondary.getAccount()),
                calendar3.getTime());
        tweetPoll3.setLikeVote(45L);
        tweetPoll3.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll3);

        // Get Max value
        final Long maxValueLike = getTweetPoll()
                .getMaxTweetPollLikeVotesbyUser(this.secondary.getUid());
         Assert.assertNotNull(maxValueLike);
    }

    /**
     * Test get tweetPolls.
     */
    @Test
    public void testGetTweetPolls() {
        final Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);

        final Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -2);

        final Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE, -8);

        final Calendar todayDate = Calendar.getInstance();

        final Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DATE, -5);

        // **** First tweetPoll **//
        this.secondary = createUserAccount("jhon", createAccount());
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("question1", secondary.getAccount()),
                calendar1.getTime());
        tweetPoll1.setLikeVote(25L);
        tweetPoll1.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll1);

        // **** Second tweetPoll **//
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("question2", secondary.getAccount()),
                todayDate.getTime());
        tweetPoll2.setLikeVote(35L);
        tweetPoll2.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        final List<TweetPoll> tpList = getTweetPoll().getTweetPolls(30, 0,
                calendar2.getTime());
        Assert.assertEquals("Should be", 3, tpList.size());
    }

    /**
     * Test Get total social accounts published.
     */
    @Test
    public void testGetTotalSocialAccountsPublished() {
        final TweetPoll tp1 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("question1", secondary.getAccount()), new Date());
        assertNotNull(tp1);
        final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tp1, " ", socialAccount, tweetContent);
        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);
        final List<TweetPollSavedPublishedStatus> tpsavedPublished = getTweetPoll()
                .getLinksByTweetPoll(tp1, null, null,
                        TypeSearchResult.TWEETPOLL);
        Assert.assertEquals("Should be", 1, tpsavedPublished.size());
    }


    /**
     * Test get total tweetpolls by user.
     */
    @Test
    public void testGetTotalTweetPollsbyUser() {
        final Question question = createQuestion("Who I am?", "");
        final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
        assertNotNull(tp);
        final Long totalTweets = getTweetPoll().getTotalTweetPoll(
                this.secondary, Boolean.TRUE);
        Assert.assertEquals("Should be", 2, totalTweets.intValue());
    }

    /**
     * Test Get total social links by type.
     */
    @Test
    public void testGetTotalLinksByType() {
        // TweePoll 1
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary,
                createQuestion("What is your favorite pastime?",
                        secondary.getAccount()), new Date());
        assertNotNull(tweetPoll);

        final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);

        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);

        final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        getTweetPoll().saveOrUpdate(tpSaved2);
        assertNotNull(tpSaved2);
        final Long tweetPollSocialLinks = getTweetPoll().getSocialLinksByType(
                tweetPoll, null, null, TypeSearchResult.TWEETPOLL);

        Assert.assertEquals("Should be", 2, tweetPollSocialLinks.intValue());
    }

    /**
     * Test get total tweetpolls published by hashtag.
     */
    @Test
    public void testGetTweetPollsbyHashTagNameAndDateRange() {
        final HashTag myHashTag = createHashTag("preferences");
        final Calendar releaseDate = Calendar.getInstance();
        releaseDate.add(Calendar.DATE, -2);
        final Question myFirstQuestion = createQuestion(
                "What is your favorite kind of movie?", secondary.getAccount());
        final Question mySecondQuestion = createQuestion(
                "What is your favorite kind of song?", secondary.getAccount());
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary,
                myFirstQuestion,
                releaseDate.getTime());
        tweetPoll.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll);
        assertNotNull(tweetPoll);
        releaseDate.add(Calendar.DATE, -4);
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                this.secondary,
                mySecondQuestion,
                releaseDate.getTime());
        tweetPoll2.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll2);
        assertNotNull(tweetPoll2);

        final List<TweetPoll> tweetPollsbyHashTag = getTweetPoll()
                .getTweetPollsbyHashTagNameAndDateRange(myHashTag.getHashTag(),
                        SearchPeriods.SEVENDAYS);
        Assert.assertEquals("Should be", 2, tweetPollsbyHashTag.size());
    }

    /**
     * Test Get social networks links by type and date range.
     */
    @Test
    public void testGetSocialLinksByTypeAndDateRange() {
        final Calendar myCalendarDate = Calendar.getInstance();

        // TweePoll 1
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary,
                createQuestion("What is your favorite pastime?",
                        secondary.getAccount()), myCalendarDate.getTime());
        assertNotNull(tweetPoll);

        final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);

        tpSaved.setApiType(SocialProvider.TWITTER);
        tpSaved.setPublicationDateTweet(myCalendarDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);

        myCalendarDate.add(Calendar.MONTH, -2);
        final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        tpSaved2.setPublicationDateTweet(myCalendarDate.getTime());
        getTweetPoll().saveOrUpdate(tpSaved2);
        assertNotNull(tpSaved2);
        final List<TweetPollSavedPublishedStatus> tweetPollSocialLinks = getTweetPoll().getSocialLinksByTypeAndDateRange(tweetPoll, null, null,
                TypeSearchResult.TWEETPOLL);
        Assert.assertEquals("Should be", 2, tweetPollSocialLinks.size());
    }


    /**
     * Test finding the distance between two coordinate points.
     */
    @Test
    public void testRetrieveTweetPollsBySearchRadiusOfGeoLocation() {
        final Calendar myCalendarDate = Calendar.getInstance();
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary,
                createQuestion("What is your favorite pastime?",
                        secondary.getAccount()), myCalendarDate.getTime());

        tweetPoll.setLocationLatitude(40.4167F);
        tweetPoll.setLocationLongitude(-3.70325F);
        getTweetPoll().saveOrUpdate(tweetPoll);
        assertNotNull(tweetPoll);

        final TweetPoll tp1 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("What is your favorite movie?",
                        secondary.getAccount()), myCalendarDate.getTime());

        tp1.setLocationLatitude(39.4167F);
        tp1.setLocationLongitude(-2.70325F);

        getTweetPoll().saveOrUpdate(tp1);
        assertNotNull(tp1);

        DateTime otherDate = new DateTime();
        final DateTime creationDate = otherDate.minusDays(9);
        final TweetPoll tp2 = createPublishedTweetPoll(
                this.secondary,
                createQuestion("What is your favorite actor?",
                        secondary.getAccount()), myCalendarDate.getTime());

        tp2.setLocationLatitude(40.4167F);
        tp2.setLocationLongitude(-2.70325F);
        tp2.setCreateDate(creationDate.toDate());
        getTweetPoll().saveOrUpdate(tp2);
        assertNotNull(tp2);
        final double latiRadian = Math.toRadians(41.3879169F);
        final double longRadian = Math.toRadians(2.16991870F);

        final List<Object[]> distanceFromOrigin = getTweetPoll()
                .retrieveTweetPollsBySearchRadiusOfGeoLocation(latiRadian,
                        longRadian, 510d, 6378, 10, TypeSearchResult.TWEETPOLL,
                        SearchPeriods.SEVENDAYS);
        Assert.assertEquals("Should be", 2, distanceFromOrigin.size());
    }

    /**
     * Test Retrieve tweetpolls published/unpublished.
     */
    @Test
    public void testRetrievePublishedUnpublishedTweetPolls() {
        createPublishedTweetPoll(secondary, this.question);
        final List<TweetPoll> tweetPollsPublished = getTweetPoll()
                .retrievePublishedUnpublishedTweetPoll(
                        this.secondary.getAccount(), 10, 0, Boolean.TRUE);
        Assert.assertEquals("Should be", 2, tweetPollsPublished.size());
        createNotPublishedTweetPoll(secondary, this.question);
        final List<TweetPoll> tweetPollsUnpublished = getTweetPoll()
                .retrievePublishedUnpublishedTweetPoll(
                        this.secondary.getAccount(), 10, 0, Boolean.FALSE);
        Assert.assertEquals("Should be", 1, tweetPollsUnpublished.size());
    }

    /**
     * Test Retrieve completed/incompleted tweetpolls.
     */
    @Test
    public void testRetrieveCompletedTweetPolls() {
        createPublishedTweetPoll(secondary, this.question);
        final List<TweetPoll> completedTweetpolls = getTweetPoll()
                .retrieveCompletedTweetPoll(this.secondary.getAccount(), 10, 0,
                        Boolean.TRUE);
        Assert.assertEquals("Should be", 1, completedTweetpolls.size());
        final TweetPoll tpoll = createPublishedTweetPoll(
                secondary, this.question);
        final List<TweetPoll> inCompletedTweetpolls = getTweetPoll()
                .retrieveCompletedTweetPoll(this.secondary.getAccount(), 10, 0,
                        Boolean.FALSE);
        Assert.assertEquals("Should be", 2, inCompletedTweetpolls.size());
    }

    /**
     * Test Advanced search tweetpolls.
     */
    @Test
    public void testTweetPollAdvancedSearch() {
        final DateTime time1 = new DateTime();
        final DateTime time2 = time1.minusDays(8);
        // published - completed - scheduled
        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("cabeza"), Boolean.TRUE, Boolean.TRUE,
                Boolean.FALSE, new Date());

        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("cara"), Boolean.TRUE, Boolean.TRUE,
                Boolean.FALSE, new Date());
        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("pelo"), Boolean.FALSE, Boolean.FALSE,
                Boolean.FALSE, time2.toDate());
        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("cana"), Boolean.TRUE, Boolean.TRUE,
                Boolean.FALSE, new Date());
        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("lentes"), Boolean.FALSE, Boolean.TRUE,
                Boolean.FALSE, new Date());
        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("dientes"), Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, time2.toDate());

        /** published - completed - favourite - scheduled **/
        final List<TweetPoll> search1 = getTweetPoll().advancedSearch(
                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
                this.secondary.getAccount(), 0, 10, 7, "ca");
        Assert.assertEquals("Should be", 3, search1.size());

        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("nariz"), Boolean.TRUE, Boolean.TRUE,
                Boolean.TRUE, new Date());
        createAdvancedTweetPoll(secondary,
                createDefaultQuestion("dedos"), Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, new Date());

        final TweetPoll tp = createAdvancedTweetPoll(secondary,
                createDefaultQuestion("boca"), Boolean.TRUE, Boolean.FALSE,
                Boolean.FALSE, time2.toDate());
        tp.setFavourites(Boolean.FALSE);
        getTweetPoll().saveOrUpdate(tp);

        final List<TweetPoll> search2 = getTweetPoll().advancedSearch(
                Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
                this.secondary.getAccount(), 0, 10, 30, "b");
        Assert.assertEquals("Should be", 1, search2.size());

        final List<TweetPoll> search3 = getTweetPoll().advancedSearch(
                Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE,
                this.secondary.getAccount(), 0, 10, 30, "d");
        Assert.assertEquals("Should be", 2, search3.size());

    }

    /** **/
    @Test
    public void testGetSocialLinksByTweetPollSearch() {

        // TweePoll 1
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary,
                createQuestion("What is your favorite pastime 11?",
                        secondary.getAccount()), new Date());
        assertNotNull(tweetPoll);

        final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text 22";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);

        tpSaved.setApiType(SocialProvider.TWITTER);
        tpSaved.setPublicationDateTweet(new Date());
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);
        // /////
        final UserAccount userAccount = createUserAccount("johannes", createAccount());
        final SocialAccount socialAccount2 = createDefaultSettedSocialAccount(userAccount);

        final TweetPollSavedPublishedStatus tpSaved12 = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount2, "TCPTEN3");

        tpSaved12.setApiType(SocialProvider.FACEBOOK);
        tpSaved12.setPublicationDateTweet(new Date());
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved12);

        final TweetPollSavedPublishedStatus tpSaved2 = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        tpSaved2.setPublicationDateTweet(new Date());
        getTweetPoll().saveOrUpdate(tpSaved2);
        assertNotNull(tpSaved2);

        // Enum list providers to search tweetpoll published
        List<SocialProvider> enums = new ArrayList<SocialProvider>();
        enums.add(SocialProvider.FACEBOOK);
        enums.add(SocialProvider.TWITTER);

        final List<SocialAccount> socials = new ArrayList<SocialAccount>();
        socials.add(socialAccount);
        socials.add(socialAccount2);

        final List<TweetPollSavedPublishedStatus> tpsp = getTweetPoll()
                .getSocialLinksByTweetPollSearch(tweetPoll,
                        TypeSearchResult.TWEETPOLL, enums, socials);

    }

    /**
     *
     */
    @Test
    public void testTpollsByHashTagNameAndDateRange() {
        final HashTag myHashTag = createHashTag("preferences");
        final Calendar releaseDate = Calendar.getInstance();

        final Question myFirstQuestion = createQuestion(
                "What is your favorite kind of movie?", secondary.getAccount());
        final Question mySecondQuestion = createQuestion(
                "What is your favorite kind of song?", secondary.getAccount());

        // FIRST TP
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                releaseDate.getTime());
        tweetPoll.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll);
        assertNotNull(tweetPoll);

         //SECOND TP
        releaseDate.add(Calendar.HOUR, -1);
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                this.secondary, mySecondQuestion,
                releaseDate.getTime());
        tweetPoll2.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll2);
        assertNotNull(tweetPoll2);

        // THIRD TP
        releaseDate.add(Calendar.HOUR, -4);
        final TweetPoll tweetPoll3 = createPublishedTweetPoll(
                this.secondary, mySecondQuestion,
                releaseDate.getTime());
        tweetPoll3.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll3);
        assertNotNull(tweetPoll3);

//        // FOURTH TP
        releaseDate.add(Calendar.DATE, -7);
        releaseDate.add(Calendar.HOUR, -5);
        final TweetPoll tweetPoll4 = createPublishedTweetPoll(
                this.secondary, mySecondQuestion,
                releaseDate.getTime());
        tweetPoll4.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll4);
        assertNotNull(tweetPoll4);

        // FIFTH
        releaseDate.add(Calendar.WEEK_OF_MONTH, -7);
        final TweetPoll tweetPoll5 = createPublishedTweetPoll(
                this.secondary, mySecondQuestion,
                releaseDate.getTime());
        tweetPoll5.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll5);
        assertNotNull(tweetPoll5);

        // SIXTH
        releaseDate.add(Calendar.YEAR, -1);
        final TweetPoll tweetPoll6 = createPublishedTweetPoll(
                this.secondary, mySecondQuestion,
                releaseDate.getTime());
        tweetPoll6.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll6);
        assertNotNull(tweetPoll6);

        // SEVENTH
        releaseDate.add(Calendar.YEAR, -2);
        final TweetPoll tweetPoll7 = createPublishedTweetPoll(
                this.secondary, mySecondQuestion,
                releaseDate.getTime());
        tweetPoll7.getHashTags().add(myHashTag);
        getTweetPoll().saveOrUpdate(tweetPoll7);
        assertNotNull(tweetPoll7);

        List<Object[]> tweetPollsbyHashTag = null;

        tweetPollsbyHashTag = getTweetPoll()
                .getTweetPollsRangeStats(myHashTag.getHashTag(), SearchPeriods.ALLTIME);
        Assert.assertEquals("Should be", 6, tweetPollsbyHashTag.size());

        tweetPollsbyHashTag = getTweetPoll()
                 .getTweetPollsRangeStats(myHashTag.getHashTag(),
                         SearchPeriods.TWENTYFOURHOURS);
        Assert.assertEquals("Should be", 3, tweetPollsbyHashTag.size());

       tweetPollsbyHashTag = getTweetPoll()
              .getTweetPollsRangeStats(myHashTag.getHashTag(),
                      SearchPeriods.THIRTYDAYS);
       Assert.assertEquals("Should be", 4, tweetPollsbyHashTag.size());

       tweetPollsbyHashTag = getTweetPoll()
               .getTweetPollsRangeStats(myHashTag.getHashTag(),
                       SearchPeriods.ONEYEAR);
       Assert.assertEquals("Should be", 5, tweetPollsbyHashTag.size());
    }

    /**
     * Test Remove {@link TweetPoll}.
     */
    @Test
    public void testTweetRemove(){

        final Question myFirstQuestion = createQuestion(
                "What is your favorite kind of movie?", secondary.getAccount());
        final Question mySecondQuestion = createQuestion(
                "What is your favorite kind of song?", secondary.getAccount());

        final TweetPollFolder tpFolder = createTweetPollFolder("My Tp1 folder", this.secondary);
        // FIRST TP
        final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary, myFirstQuestion,
                new Date());
        tweetPoll.setTweetPollFolder(tpFolder);
        getTweetPoll().saveOrUpdate(tweetPoll);

        getTweetPoll().delete(tweetPoll);
        final TweetPoll tp = getTweetPoll().getTweetPollById(
                tweetPoll.getTweetPollId());
        final Question quest = getQuestionDaoImp().retrieveQuestionById(
                myFirstQuestion.getQid());
        final Question quest2 = getQuestionDaoImp().retrieveQuestionById(
                mySecondQuestion.getQid());
        final List<TweetPoll> tpollsbyFolder = getTweetPoll()
                .retrieveTweetPollByFolder(this.secondary.getUid(),
                        tpFolder.getId());

        final TweetPollFolder folders = getTweetPoll().getTweetPollFolderById(
                tpFolder.getId());
     }

    /**
     * Test retrieve Favourites tweetpoll.
     */
    @Test
    public void testRetrieveFavouritesTweetPoll(){
        createDefaultTweetPollPublicated(Boolean.TRUE,
                Boolean.TRUE, Boolean.TRUE, this.secondary, this.question,
                this.initDate.toDate());

        final Question newQuest = createQuestion("Favorite color?", this.account);

        createDefaultTweetPollPublicated(Boolean.TRUE,
                Boolean.TRUE, Boolean.TRUE, this.secondary, newQuest,
                this.initDate.minusDays(5).toDate());


        final List<TweetPoll> tpolls = getTweetPoll()
                .retrieveFavouritesTweetPoll(this.account, 10, 0, Boolean.TRUE,
                        Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, "", "24");


        final List<TweetPoll> tpolls2 = getTweetPoll()
                .retrieveFavouritesTweetPoll(this.account, 10, 0, Boolean.TRUE,
                        Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, "Who", " ");

        final List<TweetPoll> tpolls3 = getTweetPoll()
                .retrieveFavouritesTweetPoll(this.account, 10, 0, Boolean.TRUE,
                        Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, "col", " ");


        Assert.assertEquals("Should be", 3, tpolls.size());
        Assert.assertEquals("Should be", 2, tpolls2.size());
        Assert.assertEquals("Should be", 1, tpolls3.size());
     }

    /**
     * Test
     */
    public void testGetListAnswerTweetSwitch(){
//    	final QuestionAnswer qAnsw =
//    	final List<TweetPollSwitch> getListAnswers = getTweetPoll().getAnswerTweetSwitch(questionAnswer);
    }


    /**
     * Test to retrieve all scheduled items.
     */
    @Test
    public void testScheduledItems(){

    final SocialAccount socialAcc2 = createDefaultSettedSocialAccount(this.secondary);

    // Create Tweetpoll
    final TweetPoll tpoll = createDefaultTweetPollPublicated(Boolean.TRUE,
            Boolean.TRUE, Boolean.TRUE, this.secondary, this.question,
            this.initDate.toDate());
    final DateTime secondDate = new DateTime();
    // Create Scheduled items

    createTweetpollSchedule(tpoll, secondDate.toDate(), socialAcc2, Status.FAILED, TypeSearchResult.TWEETPOLL);
    createTweetpollSchedule(tpoll, secondDate.minusDays(2).toDate(), socialAcc2, Status.FAILED, TypeSearchResult.TWEETPOLL);

    createTweetpollSchedule(tpoll, secondDate.minusDays(3).toDate(), socialAcc2, Status.FAILED, TypeSearchResult.TWEETPOLL);

    createTweetpollSchedule(tpoll, secondDate.plusDays(1).toDate(), socialAcc2, Status.FAILED, TypeSearchResult.TWEETPOLL);
    createTweetpollSchedule(tpoll, secondDate.minusDays(7).toDate(), socialAcc2, Status.FAILED, TypeSearchResult.TWEETPOLL);
    //
    final Date minimumDate = getScheduleDao().retrieveMinimumScheduledDate(Status.FAILED);

    // Retrieve Scheduled items with Status FAILED
    final List<Schedule> allScheduled = getScheduleDao().retrieveScheduled(Status.FAILED, minimumDate);

    Assert.assertEquals("Should be", 4, allScheduled.size());
    }

    /**
     * Retrieve items to remove
     */
    @Test
    public void testRetrieveFailedScheduledItems(){

        createTweetpollScheduleDefault(this.tweetPoll, this.initDate.toDate(),
                this.socialAccount, Status.SUCCESS, TypeSearchResult.TWEETPOLL,
                1);
        createTweetpollScheduleDefault(this.tweetPoll,
                this.initDate.minusDays(2).toDate(), this.socialAccount,
                Status.FAILED, TypeSearchResult.TWEETPOLL, 3);

        createTweetpollScheduleDefault(this.tweetPoll, this.initDate.toDate(),
                this.socialAccount, Status.FAILED, TypeSearchResult.TWEETPOLL,
                5);

        final List<Schedule> list = getScheduleDao().retrieveFailedScheduledItems(5, Status.SUCCESS);
        Assert.assertEquals("Should be", 2, list.size());
    }

    /**
     * Test Retrieve TweeetPollSavedPublished by Id.
     */
    @Test
    public void testRetrieveTweetPollPublishedStatusbyId(){
        final TweetPollSavedPublishedStatus tpSavedPublished = createTweetPollSavedPublishStatus(this.tweetPoll, this.socialAccount, SocialProvider.FACEBOOK );
        final TweetPollSavedPublishedStatus tweetpollSaved = getTweetPoll().getTweetPollPublishedStatusbyId(tpSavedPublished.getId());
        assertNotNull(tweetpollSaved);
    }

    /**
     * Retrieve Answer
     */
    @Test
    public void testRetrieveAnswerTweetSwitch(){
        final TweetPollSwitch tpSwitch = getTweetPoll().getAnswerTweetSwitch(this.tweetPoll, this.questionsAnswers1);
        assertNotNull(tpSwitch);
    }
}
