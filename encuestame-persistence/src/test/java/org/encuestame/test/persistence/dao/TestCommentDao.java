/**
 *
 */
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dmorales
 *
 */
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

    @Before
    public void initData(){
    	this.user = createUserAccount("isabelle", createAccount());
    	this.question = createQuestion("When did you go last weekend", "");
    	this.tpoll = createPublishedTweetPoll(user.getAccount(), this.question);
        this.comment = createDefaultTweetPollComment("I was working", this.tpoll, user);
    }

    /**
     * Test get List comment by keyword.
     */
    @Test
	public void testgetListCommentsByKeyword(){
        assertNotNull(this.comment);
        flushIndexes();
    	final String keyword = "working";
        final List<Comment> commentList = getCommentsOperations().getListCommentsByKeyword(keyword,10 ,null);
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
    	final List<Comment> commentbyTweetPoll = getCommentsOperations().getCommentsbyTweetPoll(tpoll, this.MAX_RESULTS, this.START);
    	assertEquals("Should be equals", 2, commentbyTweetPoll.size());
    	for (Comment comment : commentbyTweetPoll) {
			// System.out.println("Comments ---> " + comment.getComment());
		}
    }
}
