/*
 ************************************************************************************
 * Copyright (C) 2001-2013 encuestame: system online surveys Copyright (C) 2013
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

import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * Poll Domain.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since October 14, 2013
 */
@Entity
@Indexed(index="Scheduled")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "scheduled")
public class Schedule {

	/** **/
	private Long idpub;

	/** **/
	private TweetPoll tpoll;

	/** **/
	private Poll poll;

	/** **/
	private Survey survey;

	/** **/
	private String tweetText;

	/** **/
	private Date scheduleDate;

	/** **/
	private SocialAccount socialAccount;

	/** **/
	private Status status;

	/** **/
	private Integer publishAttempts;

	/** **/
	private TweetPollSavedPublishedStatus tpollSavedPublished;

	/** **/
	private TypeSearchResult typeSearch;


	/**
	 * @return the id
	 */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publish_scheduled_id", unique = true, nullable = false)
	public Long getIdpub() {
		return idpub;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdpub(final Long idpub) {
		this.idpub = idpub;
	}

	/**
	 * @return the tpoll
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	public TweetPoll getTpoll() {
		return tpoll;
	}

	/**
	 * @param tpoll the tpoll to set
	 */
	public void setTpoll(final TweetPoll tpoll) {
		this.tpoll = tpoll;
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
	public void setSurvey(final Survey survey) {
		this.survey = survey;
	}

	/**
	 * @return the tweetText
	 */
	@Column(name = "tweet_text", nullable = true)
	public String getTweetText() {
		return tweetText;
	}

	/**
	 * @param tweetText the tweetText to set
	 */
	public void setTweetText(final String tweetText) {
		this.tweetText = tweetText;
	}

	/**
	 * @return the schedule
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_date", nullable = true)
	public Date getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setScheduleDate(final Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * @return the socialAccount
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	public SocialAccount getSocialAccount() {
		return socialAccount;
	}

	/**
	 * @param socialAccount the socialAccount to set
	 */
	public void setSocialAccount(final SocialAccount socialAccount) {
		this.socialAccount = socialAccount;
	}

	/**
	 * @return the status
	 */
    @Column(name="status")
    @Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final Status status) {
		this.status = status;
	}

	/**
	 * @return the attempts
	 */
	@Column(name = "publish_attempts", nullable = true)
	public Integer getPublishAttempts() {
		return publishAttempts;
	}

	/**
	 * @param attempts the attempts to set
	 */
	public void setPublishAttempts(final Integer publishAttempts) {
		this.publishAttempts = publishAttempts;
	}

	/**
	 * @return the tpollSavedPublished
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	public TweetPollSavedPublishedStatus getTpollSavedPublished() {
		return tpollSavedPublished;
	}

	/**
	 * @param tpollSavedPublished the tpollSavedPublished to set
	 */
	public void setTpollSavedPublished(final
			TweetPollSavedPublishedStatus tpollSavedPublished) {
		this.tpollSavedPublished = tpollSavedPublished;
	}

	/**
	 * @return the typeSearch
	 */
	@Column(name = "type_search")
	@Enumerated(EnumType.ORDINAL)
	public TypeSearchResult getTypeSearch() {
		return typeSearch;
	}

	/**
	 * @param typeSearch the typeSearch to set
	 */
	public void setTypeSearch(final TypeSearchResult typeSearch) {
		this.typeSearch = typeSearch;
	}
}
