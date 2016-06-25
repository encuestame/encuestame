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

import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.test.persistence.config.AbstractBase;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Poll Dao.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since  March 16, 2009
 */
@Category(DefaultTest.class)
public class TestPollDao extends AbstractBase {


    /** {@link IPoll} **/
    @Autowired
    IPoll  pollI;

    /** {@link Poll} **/
    Poll poll;

    /** {@link Account}.**/
    Account user;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /** {@link Question} **/
    private Question question;

    /** {@link PollFolder} **/
    private PollFolder pollFolder;

    /** Max Results**/
    private Integer MAX_RESULTS = 10;

    /** **/
    private Integer START = 0;

    private Calendar myDate = Calendar.getInstance();

    private DateTime initDate = new DateTime();

    private HashTag initTag ;

     /** Before.
     * @throws EnMeNoResultsFoundException
     **/
    @Before
    public void initService() throws EnMeNoResultsFoundException{
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?", "html");
        this.poll = createPoll(myDate.getTime(), this.question, "FDK125", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        this.pollFolder = createPollFolder("My First Poll Folder", this.userAccount);
        addPollToFolder(this.pollFolder.getId(), this.userAccount, this.poll.getPollId());

        this.initTag = createHashTag("roses");
    }

     /** Test retrievePollsByUserId. **/
    @Test
    public void testFindAllPollByUserId(){
        final List<Poll> pollList = getPollDao().findAllPollByEditorOwner(this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 1, pollList.size());
    }

    /**
     * Test retrieve Poll By Id.
    **/
    @Test
    public void testGetPollById(){
        final Poll getpoll = getPollDao().getPollById(this.poll.getPollId());
        assertNotNull(getpoll);
    }

    /**
    * Test retrieve Results Poll By PollId.
    **/
    @Test
    public void testRetrievePollResultsById(){
        final Poll poll = createPoll(myDate.getTime(), this.question, "FDKdsadsads125", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        final Question quest = createQuestion("Do you like futboll", "Yes/No");
        final QuestionAnswer qansw = createQuestionAnswer("Yes", quest, "2020");
        final QuestionAnswer qansw2 =createQuestionAnswer("No", quest, "2021");
        createPollResults(qansw2, poll);
        createPollResults(qansw2, poll);
        createPollResults(qansw2, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        final List<Object[]> polli = getPollDao().retrieveResultPolls(poll.getPollId(), qansw.getQuestionAnswerId());
        assertEquals("Should be equals", 2, polli.size());
    }

    /**
     * Test Get Poll Folder by User.
     */
    @Test
    public void testGetPollFolderByIdandUser(){
        assertNotNull(pollFolder);
        final PollFolder pfolder = getPollDao().getPollFolderByIdandUser(this.pollFolder.getId(), this.userAccount);
        assertEquals("Should be equals", this.pollFolder.getId(), pfolder.getId());
     }

    /**
     * Test Get Poll By User
     */
    @Test
    public void testGetPollByIdandUserId(){
        assertNotNull(this.poll);
        final Poll poll = getPollDao().getPollById(this.poll.getPollId(), this.userAccount);
        assertNotNull(poll);
        assertEquals("Should be equals", this.poll.getPollId(), poll.getPollId());
    }

    /**
     * Test Get Polls By Question Keyword.
     */
    @Test
    public void testGetPollsByQuestionKeyword(){
        assertNotNull(this.poll);
        Question question1 = createQuestion("Is Guns and Roses your favorite Group?", "html");
        createPoll(myDate.getTime(), question1, "FDK125sada", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        Question question2 = createQuestion("Real Madrid VS Barcelona", "html");
        createPoll(myDate.getTime(), question2, "FDK125231321", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        final String keywordQuestion = "roses";
        flushIndexes();
        final List<Poll> listPoll = getPollDao().getPollsByQuestionKeyword(keywordQuestion, this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, listPoll.size());
    }

    /**
     * Test getPollFolderById.
     */
    @Test
    public void testGetPollFolderById(){
        assertNotNull(this.pollFolder);
        final PollFolder pollFolder = getPollDao().getPollFolderById(this.pollFolder.getId());
        assertEquals("Should be equals", this.pollFolder.getId(), pollFolder.getId());
    }

    /**
     * Test Get Polls By PollFolderId.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetPollsByPollFolderId() throws EnMeNoResultsFoundException{
         assertNotNull(this.pollFolder);
         assertNotNull(poll);
         final Poll addPoll = addPollToFolder(this.pollFolder.getId(), this.userAccount, this.poll.getPollId());
         assertNotNull(addPoll);
         final List<Poll> pfolder = getPollDao().getPollsByPollFolderId(this.userAccount, this.pollFolder);
         assertEquals("Should be equals", 1, pfolder.size());
    }

    /**
     * Test Get Polls by creation date.
     */
    @Test
    public void testGetPollByUserIdDate(){
        final Calendar yesterdayDate = Calendar.getInstance();
        yesterdayDate.add(Calendar.DAY_OF_WEEK, -1);
        final Date yesterday = yesterdayDate.getTime();
        // Second Poll
        createPoll(new Date(), this.question, "FDK115", this.userAccount,
                Boolean.TRUE, Boolean.TRUE);
        // Third Poll
        createPoll(yesterday, this.question, "FDK195", this.userAccount,
                Boolean.TRUE, Boolean.TRUE);
        Assert.assertNotNull(this.poll);
        final List<Poll> pollList = getPollDao().getPollByUserIdDate(yesterday,
                this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 3, pollList.size());
    }


    @Test
    public void testGetPollByUserIdDateWithPrivacy(){

		final UserAccount user2 = createUserAccount("user 2", this.user);

        // Second Poll
        createPoll(new Date(), this.question, "FDK115", this.userAccount,
                Boolean.TRUE, Boolean.TRUE);
        // Third Poll
        createPoll(new Date(), this.question, "FDK195", this.userAccount,
                Boolean.TRUE, Boolean.TRUE);

        // Fourth Poll
        createDefaulPollWithPrivacy(question, userAccount, new Date(), true, false); // Hidden - User1
        // Fifth Poll
        createDefaulPollWithPrivacy(question, userAccount, new Date(), true, false); // Hidden - User1
        // Sexth Poll
        createDefaulPollWithPrivacy(question, userAccount, new Date(), true, false); // Hidden - User1

        // Sexth Poll
        createDefaulPollWithPrivacy(question, userAccount, new Date(), false, false); // Not Hidden - User1

        // Poll - User 2
        createDefaulPollWithPrivacy(question, user2, new Date(), true, false); // Hidden - User2
        createDefaulPollWithPrivacy(question, user2, new Date(), false , false); // Not Hidden - User 2


        final List<Poll> pollList = getPollDao().getPollByUserIdDate(null,
                this.userAccount,
                this.MAX_RESULTS,
                this.START);
        assertEquals("Should be equals", 4, pollList.size());

        // Retrieve Hidden Polls User 1
		final List<Poll> retrieveHiddenPolls = getPollDao()
				.getHiddenPollbyUser(true, this.userAccount, 10, 0);
		assertEquals("Hidden Polls with user 1 should be equals", 3, retrieveHiddenPolls.size());

		// Retrieve Hidden Polls User 2
				final List<Poll> retrieveNotHiddenPolls1 = getPollDao()
						.getHiddenPollbyUser(false, this.userAccount, 10, 0);
				assertEquals("Hidden Polls with user 1 should be equals", 4, retrieveNotHiddenPolls1.size());

		// Retrieve Hidden Polls User 2
		final List<Poll> retrieveNotHiddenPolls = getPollDao()
				.getHiddenPollbyUser(false, user2, 10, 0);
		assertEquals("Hidden Polls with user 2 should be equals", 1, retrieveNotHiddenPolls.size());

    }


    @Test
    public void testRetrievePollsByUserId(){
        final Question question2 =  createQuestion("Why the sea is blue?","html");
        createPoll(new Date(), question2, "FDK126", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        final List<Poll> pollbyUser = getPollDao().retrievePollsByUserId(this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, pollbyUser.size());
    }

    @Test
    public void testGetPollFolderBySecUser(){
        createPollFolder("My Second Poll Folder", this.userAccount);
        createPollFolder("My Third Poll Folder", this.userAccount);
        final List<PollFolder> pollFolderbyUser = getPollDao().getPollFolderByUserAccount(this.userAccount);
        assertEquals("Should be equals", 3, pollFolderbyUser.size());
    }

    @Test
    public void testPollsByPollFolder(){
        final List<Poll> pollsbyFolder = getPollDao().getPollsByPollFolder(this.userAccount, this.pollFolder);
        assertEquals("Should be equals", 1, pollsbyFolder.size());
    }

    /**
     * Test get total polls by user.
     */
    @Test
    public void testGetTotalPollsbyUser() {
        final Poll myPoll = createPoll(myDate.getTime(), this.question,
                "FDK445", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        assertNotNull(myPoll);
        final Long totalPolls = getPollDao().getTotalPollsbyUser(
                this.userAccount, Boolean.TRUE);
       Assert.assertEquals("Should be", 2, totalPolls.intValue());
    }

    /**
     * Test get total polls by hashTag.
     */
    @Test
    public void testGetTotalPollsByHashTag() {
        final HashTag hashtag1 = createHashTag("roses");
        final HashTag hashtag2 = createHashTag("red");
        final HashTag hashtag3 = createHashTag("flowers");

        final Poll poll1 = createPoll(myDate.getTime(), this.question,
                "DPMU12", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        poll1.getHashTags().add(hashtag1);
        poll1.getHashTags().add(hashtag2);
        poll1.getHashTags().add(hashtag3);
        getPollDao().saveOrUpdate(poll1);

        final Poll poll2 = createPoll(myDate.getTime(), this.question,
                "DPMU13", this.userAccount, Boolean.TRUE, Boolean.TRUE);

        poll2.getHashTags().add(hashtag3);
        poll2.getHashTags().add(hashtag1);
        getPollDao().saveOrUpdate(poll2);

        final Poll poll3 = createPoll(myDate.getTime(), this.question,
                "DPMU14", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        poll3.getHashTags().add(hashtag3);
        getPollDao().saveOrUpdate(poll3);
        final List<Poll> totalUsagePoll = getPollDao().getPollByHashTagName(
                hashtag1.getHashTag(), this.START, this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);

        Assert.assertEquals("Should be", 2, totalUsagePoll.size());

        final List<Poll> totalUsagePoll2 = getPollDao().getPollByHashTagName(
                hashtag2.getHashTag(), this.START, this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);

        Assert.assertEquals("Should be", 1, totalUsagePoll2.size());

        final List<Poll> totalUsagePoll3 = getPollDao().getPollByHashTagName(
                hashtag3.getHashTag(), this.START, this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        Assert.assertEquals("Should be", 3, totalUsagePoll3.size());
    }

    /**
     * Get total polls by hashtag name and date range.
     */
    @Test
    public void testGetPollsbyHashTagNameAndDateRange(){
        final Calendar myDate = Calendar.getInstance();
        final Question myQuestion = createQuestion("what are your favorite flowers?", this.userAccount.getAccount());
        final HashTag hashtag1 = createHashTag("roses");

        final Poll poll1 = createPoll(myDate.getTime(), myQuestion,
                "DPMU12", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        poll1.getHashTags().add(hashtag1);
        getPollDao().saveOrUpdate(poll1);
        myDate.add(Calendar.DATE, -2);


        final Question myQuestion2 = createQuestion("What was your best gift for valentines day?", this.userAccount.getAccount());
        final Poll poll2 = createPoll(myDate.getTime(), myQuestion2,
                "DPMU19", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        poll2.getHashTags().add(hashtag1);
        getPollDao().saveOrUpdate(poll2);
        final List<Poll> getTotalPollsbyHashTag = getPollDao().getPollsbyHashTagNameAndDateRange(hashtag1.getHashTag(), SearchPeriods.SEVENDAYS);
        Assert.assertEquals("Should be", 2, getTotalPollsbyHashTag.size());
    }

    /**
     * Test for recovering all poll results by answer.
     */
    @Test
    public void testRetrievePollResultsByAnswer(){
        final Poll poll = createPoll(myDate.getTime(), this.question, "FDKL1", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        final Question quest = createQuestion("Do you like futboll", "Yes/No");
        final QuestionAnswer qansw = createQuestionAnswer("Yes", quest, "2020");
        final QuestionAnswer qansw2 =createQuestionAnswer("No", quest, "2021");
        // Create poll results for QuestionAnswer2 = 3
        createPollResults(qansw2, poll);
        createPollResults(qansw2, poll);
        createPollResults(qansw2, poll);
        // Create poll results for QuestionAnswer = 3
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);
        createPollResults(qansw, poll);

        final List<Object[]> pollsResultsbyAnswer = getPollDao().retrieveResultPollsbyAnswer(poll.getPollId(), qansw.getQuestionAnswerId());
        assertEquals("Should be equals", 1, pollsResultsbyAnswer.size());


        for (Object[] objects : pollsResultsbyAnswer) {
            if (objects[0] ==  qansw.getQuestionAnswerId()) {
                assertEquals("For answer1 should be equals",
                        objects[3], 7L);
            }
            if (objects[0] ==  qansw2.getQuestionAnswerId()) {
                assertEquals("For answer2 should be equals",
                        objects[3], 3L);
            }
        }
    }

    /**
     * Test find all {@link Poll}.
     */
    @Test
    public void testFindAllPollByAccount() {
        final Account acc2 = createUser("testEncuesta1", "testEncuesta1233");
        final Account acc3 = createUser("testEncuesta3", "testEncuesta1235");

        final UserAccount userAccount1 = createUserAccount("paola", acc2);
        final UserAccount userAccount2 = createUserAccount("carlos", acc2);
        final UserAccount userAccount3 = createUserAccount("isabella", acc3);
        createDefaultPoll(question, userAccount1);
        createDefaultPoll(question, userAccount1);
        createDefaultPoll(question, userAccount2);
        createDefaultPoll(question, userAccount2);
        createDefaultPoll(question, userAccount2);
        createDefaultPoll(question, userAccount3);

        final List<Poll> allPolls = getPollDao().findAllPollByAccount(acc2, 10,
                0);
        assertEquals("Should be equals", 5, allPolls.size());

    }

    /**
     * Test Retrieve {@link Poll} by {@link Question}.
     */
    @Test
    public void testGetPollbyQuestion() {
        final Poll pollByQuestion = getPollDao().getPollbyQuestion(
                this.question.getQid());

        assertEquals("Should be equals", this.poll.getPollId(),
                pollByQuestion.getPollId());
     }

    /**
     *Create poll Helper.
     */
    private void createPolls() {

        final UserAccount userAcc2 = createUserAccount("userAcc2",this.user);

        // -5 hours
        final Poll poll1 = createDefaultPoll(this.question, this.userAccount, this.initDate.minusHours(5).toDate(), 25L, 15L);
        poll1.getHashTags().add(initTag);
        getPollDao().saveOrUpdate(poll1);

        // -15 hours
        Poll pollH = createDefaultPoll(this.question, this.userAccount, this.initDate.minusHours(15).toDate(), 45L, 55L);
        pollH.getHashTags().add(initTag);
        getPollDao().saveOrUpdate(pollH);

        // -25 days
        final Poll poll2 = createDefaultPoll(this.question, this.userAccount, this.initDate.minusDays(25).toDate(), 75L, 160L);
        poll2.getHashTags().add(initTag);
        getPollDao().saveOrUpdate(poll2);

        // -25 days
        final Poll poll3 = createDefaultPoll(this.question, this.userAccount, this.initDate.minusDays(25).toDate(), 45L, 10L);
        poll3.getHashTags().add(initTag);
        getPollDao().saveOrUpdate(poll3);

        //-6 days
        createDefaultPoll(this.question, userAcc2, this.initDate.minusDays(6).toDate(), 78L, 35L);

        //-400 days
        Poll longTimePoll = createDefaultPoll(this.question, userAcc2, this.initDate.minusDays(400).toDate(), 120L, 63L);
        longTimePoll.getHashTags().add(initTag);
        getPollDao().saveOrUpdate(longTimePoll);
    }

    /**
     * Test Retrieve all {@link Poll} by type.
     */
    @Test
    public void testGetPolls() {
        this.createPolls();
        final List<Poll> pollsby24Hours = getPollDao().getPolls(10, 0,
                SearchPeriods.TWENTYFOURHOURS);
        assertEquals("Should be equals", 3,
                pollsby24Hours.size());

        final List<Poll> pollsby7Days = getPollDao().getPolls(10, 0,
                SearchPeriods.SEVENDAYS);
        assertEquals("Should be equals", 4,
                pollsby7Days.size());

        final List<Poll> pollsby30Days = getPollDao().getPolls(10, 0,
                SearchPeriods.THIRTYDAYS);
        assertEquals("Should be equals", 6,
                pollsby30Days.size());

    }

    /**
     * Test Retrieve Maximum Like Votes by User
     */
    @Test
    public void testGetMaxPollLikeVotesbyUser(){
        final DateTime dtFrom = this.initDate.toDateTime();
        final DateTime dtTo = this.initDate.toDateTime();
        final Long maxVotes = getPollDao().getMaxPollLikeVotesbyUser(this.userAccount.getUid(), dtTo.minusDays(30).toDate(), dtFrom.toDate());
        assertEquals(1, maxVotes.intValue());
     }

    /**
     * Test Retrieve Total votes by poll and Range.
     */
    @Test
    public void testGetTotalVotesByPollIdAndDateRange(){
        final Poll poll1 = createDefaultPoll(this.question, this.userAccount, this.initDate
                .minusHours(5).toDate(), 25L, 15L);
        final QuestionAnswer questionAnswer = createQuestionAnswer("maybe", this.question, "&93fak");
        final QuestionAnswer questionAnswer2 = createQuestionAnswer("yes", this.question, "jakP22");
        final QuestionAnswer questionAnswer3 = createQuestionAnswer("No", this.question, "Lmd93s");
        createPollResults(questionAnswer, poll1);
        createPollResults(questionAnswer2, poll1);
        createPollResults(questionAnswer3, poll1);
        final Long totalVotes = getPollDao().getTotalVotesByPollIdAndDateRange(poll1.getPollId(), SearchPeriods.SEVENDAYS);
        assertEquals("Should be equals", 3,
                totalVotes.intValue());
    }

    /**
     * Test Retrieve poll statistics by range.
     */
    @Test
    public void testGetPollsRangeStats(){
        this.createPolls();
        // seven days period
        final List<Object[]> objectStats = getPollDao().getPollsRangeStats(
                this.initTag.getHashTag(), SearchPeriods.SEVENDAYS);
        final Long firstValue = (Long) objectStats.get(0)[1];
        final Long secondValue = (Long) objectStats.get(1)[1];
        assertEquals("Should be equals", objectStats.size(), 2);
        assertEquals("Should be equals", 1, firstValue.intValue());
        assertEquals("Should be equals", 1, secondValue.intValue());
        // all days period
        final List<Object[]> objectStats2 = getPollDao().getPollsRangeStats(
                this.initTag.getHashTag(), SearchPeriods.ALLTIME);
        assertEquals("Should be equals", objectStats2.size(), 4);
        // 24h period
        final List<Object[]> objectStats3 = getPollDao().getPollsRangeStats(
                this.initTag.getHashTag(), SearchPeriods.TWENTYFOURHOURS);
        assertEquals("Should be equals", objectStats3.size(), 2);
        // one year
        final List<Object[]> objectStats4 = getPollDao().getPollsRangeStats(
                this.initTag.getHashTag(), SearchPeriods.ONEYEAR);
        assertEquals("Should be equals", objectStats4.size(), 3);
        // 30 days
        final List<Object[]> objectStats5 = getPollDao().getPollsRangeStats(
                this.initTag.getHashTag(), SearchPeriods.THIRTYDAYS);
        assertEquals("Should be equals", objectStats5.size(), 3);
     }

    /**
     * Retrieve Validate vote IP.
     */
    @Test
    public void testValidateVoteIP() {
        final String ip = "192.10.1.1";
        final String ip2 = "192.10.1.11";
        final Poll poll1 = createDefaultPoll(this.question, this.userAccount,
                this.initDate.minusHours(8).toDate());
        final QuestionAnswer qAsnwer = createQuestionAnswer("possible", this.question, "3fak34");
        createDefaultPollResults(qAsnwer, poll1, ip);
        final Integer result = getPollDao().validateVoteIP(ip, poll1);
        assertNotNull(result);
        assertEquals(result, new Integer(1));
        final Integer resultNull = getPollDao().validateVoteIP(ip2, poll1); 
        Assert.assertNotNull(resultNull);
        assertEquals(resultNull, new Integer(0));
    }
}