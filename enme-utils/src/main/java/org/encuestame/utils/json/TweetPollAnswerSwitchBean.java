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
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;

/**
 * Represents tweetpoll answer switch.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 15, 2011
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetPollAnswerSwitchBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5463311757853384293L;
    @JsonProperty(value = "id")
    private Long id;
    @JsonIgnore
    public TweetPollBean tweetPollBean;
    @JsonProperty(value = "tweet_poll_id")
    public Long tweetPollId;
    @JsonProperty(value = "answer")
    public QuestionAnswerBean answerBean;
    @JsonProperty(value = "short_url")
    public String shortUrl;
    @JsonProperty(value = "relative_url")
    public String relativeUrl;
    @JsonProperty(value = "results")
    public TweetPollResultsBean resultsBean;

    /**
     * @return the tweetPollBean
     */
    @JsonIgnore
    public TweetPollBean getTweetPollBean() {
        return tweetPollBean;
    }

    /**
     * @param tweetPollBean
     *            the tweetPollBean to set
     */
    public void setTweetPollBean(final TweetPollBean tweetPollBean) {
        this.tweetPollBean = tweetPollBean;
    }

    /**
     * @return the answerBean
     */
    @JsonIgnore
    public QuestionAnswerBean getAnswerBean() {
        return answerBean;
    }

    /**
     * @param answerBean
     *            the answerBean to set
     */
    public void setAnswerBean(final QuestionAnswerBean answerBean) {
        this.answerBean = answerBean;
    }

    /**
     * @return the shortUrl
     */
    @JsonIgnore
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * @param shortUrl
     *            the shortUrl to set
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * @return the tweetPollId
     */
    @JsonIgnore
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
     * @return the resultsBean
     */
    @JsonIgnore
    public TweetPollResultsBean getResultsBean() {
        return resultsBean;
    }

    /**
     * @param resultsBean the resultsBean to set
     */
    public void setResultsBean(TweetPollResultsBean resultsBean) {
        this.resultsBean = resultsBean;
    }

	/**
	 * @return the relativeUrl
	 */
    @JsonIgnore
	public String getRelativeUrl() {
		return relativeUrl;
	}

	/**
	 * @param relativeUrl the relativeUrl to set
	 */
	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}       
}
