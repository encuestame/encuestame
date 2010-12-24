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
import javax.persistence.Table;

/**
 * Social Account Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 11:44:52 PM
 * @version $Id:$
 */
@Entity
@Table(name = "oauth_account_social_provider")
public class SocialAccountProvider {

    private Long socialProviderId;

    private String name;

    private String implementation;

    private String apiKey;

    private String secret;

    private Long appId;

    private String requestTokenUrl;

    private String authorizeUrl;

    private String accessTokenUrl;

    /**
     * @return the socialProviderId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "social_provider_id", unique = true, nullable = false)
    public Long getSocialProviderId() {
        return socialProviderId;
    }

    /**
     * @param socialProviderId the socialProviderId to set
     */
    public void setSocialProviderId(final Long socialProviderId) {
        this.socialProviderId = socialProviderId;
    }

    /**
     * @return the name
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the implementation
     */
    @Column(name = "implementation")
    public String getImplementation() {
        return implementation;
    }

    /**
     * @param implementation the implementation to set
     */
    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    /**
     * @return the apiKey
     */
    @Column(name = "api_key")
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the secret
     */
    @Column(name = "secret")
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(final String secret) {
        this.secret = secret;
    }

    /**
     * @return the appId
     */
    @Column(name = "app_id")
    public Long getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(final Long appId) {
        this.appId = appId;
    }

    /**
     * @return the requestTokenUrl
     */
    @Column(name = "request_token_url")
    public String getRequestTokenUrl() {
        return requestTokenUrl;
    }

    /**
     * @param requestTokenUrl the requestTokenUrl to set
     */
    public void setRequestTokenUrl(final String requestTokenUrl) {
        this.requestTokenUrl = requestTokenUrl;
    }

    /**
     * @return the authorizeUrl
     */
    @Column(name = "authorize_url")
    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    /**
     * @param authorizeUrl the authorizeUrl to set
     */
    public void setAuthorizeUrl(final String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    /**
     * @return the accessTokenUrl
     */
    @Column(name = "access_token_url")
    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    /**
     * @param accessTokenUrl the accessTokenUrl to set
     */
    public void setAccessTokenUrl(final String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }
}
