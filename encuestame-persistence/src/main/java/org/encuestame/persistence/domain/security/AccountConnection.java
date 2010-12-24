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
@Table(name = "oauth_account_connection")
public class AccountConnection {

    private Long accountConnectionId;

    private SocialAccountProvider accountProvider;

    private String accessToken;

    private UserAccount userAccout;

    private String profileUrl;

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
     * @return the accountProvider
     */
    @ManyToOne()
    public SocialAccountProvider getAccountProvider() {
        return accountProvider;
    }

    /**
     * @param accountProvider the accountProvider to set
     */
    public void setAccountProvider(SocialAccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }

    /**
     * @return the accessToken
     */
    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
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
}
