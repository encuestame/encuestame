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
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.encuestame.utils.json.QuestionBean;

/**
 * Unit Poll Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  March 15, 2009
 * @version $Id: $
 */
public class PollBean extends AbstractUnitSurvey implements Serializable{

    /**
     * Serial
     */
    private static final long serialVersionUID = 7022698996782621900L;

    /***/
    @JsonProperty(value = "id")
    private Long id;

    /***/
    @JsonProperty(value = "completedPoll")
    private Boolean completedPoll;

    /***/
    @JsonProperty(value = "creationDate")
    private String creationDate;

    /***/
    @JsonProperty(value = "question")
    private QuestionBean questionBean = new QuestionBean();

    /***/
    @JsonProperty(value = "finishDate")
    private Date finishDate;

    /***/
    @JsonProperty(value = "publishPoll")
    private Boolean publishPoll;

    /***/
    @JsonProperty(value = "closeNotification")
    private Boolean closeNotification;

    /***/
    @JsonProperty(value = "showResultsPoll")
    private Boolean showResultsPoll;

    /***/
    @JsonProperty(value = "hashPoll")
    private String hashPoll;

    /** **/
    @JsonProperty(value = "updatedDate")
    private Date updatedDate;

    /** **/
    @JsonProperty(value = "url")
    private String url;

    /** **/
    @JsonProperty(value = "shortUrl")
    private String shortUrl;



    /**
     * @return the id
     */
    @JsonIgnore
    public final Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the completedPoll
     */
    @JsonIgnore
    public final Boolean getCompletedPoll() {
        return completedPoll;
    }

    /**
     * @param completedPoll the completedPoll to set
     */
    public final void setCompletedPoll(final Boolean completedPoll) {
        this.completedPoll = completedPoll;
    }

    /**
     * @return the creationDate
     */
    @JsonIgnore
    public final String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public final void setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the questionBean
     */
    @JsonIgnore
    public final QuestionBean getQuestionBean() {
        return questionBean;
    }

    /**
     * @param questionBean the questionBean to set
     */
    public final void setQuestionBean(final QuestionBean questionBean) {
        this.questionBean = questionBean;
    }

    /**
     * @return the finishDate
     */
    @JsonIgnore
    public Date getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the publishPoll
     */
    @JsonIgnore
    public Boolean getPublishPoll() {
        return publishPoll;
    }

    /**
     * @param publishPoll the publishPoll to set
     */
    public void setPublishPoll(Boolean publishPoll) {
        this.publishPoll = publishPoll;
    }

    /**
     * @return the closeNotification
     */
    @JsonIgnore
    public Boolean getCloseNotification() {
        return closeNotification;
    }

    /**
     * @param closeNotification the closeNotification to set
     */
    public void setCloseNotification(Boolean closeNotification) {
        this.closeNotification = closeNotification;
    }

    /**
     * @return the showResultsPoll
     */
    @JsonIgnore
    public Boolean getShowResultsPoll() {
        return showResultsPoll;
    }

    /**
     * @param showResultsPoll the showResultsPoll to set
     */
    public void setShowResultsPoll(Boolean showResultsPoll) {
        this.showResultsPoll = showResultsPoll;
    }

    /**
     * @return the hashPoll
     */
    @JsonIgnore
    public String getHashPoll() {
        return hashPoll;
    }

    /**
     * @param hashPoll the hashPoll to set
     */
    public void setHashPoll(String hashPoll) {
        this.hashPoll = hashPoll;
    }

    /**
    * @return the updatedDate
    */
    @JsonIgnore
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
    * @param updatedDate the updatedDate to set
    */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the url
     */
    @JsonIgnore
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the shortUrl
     */
    @JsonIgnore
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * @param shortUrl the shortUrl to set
     */
    public void setShortUrl(final String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
