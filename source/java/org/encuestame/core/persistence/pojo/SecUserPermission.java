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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SecUserPermission.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "sec_user_permission")
public class SecUserPermission implements java.io.Serializable {

    private SecUserPermissionId id;
    private SecPermission secPermission;
    private SecUsers secUsers;
    private Boolean state;

    public SecUserPermission() {
    }

    public SecUserPermission(SecUserPermissionId id,
            SecPermission secPermission, SecUsers secUsers) {
        this.id = id;
        this.secPermission = secPermission;
        this.secUsers = secUsers;
    }

    public SecUserPermission(SecUserPermissionId id,
            SecPermission secPermission, SecUsers secUsers, Boolean state) {
        this.id = id;
        this.secPermission = secPermission;
        this.secUsers = secUsers;
        this.state = state;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "uid", column = @Column(name = "uid", nullable = false)),
            @AttributeOverride(name = "idPermission", column = @Column(name = "id_permission", nullable = false)) })
    public SecUserPermissionId getId() {
        return this.id;
    }

    public void setId(SecUserPermissionId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permission", nullable = false, insertable = false, updatable = false)
    public SecPermission getSecPermission() {
        return this.secPermission;
    }

    public void setSecPermission(SecPermission secPermission) {
        this.secPermission = secPermission;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false, insertable = false, updatable = false)
    public SecUsers getSecUsers() {
        return this.secUsers;
    }

    public void setSecUsers(SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    @Column(name = "state")
    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
