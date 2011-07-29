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
package org.encuestame.social.connect;

import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.social.connect.service.ConnectOperations;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.social.SocialUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * Abstract Social Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 2:10:36 AM
 */
public abstract class AbstractSocialSignInConnect<SocialAPIOperations> implements SocialSignInOperations{

    /**
     * Log.
     */
    protected org.apache.commons.logging.Log log = LogFactory.getLog(this.getClass());

    /**
     * {@link SecurityOperations}
     */
    @Autowired
    private ConnectOperations connectOperations;

    /**
     * OAuth access grant.
     */
    private AccessGrant accessGrant;

    /**
     *
     * @return
     */
    abstract org.encuestame.social.api.support.SocialAPIOperations getAPISocialProvider();

    /**
     * {@link SocialUserProfile}.
     */
    private SocialUserProfile socialUserProfile;

    /**
     * Constructor.
     * @param accountDaoImpAccountDao
     * @throws Exception
     */
    public AbstractSocialSignInConnect(
            final AccessGrant accessGrant,
            final ConnectOperations connectOperations) throws Exception {
            this.accessGrant = accessGrant;
            this.connectOperations = connectOperations;
            this.setSocialUserProfile(this.getAPISocialProvider().getProfile());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.business.service.social.signin.SocialSignInOperations#
     * connect(java.lang.String, org.encuestame.utils.oauth.AccessGrant)
     */
    public SocialAccount reConnect(
            final String accountId,
            final AccessGrant accesGrant,
            final SocialAccount socialAccount)
            throws EnMeExistPreviousConnectionException,
            EnMeNoResultsFoundException {
        log.info("reConnect ..." + accountId);
        log.info("reConnect ..." + accesGrant.toString());
        log.info("reConnect ..." + socialAccount.getSocialAccountName());
        log.info("Connect restuls: "+socialAccount);
        log.debug("reconnect the social account");
        log.info("Updating connection .... "+socialAccount.getId());
        this.connectOperations.updateSocialAccountConnection(accesGrant, accountId, socialAccount);
        return socialAccount;
    }

    /*
     *
     */
    public abstract SocialProvider getProvider();

    /**
     * Return if user account have previous account connection.
     * @param accessToken
     * @return
     * @throws EnMeExpcetion
     */
    public SocialAccount isConnected(final String profileId) throws EnMeExpcetion {
        Assert.notNull(profileId);
        SocialAccount social = null;
        try {
            log.debug("Is connected exist? "+profileId);
            //check if this user is previously connected
            social = this.findAccountByConnection(profileId);
        } catch (EnMeExpcetion e) {
            log.fatal("isConected error :"+e);
            throw new EnMeExpcetion(e);
        }
        log.debug("Is connected "+social);
        return social;
    }

    /**
     * Find possible open provider account connections.
     * @param profileId the id provided by social network API
     * @return {@link SocialAccount}
     * @throws EnMeNoResultsFoundException
     */
    public SocialAccount findAccountByConnection(final String socialProfileId)
            throws EnMeNoResultsFoundException {
        log.info("Find connection with...-> "+socialProfileId);
        log.info("Find connection with...-> "+socialProfileId);
        return this.connectOperations.findAccountConnectionBySocialProfileId(
                getProvider(), socialProfileId);
    }

    /**
     * @return the accessGrant
     */
    public AccessGrant getAccessGrant() {
        return accessGrant;
    }

    /**
     * @param accessGrant the accessGrant to set
     */
    public void setAccessGrant(final AccessGrant accessGrant) {
        this.accessGrant = accessGrant;
    }

    /**
     * @return the socialUserProfile
     */
    public SocialUserProfile getSocialUserProfile() {
        return socialUserProfile;
    }

    /**
     * @param socialUserProfile the socialUserProfile to set
     */
    public void setSocialUserProfile(final SocialUserProfile socialUserProfile) {
        this.socialUserProfile = socialUserProfile;
    }
}
