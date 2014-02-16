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
package org.encuestame.utils.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tweet item published resonse.
 * @author Picado, Juan juanATencuestame.org
 * @since May 10, 2011
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetItemPublishedResponse implements Serializable{

    /** **/
    private static final long serialVersionUID = 5660565898832615803L;

    @JsonProperty(value = "id")
    public Long id;
    
    @JsonProperty(value = "tweet_id")
    public String tweetId;

    @JsonProperty(value = "textTweeted")
    public String textTweeted;

    @JsonProperty(value = "date_published")
    public String datePublished;

    @JsonProperty(value = "social_account")
    public String socialAccountName;

    @JsonProperty(value = "status_tweet")
    public String statusTweet;

    @JsonProperty(value = "status_description_tweet")
    public String statusDescriptionTweet;

    @JsonProperty(value = "source_tweet")
    public String sourceTweet;

    @JsonProperty(value = "tweet_url")
    public String tweetUrl;

    @JsonProperty(value = "social_account_id")
    public Long socialAccountId;
    
    @JsonProperty(value = "type_item")
    public String typeItem;

	/**
	 * @return the id
	 */
    @JsonIgnore
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tweetId
	 */
	@JsonIgnore
	public String getTweetId() {
		return tweetId;
	}

	/**
	 * @param tweetId the tweetId to set
	 */
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	/**
	 * @return the textTweeted
	 */
	@JsonIgnore
	public String getTextTweeted() {
		return textTweeted;
	}

	/**
	 * @param textTweeted the textTweeted to set
	 */
	public void setTextTweeted(String textTweeted) {
		this.textTweeted = textTweeted;
	}

	/**
	 * @return the datePublished
	 */
	@JsonIgnore
	public String getDatePublished() {
		return datePublished;
	}

	/**
	 * @param datePublished the datePublished to set
	 */
	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	/**
	 * @return the socialAccountName
	 */
	@JsonIgnore
	public String getSocialAccountName() {
		return socialAccountName;
	}

	/**
	 * @param socialAccountName the socialAccountName to set
	 */
	public void setSocialAccountName(String socialAccountName) {
		this.socialAccountName = socialAccountName;
	}

	/**
	 * @return the statusTweet
	 */
	@JsonIgnore
	public String getStatusTweet() {
		return statusTweet;
	}

	/**
	 * @param statusTweet the statusTweet to set
	 */
	public void setStatusTweet(String statusTweet) {
		this.statusTweet = statusTweet;
	}

	/**
	 * @return the statusDescriptionTweet
	 */
	@JsonIgnore
	public String getStatusDescriptionTweet() {
		return statusDescriptionTweet;
	}

	/**
	 * @param statusDescriptionTweet the statusDescriptionTweet to set
	 */
	public void setStatusDescriptionTweet(String statusDescriptionTweet) {
		this.statusDescriptionTweet = statusDescriptionTweet;
	}

	/**
	 * @return the sourceTweet
	 */
	@JsonIgnore
	public String getSourceTweet() {
		return sourceTweet;
	}

	/**
	 * @param sourceTweet the sourceTweet to set
	 */
	public void setSourceTweet(String sourceTweet) {
		this.sourceTweet = sourceTweet;
	}

	/**
	 * @return the tweetUrl
	 */
	@JsonIgnore
	public String getTweetUrl() {
		return tweetUrl;
	}

	/**
	 * @param tweetUrl the tweetUrl to set
	 */
	public void setTweetUrl(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}

	/**
	 * @return the socialAccountId
	 */
	@JsonIgnore
	public Long getSocialAccountId() {
		return socialAccountId;
	}

	/**
	 * @param socialAccountId the socialAccountId to set
	 */
	public void setSocialAccountId(Long socialAccountId) {
		this.socialAccountId = socialAccountId;
	}

	/**
	 * @return the typeItem
	 */
	public String getTypeItem() {
		return typeItem;
	}

	/**
	 * @param typeItem the typeItem to set
	 */
	public void setTypeItem(String typeItem) {
		this.typeItem = typeItem;
	}
}
