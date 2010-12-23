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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.encuestame.persistence.dao.IPermissionDao;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.security.Permission;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
/**
 * Security Permission Dao Implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since May 11, 2009
 * @version $Id$
 */
@Repository
public class PermissionDaoImp extends AbstractHibernateDaoSupport implements IPermissionDao {

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
    public List<Permission> loadAllPermissions() throws HibernateException {
        return getHibernateTemplate().find("from SecPermission");

    }

    /**
     * Load permission.
     * @param permission permission
     * @return {@link Permission}
     */
    @SuppressWarnings("unchecked")
    public Permission loadPermission(final EnMePermission permission)
           throws HibernateException{
        final DetachedCriteria criteria = DetachedCriteria.forClass(Permission.class);
        criteria.add(Restrictions.like("permission", permission) );
        return (Permission) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Find All Permissions.
     * @return List of {@link Permission}
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<Permission> findAllPermissions() throws HibernateException {
        return getHibernateTemplate().find("from SecPermission");
    }

   /**
    * Get Permission.
    * @param permId permission Id
    * return {@link Permission}
    */
   public Permission getPermissionById(final Long permId) throws HibernateException {
           return (Permission) getHibernateTemplate().get(Permission.class, permId);
   }
}
