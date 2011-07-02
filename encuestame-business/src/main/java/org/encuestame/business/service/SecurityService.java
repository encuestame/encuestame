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
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.business.service.social.signin.SocialSignInOperations;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.security.util.EnMePasswordUtils;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.persistence.exception.IllegalSocialActionException;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UserAccountBean;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;

/**
 * Security Bean Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009 11:35:01
 */
@Service
public class SecurityService extends AbstractBaseService implements SecurityOperations {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /** Default User Permission **/
    private static final EnMePermission DEFAULT = EnMePermission.ENCUESTAME_USER;

    /**
     * Dashboard path.
     */
    private final String DASHBOARD_REDIRECT = "redirect:/user/dashboard";

    /**
     * Retrieve Total Own Users.
     * @param username username
     * @return total own users.
     * @throws EnMeNoResultsFoundException exception
     */
    public Long totalOwnUsers(final String username) throws EnMeNoResultsFoundException{
        return getAccountDao().retrieveTotalUsers(getUserAccount(username).getAccount());
    }

    /**
     * Find {@link UserAccount} by UserName
     * @param username user name
     * @return {@link UserAccount}
     */
    public UserAccountBean findUserByEmail(final String email) {
        final UserAccount secondary = getAccountDao().getUserByEmail(email);
        return secondary == null ? null : ConvertDomainBean.convertSecondaryUserToUserBean(secondary);
    }


    /**
     * Load Groups by Client
     * @return list of groups
     * @throws EnMeNoResultsFoundException exception
     */
    public List<UnitGroupBean> loadGroups(final String currentUsername) throws EnMeNoResultsFoundException{
        final UserAccount userAccount = getUserAccount(currentUsername);
        final List<UnitGroupBean> groupBeans = new ArrayList<UnitGroupBean>();
        final List<Group> groupsList = getGroupDao().loadGroupsByUser(userAccount.getAccount());
        for (Group groups : groupsList) {
            groupBeans.add(ConvertDomainBean.convertGroupDomainToBean(groups));
        }
        return groupBeans;
    }

    /**
     * Assing Group to User.
     * @param user user
     * @param group group
     */
    public void assingGroupToUser(final UserAccountBean user, final UnitGroupBean group){
        // SecUsers userD = getUser(user.getUsername());
        // SecPermission perD = loadPermission(permission.getPermission());
        //assingGroup(user, group);
    }


    /**
     * Search user by username.
     * @param username username
     * @return {@link UserAccountBean}
     */
    public UserAccountBean searchUserByUsername(final String username) {
        final UserAccount userDomain = getAccountDao().getUserByUsername(username);
        UserAccountBean user = null;
        if (userDomain != null) {
            user = ConvertDomainBean.convertSecondaryUserToUserBean(userDomain);
        } else {
            log.error("user not found");
        }
        return user;
    }

    /**
     * Load all list of permisssions and covert to permission bean.
     * @return list of permisssions
     */
    public Collection<UnitPermission> loadAllListPermission() {
        final Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        final Collection<Permission> listPermission = getPermissionDao()
                .loadAllPermissions();
        for (Iterator<Permission> iterator = listPermission.iterator(); iterator
                .hasNext();) {
            final UnitPermission permissionBean = new UnitPermission();
            Permission permission = iterator.next();
            if(!permission.equals(EnMePermission.ENCUESTAME_USER)){ //this permissions not should be included.
                permissionBean.setId(permission.getIdPermission());
                permissionBean.setPermission(permission.getPermission().toString());
                permissionBean.setDescription(permission.getPermissionDescription());
                loadListPermission.add(permissionBean);
            }
        }
        return loadListPermission;
    }

    /**
     * Delete Group Domain.
     * @param group group
     */
    public void deleteGroup(final Long groupId) {
        final Group group = getGroupDao().find(groupId);
        getGroupDao().delete(group);
    }

    /**
     * Delete user.
     * @param userBean user to delete
     * @throws EnMeNoResultsFoundException
     */
    public void deleteUser(final UserAccountBean userBean) throws EnMeNoResultsFoundException{
            final UserAccount userDomain = getUserAccount(userBean.getUsername());
                log.info("notify delete account");
                getMailService().sendDeleteNotification(userBean.getEmail().trim(),
                        getMessageProperties("userMessageDeleteNotification"));
                log.info("deleting user");
                getAccountDao().delete(userDomain);
                log.info("user deleted");
    }

    /**
     * Renew password.
     * @param userBean {@link UserAccountBean}
     * @param newPassword new password
     * @throws EnMeExpcetion
     */
    public String renewPassword(final UserAccountBean userBean, String newPassword) throws EnMeExpcetion {
        // search user
        final UserAccount userDomain = getUserAccount(userBean.getUsername());
        // validate user and password
        if (userDomain != null && newPassword != null) {
            //set new password
            userDomain.setPassword(EnMePasswordUtils.encryptPassworD(newPassword));
            //TODO: security risk?
            userBean.setPassword(newPassword);
            //if notification is suspended we need retrieve password
            //if (getSuspendedNotification()) {
                getMailService().sendRenewPasswordEmail(userBean);
            //} else {
                log.warn("Notifications Email are suspendend");
            //}
            //saving user.
            getAccountDao().saveOrUpdate(userDomain);
        }
        else {
            //if we have a problem with user, we retrieve null value
           throw new EnMeExpcetion("error on renew password");
        }
        return newPassword;
    }

