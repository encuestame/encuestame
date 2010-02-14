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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String twitterAccount;
    private String twitterPassword;
/*
    private Set<CatLocationUser> catLocationUsers = new HashSet<CatLocationUser>(
            0);
    private Set<SurveyResultMod> surveyResultMods = new HashSet<SurveyResultMod>(
            0);
    private Set<ProjectUser> projectUsers = new HashSet<ProjectUser>(0);
    private Set<QuestionColettion> questionColettions = new HashSet<QuestionColettion>(
            0);*/
    private Set<Surveys> surveys = new HashSet<Surveys>();

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
     * @return the twitterAccount
     */
    @Column(name = "twitter_account", nullable = false, length = 18)
    public String getTwitterAccount() {
        return twitterAccount;
    }

    /**
     * @param twitterAccount the twitterAccount to set
     */
    public void setTwitterAccount(final String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    /**
     * @return the twitterPassword
     */
    @Column(name = "twitter_password", nullable = false, length = 18)
    public String getTwitterPassword() {
        return twitterPassword;
    }

    /**
     * @param twitterPassword the twitterPassword to set
     */
    public void setTwitterPassword(final String twitterPassword) {
        this.twitterPassword = twitterPassword;
    }
}
