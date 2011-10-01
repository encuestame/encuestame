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

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.QuestionDaoImp;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.DateUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link QuestionDaoImp}.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 14, 2010 12:18:42 AM
 * @version $Id$
 */
public class TestQuestionDao extends AbstractBase{

    /** {@link Account} **/
    private Account user;

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
    @Test
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
}
