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

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.SecGroupDaoImp;
import org.encuestame.core.persistence.dao.SecPermissionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.admon.UnitUserBean;

/**
 * Interface for Security Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009  11:35:01
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public interface ISecurityService extends IService {


    public Collection<UnitUserBean> loadListUsers() throws EnMeExpcetion;
    public void assingGroupToUser(final UnitUserBean user, final UnitGroupBean group);
    public UnitUserBean searchUserByUsername(final String username);
    public Collection<UnitPermission> loadAllListPermission();
    public void deleteGroup(final UnitGroupBean group);
    public UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain);
    public void deleteUser(final UnitUserBean userBean);
    public void renewPassword(final UnitUserBean userBean);
    public void updateGroup(UnitGroupBean groupBean);
    public void updateUser(final UnitUserBean userBean);
    public void createGroup(final UnitGroupBean groupBean);
    public void createPermission(final UnitPermission permissionBean);
    public void createUser(UnitUserBean userBean) throws EnMeExpcetion;
    public void assignPermission(
            final UnitUserBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion;
    public UnitPermission loadBeanPermission(final String permission)
    throws EnMeExpcetion;
    public SecPermission loadPermission(final String permission);
    public void inviteUser(String email, String code) throws Exception;
    public String generateHashCodeInvitation();
    public MailServiceImpl getServiceMail();
    public String getDefaultUserPermission();
    public Boolean getSuspendedNotification();
    public SurveyService getSurveyService();
    public SecGroupDaoImp getGroupDao();
    public SecPermissionDaoImp getPermissionDao();
    public SecUserDaoImp getUserDao();



}
