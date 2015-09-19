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
package org.encuestame.persistence.domain;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Rate item (Like, Dislikes)
 * @author Morales Diana, Paola paolaATencuestame.org
 * @since September 13, 2011
 */
@Entity
@Indexed(index="access_rate")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "access_rate")
public class AccessRate {

    /** **/
    private Long rateId;

    /** **/
    private TweetPoll tweetPoll;

    /** **/
    private Poll poll;

    /** **/
    private Survey survey;

    /** **/
    private Boolean rate;

    /** **/
    private UserAccount user;

    /** **/
    private Date updatedDate;

    /** **/
    private String ipAddress;

    /** {@link Comment} **/
    private Comment comments;

    /**
     * @return the tweetPollRateId
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rateId", unique = true, nullable = false)
    public Long getRateId() {
        return rateId;
    }

    /**
     * @param rateId the tweetPollRateId to set
     */
    public void setRateId(final Long rateId) {
        this.rateId = rateId;
    }

    /**
     * @return the tweetPoll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
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
     * @return the poll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Poll getPoll() {
        return poll;
    }

    /**
     * @param poll
     *            the poll to set
     */
    public void setPoll(final Poll poll) {
        this.poll = poll;
    }

    /**
     * @return the survey
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param survey
     *            the survey to set
     */
    public void setSurvey(final Survey survey) {
        this.survey = survey;
    }

    /**
     * @return the rate
     */
    @Column(name = "rate")
    public Boolean getRate() {
        return rate;
    }

    /**
     * @param rate
     *            the rate to set
     */
    public void setRate(Boolean rate) {
        this.rate = rate;
    }

    /**
     * @return the user
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public UserAccount getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(UserAccount user) {
        this.user = user;
    }

    /**
     * @return the updatedDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the ipAddress
     */
    @Field(index = Index.YES, store = Store.YES)
    @Column(name = "ipAddress", nullable = false)
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     *            the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the comments
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Comment getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(final Comment comments) {
        this.comments = comments;
    }
}
