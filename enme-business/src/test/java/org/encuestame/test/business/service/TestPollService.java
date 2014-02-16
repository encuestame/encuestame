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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.encuestame.business.service.PollService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.categories.test.InternetTest;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.web.CreatePollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.search.PollSearchBean;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void serviceInit(){
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
     * Test createPoll.
     * @throws Exception exception
     */
    @Test
    public void testcreatePoll() throws Exception{
        final QuestionBean question = ConvertDomainBean.convertQuestionsToBean(this.question);
        final PollBean unitPoll = ConvertDomainBean.convertPollDomainToBean(this.poll);
        unitPoll.setQuestionBean(question);
        //"ssss", this.answers, "ALL", "APPROVE" ,Boolean.TRUE, this.tagBeanList
        final String[] answer = {"a", "b"};
        final String[] hashtag = {"hastag1", "hastag2"};
        final CreatePollBean cb = createPollBean("ssssssssssss",answer, hashtag, "MODERATE", "ALL", true, null, null);
        final Poll myPoll = this.pollService.createPoll(cb);
        Assert.assertNotNull(myPoll);
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
    @Test(expected = HibernateException.class)
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
        final CreatePollBean cb = createPollBean("ssssssssssss",answer, hashtag, "APPROVE", "ALL", true, null, null);
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
						pollBeanResult.getResult().toString(), "7");
			}
			if (pollBeanResult.getAnswerBean().getAnswerId()
					.equals(qansw2.getQuestionAnswerId())) {
				assertEquals("For answer2 should be equals",
						pollBeanResult.getResult().toString(), "3");
			}
		}

	}	
	
	/**
	 * Test for PollService.filterSearchPollsByType()
	 * @throws EnMeExpcetion
	 */
	@Test
    public void testfilterSearchPollsByType() throws EnMeExpcetion{
		final PollSearchBean bean = new PollSearchBean();
		//all
		bean.setTypeSearch(TypeSearch.ALL);
        List<SearchBean> pollAll = this.pollService.filterSearchPollsByType(bean, this.request);
        assertEquals(pollAll.size(), 1);
        //by onwer
        bean.setTypeSearch(TypeSearch.BYOWNER);
        List<SearchBean> pollAll2 = this.pollService.filterSearchPollsByType(bean, this.request);
       assertEquals(pollAll2.size(), 1);
       //last day
       bean.setTypeSearch(TypeSearch.LASTDAY);
       List<SearchBean> pollAll3 = this.pollService.filterSearchPollsByType(bean, this.request);
       assertEquals(pollAll3.size(), 0);
       //last week
       bean.setTypeSearch(TypeSearch.LASTWEEK);
       List<SearchBean> pollAll4 = this.pollService.filterSearchPollsByType(bean, this.request);
       assertEquals(pollAll4.size(), 1);       
       //last 30 days
       bean.setTypeSearch(TypeSearch.FAVOURITES);
       List<SearchBean> pollAll5 = this.pollService.filterSearchPollsByType(bean, this.request);
       assertEquals(pollAll5.size(), 1);            
    }	

}
