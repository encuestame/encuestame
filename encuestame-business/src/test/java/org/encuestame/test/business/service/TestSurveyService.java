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
import java.util.List;

import junit.framework.Assert;

import org.encuestame.business.service.AbstractSurveyService;
import org.encuestame.core.service.imp.ISurveyService;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.SurveyBean;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test of {@link AbstractSurveyService}
 * @author Picado, Juan juanATencuestame.org
 * @since 05/12/2009 15:04:56
 * @version $Id$
 */

public class TestSurveyService  extends  AbstractSpringSecurityContext{

    /** {@link AbstractSurveyService} */
    @Autowired
    private ISurveyService surveyService;

    /** {@link Question} */
    private Question question;

    /** {@link Account} **/
    private Account user;

    private UserAccount userSecondary;

    private List<QuestionAnswerBean> answers;

    /** {@link QuestionBean} **/
    private QuestionBean questionBean;

    /** Creation date of the survey. **/
    private Calendar mySurveyDate = Calendar.getInstance();

    /** Max value to show items**/
    private Integer MAX_RESULTS = 10;

    /** Start value **/
    private Integer START_RESULTS = 0;

    /** **/
    private String myUsername;

     /**
     *
     */
    @Before
    public void serviceInit(){
        // surveyService.setServiceMail(mailServiceImpl);
         this.user = createUser("testEncuesta", "testEncuesta123");
         this.userSecondary = createUserAccount("user", this.user);
         this.question = createQuestion("Why the sky is blue?","html");
         createQuestionAnswer("Yes", this.question,"SSSA");
         //this.questionBean = createUnitQuestionBean("", 1L, 1L, listAnswers, pattern)
         answers = new ArrayList<QuestionAnswerBean>();
         answers.add(createAnswersBean("2DFAAS", "Yes", question.getQid()));
         answers.add(createAnswersBean("4DSWGK", "No", question.getQid()));

         questionBean = createUnitQuestionBean("questionName", 1L, this.user.getUid(),
                    this.answers);
         this.myUsername = getSpringSecurityLoggedUserAccount().getUsername();
    }

    /**
     * Test Load All Questions.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllQuestions() throws EnMeExpcetion{
        final List<QuestionBean> alist = surveyService.loadAllQuestions();
        assertEquals("Should be equals", 1, alist.size());
    }

    /**
     * Test Create Question.
     * @throws EnMeExpcetion
     **/
    @Test
    public void testCreateQuestion() throws EnMeExpcetion {
        this.surveyService.createQuestion(this.questionBean);
        assertNotNull(questionBean);
    }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test(expected = OutOfMemoryError.class)
    @Ignore
    public void testCreateQuestionException() throws EnMeExpcetion {
        this.surveyService.setRandomQuestionKey(1);
        this.surveyService.createQuestion(this.questionBean);
        assertNotNull(questionBean);
    }

    @Test(expected = Exception.class)
    public void testCreateQuestionException2() throws EnMeExpcetion {
        this.surveyService.setRandomQuestionKey(Integer.valueOf("tres"));
        this.surveyService.createQuestion(this.questionBean);
        assertNotNull(questionBean);
    }

    /**
    * Test Save Answers.
     * @throws EnMeExpcetion
    **/
    @Test
    @Ignore
    public void testSaveAnswers() throws EnMeExpcetion{
        final QuestionAnswerBean answersBean = createAnswersBean("ASJKE", "Yes", this.question.getQid());
        surveyService.createQuestionAnswer(answersBean, this.question);
        assertNotNull(answersBean.getAnswerId());
    }

    /**
     * Test Retrieve Answer By Question Id.
     **/
    @Test
    public void testRetrieveAnswerByQuestionId(){
           final List<QuestionAnswerBean> listUnitAnswerBean = surveyService.retrieveAnswerByQuestionId(this.question.getQid());
            assertEquals("Should be equals",1, listUnitAnswerBean.size());
    }

    /**
     * Test Update Answer By Answer Id.
     * @throws EnMeExpcetion
     */
    @Test
    public void testUpdateAnswersByAnswerId() throws EnMeExpcetion{
        final String expectedResponse = "Quizas";
        final QuestionAnswer questionAnswers = createQuestionAnswer("No", this.question, "HASH");
        surveyService.updateAnswerByAnswerId(questionAnswers.getQuestionAnswerId(), expectedResponse);
        assertEquals(questionAnswers.getAnswer(), expectedResponse);
    }

