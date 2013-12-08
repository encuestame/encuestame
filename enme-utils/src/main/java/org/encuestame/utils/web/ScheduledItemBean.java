package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.Date;

import org.encuestame.utils.json.SocialAccountBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unit ScheduledItemBean.
 * 
 * @author Picado, Juan juanATencuestame.org
 * @since December 7, 2013
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "id")
	private Long id;
	@JsonProperty(value = "item_id")
	private Long itemId;
	@JsonProperty(value = "tweet")
	private String tweet;
	@JsonProperty(value = "scheduled_date")
	private Date date;
	@JsonProperty(value = "social_bean")
	private SocialAccountBean accountBean;
	private String status;
	@JsonProperty(value = "status")
	private TweetPollResultsBean tweetPollResultsBean;
	@JsonProperty(value = "type_search")
	private String typeSearch;
	@JsonProperty(value = "publication_date")
	private Date publicationDate;

	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tweet
	 */
	@JsonIgnore
	public String getTweet() {
		return tweet;
	}

	/**
	 * @param tweet
	 *            the tweet to set
	 */
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	/**
	 * @return the date
	 */
	@JsonIgnore
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the accountBean
	 */
	@JsonIgnore
	public SocialAccountBean getAccountBean() {
		return accountBean;
	}

	/**
	 * @param accountBean
	 *            the accountBean to set
	 */
	public void setAccountBean(SocialAccountBean accountBean) {
		this.accountBean = accountBean;
	}

	/**
	 * @return the status
	 */
	@JsonIgnore
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the tweetPollResultsBean
	 */
	@JsonIgnore
	public TweetPollResultsBean getTweetPollResultsBean() {
		return tweetPollResultsBean;
	}

	/**
	 * @param tweetPollResultsBean
	 *            the tweetPollResultsBean to set
	 */
	public void setTweetPollResultsBean(
			TweetPollResultsBean tweetPollResultsBean) {
		this.tweetPollResultsBean = tweetPollResultsBean;
	}

	/**
	 * @return the typeSearch
	 */
	@JsonIgnore
	public String getTypeSearch() {
		return typeSearch;
	}

	/**
	 * @param typeSearch
	 *            the typeSearch to set
	 */
	public void setTypeSearch(String typeSearch) {
		this.typeSearch = typeSearch;
	}

	/**
	 * @return the publicationDate
	 */
	@JsonIgnore
	public Date getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @param publicationDate
	 *            the publicationDate to set
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	/**
	 * @return the itemId
	 */
	@JsonIgnore
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
