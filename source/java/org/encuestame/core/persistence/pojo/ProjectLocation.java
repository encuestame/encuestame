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

import javax.persistence.AttributeOverride;

import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProjectLocation.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "project_location")
public class ProjectLocation implements Serializable {

    private ProjectLocationId id;
    private Project project;
    private SecGroups secGroups;
    private Long idState;

    public ProjectLocation() {
    }

    public ProjectLocation(ProjectLocationId id, Project project,
            SecGroups secGroups, Long idState) {
        this.id = id;
        this.project = project;
        this.secGroups = secGroups;
        this.idState = idState;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "groupId", column = @Column(name = "group_id", nullable = false)),
            @AttributeOverride(name = "proyectId", column = @Column(name = "proyect_id", nullable = false)) })
    public ProjectLocationId getId() {
        return this.id;
    }

    public void setId(ProjectLocationId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyect_id", nullable = false, insertable = false, updatable = false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    public SecGroups getSecGroups() {
        return this.secGroups;
    }

    public void setSecGroups(SecGroups secGroups) {
        this.secGroups = secGroups;
    }

    @Column(name = "id_state", nullable = false)
    public Long getIdState() {
        return this.idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

}
