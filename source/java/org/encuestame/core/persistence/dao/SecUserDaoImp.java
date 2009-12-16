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

import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

/**
 * SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
public class SecUserDaoImp extends AbstractHibernateDaoSupport implements ISecUserDao {

   /**
     * Find All Users.
     * @return list of all users
     * @throws HibernateException hibernate
     */
    public List<SecUserSecondary> findAll() throws HibernateException {
        return  findAll("from SecUserSecondary");
    }

    /**
     * Get User By Id.
     *
     * @param userId userId
     * @return SecUserSecondary
     * @throws HibernateException hibernate exception
     */
    public SecUserSecondary getSecondaryUserById(final Long userId)
            throws HibernateException {
        session = getEnMeSession();
        try {
           /* return (SecUserSecondary) session.get(SecUserSecondary.class,
                    userId);
            */
            return (SecUserSecondary) (getHibernateTemplate().get(SecUserSecondary.class, userId));

     } catch (HibernateException e) {
            throw new HibernateException(e);
        } finally {
            //releaseSession(session);
        }
    }

    /**
     * Get Primary User By Id.
     *
     * @param userId
     *            user id
     * @return {@link SecUsers}
     * @throws HibernateException
     *             exception
     */
    public SecUsers getUserById(final Long userId) throws HibernateException {
        session = getEnMeSession();
        try {
            return (SecUsers) getHibernateTemplate().get(SecUsers.class, userId);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        } finally {
            //releaseSession(session);
        }
    }

    /**
     * Get list of user by username.
     * @param username username
     * @return list of users
     */
    @SuppressWarnings("unchecked")
    public SecUserSecondary getUserByUsername(final String username)throws HibernateException {
            final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserSecondary.class);
            criteria.add(Restrictions.eq("username", username) );
            return   (SecUserSecondary) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }


}
