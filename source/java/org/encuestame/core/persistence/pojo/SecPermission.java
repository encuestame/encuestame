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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SecPermission.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "sec_permission")
public class SecPermission {

    private Long idPermission;
    private String permission;
    private String permissionDescription;
    private Set<SecUserPermission> secUserPermissions = new HashSet<SecUserPermission>(
            0);
    private Set<SecGroupPermission> secGroupPermissions = new HashSet<SecGroupPermission>(
            0);


    /**
     * @return idPermission
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_permission", unique = true, nullable = false)
    public Long getIdPermission() {
        return this.idPermission;
    }

    /**
     * @param idPermission idPermission
     */
    public void setIdPermission(Long idPermission) {
        this.idPermission = idPermission;
    }

    /**
     * @return permission
     */
    @Column(name = "permission")
    public String getPermission() {
        return this.permission;
    }

    /**
     * @param permission permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * @return permissionDescription
     */
    @Column(name = "description")
    public String getPermissionDescription() {
        return this.permissionDescription;
    }

    /**
     * @param permissionDescription permissionDescription
     */
    public void setPermissionDescription(final String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    /**
     * @return secUserPermissions
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secPermission")
    public Set<SecUserPermission> getSecUserPermissions() {
        return this.secUserPermissions;
    }

    /**
     * @param secUserPermissions secUserPermissions
     */
    public void setSecUserPermissions(Set<SecUserPermission> secUserPermissions) {
        this.secUserPermissions = secUserPermissions;
    }

    /**
     * @return secGroupPermissions
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secPermission")
    public Set<SecGroupPermission> getSecGroupPermissions() {
        return this.secGroupPermissions;
    }

    /**
     * @param secGroupPermissions secGroupPermissions
     */
    public void setSecGroupPermissions(
            Set<SecGroupPermission> secGroupPermissions) {
        this.secGroupPermissions = secGroupPermissions;
    }

}
