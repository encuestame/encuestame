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

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.SocialAccount.TypeAuth;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.oauth.OAuth1Token;
import org.encuestame.utils.social.SocialUserProfile;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

/**
 * SocialAccount dao support.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 30, 2011
 */
public abstract class AbstractSocialAccount extends AbstractHibernateDaoSupport{

    /**
     * Return a {@link SocialAccount} by account Id.
     * @param socialAccountId
     * @return
     */
    public final SocialAccount getSocialAccountById(final Long socialAccountId){
        return (SocialAccount) (getHibernateTemplate().get(SocialAccount.class, socialAccountId));
    }

    /**
     * Return a {@link SocialAccount} by {@link SocialProvider} and social profile id (unique).
     * @param socialProvider {@link SocialProvider}
     * @param socialProfileId social profile id.
     * @return {@link SocialAccount}.
     */
    @SuppressWarnings("unchecked")
    public final SocialAccount getSocialAccount(
            final SocialProvider socialProvider,
            final String socialProfileId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("accounType", socialProvider));
        criteria.add(Restrictions.eq("socialProfileId", socialProfileId));
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Return a {@link SocialAccount} by id and {@link Account}.
     * @param socialAccountId
     * @param account
     * @return
     */
    @SuppressWarnings("unchecked")
    public final SocialAccount getSocialAccount(
            final Long socialAccountId,
            final Account account){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("id", socialAccountId));
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     *
     * @param socialAccountId
     * @param account
     * @param userAccount
     * @return
     */
    @SuppressWarnings("unchecked")
    public final SocialAccount getSocialAccount(
            final Long socialAccountId,
            final Account account,
            final UserAccount userAccount) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("userOwner", userAccount));
        criteria.add(Restrictions.eq("id", socialAccountId));
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Return {@link SocialAccount} by Account Owner and Social Profile Id.
     * @param socialProfileId
     * @param userAccount
     * @return
     */
    @SuppressWarnings("unchecked")
    public final SocialAccount getSocialAccount(
            final Long socialProfileId,
            final UserAccount userAccount) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("userOwner", userAccount));
        criteria.add(Restrictions.eq("socialProfileId", socialProfileId));
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }


    /**
     * Return a List of {@link SocialAccount} by {@link Account}.
     * This method is used to share all social accounts connections for all members.
     * @param account {@link Account}
     * @param provider {@link SocialProvider}
     * @return list of {@link SocialAccount}
     */
    @SuppressWarnings("unchecked")
    public final List<SocialAccount> getSocialAccountByAccount(
            final Account account,
            final SocialProvider provider){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("account", account) );
        //if provider is null, we fetch everything
        if (provider != null) {
            criteria.add(Restrictions.eq("accounType", provider));
        }
        return   getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     *
     * @param account
     * @param provider
     * @return
     */
    @SuppressWarnings("unchecked")
    public final List<SocialAccount> getSocialVerifiedAccountByUserAccount(
            final Account account, final SocialProvider provider) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(SocialAccount.class);
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("verfied", Boolean.TRUE));
        if (provider != null) { // if provider is null, we fetch everything
            criteria.add(Restrictions.eq("accounType", provider));
        }
        return getHibernateTemplate().findByCriteria(criteria);
    }


    /**
     * Create new social connection
     * @param provider {@link SocialProvider}
     * @param accessGrant {@link AccessGrant}
     * @param socialAccountId social account id.
     * @param userAccount {@link UserAccount}
     * @param providerProfileUrl provider profile url.
     * @return
     */
    public SocialAccount updateSocialAccountConnection(
                final AccessGrant accessGrant, //OAuth2
                final String socialAccountId,
                final SocialAccount currentSocialAccount){
        log.debug("Add Connection "+accessGrant);
        log.debug("Add Connection "+socialAccountId);
        //reference to social account.
        //connection.setSocialAccount(socialAccount);
        //store oauth provider permissions.
        if (SocialProvider.getTypeAuth(currentSocialAccount.getAccounType()).equals(TypeAuth.OAUTH1)) {
            //OAuth access token.
            //connection.setAccessToken(token.getValue());
            //OAuth1
            //connection.setSecretToken(token.getSecret());
            //TODO: pending OAUTH1.
            log.debug("pending OAUTH1 - OAuth 1 social connection is not available right now.");
        } else if(SocialProvider.getTypeAuth(currentSocialAccount.getAccounType()).equals(TypeAuth.OAUTH2)) {
            //OAuth2
            currentSocialAccount.setAccessToken(accessGrant.getAccessToken());
            currentSocialAccount.setRefreshToken(accessGrant.getRefreshToken());
            currentSocialAccount.setExpires(accessGrant.getExpires());
        }
        Assert.assertNotNull(socialAccountId);
        currentSocialAccount.setSocialProfileId(socialAccountId);
        this.saveOrUpdate(currentSocialAccount); //TODO: this seems not save or update properly.
        log.debug("Added Connection:{"+currentSocialAccount.toString());
        return currentSocialAccount;
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

    /**
     * {@link SocialAccount} Is Connected.
     * @param accountId
     * @param provider
     * @return
     */
    public boolean isConnected(String accountId, SocialProvider provider) {
        boolean account = false;
        final SocialAccount connection =  this.getAccountConnection(accountId, provider);
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
    @SuppressWarnings("unchecked")
    public SocialAccount getAccountConnection(final String accountId, final SocialProvider provider){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
        criteria.createAlias("userAccout","userAccout");
        criteria.add(Restrictions.eq("userAccout.uid", accountId));
        criteria.add(Restrictions.eq("accounType", provider));
        return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByCriteria(criteria));
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
     * Get Access Token.
     * @param accountId
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public OAuth1Token getAccessToken(String accountId, SocialProvider provider) throws EnMeNoResultsFoundException {
        final SocialAccount ac = this.getAccountConnection(accountId, provider);
        if (ac != null) {
            final OAuth1Token oAuthToken = new OAuth1Token(ac.getAccessToken(),
                    ac.getSecretToken());
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
    public Long getProviderAccountId(String accountId, SocialProvider provider) throws EnMeNoResultsFoundException {
        final SocialAccount ac = this.getAccountConnection(accountId, provider);
        if (ac != null) {
            return ac.getId();
        } else {
            throw new EnMeNoResultsFoundException("connection not found");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.IAccountDao#findAccountConnectionBySocialProfileId(org.encuestame.persistence.domain.social.SocialProvider, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public SocialAccount findAccountConnectionBySocialProfileId(final SocialProvider provider,
                       final String socialProfileId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(SocialAccount.class);
         criteria.add(Restrictions.eq("socialProfileId", socialProfileId));
         criteria.add(Restrictions.eq("accounType", provider));
         return (SocialAccount) DataAccessUtils.uniqueResult(getHibernateTemplate()
                 .findByCriteria(criteria));
    }

    /**
     * Get social accounts stats.
     * @param socialAccount {@link SocialAccount}.
     * @return
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Long> getSocialAccountStats(final SocialAccount socialAccount) {
        final HashMap<String, Long> stats = new HashMap<String, Long>();
        log.debug("getSocialAccountStats "+socialAccount.getId());
        final DetachedCriteria criteria = DetachedCriteria.forClass(TweetPollSavedPublishedStatus.class);
        criteria.add(Restrictions.eq("socialAccount", socialAccount));
        criteria.setProjection(Projections.rowCount());
        final List<Long> tweetPollstats = getHibernateTemplate().findByCriteria(criteria);
        log.debug("getSocialAccountStats "+tweetPollstats.size());
        log.debug("getSocialAccountStats "+tweetPollstats);
        if(tweetPollstats.size() > 0) {
            stats.put("tweetpoll", Long.valueOf(tweetPollstats.get(0).toString()));
        } else {
            stats.put("tweetpoll", 0L);
        }
      //TODO: in the future we can add another stats.
        stats.put("poll", 0L);
        stats.put("survey", 0L);
        log.debug("getSocialAccountStats stats"+stats);
        return stats;
    }
}
