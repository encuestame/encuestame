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
package org.encuestame.persistence.domain.survey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Project;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Surveys.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Indexed(index="Survey")
@Table(name = "surveys")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Survey extends AbstractSurvey {

    /****/
    private Long sid;

    /****/
    private Integer ticket;

    /****/
    private Date dateInterview;

    /****/
    private String complete;

    /****/
    private SurveyFolder surveysfolder;

    /** {@link HashTag} **/
    private Project project;

    /**
     * Date init of survey.
     * **/
    private Date startDate;

    /** Scheduled Survey. **/
    @Deprecated // moved to parent class
    private Boolean scheduleSurvey;

    /**
     * Show progress bar.
     */
     private Boolean showProgressBar;

     /** Survey slug name**/
     private String surveySlugName;

     /**
      * Hash Tags.
      **/
     private Set<HashTag> hashTags = new HashSet<HashTag>();

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
     * @return the ticket
     */
    @Column(name = "ticket")
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

    /**
     * @return the project
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(final Project project) {
        this.project = project;
    }

    /**
     * @return startDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", length = 0)
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
     * @return the scheduleSurvey
     */
    @Column(name = "is_Schedule")
    public Boolean getScheduleSurvey() {
        return scheduleSurvey;
    }

    /**
     * @param scheduleSurvey the scheduleSurvey to set
     */
    public void setScheduleSurvey(final Boolean scheduleSurvey) {
        this.scheduleSurvey = scheduleSurvey;
    }

    /**
     * @return the surveySlugName
     */
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "survey_slug_name", nullable = true)
    public String getSurveySlugName() {
        return surveySlugName;
    }

    /**
     * @param surveySlugName the surveySlugName to set
     */
    public void setSurveySlugName(final String surveySlugName) {
        this.surveySlugName = surveySlugName;
    }

    /**
     * @return the hashTags
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "survey_hashtags",
               joinColumns = {@JoinColumn(name = "sid")},
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
