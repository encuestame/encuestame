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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * SurveyGroup.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_group")
public class SurveyGroup implements java.io.Serializable {

    private Long sgId;
    private String groupName;
    private Date dateCreate;
    private Long idState;
    private Long idSidFormat;
    private Set<SurveyFormatGroup> surveyFormatGroups = new HashSet<SurveyFormatGroup>(
            0);
    private Set<SurveyGroupProject> surveyGroupProjects = new HashSet<SurveyGroupProject>(
            0);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sg_id", unique = true, nullable = false)
    public Long getSgId() {
        return this.sgId;
    }

    public void setSgId(Long sgId) {
        this.sgId = sgId;
    }

    @Column(name = "group_name", length = 60)
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create", length = 0)
    public Date getDateCreate() {
        return this.dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Column(name = "id_state")
    public Long getIdState() {
        return this.idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    @Column(name = "id_sid_format", nullable = false)
    public Long getIdSidFormat() {
        return this.idSidFormat;
    }

    public void setIdSidFormat(Long idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "surveyGroup")
    public Set<SurveyFormatGroup> getSurveyFormatGroups() {
        return this.surveyFormatGroups;
    }

    public void setSurveyFormatGroups(Set<SurveyFormatGroup> surveyFormatGroups) {
        this.surveyFormatGroups = surveyFormatGroups;
    }

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "surveyGroup")
    public Set<SurveyGroupProject> getSurveyGroupProjects() {
        return this.surveyGroupProjects;
    }

    public void setSurveyGroupProjects(
            Set<SurveyGroupProject> surveyGroupProjects) {
        this.surveyGroupProjects = surveyGroupProjects;
    }

}
