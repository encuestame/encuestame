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
package org.encuestame.core.service.imp;

import java.util.List;

import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.CommentBean;
import org.hibernate.HibernateException;

/**
 * Comment Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 14, 2011
 */
public interface ICommentService {

    /**
     *
     * @param searchResult
     * @param itemId
     * @param max
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<Comment> getComments(
            final TypeSearchResult searchResult,
            final Long itemId,
            final Integer max,
            final Integer start) throws EnMeExpcetion;

    /**
     * Get comment by id.
     * @param commentId
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws HibernateException
     */
    Comment getCommentbyId(final Long commentId) throws EnMeNoResultsFoundException, HibernateException;

    /**
     * Get comments by user.
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<CommentBean> getCommentsbyUser(final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Get comments by keyword.
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<CommentBean> getCommentsbyKeyword(
                final String keyword,
                final Integer maxResults,
                final Integer start) throws EnMeExpcetion;

    /**
     * Create comment.
     * @param commentBean
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Comment createComment(final CommentBean commentBean) throws EnMeNoResultsFoundException;

    /***
     * Get comments by TweetPoll.
     * @param tweetPollId
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<Comment> getCommentsbyTweetPoll(final TweetPoll tweetPollId,
                final Integer maxResults,
                final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Vote comment social option.
     * @param commentId
     * @param vote
     * @throws EnMeNoResultsFoundException
     * @throws HibernateException
     * @throws EnmeFailOperation
     */
     void voteCommentSocialOption(final Long commentId, final CommentsSocialOptions vote) throws EnMeNoResultsFoundException,
        HibernateException, EnmeFailOperation;
}
