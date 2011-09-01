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
package org.encuestame.persistence.dao;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.hibernate.HibernateException;

 /**
  * Interface to implement Question Dao.
  * @author Picado, Juan juanATencuestame.org
  * @since  02/06/2009 19:44:21
  * @version $Id$
  */
public interface IQuestionDao extends IBaseDao {

    /**
     * Create Question.
     * @param question question
     * @throws HibernateException exception
     */
    void createQuestion(final Question question);

    /**
     * Load All Questions.
     * @return List of {@link Question}
     * @throws HibernateException exception
     */
    List<Question> loadAllQuestions();

    /**
     * Load All Questions Patron.
     * @return  List of {@link QuestionPattern}
     * @throws HibernateException exception
     */
    List<QuestionPattern> loadAllQuestionPattern()
    throws HibernateException;

    /**
     * Load pattern info.
     * @param patronId patron id
     * @return QuestionPatron
     * @throws HibernateException exception
     */
    QuestionPattern loadPatternInfo(final Long patronId);

    /**
     * Retrieve Questions by Name.
     * @param keyword keyword
     * @return list of questions
     */
    List<Question> retrieveQuestionsByName(final String keyword, final Long userId);

    /**
     * Retrieve Question By Id.
     * @param questionId question id
     * @return  {@link Question}
     */
    Question retrieveQuestionById(final Long questionId);

    /**
     * Get Questions Answer By Question Id.
     * @param questionId question id
     * @return list of answers
     * @throws HibernateException exception
     */
    List<QuestionAnswer> getAnswersByQuestionId(final Long questionId);

    /**
     * Retrieve Answer by Id.
     * @param answerId answer id
     * @return {@link QuestionAnswer}
     */
    QuestionAnswer retrieveAnswerById(final Long answerId);

    /**
     *  Retrieve Indexes Question By Keyword
     * @param keyword keyword to search
     * @param userId user id
     * @param maxResults  limit of results for each search
     * @param startOn start on page
     * @return list of question
     */
    List<Question> retrieveIndexQuestionsByKeyword(
            final String keyword,
            final Long userId,
            final Integer maxResults,
            final Integer startOn);

    /**
     * Retrieve Indexes Question By Keyword.
     *
     * @param keyword keyword to search
     * @param userId user id
     * @param fields list of fields.
     * @param analyzer {@link Analyzer}.
     * @param maxResults  limit of result for each search
     * @param startOn start on page
     * @return list of question
     */
    List<Question> retrieveIndexQuestionsByKeyword(final String keyword,
            final Long userId, final String[] fields, final Analyzer analyzer,
            final Integer maxResults, final Integer startOn);
}
