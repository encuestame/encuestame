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
     * @param userId user id
     * @return
     */
    @SuppressWarnings("unchecked")
   // public Collection<SecUserPermission> loadPermissionByUserId(Integer userId)
    //       throws HibernateException {
    //    return getHibernateTemplate().find(
    //            "from SecUserPermission d where d.secUsers.uid =" + userId);
    //}

    /**
     * Load all permisssion.
     * @return List of  {@link SecPermission}
     */

    public Collection<SecPermission> loadAllPermissions() throws HibernateException{
        return getHibernateTemplate().find("from SecPermission");

    }

    /**
     * Load permission.
     * @param permission
     * @return {@link SecPermission}
     */
    public SecPermission loadPermission(final String permission)
           throws HibernateException{
        session = getEnMeSession();
        try {
            return (SecPermission) session
            .createCriteria(SecPermission.class)
            .add(Restrictions.eq("permission", permission))
            .uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }finally{
           // releaseEnMeSession(session);
        }
    }

    /**
     * Find All Permissions.
     * @return List of {@link SecPermission}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<SecPermission> findAllPermissions() throws HibernateException {
        return super.findAll("from SecPermission");
    }

    /**
    *
    */
   public SecPermission getPermissionById(Long permId) throws HibernateException {
       session = getEnMeSession();
       try {
           return (SecPermission) session.get(SecPermission.class, permId);
       } catch (HibernateException e) {
           throw new HibernateException(e);
       }finally{
           //releaseEnMeSession(session);
       }
   }

}
