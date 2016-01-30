/*
 ***********************************************************************************
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
import java.util.Locale;

import org.encuestame.business.service.PollService;
import org.encuestame.core.service.IPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.config.AbstractSpringSecurityContext;
import org.encuestame.utils.EnumerationUtils;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.categories.test.InternetTest;
import org.encuestame.utils.enums.*;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.web.*;
import org.encuestame.utils.web.search.PollSearchBean;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockHttpServletRequest;

 /**
 * Test for {@link PollService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 17/05/2010 19:35:36
 */
@Category(DefaultTest.class)
public class TestPollService extends AbstractSpringSecurityContext{

     /** {@link Account} **/
    private Account user;

    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;

    /** {@link Question} **/
    private Question question;

    /** {@link IPollService} **/
    @Autowired
    private IPollService pollService;

    /** {@link Poll} **/
    private Poll poll;

    private UserAccount userAccount;

    /** {@link EmailList} **/
    private EmailList emailList;

    /** {@link Email} **/
    private Email emails;

    /** {@link PollFolder}. **/
    private PollFolder folder;

    /** **/
    private Integer MAX_RESULTS = 10;

    /** **/
    private Integer START = 0;

    /** **/
    private String[] answers = new String[4];

    /** **/
    private HashTag tag1;

    /** **/
    private HashTag tag2;

    /** **/
    private HashTag tag3;

    /** **/
    private HashTag tag4;

    /** {@link HashTagBean} list. **/
    private List<HashTagBean> tagBeanList = new ArrayList<HashTagBean>();

    /**
     * Init.
     */
    @Before
    public void serviceInit() {
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?","html");

        final Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK,-1);
        final Date yesterdayDate= calendarDate.getTime();

