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
package org.encuestame.utils.social;

/**
 * Social Network Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 25, 2011
 */
public class SocialNetworkBean {

    private String consumerKey;

    private String consumerSecret;

    private String socialNetworkName;

    private SocialProvider socialProvider;

    private String urlAccessToken;

    private String urlRequestToken;

    private String apiId;

    public SocialNetworkBean() {
    }

    /**
     * @return the consumerKey
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * @param consumerKey
     *            the consumerKey to set
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * @return the consumerSecret
     */
    public String getConsumerSecret() {
        return consumerSecret;
    }

    /**
     * @param consumerSecret
     *            the consumerSecret to set
     */
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    /**
     * @return the socialNetworkName
     */
    public String getSocialNetworkName() {
        return socialNetworkName;
    }

    /**
     * @param socialNetworkName
     *            the socialNetworkName to set
     */
    public void setSocialNetworkName(String socialNetworkName) {
        this.socialNetworkName = socialNetworkName;
    }

    /**
     * @return the socialProvider
     */
    public SocialProvider getSocialProvider() {
        return socialProvider;
    }

    /**
     * @param socialProvider
     *            the socialProvider to set
     */
    public void setSocialProvider(SocialProvider socialProvider) {
        this.socialProvider = socialProvider;
    }

    /**
     * @return the urlAccessToken
     */
    public String getUrlAccessToken() {
        return urlAccessToken;
    }

    /**
     * @param urlAccessToken
     *            the urlAccessToken to set
     */
    public void setUrlAccessToken(String urlAccessToken) {
        this.urlAccessToken = urlAccessToken;
    }

    /**
     * @return the urlRequestToken
     */
    public String getUrlRequestToken() {
        return urlRequestToken;
    }

    /**
     * @param urlRequestToken
     *            the urlRequestToken to set
     */
    public void setUrlRequestToken(String urlRequestToken) {
        this.urlRequestToken = urlRequestToken;
    }

    /**
     * @return the apiId
     */
    public String getApiId() {
        return apiId;
    }

    /**
     * @param apiId
     *            the apiId to set
     */
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
}
