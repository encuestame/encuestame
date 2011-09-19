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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.TypeSearchResult;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;

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
    List<TweetPollBean> getTweetPollsbyHashTagId(final Long hashTagId, final Integer limit, final String filter, final HttpServletRequest request);

    /**
     * Check previous hashTag hit.
     * @param ipAddress
     * @return
     */
    Boolean checkPreviousHashTagHit(final String ipAddress);

    /**
     * Register hashTag hits.
     * @param tag
     * @param ipAddress
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    Boolean registerHashTagHit(final HashTag tag, final String ip) throws EnMeNoResultsFoundException;

    /**
     * Get frontEnd items.
     * @param period
     * @param start
     * @param maxResults
     * @param request
     * @return
     * @throws EnMeSearchException
     */
    List<HomeBean> getFrontEndItems(final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException;

    /**
     * Check previous item hit.
     * @param ipAddress
     * @param id
     * @param searchHitby
     * @return
     */
    Boolean checkPreviousHit(final String ipAddress, final Long id, final String searchHitby);

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
    public AccessRate registerAccessRate(final TypeSearchResult type,
            final Long itemId, final String ipAddress, final Boolean rate)
            throws EnMeExpcetion;

}
