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
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.security.util.EnMePasswordUtils;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.service.util.ConvertDomainsToSecurityContext;
import org.encuestame.utils.security.SingUpBean;
import org.encuestame.utils.web.UnitGroupBean;
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
     * Update Twitter Account.
     * @param account account
     * @param password password
     * @param secUser {@link SecUsers}
     */
    public void updateTwitterAccount(final String account, final String password, final SecUsers secUser){
        secUser.setTwitterAccount(account);
        secUser.setTwitterPassword(password);
        getSecUserDao().saveOrUpdate(secUser);
        log.info("update Twitter Account");
    }

    /**
     * Update Secret Twitter Credentials.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @param pin pin
     * @param secUser {@link SecUsers}
     */
    public void updateSecretTwitterCredentials(final String consumerKey, final String consumerSecret,  final Integer pin, final SecUsers secUser){
        secUser.setConsumerKey(consumerKey);
        secUser.setConsumerSecret(consumerSecret);
        secUser.setTwitterPing(pin);
        getSecUserDao().saveOrUpdate(secUser);
        log.info("update Twitter Account");
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
        final SecUserSecondary userD = getSecUserDao().getUserByUsername(username);
        UnitUserBean user = null;
        if (userD != null) {
            user = ConvertDomainBean.convertUserDaoToUserBean(userD);
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
                    getServiceMail().sendDeleteNotification(userBean.getEmail(),
                            getMessageProperties("MessageDeleteNotification"));
                }
                getSecUserDao().delete(userDomain);
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
            //if notification is suspended we need retrieve password
            if (getSuspendedNotification()) {
                sendUserPassword(userDomain.getUserEmail().trim(), newPassword);
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
     * Get User.
     * @param username
     * @return user domain
     */
    // TODO: maybe should be move to parent beans.
    private SecUserSecondary getUser(final String username) {
        return getSecUserDao().getUserByUsername(username.trim());
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
                updateUser.setPublisher(userBean.getPublisher());
                log.info("updateing user, user "+updateUser.getUid());
                getSecUserDao().saveOrUpdate(updateUser);
            }
    }

    /**
     * Create a new Group.
     * @param groupBean group bean
     */
    public UnitGroupBean createGroup(final UnitGroupBean groupBean, final String username) {
        log.info("Create Group");
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
        secondaryUser.setPublisher(userBean.getPublisher());
        secondaryUser.setCompleteName(userBean.getName() == null ? "" : userBean.getName());
        secondaryUser.setUserStatus(Boolean.TRUE);
        secondaryUser.setEnjoyDate(new Date());
            // send to user the password to her emails
            if((getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), password);
            }
            // save user
            getSecUserDao().saveOrUpdate(secondaryUser);
            // assing firs default group to user
            //TODO: we need assing defaul group to user.
            final SecUserSecondary retrievedUser = getSecUserDao().getSecondaryUserById(secondaryUser.getUid());
            final SecPermission permission = getPermissionDao().loadPermission("ENCUESTAME_USER");
            final List<SecPermission> all = getPermissionDao().findAllPermissions();
            log.info("all permission "+all.size());
            log.info("default permission "+permission);
            retrievedUser.getSecUserPermissions().add(permission);
            log.info("saving user");
            getSecUserDao().saveOrUpdate(retrievedUser);
            final SecUserSecondary retrievedUser2 = getSecUserDao().getSecondaryUserById(retrievedUser.getUid());
            log.info("saved user total permissions "+retrievedUser2.getSecUserPermissions().size());
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
     * Assing group to user.
     * @param userBean userBean
     * @param groupBean groupBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void assingGroup(
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
     * @param singUpBean {@link SingUpBean}.
     * @return {@link UnitUserBean}.
     */
    public UnitUserBean singupUser(final SingUpBean singUpBean){
        final SecUsers secUsers = new SecUsers();
        getSecUserDao().saveOrUpdate(secUsers);
        final SecUserSecondary secUserSecondary = new SecUserSecondary();
        secUserSecondary.setUsername(singUpBean.getUsername());
        secUserSecondary.setPassword(encodingPassword(singUpBean.getPassword()));
        secUserSecondary.setEnjoyDate(new Date());
        secUserSecondary.setOwner(Boolean.TRUE);
        secUserSecondary.setSecUser(secUsers);
        secUserSecondary.setUserStatus(Boolean.TRUE);
        secUserSecondary.setUserEmail(singUpBean.getEmail());
        secUserSecondary.setCompleteName("");
        secUserSecondary.setInviteCode(""); //TODO: invite code?
        secUserSecondary.setPublisher(Boolean.TRUE);
        getSecUserDao().saveOrUpdate(secUserSecondary);
        //Add default permissions, if user is signup we should add admin access
        final Set<SecPermission> permissions = new HashSet<SecPermission>();
        permissions.add(getPermissionByName(this.DEFAULT));
        permissions.add(getPermissionByName(this.ADMIN));
        this.assingPermission(secUserSecondary, permissions);
        //Create login.
        setSpringSecurityAuthentication(singUpBean.getUsername(), singUpBean.getPassword(), permissions);
        log.info("new user "+secUserSecondary.getUsername());
        log.info("Get Authoritie Name"+SecurityContextHolder.getContext().getAuthentication().getName());
        return ConvertDomainBean.convertUserDaoToUserBean(secUserSecondary);
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
    private void sendUserPassword(final String email,
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
}
