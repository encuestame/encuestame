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

import org.encuestame.persistence.dao.imp.SecUserDaoImp;
import org.encuestame.persistence.domain.security.SecUser;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.persistence.domain.security.SecUserTwitterAccounts;
import org.hibernate.HibernateException;

/**
 * Interface SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public interface ISecUserDao extends IBaseDao {

    /**
     * @param username username
     * @return {@link SecUserSecondary}
     * @throws HibernateException HibernateException
     */
    SecUserSecondary getUserByUsername(final String username);

    /**
     * Retrieve Total Users.
     * @param secUsers
     * @return
     */
    Long retrieveTotalUsers(final SecUser secUsers);


    /**
     * @return List {@link SecUserSecondary}
     * @throws HibernateException HibernateException
     */
     List<SecUserSecondary> findAll();

     /**
      * Retrieve List of Secondary users without owner account.
      * @param secUsers {@link SecUser}.
      * @return List of {@link SecUserSecondary}
      */
     List<SecUserSecondary> retrieveListOwnerUsers(final SecUser secUsers,
                final Integer maxResults, final Integer start);

    /**
     * @param userId userId
     * @return {@link SecUser}
     * @throws HibernateException HibernateException
     */
    SecUser getUserById(final Long userId);

    /**
     * @param userId userId
     * @return {@link SecUserSecondary}
     * @throws HibernateException HibernateException
     */
    SecUserSecondary getSecondaryUserById(final Long userId);

    /**
     * Get list of users by username.
     * @param username username
     * @return list of users
     */
    List<SecUserSecondary> getUsersByUsername(final String username);

    /**
     * Get {@link SecUserSecondary} but {@link SecUser} id.
     * @param userId user id
     * @return secondary user list
     */
    List<SecUserSecondary> getSecondaryUsersByUserId(final Long userId);

    /**
     * Search user by email
     * @param email email
     * @return
     */
    List<SecUserSecondary> searchUsersByEmail(final String email);

    /**
     * Get one user by email.
     * @param email
     * @return
     */
    SecUserSecondary getUserByEmail(final String email);

    /**
     * Get Twitter Accounts.
     * @param secUsers {@link SecUser}.
     * @return List {@link SecUserTwitterAccounts}.
     *
     */
    List<SecUserTwitterAccounts> getTwitterAccountByUser(final SecUser secUsers);

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    SecUserTwitterAccounts getTwitterAccount(final Long twitterAccountId);

    /**
     * Get Twitter Verified Accounts.
     * @param secUsers {@link SecUserDaoImp}
     * @return List {@link SecUserTwitterAccounts}.
     */
   List<SecUserTwitterAccounts> getTwitterVerifiedAccountByUser(final SecUser secUsers);

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

}
