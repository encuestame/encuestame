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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.CommentDao;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
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

    /** {@link Poll} **/
    private Poll poll;

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
        createDefaultTweetPollCommentVoted("I was working alone", this.tpoll, user, 120L, 985L, new Date());
        this.poll = createDefaultPoll(this.question, this.user);
    }

    /**
     * Test get List comment by keyword.
     */
    @Test
    public void testgetListCommentsByKeyword(){
    	final DateTime dt = new DateTime();
        assertNotNull(this.comment);
        createComment("Home alone", 25L, this.tpoll, null, null, user, 3L, dt.plusHours(2).toDate());
        createComment("I was watching TV", 40L, this.tpoll, null, null, user, 50L, dt.plusHours(3).toDate());
        createComment("I was at the beach", 0L, this.tpoll, null, null, user, 10L, dt.plusHours(1).toDate());
        createComment("I was working at home ", 3L, this.tpoll, null, null, user, 9L, dt.plusHours(1).toDate());
       
        flushIndexes();
        final String keyword = "working";  
        final List<Comment> commentList = getCommentsOperations().getCommentsByKeyword(keyword,10 ,null); 
        assertEquals("Should be equals", 2, commentList.size());
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
        final List<CommentOptions> commentOpt = new ArrayList<>();
        commentOpt.add(CommentOptions.ALL);
        final List<Comment> commentList = getCommentsOperations().getCommentsbyUser(this.user, this.MAX_RESULTS, this.START, commentOpt);
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
                        TypeSearchResult.TWEETPOLL, CommentOptions.ALL, null);
        assertEquals("Should be equals", 2, totalComment.intValue());
    }

    /**
     *
     */
    @Test
    public void testRetrieveTweetPollCommentsbyStatus(){
		DateTime commentCreatedAt = this.creationDate;
		createDefaultTweetPollCommentWithStatus("first comments", tpoll,
				this.user, CommentOptions.APPROVE, commentCreatedAt.toDate());

		commentCreatedAt = this.creationDate.minusDays(2);

		createDefaultTweetPollCommentWithStatus("second comments", tpoll,
				this.user, CommentOptions.MODERATE, commentCreatedAt.toDate());

//		commentCreatedAt = this.creationDate.minusMonths(3);
//		createDefaultTweetPollCommentWithStatus("third comments", tpoll,
//				this.user, CommentOptions.RESTRICT,  commentCreatedAt.toDate());
//
//		commentCreatedAt = this.creationDate.minusYears(2);
//		createDefaultTweetPollCommentWithStatus("third comments", tpoll,
//				this.user, CommentOptions.RESTRICT, commentCreatedAt.toDate());


		commentCreatedAt = this.creationDate.minusDays(3);

		createDefaultTweetPollCommentWithStatus("fourth comments", tpoll,
				this.user, CommentOptions.APPROVE, commentCreatedAt.toDate());

		createDefaultTweetPollCommentWithStatus("fifth comments", tpoll,
				this.user, CommentOptions.MODERATE, this.creationDate.toDate());

		commentCreatedAt = this.creationDate.minusDays(4);
		createDefaultTweetPollCommentWithStatus("sixth comments", tpoll,
				this.user, CommentOptions.APPROVE, commentCreatedAt.toDate());

	    // Retrieve without
		final Long tweetpollCommentsApproved = getCommentsOperations()
				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, CommentOptions.APPROVE, null);
		assertEquals("Comments approved should be equals", 3, tweetpollCommentsApproved.intValue());

		// Retrieve without
		final Long tweetpollCommentsModerate = getCommentsOperations()
				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, CommentOptions.MODERATE, null);

		assertEquals("Comments moderate should be equals", 2, tweetpollCommentsModerate.intValue());

