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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.social.SocialProvider;

/**
 * Define Account App Coonection.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 10:45:15 PM
 * @version $Id:$
 */
@Entity
@Table(name = "oauth_account_connection")
public class AccountConnection {

    /**
     * Account Id.
     */
    private Long accountConnectionId;

    /**
     * Reference to User Account.
     */
    private UserAccount userAccout;

    /**
     * Reference to Account Provider.
     */
    private SocialProvider accountProvider;

    /**
     * Access Token.
     */
    private String accessToken;

    /**
     * Social Account Id, eg: @encuestame
     */
    private String socialAccountId;

    /**
     * Secret Key.
     */
    private String secret;

    /**
     * Url to Social User Profile.
     */
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
    @Column(name="account_provider")
    @Enumerated(EnumType.STRING)
    public SocialProvider getAccountProvider() {
        return accountProvider;
    }

    /**
     * @param accountProvider the accountProvider to set
     */
    public void setAccountProvider(SocialProvider accountProvider) {
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

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    @Column(name = "secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the socialAccountId
     */
    public String getSocialAccountId() {
        return socialAccountId;
    }

    /**
     * @param socialAccountId the socialAccountId to set
     */
    public void setSocialAccountId(final String socialAccountId) {
        this.socialAccountId = socialAccountId;
    }
}
