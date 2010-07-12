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
package org.encuestame.core.service;

import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.mail.MailSendException;

/**
 * Interface for Security Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009  11:35:01
 * @version $Id$
 */
public interface ISecurityService extends IService {

     String getDefaultUserPermission();
    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception Exception
     * @throws EnMeExpcetion excepcion
     */
    List<UnitUserBean> loadListUsers(final String currentUsername) throws Exception;


    /**
     * Add new Twitter Account.
     * @param account account.
     * @param username
     */
     void addNewTwitterAccount(final String account, final String username);

    /**
     * Assing Group to User.
     * @param user user
     * @param group group
     */
    void assingGroupToUser(final UnitUserBean user, final UnitGroupBean group);

    /**
     * Search user by username.
     * @param username username
     * @return {@link UnitUserBean}
     */
    UnitUserBean searchUserByUsername(final String username);

    /**
     * Load all list of permisssions and covert to permission bean.
     * @return list of permisssions
     */
    Collection<UnitPermission> loadAllListPermission();

    /**
     * Delete Group Domain.
     * @param group group
     */
    void deleteGroup(final UnitGroupBean group);

    /**
     * Delete user.
     * @param userBean user to delete
     * @throws EnMeExpcetion exception
     */
     void deleteUser(final UnitUserBean userBean);
    /**
     * Renew password.
     * @param userBean {@link UnitUserBean}
     * @param newPassword new password
     * @return new password
     * @throws EnMeExpcetion exception
     */
    String renewPassword(final UnitUserBean userBean, final String newPassword) throws EnMeExpcetion;
    /**
     * Update a Group.
     * @param groupBean {@link UnitGroupBean}
     */
    UnitGroupBean updateGroup(UnitGroupBean groupBean) throws EnMeExpcetion;
    /**
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    void updateUser(final UnitUserBean userBean);
    /**
     * Create a new Group.
     * @param groupBean group bean
     */
    UnitGroupBean createGroup(final UnitGroupBean groupBean , final String username);
    /**
     * Create a new Permisssion.
     * @param permissionBean {@link UnitPermission}
     */
    void createPermission(final UnitPermission permissionBean);
    /**
     * Create a user, generate password for user and send email to confirmate
     * the account.
     * @param userBean user bean
     * @throws EnMeExpcetion
     * @throws EnMeExpcetion personalize exception
     */
    void createUser(final UnitUserBean userBean,  final String username) throws EnMeExpcetion;

    /**
     * Assign permission to user.
     * @param userBean {@link UnitUserBean}
     * @param permissionBean {@link UnitPermission}
     * @throws EnMeExpcetion exception
     */
    void assignPermission(
            final UnitUserBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion;

    /**
     * Assign group to user.
     * @param userBean userBean
     * @param groupBean groupBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void assingGroupFromUser(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion;

    /**
     * Remove {@link SecGroups} from User.
     * @param userBean {@link UnitUserBean}
     * @param groupBean {@link UnitGroupBean}
     * @throws EnMeExpcetion
     */
    public void removeGroupFromUser(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion;

    /**
     * Load Permission domain and covert to permission bean.
     * @param permission permission
     * @return permission bean
     * @throws EnMeExpcetion excepcion
     */
    UnitPermission loadBeanPermission(final String permission)
    throws EnMeExpcetion;

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    SecPermission loadPermission(final String permission);

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception
     * @throws Exception excepcion
     */
    void inviteUser(String email, String code) throws Exception;

    /**
     * Generate hash code invitation.
     * @return generated code
     */
    String generateHashCodeInvitation();


    /**
     * Find {@link SecUserSecondary} by UserName
     * @param username user name
     * @return {@link SecUserSecondary}
     */
    SecUserSecondary findUserByUserName(final String username);

    /**
     * Setter.
     * @param suspendedNotification suspended notification
     */
    void setSuspendedNotification(final Boolean suspendedNotification);

    /**
     * Load {@link SecUserSecondary} on {@link SelectItem}.
     * @param userId user id
     * @return List of users
     * @throws EnMeExpcetion exception
     */
     List<SelectItem> loadSelectItemSecondaryUser(final Long userId);

     /**
      * Update Twitter Account.
      * @param account account
      * @param password password
      * @param secUser {@link SecUsers}
      */
     void updateTwitterAccount(final UnitTwitterAccountBean accountBean, final String password);

     /**
      * Update OAuth Secret Twitter Credentials.
      * @param accountBean
      * @param username
      */
     void updateSecretTwitterCredentials(final UnitTwitterAccountBean accountBean,
             final String username);

     /**
      * Get Twitter Account.
      * @param twitterAccountId
      * @return
      */
     UnitTwitterAccountBean getTwitterAccount(final Long twitterAccountId);


    /**
     * Load Groups by Client.
     * @return
     */
    List<UnitGroupBean> loadGroups(final String currentUsername);

    /**
     * SingUp User
     * @param singUpBean {@link SignUpBean}.
     * @return {@link UnitUserBean}.
     */
    UnitUserBean singupUser(final SignUpBean singUpBean);

    /**
     * Search {@link SecUserSecondary} by email.
     * @param email email
     * @return
     */
    List<SecUserSecondary> searchUsersByEmail(final String email);

    /**
     * Search List of User By Username
     * @param username username
     * @return
     */
    List<SecUserSecondary> searchUsersByUsesrname(final String username);

    /**
     * Load list of {@link SecPermission}.
     * @return list of permissions.
     */
    List<UnitPermission> loadPermissions();

    /**
     * Send password to user.
     * @param email email
     * @param password password
     * @throws MailSendException
     */
    void sendUserPassword(final String email,
            final String password)
            throws MailSendException;

    /**
     * Find {@link SecUserSecondary} by UserName
     * @param username user name
     * @return {@link SecUserSecondary}
     */
    UnitUserBean findUserByEmail(final String email);

    /**
     * Get User Logged Twitter Accounts.
     * @return
     */
    List<UnitTwitterAccountBean> getUserLoggedTwitterAccount(final String username);

    /**
     * Get User Logged Verified Twitter Accounts.
     * @param username username
     * @return
     */
    List<UnitTwitterAccountBean> getUserLoggedVerifiedTwitterAccount(final String username);
}
