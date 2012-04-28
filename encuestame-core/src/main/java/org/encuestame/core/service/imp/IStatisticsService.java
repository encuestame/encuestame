/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service.imp;

import java.util.List;

import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.encuestame.utils.web.stats.ItemStatDetail;

/**
 * Interface to Statistics Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2012
 * @version $Id$
 */
public interface IStatisticsService extends ServiceOperations{

	/**
	 * Get total usage items by hashTag and Date Range
	 * @param hashTagName
	 * @param period
	 * @param startResults
	 * @param maxResults
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	List<HashTagDetailStats> getTotalUsagebyHashTagAndDateRange(final String hashTagName, final Integer period, final Integer startResults, final Integer maxResults) throws EnMeNoResultsFoundException;
	
	/**
	 * 
	 * @param tagName
	 * @param initResults
	 * @param maxResults
	 * @param filter
	 * @param period
	 * @return
	 */
	List<HashTagDetailStats> getTweetPollSocialNetworkLinksbyTagAndDateRange(
			final String tagName, final Integer initResults,
			final Integer maxResults, final TypeSearchResult filter, final Integer period);
	
	
	/**
	 * Get total votes by hashTag usage and Tweetpolls.
	 * @param tagName
	 * @param period
	 * @return
	 */
	List<HashTagDetailStats> getTotalVotesbyHashTagUsageAndDateRange(final String tagName, final Integer period);
	
}
