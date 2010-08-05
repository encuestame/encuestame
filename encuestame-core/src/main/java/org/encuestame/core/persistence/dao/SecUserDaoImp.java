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
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUserTwitterAccounts;
import org.encuestame.core.persistence.pojo.SecUser;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * SecUsers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
@SuppressWarnings("unchecked")
@Repository
public class SecUserDaoImp extends AbstractHibernateDaoSupport implements ISecUserDao {

   /**
     * Find All Users.
     * @return list of all users
     * @throws HibernateException hibernate
     */
    public List<SecUserSecondary> findAll() throws HibernateException {
        return getHibernateTemplate().find("from SecUserSecondary");
    }

    /**
     * Retrieve List of Secondary users without owner account.
     * @param secUsers {@link SecUser}.
     * @return List of {@link SecUserSecondary}
     */
    public List<SecUserSecondary> retrieveListOwnerUsers(final SecUser	secUsers){
        return getHibernateTemplate().findByNamedParam("from SecUserSecondary where secUser = :secUsers "
            +" ", "secUsers", secUsers);
    }

    /**
     * Get User By Id.
     *
     * @param userId userId
     * @return SecUserSecondary
     * @throws HibernateException hibernate exception
     */
    public SecUserSecondary getSecondaryUserById(final Long userId){
            return (SecUserSecondary) (getHibernateTemplate().get(SecUserSecondary.class, userId));
    }

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    public SecUserTwitterAccounts getTwitterAccount(final Long twitterAccountId){
        return (SecUserTwitterAccounts) (getHibernateTemplate().get(SecUserTwitterAccounts.class, twitterAccountId));
    }

    /**
     * Get Primary User By Id.
     * @param userId user id
     * @return {@link SecUser}
     * @throws HibernateException exception
     */
    public SecUser getUserById(final Long userId) throws HibernateException {
            return (SecUser) getHibernateTemplate().get(SecUser.class, userId);
    }

    /**
     * Get one user by username.
     * @param username username
     * @return list of users
     */
    public SecUserSecondary getUserByUsername(final String username)throws HibernateException {
            final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserSecondary.class);
            criteria.add(Restrictions.eq("username", username) );
            return (SecUserSecondary) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get one user by email.
     * @param email
     * @return
     */
    public SecUserSecondary getUserByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserSecondary.class);
        criteria.add(Restrictions.eq("userEmail", email) );
        return (SecUserSecondary) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get list of users by username.
     * @param username username
     * @return list of users
     */
    public List<SecUserSecondary> getUsersByUsername(final String username) {
            final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserSecondary.class);
            criteria.add(Restrictions.like("username", username) );
            return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Twitter Accounts.
     * @param secUsers {@link SecUser}.
     * @return List {@link SecUserTwitterAccounts}.
     *
     */
    public List<SecUserTwitterAccounts> getTwitterAccountByUser(final SecUser secUsers){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserTwitterAccounts.class);
        criteria.add(Restrictions.like("secUsers", secUsers) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Twitter Verified Accounts.
     * @param secUsers {@link SecUserDaoImp}
     * @return List {@link SecUserTwitterAccounts}.
     */
    public List<SecUserTwitterAccounts> getTwitterVerifiedAccountByUser(final SecUser secUsers){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserTwitterAccounts.class);
        criteria.add(Restrictions.like("secUsers", secUsers) );
        criteria.add(Restrictions.eq("verfied", true) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Search user by email
     * @param email email
     * @return
     */
    public List<SecUserSecondary> searchUsersByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SecUserSecondary.class);
        criteria.add(Restrictions.like("userEmail", email) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }


    /**
     * Get {@link SecUserSecondary} but {@link SecUser} id.
     * @param userId user id
     * @return secondary user list
     */
    public List<SecUserSecondary> getSecondaryUsersByUserId(final Long userId){
            return getHibernateTemplate().findByNamedParam("from SecUserSecondary"
                                          +" WHERE secUser.id = :userId", "userId", userId);
    }
}