    /**
     * Test Suggestion Question List.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testSuggestionQuestionList() throws EnMeNoResultsFoundException{
         List<QuestionBean> unitQuestionBean = new ArrayList<QuestionBean>();
        final String keyword = "sky";
        flushIndexes();
        unitQuestionBean = surveyService.listSuggestQuestion(keyword, this.userSecondary.getUsername());
        assertEquals("should be equals", 1, unitQuestionBean.size());
    }

    /**
     * Test search surveys by today date.
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchSurveysToday() throws EnMeExpcetion {
        final SurveyBean surveyBean = createSurveyBean("My first survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                this.mySurveyDate.getTime());
        surveyService.createSurvey(surveyBean);
        final String mykeyword = "";
        final List<SurveyBean> surveyBeanList = surveyService.filterSurveyItemsByType(
                TypeSearch.LASTDAY, mykeyword, this.MAX_RESULTS,
                this.START_RESULTS);
        assertEquals("should be equals", 1, surveyBeanList.size());
    }

    /**
     * Test search surveys from last week.
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchSurveysLastWeek() throws EnMeExpcetion {
        mySurveyDate.add(Calendar.DATE, -3);
        final SurveyBean surveyBean = createSurveyBean("My first survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                this.mySurveyDate.getTime());
        surveyService.createSurvey(surveyBean);
        final List<SurveyBean> surveyBeanList = surveyService
                .searchSurveysLastWeek(getUsernameLogged(), this.MAX_RESULTS,
                        this.START_RESULTS);
       assertEquals("should be equals", 1, surveyBeanList.size());
    }

    /**
     * Test search all surveys by owner.
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchAllSurveys() throws EnMeExpcetion {
        final Calendar lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);

        final Calendar lastWeek = Calendar.getInstance();
        lastWeek.add(Calendar.DATE, -8);

        // First Survey Bean created today .
        final SurveyBean surveyBean = createSurveyBean("My first survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                this.mySurveyDate.getTime());

        // Second Survey Bean created last month.
        final SurveyBean surveyBean2 = createSurveyBean("My Second survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                lastMonth.getTime());

        // Third Survey Bean created 3 days ago.
        final SurveyBean surveyBean3 = createSurveyBean("My Third survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                lastWeek.getTime());

        surveyService.createSurvey(surveyBean);
        surveyService.createSurvey(surveyBean2);
        surveyService.createSurvey(surveyBean3);

        final String mykeyword = "";
        final List<SurveyBean> surveyBeanList = surveyService
                .filterSurveyItemsByType(TypeSearch.ALL, mykeyword,
                        this.MAX_RESULTS, this.START_RESULTS);
        assertEquals("should be equals", 3, surveyBeanList.size());
    }

    /**
     * Test search favorite surveys.
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchFavoriteSurveys() throws EnMeExpcetion {
        final SurveyBean surveyBean = createSurveyBean("My first survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                this.mySurveyDate.getTime());
        surveyBean.setFavorites(Boolean.TRUE);
        surveyService.createSurvey(surveyBean);
        final List<SurveyBean> surveyBeanList = surveyService
                .searchSurveysFavourites(getUsernameLogged(), this.MAX_RESULTS,
                        this.START_RESULTS);
      assertEquals("should be equals", 1, surveyBeanList.size());
    }

    /**
     * Test search surveys by Name
     * @throws EnMeExpcetion
     */
    @Test
    public void testSearchbySurveyName() throws EnMeExpcetion {
        final String keyWord = "first";
        final SurveyBean surveyBean = createSurveyBean("My first survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                this.mySurveyDate.getTime());

        final SurveyBean surveyBean2 = createSurveyBean("My Second survey",
                getSpringSecurityLoggedUserAccount().getUsername(),
                this.mySurveyDate.getTime());
        surveyService.createSurvey(surveyBean);
        surveyService.createSurvey(surveyBean2);
        final List<SurveyBean> surveyBeanList = surveyService
                .searchSurveysbyKeywordName(keyWord,
                        getSpringSecurityLoggedUserAccount().getUsername(),
                        this.MAX_RESULTS, this.START_RESULTS);
        assertEquals("should be equals", 1, surveyBeanList.size());
    }

    /**
     * Test create survey folder.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testCreateSurveyFolder() throws EnMeNoResultsFoundException {
        final FolderBean fbean = surveyService.createSurveyFolder(
                "My First Folder", getSpringSecurityLoggedUserAccount()
                        .getUsername());
        Assert.assertNotNull(fbean);
    }

    /**
     * Test get surveys by folder.
     * @throws EnMeExpcetion
     */
    @Test
    public void testGetSurveysByFolder() throws EnMeExpcetion{
        final FolderBean fbean = surveyService.createSurveyFolder(
                "My First Folder", this.myUsername);
        Assert.assertNotNull(fbean);
        final SurveyBean mySurveyBean = createSurveyBean("My Second survey",
                this.myUsername, this.mySurveyDate.getTime());
        final Survey mySurvey = surveyService.createSurvey(mySurveyBean);
        surveyService.addSurveyToFolder(fbean.getId(), this.myUsername,
                mySurvey.getSid());
        final List<Survey> listSurvey = surveyService.retrieveSurveyByFolder(
                getSpringSecurityLoggedUserAccount().getAccount().getUid(),
                fbean.getId());
        assertEquals("should be equals", 1, listSurvey.size());
    }

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(final ISurveyService surveyService) {
        this.surveyService = surveyService;
    }
    /**
     * @return the question
     */
    public Question getQuestion() {
        return question;
    }
}
