/**
 *
 */
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.hibernate.HibernateException;

/**
 * @author dmorales
 *
 */
public interface CommentsOperations extends IBaseDao {

	/**
	 * Get list comments by keyword.
	 * @param keyword
	 * @param maxResults
	 * @param excludes
	 * @return
	 */
	List<Comment> getListCommentsByKeyword(
			final String keyword,
	        final Integer maxResults,
	        final Long[] excludes);

	/**
	 * Get comment by id.
	 * @param commentId
	 * @return
	 * @throws HibernateException
	 */
	Comment getCommentById(final Long commentId) throws HibernateException;

	/**
	 * Get comments by id and user.
	 * @param commentId
	 * @param userAcc
	 * @return
	 * @throws HibernateException
	 */
	Comment getCommentByIdandUser(final Long commentId, final UserAccount userAcc) throws HibernateException;

	/**
	 * Get comments by user ordered by creation date.
	 * @param userAcc
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Comment> getCommentsbyUser(final UserAccount userAcc, final Integer maxResults, final Integer start);

	/**
	 * Get comments by TweetPoll
	 * @param tpoll
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Comment> getCommentsbyTweetPoll(final TweetPoll tpoll, final Integer maxResults, final Integer start);
}