    /**
     * Update a Group.
     * @param groupBean {@link UnitGroupBean}
     * @throws EnMeExpcetion exception
     */
    public UnitGroupBean updateGroup(UnitGroupBean groupBean) throws EnMeExpcetion {
        log.info("group to search "+groupBean.getId());
        final Group group = getGroupDao().find(Long.valueOf(groupBean.getId()));
        log.info("group found "+group);
        if (group != null) {
            log.info("group updated name "+groupBean.getGroupName());
            group.setGroupName(groupBean.getGroupName());
            log.info("group updated description "+groupBean.getGroupDescription());
            group.setGroupDescriptionInfo(groupBean.getGroupDescription());
            log.info("group updated state id "+groupBean.getStateId());
            // group.setIdState(Long.valueOf((groupBean.getStateId())));
            getGroupDao().saveOrUpdate(group);
            log.info("group new name "+group.getGroupName());
            log.info("group new description "+group.getGroupDescriptionInfo());
            groupBean = ConvertDomainBean.convertGroupDomainToBean(group);
        } else {
            throw new EnMeExpcetion("group not found");
        }
        return groupBean;
    }

    /**
     * Get Group by Primary User and Group Id.
     * @param Long groupId.
     * @param String username.
     * @throws EnMeExpcetion exception
     */
    public Group getGroupbyIdandUser(final Long groupId, final String username) throws EnMeNoResultsFoundException {
        return getGroupDao().getGroupByIdandUser(groupId, getPrimaryUser(username));
    }

    /**
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    public void updateUser(final UserAccountBean userBean){
        log.info("service update user method");
            final UserAccount updateUser = getAccountDao().getUserByUsername(userBean.getUsername());
            log.info("update user, user found: "+updateUser.getUid());
            if (updateUser != null) {
                updateUser.setUserEmail(userBean.getEmail());
                updateUser.setCompleteName(userBean.getName());
                updateUser.setUserStatus(userBean.getStatus());
                log.info("updateing user, user "+updateUser.getUid());
                getAccountDao().saveOrUpdate(updateUser);
            }
    }

    /**
     * Get Users by Group.
     * @param secGroupId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Long getUserbyGroup(final Long groupId, final String username) throws EnMeNoResultsFoundException{
        Long counterUsers = 0L;
        try {
             final Group group = getGroupDao().getGroupByIdandUser(groupId, getPrimaryUser(username));
             if(group != null){
             counterUsers = getGroupDao().getCountUserbyGroup(groupId);
             }
        } catch (Exception e) {
            // TODO: handle exception Group no pertenece a usuario
        }
        return counterUsers;
    }

    /**
     * Get Users by Groups.
     * @param user
     * @return
     */
    public List<Object[]> countUsersbyGroups(final Long groupId, final String username){
        List<Object[]> usersbyGroups = null;
          try {
               final Group group = getGroupDao().getGroupByIdandUser(groupId, getPrimaryUser(username));
               if(group != null){
                   usersbyGroups = getGroupDao().countUsersbyGroups(getPrimaryUser(username));
               }
          } catch (Exception e) {
              // TODO: handle exception Group don't belong to user
          }
          return usersbyGroups;
    }

    /**
     * Create a new Group.
     * @param groupBean group bean
     * @throws EnMeNoResultsFoundException
     */
    public UnitGroupBean createGroup(final UnitGroupBean groupBean, final String username) throws EnMeNoResultsFoundException {
        //log.info("Create Group");
        final Group groupDomain = new Group();
        final Account secUsers = getUserAccount(username).getAccount();
        groupDomain.setGroupDescriptionInfo(groupBean.getGroupDescription());
        groupDomain.setGroupName(groupBean.getGroupName());
        groupDomain.setIdState(null);
        groupDomain.setAccount(secUsers);
        getGroupDao().saveOrUpdate(groupDomain);
        groupBean.setId(groupDomain.getGroupId());
        return ConvertDomainBean.convertGroupDomainToBean(groupDomain);
    }

    /**
     * Create a new Permisssion.
     * @param permissionBean {@link UnitPermission}
     */
    public void createPermission(final UnitPermission permissionBean) {
        final Permission permissionDomain = new Permission();
        permissionDomain.setPermission(EnMePermission.getPermissionString(permissionBean.getPermission()));
        permissionDomain.setPermissionDescription(permissionBean.getDescription());
        getPermissionDao().saveOrUpdate(permissionDomain);
    }

