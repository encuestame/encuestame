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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.Project;

/**
 * SecGroups.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "groups")
public class Group {

     /**
     */
    public enum Type {
    /**
     * Security Groups.
     */
    SECURITY,
    /**
     * Groups for Projects.
     */
    PROJECT
    };

    /**
     * Group Id.
     */
    private Long groupId;

    /**
     * Name.
     */
    private String groupName;

    /**
     * Group Type.
     */
    private Type groupType = Type.SECURITY;

    /**
     * Description.
     */
    private String groupDescriptionInfo;

    /**
     * State Id.
     */
    private Long idState;

    /**
     * Projects by Group.
     */
    private Set<Project> projects = new HashSet<Project>();

    /**
     * Permissions by Group.
     */
    private Set<Permission> permissions = new HashSet<Permission>();

    /**
     * {@link Account}.
     */
    private Account account = new Account();

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
    @Column(name = "id_state", nullable = true)
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
     * @return the permissions
     */
    @ManyToMany()
    @JoinTable(name="groups_permission",
               joinColumns={@JoinColumn(name="sec_id_group")},
               inverseJoinColumns={@JoinColumn(name="sec_id_permission")})
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the projects
     */
    @ManyToMany()
    @JoinTable(name="project_group",
              joinColumns={@JoinColumn(name="sec_id_group")},
              inverseJoinColumns={@JoinColumn(name="cat_id_project")})
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(final Set<Project> projects) {
        this.projects = projects;
    }

    /**
     * @return the secUsers
     */
    @ManyToOne()
    public Account getAccount() {
        return account;
    }

    /**
     * @param secUsers the secUsers to set
     */
    public void setAccount(final Account account) {
        this.account = account;
    }

    /**
     * @return the groupType
     */
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    public Type getGroupType() {
        return groupType;
    }

    /**
     * @param groupType the groupType to set
     */
    public void setGroupType(Type groupType) {
        this.groupType = groupType;
    }


}
