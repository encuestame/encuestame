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
package org.encuestame.persistence.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.encuestame.persistence.dao.imp.AccountDaoImp;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.hibernate.HibernateException;

/**
 * Interface SecUsers.
 * @author Picado, Juan juanATencuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public interface IAccountDao extends IBaseDao {

    /**
     * @param username username
     * @return {@link UserAccount}
     * @throws HibernateException HibernateException
     */
    UserAccount getUserByUsername(final String username);

    /**
     * Retrieve Total Users.
     * @param secUsers
     * @return
     */
    Long retrieveTotalUsers(final Account secUsers);


    /**
     * @return List {@link UserAccount}
     * @throws HibernateException HibernateException
     */
     List<UserAccount> findAll();

     /**
      * Retrieve List of Secondary users without owner account.
      * @param secUsers {@link Account}.
      * @return List of {@link UserAccount}
      */
     List<UserAccount> retrieveListOwnerUsers(final Account secUsers,
                final Integer maxResults, final Integer start);

    /**
     * @param userId userId
     * @return {@link Account}
     * @throws HibernateException HibernateException
     */
    Account getUserById(final Long userId);

    /**
     * @param userId userId
     * @return {@link UserAccount}
     * @throws HibernateException HibernateException
     */
    UserAccount getUserAccountById(final Long userId);

    /**
     * Search user by email
     * @param email email
     * @return
     */
    List<UserAccount> searchUsersByEmail(final String email);

    /**
     * Get one user by email.
     * @param email
     * @return
     */
    UserAccount getUserByEmail(final String email);

    /**
     * Get Twitter Accounts.
     * @param secUsers {@link Account}.
     * @param provider
     * @return List {@link SocialAccount}.
     *
     */
    List<SocialAccount> getSocialAccountByAccount(final Account secUsers,
            final SocialProvider provider);

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    SocialAccount getSocialAccountById(final Long socialAccountId);

    /**
     * Get Social Account.
     * @param socialProvider
     * @param socialAccountId
     * @return
     */
    SocialAccount getSocialAccount(final SocialProvider socialProvider, final String socialAccountId);


    /**
     * Return a {@link SocialAccount} by {@link SocialProvider} and social profile id (unique) and social user name.
     * @param socialProvider
     * @param socialProfileId
     * @param socialUsername
     * @return
     */
    SocialAccount getSocialAccount(
            final SocialProvider socialProvider,
            final String socialProfileId,
            final String socialUsername);

    /**
     * Get Social Account.
     * @param socialAccountId
     * @param account
     * @return
     */
    SocialAccount getSocialAccount(final Long socialAccountId, final Account account);

    /**
     * Get Twitter Verified Accounts.
     * @param secUsers {@link AccountDaoImp}
     * @param provider {@link SocialProvider}
     * @return List {@link SocialAccount}.
     */
   List<SocialAccount> getSocialVerifiedAccountByUserAccount(
           final Account secUsers,
           final SocialProvider provider);

   /**
    * Get Total of TweetPoll By User Editor.
    * @param userSecondary
    * @return
    */
   List<Long> getTotalTweetPollByUser(final Long userId);

  /**
   * Get Total of TweetPoll By User Editor.
   * @param userSecondary
   * @return
   */
   List<Long> getTotalPollByUser(final Long userId);


   /**
    * Return {@link UserAccount} by provider name and access token key.
    * @param provider
    * @param accessToken
    * @return
    * @throws EnMeExpcetion
    */
    UserAccount findAccountByConnection(final SocialProvider provider, final String profileId) throws EnMeNoResultsFoundException;

    /**
     * Get Provider Account Id.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Long getProviderAccountId(String accountId, SocialProvider provider)
         throws EnMeNoResultsFoundException;


    /**
     * Get Access Token.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    OAuth1Token getAccessToken(String accountId, SocialProvider provider)
           throws EnMeNoResultsFoundException;

    /**
     * Disconnect Account Connection.
     * @param accountId
     * @param provider
     * @throws EnMeNoResultsFoundException
     */
    void disconnect(String accountId, SocialProvider provider) throws EnMeNoResultsFoundException;

    /**
     * Get Account Connection.
     * @param accountId
     * @param proviver
     * @return
     */
    SocialAccount getAccountConnection(final String accountId, final SocialProvider provider);

    /**
     * {@link SocialAccount} Is Connected.
     * @param accountId
     * @param provider
     * @return
     */
    boolean isConnected(String accountId, SocialProvider provider);

    /**
     *
     * @param provider
     * @param token
     * @param accessGrant
     * @param socialAccountId
     * @param userAccountId
     * @param providerProfileUrl
     * @param socialAccoun
     * @return
     */
    SocialAccount updateSocialAccountConnection(
            final AccessGrant accessGrant, //OAuth2
            final String socialAccountId,
            final SocialAccount socialAccount);

    /**
     *
     * @param provider
     * @param socialProfileId
     * @return
     */
    SocialAccount findAccountConnectionBySocialProfileId(final SocialProvider provider,
            final String socialProfileId);

    /**
     * Get list of id accounts only if are enabled.
     * @return list of id's.
     */
    List<Long> getAccountsEnabled(final Boolean option);

    /**
     *
     * @param keyword
     * @param maxResults
     * @param startOn
     * @return
     */
    List<UserAccount> getPublicProfiles(final String keyword,
            final Integer maxResults, final Integer startOn);

    /**
    *
    * @param socialAccountId
    * @param token
    * @param tokenSecret
    * @param username
    * @param socialProvider
    * @param account
    * @return
    */
    SocialAccount createSocialAccount(
            final String socialAccountId,
            final String token,
            final String tokenSecret,
            final String expiresToken,
            final String username,
            final SocialUserProfile socialUserProfile,
            final SocialProvider socialProvider,
            final UserAccount userAccount);

       /**
     * Get {@link UserAccount} by invitationCode.
     * @param inviteCode
     * @return
     */
    UserAccount getUserAccountbyInvitationCode(final String inviteCode);

    /**
     * Get user account by status.
     * @param status
     * @param beforeDate
     * @param afterDate
     * @return
     */
    List<UserAccount> getUserAccountsbyStatus(final Boolean status, final Date beforeDate, final Date afterDate);

    /**
     * Get social accounts stats.
     * @param socialAccount {@link SocialAccount}.
     * @return
     */
    HashMap<String, Long> getSocialAccountStats(final SocialAccount socialAccount);

    /**
     * Get user accounts.
     * @param status
     * @return
     */
    List<UserAccount> getUserAccounts(final Boolean status);
}
