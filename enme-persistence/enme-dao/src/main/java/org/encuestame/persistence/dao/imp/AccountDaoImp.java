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
package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.HelpPage;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * Account, UsserAccount, SocialAccount Data Repository.
 * @author Picado, Juan juanATencuestame.org
 * @since May 05, 2009
 */
@SuppressWarnings("unchecked")
@Repository("accountDao")
public class AccountDaoImp extends AbstractSocialAccount implements IAccountDao {

    /**
     * Constructor.
     * @param sessionFactory
     */
    @Autowired
    public AccountDaoImp(final SessionFactory sessionFactory) {
           setSessionFactory(sessionFactory);
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.persistence.dao.IAccountDao#findAll()
    */
    public final List<UserAccount> findAll() throws HibernateException {
        return (List<UserAccount>) getHibernateTemplate().find("from UserAccount");
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
        getHibernateTemplate().setCacheQueries(true);
        return (List<UserAccount>) getHibernateTemplate().findByCriteria(criteria, start, maxResults);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#retrieveListUserUnconfirmedByAccount(org.encuestame.persistence.domain.security.Account)
     */
    public final List<UserAccount> retrieveListUserUnconfirmedByAccount(final Account account) {
     final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
     criteria.add(Restrictions.isNotNull("inviteCode"));
     return (List<UserAccount>) getHibernateTemplate().findByCriteria(criteria);
 }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#retrieveTotalUsers(org.encuestame.persistence.domain.security.Account)
     */
    public final Long retrieveTotalUsers(final Account account){
         Long resultsSize = 0L;
         final List<Long> list =  (List<Long>) getHibernateTemplate().findByNamedParam("select count(*) from UserAccount WHERE account = :account", "account", account);
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
        log.trace("getUserByUsername by :{"+username);
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("username", username));
        getHibernateTemplate().setQueryCacheRegion("query.user.by.username");
        getHibernateTemplate().setCacheQueries(true);
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
    * @see org.encuestame.persistence.dao.IAccountDao#searchUsersByEmail(java.lang.String)
    */
    public List<UserAccount> searchUsersByEmail(final String email){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.like("userEmail", email) );
        return   (List<UserAccount>) getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Total of TweetPoll By User Editor.
     * @param userId
     * @return
     */
    public List<Long> getTotalTweetPollByUser(final Long userId){ //editorOwner
        return (List<Long>) getHibernateTemplate().findByNamedParam("select count(tweetPollId) "
               +" from TweetPoll where editorOwner.id = :editorOwner", "editorOwner", userId);
    }

    /**
     * Get Total of TweetPoll By User Editor.
     * @param userId
     * @return
     */
    public List<Long> getTotalPollByUser(final Long userId) { //editorOwner
        return (List<Long>) getHibernateTemplate().findByNamedParam("select count(pollId) "
               +" from Poll where editorOwner.id = :editorOwner", "editorOwner", userId);
    }

    /**
     * Return {@link UserAccount} by provider name and access token key.
     * @param provider
     * @param profileId
     * @return
     * @throws EnMeExpcetion
     */
    public UserAccount findAccountByConnection(final SocialProvider provider, final String profileId)
           throws EnMeNoResultsFoundException {
        final SocialAccount ac = this.findAccountConnectionBySocialProfileId(provider, profileId);
        if (ac == null) {
            throw new EnMeNoResultsFoundException("connection not found");
        } else {
            return ac.getUserOwner();
        }
    }

    /**
     * Get list of id accounts only if are enabled.
     * @return list of id's.
     */
    public List<Long> getAccountsEnabled(final Boolean status){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
        criteria.add(Restrictions.eq("enabled", status));
        criteria.setProjection(Projections.id());
        final List<Long> accountsId = (List<Long>) getHibernateTemplate().findByCriteria(criteria);
        return accountsId;
    }

    /**
     * Disconnect Account Connection.
     * @param accountId
     * @param provider
     * @throws EnMeNoResultsFoundException
     */
    public void disconnect(String accountId, SocialProvider provider) throws EnMeNoResultsFoundException {
        final SocialAccount ac = this.getAccountConnection(accountId, provider);
        if(ac == null){
            throw new EnMeNoResultsFoundException("connection not found");
        } else {
            getHibernateTemplate().delete(ac);
        }
    }

    /**
     * Create new social account.
     * @param socialAccountId
     * @param token
     * @param tokenSecret
     * @param expiresToken
     * @param username
     * @param socialUserProfile
     * @param socialProvider
     * @param userAccount
     * @return
     */
    public SocialAccount createSocialAccount(
            final String socialAccountId,
            final String token,
            final String tokenSecret,
            final String expiresToken,
            final String username,
            final SocialUserProfile socialUserProfile,
            final SocialProvider socialProvider,
            final UserAccount userAccount) {
        final SocialAccount socialAccount = new SocialAccount();
        socialAccount.setAccessToken(token);
        socialAccount.setSecretToken(tokenSecret);
        socialAccount.setAccount(userAccount.getAccount());
        socialAccount.setUserOwner(userAccount);
        socialAccount.setExpires(expiresToken);
        socialAccount.setAccounType(socialProvider);
        socialAccount.setAddedAccount(new Date());
        socialAccount.setVerfied(Boolean.TRUE);
        socialAccount.setSocialAccountName(socialUserProfile.getUsername());
        socialAccount.setType(SocialProvider.getTypeAuth(socialProvider));
        socialAccount.setUpgradedCredentials(new Date());
        socialAccount.setSocialProfileId(socialUserProfile.getId());
        socialAccount.setPublicProfileUrl(socialUserProfile.getProfileUrl());
        socialAccount.setPrictureUrl(socialUserProfile.getProfileImageUrl()); //TODO: repeated
        socialAccount.setProfilePictureUrl(socialUserProfile.getProfileImageUrl());
        socialAccount.setEmail(socialUserProfile.getEmail());
        socialAccount.setProfileThumbnailPictureUrl(socialUserProfile.getProfileImageUrl());
        socialAccount.setRealName(socialUserProfile.getRealName());
        this.saveOrUpdate(socialAccount);
        return socialAccount;
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

    /**
     * Get user account by invitation code.
     * @param inviteCode
     * @return
     */
    public UserAccount getUserAccountbyInvitationCode(final String inviteCode){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("inviteCode", inviteCode));
        return (UserAccount) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByCriteria(criteria));
    }

    /**
     * Get user accounts by status.
     * @param status
     * @return
     */
    public List<UserAccount> getUserAccountsbyStatus(final Boolean status, final Date beforeDate, final Date afterDate){
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("userStatus", status));
        criteria.add(Restrictions.between("enjoyDate", beforeDate, afterDate)); 
        return (List<UserAccount>) getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#getUserAccounts(java.lang.Boolean)
     */
    public List<UserAccount> getUserAccounts(final Boolean status) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserAccount.class);
        criteria.add(Restrictions.eq("userStatus", status));
        return (List<UserAccount>) getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     *
     * @param pagePath
     * @param user
     * @return
     */
    public List<HelpPage> getHelpReference(final String pagePath, final UserAccount user) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(HelpPage.class);
        criteria.add(Restrictions.eq("userAccount", user));
        criteria.add(Restrictions.eq("pagePath", pagePath));
        return (List<HelpPage>) getHibernateTemplate().findByCriteria(criteria);
    }


}
