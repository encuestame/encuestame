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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.SecGroupDaoImp;
import org.encuestame.core.persistence.dao.SecPermissionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.security.util.EnMePasswordUtils;
import org.encuestame.core.security.util.PasswordGenerator;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.mail.MailSendException;

/**
 * Security Bean Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009 11:35:01
 * @version $Id$
 */
public class SecurityService extends Service implements ISecurityService {

    private Log log = LogFactory.getLog(this.getClass());
    /** SecUserDao. **/
    private SecUserDaoImp secUserDao;
    /** Group Dao. **/
    private SecGroupDaoImp groupDao;
    /** Permission Dao **/
    private SecPermissionDaoImp permissionDao;
    /** Services Mail **/
    private MailServiceImpl serviceMail;
    /** Default User Permission **/
    private static final String DEFAULT = "ENCUESTAME_USER";
    /** Suspended Notification. **/
    private Boolean suspendedNotification;
    /**  {@link SurveyService} **/
    private SurveyService surveyService;

    /**
     * Getter.
     * @return {@link SecUserDaoImp}
     */
    public SecUserDaoImp getUserDao() {
        return secUserDao;
    }
    /**
     * Setter.
     * @param userDao {@link SecUserDaoImp}
     */
    public void setUserDao(final SecUserDaoImp userDao) {
        this.secUserDao = userDao;
    }

    /**
     * Getter.
     * @return {@link SecGroupDaoImp}
     */
    public SecGroupDaoImp getGroupDao() {
        return groupDao;
    }

    /**
     * Setter.
     * @param groupDao {@link SecGroupDaoImp}
     */
    public void setGroupDao(final SecGroupDaoImp groupDao) {
        this.groupDao = groupDao;
    }

    /**
     * Getter.
     * @return {@link SecPermissionDaoImp}
     */
    public SecPermissionDaoImp getPermissionDao() {
        return permissionDao;
    }

    /**
     * Setter.
     * @param permissionDao {@link SecPermissionDaoImp}
     */
    public void setPermissionDao(final SecPermissionDaoImp permissionDao) {
        this.permissionDao = permissionDao;
    }

