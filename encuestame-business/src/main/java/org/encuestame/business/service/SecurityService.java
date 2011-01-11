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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.ISecurityService;
import org.encuestame.core.security.util.EnMePasswordUtils;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount.TypeAuth;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitUserBean;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Security Bean Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009 11:35:01
 * @version $Id$
 */
@Service
public class SecurityService extends AbstractBaseService implements ISecurityService {

    private Logger log = Logger.getLogger(this.getClass());

    /** Default User Permission **/
    private static final String DEFAULT = EnMePermission.ENCUESTAME_USER.name();

    /** Default User Permission **/
    private static final String ADMIN = EnMePermission.ENCUESTAME_ADMIN.name();

    /** Default User Permission **/
    private static final String EDITOR = EnMePermission.ENCUESTAME_EDITOR.name();

    /** Default User Permission **/
    private static final String OWNER = EnMePermission.ENCUESTAME_OWNER.name();

    /** Default User Permission **/
    private static final String PUBLISHER = EnMePermission.ENCUESTAME_PUBLISHER.name();

    /** Anonnymous User. **/
    private static final String ANONYMOUS = EnMePermission.ENCUESTAME_ANONYMOUS.name();

    /** Suspended Notification. **/
    private Boolean suspendedNotification;

    /**
     * Retrieve Total Own Users.
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public Long totalOwnUsers(final String username) throws EnMeDomainNotFoundException{
        return getAccountDao().retrieveTotalUsers(getUser(username).getAccount());
    }

    /**
     * Find {@link UserAccount} by UserName
     * @param username user name
     * @return {@link UserAccount}
     */
    public UnitUserBean findUserByEmail(final String email) {
        final UserAccount secondary = getAccountDao().getUserByEmail(email);
        return secondary == null ? null : ConvertDomainBean.convertSecondaryUserToUserBean(secondary);
    }


    /**
     * Load Groups by Client
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitGroupBean> loadGroups(final String currentUsername) throws EnMeDomainNotFoundException{
        final UserAccount userAccount = getUser(currentUsername);
        final List<UnitGroupBean> groupBeans = new ArrayList<UnitGroupBean>();
        final List<Group> groupsList = getGroupDao().loadGroupsByUser(userAccount.getAccount());
        for (Group groups : groupsList) {
            groupBeans.add(ConvertDomainBean.convertGroupDomainToBean(groups));
        }
        return groupBeans;
    }

    /**
     * Update Twitter Account.
     * @param account account
     * @param password password
     * @param secUser {@link Account}
     * TODO: this method is close to be deprecated, twitter don't allow password login.
     */
    public void updateTwitterAccount(
            final UnitTwitterAccountBean accountBean,
            final String password,
            final Boolean verify){
        if(accountBean.getAccountId() != null){
            final SocialAccount twitterAccount = getAccountDao().getTwitterAccount(accountBean.getAccountId());
            if(twitterAccount != null){
                twitterAccount.setTwitterPassword(password);
                twitterAccount.setVerfied(verify);
                log.debug("Updating twitter password account");
                getAccountDao().saveOrUpdate(twitterAccount);
            }
        }
        log.info("update Twitter Account");
    }

    /**
     *
     * @param accountId
     * @return
     */
    private SocialAccount getSocialAccount(final Long accountId){
         return  getAccountDao().getTwitterAccount(accountId); //TODO: filter by Username Too
    }

