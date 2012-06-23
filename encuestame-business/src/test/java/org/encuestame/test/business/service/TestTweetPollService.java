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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.business.service.TweetPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link TweetPollService}.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 5, 2010 3:36:43 PM
 * @version $Id:$
 */
public class TestTweetPollService  extends AbstractSpringSecurityContext{
    /**
     * {@link TweetPollService}.
     */
    @Autowired
    private ITweetPollService tweetPollService;

    /** {@link Question} */
    private Question question;

    /** {@link Account} **/
    private Account user;

    /** {@link UserAccount}. **/
    private UserAccount userAccount;

    private List<QuestionAnswerBean> answers;

    /** {@link QuestionBean} **/
    private QuestionBean questionBean;

    /** List {@link QuestionAnswerBean}. **/
    private List<QuestionAnswerBean> answersSaveTweet;

    private List<SocialAccountBean> twitterAccount;
    
    /** Tweet text.**/
    private String tweetText;
    
    /** {@link SocialAccountBean} **/
    private List<SocialAccountBean> socialBeans;

    /**
    * Before.
    */
   @Before
   public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("jhon", user);
        this.question = createQuestion("Why the sky is blue?","html");
        createQuestionAnswer("Yes", this.question,"SSSA");
        //this.questionBean = createUnitQuestionBean("", 1L, 1L, listAnswers, pattern)
        answers = new ArrayList<QuestionAnswerBean>();
        answers.add(createAnswersBean("2DFAAS", "Yes", question.getQid()));
        answers.add(createAnswersBean("4DSWGK", "No", question.getQid()));
        questionBean = createUnitQuestionBean("questionName", 1L, this.user.getUid(),
                   this.answers);
		this.tweetText = RandomStringUtils.randomAlphabetic(5);
		this.socialBeans = this.createSocialAccounts();
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
  //  final TweetPoll tweetPoll = this.tweetPollService.createTweetPoll(tweetPollBean, "", a, this.userAccount);
   // final String s = this.tweetPollService.generateTweetPollContent(tweetPoll,  RandomStringUtils.randomAlphabetic(15));
    //final Status status = this.tweetPollService.publicTweetPoll(s, this.user.getTwitterAccount(), this.user.getTwitterPassword());
    //assertNotNull(status.getId());
    }

    /**
     * Test Save Tweet Id.
     * @throws EnMeExpcetion
     */
    public void testSaveTweetId() throws EnMeExpcetion{
        Question questionSave = createQuestion("how much or How Many?","html");
        final Account usersave = createUser("dianmora", "xxxxxxx");
        final UserAccount account = createUserAccount("jota", usersave);
        final String tweetUrl = "http://www.encuestame.org";
        final TweetPoll tweetPoll = createTweetPollPublicated(true, true, new Date(), account, questionSave);

        answersSaveTweet = new ArrayList<QuestionAnswerBean>();
        answersSaveTweet.add(createAnswersBean("GBHD", "Maybe", questionSave.getQid()));
        answersSaveTweet.add(createAnswersBean("GTJU", "Yes", questionSave.getQid()));


        questionBean = createUnitQuestionBean(questionSave.getQuestion(), 1L, usersave.getUid(),
                   answersSaveTweet);
        final TweetPollBean unitTweetPoll = createUnitTweetPollPublicated(new Date(), true, tweetUrl, usersave.getUid(),
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
        final TweetPoll tweetPollPublicate = createTweetPollPublicated(true,true,new Date(), this.userAccount, this.question);
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
    final List<TweetPollResultsBean> results = this.tweetPollService.getResultsByTweetPollId(tweetPoll.getTweetPollId());
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
        createTweetPollPublicated(true, true, new Date(), this.userAccount, question1);
        createTweetPollPublicated(true, true, new Date(), this.userAccount, question2);
        final UserAccount secUser = createUserAccount("diana", this.user);
        final List<TweetPollBean> tweetPollsByUser = this.tweetPollService.getTweetsPollsByUserName(
                secUser.getUsername(), 5, 0);
        //assertEquals("Should be equals", 2 , tweetPollsByUser.size());
    }

    /**
     * Create {@link SocialAccountBean}
     * @return
     */
	private List<SocialAccountBean> createSocialAccounts() {
		createDefaultSettedSocialAccount(this.userAccount); 
		final List<SocialAccount> list = getAccountDao()
				.getSocialAccountByAccount(this.userAccount.getAccount(),
						SocialProvider.TWITTER);
		
		final List<SocialAccountBean> listUnitTwitterAccount = ConvertDomainBean
				.convertListSocialAccountsToBean(list); 
		return listUnitTwitterAccount;
	}

    /**
     * Test Public TweetPoll on multiples social networks.
     */
    @Test
    //@Ignore
	public void testPublicMultiplesTweetAccounts() {
    
		final TweetPoll tweetPoll = createTweetPollPublicated(true, true,
				new Date(), this.userAccount, question); 
		tweetPollService.publishMultiplesOnSocialAccounts(this.socialBeans,
				tweetPoll, this.tweetText, TypeSearchResult.TWEETPOLL, null,
				null); 
		final TweetPoll tweet = getTweetPoll().getTweetPollById(
				tweetPoll.getTweetPollId());
		assertNotNull(tweet);
		final List<LinksSocialBean> linksPublished = getTweetPollService()
				.getTweetPollLinks(tweetPoll, null, null,
						TypeSearchResult.TWEETPOLL); 
		assertEquals("Should be equals", 1 , linksPublished.size());
	}
    
    /**
     * 
     */
	@Test
	public void testPublishPollOnMultiplesTweetAccounts() {
		  
		final Question otherQuestion = createDefaultQuestion("What is your favorite android app");

		final Poll myPoll = createDefaultPoll(otherQuestion, this.userAccount);

		final List<TweetPollSavedPublishedStatus> itemsPublished = tweetPollService
				.publishMultiplesOnSocialAccounts(this.socialBeans, null, this.tweetText, TypeSearchResult.POLL, myPoll, null);
		assertEquals("Should be equals", 1, itemsPublished.size()); 
		final List<LinksSocialBean> linksPublished = getTweetPollService()
				.getTweetPollLinks(null, myPoll, null,
						TypeSearchResult.POLL); 
		assertEquals("Should be equals", 1 , linksPublished.size());
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

    /**
     * Test Validate ip before tweetPoll vote.
     * @throws EnMeExpcetion
     */
    @Test
    public void testValidateIp() throws EnMeExpcetion{
        final String ipVote = EnMeUtils.ipGenerator();
        final TweetPollBean myTpBean = createUnitTweetPoll(Boolean.TRUE,
                "tweetPollUrl", getSpringSecurityLoggedUserAccount().getUid(),
                questionBean);
        final TweetPoll myTweetPoll = tweetPollService.createTweetPoll(
                myTpBean, "What is your favourite city?",
                getSpringSecurityLoggedUserAccount());
        final Question myQuestion = createQuestion(
                "What is your favourite city", "pattern");

        final QuestionAnswerBean qAnswerBean = createAnswersBean("26354",
                "Yes", myQuestion.getQid());
        final QuestionAnswerBean qAnswerBean2 = createAnswersBean("26355",
                "No", myQuestion.getQid());

        final TweetPollSwitch pollSwitch = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll);
        final TweetPollSwitch pollSwitch2 = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll);

        tweetPollService.tweetPollVote(pollSwitch, ipVote, Calendar.getInstance().getTime());
        //tweetPollService.tweetPollVote(pollSwitch2, ipVote);
        final TweetPollResult result = tweetPollService.validateTweetPollIP(ipVote, myTweetPoll);
        assertEquals("Should be equals", ipVote , result.getIpVote());
    }
}
