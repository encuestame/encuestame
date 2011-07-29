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

import org.encuestame.persistence.exception.EnMeOAuthSecurityException;
import org.encuestame.utils.oauth.OAuth1Token;

/**
 * A service interface for the OAuth 1 flow.
 */
public interface OAuth1RestOperations {

    /**
     * Begin the account connection process by fetching a new request token from this service provider.
     * The request token should be stored in the user's session up until the authorization callback is made and it's time to exchange it for an {@link #exchangeForAccessToken(AuthorizedRequestToken) access token}.
     * @param callbackUrl the URL the provider should redirect to after the member authorizes the connection. Ignored for OAuth 1.0 providers.
     * @throws EnMeOAuthSecurityException
     */
    OAuth1Token fetchNewRequestToken(String callbackUrl) throws EnMeOAuthSecurityException;

    /**
     * Construct the URL to redirect the user to for connection authorization.
     * @param requestToken the request token value, to be encoded in the authorize URL.
     * @param callbackUrl the URL the provider should redirect to after the member authorizes the connection. Ignored for OAuth 1.0a providers.
     * @return the absolute authorize URL to redirect the member to for authorization
     */
    String buildAuthorizeUrl(String requestToken, String callbackUrl);

    /**
     * Exchange the authorized request token for an access token.
     * @param requestToken an authorized request token and verifier. The verifier will be ignored for OAuth 1.0 providers.
     * @return an access token granted by the provider
     * @throws EnMeOAuthSecurityException
     */
    OAuth1Token exchangeForAccessToken(AuthorizedRequestToken requestToken) throws EnMeOAuthSecurityException;

}
