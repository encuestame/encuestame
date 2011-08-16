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
package org.encuestame.persistence.domain;

/**
 * Social Options Enumeration
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public enum CommentsSocialOptions {

	/** **/
	LIKE_VOTE,

	/** **/
	DISLIKE_VOTE,

	CommentsSocialOptions(){
	};

	/**
	 * To string.
	 */
	public String toString() {
		String option = "";
		if (this == LIKE_VOTE) { option = "LIKEVOTE"; }
		else if (this == DISLIKE_VOTE) { option = "DISLIKEVOTE"; }
	return option;
	}

	/**
	 * Get comments social options.
	 * @param option
	 * @return
	 */
	public static CommentsSocialOptions getCommentsSocialOptions(final String option) {
	    if (null == option) { return null; }
	     else if (option.equalsIgnoreCase("LIKEVOTE")) { return  LIKE_VOTE; }
	     else if (option.equalsIgnoreCase("DISLIKEVOTE")) { return  DISLIKE_VOTE; }
	     else
	return null;
	}

}