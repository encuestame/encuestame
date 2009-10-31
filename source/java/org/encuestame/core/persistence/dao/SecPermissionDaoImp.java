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
package org.encuestame.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISecPermissionDao;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
/**
 * Security Permission Dao Implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since May 11, 2009
 */
public class SecPermissionDaoImp extends AbstractHibernateDaoSupport implements
        ISecPermissionDao {

    /**
     * Load permissions by user.
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<SecUserPermission> loadPermissionByUser(Integer id)
           throws HibernateException {
        return getHibernateTemplate().find(
                "from SecUserPermission d where d.secUsers.uid =" + id);

    }

    /**
     * Load all permisssion.
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<SecPermission> loadAllPermissions() throws HibernateException{
        return getHibernateTemplate().find("from SecPermission");

    }

    /**
     * Load permission.
     * @param permission
     * @return
     */
    public SecPermission loadPermission(final String permission)
           throws HibernateException{
        return (SecPermission) getSession()
        .createCriteria(SecPermission.class)
        .add(Restrictions.eq("permission", permission))
        .uniqueResult();
    }

    /**
     * Find All Permissions.
     */
    @SuppressWarnings("unchecked")
    public List<SecPermission> findAllPermissions() throws HibernateException {
        return super.findAll("from SecPermission");
    }

    /**
     * Save or Update Permission.
     * @param permission
     */
    public void saveOrUpdate(Object permission) throws HibernateException {
           getHibernateTemplate().saveOrUpdate(permission);
    }
}
