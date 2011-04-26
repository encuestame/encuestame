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

import org.encuestame.business.service.social.api.FacebookAPITemplate;
import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.social.SocialAccountProvider;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AuthorizedRequestToken;
import org.encuestame.utils.oauth.OAuth1Token;
import org.springframework.beans.factory.annotation.Value;
/**
 * Facebook. Social Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 */
public class FacebookSocialService implements IFacebookSocialService{


    /**
     * Social Account Provider;
     */
    private SocialAccountProvider parameters = new SocialAccountProvider();


    /**
     * Consumer Key.
     */
    public @Value("${facebook.api.id}") Long apiId;

    /**
     * Consumer Secret.
     */
    public @Value("${facebook.api.secret}") String secret;

    /**
     * Authorize Url.
     */
    public @Value("${facebook.api.key}") String key;


    /**
     *
     * @param socialProviderName
     */
    public FacebookSocialService() {
        this.loadParameters();
    }

    /**
     * Load Parameters.
     */
    private void loadParameters(){
        this.parameters.setAppId(this.apiId);
        this.parameters.setApiKey(this.key);
        this.parameters.setSecret(this.secret);
    }


    public boolean isConnected(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    public void disconnect(Long accountId) {
        // TODO Auto-generated method stub

    }

    public String getApiKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public OAuth1Token fetchNewRequestToken(String callbackUrl) {
        // TODO Auto-generated method stub
        return null;
    }

    public String buildAuthorizeUrl(String requestToken) {
        // TODO Auto-generated method stub
        return null;
    }

    public void connect(Long accountId, AuthorizedRequestToken requestToken)
            throws EnMeExistPreviousConnectionException {
        // TODO Auto-generated method stub

    }

    public UserAccount findAccountByConnection(String accessToken)
            throws EnMeNoResultsFoundException {
        // TODO Auto-generated method stub
        return null;
    }

//    @Override
//    SocialAccountProvider getParameters() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    SocialAccountProvider setParameters(SocialAccountProvider accountProvider) {
//        // TODO Auto-generated method stub
//        return null;
//    }

}
