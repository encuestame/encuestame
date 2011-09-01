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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Survey Pagination.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since August 10, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "survey_pagination")
public class SurveyPagination {

    /****/
    private Long paginationId;

    /****/
    private Integer pageNumber;

    /****/
    private SurveySection surveySection;

    private Survey survey;

    /**
     * @return the paginationId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pagination_id", unique = true, nullable = false)
    public Long getPaginationId() {
        return paginationId;
    }

    /**
     * @param paginationId the paginationId to set
     */
    public void setPaginationId(Long paginationId) {
        this.paginationId = paginationId;
    }


    /**
     * @return the pageNumber
     */
    @Column(name = "pageNumber")
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the surveySection
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ssid", nullable = false)
    public SurveySection getSurveySection() {
        return surveySection;
    }

    /**
     * @param surveySection the surveySection to set
     */
    public void setSurveySection(SurveySection surveySection) {
        this.surveySection = surveySection;
    }

    /**
     * @return the survey
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sid", nullable = false)
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
 }
