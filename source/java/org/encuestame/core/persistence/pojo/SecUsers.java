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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "sec_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class SecUsers implements Serializable {

    private Integer uid;
    private String name;
    private String email;
    private String username;
    private String password;
    private Boolean status;
    private String inviteCode;
    private Date dateNew;
    private String publisher;
    private String owner;
    private String twitter;
    private Set<SecGroupUser> secGroupUsers = new HashSet<SecGroupUser>(0);
    private Set<SecUserPermission> secUserPermissions = new HashSet<SecUserPermission>(
            0);
    private Set<CatLocationUser> catLocationUsers = new HashSet<CatLocationUser>(
            0);
    private Set<SurveyResultMod> surveyResultMods = new HashSet<SurveyResultMod>(
            0);
    private Set<ProjectUser> projectUsers = new HashSet<ProjectUser>(0);
    private Set<QuestionColettion> questionColettions = new HashSet<QuestionColettion>(
            0);
    private Set<Surveys> surveyses = new HashSet<Surveys>(0);

    public SecUsers() {
    }

    public SecUsers(String name, String email, String password, boolean status,
            Date dateNew, String publisher) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.dateNew = dateNew;
        this.publisher = publisher;
    }

    public SecUsers(String name, String email, String username,
            String password, Boolean status, String inviteCode, Date dateNew,
            String publisher, String owner, String twitter,
            Set<SecGroupUser> secGroupUsers,
            Set<SecUserPermission> secUserPermissions, Set catLocationUsers,
            Set surveyResultMods, Set<ProjectUser> projectUsers,
            Set<QuestionColettion> questionColettions, Set<Surveys> surveyses) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
        this.inviteCode = inviteCode;
        this.dateNew = dateNew;
        this.publisher = publisher;
        this.owner = owner;
        this.twitter = twitter;
        this.secGroupUsers = secGroupUsers;
        this.secUserPermissions = secUserPermissions;
        this.catLocationUsers = catLocationUsers;
        this.surveyResultMods = surveyResultMods;
        this.projectUsers = projectUsers;
        this.questionColettions = questionColettions;
        this.surveyses = surveyses;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "uid", unique = true, nullable = false)
    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", unique = true, nullable = false, length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "status", nullable = false)
    public Boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "invite_code")
    public String getInviteCode() {
        return this.inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_new", nullable = false, length = 0)
    public Date getDateNew() {
        return this.dateNew;
    }

    public void setDateNew(Date dateNew) {
        this.dateNew = dateNew;
    }

    @Column(name = "publisher", nullable = true, length = 2)
    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Column(name = "owner", length = 2)
    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "twitter", length = 2)
    public String getTwitter() {
        return this.twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<SecGroupUser> getSecGroupUsers() {
        return this.secGroupUsers;
    }

    public void setSecGroupUsers(Set<SecGroupUser> secGroupUsers) {
        this.secGroupUsers = secGroupUsers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<SecUserPermission> getSecUserPermissions() {
        return this.secUserPermissions;
    }

    public void setSecUserPermissions(Set<SecUserPermission> secUserPermissions) {
        this.secUserPermissions = secUserPermissions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<CatLocationUser> getCatLocationUsers() {
        return this.catLocationUsers;
    }

    public void setCatLocationUsers(Set<CatLocationUser> catLocationUsers) {
        this.catLocationUsers = catLocationUsers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<SurveyResultMod> getSurveyResultMods() {
        return this.surveyResultMods;
    }

    public void setSurveyResultMods(Set<SurveyResultMod> surveyResultMods) {
        this.surveyResultMods = surveyResultMods;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<ProjectUser> getProjectUsers() {
        return this.projectUsers;
    }

    public void setProjectUsers(Set<ProjectUser> projectUsers) {
        this.projectUsers = projectUsers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<QuestionColettion> getQuestionColettions() {
        return this.questionColettions;
    }

    public void setQuestionColettions(Set<QuestionColettion> questionColettions) {
        this.questionColettions = questionColettions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secUsers")
    public Set<Surveys> getSurveyses() {
        return this.surveyses;
    }

    public void setSurveyses(Set<Surveys> surveyses) {
        this.surveyses = surveyses;
    }

}
