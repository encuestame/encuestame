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
public class SecPermission implements java.io.Serializable {

    private int idPermission;
    private String permission;
    private String description;
    private Set<SecUserPermission> secUserPermissions = new HashSet<SecUserPermission>(
            0);
    private Set<SecGroupPermission> secGroupPermissions = new HashSet<SecGroupPermission>(
            0);

    public SecPermission() {
    }

    public SecPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    public SecPermission(int idPermission, String permission,
            String description, Set<SecUserPermission> secUserPermissions,
            Set<SecGroupPermission> secGroupPermissions) {
        this.idPermission = idPermission;
        this.permission = permission;
        this.description = description;
        this.secUserPermissions = secUserPermissions;
        this.secGroupPermissions = secGroupPermissions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_permission", unique = true, nullable = false)
    public int getIdPermission() {
        return this.idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    @Column(name = "permission")
    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secPermission")
    public Set<SecUserPermission> getSecUserPermissions() {
        return this.secUserPermissions;
    }

    public void setSecUserPermissions(Set<SecUserPermission> secUserPermissions) {
        this.secUserPermissions = secUserPermissions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secPermission")
    public Set<SecGroupPermission> getSecGroupPermissions() {
        return this.secGroupPermissions;
    }

    public void setSecGroupPermissions(
            Set<SecGroupPermission> secGroupPermissions) {
        this.secGroupPermissions = secGroupPermissions;
    }

}
