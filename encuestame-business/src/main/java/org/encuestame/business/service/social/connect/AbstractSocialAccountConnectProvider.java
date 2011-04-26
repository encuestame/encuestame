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
package org.encuestame.business.service.social.connect;


import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.social.SocialAccountProvider;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.oauth.AuthorizedRequestToken;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public abstract class AbstractSocialAccountConnectProvider {


    protected org.apache.commons.logging.Log log = LogFactory.getLog(this.getClass());

    /**
     * Account Dao.
     */
    @Autowired
    private IAccountDao accountDaoImp;

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
     * Add new Connection, UserAccount <--> Social Provider Account.
     * @throws EnMeExistPreviousConnectionException
     */
//    public void connect(Long accountId, AuthorizedRequestToken requestToken) throws EnMeExistPreviousConnectionException {
//        final OAuth1Token accessToken = getAccessToken(requestToken);
//        S serviceOperations = createServiceOperations(accessToken);
//        //find
//        String providerAccountId = fetchProviderAccountId(serviceOperations);
//        try {
//            final AccountConnection s = this.findAccountConnection(accessToken.getValue());
//            if (s != null) {
//                log.debug("adding new connection");
//                this.accountDaoImp.addConnection(
//                        getName(),
//                        accessToken,
//                        providerAccountId,
//                        accountId,
//                        buildProviderProfileUrl(providerAccountId, serviceOperations));
//            } else {
//                log.info("There is already a connection created");
//                throw new EnMeExistPreviousConnectionException("There is already a connection created");
//            }
//        } catch (EnMeNoResultsFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /**
     * Records an existing connection between a user account and this service provider.
     * Use when the connection process happens outside of the control of this package; for example, in JavaScript.
     * @param accountId the member account identifier
     * @param accessToken the access token that was granted as a result of the connection
     * @param providerAccountId the id of the user in the provider's system; may be an assigned number or a user-selected screen name.
     */
//    public void addConnection(Long accountId, String accessToken, String providerAccountId) {
//        OAuth1Token oauthAccessToken = new OAuth1Token(accessToken);
//        S serviceOperations = createServiceOperations(oauthAccessToken);
//        this.accountDaoImp.addConnection(getName(), oauthAccessToken, providerAccountId, accountId,
//                buildProviderProfileUrl(providerAccountId, serviceOperations));
//    }

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
            } catch (EnMeNoResultsFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * Gets a handle to the API offered by this service provider.
     * This API may be used by the application to invoke the service on behalf of a member.
     * @param accountId the member account identifier (may be null, if so, only operations that require no authorization may be invoked)
     * @throws EnMeNoResultsFoundException
     */
    @Transactional
//    public S getServiceOperations(Long accountId) throws EnMeNoResultsFoundException {
//        if (accountId == null || !isConnected(accountId)) {
//            return getServiceOperations(null);
//        }
//        OAuth1Token accessToken = this.accountDaoImp.getAccessToken(accountId, getName());
//        return createServiceOperations(accessToken);
//    }

    /**
     * Find possible open provider account connections.
     * @param accessToken
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UserAccount findAccountByConnection(String accessToken) throws EnMeNoResultsFoundException {
        return this.accountDaoImp.findAccountByConnection(this.getName(), accessToken);
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public AccountConnection findAccountConnection(String accessToken) throws EnMeNoResultsFoundException {
        return this.accountDaoImp.findAccountConnectionByAccessToken(this.getName(), accessToken);
    }

    /**
     * Use the service API to fetch the id the member has been assigned in the provider's system.
     * This id is stored locally to support linking to the user's connected profile page.
     * It is also used for finding connected friends, see {@link #findMembersConnectedTo(List)}.
     */
    //protected abstract String fetchProviderAccountId(S serviceOperations);

    /**
     * The {@link #getApiKey() apiKey} secret.
     */
    protected String getSecret() {
        return getParameters().getSecret();
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
