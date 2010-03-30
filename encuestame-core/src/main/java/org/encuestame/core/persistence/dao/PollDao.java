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

import java.util.ArrayList;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.IPoll;
import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.PollResult;
import org.hibernate.HibernateException;

/**
 * Poll Dao.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  March 15, 2009
 * @version $Id: $
 */

public class PollDao extends  AbstractHibernateDaoSupport implements IPoll{

     /**
     * Find All Polls.
     * @return list of all Poll
     * @throws HibernateException hibernate
     */
    public List<Poll> findAll() throws HibernateException {
        return findAll("FROM poll");}


    /**
     * Find All Polls by User Id.
     * @return list of all Poll
     * @throws HibernateException hibernate
     */
    @SuppressWarnings("unchecked")
    public List<Poll> findAllPollByUserId(final Long userId) throws HibernateException {
       return getHibernateTemplate().findByNamedParam("from Poll where pollOwner.uid= :userId", "userId", userId);}

    /**
     * Retrieve Poll by id.
     * @param pollId Poll id
     * @return {@link Poll}
     * @throws HibernateException hibernate expcetion
     */
    public Poll getPollById(final Long pollId) throws HibernateException{
        return (Poll) getHibernateTemplate().get(Poll.class, pollId);
    }

    /**
     * Retrieve Results Poll by PollId.
     * @param pollId Poll id
     * @return {@link PollResult}
     * @throws HibernateException hibernate expcetion
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> retrieveResultPolls(final Long polliId, final Long questionId){
        final String pollResultsCounter = "select answer.answer," +
                "count(poll.pollId) FROM PollResult "
            +"where poll.pollId= :polliId and answer.questionAnswerId= :questionId "
            + "group by answer.answer";
                return new ArrayList<Object[]>(getSession().createQuery(pollResultsCounter)
                        .setParameter("polliId", polliId)
                        .setParameter("questionId", questionId).list());

    }


}

