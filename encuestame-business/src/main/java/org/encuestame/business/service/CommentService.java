/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.ICommentService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.CommentsSocialOptions;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeCommentNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.web.CommentBean;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

/**
 * {@link Comment} service support.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  August 14, 2011
 */
@Service
public class CommentService extends AbstractBaseService implements ICommentService{

    /** Log. **/
    private Log log = LogFactory.getLog(this.getClass());

    private Long VOTE_VALUE = 1L;

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getCommentbyId(java.lang.Long)
     */
    public Comment getCommentbyId(final Long commentId) throws EnMeNoResultsFoundException, HibernateException{
        Comment comment = getCommentsOperations().getCommentByIdandUser(
                commentId,  getUserAccount(getUserPrincipalUsername()));
        if (comment == null){
            throw new EnMeCommentNotFoundException("comment not found");
        }
        return comment;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getCommentsbyUser(java.lang.Integer, java.lang.Integer)
     */
    public List<CommentBean> getCommentsbyUser(final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        final List<CommentBean> commentBean = new ArrayList<CommentBean>();
        final List<Comment> comments = getCommentsOperations().getCommentsbyUser(
                getUserAccount(getUserPrincipalUsername()), maxResults, start);
        commentBean.addAll(ConvertDomainBean.convertListCommentDomainToBean(comments));
        return commentBean;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getCommentsbyKeyword(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<CommentBean> getCommentsbyKeyword(
            final String keyword,
            final Integer maxResults,
            final Integer start) throws EnMeExpcetion{
        List<CommentBean> commentBean = new ArrayList<CommentBean>();
        List<Comment> comments = new ArrayList<Comment>();
        if (keyword == null){
            throw new EnMeExpcetion("keyword is missing");
        }
        else {
            comments = getCommentsOperations().getCommentsByKeyword(keyword, maxResults, null);
            log.info(" Comments by keyword size" + comments.size());
            commentBean.addAll(ConvertDomainBean.convertListCommentDomainToBean(comments));
        }
        return commentBean;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#createComment(org.encuestame.utils.web.CommentBean)
     */
    public Comment createComment(final CommentBean commentBean) throws EnMeNoResultsFoundException{
        final Comment comment = new Comment();
        comment.setComment(commentBean.getComment());
        comment.setCreatedAt(commentBean.getCreatedAt());
        comment.setUser(getUserAccount(getUserPrincipalUsername()));
        getCommentsOperations().saveOrUpdate(comment);
        return comment;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getCommentsbyTweetPoll(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    public List<Comment> getCommentsbyTweetPoll(final Long tweetPollId,
            final Integer maxResults,
            final Integer start) throws EnMeTweetPollNotFoundException{
        List<Comment> tweetPollComments = new ArrayList<Comment>();
        final TweetPoll tpoll = getTweetPollDao().getTweetPollById(tweetPollId);
        if (tpoll == null){
            throw new EnMeTweetPollNotFoundException("keyword is missing");
        }else {
            tweetPollComments = getCommentsOperations().getCommentsbyTweetPoll(tpoll, maxResults, start);
        }
        return tweetPollComments;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#voteCommentSocialOption(java.lang.Long, org.encuestame.persistence.domain.CommentsSocialOptions)
     */
    public void voteCommentSocialOption(final Long commentId, final CommentsSocialOptions vote) throws EnMeNoResultsFoundException,
                                        HibernateException, EnmeFailOperation{
        final Comment comment = this.getCommentbyId(commentId);
        if (vote.equals(CommentsSocialOptions.LIKE_VOTE)) {
            this.CommentLikeVote(comment);
        } else if (vote.equals(CommentsSocialOptions.DISLIKE_VOTE)) {
            this.CommentDislikeVote(comment);
        } else {
            throw new EnmeFailOperation("Social option not found");
        }

    }

    /**
     * Vote dislike comment option.
     * @param comment
     */
    private void CommentDislikeVote(final Comment comment){
        long lastDislikeVote = comment.getDislikeVote();
        lastDislikeVote += this.VOTE_VALUE;
        comment.setDislikeVote(lastDislikeVote);
        getCommentsOperations().saveOrUpdate(comment);
    }

    /**
     * Vote Like comment option.
     * @param comment
     */
    private void CommentLikeVote(final Comment comment){
        long lastLikeVote = comment.getLikeVote();
        lastLikeVote += this.VOTE_VALUE;
        comment.setDislikeVote(lastLikeVote);
        getCommentsOperations().saveOrUpdate(comment);
    }
}
