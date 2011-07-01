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

import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.business.service.social.api.BuzzAPITemplate;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.social.BuzzAPIOperations;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.utils.oauth.AccessGrant;

/**
 * Google SignIn Support.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 */
public class GoogleSignInSocialService extends
        AbstractSocialSignInConnect<BuzzAPIOperations> {

    /**
     *
     * @param accessGrant
     * @param key
     * @throws Exception
     */

    public GoogleSignInSocialService(final AccessGrant accessToken,
            final SecurityOperations securityOperations) throws Exception {
        super(accessToken, securityOperations);
    }

    /**
     *
     */
    @Override
    public SocialProvider getProvider() {
        return SocialProvider.GOOGLE;
    }

    @Override
    public BuzzAPIOperations getAPISocialProvider() {
        return new BuzzAPITemplate(getAccessGrant().getAccessToken(),
                EnMePlaceHolderConfigurer.getProperty("google.api.key"));
    }

}
