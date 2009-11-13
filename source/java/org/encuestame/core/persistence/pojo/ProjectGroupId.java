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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProjectGroupId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class ProjectGroupId implements java.io.Serializable {

    private Long groupId;
    private Long proyectId;

    public ProjectGroupId() {
    }

    public ProjectGroupId(Long groupId, Long proyectId) {
        this.groupId = groupId;
        this.proyectId = proyectId;
    }

    @Column(name = "group_id", nullable = false)
    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "proyect_id", nullable = false)
    public Long getProyectId() {
        return this.proyectId;
    }

    public void setProyectId(Long proyectId) {
        this.proyectId = proyectId;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof ProjectGroupId))
            return false;
        ProjectGroupId castOther = (ProjectGroupId) other;

        return (this.getGroupId() == castOther.getGroupId())
                && (this.getProyectId() == castOther.getProyectId());
    }

    public int hashCode() {
        int result = 17;

        result = (int) (37 * result + this.getGroupId());
        result = (int) (37 * result + this.getProyectId());
        return result;
    }

}
