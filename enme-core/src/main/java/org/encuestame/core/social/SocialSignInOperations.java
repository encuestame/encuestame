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
package org.encuestame.core.social;

import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;

/**
 * Social Sign In Operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 2:11:24 AM
 */
public interface SocialSignInOperations{

        /**
         *
         * @param accountId
         * @param acessGrant
         * @return
         * @throws EnMeExistPreviousConnectionException
         * @throws EnMeNoResultsFoundException
         */
        SocialAccount reConnect(
                final String accountId,
                final AccessGrant acessGrant,
                final SocialAccount socialAccount)
                throws EnMeExistPreviousConnectionException,
                EnMeNoResultsFoundException;


        /**
         * Return {@link SocialUserProfile}.
         * @return
         */
        SocialUserProfile getSocialUserProfile();

        /**
         *
         * @return
         */
        SocialProvider getProvider();

        /**
         *
         * @return
         */
        AccessGrant getAccessGrant();

        /**
         * Returns true if the member account is connected to this provider, false otherwise.
         * @throws EnMeExpcetion
         */
        SocialAccount isConnected(String accountId) throws EnMeExpcetion;

        /**
         * Authenticate a member Account by a connection established with this service provider.
         * Used to support "Sign in using Facebook"-type scenarios, where the access token identifying a connection is available to client code, typically a cookie managed by JavaScript.
         * @throws NoSuchAccountConnectionException no such connection has been established between a member and this service provider
         */
        SocialAccount findAccountByConnection(String accessToken) throws EnMeNoResultsFoundException;

}
