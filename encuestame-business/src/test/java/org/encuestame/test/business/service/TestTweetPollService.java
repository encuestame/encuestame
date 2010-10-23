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
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.domain.Question;
import org.encuestame.core.persistence.domain.survey.QuestionPattern;
import org.encuestame.core.persistence.domain.security.SecUser;
import org.encuestame.core.persistence.domain.security.SecUserSecondary;
import org.encuestame.core.persistence.domain.security.SecUserTwitterAccounts;
import org.encuestame.core.persistence.domain.survey.TweetPoll;
import org.encuestame.core.service.TweetPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.test.config.AbstractBaseUnitBeans;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
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
public class TestTweetPollService  extends AbstractBaseUnitBeans{
    /**
     * {@link TweetPollService}.
     */
    @Autowired
    private ITweetPollService tweetPollService;

    /** {@link Question} */
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern pattern;

    /** {@link SecUser} **/
    private SecUser user;

    /** {@link SecUserSecondary}. **/
    private SecUserSecondary secUserSecondary;

    private List<UnitAnswersBean> answers;

    /** {@link UnitPatternBean}**/
    private UnitPatternBean patternBean;

    /** {@link UnitQuestionBean} **/
    private UnitQuestionBean questionBean;

    /** List {@link UnitAnswersBean}. **/
    private List<UnitAnswersBean> answersSaveTweet;

    private List<UnitTwitterAccountBean> twitterAccount;

