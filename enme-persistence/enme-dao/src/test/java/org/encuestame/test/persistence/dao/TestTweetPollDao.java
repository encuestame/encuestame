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

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.RelativeTimeEnum;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test {@link TweetPollDao}..
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 13, 2010 11:57:17 PM
 */
@Category(DefaultTest.class)
public class TestTweetPollDao  extends AbstractBase{

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

    /**
     * Before.
     */
    @Before
    public void initData(){
      this.secondary = createUserAccount("jhonny", createAccount());
      this.question = createQuestion("Who I am?", "");
      this.questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
      this.questionsAnswers2 = createQuestionAnswer("no", question, "12346");
      this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), this.question);
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
      this.tweetPollFolder = createTweetPollFolder("First TweetPoll Folder", secondary);
      tweetPoll.setNumbervotes(65L);
    }

    /**
     * Test retrieveTweetsPollSwitch.
     */
    @Test
    public void testRetrieveTweetsPollSwitch(){
        //System.out.println("----------");
        final TweetPollSwitch pollSwitch = getTweetPoll().retrieveTweetsPollSwitch(this.pollSwitch1.getCodeTweet());
        assertNotNull(pollSwitch);
    }

    /**
     * Test getResultsByTweetPoll.
     */
    @Test
    public void testgetResultsByTweetPoll(){
        final List<Object[]> results = getTweetPoll().getResultsByTweetPoll(tweetPoll, this.questionsAnswers1);
        assertEquals("Should be equals", 1,  results.size());
        assertEquals("Should be equals", "yes",  results.get(0)[0]);
        assertEquals("Should be equals", "2", results.get(0)[1].toString());
    }

    /**
     * Test Get Total Votes by TweetPoll
     */
    @Test
    public void testgetTotalVotesByTweetPoll(){
        final List<Object[]>  pollSwitchs = getTweetPoll().getTotalVotesByTweetPoll(this.tweetPoll.getTweetPollId());
        assertEquals("Should be equals", 2, pollSwitchs.size());
    }

    @Test
    public void testgetVotesByTweetPollId(){
        final Long totalVotes = getTweetPoll().getTotalVotesByTweetPollId(this.tweetPoll.getTweetPollId());
        assertEquals("Should be equals", 4, totalVotes.intValue());
    }

    /**
     * Test to get total votes by tweetpoll and specific date range.
     */
    @Test
	public void testGetTotalVotesByTweetPollIdAndDateRange(){
    	final Question myQuestion = createQuestion("Where are you from?", "");
    	final QuestionAnswer qaAmerica = createQuestionAnswer("America", myQuestion, "123457");
    	final QuestionAnswer qaEurope = createQuestionAnswer("Europa", myQuestion, "123469");
    	final TweetPoll myTweetPoll = createPublishedTweetPoll(secondary.getAccount(), myQuestion);
    	HashTag htCitizen = createHashTag("citizen");
    	HashTag htCitizenShip = createHashTag("citizenship");
    	myTweetPoll.getHashTags().add(htCitizen);
    	myTweetPoll.getHashTags().add(htCitizenShip);
    	getTweetPoll().saveOrUpdate(myTweetPoll);

    	TweetPollSwitch pollSwitchAmerica = createTweetPollSwitch(qaAmerica, myTweetPoll);
    	TweetPollSwitch pollSwitchEurope = createTweetPollSwitch(qaEurope, myTweetPoll);

    	final Calendar pollingDate = Calendar.getInstance();
    	pollingDate.add(Calendar.MONTH, -1);

    	final TweetPollResult tpResultAmerica =  createTweetPollResultWithPollingDate(pollSwitchAmerica, "192.168.0.1", pollingDate.getTime());

    	pollingDate.add(Calendar.MONTH, -5);
    	final TweetPollResult tpResultAmerica2 =  createTweetPollResultWithPollingDate(pollSwitchAmerica, "192.168.0.2", pollingDate.getTime());

    	final TweetPollResult tpResultEurope =  createTweetPollResultWithPollingDate(pollSwitchEurope, "192.168.0.2", pollingDate.getTime());

    	final Long totalVotes = getTweetPoll().getTotalVotesByTweetPollIdAndDateRange(myTweetPoll.getTweetPollId(), SearchPeriods.ONEYEAR.toDays());
    	assertEquals("Should be equals", 3, totalVotes.intValue());


    }

    /**
     * Test get all {@link TweetPollResult} by {@link TweetPollSwitch}
     */
    @Test
    public void testGetTweetPollResultsByTweetPollSwitch(){
    	final List<TweetPollResult> tpResults = getTweetPoll().getTweetPollResultsByTweetPollSwitch(this.pollSwitch1);
    	assertEquals("Should be equals", 2, tpResults.size());
    }

    /**
     * Test retrieve  counter value from {@link TweetPollResult} by {@link TweetPollSwitch}.
     */
    @Test
    public void testGetTotalTweetPollResultByTweetPollSwitch(){
    	final Long myvalue = this.getTweetPoll().getTotalTweetPollResultByTweetPollSwitch(pollSwitch1, SearchPeriods.ONEYEAR);
    	// See @Before on the top
    	assertEquals("Should be equals", 2,  myvalue.intValue());
    }

    /**
     * Test Get TweetPoll by TweetPoll Id and User.
     */
    @Test
    public void testGetTweetPollByIdandUserId(){
        assertNotNull(tweetPoll);
        assertNotNull(secondary);
        final TweetPoll tp = getTweetPoll().getTweetPollByIdandUserId(this.tweetPoll.getTweetPollId(), secondary.getAccount().getUid());
        assertEquals("Should be equals", 1, 1);
        assertEquals("Should be equals", this.tweetPoll.getTweetPollId(), tp.getTweetPollId());
    }

    /**
     * Test Get TweetPoll Folder by FolderId and User.
     */
    @Test
    public void testGetTweetPollFolderByIdandUser(){
        assertNotNull(this.tweetPollFolder);
        final TweetPollFolder tpf = getTweetPoll().getTweetPollFolderByIdandUser(this.tweetPollFolder.getId(), secondary.getAccount());
        assertEquals("Should be equals", this.tweetPollFolder.getId(), tpf.getId());
     }

    /**
     * Test Get TweetPoll Folder by folderId.
     */
    @Test
    public void testGetTweetPollFolderById(){
        assertNotNull(tweetPollFolder);
        final TweetPollFolder tpf = getTweetPoll().getTweetPollFolderById(this.tweetPollFolder.getId());
        assertNotNull(tpf);
     }

    /**
     * Test Retrieve TweetPoll Folder by User.
     */
    @Test
    public void testRetrieveTweetPollFolderByUserId(){
        assertNotNull(tweetPollFolder);
        assertNotNull(secondary);
        final List<TweetPollFolder> tpfu = getTweetPoll().retrieveTweetPollFolderByAccount(this.secondary.getAccount());
        assertEquals("Should be equals", 1, tpfu.size());
    }

    /**
     * Test Retrieve TweetPoll by Folder.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testRetrieveTweetPollByFolder() throws EnMeNoResultsFoundException {
        final Long user = this.secondary.getAccount().getUid();
        assertNotNull(tweetPollFolder);
        assertNotNull(tweetPoll);
        final TweetPoll addTweetPoll = addTweetPollToFolder(this.tweetPollFolder.getId(), user, this.tweetPoll.getTweetPollId());
        assertNotNull(addTweetPoll);
        final List<TweetPoll> tpfolder = getTweetPoll().retrieveTweetPollByFolder(user, this.tweetPollFolder.getId());
        assertEquals("Should be equals", 1, tpfolder.size());
    }

    /**
     * Test Retrieve Tweets By User.
     */
    @Test
    public void testRetrieveTweetsByUserId(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final List<TweetPoll> tweets = getTweetPoll().retrieveTweetsByUserId(userId, 5, 0);
        assertEquals("Should be equals", 1, tweets.size());
    }

    /**
     * Test Retrieve Tweets by
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testRetrieveTweetsByQuestionName() throws NoSuchAlgorithmException, UnsupportedEncodingException{
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
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.TRUE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.TRUE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.TRUE, Boolean.TRUE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		// 24 hours
		creationDate = creationDate.minusDays(3);
		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE, Boolean.TRUE	);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);


		creationDate = creationDate.minusDays(2);


		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		creationDate = creationDate.minusDays(4);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

		this.createTweetPollItems(creationDate.toDate(),
				this.secondary.getAccount(), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);

        // Search Tweetpolls by keyword - Period 24
		// Completed - Scheduled - Favourite - Published
		final List<TweetPoll> tweetpollsResults = getTweetPoll()
				.retrieveTweetsByQuestionName(keyword, userId,
						this.MAX_RESULTS, this.INIT_RESULTS, Boolean.TRUE,
						Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, 24);
		 assertEquals("Should be equals", 3, tweetpollsResults.size());

		final List<TweetPoll> tweetpollsResultsLastWeek = getTweetPoll()
				.retrieveTweetsByQuestionName(keyword, userId,
						this.MAX_RESULTS, this.INIT_RESULTS, Boolean.TRUE,
						Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, 7);
		 assertEquals("Should be equals", 9, tweetpollsResultsLastWeek.size());

		final List<TweetPoll> tweetpollsResultsLastMonth = getTweetPoll()
				.retrieveTweetsByQuestionName(keyword, userId,
						this.MAX_RESULTS, this.INIT_RESULTS, Boolean.TRUE,
						Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, 30);
		 assertEquals("Should be equals", 11, tweetpollsResultsLastMonth.size());
    }


    /**
     * Test Retrieve TweetPoll Today.
     */
    @Test
    public void testRetrieveTweetPollToday(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        System.out.println("Secondary account --->" + this.secondary.getAccount());
        final List<TweetPoll> tweetsToday = getTweetPoll().retrieveTweetPollToday(this.secondary.getAccount(), 10, 0, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "who", 24);
        assertEquals("Should be equals", 1, tweetsToday.size());
    }

    /**
     * Test Retrieve tweetpoll by date.
     */
    @Test
    public void testRetrieveTweetPollByDate(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final DateMidnight initDate = new DateMidnight();
		final List<TweetPoll> tweetsByDate = getTweetPoll()
				.retrieveTweetPollByDate(this.secondary.getAccount(),
						initDate.toDate(), 5, 0, null, null, null, null, null,
						null);
        		//(this.secondary.getAccount(), initDate.toDate(), 5, 0);
        assertEquals("Should be equals", 1, tweetsByDate.size());
    }

    /**
     * Test Retrieve TweetPoll Last Week
     */
    @Test
    public void testRetrieveFavouritesTweetPoll(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
     //   final List<TweetPoll> favouritesTweets = getTweetPoll().retrieveFavouritesTweetPoll(this.secondary.getAccount(), 5, 0);
     //   assertEquals("Should be equals", 1, favouritesTweets.size());
    }

    /**
     *
     */
    @Test
    public void testRetrieveScheduledTweetPoll(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
      //  final List<TweetPoll> scheduledTweets = getTweetPoll().retrieveScheduledTweetPoll(userId, 5, 0);
    //    assertEquals("Should be equals", 1, scheduledTweets.size());
    }

    /**
     *
     */
    @Test
    public void testgetTweetpollByHashTagName(){
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
                secondary.getAccount(),
                createQuestion("question1", secondary.getAccount()), calendar.getTime());
        tweetPoll1.getHashTags().add(this.hashTag1);
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                secondary.getAccount(),
                createQuestion("question2", secondary.getAccount()), calendar2.getTime());
        tweetPoll2.getHashTags().add(this.hashTag1);

        getTweetPoll().saveOrUpdate(tweetPoll1);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        final Calendar calendar3 = Calendar.getInstance();
        //System.out.println("THIRD CALENDAR --> "+calendar3.getTime());

        final HashMap<Integer, RelativeTimeEnum> hm3 = DateUtil.getRelativeTime(tweetPoll1.getCreateDate());
        //System.out.println("HM 3 ---------->"+hm3);

		final List<TweetPoll> tweetPolls2 = getTweetPoll()
				.getTweetpollByHashTagName(this.hashTag1.getHashTag(),
						this.INIT_RESULTS, this.MAX_RESULTS,
						TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        //System.out.println("------------- HASH TAG NAME---------> " + this.hashTag1.getHashTag());


        final Calendar calendar4 = Calendar.getInstance();
        //System.out.println(calendar.getTime());

        final HashMap<Integer, RelativeTimeEnum> hm4 = DateUtil.getRelativeTime(tweetPoll2.getCreateDate());
        //System.out.println("HM---------->"+hm4);

        for (TweetPoll tweetPoll : tweetPolls2) {
             //System.out.println(" TWITS BY HASHTAG --> " + tweetPoll.getQuestion().getQuestion() + "Published -->" + tweetPoll.getCreateDate());
        }
        assertEquals("Should be equals", 3, tweetPolls2.size());
    }


	@Test
	public void testgetTweetpollByHashTagName2() {

		final Calendar calendar = Calendar.getInstance();

		final HashTag hashtag2 = createHashTag("paola");
		final HashTag hashtag3 = createHashTag("juan");

		getTweetPoll().saveOrUpdate(this.tweetPoll);
		final TweetPoll tweetPoll1 = createPublishedTweetPoll(
				secondary.getAccount(),
				createQuestion("question1", secondary.getAccount()),
				calendar.getTime());
		tweetPoll1.getHashTags().add(this.hashTag1);

		getTweetPoll().saveOrUpdate(tweetPoll1);
		//System.out.println(" TP 1 -->" +tweetPoll1.getTweetPollId());

		final TweetPoll tweetPollsbyTag = getTweetPoll()
				.checkIfTweetPollHasHashTag(this.hashTag1.getHashTag(),  SearchPeriods.ALLTIME,
						tweetPoll1.getTweetPollId());

		//System.out.println(" TP Result 1 -->" +tweetPollsbyTag);

		// SIN TP Correcto

		  final TweetPoll tweetPoll2 = createPublishedTweetPoll(
	                secondary.getAccount(),
	                createQuestion("question2", secondary.getAccount()), calendar.getTime());

	    	tweetPoll2.getHashTags().add(hashtag3);
	    	tweetPoll2.getHashTags().add(this.hashTag1);

			getTweetPoll().saveOrUpdate(tweetPoll2);
			//System.out.println(" TP 2 -->" +tweetPoll2.getTweetPollId());

		final TweetPoll tweetPollsbyTag2 = getTweetPoll()
				.checkIfTweetPollHasHashTag(this.hashTag1.getHashTag(), SearchPeriods.ALLTIME,
						tweetPoll2.getTweetPollId());

		//System.out.println(" TP RESULT 2 -->" +tweetPollsbyTag2);
	}

    @Test
    public void testgetTweetpollByTop(){
        assertNotNull(this.tweetPoll);
        final Integer limit = 4;

        final Calendar calendar = Calendar.getInstance();
        //  Create hashtags
        final HashTag hashtag2 = createHashTag("paola");
        final HashTag hashtag3 = createHashTag("juan");
        // Add hashtags to tweetPoll
        this.tweetPoll.getHashTags().add(hashtag2);
        this.tweetPoll.getHashTags().add(hashtag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);
        // Publish tweetpoll
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                secondary.getAccount(),
                createQuestion("question1", secondary.getAccount()), calendar.getTime());
        tweetPoll1.setNumbervotes(25L);
        tweetPoll1.getHashTags().add(this.hashTag1);

        final Calendar calendar2 = Calendar.getInstance();

        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                secondary.getAccount(),
                createQuestion("question2", secondary.getAccount()), calendar2.getTime());
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

        // System.out.println("Date From -->"+ dateFrom.getTime() + " \n");
        // System.out.println("Today date -->"+ todayDate.getTime() + " \n");

        // **** First tweetPoll **//
        this.secondary = createUserAccount("jhon", createAccount());
        final TweetPoll tweetPoll1 = createPublishedTweetPoll(
                this.secondary.getAccount(),
                createQuestion("question1", secondary.getAccount()),
                calendar1.getTime());
        tweetPoll1.setLikeVote(25L);
        tweetPoll1.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll1);

        // **** Second tweetPoll **//
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                this.secondary.getAccount(),
                createQuestion("question2", secondary.getAccount()),
                calendar2.getTime());
        tweetPoll2.setLikeVote(35L);
        tweetPoll2.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        // **** Third tweetPoll **//
        final TweetPoll tweetPoll3 = createPublishedTweetPoll(
                this.secondary.getAccount(),
                createQuestion("question3", secondary.getAccount()),
                calendar3.getTime());
        tweetPoll3.setLikeVote(45L);
        tweetPoll3.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll3);

        // Get Max value
        final Long maxValueLike = getTweetPoll()
                .getMaxTweetPollLikeVotesbyUser(this.secondary.getUid());

        //System.out.println("Max tweetPoll like vote : " + maxValueLike);
        Assert.assertNotNull(maxValueLike);
    }

    /**
    * Test get tweetPolls.
    */
    @Test
    public void testGetTweetPolls(){
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
                this.secondary.getAccount(),
                createQuestion("question1", secondary.getAccount()),
                calendar1.getTime());
        tweetPoll1.setLikeVote(25L);
        tweetPoll1.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll1);

        // **** Second tweetPoll **//
        final TweetPoll tweetPoll2 = createPublishedTweetPoll(
                this.secondary.getAccount(),
                createQuestion("question2", secondary.getAccount()),
                todayDate.getTime());
        tweetPoll2.setLikeVote(35L);
        tweetPoll2.setEditorOwner(this.secondary);
        getTweetPoll().saveOrUpdate(tweetPoll2);

        final List<TweetPoll> tpList = getTweetPoll().getTweetPolls(30, 0, calendar2.getTime());
        Assert.assertEquals("Should be", 3, tpList.size());
    }

    /**
     * Test Get total social accounts published.
     */
    @Test
    public void testGetTotalSocialAccountsPublished() {
        final TweetPoll tp1 = createPublishedTweetPoll(
                this.secondary.getAccount(),
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
                .getLinksByTweetPoll(tp1 , null, null, TypeSearchResult.TWEETPOLL);
        Assert.assertEquals("Should be", 1, tpsavedPublished.size());
    }

    /**
     * Test get total tweetpolls by user.
     */
    @Test
    public void testGetTotalTweetPollsbyUser(){
         final Question question = createQuestion("Who I am?", "");
         final TweetPoll tp = createPublishedTweetPoll(question, this.secondary);
         assertNotNull(tp);
         final Long totalTweets = getTweetPoll().getTotalTweetPoll(this.secondary, Boolean.TRUE);
         Assert.assertEquals("Should be", 1, totalTweets.intValue());
    }

    /**
     * Test Get total social links by type.
     */
    @Test
    public void testGetTotalLinksByType() {
        // TweePoll 1
    	final TweetPoll tweetPoll = createPublishedTweetPoll(
                this.secondary.getAccount(),
                createQuestion("What is your favorite pastime?", secondary.getAccount()), new Date());
        assertNotNull(tweetPoll);

        final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.secondary);
        assertNotNull(socialAccount);
        final String tweetContent = "Tweet content text";
        final TweetPollSavedPublishedStatus tpSaved = createTweetPollSavedPublishedStatus(
                tweetPoll, " ", socialAccount, tweetContent);

        tpSaved.setApiType(SocialProvider.TWITTER);
        getTweetPoll().saveOrUpdate(tpSaved);
        assertNotNull(tpSaved);

        final TweetPollSavedPublishedStatus tpSaved2= createTweetPollSavedPublishedStatus(
        		tweetPoll, " ", socialAccount, tweetContent);
        tpSaved2.setApiType(SocialProvider.FACEBOOK);
        getTweetPoll().saveOrUpdate(tpSaved2);
        assertNotNull(tpSaved2);
        final Long tweetPollSocialLinks = getTweetPoll().getSocialLinksByType(tweetPoll, null, null, TypeSearchResult.TWEETPOLL);

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
				this.secondary.getAccount(), myFirstQuestion,
				releaseDate.getTime());
		tweetPoll.getHashTags().add(myHashTag);
		getTweetPoll().saveOrUpdate(tweetPoll);
		assertNotNull(tweetPoll);
		releaseDate.add(Calendar.DATE, -4);
		final TweetPoll tweetPoll2 = createPublishedTweetPoll(
				this.secondary.getAccount(), mySecondQuestion,
				releaseDate.getTime());
		tweetPoll2.getHashTags().add(myHashTag);
		getTweetPoll().saveOrUpdate(tweetPoll2);
		assertNotNull(tweetPoll2);

		final List<TweetPoll> tweetPollsbyHashTag = getTweetPoll()
				.getTweetPollsbyHashTagNameAndDateRange(myHashTag.getHashTag(), SearchPeriods.SEVENDAYS);
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
				this.secondary.getAccount(),
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
		//final Long tweetPollSocialLinks = getTweetPoll()
		//		.getSocialLinksByTypeAndDateRange(tweetPoll, null, null,
		//					TypeSearchResult.TWEETPOLL, 365, 0, this.MAX_RESULTS);
		//Assert.assertEquals("Should be", 2, tweetPollSocialLinks.intValue());
	}

	/**
	 * Test finding the distance between two coordinate points.
	 */
	@Test
	public void testRetrieveTweetPollsBySearchRadiusOfGeoLocation() {
		final Calendar myCalendarDate = Calendar.getInstance();
		final TweetPoll tweetPoll = createPublishedTweetPoll(
				this.secondary.getAccount(),
				createQuestion("What is your favorite pastime?",
						secondary.getAccount()), myCalendarDate.getTime());

		tweetPoll.setLocationLatitude(40.4167F);
		tweetPoll.setLocationLongitude(-3.70325F);
		getTweetPoll().saveOrUpdate(tweetPoll);
		assertNotNull(tweetPoll);

		final TweetPoll tp1 = createPublishedTweetPoll(
				this.secondary.getAccount(),
				createQuestion("What is your favorite movie?",
						secondary.getAccount()), myCalendarDate.getTime());

		tp1.setLocationLatitude(39.4167F);
		tp1.setLocationLongitude(-2.70325F);

		getTweetPoll().saveOrUpdate(tp1);
		assertNotNull(tp1);

		DateTime otherDate = new DateTime();
		final DateTime creationDate = otherDate.minusDays(9);
		final TweetPoll tp2 = createPublishedTweetPoll(
				this.secondary.getAccount(),
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
						longRadian, 510d, 6378, 10, TypeSearchResult.TWEETPOLL, SearchPeriods.SEVENDAYS);
		Assert.assertEquals("Should be", 2, distanceFromOrigin.size());

		/*for (Object[] objects : distanceFromOrigin) {
			System.out.println(" ------------------");
			System.out.println(" id values -->" + objects[0]);
			System.out.println(" distance values -->" + objects[1]);

		}*/
	}

	/**
	 * Test Retrieve tweetpolls published/unpublished.
	 */
	@Test
	public void testRetrievePublishedUnpublishedTweetPolls() {
		createPublishedTweetPoll(secondary.getAccount(), this.question);
		final List<TweetPoll> tweetPollsPublished = getTweetPoll()
				.retrievePublishedUnpublishedTweetPoll(
						this.secondary.getAccount(), 10, 0, Boolean.TRUE);
		Assert.assertEquals("Should be", 2, tweetPollsPublished.size());
		createNotPublishedTweetPoll(secondary.getAccount(), this.question);
		final List<TweetPoll> tweetPollsUnpublished = getTweetPoll()
				.retrievePublishedUnpublishedTweetPoll(
						this.secondary.getAccount(), 10, 0, Boolean.FALSE);
		Assert.assertEquals("Should be", 1, tweetPollsUnpublished.size());
	}

	/**
	 * Test Retrieve completed/incompleted tweetpolls.
	 */
	@Test
	@Ignore
	public void testRetrieveCompletedTweetPolls() {
		createPublishedTweetPoll(secondary.getAccount(), this.question);
		final List<TweetPoll> completedTweetpolls = getTweetPoll()
				.retrieveCompletedTweetPoll(this.secondary.getAccount(), 10, 0,
						Boolean.TRUE);
		Assert.assertEquals("Should be", 2, completedTweetpolls.size());
		final TweetPoll tpoll = createPublishedTweetPoll(
				secondary.getAccount(), this.question);
		tpoll.setCompleted(Boolean.FALSE);
		getTweetPoll().saveOrUpdate(tpoll);
		final List<TweetPoll> inCompletedTweetpolls = getTweetPoll()
				.retrieveCompletedTweetPoll(this.secondary.getAccount(), 10, 0,
						Boolean.FALSE);
		Assert.assertEquals("Should be", 1, inCompletedTweetpolls.size());
	}

	/**
	 * Test Advanced search tweetpolls.
	 */
	@Test
	public void testTweetPollAdvancedSearch() {
		final DateTime time1 = new DateTime();
		final DateTime time2 = time1.minusDays(8);
		// published - completed - scheduled
		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("cabeza"), Boolean.TRUE, Boolean.TRUE,
				Boolean.FALSE, new Date());

		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("cara"), Boolean.TRUE, Boolean.TRUE,
				Boolean.FALSE, new Date());
		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("pelo"), Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE, time2.toDate());
		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("cana"), Boolean.TRUE, Boolean.TRUE,
				Boolean.FALSE, new Date());
		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("lentes"), Boolean.FALSE, Boolean.TRUE,
				Boolean.FALSE, new Date());
		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("dientes"), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, time2.toDate());

		/** published - completed - favourite - scheduled **/
		final List<TweetPoll> search1 = getTweetPoll().advancedSearch(
				Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
				this.secondary.getAccount(), 0, 10, 7, "ca");
		Assert.assertEquals("Should be", 3, search1.size());

		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("nariz"), Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, new Date());
		createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("dedos"), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, new Date());

		final TweetPoll tp = createAdvancedTweetPoll(secondary.getAccount(),
				createDefaultQuestion("boca"), Boolean.TRUE, Boolean.FALSE,
				Boolean.FALSE, time2.toDate());
		tp.setFavourites(Boolean.FALSE);
		getTweetPoll().saveOrUpdate(tp);

		final List<TweetPoll> search2 = getTweetPoll().advancedSearch(
				Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
				this.secondary.getAccount(), 0, 10, 30, "b");
		Assert.assertEquals("Should be", 1, search2.size());

		System.out.println("\n");

		final List<TweetPoll> search3 = getTweetPoll().advancedSearch(
				Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE,
				this.secondary.getAccount(), 0, 10, 30, "d");
		Assert.assertEquals("Should be", 2, search3.size());

	}

	/** **/
	@Test
	public void testGetSocialLinksByTweetPollSearch(){

		// TweePoll 1
		final TweetPoll tweetPoll = createPublishedTweetPoll(
				this.secondary.getAccount(),
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
		final TweetPollSavedPublishedStatus tpSaved12 = createTweetPollSavedPublishedStatus(
				tweetPoll, " ", socialAccount, "TCPTEN2");

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
		enums.add(SocialProvider.LINKEDIN);
		enums.add(SocialProvider.TWITTER);

		final List<TweetPollSavedPublishedStatus> tpsp = getTweetPoll()
				.getSocialLinksByTweetPollSearch(tweetPoll,
						TypeSearchResult.TWEETPOLL, enums);
	}
}
