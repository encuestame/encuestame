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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Security User Secondary.
 * @author Picado, Juan juan@encuestame.org
 * @since 08/12/2009 19:01:26
 * @version $Id$
 */
@Entity
@Table(name = "sec_user_secondary")
public class SecUserSecondary {

    private Long uid;
    private String completeName;
    private String userEmail;
    private String username;
    private String password;
    private SecUsers secUser;
    private String inviteCode;
    private Date enjoyDate;
    private Boolean userStatus;
    private String userTwitterAccount;

    /**
     * Account Enabled.
     */
    private boolean enabled = true;

    /**
     * Account Non Expired.
     */
    private boolean accountNonExpired = true;

    /**
     * Account Non Locked.
     */
    private boolean accountNonLocked = true;

    /**
     * Credentials Non Expired.
     */
    private boolean credentialsNonExpired = true;

    private Set<SecGroups> secGroups = new HashSet<SecGroups>();

    private Set<Project> projects = new HashSet<Project>();

    private Set<SecPermission> secUserPermissions = new HashSet<SecPermission>();

    private Set<CatLocation> cLocations = new HashSet<CatLocation>();

    /**
     * @return uid
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid", unique = true, nullable = false)
    public Long getUid() {
        return this.uid;
    }

    /**
     * @param uid uid
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }


    /**
     * @return completeName
     */
    @Column(name = "name", nullable = true, length = 50)
    public String getCompleteName() {
        return this.completeName;
    }

    /**
     * @param completeName completeName
     */
    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    /**
     * @return userEmail userEmail
     */
    @Column(name = "email", unique = true, nullable = false, length = 150)
    public String getUserEmail() {
        return this.userEmail;
    }

    /**
     * @param userEmail userEmail
     */
    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return username
     */
    @Column(name = "username", nullable = false, length = 16)
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the secUser
     */
    @ManyToOne()
    public SecUsers getSecUser() {
        return secUser;
    }

    /**
     * @param secUser the secUser to set
     */
    public void setSecUser(SecUsers secUser) {
        this.secUser = secUser;
    }

    /**
     * @return inviteCode
     */
    @Column(name = "invite_code" , nullable = true )
    public String getInviteCode() {
        return this.inviteCode;
    }

    /**
     * @param inviteCode inviteCode
     */
    public void setInviteCode(final String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * @return enjoyDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_new", nullable = true)
    public Date getEnjoyDate() {
        return this.enjoyDate;
    }

    /**
     * @param enjoyDate enjoyDate
     */
    public void setEnjoyDate(final Date enjoyDate) {
        this.enjoyDate = enjoyDate;
    }

    /**
     * @return userStatus
     */
    @Column(name = "status", nullable = true)
    public Boolean isUserStatus() {
        return this.userStatus;
    }

    /**
     * @param userStatus userStatus
     */
    public void setUserStatus(final boolean userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return userTwitterAccount
     */
    @Column(name = "twitter", nullable = true)
    public String getUserTwitterAccount() {
        return this.userTwitterAccount;
    }

    /**
     * @return the secUserPermissions
     */
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="sec_user_permission",
               joinColumns={@JoinColumn(name="sec_id_secondary")},
               inverseJoinColumns={@JoinColumn(name="sec_id_permission")})
    public Set<SecPermission> getSecUserPermissions() {
        return secUserPermissions;
    }

    /**
     * @param secUserPermissions the secUserPermissions to set
     */
    public void setSecUserPermissions(Set<SecPermission> secUserPermissions) {
        this.secUserPermissions = secUserPermissions;
    }

    /**
     * @param userTwitterAccount userTwitterAccount
     */
    public void setUserTwitterAccount(final String userTwitterAccount) {
        this.userTwitterAccount = userTwitterAccount;
    }

    /**
     * @return the secGroups
     */
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="sec_user_group",
               joinColumns={@JoinColumn(name="sec_id_secondary")},
               inverseJoinColumns={@JoinColumn(name="sec_id_group")})
    public Set<SecGroups> getSecGroups() {
        return secGroups;
    }

    /**
     * @param secGroups the secGroups to set
     */
    public void setSecGroups(Set<SecGroups> secGroups) {
        this.secGroups = secGroups;
    }

    /**
     * @return the projects
     */
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="sec_user_project",
               joinColumns={@JoinColumn(name="sec_id_secondary")},
               inverseJoinColumns={@JoinColumn(name="cat_id_project")})
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    /**
     * @return the cLocations
     */
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="cat_locations_user",
               joinColumns={@JoinColumn(name="sec_id_secondary")},
               inverseJoinColumns={@JoinColumn(name="cat_location_id")})
    public Set<CatLocation> getcLocations() {
        return cLocations;
    }

    /**
     * @param cLocations the cLocations to set
     */
    public void setcLocations(Set<CatLocation> cLocations) {
        this.cLocations = cLocations;
    }
}
