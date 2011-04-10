/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.OAuthToken;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * Account, UsserAccount, AccountConnection Data Repository.
 * @author Picado, Juan juan@encuestame.org
 * @since May 05, 2009
 */
@SuppressWarnings("unchecked")
@Repository("accountDao")
public class AccountDaoImp extends AbstractHibernateDaoSupport implements IAccountDao {

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
    public final List<UserAccount> findAll() throws HibernateException {
        return getHibernateTemplate().find("from UserAccount");
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#retrieveListOwnerUsers(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer)
     */
    public final List<UserAccount> retrieveListOwnerUsers(final Account account,
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
    public final Long retrieveTotalUsers(final Account account){
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
    public final UserAccount getUserAccountById(final Long userId){
            return (UserAccount) (getHibernateTemplate().get(UserAccount.class, userId));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getTwitterAccount(java.lang.Long)
     */
    public final SocialAccount getTwitterAccount(final Long twitterAccountId){
        return (SocialAccount) (getHibernateTemplate().get(SocialAccount.class, twitterAccountId));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getSocialAccount(org.encuestame.persistence.domain.social.SocialProvider, java.lang.Long)
     */
    public final SocialAccount getSocialAccount(final SocialProvider socialProvider, final Long socialAccountId){
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
    public final SocialAccount getSocialAccount(final Long socialAccountId, final Account account){
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
    public final Account getUserById(final Long userId) throws HibernateException {
            return (Account) getHibernateTemplate().get(Account.class, userId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserByUsername(java.lang.String)
     */
    //@Cacheable(cacheName = "userByUsername")
    public final UserAccount getUserByUsername(final String username)throws HibernateException {
        log.debug("getUserByUsername by :{"+username);
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("username", username));
        final UserAccount userAccount = (UserAccount) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByCriteria(criteria));
        return userAccount;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserByEmail(java.lang.String)
     */
    public final UserAccount getUserByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("userEmail", email) );
        return (UserAccount) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByCriteria(criteria));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getTwitterAccountByUser(org.encuestame.persistence.domain.security.Account, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public final List<SocialAccount> getSocialAccountByAccount(
            final Account account,
            final SocialProvider provider){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("secUsers", account) );
        if (provider != null) { //if provider is null, we fetch everything
            criteria.add(Restrictions.eq("accounType", provider));
        }
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getTwitterVerifiedAccountByUser(org.encuestame.persistence.domain.security.Account, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public final List<SocialAccount> getTwitterVerifiedAccountByUser(
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
        final SocialProvider providerSocial = SocialProvider.getProvider(provider);
        connection.setAccountProvider(providerSocial);
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
        criteria.add(Restrictions.eq("userAccout.uid", accountId));
        criteria.add(Restrictions.eq("accountProvider", SocialProvider.getProvider(provider)));
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
         criteria.add(Restrictions.eq("accessToken", accessToken));
         criteria.add(Restrictions.eq("accountProvider", SocialProvider.getProvider(provider)));
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
     * Get list of id accounts only if are enabled.
     * @return list of id's.
     */
    public List<Long> getAccountsEnabled(){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
        criteria.add(Restrictions.eq("enabled", Boolean.TRUE));
        criteria.setProjection(Projections.id());
        final List<Long> accountsId = getHibernateTemplate().findByCriteria(criteria);
        return accountsId;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getPublicProfiles(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<UserAccount> getPublicProfiles(final String keyword,
            final Integer maxResults, final Integer startOn) {
        final List<UserAccount> searchResult = (List<UserAccount>) getHibernateTemplate()
                .execute(new HibernateCallback() {
                    public Object doInHibernate(org.hibernate.Session session) {
                        List<UserAccount> searchResult = new ArrayList<UserAccount>();
                        long start = System.currentTimeMillis();
                        final Criteria criteria = session
                                .createCriteria(UserAccount.class);
                        //only shared profiles.
                        criteria.add(Restrictions.eq("sharedProfile", Boolean.TRUE));
                        // limit results
                        if (maxResults != null) {
                            criteria.setMaxResults(maxResults.intValue());
                        }
                        // start on page x
                        if (startOn != null) {
                            criteria.setFirstResult(startOn.intValue());
                        }
                        searchResult = (List<UserAccount>) fetchMultiFieldQueryParserFullText(
                                keyword, new String[] { "completeName, username" },
                                UserAccount.class, criteria, new SimpleAnalyzer());
                        final List listAllSearch = new LinkedList();
                        listAllSearch.addAll(searchResult);

                        // Fetch result by phrase
                        final List<UserAccount> phraseFullTestResult = (List<UserAccount>) fetchPhraseFullText(
                                keyword, "completeName", UserAccount.class,
                                criteria, new SimpleAnalyzer());
                        log.debug("phraseFullTestResult:{"
                                + phraseFullTestResult.size());
                        listAllSearch.addAll(phraseFullTestResult);
                        // Fetch result by wildcard
                        final List<UserAccount> wildcardFullTextResult = (List<UserAccount>) fetchWildcardFullText(
                                keyword, "completeName", UserAccount.class,
                                criteria, new SimpleAnalyzer());
                        log.debug("wildcardFullTextResult:{"
                                + wildcardFullTextResult.size());
                        listAllSearch.addAll(wildcardFullTextResult);
                        // Fetch result by prefix
                        final List<UserAccount> prefixQueryFullTextResuslts = (List<UserAccount>) fetchPrefixQueryFullText(
                                keyword, "completeName", UserAccount.class, criteria,
                                new SimpleAnalyzer());
                        log.debug("prefixQueryFullTextResuslts:{"
                                + prefixQueryFullTextResuslts.size());
                        listAllSearch.addAll(prefixQueryFullTextResuslts);
                        // Fetch fuzzy results
                        final List<UserAccount> fuzzyQueryFullTextResults = (List<UserAccount>) fetchFuzzyQueryFullText(
                                keyword, "completeName", UserAccount.class, criteria,
                                new SimpleAnalyzer(), SIMILARITY_VALUE);
                        log.debug("fuzzyQueryFullTextResults: {"
                                + fuzzyQueryFullTextResults.size());
                        listAllSearch.addAll(fuzzyQueryFullTextResults);

                        log.debug("listAllSearch size:{" + listAllSearch.size());

                        // removing duplcates
                        final ListOrderedSet totalResultsWithoutDuplicates = ListOrderedSet
                                .decorate(new LinkedList());
                        totalResultsWithoutDuplicates.addAll(listAllSearch);

                        /*
                         * Limit results if is enabled.
                         */
                        List<UserAccount> totalList = totalResultsWithoutDuplicates
                                .asList();
                        if (maxResults != null && startOn != null) {
                            log.debug("split to " + maxResults
                                    + " starting on " + startOn
                                    + " to list with size " + totalList.size());
                            totalList = totalList.size() > maxResults ? totalList
                                    .subList(startOn, maxResults) : totalList;
                        }
                        long end = System.currentTimeMillis();
                        log.debug("UserAccount{ totalResultsWithoutDuplicates:{"
                                + totalList.size() + " items with search time:"
                                + (end - start) + " milliseconds");
                        return totalList;
                    }
                });
        return searchResult;
    }
}
