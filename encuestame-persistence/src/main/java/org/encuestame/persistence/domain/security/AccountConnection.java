/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Define Account App Coonection.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 10:45:15 PM
 * @version $Id:$
 */
@Entity
@Table(name = "account_connection")
public class AccountConnection extends AbstractSocial{

    /**
     * Account Id.
     */
    private Long accountConnectionId;

    /**
     * Reference to User Account.
     */
    private UserAccount userAccout;

    /**
     * Url to Social User Profile.
     */
    private String profileUrl;

    /**
     *
     */
    private SocialAccount socialAccount;

    /**
     * @return the accountConnectionId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_connection_id", unique = true, nullable = false)
    public Long getAccountConnectionId() {
        return accountConnectionId;
    }

    /**
     * @param accountConnectionId the accountConnectionId to set
     */
    public void setAccountConnectionId(Long accountConnectionId) {
        this.accountConnectionId = accountConnectionId;
    }

    /**
     * @return the userAccout
     */
    @ManyToOne()
    public UserAccount getUserAccout() {
        return userAccout;
    }

    /**
     * @param userAccout the userAccout to set
     */
    public void setUserAccout(UserAccount userAccout) {
        this.userAccout = userAccout;
    }

    /**
     * @return the profileUrl
     */
    @Column(name = "profile_url")
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * @param profileUrl the profileUrl to set
     */
    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * @return the socialAccount
     */
    @ManyToOne()
    public SocialAccount getSocialAccount() {
        return socialAccount;
    }

    /**
     * @param socialAccount the socialAccount to set
     */
    public void setSocialAccount(final SocialAccount socialAccount) {
        this.socialAccount = socialAccount;
    }
}
