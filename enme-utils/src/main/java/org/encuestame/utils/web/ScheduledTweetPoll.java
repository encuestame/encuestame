package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.Date;

import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetItemPublishedResponse;
import org.encuestame.utils.json.TweetPollBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unit ScheduledTweetPoll.
 * @author Picado, Juan juanATencuestame.org
 * @since December 7, 2013
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledTweetPoll implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8191050998239747994L;
		
	@JsonProperty(value = "publish_response")
	private TweetItemPublishedResponse publishedResponse;
	
	@JsonProperty(value = "scheduled_date")
	private Date scheduledDate;

	public ScheduledTweetPoll() {}


	/**
	 * @return the scheduledDate
	 */
	@JsonIgnore
	public Date getScheduledDate() {
		return scheduledDate;
	}

	/**
	 * @param scheduledDate the scheduledDate to set
	 */
	public void setScheduledDate(final Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	/**
	 * @return the publishedResponse
	 */
	public TweetItemPublishedResponse getPublishedResponse() {
		return publishedResponse;
	}

	/**
	 * @param publishedResponse the publishedResponse to set
	 */
	public void setPublishedResponse(final TweetItemPublishedResponse publishedResponse) {
		this.publishedResponse = publishedResponse;
	}
}
