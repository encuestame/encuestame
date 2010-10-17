/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import java.util.Calendar;
import java.util.List;

import org.encuestame.core.persistence.domain.survey.Poll;
import org.encuestame.core.persistence.domain.survey.TweetPoll;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Front End Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 16, 2010 10:53:26 PM
 * @version $Id:$
 */
public class FrontEndDao extends AbstractHibernateDaoSupport{

    /**
     * Constructor.
     */
    public FrontEndDao() {
        super();
    }

    /**
     * Get TweetPoll Front End.
     * @param period date period
     * @param firstResult first results
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> getTweetPollFrontEnd(Integer period, final Integer maxResults, final Integer firstResult){
        if(period == null){
            period = 1;
        }
        final Calendar hi = Calendar.getInstance();
        hi.add(Calendar.DAY_OF_YEAR, -period);
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.createAlias("question", "question");
        criteria.add(Restrictions.between("createDate", Calendar.getInstance().getTime(), hi.getTime()));
        criteria.add(Restrictions.le("enabled", Boolean.FALSE)); //should be enabled
        criteria.add(Restrictions.eq("publishTweetPoll", Boolean.TRUE)); //should be published
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    /**
     * Get Poll on Front End
     * @param period period
     * @param maxResults maxResults
     * @param firstResult firstResults.
     * @return list of poll.
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> getPollFrontEnd(Integer period, final Integer maxResults, final Integer firstResult){
        if(period == null){
            period = 1;
        }
        final Calendar hi = Calendar.getInstance();
        hi.add(Calendar.DAY_OF_YEAR, -period);
        final DetachedCriteria criteria = DetachedCriteria.forClass(Poll.class);
        criteria.createAlias("question", "question");
        criteria.add(Restrictions.between("createdAt", Calendar.getInstance().getTime(), hi.getTime()));
        criteria.add(Restrictions.le("enabled", Boolean.FALSE)); //should be enabled
        criteria.add(Restrictions.eq("publish", Boolean.TRUE)); //should be published
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    /**
     * Get TweetPoll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<TweetPoll> getTweetPollFrontEndLast24(final Integer maxResults){
        return this.getTweetPollFrontEnd(1, maxResults, -1);
    }

    /**
     * Get TweetPoll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<TweetPoll> getTweetPollFrontEndLast7Days(final Integer maxResults){
        return this.getTweetPollFrontEnd(7 , maxResults, -1);
    }

    /**
     * Get TweetPoll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<TweetPoll> getTweetPollFrontEndLast30Days(final Integer maxResults){
        return this.getTweetPollFrontEnd(30 , maxResults, -1);
    }

    /**
     * Get Poll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<TweetPoll> getPollFrontEndLast24(final Integer maxResults){
        return this.getPollFrontEnd(1, maxResults, -1);
    }

    /**
     * Get Poll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<TweetPoll> getPollFrontEndLast7Days(final Integer maxResults){
        return this.getPollFrontEnd(7 , maxResults, -1);
    }

    /**
     * Get Poll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    public List<TweetPoll> getPollFrontEndLast30Days(final Integer maxResults){
        return this.getPollFrontEnd(30 , maxResults, -1);
    }

}

