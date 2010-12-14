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

package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollFolder;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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

     private Log log = LogFactory.getLog(this.getClass());

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
    public List<TweetPoll> retrieveTweetsByUserId(final Long userId,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
         criteria.createAlias("tweetOwner","tweetOwner");
         criteria.add(Restrictions.eq("publishTweetPoll", Boolean.TRUE));
         criteria.add(Restrictions.eq("tweetOwner.id", userId));
         criteria.addOrder(Order.desc("createDate"));
         return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Get TweetPoll by Question Name.
     * @param keyWord keyword
     * @param userId user Id.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> retrieveTweetsByQuestionName(final String keyWord, final Long userId,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.createAlias("question","question");
        criteria.createAlias("tweetOwner","tweetOwner");
        criteria.add(Restrictions.like("question.question", keyWord, MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq("tweetOwner.id", userId));
        return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Retrieve TweetPoll Today.
     * @param keyWord
     * @param userId
     * @return
     */
    public List<TweetPoll> retrieveTweetPollToday(
            final Long userId,
             final Integer maxResults,
             final Integer start){
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return retrieveTweetPollByDate(userId, cal.getTime(), maxResults, start);
    }


    /**
     *
     * @param keyWord
     * @param userId
     * @param initDate
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<TweetPoll> retrieveTweetPollByDate(
            final Long userId,
            final Date initDate,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
         criteria.createAlias("tweetOwner","tweetOwner");
         criteria.add(Restrictions.between("createDate", initDate, new Date()));
         criteria.add(Restrictions.eq("tweetOwner.id", userId));
         return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Retrieve TweetPoll Last Week.
     * @param keyWord
     * @param userId
     * @return
     */
    public List<TweetPoll> retrieveTweetPollLastWeek(
            final Long userId,
            final Integer maxResults,
            final Integer start){
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, -7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return retrieveTweetPollByDate(userId, cal.getTime(), maxResults, start);
    }

    /**
     * Retrieve Favourites TweetPolls.
     * @param keyWord
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> retrieveFavouritesTweetPoll(
            final Long userId,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.createAlias("tweetOwner","tweetOwner");
        criteria.add(Restrictions.eq("favourites", Boolean.TRUE));
        criteria.add(Restrictions.eq("tweetOwner.id", userId));
        return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
    }

    /**
     * Retrieve Favourites TweetPolls.
     * @param keyWord
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> retrieveScheduledTweetPoll(
            final Long userId,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.createAlias("tweetOwner","tweetOwner");
        criteria.add(Restrictions.eq("scheduleTweetPoll", Boolean.TRUE));
        criteria.add(Restrictions.isNotNull("scheduleDate"));
        criteria.add(Restrictions.eq("tweetOwner.id", userId));
        return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
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

    /**
     * Get List of Switch Answers by TweetPoll.
     * @param tweetPoll {@link TweetPoll}.
     * @return List of {@link TweetPollSwitch}
     */
    @SuppressWarnings("unchecked")
    public List<TweetPollSwitch> getListAnswesByTweetPoll(final TweetPoll tweetPoll){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollSwitch.class);
        criteria.add(Restrictions.eq("tweetPoll", tweetPoll));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Votes By {@link TweetPollSwitch}..
     * @param pollSwitch {@link TweetPollSwitch}..
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Long> getVotesByAnswer(final TweetPollSwitch pollSwitch){
        return getHibernateTemplate().findByNamedParam("select count(tweetPollResultId) "
               +" from TweetPollResult where tweetPollSwitch = :tweetPollSwitch", "tweetPollSwitch", pollSwitch);
    }

    /**
     * Get Total Votes By {@link TweetPoll}.
     * @param tweetPoll {@link TweetPoll}.
     * @return List of Votes.
     */
    public List<Object[]> getTotalVotesByTweetPoll(final Long tweetPollId){
            final List<Object[]> result = new ArrayList<Object[]>();
            final List<TweetPollSwitch> answers = this.getListAnswesByTweetPoll(this.getTweetPollById(tweetPollId));
            for (TweetPollSwitch tweetPollSwitch : answers) {
                final List<Long> answerResult = this.getVotesByAnswer(tweetPollSwitch);
                final Object[] objects = {tweetPollSwitch.getAnswers().getAnswer(), answerResult.get(0)};
                result.add(objects);
            }
            return result;
    }

    /**
     * Get Total Votes by TweetPoll Id.
     */
    public Long getTotalVotesByTweetPollId(final Long tweetPollId){
        Long totalvotes = 0L;
        final List<TweetPollSwitch> answers = this.getListAnswesByTweetPoll(this.getTweetPollById(tweetPollId)); // Type YES-NO
        for (TweetPollSwitch tweetPollSwitch : answers) {
            final List<Long> answerResult = this.getVotesByAnswer(tweetPollSwitch); // Count
            for (Long objects : answerResult) {
                 if (objects != null){
                    totalvotes += objects;
                }
            }
            log.info("Total Votes: "+ totalvotes);
        }
        return totalvotes;
    }
    /**
     * Retrieve Tweet Polls Folders By UserId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TweetPollFolder> retrieveTweetPollFolderByUserId(final Long userId){
        return getHibernateTemplate().findByNamedParam("FROM TweetPollFolder where users.uid=:userId","userId", userId);
    }

    /**
     * Retrieve TweetPoll by Folder
     * @param userId
     * @param folderId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TweetPollFolder> retrieveTweetPollByFolder(final Long userId, final Long folderId){
        return getHibernateTemplate().findByNamedParam("FROM TweetPollFolder where surveyFolderId:folderId","folderId", folderId);
    }

    /**
     * Get TweetPoll Folder By Id.
     * @param folderId
     * @return
     */
    public TweetPollFolder getTweetPollFolderById(final Long folderId){
        return getHibernateTemplate().get(TweetPollFolder.class, folderId);
    }

    /**
     * Get TweetPoll Folder By Id and UserId.
     * @param folderId
     * @param userId
     * @return
     */

    @SuppressWarnings("unchecked")
    public TweetPollFolder getTweetPollFolderByIdandUser(final Long FolderId, final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollFolder.class);
         criteria.add(Restrictions.eq("users.id", userId));
         criteria.add(Restrictions.eq("tweetPollFolderId", FolderId));
         return (TweetPollFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get TweetPoll Folder By Id and UserId.
     * @param folderId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public TweetPoll getTweetPollByIdandUserId(final Long tweetPollId, final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
         criteria.add(Restrictions.eq("tweetOwner.uid", userId));
         criteria.add(Restrictions.eq("tweetPollId", tweetPollId));
         return (TweetPoll) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }
}
