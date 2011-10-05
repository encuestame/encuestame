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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.tweetpoll.Status;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * TweetPoll Dao Implementation.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 17, 2010 8:26:57 PM
 */
@Repository("tweetPollDao")
public class TweetPollDao extends AbstractHibernateDaoSupport implements ITweetPoll{

     private Log log = LogFactory.getLog(this.getClass());

     @Autowired
    public TweetPollDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

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
    public List<TweetPoll> retrieveTweetPollByDate(
            final Long userId,
            final Date initDate,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
         criteria.createAlias("tweetOwner","tweetOwner");
         criteria.add(Restrictions.between("createDate", initDate, getNextDayMidnightDate()));
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
        criteria.add(Restrictions.eq("tweetOwner.uid", userId));
        return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
    }


    /**
     * Retrieve {@link TweetPollSwitch} by code.
     * @param tweetCode tweetCode code.
     * @return switch {@link TweetPollSwitch}
     */
    public TweetPollSwitch retrieveTweetsPollSwitch(final String tweetCode){
        log.debug("retrieveTweetsPollSwitch codeTweet:"+tweetCode);
        return this.searchByParamStringTweetPollSwitch("codeTweet", tweetCode);
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
        criteria.add(Restrictions.eq(param, value));
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
     * Get Results By {@link TweetPoll} && {@link QuestionAnswer}.
     * @param tweetPoll {@link TweetPoll}
     * @param answers {@link QuestionAnswer}
     * @return List of {@link TweetPollResult}
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getResultsByTweetPoll(final TweetPoll tweetPoll, final QuestionAnswer answers){
        return getHibernateTemplate().findByNamedParam("select tweetPollSwitch.answers.answer, count(tweetPollResultId) from TweetPollResult "
              +"where tweetPollSwitch.tweetPoll = :tweetPoll and tweetPollSwitch.answers = :answer group by tweetPollSwitch.answers.answer",
              new String[]{"tweetPoll", "answer"}, new Object[]{tweetPoll, answers});
    }

    /**
     *
     * @param tweetPollId
     * @param answerId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getResultsByTweetPoll(final Long tweetPollId, final Long answerId) {
        return getHibernateTemplate().findByNamedParam(
              " select tweetPollSwitch.answers.answer, tweetPollSwitch.answers.color, count(tweetPollResultId)"
              +" from TweetPollResult "
              +" where tweetPollSwitch.tweetPoll.tweetPollId = :tweetPoll "
              +" and tweetPollSwitch.answers.questionAnswerId = :answer group by tweetPollSwitch.answers.answer, tweetPollSwitch.answers.color",
              new String[]{"tweetPoll", "answer"}, new Object[]{tweetPollId, answerId});
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

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ITweetPoll#getAnswerTweetSwitch(org.encuestame.persistence.domain.tweetpoll.TweetPoll, org.encuestame.persistence.domain.question.QuestionAnswer)
     */
    @SuppressWarnings("unchecked")
    public TweetPollSwitch getAnswerTweetSwitch(final TweetPoll tweetPoll, final QuestionAnswer questionAnswer){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollSwitch.class);
        criteria.add(Restrictions.eq("tweetPoll", tweetPoll));
        criteria.add(Restrictions.eq("answers", questionAnswer));
        return (TweetPollSwitch) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ITweetPoll#getAnswerTweetSwitch(org.encuestame.persistence.domain.question.QuestionAnswer)
     */
    @SuppressWarnings("unchecked")
    public List<TweetPollSwitch> getAnswerTweetSwitch(final QuestionAnswer questionAnswer){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollSwitch.class);
        criteria.add(Restrictions.eq("answers", questionAnswer));
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
    public List<TweetPollFolder> retrieveTweetPollFolderByAccount(final Account account){
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollFolder.class);
        criteria.add(Restrictions.eq("users", account));
        criteria.add(Restrictions.eq("status", org.encuestame.persistence.domain.Status.ACTIVE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Retrieve TweetPoll by Folder
     * @param userId
     * @param folderId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> retrieveTweetPollByFolder(final Long userId, final Long folderId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
         criteria.add(Restrictions.eq("tweetOwner.uid", userId));
         criteria.add(Restrictions.eq("tweetPollFolder.id", folderId));
         return getHibernateTemplate().findByCriteria(criteria);
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
    public TweetPollFolder getTweetPollFolderByIdandUser(final Long folderId, final Account account) {
         final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollFolder.class);
         criteria.add(Restrictions.eq("users", account));
         criteria.add(Restrictions.eq("status", org.encuestame.persistence.domain.Status.ACTIVE));
         criteria.add(Restrictions.eq("id", folderId));
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

    /**
     * Get {@link TweetPoll} by id, userid and slug name.
     * @param tweetPollId tweet poll id.
     * @param userId user id.
     * @param slugName slug name.
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    public TweetPoll getTweetPollByIdandSlugName(final Long tweetPollId, final String slugName) throws UnsupportedEncodingException {
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.createAlias("question", "q");
        criteria.add(Restrictions.eq("tweetPollId", tweetPollId));
        criteria.add(Restrictions.eq("q.slugQuestion", URLEncoder.encode(slugName, "UTF-8")));
        return (TweetPoll) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
   }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ITweetPoll#getTweetpollByHashTagName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> getTweetpollByHashTagId(final Long hashTagId, final Integer limit, final String filterby){
         final DetachedCriteria detached = DetachedCriteria.forClass(TweetPoll.class)
        .createAlias("hashTags", "hashTags")
        .setProjection(Projections.id())
        .add(Subqueries.propertyIn("hashTags.hashTagId",
        DetachedCriteria.forClass(HashTag.class, "hash")
              .setProjection(Projections.id())
              .add(Restrictions.in("hash.hashTagId", new Long[] {hashTagId}))));
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class, "tweetPoll");
        criteria.add(Subqueries.propertyIn("tweetPoll.tweetPollId", detached));
        if(filterby.equals("hashtag")){
            criteria.addOrder(Order.desc("tweetPoll.createDate"));
        }
        else if (filterby.equals("hashtagRated")){
            criteria.addOrder(Order.desc("numbervotes"));
        }
        return getHibernateTemplate().findByCriteria(criteria, 0, limit);
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ITweetPoll#getLinksByTweetPoll(org.encuestame.persistence.domain.tweetpoll.TweetPoll)
     */
    @SuppressWarnings("unchecked")
    public List<TweetPollSavedPublishedStatus> getLinksByTweetPoll(final TweetPoll tweetPoll) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollSavedPublishedStatus.class);
        criteria.add(Restrictions.eq("tweetPoll", tweetPoll));
        criteria.add(Restrictions.isNotNull("apiType"));
        criteria.add(Restrictions.isNotNull("tweetId"));
        criteria.add(Restrictions.eq("status", Status.SUCCESS));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ITweetPoll#getMaxTweetPollLikeVotesbyUser(java.lang.Long, java.util.Date, java.util.Date)
     */
    public Long getMaxTweetPollLikeVotesbyUser(final Long userId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TweetPoll.class);
        criteria.setProjection(Projections.max("likeVote"));
        criteria.createAlias("editorOwner", "editorOwner");
        criteria.add(Restrictions.eq("editorOwner.uid", userId));
        @SuppressWarnings("unchecked")
        List<Long> results = getHibernateTemplate().findByCriteria(criteria);
        return (Long) (results.get(0) == null ? 0 : results.get(0));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ITweetPoll#getTweetPolls(java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<TweetPoll> getTweetPolls(final Integer maxResults,
            final Integer start, final Date range) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(TweetPoll.class);
        criteria.add(Restrictions.eq("publishTweetPoll", Boolean.TRUE));
        //criteria.add(Restrictions.gt("createDate", range));
        criteria.addOrder(Order.desc("createDate"));
        return (List<TweetPoll>) filterByMaxorStart(criteria, maxResults, start);
    }
}