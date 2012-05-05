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

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.encuestame.utils.enums.HitCategory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Hits.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 08, 2011
 */
@Entity
@Table(name = "hits")
@Indexed(index="hits")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hit { //TODO: Create superMapped class with Access rate.

    /** Id. **/
    private Long id;

    /** {@link TweetPoll} **/
    private TweetPoll tweetPoll;

    /** **/
    private Date hitDate = Calendar.getInstance().getTime();

    /** **/
    private String ipAddress;

    /** {@link Poll} **/
    private Poll poll;

    /** {@link Survey} **/
    private Survey survey;

    /** {@link HashTag} **/
    private HashTag hashTag;
    
    /** {@link UserAccount} **/
    private UserAccount userAccount;
   
    /** {@link HitCategory} **/
    private HitCategory hitCategory;


    /**
     * @return the id
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hit_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the tweetPoll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public TweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(final TweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
     * @return the hitDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    public Date getHitDate() {
        return hitDate;
    }

    /**
     * @param hitDate the hitDate to set
     */
    public void setHitDate(final Date hitDate) {
        this.hitDate = hitDate;
    }

    /**
     * @return the ipAddress
     */
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "hits_ip_address", nullable = false, length = 100)
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }


    /**
     * @return the poll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Poll getPoll() {
        return poll;
    }

    /**
     * @param poll the poll to set
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
     * @param survey the survey to set
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * @return the hashTag
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public HashTag getHashTag() {
        return hashTag;
    }

    /**
     * @param hashTag the hashTag to set
     */
    public void setHashTag(final HashTag hashTag) {
        this.hashTag = hashTag;
    }

	/**
	 * @return the hitCategory
	 */
    @Column(name = "hit_category", nullable = false)
    @Enumerated(EnumType.ORDINAL)
	public HitCategory getHitCategory() {
		return hitCategory;
	}

	/**
	 * @param hitCategory the hitCategory to set
	 */
	public void setHitCategory(final HitCategory hitCategory) {
		this.hitCategory = hitCategory;
	}

	/**
	 * @return the userAccount
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	/**
	 * @param userAccount the userAccount to set
	 */
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}	
}
