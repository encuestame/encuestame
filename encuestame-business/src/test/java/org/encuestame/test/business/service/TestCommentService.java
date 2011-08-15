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
package org.encuestame.test.business.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.encuestame.business.service.CommentService;
import org.encuestame.persistence.dao.CommentsOperations;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link CommentService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 14, 2011
 */
public class TestCommentService extends AbstractSpringSecurityContext {

	@Autowired
	private CommentsOperations commentsOperations;

	/** {@link Comment} **/
	private Comment comment;

	/** {@link Account} **/
	private Account account;

	/** {@link UserAccount} **/
	private UserAccount userAccount;

	  /** Max Results. **/
    private Integer MAX_RESULTS = 10;

    /** Start Results. **/
    private Integer START = 0;


	@Before
	public void initService(){
		this.account = createAccount();
		this.userAccount = createUserAccount("Diana", this.account);
		final Question question = createQuestion("Why the sky is blue?","html");
        final TweetPoll tweetPoll = createTweetPollPublicated(true, true, new Date(), userAccount, question);
		this.comment = createDefaultTweetPollComment("My first tweetPoll comment", tweetPoll, userAccount);
	}

	@Test
	public void testGetCommentbyId(){
		assertNotNull(this.comment);
		final Comment comment = getCommentsOperations().getCommentById(this.comment.getCommentId());
		System.out.println("Comment ID getted --> "+ comment.getCommentId());
		assertEquals("Should be equals", this.comment.getCommentId(), comment.getCommentId());
	}

	/**
	 * @return the commentsOperations
	 */
	public CommentsOperations getCommentsOperations() {
		return commentsOperations;
	}

	/**
	 * @param commentsOperations the commentsOperations to set
	 */
	public void setCommentsOperations(final CommentsOperations commentsOperations) {
		this.commentsOperations = commentsOperations;
	}
}
