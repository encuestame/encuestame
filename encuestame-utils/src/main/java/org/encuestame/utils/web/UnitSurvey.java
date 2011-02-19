/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

public class UnitSurvey extends UnitAbstractSurvey implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -2565383479821019696L;

    /****/
    private Long sid;

    /***/
    private Integer ticket;

    /****/
    private Date startDate;

    /****/
    private Date endDate;

    /****/
    private Date dateInterview;

    /****/
    private String complete;

    private UserAccountBean unitUserBean;

    /**
     * @return the sid
     */
    public Long getSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(Long sid) {
        this.sid = sid;
    }

    /**
     * @return the ticket
     */
    public Integer getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the dateInterview
     */
    public Date getDateInterview() {
        return dateInterview;
    }

    /**
     * @param dateInterview the dateInterview to set
     */
    public void setDateInterview(Date dateInterview) {
        this.dateInterview = dateInterview;
    }

    /**
     * @return the complete
     */
    public String getComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(String complete) {
        this.complete = complete;
    }

    /**
     * @return the unitUserBean
     */
    public UserAccountBean getUnitUserBean() {
        return unitUserBean;
    }

    /**
     * @param unitUserBean the unitUserBean to set
     */
    public void setUnitUserBean(UserAccountBean unitUserBean) {
        this.unitUserBean = unitUserBean;
    }

}
