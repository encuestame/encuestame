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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.encuestame.core.config.AdministratorProfile;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.security.util.EnMePasswordUtils;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.UserAccount.PictureSource;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.persistence.exception.IllegalSocialActionException;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.enums.FollowOperations;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.Profile;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UserAccountBean;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * Security Service Implementation.
 * @author Picado, Juan juanATencuestame.org
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
        //SecUsers userD = getUser(user.getUsername());
        // SecPermission perD = loadPermission(permission.getPermission());
        //assingGroup(user, group);
        //TODO: ????/ emtpy??
    }


    /**
     * Search user by username.
     * @param username username
     * @return {@link UserAccountBean}
     */
    @Cacheable(cacheName = "searchUserByUsername")
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
            if (!permission.equals(EnMePermission.ENCUESTAME_USER)) { //this permissions not should be included.
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
                if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
                    getMailService().sendDeleteNotification(userBean.getEmail().trim(),
                            getMessageProperties("userMessageDeleteNotification"));
                }
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
            if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
                try {
                    getMailService().sendRenewPasswordEmail(userBean);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            //} else {
                //log.warn("Notifications Email are suspendend");
            }
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
        return getGroupDao().getGroupByIdandUser(groupId, getUserAccountId(username));
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
             final Group group = getGroupDao().getGroupByIdandUser(groupId, getUserAccountId(username));
             if(group != null){
             counterUsers = getGroupDao().getCountUserbyGroup(groupId);
             }
        } catch (Exception e) {
            // TODO: handle exception Group no pertenece a usuario
            e.printStackTrace();
            log.error(e);
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
               final Group group = getGroupDao().getGroupByIdandUser(groupId, getUserAccountId(username));
               if(group != null){
                   usersbyGroups = getGroupDao().countUsersbyGroups(getUserAccountId(username));
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
        if (userBean.getPassword() != null) {
             password = userBean.getPassword();
             userAccount.setPassword(EnMePasswordUtils.encryptPassworD(password));
        }
        else{
            password = generatePassword();
            userAccount.setPassword(EnMePasswordUtils.encryptPassworD(password));
        }
        //TODO: maybe we need create a table for editor permissions
        userAccount.setCompleteName(userBean.getName() == null ? "" : userBean.getUsername());
        userAccount.setUserStatus(Boolean.TRUE);
        userAccount.setEnjoyDate(Calendar.getInstance().getTime());
            // send to user the password to her emails
            final SignUpBean singUpBean = new SignUpBean();
            singUpBean.setEmail(userBean.getEmail());
            singUpBean.setFullName(userAccount.getCompleteName());
            singUpBean.setUsername(userBean.getUsername());
            singUpBean.setPassword(password);
            final String inviteCode =  UUID.randomUUID().toString();
            userAccount.setInviteCode(inviteCode);
            try {
                getMailService().sendConfirmYourAccountEmail(singUpBean, inviteCode);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                getMailService().sendPasswordConfirmationEmail(singUpBean);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // save user
            getAccountDao().saveOrUpdate(userAccount);
            // assing first default group to user
            final UserAccount retrievedUser = getAccountDao().getUserAccountById(userAccount.getUid());
            final Permission permission = getPermissionByName(SecurityService.DEFAULT);
            if (permission != null) {
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
                log.debug(e);
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
        for (Permission permission : permissions) {
            if(permission != null){
                userAccount.getSecUserPermissions().add(permission);
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
        final UserAccount userAccount = getUserAccount(userId); //TODO: I need confirm this user perhaps same group of logged user.
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
     *
     * @return
     */
    private Account createDefaultAccount(){
        final Account account = new Account();
        account.setCreatedAccount(Calendar.getInstance().getTime());
        account.setEnabled(Boolean.TRUE);
        getAccountDao().saveOrUpdate(account);
        return account;
    }

    /**
     * Generate a random password if default password is null.
     * @param defaultPassword default password.
     * @return
     */
    private String generateRandomPassword(final String defaultPassword){
         final String password = defaultPassword == null ? EnMePasswordUtils
                 .createRandomPassword(EnMePasswordUtils.DEFAULT_LENGTH_PASSWORD)
                 : defaultPassword;
          return password;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.SecurityOperations#createAdministrationUser(org.encuestame.core.config.AdministratorProfile)
     */
    public UserAccountBean createAdministrationUser(
            final AdministratorProfile administratorProfile) {
        log.debug("----------- create administration user ---------");
        final UserAccount userAccount = new UserAccount();
        try{
            final Account account = this.createDefaultAccount();
            // create directory account.
            // this.createDirectoryAccount(account);
            // create first user account.
            userAccount.setUsername(administratorProfile.getUsername());
            // generate password.
            final String password = this
                    .generateRandomPassword(administratorProfile.getPassword());
            userAccount.setPassword(encodingPassword(password));
            // invite code
            userAccount.setEnjoyDate(Calendar.getInstance().getTime()); // current
                                                                        // date
            userAccount.setAccount(account);
            userAccount.setUserStatus(Boolean.TRUE);
            userAccount.setUserEmail(administratorProfile.getEmail());
            userAccount.setCompleteName(administratorProfile.getUsername());
            getAccountDao().saveOrUpdate(userAccount);
            log.debug("administration user ----> "+userAccount.toString());
            // default permissions.
            final Set<Permission> permissions = new HashSet<Permission>();
            permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_USER));
            permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_ADMIN));
            permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_OWNER));
            permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_PUBLISHER));
            permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_EDITOR));
            this.assingPermission(userAccount, permissions);
            log.debug("administration user ----> Adding Security label");

            //Disabled auto-autenticate, the administrative user should sign in manually
            //SecurityUtils.authenticate(userAccount);

            if (log.isDebugEnabled()) {
                log.debug("new user " + userAccount.getUsername());
                log.debug("Get Authoritie Name:{ "
                        + SecurityContextHolder.getContext()
                                .getAuthentication().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        final UserAccountBean bean = ConvertDomainBean.convertBasicSecondaryUserToUserBean(userAccount);
        log.debug("------ administration return ----> userBean "+bean.toString());
        return bean;
    }

    /**
     * SingUp User.
     * @param singUpBean {@link SignUpBean}.
     * @return {@link UserAccountBean}.
     * @throws EnMeNoResultsFoundException 
     */
    public UserAccount singupUser(final SignUpBean singUpBean, boolean disableEmail) throws EnMeNoResultsFoundException {
        //FIXME: Validate the email inside this service.
        log.debug("singupUser "+singUpBean.toString());
        //create account/
        final Account account = this.createDefaultAccount();
        //create directory account.
        createDirectoryAccount(account);
        //create first user account.
        final UserAccount userAccount = new UserAccount();
        userAccount.setUsername(singUpBean.getUsername());
        //generate password.
        final String password = singUpBean.getPassword() == null ? EnMePasswordUtils
                .createRandomPassword(EnMePasswordUtils.DEFAULT_LENGTH_PASSWORD)
                : singUpBean.getPassword();
        userAccount.setPassword(encodingPassword(password));
        singUpBean.setPassword(password);
        //invite code
        final String inviteCode =  UUID.randomUUID().toString();
        userAccount.setEnjoyDate(Calendar.getInstance().getTime()); //current date
        userAccount.setAccount(account);
        userAccount.setUserStatus(Boolean.TRUE);
        userAccount.setSharedProfile(Boolean.TRUE);
        userAccount.setUserEmail(singUpBean.getEmail());
        userAccount.setCompleteName(singUpBean.getFullName());
        userAccount.setInviteCode(inviteCode); //thinking, maybe create invite code table.
        getAccountDao().saveOrUpdate(userAccount);
        //create global account directory
        if (log.isDebugEnabled()) {
            log.debug("singupUser created user account");
        }
        //default permissions.
        final Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_USER));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_OWNER));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_PUBLISHER));
        permissions.add(getPermissionByName(EnMePermission.ENCUESTAME_EDITOR));
        this.assingPermission(userAccount, permissions);

        if (!disableEmail) { //test proposes.
            //send new password
            if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
                //send confirmation account request
                try {
                    getMailService().sendConfirmYourAccountEmail(singUpBean, inviteCode);
                } catch (Exception e) {
                    // ENCUESTAME-602 ????
                    e.printStackTrace();
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("new user "+userAccount.getUsername());
            log.debug("Get Authoritie Name:{ "+SecurityContextHolder.getContext().getAuthentication().getName());
        }
        	
        // create a welcome notification
        createNotification(NotificationEnum.WELCOME_SIGNUP,
                getMessageProperties("notification.wellcome.account"),
                null, false, userAccount);

        // disable, user should sign in from web.
        // SecurityUtils.authenticate(userAccount);

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
        final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return  passwordEncryptor.encryptPassword(password);
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
        @SuppressWarnings("rawtypes")
        final Set permissionCollection = new HashSet(getPermissionDao().findAllPermissions());
        final List<UnitPermission> arrayPermission = new ArrayList<UnitPermission>(ConvertDomainBean.convertSetToUnitPermission(permissionCollection));
        return arrayPermission;
    }

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception excepcion
     */
    public void inviteUser(String email, String code) {
        if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
            try {
                getMailService().sendInvitation(email, code);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
     * @deprecated should user invitation mail service
     */
    @Deprecated
    public void sendUserPassword(final String email,
            final String password)
            throws MailSendException {
        if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
            try {
                getMailService().send(email, getMessageProperties("NewPassWordMail"), password);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
            return ConvertDomainBean.convertEmailListToBean(getEmailListsDao().findListbyUser(getUserAccountId(username)));
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
    public void updateAccountProfile(
            final Profile property,
            final String value) throws EnMeNoResultsFoundException{
        log.debug("updating accoutn profile :" + property + " whith value "
                + value);
        final UserAccount account = getUserAccount(getUserPrincipalUsername());
        if (Profile.USERNAME.equals(property)) {
            account.setUsername(value.trim());
            //TODO: we need update authorities
        } else if (Profile.EMAIL.equals(property)) {
            account.setUserEmail(value.trim());
       } else if (Profile.PICTURE.equals(property)) {
           PictureSource picture = PictureSource.findPictureSource(value);
           if (picture != null) {
               account.setPictureSource(picture);
           }
       }
        getAccountDao().merge(account);
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
    public void updateAccountProfile(
            final String bio,
            final String language,
            final String completeName,
            final String username,
            final String email) throws EnMeNoResultsFoundException{
        final UserAccount account = getUserAccount(getUserPrincipalUsername());
            if (log.isDebugEnabled()) {
		    	log.debug("update Account user to update " + account.getUsername());
		        log.debug("update Account Profile bio " + bio);
		        log.debug("update Account Profile language " + language);
		        log.debug("update Account Profile username " + username);
            }
            account.setCompleteName(completeName);
            account.setUserEmail(email);
            account.setUsername(username);
            account.setLanguage(language == null ? new Locale(EnMeUtils.DEFAULT_LANG).getLanguage() : new Locale(language).getLanguage());
            getAccountDao().saveOrUpdate(account);
            //clear the security context
            SecurityContextHolder.clearContext();
            // login the user
            SecurityUtils.authenticate(account);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#changeStateSocialAccount(java.lang.Long, java.lang.String)
     */
    public void changeStateSocialAccount(
            final Long accountId,
            final String action) throws EnMeNoResultsFoundException, IllegalSocialActionException{
        final UserAccount userAccount = getUserAccount(getUserPrincipalUsername());
        final SocialAccount social = getAccountDao().getSocialAccount(accountId, userAccount.getAccount());
        if (log.isDebugEnabled()) {
	        log.debug("changeStateSocialAccount account");
	        log.debug("changeStateSocialAccount account accountId "+accountId);
	        log.debug("changeStateSocialAccount account action "+action);
        }
        if (social == null) {
            throw new EnMeNoResultsFoundException("social accout not found");
        }
        if ("default".equals(action)) {
           social.setDefaultSelected(!social.getDefaultSelected());
           getAccountDao().saveOrUpdate(social);
        } else if("remove".equals(action)) {
            getAccountDao().delete(social);
        } else {
            throw new IllegalSocialActionException();
        }
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

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.SecurityOperations#getCurrentSocialAccount(org.encuestame.utils.social.SocialProvider, java.lang.String)
     */
    public SocialAccount getCurrentSocialAccount(final SocialProvider socialProvider, final String socialProfileId){
        return getAccountDao().getSocialAccount(socialProvider, socialProfileId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.SecurityOperations#getCurrentSocialAccount(org.encuestame.utils.social.SocialProvider, java.lang.String, java.lang.String)
     */
    public SocialAccount getCurrentSocialAccount(final SocialProvider socialProvider, final String socialProfileId, final String socialUserName) {
        return getAccountDao().getSocialAccount(socialProvider, socialProfileId, socialUserName);
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
    public List<SocialAccountBean> getValidSocialAccounts(
            final SocialProvider provider,
            final Boolean addStats)
             throws EnMeNoResultsFoundException{
        final List<SocialAccount> socialAccounts = getAccountDao()
                .getSocialVerifiedAccountByUserAccount(getUserAccount(getUserPrincipalUsername()).getAccount(), provider);
        log.debug("social provider verified:{"+socialAccounts.size());
        List<SocialAccountBean> accounts = new ArrayList<SocialAccountBean>();
        if ( addStats) {
            accounts = this.addSocialStats(socialAccounts);
        } else {
            accounts = ConvertDomainBean.convertListSocialAccountsToBean(socialAccounts);
        }
        return accounts;
   }

    /**
     * Add social stats.
     * @param socialAccount {@link SocialAccount}.
     * @return list of social accounts.
     */
    private List<SocialAccountBean> addSocialStats(final List<SocialAccount> socialAccounts) {
        final List<SocialAccountBean> accounts = new ArrayList<SocialAccountBean>();
        for (SocialAccount socialAccount : socialAccounts) {
            log.debug("addSocialStats to "+socialAccount.getId());
            final HashMap<String, Long> stats = getAccountDao().getSocialAccountStats(socialAccount);
            log.debug("addSocialStats stats: "+stats);
            final SocialAccountBean bean = ConvertDomainBean.convertSocialAccountToBean(socialAccount);
            log.debug("addSocialStats stats tweetpoll: "+stats.get("tweetpoll"));
            log.debug("addSocialStats stats poll: "+stats.get("poll"));
            log.debug("addSocialStats stats survey: "+stats.get("survey"));
            bean.setTweetpoll(stats.get("tweetpoll"));
            bean.setPoll(stats.get("poll"));
            bean.setSurvey(stats.get("survey"));
            log.debug("addSocialStats bean: "+bean.toString());
            accounts.add(bean);
        }
        return accounts;
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
            if (userAcc!=null) {
                userAcc.setInviteCode(null);
                userAcc.setUserStatus(Boolean.TRUE);
                getAccountDao().saveOrUpdate(userAcc);
            }else{
                throw new EnMeNoResultsFoundException("confirmation code not found");
            }
        }
        singUp = ConvertDomainBean.convertUserAccountToSignUpBean(userAcc);
        if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
            try {
                getMailService().welcomeNotificationAccount(userAcc);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ConvertDomainBean.convertBasicSecondaryUserToUserBean(userAcc);
    }

    /**
     * Refresh the invite code and send new email to user account.
     * @throws EnMeNoResultsFoundException
     */
    public void refreshInviteCode() throws EnMeNoResultsFoundException {
            final UserAccount userAccount = getUserAccount(getUserPrincipalUsername());
            if (userAccount.getInviteCode() != null) {
                final SignUpBean singUpBean = new SignUpBean();
                singUpBean.setEmail(userAccount.getUserEmail());
                singUpBean.setFullName(userAccount.getCompleteName());
                singUpBean.setUsername(userAccount.getUsername());
                final String inviteCode = UUID.randomUUID().toString();
                userAccount.setInviteCode(inviteCode);
                getAccountDao().saveOrUpdate(userAccount);
                try {
                    getMailService().sendConfirmYourAccountEmail(singUpBean, inviteCode);
                } catch (Exception e) {
                    log.fatal("not able to send new invite code");
                    e.printStackTrace();
                }
            } else {
                log.info("invite code requested by " + userAccount.getUsername() + " it's null, nothing to do");
            }
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
        if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.enabled")) {
            try {
                getMailService().sendNotificationStatusAccount(singUp, "Change user status");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /* Social Account SignIn Connect. * */

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#removeUnconfirmedAccount(java.lang.Boolean)
     */
    public void removeUnconfirmedAccount(final Boolean status) {
        final String expireLimit = EnMePlaceHolderConfigurer.getProperty("account.expire.limit");
        final Calendar currentDate = Calendar.getInstance();
        final Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.DATE, -Integer.parseInt(expireLimit));
        log.debug("Before date ----->" + expireDate.getTime());

        final List<UserAccount> accountWithoutConfirmation = getAccountDao().getUserAccountsbyStatus(status, expireDate.getTime(), currentDate.getTime());
        log.debug("Account without confirmation --->"+ accountWithoutConfirmation.size());
        for (UserAccount userAcc  : accountWithoutConfirmation) {
            final Account ownerAccount = getAccountDao().getUserById(userAcc.getAccount().getUid());
            log.debug("User account id ----> " + userAcc.getUid());
            log.debug("Owner account id ----> " + ownerAccount.getUid());
            if(ownerAccount!=null){
                ownerAccount.setEnabled(Boolean.FALSE);
                getAccountDao().saveOrUpdate(ownerAccount);
           }
        }
   }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.SecurityOperations#getUserAccountsAvailable(java.lang.Boolean)
     */
    public List<UserAccount> getUserAccountsAvailable(final Boolean status)
            throws EnMeNoResultsFoundException {
        final List<UserAccount> userListAvailable = getAccountDao()
                .getUserAccounts(status);
        return userListAvailable;
    }
}
