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
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollResult;
import org.hibernate.HibernateException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.security.access.annotation.Secured;
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
    public List<SecGroups> findAllGroups() {
        return super.findAll("from SecGroups");
    }

    /**
     * Load Groups By User.
     * @param secUsers {@link SecUsers}.
     * @return list of groups.
     */
    @SuppressWarnings("unchecked")
    public List<SecGroups> loadGroupsByUser(final SecUsers secUsers) {
        return getHibernateTemplate().findByNamedParam("from SecGroups where secUsers = :secUsers ", "secUsers", secUsers);
    }

    /**
     *
     */
    public SecGroups getGroupById(final Long groupId) throws HibernateException {
        return (SecGroups) getHibernateTemplate().get(SecGroups.class,
               groupId);
    }

    /**
     * Get Group by Id and User.
     * @param groupId group id
     * @param secUser {@link SecUsers}
     * @return
     */
    public SecGroups getGroupById(final Long groupId, final SecUsers secUser){
        return (SecGroups) DataAccessUtils.uniqueResult(getHibernateTemplate()
               .findByNamedParam("from SecGroups where groupId = :groupId and  secUsers = :secUser",
                new String[]{"groupId", "secUser"}, new Object[]{groupId, secUser}));
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
