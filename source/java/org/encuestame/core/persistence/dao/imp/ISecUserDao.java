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
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;

/**
 * Interface SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 */
public interface ISecUserDao extends IBaseDao {

    public SecUserSecondary getUserByUsername(final String username)throws HibernateException;

    public List<SecGroupPermission> getGroupPermission(List<SecGroupUser> groups);

    public void assingGroupToUser(final SecGroupUser secGroupUser)
    throws HibernateException;

    public List<SecUserPermission> getUserPermission(SecUsers user);

    public List<SecGroupUser> getUserGroups(SecUsers username);

    public SecUserSecondary getUserById(Long userId) throws HibernateException;

    public List<SecUserSecondary> findAll() throws HibernateException;

}
