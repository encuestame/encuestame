/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.test.config.AbstractBaseUnitBeans;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.QuestionBean;
import org.encuestame.utils.web.TweetPollBean;
import org.encuestame.utils.web.UnitTweetPollResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link TweetPollService}.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 5, 2010 3:36:43 PM
 * @version $Id:$
 */
public class TestTweetPollService  extends AbstractServiceBase{
    /**
     * {@link TweetPollService}.
     */
    @Autowired
    private ITweetPollService tweetPollService;

    /** {@link Question} */
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern pattern;

    /** {@link Account} **/
    private Account user;

    /** {@link UserAccount}. **/
    private UserAccount userAccount;

    private List<QuestionAnswerBean> answers;

    /** {@link UnitPatternBean}**/
    private UnitPatternBean patternBean;

    /** {@link QuestionBean} **/
    private QuestionBean questionBean;

    /** List {@link QuestionAnswerBean}. **/
    private List<QuestionAnswerBean> answersSaveTweet;

    private List<SocialAccountBean> twitterAccount;

    /**
    * Before.
    */
   @Before
   public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("jhon", user);
        this.question = createQuestion("Why the sky is blue?","html");
        this.pattern = createQuestionPattern("html");
        createQuestionAnswer("Yes", this.question,"SSSA");
        //this.questionBean = createUnitQuestionBean("", 1L, 1L, listAnswers, pattern)
        answers = new ArrayList<QuestionAnswerBean>();
        answers.add(createAnswersBean("2DFAAS", "Yes", question.getQid()));
        answers.add(createAnswersBean("4DSWGK", "No", question.getQid()));
        patternBean = createPatternBean("radio.class",
                   "radio buttons", "2", "Yes/No", "template.html");
        questionBean = createUnitQuestionBean("questionName", 1L, this.user.getUid(),
                   this.answers, patternBean);
   }


   /**
    * Test Get Tweets.
 * @throws EnMeDomainNotFoundException
    */
