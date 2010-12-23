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
package org.encuestame.persistence.domain.survey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;

/**
 * TweetPoll Domain.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since February 13, 2009
 * @version $Id$
 */

@Entity
@Table(name = "tweetPoll")
public class TweetPoll {

    private Long tweetPollId;

    /** Close Notification. **/
    private Boolean closeNotification;

    /** Result Notification after close. **/
    private Boolean resultNotification;

    /** Allow Live Results. **/
    private Boolean allowLiveResults;

    /** Published TweetPoll.**/
    private Boolean publishTweetPoll;

    /** Scheduled TweetPoll. **/
    private Boolean scheduleTweetPoll;

    /** Scheduled Date. **/
    private Date scheduleDate;

    /** Create Date. **/
    private Date createDate;

    /** True to close poll. **/
    private Boolean completed = false;

    /** Required Captcha to Vote. **/
    private Boolean captcha = false;

    /** Limit Votes. **/
    private Integer limitVotes;

    /** If true, system display in left nav live results. **/
    private Boolean resumeLiveResults = false;

    /** {@link Account}. **/
    private Account tweetOwner;

    /**
     * Define which user create this tweetPoll.
     */
    private UserAccount editorOwner;

    /** {@link Question}. **/
    private Question question;

    /** Repeated Votes. **/
    private Boolean allowRepatedVotes;

    /** Hash Tags. **/
    private Set<HashTag> hashTags = new HashSet<HashTag>();

    /** Number votes for Survey and Poll.**/
     private Integer numbervotes;

     /** Number Hits or visits **/
     private Integer hits;

     /** TweetPoll Folder. **/
     private TweetPollFolder tweetPollFolder;

     /** Set Item as Favourite. **/
     private Boolean favourites = false;

    /**
     * @return the tweetPollId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweet_poll_id", unique = true, nullable = false)
    public Long getTweetPollId() {
        return tweetPollId;
    }

    /**
     * @param tweetPollId
     *            the tweetPollId to set
     */
    public void setTweetPollId(Long tweetPollId) {
        this.tweetPollId = tweetPollId;
    }

    /**
     * @return the closeNotification
     */
    @Column(name = "close_notification", nullable = true)
    public Boolean getCloseNotification() {
        return closeNotification;
    }

    /**
     * @param closeNotification
     *            the closeNotification to set
     */
    public void setCloseNotification(Boolean closeNotification) {
        this.closeNotification = closeNotification;
    }

    /**
     * @return the resultNotification
     */

    @Column(name = "result_notification", nullable = true)
    public Boolean getResultNotification() {
        return resultNotification;
    }

    /**
     * @param resultNotification
     *            the resultNotification to set
     */
    public void setResultNotification(Boolean resultNotification) {
        this.resultNotification = resultNotification;
    }

    /**
     * @return the completed
     */
    @Column(name = "completed", nullable = false)
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * @param completed
     *            the completed to set
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the tweetOwner
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public Account getTweetOwner() {
        return tweetOwner;
    }

    /**
     * @param tweetOwner
     *            the tweetOwner to set
     */
    public void setTweetOwner(final Account tweetOwner) {
        this.tweetOwner = tweetOwner;
    }

    /**
     * @return the question
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "qid", nullable = false)
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion(final Question question) {
        this.question = question;
    }

    /**
     * @return the publishTweetPoll
     */
    @Column(name = "publish", nullable = true)
    public Boolean getPublishTweetPoll() {
        return publishTweetPoll;
    }

    /**
     * @param publishTweetPoll
     *            the publishTweetPoll to set
     */
    public void setPublishTweetPoll(final Boolean publishTweetPoll) {
        this.publishTweetPoll = publishTweetPoll;
    }

    /**
     * @return the allowLiveResults
     */
    @Column(name = "allow_live_results", nullable = true)
    public Boolean getAllowLiveResults() {
        return allowLiveResults;
    }

