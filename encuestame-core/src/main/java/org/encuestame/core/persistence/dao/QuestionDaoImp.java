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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.IQuestionDao;
import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Question Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since June 02, 2009
 * @version $Id$
 */
@Repository
public class QuestionDaoImp extends AbstractHibernateDaoSupport implements IQuestionDao {

    /**
     * Create Question.
     * @param question question
     * @throws HibernateException exception
     */
    public void createQuestion(final Questions question) throws HibernateException {
        saveOrUpdate(question);
    }

    /**
     * Retrieve Questions by Name.
     * @param keyword keyword
     * @return list of questions
     */
    @SuppressWarnings("unchecked")
    public List<Questions> retrieveQuestionsByName(final String keyword){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Questions.class);
        criteria.add(Restrictions.like("question", keyword, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Retrieve Question By Id.
     * @param questionId question id
     * @return  {@link Questions}
     */
    public Questions retrieveQuestionById(final Long questionId){
        return (Questions) getHibernateTemplate().get(Questions.class, questionId);
    }

    /**
     * Retrieve Answer by Id.
     * @param answerId answer id
     * @return {@link QuestionsAnswers}
     */
    public QuestionsAnswers retrieveAnswerById(final Long answerId){
       return (QuestionsAnswers) getHibernateTemplate().get(QuestionsAnswers.class, answerId);
    }

    /**
     * Get Questions Answer By Question Id.
     * @param questionId question id
     * @return list of answers
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<QuestionsAnswers> getAnswersByQuestionId(final Long questionId) throws HibernateException {
        return getHibernateTemplate().findByNamedParam("from QuestionsAnswers where questions.id =:questionId ",
                                                       "questionId", questionId);
    }

    /**
     * Load All Questions.
     * @return List of {@link Questions}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<Questions> loadAllQuestions() throws HibernateException {
        return getHibernateTemplate().find("from Questions");
    }

    /**
     * Load All Questions Patron.
     * @return  List of {@link QuestionPattern}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<QuestionPattern> loadAllQuestionPattern()
            throws HibernateException {
        return getHibernateTemplate().find("from QuestionPattern");

    }

    /**
     * Load pattern info.
     * @param patronId patron id
     * @return QuestionPatron
     * @throws HibernateException exception
     */
    public QuestionPattern loadPatternInfo(final Long patronId) throws HibernateException{
        return (QuestionPattern) getHibernateTemplate().get(QuestionPattern.class, patronId);
    }


    /**
     * Retrieve Polls by Question Keyword.
     * @param keywordQuestion keywordQuestion
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Questions> getQuestionbyKeyword(final String keywordQuestion){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Questions.class);
       // criteria.createAlias("question", "questionAlias");
        criteria.add(Restrictions.like("question", "%"+keywordQuestion+"%"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

}
