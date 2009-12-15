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
 **/
package org.encuestame.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISecGroups;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.hibernate.HibernateException;

/**
 * Security Group Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since May 05, 2009
 */
public class SecGroupDaoImp extends AbstractHibernateDaoSupport implements
        ISecGroups {

    /**
     * Find all groups.
     */
    @SuppressWarnings("unchecked")
    public List<SecGroups> findAllGroups() {
        return super.findAll("from SecGroups");
    }

    /**
     * Load Groups By User.
     * @param userId user id
     * @return list of groups.

    @SuppressWarnings("unchecked")
    public Collection<SecGroupUser> loadGroupsByUser(Long userId) {
        return getHibernateTemplate().findByNamedParam("from SecGroupUser  "
         +"where secUsers.uid = :uid", "uid", userId);
    }*/

    /**
     *
     */
    public SecGroups getGroupById(Long groupId) throws HibernateException {
        return (SecGroups) getEnMeSession().get(SecGroups.class,
               groupId);
    }

    /**
     * Find group by Id.
     * @param groupId group id.
     * @return group
     */
    public SecGroups find(final Long groupId) {
        return (SecGroups) getHibernateTemplate().get(SecGroups.class, groupId);
    }
}
