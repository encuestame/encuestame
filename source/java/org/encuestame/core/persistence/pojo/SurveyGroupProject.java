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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SurveyGroupProject.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_group_project")
public class SurveyGroupProject implements java.io.Serializable {

    private SurveyGroupProjectId id;
    private Project project;
    private SurveyGroup surveyGroup;
    private Boolean state;

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "sgId", column = @Column(name = "sg_id", nullable = false)),
            @AttributeOverride(name = "proyectId", column = @Column(name = "proyect_id", nullable = false)) })
    public SurveyGroupProjectId getId() {
        return this.id;
    }

    public void setId(SurveyGroupProjectId id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "proyect_id", nullable = false, insertable = false, updatable = false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sg_id", nullable = false, insertable = false, updatable = false)
    public SurveyGroup getSurveyGroup() {
        return this.surveyGroup;
    }

    public void setSurveyGroup(SurveyGroup surveyGroup) {
        this.surveyGroup = surveyGroup;
    }

    @Column(name = "state")
    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
