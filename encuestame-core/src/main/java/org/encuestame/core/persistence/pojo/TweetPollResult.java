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
 * TweetPollResult Domain.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since February 13, 2009
 * @version $Id$
 */

@Entity
@Table(name = "tweetpoll_result")
public class TweetPollResult {

    private Long tweetPollResultId;
    private TweetPollSwitch tweetPollSwitch;
    private String ipVote;
    private Date tweetResponseDate;

    /**
     * @return the tweetPollResultId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweetpoll_resultId", unique = true, nullable = false)
    public Long getTweetPollResultId() {
        return tweetPollResultId;
    }

    /**
     * @param tweetPollResultId the tweetPollResultId to set
     */
    public void setTweetPollResultId(final Long tweetPollResultId) {
        this.tweetPollResultId = tweetPollResultId;
    }

    /**
     * @return the tweetResponseDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tweet_date_response", nullable = false)
    public Date getTweetResponseDate() {
        return tweetResponseDate;
    }
    /**
     * @param tweetResponseDate the tweetResponseDate to set
     */
    public void setTweetResponseDate(final Date tweetResponseDate) {
        this.tweetResponseDate = tweetResponseDate;
    }

    /**
     * @return the tweetPollSwitch
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tweetpoll_switch_id", nullable = false)
    public TweetPollSwitch getTweetPollSwitch() {
        return tweetPollSwitch;
    }

    /**
     * @param tweetPollSwitch the tweetPollSwitch to set
     */
    public void setTweetPollSwitch(TweetPollSwitch tweetPollSwitch) {
        this.tweetPollSwitch = tweetPollSwitch;
    }

    /**
     * @return the ipVote
     */
    @Column(name = "ip_vote", nullable = true, length = 18)
    public String getIpVote() {
        return ipVote;
    }

    /**
     * @param ipVote the ipVote to set
     */
    public void setIpVote(String ipVote) {
        this.ipVote = ipVote;
    }
}
