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
import java.util.HashMap;
import java.util.List;

import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.RelativeTimeEnum;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.social.SocialProvider;
import org.joda.time.DateMidnight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link TweetPollDao}..
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 13, 2010 11:57:17 PM
 * @version $Id: change to one dolar simbol
 */
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
    private Integer MAX_RESULTS = 10;

    /** Init results query. **/
    private Integer INIT_RESULTS = 0;
    
    /**
     * Before.
     */
    @Before
    public void initData(){
      this.secondary = createUserAccount("jhonny", createAccount());
      final Question question = createQuestion("Who I am?", "");
      this.questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
      this.questionsAnswers2 = createQuestionAnswer("no", question, "12346");
      this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question);
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
     */
    @Test
    public void testRetrieveTweetsByQuestionName(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final String keyword = "Who";
        final List<TweetPoll> tweets = getTweetPoll().retrieveTweetsByQuestionName(keyword, userId, 5, 0);
        assertEquals("Should be equals", 1, tweets.size());
    }

    /**
     * Test Retrieve TweetPoll Today.
     */
    @Test
    public void testRetrieveTweetPollToday(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final List<TweetPoll> tweetsToday = getTweetPoll().retrieveTweetPollToday(this.secondary.getAccount(), 5, 0);
        assertEquals("Should be equals", 1, tweetsToday.size());
    }

    /**
     *
     */
    @Test
    public void testRetrieveTweetPollByDate(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final DateMidnight initDate = new DateMidnight();
        final List<TweetPoll> tweetsByDate = getTweetPoll().retrieveTweetPollByDate(this.secondary.getAccount(), initDate.toDate(), 5, 0);
        assertEquals("Should be equals", 1, tweetsByDate.size());
    }

    /**
     * Test Retrieve TweetPoll Last Week
     */
    @Test
    public void testRetrieveFavouritesTweetPoll(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final List<TweetPoll> favouritesTweets = getTweetPoll().retrieveFavouritesTweetPoll(this.secondary.getAccount(), 5, 0);
        assertEquals("Should be equals", 1, favouritesTweets.size());
    }

    /**
     *
     */
    @Test
    public void testRetrieveScheduledTweetPoll(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final List<TweetPoll> scheduledTweets = getTweetPoll().retrieveScheduledTweetPoll(userId, 5, 0);
        assertEquals("Should be equals", 1, scheduledTweets.size());
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
        final List<TweetPoll> tweetPolls = getTweetPoll().getTweetpollByHashTagName(this.hashTag1.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS, TypeSearchResult.HASHTAG);
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

        final List<TweetPoll> tweetPolls2 = getTweetPoll().getTweetpollByHashTagName(this.hashTag1.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS, TypeSearchResult.HASHTAG);
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

        final List<TweetPoll> tweetPolls2 = getTweetPoll().getTweetpollByHashTagName(this.hashTag1.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS, TypeSearchResult.HASHTAGRATED);
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
}
