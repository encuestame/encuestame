/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */
package org.encuestame.core.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 */
public class SecUserDaoImp extends AbstractHibernateDaoSupport implements ISecUserDao {

    /**
     * Assing user to group.
     * @param secGroupUser group user
     * @throws HibernateException
     */
    public void assingGroupToUser(final SecGroupUser secGroupUser)
                throws HibernateException {
        getHibernateTemplate().save(secGroupUser);
    }

    /**
     * Find All Users.
     * @return list of all users
     * @throws HibernateException hibernate
     */
    public List<SecUsers> findAll() throws HibernateException {
        return super.findAll("from SecUsers");
    }

    /**
     * Get User By Id.
     * @param userId userId
     * @return SecUsers
     * @throws HibernateException hibernate exception
     */
    public SecUsers getUserById(final Long userId) throws HibernateException{
        return (SecUsers) getSession().get(SecUsers.class, Integer.valueOf(userId.toString()));
    }

    /**
     * Get list of user by username.
     * @param username username
     * @return list of users
     */
    public SecUsers getUserByUsername(final String username)throws HibernateException {
        return  (SecUsers) getSession()
        .createCriteria(SecUsers.class)
        .add(Restrictions.eq("username", username))
        .uniqueResult();
    }

    /**
     * Obtiene una lista de Permisos de los diferentes grupos a los que
     * pertenece
     *
     * @param lista
     *            de grupos
     * @return lista de permisos
     */
    public List<SecGroupPermission> getGroupPermission(List<SecGroupUser> groups) {
        List<SecGroupPermission> listGroupPermission = new ArrayList<SecGroupPermission>();
        Iterator<SecGroupUser> iList = groups.iterator();
        log.info("iniciando el while");
        while (iList.hasNext()) {
            SecGroupUser secGroups = (SecGroupUser) iList.next();
            log.info("secGroups " + secGroups.getSecGroups().getName());
            List<SecGroupPermission> permission = getHibernateTemplate()
                    .findByNamedQuery("User.loadGroupPermission",
                            secGroups.getSecGroups());
            log.info("permission para " + secGroups.getSecGroups().getName()
                    + "->" + permission.size());
            if (permission != null && permission.size() > 0) {
                Iterator<SecGroupPermission> ilistPermission = permission
                        .iterator();
                while (ilistPermission.hasNext()) {
                    SecGroupPermission secPermission = (SecGroupPermission) ilistPermission
                            .next();
                    listGroupPermission.add(secPermission);
                }
            }
        }
        log.info("lista de permisos " + listGroupPermission.size());
        return listGroupPermission;
    }

    /**
     * Get User Permissions.
     * @param user user
     * @return list of permissions
     */
    @SuppressWarnings("unchecked")
    public List<SecUserPermission> getUserPermission(SecUsers user) {
        return getHibernateTemplate()
                .findByNamedParam("from " +
                        "SecUserPermission  where secUsers.uid "
                        +"= :user ", "user", user.getUid());
    }

    /**
     * List of groups for one user.
     * @param username username
     * @return list of user groups
     */
    public List<SecGroupUser> getUserGroups(final SecUsers user) {
        return getHibernateTemplate()
                .findByNamedParam("from SecGroupUser "
                 +" where secUsers = :user ", "user", user);
    }
}
