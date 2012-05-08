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
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException; 
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.stats.HashTagDetailStats;

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
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	List<HashTagDetailStats> getTotalUsagebyHashTagAndDateRange(
			final String hashTagName, final Integer period)
			throws EnMeNoResultsFoundException, EnMeSearchException;
	
	/**
	 * 
	 * @param tagName
	 * @param initResults
	 * @param maxResults
	 * @param filter
	 * @param period
	 * @return
	 */
	/*List<HashTagDetailStats> getTweetPollSocialNetworkLinksbyTagAndDateRange(
			final String tagName, final Integer initResults,
			final Integer maxResults, final TypeSearchResult filter, final Integer period);*/
	
	
	/**
	 * Get total votes by hashTag usage and Tweetpolls.
	 * @param tagName
	 * @param period
	 * @return
	 */
	List<HashTagDetailStats> getTotalVotesbyHashTagUsageAndDateRange(final String tagName, final String period) throws EnMeSearchException;
	
	/**
	 * 
	 * @param tagName
	 * @param period
	 * @return
	 */
	List<HashTagDetailStats> getTotalSocialLinksbyHashTagUsageAndDateRange(
			final String tagName, final String period)
			throws EnMeSearchException;
	
	/**
	 * 
	 * @param hashTagName
	 * @param period
	 * @param startResults
	 * @param maxResults
	 * @return
	 * @throws EnMeNoResultsFoundException
	 * @throws EnMeSearchException
	 */
	List<HashTagDetailStats> getTotalHitsUsagebyHashTagAndDateRange(
			final String hashTagName, final Integer period)
			throws EnMeNoResultsFoundException, EnMeSearchException;
	

    /**
     * Get total usage {@link TweetPoll}, {@link Poll} or {@link Survey} by
     * HashTag.
     *
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
	HashTagDetailStats getTotalUsageByHashTag(final String tagName, final Integer initResults,
            final Integer maxResults, final TypeSearchResult filter);
	
	/**
	* Get total social network links published by {@link TweetPoll}, {@link Poll} and {@link Survey}.
	* @param tagName
	* @param initResults
	* @param maxResults 
	* @return
	*/
	HashTagDetailStats getSocialNetworkUseByHashTag(final String tagName, final Integer initResults, final Integer maxResults);
	
	/**
     * Get total hash tag hits by tag name.
	 * @param tagName
	 * @param filterBy
	 * @return
	 */
	HashTagDetailStats getHashTagHitsbyName(final String tagName, final TypeSearchResult filterBy);
	
	/**
	 * Get Total usage by hashtags on tweepolls voted.
	 * @param tagName
	 * @param initResults
	 * @param max
	 * @return
	 */
	HashTagDetailStats getHashTagUsedOnItemsVoted(final String tagName, final Integer initResults, final Integer max);
		
}
