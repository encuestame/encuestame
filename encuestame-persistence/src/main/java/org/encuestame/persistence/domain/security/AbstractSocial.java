/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.encuestame.persistence.domain.social.SocialProvider;

/**
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
@MappedSuperclass
public class AbstractSocial {

    /**
     * Type Accountd.
     */
    private SocialProvider accounType = SocialProvider.TWITTER;

    /**
     * Access Token.
     */
    private String accessToken;

    /**
     * Secret Token.
     */
    private String secretToken;

    /**
     * Refresh token, OAUTH2 required.
     */
    private String refreshToken;

    /**
     * Social Profile Id.
     */
    private String socialProfileId;

    /**
     *
     */
    private Long applicationKey;


    /**
     * @return the accounType
     */
    @Column(name="type_account")
    @Enumerated(EnumType.ORDINAL)
    public SocialProvider getAccounType() {
        return accounType;
    }

    /**
     * @param accounType the accounType to set
     */
    public void setAccounType(final SocialProvider accounType) {
        this.accounType = accounType;
    }

    /**
     *
     * @return
     */
    @Column(name = "oauth_token", unique = true)
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param token
     */
    public void setAccessToken(final String token) {
        this.accessToken = token;
    }

    /**
     *
     * @return
     */
    @Column(name = "oauth_secret_token", unique = true)
    public String getSecretToken() {
        return secretToken;
    }

    /**
     *
     * @param secretToken
     */
    public void setSecretToken(final String secretToken) {
        this.secretToken = secretToken;
    }

    /**
     * @return the socialAccountId
     */
    @Column (name="social_profile_id", nullable = false, unique = true)
    public String getSocialProfileId() {
        return socialProfileId;
    }

    /**
     * @param socialAccountId the socialAccountId to set
     */
    public void setSocialProfileId(final String socialAccountId) {
        this.socialProfileId = socialAccountId;
    }

    /**
     * @return the application key.
     */
    @Column(name = "oauth_app_key", unique = true)
    public Long getApplicationKey() {
        return applicationKey;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationKey(final Long applicationKey) {
        this.applicationKey = applicationKey;
    }

    /**
     * @return the refreshToken
     */
    @Column(name = "oauth_refresh_token", unique = true)
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}