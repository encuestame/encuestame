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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Project.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version  $Id$
 */
@Entity
@Table(name = "project")
public class Project {

    private Long proyectId;
    private CatState stateProject;
    private String projectDescription;
    private String projectInfo;
    private Date projectDateStart;
    private Date projectDateFinish;
    private SecUsers users;
    private Set<SurveyGroup> surveyGroups = new HashSet<SurveyGroup>();
    private Set<CatLocation> locations = new HashSet<CatLocation>();
    private Set<SecGroups> groups = new HashSet<SecGroups>();
    private Set<SecUserSecondary> secUserSecondaries = new HashSet<SecUserSecondary>();

    private Priority priority = Priority.MEDIUM;

    /**
     */
    public enum Priority {
    /**
     *
     */
    HIGH,
    /**
     *
     */
    MEDIUM,
    /**
     *
     */
    LOW;}

    /**
     * @return proyectId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id", unique = true, nullable = false)
    public Long getProyectId() {
        return this.proyectId;
    }

    /**
     * @param proyectId proyectId
     */
    public void setProyectId(Long proyectId) {
        this.proyectId = proyectId;
    }

    /**
     * @return projectDescription
     */
    @Column(name = "description", nullable = false)
    public String getProjectDescription() {
        return this.projectDescription;
    }

    /**
     * @param projectDescription projectDescription
     */
    public void setProjectDescription(final String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     * @return projectInfo
     */
    @Column(name = "info", nullable = false)
    public String getProjectInfo() {
        return this.projectInfo;
    }

    /**
     * @param projectInfo projectInfo
     */
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    /**
     * @return projectDateStart
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_start", nullable = false, length = 0)
    public Date getProjectDateStart() {
        return this.projectDateStart;
    }

    /**
     * @param projectDateStart projectDateStart
     */
    public void setProjectDateStart(final Date projectDateStart) {
        this.projectDateStart = projectDateStart;
    }

    /**
     * @return projectDateFinish
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_finish", length = 0)
    public Date getProjectDateFinish() {
        return this.projectDateFinish;
    }

    /**
     * @param projectDateFinish projectDateFinish
     */
    public void setProjectDateFinish(final Date projectDateFinish) {
        this.projectDateFinish = projectDateFinish;
    }

    /**
     * @return the locations
     */
    @ManyToMany()
    @JoinTable(name="project_locations",
              joinColumns={@JoinColumn(name="cat_id_project")},
              inverseJoinColumns={@JoinColumn(name="cat_id_loc")})
    public Set<CatLocation> getLocations() {
        return locations;
    }

    /**
     * @param locations the locations to set
     */
    public void setLocations(final Set<CatLocation> locations) {
        this.locations = locations;
    }

    /**
     * @return the groups
     */
    @ManyToMany()
    @JoinTable(name="sec_project_group",
              joinColumns={@JoinColumn(name="cat_id_project")},
              inverseJoinColumns={@JoinColumn(name="sec_id_group")})
    public Set<SecGroups> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(final Set<SecGroups> groups) {
        this.groups = groups;
    }

    /**
     * @return the secUserSecondaries
     */
    @ManyToMany()
    @JoinTable(name="sec_user_project",
               joinColumns={@JoinColumn(name="cat_id_project")},
               inverseJoinColumns={@JoinColumn(name="sec_id_secondary")})
    public Set<SecUserSecondary> getSecUserSecondaries() {
        return secUserSecondaries;
    }

    /**
     * @param secUserSecondaries the secUserSecondaries to set
     */
    public void setSecUserSecondaries(final Set<SecUserSecondary> secUserSecondaries) {
        this.secUserSecondaries = secUserSecondaries;
    }

    /**
     * @return the stateProject
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cat_state_id", nullable = false)
    public CatState getStateProject() {
        return stateProject;
    }

    /**
     * @param stateProject the stateProject to set
     */
    public void setStateProject(final CatState stateProject) {
        this.stateProject = stateProject;
    }

    /**
     * @return the surveyGroups
     */
    @ManyToMany()
    @JoinTable(name="survey_group_project",
               joinColumns={@JoinColumn(name="cat_id_project")},
               inverseJoinColumns={@JoinColumn(name="id_sid_format")})
    public Set<SurveyGroup> getSurveyGroups() {
        return surveyGroups;
    }

    /**
     * @param surveyGroups the surveyGroups to set
     */
    public void setSurveyGroups(final Set<SurveyGroup> surveyGroups) {
        this.surveyGroups = surveyGroups;
    }

    /**
     * @return the users
     */
    @ManyToOne()
    public SecUsers getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(SecUsers users) {
        this.users = users;
    }

    /**
     * @return the priority
     */
    @Column(name="priority")
    @Enumerated(EnumType.STRING)
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(final Priority priority) {
        this.priority = priority;
    }
}
