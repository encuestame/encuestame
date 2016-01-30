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
package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.encuestame.persistence.dao.IQuestionDao;
import org.encuestame.persistence.dao.search.QueryBuilder;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPreferences;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * Question Dao.
 * @author Picado, Juan Carlos juanATencuestame.org
 * @since June 02, 2009
 */
@Repository("questionDaoImp")
public class QuestionDaoImp extends AbstractHibernateDaoSupport implements IQuestionDao {

    /**
     * Constructor.
     * @param sessionFactory {@link SessionFactory}.
     */
    @Autowired
    public QuestionDaoImp(final SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#createQuestion(org.encuestame.persistence.domain.question.Question)
     */
    //@CacheFlush(modelId="createQuestion")
    public final void createQuestion(final Question question){
        saveOrUpdate(question);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#retrieveQuestionbyId(java.lang.Long, java.lang.Long)
     */
    public Question retrieveQuestionbyId(Long questionId, final Long userId) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(Question.class);
        criteria.add(Restrictions.eq("accountQuestion.uid", userId));
        // criteria.add(Restrictions.eq("qid", questionId));
        return (Question) getHibernateTemplate().findByCriteria(criteria);

    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#retrieveQuestionsByName(java.lang.String, java.lang.Long)
     */
    //@Cacheable(modelId="retrieveQuestionsByName")
    @SuppressWarnings("unchecked")
    public final List<Question> retrieveQuestionsByName(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
        criteria.add(Restrictions.like("question", keyword, MatchMode.ANYWHERE));
        return (List<Question>) getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#retrieveIndexQuestionsByKeyword(java.lang.String, java.lang.Long)
     */
    public final List<Question> retrieveIndexQuestionsByKeyword(
            final String keyword,
            final Long userId,
            final Integer maxResults,
            final Integer startOn){
        return this.retrieveIndexQuestionsByKeyword(
                keyword, userId, new String[]{"question"}, maxResults, startOn);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#retrieveIndexQuestionsByKeyword(java.lang.String, java.lang.Long, java.lang.String[], org.apache.lucene.analysis.Analyzer)
     */
    @SuppressWarnings("unchecked")
    public final List<Question> retrieveIndexQuestionsByKeyword(
            final String keyword,
                 final Long userId,
                 final String[] fields,
                 final Integer maxResults,
                 final Integer startOn){
        log.debug("keyword "+keyword);
        log.debug("userId " + userId);
        log.debug("fields " + fields);
        @SuppressWarnings("rawtypes")
        final List<Question> searchResult = (List<Question>) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(org.hibernate.Session session) {
            final Criteria criteria = session.createCriteria(Question.class);
                //filter by account.
                if (userId != null) {
                    criteria.createAlias("accountQuestion", "accountQuestion");
                    criteria.add(Restrictions.eq("accountQuestion.uid", userId));
                }
                //else {
                    //if user id is missing, the question should be shared to be listed.
                    //criteria.add(Restrictions.eq("sharedQuestion", Boolean.TRUE));
                //}
                //limit results
                if (maxResults != null) {
                    criteria.setMaxResults(maxResults.intValue());
                }
                //start on page x
                if (startOn != null) {
                    criteria.setFirstResult(startOn.intValue());
                }
                final QueryBuilder<Question> query = new QueryBuilder<>(getSessionFactory());
                List<Question> results = query.build(criteria, keyword, maxResults, 0,  new String[] { "question"}, "question", Question.class);
                return results;
            }
        });
        return (List<Question>) searchResult;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#retrieveQuestionById(java.lang.Long)
     */
    public final Question retrieveQuestionById(final Long questionId){
        return (Question) getHibernateTemplate().get(Question.class, questionId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#retrieveAnswerById(java.lang.Long)
     */
    public final QuestionAnswer retrieveAnswerById(final Long answerId){
       return (QuestionAnswer) getHibernateTemplate().get(QuestionAnswer.class, answerId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#getAnswersByQuestionId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public final List<QuestionAnswer> getAnswersByQuestionId(final Long questionId) throws HibernateException {
     /*   return getHibernateTemplate().findByNamedParam("from QuestionAnswer where questions.id =:questionId ",
                                                       "questionId", questionId);*/
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(QuestionAnswer.class);
        criteria.createAlias("questions", "questions");
        criteria.add(Restrictions.eq("questions.qid", questionId));
        return (List<QuestionAnswer>) getHibernateTemplate().findByCriteria(criteria);

    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.persistence.dao.IQuestionDao#retrieveAnswersByQuestionId(
	 * org.encuestame.persistence.domain.question.Question, java.lang.Long)
	 */
    public final QuestionAnswer retrieveAnswersByQuestionId(
            final Question question, final Long answerId)
            throws HibernateException {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(QuestionAnswer.class);
        criteria.add(Restrictions.eq("questions", question));
        criteria.add(Restrictions.eq("questionAnswerId", answerId));
        return (QuestionAnswer) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#loadAllQuestions()
     */
    @SuppressWarnings("unchecked")
    public final List<Question> loadAllQuestions() throws HibernateException {
        return (List<Question>) getHibernateTemplate().find("from Question");
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#getQuestionsbySection(org.encuestame.persistence.domain.survey.SurveySection)
     */
    @SuppressWarnings("unchecked")
    public List<Question> getQuestionsbySection(final SurveySection section){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
        criteria.add(Restrictions.eq("section", section));
        return (List<Question>) getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#getQuestionPreferences(org.encuestame.persistence.domain.question.Question)
     */
    @SuppressWarnings("unchecked")
    public List<QuestionPreferences> getQuestionPreferences(final Question question){
        final DetachedCriteria criteria = DetachedCriteria.forClass(QuestionPreferences.class);
        criteria.add(Restrictions.eq("question", question));
        return (List<QuestionPreferences>) getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.persistence.dao.IQuestionDao#getQuestionPreference(org
     * .encuestame.persistence.domain.question.Question, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<QuestionPreferences> getQuestionPreference(final Question question, final String pref){
        final DetachedCriteria criteria = DetachedCriteria.forClass(QuestionPreferences.class);
        criteria.createAlias("question", "question");
        criteria.add(Restrictions.eq("question", question));
        //criteria.add(Restrictions.eq("preference", preference));
        criteria.add(Restrictions.like("preference", pref, MatchMode.ANYWHERE));
        return (List<QuestionPreferences>) getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IQuestionDao#getPreferenceById(java.lang.Long)
     */
     public QuestionPreferences getPreferenceById(final Long preferenceId) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(QuestionPreferences.class);
        criteria.add(Restrictions.eq("preferenceId", preferenceId));
        return (QuestionPreferences) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

}
