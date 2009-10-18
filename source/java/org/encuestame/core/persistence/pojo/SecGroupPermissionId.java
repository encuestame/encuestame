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
 * SecGroupPermissionId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class SecGroupPermissionId implements java.io.Serializable {

    private int idPermission;
    private int groupId;

    public SecGroupPermissionId() {
    }

    public SecGroupPermissionId(int idPermission, int groupId) {
        this.idPermission = idPermission;
        this.groupId = groupId;
    }

    @Column(name = "id_permission", nullable = false)
    public int getIdPermission() {
        return this.idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    @Column(name = "group_id", nullable = false)
    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SecGroupPermissionId))
            return false;
        SecGroupPermissionId castOther = (SecGroupPermissionId) other;

        return (this.getIdPermission() == castOther.getIdPermission())
                && (this.getGroupId() == castOther.getGroupId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getIdPermission();
        result = 37 * result + this.getGroupId();
        return result;
    }

}
