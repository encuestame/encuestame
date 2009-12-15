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

/**
 * SurveyGroup.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "survey_group")
public class SurveyGroup implements java.io.Serializable {

    private Long sgId;
    private String groupName;
    private Date dateCreate;
    private CatState catState;
    private Set<SurveyFormat> surveyFormats = new HashSet<SurveyFormat>();
    private Set<Project> projects = new HashSet<Project>();

    /**
     * @return sgId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sg_id", unique = true, nullable = false)
    public Long getSgId() {
        return this.sgId;
    }

    /**
     * @param sgId sgId
     */
    public void setSgId(Long sgId) {
        this.sgId = sgId;
    }

    /**
     * @return groupName
     */
    @Column(name = "group_name", length = 60)
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * @param groupName groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return dateCreate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create", length = 0)
    public Date getDateCreate() {
        return this.dateCreate;
    }

    /**
     * @param dateCreate dateCreate
     */
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    /**
     * @return the catState
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cat_state_id_survey_group", nullable = false)
    public CatState getCatState() {
        return catState;
    }

    /**
     * @param catState the catState to set
     */
    public void setCatState(CatState catState) {
        this.catState = catState;
    }

    /**
     * @return the surveyFormats
     */
    @ManyToMany()
    @JoinTable(name="survey_group_format",
               joinColumns={@JoinColumn(name="sg_id")},
               inverseJoinColumns={@JoinColumn(name="id_sid_format")})
    public Set<SurveyFormat> getSurveyFormats() {
        return surveyFormats;
    }

    /**
     * @param surveyFormats the surveyFormats to set
     */
    public void setSurveyFormats(Set<SurveyFormat> surveyFormats) {
        this.surveyFormats = surveyFormats;
    }

    /**
     * @return the projects
     */
    @ManyToMany()
    @JoinTable(name="survey_group_project",
               joinColumns={@JoinColumn(name="id_sid_format")},
               inverseJoinColumns={@JoinColumn(name="cat_id_project")})
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
