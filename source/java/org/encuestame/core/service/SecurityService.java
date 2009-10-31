package org.encuestame.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.SecGroupDaoImp;
import org.encuestame.core.persistence.dao.SecPermissionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroupPermissionId;
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
import org.springframework.mail.MailSendException;

/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
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
 *
 * Id: SecurityService.java Date: 27/04/2009
 *
 * @author juanpicado package: org.encuestame.core.service
 * @version 1.0
 */
public class SecurityService extends MasterService implements ISecurityService {

    private Log log = LogFactory.getLog(this.getClass());
    private SecUserDaoImp secUserDao;
    private SecGroupDaoImp groupDao;
    private SecPermissionDaoImp permissionDao;
    private MailServiceImpl serviceMail;
    private String defaultUserPermission;
    private Boolean suspendedNotification;

    public SecUserDaoImp getUserDao() {
        return secUserDao;
    }

    public void setUserDao(SecUserDaoImp userDao) {
        this.secUserDao = userDao;
    }

    public SecGroupDaoImp getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(SecGroupDaoImp groupDao) {
        this.groupDao = groupDao;
    }

    public SecPermissionDaoImp getPermissionDao() {
        return permissionDao;
    }

    public void setPermissionDao(SecPermissionDaoImp permissionDao) {
        this.permissionDao = permissionDao;
    }

    /**
     * load list of users
     *
     * @return list of users with groups and permission
     * @throws Exception
     */
    public Collection<UnitUserBean> loadListUsers() throws Exception {
        Collection<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
        Collection<SecUsers> listUsers = getUserDao().findAll();
        if (listUsers != null && listUsers.size() > 0) {
            for (Iterator<SecUsers> i = listUsers.iterator(); i.hasNext();) {
                UnitUserBean userB = new UnitUserBean();
                SecUsers user = i.next();
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

    public void assingGroupToUser(UnitUserBean user, UnitGroupBean group)
            throws HibernateException {
        // SecUsers userD = getUser(user.getUsername());
        // SecPermission perD = loadPermission(permission.getPermission());
        assingGroup(user, group);
    }

    /**
     * search user by username
     *
     * @param username
     * @return
     */
    public UnitUserBean searchUserByUsername(String username) {
        SecUsers userD = getUserDao().getUserByUsername(username);
        log.info("Usuario Encontrado por Nombnre->" + userD);
        if (userD != null) {
            UnitUserBean user = convertUserDaoToUserBean(userD);
            log.info("Conversi�n Usuario to Bean ->" + user);
            return user;
        } else {
            log.error("No se encontro usuario");
            return null;
        }

    }

    private UnitUserBean convertUserDaoToUserBean(SecUsers userD) {
        UnitUserBean user = new UnitUserBean();
        try {
            user.setName(userD.getName());
            user.setUsername(userD.getUsername());
            user.setEmail(userD.getEmail());
            user.setId(userD.getUid());
            user.setStatus(userD.isStatus());
            user.setDate_new(userD.getDateNew());
            user.setInvite_code(userD.getInviteCode());
            user.setPublisher(userD.getPublisher());
        } catch (Exception e) {
            log.error("Error convirtiendo a User BEan -" + e.getMessage());
        }
        return user;
    }

    /**
     * convert set to unit group bean
     *
     * @param set
     * @return
     * @throws Exception
     */
    private Collection<UnitGroupBean> convertSetToUnitGroupBean(Integer id)
            throws Exception {
        Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
        if (id != null) {
            UnitGroupBean group = new UnitGroupBean();
            Collection<SecGroupUser> listSecGru = getGroupDao()
                    .loadGroupsByUser(id);
            for (Iterator<SecGroupUser> i = listSecGru.iterator(); i.hasNext();) {
                SecGroupUser userg = i.next();
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
     * convert dao permission in permission bean
     *
     * @param set
     * @return
     * @throws Exception
     */
    private Collection<UnitPermission> convertSetToUnitPermission(Integer id)
            throws Exception {
        Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        if (id != null) {
            UnitPermission per = new UnitPermission();
            Collection<SecUserPermission> listSecGru = getPermissionDao()
                    .loadPermissionByUser(id);
            for (Iterator<SecUserPermission> i = listSecGru.iterator(); i
                    .hasNext();) {
                SecUserPermission permission = i.next();
                per.setId(permission.getSecPermission().getIdPermission());
                per
                        .setPermission(permission.getSecPermission()
                                .getPermission());
                per.setDescription(permission.getSecPermission()
                        .getDescription());
                loadListPermission.add(per);
            }
        }
        return loadListPermission;
    }

    /**
     * load all list of permisssions
     *
     * @return list of permisssions
     */
    public Collection<UnitPermission> loadAllListPermission() {
        Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        Collection<SecPermission> listSecPermission = getPermissionDao()
                .loadAllPermissions();
        log.debug("Permissions Total->" + listSecPermission.size());
        for (Iterator<SecPermission> i = listSecPermission.iterator(); i
                .hasNext();) {
            UnitPermission per = new UnitPermission();
            SecPermission permission = i.next();
            per.setId(permission.getIdPermission());
            per.setPermission(permission.getPermission());
            per.setDescription(permission.getDescription());
            loadListPermission.add(per);
        }
        return loadListPermission;

    }

    /**
     * Delete Group
     *
     * @param group
     */
    public void deleteGroup(UnitGroupBean group) {
        SecGroups g = getGroupDao().find(group.getId());
        getGroupDao().delete(g);
    }

    /**
     * delete user
     *
     * @param user
     *            to delete
     */
    public void deleteUser(UnitUserBean user) throws MailSendException,
            HibernateException, Exception {
        SecUsers g = getUser(user.getUsername().trim());
        log.info("delete deleteUserDao->" + g.getUsername());
        if (getSuspendedNotification()) {
            log.info("Notificar->" + user.getEmail());
            getServiceMail().sendDeleteNotification(user.getEmail(),
                    getMessageProperties("MessageDeleteNotification"));
        }
        log.info("user to delete ->" + g.getEmail());
        getUserDao().delete(g);
        log.info("user delete");
    }

    /**
     * renew password
     *
     * @param user
     * @throws Exception
     */
    public void renewPassword(UnitUserBean user) throws MailSendException {
        SecUsers g = getUser(user.getUsername().trim());
        if (g.getPassword() != null) {
            String newPassowrd = generatePassword();
            g.setPassword(encryptPassworD(newPassowrd));
            try {
                sendUserPassword(g.getEmail().trim(), newPassowrd);
                getUserDao().saveOrUpdate(g);
            } catch (MailSendException ex) {
                throw new MailSendException(ex.getMessage());
            }
        } else {
            throw new MailSendException("test");
        }
    }

    /**
     * get user by username
     *
     * @param username
     * @return
     */
    private SecUsers getUser(String username) {
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
            getUserDao().assingPermissiontoUser(userPerId);
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
