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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.encuestame.persistence.dao.IQuestionDao;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.survey.QuestionPattern;
import org.encuestame.persistence.domain.survey.QuestionAnswer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * Question Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since June 02, 2009
 * @version $Id$
 */
@Repository("questionDaoImp")
public class QuestionDaoImp extends AbstractHibernateDaoSupport implements IQuestionDao {

    @Autowired
    public QuestionDaoImp(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Create Question.
     * @param question question
     * @throws HibernateException exception
     */
    //@CacheFlush(modelId="createQuestion")
    public void createQuestion(final Question question) throws HibernateException {
        saveOrUpdate(question);
    }

    /**
     * Retrieve Questions by Name.
     * @param keyword keyword
     * @return list of questions
     */
    //@Cacheable(modelId="retrieveQuestionsByName")
    @SuppressWarnings("unchecked")
    public List<Question> retrieveQuestionsByName(final String keyword, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
        criteria.add(Restrictions.like("question", keyword, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Retrieve Indexes Question By Keyword
     * @param keyword
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Question> retrieveIndexQuestionsByKeyword(final String keyword, final Long userId){
        log.info("keyword "+keyword);
        log.info("userId "+userId);
        List<Question> searchResult = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    @SuppressWarnings("deprecation")
                    public Object doInHibernate(org.hibernate.Session session) {
                        try {
                            final FullTextSession fullTextSession = Search.getFullTextSession(session);
                            final MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30,
                                    new String[]{"question"},
                                    new SimpleAnalyzer());
                            final org.apache.lucene.search.Query query = parser.parse(keyword);
                            final FullTextQuery hibernateQuery = fullTextSession.createFullTextQuery(query, Question.class);
                            final Criteria criteria = session.createCriteria(Question.class);
                            criteria.createAlias("accountQuestion", "accountQuestion");
                            criteria.add(Restrictions.eq("accountQuestion.uid", userId));
                            hibernateQuery.setCriteriaQuery(criteria);
                            final List<Question> result = hibernateQuery.list();
                            log.info("result LUCENE "+result.size());
                            return result;
                        } catch (ParseException ex) {
                            log.error("Index Search Erro "+ex.getMessage());
                            return null;
                        }
                    }
                });
        return searchResult;
    }

    /**
     * Retrieve Question By Id.
     * @param questionId question id
     * @return  {@link Question}
     */
    public Question retrieveQuestionById(final Long questionId){
        return (Question) getHibernateTemplate().get(Question.class, questionId);
    }

    /**
     * Retrieve Answer by Id.
     * @param answerId answer id
     * @return {@link QuestionAnswer}
     */
    public QuestionAnswer retrieveAnswerById(final Long answerId){
       return (QuestionAnswer) getHibernateTemplate().get(QuestionAnswer.class, answerId);
    }

    /**
     * Get Questions Answer By Question Id.
     * @param questionId question id
     * @return list of answers
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<QuestionAnswer> getAnswersByQuestionId(final Long questionId) throws HibernateException {
        return getHibernateTemplate().findByNamedParam("from QuestionAnswer where questions.id =:questionId ",
                                                       "questionId", questionId);
    }

    /**
     * Load All Questions.
     * @return List of {@link Question}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<Question> loadAllQuestions() throws HibernateException {
        return getHibernateTemplate().find("from Question");
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
    @Deprecated
    //We have similar method on the top of the class.
    public List<Question> getQuestionbyKeyword(final String keywordQuestion){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
        criteria.add(Restrictions.like("question", "%"+keywordQuestion+"%"));
        return getHibernateTemplate().findByCriteria(criteria);
    }
}
