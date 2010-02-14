/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.web.beans.survey.tweetpoll;

import java.util.List;

import org.encuestame.web.beans.survey.UnitAnswersBean;
import org.encuestame.web.beans.survey.UnitQuestionBean;

/**
 * TweetPoll Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 14, 2010 10:00:58 AM
 * @version $Id: $
 */
public class UnitTweetPoll {

    private Long id;
    private UnitQuestionBean tweetPoll;
    private Boolean publishTweet;
    private Boolean reportResults;
    private Long userId;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the tweetPoll
     */
    public UnitQuestionBean getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll
     *            the tweetPoll to set
     */
    public void setTweetPoll(final UnitQuestionBean tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
     * @return the publishTweet
     */
    public Boolean getPublishTweet() {
        return publishTweet;
    }

    /**
     * @param publishTweet
     *            the publishTweet to set
     */
    public void setPublishTweet(final Boolean publishTweet) {
        this.publishTweet = publishTweet;
    }

    /**
     * @return the reportResults
     */
    public Boolean getReportResults() {
        return reportResults;
    }

    /**
     * @param reportResults
     *            the reportResults to set
     */
    public void setReportResults(final Boolean reportResults) {
        this.reportResults = reportResults;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }
}
