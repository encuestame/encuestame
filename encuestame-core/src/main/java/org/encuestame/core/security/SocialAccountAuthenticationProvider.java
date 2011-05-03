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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.security.details.EnmeSocialUserDetails;
import org.encuestame.core.security.details.SocialAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Social Authentication Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since May 1, 2011
 */
public class SocialAccountAuthenticationProvider implements AuthenticationProvider{

     private static final Log logger = LogFactory.getLog(SocialAccountAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                    "+authentication);
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        UserDetails user = new  EnmeSocialUserDetails(null, null, authentication.getAuthorities());
        logger.debug("@@"+authentication.getAuthorities().size());
        logger.debug("@@"+authentication.getName());
        logger.debug("@@"+authentication.getClass());
        logger.debug("@@"+authentication.getDetails());
        logger.debug("@@"+authentication.getPrincipal());
        logger.debug("@@@@@@@@@@@@@@@ social user "+user.toString());
        return createSuccessAuthentication(user);
    }

    protected Authentication createSuccessAuthentication(UserDetails user) {
        SocialAuthenticationToken result = new SocialAuthenticationToken(user, user.getAuthorities());
        return result;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (SocialAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
