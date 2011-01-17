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
package org.encuestame.persistence.domain.security;

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
import javax.persistence.UniqueConstraint;

import org.encuestame.persistence.domain.Project;

/**
 * Security User Secondary.
 * @author Picado, Juan juan@encuestame.org
 * @since 08/12/2009 19:01:26
 * @version $Id$
 */
@Entity
@Table(name = "userAccount",
       uniqueConstraints = {@UniqueConstraint(columnNames={"username", "email"})}
  )
public class UserAccount {

    private Long uid;
    private String completeName;
    private String userEmail;
    private String username;
    private String password;
    private Account account;
    private String inviteCode;
    private Date enjoyDate;
    private Boolean userStatus;
    private String userTwitterAccount;
    private Date lastTimeLogged;
    private String lastIpLogged;
    private Long followers;

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

    private Set<Project> projects = new HashSet<Project>();

    private Set<Permission> secUserPermissions = new HashSet<Permission>();

    /**
     * {@link Group}
     */
    private Group group;

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
    @Column(name = "username", nullable = false, length = 30)
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
    public Account getAccount() {
        return account;
    }

    /**
     * @param secUser the secUser to set
     */
    public void setAccount(Account account) {
        this.account = account;
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
    @JoinTable(name="userAccount_permission",
               joinColumns={@JoinColumn(name="sec_id_secondary")},
               inverseJoinColumns={@JoinColumn(name="sec_id_permission")})
    public Set<Permission> getSecUserPermissions() {
        return secUserPermissions;
    }

    /**
     * @param secUserPermissions the secUserPermissions to set
     */
    public void setSecUserPermissions(Set<Permission> secUserPermissions) {
        this.secUserPermissions = secUserPermissions;
    }

    /**
     * @param userTwitterAccount userTwitterAccount
     */
    public void setUserTwitterAccount(final String userTwitterAccount) {
        this.userTwitterAccount = userTwitterAccount;
    }

    /**
     * @return the projects
     */
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="userAccount_project",
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
     * @return the lastTimeLogged
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_time_logged")
    public Date getLastTimeLogged() {
        return lastTimeLogged;
    }

    /**
     * @param lastTimeLogged the lastTimeLogged to set
     */
    public void setLastTimeLogged(Date lastTimeLogged) {
        this.lastTimeLogged = lastTimeLogged;
    }

    /**
     * @return the lastIpLogged
     */
     @Column(name = "last_ip_logged")
    public String getLastIpLogged() {
        return lastIpLogged;
    }

    /**
     * @param lastIpLogged the lastIpLogged to set
     */
    public void setLastIpLogged(String lastIpLogged) {
        this.lastIpLogged = lastIpLogged;
    }

    /**
     * @return the secGroup
     */
    @ManyToOne()
    @JoinColumn(name = "groupId", nullable = true)
    public Group getGroup() {
        return group;
    }

    /**
     * @param secGroup the secGroup to set
     */
    public void setGroup(final Group group) {
        this.group = group;
    }

    /**
     * @return the followers
     */
    public Long getFollowers() {
        return followers;
    }

    /**
     * @param followers the followers to set
     */
    @Column(name = "followers")
    public void setFollowers(final Long followers) {
        this.followers = followers;
    }
 }
