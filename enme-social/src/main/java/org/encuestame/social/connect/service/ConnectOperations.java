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
package org.encuestame.social.connect.service;

import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.social.connect.SocialSignInOperations;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.social.SocialProvider;

/**
 * Connect operations support.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public interface ConnectOperations extends ServiceOperations{

    /**
    *
    * @param social
    * @return
    * @throws EnMeExistPreviousConnectionException
    * @throws Exception
    */
   String connectSignInAccount(final SocialSignInOperations social)
                     throws EnMeExistPreviousConnectionException, Exception;

   /**
    *
    * @param social
    */
   void disconnectSignInAccount(final SocialSignInOperations social);

   /**
    *
    * @param provider
    * @param socialProfileId
    * @return
    */
   SocialAccount findAccountConnectionBySocialProfileId(
           final SocialProvider provider,
           final String socialProfileId);

   /**
   *
   * @param accessGrant
   * @param socialAccountId
   * @param userAccount
   * @param providerProfileUrl
   * @param currentSocialAccount
   * @return
   */
  SocialAccount updateSocialAccountConnection(
          final AccessGrant accessGrant,
          final String socialAccountId,
          final SocialAccount currentSocialAccount);
}
