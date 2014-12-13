package org.encuestame.utils.web;

/**
 * Default implementation which represent a single answer result.
 * @author jpicado
 *
 */
public interface ResultBean {

     public QuestionAnswerBean getAnswerBean();

        /**
         * @param answerBean the answerBean to set
         */
     public void setAnswerBean(QuestionAnswerBean answerBean);

     /**
     * @param percent the percent to set
     */
    public void setPercent(String percent);

    /**
     * @return the answerName
     */
    public String getAnswerName();

    /**
     * @param answerName the answerName to set
     */
    public void setAnswerName(String answerName);

    /**
     * @return the votes
     */
    public Long getVotes();

    /**
     * @param votes the votes to set
     */
    public void setVotes(Long votes);

    /**
     * @return the color
     */
    public String getColor();

    /**
     * @param color the color to set
     */
    public void setColor(String color);

    /**
     * @return the url
     */
    public String getUrl();

    /**
     * @param url the url to set
     */
    public void setUrl(String url);

    /**
     * @return the shortUrl
     */
    public String getShortUrl();

    /**
     * @param shortUrl the shortUrl to set
     */
    public void setShortUrl(String shortUrl);


    /**
     * @return the answerId
     */
    public Long getAnswerId();

    /**
     * @param answerId
     *            the answerId to set
     */
    public void setAnswerId(final Long answerId);

}
