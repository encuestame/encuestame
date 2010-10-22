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
package org.encuestame.persistence.domain.survey;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.security.SecUser;

/**
 * Surveys.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "surveys")
public class Surveys extends AbstractSurvey {

    private Long sid;
    private SecUser secUsers;
    private Integer ticket;
    private Date startDate;
    private Date endDate;
    private Date dateInterview;
    private String complete;
    private SurveyFormat surveyFormat;
    private SurveyFolder surveysfolder;

    /**
     * @return sid
     */
    @Id
    //TODO: need add autoincrement
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sid", unique = true, nullable = false)
    public Long getSid() {
        return this.sid;
    }

    /**
     * @param sid sid
     */
    public void setSid(Long sid) {
        this.sid = sid;
    }

    /**
     * @return secUsers
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUser getSecUsers() {
        return this.secUsers;
    }

    /**
     * @param secUsers secUsers
     */
    public void setSecUsers(SecUser secUsers) {
        this.secUsers = secUsers;
    }

    /**
     * @return ticket
     */
    @Column(name = "ticket", nullable = false)
    public int getTicket() {
        return this.ticket;
    }

    /**
     * @param ticket ticket
     */
    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    /**
     * @return startDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, length = 0)
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * @param startDate startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return endDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false, length = 0)
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
     * @return dateInterview
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "date_interview", length = 0)
    public Date getDateInterview() {
        return this.dateInterview;
    }

    /**
     * @param dateInterview dateInterview
     */
    public void setDateInterview(Date dateInterview) {
        this.dateInterview = dateInterview;
    }

    /**
     * @return complete
     */
    @Column(name = "complete", length = 2)
    public String getComplete() {
        return this.complete;
    }

    /**
     * @param complete complete
     */
    public void setComplete(String complete) {
        this.complete = complete;
    }

    /**
     * @return the surveyFormat
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_sid_format", nullable = false)
    public SurveyFormat getSurveyFormat() {
        return surveyFormat;
    }

    /**
     * @param surveyFormat the surveyFormat to set
     */
    public void setSurveyFormat(SurveyFormat surveyFormat) {
        this.surveyFormat = surveyFormat;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the surveysfolder
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "survey_folder")
    public SurveyFolder getSurveysfolder() {
        return surveysfolder;
    }

    /**
     * @param surveysfolder the surveysfolder to set
     */
    public void setSurveysfolder(SurveyFolder surveysfolder) {
        this.surveysfolder = surveysfolder;
    }

}
