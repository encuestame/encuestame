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
package org.encuestame.persistence.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.enums.CommentOptions;

/**
 * Abstract Survey.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 21, 2010
 */
@MappedSuperclass
public abstract class AbstractSurvey extends AbstractGeoPoint {

    /**
     * Use a custom start message.
     */
    private Boolean customMessage;

    /**
     * Custom start message.
     */
    private String customStartMessages;

    /**
     * Define which user create this tweetPoll.
     */
    private UserAccount editorOwner;

    /**
     * Define the account owner of the item.
     */
    private Account accountItem;

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
    private CustomFinalMessage customFinalMessage;

    /**
     * Custom Multiple Response.
     */
    private MultipleResponse multipleResponse;

     /**
      * Optional title.
      */
     private String optionalTitle;

     /**
      * Password protected to view and take part in the survey.
      */
     private Boolean passwordRestrictions;

     /**
      * IP Restriction.
      */
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
     private Boolean closeAfterDate;

     /**
      * Close Date.
      */
     private Date closedDate;

     /**
      * Survey Closing after quota.
      */
    private Boolean closeAfterquota;

    /**
     * Close Quota.
     */
    private Integer closedQuota;

    /**
     * Show Results
     */
    private Boolean showResults;

    /**
     * Show Comments Option.
     */
    private CommentOptions showComments;

   /**
    * Number votes for Survey and Poll.
    */
    private Long numbervotes;

    /**
     * Number Hits or visits
     */
    private Long hits;

    /**
     * Show Option- Additional Info in Questions
     */
    private Boolean showAdditionalInfo;

    /**
     *  Additional Info in Questions.
     */
    private String additionalInfo;

    /**
     * Send Notifications after completed survey or Poll.
     */
    private Boolean notifications;

    /**
     * Survey or Poll Name.
     */
    private String name;

    /** **/
    private Long relevance;

    /** Like option**/
    private Long likeVote;

    /** Unlike **/
    private Long dislikeVote;

    /**
     * Mark as favourites.
     */
    private Boolean favourites = false;

    /**
     * Update Date
     */
    private Date updatedDate;

    /**
     * End date of survey.
     ***/
    private Date endDate;

    /**
     * Survey created at.
     ***/
    private Date createdAt;

    /**
     * Hash Tags.
     **/
    private Set<HashTag> hashTags = new HashSet<HashTag>();

    /**
     * @return the customMessage.
     */
    @Column(name = "custom_message")
    public Boolean getCustomMessage() {
        return customMessage;
    }

    /**
     * @param customMessage the customMessage to set.
     */
    public void setCustomMessage(final Boolean customMessage) {
        this.customMessage = customMessage;
    }

    /**
     * @return the customStartMessages.
     */
    @Column(name = "custom_start_message")
    public String getCustomStartMessages() {
        return customStartMessages;
    }

    /**
     * @param customStartMessages the customStartMessages to set.
     */
    public void setCustomStartMessages(final String customStartMessages) {
        this.customStartMessages = customStartMessages;
    }

    /**
     * @return the optionalTitle.
     */
    @Column(name = "optional_title")
    public String getOptionalTitle() {
        return optionalTitle;
    }

    /**
     * @param optionalTitle the optionalTitle to set.
     */
    public void setOptionalTitle(final String optionalTitle) {
        this.optionalTitle = optionalTitle;
    }

    /**
     * @return the passwordRestrictions.
     */
    @Column(name = "password_restrictions")
    public Boolean getPasswordRestrictions() {
        return passwordRestrictions;
    }

    /**
     * @param passwordRestrictions the passwordRestrictions to set.
     */
    public void setPasswordRestrictions(final Boolean passwordRestrictions) {
        this.passwordRestrictions = passwordRestrictions;
    }

    /**
     * @return the ipRestriction.
     */
    @Column(name = "ip_restrictions")
    public Boolean getIpRestriction() {
        return ipRestriction;
    }

    /**
     * @param ipRestriction the ipRestriction to set.
     */
    public void setIpRestriction(final Boolean ipRestriction) {
        this.ipRestriction = ipRestriction;
    }

    /**
     * @return the passProtection.
     */
    @Column(name = "password_protection")
    public String getPassProtection() {
        return passProtection;
    }

    /**
     * @param passProtection the passProtection to set.
     */
    public void setPassProtection(final String passProtection) {
        this.passProtection = passProtection;
    }

    /**
     * @return the ipProtection.
     */
    @Column(name = "ip_protection")
    public String getIpProtection() {
        return IpProtection;
    }

    /**
     * @param ipProtection the ipProtection to set.
     */
    public void setIpProtection(final String ipProtection) {
        IpProtection = ipProtection;
    }

    /**
     * @return the closeAfterDate.
     */
    @Column(name = "closeAfterDate")
    public Boolean getCloseAfterDate() {
        return closeAfterDate;
    }