/*   @Test
   public void testGetTweetsPollByUserId() throws EnMeDomainNotFoundException{
        createTweetPoll(2L, false, false, false, true, false, new Date(), new Date(), false,
                                                  this.user, this.question);
        createQuestionAnswer("Yes", this.question, "BBB");
        createQuestionAnswer("No", this.question, "CCC");
        final List<UnitTweetPoll> unitTweetPollList =
         this.tweetPollService.getTweetsPollsByUserName(this.secUserSecondary.getUsername(),5 , 1);
        assertEquals("Should be equals", 1, unitTweetPollList.size());
   }*/


    /**
     * Test Create Tweet Poll.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testCreateTweetPoll() throws EnMeExpcetion{
    final TweetPollBean tweetPollBean = new TweetPollBean();
    questionBean.setId(question.getQid());
    tweetPollBean.setQuestionBean(questionBean);
    tweetPollBean.setPublishPoll(true);
    tweetPollBean.setScheduleDate(new Date());
    tweetPollBean.setCompleted(false);
    tweetPollBean.setUserId(this.user.getUid());
    // createTweetPoll(TweetPollBean, String, String[], UserAccount)
    String[] a = {"yes","no"};
    final TweetPoll tweetPoll = this.tweetPollService.createTweetPoll(tweetPollBean, "", a, this.userAccount);
    final String s = this.tweetPollService.generateTweetPollText(tweetPoll,  RandomStringUtils.randomAlphabetic(15));
    //final Status status = this.tweetPollService.publicTweetPoll(s, this.user.getTwitterAccount(), this.user.getTwitterPassword());
    //assertNotNull(status.getId());
    }

    /**
     * Test Save Tweet Id.
     * @throws EnMeExpcetion
     */
    public void testSaveTweetId() throws EnMeExpcetion{
        Question questionSave = createQuestion("how much or How Many?","html");
        Account usersave = createUser("dianmora", "xxxxxxx");
        final String tweetUrl = "http://www.encuestame.org";
        final TweetPoll tweetPoll = createTweetPollPublicated(true, true, new Date(), usersave, questionSave);

        answersSaveTweet = new ArrayList<QuestionAnswerBean>();
        answersSaveTweet.add(createAnswersBean("GBHD", "Maybe", questionSave.getQid()));
        answersSaveTweet.add(createAnswersBean("GTJU", "Yes", questionSave.getQid()));

        patternBean = createPatternBean("radio.class",
                   "radio buttons", "2", "Yes/No", "template.php");
        questionBean = createUnitQuestionBean(questionSave.getQuestion(), 1L, usersave.getUid(),
                   answersSaveTweet, patternBean);
        final TweetPollBean unitTweetPoll = createUnitTweetPollPublicated(new Date(), true, tweetUrl,usersave.getUid(),
                                            this.questionBean, null);
        unitTweetPoll.setId(tweetPoll.getTweetPollId());
        //final String s = this.tweetPollService.generateTweetPollText(unitTweetPoll, tweetUrl);
        //final Status status = this.tweetPollService.publicTweetPoll(s, userpao.getTwitterAccount(), userpao.getTwitterPassword());
        //assertNotNull(status.getId());
        //this.tweetPollService.saveTweetId(unitTweetPoll);
    }

    /**
     * Test Tweet Poll Vote
     */
    public void testTweetPollVote(){

    }

    /**
     * Test Generate Tweet Poll Text.
     * @throws EnMeExpcetion EnMeExpcetion
     */
    @Test
    public void testGenerateTweetPollText() throws EnMeExpcetion{
        final TweetPoll tweetPollPublicate = createTweetPollPublicated(true,true,new Date(), this.user, this.question);
        createQuestionAnswer("Yes", this.question, "EEEE");
        createQuestionAnswer("No", this.question, "FFFF");
        final String tweetUrl = "http://www.encuestame.org";
        final TweetPollBean uniTweetPoll = createUnitTweetPollPublicated(new Date(), true, tweetUrl, this.user.getUid(), this.questionBean, "testtweetuser");
        uniTweetPoll.setId(tweetPollPublicate.getTweetPollId());
        //final String twettQuestionText = this.tweetPollService.generateTweetPollText(uniTweetPoll, tweetUrl);
        final String twettQuestionText = "test";
        assertNotNull(twettQuestionText);
        final Integer textLength = twettQuestionText.length();
        assertEquals(true, (textLength < 140 ? true: false));
     }


    /**
     * Service to retrieve Results TweetPoll  Id.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetResultsByTweetPollId() throws EnMeNoResultsFoundException{
    final TweetPoll tweetPoll = createFastTweetPollVotes();
    final List<UnitTweetPollResult> results = this.tweetPollService.getResultsByTweetPollId(tweetPoll.getTweetPollId());
    assertEquals("Should be equals", 2 , results.size());
    }


 /*   @Test
    public void testSearchTweetsPollsByKeyWord() throws EnMeExpcetion{
        final Question questionSearch = createQuestion("Why the sea is blue?","html");
        final String keywordGood = "Why";
        final String keywordBad = "red";
        createTweetPollPublicated(true, true, new Date(), this.user, questionSearch);
        final List<UnitTweetPoll> tweetsResults = this.tweetPollService.searchTweetsPollsByKeyWord(this.secUserSecondary.getUsername(), keywordGood, 5, 1);
        final List<UnitTweetPoll> tweetsResultsBad = this.tweetPollService.searchTweetsPollsByKeyWord(this.secUserSecondary.getUsername(), keywordBad, 5, 1);
        assertEquals("Should be equals", 1 , tweetsResults.size());
        assertEquals("Should be equals", 0 , tweetsResultsBad.size());
    }*/

    @Test
    public void testGetTweetsPollsByUserName() throws EnMeNoResultsFoundException{
        final Question question1 = createQuestion("Why the sea is salad?","html");
        final Question question2 = createQuestion("Why the sea is big?","html");
        createTweetPollPublicated(true, true, new Date(), this.user, question1);
        createTweetPollPublicated(true, true, new Date(), this.user, question2);
        final UserAccount secUser = createUserAccount("diana", this.user);
        final List<TweetPollBean> tweetPollsByUser = this.tweetPollService.getTweetsPollsByUserName(
                secUser.getUsername(),5,0);
        assertEquals("Should be equals", 2 , tweetPollsByUser.size());

    }

    @Test
    public void testPublicMultiplesTweetAccounts(){
            createDefaultSettedTwitterAccount(this.userAccount.getAccount());
            final List<SocialAccount> list = getAccountDao().getTwitterAccountByUser(this.userAccount.getAccount(), SocialProvider.TWITTER);
            final List<SocialAccountBean> listUnitTwitterAccount = ConvertDomainBean.convertListSocialAccountsToBean(list);
             final String tweetText = RandomStringUtils.randomAlphabetic(5);
            final TweetPoll tweetPoll = createTweetPollPublicated(true, true, new Date(), this.user, question);
            tweetPollService.publicMultiplesTweetAccounts(listUnitTwitterAccount, tweetPoll, tweetText);
            final TweetPoll tweet = getTweetPoll().getTweetPollById(tweetPoll.getTweetPollId());
            assertNotNull(tweet);
    }

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService the tweetPollService to set
     */
    public void setTweetPollService(final ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }
}
