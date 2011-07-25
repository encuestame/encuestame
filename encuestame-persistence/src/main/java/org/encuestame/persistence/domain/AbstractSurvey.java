/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.security.UserAccount;

/**
 * Abstract Survey.
 *
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since September 21, 2010
 * @version $Id: $
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
     * Show progress bar.
     */
     private Boolean showProgressBar;

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

    private showComments showComments;

   /**
    * Number votes for Survey and Poll.
    */
    private Integer numbervotes;

    /**
     * Number Hits or visits
     */
    private Integer hits;

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
     * @return the showProgressBar.
     */
    @Column(name = "show_progress_bar")
    public Boolean getShowProgressBar() {
        return showProgressBar;
    }

    /**
     * @param showProgressBar the showProgressBar to set.
     */
    public void setShowProgressBar(final Boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
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
    @Enumerated(EnumType.STRING)
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
    @Enumerated(EnumType.STRING)
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
     * @return the numbervotes
     */
    @Column(name = "numbervotes")
    public Integer getNumbervotes() {
        return numbervotes;
    }

    /**
     * @param numbervotes the numbervotes to set
     */
    public void setNumbervotes(Integer numbervotes) {
        this.numbervotes = numbervotes;
    }

    /**
     * @return the hits
     */
    @Column(name = "hits")
    public Integer getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(Integer hits) {
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
    public void setName(String name) {
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
}