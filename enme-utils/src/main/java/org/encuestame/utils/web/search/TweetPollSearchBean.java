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
package org.encuestame.utils.web.search;


/**
 * HashTag Controller.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since December 4, 2012
 */
public class TweetPollSearchBean extends Search{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -3143051300650851973L;


	/** **/
	private Boolean isPublished = Boolean.TRUE;



	/**
	 * @return the isPublished
	 */
	public Boolean getIsPublished() {
		return isPublished;
	}

	/**
	 * @param isPublished the isPublished to set
	 */
	public void setIsPublished(final Boolean isPublished) {
		this.isPublished = isPublished;
	}

}
