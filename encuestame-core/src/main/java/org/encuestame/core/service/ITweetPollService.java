/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollResult;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

import twitter4j.Status;

/**
 * Tweet Poll Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 02, 2010
 * @version $Id: $
 */
public interface ITweetPollService extends IMasterSurveyService{

     /**
     * Public Tweet Poll.
     * @param tweetText tweet text
     * @param username username
     * @param password  password
     * @return status of tweet
     * @throws EnMeExpcetion exception
     */
    Status publicTweetPoll(final String tweetText, final String username, final String password) throws EnMeExpcetion;

    /**
     * Create Tweet Poll.
     * @param tweetPollBean tweet poll bean.
     * @throws EnMeExpcetion exception
     */
    void createTweetPoll(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion;

    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @param url url
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    String generateTweetPollText(final UnitTweetPoll tweetPoll, final String url) throws EnMeExpcetion;

    /**
     * Save Tweet Id.
     * @param tweetPollBean {@link UnitTweetPoll}
     * @throws EnMeExpcetion exception
     */
    void saveTweetId(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion;

    /**
     * Get Tweet Path.
     * @return tweet
     */
    String getTweetPath();

    /**
     * @return the tweetPollDao
     */
    ITweetPoll getTweetPollDao();

    /**
     * Vote on TweetPoll.
     * @param pollSwitch {@link TweetPollSwitch}
     * @param ip ip
     */
    void tweetPollVote(final TweetPollSwitch pollSwitch, final String ip);

    /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollResult}
     */
    TweetPollResult validateTweetPollIP(final String ipVote, final TweetPoll tweetPoll);

    /**
     * Get Results By {@link TweetPoll}.
     * @param tweetPollId tweetPoll Id
     * @return list of {@link UnitTweetPollResult}
     */
    List<UnitTweetPollResult> getResultsByTweetPollId(final Long tweetPollId);

    /**
     *
     * @param username username
     * @return
     */
    List<UnitTweetPoll> getTweetsPollsByUserName(final String username);


}
