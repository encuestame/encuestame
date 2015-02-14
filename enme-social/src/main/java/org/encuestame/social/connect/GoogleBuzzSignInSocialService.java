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

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.social.api.templates.GoogleBuzzAPITemplate;
import org.encuestame.social.api.operation.GoogleContactsAPIOperations;
import org.encuestame.social.connect.service.ConnectOperations;
import org.encuestame.utils.oauth.AccessGrant;
import org.encuestame.utils.social.SocialProvider;

/**
 * Google SignIn Support.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 */
public class GoogleBuzzSignInSocialService extends AbstractSocialSignInConnect<GoogleContactsAPIOperations> {

    /**
     * Constructor.
     * @param accessGrant
     * @param key
     * @throws Exception
     */

    public GoogleBuzzSignInSocialService(final AccessGrant accessToken,
            final ConnectOperations securityOperations) throws Exception {
        super(accessToken, securityOperations);
    }

    /**
     * Google buzz provider.
     */
    @Override
    public SocialProvider getProvider() {
        return SocialProvider.GOOGLE_BUZZ;
    }

    /**
     *
     */
    @Override
    public GoogleBuzzAPITemplate getAPISocialProvider() {
        return new GoogleBuzzAPITemplate(getAccessGrant().getAccessToken(),
                EnMePlaceHolderConfigurer.getProperty("google.api.key"));
    }

}
