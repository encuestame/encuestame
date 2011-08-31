/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Account;

/**
 * Interface to implement Sec Group Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since 11/05/2009 10:45:30
 * @version $Id$
 */
public interface IGroupDao extends IBaseDao {

    /**
     * Find All Groups.
     * @return
     */
    List<Group> findAllGroups();

    /**
     * Group By Id.
     * @param groupId
     * @return
     */
    Group getGroupById(Long groupId);

    /**
     * Get Group by Id
     * @param groupId
     * @param secUser
     * @return
     */
    Group getGroupById(final Long groupId, final Account secUser);

    /**
     * Get Group by Id and User.
     * @param groupId
     * @param userId
     * @return
     */
    Group getGroupByIdandUser(final Long groupId, final Long userId);

     /**
     * Find.
     * @param groupId
     * @return
     */
    Group find(final Long groupId);

    /**
     * Load Groups By User.
     * @param secUsers {@link Account}.
     * @return list of groups.
     */
    List<Group> loadGroupsByUser(final Account secUsers);

    /**
     * Counter Users by Group.
     * @param secGroupId
     * @return
     */
     Long getCountUserbyGroup(final Long secGroupId);

     /**
      *	Get Users by Groups.
      * @param user
      * @return
      */
     List<Object[]> getUsersbyGroups(final Account user);

    /**
     * Get Users by Groups.
     * @param user
     * @return
     */
    List<Object[]> countUsersbyGroups(final Long user);
}

