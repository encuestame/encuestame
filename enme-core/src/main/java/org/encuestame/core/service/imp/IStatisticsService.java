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

import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
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
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<HashTagDetailStats> getTotalUsagebyHashTagAndDateRange(
            final String hashTagName,
            final SearchPeriods period,
            final HttpServletRequest request)
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
     * @param request
     * @return
     */
    List<HashTagDetailStats> getTotalVotesbyHashTagUsageAndDateRange(
            final String tagName,
            final SearchPeriods period,
            final HttpServletRequest request) throws EnMeSearchException;

    /**
     *
     * @param tagName
     * @param period
     * @param request
     * @return
     */
    List<HashTagDetailStats> getTotalSocialLinksbyHashTagUsageAndDateRange(
            final String tagName,
            final SearchPeriods period,
            final HttpServletRequest request)
            throws EnMeSearchException;

    /**
     *
     * @param hashTagName
     * @param period
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeSearchException
     */
    List<HashTagDetailStats> getTotalHitsUsagebyHashTagAndDateRange(
            final String hashTagName, final SearchPeriods period, final HttpServletRequest request)
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
     * @throws EnMeNoResultsFoundException
     */
    HashTagDetailStats getTotalUsageByHashTag(final String tagName,
            final Integer initResults, final Integer maxResults,
            final TypeSearchResult filter, final HttpServletRequest request,
            final SearchPeriods periods)
            throws EnMeNoResultsFoundException;

    /**
     * Get total social network links published by {@link TweetPoll}, {@link Poll} and {@link Survey}.
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param request
     * @return
     */
    HashTagDetailStats getSocialNetworkUseByHashTag(final String tagName,
            final Integer initResults, final Integer maxResults,
            final HttpServletRequest request,
            final SearchPeriods searchPeriods);

    /**
     * Get total hash tag hits by tag name.
     * @param tagName
     * @param filterBy
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     */
    HashTagDetailStats getHashTagHitsbyName(final String tagName,
            final TypeSearchResult filterBy,
            final HttpServletRequest request,
            final SearchPeriods periods)
            throws EnMeNoResultsFoundException;

    /**
     * Get Total usage by hashtags on tweepolls voted.
     * @param tagName
     * @param initResults
     * @param max
     * @param request
     * @return
     */
    HashTagDetailStats getHashTagUsedOnItemsVoted(final String tagName,
            final Integer initResults, final Integer max,
            final HttpServletRequest request,
            final SearchPeriods periods);
    /**
     *
     * @param hashTagName
     * @param period
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeSearchException
     */
    List<HashTagDetailStats> getTotalUsagebyHashtagAndDateRangeGraph(
                final String hashTagName,
                final SearchPeriods period,
                final HttpServletRequest request) throws EnMeNoResultsFoundException, EnMeSearchException;


	List<HashTagDetailStats> compareHashtagListGraph(
			final List<ItemStatDetail> itemList, final SearchPeriods period,
			final HttpServletRequest request) throws EnMeSearchException;

	/**
	 * Retrieve all social links by range.
	 * @param tagName
	 * @param period
	 * @param request
	 * @return
	 * @throws EnMeSearchException
	 */
	List<HashTagDetailStats> getTotalSocialLinksbyHashTagUsageAndDateRangeGraph(
	           final String tagName, final SearchPeriods period, final HttpServletRequest request)
	           throws EnMeSearchException;

	/**
	 *
	 * @param tagName
	 * @param period
	 * @param request
	 * @return
	 * @throws EnMeSearchException
	 */
	List<HashTagDetailStats> getTotalVotesbyHashTagUsageAndDateRangeGraph(
            final String tagName, final SearchPeriods period,
            final HttpServletRequest request) throws EnMeSearchException;

	/**
	 *
	 * @param hashTagName
	 * @param period
	 * @param request
	 * @return
	 * @throws EnMeSearchException
	 */
	List<HashTagDetailStats> getTotalHitsUsagebyHashTagAndDateRangeGraph(
			final String hashTagName, final SearchPeriods period,
			final HttpServletRequest request) throws EnMeNoResultsFoundException, EnMeSearchException ;
}
