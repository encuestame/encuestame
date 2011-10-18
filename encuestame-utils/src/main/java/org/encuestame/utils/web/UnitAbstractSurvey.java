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
    private Boolean customMessage;

    /**
     * Custom start message.
     */
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
    private Long numbervotes;

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

    /** **/
    private Long relevance;

    /**
     * @return the customMessage
     */
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
     * @return the numbervotes
     */
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
     * @return the name
     */
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
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the relevance
     */
    public Long getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set
     */
    public void setRelevance(final Long relevance) {
        this.relevance = relevance;
    }
}
