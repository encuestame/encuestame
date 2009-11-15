/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.SecGroupDaoImp;
import org.encuestame.core.persistence.dao.SecPermissionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecGroupUserId;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUserPermissionId;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.hibernate.HibernateException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.mail.MailSendException;

/**
 * Security Bean Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009 11:35:01
 */
public class SecurityService extends Service implements ISecurityService {

    /** SecUserDao. **/
    private SecUserDaoImp secUserDao;
    /** Group Dao. **/
    private SecGroupDaoImp groupDao;
    /** Permission Dao **/
    private SecPermissionDaoImp permissionDao;
    /** Services Mail **/
    private MailServiceImpl serviceMail;
    /** Default User Permission **/
    private String defaultUserPermission = "ENCUESTAME_USER";
    /** Suspende Notification. **/
    private Boolean suspendedNotification;

    /**
     * Getter.
     * @return
     */
    public SecUserDaoImp getUserDao() {
        return secUserDao;
    }
    /**
     * Setter.
     * @param userDao
     */
    public void setUserDao(final SecUserDaoImp userDao) {
        this.secUserDao = userDao;
    }

    /**
     * Getter.
     * @return
     */
    public SecGroupDaoImp getGroupDao() {
        return groupDao;
    }

    /**
     * Setter.
     * @param groupDao
     */
    public void setGroupDao(final SecGroupDaoImp groupDao) {
        this.groupDao = groupDao;
    }

    /**
     * Getter.
     * @return
     */
    public SecPermissionDaoImp getPermissionDao() {
        return permissionDao;
    }

