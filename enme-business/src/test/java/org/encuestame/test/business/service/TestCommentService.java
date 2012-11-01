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
package org.encuestame.test.business.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.business.service.CommentService;
import org.encuestame.core.service.imp.ICommentService;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.web.CommentBean;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link CommentService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 14, 2011
 */
@Category(DefaultTest.class)
public class TestCommentService extends AbstractSpringSecurityContext {

    @Autowired
    private ICommentService commentsOperationsService;

    /** {@link Comment} **/
    private Comment comment;

      /** Max Results. **/
    private Integer MAX_RESULTS = 10;

    /** Start Results. **/
    private Integer START = 0;

    private TweetPoll tweetPoll;


    @Before
    public void initService(){
        final Question question = createQuestion("Why the sky is blue?","html");
        this.tweetPoll = createTweetPollPublicated(true, true, new Date(), getSpringSecurityLoggedUserAccount(), question);
        // First comment on Tweetpoll
        this.comment = createDefaultTweetPollComment("My first tweetPoll comment", tweetPoll, getSpringSecurityLoggedUserAccount());
        // Second comment on Tweetpoll
        createDefaultTweetPollComment("My Second tweetPoll comment", tweetPoll, getSpringSecurityLoggedUserAccount());
        // Third comment on Tweetpoll
        createDefaultTweetPollComment("My Third tweetPoll comment", tweetPoll, getSpringSecurityLoggedUserAccount());
        // Fourth comment on Tweetpoll
        createDefaultTweetPollComment("dumb tweetPoll question", tweetPoll, getSpringSecurityLoggedUserAccount());
    }

    /**
     * Test get comment by id and user
     * @throws HibernateException
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetCommentbyId() throws EnMeNoResultsFoundException, HibernateException{
        assertNotNull(this.comment);
        final Comment comment = getCommentsOperationsService().getCommentbyId(this.comment.getCommentId());
        assertEquals("Should be equals", this.comment.getCommentId(), comment.getCommentId());
    }

    /**
     * Test get comments by user.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetCommentsbyUser() throws EnMeNoResultsFoundException{
        assertNotNull(this.comment);
        final List<CommentBean> commentsbyUser = getCommentsOperationsService().getCommentsbyUser(this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 4, commentsbyUser.size());
    }

    /**
     * Test get comments by keyword.
     * @throws EnMeExpcetion
     */
    @Test
    public void testGetCommentsbyKeyword() throws EnMeExpcetion{
        assertNotNull(this.comment);
        final String keyword1 = "tweetPoll";
        final String keyword2 = "My";
        final String keyword3 = "question";
        flushIndexes();
        final List<CommentBean> commentsbyKeyword1 = getCommentsOperationsService().getCommentsbyKeyword(keyword1, this.MAX_RESULTS, null);
        assertEquals("Should be equals", 4, commentsbyKeyword1.size());
        final List<Comment> commentsbyKeyword2 = getCommentsOperations().getCommentsByKeyword(keyword2, this.MAX_RESULTS, null);
        assertEquals("Should be equals", 3, commentsbyKeyword2.size());
        final List<Comment> commentsbyKeyword3 = getCommentsOperations().getCommentsByKeyword(keyword3, this.MAX_RESULTS, null);
        assertEquals("Should be equals", 1, commentsbyKeyword3.size());
    }

    /**
     * Test create comment
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testCreateComment() throws EnMeNoResultsFoundException{
        final CommentBean commentBean = createCommentBean("totally Agree", new Date(),
                getSpringSecurityLoggedUserAccount().getUid(), this.tweetPoll.getTweetPollId(), null);
        final Comment comment = getCommentsOperationsService().createComment(commentBean);
        assertNotNull(comment);
    }

    @Test
    public void testGetCommentsbyTweetPoll() throws EnMeNoResultsFoundException{
        final List<Comment> comments = getCommentsOperationsService().getCommentsbyTweetPoll(
                this.tweetPoll, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 4, comments.size());
    }

    /**
     *Test get top rated comments.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetTopCommentsRated() throws EnMeNoResultsFoundException {
        final Calendar myCal = Calendar.getInstance();
        myCal.add(Calendar.DATE, -1);
        createDefaultTweetPollCommentVoted("My top1 tweetPoll comment",
                tweetPoll, getSpringSecurityLoggedUserAccount(), 150L, 420L,
                myCal.getTime());
        createDefaultTweetPollCommentVoted("My top2 tweetPoll comment",
                tweetPoll, getSpringSecurityLoggedUserAccount(), 35L, 580L,
                new Date());
        myCal.add(Calendar.DATE, -2);
        createDefaultTweetPollCommentVoted("My top3 tweetPoll comment",
                tweetPoll, getSpringSecurityLoggedUserAccount(), 325L, 70L,
                myCal.getTime());

        final List<Comment> comments = getCommentsOperationsService()
                .getCommentsbyTweetPoll(this.tweetPoll, this.MAX_RESULTS,
                        this.START);
        assertEquals("Should be equals", 7, comments.size());
        final List<CommentBean> topList = getCommentsOperationsService()
                .getTopRatedComments(CommentsSocialOptions.LIKE_VOTE,
                        this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 7, topList.size());
    }

    /**
     * @return the commentsOperationsService
     */
    public ICommentService getCommentsOperationsService() {
        return commentsOperationsService;
    }

    /**
     * @param commentsOperationsService the commentsOperationsService to set
     */
    public void setCommentsOperationsService(final ICommentService commentsOperationsService) {
        this.commentsOperationsService = commentsOperationsService;
    }
}
