/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service.social.connect;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.dao.ISocialProviderDao;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.oauth.AuthorizedRequestToken;
import org.encuestame.utils.oauth.OAuthToken;
import org.scribe.extractors.BaseStringExtractorImpl;
import org.scribe.extractors.HeaderExtractorImpl;
import org.scribe.extractors.TokenExtractorImpl;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuth10aServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.services.HMACSha1SignatureService;
import org.scribe.services.TimestampServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract Social Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 2:10:36 AM <-- Merry Christmas
 * @version $Id:$
 */
public abstract class AbstractSocialProvider<S> implements SocialProviderOperations<S>{


    protected org.apache.commons.logging.Log log = LogFactory.getLog(this.getClass());

    /**
     * Account Dao.
     */
    @Autowired
    private IAccountDao accountDaoImp;

    /**
     * Social Provide Dao.
     */
    @Autowired
    private ISocialProviderDao socialProviderDao;

    /**
     * Get Provider Name.
     * @return
     */
    public String getName() {
        return getParameters().getName();
    }

    /**
     * Get Provider Display Name.
     * @return
     */
    public String getDisplayName() {
        return getParameters().getName(); //display name, maybe should be a description.
    }

    /**
     * Get Provider Api Key.
     * @return
     */
    public String getApiKey() {
        return getParameters().getApiKey();
    }

    /**
     * Get Provider application Id.
     * @return
     */
    public Long getAppId() {
        return getParameters().getAppId();
    }

    /**
     * Get provider parameters.
     * @return
     */
    abstract SocialAccountProvider getParameters();


    /**
     * Set provider parameters.
     * @param accountProvider provider.
     * @return
     */
    abstract SocialAccountProvider setParameters(final SocialAccountProvider accountProvider);

    /**
     * Fetch New Request Token.
     * @param callbackUrl
     * @return
     */
    public OAuthToken fetchNewRequestToken(final String callbackUrl) {
        final Token requestToken = getOAuthService(callbackUrl).getRequestToken();
        return new OAuthToken(requestToken.getToken(), requestToken.getSecret());
    }

    /**
     * Build Authorize Url.
     * @param requestToken request token.
     * @return
     */
    public String buildAuthorizeUrl(final String requestToken) {
        //get authorize url and replace word {token} by request Token
        return getParameters().getAuthorizeUrl().replace("{token}", requestToken);
    }

