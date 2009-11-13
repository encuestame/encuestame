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

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SecGroups.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "sec_groups")
public class SecGroups implements java.io.Serializable {

    private Long groupId;
    private String name;
    private String desInfo;
    private Long idState;
    private Set<SecGroupUser> secGroupUsers = new HashSet<SecGroupUser>(0);
    private Set secGroupPermissions = new HashSet(0);
    private Set projectGroups = new HashSet(0);
    private Set projectLocations = new HashSet(0);

    public SecGroups() {
    }

    public SecGroups(Long idState) {
        this.idState = idState;
    }

    public SecGroups(String name, String desInfo, Long idState,
            Set<SecGroupUser> secGroupUsers, Set secGroupPermissions,
            Set projectGroups, Set projectLocations) {
        this.name = name;
        this.desInfo = desInfo;
        this.idState = idState;
        this.secGroupUsers = secGroupUsers;
        this.secGroupPermissions = secGroupPermissions;
        this.projectGroups = projectGroups;
        this.projectLocations = projectLocations;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id", unique = true, nullable = false)
    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "des_info")
    public String getDesInfo() {
        return this.desInfo;
    }

    public void setDesInfo(String desInfo) {
        this.desInfo = desInfo;
    }

    @Column(name = "id_state", nullable = false)
    public Long getIdState() {
        return this.idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secGroups")
    public Set<SecGroupUser> getSecGroupUsers() {
        return this.secGroupUsers;
    }

    public void setSecGroupUsers(Set<SecGroupUser> secGroupUsers) {
        this.secGroupUsers = secGroupUsers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secGroups")
    public Set<SecGroupPermission> getSecGroupPermissions() {
        return this.secGroupPermissions;
    }

    public void setSecGroupPermissions(
            Set<SecGroupPermission> secGroupPermissions) {
        this.secGroupPermissions = secGroupPermissions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secGroups")
    public Set<ProjectGroup> getProjectGroups() {
        return this.projectGroups;
    }

    public void setProjectGroups(Set<ProjectGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secGroups")
    public Set<ProjectLocation> getProjectLocations() {
        return this.projectLocations;
    }

    public void setProjectLocations(Set<ProjectLocation> projectLocations) {
        this.projectLocations = projectLocations;
    }

}
