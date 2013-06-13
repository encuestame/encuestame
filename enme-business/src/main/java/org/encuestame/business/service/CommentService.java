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
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.ICommentService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.service.imp.ISurveyService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeCommentNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.persistence.exception.EnmeNotAllowedException;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.CommentBean;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link Comment} service support.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  August 14, 2011
 */
@Service
public class CommentService extends AbstractBaseService implements ICommentService{

    /**
     * Tweetpoll Service.
     */
    @Autowired
    public ITweetPollService tweetPollService;

    /**
     * Poll Service.
     */
    @Autowired
    public IPollService pollService;

    /**
     * Survey Service.
     */
    @Autowired
    public ISurveyService surveyService;

    /**
     * Log.
     **/
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Default value for a single vote.
     */
    private Long VOTE_VALUE = 1L;

    /** Time Range value by default. **/
    private Integer TIME_RANGE_VALUE = 1;


    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getComments(org.encuestame.utils.enums.TypeSearchResult, java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    public List<Comment> getComments(
            final TypeSearchResult searchResult,
            final Long itemId,
            final Integer max,
            final Integer start) throws EnMeExpcetion{
        final List<Comment> comments = new ArrayList<Comment>();
        if (searchResult.equals(TypeSearchResult.TWEETPOLL)) {
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollPublishedById(
                    itemId);
             comments.addAll(this.getCommentsbyTweetPoll(tweetPoll, max, start));
        } else if (searchResult.equals(TypeSearchResult.POLL)) {
            final Poll poll = getPollService().getPollById(itemId);
            comments.addAll(getCommentsOperations().getCommentsbPoll(poll, max, start));
        } else if (searchResult.equals(TypeSearchResult.SURVEY)) {
            final Survey survey = null;
            //TODO:
        } else {
            throw new EnMeExpcetion("invalid type");
        }
        return comments;
    }

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
            final Integer start, final CommentOptions commentStatus) throws EnMeNoResultsFoundException{
        final List<CommentBean> commentBean = new ArrayList<CommentBean>();
        final List<Comment> comments = getCommentsOperations().getCommentsbyUser(
                getUserAccount(getUserPrincipalUsername()), maxResults, start, commentStatus);
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
    public Comment createComment(final CommentBean commentBean) throws EnMeNoResultsFoundException, EnmeNotAllowedException{
        final Comment comment = new Comment();
        comment.setComment(commentBean.getComment());
        comment.setCreatedAt(commentBean.getCreatedAt());
        comment.setParentId(commentBean.getParentId());
        comment.setDislikeVote(0L);
        comment.setLikeVote(0L);
        comment.setUser(getUserAccount(getUserPrincipalUsername()));
        if (TypeSearchResult.TWEETPOLL.equals(commentBean.getType())) {
            final TweetPoll tweetPoll = getTweetPollById(commentBean.getId());
            comment.setTweetPoll(tweetPoll);
        } else if (TypeSearchResult.POLL.equals(commentBean.getType())) {
            final Poll poll = getPollById(commentBean.getId());
            comment.setPoll(poll);
        } else if (TypeSearchResult.SURVEY.equals(commentBean.getType())) {
            //TODO: survey get imp
        } else {
            throw new EnmeNotAllowedException("type not allowed");
        }
        getCommentsOperations().saveOrUpdate(comment);
        return comment;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getCommentsbyTweetPoll(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    public List<Comment> getCommentsbyTweetPoll(final TweetPoll tweetPoll,
            final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        List<Comment> tweetPollComments = new ArrayList<Comment>();
        tweetPollComments = getCommentsOperations().getCommentsbyTweetPoll(tweetPoll, maxResults, start);
        return tweetPollComments;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ICommentService#getTopRatedComments(org.encuestame.utils.enums.CommentsSocialOptions, java.lang.Integer, java.lang.Integer)
     */
    public List<CommentBean> getTopRatedComments(
            final CommentsSocialOptions socialCommentOption,
            final Integer maxResults,
            final Integer start) {
        final Integer timeRange = EnMePlaceHolderConfigurer
                .getIntegerProperty("comment.time.range") == null ? this.TIME_RANGE_VALUE
                : EnMePlaceHolderConfigurer
                        .getIntegerProperty("comment.time.range");

        log.debug("getTopRatedCommentsY TIME RANGE ---> " + timeRange);
        log.debug("getTopRatedComments TIME maxResults ---> " + maxResults);
        log.debug("getTopRatedComments TIME start ---> " + start);
        final List<CommentBean> commentBean = new ArrayList<CommentBean>();
        final List<Comment> topCommentList = getCommentsOperations()
                .getTopRatedComments(socialCommentOption,
                        timeRange, maxResults, start);
        commentBean.addAll(ConvertDomainBean
                .convertListCommentDomainToBean(topCommentList));
        return commentBean;
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

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.ICommentService#
     * retrieveCommentsByTypeAndStatus(java.lang.Long,
     * org.encuestame.utils.enums.TypeSearchResult, java.lang.Integer,
     * java.lang.Integer, org.encuestame.utils.enums.CommentOptions)
     */
    public List<Comment> retrieveCommentsByTypeAndStatus(final Long id,
            final TypeSearchResult typeSearch, final Integer maxResults,
            final Integer start, final CommentOptions commentStatus, final SearchPeriods period) {
        List<Comment> commentsByStatus = new ArrayList<Comment>();

        commentsByStatus = getCommentsOperations().getCommentsbyTypeAndStatus(
                id, typeSearch, maxResults, start, commentStatus, period);

        return commentsByStatus;
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.encuestame.core.service.imp.ICommentService#totalCommentsbyType(java
	 * .lang.Long, org.encuestame.utils.enums.TypeSearchResult,
	 * org.encuestame.utils.enums.CommentOptions,
	 * org.encuestame.utils.enums.SearchPeriods)
	 */
	public Long totalCommentsbyType(final Long id,
			final TypeSearchResult itemType,
			final CommentOptions commentStatus, final SearchPeriods period) throws EnMeNoResultsFoundException {
		Long totalComments = 0L;

		if(itemType.equals(TypeSearchResult.TWEETPOLL)){
			final TweetPoll tpoll = this.getTweetPollById(id);
			totalComments = getCommentsOperations().getTotalCommentsbyItem(tpoll.getTweetPollId(), itemType, commentStatus, period);
		}
		else if(itemType.equals(TypeSearchResult.POLL)){
			final Poll poll = this.getPollById(id);
			totalComments = getCommentsOperations().getTotalCommentsbyItem(poll.getPollId(), itemType, commentStatus, period);

		}
		else if(itemType.equals(TypeSearchResult.SURVEY)){
			//
		}
		return totalComments;
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.encuestame.core.service.imp.ICommentService#totalCommentsbyTypeAndStatus
	 * (org.encuestame.utils.enums.TypeSearchResult,
	 * org.encuestame.utils.enums.CommentOptions,
	 * org.encuestame.utils.enums.SearchPeriods)
	 */
	public Long totalCommentsbyTypeAndStatus(final TypeSearchResult itemType,
			final CommentOptions commentStatus, final SearchPeriods period)
			throws EnMeNoResultsFoundException {
		Long totalCommentsbyType = 0L;

		totalCommentsbyType = getCommentsOperations()
				.getTotalCommentsbyTypeAndStatus(itemType, commentStatus,
						period);

		return totalCommentsbyType;
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

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService the tweetPollService to set
     */
    public void setTweetPollService(final ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }

    /**
     * @return the pollService
     */
    public IPollService getPollService() {
        return pollService;
    }

    /**
     * @param pollService the pollService to set
     */
    public void setPollService(final IPollService pollService) {
        this.pollService = pollService;
    }

    /**
     * @return the surveyService
     */
    public ISurveyService getSurveyService() {
        return surveyService;
    }

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(final ISurveyService surveyService) {
        this.surveyService = surveyService;
    }
}
