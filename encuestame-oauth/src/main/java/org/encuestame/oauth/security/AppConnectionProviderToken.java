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

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.dao.imp.AccountDaoImp;
import org.encuestame.persistence.domain.application.ApplicationConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;

/**
 * Implementation to a Spring Security {@link OAuthAccessProviderToken}.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 24, 2010 10:58:37 AM
 * @version $Id:$
 * @see OAuthSessionManagerProviderTokenServices
 */
@SuppressWarnings("serial")
public class AppConnectionProviderToken implements OAuthAccessProviderToken {

    /** Log. **/
    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * Reference to {@link ApplicationConnection}.
     */
    private ApplicationConnection connection;

    /**
     * Account Dao.
     * **/
    @Autowired
    private AccountDaoImp accountDaoImp;

    /**
     * Authentication.
     * **/
    private Authentication userAuthentication;

    /**
     * Constructor.
     * @param connection application connection.
     */
    public AppConnectionProviderToken(ApplicationConnection connection) {
        this.connection = connection;
    }

    /**
     * Consumer Key.
     * A value used by the Consumer to identify itself to the Service Provider.
     */
    public String getConsumerKey() {
        return connection.getApiKey();
    }

    /**
     * The value of the token.
     *
     * @return The value of the token.
     */
    public String getValue() {
        return connection.getAccessToken();
    }

    /**
     * The token secret.
     *
     * @return The token secret.
     */
    public String getSecret() {
        return connection.getSecret();
    }

    /**
     * The callback URL associated with this token.
     *
     * @return The callback URL associated with this token.
     */
    public String getCallbackUrl() {
        return null;
    }

    /**
     * The verifier string for this token.
     *
     * @return The verifier string for this token.
     */
    public String getVerifier() {
        return null;
    }

    /**
     * Whether this is an OAuth access token.
     *
     * @return Whether this is an OAuth access token.
     */
    public boolean isAccessToken() {
        return true;
    }

    /**
     * Get {@link UserAccount}. Authentication.
     */
    public Authentication getUserAuthentication() {
        if (userAuthentication == null) {
            UserAccount account = this.accountDaoImp.getUserAccountById(connection.getAccount().getUid());
            log.debug("Get User Authentication "+account);
            return this.authenticationTokenFor(account);
        }
        return userAuthentication;
    }

    /**
     * Create Authentication.
     * @param account {@link UserAccount}.
     * @return
     */
    public Authentication authenticationTokenFor(final UserAccount account) {
        //create traditional UserNamePasswordAuthentication.
        return new UsernamePasswordAuthenticationToken(account, null, (Collection<GrantedAuthority>)null);
    }

}
