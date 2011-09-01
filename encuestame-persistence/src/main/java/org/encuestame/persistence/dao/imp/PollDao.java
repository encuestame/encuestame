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
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Poll Dao.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since March 15, 2009
 * @version $Id: $
 */
@Repository("pollDao")
public class PollDao extends AbstractHibernateDaoSupport implements IPoll {

    @Autowired
    public PollDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Poll> findAll() throws HibernateException {
         return getHibernateTemplate().find("FROM Poll");
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollFolderBySecUser(org.encuestame.persistence.domain.security.UserAccount)
     */
    @SuppressWarnings("unchecked")
    public List<PollFolder> getPollFolderBySecUser(final UserAccount secUser){
          final DetachedCriteria criteria = DetachedCriteria.forClass(PollFolder.class);
          criteria.add(Restrictions.eq("createdBy", secUser));
          return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollsByPollFolder(org.encuestame.persistence.domain.security.UserAccount, org.encuestame.persistence.domain.survey.PollFolder)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByPollFolder(final UserAccount userAcc, final PollFolder folder){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("pollOwner", userAcc));
        criteria.add(Restrictions.eq("pollFolder", folder));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollsByPollFolderId(org.encuestame.persistence.domain.security.UserAccount, org.encuestame.persistence.domain.survey.PollFolder)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByPollFolderId(final UserAccount userId, final PollFolder folder){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("pollOwner", "pollOwner");
        //criteria.add(Restrictions.eq("pollOwner.uid", userId));
        criteria.add(Restrictions.eq("pollFolder", folder));
        return getHibernateTemplate().findByCriteria(criteria);
    }

     /*
      * (non-Javadoc)
      * @see org.encuestame.persistence.dao.IPoll#findAllPollByUserId(org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
      */
    @SuppressWarnings("unchecked")
    public List<Poll> findAllPollByUserId(final UserAccount userAcc, final Integer maxResults, final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        //criteria.createAlias("pollOwner","pollOwner");
        criteria.add(Restrictions.eq("pollOwner", userAcc));
        criteria.add(Restrictions.eq("publish", Boolean.TRUE));
        criteria.addOrder(Order.desc("createdAt"));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollById(java.lang.Long)
     */
    public Poll getPollById(final Long pollId) throws HibernateException {
        return (Poll) getHibernateTemplate().get(Poll.class, pollId);
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollFolderById(java.lang.Long)
     */
    public PollFolder getPollFolderById(final Long folderId){
        return getHibernateTemplate().get(PollFolder.class, folderId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrieveResultPolls(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> retrieveResultPolls(final Long pollId,
            final Long questionId) {
        final String pollResultsCounter = "select answer.answer,"
                + "count(poll.pollId) FROM PollResult "
                + "where poll.pollId= :pollId and answer.questionAnswerId= :questionId "
                + "group by answer.answer";
        return new ArrayList<Object[]>(getSession().createQuery(
                pollResultsCounter).setParameter("pollId", pollId)
                .setParameter("questionId", questionId).list());

    }

     /*
      * (non-Javadoc)
      * @see org.encuestame.persistence.dao.IPoll#getPollsByQuestionKeyword(java.lang.String, org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
      */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByQuestionKeyword(final String keywordQuestion, final UserAccount userAcc,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("question", "questionAlias");
        criteria.add(Restrictions.like("questionAlias.question", keywordQuestion , MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq("pollOwner", userAcc));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollByIdandUserId(java.lang.Long, org.encuestame.persistence.domain.security.UserAccount)
     */
    @SuppressWarnings("unchecked")
    public Poll getPollByIdandUserId(final Long pollId, UserAccount userAcc){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("pollOwner", userAcc));
        criteria.add(Restrictions.eq("pollId", pollId));
        return (Poll) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollFolderByIdandUser(java.lang.Long, org.encuestame.persistence.domain.security.UserAccount)
     */
    @SuppressWarnings("unchecked")
    public PollFolder getPollFolderByIdandUser(final Long pollFolderId, final UserAccount userAcc){
        final DetachedCriteria criteria = DetachedCriteria.forClass(PollFolder.class);
        criteria.add(Restrictions.eq("createdBy", userAcc));
        criteria.add(Restrictions.eq("id", pollFolderId));
        return (PollFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#getPollByIdandCreationDate(java.util.Date, org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollByIdandCreationDate(final Date date, final UserAccount userAcc,
            final Integer maxResults, final Integer start ){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("pollOwner", userAcc));
        criteria.add(Restrictions.eq("createdAt", date));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IPoll#retrievePollsByUserId(org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Poll> retrievePollsByUserId(final UserAccount userAcc,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
         criteria.add(Restrictions.eq("publish", Boolean.TRUE));
         criteria.add(Restrictions.eq("pollOwner", userAcc));
         criteria.addOrder(Order.desc("createdAt"));
         return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }
}