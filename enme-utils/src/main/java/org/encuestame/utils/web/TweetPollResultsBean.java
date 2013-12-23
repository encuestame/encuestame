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
package org.encuestame.utils.web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * TweetPoll results bean.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetPollResultsBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8477551175621750552L;


    @JsonProperty("color")
    private String color;

    @JsonProperty("votes")
    private Long votes;

    @JsonProperty("id")
    private Long answerId;

    @JsonProperty("percent")
    private String percent;

    @JsonProperty("question_label")
    private String answerName;

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the votes
     */
    public Long getVotes() {
        votes = (votes == null ? 0L : votes);
        return votes;
    }

    /**
     * @param votes
     *            the votes to set
     */
    public void setVotes(Long votes) {
        this.votes = votes;
    }

    /**
     * @return the answerId
     */
    public Long getAnswerId() {
        return answerId;
    }

    /**
     * @param answerId
     *            the answerId to set
     */
    public void setAnswerId(final Long answerId) {
        this.answerId = answerId;
    }

    /**
     * @return the percent
     */
    public String getPercent() {
        return percent;
    }

    /**
     * @param percent
     *            the percent to set
     */
    public void setPercent(final String percent) {
        this.percent = percent;
    }

    /**
     * @return the answerName
     */
    public String getAnswerName() {
        return answerName;
    }

    /**
     * @param answerName the answerName to set
     */
    public void setAnswerName(final String answerName) {
        this.answerName = answerName;
    }
}
