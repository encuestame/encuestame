/*
 ************************************************************************************
 * Copyright (C) 2001-2013 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.cron;

import org.encuestame.core.service.imp.ICommentService;

/**
 * Remove Spam comments Job.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Jun 05, 2013
 */
public class RemoveSpamCommentsJob {

	/** **/
	private ICommentService commentService;

	/**
	 * @return the commentService
	 */
	public ICommentService getCommentService() {
		return commentService;
	}

	/**
	 * @param commentService the commentService to set
	 */
	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}

}
