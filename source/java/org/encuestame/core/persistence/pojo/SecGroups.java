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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class SecGroups {

    private Long groupId;
    private String groupName;
    private String groupDescriptionInfo;
    private Long idState;
    private Set<SecGroupUser> secGroupUsers = new HashSet<SecGroupUser>(0);
    private Set<SecGroupPermission> secGroupPermissions = new HashSet<SecGroupPermission>(0);
    private Set<ProjectGroup> projectGroups = new HashSet<ProjectGroup>(0);
    private Set<ProjectLocation> projectLocations = new HashSet<ProjectLocation>(0);

    /**
     * @return groupId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id", unique = true, nullable = false)
    public Long getGroupId() {
        return this.groupId;
    }

    /**
     * @param groupId groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return groupName
     */
    @Column(name = "name", length = 50)
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * @param groupName groupName
     */
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return groupDescriptionInfo
     */
    @Column(name = "des_info")
    public String getGroupDescriptionInfo() {
        return this.groupDescriptionInfo;
    }

    /**
     * @param groupDescriptionInfo groupDescriptionInfo
     */
    public void setGroupDescriptionInfo(String groupDescriptionInfo) {
        this.groupDescriptionInfo = groupDescriptionInfo;
    }

    /**
     * @return idState
     */
    @Column(name = "id_state", nullable = false)
    public Long getIdState() {
        return this.idState;
    }

    /**
     * @param idState idState
     */
    public void setIdState(Long idState) {
        this.idState = idState;
    }

    /**
     * @return secGroupUsers
     */
    @OneToMany(mappedBy = "secGroups")
    public Set<SecGroupUser> getSecGroupUsers() {
        return this.secGroupUsers;
    }

    /**
     * @param secGroupUsers secGroupUsers
     */
    public void setSecGroupUsers(final Set<SecGroupUser> secGroupUsers) {
        this.secGroupUsers = secGroupUsers;
    }

    /**
     * @return secGroupPermissions secGroupPermissions
     */
    @OneToMany(mappedBy = "secGroups")
    public Set<SecGroupPermission> getSecGroupPermissions() {
        return this.secGroupPermissions;
    }

    /**
     *
     * @param secGroupPermissions secGroupPermissions
     */
    public void setSecGroupPermissions(
            final Set<SecGroupPermission> secGroupPermissions) {
        this.secGroupPermissions = secGroupPermissions;
    }

    /**
     * @return projectGroups
     */
    @OneToMany(mappedBy = "secGroups")
    public Set<ProjectGroup> getProjectGroups() {
        return this.projectGroups;
    }

    /**
     * @param projectGroups projectGroups
     */
    public void setProjectGroups(Set<ProjectGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    /**
     * @return projectLocations
     */
    @OneToMany(mappedBy = "secGroups")
    public Set<ProjectLocation> getProjectLocations() {
        return this.projectLocations;
    }

    /**
     * @param projectLocations projectLocations
     */
    public void setProjectLocations(final Set<ProjectLocation> projectLocations) {
        this.projectLocations = projectLocations;
    }

}
