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
package org.encuestame.core.persistence.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SurveyFormat.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "survey_format")
public class SurveyFormat implements java.io.Serializable {

    private Long idSidFormat;
    private String surveyFormatName;
    private Date dateCreated;
    private Set<SurveyGroup> surveyGroups = new  HashSet<SurveyGroup>();
    //private Set<SurveyDetail> surveyDetails = new HashSet<SurveyDetail>(0);

    /**
     * @return idSidFormat
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sid_format", unique = true, nullable = false)
    public Long getIdSidFormat() {
        return this.idSidFormat;
    }

    /**
     * @param idSidFormat idSidFormat
     */
    public void setIdSidFormat(Long idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    /**
     * @return the surveyFormatName
     */
    @Column(name = "name", length = 60)
    public String getSurveyFormatName() {
        return surveyFormatName;
    }

    /**
     * @param surveyFormatName the surveyFormatName to set
     */
    public void setSurveyFormatName(String surveyFormatName) {
        this.surveyFormatName = surveyFormatName;
    }

    /**
     * @return dateCreated
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", length = 0)
    public Date getDateCreated() {
        return this.dateCreated;
    }

    /**
     * @param dateCreated dateCreated
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the surveyGroups
     */
    @ManyToMany()
    @JoinTable(name="survey_group_format",
               joinColumns={@JoinColumn(name="id_sid_format")},
               inverseJoinColumns={@JoinColumn(name="sg_id")})
    public Set<SurveyGroup> getSurveyGroups() {
        return surveyGroups;
    }

    /**
     * @param surveyGroups the surveyGroups to set
     */
    public void setSurveyGroups(Set<SurveyGroup> surveyGroups) {
        this.surveyGroups = surveyGroups;
    }
}
