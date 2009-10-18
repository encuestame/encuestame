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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
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

    private Integer proyectId;
    private CatState catState;
    private String description;
    private String info;
    private Date dateStart;
    private Date dateFinish;
    private Set<SurveyGroupProject> surveyGroupProjects = new HashSet(0);
    private Set<ProjectLocation> projectLocations = new HashSet(0);
    private Set<ProjectGroup> projectGroups = new HashSet(0);
    private Set<ProjectUser> projectUsers = new HashSet(0);

    public Project() {
    }

    public Project(CatState catState, String description, String info,
            Date dateStart) {
        this.catState = catState;
        this.description = description;
        this.info = info;
        this.dateStart = dateStart;
    }

    public Project(CatState catState, String description, String info,
            Date dateStart, Date dateFinish, Set surveyGroupProjects,
            Set projectLocations, Set projectGroups, Set projectUsers) {
        this.catState = catState;
        this.description = description;
        this.info = info;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.surveyGroupProjects = surveyGroupProjects;
        this.projectLocations = projectLocations;
        this.projectGroups = projectGroups;
        this.projectUsers = projectUsers;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "proyect_id", unique = true, nullable = false)
    public Integer getProyectId() {
        return this.proyectId;
    }

    public void setProyectId(Integer proyectId) {
        this.proyectId = proyectId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_state", nullable = false)
    public CatState getCatState() {
        return this.catState;
    }

    public void setCatState(CatState catState) {
        this.catState = catState;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "info", nullable = false, length = 65535)
    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_start", nullable = false, length = 0)
    public Date getDateStart() {
        return this.dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_finish", length = 0)
    public Date getDateFinish() {
        return this.dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Set<SurveyGroupProject> getSurveyGroupProjects() {
        return this.surveyGroupProjects;
    }

    public void setSurveyGroupProjects(
            Set<SurveyGroupProject> surveyGroupProjects) {
        this.surveyGroupProjects = surveyGroupProjects;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Set<ProjectLocation> getProjectLocations() {
        return this.projectLocations;
    }

    public void setProjectLocations(Set<ProjectLocation> projectLocations) {
        this.projectLocations = projectLocations;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Set<ProjectGroup> getProjectGroups() {
        return this.projectGroups;
    }

    public void setProjectGroups(Set<ProjectGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Set<ProjectUser> getProjectUsers() {
        return this.projectUsers;
    }

    public void setProjectUsers(Set<ProjectUser> projectUsers) {
        this.projectUsers = projectUsers;
    }

}
