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

package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * TweetPoll Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 14, 2010 10:00:58 AM
 * @version $Id$
 */
public class UnitTweetPoll implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5248987840986024954L;
    private Long id;
    private UnitQuestionBean questionBean = new UnitQuestionBean();
    private Boolean closeNotification;
    private Long tweetId;
    private Boolean resultNotification;
    private Boolean publishPoll;
    private Date scheduleDate;
    private Boolean allowLiveResults;
    private Boolean schedule;
    private Date publicationDateTweet;
    private Boolean completed;
    private Long userId;
    private String tweetUrl;
    private String twitterUserAcoount;

    private static final  String TWITTER_STATUS_URL = "http://www.twitter.com/";

    private List<ResumeResultTweetPoll> results = new ArrayList<ResumeResultTweetPoll>();

    /**
     * Constructor.
     */
    public UnitTweetPoll() {
    }

    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public final Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public final void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the closeNotification
     */
    public final Boolean getCloseNotification() {
        return closeNotification;
    }

    /**
     * @param closeNotification
     *            the closeNotification to set
     */
    public final void setCloseNotification(final Boolean closeNotification) {
        this.closeNotification = closeNotification;
    }

    /**
     * @return the tweetId
     */
    public final Long getTweetId() {
        return tweetId;
    }

    /**
     * @param tweetId
     *            the tweetId to set
     */
    public final void setTweetId(final Long tweetId) {
        this.tweetId = tweetId;
    }

    /**
     * @return the resultNotification
     */
    public final Boolean getResultNotification() {
        return resultNotification;
    }

    /**
     * @param resultNotification
     *            the resultNotification to set
     */
    public final void setResultNotification(final Boolean resultNotification) {
        this.resultNotification = resultNotification;
    }

    /**
     * @return the publishPoll
     */
    public final Boolean getPublishPoll() {
        return publishPoll;
    }

    /**
     * @param publishPoll
     *            the publishPoll to set
     */
    public final void setPublishPoll(final Boolean publishPoll) {
        this.publishPoll = publishPoll;
    }

    /**
     * @return the publicationDateTweet
     */
    public final Date getPublicationDateTweet() {
        return publicationDateTweet;
    }

    /**
     * @param publicationDateTweet
     *            the publicationDateTweet to set
     */
    public final void setPublicationDateTweet(final Date publicationDateTweet) {
        this.publicationDateTweet = publicationDateTweet;
    }

    /**
     * @return the completed
     */
    public final Boolean getCompleted() {
        return completed;
    }

    /**
     * @param completed
     *            the completed to set
     */
    public final void setCompleted(final Boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the questionBean
     */
    public final UnitQuestionBean getQuestionBean() {
        return questionBean;
    }

    /**
     * @param questionBean
     *            the questionBean to set
     */
    public final void setQuestionBean(final UnitQuestionBean questionBean) {
        this.questionBean = questionBean;
    }

    /**
     * @return the results
     */
    public final List<ResumeResultTweetPoll> getResults() {
        return results;
    }

    /**
     * @param results
     *            the results to set
     */
    public final void setResults(List<ResumeResultTweetPoll> results) {
        this.results = results;
    }

    /**
     * @return the tweetUrl
     */
    public final String getTweetUrl() {
        final StringBuffer tweetUrlString = new StringBuffer(
                this.TWITTER_STATUS_URL);
        tweetUrlString.append(getTwitterUserAcoount());
        tweetUrlString.append("/status/");
        tweetUrlString.append(getTweetId());
        this.tweetUrl = tweetUrlString.toString();
        return this.tweetUrl;
    }

    /**
     * @param tweetUrl
     *            the tweetUrl to set
     */
    public final void setTweetUrl(String tweetUrl) {
        this.tweetUrl = tweetUrl;
    }

    /**
     * @return the scheduleDate
     */
    public final Date getScheduleDate() {
        return scheduleDate;
    }

    /**
     * @param scheduleDate
     *            the scheduleDate to set
     */
    public final void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    /**
     * @return the allowLiveResults
     */
    public final Boolean getAllowLiveResults() {
        return allowLiveResults;
    }

    /**
     * @param allowLiveResults
     *            the allowLiveResults to set
     */
    public final void setAllowLiveResults(Boolean allowLiveResults) {
        this.allowLiveResults = allowLiveResults;
    }

    /**
     * @return the schedule
     */
    public final Boolean getSchedule() {
        return schedule;
    }

    /**
     * @param schedule
     *            the schedule to set
     */
    public final void setSchedule(Boolean schedule) {
        this.schedule = schedule;
    }

    /**
     * @return the twitterUserAcoount
     */
    public final String getTwitterUserAcoount() {
        return twitterUserAcoount;
    }

    /**
     * @param twitterUserAcoount the twitterUserAcoount to set
     */
    public final void setTwitterUserAccount(final String twitterUserAcoount) {
        this.twitterUserAcoount = twitterUserAcoount;
    }
}