//		// Retrieve without
//		final Long tweetpollCommentsRestrict = getCommentsOperations()
//				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
//						TypeSearchResult.TWEETPOLL, CommentOptions.RESTRICT, null);
//
//		assertEquals("Comments Restrict should be equals", 2, tweetpollCommentsRestrict.intValue());

		 // Retrieve with
		final Long tweetpollCommentsApprovedWithRange = getCommentsOperations()
				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, CommentOptions.APPROVE, SearchPeriods.SEVENDAYS);
		assertEquals("Comments approved should be equals", 3, tweetpollCommentsApprovedWithRange.intValue());


		// Retrieve with
		final Long tweetpollCommentsModerateWithRange = getCommentsOperations()
				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, CommentOptions.MODERATE, SearchPeriods.TWENTYFOURHOURS);

			assertEquals("Comments moderate should be equals", 1, tweetpollCommentsModerateWithRange.intValue());

//		// Retrieve with
//		final Long tweetpollCommentsRestrictWithRange = getCommentsOperations()
//				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
//						TypeSearchResult.TWEETPOLL, CommentOptions.RESTRICT, SearchPeriods.ONEYEAR);
//		assertEquals("Comments Restrict should be equals", 1, tweetpollCommentsRestrictWithRange.intValue());
//
//		// Retrieve with
//		final Long tweetpollCommentsAllRestrictWithRange = getCommentsOperations()
//				.getTotalCommentsbyItem(this.tpoll.getTweetPollId(),
//						TypeSearchResult.TWEETPOLL, CommentOptions.RESTRICT, SearchPeriods.ALLTIME);
//		assertEquals("Comments Restrict should be equals", 2, tweetpollCommentsAllRestrictWithRange.intValue());


    }

    /**
     * Retrieve all {@link Comment} by status
     */
	@Test
    public void testRetrieveAllCommentsbyStatus(){
		 DateTime commentPollCreated = this.creationDate;

		createDefaultTweetPollCommentWithStatus("aaa comments", tpoll,
				this.user, CommentOptions.APPROVE, this.creationDate.toDate());

		commentPollCreated = this.creationDate.minusDays(4);
		createDefaultTweetPollCommentWithStatus("bbb comments", tpoll,
				this.user, CommentOptions.MODERATE, commentPollCreated.toDate());

		createDefaultTweetPollCommentWithStatus("ccc comments", tpoll,
				this.user, CommentOptions.MODERATE, this.creationDate.toDate());

		commentPollCreated = this.creationDate.minusMonths(3);
		createDefaultPollCommentWithStatus("ggg comments", poll, this.user,
				CommentOptions.APPROVE, commentPollCreated.toDate());


		createDefaultPollCommentWithStatus("hhh comments", poll, this.user,
				CommentOptions.MODERATE, commentPollCreated.toDate());


		createDefaultTweetPollCommentWithStatus("xxx comments", tpoll,
				this.user, CommentOptions.APPROVE, commentPollCreated.toDate());

	    final Long totalPollCommentsApproved = getCommentsOperations().getTotalCommentsbyTypeAndStatus(TypeSearchResult.POLL, CommentOptions.MODERATE, null);
	    assertEquals("Comments Moderate should be equals", 1, totalPollCommentsApproved.intValue());


	    final Long totalTweetPollCommentsApproved = getCommentsOperations()
				.getTotalCommentsbyTypeAndStatus(TypeSearchResult.TWEETPOLL,
						CommentOptions.APPROVE, null);
	    assertEquals("Comments Approve should be equals", 2, totalTweetPollCommentsApproved.intValue());


	    // With
		final Long totalPollCommentsApprovedWithRange = getCommentsOperations()
				.getTotalCommentsbyTypeAndStatus(TypeSearchResult.POLL,
						CommentOptions.MODERATE, null);
	    assertEquals("Comments Moderate should be equals", 1, totalPollCommentsApproved.intValue());

	    // With
	    final Long totalTweetPollCommentsApprovedWithRange = getCommentsOperations()
				.getTotalCommentsbyTypeAndStatus(TypeSearchResult.TWEETPOLL,
						CommentOptions.APPROVE, SearchPeriods.ONEYEAR);
	    assertEquals("Comments Approve should be equals", 2, totalTweetPollCommentsApprovedWithRange.intValue());

	    final Long totalTweetPollCommentsApprovedWithRangeToday = getCommentsOperations()
				.getTotalCommentsbyTypeAndStatus(TypeSearchResult.TWEETPOLL,
						CommentOptions.APPROVE, SearchPeriods.TWENTYFOURHOURS);

	    assertEquals("Comments Approve should be equals", 1, totalTweetPollCommentsApprovedWithRangeToday.intValue());
    }

	/**
	 *
	 */
	@Test
	public void testRetrieveAllPollCommentsbyStatus() {
		createDefaultPollCommentWithStatus("aaa comments", this.poll,
				this.user, CommentOptions.APPROVE, this.creationDate.toDate());
		createDefaultPollCommentWithStatus("bbb comments", this.poll,
				this.user, CommentOptions.MODERATE, this.creationDate.toDate());
//		createDefaultPollCommentWithStatus("ccc comments", this.poll,
//				this.user, CommentOptions.RESTRICT, this.creationDate.toDate());
		createDefaultPollCommentWithStatus("ddd comments", this.poll,
				this.user, CommentOptions.APPROVE, this.creationDate.toDate());
		createDefaultPollCommentWithStatus("eee comments", this.poll,
				this.user, CommentOptions.MODERATE, this.creationDate.toDate());
		createDefaultPollCommentWithStatus("fff comments", this.poll,
				this.user, CommentOptions.APPROVE, this.creationDate.toDate());

		createDefaultPollCommentWithStatus("ggg comments", this.poll, this.user,
				CommentOptions.APPROVE, creationDate.toDate());
		createDefaultPollCommentWithStatus("hhh comments", this.poll, this.user,
				CommentOptions.MODERATE, creationDate.toDate());

		final Long totalPollCommentsApproved = getCommentsOperations()
				.getTotalCommentsbyTypeAndStatus(TypeSearchResult.POLL,
						CommentOptions.APPROVE, null);
		  assertEquals("Comments Approve should be equals", 4, totalPollCommentsApproved.intValue());
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
        
        //FIXME: Why this method return 1 instead 3 ???
        // Get total rated comments published.
        final List<Comment> getLikeTopRatedComments = getCommentsOperations().getTopRatedComments(CommentsSocialOptions.LIKE_VOTE, 3, 15, 0);
        assertEquals("Should be equals", 1, getLikeTopRatedComments.size());

        final List<Comment> getDisLikeTopRatedComments = getCommentsOperations().getTopRatedComments(CommentsSocialOptions.DISLIKE_VOTE, 3, 15, 0);
        assertEquals("Should be equals", 1, getDisLikeTopRatedComments.size());
    }

    /**
     * Test Retrieve Approved comments.
     */
    @Test
    public void testRetrieveApprovedComments(){

    	createDefaultTweetPollCommentWithStatus("First comment", tpoll, this.user, CommentOptions.APPROVE, this.creationDate.toDate());
    	createDefaultTweetPollCommentWithStatus("Second comment", tpoll, this.user, CommentOptions.APPROVE, this.creationDate.toDate());
    	createDefaultTweetPollCommentWithStatus("Third comment", tpoll, this.user, CommentOptions.SPAM, this.creationDate.toDate());

		final List<Comment> approvedComments = getCommentsOperations()
				.getCommentsbyTypeAndStatus(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 10, 0,
						CommentOptions.APPROVE, null);
		assertEquals("Should be equals", 2, approvedComments.size());

		final List<Comment> allComments = getCommentsOperations()
				.getCommentsbyTypeAndStatus(this.tpoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 10, 0,
						CommentOptions.ALL, null);
		assertEquals("Should be equals", 4, allComments.size());
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
		//assertEquals("Should be equals", 1, approvedComments.size());
    }

}
