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
package org.encuestame.core.service.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.encuestame.web.beans.project.UnitProjectBean;

/**
 * Convert Domain to  Beans.
 * @author Picado, Juan juan@encuestame.org
 * @since 03/12/2009 06:38:32
 */
public class ConvertDomainBean {

    private static Log log = LogFactory.getLog(ConvertDomainBean.class);

    /**
     * Convert Domain user to Bean User.
     * @param domainUser Domain User
     * @return Bean User
     */
    public static UnitUserBean convertUserDaoToUserBean(SecUsers domainUser) {
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
     * Convert Group Domain to Group Bean
     * @param groupDomain {@link SecGroups}
     * @return {@link UnitGroupBean}
     */
    public static UnitGroupBean convertGroupDomainToBean(final SecGroups groupDomain) {
        UnitGroupBean groupBean = new UnitGroupBean();
        groupBean.setId(Integer.valueOf(groupDomain.getGroupId().toString()));
        groupBean.setGroupDescription(groupDomain.getDesInfo());
        groupBean.setStateId(String.valueOf(groupDomain.getIdState()));
        return groupBean;
    }


    /**
     * Convert {@link Project} to {@link UnitProjectBean}
      * @param project {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     */
    public static UnitProjectBean convertProjectDomainToBean(final Project project) {
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setName(project.getDescription());
        projectBean.setDateFinish(project.getDateFinish());
        projectBean.setDateInit(project.getDateStart());
        projectBean.setId(project.getProyectId());
        projectBean.setState(project.getCatState().getIdState());
        return projectBean;
    }


    /**
     * @param permission permission.
     * @return permBean
     */
    public static UnitPermission convertPermissionToBean(final SecPermission permission){
      UnitPermission permBean = new UnitPermission();
      permBean.setId(Integer.valueOf(permission.getIdPermission().toString()));
      permBean.setDescription(permission.getDescription());
      permBean.setPermission(permission.getPermission());
      return permBean;
    }

}
