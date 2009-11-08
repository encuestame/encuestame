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

    private String defaultUserPermission;
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
     * @throws Exception
     */
    public Collection<UnitUserBean> loadListUsers() throws Exception {
        final Collection<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
        final Collection<SecUsers> listUsers = getUserDao().findAll();
        if (listUsers != null && listUsers.size() > 0) {
            for (Iterator<SecUsers> i = listUsers.iterator(); i.hasNext();) {
                final UnitUserBean userB = new UnitUserBean();
                final  SecUsers user = i.next();
                userB.setId(user.getUid());
                userB.setName(user.getName());
                userB.setEmail(user.getEmail());
                userB.setUsername(user.getUsername());
                userB.setPublisher(user.getPublisher());
                userB.setStatus(user.isStatus());
                userB.setListGroups(convertSetToUnitGroupBean(user.getUid()));
                userB.setListPermission(convertSetToUnitPermission(user
                        .getUid()));
                loadListUsers.add(userB);
            }
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
        assingGroup(user, group);
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
            user.setId(domainUser.getUid());
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
                    .loadGroupsByUser(userId);
            for (Iterator<SecGroupUser> i = listSecGru.iterator(); i.hasNext();) {
                final SecGroupUser userg = i.next();
                group.setGroupName(userg.getSecGroups().getName());
                group.setGroupDescription(userg.getSecGroups().getDesInfo());
                group.setId(userg.getSecGroups().getGroupId());
                group.setStateId(Integer.toString(userg.getSecGroups()
                        .getIdState()));
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
                permissionBean.setId(permission.getSecPermission().getIdPermission());
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
            permissionBean.setId(permission.getIdPermission());
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
        final SecGroups g = getGroupDao().find(group.getId());
        getGroupDao().delete(g);
    }

    /**
     * Delete user.
     * @param user user to delete
     */
    public void deleteUser(final UnitUserBean user){
        final SecUsers userDomain = getUser(user.getUsername().trim());
        if (getSuspendedNotification()) {
            getServiceMail().sendDeleteNotification(user.getEmail(),
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
     * Update a Group
     *
     * @param group
     * @throws EnMeExpcetion
     */
    public void updateGroup(UnitGroupBean uGroup) throws EnMeExpcetion {
        SecGroups group = getGroupDao().find(uGroup.getId());
        if (group != null) {
            group.setName(uGroup.getGroupName());
            group.setDesInfo(uGroup.getGroupDescription());
            group.setIdState((new Integer(uGroup.getStateId())));
            getGroupDao().saveOrUpdate(group);
        } else {
            throw new EnMeExpcetion("No se recupero el grupo.");
        }

    }

    /**
     * update user
     *
     * @param userD
     */
    public void updateUser(UnitUserBean userD) throws EnMeExpcetion {
        SecUsers updateUser = getUserDao().getUserByUsername(userD.getUsername());
        if (updateUser != null) {
            updateUser.setEmail(userD.getEmail());
            updateUser.setName(userD.getName());
            log.info("GET STATUS->"+userD.getStatus());
            updateUser.setStatus(userD.getStatus());
            updateUser.setPublisher(userD.getPublisher());
            log.info("GET getPublisher->"+userD.getPublisher());
            getUserDao().saveOrUpdate(updateUser);
        } else {
            throw new EnMeExpcetion("No se recupero el usuaario");
        }

    }

    /**
     * Create a new Group
     *
     * @param group
     */
    public void createGroup(UnitGroupBean group) {
        SecGroups newG = new SecGroups();
        newG.setDesInfo(group.getGroupDescription());
        newG.setName(group.getGroupName());
        newG.setIdState(new Integer(group.getStateId()).intValue());
        getGroupDao().saveOrUpdate(newG);
    }

    /**
     * create a user
     *
     * @param user
     * @throws EnMeExpcetion
     * @throws HibernateException
     */
    public void createUser(UnitUserBean user) throws MailSendException,
            HibernateException, EnMeExpcetion {
        SecUsers userBd = new SecUsers();
        if (user.getEmail() != null && user.getUsername() != null) {
            userBd.setEmail(user.getEmail());
            userBd.setUsername(user.getUsername());
        } else {
            throw new EnMeExpcetion("we need email and username to create user");
        }
        String password = generatePassword();
        userBd.setPassword(encryptPassworD(password));
        userBd.setPublisher(user.getPublisher());
        userBd.setName(user.getName());
        userBd.setStatus(user.getStatus());
        userBd.setDateNew(new Date());
        try {
            // send to user first password
            sendUserPassword(user.getEmail(), password);
            // create user
            getUserDao().saveOrUpdate(userBd);
            // assing first permissions and default group
            UnitPermission perM = new UnitPermission();
            perM = loadDefaultPermissionBean();

            assignPermission(user, perM);
            // assing firs default group to user
        } catch (MailSendException e) {
            throw new MailSendException(
                    "no se pudo notificar el nuevo usuario " + e);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (EnMeExpcetion e) {
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * load default permission in the config
     *
     * @return
     * @throws HibernateException
     * @throws EnMeExpcetion
     */
    private UnitPermission loadDefaultPermissionBean()
            throws HibernateException, EnMeExpcetion {

        SecPermission per = getPermissionDao().loadPermission(
                getDefaultUserPermission());
        if (per != null) {
            UnitPermission perU = new UnitPermission();
            perU.setDescription(per.getDescription());
            perU.setPermission(per.getPermission());
            perU.setId(per.getIdPermission());
            return perU;
        } else {
            throw new EnMeExpcetion("permiso no encontrado");
        }
    }

    /**
     * assign permission to user
     *
     * @param user
     * @param permission
     */
    public void assignPermission(UnitUserBean user, UnitPermission permission)
            throws HibernateException, EnMeExpcetion {

        if (user.getId() == null && user.getUsername() != null) {
            SecUsers userRe = getUser(user.getUsername());
            user.setId(userRe.getUid());
        }
        if (permission.getId() == null && permission.getPermission() != null) {
            SecPermission perS = loadPermission(permission.getPermission());
            permission.setId(perS.getIdPermission());
        }
        if (user.getId() != null && permission.getId() != null) {
            SecUserPermission userPerId = new SecUserPermission();
            SecUserPermissionId id = new SecUserPermissionId();
            id.setIdPermission(permission.getId());
            id.setUid(user.getId());
            userPerId.setId(id);
            userPerId.setState(true);
            getUserDao().saveOrUpdate(userPerId);
        } else {
            throw new EnMeExpcetion("id user or permission null");
        }
    }

    /**
     * verify if user have permissions
     *
     * @param user
     * @param permission
     */
    private void verifyUserPermission(UnitUserBean user,
            UnitPermission permission) {

    }

    /**
     * assig group to user
     *
     * @param user
     * @param group
     * @throws HibernateException
     */
    private void assingGroup(UnitUserBean user, UnitGroupBean group)
            throws HibernateException {
        SecGroupUserId id = new SecGroupUserId();
        id.setGroupId(group.getId());
        id.setUid(user.getId());
        SecGroupUser gu = new SecGroupUser();
        gu.setId(id);
        gu.setState(true);
        getUserDao().assingGroupToUser(gu);
    }

    /**
     * load Permission bean
     *
     * @param permission
     * @return
     */
    public UnitPermission loadBeanPermission(String permission)
            throws HibernateException {
        SecPermission per = getPermissionDao().loadPermission(permission);
        UnitPermission uPer = new UnitPermission();
        if (per != null) {
            uPer.setId(per.getIdPermission());
            uPer.setPermission(per.getPermission());
            uPer.setDescription(per.getDescription());
        }
        return uPer;
    }

    /**
     * load dao permission
     *
     * @param permission
     * @return
     * @throws HibernateException
     */
    public SecPermission loadPermission(String permission)
            throws HibernateException {
        SecPermission per = getPermissionDao().loadPermission(permission);
        return per;
    }

    /**
     * invite some users to register in the system
     *
     * @param emails
     *            list of users
     * @throws Exception
     */
    public void inviteUser(String email, String code) throws Exception {
        log.info("Correo Usuarios->" + email);
        getServiceMail().sendInvitation(email, code);

    }

    /**
     * generate hash code invitation
     *
     * @return
     */
    public String generateHashCodeInvitation() {
        return generatePassword();
    }

    /**
     * send user to user
     *
     * @param email
     * @param password
     * @return
     */
    private void sendUserPassword(String email, String password)
            throws MailSendException {
        getServiceMail().send(email, getMessageProperties("NewPassWordMail"),
                password);
    }

    /**
     * generate a password
     *
     * @return
     */
    private String generatePassword() {
        String passGenerate = PasswordGenerator.getPassword(
                PasswordGenerator.LOWERCASE + PasswordGenerator.CAPITALS, 10);
        return passGenerate;
    }

    /**
     * encrypt the password
     *
     * @param password
     * @return
     */
    private String encryptPassworD(String password) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        return encryptedPassword;
    }

    public MailServiceImpl getServiceMail() {
        return serviceMail;
    }

    public void setServiceMail(MailServiceImpl serviceMail) {
        this.serviceMail = serviceMail;
    }

    private String getDefaultUserPermission() {
        return defaultUserPermission;
    }

    public void setDefaultUserPermission(String defaultUserPermission) {
        this.defaultUserPermission = defaultUserPermission;
    }

    private Boolean getSuspendedNotification() {
        log.info("suspendedNotification->" + suspendedNotification);
        return suspendedNotification;
    }

    public void setSuspendedNotification(Boolean suspendedNotification) {
        this.suspendedNotification = suspendedNotification;
    }

}