    /**
     * Find {@link SecUserSecondary} by UserName
     * @param username user name
     * @return {@link SecUserSecondary}
     */
    public SecUserSecondary findUserByUserName(final String username) {
        return getUserDao().getUserByUsername(username);
    }

    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception
     * @throws EnMeExpcetion excepcion
     */
    public List<UnitUserBean> loadListUsers() {
        final List<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
            final Collection<SecUserSecondary> listUsers = getUserDao().findAll();
                if (listUsers.size() > 0) {
                    for (Iterator<SecUserSecondary> i = listUsers.iterator(); i.hasNext();) {
                        final UnitUserBean userBean = new UnitUserBean();
                        final  SecUserSecondary userDomain = i.next();
                        userBean.setId(userDomain.getUid());
                        userBean.setName(userDomain.getCompleteName());
                        userBean.setEmail(userDomain.getUserEmail());
                        userBean.setUsername(userDomain.getUsername());
                        userBean.setPublisher(userDomain.getPublisher());
                        userBean.setStatus(userDomain.isUserStatus());
                        userBean.setListGroups(convertSetToUnitGroupBean(userDomain.getSecGroups()));
                        userBean.setListPermission(convertSetToUnitPermission(userDomain.getSecUserPermissions()));
                        loadListUsers.add(userBean);
                    }
                }
        return loadListUsers;
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
        getUserDao().saveOrUpdate(secUser);
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
        getUserDao().saveOrUpdate(secUser);
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
        final SecUserSecondary userD = getUserDao().getUserByUsername(username);
        UnitUserBean user = null;
        if (userD != null) {
            user = ConvertDomainBean.convertUserDaoToUserBean(userD);
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
    @Deprecated
    public UnitUserBean convertUserDaoToUserBean(SecUserSecondary domainUser) {
        final UnitUserBean user = new UnitUserBean();
        try {
            user.setName(domainUser.getCompleteName());
            user.setUsername(domainUser.getUsername());
            user.setEmail(domainUser.getUserEmail());
            user.setId(domainUser.getUid());
            user.setStatus(domainUser.isUserStatus());
            user.setDateNew(domainUser.getEnjoyDate());
            user.setInviteCode(domainUser.getInviteCode());
            user.setPublisher(domainUser.getPublisher());
        } catch (Exception e) {
            log.error("Error convirtiendo a User Bean -" + e.getMessage());
        }
        return user;
    }

    /**
     * Convert set to unit group bean
     * @param userId user id
     * @return collection of groups beans.
     * @throws Exception
     */
    private Collection<UnitGroupBean> convertSetToUnitGroupBean(final Set<SecGroups> groups){
            final Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
            for (SecGroups secGroups : groups) {
                 loadListGroups.add(ConvertDomainBean.convertGroupDomainToBean(secGroups));
            }
        return loadListGroups;
    }

    /**
     * Convert Domain Permission to Bean Permission.
     * @param userId user id
     * @return collection of permission
     * @throws Exception all exceptions.
  */
    private Collection<UnitPermission> convertSetToUnitPermission(final Set<SecPermission> permissions) {
        final Collection<UnitPermission> loadListPermission = new LinkedList<UnitPermission>();
        for (SecPermission secPermission : permissions) {
            loadListPermission.add(ConvertDomainBean.convertPermissionToBean(secPermission));
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
     * Convert Group Domain to Group Bean
     * @param groupDomain {@link SecGroups}
     * @return {@link UnitGroupBean}
     */
    @Deprecated
    public UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain) {
        final UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(Integer.valueOf(groupDomain.getGroupId().toString()));
        groupBean.setGroupDescription(groupDomain.getGroupDescriptionInfo());
        groupBean.setStateId(String.valueOf(groupDomain.getIdState()));
        return groupBean;
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
                getUserDao().delete(userDomain);
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
            getUserDao().saveOrUpdate(userDomain);
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
        return getUserDao().getUserByUsername(username.trim());
    }

    /**
     * Update a Group.
     * @param groupBean {@link UnitGroupBean}
     */
    public void updateGroup(UnitGroupBean groupBean) {
        final SecGroups group = getGroupDao().find(Long.valueOf(groupBean.getId()));
        if (group != null) {
            group.setGroupName(groupBean.getGroupName());
            group.setGroupDescriptionInfo(groupBean.getGroupDescription());
            group.setIdState(Long.valueOf((groupBean.getStateId())));
            getGroupDao().saveOrUpdate(group);
        }
    }

    /**
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    public void updateUser(final UnitUserBean userBean){
        log.info("service update user method");
            final SecUserSecondary updateUser = getUserDao().getUserByUsername(userBean.getUsername());
            log.info("update user, user found: "+updateUser.getUid());
            if (updateUser != null) {
                updateUser.setUserEmail(userBean.getEmail());
                updateUser.setCompleteName(userBean.getName());
                updateUser.setUserStatus(userBean.getStatus());
                updateUser.setPublisher(userBean.getPublisher());
                log.info("updateing user, user "+updateUser.getUid());
                getUserDao().saveOrUpdate(updateUser);
            }
    }

    /**
     * Create a new Group.
     * @param groupBean group bean
     */
    public void createGroup(final UnitGroupBean groupBean) {
        final SecGroups groupDomain = new SecGroups();
        groupDomain.setGroupDescriptionInfo(groupBean.getGroupDescription());
        groupDomain.setGroupName(groupBean.getGroupName());
        groupDomain.setIdState(Long.valueOf((groupBean.getStateId())));
        getGroupDao().saveOrUpdate(groupDomain);
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
    public String createUser(final UnitUserBean userBean) throws EnMeExpcetion {
        final SecUserSecondary secondaryUser = new SecUserSecondary();
        //validate email and password
        if (userBean.getEmail() != null && userBean.getUsername() != null) {
            secondaryUser.setUserEmail(userBean.getEmail());
            secondaryUser.setUsername(userBean.getUsername());
           // log.debug("user primary id "+getUserDao().getUserById(userBean.getPrimaryUserId()));
            secondaryUser.setSecUser(getUserDao().getUserById(userBean.getPrimaryUserId()));
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
        secondaryUser.setCompleteName(userBean.getName());
        secondaryUser.setUserStatus(userBean.getStatus());
        secondaryUser.setEnjoyDate(new Date());
            // send to user the password to her emails
            if((getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), password);
            }
            // save user
            getUserDao().saveOrUpdate(secondaryUser);
            // assing firs default group to user
            //TODO: we need assing defaul group to user.
            final SecUserSecondary retrievedUser = getUserDao().getSecondaryUserById(secondaryUser.getUid());
            final SecPermission permission = getPermissionDao().loadPermission("ENCUESTAME_USER");
            final List<SecPermission> all = getPermissionDao().findAllPermissions();
            log.info("all permission "+all.size());
            log.info("default permission "+permission);
            retrievedUser.getSecUserPermissions().add(permission);
            log.info("saving user");
            getUserDao().saveOrUpdate(retrievedUser);
            final SecUserSecondary retrievedUser2 = getUserDao().getSecondaryUserById(retrievedUser.getUid());
            log.info("saved user total permissions "+retrievedUser2.getSecUserPermissions().size());
        return password;
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
            userDomain = getUserDao().getSecondaryUserById(userBean.getId());
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
           getUserDao().saveOrUpdate(userDomain);
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
            return ConvertListDomainSelectBean.convertListSecondaryUsersDomainToSelect(getUserDao().getSecondaryUsersByUserId(userId));
    }

    /**
     * {@link MailServiceImpl}.
     */
    public MailServiceImpl getServiceMail() {
        return serviceMail;
    }
    /**
     * Setter.
     * @param serviceMail {@link MailServiceImpl}
     */
    public void setServiceMail(final MailServiceImpl serviceMail) {
        this.serviceMail = serviceMail;
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
     * @return the surveyService
     */
    public SurveyService getSurveyService() {
        return surveyService;
    }
    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }
}
