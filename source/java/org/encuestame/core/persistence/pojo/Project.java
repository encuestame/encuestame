/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.encuestame.core.persistence.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Project.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private Long proyectId;
    private CatState catStateProject;
    private String projectDescription;
    private String projectInfo;
    private Date projectDateStart;
    private Date projectDateFinish;
    private Set<SurveyGroupProject> surveyGroupProjects = new HashSet(0);
    private Set<ProjectLocation> projectLocations = new HashSet(0);
    private Set<ProjectGroup> projectGroups = new HashSet(0);
    private Set<ProjectUser> projectUsers = new HashSet(0);


    /**
     * @return proyectId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "proyect_id", unique = true, nullable = false)
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
     * @return catStateProject
     */
    @ManyToOne()
    @JoinColumn(name = "id_state", nullable = false)
    public CatState getCatStateProject() {
        return this.catStateProject;
    }

    /**
     * @param catStateProject catStateProject
     */
    public void setCatStateProject(final CatState catStateProject) {
        this.catStateProject = catStateProject;
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
    public void setProjectDateFinish(Date projectDateFinish) {
        this.projectDateFinish = projectDateFinish;
    }

    /**
     * @return surveyGroupProjects
     */
    @OneToMany(mappedBy = "project")
    public Set<SurveyGroupProject> getSurveyGroupProjects() {
        return this.surveyGroupProjects;
    }

    /**
     * @param surveyGroupProjects surveyGroupProjects
     */
    public void setSurveyGroupProjects(
            Set<SurveyGroupProject> surveyGroupProjects) {
        this.surveyGroupProjects = surveyGroupProjects;
    }

    /**
     * @return projectLocations
     */
    @OneToMany(mappedBy = "project")
    public Set<ProjectLocation> getProjectLocations() {
        return this.projectLocations;
    }

    /**
     * @param projectLocations projectLocations
     */
    public void setProjectLocations(final Set<ProjectLocation> projectLocations) {
        this.projectLocations = projectLocations;
    }

    /**
     * @return projectGroups
     */
    @OneToMany(mappedBy = "project")
    public Set<ProjectGroup> getProjectGroups() {
        return this.projectGroups;
    }

    /**
     * @param projectGroups projectGroups
     */
    public void setProjectGroups(final Set<ProjectGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    /**
     * @return projectUsers
     */
    @OneToMany(mappedBy = "project")
    public Set<ProjectUser> getProjectUsers() {
        return this.projectUsers;
    }

    /**
     * @param projectUsers projectUsers
     */
    public void setProjectUsers(Set<ProjectUser> projectUsers) {
        this.projectUsers = projectUsers;
    }

}
