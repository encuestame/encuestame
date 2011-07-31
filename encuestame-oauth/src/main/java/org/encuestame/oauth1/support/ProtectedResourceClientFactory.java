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
package org.encuestame.oauth1.support;

import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Factory for RestTemplate instances that execute requests for resources protected by the OAuth 1 protocol.
 */
public class ProtectedResourceClientFactory {

    /**
     * Constructs a RestTemplate that adds the OAuth1 Authorization header to each request before it is executed.
     */
    public static RestTemplate create(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        final RestTemplate restTemplate = new RestTemplate(new CommonsClientHttpRequestFactory());
        restTemplate.setRequestFactory(new OAuth1RequestFactory(restTemplate.getRequestFactory(), consumerKey, consumerSecret, accessToken, accessTokenSecret));
        return restTemplate;
    }
}