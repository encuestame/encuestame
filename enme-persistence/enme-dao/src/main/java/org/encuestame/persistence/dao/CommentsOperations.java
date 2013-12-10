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
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.enums.CommentStatus;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.HibernateException;

/**
 * {@link Comment} Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 11, 2011
 */
public interface CommentsOperations extends IBaseDao {

    /**
     * Get list comments by keyword.
     * @param keyword
     * @param maxResults
     * @param excludes
     * @return
     */
    List<Comment> getCommentsByKeyword(
            final String keyword,
            final Integer maxResults,
            final Long[] excludes);

    /**
     * Get comment by id.
     * @param commentId
     * @return
     * @throws HibernateException
     */
    Comment getCommentById(final Long commentId) throws HibernateException;

    /**
     * Get comments by id and user.
     * @param commentId
     * @param userAcc
     * @return
     * @throws HibernateException
     */
    Comment getCommentByIdandUser(final Long commentId, final UserAccount userAcc) throws HibernateException;

    /**
     * Get comments by user ordered by creation date.
     * @param userAcc
     * @param maxResults
     * @param start
     * @param commentStatus
     * @return
     */
    List<Comment> getCommentsbyUser(final UserAccount userAcc, final Integer maxResults, final Integer start, final CommentStatus commentStatus);

    /**
     * Get comments by TweetPoll
     * @param tpoll
     * @param maxResults
     * @param start
     * @return
     */
    List<Comment> getCommentsbyTweetPoll(final TweetPoll tpoll, final Integer maxResults, final Integer start);

    /**
     * Get total comments by item.
     * @param id
     * @param itemType
     * @param commentStatus
     * @return
     */
    Long getTotalCommentsbyItem(final Long id, final TypeSearchResult itemType, final CommentStatus commentStatus, final SearchPeriods period);

    /**
     * Get top rated comments.
     * @param socialOption
     * @param timeRange
     * @param maxResults
     * @param startResults
     * @return
     */
    List<Comment> getTopRatedComments(
            final CommentsSocialOptions socialOption, final Integer timeRange,
            final Integer maxResults, final Integer startResults);

    /**
     * Retrieve a list of comments for a poll.
     * @param poll
     * @param maxResults
     * @param start
     * @return
     */
    List<Comment> getCommentsbPoll(final Poll poll, final Integer maxResults, final Integer start);

    /**
     *
     * @param tpoll
     * @param maxResults
     * @param start
     * @param commentStatus
     * @param isApproved
     * @return
     */
    List<Comment> getCommentsbyTypeAndStatus(final Long id,
            final TypeSearchResult typeSearch, final Integer maxResults,
            final Integer start, final CommentStatus commentStatus, final SearchPeriods period);

    /**
     *
     * @param itemType
     * @param commentStatus
     * @param period
     * @return
     */
	Long getTotalCommentsbyTypeAndStatus(
			final TypeSearchResult itemType, final CommentStatus commentStatus, final SearchPeriods period);
}