    /**
     * Update OAuth Secret Twitter Credentials.
     * @param accountBean {@link UnitTwitterAccountBean}
     * @param username username logged
     * @throws EnMeExpcetion exception
     */
    public void updateSecretTwitterCredentials(final UnitTwitterAccountBean accountBean,
            final String username) throws EnMeExpcetion{
         //TODO: we should search twitter account filter by username
         final SocialAccount twitterAccount = this.getSocialAccount(accountBean.getAccountId()); //TODO: filter by Username Too
         twitterAccount.setConsumerKey(accountBean.getKey());
         twitterAccount.setConsumerSecret(accountBean.getSecret());
         twitterAccount.setType(ConvertDomainBean.convertStringToEnum(accountBean.getType()));
         if(accountBean.getPin() != null && !accountBean.getPin().isEmpty()){
             log.debug("PIN Exists {"+accountBean.getPin());
             twitterAccount.setTwitterPin(Integer.valueOf(accountBean.getPin()));
            //If exist pin, we can verify credentials
            log.debug("Verify OAuth Credentials");
                if(verifyCredentials(
                        //Token and Secret token should be always from database
                        twitterAccount.getToken(),
                        twitterAccount.getSecretToken(),
                        //consumer key's
                        accountBean.getKey(),
                        accountBean.getSecret(),
                        //pin, update by the user.
                        accountBean.getPin())){
                    twitterAccount.setVerfied(Boolean.TRUE);
                } else {
                    twitterAccount.setVerfied(Boolean.FALSE);
                }
         } else {
             log.info("Account not verified, pin not found");
             twitterAccount.setTwitterPin(null);
             twitterAccount.setVerfied(Boolean.FALSE);
         }
        log.debug("Update Secret Twitter Credentials");
        getAccountDao().saveOrUpdate(twitterAccount);
        log.info("update Twitter Account");
    }


    /**
     * Update OAuth Token/Secret Social Account.
     * @param accountId
     * @param token
     * @param tokenSecret
     * @param username
     * @throws EnMeExpcetion
     */
    public void updateOAuthTokenSocialAccount(final Long accountId, final String token, final String tokenSecret,
            final String username) throws EnMeExpcetion{
        final SocialAccount twitterAccount = this.getSocialAccount(accountId); //TODO: filter by Username Too
        if(twitterAccount == null){
            throw new EnMeExpcetion("Social Account not found");
        }
        else{
            log.debug("Updating  Token to {"+token);
            log.debug("Updating Secret Token to {"+tokenSecret);
            twitterAccount.setToken(token);
            twitterAccount.setSecretToken(tokenSecret);
            getAccountDao().saveOrUpdate(twitterAccount);
            log.debug("Updated Token");
        }
    }

    /**
     * Add new Twitter Account.
     * @param account account.
     * @param username
     * @throws EnMeDomainNotFoundException
     */
    public void addNewTwitterAccount(final String account, final String username) throws EnMeDomainNotFoundException{
        final Account user = getUser(username).getAccount();
        final SocialAccount userTwitterAccount = new SocialAccount();
        userTwitterAccount.setSecUsers(user);
        userTwitterAccount.setTwitterAccount(account);
        userTwitterAccount.setTwitterPassword("");
        userTwitterAccount.setType(TypeAuth.PASSWORD); //By default is PASSWORD.
        getAccountDao().saveOrUpdate(userTwitterAccount);
    }

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    public UnitTwitterAccountBean getTwitterAccount(final Long twitterAccountId){
        return ConvertDomainBean.convertTwitterAccountToBean(getAccountDao().getTwitterAccount(twitterAccountId));
    }

    /**
     * Assing Group to User.
     * @param user user
     * @param group group
     */
    public void assingGroupToUser(final UnitUserBean user, final UnitGroupBean group){
        // SecUsers userD = getUser(user.getUsername());
        // SecPermission perD = loadPermission(permission.getPermission());
        //assingGroup(user, group);
    }


