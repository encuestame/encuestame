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
package org.encuestame.test.persistence.domain;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionAnswer.AnswerType;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.ShortUrlProvider;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test Question Answer.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since  March 16, 2009
 */
@Category(DefaultTest.class)
public class TestQuestionAnswer extends AbstractBase {

    /**
     * Test QuestionAnswer.
     **/
    @Test
    public void testQuestionAnswer(){
        final QuestionAnswer qanswer = new QuestionAnswer();
        qanswer.setAnswer("Yes");
        qanswer.setProvider(ShortUrlProvider.GOOGL);
        qanswer.setQuestions(createQuestion("Are you single?", "yesNo",createAccount()));
        qanswer.setUniqueAnserHash("AKDL12_"+RandomStringUtils.randomAlphabetic(4));
        qanswer.setAnswerType(AnswerType.DEFAULT);
        qanswer.setUrlAnswer("url");
        //getQuestionDaoImp().saveOrUpdate(qanswer);
        //assertNotNull(qanswer);
    }


}
