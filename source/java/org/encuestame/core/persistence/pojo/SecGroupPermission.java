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
 * SecGroupPermission.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "sec_group_permission")
public class SecGroupPermission implements java.io.Serializable {

    private SecGroupPermissionId id;
    private SecPermission secPermission;
    private SecGroups secGroups;
    private Boolean state;

    public SecGroupPermission() {
    }

    public SecGroupPermission(SecGroupPermissionId id,
            SecPermission secPermission, SecGroups secGroups) {
        this.id = id;
        this.secPermission = secPermission;
        this.secGroups = secGroups;
    }

    public SecGroupPermission(SecGroupPermissionId id,
            SecPermission secPermission, SecGroups secGroups, Boolean state) {
        this.id = id;
        this.secPermission = secPermission;
        this.secGroups = secGroups;
        this.state = state;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "idPermission", column = @Column(name = "id_permission", nullable = false)),
            @AttributeOverride(name = "groupId", column = @Column(name = "group_id", nullable = false)) })
    public SecGroupPermissionId getId() {
        return this.id;
    }

    public void setId(SecGroupPermissionId id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "id_permission", nullable = false, insertable = false, updatable = false)
    public SecPermission getSecPermission() {
        return this.secPermission;
    }

    public void setSecPermission(SecPermission secPermission) {
        this.secPermission = secPermission;
    }

    @ManyToOne()
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    public SecGroups getSecGroups() {
        return this.secGroups;
    }

    public void setSecGroups(SecGroups secGroups) {
        this.secGroups = secGroups;
    }

    @Column(name = "state")
    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
