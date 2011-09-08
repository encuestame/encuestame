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

/**
 * Unit survey bean.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class SurveyBean extends UnitAbstractSurvey implements Serializable {

    /**  Serial. **/
    private static final long serialVersionUID = -2565383479821019696L;

    /****/
    @JsonProperty(value = "sid")
    private Long sid;

    /***/
    @JsonProperty(value = "ticket")
    private Integer ticket;

    /****/
    @JsonProperty(value = "startDate")
    private Date startDate;

    /****/
    @JsonProperty(value = "endDate")
    private Date endDate;

    /****/
    @JsonProperty(value = "dateInterview")
    private Date dateInterview;

    /****/
    @JsonProperty(value = "complete")
    private String complete;

    @JsonProperty(value = "id")
    private UserAccountBean unitUserBean;

    /** **/
    @JsonProperty(value = "userId")
    private Long userId;

    /** **/
    @JsonProperty(value = "ownerUsername")
    private String ownerUsername;

    /** **/
    @JsonProperty(value = "relativeTime")
    private String relativeTime;

    /** **/
    @JsonProperty(value = "totalVotes")
    private Long totalVotes;

    /** **/
    @JsonProperty(value = "itemType")
    private String itemType;

    /**
     * @return the sid
     */
    @JsonIgnore
    public Long getSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(final Long sid) {
        this.sid = sid;
    }

    /**
     * @return the ticket
     */
    @JsonIgnore
    public Integer getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(final Integer ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the startDate
     */
    @JsonIgnore
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    @JsonIgnore
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the dateInterview
     */
    @JsonIgnore
    public Date getDateInterview() {
        return dateInterview;
    }

    /**
     * @param dateInterview the dateInterview to set
     */
    public void setDateInterview(final Date dateInterview) {
        this.dateInterview = dateInterview;
    }

    /**
     * @return the complete
     */
    @JsonIgnore
    public String getComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(final String complete) {
        this.complete = complete;
    }

    /**
     * @return the unitUserBean
     */
    @JsonIgnore
    public UserAccountBean getUnitUserBean() {
        return unitUserBean;
    }

    /**
     * @param unitUserBean the unitUserBean to set
     */
    public void setUnitUserBean(final UserAccountBean unitUserBean) {
        this.unitUserBean = unitUserBean;
    }

    /**
     * @return the userId
     */
    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the ownerUsername
     */
    @JsonIgnore
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * @param ownerUsername the ownerUsername to set
     */
    public void setOwnerUsername(final String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * @return the relativeTime
     */
    @JsonIgnore
    public String getRelativeTime() {
        return relativeTime;
    }

    /**
     * @param relativeTime the relativeTime to set
     */
    public void setRelativeTime(final String relativeTime) {
        this.relativeTime = relativeTime;
    }

    /**
     * @return the totalVotes
     */
    @JsonIgnore
    public Long getTotalVotes() {
        return totalVotes;
    }

    /**
     * @param totalVotes the totalVotes to set
     */
    public void setTotalVotes(final Long totalVotes) {
        this.totalVotes = totalVotes;
    }

    /**
     * @return the itemType
     */
    @JsonIgnore
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    @JsonIgnore
    public void setItemType(final String itemType) {
        this.itemType = itemType;
    }
}
