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
package org.encuestame.core.social.oauth;

import org.encuestame.persistence.domain.social.SocialProvider;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 24, 2011
 */
public class OAuth2Parameters {

    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String authorizeUrl;
    private SocialProvider socialProvider;
    private Long appId;
    private String apiKey;


    /**
     *
     * @param clientId
     * @param clientSecret
     * @param accessTokenUrl
     * @param authorizeUrl
     * @param socialProvider
     * @param appId
     */
    public OAuth2Parameters(String clientId, String clientSecret,
            String accessTokenUrl, String authorizeUrl,
            SocialProvider socialProvider, Long appId) {
        super();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
        this.authorizeUrl = authorizeUrl;
        this.socialProvider = socialProvider;
        this.appId = appId;
    }

    /**
     *
     * @param clientId
     * @param clientSecret
     * @param accessTokenUrl
     * @param authorizeUrl
     * @param socialProvider
     * @param apiKey
     */
    public OAuth2Parameters(String clientId, String clientSecret,
            String accessTokenUrl, String authorizeUrl,
            SocialProvider socialProvider, String apiKey) {
        super();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
        this.authorizeUrl = authorizeUrl;
        this.socialProvider = socialProvider;
        this.apiKey = apiKey;
    }



    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }
    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    /**
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }
    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    /**
     * @return the accessTokenUrl
     */
    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }
    /**
     * @param accessTokenUrl the accessTokenUrl to set
     */
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }
    /**
     * @return the authorizeUrl
     */
    public String getAuthorizeUrl() {
        return authorizeUrl;
    }
    /**
     * @param authorizeUrl the authorizeUrl to set
     */
    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }
    /**
     * @return the socialProvider
     */
    public SocialProvider getSocialProvider() {
        return socialProvider;
    }
    /**
     * @param socialProvider the socialProvider to set
     */
    public void setSocialProvider(SocialProvider socialProvider) {
        this.socialProvider = socialProvider;
    }
    /**
     * @return the appId
     */
    public Long getAppId() {
        return appId;
    }
    /**
     * @param appId the appId to set
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }
    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }
    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