    /**
     * Create a user account, generate password for user and send email to confirmate
     * the account.
     * @param userBean {@link UserAccountBean}
     * @throws EnMeExpcetion personalize exception
     * @return if password is not notified  is returned
     */
    public void createUser(final UserAccountBean userBean, final String username) throws EnMeExpcetion {
        final UserAccount userAccount = new UserAccount();
        final Account account = getUserAccount(username).getAccount();
        //validate email and password
        if (userBean.getEmail() != null && userBean.getUsername() != null) {
            userAccount.setUserEmail(userBean.getEmail());
            userAccount.setUsername(userBean.getUsername());
            userAccount.setAccount(account);
        } else {
            throw new EnMeExpcetion("needed email and username to create user");
        }
        String password = null;
        if (userBean.getPassword()!=null) {
             password = userBean.getPassword();
             userAccount.setPassword(EnMePasswordUtils.encryptPassworD(password));
        }
        else{
            password = generatePassword();
            userAccount.setPassword(EnMePasswordUtils.encryptPassworD(password));
        }
        //TODO: maybe we need create a table for editor permissions
        userAccount.setCompleteName(userBean.getName() == null ? "" : userBean.getName());
        userAccount.setUserStatus(Boolean.TRUE);
        userAccount.setEnjoyDate(new Date());
            // send to user the password to her emails
            //if((getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), password);
            //}
            // save user
            getAccountDao().saveOrUpdate(userAccount);
            // assing first default group to user
            final UserAccount retrievedUser = getAccountDao().getUserAccountById(userAccount.getUid());
            final Permission permission = getPermissionByName(SecurityService.DEFAULT);
            if(permission != null){
                final List<Permission> all = getPermissionDao().findAllPermissions();
                log.info("all permission "+all.size());
                log.info("default permission "+permission);
                retrievedUser.getSecUserPermissions().add(permission);
            }
            else{
                log.warn("error assing default permissions");
            }
            log.info("saving user");
            try{
                getAccountDao().saveOrUpdate(retrievedUser);
            } catch (Exception e) {
                throw new EnMeExpcetion(e.getMessage());
            }
    }

    /**
     * Search {@link UserAccount} by email.
     * @param email email
     * @return
     */
    public List<UserAccount> searchUsersByEmail(final String email){
        return getAccountDao().searchUsersByEmail(email);
    }

    /**
     * Get Permission by {@link EnMePermission}.
     * @param permission permission.
     * @return
     */
    public Permission getPermissionByName(final EnMePermission permission){
        final Permission permission2 = getPermissionDao().loadPermission(permission);
        return permission2;
    }


    /**
     * Assign Permissions to {@link UserAccount}.
     * @param userAccount {@link UserAccount}.
     * @param permissions List of {@link Permission}.
     */
    private void assingPermission(final UserAccount userAccount , final Set<Permission> permissions){
        for (Permission Permission : permissions) {
            if(Permission != null){
                userAccount.getSecUserPermissions().add(Permission);
            } else {
                log.error("Error on assing permission to "+userAccount.getUsername());
            }
        }
        getAccountDao().saveOrUpdate(userAccount);
    }

    /**
     * Assign permission to user.
     * @param userBean {@link UserAccountBean}
     * @param permissionBean {@link UnitPermission}
     * @throws EnMeExpcetion exception
     */

