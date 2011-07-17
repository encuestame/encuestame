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

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeNoSuchAccountConnectionException;
import org.encuestame.core.security.service.SocialUserService;
import org.encuestame.core.security.token.SocialAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Social Authentication Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since May 1, 2011
 */
public class SocialAccountAuthenticationProvider implements AuthenticationProvider{

    /**
     * Log.
     */
     private static final Log logger = LogFactory.getLog(SocialAccountAuthenticationProvider.class);

     @Autowired
     private SocialUserService socialUserService;

     /*
      * (non-Javadoc)
      * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
      */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (authentication instanceof SocialAuthenticationToken) {
            SocialAuthenticationToken response = (SocialAuthenticationToken) authentication;
            // handle the various possibilities
                // Lookup user details
                UserDetails userDetails;
                try {
                    userDetails = socialUserService.loadAccountConnection(response.getProfileId(), response.getProvider());
                    logger.debug("user details "+userDetails);
                } catch (EnMeNoSuchAccountConnectionException e) {
                    throw new BadCredentialsException(e.getMessage());
                }
            Authentication auth = createSuccessAuthentication(response);
            return auth;
        } else {
            return null;
        }
    }

    /**
     *
     * @param authentication
     * @param user
     * @return
     */
    protected Authentication createSuccessAuthentication(SocialAuthenticationToken authentication) {
        //TODO: in the future add more conditionals to block accounts.
        Object user = authentication.getPrincipal();
        final SocialAuthenticationToken result = new SocialAuthenticationToken(user, authentication.getAuthorities());
        result.setProfileId(authentication.getProfileId());
        result.setProvider(authentication.getProvider());
        if (logger.isDebugEnabled()) {
            logger.debug("createSuccessAuthentication "+result);
        }
        return result;
    }

    /**
     *
     */
    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (SocialAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * @return the socialUserService
     */
    public SocialUserService getSocialUserService() {
        return socialUserService;
    }

    /**
     * @param socialUserService the socialUserService to set
     */
    public void setSocialUserService(SocialUserService socialUserService) {
        this.socialUserService = socialUserService;
    }
}
