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
package org.encuestame.business.service.imp;

import java.util.Collection;
import java.util.List;

import org.encuestame.business.service.SecurityService.FollowOperations;
import org.encuestame.business.service.SecurityService.Profile;
import org.encuestame.business.service.social.signin.SocialSignInOperations;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.mail.MailSendException;

/**
 * Interface for Security Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009  11:35:01
 * @version $Id$
 */
public interface SecurityOperations extends ServiceOperations {

     String getDefaultUserPermission();
    /**f
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception Exception
     * @throws EnMeExpcetion excepcion
     */
    List<UserAccountBean> loadListUsers(final String currentUsername, final Integer maxResults,
            final Integer start) throws Exception;

    /**
     * Get User Logged Verified Social Accounts.
     * @param username username
     * @param socialProvider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<SocialAccountBean> getUserLoggedVerifiedSocialAccounts(
            final SocialProvider socialProvider) throws EnMeNoResultsFoundException;

    /**
     * Retrieve Total Own Users.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Long totalOwnUsers(final String username) throws EnMeNoResultsFoundException;

    /**
     * Get User Complete Info.
     * @param currentUsername
     * @return
     * @throws EnMeNoResultsFoundException
     */
    UserAccountBean getUserCompleteInfo(final Long userId, final String currentUsername) throws EnMeNoResultsFoundException;


    /**
     * Assing Group to User.
     * @param user user
     * @param group group
     * @throws EnMeNoResultsFoundException
     */
    void assingGroupFromUser(
            final Long groupId,
            final Long userId,
            final String username) throws EnMeNoResultsFoundException;

    /**
     * Search user by username.
     * @param username username
     * @return {@link UserAccountBean}
     */
    UserAccountBean searchUserByUsername(final String username);

    /**
     * Load all list of permisssions and covert to permission bean.
     * @return list of permisssions
     */
    Collection<UnitPermission> loadAllListPermission();

    /**
     * Delete Group Domain.
     * @param Long group
     */
    void deleteGroup(final Long groupId);

    /**
     * Delete user.
     * @param userBean user to delete
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion exception
     */
     void deleteUser(final UserAccountBean userBean) throws EnMeNoResultsFoundException;
    /**
     * Renew password.
     * @param userBean {@link UserAccountBean}
     * @param newPassword new password
     * @return new password
     * @throws EnMeExpcetion exception
     */
    String renewPassword(final UserAccountBean userBean, final String newPassword) throws EnMeExpcetion;
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
    void updateUser(final UserAccountBean userBean);
    /**
     * Create a new Group.
     * @param groupBean group bean
     * @throws EnMeNoResultsFoundException
     */
    UnitGroupBean createGroup(final UnitGroupBean groupBean , final String username) throws EnMeNoResultsFoundException;
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
    void createUser(final UserAccountBean userBean,  final String username) throws EnMeExpcetion;

