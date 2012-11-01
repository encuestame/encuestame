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
package org.encuestame.social.connect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.social.connect.SocialSignInOperations;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.social.SocialUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Social connect support service.
 * @author Picado, Juan juanATencuestame.org
 * @since 29/07/2011
 */
@Service
public class ConnectSupportService  extends AbstractBaseService implements ConnectOperations{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     */
    @Autowired
    private SecurityOperations securityOperations;

    /**
     * Start the process to sign in with social account.
     * @param SocialSignInOperations sign in social support.
     */
    public String connectSignInAccount(final SocialSignInOperations social) throws EnMeExpcetion {
        // first, we check if this social account already exist as previous connection.
        log.info("Sign In with Social Account");
        //if user account is previously connected
        SocialAccount socialAccount = social.isConnected(social.getSocialUserProfile().getId());
        if (socialAccount != null) {
            log.info("Connecting: Exist previously connection");
            //if exist, we update credentials on account connect store.
            social.reConnect(social.getSocialUserProfile().getId(), social.getAccessGrant(), socialAccount);
            //getAccountDao().saveOrUpdate(connection.getSocialAccount());
            getAccountDao().saveOrUpdate(socialAccount);
            SecurityUtils.socialAuthentication(socialAccount); //TODO: only with OWNER UserAccount.
            return PathUtil.DASHBOARD_REDIRECT;
        } else {
            //if user has been never connected, we check if the user exist with the social account email.
            log.info("Connecting: Creating new connection");
            //get email from social profile.
            final String email = this.convertSocialConnectedAccountToBean(social.getSocialUserProfile()).getEmail();
            log.info("sign in social account email -->"+email);
            String redirectPath =  "signin/provider/register";
            Assert.notNull(email);
            //user account by email
            final UserAccount accountEmail = getAccountDao().getUserByEmail(email);
            //if the user account is new, we create new account.
            if (accountEmail == null) {
                log.debug("This email ["+email+"] never has been used.");
                //create fist connection and social account.
                final SocialAccount accountConnection = this.signUpSocial(
                        social.getSocialUserProfile(), social.getAccessGrant(),
                        social.getProvider(), null);
                SecurityUtils.socialAuthentication(accountConnection);
            } else {
                //if user exist, we create new account connection
                final SocialAccount accountConnection = addNewSocialAccount(
                        social.getAccessGrant().getAccessToken(),
                        social.getAccessGrant().getRefreshToken(),
                        social.getAccessGrant().getExpires(),
                        social.getSocialUserProfile(),
                        social.getProvider(),
                        accountEmail);
                SecurityUtils.socialAuthentication(accountConnection);
                redirectPath = PathUtil.DASHBOARD_REDIRECT;
            }
            return redirectPath;
        }
    }

    /**
     * Default sign up social account.
     * @param profile
     * @param accessGrant
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private SocialAccount signUpSocial(
            final SocialUserProfile profile,
            final AccessGrant accessGrant,
            final SocialProvider provider,
            final UserAccount account) throws EnMeNoResultsFoundException{
        UserAccount accountEmail;
        if (account == null) {
            //create new account.
            accountEmail = getSecurityOperations().singupUser(this.convertSocialConnectedAccountToBean(profile), false);
        } else {
            //use the current account.
            accountEmail = account;
        }
        final SocialAccount accountConnection = addNewSocialAccount(
                accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpires(),
                profile,
                provider,
                accountEmail);
        return accountConnection;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#addNewSocialAccount(java.lang.String, java.lang.String, org.encuestame.core.social.SocialUserProfile, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public SocialAccount addNewSocialAccount(
            final String token,
            final String tokenSecret,
            final String expiresToken,
            final SocialUserProfile socialUserProfile,
            final SocialProvider socialProvider,
            final UserAccount userAccount){
        return getAccountDao().createSocialAccount(socialUserProfile.getId(), token,
                tokenSecret, expiresToken, socialUserProfile.getUsername(), socialUserProfile,
                socialProvider, userAccount);
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#findAccountConnectionBySocialProfileId(org.encuestame.persistence.domain.social.SocialProvider, java.lang.String)
     */
    public final SocialAccount findAccountConnectionBySocialProfileId(
            final SocialProvider provider,
            final String socialProfileId) {
        return getAccountDao().findAccountConnectionBySocialProfileId(provider, socialProfileId);
    }

    /**
     *
     * @param accessGrant
     * @param socialAccountId
     * @param userAccount
     * @param providerProfileUrl
     * @param currentSocialAccount
     * @return
     */
    public SocialAccount updateSocialAccountConnection(
            final AccessGrant accessGrant, //OAuth2
            final String socialAccountId,
            final SocialAccount currentSocialAccount){
        return getAccountDao().updateSocialAccountConnection(accessGrant, socialAccountId, currentSocialAccount);
    }


    /**
    *
    * @param userProfile
    * @return
    */
   private SignUpBean convertSocialConnectedAccountToBean(final SocialUserProfile userProfile){
       log.info("Social Account Profile "+userProfile.toString());
       final SignUpBean singUpBean = new SignUpBean();
       singUpBean.setEmail(userProfile.getEmail());
       singUpBean.setUsername(userProfile.getUsername());
       return singUpBean;
   }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.SecurityOperations#disconnectSignInAccount(org.encuestame.business.service.social.signin.SocialSignInOperations)
     */
    public void disconnectSignInAccount(final SocialSignInOperations social) {

    }

    /**
     * @return the securityOperations
     */
    public SecurityOperations getSecurityOperations() {
        return securityOperations;
    }

    /**
     * @param securityOperations the securityOperations to set
     */
    public void setSecurityOperations(SecurityOperations securityOperations) {
        this.securityOperations = securityOperations;
    }
}
