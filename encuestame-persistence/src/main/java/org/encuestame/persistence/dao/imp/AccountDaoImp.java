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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
@Repository("accountDao")
public class AccountDaoImp extends AbstractHibernateDaoSupport implements IAccountDao {


    @Autowired
    public AccountDaoImp(SessionFactory sessionFactory) {
              setSessionFactory(sessionFactory);
    }

   /**
     * Find All Users.
     * @return list of all users
     * @throws HibernateException hibernate
     */
    public List<UserAccount> findAll() throws HibernateException {
        return getHibernateTemplate().find("from UserAccount");
    }

    /**
     * Retrieve List of Secondary users without owner account.
     * @param secUsers {@link Account}.
     * @return List of {@link UserAccount}
     */
    public List<UserAccount> retrieveListOwnerUsers(final Account secUsers,
               final Integer maxResults, final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("secUser", secUsers));
        criteria.addOrder(Order.asc("enjoyDate"));
        return getHibernateTemplate().findByCriteria(criteria, start, maxResults);
    }

    /**
     * Retrieve Total Users.
     * @param secUsers
     * @return
     */
    public Long retrieveTotalUsers(final Account secUsers){
         Long resultsSize = 0L;
         final List list =  getHibernateTemplate().findByNamedParam("select count(*) from UserAccount "
                 +" WHERE secUser = :user", "user", secUsers);
         if (list.get(0) instanceof Long){
             log.debug("instace of Long");
             resultsSize = (Long) list.get(0);
         }
         return resultsSize;
     }

    /**
     * Get User By Id.
     *
     * @param userId userId
     * @return SecUserSecondary
     * @throws HibernateException hibernate exception
     */
    public UserAccount getSecondaryUserById(final Long userId){
            return (UserAccount) (getHibernateTemplate().get(UserAccount.class, userId));
    }

    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    public SocialAccount getTwitterAccount(final Long twitterAccountId){
        return (SocialAccount) (getHibernateTemplate().get(SocialAccount.class, twitterAccountId));
    }

    /**
     * Get Primary User By Id.
     * @param userId user id
     * @return {@link Account}
     * @throws HibernateException exception
     */
    public Account getUserById(final Long userId) throws HibernateException {
            return (Account) getHibernateTemplate().get(Account.class, userId);
    }

    /**
     * Get one user by username.
     * @param username username
     * @return list of users
     */
    public UserAccount getUserByUsername(final String username)throws HibernateException {
            final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
            criteria.add(Restrictions.eq("username", username) );
            return (UserAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get one user by email.
     * @param email
     * @return
     */
    public UserAccount getUserByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("userEmail", email) );
        return (UserAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get list of users by username.
     * @param username username
     * @return list of users
     */
    public List<UserAccount> getUsersByUsername(final String username) {
            final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
            criteria.add(Restrictions.like("username", username) );
            return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Twitter Accounts.
     * @param secUsers {@link Account}.
     * @return List {@link SocialAccount}.
     *
     */
    public List<SocialAccount> getTwitterAccountByUser(final Account secUsers){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.like("secUsers", secUsers) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Twitter Verified Accounts.
     * @param secUsers {@link AccountDaoImp}
     * @return List {@link SocialAccount}.
     */
    public List<SocialAccount> getTwitterVerifiedAccountByUser(final Account secUsers){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.like("secUsers", secUsers) );
        criteria.add(Restrictions.eq("verfied", true) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Search user by email
     * @param email email
     * @return
     */
    public List<UserAccount> searchUsersByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.like("userEmail", email) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }


    /**
     * Get {@link UserAccount} but {@link Account} id.
     * @param userId user id
     * @return secondary user list
     */
    public List<UserAccount> getSecondaryUsersByUserId(final Long userId){
            return getHibernateTemplate().findByNamedParam("from UserAccount"
                                          +" WHERE secUser.id = :userId", "userId", userId);
    }

    /**
     * Get Total of TweetPoll By User Editor.
     * @param userSecondary
     * @return
     */
    public List<Long> getTotalTweetPollByUser(final Long userId){ //editorOwner
        return getHibernateTemplate().findByNamedParam("select count(tweetPollId) "
               +" from TweetPoll where editorOwner.id = :editorOwner", "editorOwner", userId);
    }

    /**
     * Get Total of TweetPoll By User Editor.
     * @param userSecondary
     * @return
     */
    public List<Long> getTotalPollByUser(final Long userId){ //editorOwner
        return getHibernateTemplate().findByNamedParam("select count(pollId) "
               +" from Poll where editorOwner.id = :editorOwner", "editorOwner", userId);
    }

    /**
     * Get Total of TweetPoll By User Editor.
     * @param userSecondary
     * @return
     */
    public List<Long> getTotalSurveyByUser(final Long userId){ //editorOwner
        return getHibernateTemplate().findByNamedParam("select count(sid) "
               +" from Survey where editorOwner.id = :editorOwner", "editorOwner", userId);
    }



}
