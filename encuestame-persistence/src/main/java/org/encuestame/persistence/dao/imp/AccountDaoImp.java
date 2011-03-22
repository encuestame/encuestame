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
import org.encuestame.persistence.dao.ISocialProviderDao;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.SocialAccountProvider;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.oauth.OAuthToken;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Account, UsserAccount, AccountConnection Data Repository.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 * @version $Id$
 */
@SuppressWarnings("unchecked")
@Repository("accountDao")
public class AccountDaoImp extends AbstractHibernateDaoSupport implements IAccountDao {

    /**
     * Social Provider Dao.
     */
    @Autowired
    private ISocialProviderDao socialProviderDao;

    /**
     * Constructor.
     * @param sessionFactory
     */
    @Autowired
    public AccountDaoImp(SessionFactory sessionFactory) {
           setSessionFactory(sessionFactory);
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.persistence.dao.IAccountDao#findAll()
    */
    public List<UserAccount> findAll() throws HibernateException {
        return getHibernateTemplate().find("from UserAccount");
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#retrieveListOwnerUsers(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer)
     */
    public List<UserAccount> retrieveListOwnerUsers(final Account account,
               final Integer maxResults, final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("account", account));
        criteria.setFetchMode("secUserPermissions", FetchMode.SELECT);
        criteria.addOrder(Order.asc("enjoyDate"));
        return getHibernateTemplate().findByCriteria(criteria, start, maxResults);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#retrieveTotalUsers(org.encuestame.persistence.domain.security.Account)
     */
    public Long retrieveTotalUsers(final Account account){
         Long resultsSize = 0L;
         final List list =  getHibernateTemplate().findByNamedParam("select count(*) from UserAccount "
                 +" WHERE account = :account", "account", account);
         if (list.get(0) instanceof Long){
             log.debug("instace of Long");
             resultsSize = (Long) list.get(0);
         }
         return resultsSize;
     }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserAccountById(java.lang.Long)
     */
    public UserAccount getUserAccountById(final Long userId){
            return (UserAccount) (getHibernateTemplate().get(UserAccount.class, userId));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getTwitterAccount(java.lang.Long)
     */
    public SocialAccount getTwitterAccount(final Long twitterAccountId){
        return (SocialAccount) (getHibernateTemplate().get(SocialAccount.class, twitterAccountId));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getSocialAccount(org.encuestame.persistence.domain.social.SocialProvider, java.lang.Long)
     */
    public SocialAccount getSocialAccount(final SocialProvider socialProvider, final Long socialAccountId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        log.debug("accounType "+socialProvider);
        log.debug("socialAccountId "+socialAccountId);
        criteria.add(Restrictions.eq("accounType", socialProvider));
        criteria.add(Restrictions.eq("socialUserId", socialAccountId) );
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getSocialAccount(java.lang.Long, org.encuestame.persistence.domain.security.Account)
     */
    public SocialAccount getSocialAccount(final Long socialAccountId, final Account account){
        log.debug("account "+account.getUid());
        log.debug("socialAccountId "+socialAccountId);
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("secUsers", account));
        criteria.add(Restrictions.eq("id", socialAccountId));
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserById(java.lang.Long)
     */
    public Account getUserById(final Long userId) throws HibernateException {
            return (Account) getHibernateTemplate().get(Account.class, userId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserByUsername(java.lang.String)
     */
    public UserAccount getUserByUsername(final String username)throws HibernateException {
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("username", username));
        final UserAccount userAccount = (UserAccount) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByCriteria(criteria));
        log.debug("getUserByUsername: "+userAccount);
        return userAccount;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserByEmail(java.lang.String)
     */
    public UserAccount getUserByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("userEmail", email) );
        return (UserAccount) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUsersByUsername(java.lang.String)
     */
    public List<UserAccount> getUsersByUsername(final String username) {
            final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
            criteria.add(Restrictions.like("username", username) );
            return   getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getTwitterAccountByUser(org.encuestame.persistence.domain.security.Account, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public List<SocialAccount> getTwitterAccountByUser(
            final Account secUsers,
            final SocialProvider provider){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("secUsers", secUsers) );
        if (provider != null) { //if provider is null, we fetch everything
            criteria.add(Restrictions.eq("accounType", provider));
        }
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getTwitterVerifiedAccountByUser(org.encuestame.persistence.domain.security.Account, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public List<SocialAccount> getTwitterVerifiedAccountByUser(
            final Account secUsers,
            final SocialProvider provider){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("secUsers", secUsers));
        criteria.add(Restrictions.eq("verfied", Boolean.TRUE) );
        if (provider != null) { //if provider is null, we fetch everything
            criteria.add(Restrictions.eq("accounType", provider));
        }
        return getHibernateTemplate().findByCriteria(criteria);
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.persistence.dao.IAccountDao#searchUsersByEmail(java.lang.String)
    */
    public List<UserAccount> searchUsersByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.like("userEmail", email) );
        return   getHibernateTemplate().findByCriteria(criteria);
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getSecondaryUsersByUserId(java.lang.Long)
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

    /**
     * Add connection.
     * @param accountId
     * @param provider
     * @param accessToken
     * @param providerAccountId
     * @param userAccountId
     * @param providerProfileUrl
     */
    public AccountConnection addConnection(
                final String provider,
                final OAuthToken token,
                final String socialAccountId,
                final Long userAccountId,
                final String providerProfileUrl){
        final AccountConnection connection = new AccountConnection();
        //get provider
        final SocialAccountProvider providerSocial = getSocialProviderDao()
                                   .getSocialAccountProviderId(provider);
        connection.setAccountProvider(SocialProvider.TWITTER); //TODO: FIX THIS HARD CODE.
        connection.setAccessToken(token.getValue());
        connection.setSocialAccountId(socialAccountId);
        connection.setSecret(token.getSecret());
        connection.setProfileUrl(providerProfileUrl);
        connection.setUserAccout(this.getUserAccountById(userAccountId));
        getHibernateTemplate().saveOrUpdate(connection);
        return connection;
    }


    /**
     * {@link AccountConnection} Is Connected.
     * @param accountId
     * @param provider
     * @return
     */
    public boolean isConnected(Long accountId, String provider) {
        boolean account = false;
        final AccountConnection connection =  this.getAccountConnection(accountId, provider);
        log.debug("Is connected "+account);
        if(connection != null){
            account = true;
        }
        return account;
    }

    /**
     * Get Account Connection.
     * @param accountId
     * @param proviver
     * @return
     */
    public AccountConnection getAccountConnection(final Long accountId, final String provider){
        final DetachedCriteria criteria = DetachedCriteria.forClass(AccountConnection.class);
        criteria.createAlias("userAccout","userAccout");
        criteria.createAlias("accountProvider","accountProvider");
        criteria.add(Restrictions.eq("userAccout.uid", accountId));
        criteria.add(Restrictions.eq("accountProvider.name", provider));
        return (AccountConnection) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByCriteria(criteria));
    }

    /**
     * Disconnect Account Connection.
     * @param accountId
     * @param provider
     * @throws EnMeNoResultsFoundException
     */
    public void disconnect(Long accountId, String provider) throws EnMeNoResultsFoundException {
        final AccountConnection ac = this.getAccountConnection(accountId, provider);
        if(ac == null){
            throw new EnMeNoResultsFoundException("connection not found");
        } else {
            getHibernateTemplate().delete(ac);
        }
    }

    /**
     * Get Access Token.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public OAuthToken getAccessToken(Long accountId, String provider) throws EnMeNoResultsFoundException {
        final AccountConnection ac = this.getAccountConnection(accountId, provider);
        if (ac != null) {
            final OAuthToken oAuthToken = new OAuthToken(ac.getAccessToken(),
                    ac.getSecret());
            return oAuthToken;
        } else {
            throw new EnMeNoResultsFoundException("connection not found");
        }
    }

    /**
     * Get Provider Account Id.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Long getProviderAccountId(Long accountId, String provider) throws EnMeNoResultsFoundException {
        final AccountConnection ac = this.getAccountConnection(accountId, provider);
        if (ac != null) {
            return ac.getAccountConnectionId();
        } else {
            throw new EnMeNoResultsFoundException("connection not found");
        }

    }

    /**
     * Retrieve {@link AccountConnection} by access token and provider name.
     * @param provider
     * @param accessToken
     * @return
     * @throws EnMeExpcetion
     */
    public AccountConnection findAccountConnectionByAccessToken(final String provider,
                       final String accessToken){
         final DetachedCriteria criteria = DetachedCriteria.forClass(AccountConnection.class);
         criteria.createAlias("accountProvider","accountProvider");
         criteria.add(Restrictions.eq("accessToken", accessToken));
         criteria.add(Restrictions.eq("accountProvider.name", provider));
         return (AccountConnection) DataAccessUtils.uniqueResult(getHibernateTemplate()
                 .findByCriteria(criteria));
    }

    /**
     * Return {@link UserAccount} by provider name and access token key.
     * @param provider
     * @param accessToken
     * @return
     * @throws EnMeExpcetion
     */
    public UserAccount findAccountByConnection(final String provider, final String accessToken)
           throws EnMeNoResultsFoundException {
        final AccountConnection ac = this.findAccountConnectionByAccessToken(provider, accessToken);
        if (ac == null) {
            throw new EnMeNoResultsFoundException("connection not found");
        } else {
            return ac.getUserAccout();
        }
    }

    /**
     * Find user account connected.
     * @param provider
     * @param providerAccount
     * @return
     */
    public List<?> findUserAccountsConnectedTo(String provider,
                   List<SocialAccountProvider> providerAccount) {
         //where id in (select member from AccountConnection where provider =
        //:provider and accountId in ( :providerAccountIds ))";

        return null;
    }

    /**
     * @return the socialProviderDao
     */
    public ISocialProviderDao getSocialProviderDao() {
        return socialProviderDao;
    }

    /**
     * @param socialProviderDao the socialProviderDao to set
     */
    public void setSocialProviderDao(final ISocialProviderDao socialProviderDao) {
        this.socialProviderDao = socialProviderDao;
    }
}
