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
package org.encuestame.core.security;

import org.apache.log4j.Logger;
import org.encuestame.core.security.service.EnMeSocialAccountUserService;
import org.encuestame.core.security.token.EnMeSecurityToken;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * {@link UserAccount} Authentication Provider.
 * <p>
 * This provider could be used to re-login and in signup process.
 * </p>
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 17, 2011
 */
public class EnMeUserAccountProvider implements AuthenticationProvider {

    /*
     * Log.
     */
    private Logger log = Logger.getLogger(EnMeSocialAccountUserService.class);

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
         if (authentication instanceof EnMeSecurityToken) {
             return authentication;
         } else {
             return null;
         }
    }

    @Override
    public boolean supports(Class<?> authentication) {
         return (EnMeSecurityToken.class.isAssignableFrom(authentication));
    }
}
