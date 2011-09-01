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
package org.encuestame.social.connect;

import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.social.api.FacebookAPITemplate;
import org.encuestame.social.api.support.FacebookAPIOperations;
import org.encuestame.social.api.support.SocialAPIOperations;
import org.encuestame.social.connect.service.ConnectOperations;
import org.encuestame.utils.oauth.AccessGrant;

/**
 * Facebook. Social Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 */
public class FacebookSignInSocialSupport extends AbstractSocialSignInConnect<FacebookAPIOperations> {

    /**
     *
     * @param accountDao
     * @throws Exception
     */
    public FacebookSignInSocialSupport(
            final AccessGrant accessToken,
            final ConnectOperations securityOperations)
            throws Exception {
        super(accessToken, securityOperations);
    }

    @Override
    public SocialAPIOperations getAPISocialProvider() {
        return new FacebookAPITemplate(getAccessGrant().getAccessToken());
    }

    @Override
    public SocialProvider getProvider() {
        return SocialProvider.FACEBOOK;
    }
}
