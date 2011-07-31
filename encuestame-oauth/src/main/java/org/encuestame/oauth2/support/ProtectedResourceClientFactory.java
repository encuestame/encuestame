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
package org.encuestame.oauth2.support;

import org.encuestame.utils.oauth.OAuth2Version;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Factory for RestTemplate instances that execute requests for resources protected by the OAuth 2 protocol.
 */
public class ProtectedResourceClientFactory {

    /**
     * Constructs a RestTemplate that adds the Authorization header using the bearer token style described in the latest draft (draft 12) of the OAuth2 specification:
     * http://tools.ietf.org/html/draft-ietf-oauth-v2-12#section-7.1
     * @param accessToken the access token
     */
    public static RestTemplate standard(String accessToken) {
        return version(accessToken, OAuth2Version.STANDARD);
    }

    /**
     * Constructs a RestTemplate that adds the Authorization header using the style described in the draft 10 of the OAuth2 specification:
     * http://tools.ietf.org/html/draft-ietf-oauth-v2-10#section-5.1.1
     * @param accessToken the access token
     */
    public static RestTemplate draft10(String accessToken) {
        return version(accessToken, OAuth2Version.DRAFT_10);
    }

    /**
     * Constructs a RestTemplate that adds the Authorization header using the style described in the draft 8 of the OAuth2 specification:
     * http://tools.ietf.org/html/draft-ietf-oauth-v2-08#section-5.1
     * @param accessToken the access token
     */
    public static RestTemplate draft8(String accessToken) {
        return version(accessToken, OAuth2Version.DRAFT_8);
    }

    /**
     *
     * @param accessToken
     * @param version
     * @return
     */
    private static RestTemplate version(String accessToken, OAuth2Version version) {
        final RestTemplate restTemplate = new RestTemplate(new CommonsClientHttpRequestFactory());
        restTemplate.setRequestFactory(new OAuth2RequestFactory(restTemplate.getRequestFactory(), accessToken, version));
        return restTemplate;
    }
}
