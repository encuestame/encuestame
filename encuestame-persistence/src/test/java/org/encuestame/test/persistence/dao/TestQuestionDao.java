/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import java.util.List;

import org.encuestame.persistence.dao.imp.QuestionDaoImp;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link QuestionDaoImp}.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 14, 2010 12:18:42 AM
 * @version $Id$
 */
public class TestQuestionDao extends AbstractBase{

    /** {@link Account} **/
    private Account user;

    /**
     * Before.
     */
    @Before
    public void beforeQuestion(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        createQuestion("Do you want soccer?",  this.user);
        createQuestion("Do you like apple's?",  this.user);
        createQuestion("Do you buy iPods?",  this.user);
        createQuestion("Do you like sky iPods Touch?",  this.user);
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
        final List<Question> listOfQuestions = getQuestionDaoImp().retrieveQuestionsByName("iPods",  this.user.getUid());
        assertEquals("Results should be equals", 2,  listOfQuestions.size());
    }

    /**
     * Test Retrieve Indexed QuestionsByName.
     */
    @Test
    public void testRetrieveIndexedQuestionsByName(){
        flushIndexes();
        final List<Question> listOfQuestions = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("iPods", this.user.getUid());
        //TODO: need check this search
        assertEquals("Results should be equals", 2,  listOfQuestions.size());

    }
}
