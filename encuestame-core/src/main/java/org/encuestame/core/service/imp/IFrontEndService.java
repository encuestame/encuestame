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
package org.encuestame.core.service.imp;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.ProfileRatedTopBean;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.stats.HashTagRankingBean;

/**
 * Implementation for Front End Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:29:51 AM
 * @version $Id:$
 */
public interface IFrontEndService extends ServiceOperations {

    /**
     * Search Items By TweetPoll.
     * @param maxResults limit of results to return.
     * @return result of the search.
     * @throws EnMeSearchException search exception.
     */
     List<TweetPollBean> searchItemsByTweetPoll(
            final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request)
            throws EnMeSearchException;

     /**
      * Search items by poll.
      * @param period
      * @param maxResults
      * @return
      * @throws EnMeSearchException
      */
    List<PollBean> searchItemsByPoll(
             final String period,
             final Integer start,
             Integer maxResults)
             throws EnMeSearchException;

    /**
     * List Hash tags
     * @param maxResults
     * @param start
     * @param tagCriteria
     * @return
     */
    List<HashTagBean> getHashTags(
            Integer maxResults,
            final Integer start,
            final String tagCriteria);

    /**
     * Get hashTag item.
     * @param tagName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    HashTag getHashTagItem(final String tagName) throws EnMeNoResultsFoundException;

    /**
     * Get TweetPolls by hashTag id.
     * @param hashTagId
     * @param limit
     * @param request
     * @return
     */
    List<TweetPollBean> getTweetPollsbyHashTagName(final String tagName, final Integer initResults,
            final Integer limit, final String filter,
            final HttpServletRequest request);

    /**
     * Get frontEnd items.
     * @param period
     * @param start
     * @param maxResults
     * @param request
     * @return
     * @throws EnMeSearchException
     */
    List<HomeBean> getFrontEndItems(final String period, final Integer start,
            Integer maxResults, final HttpServletRequest request)
            throws EnMeSearchException;

    /**
     * Check previous item hit.
     * @param ipAddress
     * @param id
     * @param searchHitby
     * @return
     */
    Boolean checkPreviousHit(final String ipAddress, final Long id, final TypeSearchResult searchHitby);

    /**
     * Register hit.
     * @param tweetPoll
     * @param poll
     * @param survey
     * @param tag
     * @param ip
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Boolean registerHit(final TweetPoll tweetPoll, final Poll poll, final Survey survey, final HashTag tag,
            final String ip) throws EnMeNoResultsFoundException;

    /**
     * Register access rate.
     * @param type
     * @param itemId
     * @param ipAddress
     * @param rate
     * @return
     * @throws EnMeExpcetion
     */
    AccessRate registerAccessRate(final TypeSearchResult type,
            final Long itemId, final String ipAddress, final Boolean rate)
            throws EnMeExpcetion;

    /**
     * Search items by survey.
     * @param period
     * @param start
     * @param maxResults
     * @param request
     * @return
     * @throws EnMeSearchException
     */
    List<SurveyBean> searchItemsBySurvey(final String period,
            final Integer start, Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException;

    /**
     * Process items to calculate relevance on home page.
     * @param tweetPollList
     * @param pollList
     * @param surveyList
     * @param datebefore
     * @param todayDate
     */
    void processItemstoCalculateRelevance(final List<TweetPoll> tweetPollList, final List<Poll> pollList, final List<Survey> surveyList,
            final Calendar datebefore, final Calendar todayDate);

    /**
     * Get the list with the users rated top.
     * @param status
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<ProfileRatedTopBean> getTopRatedProfile(final Boolean status)
    throws EnMeNoResultsFoundException;


    /**
    * Return last publications by {@link HashTag}.
    * @param hashTag {@link HashTag}
    * @param keyword keyword if not null, the search should be by keyword.
    * @param limit limit of items
    * @param filter order by
    * @param request {@link HttpServletRequest}.
    * @return
   **/
       List<HomeBean> searchLastPublicationsbyHashTag(
               final HashTag hashTag, final String keyword, final Integer initResults, final Integer limit,
               final String filter, final HttpServletRequest request);

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
    Long getTotalUsageByHashTag(final String tagName, final Integer initResults,
            final Integer maxResults, final TypeSearchResult filter);

    /**
    *
    * @param hash
    * @return
    */
   List<LinksSocialBean> getHashTagLinks(final HashTag hash);
   
   /**
    * Get total social network links published by {@link TweetPoll}, {@link Poll} and {@link Survey}.
    * @param tagName
    * @param initResults
    * @param maxResults 
    * @return
    */
   Long getSocialNetworkUseByHashTag(final String tagName, final Integer initResults, final Integer maxResults); 
   
   /**
    * Get total hash tag hits by tag name.
    * @param tagName
    * @param filterBy
    * @return
    */
	Long getHashTagHitsbyName(final String tagName, final TypeSearchResult filterBy);
	
	/**
	 * Get hashTag ranking.
	 * @param tagName
	 * @return 
	 */
	List<HashTagRankingBean> getHashTagRanking(final String tagName);
}