package org.encuestame.utils.web;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
