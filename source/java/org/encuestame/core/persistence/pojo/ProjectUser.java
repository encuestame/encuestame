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
 * ProjectLocationId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "project_user")
public class ProjectUser implements Serializable {

    private ProjectUserId id;
    private Project project;
    private SecUsers secUsers;
    private Date dateNew;

    public ProjectUser() {
    }

    public ProjectUser(ProjectUserId id, Project project, SecUsers secUsers,
            Date dateNew) {
        this.id = id;
        this.project = project;
        this.secUsers = secUsers;
        this.dateNew = dateNew;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "uid", column = @Column(name = "uid", nullable = false)),
            @AttributeOverride(name = "proyectId", column = @Column(name = "proyect_id", nullable = false)) })
    public ProjectUserId getId() {
        return this.id;
    }

    public void setId(ProjectUserId id) {
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
    @JoinColumn(name = "uid", nullable = false, insertable = false, updatable = false)
    public SecUsers getSecUsers() {
        return this.secUsers;
    }

    public void setSecUsers(SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_new", nullable = false, length = 0)
    public Date getDateNew() {
        return this.dateNew;
    }

    public void setDateNew(Date dateNew) {
        this.dateNew = dateNew;
    }

}
