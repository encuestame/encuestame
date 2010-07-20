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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUserTwitterAccounts;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.persistence.pojo.SecUserTwitterAccounts.TypeAuth;
import org.encuestame.core.security.util.EnMePasswordUtils;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.service.util.ConvertDomainsToSecurityContext;
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
    private static final String DEFAULT = "ENCUESTAME_USER";

    /** Default User Permission **/
    private static final String ADMIN = "ENCUESTAME_ADMIN";

    /** Default User Permission **/
    private static final String EDITOR = "ENCUESTAME_EDITOR";

    /** Default User Permission **/
    private static final String OWNER = "ENCUESTAME_OWNER";

    /** Default User Permission **/
    private static final String PUBLISHER = "ENCUESTAME_PUBLISHER";

    /** Anonnymous User. **/
    private static final String ANONYMOUS = "ENCUESTAME_ANONYMOUS";

    /** Suspended Notification. **/
    private Boolean suspendedNotification;


    /**
     * Find {@link SecUserSecondary} by UserName
     * @param username user name
     * @return {@link SecUserSecondary}
     */
    public SecUserSecondary findUserByUserName(final String username) {
        return getSecUserDao().getUserByUsername(username);
    }

    /**
     * Find {@link SecUserSecondary} by UserName
     * @param username user name
     * @return {@link SecUserSecondary}
     */
    public UnitUserBean findUserByEmail(final String email) {
        return ConvertDomainBean.convertSecondaryUserToUserBean(getSecUserDao().getUserByEmail(email));
    }


    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception
     * @throws EnMeExpcetion excepcion
     */
    public List<UnitUserBean> loadListUsers(final String currentUsername) {
        log.info("currentUsername "+currentUsername);
        final List<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
        final SecUserSecondary secUserSecondary = getUser(currentUsername);
        log.info("secUserSecondary "+secUserSecondary);
            final Collection<SecUserSecondary> listUsers = getSecUserDao().retrieveListOwnerUsers(secUserSecondary.getSecUser());
            log.info("list users "+listUsers.size());
                for (SecUserSecondary secUserSecondary2 : listUsers) {
                    loadListUsers.add(ConvertDomainBean.convertSecondaryUserToUserBean(secUserSecondary2));
                }
        return loadListUsers;
    }

    /**
     * Load Groups by Client
     * @return
     */
    public List<UnitGroupBean> loadGroups(final String currentUsername){
        final SecUserSecondary secUserSecondary = getUser(currentUsername);
        final List<UnitGroupBean> groupBeans = new ArrayList<UnitGroupBean>();
        final List<SecGroups> groups = getGroupDao().loadGroupsByUser(secUserSecondary.getSecUser());
        for (SecGroups secGroups : groups) {
            groupBeans.add(ConvertDomainBean.convertGroupDomainToBean(secGroups));
        }
        return groupBeans;
    }

    /**
     * Load {@link SecGroups} by Username.
     * @param currentUsername username
     * @return
     */
    public List<SelectItem> loadSelectItemGroups(final String currentUsername){
        final SecUserSecondary secUserSecondary = getUser(currentUsername);
        final List<SecGroups> groups = getGroupDao().loadGroupsByUser(secUserSecondary.getSecUser());
        final Set<SecGroups> groupsCollection = new HashSet<SecGroups>(groups);
        return ConvertListDomainSelectBean.convertListGroupDomainToSelect(groupsCollection);
    }


    /**
     * Update Twitter Account.
     * @param account account
     * @param password password
     * @param secUser {@link SecUsers}
     */
    public void updateTwitterAccount(
            final UnitTwitterAccountBean accountBean,
            final String password,
            final Boolean verify){
        if(accountBean.getAccountId() != null){
            final SecUserTwitterAccounts twitterAccount = getSecUserDao().getTwitterAccount(accountBean.getAccountId());
            if(twitterAccount != null){
                twitterAccount.setTwitterPassword(password);
                twitterAccount.setVerfied(verify);
                log.debug("Updating twitter password account");
                getSecUserDao().saveOrUpdate(twitterAccount);
            }
        }
        log.info("update Twitter Account");
    }

    /**
     * Update OAuth Secret Twitter Credentials.
     * @param accountBean
     * @param username
     */
    public void updateSecretTwitterCredentials(final UnitTwitterAccountBean accountBean,
            final String username){
         //TODO: we should search twitter account filter by username
         final SecUserTwitterAccounts twitterAccount = getSecUserDao().getTwitterAccount(accountBean.getAccountId());
         twitterAccount.setConsumerKey(accountBean.getKey());
         twitterAccount.setConsumerSecret(accountBean.getSecret());
         twitterAccount.setTwitterPin(Integer.valueOf(accountBean.getPin()));
         twitterAccount.setType(ConvertDomainBean.convertStringToEnum(accountBean.getType()));
         log.debug("Update Secret Twitter Credentials");
          getSecUserDao().saveOrUpdate(twitterAccount);
         log.info("update Twitter Account");
    }

    /**
     * Add new Twitter Account.
     * @param account account.
     * @param username
     */
    public void addNewTwitterAccount(final String account, final String username){
        final SecUsers secUsers = getUser(username).getSecUser();
        final SecUserTwitterAccounts userTwitterAccount = new SecUserTwitterAccounts();
        userTwitterAccount.setSecUsers(secUsers);
        userTwitterAccount.setTwitterAccount(account);
        userTwitterAccount.setTwitterPassword("");
        userTwitterAccount.setType(TypeAuth.PASSWORD); //By default is PASSWORD.
        getSecUserDao().saveOrUpdate(userTwitterAccount);
    }

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    public UnitTwitterAccountBean getTwitterAccount(final Long twitterAccountId){
        return ConvertDomainBean.convertTwitterAccountToBean(getSecUserDao().getTwitterAccount(twitterAccountId));
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
        final SecUserSecondary userDomain = getSecUserDao().getUserByUsername(username);
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
    @Deprecated
    public Collection<UnitPermission> loadAllListPermission() {
        final Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        final Collection<SecPermission> listSecPermission = getPermissionDao()
                .loadAllPermissions();
        for (Iterator<SecPermission> iterator = listSecPermission.iterator(); iterator
                .hasNext();) {
            final UnitPermission permissionBean = new UnitPermission();
            SecPermission permission = iterator.next();
            permissionBean.setId(permission.getIdPermission());
            permissionBean.setPermission(permission.getPermission());
            permissionBean.setDescription(permission.getPermissionDescription());
            loadListPermission.add(permissionBean);
        }
        return loadListPermission;
    }

    /**
     * Delete Group Domain.
     * @param group group
     */
    public void deleteGroup(final UnitGroupBean group) {
        final SecGroups g = getGroupDao().find(Long.valueOf(group.getId()));
        getGroupDao().delete(g);
    }

    /**
     * Delete user.
     * @param userBean user to delete
     */
    public void deleteUser(final UnitUserBean userBean){
            final SecUserSecondary userDomain = getUser(userBean.getUsername().trim());
            log.info("user found "+userDomain);
            if(userDomain == null) {
               log.warn("user "+userBean.getUsername()+ "not found.");
            }
            else {
                if (getSuspendedNotification()) {
                    log.info("notify delete account");
                    getServiceMail().sendDeleteNotification(userBean.getEmail(),
                            getMessageProperties("MessageDeleteNotification"));
                }
                log.info("deleting user");
                getSecUserDao().delete(userDomain);
                log.info("user deleted");
            }
    }

    /**
     * Renew password.
     * @param userBean {@link UnitUserBean}
     * @param newPassword new password
     * @throws EnMeExpcetion
     */
    public String renewPassword(final UnitUserBean userBean, String newPassword) throws EnMeExpcetion {
        // search user
        final SecUserSecondary userDomain = getUser(userBean.getUsername());
        // validate user and password
        if (userDomain != null && newPassword != null) {
            //set new password
            userDomain.setPassword(EnMePasswordUtils.encryptPassworD(newPassword));
            //TODO: security risk?
            userBean.setPassword(newPassword);
            //if notification is suspended we need retrieve password
            if (getSuspendedNotification()) {
                getServiceMail().sendRenewPasswordEmail(userBean);
            }
            //saving user.
            getSecUserDao().saveOrUpdate(userDomain);
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
        final SecGroups group = getGroupDao().find(Long.valueOf(groupBean.getId()));
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
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    public void updateUser(final UnitUserBean userBean){
        log.info("service update user method");
            final SecUserSecondary updateUser = getSecUserDao().getUserByUsername(userBean.getUsername());
            log.info("update user, user found: "+updateUser.getUid());
            if (updateUser != null) {
                updateUser.setUserEmail(userBean.getEmail());
                updateUser.setCompleteName(userBean.getName());
                updateUser.setUserStatus(userBean.getStatus());
                log.info("updateing user, user "+updateUser.getUid());
                getSecUserDao().saveOrUpdate(updateUser);
            }
    }

    /**
     * Create a new Group.
     * @param groupBean group bean
     */
    public UnitGroupBean createGroup(final UnitGroupBean groupBean, final String username) {
        //log.info("Create Group");
        final SecGroups groupDomain = new SecGroups();
        final SecUsers secUsers = getUser(username).getSecUser();
        groupDomain.setGroupDescriptionInfo(groupBean.getGroupDescription());
        groupDomain.setGroupName(groupBean.getGroupName());
        groupDomain.setIdState(null);
        groupDomain.setSecUsers(secUsers);
        getGroupDao().saveOrUpdate(groupDomain);
        groupBean.setId(groupDomain.getGroupId());
        return ConvertDomainBean.convertGroupDomainToBean(groupDomain);
    }

    /**
     * Create a new Permisssion.
     * @param permissionBean {@link UnitPermission}
     */
    public void createPermission(final UnitPermission permissionBean) {
        final SecPermission permissionDomain = new SecPermission();
        permissionDomain.setPermission(permissionBean.getPermission());
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
        final SecUserSecondary secondaryUser = new SecUserSecondary();
        final SecUsers secUsers = getUser(username).getSecUser();
        //validate email and password
        if (userBean.getEmail() != null && userBean.getUsername() != null) {
            secondaryUser.setUserEmail(userBean.getEmail());
            secondaryUser.setUsername(userBean.getUsername());
            secondaryUser.setSecUser(secUsers);
        }
        else {
            throw new EnMeExpcetion("needed email and username to create user");
        }
        String password = null;
        if (userBean.getPassword()!=null) {
             password = userBean.getPassword();
             secondaryUser.setPassword(EnMePasswordUtils.encryptPassworD(password));
        }
        else{
            password = generatePassword();
            secondaryUser.setPassword(EnMePasswordUtils.encryptPassworD(password));
        }
        //TODO: maybe we need create a table for editor permissions
        //secondaryUser.setPublisher(userBean.getPublisher());
        secondaryUser.setCompleteName(userBean.getName() == null ? "" : userBean.getName());
        secondaryUser.setUserStatus(Boolean.TRUE);
        secondaryUser.setEnjoyDate(new Date());
            // send to user the password to her emails
            if((getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), password);
            }
            // save user
            getSecUserDao().saveOrUpdate(secondaryUser);
            // assing first default group to user
            final SecUserSecondary retrievedUser = getSecUserDao().getSecondaryUserById(secondaryUser.getUid());
            final SecPermission permission = getPermissionByName(SecurityService.DEFAULT);
            if(permission != null){
                final List<SecPermission> all = getPermissionDao().findAllPermissions();
                log.info("all permission "+all.size());
                log.info("default permission "+permission);
                retrievedUser.getSecUserPermissions().add(permission);
            }
            else{
                log.warn("error assing default permissions");
            }
            log.info("saving user");
            getSecUserDao().saveOrUpdate(retrievedUser);
    }

    /**
     * Search {@link SecUserSecondary} by email.
     * @param email email
     * @return
     */
    public List<SecUserSecondary> searchUsersByEmail(final String email){
        return getSecUserDao().searchUsersByEmail(email);
    }

    /**
     * Search List of User By Username
     * @param username username
     * @return
     */
    public List<SecUserSecondary> searchUsersByUsesrname(final String username){
        return getSecUserDao().getUsersByUsername(username);
    }

    /**
     * Get Permission By Name
     * @param permission permission
     * @return {@link SecPermission}
     */
    public SecPermission getPermissionByName(final String permission){
        return getPermissionDao().loadPermission(permission);
    }

    /**
     * Assign Permissions to {@link SecUserSecondary}.
     * @param secUserSecondary {@link SecUserSecondary}.
     * @param secPermissions List of {@link SecPermission}.
     */
    public void assingPermission(final SecUserSecondary secUserSecondary , final Set<SecPermission> secPermissions){
        for (SecPermission secPermission : secPermissions) {
            secUserSecondary.getSecUserPermissions().add(secPermission);
        }
        getSecUserDao().saveOrUpdate(secUserSecondary);
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
        SecUserSecondary userDomain = null;
        SecPermission permissionDomain = null;
        log.info("userBean found "+userBean.getId());
        log.info("permissionBean found "+permissionBean.getId());
        if (userBean.getId() != null) {
            userDomain = getSecUserDao().getSecondaryUserById(userBean.getId());
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
           getSecUserDao().saveOrUpdate(userDomain);
           log.info("saved permission "+userDomain.getSecUserPermissions().size());
        } else {
            throw new EnMeExpcetion("error adding permission");
        }
    }

    /**
     * Assign group to user.
     * @param userBean userBean
     * @param groupBean groupBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void assingGroupFromUser(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion {
        final SecUserSecondary secUserSecondary = getUser(userBean.getUsername());
        //search group by group id and owner user id.
        final SecGroups secGroup = getGroupDao().getGroupById(groupBean.getId(), secUserSecondary.getSecUser());
        if(secGroup == null){
            throw new EnMeExpcetion("group not found");
        }
        //add new group.
        secUserSecondary.getSecGroups().add(secGroup);
        getSecUserDao().saveOrUpdate(secUserSecondary);
    }

    /**
     * Remove {@link SecGroups} from User.
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
    public UnitPermission loadBeanPermission(final String permission) {
        final UnitPermission permissionBean = new UnitPermission();
            final SecPermission permissionDomain = getPermissionDao().loadPermission(permission);
                if (permissionDomain != null) {
                    permissionBean.setId(permissionDomain.getIdPermission());
                    permissionBean.setPermission(permissionDomain.getPermission());
                    permissionBean.setDescription(permissionDomain.getPermissionDescription());
                }
        return permissionBean;
    }

    /**
     * SingUp User
     * @param singUpBean {@link SignUpBean}.
     * @return {@link UnitUserBean}.
     */
    public UnitUserBean singupUser(final SignUpBean singUpBean){
        final SecUsers secUsers = new SecUsers();
        getSecUserDao().saveOrUpdate(secUsers);
        final SecUserSecondary secUserSecondary = new SecUserSecondary();
        secUserSecondary.setUsername(singUpBean.getUsername());
        secUserSecondary.setPassword(encodingPassword(singUpBean.getPassword()));
        secUserSecondary.setEnjoyDate(new Date());
        secUserSecondary.setSecUser(secUsers);
        secUserSecondary.setUserStatus(Boolean.TRUE);
        secUserSecondary.setUserEmail(singUpBean.getEmail());
        secUserSecondary.setCompleteName("");
        secUserSecondary.setInviteCode(""); //TODO: invite code?
        getSecUserDao().saveOrUpdate(secUserSecondary);
        //Add default permissions, if user is signup we should add admin access
        final Set<SecPermission> permissions = new HashSet<SecPermission>();
        permissions.add(getPermissionByName(SecurityService.DEFAULT));
        permissions.add(getPermissionByName(SecurityService.ADMIN));
        permissions.add(getPermissionByName(SecurityService.OWNER));
        permissions.add(getPermissionByName(SecurityService.PUBLISHER));
        permissions.add(getPermissionByName(SecurityService.EDITOR));
        this.assingPermission(secUserSecondary, permissions);
        //Create login.
        setSpringSecurityAuthentication(singUpBean.getUsername(), singUpBean.getPassword(), permissions);
        if(this.suspendedNotification){
            getServiceMail().sendPasswordConfirmationEmail(singUpBean);
        }
        log.info("new user "+secUserSecondary.getUsername());
        log.info("Get Authoritie Name"+SecurityContextHolder.getContext().getAuthentication().getName());
        return ConvertDomainBean.convertSecondaryUserToUserBean(secUserSecondary);
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
            final Set<SecPermission> secPermissions){
         SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
         Collection<GrantedAuthority> authorities = ConvertDomainsToSecurityContext.convertEnMePermission(secPermissions);
         SecurityContextHolder.getContext().setAuthentication(
                 new UsernamePasswordAuthenticationToken(username, String.valueOf(password), authorities));
    }

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    public SecPermission loadPermission(final String permission) {
        return getPermissionDao().loadPermission(permission);
    }

    /**
     * Load list of {@link SecPermission}.
     * @return list of permissions.
     */
    @SuppressWarnings("unchecked")
    public List<UnitPermission> loadPermissions(){
        final Set permissionCollection = new HashSet(getPermissionDao().findAllPermissions());
        final List arrayPermission = new ArrayList<UnitPermission>(ConvertDomainBean.convertSetToUnitPermission(permissionCollection));
        return arrayPermission;
    }

    /**
     * Load {@link SelectItem} permissions.
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> loadSelectItemPermissions(){
        final Set permissionCollection = new HashSet(getPermissionDao().findAllPermissions());
        return ConvertListDomainSelectBean.convertListPermissionsToSelect(permissionCollection);
    }

    /**
     * Get User Logged Twitter Accounts.
     * @return
     */
    public List<UnitTwitterAccountBean> getUserLoggedTwitterAccount(final String username){
         return ConvertDomainBean.convertListTwitterAccountsToBean(getSecUserDao()
                                 .getTwitterAccountByUser(getUser(username).getSecUser()));
    }

    /**
     * Get User Logged Verified Twitter Accounts.
     * @param username username
     * @return
     */
    public List<UnitTwitterAccountBean> getUserLoggedVerifiedTwitterAccount(final String username){
        return ConvertDomainBean.convertListTwitterAccountsToBean(getSecUserDao()
                                .getTwitterVerifiedAccountByUser(getUser(username).getSecUser()));
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
     * Load {@link SecUserSecondary} on {@link SelectItem}.
     * @param userId user id
     * @return List of users
     * @throws EnMeExpcetion exception
     */
    public List<SelectItem> loadSelectItemSecondaryUser(final Long userId){
            return ConvertListDomainSelectBean.convertListSecondaryUsersDomainToSelect(getSecUserDao().getSecondaryUsersByUserId(userId));
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
     */
    public List<UnitLists> getListbyUsername(final String username){
            return ConvertDomainBean.convertEmailListToBean(getEmailListsDao().findListbyUser(getPrimaryUser(username)));
    }
}
