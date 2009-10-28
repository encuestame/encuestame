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
 *
 */
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;

/**
 * Id: IUserDao.java Date: 07/05/2009
 * @author juanpicado
 * package: org.encuestame.core.persistence.dao.imp
 * @version 1.0
 */
public interface ISecUserDao extends IBaseDao {

    public SecUsers getUser(String username);

    public List<SecGroupPermission> getGroupPermission(List<SecGroupUser> groups);

    public List<SecUserPermission> getUserPermission(SecUsers user);

    public List<SecGroupUser> getUserGroups(SecUsers username);

    public void delete(SecUsers user) throws HibernateException;

    public void createUser(SecUsers user) throws HibernateException;
}
