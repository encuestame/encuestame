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
package org.encuestame.persistence.domain.tweetpoll;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.AbstractGeoPoint;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.enums.CommentOptions;
import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 * TweetPoll Domain.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since February 13, 2009
 */

@Indexed(index="TweetPoll")
@Entity
@Table(name = "tweetPoll")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TweetPoll extends AbstractGeoPoint {

    private Long tweetPollId;

    /** Close Notification. eg: if tweetpoll was closed, the app should be notify user with ui notification. **/
    private Boolean closeNotification;

    /**
     * Result Notification after close. eg: after close the app
     * should be notify with ui notification with
     * resume of results.
     * **/
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

    /** Updated Date. **/
    private Date updatedDate;

    /**  This value is true when the tweetpoll has been archived. **/
    private Boolean completed = false;

    /** Required Captcha to Vote. **/
    private Boolean captcha = false;

    /** Limit with date. **/
    private Boolean dateLimit = true;

    /** Limit to close the tweetPoll. **/
    private Date dateLimited;

    /**
     * Enable a limit of valid votes.
     **/
    private Boolean limitVotesEnabled  = false;

    /**
     * Limit Votes.
     **/
    private Integer limitVotes;

    /**
     *  Enable auto refresh when chart result is displayed, eg. vote page and detail page.
     **/
    private Boolean resumeLiveResults = false;

    /** {@link Account}. **/
    private Account tweetOwner;

    /**
     * Define which user create this tweetPoll.
     */
    private UserAccount editorOwner;

    /** {@link Question}. **/
    private Question question;

    /**
     * Repeated Votes
     * **/
    private Boolean allowRepatedVotes  = false;

    /**
     * Limit of repeated votes by user (ip).
     **/
    private Integer maxRepeatedVotes;

    /**
     * Hash Tags.
     **/
    private Set<HashTag> hashTags = new HashSet<HashTag>();

    /**
     * Number votes for Survey and Poll.
     **/
     private Long numbervotes = 1L;

     /** Number Hits or visits **/
     private Long hits = 1L;

     /** TweetPoll Folder. **/
     private TweetPollFolder tweetPollFolder;

     /** Set Item as Favourite. **/
     private Boolean favourites = false;

     /** **/
     private Long relevance;

     /** Like option**/
     private Long likeVote = 0L;

     /** Unlike **/
     private Long dislikeVote = 0L;

     /**
      * Show Comments Option.
      */
     private CommentOptions showComments;


    /**
     * @return the tweetPollId
     */
    @Id
    @DocumentId
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
    public void setCompleted(final Boolean completed) {
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
    @IndexedEmbedded
    @JoinColumn(name = "qid", nullable = false)
    @Cascade( org.hibernate.annotations.CascadeType.REMOVE )
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tweetpoll_hashtags",
               joinColumns = {@JoinColumn(name = "tweetpoll_id")},
               inverseJoinColumns = {@JoinColumn(name = "hastag_id")})
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
    public Long getNumbervotes() {
        return numbervotes;
    }

    /**
     * @param numbervotes the numbervotes to set
     */
    public void setNumbervotes(Long numbervotes) {
        this.numbervotes = numbervotes;
    }

    /**
     * @return the hits
     */
    @Column(name = "hits")
    public Long getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(Long hits) {
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

    /**
     * @return the limitVotesEnabled
     */
    @Column(name = "limits_votes_enabled")
    public Boolean getLimitVotesEnabled() {
        return limitVotesEnabled;
    }

    /**
     * @param limitVotesEnabled the limitVotesEnabled to set
     */
    public void setLimitVotesEnabled(Boolean limitVotesEnabled) {
        this.limitVotesEnabled = limitVotesEnabled;
    }

    /**
     * @return the maxRepeatedVotes
     */
    @Column(name = "max_repeated_votes")
    public Integer getMaxRepeatedVotes() {
        return maxRepeatedVotes;
    }

    /**
     * @param maxRepeatedVotes the maxRepeatedVotes to set
     */
    public void setMaxRepeatedVotes(Integer maxRepeatedVotes) {
        this.maxRepeatedVotes = maxRepeatedVotes;
    }

    /**
     * @return the updatedDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_date_updated", nullable = true)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }



    /**
     * @return the dateLimit
     */
    @Column(name = "limit_with_date")
    public Boolean getDateLimit() {
        return dateLimit;
    }

    /**
     * @param dateLimit the dateLimit to set
     */
    public void setDateLimit(final Boolean dateLimit) {
        this.dateLimit = dateLimit;
    }

    /**
     * @return the dateLimited
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_limited", nullable = true)
    public Date getDateLimited() {
        return dateLimited;
    }

    /**
     * @param dateLimited the dateLimited to set
     */
    public void setDateLimited(Date dateLimited) {
        this.dateLimited = dateLimited;
    }

    /**
     * @return the relevance
     */
    public Long getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set
     */
    @Column(name = "relevance", nullable = true)
    public void setRelevance(final Long relevance) {
        this.relevance = relevance;
    }

    /**
     * @return the likeVote
     */
    public Long getLikeVote() {
        return likeVote;
    }

    /**
     * @param likeVote the likeVote to set
     */
    public void setLikeVote(final Long likeVote) {
        this.likeVote = likeVote;
    }

    /**
     * @return the dislikeVote
     */
    public Long getDislikeVote() {
        return dislikeVote;
    }

    /**
     * @param dislikeVote the dislikeVote to set
     */
    public void setDislikeVote(final Long dislikeVote) {
        this.dislikeVote = dislikeVote;
    }

    /**
     * @return the showComments
     */
    @Column(name = "comment_option")
    @Enumerated(EnumType.ORDINAL)
    public CommentOptions getShowComments() {
        return showComments;
    }

    /**
     * @param showComments the showComments to set
     */
    public void setShowComments(final CommentOptions showComments) {
        this.showComments = showComments;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TweetPoll [tweetPollId=" + tweetPollId + ", closeNotification="
                + closeNotification + ", resultNotification="
                + resultNotification + ", allowLiveResults=" + allowLiveResults
                + ", publishTweetPoll=" + publishTweetPoll
                + ", scheduleTweetPoll=" + scheduleTweetPoll
                + ", scheduleDate=" + scheduleDate + ", createDate="
                + createDate + ", updatedDate=" + updatedDate + ", completed="
                + completed + ", captcha=" + captcha + ", limitVotesEnabled="
                + limitVotesEnabled + ", limitVotes=" + limitVotes
                + ", resumeLiveResults=" + resumeLiveResults + ", tweetOwner="
                + tweetOwner + ", editorOwner=" + editorOwner + ", question="
                + question + ", allowRepatedVotes=" + allowRepatedVotes
                + ", maxRepeatedVotes=" + maxRepeatedVotes + ", hashTags="
                + hashTags + ", numbervotes=" + numbervotes + ", hits=" + hits
                + ", tweetPollFolder=" + tweetPollFolder + ", favourites="
                + favourites + "]";
    }

}