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
import org.encuestame.core.social.oauth.OAuth2Parameters;
import org.encuestame.core.social.oauth2.OAuth2RestOperations;
import org.encuestame.core.social.oauth2.OAuth2Support;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.AccessGrant;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public class OAuth2RequestFlow {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());


    public String DEFAULT_CALLBACK_PATH = "/social/back/";


    OAuth2RestOperations oAuth2RestOperations;

    /**
    *
    */
   private SocialProvider provider;


   /**
    *
    */
   private OAuth2Parameters auth2Parameters;


   /**
    *
    * @param auth2Parameters
    */
    public OAuth2RequestFlow(final OAuth2Parameters auth2Parameters){
        this.provider = auth2Parameters.getSocialProvider();
        this.auth2Parameters = auth2Parameters;
        this.oAuth2RestOperations = new OAuth2Support(auth2Parameters.getClientId(),
                auth2Parameters.getClientSecret(),
                auth2Parameters.getAuthorizeUrl(),
                auth2Parameters.getAccessTokenUrl());
    }

    /**
     *
     * @param code
     * @param httpRequest
     * @return
     */
    public AccessGrant getAccessGrant(final String code,final HttpServletRequest httpRequest){
        log.debug("Access Grant "+this.buildCallBackUrl(httpRequest));
        log.debug("Access Grant code "+code);
        return  getoAuth2RestOperations().exchangeForAccess(code, this.buildCallBackUrl(httpRequest));
    }

    /**
     *
     * @param scope
     * @param httpRequest
     * @return
     */
    public String buildOAuth2AuthorizeUrl(
            final String scope,
            final HttpServletRequest httpRequest,
            final Boolean forceScope){
        this.oAuth2RestOperations = new OAuth2Support(auth2Parameters.getAppId() == null
                ? auth2Parameters.getApiKey() : auth2Parameters.getAppId().toString(),
                auth2Parameters.getClientSecret(),
                auth2Parameters.getAuthorizeUrl(),
                auth2Parameters.getAccessTokenUrl());
        final StringBuilder authorizeUrl = new StringBuilder("redirect:");
        authorizeUrl.append(oAuth2RestOperations.buildAuthorizeUrl(this.buildCallBackUrl(httpRequest), scope));
        if (forceScope) {
            authorizeUrl.append("&scope=");
            authorizeUrl.append(scope);
        }
        log.debug("Authorize Url "+authorizeUrl.toString() + " for "+this.provider.name());
        return authorizeUrl.toString();
    }

    /**
     *
     * @param request
     * @return
     */
    public String buildCallBackUrl(final HttpServletRequest request){
        final StringBuilder callBackurl = new StringBuilder(InternetUtils.getDomain(request));
        callBackurl.append(DEFAULT_CALLBACK_PATH);
        callBackurl.append(provider.toString().toLowerCase());
        log.debug("buildCallBackUrl "+callBackurl.toString());
        return callBackurl.toString();
    }

    /**
     * @return the oAuth2RestOperations
     */
    public OAuth2RestOperations getoAuth2RestOperations() {
        return oAuth2RestOperations;
    }

    /**
     * @return the provider
     */
    public SocialProvider getProvider() {
        return provider;
    }
}