    /**
    * Before.
    */
   @Before
   public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.secUserSecondary = createSecondaryUser("jhon", user);
        this.question = createQuestion("Why the sky is blue?","html");
        this.pattern = createQuestionPattern("html");
        createQuestionAnswer("Yes", this.question,"SSSA");
        //this.questionBean = createUnitQuestionBean("", 1L, 1L, listAnswers, pattern)
        answers = new ArrayList<UnitAnswersBean>();
        answers.add(createAnswersBean("2DFAAS", "Yes", question.getQid()));
        answers.add(createAnswersBean("4DSWGK", "No", question.getQid()));
        patternBean = createPatternBean("radio.class",
                   "radio buttons", "2", "Yes/No", "template.html");
        questionBean = createUnitQuestionBean("questionName", 1L, this.user.getUid(),
                   this.answers, patternBean);
   }


   /**
    * Test Get Tweets
 * @throws EnMeDomainNotFoundException
    */
   @Test
   public void testGetTweetsPollByUserId() throws EnMeDomainNotFoundException{
        createTweetPoll(2L, false, false, false, false, false, new Date(), new Date(), false,
                                                  this.user, this.question);
        createQuestionAnswer("Yes", this.question, "BBB");
        createQuestionAnswer("No", this.question, "CCC");
        final List<UnitTweetPoll> unitTweetPollList = tweetPollService.getTweetsPollsByUserName(this.secUserSecondary.getUsername());
        assertEquals("Should be equals", 1, unitTweetPollList.size());
   }


    /**
     * Test Create Tweet Poll.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testCreateTweetPoll() throws EnMeExpcetion{
    final Question question = createQuestion("why the sky is blue?", "yes/no", this.user);
    createQuestionAnswer("yes", question, "12345");
    createQuestionAnswer("no", question, "12346");
    final UnitTweetPoll tweetPollBean = new UnitTweetPoll();
    final UnitQuestionBean questionBean = new UnitQuestionBean();
    questionBean.setId(question.getQid());
    tweetPollBean.setQuestionBean(questionBean);
    tweetPollBean.setPublishPoll(true);
    tweetPollBean.setScheduleDate(new Date());
    tweetPollBean.setCompleted(false);
    tweetPollBean.setUserId(this.user.getUid());
    this.tweetPollService.createTweetPoll(tweetPollBean);
    final String s = this.tweetPollService.generateTweetPollText(tweetPollBean,  RandomStringUtils.randomAlphabetic(15));
    //final Status status = this.tweetPollService.publicTweetPoll(s, this.user.getTwitterAccount(), this.user.getTwitterPassword());
    //assertNotNull(status.getId());
    }

    /**
     * Test Save Tweet Id.
     * @throws EnMeExpcetion
     */
    public void testSaveTweetId() throws EnMeExpcetion{
        Question questionSave = createQuestion("how much or How Many?","html");
        SecUser usersave= createUser("dianmora", "xxxxxxx");
        final String tweetUrl = "http://www.encuestame.org";
        final TweetPoll tweetPoll = createTweetPollPublicated(true, true, new Date(), usersave, questionSave);

        answersSaveTweet = new ArrayList<UnitAnswersBean>();
        answersSaveTweet.add(createAnswersBean("GBHD", "Maybe", questionSave.getQid()));
        answersSaveTweet.add(createAnswersBean("GTJU", "Yes", questionSave.getQid()));

        patternBean = createPatternBean("radio.class",
                   "radio buttons", "2", "Yes/No", "template.php");
        questionBean = createUnitQuestionBean(questionSave.getQuestion(), 1L, usersave.getUid(),
                   answersSaveTweet, patternBean);
        final UnitTweetPoll unitTweetPoll = createUnitTweetPollPublicated(new Date(), true, tweetUrl,usersave.getUid(),
                                            this.questionBean, usersave.getTwitterAccount());
        unitTweetPoll.setId(tweetPoll.getTweetPollId());
        final String s = this.tweetPollService.generateTweetPollText(unitTweetPoll, tweetUrl);
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
        final UnitTweetPoll uniTweetPoll = createUnitTweetPollPublicated(new Date(), true, tweetUrl, this.user.getUid(), this.questionBean, "testtweetuser");
        uniTweetPoll.setId(tweetPollPublicate.getTweetPollId());
        final String twettQuestionText = this.tweetPollService.generateTweetPollText(uniTweetPoll, tweetUrl);
        assertNotNull(twettQuestionText);
        final Integer textLength = twettQuestionText.length();
        assertEquals(true, (textLength < 140 ? true: false));
     }


    /**
     * Service to retrieve Results TweetPoll  Id.
     */
    @Test
    public void testGetResultsByTweetPollId(){
    final TweetPoll tweetPoll = createFastTweetPollVotes();
    final List<UnitTweetPollResult> results = this.tweetPollService.getResultsByTweetPollId(tweetPoll.getTweetPollId());
    assertEquals("Should be equals", 2 , results.size());
    }


    @Test
    public void testSearchTweetsPollsByKeyWord() throws EnMeDomainNotFoundException{
        final Question questionSearch = createQuestion("Why the sea is blue?","html");
        final String keywordGood = "Why";
        final String keywordBad = "red";
        createTweetPollPublicated(true, true, new Date(), this.user, questionSearch);
        final List<UnitTweetPoll> tweetsResults = this.tweetPollService.searchTweetsPollsByKeyWord(this.secUserSecondary.getUsername(), keywordGood);
        final List<UnitTweetPoll> tweetsResultsBad = this.tweetPollService.searchTweetsPollsByKeyWord(this.secUserSecondary.getUsername(), keywordBad);
        assertEquals("Should be equals", 1 , tweetsResults.size());
        assertEquals("Should be equals", 0 , tweetsResultsBad.size());
    }

    @Test
    public void testGetTweetsPollsByUserName() throws EnMeDomainNotFoundException{
        final Question question1 = createQuestion("Why the sea is salad?","html");
        final Question question2 = createQuestion("Why the sea is big?","html");
        createTweetPollPublicated(true, true, new Date(), this.user, question1);
        createTweetPollPublicated(true, true, new Date(), this.user, question2);
        final SecUserSecondary secUser = createSecondaryUser("diana", this.user);
        final List<UnitTweetPoll> tweetPollsByUser = this.tweetPollService.getTweetsPollsByUserName(secUser.getUsername());
        assertEquals("Should be equals", 2 , tweetPollsByUser.size());

    }

    public void testDisableTweetPoll() throws EnMeExpcetion{
        final Question questiondisable = createQuestion("Why the sea is blue?","html");
        final TweetPoll tpoll = createTweetPoll(2L, false, false, false, false, false, new Date(), new Date(), false,
                   this.user, this.question);
         createQuestionAnswer("Yes", questiondisable, "AADD");
         createQuestionAnswer("No", questiondisable, "VVBB");
        tweetPollService.disableTweetPoll(tpoll.getTweetPollId());


    }

    @Test
    public void testPublicMultiplesTweetAccounts(){
            createDefaultSettedTwitterAccount(this.secUserSecondary.getSecUser());
            final List<SecUserTwitterAccounts> list = getSecUserDao().getTwitterAccountByUser(this.secUserSecondary.getSecUser());
            final List<UnitTwitterAccountBean> listUnitTwitterAccount = ConvertDomainBean.convertListTwitterAccountsToBean(list);
             final String tweetText = RandomStringUtils.randomAlphabetic(5);
            final TweetPoll tweetPoll = createTweetPollPublicated(true, true, new Date(), this.user, question);
            tweetPollService.publicMultiplesTweetAccounts(listUnitTwitterAccount, tweetPoll.getTweetPollId(), tweetText);
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
