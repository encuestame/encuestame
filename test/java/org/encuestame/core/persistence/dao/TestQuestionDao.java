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

package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link QuestionDaoImp}.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 14, 2010 12:18:42 AM
 * @version $Id$
 */
public class TestQuestionDao extends AbstractBaseTest{

    /**
     * Before.
     */
    @Before
    public void before(){
        createQuestion("Do you like soccer?",  "yes/no");
        createQuestion("Do you like apple's?",  "yes/no");
        createQuestion("Do you like iPods?",  "yes/no");
    }


    /**
     * Test create question.
     */
    @Test
    public void testCreateQuestion(){
        final Questions question = createQuestion("Why encuestame is better than polldady?", "option");
        final Questions retrieveQuestion = getQuestionDaoImp().retrieveQuestionById(question.getQid());
        assertEquals("Questions should be equals",question.getQid() , retrieveQuestion.getQid());
    }

    /**
     * Test retrieveQuestionsByName.
     */
    @Test
    public void testRetrieveQuestionsByName(){
        final List<Questions> listOfQuestions = getQuestionDaoImp().retrieveQuestionsByName("iPods");
        assertEquals("Results should be equals", 1,  listOfQuestions.size());
    }
}
