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

import org.encuestame.business.service.social.api.FacebookAPITemplate;
import org.encuestame.core.social.FacebookAPIOperations;
import org.encuestame.core.social.SocialAPIOperations;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.oauth.AccessGrant;
/**
 * Facebook. Social Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 5:57:35 PM
 */
public class FacebookSignInSocialSupport extends AbstractSocialSignInConnect<FacebookAPIOperations>{

    /**
     *
     * @param accountDao
     * @throws Exception
     */
    public FacebookSignInSocialSupport(final AccessGrant accessToken) throws Exception {
        super(accessToken);
    }


    @Override
    public SocialAPIOperations getAPISocialProvider() {
       return new FacebookAPITemplate(getAccessGrant().getAccessToken());
    }


    @Override
    public void disconnect(String accountId) {
        // TODO Auto-generated method stub
    }

    @Override
    public SocialProvider getProvider() {
        return SocialProvider.FACEBOOK;
    }


    @Override
    public void disconnect() {
        // TODO Auto-generated method stub

    }
}
