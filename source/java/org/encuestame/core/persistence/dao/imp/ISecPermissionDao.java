/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
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
 * this program; if not, writes to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.dao.imp;

import java.util.Collection;

import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;

 /**
  * Interface to implement Permission Dao.
  * @author Picado, Juan juan@encuestame.org
  * @since  11/05/2009 10:46:01
  */
public interface ISecPermissionDao extends IBaseDao {

    public Collection<SecUserPermission> loadPermissionByUserId(Integer id) throws HibernateException;
    public Collection<SecPermission> loadAllPermissions() throws HibernateException;
    public SecPermission loadPermission(String permission) throws HibernateException;
    public SecPermission getPermissionById(Long idPermission) throws HibernateException;
}
