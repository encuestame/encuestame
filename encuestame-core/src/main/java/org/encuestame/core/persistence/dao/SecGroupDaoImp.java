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

import java.util.List;

import org.encuestame.core.persistence.dao.imp.ISecGroups;
import org.encuestame.core.persistence.pojo.SecGroup;
import org.encuestame.core.persistence.pojo.SecUser;
import org.hibernate.HibernateException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Security Group Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
@Repository
public class SecGroupDaoImp extends AbstractHibernateDaoSupport implements
        ISecGroups {

    /**
     * Find all groups.
     */
    //@Secured("ENCUESTAME_SUPER_ADMIN")
    @SuppressWarnings("unchecked")
    public List<SecGroup> findAllGroups() {
        return getHibernateTemplate().find("from SecGroups");
    }

    /**
     * Load Groups By User.
     * @param secUsers {@link SecUser}.
     * @return list of groups.
     */
    @SuppressWarnings("unchecked")
    public List<SecGroup> loadGroupsByUser(final SecUser secUsers) {
        return getHibernateTemplate().findByNamedParam("from SecGroup where secUsers = :secUsers ", "secUsers", secUsers);
    }

    /**
     *
     */
    public SecGroup getGroupById(final Long groupId) throws HibernateException {
        return (SecGroup) getHibernateTemplate().get(SecGroup.class,
               groupId);
    }

    /**
     * Get Group by Id and User.
     * @param groupId group id
     * @param secUser {@link SecUser}
     * @return
     */
    @SuppressWarnings("unchecked")
    public SecGroup getGroupById(final Long groupId, final SecUser secUser){
        return (SecGroup) DataAccessUtils.uniqueResult(getHibernateTemplate()
               .findByNamedParam("from SecGroup where groupId = :groupId and  secUsers = :secUser",
                new String[]{"groupId", "secUser"}, new Object[]{groupId, secUser}));
    }

    /**
     * Find group by Id.
     * @param groupId group id.
     * @return group
     */
    public SecGroup find(final Long groupId) {
        return (SecGroup) getHibernateTemplate().get(SecGroup.class, groupId);
    }
}
