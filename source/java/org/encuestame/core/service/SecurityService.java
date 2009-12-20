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

import org.dom4j.util.UserDataAttribute;
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
 * @version $Id$
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
     * @throws EnMeExpcetion excepcion
     */
    public List<UnitUserBean> loadListUsers() throws EnMeExpcetion {
        final List<UnitUserBean> loadListUsers = new LinkedList<UnitUserBean>();
        try {
            final Collection<SecUserSecondary> listUsers = getUserDao().findAll();
                if (listUsers.size() > 0) {
                    for (Iterator<SecUserSecondary> i = listUsers.iterator(); i.hasNext();) {
                        final UnitUserBean userBean = new UnitUserBean();
                        final  SecUserSecondary user = i.next();
                        userBean.setId(user.getUid());
                        userBean.setName(user.getCompleteName());
                        userBean.setEmail(user.getUserEmail());
                        userBean.setUsername(user.getUsername());
                       // userBean.setPublisher(user.getPublisher());
                       // userBean.setStatus(user.isUserStatus());
                        userBean.setListGroups(convertSetToUnitGroupBean(Integer.valueOf((user.getUid().toString()))));
                      /*  userBean.setListPermission(convertSetToUnitPermission(Integer.valueOf(user.getUid().toString())));*/
                        loadListUsers.add(userBean);
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
     * @param username username
     * @return {@link UnitUserBean}
     */
    public UnitUserBean searchUserByUsername(final String username) {
        final SecUserSecondary userD = getUserDao().getUserByUsername(username);
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
    private Collection<UnitGroupBean> convertSetToUnitGroupBean(final Integer userId)
            throws Exception {
        final Collection<UnitGroupBean> loadListGroups = new LinkedList<UnitGroupBean>();
      /*  if (userId != null) {
            final UnitGroupBean group = new UnitGroupBean();
            Collection<SecGroupUser> listSecGru = getGroupDao()
                    .loadGroupsByUser(Long.valueOf(userId));
            for (Iterator<SecGroupUser> i = listSecGru.iterator(); i.hasNext();) {
                final SecGroupUser userg = i.next();
                group.setGroupName(userg.getSecGroups().getGroupName());
                group.setGroupDescription(userg.getSecGroups().getGroupDescriptionInfo());
                group.setId(Integer.valueOf(userg.getSecGroups().getGroupId().toString()));
                group.setStateId(userg.getSecGroups().getIdState().toString());
                loadListGroups.add(group);
            }
        }*/
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

           /* for (Iterator<SecUserPermission> i = listSecGru.iterator(); i
                    .hasNext();) {
                final SecUserPermission permission = i.next();
                permissionBean.setId(Integer.valueOf(permission.getSecPermission().getIdPermission().toString()));
                permissionBean
                        .setPermission(permission.getSecPermission()
                                .getPermission());
                permissionBean.setDescription(permission.getSecPermission()
                        .getPermissionDescription());
                loadListPermission.add(permissionBean);
            }*/
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
    public UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain) {
        UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(Integer.valueOf(groupDomain.getGroupId().toString()));
        groupBean.setGroupDescription(groupDomain.getGroupDescriptionInfo());
        groupBean.setStateId(String.valueOf(groupDomain.getIdState()));
        return groupBean;
    }

    /**
     * Delete user.
     * @param userBean user to delete
     */
    public void deleteUser(final UnitUserBean userBean) throws EnMeExpcetion {
        try{
            final SecUserSecondary userDomain = getUser(userBean.getUsername().trim());
            if (getSuspendedNotification()) {
                getServiceMail().sendDeleteNotification(userBean.getEmail(),
                        getMessageProperties("MessageDeleteNotification"));
            }
            getUserDao().delete(userDomain);
        }
        catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * Renew password.
     * @param userBean {@link UnitUserBean}
     * @param newPassword new password
     */
    public String renewPassword(final UnitUserBean userBean, String newPassword) {
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
            newPassword = null;
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
    public void updateUser(final UnitUserBean userBean) throws EnMeExpcetion {
        log.info("service update user method");
       try{
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
       catch (Exception e) {
           throw new EnMeExpcetion(e);
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
     *
     * @return
     */
    private SecUsers verifyUserPrimary() {
        final SecUsers userPrimary = new SecUsers();
        getUserDao().saveOrUpdate(userPrimary);
        return userPrimary;
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
            log.debug("user primary id "+getUserDao().getUserById(userBean.getPrimaryUserId()));
            secondaryUser.setSecUser(getUserDao().getUserById(userBean.getPrimaryUserId()));
        }
        else {
            throw new EnMeExpcetion("needed email and username to create user");
        }
        String password = null;
        System.out.println(userBean.getPassword());
        if (userBean.getPassword()!=null) {
             password = userBean.getPassword();
             secondaryUser.setPassword(encryptPassworD(password));
        }
        else{
            password = generatePassword();
            secondaryUser.setPassword(encryptPassworD(password));
        }
        secondaryUser.setPublisher(userBean.getPublisher());
        secondaryUser.setCompleteName(userBean.getName());
        secondaryUser.setUserStatus(userBean.getStatus());
        secondaryUser.setEnjoyDate(new Date());
        try {
            // send to user the password to her emails
            if((getSuspendedNotification())) {
            sendUserPassword(userBean.getEmail(), password);
            }
            // save user
            getUserDao().saveOrUpdate(secondaryUser);
            // assing firs default group to user
            //TODO: we need assing defaul group to user.
        }
        catch (MailSendException ex) {
            log.error("error on notifications, you need desactivate notifications or configure "
                    +"correctly your access on mail server. trace: "+ ex.getMessage());
            throw new EnMeExpcetion(
                    "error on notifications, you need desactivate notifications or configure "
                    +"correctly your access on mail server ");
        }
        catch (HibernateException ex) {
            log.error("data access ERROR on save user trace:"+ex.getMessage());
            throw new EnMeExpcetion("data access ERROR on save user");
        }
        return password;
    }

    /**
     * Load default permission when create user.
     * @return
     * @throws EnMeExpcetion
     */
    private UnitPermission loadDefaultPermissionBean()
            throws EnMeExpcetion {
        final SecPermission permissionDomain = getPermissionDao().loadPermission(
                getDefaultUserPermission().trim());
        log.info("default permission load "+permissionDomain);
        if (permissionDomain != null) {
            //convert domain to bean permission
            final UnitPermission permissionBean = new UnitPermission();
            permissionBean.setDescription(permissionDomain.getPermissionDescription());
            permissionBean.setPermission(permissionDomain.getPermission());
            permissionBean.setId(permissionDomain.getIdPermission());
            return permissionBean;
        } else {
            throw new EnMeExpcetion("default permission not found.");
        }
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
     * @param userBean userBean
     * @param groupBean groupBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void assingGroup(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion {
        try{
         /*   SecGroupUserId groupUserId = new SecGroupUserId();
            groupUserId.setGroupId(Long.valueOf(groupBean.getId().toString()));
            groupUserId.setUid(Long.valueOf(userBean.getId().toString()));
            SecGroupUser groupUser = new SecGroupUser();
            groupUser.setSecGroupUserId(groupUserId);
            groupUser.setState(true);
            getUserDao().assingGroupToUser(groupUser);*/
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
                    permissionBean.setId(permissionDomain.getIdPermission());
                    permissionBean.setPermission(permissionDomain.getPermission());
                    permissionBean.setDescription(permissionDomain.getPermissionDescription());
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
    public SecPermission loadPermission(final String permission) {
        return getPermissionDao().loadPermission(permission);
    }

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception excepcion
     */
    public void inviteUser(String email, String code) throws Exception {
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
                PasswordGenerator.LOWERCASE + PasswordGenerator.CAPITALS, 10);
    }

    /**
     * Encrypt the password.
     * @param password password
     * @return encrypt password
     */
    @Deprecated //Use EnMePasswordUtils
    private String encryptPassworD(final String password) {
        final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.encryptPassword(password);
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
        return defaultUserPermission;
    }
    /**
     * Setter.
     * @param defaultUserPermission default permission
     */
    public void setDefaultUserPermission(final String defaultUserPermission) {
        this.defaultUserPermission = defaultUserPermission;
    }

    /**
     * Getter.
     * @return suspendend notification
     */
    public Boolean getSuspendedNotification() {
        log.info("suspendedNotification->" + suspendedNotification);
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
