/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Account.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 */

@Entity
@Table(name = "account")
public class Account{

    /** User Id. **/
    private Long uid;


    /**
     * Define if account is enabled.
     * by default is enabled.
     */
    private Boolean enabled = Boolean.TRUE;

    /**
     * Store when this account was created.
     * by default is the current date.
     */
    private Date createdAccount = Calendar.getInstance().getTime();

/*
    private Set<CatLocationUser> catLocationUsers = new HashSet<CatLocationUser>(
            0);
    private Set<SurveyResultMod> surveyResultMods = new HashSet<SurveyResultMod>(
            0);
    private Set<ProjectUser> projectUsers = new HashSet<ProjectUser>(0);
    private Set<QuestionColettion> questionColettions = new HashSet<QuestionColettion>(
            0);*/
    //private Set<Surveys> surveys = new HashSet<Surveys>();

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
     * @return the enabled
     */
    @Column(name="account_enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the createdAccount
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_created_date", nullable = false)
    public Date getCreatedAccount() {
        return createdAccount;
    }

    /**
     * @param createdAccount the createdAccount to set
     */
    public void setCreatedAccount(final Date createdAccount) {
        this.createdAccount = createdAccount;
    }
}