    /**
     * @param closeAfterDate the closeAfterDate to set.
     */
    public void setCloseAfterDate(final Boolean closeAfterDate) {
        this.closeAfterDate = closeAfterDate;
    }

    /**
     * @return the closedDate.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "close_date")
    public Date getClosedDate() {
        return closedDate;
    }

    /**
     * @param closedDate the closedDate to set.
     */
    public void setClosedDate(final Date closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * @return the closeAfterquota.
     */
    @Column(name = "close_after_quota")
    public Boolean getCloseAfterquota() {
        return closeAfterquota;
    }

    /**
     * @param closeAfterquota the closeAfterquota to set.
     */
    public void setCloseAfterquota(final Boolean closeAfterquota) {
        this.closeAfterquota = closeAfterquota;
    }

    /**
     * @return the closedQuota.
     */
    @Column(name = "closed_quota")
    public Integer getClosedQuota() {
        return closedQuota;
    }

    /**
     * @param closedQuota the closedQuota to set.
     */
    public void setClosedQuota(final Integer closedQuota) {
        this.closedQuota = closedQuota;
    }

    /**
     * @return the customFinalMessage
     */
    @Column(name = "custom_final_message")
    @Enumerated(EnumType.ORDINAL)
    public CustomFinalMessage getCustomFinalMessage() {
        return customFinalMessage;
    }

    /**
     * @param customFinalMessage the customFinalMessage to set
     */
    public void setCustomFinalMessage(final CustomFinalMessage customFinalMessage) {
        this.customFinalMessage = customFinalMessage;
    }

    /**
     * @return the multipleResponse
     */
    @Column(name = "multiple_response")
    @Enumerated(EnumType.ORDINAL)
    public MultipleResponse getMultipleResponse() {
        return multipleResponse;
    }

    /**
     * @param multipleResponse the multipleResponse to set
     */
    public void setMultipleResponse(final MultipleResponse multipleResponse) {
        this.multipleResponse = multipleResponse;
    }

    /**
     * @return the showResults
     */
    @Column(name = "show_results")
    public Boolean getShowResults() {
        return showResults;
    }

    /**
     * @param showResults the showResults to set
     */
    public void setShowResults(final Boolean showResults) {
        this.showResults = showResults;
    }

    /**
     * @return the showComments
     */
    public CommentOptions getShowComments() {
        return showComments;
    }

    /**
     * @param showComments the showComments to set
     */
    @Column(name = "comment_Option")
    @Enumerated(EnumType.ORDINAL)
    public void setShowComments(final CommentOptions showComments) {
        this.showComments = showComments;
    }

    /**
     * @return the showAdditionalInfo
     */
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
    @Column(name = "additional_info")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalInfo(final String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * @return the notifications
     */
    @Column(name = "notifications")
    public Boolean getNotifications() {
        return notifications;
    }

    /**
     * @param notifications the notifications to set
     */
    public void setNotifications(final Boolean notifications) {
        this.notifications = notifications;
    }

    /**
     * @return the numbervotes
     */
    @Column(name = "numbervotes")
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
    @Column(name = "hits")
    public Long getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(final Long hits) {
        this.hits = hits;
    }

    /**
     * @return the name
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the editorOwner
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "editor")
    public UserAccount getEditorOwner() {
        return editorOwner;
    }

    /**
     * @param editorOwner the editorOwner to set
     */
    public void setEditorOwner(final UserAccount editorOwner) {
        this.editorOwner = editorOwner;
    }

    /**
     * @return the relevance
     */
    @Column(name = "relevance", nullable = true)
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
     * @return the likeVote
     */
    @Column(name = "like_vote", nullable = true)
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
    @Column(name = "dislike_vote", nullable = true)
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
     * @return the favourites
     */
    @Column(name = "favourites", nullable = true)
    public Boolean getFavourites() {
        return favourites;
    }

    /**
     * @param favourites the favourites to set
     */
    public void setFavourites(final Boolean favourites) {
        this.favourites = favourites;
    }

    /**
     * @return endDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = true, length = 0)
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * @param endDate endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    /**
    * @return the updatedDate
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", nullable = true)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
    * @param updatedDate the updatedDate to set
    */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the accountItem
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    public Account getAccountItem() {
        return accountItem;
    }

    /**
     * @param accountItem the accountItem to set
     */
    public void setAccountItem(final Account accountItem) {
        this.accountItem = accountItem;
    }

    /**
     * @return the createdAt
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
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
     * @return the hashTags
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "poll_hashtags",
               joinColumns = {@JoinColumn(name = "poll_id")},
               inverseJoinColumns = {@JoinColumn(name = "hastag_id")})
    public Set<HashTag> getHashTags() {
        return hashTags;
    }

    /**
     * @param hashTags the hashTags to set
     */
    public void setHashTags(Set<HashTag> hashTags) {
        this.hashTags = hashTags;
    }
}