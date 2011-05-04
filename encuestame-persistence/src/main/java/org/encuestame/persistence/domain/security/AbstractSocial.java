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
     * Profile picture url.
     */
    private String profilePictureUrl;


    /**
     * Time to expires OAuth2 access token.
     */
    private String expires;


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
    @Column(name = "oauth_token")
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
    @Column(name = "oauth_secret_token")
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
    @Column(name = "oauth_app_key")
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
    @Column(name = "oauth_refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the profilePictureUrl
     */
    @Column(name = "picture_profile_url")
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    /**
     * @param profilePictureUrl the profilePictureUrl to set
     */
    public void setProfilePictureUrl(final String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    /**
     * @return the expires
     */
    @Column(name = "oauth2_expires")
    public String getExpires() {
        return expires;
    }

    /**
     * @param expires the expires to set
     */
    public void setExpires(final String expires) {
        this.expires = expires;
    }
}