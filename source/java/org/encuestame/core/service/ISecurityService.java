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
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.SecGroupDaoImp;
import org.encuestame.core.persistence.dao.SecPermissionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.springframework.mail.MailSendException;

/**
 * Interface for Security Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009  11:35:01
 * @version $Id$
 */
public interface ISecurityService extends IService {


    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws EnMeExpcetion excepcion
     */
    public List<UnitUserBean> loadListUsers() throws EnMeExpcetion;

    /**
     * Assing Group to User.
     * @param user user
     * @param group group
     */
    public void assingGroupToUser(final UnitUserBean user, final UnitGroupBean group);

    /**
     * Search user by username.
     * @param username username
     * @return {@link UnitUserBean}
     */
    public UnitUserBean searchUserByUsername(final String username);

    /**
     * Load all list of permisssions and covert to permission bean.
     * @return list of permisssions
     */
    public Collection<UnitPermission> loadAllListPermission();

    /**
     * Delete Group Domain.
     * @param group group
     */
    public void deleteGroup(final UnitGroupBean group);

    /**
     * Convert Group Domain to Group Bean
     * @param groupDomain {@link SecGroups}
     * @return {@link UnitGroupBean}
     */
    public UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain);
    /**
     * Delete user.
     * @param userBean user to delete
     * @throws EnMeExpcetion exception
     */
    public void deleteUser(final UnitUserBean userBean) throws EnMeExpcetion;
    /**
     * Renew password.
     * @param userBean {@link UnitUserBean}
     * @param newPassword new password
     * @return new password
     */
    public String renewPassword(final UnitUserBean userBean, final String newPassword);
    /**
     * Update a Group.
     * @param groupBean {@link UnitGroupBean}
     */
    public void updateGroup(UnitGroupBean groupBean);
    /**
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    public void updateUser(final UnitUserBean userBean) throws EnMeExpcetion;
    /**
     * Create a new Group.
     * @param groupBean group bean
     */
    public void createGroup(final UnitGroupBean groupBean);
    /**
     * Create a new Permisssion.
     * @param permissionBean {@link UnitPermission}
     */
    public void createPermission(final UnitPermission permissionBean);
    /**
     * Create a user, generate password for user and send email to confirmate
     * the account.
     * @param userBean user bean
     * @return password
     * @throws EnMeExpcetion personalize exception
     */
    public String createUser(final UnitUserBean userBean) throws EnMeExpcetion;

    /**
     * Assign permission to user.
     * @param userBean {@link UnitUserBean}
     * @param permissionBean {@link UnitPermission}
     * @throws EnMeExpcetion exception
     */
    public void assignPermission(
            final UnitUserBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion;

    /**
     * Assing group to user.
     * @param userBean userBean
     * @param groupBean groupBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void assingGroup(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion;

    /**
     * Load Permission domain and covert to permission bean.
     * @param permission permission
     * @return permission bean
     * @throws EnMeExpcetion excepcion
     */
    public UnitPermission loadBeanPermission(final String permission)
    throws EnMeExpcetion;

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    public SecPermission loadPermission(final String permission);

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception excepcion
     */
    public void inviteUser(String email, String code) throws Exception;

    /**
     * Generate hash code invitation.
     * @return generated code
     */
    public String generateHashCodeInvitation();

    /**
     * @return {@link MailServiceImpl}.
     */
    public MailServiceImpl getServiceMail();

    /**
     * Getter.
     * @return default user permission.
     */
    public String getDefaultUserPermission();

    /**
     * Getter.
     * @return suspendend notification
     */
    public Boolean getSuspendedNotification();

    /**
     * @return the surveyService
     */
    public SurveyService getSurveyService();

    /**
     * Getter.
     * @return {@link SecGroupDaoImp}
     */
    public SecGroupDaoImp getGroupDao();

    /**
     * Getter.
     * @return {@link SecPermissionDaoImp}
     */
    public SecPermissionDaoImp getPermissionDao();

    /**
     * Getter.
     * @return {@link SecUserDaoImp}
     */
    public SecUserDaoImp getUserDao();

    /**
     * Find {@link SecUserSecondary} by UserName
     * @param username user name
     * @return {@link SecUserSecondary}
     */
    public SecUserSecondary findUserByUserName(final String username);

}
