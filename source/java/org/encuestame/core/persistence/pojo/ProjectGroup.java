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
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProjectGroup.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "project_group")
public class ProjectGroup implements Serializable {

    private ProjectGroupId id;
    private Project project;
    private SecGroups secGroups;
    private Date fechaIngreso;

    public ProjectGroup() {
    }

    public ProjectGroup(ProjectGroupId id, Project project,
            SecGroups secGroups, Date fechaIngreso) {
        this.id = id;
        this.project = project;
        this.secGroups = secGroups;
        this.fechaIngreso = fechaIngreso;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "groupId", column = @Column(name = "group_id", nullable = false)),
            @AttributeOverride(name = "proyectId", column = @Column(name = "proyect_id", nullable = false)) })
    public ProjectGroupId getId() {
        return this.id;
    }

    public void setId(ProjectGroupId id) {
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso", nullable = false, length = 0)
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
