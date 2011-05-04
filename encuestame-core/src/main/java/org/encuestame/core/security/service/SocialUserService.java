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
package org.encuestame.core.security.service;

import org.encuestame.core.exception.EnMeNoSuchAccountConnectionException;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Social account user service.
 * @author Picado, Juan juanATencuestame.org
 * @since May 2, 2011
 */
public interface SocialUserService {

    /**
     *
     * @param profileId
     * @param provider
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadAccountConnection(String profileId, final SocialProvider provider) throws EnMeNoSuchAccountConnectionException;
}
