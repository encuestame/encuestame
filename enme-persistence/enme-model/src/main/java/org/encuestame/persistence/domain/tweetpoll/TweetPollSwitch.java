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

import java.util.Calendar;
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

import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * TweetPoll Switch.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 12, 2010 10:31:45 PM
 * @version $Id:$
 */

@Entity
@Table(name = "tweetpoll_switch")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TweetPollSwitch {

    /**
     * Switch id.
     */
    private Long switchId;

    /**
     * Code tweet.
     */
    private String codeTweet;

    /**
     * {@link TweetPoll}.
     */
    private TweetPoll tweetPoll;

    /**
     * {@link QuestionAnswer}.
     */
    private QuestionAnswer answers;

    /**
     * Last date updated.
     */
    private Date dateUpdated = Calendar.getInstance().getTime();

    /**
     * Short URL.
     */
    private String shortUrl;

    /**
     * The relative time.
     */
    private String relativeUrl;

    /**
     * @return the switchId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweetpoll_switch_id", unique = true, nullable = false)
    public Long getSwitchId() {
        return switchId;
    }

    /**
     * @param switchId
     *            the switchId to set
     */
    public void setSwitchId(Long switchId) {
        this.switchId = switchId;
    }

    /**
     * @return the codeTweet
     */
    @Column(name = "tweet_code", nullable = false, unique = true)
    public String getCodeTweet() {
        return codeTweet;
    }

    /**
     * @param codeTweet
     *            the codeTweet to set
     */
    public void setCodeTweet(String codeTweet) {
        this.codeTweet = codeTweet;
    }

    /**
     * @return the tweetPoll
     */

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tweet_poll_id", nullable = false)
    public TweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll
     *            the tweetPoll to set
     */
    public void setTweetPoll(TweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
     * @return the answers
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "q_answer_id", nullable = false)
    public QuestionAnswer getAnswers() {
        return answers;
    }

    /**
     * @param answers
     *            the answers to set
     */
    public void setAnswers(QuestionAnswer answers) {
        this.answers = answers;
    }

    /**
     * @return the shortUrl
     */
    @Column(name = "short_url", nullable = true, length = 400)
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * @param shortUrl the shortUrl to set
     */
    public void setShortUrl(final String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * @return the dateUpdated
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_date_updated", nullable = false)
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * @param dateUpdated the dateUpdated to set
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }



    /**
     * @return the relativeUrl
     */
    @Column(name = "relative_url", nullable = true, length = 400)
    public String getRelativeUrl() {
        return relativeUrl;
    }

    /**
     * @param relativeUrl the relativeUrl to set
     */
    public void setRelativeUrl(final String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TweetPollSwitch [switchId=" + switchId + ", codeTweet="
                + codeTweet + ", tweetPoll=" + tweetPoll + ", answers="
                + answers + ", dateUpdated=" + dateUpdated + ", shortUrl="
                + shortUrl + "]";
    }
}