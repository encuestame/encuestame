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
 * @version $Id$
 */
@Entity
@Table(name = "sec_user")
public class SecUsers{


    private Long uid;

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
