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

import java.util.Date;

import org.encuestame.core.persistence.pojo.SecUserSecondary;
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
    private Long questionId;
    private Boolean reportResults;
    private Boolean closeNotification;
    private Long tweetId;
    private Boolean resultNotification;
    private Boolean publishPoll;
    private Date startDateTweet;
    private Date endDateTweet;
    private Date publicationDateTweet;
    private Boolean completed;
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
     * @param userId
     *            the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the closeNotification
     */
    public Boolean getCloseNotification() {
        return closeNotification;
    }

    /**
     * @param closeNotification
     *            the closeNotification to set
     */
    public void setCloseNotification(final Boolean closeNotification) {
        this.closeNotification = closeNotification;
    }

    /**
     * @return the tweetId
     */
    public Long getTweetId() {
        return tweetId;
    }

    /**
     * @param tweetId
     *            the tweetId to set
     */
    public void setTweetId(final Long tweetId) {
        this.tweetId = tweetId;
    }

    /**
     * @return the resultNotification
     */
    public Boolean getResultNotification() {
        return resultNotification;
    }

    /**
     * @param resultNotification
     *            the resultNotification to set
     */
    public void setResultNotification(final Boolean resultNotification) {
        this.resultNotification = resultNotification;
    }

    /**
     * @return the publishPoll
     */
    public Boolean getPublishPoll() {
        return publishPoll;
    }

    /**
     * @param publishPoll
     *            the publishPoll to set
     */
    public void setPublishPoll(final Boolean publishPoll) {
        this.publishPoll = publishPoll;
    }

    /**
     * @return the startDateTweet
     */
    public Date getStartDateTweet() {
        return startDateTweet;
    }

    /**
     * @param startDateTweet
     *            the startDateTweet to set
     */
    public void setStartDateTweet(final Date startDateTweet) {
        this.startDateTweet = startDateTweet;
    }

    /**
     * @return the endDateTweet
     */
    public Date getEndDateTweet() {
        return endDateTweet;
    }

    /**
     * @param endDateTweet
     *            the endDateTweet to set
     */
    public void setEndDateTweet(final Date endDateTweet) {
        this.endDateTweet = endDateTweet;
    }

    /**
     * @return the publicationDateTweet
     */
    public Date getPublicationDateTweet() {
        return publicationDateTweet;
    }

    /**
     * @param publicationDateTweet
     *            the publicationDateTweet to set
     */
    public void setPublicationDateTweet(final Date publicationDateTweet) {
        this.publicationDateTweet = publicationDateTweet;
    }

    /**
     * @return the completed
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * @param completed
     *            the completed to set
     */
    public void setCompleted(final Boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the questionId
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

}
