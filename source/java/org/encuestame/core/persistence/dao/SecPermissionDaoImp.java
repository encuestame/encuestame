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
package org.encuestame.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISecPermissionDao;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
/**
 * Security Permission Dao Implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since May 11, 2009
 * @version $Id:$
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
    public List<SecPermission> loadAllPermissions() throws HibernateException {
        return getHibernateTemplate().find("from SecPermission");

    }

    /**
     * Load permission.
     * @param permission permission
     * @return {@link SecPermission}
     */
    public SecPermission loadPermission(final String permission)
           throws HibernateException{
        return (SecPermission) DataAccessUtils.uniqueResult(
                getHibernateTemplate().findByNamedParam("FROM SecPermission where permission = :permission",
                                       "permission", permission));
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
    * Get Permission.
    * @param permId permission Id
    * return {@link SecPermission}
    */
   public SecPermission getPermissionById(final Long permId) throws HibernateException {
           return (SecPermission) getHibernateTemplate().get(SecPermission.class, permId);
   }

}