        this.poll = createPoll(yesterdayDate, this.question, "FDK125", getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
        this.emailList = createDefaultListEmail(this.userAccount.getAccount());
        createDefaultListEmail(this.user, "default");
        this.emails = createDefaultEmails("paola@demo.com", this.emailList);
        createDefaultEmails("dianmorales@demo.com", this.emailList);
        this.folder = createPollFolder("folder 1", getSpringSecurityLoggedUserAccount());
        this.poll.setPollFolder(folder);

        // Answers.
        this.answers[0] = "answer One";
        this.answers[1] = "answer Two";
        this.answers[2] = "answer Three";
        this.answers[3] = "answer Four";

        // HashTags
        this.tag1 = createHashTag("one");
        this.tag2 = createHashTag("two");
        this.tag3 = createHashTag("three");
        this.tag4 = createHashTag("four");

        // HashtagBean List
        tagBeanList.add(ConvertDomainBean
                .convertHashTagDomain(this.tag1));
        tagBeanList.add(ConvertDomainBean
                .convertHashTagDomain(this.tag2));
        tagBeanList.add(ConvertDomainBean
                .convertHashTagDomain(this.tag3));
        tagBeanList.add(ConvertDomainBean
                .convertHashTagDomain(this.tag4));

        //fake request
        request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.ENGLISH);
     }

    /**
     * Test single createPoll.
     * @throws Exception exception
     */
    @Test
    public void testcreatePoll() throws Exception{
        final String[] answer = {"a", "b"};
        final String[] hashtag = {"hastag1", "hastag2"};
        /*
         * questionName
            answer
            hashtag
            showComments
            showResults
            multipleSelection
            limitVotes
            closeDate
         */
        final CreatePollBean cb = createPrivatePollBean(
                "Question",
                answer,
                hashtag,
                "MODERATE",
                "ALL",
                true,
                null,
                null,
                Boolean.TRUE,
                Boolean.TRUE);
        //System.out.println(cb);
        final Poll myPoll = this.pollService.createPoll(cb);
        Assert.assertNotNull(myPoll);
        assertEquals(myPoll.getQuestion().getQuestion(), "Question");
        assertEquals(myPoll.getHashTags().size(), 2);
        assertEquals(myPoll.getPollCompleted(), false);
        assertEquals(myPoll.getPublish(), true);
        //FIXME: ENCUESTAME-673
        assertEquals(myPoll.getMultipleResponse(), AbstractSurvey.MultipleResponse.SINGLE);
        assertEquals(myPoll.getCloseAfterquota(), false);
        assertEquals(myPoll.getCloseAfterDate(), false);
        assertEquals(myPoll.getShowResults(), ShowResultsOptions.ALL);
        assertEquals(myPoll.getAllowRepeatedVotes(), false);
        assertEquals(myPoll.getShowComments(), CommentOptions.MODERATE);
        assertEquals(myPoll.getIsHidden(), true);
        assertEquals(myPoll.getIsPasswordProtected(), true);
        assertNotNull(myPoll.getPassword());
    }

     /**
      * Test createPoll with limits vote by IP.
      * @throws Exception exception
      */
     @Test
     public void testcreatePollLimtIP() throws Exception{
         final String[] answer = {"a", "b"};
         final String[] hashtag = {"hastag1", "hastag2"};
         final CreatePollBean cb = createPrivatePollBean(
                 "Question",
                 answer,
                 hashtag,
                 "MODERATE",
                 "ALL",
                 true,
                 null,
                 null,
                 true,
                 true
                 );
         cb.setRepeatedVotes(4);
         cb.setMultiple(false);
         cb.setLimitVote(null);
         //System.out.println(cb);
         final Poll myPoll = this.pollService.createPoll(cb);
         Assert.assertNotNull(myPoll);
         assertEquals(myPoll.getQuestion().getQuestion(), "Question");
         assertEquals(myPoll.getHashTags().size(), 2);
         assertEquals(myPoll.getPollCompleted(), false);
         assertEquals(myPoll.getPublish(), true);
         assertEquals(myPoll.getAllowRepeatedVotes(), true);
         assertEquals(myPoll.getLimitVotesEnabled(), false);
         assertEquals(myPoll.getMultipleResponse(), AbstractSurvey.MultipleResponse.SINGLE);
         assertEquals(myPoll.getCloseAfterquota(), false);
         assertEquals(myPoll.getCloseAfterDate(), false);
         assertEquals(myPoll.getShowResults(), ShowResultsOptions.ALL);
         assertEquals(myPoll.getShowComments(), CommentOptions.MODERATE);
         Boolean valid1 = this.pollService.checkLimitVotesByIP("0.0.0.0.2", myPoll);
         assertEquals(valid1, false);
         List<QuestionAnswerBean> answers = this.pollService.retrieveAnswerByQuestionId(myPoll.getQuestion().getQid());
         assertEquals(answers.size(), 2);
         if (answers.size() > 1) {
             QuestionAnswerBean answer1 = answers.get(0);
             this.pollService.vote(myPoll, myPoll.getQuestion().getSlugQuestion(), "0.0.0.0.2", answer1.getAnswerId());
             this.pollService.vote(myPoll, myPoll.getQuestion().getSlugQuestion(), "0.0.0.0.2", answer1.getAnswerId());
             this.pollService.vote(myPoll, myPoll.getQuestion().getSlugQuestion(), "0.0.0.0.2", answer1.getAnswerId());
             this.pollService.vote(myPoll, myPoll.getQuestion().getSlugQuestion(), "0.0.0.0.2", answer1.getAnswerId());
             this.pollService.vote(myPoll, myPoll.getQuestion().getSlugQuestion(), "0.0.0.0.2", answer1.getAnswerId());
             this.pollService.vote(myPoll, myPoll.getQuestion().getSlugQuestion(), "0.0.0.0.2", answer1.getAnswerId());
             Boolean quota2 = this.pollService.checkLimitVotesByIP("0.0.0.0.2", myPoll);
             assertEquals(quota2, Boolean.TRUE);
         } else {
             assertEquals(true, false);
         }
     }

     @Test
     public void testvalidatePollIP() throws EnMeExpcetion {
            Poll pQuota =  this.createQuickPoll("test1");
            Integer status1 = this.pollService.validatePollIP("0.0.0.0.1", pQuota);
            assertEquals(status1, new Integer(0));
            List<QuestionAnswerBean> answers = this.pollService.retrieveAnswerByQuestionId(pQuota.getQuestion().getQid());
            assertEquals(answers.size(), 2);
            QuestionAnswerBean answer1 = answers.get(0);
            this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
            this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
            this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
            this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
            this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
            this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
            Integer status2 = this.pollService.validatePollIP("0.0.0.0.1", pQuota);
            assertEquals(status2, new Integer(6));
     }

     @Test
     public void testgetPollDetailInfo() throws EnMeExpcetion {
         Poll pQuota =  this.createQuickPoll("test1");
         //step1
         final PollDetailBean detail = this.pollService.getPollDetailInfo(pQuota.getPollId());
         assertNotNull(detail);
         assertEquals(detail.getListAnswers().size(), 2);
         assertEquals(detail.getResults().size(), 2);
         //step2
         List<QuestionAnswerBean> answers = this.pollService.retrieveAnswerByQuestionId(pQuota.getQuestion().getQid());
         assertEquals(answers.size(), 2);
         QuestionAnswerBean answer1 = answers.get(0);
         this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
         this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
         this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
         this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
         this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
         this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
         final PollDetailBean detail2 = this.pollService.getPollDetailInfo(pQuota.getPollId());
         assertEquals(detail2.getResults().size(), 2);
         PollBeanResult re1 = detail2.getResults().get(0);
         PollBeanResult re2 = detail2.getResults().get(1);
         assertEquals(re1.getVotes(), new Long(6));
         assertEquals(re1.getPercent(), "100%");
         assertEquals(re2.getVotes(), new Long(0));
         assertEquals(re2.getPercent(), "0.00%");
         assertNotNull(detail2.getPollBean());
         assertNotNull(detail2.getPollBean().getId());
     }

     /**
      *
      * @param question
      * @return
      * @throws EnMeExpcetion
      */
     private Poll createQuickPoll(String question)  throws EnMeExpcetion{
         final String[] answer = {"a", "b"};
         final String[] hashtag = {"hastag1", "hastag2"};
         final CreatePollBean cb = createPollBean(
                 question,
                 answer,
                 hashtag,
                 "MODERATE",
                 "ALL",
                 true,
                 null,
                 null);
         cb.setRepeatedVotes(5);
         cb.setMultiple(false);
         cb.setLimitVote(10);
         cb.setIsHidden(true);
         cb.setIsPasswordProtected(true);
         cb.setPassword("dada");
         return this.pollService.createPoll(cb);
     }

     /**
      *
      * @throws EnMeExpcetion
      */
     @Test
     public void testfilterPollByItemsByType() throws EnMeExpcetion{
         Poll p1 =  this.createQuickPoll("test1");
         Poll p2 = this.createQuickPoll("test2");
         Poll p3 = this.createQuickPoll("test3");
         Poll p4 = this.createQuickPoll("juan");
         flushIndexes();
         //all items
         List<PollBean> allItems =  this.pollService.filterPollByItemsByType(
                 TypeSearch.ALL,
                 "t",
                 200,
                 0);
         assertEquals(allItems.size(), 5);
         // no items by keyword
         List<PollBean> keyItems =  this.pollService.filterPollByItemsByType(
                 TypeSearch.KEYWORD,
                 "a",
                 200,
                 0);
         assertEquals(keyItems.size(), 0);
         List<PollBean> keyItems1 =  this.pollService.filterPollByItemsByType(
                 TypeSearch.KEYWORD,
                 "test",
                 200,
                 0);
         assertEquals(keyItems1.size(), 3);
         //
         final DateTime d = new DateTime(Calendar.getInstance().getTime());
         d.minusDays(3);
         p1.setCreateDate(d.toDate());
         getPollDao().saveOrUpdate(p1);
         flushIndexes();
         List<PollBean> keyItems2 =  this.pollService.filterPollByItemsByType(
                 TypeSearch.LASTDAY,
                 "test",
                 200,
                 0);
         assertEquals(keyItems2.size(), 0);
         final DateTime d2 = new DateTime(Calendar.getInstance().getTime());
         d.minusWeeks(3);
         p1.setCreateDate(d2.toDate());
         getPollDao().saveOrUpdate(p1);
         flushIndexes();
         List<PollBean> keyItems3 =  this.pollService.filterPollByItemsByType(
                 TypeSearch.LASTWEEK,
                 "",
                 200,
                 0);
         assertEquals(keyItems3.size(), 0);

         //
         p1.setFavourites(true);
         getPollDao().saveOrUpdate(p1);
         flushIndexes();
         List<PollBean> keyItems4 =  this.pollService.filterPollByItemsByType(
                 TypeSearch.FAVOURITES,
                 "",
                 200,
                 0);
         assertEquals(keyItems4.size(), 2);

         //
         List<PollBean> keyItems5 =  this.pollService.filterPollByItemsByType(
                 TypeSearch.BYOWNER,
                 "",
                 200,
                 0);
         assertEquals(keyItems5.size(), 5);
     }

     /**
      *
      * @throws EnMeExpcetion
      */
     @Test(expected = EnMeExpcetion.class)
     public void testfilterPollByItemsByTypeException() throws EnMeExpcetion{
         List<PollBean> keyItems5 =  this.pollService.filterPollByItemsByType(
                 EnumerationUtils.getEnumFromString(TypeSearch.class, "dsda"),
                         "",
                         200,
                         0);
     }

     @Test
     public void testsearchPollsToday()  throws EnMeExpcetion {
        final SocialAccount social1 = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
        PollSearchBean searchBean = new PollSearchBean();
        final List<Long> sa = new ArrayList<Long>();
        sa.add(social1.getId());
        searchBean.setSocialAccounts(sa);
        List<SearchBean> list = this.pollService.searchPollsToday(getUsernameLogged(), searchBean);
        assertEquals(list.size(), 0);
     }

     @Test
     public void testsearchPollScheduled() throws EnMeExpcetion {
         Poll p1 = this.createQuickPoll("secheduled1");
         Poll p2 = this.createQuickPoll("secheduled2");
         p1.setScheduled(true);
         final DateTime d1 = new DateTime();
         d1.plusDays(4);
         p1.setScheduleDate(d1.toDate());
         final SocialAccount social1 = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
         PollSearchBean searchBean = new PollSearchBean();
         final List<Long> sa = new ArrayList<Long>();
         sa.add(social1.getId());
         searchBean.setSocialAccounts(sa);
         List<SearchBean> list = this.pollService.searchPollScheduled(getUsernameLogged(), searchBean);
         assertEquals(list.size(), 0);
     }

     /**
      *
      * @throws EnMeExpcetion
      */
     //@Test
     public void testfilterSearchPollsByType2() throws EnMeExpcetion{
         final PollSearchBean pollSearchBean = new PollSearchBean();
         List<SearchBean> items =  this.pollService.filterSearchPollsByType(pollSearchBean);
     }

     /**
      *
      * @throws EnMeExpcetion
      */
     @Test
     public void testretrieveFoldersbyKeyword() throws EnMeExpcetion{
         List<PollFolder> folders = this.pollService.retrieveFoldersbyKeyword("test");
         assertEquals(folders.size(), 0);
         this.pollService.createPollFolder("test1");
         List<PollFolder> folders1 = this.pollService.retrieveFoldersbyKeyword("test");
         assertEquals(folders1.size(), 1);
         List<PollFolder> folders2 = this.pollService.retrieveFoldersbyKeyword("facebook");
         assertEquals(folders2.size(), 0);
         this.pollService.createPollFolder("facebook");
         this.pollService.createPollFolder("twitter");
         List<PollFolder> folders3 = this.pollService.retrieveFoldersbyKeyword("facebook");
         assertEquals(folders3.size(), 1);
     }

     /**
      *
      * @throws EnMeExpcetion
      */
     @Test
     public void testrestrictVotesByQuota()  throws EnMeExpcetion {
            Poll pQuota =  this.createQuickPoll("test1 testrestrictVotesByQuota");
            pQuota.setAllowRepeatedVotes(true);
            pQuota.setLimitVotes(4);
            pQuota.setClosedQuota(2);
            assertNotNull(pQuota.getQuestion());
            QuestionAnswer q1 = createQuestionAnswer("aaa", pQuota.getQuestion(), "");
            assertNotNull(q1);
            QuestionAnswer q2 = createQuestionAnswer("bb", pQuota.getQuestion(), "");
            assertNotNull(q2);
            getPollDao().saveOrUpdate(pQuota);
            Boolean quota = this.pollService.restrictVotesByQuota(pQuota);
            assertNotNull(quota);
            assertEquals(quota, Boolean.FALSE);
            List<QuestionAnswerBean> answers = this.pollService.retrieveAnswerByQuestionId(pQuota.getQuestion().getQid());
            assertEquals(answers.size(), 4);
            if (answers.size() > 1) {
                QuestionAnswerBean answer1 = answers.get(0);
                this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
                this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
                this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
                this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
                this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
                this.pollService.vote(pQuota, pQuota.getQuestion().getSlugQuestion(), "0.0.0.0.1", answer1.getAnswerId());
                Boolean quota2 = this.pollService.restrictVotesByQuota(pQuota);
                assertEquals(quota2, Boolean.TRUE);
            } else {
               assertEquals(true, false);
            }
     }


     /**
      *
      * @throws EnMeExpcetion
      */
     @Test
     public void testrestrictVotesByDate()  throws EnMeExpcetion {
         Poll p1 =  this.createQuickPoll("test1");
         p1.setCloseAfterDate(true);
         DateTime close = new DateTime();
         close.plusDays(3);
         p1.setClosedDate(close.toDate());
         p1.setClosedQuota(2);
         getPollDao().saveOrUpdate(p1);
         Boolean quota = this.pollService.restrictVotesByDate(p1);
        // assertEquals(quota, Boolean.FALSE);
         p1.setCloseAfterDate(false);
         p1.setClosedDate(null);
         getPollDao().saveOrUpdate(p1);
         Boolean quota2 = this.pollService.restrictVotesByDate(p1);
         assertEquals(quota2, Boolean.FALSE);
     }

    /**
     * Test getPollsByFolder.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    //TODO: ignore for now.
    public void testgetPollsByFolder() throws EnMeNoResultsFoundException{
        getPollDao().saveOrUpdate(this.poll);
        List<PollBean> polls = this.pollService.getPollsByFolder(ConvertDomainBean
                              .convertFolderToBeanFolder(folder));
        assertEquals(1, polls.size());
    }

    /**
     * Test retrieveFolderPoll.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testretrieveFolderPoll() throws EnMeNoResultsFoundException{
        List<FolderBean> folders = this.pollService.retrieveFolderPoll();
        assertEquals(folders.size(), 1);
    }

    /**
     * Test createPollFolder.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testcreatePollFolder() throws EnMeNoResultsFoundException{
       this.pollService.createPollFolder("folder 2");
       List<FolderBean> folders = this.pollService.retrieveFolderPoll();
       assertEquals(folders.size(), 2);
    }

    /**
     * Test updateFolderName.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testupdateFolderName() throws EnMeNoResultsFoundException{
        this.pollService.updateFolderName(this.folder.getId(), "newFolderName", this.userAccount.getUsername());
        final PollFolder folder = this.getPollDao().getPollFolderById(this.folder.getId());
        assertEquals(folder.getFolderName(), "newFolderName");
    }

    /**
     * test removePollFolder.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void testremovePollFolderBatchUpdateException() throws EnMeNoResultsFoundException{
        final long id = this.folder.getId();
        this.pollService.removePollFolder(id);
    }

    /**
     * Remove Poll Folder.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testremovePollFolder() throws EnMeNoResultsFoundException{
        this.poll.setPollFolder(null);
        getPollDao().saveOrUpdate(this.poll);
        final long id = this.folder.getId();
        this.pollService.removePollFolder(id);
        Assert.assertNull(getPollDao().getPollFolderById(id));
    }

    /**
     * Test Find Polls By User.
     * @throws EnMeNoResultsFoundException
     **/
    @Test
    public void testFindAllPollByUserId() throws EnMeNoResultsFoundException{
        List<PollBean> unitPoll =  new ArrayList<PollBean>();
        unitPoll = pollService.listPollByUser(5, 0);
        assertEquals("should be equals",1, unitPoll.size());
    }

    /**
     * Test Update Question Poll.
     * @throws EnMeExpcetion
     */
    @Test
    public void testUpdateQuestionPoll() throws EnMeExpcetion{
        final Question newQuestion = createQuestion("Why the tooth are white", "pattern");
        final PollBean pb = pollService.updateQuestionPoll(this.poll.getPollId(), newQuestion);
        assertEquals(newQuestion.getQuestion(), pb.getQuestionBean().getQuestionName());
     }

    /**
     * Test publish poll by email list.
     */
    @Category(InternetTest.class)
   // @Test(timeout=80000)
    public void testPublicPollByEmailList(){
        final UnitLists emailUnitList = createUnitEmailList(this.emailList.getIdList(),
                        new Date(), this.emailList.getListName(), this.userAccount.getUid());
         pollService.publicPollByList(this.poll, emailUnitList);
         assertEquals(1, 1);
    }

    /**
     * Retrieve polls by specific date.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetPollsbyDate() throws EnMeNoResultsFoundException{
        final Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK,-1);
        final Date yesterdayDate= calendarDate.getTime();
        final List<PollBean> pbean = this.pollService.getPollsbyDate(yesterdayDate, this.MAX_RESULTS, this.START);
        for (PollBean pollBean : pbean) {
             log.debug(" poll name and Id--> " + pollBean.getQuestionBean().getQuestionName() + "ID -->" + pollBean.getId());
        }
    }


    /**
     * Test remove poll
     * @throws EnMeExpcetion
     */
    @Test
    public void testRemovePoll() throws EnMeExpcetion {
        //this.answers[3] = "answer Four";
        final String[] answer = {"a", "b"};
        final String[] hashtag = {"hastag1", "hastag2"};
        final CreatePollBean cb = createPrivatePollBean("Who Invented The Telescope?",answer, hashtag, "APPROVE", "ALL", true, null, null, false, false);
        final Poll newPollService = this.pollService.createPoll(cb);

        final List<QuestionAnswer> beforeAnswers = getQuestionDaoImp()
                .getAnswersByQuestionId(newPollService.getQuestion().getQid());
        assertEquals(beforeAnswers.size(), 2);

        this.pollService.removePoll(newPollService.getPollId());

        /*
         * final Poll checkPoll = this.pollService.getPollById(newPollService
         * .getPollId());
         *
         * assertEquals("Should be equals", "poll invalid with this id" +
         * newPollService.getPollId(), checkPoll);
         */

        final List<QuestionAnswer> afterAnswers = getQuestionDaoImp()
                .getAnswersByQuestionId(newPollService.getQuestion().getQid());
        assertEquals(afterAnswers.size(), 0);

    }

    /**
     * Test Remove HashTags from Poll.
     * @throws EnMeExpcetion
     */
    //@Test
    public void testRemoveHashTagsFromPoll() throws EnMeExpcetion {
        final QuestionBean question = ConvertDomainBean
                .convertQuestionsToBean(this.question);
        final PollBean unitPoll = ConvertDomainBean
                .convertPollDomainToBean(this.poll);
        unitPoll.setQuestionBean(question);


        final String[] answer = {"a", "b"};
        final String[] hashtag = {"hastag1", "hastag2"};
        final CreatePollBean cb = createPollBean("dddd",answer, hashtag, "APPROVE", "ALL", true, null, null);
        final Poll myPoll = this.pollService.createPoll(cb);

        Assert.assertNotNull(myPoll);
        final String[] answer1 = {"a", "b"};
        final String[] hashtag2 = {"hastag3", "hastag4"};
        final CreatePollBean cb2 = createPollBean("dddd",answer1, hashtag2, "APPROVE", "ALL", true, null, null);
        final Poll myPoll2 = this.pollService.createPoll(cb2);
        Assert.assertNotNull(myPoll2);

        final List<Poll> retrievePollsbyTagBeforeRemove = getPollDao()
                .getPollByHashTagName(this.tag1.getHashTag(), this.START,
                        this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        assertEquals(retrievePollsbyTagBeforeRemove.size(), 2);

        // Remove hashtag
        myPoll.getHashTags().remove(tag1);
        getPollDao().saveOrUpdate(myPoll);

        final List<Poll> retrievePollsbyTagAfterRemove = getPollDao()
                .getPollByHashTagName(this.tag1.getHashTag(), this.START,
                        this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        assertEquals(retrievePollsbyTagAfterRemove.size(), 1);
    }

    /**
     * Get poll by answer
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetPollByAnswerId() throws EnMeNoResultsFoundException {
        final Question q = createDefaultQuestion("question 1");
        final Question q1 = createDefaultQuestion("question 2");
        final QuestionAnswer a = createQuestionAnswer("Yes", q, "1234DF");
        final QuestionAnswer a1 = createQuestionAnswer("No", q, "5678MG");
        final QuestionAnswer a2 = createQuestionAnswer("Maybe", q1, "9101112");
        final Poll p = createDefaultPoll(q, userAccount);

        final Poll mp = this.pollService.getPollByAnswerId(p.getPollId(),
                a.getQuestionAnswerId(), null);
        Assert.assertNotNull(mp);
         // final Poll mp2 = this.pollService.getPollByAnswerId(p.getPollId(), a2.getQuestionAnswerId(), null);

    }

    /*
     * Test for retrieve poll results.
     */
    @Test
    public void testGetResultVotes(){
            final Question quest = createQuestion("Do you like action movies", "Yes/No");
            final Poll poll = createPoll(new Date(), quest, "ACTMOV", this.userAccount, Boolean.TRUE, Boolean.TRUE);
            final QuestionAnswer qansw = createQuestionAnswer("Yes", quest, "2023");
            final QuestionAnswer qansw2 =createQuestionAnswer("No", quest, "2024");
            // Create poll results for QuestionAnswer2 = 3
            createPollResults(qansw2, poll);
            createPollResults(qansw2, poll);
            createPollResults(qansw2, poll);
            // Create poll results for QuestionAnswer1 = 7
            createPollResults(qansw, poll);
            createPollResults(qansw, poll);
            createPollResults(qansw, poll);
            createPollResults(qansw, poll);
            createPollResults(qansw, poll);
            createPollResults(qansw, poll);
            createPollResults(qansw, poll);

        final List<PollBeanResult> pollbean = this.pollService
                .getResultVotes(poll);
        assertEquals(pollbean.size(), 2);
        for (PollBeanResult pollBeanResult : pollbean) {
            if (pollBeanResult.getAnswerBean().getAnswerId()
                    .equals(qansw.getQuestionAnswerId())) {
                assertEquals("For answer1 should be equals",
                        pollBeanResult.getVotes().toString(), "7");
            }
            if (pollBeanResult.getAnswerBean().getAnswerId()
                    .equals(qansw2.getQuestionAnswerId())) {
                assertEquals("For answer2 should be equals",
                        pollBeanResult.getVotes().toString(), "3");
            }
        }

    }

    /**
     * Create Polls for Search test
     */
    private void createPollsToFilterSearch(){
    	final DateTime lastDay = new DateTime();
    	final DateTime lastWeek = new DateTime();
    	final Poll poll2 = createDefaulPollWithPrivacy(
				createDefaultQuestion("What Is Sesquipedalophobia?"),
				getSpringSecurityLoggedUserAccount(),
				new Date(),
				true,
				false);
    	poll2.setFavourites(true);
    	getPollDao().saveOrUpdate(poll2);

    	createDefaulPollWithPrivacy(
    			createDefaultQuestion("Where Is The Smallest Bone in The Human Body?"),
    			getSpringSecurityLoggedUserAccount(),
    			lastDay.minusHours(3).toDate(),
    			true,
    			true);

    	final Poll poll3 = createDefaulPollWithPrivacy(
    			createDefaultQuestion("What Is The Longest Interstate Highway In The US?"),
    			getSpringSecurityLoggedUserAccount(),
    			new Date(),
    			true,
    			true);
    	poll3.setFavourites(true);
    	getPollDao().saveOrUpdate(poll3);

    	final Poll poll1 = createPoll(lastDay.minusHours(1).toDate(),
    			createDefaultQuestion("Do Fish Get Thirsty?"),
    			"FDK2335",
    			getSpringSecurityLoggedUserAccount(),
    			Boolean.TRUE,
    			Boolean.TRUE);
    			poll1.setIsHidden(true);
    			poll1.setIsPasswordProtected(false);
    			getPollDao().saveOrUpdate(poll1);

    	final Poll poll4 = createDefaulPollWithPrivacy(
    			createDefaultQuestion("What's The Origin Of The Easter Bunny?"),
    			getSpringSecurityLoggedUserAccount(),
    			lastDay.minusHours(1).toDate(),
    			false,
    			true);
    	poll4.setFavourites(true);
    	getPollDao().saveOrUpdate(poll4);

    	createDefaulPollWithPrivacy(
    			createDefaultQuestion("What's The Origin Of The Term Checkmate In Chess?"),
    			getSpringSecurityLoggedUserAccount(),
    			lastWeek.minusDays(4).toDate(),
    			true,
    			true);

    	createDefaulPollWithPrivacy(
    			createDefaultQuestion("What Do You Like To Do in Your Spare Time?"),
    			getSpringSecurityLoggedUserAccount(),
    			lastWeek.minusDays(3).toDate(),
    			false,
    			true);



    }

    /**
     * Test Advanced search poll by User.
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    @Test
    public void testFilterSearchPollsbyUser() throws EnMeNoResultsFoundException, EnMeExpcetion{
    	createPollsToFilterSearch();

		final PollSearchBean search1 = createPollSearchBean(true, false, false,
				false, "W", null, 10, 0, TypeSearch.BYOWNER, true, false);

		List<SearchBean> pollsbyUser1 = this.pollService.filterSearchPollsByType(search1);
		assertEquals(pollsbyUser1.size(), 4);

		final PollSearchBean search2 = createPollSearchBean(true, false, false,
				false, "B", null, 10, 0, TypeSearch.BYOWNER, false, true);

		List<SearchBean> pollsbyUser2 = this.pollService.filterSearchPollsByType(
				search2);
		assertEquals(pollsbyUser2.size(), 2);

    }

    /**
     * Test Advanced search poll by Lastday filter.
     * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testFilterSearchPollsbyLastDay() throws EnMeNoResultsFoundException, EnMeExpcetion{
    	createPollsToFilterSearch();
    	final PollSearchBean search1 = createPollSearchBean(true, false, false,
				false, "F", null, 10, 0, TypeSearch.LASTDAY, true, false);
    	List<SearchBean> allPolls1 = this.pollService.filterSearchPollsByType(
    			search1);
    	assertEquals(allPolls1.size(), 1);
    }

    /**
     * Test Advanced search {@link Poll} by Lastweek filter.
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    @Test
    public void testFilterSearchPollsbyLastWeek() throws EnMeExpcetion{
    	createPollsToFilterSearch();
    	final PollSearchBean search1 = createPollSearchBean(true, false, false,
				false, "in", null, 10, 0, TypeSearch.LASTWEEK, false, false);
    	List<SearchBean> allPolls1 = this.pollService.filterSearchPollsByType(
    			search1);
    	assertEquals(allPolls1.size(), 4);
    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    @Test
	public void testFilterSearchPollsbyFavourite()
			throws EnMeNoResultsFoundException, EnMeExpcetion {
		createPollsToFilterSearch();
		final PollSearchBean search1 = createPollSearchBean(true, false, true,
				false, "B", null, 10, 0, TypeSearch.FAVOURITES, false, false);
		List<SearchBean> allPolls1 = this.pollService.filterSearchPollsByType(
				search1);
		assertEquals(allPolls1.size(), 1);
	}

    /**
     *
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    @Test
    public void testFilterSearchPollsbyAll() throws EnMeExpcetion{
    	createPollsToFilterSearch();
    	final PollSearchBean search1 = createPollSearchBean(true, false, false,
				false, "Do", null, 10, 0, TypeSearch.ALL, false, true);
    	List<SearchBean> allPolls1 = this.pollService.filterSearchPollsByType(
    			search1);

    	assertEquals(allPolls1.size(), 1);
    }
}
