/**
 *
 */
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.Comment;

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
}
