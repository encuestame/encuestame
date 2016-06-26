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


import org.encuestame.business.service.CommentService;
import org.encuestame.core.service.ICommentService;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.test.business.config.AbstractSpringSecurityContext;
import org.encuestame.util.exception.EnMeException;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.util.exception.EnmeNotAllowedException;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.CommentsSocialOptions;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.CommentBean;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    private Poll poll;

    private Survey survey;

    private DateTime creationDate = new DateTime();


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

        this.poll = createDefaultPoll(question, getSpringSecurityLoggedUserAccount());

        this.survey = createDefaultSurvey(getSpringSecurityLoggedUserAccount().getAccount(), "My First survey", creationDate.toDate());
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
        // comment on Tweetpoll
		createDefaultTweetPollCommentWithStatus("dumb tweetPoll question",
				tweetPoll, getSpringSecurityLoggedUserAccount(),
				CommentOptions.APPROVE, this.creationDate.toDate());
		final List<CommentOptions> opt = new ArrayList<CommentOptions>();
		opt.add(CommentOptions.ALL);
        createDefaultTweetPollComment("dumb tweetPoll question", tweetPoll, getSpringSecurityLoggedUserAccount());

        final List<CommentBean> commentsbyUser = getCommentsOperationsService().getCommentsbyUser(this.MAX_RESULTS, this.START, opt);
        assertEquals("Should be equals", 6, commentsbyUser.size());

    }

    /**
     * Test get comments by keyword.
     * @throws EnMeException
     */
    @Test
    public void testGetCommentsbyKeyword() throws EnMeException{
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
     * @throws EnmeNotAllowedException
     */
    @Test
    public void testCreateComment() throws EnMeNoResultsFoundException, EnmeNotAllowedException{
        final CommentBean commentBean = createCommentBean("totally Agree", new Date(),
				getSpringSecurityLoggedUserAccount().getUid(), this.tweetPoll.getTweetPollId(), null);
        final Comment comment = getCommentsOperationsService().createComment(commentBean);
        assertNotNull(comment);
    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
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
                .getTopRatedComments(CommentsSocialOptions.LIKE,
						this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 7, topList.size());
    }

	/**
	 * Test to retrieve all comments by type: {@link TweetPoll} and {@link CommentOptions}
	 */
	@Test
	public void testTweetPollCommentsByStatusAndType() {
		final int totalCommentsApprove = 10;
		final int totalCommentsSpam = 5;

		for (int i = 0; i < totalCommentsApprove; i++) {
			createDefaultTweetPollCommentWithStatus("Comment" + i,
					this.tweetPoll, getSpringSecurityLoggedUserAccount(),
					CommentOptions.APPROVE, this.creationDate.toDate());
		}

		for (int i = 0; i < totalCommentsSpam; i++) {
			createDefaultTweetPollCommentWithStatus(
						"Comment" + i, this.tweetPoll, getSpringSecurityLoggedUserAccount(),
						CommentOptions.SPAM, this.creationDate.toDate());
		}

		final List<CommentBean> tweetPollCommentsApproved = getCommentsOperationsService()
				.retrieveCommentsByTypeAndStatus(
						this.tweetPoll.getTweetPollId(),
						 TypeSearchResult.TWEETPOLL, 20, 0,
						 CommentOptions.APPROVE, null);
		assertEquals("Should be equals", 10, tweetPollCommentsApproved.size());

		final List<CommentBean> tweetPollCommentsSpam = getCommentsOperationsService()
				.retrieveCommentsByTypeAndStatus(
						this.tweetPoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 20, 0,
						CommentOptions.SPAM, null);
		assertEquals("Should be equals", 5, tweetPollCommentsSpam.size());

		final List<CommentBean> alltweetPollComments = getCommentsOperationsService()
				.retrieveCommentsByTypeAndStatus(
						this.tweetPoll.getTweetPollId(),
						TypeSearchResult.TWEETPOLL, 20, 0,
						CommentOptions.ALL, null);
		assertEquals("Should be equals", 19, alltweetPollComments.size());

	}

	/**
	 *  Test to retrieve all comments by type: {@link Poll} and {@link CommentOptions}
	 */
	@Test
	public void testPollCommentsByStatusAndType() {
		final int totalCommentsSpam = 5;
		//System.out.println("fecha --> " + this.creationDate.toDate());
		for (int i = 0; i < totalCommentsSpam; i++) {
			createDefaultPollCommentWithStatus("Comment" + i, this.poll,
					getSpringSecurityLoggedUserAccount(), CommentOptions.SPAM,
					this.creationDate.toDate());
		}

		final List<CommentBean> tweetPollCommentsApproved = getCommentsOperationsService()
				.retrieveCommentsByTypeAndStatus(this.poll.getPollId(),
						TypeSearchResult.POLL, 20, 0, CommentOptions.SPAM,
						SearchPeriods.TWENTYFOURHOURS);

		assertEquals("Should be equals", 5, tweetPollCommentsApproved.size());
	}

	/**
	 * Retrieve total {@link Comment}
	 */
	@Test
	public void testTotalCommentsByType(){
		this.createPollComments(); // Create Poll Comments.
		this.createTweetPollComments(); // Create TweetPoll Comments.
		final long totalTweetPollComments = getCommentsOperations()
				.getTotalCommentsbyItem(tweetPoll.getTweetPollId(), TypeSearchResult.TWEETPOLL,
						CommentOptions.ALL, SearchPeriods.ALLTIME);
		assertEquals("Should be equals", 13, totalTweetPollComments);

		final long totalTweetPollSpamComments = getCommentsOperations()
				.getTotalCommentsbyItem(tweetPoll.getTweetPollId(), TypeSearchResult.TWEETPOLL,
						CommentOptions.SPAM, SearchPeriods.ALLTIME);
		assertEquals("Should be equals", 3, totalTweetPollSpamComments);

		// Comments POLL

		final long totalPollComments = getCommentsOperations()
				.getTotalCommentsbyItem(poll.getPollId(),
						TypeSearchResult.POLL, CommentOptions.ALL,
						SearchPeriods.ALLTIME);
		assertEquals("Should be equals", 10, totalPollComments);

		final long totalPollRestrictedComments = getCommentsOperations()
				.getTotalCommentsbyItem(poll.getPollId(),
						TypeSearchResult.POLL, CommentOptions.RESTRICT,
						SearchPeriods.ALLTIME);
		assertEquals("Should be equals", 3, totalPollRestrictedComments);
	}

	/**
	 * Retrieve Comments by Type and {@link SearchPeriods}.
	 */
	@Test
	public void testTotalPollCommentsByTypeAndDateRange(){
		this.createPollComments();
		final long totalComments7Days = getCommentsOperations().getTotalCommentsbyItem(
				this.poll.getPollId(), TypeSearchResult.POLL, CommentOptions.ALL,
				SearchPeriods.SEVENDAYS);
		assertEquals("Should be equals", 6, totalComments7Days);

		final long totalCommentsSpam24Hours = getCommentsOperations().getTotalCommentsbyItem(
				this.poll.getPollId(), TypeSearchResult.POLL, CommentOptions.SPAM,
				SearchPeriods.TWENTYFOURHOURS);
		assertEquals("Should be equals", 1, totalCommentsSpam24Hours);

		final long totalAllComments24Hours = getCommentsOperations().getTotalCommentsbyItem(
				this.poll.getPollId(), TypeSearchResult.POLL, CommentOptions.ALL,
				SearchPeriods.TWENTYFOURHOURS);
		assertEquals("Should be equals", 3, totalAllComments24Hours);

		final long totalModerateComments30Days = getCommentsOperations().getTotalCommentsbyItem(
				this.poll.getPollId(), TypeSearchResult.POLL, CommentOptions.MODERATE,
				SearchPeriods.THIRTYDAYS);
		assertEquals("Should be equals", 2, totalModerateComments30Days);

		final long totalRestrictCommentsOneYear = getCommentsOperations().getTotalCommentsbyItem(
				this.poll.getPollId(), TypeSearchResult.POLL, CommentOptions.RESTRICT,
				SearchPeriods.ONEYEAR);
		assertEquals("Should be equals", 2, totalRestrictCommentsOneYear);

		final long totalAllComments = getCommentsOperations().getTotalCommentsbyItem(
				this.poll.getPollId(), TypeSearchResult.POLL, CommentOptions.ALL,
				SearchPeriods.ALLTIME);
		assertEquals("Should be equals", 10, totalAllComments);
	}


	/**
	 * Create Poll {@link Comment}
	 */
	private void createPollComments(){

		createDefaultPollCommentWithStatus("my12", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.APPROVE,
				creationDate.minusHours(5).toDate());

				createDefaultPollCommentWithStatus("my13", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.APPROVE,
				creationDate.minusHours(3).toDate());

		createDefaultPollCommentWithStatus("my131", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.SPAM,
				creationDate.minusHours(2).toDate());

		createDefaultPollCommentWithStatus("my131", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.SPAM,
				creationDate.minusHours(25).toDate());

		createDefaultPollCommentWithStatus("my1", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.APPROVE,
				creationDate.minusDays(3).toDate());

		createDefaultPollCommentWithStatus("my14", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.MODERATE,
				creationDate.minusDays(4).toDate());

		createDefaultPollCommentWithStatus("my15", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.MODERATE,
				creationDate.minusDays(10).toDate());

		createDefaultPollCommentWithStatus("my16", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.RESTRICT,
				creationDate.minusDays(20).toDate());

		createDefaultPollCommentWithStatus("my168", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.RESTRICT,
				creationDate.minusMonths(2).toDate());

		createDefaultPollCommentWithStatus("my168", this.poll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.RESTRICT,
				creationDate.minusYears(2).toDate());

	}

	/**
	 * Create {@link TweetPoll} {@link Comment}
	 */
	private void createTweetPollComments(){
		createDefaultTweetPollCommentWithStatus("tpoll1 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.MODERATE,
				creationDate.minusHours(2).toDate());

		createDefaultTweetPollCommentWithStatus("tpoll12 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.SPAM,
				creationDate.minusHours(10).toDate());

		createDefaultTweetPollCommentWithStatus("tpoll123 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.SPAM,
				creationDate.toDate());

		createDefaultTweetPollCommentWithStatus("tpoll124 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.RESTRICT,
				creationDate.minusDays(2).toDate());

		createDefaultTweetPollCommentWithStatus("tpoll124 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.MODERATE,
				creationDate.minusDays(5).toDate());

		createDefaultTweetPollCommentWithStatus("tpoll124 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.SPAM,
				creationDate.minusDays(7).toDate());


		createDefaultTweetPollCommentWithStatus("tpoll1245 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.MODERATE,
				creationDate.minusDays(17).toDate());


		createDefaultTweetPollCommentWithStatus("tpoll12455 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.APPROVE,
				creationDate.minusMonths(6).toDate());


		createDefaultTweetPollCommentWithStatus("tpoll1245567 ", this.tweetPoll,
				getSpringSecurityLoggedUserAccount(), CommentOptions.RESTRICT,
				creationDate.minusYears(2).toDate());

	}

	/**
	 * Retrieve {@link TweetPoll} Comments by Type and {@link SearchPeriods}
	 */
	@Test
	public void testTotalTweetPollCommentsByTypeAndDateRange(){
		this.createTweetPollComments();
		final long totalCommentsSpam24Hours = getCommentsOperations().getTotalCommentsbyItem(
				this.tweetPoll.getTweetPollId(), TypeSearchResult.TWEETPOLL, CommentOptions.SPAM,
				SearchPeriods.TWENTYFOURHOURS);
		assertEquals("Should be equals", 2, totalCommentsSpam24Hours);

		final long totalComments7Days = getCommentsOperations().getTotalCommentsbyItem(
				this.tweetPoll.getTweetPollId(), TypeSearchResult.TWEETPOLL, CommentOptions.ALL,
				SearchPeriods.SEVENDAYS);
		assertEquals("Should be equals", 9, totalComments7Days);
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

	/**
	 * Test Retrieve Comments
	 */
	@Test
	public void testRetrieveAllTweetPollComments() throws EnMeException {
		final List<Comment> comments = getCommentsOperationsService().getComments(TypeSearchResult.TWEETPOLL, this.tweetPoll.getTweetPollId(), 10, 0);
 	}
}
