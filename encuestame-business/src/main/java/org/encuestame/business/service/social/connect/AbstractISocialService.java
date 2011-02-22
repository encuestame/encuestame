/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service.social.connect;

import org.encuestame.core.exception.EnMeNoSuchAccountConnectionException;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.oauth.AuthorizedRequestToken;
import org.encuestame.utils.oauth.OAuthToken;
import org.springframework.social.twitter.TwitterOperations;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 31, 2010 3:34:55 PM
 * @version Id:
 */
public abstract interface AbstractISocialService {

     boolean isConnected(Long id);

        /**
         * Sever the connection between the member account and this service provider.
         * Has no effect if no connection is established to begin with.
         */
        void disconnect(Long accountId);


        String fetchProviderAccountId(TwitterOperations serviceOperations);

        /**
         * The key used to identify the local application with the remote service provider.
         * Used when establishing an account connection with the service provider.
         * Available as a public property to support client code that wishes to manage the service connection process itself, for example, in JavaScript.
         * The term "API key" is derived from the OAuth 2 specification.
         */
        String getApiKey();

        OAuthToken fetchNewRequestToken(final String callbackUrl);

        String buildAuthorizeUrl(final String requestToken);

        void connect(Long accountId, AuthorizedRequestToken requestToken) throws EnMeExistPreviousConnectionException;


        UserAccount findAccountByConnection(String accessToken) throws EnMeDomainNotFoundException;

}
