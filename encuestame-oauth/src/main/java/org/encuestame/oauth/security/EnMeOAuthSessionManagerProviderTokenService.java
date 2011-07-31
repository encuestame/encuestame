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
package org.encuestame.oauth.security;

import org.encuestame.persistence.dao.IApplicationDao;
import org.encuestame.persistence.dao.imp.AccountDaoImp;
import org.encuestame.persistence.dao.imp.ApplicationDao;
import org.encuestame.persistence.domain.application.ApplicationConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeNotValidKeyOAuthSecurityException;
import org.encuestame.utils.oauth.OAuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.provider.token.InvalidOAuthTokenException;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;

/**
 * Adapts the {@link OAuthSessionManager} API to the Spring Security {@link OAuthProviderTokenServices}.
 * Allows for the {@link OAuthSessionManager} to be used with Spring Security OAuth-based Provider to store OAuth request state and establish OAuth connections.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 7:21:29 PM
 * @version $Id:$
 */
public class EnMeOAuthSessionManagerProviderTokenService implements
        OAuthProviderTokenServices {

    /** Oauth session  manager. **/
    @Autowired
    private OAuthSessionManager sessionManager;

    /** {@link AccountDaoImp}. **/
    @Autowired
    private AccountDaoImp accountDaoImp;

    /** {@link ApplicationDao}. **/
    @Autowired
    private IApplicationDao applicationDao;

    /* (non-Javadoc)
     * @see org.springframework.security.oauth.provider.token.OAuthProviderTokenServices#getToken(java.lang.String)
     */
    public OAuthProviderToken getToken(String token)
            throws AuthenticationException {
        try {
            return providerTokenFor(sessionManager.getSession(token));
        } catch (EnMeNotValidKeyOAuthSecurityException e) {
            try {
                return providerTokenFor(this.applicationDao.findAppConnection(token));
            } catch (EnMeNoResultsFoundException ex) {
                throw new InvalidOAuthTokenException("Could not find OAuthSession or AppConnection for provided OAuth request token " + token);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.security.oauth.provider.token.OAuthProviderTokenServices#createUnauthorizedRequestToken(java.lang.String, java.lang.String)
     */
    public OAuthProviderToken createUnauthorizedRequestToken(
            String consumerKey, String callbackUrl)
            throws AuthenticationException {
        return providerTokenFor(sessionManager.newOAuthSession(consumerKey, callbackUrl));
    }

    /* (non-Javadoc)
     * @see org.springframework.security.oauth.provider.token.OAuthProviderTokenServices#authorizeRequestToken(java.lang.String, java.lang.String, org.springframework.security.core.Authentication)
     */
    public void authorizeRequestToken(String requestToken, String verifier,
            Authentication authentication) throws AuthenticationException {
        if (!(authentication.getPrincipal() instanceof UserAccount)) {
            throw new IllegalArgumentException("Authenticated user principal is not of expected Account type");
        }
        try {
            Long authorizingAccountId = ((UserAccount) authentication.getPrincipal()).getUid();
            sessionManager.authorize(requestToken, authorizingAccountId, verifier);
        } catch (EnMeNotValidKeyOAuthSecurityException e) {
            throw new InvalidOAuthTokenException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.security.oauth.provider.token.OAuthProviderTokenServices#createAccessToken(java.lang.String)
     */
    public OAuthAccessProviderToken createAccessToken(String requestToken)
            throws AuthenticationException {
        try {
            return providerTokenFor(sessionManager.grantAccess(requestToken));
        } catch (EnMeNotValidKeyOAuthSecurityException e) {
            throw new InvalidOAuthTokenException(e.getMessage());
        }
    }

    private OAuthProviderToken providerTokenFor(OAuthSession session) {
        return new OAuthSessionProviderToken(session);
    }

    private OAuthAccessProviderToken providerTokenFor(ApplicationConnection connection) {
        return new AppConnectionProviderToken(connection);
    }

    /**
     * @return the accountDaoImp
     */
    public AccountDaoImp getAccountDaoImp() {
        return accountDaoImp;
    }

    /**
     * @param accountDaoImp the accountDaoImp to set
     */
    public void setAccountDaoImp(final AccountDaoImp accountDaoImp) {
        this.accountDaoImp = accountDaoImp;
    }

    /**
     * @return the applicationDao
     */
    public IApplicationDao getApplicationDao() {
        return applicationDao;
    }

    /**
     * @param applicationDao the applicationDao to set
     */
    public void setApplicationDao(final IApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    /**
     * @return the sessionManager
     */
    public OAuthSessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @param sessionManager the sessionManager to set
     */
    public void setSessionManager(final OAuthSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