    /**
     * Search user by username.
     * @param username username
     * @return {@link UnitUserBean}
     */
    public UnitUserBean searchUserByUsername(final String username) {
        final UserAccount userDomain = getAccountDao().getUserByUsername(username);
        UnitUserBean user = null;
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
     * @throws EnMeDomainNotFoundException
     */
    public void deleteUser(final UnitUserBean userBean) throws EnMeDomainNotFoundException{
            final UserAccount userDomain = getUser(userBean.getUsername());
                if (getSuspendedNotification()) {
                    log.info("notify delete account");
                    getServiceMail().sendDeleteNotification(userBean.getEmail(),
                            getMessageProperties("MessageDeleteNotification"));
                }
                log.info("deleting user");
                getAccountDao().delete(userDomain);
                log.info("user deleted");
    }

    /**
     * Renew password.
     * @param userBean {@link UnitUserBean}
     * @param newPassword new password
     * @throws EnMeExpcetion
     */
    public String renewPassword(final UnitUserBean userBean, String newPassword) throws EnMeExpcetion {
        // search user
        final UserAccount userDomain = getUser(userBean.getUsername());
        // validate user and password
        if (userDomain != null && newPassword != null) {
            //set new password
            userDomain.setPassword(EnMePasswordUtils.encryptPassworD(newPassword));
            //TODO: security risk?
            userBean.setPassword(newPassword);
            //if notification is suspended we need retrieve password
            if (getSuspendedNotification()) {
                getServiceMail().sendRenewPasswordEmail(userBean);
            } else {
                log.warn("Notifications Email are suspendend");
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
    public Group getGroupbyIdandUser(final Long groupId, final String username) throws EnMeDomainNotFoundException {
        return getGroupDao().getGroupByIdandUser(groupId, getPrimaryUser(username));
    }

    /**
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    public void updateUser(final UnitUserBean userBean){
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
     * @throws EnMeDomainNotFoundException
     */
    public Long getUserbyGroup(final Long groupId, final String username) throws EnMeDomainNotFoundException{
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
     * @throws EnMeDomainNotFoundException
     */
    public UnitGroupBean createGroup(final UnitGroupBean groupBean, final String username) throws EnMeDomainNotFoundException {
        //log.info("Create Group");
        final Group groupDomain = new Group();
        final Account secUsers = getUser(username).getAccount();
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
     * Create a secondary user, generate password for user and send email to confirmate
     * the account.
     * @param userBean {@link UnitUserBean}
     * @throws EnMeExpcetion personalize exception
     * @return if password is not notified  is returned
     */
    public void createUser(final UnitUserBean userBean, final String username) throws EnMeExpcetion {
        final UserAccount userAccount = new UserAccount();
        final Account account = getUser(username).getAccount();
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
        //secondaryUser.setPublisher(userBean.getPublisher());
        userAccount.setCompleteName(userBean.getName() == null ? "" : userBean.getName());
        userAccount.setUserStatus(Boolean.TRUE);
        userAccount.setEnjoyDate(new Date());
            // send to user the password to her emails
            if((getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), password);
            }
            // save user
            getAccountDao().saveOrUpdate(userAccount);
            // assing first default group to user
            final UserAccount retrievedUser = getAccountDao().getSecondaryUserById(userAccount.getUid());
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
     * Search List of User By Username
     * @param username username
     * @return
     */
    public List<UserAccount> searchUsersByUsername(final String username){
        return getAccountDao().getUsersByUsername(username);
    }

    /**
     * Get Permission By Name
     * @param permission permission
     * @return {@link Permission}
     */
    public Permission getPermissionByName(final String permission){
        final Permission permission2 = getPermissionDao().loadPermission(
              EnMePermission.getPermissionString(permission));
        return permission2;
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
     * @param userBean {@link UnitUserBean}
     * @param permissionBean {@link UnitPermission}
     * @throws EnMeExpcetion exception
     */

    public void assignPermission(
            final UnitUserBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion
   {
        UserAccount userDomain = null;
        Permission permissionDomain = null;
        log.info("userBean found "+userBean.getId());
        log.info("permissionBean found "+permissionBean.getId());
        if (userBean.getId() != null) {
            userDomain = getAccountDao().getSecondaryUserById(userBean.getId());
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
            throw new EnMeDomainNotFoundException("user not found");
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
     * @throws EnMeDomainNotFoundException
     */
    public void assingGroupFromUser(
            final Long groupId,
            final Long userId,
            final String username) throws EnMeDomainNotFoundException {
        final UserAccount userAccount = getUser(userId); //TODO: I need confirm this user perhaps same group of logged user.
        //search group by group id and owner user id.
        final Group group = getGroupDao().getGroupById(groupId, getUser(username).getAccount());
        if(group == null){
            throw new EnMeDomainNotFoundException("group not found");
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
     * @param userBean {@link UnitUserBean}
     * @param groupBean {@link UnitGroupBean}
     * @throws EnMeExpcetion
     */
    public void removeGroupFromUser(
            final UnitUserBean userBean,
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
     * @return {@link UnitUserBean}.
     */
    public UnitUserBean singupUser(final SignUpBean singUpBean){
        final Account account = new Account();
        getAccountDao().saveOrUpdate(account);
        final UserAccount userAccount = new UserAccount();
        userAccount.setUsername(singUpBean.getUsername());
        userAccount.setPassword(encodingPassword(singUpBean.getPassword()));
        userAccount.setEnjoyDate(new Date());
        userAccount.setAccount(account);
        userAccount.setUserStatus(Boolean.TRUE);
        userAccount.setUserEmail(singUpBean.getEmail());
        userAccount.setCompleteName("");
        userAccount.setInviteCode(""); //TODO: invite code?
        getAccountDao().saveOrUpdate(userAccount);
        //Add default permissions, if user is signup we should add admin access
        final Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(getPermissionByName(SecurityService.DEFAULT));
        permissions.add(getPermissionByName(SecurityService.ADMIN));
        permissions.add(getPermissionByName(SecurityService.OWNER));
        permissions.add(getPermissionByName(SecurityService.PUBLISHER));
        permissions.add(getPermissionByName(SecurityService.EDITOR));
        this.assingPermission(userAccount, permissions);
        //Create login.
        setSpringSecurityAuthentication(singUpBean.getUsername(), singUpBean.getPassword(), permissions);
        if(this.suspendedNotification){
            getServiceMail().sendPasswordConfirmationEmail(singUpBean);
        }
        log.info("new user "+userAccount.getUsername());
        log.info("Get Authoritie Name"+SecurityContextHolder.getContext().getAuthentication().getName());
        return ConvertDomainBean.convertSecondaryUserToUserBean(userAccount);
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
     * Set Spring Authentication
     * @param username
     * @param password
     */
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
     * Get User Logged Twitter Accounts.
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitTwitterAccountBean> getUserLoggedTwitterAccount(final String username) throws EnMeDomainNotFoundException{
         return ConvertDomainBean.convertListTwitterAccountsToBean(getAccountDao()
                                 .getTwitterAccountByUser(getUser(username).getAccount()));
    }

    /**
     * Get User Logged Verified Twitter Accounts.
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitTwitterAccountBean> getUserLoggedVerifiedTwitterAccount(final String username) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertListTwitterAccountsToBean(getAccountDao()
                                .getTwitterVerifiedAccountByUser(getUser(username).getAccount()));
   }

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception excepcion
     */
    public void inviteUser(String email, String code){
        getServiceMail().sendInvitation(email, code);
    }

    /**
     * Generate hash code invitation.
     * @return generated code
     */
    public String generateHashCodeInvitation() {
        return generatePassword();
    }

    /**
     * User Bean.
     * @param user
     * @return
     */
    public void getStatsByUsers(final UnitUserBean user){
            final Long id = user.getId();
            final List<Long> tweetPoll = getAccountDao().getTotalTweetPollByUser(id);
            final List<Long> poll = getAccountDao().getTotalPollByUser(id);
            final List<Long> surveys = getAccountDao().getTotalSurveyByUser(id);
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
        getServiceMail().send(email, getMessageProperties("NewPassWordMail"),
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
        return  DEFAULT;
    }

    /**
     * Getter.
     * @return suspendend notification
     */
    public Boolean getSuspendedNotification() {
        return suspendedNotification;
    }
    /**
     * Setter.
     * @param suspendedNotification suspended notification
     */
    public void setSuspendedNotification(final Boolean suspendedNotification) {
        this.suspendedNotification = suspendedNotification;
    }

    /**
     * Get Email List by Username.
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitLists> getListbyUsername(final String username) throws EnMeDomainNotFoundException{
            return ConvertDomainBean.convertEmailListToBean(getEmailListsDao().findListbyUser(getPrimaryUser(username)));
    }
}
