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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.oauth.AbstractOAuthSupport;
import org.encuestame.utils.oauth.AccessGrant;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

/**
 * Implementation that uses REST-template to make the OAuth calls.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public class OAuth2Support extends AbstractOAuthSupport implements OAuth2RestOperations {

    /**
     *
     */
    private final String clientId;

    private final String clientSecret;

    private final String accessTokenUrl;


    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(OAuth2Support.class);

            /**
             * OAuth2 Constructor.
             * @param clientId
             * @param clientSecret
             * @param authorizeUrl
             * @param accessTokenUrl
             */

    public OAuth2Support(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(Arrays.<HttpMessageConverter<?>> asList(new FormHttpMessageConverter() {
             public boolean canRead(Class<?> clazz, MediaType mediaType) {
                 return clazz.equals(Map.class) && mediaType != null && mediaType.getType().equals("text")
                         && mediaType.getSubtype().equals("plain");
             }
         }, new MappingJackson2HttpMessageConverter()));
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizeUrlTemplate = new UriTemplate(authorizeUrl);
        this.accessTokenUrl = accessTokenUrl;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.oauth2.OAuth2RestOperations#buildAuthorizeUrl(java.lang.String, java.lang.String)
     */
    public String buildAuthorizeUrl(String redirectUri, String scope) {
        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("client_id", clientId);
        urlVariables.put("redirect_uri", redirectUri);
        urlVariables.put("scope", scope);
        return authorizeUrlTemplate.expand(urlVariables).toString();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.oauth2.OAuth2RestOperations#exchangeForAccess(java.lang.String, java.lang.String)
     */
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri) {
        log.debug("exchangeForAccess:{"+authorizationCode);
        log.debug("exchangeForAccess redirectUri{: "+redirectUri);
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<String, String>();
        requestParameters.set("client_id", clientId);
        requestParameters.set("client_secret", clientSecret);
        requestParameters.set("code", authorizationCode);
        requestParameters.set("redirect_uri", redirectUri);
        requestParameters.set("grant_type", "authorization_code");
        log.debug("requestParameters "+requestParameters.toString());
        Map result = getRestTemplate().postForObject(accessTokenUrl, requestParameters, Map.class);
        log.debug("Access Grant "+result.toString());
        return new AccessGrant(valueOf(result.get("access_token")), valueOf(result.get("refresh_token")));
    }

    /**
     *
     * @param refreshToken
     * @return
     */
    public AccessGrant refreshToken(final String refreshToken) {
        log.debug("refreshToken"+refreshToken);
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<String, String>();
        requestParameters.set("client_id", clientId);
        requestParameters.set("client_secret", clientSecret);
        requestParameters.set("refresh_token", refreshToken);
        requestParameters.set("grant_type", "refresh_token");
        log.debug("requestParameters "+requestParameters.toString());
        @SuppressWarnings("unchecked")
        Map<String, ?> result = getRestTemplate().postForObject(accessTokenUrl, requestParameters, Map.class);
        log.debug(result);
        return new AccessGrant(valueOf(result.get("access_token")), refreshToken);
    }

    /**
     *
     */
    private String valueOf(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof List) {
            List<?> list = (List<?>) object;
            if (list.size() > 0) {
                return String.valueOf(list.get(0));
            }
            return null;
        }
        return String.valueOf(object);
    }
}
