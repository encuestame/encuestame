/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import org.encuestame.persistence.domain.EnMePermission;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Permission.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "permission")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Permission {

    private Long idPermission;
    private EnMePermission permission;
    private String permissionDescription;
    private Set<Group> groups = new HashSet<Group>();
    private Set<UserAccount> secUserSecondaries = new HashSet<UserAccount>();

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
    @Enumerated(EnumType.STRING)
    public EnMePermission getPermission() {
        return this.permission;
    }

    /**
     * @param permission permission
     */
    public void setPermission(EnMePermission permission) {
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
    @JoinTable(name="userAccount_permission",
               joinColumns={@JoinColumn(name="sec_id_permission")},
               inverseJoinColumns={@JoinColumn(name="sec_id_secondary")})
    public Set<UserAccount> getSecUserSecondaries() {
        return secUserSecondaries;
    }

    /**
     * @param secUserSecondaries the secUserSecondaries to set
     */
    public void setSecUserSecondaries(Set<UserAccount> secUserSecondaries) {
        this.secUserSecondaries = secUserSecondaries;
    }

    /**
     * @return the secGroups
     */
    @ManyToMany()
    @JoinTable(name="group_permission",
               joinColumns={@JoinColumn(name="sec_id_permission")},
               inverseJoinColumns={@JoinColumn(name="sec_id_group")})
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * @param secGroups the secGroups to set
     */
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
