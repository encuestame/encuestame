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

import org.encuestame.persistence.dao.CommentsOperations;
import org.encuestame.persistence.dao.search.QueryBuilder;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link Comment} Dao.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 11, 2011
 */
@Repository("commentDao")
public class CommentDao extends AbstractHibernateDaoSupport implements CommentsOperations{

    @Autowired
    public CommentDao(SessionFactory sessionFactory){
             setSessionFactory(sessionFactory);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.CommentsOperations#getCommentById(java.lang.Long)
     */
    public Comment getCommentById(final Long commentId) throws HibernateException {
        return (Comment) getHibernateTemplate().get(Comment.class, commentId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.CommentsOperations#getCommentByIdandUser(java.lang.Long, org.encuestame.persistence.domain.security.UserAccount)
     */ 
    public Comment getCommentByIdandUser(final Long commentId, final UserAccount userAcc) throws HibernateException {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
        criteria.add(Restrictions.eq("user", userAcc));
        criteria.add(Restrictions.eq("commentId", commentId));
        return (Comment) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.encuestame.persistence.dao.CommentsOperations#getCommentsbyUser(org
	 * .encuestame.persistence.domain.security.UserAccount, java.lang.Integer,
	 * java.lang.Integer, org.encuestame.utils.enums.CommentOptions)
	 */
    @SuppressWarnings("unchecked")
	public List<Comment> getCommentsbyUser(final UserAccount userAcc,
			final Integer maxResults, final Integer start,
			final List<CommentOptions> commentOptions) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
        criteria.add(Restrictions.eq("user", userAcc));
        criteria.addOrder(Order.desc("createdAt"));
		criteria.addOrder(Order.desc("likeVote"));
		if ((commentOptions.size() != 1)
				&& (!commentOptions.get(0).equals(CommentOptions.ALL))) {
			criteria.add(Restrictions.in("commentOptions", commentOptions));
		}
		return (List<Comment>) filterByMaxorStart(criteria, maxResults, start);
    }



    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.CommentsOperations#getCommentsbyTweetPoll(org.encuestame.persistence.domain.tweetpoll.TweetPoll, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsbyTweetPoll(final TweetPoll tpoll, final Integer maxResults, final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
        criteria.add(Restrictions.eq("tweetPoll",tpoll));
        criteria.addOrder(Order.desc("likeVote"));
        return (List<Comment>) filterByMaxorStart(criteria, maxResults, start);
    }

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsbyTypeAndStatus(final Long id,
			final TypeSearchResult typeSearch, final Integer maxResults,
			final Integer start, final CommentOptions commentOptions, final SearchPeriods period) {
		final DetachedCriteria criteria = DetachedCriteria
				.forClass(Comment.class);
		if (typeSearch.equals(TypeSearchResult.TWEETPOLL)) {
			criteria.createAlias("tweetPoll", "tweetPoll");
			criteria.add(Restrictions.eq("tweetPoll.tweetPollId", id));
		} else if (typeSearch.equals(TypeSearchResult.POLL)) {
			criteria.createAlias("poll", "poll");
			criteria.add(Restrictions.eq("poll.pollId", id));
		} else if (typeSearch.equals(TypeSearchResult.SURVEY)) {
			criteria.createAlias("survey", "survey");
			criteria.add(Restrictions.eq("survey.sid", id));
		} else {
			log.error(" Search result type undefined " + typeSearch.toString());
		}

		criteria.addOrder(Order.desc("likeVote"));
		if (!commentOptions.equals(CommentOptions.ALL)) {
			criteria.add(Restrictions.eq("commentOptions", commentOptions));
		}

		if(period!=null){
			calculateSearchPeriodsDates(period, criteria, "createdAt");
		}
		return (List<Comment>) filterByMaxorStart(criteria, maxResults, start);
	}



    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.CommentsOperations#getCommentsbPoll(org.encuestame.persistence.domain.survey.Poll, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsbPoll(final Poll poll, final Integer maxResults, final Integer start) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
        criteria.add(Restrictions.eq("poll",poll));
        criteria.addOrder(Order.desc("likeVote"));
        return (List<Comment>) filterByMaxorStart(criteria, maxResults, start);
    }


    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.encuestame.persistence.dao.CommentsOperations#getTotalCommentsbyItem
	 * (java.lang.Long, org.encuestame.utils.enums.TypeSearchResult,
	 * org.encuestame.utils.enums.CommentOptions)
	 */
    public Long getTotalCommentsbyItem(final Long id, final TypeSearchResult itemType, final CommentOptions commentOptions, final SearchPeriods period){
          final DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
          criteria.setProjection(Projections.rowCount());
          if (itemType.equals(TypeSearchResult.TWEETPOLL)) {
              criteria.createAlias("tweetPoll", "tweetPoll");
              criteria.add(Restrictions.eq("tweetPoll.tweetPollId", id));
          } else if (itemType.equals(TypeSearchResult.POLL)) {
              criteria.createAlias("poll", "poll");
              criteria.add(Restrictions.eq("poll.pollId", id));
          } else if (itemType.equals(TypeSearchResult.SURVEY)) {
              criteria.createAlias("survey", "survey");
              criteria.add(Restrictions.eq("survey.sid", id));
          } else {
              log.error(" Search result type undefined " + itemType);
          }
          if(!commentOptions.equals(CommentOptions.ALL)){
      		criteria.add(Restrictions.eq("commentOptions", commentOptions));
          }
          if(period!=null){
  				calculateSearchPeriodsDates(period, criteria, "createdAt");
            }
          @SuppressWarnings("unchecked")
          List<Comment> results = (List<Comment>) getHibernateTemplate().findByCriteria(criteria);
          log.trace("Retrieve total comments by  " + itemType + "--->"
                  + results.size());
          return (Long) (results.get(0) == null ? 0 : results.get(0));
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see org.encuestame.persistence.dao.CommentsOperations#
	 * getTotalCommentsbyTypeAndStatus
	 * (org.encuestame.utils.enums.TypeSearchResult,
	 * org.encuestame.utils.enums.CommentOptions)
	 */
	public Long getTotalCommentsbyTypeAndStatus(
			final TypeSearchResult itemType, final CommentOptions commentOptions, final SearchPeriods period) {
		final DetachedCriteria criteria = DetachedCriteria
				.forClass(Comment.class);
		criteria.setProjection(Projections.rowCount());
		if (itemType.equals(TypeSearchResult.TWEETPOLL)) {
			criteria.createAlias("tweetPoll", "tweetPoll");
			criteria.add(Restrictions.isNotNull("tweetPoll"));
		} else if (itemType.equals(TypeSearchResult.POLL)) {
			criteria.createAlias("poll", "poll");
			criteria.add(Restrictions.isNotNull("poll"));
		} else if (itemType.equals(TypeSearchResult.SURVEY)) {
			criteria.createAlias("survey", "survey");
			criteria.add(Restrictions.isNotNull("survey"));
		} else {
			log.error(" Search result type undefined " + itemType);
		}
		if (!commentOptions.equals(CommentOptions.ALL)) {
			criteria.add(Restrictions.eq("commentOptions", commentOptions));
		}
		 if(period!=null){
				calculateSearchPeriodsDates(period, criteria, "createdAt");
         }
		@SuppressWarnings("unchecked")
		List<Comment> results = (List<Comment>) getHibernateTemplate().findByCriteria(criteria);
		log.trace("Retrieve total comments by  " + itemType + "--->"
				+ results.size());
		return (Long) (results.get(0) == null ? 0 : results.get(0));
	}


    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.CommentsOperations#getTopRatedComments(org.encuestame.utils.enums.CommentsSocialOptions, java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getTopRatedComments(
            final CommentsSocialOptions socialOption,
            final Integer timeRange,
            final Integer maxResults, final Integer startResults) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
        log.debug("getTopRatedComments start date "+ getCommentTimeRange(timeRange));
        log.debug("getTopRatedComments end date "+  getNextDayMidnightDate());
        criteria.add(Restrictions.between("createdAt",
                getCommentTimeRange(timeRange), getNextDayMidnightDate()));
        if (socialOption != null) {
            if (socialOption.equals(CommentsSocialOptions.LIKE)) {
                criteria.addOrder(Order.desc("likeVote"));
            } else if (socialOption.equals(CommentsSocialOptions.DISLIKE)) {
                criteria.addOrder(Order.desc("dislikeVote"));
            } else {
                criteria.addOrder(Order.desc("likeVote"));
            }
        } else {
            criteria.addOrder(Order.desc("likeVote"));
        }
        return (List<Comment>) filterByMaxorStart(criteria, maxResults,
                startResults);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.CommentsOperations#getListCommentsByKeyword(java.lang.String, java.lang.Integer, java.lang.Long[])
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsByKeyword(
            final String keyword,
            final Integer maxResults,
            final Long[] excludes){
            log.info("keyword "+keyword);
            List<Comment> searchResult = (List<Comment>) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        @SuppressWarnings("deprecation")
                        public Object doInHibernate(org.hibernate.Session session) {
                            final Criteria criteria = session.createCriteria(Comment.class);
                            final QueryBuilder<Comment> query = new QueryBuilder(getSessionFactory());
                            if (excludes != null && excludes.length > 0) {
                                for (int i = 0; i < excludes.length; i++) {
                                    log.debug("excluding hashtag... "+excludes[i]);
                                    criteria.add(Restrictions.ne("commentId", excludes[i]));
                                }
                            }
                            List<Comment> results = query.build(criteria,
                                    keyword,
                                    maxResults,
                                    0,
                                    new String[] { "comment"},
                                    "comment",
                                    Comment.class);
                            return results;
                        }
                    });
            return searchResult;
        }
}
