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
package org.encuestame.business.service.social.signin;

import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;

/**
 * Abstract Social Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 2:10:36 AM
 */
public abstract class AbstractSocialSignInConnect<S> implements SocialSignInOperations{

    /**
     * Log.
     */
    protected org.apache.commons.logging.Log log = LogFactory.getLog(this.getClass());

    /**
     * Account Dao.
     */
    private IAccountDao accountDaoImp;

    /**
     * OAuth access grant.
     */
    private AccessGrant accessGrant;


    abstract S getAPISocialProvider();

    /**
     *
     * @param accountDaoImpAccountDao
     */
    public AbstractSocialSignInConnect(
            final IAccountDao accountDaoImpAccountDao,
            final AccessGrant accessGrant) {
            this.accountDaoImp = accountDaoImpAccountDao;
            this.accessGrant = accessGrant;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.social.signin.SocialSignInOperations#connect(java.lang.String, org.encuestame.utils.oauth.AccessGrant)
     */
    public void connect(String accountId, AccessGrant accesGrant) throws EnMeExistPreviousConnectionException {
        try {
            final AccountConnection s = this.findAccountConnection(accountId);
            if (s != null) {
                log.debug("adding new connection");
//                this.accountDaoImp.addConnection(
//                        provider,
//                        token,
//                        accessGrant,
//                        socialAccountId,
//                        userAccountId,
//                        providerProfileUrl,
//                        socialAccoun);
            } else {
                log.info("There is already a connection created");
                throw new EnMeExistPreviousConnectionException("There is already a connection created");
            }
        } catch (EnMeNoResultsFoundException e) {
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
//        this.accountDaoImp.addConnection(
//                provider,
//                token,
//                accessGrant,
//                socialAccountId,
//                userAccountId,
//                providerProfileUrl,
//                socialAccoun)
    }

    /*
     *
     */
    abstract SocialProvider getProvider();

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
    public void disconnect(String accountId) {
            try {
                this.accountDaoImp.disconnect(accountId, getProvider());
            } catch (EnMeNoResultsFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * Find possible open provider account connections.
     * @param accessToken
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccount findAccountByConnection(String accessToken) throws EnMeNoResultsFoundException {
        return this.accountDaoImp.findAccountByConnection(getProvider(), accessToken);
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public AccountConnection findAccountConnection(String socialProfileId) throws EnMeNoResultsFoundException {
        return this.accountDaoImp.findAccountConnectionBySocialProfileId(getProvider(), socialProfileId);
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
}
