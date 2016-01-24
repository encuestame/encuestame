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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.oauth.AbstractOAuthSupport;
import org.encuestame.persistence.exception.EnMeBadCredentialsException;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriTemplate;

/**
 * Oauth 1 support.
 * OAuth1 implementation that uses REST-template to make the OAuth calls.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public class OAuth1Support extends AbstractOAuthSupport implements OAuth1RestOperations {

    private final String consumerKey;

    private final String consumerSecret;

    private final String requestTokenUrl;

    private final String accessTokenUrl;


    private final boolean oauth10a;

    private static Log log = LogFactory.getLog(OAuth1Support.class);

            /**
             * Constructs an OAuth1Template in OAuth 1.0a mode.
             */

    public OAuth1Support(String consumerKey, String consumerSecret, String requestTokenUrl, String authorizeUrl, String accessTokenUrl) {
        this(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl, accessTokenUrl, true);
        log.debug("consumer key "+consumerKey);
        log.debug("consumerSecret key "+consumerSecret);
        log.debug("requestTokenUrl key "+requestTokenUrl);
        log.debug("authorizeUrl key "+authorizeUrl);
        log.debug("accessTokenUrl key "+accessTokenUrl);
    }

    /**
     * Constructs an OAuth1Template.
     * @param oauth10a if true this template operates against an OAuth 1.0a provider. If false, it works in OAuth 1.0 mode.
     */
    public OAuth1Support(String consumerKey, String consumerSecret, String requestTokenUrl, String authorizeUrl, String accessTokenUrl, boolean oauth10a) {
        super(Arrays.<HttpMessageConverter<?>> asList(
                new StringHttpMessageConverter(), new FormHttpMessageConverter()));
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.requestTokenUrl = requestTokenUrl;
        this.oauth10a = oauth10a;
        this.authorizeUrlTemplate = new UriTemplate(authorizeUrl);
        this.accessTokenUrl = accessTokenUrl;

    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.oauth1.OAuth1RestOperations#fetchNewRequestToken(java.lang.String)
     */
    public OAuth1Token fetchNewRequestToken(String callbackUrl) throws EnMeOAuthSecurityException {
        Map<String, String> requestTokenParameters = new HashMap<String, String>();
        if (oauth10a) {
            requestTokenParameters.put("oauth_callback", callbackUrl);
        }
        return getTokenFromProvider(requestTokenUrl, requestTokenParameters, Collections.<String, String> emptyMap(), null);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.oauth1.OAuth1RestOperations#buildAuthorizeUrl(java.lang.String, java.lang.String)
     */
    public String buildAuthorizeUrl(String requestToken, String callbackUrl) {
        if (oauth10a) {
            return authorizeUrlTemplate.expand(requestToken).toString();
        } else {
            return authorizeUrlTemplate.expand(requestToken, callbackUrl).toString();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.oauth1.OAuth1RestOperations#exchangeForAccessToken(org.encuestame.core.social.oauth1.AuthorizedRequestToken)
     */
    public OAuth1Token exchangeForAccessToken(AuthorizedRequestToken requestToken) throws EnMeOAuthSecurityException {
        Map<String, String> accessTokenParameters = new HashMap<String, String>();
        accessTokenParameters.put("oauth_token", requestToken.getValue());
        if (oauth10a) {
            accessTokenParameters.put("oauth_verifier", requestToken.getVerifier());
        }
        return getTokenFromProvider(accessTokenUrl, accessTokenParameters, Collections.<String, String> emptyMap(), requestToken.getSecret());
    }

    // internal helpers

    /**
     *
     * @param tokenUrl
     * @param tokenRequestParameters
     * @param additionalParameters
     * @param tokenSecret
     * @return
     * @throws EnMeOAuthSecurityException
     */
    protected OAuth1Token getTokenFromProvider(String tokenUrl, Map<String, String> tokenRequestParameters, Map<String, String> additionalParameters, String tokenSecret) throws EnMeOAuthSecurityException {
        log.debug("getTokenFromProvider TOKEN "+tokenUrl);
        log.debug("getTokenFromProvider TOKEN "+tokenRequestParameters);
        log.debug("getTokenFromProvider TOKEN "+additionalParameters);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getAuthorizationHeaderValue(tokenUrl, tokenRequestParameters, additionalParameters, tokenSecret));
        MultiValueMap<String, String> bodyParameters = new LinkedMultiValueMap<String, String>();
        bodyParameters.setAll(additionalParameters);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(bodyParameters, headers);
        Map<String, String> responseMap = null;
        /*
         * org.springframework.web.client.HttpServerErrorException: 503 Service Unavailable
         * Sometimes, api service are unavailable, like the famous twitter over capacity
         */
        try {
            ResponseEntity<String> response = getRestTemplate().exchange(tokenUrl, HttpMethod.POST, request, String.class);
            responseMap = parseResponse(response.getBody());
        } catch (HttpServerErrorException e) {
        	e.printStackTrace();
            throw new EnMeOAuthSecurityException(e);
        } catch (HttpClientErrorException e) {
            // normally if the social credentials are wrong
        	e.printStackTrace();
            throw new EnMeBadCredentialsException(e.getMessage());
        } catch (Exception e) {
            // another kind of error, possible wrong configuration
            //TODO : only happends with twitter
        	e.printStackTrace();
            throw new EnMeBadCredentialsException(e.getMessage());
        }

        return new OAuth1Token(responseMap.get("oauth_token"), responseMap.get("oauth_token_secret"));
    }

    // manually parse the response instead of using a message converter.
    // The response content type could be text/plain, text/html, etc...and may not trigger the form-encoded message converter
    private Map<String, String> parseResponse(String response) {
        Map<String, String> responseMap = new HashMap<String, String>();
        String[] responseEntries = response.split("&");
        for (String entry : responseEntries) {
            String[] keyValuePair = entry.trim().split("=");
            if (keyValuePair.length > 1) {
                responseMap.put(keyValuePair[0], keyValuePair[1]);
            }
        }
        return responseMap;
    }

    protected String getAuthorizationHeaderValue(String tokenUrl, Map<String, String> tokenRequestParameters, Map<String, String> additionalParameters, String tokenSecret) {
        Map<String, String> oauthParameters = OAuth1Utils.commonOAuthParameters(consumerKey);
        oauthParameters.putAll(tokenRequestParameters);
        return OAuth1Utils.buildAuthorizationHeaderValue(tokenUrl, oauthParameters, additionalParameters, HttpMethod.POST, consumerSecret, tokenSecret);
    }
}