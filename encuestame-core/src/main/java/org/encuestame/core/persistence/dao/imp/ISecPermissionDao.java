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
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecPermission;
import org.hibernate.HibernateException;

 /**
  * Interface to implement Permission Dao.
  * @author Picado, Juan juan@encuestame.org
  * @since  11/05/2009 10:46:01
  * @version $Id$
  */
public interface ISecPermissionDao extends IBaseDao {

    //public Collection<SecUserPermission> loadPermissionByUserId(Integer id) throws HibernateException;

    /**
     * Load all permisssion.
     * @return List of  {@link SecPermission}
     * @throws HibernateException exception
     */
    List<SecPermission> loadAllPermissions();
    /**
     * Load permission.
     * @param permission permission
     * @return {@link SecPermission}
     * @throws HibernateException exception
     */
    SecPermission loadPermission(String permission);
    /**
     * Get Permission.
     * @param permId permission
     * @return {@link SecPermission}
     * @throws HibernateException exception
     */
     SecPermission getPermissionById(Long permId);

     /**
      * Find All Permissions.
      * @return {@link SecPermission}
      */
     List<SecPermission> findAllPermissions();
}