    /**
     * Setter.
     * @param permissionDao
     */
    public void setPermissionDao(final SecPermissionDaoImp permissionDao) {
        this.permissionDao = permissionDao;
    }

    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws EnMeExpcetion
     */
    public Collection<UnitUserBean> loadListUsers() throws EnMeExpcetion{
        final Collection<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
        try {
            final Collection<SecUsers> listUsers = getUserDao().findAll();
                if (listUsers != null && listUsers.size() > 0) {
                    for (Iterator<SecUsers> i = listUsers.iterator(); i.hasNext();) {
                        final UnitUserBean userB = new UnitUserBean();
                        final  SecUsers user = i.next();
                        userB.setId(Integer.valueOf(user.getUid().toString()));
                        userB.setName(user.getName());
                        userB.setEmail(user.getEmail());
                        userB.setUsername(user.getUsername());
                        userB.setPublisher(user.getPublisher());
                        userB.setStatus(user.isStatus());
                        userB.setListGroups(convertSetToUnitGroupBean(Integer.valueOf((user.getUid().toString()))));
                        userB.setListPermission(convertSetToUnitPermission(Integer.valueOf(user.getUid().toString())));
                        loadListUsers.add(userB);
                    }
                }
        }
        catch (Exception e) {
           throw new EnMeExpcetion(e.getMessage());
        }
        return loadListUsers;
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
     * @param username
     * @return
     */
    public UnitUserBean searchUserByUsername(final String username) {
        final SecUsers userD = getUserDao().getUserByUsername(username);
        UnitUserBean user = null;
        if (userD != null) {
            user = convertUserDaoToUserBean(userD);
        } else {
            log.error("user not found");
        }
        return user;
    }

    /**
     * Convert Domain user to Bean User.
     * @param domainUser Domain User
     * @return Bean User
     */
    private UnitUserBean convertUserDaoToUserBean(SecUsers domainUser) {
        final UnitUserBean user = new UnitUserBean();
        try {
            user.setName(domainUser.getName());
            user.setUsername(domainUser.getUsername());
            user.setEmail(domainUser.getEmail());
            user.setId(Integer.valueOf(domainUser.getUid().toString()));
            user.setStatus(domainUser.isStatus());
            user.setDate_new(domainUser.getDateNew());
            user.setInvite_code(domainUser.getInviteCode());
            user.setPublisher(domainUser.getPublisher());
        } catch (Exception e) {
            log.error("Error convirtiendo a User BEan -" + e.getMessage());
        }
        return user;
    }

    /**
     * Convert set to unit group bean
     * @param userId user id
     * @return collection of groups beans.
     * @throws Exception
     */
    private Collection<UnitGroupBean> convertSetToUnitGroupBean(final Integer userId)
            throws Exception {
        final Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
        if (userId != null) {
            final UnitGroupBean group = new UnitGroupBean();
            Collection<SecGroupUser> listSecGru = getGroupDao()
                    .loadGroupsByUser(Long.valueOf(userId));
            for (Iterator<SecGroupUser> i = listSecGru.iterator(); i.hasNext();) {
                final SecGroupUser userg = i.next();
                group.setGroupName(userg.getSecGroups().getName());
                group.setGroupDescription(userg.getSecGroups().getDesInfo());
                group.setId(Integer.valueOf(userg.getSecGroups().getGroupId().toString()));
                group.setStateId(userg.getSecGroups().getIdState().toString());
                loadListGroups.add(group);
            }
        }
        return loadListGroups;
    }

    /**
     * Convert Domain Permission to Bean Permission.
     * @param userId user id
     * @return collection of permission
     * @throws Exception all exceptions.
     */
    private Collection<UnitPermission> convertSetToUnitPermission(final Integer userId)
            throws Exception {
        final Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        if (userId != null) {
            final UnitPermission permissionBean = new UnitPermission();
            final Collection<SecUserPermission> listSecGru = getPermissionDao()
                    .loadPermissionByUserId(userId);
            for (Iterator<SecUserPermission> i = listSecGru.iterator(); i
                    .hasNext();) {
                final SecUserPermission permission = i.next();
                permissionBean.setId(Integer.valueOf(permission.getSecPermission().getIdPermission().toString()));
                permissionBean
                        .setPermission(permission.getSecPermission()
                                .getPermission());
                permissionBean.setDescription(permission.getSecPermission()
                        .getDescription());
                loadListPermission.add(permissionBean);
            }
        }
        return loadListPermission;
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
            permissionBean.setId(Integer.valueOf(permission.getIdPermission().toString()));
            permissionBean.setPermission(permission.getPermission());
            permissionBean.setDescription(permission.getDescription());
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
     * Convert Group Domain to Group Bean
     * @param groupDomain
     * @return
     */
    public UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain){
        UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(Integer.valueOf(groupDomain.getGroupId().toString()));
        groupBean.setGroupDescription(groupDomain.getDesInfo());
        groupBean.setStateId(String.valueOf(groupDomain.getIdState()));
        return groupBean;
    }

    /**
     * Delete user.
     * @param userBean user to delete
     */
    public void deleteUser(final UnitUserBean userBean){
        final SecUsers userDomain = getUser(userBean.getUsername().trim());
        if (getSuspendedNotification()) {
            getServiceMail().sendDeleteNotification(userBean.getEmail(),
                    getMessageProperties("MessageDeleteNotification"));
        }
        getUserDao().delete(userDomain);
    }

    /**
     * Renew password.
     * @param userBean
     * @throws Exception
     */
    public void renewPassword(final UnitUserBean userBean){
        final SecUsers userDomain = getUser(userBean.getUsername().trim());
        if (userDomain.getPassword() != null) {
            final String newPassowrd = generatePassword();
            userDomain.setPassword(encryptPassworD(newPassowrd));
                sendUserPassword(userDomain.getEmail().trim(), newPassowrd);
                getUserDao().saveOrUpdate(userDomain);
        }
    }

    /**
     * Get User.
     * @param username
     * @return user domain
     */
    // TODO: maybe should be move to parent beans.
    private SecUsers getUser(final String username) {
        return getUserDao().getUserByUsername(username.trim());
    }

    /**
     * Update a Group.
     * @param group
     * @throws EnMeExpcetion
     */
    public void updateGroup(UnitGroupBean groupBean){
        final SecGroups group = getGroupDao().find(Long.valueOf(groupBean.getId()));
        if (group != null) {
            group.setName(groupBean.getGroupName());
            group.setDesInfo(groupBean.getGroupDescription());
            group.setIdState(Long.valueOf((groupBean.getStateId())));
            getGroupDao().saveOrUpdate(group);
        }
    }

    /**
     * Update user.
     * @param userBean user bean.
     */
    public void updateUser(final UnitUserBean userBean) {
        final SecUsers updateUser = getUserDao().getUserByUsername(userBean.getUsername());
        if (updateUser != null) {
            updateUser.setEmail(userBean.getEmail());
            updateUser.setName(userBean.getName());
            updateUser.setStatus(userBean.getStatus());
            updateUser.setPublisher(userBean.getPublisher());
            getUserDao().saveOrUpdate(updateUser);
        }
    }

    /**
     * Create a new Group.
     * @param groupBean group bean
     */
    public void createGroup(final UnitGroupBean groupBean) {
        final SecGroups groupDomain = new SecGroups();
        groupDomain.setDesInfo(groupBean.getGroupDescription());
        groupDomain.setName(groupBean.getGroupName());
        groupDomain.setIdState(Long.valueOf((groupBean.getStateId())));
        getGroupDao().saveOrUpdate(groupDomain);
    }

    /**
     *Create a new Permisssion.
     * @param permissionBean
     */
    public void createPermission(final UnitPermission permissionBean){
        final SecPermission permissionDomain = new SecPermission();
        permissionDomain.setPermission(permissionBean.getPermission());
        permissionDomain.setDescription(permissionBean.getDescription());
        getPermissionDao().saveOrUpdate(permissionDomain);
    }

    /**
     * Create a user, generate password for user and send email to confirmate
     * the account.
     * @param userBean user bean
     * @throws EnMeExpcetion personalize exception
     */
    public void createUser(UnitUserBean userBean) throws EnMeExpcetion {
        final SecUsers userDomain = new SecUsers();
        if (userBean.getEmail() != null && userBean.getUsername() != null) {
            userDomain.setEmail(userBean.getEmail());
            userDomain.setUsername(userBean.getUsername());
        } else {
            throw new EnMeExpcetion("we need email and username to create user");
        }
        final String passwordGenerated = generatePassword();
        userDomain.setPassword(encryptPassworD(passwordGenerated));
        userDomain.setPublisher(userBean.getPublisher());
        userDomain.setName(userBean.getName());
        userDomain.setStatus(userBean.getStatus());
        userDomain.setDateNew(new Date());
        try {
            // send to user the password to her emails
            if((!getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), passwordGenerated);
            }
            // save user
            getUserDao().saveOrUpdate(userDomain);
            // assing first permissions and default group
            assignPermission(userBean, loadDefaultPermissionBean());
            // assing firs default group to user
            //TODO: we need assing defaul group to user.
        } catch (MailSendException ex) {
            log.error("user could not be notified :"+ex.getMessage());
            throw new EnMeExpcetion(
                    "user could not be notified");
        } catch (HibernateException ex) {
            log.error("user could not be saved :"+ex.getMessage());
            throw new EnMeExpcetion("user could not be saved");
        }
    }

