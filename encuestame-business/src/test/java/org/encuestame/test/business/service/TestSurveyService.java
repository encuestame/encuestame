/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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
import java.util.Collection;
import java.util.List;

import org.encuestame.business.mail.MailServiceImpl;
import org.encuestame.business.service.imp.ISurveyService;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.test.config.AbstractBaseUnitBeans;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.QuestionBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Test of {@link AbstractSurveyService}
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 15:04:56
 * @version $Id$
 */

public class TestSurveyService  extends AbstractServiceBase{

    /** {@link AbstractSurveyService} */
    @Autowired
    private ISurveyService surveyService;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    /** {@link Question} */
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern pattern;

    /** {@link Account} **/
    private Account user;

    private UserAccount userSecondary;

    private List<QuestionAnswerBean> answers;

    /** {@link QuestionBean} **/
    private QuestionBean questionBean;

    /** {@link UnitPatternBean}**/
    private UnitPatternBean patternBean;

     /**
     *
     */
    @Before
    public void serviceInit(){
        // surveyService.setServiceMail(mailServiceImpl);
         this.user = createUser("testEncuesta", "testEncuesta123");
         this.userSecondary = createUserAccount("user", this.user);
         this.question = createQuestion("Why the sky is blue?","html");
         this.pattern = createQuestionPattern("html");
         createQuestionAnswer("Yes", this.question,"SSSA");
         //this.questionBean = createUnitQuestionBean("", 1L, 1L, listAnswers, pattern)
         answers = new ArrayList<QuestionAnswerBean>();
         answers.add(createAnswersBean("2DFAAS", "Yes", question.getQid()));
         answers.add(createAnswersBean("4DSWGK", "No", question.getQid()));
         patternBean = createPatternBean("radio.class",
                    "radio buttons", "2", "Yes/No", "template.php");
         questionBean = createUnitQuestionBean("questionName", 1L, this.user.getUid(),
                    this.answers, patternBean);
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
     * Load Patter Info Null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testloadPatternInfoNull() throws EnMeExpcetion {
        surveyService.loadPatternInfo(null);
    }

    /**
     * Load Patter Info.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadPatternInfo() throws EnMeExpcetion {
        //  this.serviceInit();
        UnitPatternBean patternBean = new UnitPatternBean();
        patternBean.setId(this.pattern.getPatternId());
        //    	patternBean.setId(createQuestionPattern("html").getPatternId());
        patternBean = surveyService.loadPatternInfo(patternBean);
        // 	assertNotNull(patternBean);
        assertEquals("Should be equals",patternBean.getPatronType(), getPattern().getPatternType());
    }

    /**
     * Load All Patterns.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllPatrons() throws EnMeExpcetion {
    // this.serviceInit();
        final Collection<UnitPatternBean> patternList = surveyService.loadAllPatrons();
    // assertNotNull(patternList);
        assertEquals("Should be equals",2, patternList.size());
    }

    /**
     * Load All Patterns Zero Results.
     * @throws EnMeExpcetion exception
     */
//  @Test
    public void testloadAllPatronsZeroResults() throws EnMeExpcetion {
        final Collection<UnitPatternBean> patternList = surveyService.loadAllPatrons();
        assertNotNull(patternList);
        assertEquals("Should be equals",0, patternList.size());
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
    public void testCreateQuestionException() throws EnMeExpcetion {
        this.surveyService.setRandomQuestionKey(600000000);
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
    public void testSaveAnswers() throws EnMeExpcetion{
        final QuestionAnswerBean answersBean = createAnswersBean("ASJKE", "Yes", this.question.getQid());
        surveyService.saveAnswer(answersBean, this.question);
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
    /**
     * @return the pattern
     */
    public QuestionPattern getPattern() {
        return pattern;
    }

    /**
     * @param mailServiceImpl the mailServiceImpl to set
     */
    public void setMailServiceImpl(final MailServiceImpl mailServiceImpl) {
        this.mailServiceImpl = mailServiceImpl;
    }

}
