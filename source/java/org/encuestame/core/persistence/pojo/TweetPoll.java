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
package org.encuestame.core.persistence.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TweetPoll Domain.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since February 13, 2009
 * @version $Id: $
 */

@Entity
@Table(name = "tweetPoll")
public class TweetPoll {

    private Long tweetPollId;
    private Long tweetId;
    private Boolean closeNotification;
    private Boolean resultNotification;
    private Boolean publishTweetPoll;
    private Date startDateTweet;
    private Date endDateTweet;
    private Date publicationDateTweet;
    private Boolean completed;
    private SecUsers tweetOwner;
    private Questions question;

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
     * @param tweetPollId the tweetPollId to set
     */
    public void setTweetPollId(Long tweetPollId) {
        this.tweetPollId = tweetPollId;
    }
    /**
     * @return the tweetId
     */
    @Column(name = "tweet_id", unique = true, nullable = true)
    public Long getTweetId() {
        return tweetId;
    }
    /**
     * @param tweetId the tweetId to set
     */
    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }
    /**
     * @return the closeNotification
     */
    @Column(name = "close_notification", nullable = true)
    public Boolean getCloseNotification() {
        return closeNotification;
    }
    /**
     * @param closeNotification the closeNotification to set
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
     * @param resultNotification the resultNotification to set
     */
    public void setResultNotification(Boolean resultNotification) {
        this.resultNotification = resultNotification;
    }

    /**
     * @return the startDateTweet
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date_tweet", nullable = false)
    public Date getStartDateTweet() {
        return startDateTweet;
    }

    /**
     * @param startDateTweet the startDateTweet to set
     */
    public void setStartDateTweet(Date startDateTweet) {
        this.startDateTweet = startDateTweet;
    }

    /**
     * @return the endDateTweet
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date_tweet", nullable = false)
    public Date getEndDateTweet() {
        return endDateTweet;
    }

    /**
     * @param endDateTweet the endDateTweet to set
     */
    public void setEndDateTweet(Date endDateTweet) {
        this.endDateTweet = endDateTweet;
    }

    /**
     * @return the publicationDateTweet
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publication_date_tweet", nullable = true)
    public Date getPublicationDateTweet() {
        return publicationDateTweet;
    }

    /**
     * @param publicationDateTweet the publicationDateTweet to set
     */
    public void setPublicationDateTweet(Date publicationDateTweet) {
        this.publicationDateTweet = publicationDateTweet;
    }

    /**
     * @return the completed
     */
    @Column(name = "completed", nullable = false)
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    /**
     * @return the tweetOwner
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUsers getTweetOwner() {
        return tweetOwner;
    }

    /**
     * @param tweetOwner the tweetOwner to set
     */
    public void setTweetOwner(final SecUsers tweetOwner) {
        this.tweetOwner = tweetOwner;
    }

    /**
     * @return the question
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "qid", nullable = false)
    public Questions getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(final Questions question) {
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
     * @param publishTweetPoll the publishTweetPoll to set
     */
    public void setPublishTweetPoll(final Boolean publishTweetPoll) {
        this.publishTweetPoll = publishTweetPoll;
    }


}
