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
package org.encuestame.persistence.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Project.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version  $Id$
 */
@Entity
@Table(name = "project")
@Indexed(index="Project")
public class Project {

    private Long proyectId;
    private String projectDescription;
    private String projectName;
    private Status projectStatus = Status.ACTIVE;
    private String projectInfo;
    private Date projectDateStart;
    private Date projectDateFinish;
    private Account users;
    private Priority priority = Priority.MEDIUM;
    private UserAccount lead;
    private Boolean notifyMembers;
    private Boolean hideProject;
    private Boolean published;

    /** Survey Groups on the project. **/
    private Set<SurveyGroup> surveyGroups = new HashSet<SurveyGroup>();
    /** Locations selected for this project. **/
    private Set<GeoPoint> locations = new HashSet<GeoPoint>();
    /** Group of user for this project. **/
    private Set<Group> groups = new HashSet<Group>();
    /** List of User for the project. **/
    private Set<UserAccount> secUserSecondaries = new HashSet<UserAccount>();

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
    @DocumentId
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
    @Field(index = Index.TOKENIZED)
    @Column(name = "description", nullable = true, length = 600)
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
     * @return the projectName
     */
    @Field(index = Index.TOKENIZED)
    @Column(name = "project_name", nullable = false)
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return projectInfo
     */
    @Column(name = "project_info")
    @Lob
    @Field(index = Index.TOKENIZED)
    public String getProjectInfo() {
        return this.projectInfo;
    }

    /**
     * @param projectInfo projectInfo
     */
    public void setProjectInfo(final String projectInfo) {
        this.projectInfo = projectInfo;
    }

    /**
     * @return projectDateStart
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "date_start", nullable = false)
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
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "date_finish", nullable = false)
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
    @JoinTable(name="project_geoPoint",
              joinColumns={@JoinColumn(name="cat_id_project")},
              inverseJoinColumns={@JoinColumn(name="cat_id_loc")})
    public Set<GeoPoint> getLocations() {
        return locations;
    }

    /**
     * @param locations the locations to set
     */
    public void setLocations(final Set<GeoPoint> locations) {
        this.locations = locations;
    }

    /**
     * @return the groups
     */
    @ManyToMany()
    @JoinTable(name="project_group",
              joinColumns={@JoinColumn(name="cat_id_project")},
              inverseJoinColumns={@JoinColumn(name="sec_id_group")})
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(final Set<Group> groups) {
        this.groups = groups;
    }

    /**
     * @return the secUserSecondaries
     */
    @ManyToMany()
    @JoinTable(name="userAccount_project",
               joinColumns={@JoinColumn(name="cat_id_project")},
               inverseJoinColumns={@JoinColumn(name="sec_id_secondary")})
    public Set<UserAccount> getSecUserSecondaries() {
        return secUserSecondaries;
    }

    /**
     * @param secUserSecondaries the secUserSecondaries to set
     */
    public void setSecUserSecondaries(final Set<UserAccount> secUserSecondaries) {
        this.secUserSecondaries = secUserSecondaries;
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
    public Account getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Account users) {
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

    /**
     * @return the lead
     */
    @ManyToOne()
    public UserAccount getLead() {
        return lead;
    }

    /**
     * @param lead the lead to set
     */
    public void setLead(UserAccount lead) {
        this.lead = lead;
    }

    /**
     * @return the notifyMembers
     */
    @Column(name="notify_members")
    public Boolean getNotifyMembers() {
        return notifyMembers;
    }

    /**
     * @param notifyMembers the notifyMembers to set
     */
    public void setNotifyMembers(Boolean notifyMembers) {
        this.notifyMembers = notifyMembers;
    }

    /**
     * @return the hideProject
     */
    @Column(name="hide_project")
    public Boolean getHideProject() {
        return hideProject;
    }

    /**
     * @param hideProject the hideProject to set
     */
    public void setHideProject(Boolean hideProject) {
        this.hideProject = hideProject;
    }

    /**
     * @return the projectStatus
     */
    @Column(name="project_status")
    @Enumerated(EnumType.STRING)
    public Status getProjectStatus() {
        return projectStatus;
    }

    /**
     * @param projectStatus the projectStatus to set
     */
    public void setProjectStatus(Status projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * @return the published
     */
    @Column(name="published")
    public Boolean getPublished() {
        return published;
    }

    /**
     * @param published the published to set
     */
    public void setPublished(Boolean published) {
        this.published = published;
    }
}
