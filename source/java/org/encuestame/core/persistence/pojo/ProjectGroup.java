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
    private Date entryDate;

    /**
     * @return id
     */
    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "groupId", column = @Column(name = "group_id", nullable = false)),
            @AttributeOverride(name = "proyectId", column = @Column(name = "proyect_id", nullable = false)) })
    public ProjectGroupId getId() {
        return this.id;
    }

    /**
     * @param id id
     */
    public void setId(ProjectGroupId id) {
        this.id = id;
    }

    /**
     * @return project
     */
    @ManyToOne()
    @JoinColumn(name = "proyect_id", nullable = false, insertable = false, updatable = false)
    public Project getProject() {
        return this.project;
    }

    /**
     * @param project project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return secGroups
     */
    @ManyToOne()
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    public SecGroups getSecGroups() {
        return this.secGroups;
    }

    /**
     * @param secGroups secGroups
     */
    public void setSecGroups(SecGroups secGroups) {
        this.secGroups = secGroups;
    }

    /**
     * @return entryDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "projectGroup_entryDate", nullable = false, length = 0)
    public Date getEntryDate() {
        return this.entryDate;
    }

    /**
     * @param entryDate entryDate
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

}
