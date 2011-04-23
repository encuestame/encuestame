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
package org.encuestame.business.service.social;

import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.social.oauth1.OAuth1RestOperations;
import org.encuestame.core.social.oauth1.OAuth1Support;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.OAuth1Token;

/**
 * Description.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public class OAuth1RequestProvider {

    /**
    *
    */
    private OAuth1RestOperations oAuth1RestTemplate;


    private OAuth1Token requestToken;


    private SocialProvider provider;

    /**
     *
     * @param apiKey
     * @param consumerSecret
     * @param requestTokenUrl
     * @param authorizeUrl
     * @param accessToken
     */
    public OAuth1RequestProvider(final String apiKey,
            final String consumerSecret,
            final String requestTokenUrl,
            final String authorizeUrl,
            final String accessToken,
            final SocialProvider socialProvider) {
        this.provider = socialProvider;
        oAuth1RestTemplate = new OAuth1Support(apiKey, consumerSecret, requestTokenUrl,
                authorizeUrl, accessToken);
    }

    /**
     *
     * @return the requestToken
     */
    public OAuth1Token getRequestToken(final HttpServletRequest request) {
        this.requestToken = oAuth1RestTemplate.fetchNewRequestToken(this.buildCallBackUrl(request));
        return this.requestToken;
    }


    public String buildCallBackUrl(final HttpServletRequest request){
        final StringBuilder callBackurl = new StringBuilder(InternetUtils.getDomain(request));
        callBackurl.append("/social/back/");
        callBackurl.append(provider.toString().toLowerCase());
        return callBackurl.toString();
    }


    public String buildRequestTokenUrl(final HttpServletRequest request) {
        final StringBuilder redirect = new StringBuilder("redirect:");
        redirect.append(this.getoAuth1RestTemplate().buildAuthorizeUrl(
                requestToken.getValue(), this.buildCallBackUrl(request)));
        return redirect.toString();
    }

    /**
     * @return the oAuth1RestTemplate
     */
    public OAuth1RestOperations getoAuth1RestTemplate() {
        return oAuth1RestTemplate;
    }


}

