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

import java.util.ArrayList;
import java.util.List;

import org.encuestame.persistence.dao.IFolder;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
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
 *
 * @author Morales,Diana Paola paola@encuestame.org
 * @since March 15, 2009
 * @version $Id: $
 */
@Repository("pollDao")
public class PollDao extends AbstractHibernateDaoSupport implements IPoll {

    @Autowired
    public PollDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Find All Polls.
     *
     * @return list of all Poll
     * @throws HibernateException
     *             hibernate
     */
    @SuppressWarnings("unchecked")
    public List<Poll> findAll() throws HibernateException {
         return getHibernateTemplate().find("FROM Poll");
    }

    /**
     * List of Poll Folder by User.
     * @param secUser {@link Account}.
     * @return list of folders.
     */
    @SuppressWarnings("unchecked")
    public List<IFolder> getPollFolderBySecUser(final Account secUser){
          final DetachedCriteria criteria = DetachedCriteria.forClass(PollFolder.class);
          criteria.add(Restrictions.eq("users", secUser));
          return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Polls by Folder.
     * @param secUser
     * @param folder
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByPollFolder(final Account userId, final PollFolder folder){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("pollOwner", userId));
        criteria.add(Restrictions.eq("pollFolder", folder));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Polls by Folder.
     * @param secUser
     * @param folder
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByPollFolderId(final Long userId, final PollFolder folder){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("pollOwner", "pollOwner");
        criteria.add(Restrictions.eq("pollOwner.uid", userId));
        criteria.add(Restrictions.eq("pollFolder", folder));
        return getHibernateTemplate().findByCriteria(criteria);
    }

     /**
      * List All Polls by UserId.
      * @param userId
      * @param maxResults
      * @param start
      * @return
      */
     @SuppressWarnings("unchecked")
    public List<Poll> findAllPollByUserId(final Long userId, final Integer maxResults, final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("pollOwner","pollOwner");
        criteria.add(Restrictions.eq("pollOwner.uid", userId));
        criteria.add(Restrictions.eq("publish", Boolean.TRUE));
        criteria.addOrder(Order.desc("createdAt"));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Retrieve Poll by id.
     *
     * @param pollId
     *            Poll id
     * @return {@link Poll}
     * @throws HibernateException
     *             hibernate expcetion
     */
    public Poll getPollById(final Long pollId) throws HibernateException {
        return (Poll) getHibernateTemplate().get(Poll.class, pollId);
    }


    /**
     * GetPoll Folder ById.
     * @param folderId
     * @return
     */
    public PollFolder getPollFolderById(final Long folderId){
        return getHibernateTemplate().get(PollFolder.class, folderId);
    }

    /**
     * Retrieve Results Poll by PollId.
     *
     * @param pollId
     *            Poll id
     * @return {@link PollResult}
     * @throws HibernateException
     *             hibernate expcetion
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

     /**
      * Retrieve Polls by Question Keyword.
      * @param keywordQuestion
      * @param UserId
      * @param maxResults
      * @param start
      * @return
      */
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsByQuestionKeyword(final String keywordQuestion, final Long UserId,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("question", "questionAlias");
        criteria.add(Restrictions.like("questionAlias.question", keywordQuestion , MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq("pollOwner.uid", UserId));
        return (List<Poll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Get Poll by PollId and UserId.
     * @param pollId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Poll getPollByIdandUserId(final Long pollId, Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.add(Restrictions.eq("pollOwner.uid", userId));
        criteria.add(Restrictions.eq("pollId", pollId));
        return (Poll) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get Poll Folder by PollId and UserId
     * @param pollFolderId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public PollFolder getPollFolderByIdandUser(final Long pollFolderId, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(PollFolder.class);
        criteria.add(Restrictions.eq("users.id", userId));
        criteria.add(Restrictions.eq("id", pollFolderId));
        return (PollFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }
}
