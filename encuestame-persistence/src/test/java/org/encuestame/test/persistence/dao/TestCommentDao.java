/**
 *
 */
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;

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

    @Before
    public void initData(){
    	final UserAccount user = createUserAccount("isabelle", createAccount());
    	final Question question = createQuestion("When did you go last weekend", "");
    	final TweetPoll tpoll = createPublishedTweetPoll(user.getAccount(), question);
        this.comment = createDefaultTweetPollComment("I was working", tpoll, user);
    }

    @Test
	public void testGetListCommentsbyKeyword(){
    	final String keyword = "working";
    	// final List<Comment> commentList = getCommentsOperations().getListCommentsByKeyword(keyword, 5, null);
        // assertEquals("Should be equals", 1, commentList.size());
	}

}
