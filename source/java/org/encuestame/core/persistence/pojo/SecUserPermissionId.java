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
 * SecUserPermission.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class SecUserPermissionId implements java.io.Serializable {

    private Long uid;
    private Long idPermission;

    public SecUserPermissionId() {
    }

    public SecUserPermissionId(Long uid, Long idPermission) {
        this.uid = uid;
        this.idPermission = idPermission;
    }

    @Column(name = "uid", nullable = false)
    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Column(name = "id_permission", nullable = false)
    public Long getIdPermission() {
        return this.idPermission;
    }

    public void setIdPermission(Long idPermission) {
        this.idPermission = idPermission;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SecUserPermissionId))
            return false;
        SecUserPermissionId castOther = (SecUserPermissionId) other;

        return (this.getUid() == castOther.getUid())
                && (this.getIdPermission() == castOther.getIdPermission());
    }

    public int hashCode() {
        int result = 17;

        result = (int) (37 * result + this.getUid());
        result = (int) (37 * result + this.getIdPermission());
        return result;
    }

}
