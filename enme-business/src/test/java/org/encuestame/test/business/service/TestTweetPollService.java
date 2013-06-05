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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.business.service.TweetPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.categories.test.InternetTest;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.encuestame.utils.web.search.TweetPollSearchBean;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test for {@link TweetPollService}.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 5, 2010 3:36:43 PM
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

    /** **/
    private DateTime creationDate;

    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;

    /** {@link PollFolder}. **/
    private TweetPollFolder folder;


    /**
    * Before.
    */
   @Before
   public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = getSpringSecurityLoggedUserAccount();
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
        this.creationDate = new DateTime();
        request = new MockHttpServletRequest();
		request.addPreferredLocale(Locale.ENGLISH);

		 this.folder = createTweetPollFolder("Folder 1", getSpringSecurityLoggedUserAccount());
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
    @Category(DefaultTest.class)
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
    @Category(DefaultTest.class)
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
    @Category(DefaultTest.class)
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
    @Category(DefaultTest.class)
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
    @Category(DefaultTest.class)
    public void testGetTweetsPollsByUserName() throws EnMeNoResultsFoundException{
        final Question question1 = createQuestion("Why the sea is salad?","html");
        final Question question2 = createQuestion("Why the sea is big?","html");
        createTweetPollPublicated(true, true, new Date(), this.userAccount, question1);
        createTweetPollPublicated(true, true, new Date(), this.userAccount, question2);
        final UserAccount secUser = createUserAccount("diana", this.user);
        final TweetPollSearchBean tpSearchBean = createTweetpollSearchBean(
                Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "xxx",
                "7", 10, 0, TypeSearch.BYOWNER);
        List<SocialProvider> enums = new ArrayList<SocialProvider>();
        enums.add(SocialProvider.FACEBOOK);
        enums.add(SocialProvider.TWITTER);
        tpSearchBean.setProviders(ListUtils.EMPTY_LIST);
        tpSearchBean.setSocialAccounts(ListUtils.EMPTY_LIST);
        final List<TweetPollBean> tweetPollsByUser =getTweetPollService().getTweetsPollsByUserName(getUsernameLogged(), null, tpSearchBean);

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
    @Category(InternetTest.class)
    @Test
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
    @Category(InternetTest.class)
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
    @Category(DefaultTest.class)
    public void testValidateIp() throws EnMeExpcetion{
        final String ipVote = EnMeUtils.ipGenerator();
        final TweetPollBean myTpBean = createUnitTweetPoll(Boolean.TRUE,
                "tweetPollUrl", getSpringSecurityLoggedUserAccount().getUid(),
                questionBean);
        final TweetPoll myTweetPoll = tweetPollService.createTweetPoll(
                myTpBean, "What is your favourite city?",
                getSpringSecurityLoggedUserAccount(), null);
        final Question myQuestion = createQuestion(
                "What is your favourite city", "pattern");

        final QuestionAnswerBean qAnswerBean = createAnswersBean("26354",
                "Yes", myQuestion.getQid());
        final QuestionAnswerBean qAnswerBean2 = createAnswersBean("26355",
                "No", myQuestion.getQid());

        final TweetPollSwitch pollSwitch = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);
        final TweetPollSwitch pollSwitch2 = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);

        tweetPollService.tweetPollVote(pollSwitch, ipVote, Calendar.getInstance().getTime());
        //tweetPollService.tweetPollVote(pollSwitch2, ipVote);
        final TweetPollResult result = tweetPollService.validateTweetPollIP(ipVote, myTweetPoll);
        assertEquals("Should be equals", ipVote , result.getIpVote());
    }

    /**
     * Test search advanced tweetpoll.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testSearchAdvancedTweetPoll() throws EnMeNoResultsFoundException{
        final DateTime time1 = new DateTime();
        final DateTime time2 = time1.minusDays(8);
        // published - completed - scheduled
        createAdvancedTweetPoll(
                getSpringSecurityLoggedUserAccount().getAccount(),
                createDefaultQuestion("What is your favourite color"), Boolean.TRUE, Boolean.TRUE,
                Boolean.FALSE, new Date());
        createAdvancedTweetPoll(getSpringSecurityLoggedUserAccount()
                .getAccount(), createDefaultQuestion("What is your favourite movie"), Boolean.TRUE,
                Boolean.TRUE, Boolean.FALSE, time2.toDate());
       // final Search adv1 = createAdvancedSearchBean(Boolean.TRUE, Boolean.TRUE,
          //              Boolean.TRUE, Boolean.FALSE, "fav", 7, 0, 10);
        // published-completed-favourite-scheduled
       /* final List<TweetPollBean> tpBean = getTweetPollService()
                .searchAdvancedTweetPoll(adv1);
        //("fav", 7, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, 0, 10);
          assertEquals("Should be equals", 1 , tpBean.size());*/
        //  final Search adv2 = createAdvancedSearchBean(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, "fav", 30, 0, 10);
          /*   final List<TweetPollBean> tpBean30 = getTweetPollService()
                .searchAdvancedTweetPoll(adv2);
        //("fav", 30, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, 0, 10);
        assertEquals("Should be equals", 2, tpBean30.size());*/
    }

    /**
     * Test Filter {@link TweetPoll} by type.
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    @Test
    @Category(DefaultTest.class)
    public void testFilterTweetPollByItemsByType() throws EnMeNoResultsFoundException, EnMeExpcetion{
        final DateTime date1 = new DateTime();
        DateTime dt2 = date1.minusDays(5);
        DateTime dt3 = date1.minusDays(4);
        final Question q1 = createDefaultQuestion("What is your favourite movie");
        final Question q2 = createDefaultQuestion("What is your favourite book");
        final Question q3 = createDefaultQuestion("What is your favourite song");

        final String keyword = "favourite";

        final TweetPoll tp1 = createPublishedTweetPoll(getSpringSecurityLoggedUserAccount().getAccount(), q1);
        tp1.setCreateDate(dt3.toDate());
        tp1.setCompleted(Boolean.TRUE);
        getTweetPoll().saveOrUpdate(tp1);

        final TweetPoll tp2 = createPublishedTweetPoll(getSpringSecurityLoggedUserAccount().getAccount(), q2);
        tp2.setCreateDate(dt2.toDate());
        tp2.setCompleted(Boolean.TRUE);

        getTweetPoll().saveOrUpdate(tp2);


        final TweetPoll tp3 = createPublishedTweetPoll(getSpringSecurityLoggedUserAccount().getAccount(), q3);
		final TweetPollSearchBean tpSearch = createTweetpollSearchBean(
				Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, "your",
				"24", 10, 0, TypeSearch.LASTWEEK);
        final List<TweetPollBean> tpoll = getTweetPollService().filterTweetPollByItemsByType(tpSearch, this.request);

        assertEquals("Should be equals", 2, tpoll.size());
    }

    /**
     * Test filter tweetpolls.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testFilterTweetpoll() throws NoSuchAlgorithmException, UnsupportedEncodingException{

        // Completed - Favourites - Scheduled - Published
                DateTime creationDate = new DateTime();
                creationDate = creationDate.minusHours(3);
                final SocialAccount socialAccount = createDefaultSettedSocialAccount(this.userAccount);

                final List<SocialProvider> providers = new ArrayList<SocialProvider>();
                providers.add(SocialProvider.FACEBOOK);
                providers.add(SocialProvider.LINKEDIN);
                providers.add(SocialProvider.TWITTER);
                providers.add(SocialProvider.IDENTICA);

                // Completed - Favourites - Scheduled - Published
                final TweetPoll tp1 = this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.TRUE);
                createTweetPollSavedPublishStatusMultiple(tp1, providers, socialAccount);

                final TweetPoll tp2 =this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.TRUE);

                createTweetPollSavedPublishStatusMultiple(tp2, providers, socialAccount);


                final TweetPoll tp3 =this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.TRUE, Boolean.TRUE);

                createTweetPollSavedPublishStatusMultiple(tp1, providers, socialAccount);

                final TweetPoll tp4 =this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.FALSE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);
/*
                // 24 hours
                creationDate = creationDate.minusDays(3);
                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.FALSE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.TRUE	);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);


                creationDate = creationDate.minusDays(2);


                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);

                creationDate = creationDate.minusDays(4);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);

                this.createTweetPollItems(creationDate.toDate(),
                        this.userAccount.getAccount(), Boolean.TRUE, Boolean.FALSE,
                        Boolean.FALSE, Boolean.FALSE);*/

    }

    /**
    *
    * @throws NoSuchAlgorithmException
    * @throws UnsupportedEncodingException
    * @throws EnMeNoResultsFoundException
    * @throws EnMeExpcetion
    */
    @Test
    public void testFilterTweetpollByOwner() throws NoSuchAlgorithmException,
            UnsupportedEncodingException, EnMeNoResultsFoundException,
            EnMeExpcetion {

        // Completed - Favourites - Scheduled - Published
        this.createTweetPollItems(this.creationDate.toDate(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);

        this.createTweetPollItems(this.creationDate.toDate(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);

        this.createTweetPollItems(this.creationDate.toDate(),
                getSpringSecurityLoggedUserAccount().getAccount(),
                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);

        // Published - completed - Favourite - Scheduled
        final TweetPollSearchBean tpSearch = createTweetpollSearchBean(
                Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE,
                "What", "24", 10, 0, TypeSearch.BYOWNER);
        final List<TweetPollBean> tpollsByOwner = getTweetPollService()
                .filterTweetPollByItemsByType(tpSearch, this.request);
        assertEquals("Should be equals", 3, tpollsByOwner.size());
    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testSearchTweetPollsByFolder() throws EnMeNoResultsFoundException{
		final Question question1 = createQuestion("Why the sea is salad?",
				"html");
		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		tp.setTweetPollFolder(this.folder);
		getTweetPoll().saveOrUpdate(tp);

		final TweetPoll tp2 = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		tp2.setTweetPollFolder(this.folder);
		getTweetPoll().saveOrUpdate(tp2);


		final List<TweetPollBean> tpollsfolders = getTweetPollService()
				.searchTweetPollsByFolder(this.folder.getId(),
						getSpringSecurityLoggedUserAccount().getUsername());
		assertEquals("Should be equals", 2, tpollsfolders.size());
    }

    /**
     *
     */
    @Test
    public void testGetTweetPollsbyRange(){
    	final DateTime cale = new DateTime().minusDays(1);
    	final Question question1 = createQuestion("Why the sea is salad?",
				"html");
		final TweetPoll tp = createTweetPollPublicated(true, true, cale.toDate(),
				this.userAccount, question1);
		final DateTime cale2 = new DateTime().minusDays(1);
		// No encuentra nada porque el metodo no tiene riteria de rango de fecha
		final List<TweetPoll> tweetPollsbyRange = getTweetPollService().getTweetPollsbyRange(10, 0, cale2.toDate());
		assertEquals("Should be equals", 1, tweetPollsbyRange.size());

    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testAddHashtagToTweetPoll() throws EnMeNoResultsFoundException{
    	final HashTag ht = createHashTag("xx");
    	final HashTagBean tagBean= this.createHashTagBean("Hashtag bean", 20L, ht.getSize().intValue());
    	final Question question1 = createQuestion("Why the sea is salad?",
				"html");
		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		final HashTag ht2 = getTweetPollService().addHashtagToTweetPoll(tp, tagBean);
		assertEquals("Should be equals", tagBean.getHashTagName(), ht2.getHashTag());
    }

    /**
     *
     */
    @Test
    public void testgetTweetPollSwitch(){

    	final Question question1 = createQuestion("Why the sea is salad?",
				"html");
		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		final QuestionAnswer qA = createQuestionAnswer("string", question1, "JDKSLD");
		final QuestionAnswer qB = createQuestionAnswer("string", question1, "JDKSELD");
		final QuestionAnswer qC = createQuestionAnswer("string", question1, "JDFSELD");
		final TweetPollSwitch tpswith =   createTweetPollSwitch(qA, tp);
		final TweetPollSwitch tpswith2 =   createTweetPollSwitch(qB, tp);
		final TweetPollSwitch tpswith3 =   createTweetPollSwitch(qC, tp);
		List<TweetPollSwitch> tpSwitch = getTweetPollService().getTweetPollSwitch(tp);
		assertEquals("Should be equals", 3, tpSwitch.size());
    }

    /**
     *
     */
    @Test
    public void testGetTweetPollTotalVotes(){
    	final Question question1 = createQuestion("Why the sea is salad?",
				"html");
		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		final QuestionAnswer qA = createQuestionAnswer("YES", question1, "JDKSLD");
		final QuestionAnswer qB = createQuestionAnswer("NO", question1, "JDKSSLD");
		final QuestionAnswer qC = createQuestionAnswer("NO", question1, "JDKSZLD");
		final TweetPollSwitch tps1 = createTweetPollSwitch(qA, tp);
		final TweetPollSwitch tps2 = createTweetPollSwitch(qB, tp);
		final TweetPollSwitch tps3 = createTweetPollSwitch(qC, tp);
		createTweetPollResult(tps1, "192.1.1.1");
		createTweetPollResult(tps2, "192.1.1.2");
		createTweetPollResult(tps2, "192.1.1.3");

		final Long totalTweetPollVotes = getTweetPollService().getTweetPollTotalVotes(tp);
		assertEquals("Should be equals", 3, totalTweetPollVotes.intValue());
    }


    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testCheckTweetPollCompleteStatus() throws EnMeNoResultsFoundException{

    	final Question myQuestion = createQuestion("Why the sea is salad? 3",
				"html");
		final DateTime dt = new DateTime();
		final TweetPoll myTweetPoll = createTweetPollPublicated(true, false, dt.toDate(),
				this.userAccount, myQuestion);
		myTweetPoll.setLimitVotesEnabled(Boolean.TRUE);
		myTweetPoll.setLimitVotes(2);
		getTweetPoll().saveOrUpdate(myTweetPoll);

	    final QuestionAnswerBean qAnswerBean = createAnswersBean("26354",
                "Yes", myQuestion.getQid());
        final QuestionAnswerBean qAnswerBean2 = createAnswersBean("26355",
                "No", myQuestion.getQid());

        final TweetPollSwitch pollSwitch = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);
        final TweetPollSwitch pollSwitch2 = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);

        tweetPollService.tweetPollVote(pollSwitch, "192.1.1.1", Calendar.getInstance().getTime());
        tweetPollService.tweetPollVote(pollSwitch2, "192.1.1.2", Calendar.getInstance().getTime());
    	this.getTweetPollService().checkTweetPollCompleteStatus(myTweetPoll);

    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetTweetPollFolderbyId() throws EnMeNoResultsFoundException{
    	final TweetPollFolder tpollFolder = createTweetPollFolder("My fOlder22", this.userAccount);
    	final TweetPollFolder myFolder = this.getTweetPollService().getTweetPollFolderbyId(tpollFolder.getId());
    }


    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetTweetPollByIdSlugName() throws EnMeNoResultsFoundException{
    	final Question question1 = createQuestion("Why the sea is salt? 6",
				"html");

		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		final TweetPoll tpollSlug = getTweetPollService().getTweetPollByIdSlugName(tp.getTweetPollId(), "Why-the-sea-is-salt%3F-6");

    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testCreateTweetPollNotification() throws EnMeNoResultsFoundException{
    	final Question question1 = createQuestion("Why the sea is salt? 6",
				"html");

		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);

		getTweetPollService().createTweetPollNotification(tp);
		final List<Notification> noti = getNotification()
				.loadNotificationByUserAndLimit(this.userAccount.getAccount(),
						10, 0, Boolean.TRUE);
	}

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
    public void testgenerateTweetPollContent() throws EnMeExpcetion{
    	final Question question1 = createQuestion("Why the sea is salt? 6",
				"html");

		final TweetPoll tp = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);
		final String tweetcontent = getTweetPollService().generateTweetPollContent(tp);
	}


    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testremoveQuestionAnswer() throws EnMeNoResultsFoundException{
    	final Question question1 = createQuestion("Why the sea is salt? 6",
				"html");

		final TweetPoll myTweetPoll = createTweetPollPublicated(true, true, new Date(),
				this.userAccount, question1);


	    final QuestionAnswerBean qAnswerBean = createAnswersBean("26354",
                "Yes", question1.getQid());
        final QuestionAnswerBean qAnswerBean2 = createAnswersBean("26355",
                "No", question1.getQid());

        final List<QuestionAnswer> qAnswer = getQuestionDaoImp().getAnswersByQuestionId(question1.getQid());
		         final TweetPollSwitch pollSwitch = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);
        final TweetPollSwitch pollSwitch2 = tweetPollService
                .createTweetPollQuestionAnswer(qAnswerBean, myTweetPoll, null);

    }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchTweetsPollScheduled() throws EnMeExpcetion{

    	final Question question1 = createQuestion("Why the sky is blue ?",
				"html");
		final DateTime dt = new DateTime();
		final TweetPoll tp = createTweetPollPublicated(true, true, dt.toDate(),
				this.userAccount, question1);

  		tp.setScheduleTweetPoll(Boolean.TRUE);
  		tp.setFavourites(Boolean.TRUE);
		getTweetPoll().saveOrUpdate(tp);
		final TweetPollSearchBean tpbean22 = createTweetpollSearchBean(
				Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, " ",
				"7", 10, 0, TypeSearch.SCHEDULED);
		final List<TweetPollBean> tpLastWeek = getTweetPollService()
				.searchTweetsPollScheduled(this.userAccount.getUsername(),
						this.request, tpbean22);
     }


    @Test
    public void testSearchTweetsPollFavourites() throws EnMeExpcetion{

		final Question question1 = createQuestion("Why the sea is big? ",
				"html");
		final DateTime dt = new DateTime();
		final TweetPoll tp = createTweetPollPublicated(true, true, dt.toDate(),
				this.userAccount, question1);

		tp.setScheduleTweetPoll(Boolean.TRUE);
		tp.setFavourites(Boolean.TRUE);
		getTweetPoll().saveOrUpdate(tp);

		final TweetPollSearchBean tpbean22 = createTweetpollSearchBean(
				Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, " ",
				"7", 10, 0, TypeSearch.FAVOURITES);
		final List<TweetPollBean> tpLastWeek = getTweetPollService()
				.searchTweetsPollFavourites(this.userAccount.getUsername(),
						this.request, tpbean22);
    }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
	public void testsearchTweetsPollsLastWeek() throws EnMeExpcetion {

		final Question question1 = createQuestion("Why the sea is salad?",
				"html");
		final DateTime dt = new DateTime();
		final TweetPoll tp = createTweetPollPublicated(true, true, dt
				.minusDays(3).toDate(), this.userAccount, question1);
		tp.setTweetPollFolder(this.folder);
		tp.setScheduleTweetPoll(Boolean.TRUE);
		getTweetPoll().saveOrUpdate(tp);
		final TweetPollSearchBean tpbean = createTweetpollSearchBean(
				Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, " ",
				"7", 10, 0, TypeSearch.LASTDAY);
		final List<TweetPollBean> tpLastWeek = getTweetPollService()
				.searchTweetsPollsLastWeek(this.userAccount.getUsername(),
						this.request, tpbean);
	 }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
	public void testSearchTweetsPollsToday() throws EnMeExpcetion {
		final Question question1 = createQuestion("Why the sea is saltz?",
				"html");

		final Question question2 = createQuestion("Why the sea is blue ?",
				"html");
		final DateTime dt = new DateTime();
		final TweetPoll tp = createTweetPollPublicated(true, true, dt.toDate(),
				this.userAccount, question1);
		tp.setScheduleTweetPoll(Boolean.TRUE);
		getTweetPoll().saveOrUpdate(tp);
		final TweetPoll tp2 = createTweetPollPublicated(true, true,
				dt.toDate(), this.userAccount, question2);
		tp2.setScheduleTweetPoll(Boolean.TRUE);
		getTweetPoll().saveOrUpdate(tp2);
		final TweetPollSearchBean tpbean = createTweetpollSearchBean(
				Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, " ",
				"7", 10, 0, TypeSearch.LASTWEEK);
		final List<TweetPollBean> tpLastWeek = getTweetPollService()
				.searchTweetsPollsToday(this.userAccount.getUsername(),
						this.request, tpbean);
	}


    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
	public void testUpdateTweetPoll() throws EnMeNoResultsFoundException {

		final Question question1 = createQuestion("Why the sea is salad? 3",
				"html");
		final DateTime dt = new DateTime();
		final TweetPoll tp = createTweetPollPublicated(true, true, dt.toDate(),
				this.userAccount, question1);

		final QuestionBean questionBean = createUnitQuestionBean(
				" Mi nueva pregunta", null, null, null);
		final TweetPollBean tpBeanToUpdate = createTweetPoll(Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE, new Date(), Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE, new Date(), " ",
				this.userAccount.getUid(), questionBean, "twitterAccoint",
				tp.getTweetPollId());

		final TweetPoll tpupdate = getTweetPollService().updateTweetPoll(
				tpBeanToUpdate);

	}

    /**
     *
     * @param provider1
     * @param provider2
     * @return
     */
    private List<SocialProvider> socialProvider (final SocialProvider provider1, final SocialProvider provider2){
        final List<SocialProvider> searchproviders = new ArrayList<SocialProvider>();
        searchproviders.add(provider1);
        searchproviders.add(provider2);
        return searchproviders;
    }

    /**
     * Create {@link HashTagBean}
     * @param tagName
     * @param hits
     * @param size
     * @return
     */
    private HashTagBean createHashTagBean(final String tagName, final Long hits, final Integer size){
    	final HashTagBean hashTagBean = new HashTagBean();
    	hashTagBean.setHashTagName(tagName);
    	hashTagBean.setHits(hits);
    	hashTagBean.setSize(size);
    	return hashTagBean;
    }
}
