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
package org.encuestame.business.security.oauth;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.dao.IApplicationDao;
import org.encuestame.persistence.dao.imp.ApplicationDao;
import org.encuestame.persistence.domain.application.ApplicationConnection;
import org.encuestame.persistence.exception.EnMeNotValidKeyOAuthSecurityException;
import org.encuestame.persistence.utils.SecureRandomStringKeyGenerator;
import org.encuestame.utils.oauth.OAuthSession;
import org.encuestame.utils.oauth.StandardOAuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.MapMaker;
/**
 * Implementation to OAuth Session Manager.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 7:23:16 PM
 * @version $Id:$
 */
public class ConcurrentMapOAuthSessionManager implements OAuthSessionManager {

    /**
     * Log.
     */
    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * Map of Sessions.
     */
    private final ConcurrentMap<String, StandardOAuthSession> sessions;

    /**
     * Dao Application.
     * **/
    @Autowired
    private IApplicationDao applicationDao;

    /**
     *  Key Generator.
     * **/
    private SecureRandomStringKeyGenerator keyGenerator = new SecureRandomStringKeyGenerator();

    /**
     * Constructor.
     */
    public ConcurrentMapOAuthSessionManager() {
        sessions = new MapMaker().softValues().expiration(2, TimeUnit.MINUTES).makeMap();
    }

    /**
     * New OAuth Session.
     */
    public OAuthSession newOAuthSession(String apiKey, String callbackUrl) {
        final StandardOAuthSession session = new StandardOAuthSession(apiKey, callbackUrl, keyGenerator.generateKey(), keyGenerator.generateKey());
        log.debug("New OAuth StandardOAuthSession"+session.getApiKey());
        log.debug("New OAuth StandardOAuthSession"+session.getSecret());
        log.debug("New OAuth StandardOAuthSession"+session.getVerifier());
        log.debug("New OAuth StandardOAuthSession"+session.getCallbackUrl());
        sessions.put(session.getRequestToken(), session);
        return session;
    }

    /**
     * Grant Access to App.
     * @param requestToken
     * @return
     * @throws EnMeNotValidKeyOAuthSecurityException
     */
    public ApplicationConnection grantAccess(String requestToken) throws EnMeNotValidKeyOAuthSecurityException {
        log.debug("Grant Access");
        StandardOAuthSession session = getStandardSession(requestToken);
        if (!session.authorized()) {
            throw new IllegalStateException("OAuthSession is not yet authorized");
        }
        log.debug("Grant Access is authorized "+session.authorized());
        try {
            ApplicationConnection connection = this.applicationDao.connectApplication(
                                  session.getAuthorizingAccountId(), session.getApiKey());
            log.debug("Grant Access new connection "+connection.getConnectionId());
            sessions.remove(requestToken);
            return connection;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to grant access due to session - have the App's key changed?", e);
        }
    }

    /**
     * Get Session.
     */
    public OAuthSession getSession(String requestToken)
            throws EnMeNotValidKeyOAuthSecurityException {
        OAuthSession session = sessions.get(requestToken);
        log.debug("OAuth Session SE "+session.getSecret());
        log.debug("OAuth Session AP "+session.getApiKey());
        log.debug("OAuth Session RT "+session.getRequestToken());
        if (session == null) {
            log.error("OAuth Session is null");
            throw new EnMeNotValidKeyOAuthSecurityException(requestToken);
        }
        return session;
    }

    /**
     * Authorize application.
     */
    public OAuthSession authorize(String requestToken,
            Long authorizingAccountId, String verifier)
            throws EnMeNotValidKeyOAuthSecurityException {
        final StandardOAuthSession session = getStandardSession(requestToken);
        log.debug("Authorize session");
        if (session.authorized()) {
            throw new IllegalStateException("OAuthSession is already authorized");
        }
        log.debug("Authorize session RT "+session.getRequestToken());
        session.authorize(authorizingAccountId, verifier);
        return session;
    }


    /**
     * Get Standard Session.
     * @param requestToken
     * @return
     * @throws EnMeNotValidKeyOAuthSecurityException
     */
    private StandardOAuthSession getStandardSession(String requestToken)
            throws EnMeNotValidKeyOAuthSecurityException {
        return (StandardOAuthSession) this.getSession(requestToken);
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
    public void setApplicationDao(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }
}
