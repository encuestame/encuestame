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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.encuestame.utils.json.QuestionBean;

/**
 * Unit Poll Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  March 15, 2009
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollBean extends AbstractUnitSurvey implements Serializable{

    /**
     * Serial
     */
    private static final long serialVersionUID = 7022698996782621900L;

    /***/
    @JsonProperty(value = "id")
    private Long id;

    /***/
    @JsonProperty(value = "completed_poll")
    private Boolean completedPoll;

    /***/
    @JsonProperty(value = "creation_date")
    private String creationDate;

    /***/
    @JsonProperty(value = "question")
    private QuestionBean questionBean = new QuestionBean();

    /***/
    @JsonProperty(value = "finish_date")
    private Date finishDate;

    /***/
    @JsonProperty(value = "published")
    private Boolean publishPoll;

    /***/
    @JsonProperty(value = "close_notification")
    private Boolean closeNotification;

    /***/
    @JsonProperty(value = "show_resultsPoll")
    private Boolean showResultsPoll;


    /** **/
    @JsonProperty(value = "updated_date")
    private Date updatedDate;

    /** **/
    @JsonProperty(value = "url")
    private String url;

    /** **/
    @JsonProperty(value = "short_url")
    private String shortUrl;

    /** **/
    @JsonProperty(value = "isHidden")
    private Boolean isHidden;


    /** **/
    @JsonProperty(value = "isPasswordProtected")
    private Boolean isPasswordProtected;


    /** **/
    @JsonProperty(value = "password")
    private String password;


    /**
     *
     */
    @JsonProperty(value = "poll_results")
    private List<PollBeanResult> resultsBean = new ArrayList<PollBeanResult>();

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

    /**
     * @return the resultsBean
     */
    public List<PollBeanResult> getResultsBean() {
        return resultsBean;
    }

    /**
     * @param resultsBean the resultsBean to set
     */
    public void setResultsBean(List<PollBeanResult> resultsBean) {
        this.resultsBean = resultsBean;
    }

    /**
	 * @return the isHidden
	 */
	public Boolean getIsHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden the isHidden to set
	 */
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * @return the isPasswordProtected
	 */
	public Boolean getIsPasswordProtected() {
		return isPasswordProtected;
	}

	/**
	 * @param isPasswordProtected the isPasswordProtected to set
	 */
	public void setIsPasswordProtected(Boolean isPasswordProtected) {
		this.isPasswordProtected = isPasswordProtected;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PollBean [id=" + id + ", completedPoll=" + completedPoll
                + ", creationDate=" + creationDate + ", questionBean="
                + questionBean + ", finishDate=" + finishDate
                + ", publishPoll=" + publishPoll + ", closeNotification="
                + closeNotification + ", showResultsPoll=" + showResultsPoll
                + ", updatedDate=" + updatedDate + ", url=" + url
                + ", shortUrl=" + shortUrl + ", resultsBean=" + resultsBean
                + ", getHashTags()=" + getHashTags() + ", getOwnerUsername()="
                + getOwnerUsername() + ", getRelativeTime()="
                + getRelativeTime() + ", getTotalVotes()=" + getTotalVotes()
                + ", getItemType()=" + getItemType() + ", getLikeVote()="
                + getLikeVote() + ", getDislikeVote()=" + getDislikeVote()
                + ", getCreateDate()=" + getCreateDate() + ", getRelevance()="
                + getRelevance() + ", getFavorite()=" + getFavorite()
                + ", getHits()=" + getHits() + ", getLatitude()="
                + getLatitude() + ", getLongitude()=" + getLongitude()
                + ", getAdditionalInfo()=" + getAdditionalInfo()
                + ", getShowComments()=" + getShowComments()
                + ", getFolderId()=" + getFolderId()
                + ", getIsShowAdditionalInfo()=" + getIsShowAdditionalInfo()
                + ", getIsCloseAfterDate()=" + getIsCloseAfterDate()
                + ", getClosedDate()=" + getClosedDate()
                + ", getIsCloseAfterQuota()=" + getIsCloseAfterQuota()
                + ", getClosedQuota()=" + getClosedQuota()
                + ", getIsIpRestricted()=" + getIsIpRestricted()
                + ", getIpRestricted()=" + getIpRestricted()
                + ", getMultipleResponse()=" + getMultipleResponse()
                + ", getIsShowResults()=" + getIsShowResults()
                + ", getTotalComments()=" + getTotalComments()
                + ", getHashtagAsString()=" + getHashtagAsString()
                + ", getIsPasswordRestriction()=" + getIsPasswordRestriction()
                + ", getCreateDateComparable()=" + getCreateDateComparable()
                + ", getShowResults()=" + getShowResults()
                + ", getTypeSearchResult()=" + getTypeSearchResult()
                + ", getComments()=" + getComments() + ", getClass()="
                + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }


}
