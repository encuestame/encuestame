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

import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;

/**
 * Interface SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public interface ISecUserDao extends IBaseDao {

    public SecUserSecondary getUserByUsername(final String username)throws HibernateException;

    //public List<SecGroupPermission> getGroupPermission(List<SecGroupUser> groups);

    //public void assingGroupToUser(final SecGroupUser secGroupUser)
   // throws HibernateException;

   // public List<SecUserPermission> getUserPermission(SecUsers user);

    //public List<SecGroupUser> getUserGroups(SecUserSecondary username);

    //public SecUserSecondary getSecondaryUserById(Long userId) throws HibernateException;

    public List<SecUserSecondary> findAll() throws HibernateException;


    public SecUsers getUserById(final Long userId) throws HibernateException;
}