    /**
     * Assign permission to user.
     * @param userBean {@link UserAccountBean}
     * @param permissionBean {@link UnitPermission}
     * @throws EnMeExpcetion exception
     */
    void assignPermission(
            final UserAccountBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion;

    /**
     * Remove {@link Group} from User.
     * @param userBean {@link UserAccountBean}
     * @param groupBean {@link UnitGroupBean}
     * @throws EnMeExpcetion
     */
    void removeGroupFromUser(
            final UserAccountBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion;

    /**
     * Load Permission domain and covert to permission bean.
     * @param permission permission
     * @return permission bean
     * @throws EnMeExpcetion excepcion
     */
    UnitPermission loadBeanPermission(final EnMePermission  permission)
    throws EnMeExpcetion;

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    Permission loadPermission(final String permission);

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
     * Find {@link UserAccount} by UserName
     * @param username user name
     * @return {@link UserAccount}
     */
    UserAccount findUserByUserName(final String username);

    /**
     * Find {@link UserAccount} by email.
     * @param email
     * @return
     */
    UserAccount findUserAccountByEmail(final String email);

    /**
     * Load Groups by Client.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UnitGroupBean> loadGroups(final String currentUsername) throws EnMeNoResultsFoundException;

    /**
     * SingUp User
     * @param singUpBean {@link SignUpBean}.
     * @return {@link UserAccountBean}.
     */
    UserAccount singupUser(final SignUpBean singUpBean);

    /**
     * Search {@link UserAccount} by email.
     * @param email email
     * @return
     */
    List<UserAccount> searchUsersByEmail(final String email);

    /**
     * Load list of {@link Permission}.
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
     * Find {@link UserAccount} by UserName
     * @param username user name
     * @return {@link UserAccount}
     */
    UserAccountBean findUserByEmail(final String email);

    /**
     * Get Email List by Username.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UnitLists> getListbyUsername(final String username) throws EnMeNoResultsFoundException;

    /**
     * Assign Permission,
     * @param userId user id
     * @param permission {@link EnMePermission}.
     * @param loggedUse user logged.
     * @throws EnMeExpcetion exception.
     */
    void updatePermission(
            final Long userId,
            final String loggedUser,
            final EnMePermission permission,
            final String action)
            throws EnMeExpcetion;

    /**
     * Get Group by Id and User Id.
     * @param groupId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Group getGroupbyIdandUser(final Long groupId, final String username) throws EnMeNoResultsFoundException;

    /**
     * User Bean.
     * @param user
     * @return
     */
    void getStatsByUsers(final UserAccountBean user);

    /**
     * Get User by Group.
     * @param secGroupId
     * @param username
     * @return
     */
    Long getUserbyGroup(final Long groupId, final String username)throws EnMeNoResultsFoundException;

    /**
     * Count Users by Groups
     * @param username
     * @param groupId
     * @return
     */
    List<Object[]> countUsersbyGroups(final Long groupId, final String username);

    /**
     * Change User Status.
     * @param username
     * @throws EnmeFailOperation
     */
    void changeUserStatus(final String username) throws EnmeFailOperation;

    /**
     * Update property for user account.
     * @param property
     * @param value
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    void upadteAccountProfile(
            final Profile property,
            final String value,
            final String username) throws EnMeNoResultsFoundException;

    /**
     * Update Account Profile.
     * @param bio
     * @param email
     * @param username
     * @param language
     * @param completeName
     * @throws EnMeNoResultsFoundException
     */
    void upadteAccountProfile(
            final String bio,
            final String language,
            final String completeName,
            final String loggedUsername) throws EnMeNoResultsFoundException;

    /**
     * User Account Is Activated.
     * @param signUpBean
     * @return
     */
    Boolean isActivated(final SignUpBean signUpBean);

    /**
     * Add Followers.
     * @param myUser
     * @param followerUser
     * @return
     * @throws EnMeNoResultsFoundException
     */
    UserAccount addFollower(final String myUser, final String followerUser) throws EnMeNoResultsFoundException;

   /**
    * Follow Operations.
    * @param userAcc
    * @param myUsername
    * @param followerUser
    * @param operation
    * @throws EnMeNoResultsFoundException
    */
    void followOperations(final UserAccount userAcc,
            final String myUsername, final String followerUser,
            final FollowOperations operation) throws EnMeNoResultsFoundException;

    /**
     * Check if exist {@link SocialAccount} with unique social profile social id.
     * @param socialProvider
     * @param socialAccountId
     * @return
     */
    SocialAccount getCurrentSocialAccount(final SocialProvider socialProvider, final String socialProfileId);

    /**
     * Update OAuth Token/Secret Social Account.
     * @param socialAccountId
     * @param token
     * @param tokenSecret
     * @param username
     * @param account
     * @param socialProvider
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    SocialAccount addNewSocialAccount(
            final String token,
            final String tokenSecret,
            final String expiresToken,
            final SocialUserProfile socialUserProfile,
            final SocialProvider socialProvider,
            final UserAccount userAccount);

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
     * Get {@link UserAccount} by code.
     * @param inviteCode
     * @return
     * @throws EnMeNoResultsFoundException
     */
    UserAccountBean getUserAccountbyCode(final String inviteCode) throws EnMeNoResultsFoundException;


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

    /**
     *
     * @param limit
     * @return
     */
    List<Notification> loadNotificationByUserAndLimit(final Integer limit, final Integer start,
            final Boolean onlyUnread);


    /**
     *
     * @param provider
     * @param socialProfileId
     * @return
     */
    SocialAccount findAccountConnectionBySocialProfileId(
            final SocialProvider provider,
            final String socialProfileId);
}