    public void assignPermission(
            final UserAccountBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion
   {
        UserAccount userDomain = null;
        Permission permissionDomain = null;
        log.info("userBean found "+userBean.getId());
        log.info("permissionBean found "+permissionBean.getId());
        if (userBean.getId() != null) {
            userDomain = getAccountDao().getUserAccountById(userBean.getId());
            log.info("user found "+userDomain);
        }
        if (permissionBean.getId() != null) {
            permissionDomain = getPermissionDao().getPermissionById(permissionBean.getId());
            log.info("permission found "+permissionDomain);
        }
        if (userDomain != null && permissionDomain != null) {
           log.info("saving permissions");
           log.info("permission selected "+permissionDomain.getPermission());
           log.info("user selected "+userDomain.getUid());
           userDomain.getSecUserPermissions().add(permissionDomain);
           getAccountDao().saveOrUpdate(userDomain);
           log.info("saved permission "+userDomain.getSecUserPermissions().size());
        } else {
            throw new EnMeExpcetion("error adding permission");
        }
    }

    /**
     * Assign Permission,
     * @param userId user id
     * @param permission {@link EnMePermission}.
     * @param loggedUse user logged.
     * @throws EnMeExpcetion exception.
     */
    public void updatePermission(
            final Long userId,
            final String loggedUser,
            final EnMePermission permission,
            final String action)
            throws EnMeExpcetion{
        final UserAccount user = getValidateUser(userId, loggedUser);
        if(user == null){
            throw new EnMeNoResultsFoundException("user not found");
        } else {
            log.debug("Update Permission "+permission.toString());
            if(action.equals("add")){
                user.getSecUserPermissions().add(this.getPermissionByName(permission));
                log.debug("Added Permission "+permission.toString());
            } else if(action.equals("remove")){
                user.getSecUserPermissions().remove(this.getPermissionByName(permission));
                log.debug("Removed Permission "+permission.toString());
            }
            getAccountDao().saveOrUpdate(user);
        }
    }

    /**
     * Assign group to user.
     * @param userBean userBean
     * @param groupBean groupBean
     * @throws EnMeNoResultsFoundException
     */
    public void assingGroupFromUser(
            final Long groupId,
            final Long userId,
            final String username) throws EnMeNoResultsFoundException {
        final UserAccount userAccount = getUser(userId); //TODO: I need confirm this user perhaps same group of logged user.
        //search group by group id and owner user id.
        final Group group = getGroupDao().getGroupById(groupId, getUserAccount(username).getAccount());
        if(group == null){
            throw new EnMeNoResultsFoundException("group not found");
        } else {
            //add new group.
            userAccount.setGroup(group);
            getAccountDao().saveOrUpdate(userAccount);
        }
    }


    /**
     * Change User Status.
     * @param username
     * @throws EnmeFailOperation
     */
    public void changeUserStatus(final String username) throws EnmeFailOperation{
        final UserAccount secondaryUser = getAccountDao().getUserByUsername(username);
        if (secondaryUser != null){
            secondaryUser.setUserStatus(secondaryUser.isUserStatus()== null ? false : ! secondaryUser.isUserStatus());
            getAccountDao().saveOrUpdate(secondaryUser);
        }
        else {
            throw new EnmeFailOperation("Fail Change User Status");
        }
    }

    /**
     * Remove {@link Group} from User.
     * @param userBean {@link UserAccountBean}
     * @param groupBean {@link UnitGroupBean}
     * @throws EnMeExpcetion
     */
    public void removeGroupFromUser(
            final UserAccountBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion {
            //TODO: need be implemented
    }

    /**
     * Load Permission domain and covert to permission bean.
     * @param permission permission
     * @return permission bean
     */
    public UnitPermission loadBeanPermission(final EnMePermission permission) {
        UnitPermission permissionBean = null;
        final Permission permissionDomain = getPermissionDao().loadPermission(permission);
        if(permissionDomain != null){
             permissionBean = ConvertDomainBean.convertPermissionToBean(permissionDomain);
        }
        return permissionBean;
    }

    /**
     * SingUp User.
     * @param singUpBean {@link SignUpBean}.
     * @return {@link UserAccountBean}.
     */
    public UserAccount singupUser(final SignUpBean singUpBean){
        log.debug("singupUser "+singUpBean.toString());
        //create account/
        final Account account = new Account();
        account.setCreatedAccount(Calendar.getInstance().getTime());
        account.setEnabled(Boolean.TRUE);
        getAccountDao().saveOrUpdate(account);
        //create directory account.
        createDirectoryAccount(account);
        //create first user account.
        final UserAccount userAccount = new UserAccount();
        userAccount.setUsername(singUpBean.getUsername());
        //generate password.
        final String password = EnMePasswordUtils.createRandomPassword(EnMePasswordUtils.DEFAULT_LENGTH_PASSWORD);
        userAccount.setPassword(encodingPassword(password));
        singUpBean.setPassword(password);
        //invite code
        final String inviteCode =  UUID.randomUUID().toString();
        userAccount.setEnjoyDate(Calendar.getInstance().getTime()); //current date
        userAccount.setAccount(account);
        userAccount.setUserStatus(Boolean.TRUE);
        userAccount.setUserEmail(singUpBean.getEmail());
        userAccount.setCompleteName("");
        userAccount.setInviteCode(inviteCode); //thinking, maybe create invite code table.
        getAccountDao().saveOrUpdate(userAccount);
        //create global account directory
        if (log.isDebugEnabled()) {
            log.debug("singupUser created user account");
        }
        //default permissions.
        final Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_USER));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_ADMIN));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_OWNER));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_PUBLISHER));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_EDITOR));
        this.assingPermission(userAccount, permissions);
        //send new password
        getMailService().sendPasswordConfirmationEmail(singUpBean);
        //send confirmation account
        getMailService().sendConfirmYourAccountEmail(singUpBean, inviteCode); //TODO: ENCUESTAME-202
        if (log.isDebugEnabled()) {
            log.debug("new user "+userAccount.getUsername());
            log.debug("Get Authoritie Name:{ "+SecurityContextHolder.getContext().getAuthentication().getName());
        }
        //login user.
        setSpringSecurityAuthentication(singUpBean.getUsername(), password, permissions);
        return userAccount;
    }

    /**
     * User Account Is Activated.
     * @param signUpBean
     * @return
     */
    public Boolean isActivated(final SignUpBean signUpBean){
        //TODO: true?
        return true;
    }

    /**
     * Ecrypt Password with Jasypt.
     * @param password password
     * @return
     */
    private String encodingPassword(final String password){
        //TODO: remove on stable versions, only for debug
        if (log.isDebugEnabled()) {
            log.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            log.debug("^^ "+password);
            log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return  passwordEncryptor.encryptPassword(password);
    }

    /**
     * Set Spring Authentication
     * @param username
     * @param password
     * @deprecated use {@link SecurityUtils}.
     */
    @Deprecated
    private void setSpringSecurityAuthentication(
            final String username,
            final String password,
            final Set<Permission> permissions){
         SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
         Collection<GrantedAuthority> authorities = ConvertDomainsToSecurityContext.convertEnMePermission(permissions);
         SecurityContextHolder.getContext().setAuthentication(
                 new UsernamePasswordAuthenticationToken(username, String.valueOf(password), authorities));
    }

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    public Permission loadPermission(final String permission) {
        return getPermissionDao().loadPermission(EnMePermission.getPermissionString(permission));
    }

    /**
     * Load list of {@link Permission}.
     * @return list of permissions.
     */
    @SuppressWarnings("unchecked")
    public List<UnitPermission> loadPermissions(){
        final Set permissionCollection = new HashSet(getPermissionDao().findAllPermissions());
        final List arrayPermission = new ArrayList<UnitPermission>(ConvertDomainBean.convertSetToUnitPermission(permissionCollection));
        return arrayPermission;
    }

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception excepcion
     */
    public void inviteUser(String email, String code){
        getMailService().sendInvitation(email, code);
    }

    /**
     * Generate hash code invitation.
     * @return generated code
     */
    public String generateHashCodeInvitation() {
        return generatePassword();
    }

    /**
     * Fetch stats by user account.
     * @param user
     * @return
     */
    public void getStatsByUsers(final UserAccountBean user){
            final Long id = user.getId();
            final List<Long> tweetPoll = getAccountDao().getTotalTweetPollByUser(id);
            final List<Long> poll = getAccountDao().getTotalPollByUser(id);
            final List<Long> surveys = getSurveyDaoImp().getTotalSurveyByOwner(id);
            if(tweetPoll.size() > 0){
                user.setTweetPoll(tweetPoll.get(0));
            }
            if(poll.size() > 0){
                user.setPoll(poll.get(0));
            }
            if(surveys.size() > 0){
                user.setSurvey(surveys.get(0));
            }
    }

    /**
     * Send password to user.
     * @param email email
     * @param password password
     * @throws MailSendException
     */
    public void sendUserPassword(final String email,
            final String password)
            throws MailSendException {
        getMailService().send(email, getMessageProperties("NewPassWordMail"),
                password);
    }

    /**
     * Generate a password.
     * @return generate password string
     */
    private String generatePassword() {
        return PasswordGenerator.getPassword(
                PasswordGenerator.lowercase + PasswordGenerator.capitals, 10);
    }

    /**
     * Getter.
     * @return default user permission.
     */
    public String getDefaultUserPermission() {
        return  DEFAULT.name();
    }

    /**
     * Get Email List by Username.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLists> getListbyUsername(final String username) throws EnMeNoResultsFoundException{
            return ConvertDomainBean.convertEmailListToBean(getEmailListsDao().findListbyUser(getPrimaryUser(username)));
    }

    /**
     * Get Followers User.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Integer getFollowers(final String username) throws EnMeNoResultsFoundException{
        UserAccount userAcc = getUserAccount(username);
        final Integer followers = userAcc.getFollowers().size();
        return followers;
    }


    /**
     * Follow Operations.
     *  FollowOperations.FOLLOW - to follow user.
     *  FollowOperations.UNFOLLOW - to unfollow user.
     * @param userAcc
     * @param myUsername
     * @param followerUser
     * @param operation
     * @throws EnMeNoResultsFoundException
     */
    public void followOperations(final UserAccount userAcc,
            final String myUsername, final String followerUser,
            final FollowOperations operation) throws EnMeNoResultsFoundException {
        final UserAccount myAccount = getUserAccount(myUsername);
        final UserAccount myFollower = getUserAccount(followerUser);
        if (FollowOperations.FOLLOW.equals(operation)) {
            myAccount.getFollowers().add(myFollower);
        } else if (FollowOperations.UNFOLLOW.equals(operation)) {
            for (UserAccount dataAccount : myAccount.getFollowers()) {
                if (myFollower.getUsername().equals(dataAccount.getUsername())) {
                    userAcc.getFollowers().remove(dataAccount);
                    getAccountDao().delete(dataAccount);
                }
            }
        }
        getAccountDao().saveOrUpdate(myAccount);
    }

    /**
     * Add Followers.
     * @param myUser
     * @param followerUser
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccount addFollower(final String myUsername, final String followerUser) throws EnMeNoResultsFoundException{
        final UserAccount myAccount = getUserAccount(myUsername);
        final UserAccount myFollower = getUserAccount(followerUser);
        myAccount.getFollowers().add(myFollower);
        getAccountDao().saveOrUpdate(myAccount);
        return myAccount;
    }

    /**
     * Update property for user account.
     * @param property
     * @param value
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    public void upadteAccountProfile(
            final Profile property,
            final String value,
            final String username) throws EnMeNoResultsFoundException{
        final UserAccount account = getUserAccount(username);
        if(Profile.USERNAME.equals(property)){
            account.setUsername(value.trim());
            //TODO: we need update authorities
        } else if(Profile.EMAIL.equals(property)){
            account.setUserEmail(value.trim());
        }
        getAccountDao().saveOrUpdate(account);
    }

    /**
     * Update Account Profile.
     * @param bio
     * @param email
     * @param username
     * @param language
     * @param completeName
     * @throws EnMeNoResultsFoundException
     */
    public void upadteAccountProfile(
            final String bio,
            final String language,
            final String completeName,
            final String loggedUsername) throws EnMeNoResultsFoundException{
        final UserAccount account = getUserAccount(loggedUsername);
        log.debug("update Account user to update "+account.getUsername());
        log.debug("update Account Profile bio "+bio);
        log.debug("update Account Profile language "+language);
        account.setCompleteName(completeName);
        //TODO: bug 112, add another missing properties.
        getAccountDao().saveOrUpdate(account);
    }

//    /**
//     * Update OAuth Secret Twitter Credentials.
//     * @param accountBean {@link SocialAccountBean}
//     * @param username username logged
//     * @throws EnMeExpcetion exception
//     */
//   @Deprecated
//    public void updateSecretTwitterCredentials(final SocialAccountBean accountBean,
//            final String username) throws EnMeExpcetion{
//         //TODO: we should search twitter account filter by username
//         final SocialAccount twitterAccount = this.getSocialAccount(accountBean.getAccountId()); //TODO: filter by Username Too
//         //twitterAccount.setConsumerKey(accountBean.getKey());
//         //twitterAccount.setConsumerSecret(accountBean.getSecret());
//         twitterAccount.setType(ConvertDomainBean.convertStringToEnum(accountBean.getType()));
//         if(accountBean.getPin() != null && !accountBean.getPin().isEmpty()){
//             log.debug("PIN Exists {"+accountBean.getPin());
//             //twitterAccount.setTwitterPin(Integer.valueOf(accountBean.getPin()));
//            //If exist pin, we can verify credentials
//            log.debug("Verify OAuth Credentials");
//                if(verifyCredentials(
//                        //Token and Secret token should be always from database
//                        twitterAccount.getAccessToken(),
//                        twitterAccount.getSecretToken(),
//                        //consumer key's
//                        accountBean.getKey(),
//                        accountBean.getSecret(),
//                        //pin, update by the user.
//                        accountBean.getPin())){
//                    twitterAccount.setVerfied(Boolean.TRUE);
//                } else {
//                    twitterAccount.setVerfied(Boolean.FALSE);
//                }
//         } else {
//             log.info("Account not verified, pin not found");
//             //twitterAccount.setTwitterPin(null);
//             twitterAccount.setVerfied(Boolean.FALSE);
//         }
//        log.debug("Update Secret Twitter Credentials");
//        getAccountDao().saveOrUpdate(twitterAccount);
//        log.info("update Twitter Account");
//    }

    /**
     * Change state social account.
     * @param accountId
     * @param username
     * @param action
     * @throws EnMeNoResultsFoundException
     * @throws IllegalSocialActionException
     */
    public void changeStateSocialAccount(
            final Long accountId,
            final String username,
            final String action) throws EnMeNoResultsFoundException, IllegalSocialActionException{
        final UserAccount userAccount = getUserAccount(username);
        final SocialAccount social = getAccountDao().getSocialAccount(accountId, userAccount.getAccount());
        if(social == null){
            throw new EnMeNoResultsFoundException("social accout not found");
        }
        if("default".equals(action)){
           log.info("update social twitter account");
           social.setDefaultSelected(!social.getDefaultSelected());
           getAccountDao().saveOrUpdate(social);
        } else if("remove".equals(action)){
            getAccountDao().delete(social);
        } else if("valid".equals(action)){
            social.setVerfied(!social.getVerfied());
            getAccountDao().saveOrUpdate(social);
        } else {
            throw new IllegalSocialActionException();
        }
    }

    /**
     * Verify OAuth Credentials
     * @param token token stored
     * @param secretToken secret Token
     * @param pin pin
     * @return
     * @throws TwitterException
     */
    public Boolean verifyCredentials(final String token,
                                     final String tokenSecret,
                                     final String consumerKey,
                                     final String consumerSecret,
                final String pin){
        Boolean verified = false;
        log.debug("verifyCredentials OAuth");
        log.debug("Token {"+token);
        log.debug("secretToken {"+tokenSecret);
        log.debug("pin {"+pin);
        log.debug("consumerKey {"+consumerKey);
        log.debug("consumerSecret {"+consumerSecret);
        Twitter twitter = null;
        try {
             twitter = new TwitterFactory().getInstance();
            if(token == null || token.isEmpty()){
                verified = false;
            } else {
                log.debug("Exist Previous Token.");
                final AccessToken accessToken = new AccessToken(token, tokenSecret);
                log.debug("Created Token "+accessToken);
                twitter = new TwitterFactory().getOAuthAuthorizedInstance(consumerKey, consumerSecret, accessToken);
                log.debug("Verifying Credentials");
                final User user = twitter.verifyCredentials();
                log.debug("Verifying Credentials User "+user);
                if (user != null) {
                    log.debug("Verify OAuth User " + user.getId());
                    verified = true;
                }
            }
        } catch (TwitterException te) {
            log.error("Twitter Error "+te.getMessage());
            if (SocialUtils.TWITTER_AUTH_ERROR == te.getStatusCode()) {
                log.error("Twitter Error "+te.getStatusCode());
                verified = false;
            } else {
                log.error(te);
            }
            log.error("Verify OAuth Error " + te.getLocalizedMessage());
        }
        log.debug("verified "+verified);
        return verified;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#addNewSocialAccount(java.lang.String, java.lang.String, org.encuestame.core.social.SocialUserProfile, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public SocialAccount addNewSocialAccount(
            final String token,
            final String tokenSecret,
            final String expiresToken,
            final SocialUserProfile socialUserProfile,
            final SocialProvider socialProvider,
            final UserAccount userAccount){
        return getAccountDao().createSocialAccount(socialUserProfile.getId(), token,
                tokenSecret, expiresToken, socialUserProfile.getUsername(), socialUserProfile,
                socialProvider, userAccount);
    }

    /**
     *
     * @param socialProvider
     * @param socialAccountId
     * @return
     */
    public SocialAccount getCurrentSocialAccount(final SocialProvider socialProvider, final String socialProfileId){
        return getAccountDao().getSocialAccount(socialProvider, socialProfileId);
    }


    /**
     * Get {@link SocialAccount}.
     * @param socialAccountId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public SocialAccountBean getSocialAccountBean(final Long socialAccountId) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertSocialAccountToBean(this.getSocialAccount(socialAccountId));
    }


    /**
     * Get User Logged Scocial Accounts.
     * @param username
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<SocialAccountBean> getUserLoggedSocialAccount(final String username, final SocialProvider provider)
           throws EnMeNoResultsFoundException{
         return ConvertDomainBean.convertListSocialAccountsToBean(getAccountDao()
                                 .getSocialAccountByAccount(getUserAccount(username).getAccount(), provider));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#getUserLoggedVerifiedTwitterAccount(java.lang.String, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public List<SocialAccountBean> getUserLoggedVerifiedSocialAccounts(final SocialProvider provider)
             throws EnMeNoResultsFoundException{
        final List<SocialAccount> socialAccounts = getAccountDao()
                .getSocialVerifiedAccountByUserAccount(getUserAccount(getUserPrincipalUsername()).getAccount(), provider);
        log.debug("social provider verified:{"+socialAccounts.size());
        return ConvertDomainBean.convertListSocialAccountsToBean(socialAccounts);
   }

    /**
     * Get {@link UserAccount} by confirmation code.
     * @param inviteCode
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccountBean getUserAccountbyCode(final String inviteCode) throws EnMeNoResultsFoundException{
        final UserAccount userAcc;
        SignUpBean singUp = new SignUpBean();
        if (inviteCode == null) {
            throw new EnMeNoResultsFoundException("confirmation code is missing");
        } else {
            userAcc = getAccountDao().getUserAccountbyInvitationCode(inviteCode);
            if (userAcc!=null){
                userAcc.setInviteCode(null);
                userAcc.setUserStatus(Boolean.TRUE);
                getAccountDao().saveOrUpdate(userAcc);
            }else{
                throw new EnMeNoResultsFoundException("confirmation code not found");
            }
        }
        singUp = ConvertDomainBean.convertBasicSecondaryUserToSignUpBean(userAcc);
        getMailService().sendNotificationStatusAccount(singUp, "User status");
        return ConvertDomainBean.convertBasicSecondaryUserToUserBean(userAcc);
    }

    /**
     * Send notification status account.
     * @param userAccBean
     */
    public void sendNotificationStatusAccount(final UserAccountBean userAccBean){
        final SignUpBean singUp = new SignUpBean();
        singUp.setCaptcha("CaPtChA");
        singUp.setEmail(userAccBean.getEmail());
        singUp.setFullName(userAccBean.getName());
        singUp.setPassword(userAccBean.getPassword());
        singUp.setUsername(userAccBean.getUsername());
        getAccountDao().saveOrUpdate(singUp);
        getMailService().sendNotificationStatusAccount(singUp, "Change user status");
    }

   /**
    * Get social account by id.
    * @param accountId
    * @return
 * @throws EnMeNoResultsFoundException
    */
   protected SocialAccount getSocialAccount(final Long accountId) throws EnMeNoResultsFoundException{
       final SocialAccount account =  getAccountDao().getSocialAccountById(accountId);
        if(account == null){
            throw new EnMeNoResultsFoundException("social account not valid {"+accountId);
        }
        return  account;
   }

   /**
    *
    * @param userProfile
    * @return
    */
   private SignUpBean convertSocialConnectedAccountToBean(final SocialUserProfile userProfile){
       log.info("Social Account Profile "+userProfile.toString());
       final SignUpBean singUpBean = new SignUpBean();
       singUpBean.setEmail(userProfile.getEmail());
       singUpBean.setUsername(userProfile.getUsername());
       return singUpBean;
   }

   /**
    *
    * @param accessGrant
    * @param socialAccountId
    * @param userAccount
    * @param providerProfileUrl
    * @param currentSocialAccount
    * @return
    */
   public SocialAccount updateSocialAccountConnection(
           final AccessGrant accessGrant, //OAuth2
           final String socialAccountId,
           final SocialAccount currentSocialAccount){
       return getAccountDao().updateSocialAccountConnection(accessGrant, socialAccountId, currentSocialAccount);
   }

    /* Social Account SignIn Connect. * */

    /**
     * Start the process to sign in with social account.
     * @param SocialSignInOperations sign in social support.
     */
    public String connectSignInAccount(final SocialSignInOperations social) throws EnMeExpcetion {
        // first, we check if this social account already exist as previous connection.
        log.info("Sign In with Social Account");
        //if user account is previously connected
        SocialAccount socialAccount = social.isConnected(social.getSocialUserProfile().getId());
        if (socialAccount != null) {
            log.info("Connecting: Exist previously connection");
            //if exist, we update credentials on account connect store.
            social.reConnect(social.getSocialUserProfile().getId(), social.getAccessGrant(), socialAccount);
            //getAccountDao().saveOrUpdate(connection.getSocialAccount());
            getAccountDao().saveOrUpdate(socialAccount);
            SecurityUtils.socialAuthentication(socialAccount); //TODO: only with OWNER UserAccount.
            return PathUtil.DASHBOARD_REDIRECT;
        } else {
            //if user has been never connected, we check if the user exist with the social account email.
            log.info("Connecting: Creating new connection");
            //get email from social profile.
            final String email = this.convertSocialConnectedAccountToBean(social.getSocialUserProfile()).getEmail();
            log.info("sign in social account email -->"+email);
            String redirectPath =  "signin/provider/register";
            Assert.notNull(email);
            //user account by email
            final UserAccount accountEmail = getAccountDao().getUserByEmail(email);
            //if the user account is new, we create new account.
            if (accountEmail == null) {
                log.debug("This email ["+email+"] never has been used.");
                //create fist connection and social account.
                final SocialAccount accountConnection = this.signUpSocial(
                        social.getSocialUserProfile(), social.getAccessGrant(),
                        social.getProvider(), null);
                SecurityUtils.socialAuthentication(accountConnection);
            } else {
                //if user exist, we create new account connection
                final SocialAccount accountConnection = addNewSocialAccount(
                        social.getAccessGrant().getAccessToken(),
                        social.getAccessGrant().getRefreshToken(),
                        social.getAccessGrant().getExpires(),
                        social.getSocialUserProfile(),
                        social.getProvider(),
                        accountEmail);
                SecurityUtils.socialAuthentication(accountConnection);
                redirectPath = DASHBOARD_REDIRECT;
            }
            return redirectPath;
        }
    }

    /**
     * Default sign up social account.
     * @param profile
     * @param accessGrant
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private SocialAccount signUpSocial(
            final SocialUserProfile profile,
            final AccessGrant accessGrant,
            final SocialProvider provider,
            final UserAccount account) throws EnMeNoResultsFoundException{
        UserAccount accountEmail;
        if (account == null) {
            //create new account.
            accountEmail = this.singupUser(this.convertSocialConnectedAccountToBean(profile));
        } else {
            //use the current account.
            accountEmail = account;
        }
        final SocialAccount accountConnection = addNewSocialAccount(
                accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpires(),
                profile,
                provider,
                accountEmail);
        return accountConnection;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#disconnectSignInAccount(org.encuestame.business.service.social.signin.SocialSignInOperations)
     */
    public void disconnectSignInAccount(final SocialSignInOperations social) {

    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#findAccountConnectionBySocialProfileId(org.encuestame.persistence.domain.social.SocialProvider, java.lang.String)
     */
    public final SocialAccount findAccountConnectionBySocialProfileId(
            final SocialProvider provider,
            final String socialProfileId) {
        return getAccountDao().findAccountConnectionBySocialProfileId(provider, socialProfileId);
    }

   /**
    *
    * @param limit
    * @return
    */
   public List<Notification> loadNotificationByUserAndLimit(
           final Integer limit,
           final Integer start,
           final Boolean onlyUnread) {
        final List<Notification> notifications = getNotificationDao()
                .loadNotificationByUserAndLimit(
                        getUserAccountLogged().getAccount(), limit, start, onlyUnread);
        return notifications;
   }

    /**
     * Follow Operations
     * @author Picado, Juan juanATencuestame.org
     * @since Jan 23, 2011 9:53:53 AM
     */
    public enum FollowOperations{
        FOLLOW,
        UNFOLLOW
    }

    /**
     * User Account profile.
     * @author Picado, Juan juanATencuestame.org
     * @since Feb 5, 2011 11:18:56 AM
     */
    public enum Profile{
        EMAIL,
        USERNAME,
        LANGUAGE,
        PRIVATE,
        REAL_NAME;

        Profile(){
        }

        /**
         * Find Profile.
         * @param value
         * @return
         */
        public static Profile findProfile(final String value) {
            SecurityService.Profile result = null;
            if (null != value) {
               if ("EMAIL".equalsIgnoreCase(value)) { result = EMAIL; }
               if ("USERNAME".equalsIgnoreCase(value)) { result = USERNAME; }
               if ("LANGUAGE".equalsIgnoreCase(value)) { result = LANGUAGE; }
               if ("PRIVATE".equalsIgnoreCase(value)) { result = PRIVATE; }
               if ("REAL_NAME".equalsIgnoreCase(value)) { result = REAL_NAME; }
            }
            return result;
        }

        /**
         * To String.
         */
        public String toString() {
            String type = "";
            if (this == EMAIL) { type = "email"; }
            else if (this == USERNAME) { type = "username"; }
            else if (this == REAL_NAME) { type = "completeName"; }
            else if (this == LANGUAGE) { type = "language"; }
            else if (this == PRIVATE) { type = "private"; }
            return type;
        }
    }
}