    /**
     * Load default permission when create user.
     * @return
     * @throws EnMeExpcetion
     */
    private UnitPermission loadDefaultPermissionBean()
            throws EnMeExpcetion {
        log.info("default permission  "+getDefaultUserPermission());
        final SecPermission permissionDomain = getPermissionDao().loadPermission(
                getDefaultUserPermission().trim());
        log.info("default permission load "+permissionDomain);
        if (permissionDomain != null) {
            //convert domain to bean permission
            final UnitPermission permissionBean = new UnitPermission();
            permissionBean.setDescription(permissionDomain.getDescription());
            permissionBean.setPermission(permissionDomain.getPermission());
            permissionBean.setId(Integer.valueOf(permissionDomain.getIdPermission().toString()));
            return permissionBean;
        } else {
            throw new EnMeExpcetion("default permission not found.");
        }
    }

    /**
     * Assign permission to user.
     * @param userBean
     * @param permissionBean
     */
    public void assignPermission(
            final UnitUserBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion
   {
        if (userBean.getId() == null && userBean.getUsername() != null) {
            final SecUsers userDomain = getUser(userBean.getUsername());
            userBean.setId(Integer.valueOf(userDomain.getUid().toString()));
        }
        if (permissionBean.getId() == null && permissionBean.getPermission() != null) {
            final SecPermission permissionDomain = loadPermission(permissionBean.getPermission());
            permissionBean.setId(Integer.valueOf(permissionDomain.getIdPermission().toString()));
        }
        if (userBean.getId() != null && permissionBean.getId() != null) {
            final SecUserPermission userPerId = new SecUserPermission();
            SecUserPermissionId userPermissionId = new SecUserPermissionId();
            userPermissionId.setIdPermission(Long.valueOf(permissionBean.getId().toString()));
            userPermissionId.setUid(Long.valueOf(userBean.getId().toString()));
            userPerId.setId(userPermissionId);
            userPerId.setState(true);
            getUserDao().saveOrUpdate(userPerId);
        } else {
            throw new EnMeExpcetion("id user or permission was null");
        }
    }

    /**
     * Verify if user have permissions.
     * @param user
     * @param permission
     */
    private void verifyUserPermission(
            final UnitUserBean user,
            final UnitPermission permission) {
        //TODO: need finihs this.
    }

    /**
     * Assing group to user.
     * @param userBean
     * @param groupBean
     * @throws HibernateException
     */
    private void assingGroup(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion {
        try{
            SecGroupUserId groupUserId = new SecGroupUserId();
            groupUserId.setGroupId(Long.valueOf(groupBean.getId().toString()));
            groupUserId.setUid(Long.valueOf(userBean.getId().toString()));
            SecGroupUser groupUser = new SecGroupUser();
            groupUser.setId(groupUserId);
            groupUser.setState(true);
            getUserDao().assingGroupToUser(groupUser);
        }
        catch (Exception e) {
           throw new EnMeExpcetion(e.getMessage());
        }
    }

    /**
     * Load Permission domain and covert to permission bean.
     * @param permission permission
     * @return permission bean
     */
    public UnitPermission loadBeanPermission(final String permission)
            throws EnMeExpcetion {
        final UnitPermission permissionBean = new UnitPermission();
        try{
            final SecPermission permissionDomain = getPermissionDao().loadPermission(permission);
                if (permissionDomain != null) {
                    permissionBean.setId(Integer.valueOf(permissionDomain.getIdPermission().toString()));
                    permissionBean.setPermission(permissionDomain.getPermission());
                    permissionBean.setDescription(permissionDomain.getDescription());
                }
            }catch (Exception e) {
                throw new EnMeExpcetion(e.getMessage());
            }
        return permissionBean;
    }

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    public SecPermission loadPermission(final String permission){
        return getPermissionDao().loadPermission(permission);
    }

    /**
     * Invite some users to register in the system.
     * @param emails list of users
     * @throws Exception
     */
    public void inviteUser(String email, String code) throws Exception {
        getServiceMail().sendInvitation(email, code);

    }

    /**
     * Generate hash code invitation.
     * @return
     */
    public String generateHashCodeInvitation() {
        return generatePassword();
    }

    /**
     * Send password to user
     * @param email
     * @param password
     * @return
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
                PasswordGenerator.LOWERCASE + PasswordGenerator.CAPITALS, 10);
    }

    /**
     * Encrypt the password.
     * @param password
     * @return
     */
    private String encryptPassworD(final String password) {
        final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.encryptPassword(password);
    }

    public MailServiceImpl getServiceMail() {
        return serviceMail;
    }
    /**
     * Setter.
     * @param serviceMail
     */
    public void setServiceMail(final MailServiceImpl serviceMail) {
        this.serviceMail = serviceMail;
    }

    /**
     * Getter.
     * @return
     */
    public String getDefaultUserPermission() {
        return defaultUserPermission;
    }
    /**
     * Setter.
     * @param defaultUserPermission
     */
    public void setDefaultUserPermission(final String defaultUserPermission) {
        this.defaultUserPermission = defaultUserPermission;
    }

    /**
     * Getter.
     * @return
     */
    public Boolean getSuspendedNotification() {
        log.info("suspendedNotification->" + suspendedNotification);
        return suspendedNotification;
    }
    /**
     * Setter.
     * @param suspendedNotification
     */
    public void setSuspendedNotification(final Boolean suspendedNotification) {
        this.suspendedNotification = suspendedNotification;
    }

}
