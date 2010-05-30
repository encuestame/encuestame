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

import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollResult;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * TweetPoll Dao Implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 17, 2010 8:26:57 PM
 * @version $Id$
 */
@Repository
public class TweetPollDao extends AbstractHibernateDaoSupport implements ITweetPoll{

    /**
     * Get TweetPoll by Id.
     * @param tweetPollId tweetPollId
     * @return {@link TweetPoll}
     * @throws HibernateException exception
     */
    public TweetPoll getTweetPollById(final Long tweetPollId) throws HibernateException {
        return (TweetPoll) getHibernateTemplate().get(TweetPoll.class, tweetPollId);
    }

    /**
     * Retrieve Tweets Poll by User Id.
     * @param userId userId
     * @return list of tweet pools.
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> retrieveTweetsByUserId(final Long userId){
        return getHibernateTemplate().findByNamedParam("from TweetPoll where tweetOwner.id = :userId"
               +" order by publicationDateTweet desc", "userId", userId);
    }

    /**
     * Retrieve Tweets Poll Switch.
     * @param tweetCode tweetCode
     * @return switch
     */
    public TweetPollSwitch retrieveTweetsPollSwitch(final String tweetCode){
        return searchByParamStringTweetPollSwitch("codeTweet", tweetCode);
    }

    /**
     * Search By Param String {@link TweetPollSwitch}.
     * @param param param
     * @param value value
     * @return
     */
    @SuppressWarnings("unchecked")
    private TweetPollSwitch searchByParamStringTweetPollSwitch(final String param, final  String value){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollSwitch.class);
        criteria.add(Restrictions.eq(param, value) );
        return (TweetPollSwitch) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Validate Vote IP.
     * @param ip ip
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollSwitch}
     */
    @SuppressWarnings("unchecked")
    public TweetPollResult validateVoteIP(final String ip, final TweetPoll tweetPoll){
        return (TweetPollResult) DataAccessUtils.uniqueResult(getHibernateTemplate()
               .findByNamedParam("from TweetPollResult where ipVote = :ipVote and  tweetPollSwitch.tweetPoll = :tweetPoll",
                new String[]{"ipVote", "tweetPoll"}, new Object[]{ip, tweetPoll}));
    }

    /**
     * Get Results By {@link TweetPoll} && {@link QuestionsAnswers}.
     * @param tweetPoll {@link TweetPoll}
     * @param answers {@link QuestionsAnswers}
     * @return List of {@link TweetPollResult}
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getResultsByTweetPoll(final TweetPoll tweetPoll, QuestionsAnswers answers){
        return getHibernateTemplate().findByNamedParam("select tweetPollSwitch.answers.answer, count(tweetPollResultId) from TweetPollResult "
              +"where tweetPollSwitch.tweetPoll = :tweetPoll and tweetPollSwitch.answers = :answer group by tweetPollSwitch.answers.answer",
              new String[]{"tweetPoll", "answer"}, new Object[]{tweetPoll, answers});
    }
}
