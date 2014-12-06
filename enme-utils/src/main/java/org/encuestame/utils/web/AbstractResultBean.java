package org.encuestame.utils.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractResultBean implements ResultBean {


    @JsonProperty("id")
    private Long answerId;

     @JsonProperty("percent")
    private String percent;

    @JsonProperty("question_label")
    private String answerName;

    @JsonProperty("votes")
    private Long votes;

    @JsonProperty("color")
    private String color;

    @JsonProperty("url_relative")
    private String url;

    @JsonProperty("url_short_url")
    private String shortUrl;

    /**
     * @return the percent
     */
    public String getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(String percent) {
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
    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    /**
     * @return the votes
     */
    public Long getVotes() {
        return votes;
    }

    /**
     * @param votes the votes to set
     */
    public void setVotes(Long votes) {
        this.votes = votes;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the shortUrl
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * @param shortUrl the shortUrl to set
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
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

}
