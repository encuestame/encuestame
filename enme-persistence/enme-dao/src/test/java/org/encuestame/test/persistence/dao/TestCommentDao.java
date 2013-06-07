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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.CommentDao;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test for {@link CommentDao}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since
 */
@Category(DefaultTest.class)
public class TestCommentDao extends AbstractBase {

    /** {@link Comment} **/

    private Comment comment ;

    /** {@link UserAccount} **/
    private UserAccount user;

    /** {@link Question} **/
    private Question question;

    /** {@link TweetPoll} **/
    private TweetPoll tpoll;

    /** Max results**/
    private Integer MAX_RESULTS = 10;

    /** **/
    private Integer START = 0;

    /** **/
    private DateTime creationDate  = new DateTime();

    @Before
    public void initData(){
        this.user = createUserAccount("isabelle", createAccount());
        this.question = createQuestion("When did you go last weekend", "");
        this.tpoll = createPublishedTweetPoll(user.getAccount(), this.question);
        this.comment = createDefaultTweetPollCommentVoted("I was working", this.tpoll, user, 150L, 985L, new Date());

    }

    /**
     * Test get List comment by keyword.
     */
    @Test
    public void testgetListCommentsByKeyword(){
        assertNotNull(this.comment);
        flushIndexes();
        final String keyword = "working";
        final List<Comment> commentList = getCommentsOperations().getCommentsByKeyword(keyword,10 ,null);
        assertEquals("Should be equals", 1, commentList.size());
    }

    /**
     * Test get commnet by id.
     */
    @Test
    public void testGetCommentById(){
        assertNotNull(this.comment);
        final Comment comment = getCommentsOperations().getCommentById(this.comment.getCommentId());
        assertEquals("Should be equals", this.comment.getCommentId(), comment.getCommentId());
    }

    /**
     * Test get comment by id and user.
     */
    @Test
    public void testGetCommentByIdandUser(){
        assertNotNull(this.comment);
        final Comment comment = getCommentsOperations().getCommentByIdandUser(this.comment.getCommentId(), this.user);
        assertEquals("Should be equals", this.comment.getCommentId(), comment.getCommentId());
    }

    /**
     * Test get comments by user.
     */
    @Test
    public void getCommentsbyUser(){
        final Question question2 = createQuestion("Who will win the supercopa", "");
        final TweetPoll tpoll2 = createPublishedTweetPoll(user.getAccount(), question2);
        createDefaultTweetPollComment("I was playing pc games", tpoll2, user);
        final List<Comment> commentList = getCommentsOperations().getCommentsbyUser(this.user, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, commentList.size());
    }

    /**
     * Test get comments by TweetPoll.
     */
    @Test
    public void testGetCommentsbyTweetPoll(){
        final UserAccount userAcc2 = createUserAccount("anita", createAccount());
        assertNotNull(userAcc2);
        createDefaultTweetPollComment("I was watching tv", this.tpoll, userAcc2);
        final List<Comment> commentbyTweetPoll = getCommentsOperations()
                .getCommentsbyTweetPoll(tpoll, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, commentbyTweetPoll.size());
        final Long totalComment = getCommentsOperations()
                .getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
                        TypeSearchResult.TWEETPOLL);
        assertEquals("Should be equals", 2, totalComment.intValue());
    }

    /**
     * Test get top rated comments.
     */
    @Test
    public void testGetTopRatedComments(){
        final Calendar myDate = Calendar.getInstance();
        myDate.add(Calendar.DATE, -1);
        this.comment.setCreatedAt(myDate.getTime());
        getCommentsOperations().saveOrUpdate(this.comment);
        //two days before
        myDate.add(Calendar.DATE, -2);
        createDefaultTweetPollCommentVoted("Comment 1", tpoll, this.user, 170L, 300L, myDate.getTime());
        createDefaultTweetPollCommentVoted("Comment 2", tpoll, this.user, 540L, 580L, myDate.getTime());

        //three days before
        myDate.add(Calendar.DATE, -3);
        createDefaultTweetPollCommentVoted("Comment 3", tpoll, this.user, 223L, 160L, myDate.getTime());
        createDefaultTweetPollCommentVoted("Comment 4", tpoll, this.user, 389L, 60L, myDate.getTime());

        //four days before
        myDate.add(Calendar.DATE, -4);
        createDefaultTweetPollCommentVoted("Comment 5", tpoll, this.user, 110L, 630L, myDate.getTime());
        //five days before
        myDate.add(Calendar.DATE, -5);
        createDefaultTweetPollCommentVoted("Comment 6", tpoll, this.user, 15L, 155L, myDate.getTime());

        // Get total tweetpoll comments published.
        final List<Comment> totalCommentsPublished = getCommentsOperations().getCommentsbyTweetPoll(tpoll, 15, 0);
        assertEquals("Should be equals", 7, totalCommentsPublished.size());

        // Get total rated comments published.
        final List<Comment> getLikeTopRatedComments = getCommentsOperations().getTopRatedComments(CommentsSocialOptions.LIKE_VOTE, 3, 15, 0);
        assertEquals("Should be equals", 3, getLikeTopRatedComments.size());

        final List<Comment> getDisLikeTopRatedComments = getCommentsOperations().getTopRatedComments(CommentsSocialOptions.DISLIKE_VOTE, 3, 15, 0);
        assertEquals("Should be equals", 3, getDisLikeTopRatedComments.size());
    }

    /**
     * Test Retrieve Approved comments.
     */
    @Test
    public void testRetrieveApprovedComments(){

    	createDefaultTweetPollCommentWithStatus("First comment", tpoll, this.user, CommentOptions.APPROVE, this.creationDate.toDate());
    	createDefaultTweetPollCommentWithStatus("Second comment", tpoll, this.user, CommentOptions.APPROVE, this.creationDate.toDate());

		final List<Comment> approvedComments = getCommentsOperations()
				.getCommentsbyTypeAndStatus(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 10, 0,
						CommentOptions.APPROVE, null);
		assertEquals("Should be equals", 2, approvedComments.size());
    }


    /**
     * Test retrieve Comments by status and date range
     */
    @Test
    public void testRetrieveApprovedCommentsByRange(){
		// Today
		createDefaultTweetPollCommentWithStatus("Third comment", tpoll,
				this.user, CommentOptions.APPROVE, this.creationDate.toDate());
		// Last week
		this.creationDate.minusDays(2);
		createDefaultTweetPollCommentWithStatus("Third comment", tpoll,
				this.user, CommentOptions.APPROVE, this.creationDate.minusDays(2).toDate());

		// Last week
		this.creationDate.minusDays(3);
		createDefaultTweetPollCommentWithStatus("Fourth comment", tpoll,
				this.user, CommentOptions.APPROVE, this.creationDate.minusDays(2).toDate());

		final List<Comment> approvedComments = getCommentsOperations()
				.getCommentsbyTypeAndStatus(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 10, 0,
						CommentOptions.APPROVE, SearchPeriods.SEVENDAYS);

		assertEquals("Should be equals", 3, approvedComments.size());

		final List<Comment> approvedCommentsToday = getCommentsOperations()
				.getCommentsbyTypeAndStatus(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 10, 0,
						CommentOptions.APPROVE, SearchPeriods.TWENTYFOURHOURS);
		assertEquals("Should be equals", 1, approvedComments.size());
    }

}