    /**
     * @param allowLiveResults
     *            the allowLiveResults to set
     */
    public void setAllowLiveResults(Boolean allowLiveResults) {
        this.allowLiveResults = allowLiveResults;
    }

    /**
     * @return the scheduleTweetPoll
     */
    @Column(name = "schedule", nullable = true)
    public Boolean getScheduleTweetPoll() {
        return scheduleTweetPoll;
    }

    /**
     * @param scheduleTweetPoll
     *            the scheduleTweetPoll to set
     */
    public void setScheduleTweetPoll(Boolean scheduleTweetPoll) {
        this.scheduleTweetPoll = scheduleTweetPoll;
    }

    /**
     * @return the scheduleDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_date_tweet", nullable = true)
    public Date getScheduleDate() {
        return scheduleDate;
    }

    /**
     * @param scheduleDate
     *            the scheduleDate to set
     */
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    /**
     * @return the captcha
     */
    @Column(name = "captcha", nullable = true)
    public Boolean getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(Boolean captcha) {
        this.captcha = captcha;
    }

    /**
     * @return the limitVotes
     */
    @Column(name = "limit_votes", nullable = true)
    public Integer getLimitVotes() {
        return limitVotes;
    }

    /**
     * @param limitVotes the limitVotes to set
     */
    public void setLimitVotes(Integer limitVotes) {
        this.limitVotes = limitVotes;
    }

    /**
     * @return the resumeLiveResults
     */
    @Column(name = "resume_live_results", nullable = true)
    public Boolean getResumeLiveResults() {
        return resumeLiveResults;
    }

    /**
     * @param resumeLiveResults the resumeLiveResults to set
     */
    public void setResumeLiveResults(Boolean resumeLiveResults) {
        this.resumeLiveResults = resumeLiveResults;
    }

    /**
     * @return the allowRepatedVotes
     */
    @Column(name = "allow_repeated_votes", nullable = true)
    public Boolean getAllowRepatedVotes() {
        return allowRepatedVotes;
    }

    /**
     * @param allowRepatedVotes the allowRepatedVotes to set
     */
    public void setAllowRepatedVotes(Boolean allowRepatedVotes) {
        this.allowRepatedVotes = allowRepatedVotes;
    }

    /**
     * @return the hashTags
     */
    @ManyToMany(cascade=CascadeType.ALL)
    public Set<HashTag> getHashTags() {
        return hashTags;
    }

    /**
     * @param hashTags the hashTags to set
     */
    public void setHashTags(Set<HashTag> hashTags) {
        this.hashTags = hashTags;
    }

    /**
     * @return the createDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the numbervotes
     */
    @Column(name = "numberVotes")
    public Integer getNumbervotes() {
        return numbervotes;
    }

    /**
     * @param numbervotes the numbervotes to set
     */
    public void setNumbervotes(Integer numbervotes) {
        this.numbervotes = numbervotes;
    }

    /**
     * @return the hits
     */
    @Column(name = "hits")
    public Integer getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(Integer hits) {
        this.hits = hits;
    }

    /**
     * @return the tweetPollFolder
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tweetPollFolderId", nullable = true)
    public TweetPollFolder getTweetPollFolder() {
        return tweetPollFolder;
    }

    /**
     * @param tweetPollFolder the tweetPollFolder to set
     */
    public void setTweetPollFolder(TweetPollFolder tweetPollFolder) {
        this.tweetPollFolder = tweetPollFolder;
    }

    /**
     * @return the favourites
     */

    @Column(name = "favourite")
    public Boolean getFavourites() {
        return favourites;
    }

    /**
     * @param favourites the favourites to set
     */
    public void setFavourites(final Boolean favourites) {
        this.favourites = favourites;
    }

    /**
     * @return the editorOwner
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "editor")
    public UserAccount getEditorOwner() {
        return editorOwner;
    }

    /**
     * @param editorOwner the editorOwner to set
     */
    public void setEditorOwner(final UserAccount editorOwner) {
        this.editorOwner = editorOwner;
    }
}