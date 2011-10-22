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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Unit Abstract Survey.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since November 8, 2010
 * @version $Id: $
 */
public abstract class UnitAbstractSurvey implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -7080756480415569746L;

    /**
     * Use a custom start message.
     */
    @JsonProperty(value = "custom_message")
    private Boolean customMessage;

    /**
     * Custom start message.
     */
    @JsonProperty(value = "custom_start_messages")
    private String customStartMessages;

    /**
     * Multiple Responses.
     */
    public enum MultipleResponse {
        /** Allow Multiple Responses. **/
        MULTIPLE,
        /** One Response Per Computer. **/
        SINGLE
    }

    /**
     * Custom Final Option.
     * Final Message After Survey.
     */
    public enum CustomFinalMessage {
        /** Final Message After completed survey. **/
        FINALMESSAGE,
        /** URL Redirect. **/
        URLREDIRECT
    }

    /**
     * Custom Final Message.
     */
    @JsonProperty(value = "custom_final_message")
    private CustomFinalMessage customFinalMessage;

    /**
     * Custom Multiple Response.
     */
    @JsonProperty(value = "multiple_response")
    private MultipleResponse multipleResponse;

    /**
     * Show progress bar.
     */
    @JsonProperty(value = "show_progress_bar")
    private Boolean showProgressBar;

     /**
      * Optional title.
      */
    @JsonProperty(value = "optional_title")
    private String optionalTitle;

     /**
      * Password protected to view and take part in the survey.
      */
    @JsonProperty(value = "password_restrictions")
    private Boolean passwordRestrictions;

     /**
      * IP Restriction.
      */
    @JsonProperty(value = "ip_restriction")
    private Boolean ipRestriction;

     /**
      * Password Protection.
      */ 
    private String passProtection;

    /**
     * IP Restictions.
     */ 
    private String IpProtection;

    /**
     * Survey Closing after date.
     */
    @JsonProperty(value = "close_after_date")
    private Boolean closeAfterDate;

     /**
      * Close Date.
      */
    @JsonProperty(value = "closed_date")
    private Date closedDate;

     /**
      * Survey Closing after quota.
      */
    @JsonProperty(value = "close_after_quota")
    private Boolean closeAfterquota;

    /**
     * Close Quota.
     */
    @JsonProperty(value = "closed_quota")
    private Integer closedQuota;

    /**
     * Show Results
     */
    @JsonProperty(value = "show_results")
    private Boolean showResults;

    /** **/
    @JsonProperty(value = "show_comments")
    private showComments showComments;

   /**
    * Number votes for Survey and Poll.
    */
    @JsonProperty(value = "number_votes")
    private Long numbervotes;

    /**
     * Number Hits or visits
     */
    @JsonProperty(value = "hits")
    private Long hits;

    /**
     * Show Comments Option.
     */
    public enum showComments {
        /** Restrict Comments. **/
        RESTRICT,
        /** Approve Comments. **/
        APPROVE
    }

    /**
     * Show Option- Additional Info in Questions
     */
    @JsonProperty(value = "show_additional_info")
    private Boolean showAdditionalInfo;

    /**
     *  Additional Info in Questions.
     */
    @JsonProperty(value = "additional_info")
    private String additionalInfo;

    /**
     * Send Notifications after completed survey or Poll.
     */
    @JsonProperty(value = "notifications")
    private Boolean notifications;

    /**
     * Survey or Poll Name.
     */
    @JsonProperty(value = "name")
    private String name;

    /** **/
    @JsonProperty(value = "relevance")
    private Long relevance;

    /**
     * Survey created at.
     ***/
    @JsonProperty(value = "created_at")
    private Date createdAt;

    /** Like option**/
    @JsonProperty(value = "created_at")
    private Long likeVote;

    /** Unlike **/
    @JsonProperty(value = "dislike_vote")
    private Long dislikeVote;

    /** Mark as favorites. **/
    @JsonProperty(value = "favorites")
    private Boolean favorites;

    /** List of HashTags. **/
    @JsonProperty(value = "hashtags")
    private List<HashTagBean> hashTags = new ArrayList<HashTagBean>();

    /**
     * @return the customMessage
     */
    @JsonIgnore
    public Boolean getCustomMessage() {
        return customMessage;
    }

    /**
     * @param customMessage the customMessage to set
     */
    public void setCustomMessage(Boolean customMessage) {
        this.customMessage = customMessage;
    }

    /**
     * @return the customStartMessages
     */
    @JsonIgnore
    public String getCustomStartMessages() {
        return customStartMessages;
    }

    /**
     * @param customStartMessages the customStartMessages to set
     */
    public void setCustomStartMessages(String customStartMessages) {
        this.customStartMessages = customStartMessages;
    }

    /**
     * @return the customFinalMessage
     */
    @JsonIgnore
    public CustomFinalMessage getCustomFinalMessage() {
        return customFinalMessage;
    }

    /**
     * @param customFinalMessage the customFinalMessage to set
     */
    public void setCustomFinalMessage(CustomFinalMessage customFinalMessage) {
        this.customFinalMessage = customFinalMessage;
    }

    /**
     * @return the multipleResponse
     */
    @JsonIgnore
    public MultipleResponse getMultipleResponse() {
        return multipleResponse;
    }

    /**
     * @param multipleResponse the multipleResponse to set
     */
    public void setMultipleResponse(MultipleResponse multipleResponse) {
        this.multipleResponse = multipleResponse;
    }

    /**
     * @return the showProgressBar
     */
    @JsonIgnore
    public Boolean getShowProgressBar() {
        return showProgressBar;
    }

    /**
     * @param showProgressBar the showProgressBar to set
     */
    public void setShowProgressBar(Boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    /**
     * @return the optionalTitle
     */
    @JsonIgnore
    public String getOptionalTitle() {
        return optionalTitle;
    }

    /**
     * @param optionalTitle the optionalTitle to set
     */
    public void setOptionalTitle(String optionalTitle) {
        this.optionalTitle = optionalTitle;
    }

    /**
     * @return the passwordRestrictions
     */
    @JsonIgnore
    public Boolean getPasswordRestrictions() {
        return passwordRestrictions;
    }

    /**
     * @param passwordRestrictions the passwordRestrictions to set
     */
    public void setPasswordRestrictions(Boolean passwordRestrictions) {
        this.passwordRestrictions = passwordRestrictions;
    }

    /**
     * @return the ipRestriction
     */
    @JsonIgnore
    public Boolean getIpRestriction() {
        return ipRestriction;
    }

    /**
     * @param ipRestriction the ipRestriction to set
     */
    public void setIpRestriction(Boolean ipRestriction) {
        this.ipRestriction = ipRestriction;
    }

    /**
     * @return the passProtection
     */
    @JsonIgnore
    public String getPassProtection() {
        return passProtection;
    }

    /**
     * @param passProtection the passProtection to set
     */
    public void setPassProtection(String passProtection) {
        this.passProtection = passProtection;
    }

    /**
     * @return the ipProtection
     */
    @JsonIgnore
    public String getIpProtection() {
        return IpProtection;
    }

    /**
     * @param ipProtection the ipProtection to set
     */
    public void setIpProtection(String ipProtection) {
        IpProtection = ipProtection;
    }

    /**
     * @return the closeAfterDate
     */
    @JsonIgnore
    public Boolean getCloseAfterDate() {
        return closeAfterDate;
    }

    /**
     * @param closeAfterDate the closeAfterDate to set
     */
    public void setCloseAfterDate(Boolean closeAfterDate) {
        this.closeAfterDate = closeAfterDate;
    }

    /**
     * @return the closedDate
     */
    @JsonIgnore
    public Date getClosedDate() {
        return closedDate;
    }

    /**
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * @return the closeAfterquota
     */
    @JsonIgnore
    public Boolean getCloseAfterquota() {
        return closeAfterquota;
    }

    /**
     * @param closeAfterquota the closeAfterquota to set
     */
    public void setCloseAfterquota(Boolean closeAfterquota) {
        this.closeAfterquota = closeAfterquota;
    }

    /**
     * @return the closedQuota
     */
    @JsonIgnore
    public Integer getClosedQuota() {
        return closedQuota;
    }

    /**
     * @param closedQuota the closedQuota to set
     */
    public void setClosedQuota(Integer closedQuota) {
        this.closedQuota = closedQuota;
    }

    /**
     * @return the showResults
     */
    @JsonIgnore
    public Boolean getShowResults() {
        return showResults;
    }

    /**
     * @param showResults the showResults to set
     */
    public void setShowResults(Boolean showResults) {
        this.showResults = showResults;
    }

    /**
     * @return the showComments
     */
    @JsonIgnore
    public showComments getShowComments() {
        return showComments;
    }

    /**
     * @param showComments the showComments to set
     */
    public void setShowComments(showComments showComments) {
        this.showComments = showComments;
    }

    /**
     * @return the numbervotes
     */
    @JsonIgnore
    public Long getNumbervotes() {
        return numbervotes;
    }

    /**
     * @param numbervotes the numbervotes to set
     */
    public void setNumbervotes(final Long numbervotes) {
        this.numbervotes = numbervotes;
    }

    /**
     * @return the hits
     */
    @JsonIgnore
    public Long getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(Long hits) {
        this.hits = hits;
    }

    /**
     * @return the showAdditionalInfo
     */
    @JsonIgnore
    public Boolean getShowAdditionalInfo() {
        return showAdditionalInfo;
    }

    /**
     * @param showAdditionalInfo the showAdditionalInfo to set
     */
    public void setShowAdditionalInfo(Boolean showAdditionalInfo) {
        this.showAdditionalInfo = showAdditionalInfo;
    }

    /**
     * @return the additionalInfo
     */
    @JsonIgnore
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * @return the notifications
     */
    @JsonIgnore
    public Boolean getNotifications() {
        return notifications;
    }

    /**
     * @param notifications the notifications to set
     */
    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    /**
     * @return the name
     */
    @JsonIgnore
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the relevance
     */
    @JsonIgnore
    public Long getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set
     */
    public void setRelevance(final Long relevance) {
        this.relevance = relevance;
    }

    /**
     * @return the createdAt
     */
    @JsonIgnore
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the likeVote
     */
    @JsonIgnore
    public Long getLikeVote() {
        return likeVote;
    }

    /**
     * @param likeVote the likeVote to set
     */
    public void setLikeVote(final Long likeVote) {
        this.likeVote = likeVote;
    }

    /**
     * @return the dislikeVote
     */
    @JsonIgnore
    public Long getDislikeVote() {
        return dislikeVote;
    }

    /**
     * @param dislikeVote the dislikeVote to set
     */
    public void setDislikeVote(final Long dislikeVote) {
        this.dislikeVote = dislikeVote;
    }

    /**
     * @return the favorites
     */
    @JsonIgnore
    public Boolean getFavorites() {
        return favorites;
    }

    /**
     * @param favorites the favorites to set
     */
    public void setFavorites(final Boolean favorites) {
        this.favorites = favorites;
    }

    /**
     * @return the hashTags
     */
    public List<HashTagBean> getHashTags() {
        return hashTags;
    }

    /**
     * @param hashTags the hashTags to set
     */
    public void setHashTags(final List<HashTagBean> hashTags) {
        this.hashTags = hashTags;
    }
}
