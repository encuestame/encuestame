/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;

/**
 * Implementation for FrontEnd Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 16, 2010 10:53:48 PM
 * @version $Id:$
 */
public interface IFrontEndDao extends IBaseDao{

    /**
     * Get TweetPoll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<TweetPoll> getTweetPollFrontEndLast30Days(final Integer start, final Integer maxResults);

    /**
     * Get TweetPoll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<TweetPoll> getTweetPollFrontEndLast7Days(final Integer start, final Integer maxResults);

    /**
     * Get TweetPoll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<TweetPoll> getTweetPollFrontEndLast24(final Integer start, final Integer maxResults);

    /**
     * Get Poll Last 24 Hours.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<Poll> getPollFrontEndLast24(final Integer start, final Integer maxResults);

    /**
     * Get Poll Last 7 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<Poll> getPollFrontEndLast7Days(final Integer start, final Integer maxResults);

    /**
     * Get Poll Last 30 Days
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<Poll> getPollFrontEndLast30Days(final Integer start, final Integer maxResults);

    /**
     * Get Poll on All Time.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<Poll> getPollFrontEndAllTime(final Integer start, final Integer maxResults);

    /**
     * Get TweetPoll all time.
     * @param maxResults max results
     * @return list of tweetPoll.
     */
    List<TweetPoll> getTweetPollFrontEndAllTime(final Integer start, final Integer maxResults);

    /**
     * Get hashTag by ip address.
     * @param ipAddress
     * @return
     */
    List<HashTagHits> getHashTagsHitByIp(final String ipAddress);

}
