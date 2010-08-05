/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.persistence.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * SecPermission.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "sec_permission")
public class SecPermission {

    private Long idPermission;
    private String permission;
    private String permissionDescription;
    private Set<SecGroup> secGroups = new HashSet<SecGroup>();
    private Set<SecUserSecondary> secUserSecondaries = new HashSet<SecUserSecondary>();

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
     * @return the secUserSecondaries
     */
    @ManyToMany()
    @JoinTable(name="sec_user_permission",
               joinColumns={@JoinColumn(name="sec_id_permission")},
               inverseJoinColumns={@JoinColumn(name="sec_id_secondary")})
    public Set<SecUserSecondary> getSecUserSecondaries() {
        return secUserSecondaries;
    }

    /**
     * @param secUserSecondaries the secUserSecondaries to set
     */
    public void setSecUserSecondaries(Set<SecUserSecondary> secUserSecondaries) {
        this.secUserSecondaries = secUserSecondaries;
    }

    /**
     * @return the secGroups
     */
    @ManyToMany()
    @JoinTable(name="sec_group_permission",
               joinColumns={@JoinColumn(name="sec_id_permission")},
               inverseJoinColumns={@JoinColumn(name="sec_id_group")})
    public Set<SecGroup> getSecGroups() {
        return secGroups;
    }

    /**
     * @param secGroups the secGroups to set
     */
    public void setSecGroups(Set<SecGroup> secGroups) {
        this.secGroups = secGroups;
    }
}
