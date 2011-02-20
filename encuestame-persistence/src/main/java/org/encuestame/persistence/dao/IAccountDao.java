/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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

import java.util.List;

import org.encuestame.persistence.dao.imp.AccountDaoImp;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.SocialAccountProvider;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.oauth.OAuthToken;
import org.hibernate.HibernateException;

/**
 * Interface SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
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
     * Get list of users by username.
     * @param username username
     * @return list of users
     */
    List<UserAccount> getUsersByUsername(final String username);

    /**
     * Get {@link UserAccount} but {@link Account} id.
     * @param userId user id
     * @return secondary user list
     */
    List<UserAccount> getSecondaryUsersByUserId(final Long userId);

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
     * @return List {@link SocialAccount}.
     *
     */
    List<SocialAccount> getTwitterAccountByUser(final Account secUsers);

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    SocialAccount getTwitterAccount(final Long twitterAccountId);

    /**
     * Get Twitter Verified Accounts.
     * @param secUsers {@link AccountDaoImp}
     * @return List {@link SocialAccount}.
     */
   List<SocialAccount> getTwitterVerifiedAccountByUser(final Account secUsers);

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
    * Get Total of TweetPoll By User Editor.
    * @param userSecondary
    * @return
    */
   List<Long> getTotalSurveyByUser(final Long userId);

   /**
    * Find user account connected.
    * @param provider
    * @param providerAccount
    * @return
    */
   List<?> findUserAccountsConnectedTo(String provider,
                  List<SocialAccountProvider> providerAccount);

   /**
    * Return {@link UserAccount} by provider name and access token key.
    * @param provider
    * @param accessToken
    * @return
    * @throws EnMeExpcetion
    */
    UserAccount findAccountByConnection(String provider,
                      String accessToken) throws EnMeDomainNotFoundException;

    /**
     * Get Provider Account Id.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeDomainNotFoundException
     */
    Long getProviderAccountId(Long accountId, String provider)
         throws EnMeDomainNotFoundException;


    /**
     * Get Access Token.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeDomainNotFoundException
     */
    OAuthToken getAccessToken(Long accountId, String provider)
           throws EnMeDomainNotFoundException;

    /**
     * Disconnect Account Connection.
     * @param accountId
     * @param provider
     * @throws EnMeDomainNotFoundException
     */
    void disconnect(Long accountId, String provider) throws EnMeDomainNotFoundException;

    /**
     * Get Account Connection.
     * @param accountId
     * @param proviver
     * @return
     */
    AccountConnection getAccountConnection(final Long accountId, final String provider);

    /**
     * {@link AccountConnection} Is Connected.
     * @param accountId
     * @param provider
     * @return
     */
    boolean isConnected(Long accountId, String provider);

    /**
     * Add connection.
     * @param provider
     * @param accessToken
     * @param providerAccountId
     * @param userAccountId
     * @param providerProfileUrl
     * @return
     */
    AccountConnection addConnection(
                final String provider,
                final OAuthToken token,
                final String socialAccountId,
                final Long userAccountId,
                final String providerProfileUrl);

    /**
     * Retrieve {@link AccountConnection} by access token and provider name.
     * @param provider
     * @param accessToken
     * @return
     * @throws EnMeExpcetion
     */
    public AccountConnection findAccountConnectionByAccessToken(
                       final String provider,
                       final String accessToken);
}
