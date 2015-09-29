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
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.QuestionDaoImp;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPreferences;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test {@link QuestionDaoImp}.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 14, 2010 12:18:42 AM
 */
@Category(DefaultTest.class)
public class TestQuestionDao extends AbstractBase{

    /** {@link Account} **/
    private Account user;

    private Question initQuestion;

    /**
     * Before.
     * @throws ParseException
     */
    @Before
    public void beforeQuestion() throws ParseException{
        this.user = createUser("testEncuesta", "testEncuesta123");
        createFakesQuestions(this.user);
        //masive insert.
        for (int i = 0; i < 200; i++) {
            createQuestion("Word Cup 2010, Spain is a good champion?",  this.user);
        }
        final Date createDate = DateUtil.parseDate("2011-01-01", DateUtil.DEFAULT_FORMAT_DATE);
        createQuestion("Question with date and hits", this.user, createDate, 200L);
        this.initQuestion = createDefaultQuestion("What is the country with the highest number of medals at the 2012 Olympic game");
    }


    /**
     * Test create question.
     */
    @Test
    public void testCreateQuestion(){
        final Question question = createQuestion("Why encuestame is better than polldady?", this.user);
        final Question retrieveQuestion = getQuestionDaoImp().retrieveQuestionById(question.getQid());
        assertEquals("Questions should be equals",question.getQid() , retrieveQuestion.getQid());
    }

    /**
     * Test Retrieve Question by Id and User.
     */
    @Ignore
    @Test
    public void testRetrieveQuestionbyId(){
    	final Question retrieveQ = getQuestionDaoImp().retrieveQuestionbyId(this.initQuestion.getQid(), this.user.getUid());
    }

    /**
     * Test retrieveQuestionsByName.
     */
    @Test
    public void testRetrieveQuestionsByName(){
        final List<Question> listOfQuestions = getQuestionDaoImp().retrieveQuestionsByName("iPods",
                              this.user.getUid());
        assertEquals("Results should be equals", 2,  listOfQuestions.size());
    }

    /**
     * Test Retrieve Indexed QuestionsByName.
     */
    //@Test
    public void testRetrieveIndexedQuestionsByName(){
        //flush indexes
        flushIndexes();

        //keyword: iPods
        final List<Question> listOfQuestions = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("iPods",
                             this.user.getUid(), 100, 0);
        log.debug("Lucene Index "+listOfQuestions.size());
        assertEquals("Results should be equals", 2,  listOfQuestions.size());

        //keyword: i
        final List<Question> startlistOfQuestions = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("i",
                this.user.getUid(), 100, 0);
        log.debug("Lucene Index "+startlistOfQuestions.size());
        assertEquals("Results should be equals", 3,  startlistOfQuestions.size());

        //keyword: i
        final List<Question> startlistOfQuestionsLimitResults = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("i",
                this.user.getUid(), 2, 0);
        log.debug("Lucene Index "+startlistOfQuestionsLimitResults.size());
        assertEquals("Results should be equals", 2,  startlistOfQuestionsLimitResults.size());

        //keyword: a
        final List<Question> startlistOfQuestions2 = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("a",
                this.user.getUid(), 100, 0);
        log.debug("Lucene Index "+startlistOfQuestions2.size());
        assertEquals("Results should be equals", 2,  startlistOfQuestions2.size());

        //keyword: 2010
        final List<Question> startlistOfQuestions3 = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("2010",
                this.user.getUid(), 100, 0);
        log.debug("Lucene Index "+startlistOfQuestions3.size());
        assertEquals("Results should be equals", 100,  startlistOfQuestions3.size());
    }

    /**
     * Test Get questions by survey section.
     */
    @Test
    public void testGetQuestionsbySection(){
        // Sections
        final SurveySection section1 = createSurveySection("First Section");
        final SurveySection section2 = createSurveySection("Second Section");
        Assert.assertNotNull(section1);
        Assert.assertNotNull(section2);

        // Questions in first section.
        addQuestionSection("Question 11", section1, this.user);
        addQuestionSection("Question 12", section1, this.user);
        addQuestionSection("Question 13", section1, this.user);
        addQuestionSection("Question 14", section1, this.user);

        // Questions in second section.
        addQuestionSection("Question 21", section2, this.user);
        addQuestionSection("Question 22", section2, this.user);
        addQuestionSection("Question 23", section2, this.user);

        final List<Question> questionsBySection = getQuestionDaoImp()
                .getQuestionsbySection(section1);
        assertEquals("Results for first section should be equals", 4,
                questionsBySection.size());
        final List<Question> questionsBySection2 = getQuestionDaoImp()
                .getQuestionsbySection(section2);
        assertEquals("Results for second section should be equals", 3,
                questionsBySection2.size());
    }

    /**
     *
     */
    @Test
    public void testGetQuestionPreferencesbyQid(){
    	createQuestionPreference("note", "1", this.initQuestion);
    	createQuestionPreference("field type", "multilines", this.initQuestion);
    	createQuestionPreference("size", "20", this.initQuestion);
    	final List<QuestionPreferences> preferences = getQuestionDaoImp().getQuestionPreferences(this.initQuestion);
    	assertEquals("Total preferences found", 3,
    			preferences.size());
    }

    /**
     *
     */
	@Test
	public void testGetQuestionPreferences() {
		createQuestionPreference("note", "1", this.initQuestion);
		createQuestionPreference("field type", "multilines", this.initQuestion);
		createQuestionPreference("field size", "multilines", this.initQuestion);

		final List<QuestionPreferences> preferences = getQuestionDaoImp()
				.getQuestionPreference(this.initQuestion, "field");
		assertEquals("Total preferences found by name", 2, preferences.size());
	}

    /**
     *
     */
	@Test
	public void testGetQuestionPreferencesbyId() {
		final QuestionPreferences qpref1 = createQuestionPreference("note",
				"1", this.initQuestion);
		createQuestionPreference("field type", "multilines", this.initQuestion);
		createQuestionPreference("field size", "multilines", this.initQuestion);

		final QuestionPreferences qPreference = getQuestionDaoImp()
				.getPreferenceById(qpref1.getPreferenceId());
		assertEquals(qpref1.getPreference(), qPreference.getPreference());
	}

	/*
	 * Test Retrieve answers by question.
	 */
	@Test
	public void testRetrieveAnswersbyQuestion() {
		final Question question1 = createDefaultQuestion(" Question 1");
		final Question question2 = createDefaultQuestion(" Question 2");
		final Question question3 = createDefaultQuestion(" Question 3");
		final QuestionAnswer qA1 = createDefaultQuestionAnswer("Yes", question1);
		final QuestionAnswer qA2 = createDefaultQuestionAnswer("No", question1);
		final QuestionAnswer qA3 = createDefaultQuestionAnswer("Maybe",
				question3);
		final QuestionAnswer qAnswer = getQuestionDaoImp()
				.retrieveAnswersByQuestionId(question1,
						qA2.getQuestionAnswerId());
		assertEquals(qA2.getAnswer(), qAnswer.getAnswer());
		final QuestionAnswer qAnswer1 = getQuestionDaoImp()
				.retrieveAnswersByQuestionId(question1,
						qA3.getQuestionAnswerId());
		assertNull(qAnswer1);

	}
}