    /**
     * Add new Connection, UserAccount <--> Social Provider Account.
     * @throws EnMeExistPreviousConnectionException
     */
    public void connect(Long accountId, AuthorizedRequestToken requestToken) throws EnMeExistPreviousConnectionException {
        final OAuthToken accessToken = getAccessToken(requestToken);
        S serviceOperations = createServiceOperations(accessToken);
        //find
        String providerAccountId = fetchProviderAccountId(serviceOperations);
        try {
            final AccountConnection s = this.findAccountConnection(accessToken.getValue());
            if (s != null) {
                log.debug("adding new connection");
                this.accountDaoImp.addConnection(
                        getName(),
                        accessToken,
                        providerAccountId,
                        accountId,
                        buildProviderProfileUrl(providerAccountId, serviceOperations));
            } else {
                log.info("There is already a connection created");
                throw new EnMeExistPreviousConnectionException("There is already a connection created");
            }
        } catch (EnMeDomainNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Records an existing connection between a user account and this service provider.
     * Use when the connection process happens outside of the control of this package; for example, in JavaScript.
     * @param accountId the member account identifier
     * @param accessToken the access token that was granted as a result of the connection
     * @param providerAccountId the id of the user in the provider's system; may be an assigned number or a user-selected screen name.
     */
    public void addConnection(Long accountId, String accessToken, String providerAccountId) {
        OAuthToken oauthAccessToken = new OAuthToken(accessToken);
        S serviceOperations = createServiceOperations(oauthAccessToken);
        this.accountDaoImp.addConnection(getName(), oauthAccessToken, providerAccountId, accountId,
                buildProviderProfileUrl(providerAccountId, serviceOperations));
    }

    /**
     * Returns true if the user account is connected to this provider, false otherwise.
     */
    public boolean isConnected(Long accountId) {
            return this.accountDaoImp.isConnected(accountId, getName());
    }

    /**
     * Return if user account have previous account connection.
     * @param accessToken
     * @return
     */
    public boolean isConnected(final String accessToken){
        boolean conected = false;
        try {
            if(this.findAccountByConnection(accessToken) != null){
                conected = true;
            }
        } catch (EnMeExpcetion e) {
            log.error("isConected error :"+e);
        }
        return conected;
    }

    /**
     * Sever the connection between the member account and this service provider.
     * Has no effect if no connection is established to begin with.
     */
    public void disconnect(Long accountId) {
            try {
                this.accountDaoImp.disconnect(accountId, getName());
            } catch (EnMeDomainNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * Gets a handle to the API offered by this service provider.
     * This API may be used by the application to invoke the service on behalf of a member.
     * @param accountId the member account identifier (may be null, if so, only operations that require no authorization may be invoked)
     * @throws EnMeDomainNotFoundException
     */
    @Transactional
    public S getServiceOperations(Long accountId) throws EnMeDomainNotFoundException {
        if (accountId == null || !isConnected(accountId)) {
            return getServiceOperations(null);
        }
        OAuthToken accessToken = this.accountDaoImp.getAccessToken(accountId, getName());
        return createServiceOperations(accessToken);
    }

    /**
     * Find possible open provider account connections.
     * @param accessToken
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public UserAccount findAccountByConnection(String accessToken) throws EnMeDomainNotFoundException {
        return this.accountDaoImp.findAccountByConnection(this.getName(), accessToken);
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public AccountConnection findAccountConnection(String accessToken) throws EnMeDomainNotFoundException {
        return this.accountDaoImp.findAccountConnectionByAccessToken(this.getName(), accessToken);
    }

    //This method should retrieve list of user accounts connected with this provider.
    //public List<ProfileReference> findUserAccountsConnectedTo(List<String> providerAccountIds) {
      // getName(),
      // providerAccountIds;
    //}

    /**
     * Construct the strongly-typed service API template that callers may use to invoke the service offered by this service provider.
     * Subclasses should override to return their concrete service implementation.
     * @param accessToken the granted access token needed to make authorized requests for protected resources
     */

    protected abstract S createServiceOperations(OAuthToken accessToken);

    /**
     * Use the service API to fetch the id the member has been assigned in the provider's system.
     * This id is stored locally to support linking to the user's connected profile page.
     * It is also used for finding connected friends, see {@link #findMembersConnectedTo(List)}.
     */
    protected abstract String fetchProviderAccountId(S serviceOperations);

    /**
     * Build the URL pointing to the member's public profile on the provider's system.
     * @param providerAccountId the id the member is known by in the provider's system.
     * @param serviceOperations the service API
     */
    protected abstract String buildProviderProfileUrl(String providerAccountId, S serviceOperations);

    /**
     * The {@link #getApiKey() apiKey} secret.
     */
    protected String getSecret() {
        return getParameters().getSecret();
    }

    // internal helpers

    private OAuthService getOAuthService() {
        return getOAuthService(null);
    }

    /**
     * Get OAuth Service.
     * @param callbackUrl
     * @return
     */
    private OAuthService getOAuthService(String callbackUrl) {
        log.debug("get getOAuthService "+callbackUrl);
        OAuthConfig config = new OAuthConfig();
        config.setRequestTokenEndpoint(getParameters().getRequestTokenUrl());
        config.setAccessTokenEndpoint(getParameters().getAccessTokenUrl());
        config.setAccessTokenVerb(Verb.POST);
        config.setRequestTokenVerb(Verb.POST);
        config.setApiKey(getParameters().getApiKey());
        config.setApiSecret(getParameters().getSecret());
        if (callbackUrl != null) {
            log.debug("Setting callback url "+callbackUrl);
            config.setCallback(callbackUrl);
        }
        //return new OAuth.
        return new OAuth10aServiceImpl(new HMACSha1SignatureService(),
                new TimestampServiceImpl(),
                new BaseStringExtractorImpl(),
                new HeaderExtractorImpl(),
                new TokenExtractorImpl(),
                new TokenExtractorImpl(),
                config);
    }

    /**
     * Get Access Token.
     * @param requestToken
     * @return
     */
    private OAuthToken getAccessToken(final AuthorizedRequestToken requestToken) {
        Token accessToken = getOAuthService().getAccessToken(new Token(requestToken.getValue(),
                            requestToken.getSecret()),
                            new Verifier(requestToken.getVerifier()));
        return new OAuthToken(accessToken.getToken(), accessToken.getSecret());
    }

    /**
     * @return the accountDaoImp
     */
    public IAccountDao getAccountDaoImp() {
        return accountDaoImp;
    }

    /**
     * @param accountDaoImp the accountDaoImp to set
     */
    public void setAccountDaoImp(final IAccountDao accountDaoImp) {
        this.accountDaoImp = accountDaoImp;
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
