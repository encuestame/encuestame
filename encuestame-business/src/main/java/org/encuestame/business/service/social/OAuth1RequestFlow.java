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

import org.apache.log4j.Logger;
import org.encuestame.core.social.oauth1.AuthorizedRequestToken;
import org.encuestame.core.social.oauth1.OAuth1RestOperations;
import org.encuestame.core.social.oauth1.OAuth1Support;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.core.util.OAuthUtils;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.web.context.request.WebRequest;

/**
 * OAuth1 Generic Flow.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public class OAuth1RequestFlow {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
    *
    */
    private OAuth1RestOperations oAuth1RestTemplate;

    /**
     *
     */
    private OAuth1Token requestToken;

    /**
     *
     */
    private SocialProvider provider;


    public String DEFAULT_CALLBACK_PATH = "/social/back/";

    /**
     *
     * @param apiKey
     * @param consumerSecret
     * @param requestTokenUrl
     * @param authorizeUrl
     * @param accessToken
     */
    public OAuth1RequestFlow(final String apiKey,
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
     * @param scope
     * @param request
     * @param httpRequest
     * @return
     * @throws EnMeOAuthSecurityException
     */
    public String buildOAuth1AuthorizeUrl(
            final String scope,
            final WebRequest request,
            final HttpServletRequest httpRequest) throws EnMeOAuthSecurityException{
        final OAuth1Token requestToken = this.getRequestToken(httpRequest);
        request.setAttribute(OAuthUtils.OAUTH_TOKEN_ATTRIBUTE, requestToken, WebRequest.SCOPE_SESSION);
        return this.buildRequestTokenUrl(httpRequest);
    }

    /**
     *
     * @return the requestToken
     * @throws EnMeOAuthSecurityException
     */
    public OAuth1Token getRequestToken(final HttpServletRequest request) throws EnMeOAuthSecurityException {
        this.requestToken = oAuth1RestTemplate.fetchNewRequestToken(this.buildCallBackUrl(request));
        return this.requestToken;
    }

    /**
     *
     * @param verifier
     * @param request
     * @return
     * @throws EnMeOAuthSecurityException
     */
    public OAuth1Token getAccessToken(
            final String verifier,
            final WebRequest request) throws EnMeOAuthSecurityException{
        log.debug("Verifier "+verifier);
        final OAuth1Token accessToken = getoAuth1RestTemplate()
        .exchangeForAccessToken(new AuthorizedRequestToken(
                extractCachedRequestToken(request), verifier));
        return accessToken;
    }

    /**
     * Extract request token.
     * @param request
     * @return
     */
    private OAuth1Token extractCachedRequestToken(
            WebRequest request) {
        OAuth1Token requestToken = (OAuth1Token) request
                .getAttribute(OAuthUtils.OAUTH_TOKEN_ATTRIBUTE, WebRequest.SCOPE_SESSION);
        request.removeAttribute(OAuthUtils.OAUTH_TOKEN_ATTRIBUTE, WebRequest.SCOPE_SESSION);
        log.debug("requestToken "+requestToken.toString());
        return requestToken;
    }

    /**
     *
     * @param request
     * @return
     */
    public String buildCallBackUrl(final HttpServletRequest request){
        final StringBuilder callBackurl = new StringBuilder(InternetUtils.getDomain(request));
        callBackurl.append(this.DEFAULT_CALLBACK_PATH);
        callBackurl.append(provider.toString().toLowerCase());
        return callBackurl.toString();
    }

    /**
     *
     * @param request
     * @return
     */
    public String buildRequestTokenUrl(final HttpServletRequest request) {
        final StringBuilder redirect = new StringBuilder("redirect:");
        redirect.append(this.getoAuth1RestTemplate().buildAuthorizeUrl(
                requestToken.getValue(), this.buildCallBackUrl(request)));
        log.debug("OAuth1 Authorize Url :"+redirect.toString());
        return redirect.toString();
    }

    /**
     * @return the oAuth1RestTemplate
     */
    public OAuth1RestOperations getoAuth1RestTemplate() {
        return oAuth1RestTemplate;
    }
}