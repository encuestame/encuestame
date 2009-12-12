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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

/**
 * SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public class SecUserDaoImp extends AbstractHibernateDaoSupport implements ISecUserDao {

    /**
     * Assing user to group.
     * @param secGroupUser group user
     * @throws HibernateException exception
     */
    public void assingGroupToUser(final SecGroupUser secGroupUser)
                throws HibernateException {
        getHibernateTemplate().save(secGroupUser);
    }

    /**
     * Find All Users.
     * @return list of all users
     * @throws HibernateException hibernate
     */
    public List<SecUserSecondary> findAll() throws HibernateException {
        return super.findAll("from SecUserSecondary");
    }

    /**
     * Get User By Id.
     * @param userId userId
     * @return SecUserSecondary
     * @throws HibernateException hibernate exception
     */
    public SecUserSecondary getSecondaryUserById(final Long userId) throws HibernateException {
        return (SecUserSecondary) getSession().get(SecUserSecondary.class, userId);
    }

    /**
     * Get Primary User By Id.
     * @param userId user id
     * @return {@link SecUsers}
     * @throws HibernateException exception
     */
    public SecUsers getUserById(final Long userId) throws HibernateException {
        return (SecUsers) getSession().get(SecUsers.class, userId);
    }

    /**
     * Get list of user by username.
     * @param username username
     * @return list of users
     */
    public SecUserSecondary getUserByUsername(final String username)throws HibernateException {
        return  (SecUserSecondary) getSession()
        .createCriteria(SecUserSecondary.class)
        .add(Restrictions.eq("username", username))
        .uniqueResult();
    }

    /**
     * Retrieve a list of permissions by group.
     * @param groups list of groups
     * @return list of permissions.
     */
    @SuppressWarnings("unchecked")
    public List<SecGroupPermission> getGroupPermission(final List<SecGroupUser> groups) {
        final List<SecGroupPermission> listGroupPermission = new ArrayList<SecGroupPermission>();
        final Iterator<SecGroupUser> iList = groups.iterator();
        while (iList.hasNext()) {
            final SecGroupUser secGroups = (SecGroupUser) iList.next();
            List<SecGroupPermission> permission = getHibernateTemplate()
                    .findByNamedParam("from SecGroupPermission d where d.secGroups = :groupId", "groupId",
                            secGroups.getSecGroups());
                final Iterator<SecGroupPermission> ilistPermission = permission
                        .iterator();
                while (ilistPermission.hasNext()) {
                    final SecGroupPermission secPermission = (SecGroupPermission) ilistPermission
                            .next();
                    listGroupPermission.add(secPermission);
                }
        }
        return listGroupPermission;
    }

    /**
     * Get User Permissions.
     * @param user user
     * @return list of permissions
     */
    @SuppressWarnings("unchecked")
    public List<SecUserPermission> getUserPermission(final SecUsers user) {
        return getHibernateTemplate()
                .findByNamedParam("from " +
                        "SecUserPermission  where secUsers.uid "
                        +"= :user ", "user", user.getUid());
    }

    /**
     * List of groups for one user.
     * @param user username
     * @return list of user groups
     */
    @SuppressWarnings("unchecked")
    public List<SecGroupUser> getUserGroups(final SecUserSecondary user) {
        return getHibernateTemplate()
                .findByNamedParam("from SecGroupUser "
                 +" where secUsers = :user ", "user", user);
    }
}
