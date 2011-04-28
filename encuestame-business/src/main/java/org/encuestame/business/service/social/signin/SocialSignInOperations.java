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
package org.encuestame.business.service.social.signin;

import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 2:11:24 AM
 * @version Id:
 */
public interface SocialSignInOperations{

        /**
         *
         * @param accountId
         * @param requestToken
         * @throws EnMeExistPreviousConnectionException
         */
        void connect(String accountId, AccessGrant acessGrant) throws EnMeExistPreviousConnectionException;

        /**
         *
         * @param accountId
         * @param accessToken
         * @param providerAccountId
         */
        void addConnection(String accountId, String accessToken);

        /**
         * Returns true if the member account is connected to this provider, false otherwise.
         */
        boolean isConnected(String accountId);

        /**
         * Sever the connection between the member account and this service provider.
         * Has no effect if no connection is established to begin with.
         */
        void disconnect(String accountId);

        /**
         * Authenticate a member Account by a connection established with this service provider.
         * Used to support "Sign in using Facebook"-type scenarios, where the access token identifying a connection is available to client code, typically a cookie managed by JavaScript.
         * @throws NoSuchAccountConnectionException no such connection has been established between a member and this service provider
         */
        UserAccount findAccountByConnection(String accessToken) throws EnMeNoResultsFoundException;

}
