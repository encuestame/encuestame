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
package org.encuestame.core.service;

import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.social.SocialProvider;

import java.util.List;

/**
 * Social Factory.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 8, 2011
 */
@Deprecated
public interface SocialOperations {

    /**
     * Change state social account.
     * @param accountId
     * @param username
     * @param action
     * @throws EnMeNoResultsFoundException
     * @throws IllegalSocialActionException
     */
//    void changeStateSocialAccount(
//            final Long accountId,
//            final String username,
//            final String action) throws EnMeNoResultsFoundException, IllegalSocialActionException;

    /**
     * Update Twitter Account.
     * @param accountBean accountBean
     * @param password password
     * @param verify verify
     */
//     void updateTwitterAccount(final SocialAccountBean accountBean, final String password,
//                               final Boolean verify);

     /**
      * Update OAuth Secret Twitter Credentials.
      * @param accountBean
      * @param username
      */
//     void updateSecretTwitterCredentials(final SocialAccountBean accountBean,
//             final String username) throws EnMeExpcetion;

     /**
      * Get Twitter Account.
      * @param twitterAccountId
      * @return
      */
//     SocialAccountBean getTwitterAccount(final Long twitterAccountId);

     /**
      * Get User Logged Twitter Accounts.
      * @param username
      * @param provider
      * @return
      * @throws EnMeNoResultsFoundException
      */
//     List<SocialAccountBean> getUserLoggedSocialAccount(
//             final String username
//             , final SocialProvider provider) throws EnMeNoResultsFoundException;

     /**
      * Get User Logged Verified Twitter Accounts.
      * @param username username
      * @param socialProvider
      * @return
      * @throws EnMeNoResultsFoundException
      */
     List<SocialAccountBean> getUserLoggedVerifiedTwitterAccount(
             final String username,
             final SocialProvider socialProvider) throws EnMeNoResultsFoundException;

     /**
      * Update OAuth Token/Secret Social Account.
      * @param socialAccountId
      * @param token
      * @param tokenSecret
      * @param username
      * @param account
      * @param socialProvider
      * @throws EnMeExpcetion
      */
//     public void addOrUpdateOAuthTokenSocialAccount(
//             final Long socialAccountId,
//             final String token,
//             final String tokenSecret,
//             final String username,
//             final UserAccount account,
//             final SocialProvider socialProvider) throws EnMeExpcetion;

}
