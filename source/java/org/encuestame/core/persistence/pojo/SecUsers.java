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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "sec_user")
public class SecUsers{


    private Long uid;
    private String userTwitterAccount;
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
     * @return userTwitterAccount
     */
    @Column(name = "twitter", length = 2)
    public String getUserTwitterAccount() {
        return this.userTwitterAccount;
    }

    /**
     * @param userTwitterAccount userTwitterAccount
     */
    public void setUserTwitterAccount(final String userTwitterAccount) {
        this.userTwitterAccount = userTwitterAccount;
    }

    /**
     * @return secGroupUsers
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
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
     * @return secUserPermissions
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
    public Set<SecUserPermission> getSecUserPermissions() {
        return this.secUserPermissions;
    }

    /**
     * @param secUserPermissions secUserPermissions
     */
    public void setSecUserPermissions(final Set<SecUserPermission> secUserPermissions) {
        this.secUserPermissions = secUserPermissions;
    }

    /**
     * @return catLocationUsers
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
    public Set<CatLocationUser> getCatLocationUsers() {
        return this.catLocationUsers;
    }

    /**
     * @param catLocationUsers catLocationUsers
     */
    public void setCatLocationUsers(final Set<CatLocationUser> catLocationUsers) {
        this.catLocationUsers = catLocationUsers;
    }

    /**
     * @return surveyResultMods
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
    public Set<SurveyResultMod> getSurveyResultMods() {
        return this.surveyResultMods;
    }

    /**
     * @param surveyResultMods surveyResultMods
     */
    public void setSurveyResultMods(final Set<SurveyResultMod> surveyResultMods) {
        this.surveyResultMods = surveyResultMods;
    }

    /**
     * @return projectUsers
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
    public Set<ProjectUser> getProjectUsers() {
        return this.projectUsers;
    }

    /**
     * @param projectUsers projectUsers
     */
    public void setProjectUsers(final Set<ProjectUser> projectUsers) {
        this.projectUsers = projectUsers;
    }

    /**
     * @return questionColettions
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
    public Set<QuestionColettion> getQuestionColettions() {
        return this.questionColettions;
    }

    /**
     * @param questionColettions questionColettions
     */
    public void setQuestionColettions(final Set<QuestionColettion> questionColettions) {
        this.questionColettions = questionColettions;
    }

    /**
     * @return surveyses
     */
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "secUsers")
    public Set<Surveys> getSurveyses() {
        return this.surveyses;
    }

    /**
     * @param surveyses surveyses
     */
    public void setSurveyses(final Set<Surveys> surveyses) {
        this.surveyses = surveyses;
    }

}
