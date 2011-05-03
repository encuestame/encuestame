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
import org.encuestame.core.exception.EnMeNoSuchAccountConnectionException;
import org.encuestame.core.security.details.SocialAuthenticationToken;
import org.encuestame.core.security.service.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

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
            logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                    "+authentication);
            logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            logger.debug("@@"+authentication.getAuthorities().size()); //size authorties.
            logger.debug("@@"+authentication.getName()); //username
            logger.debug("@@"+authentication.getClass()); //class
            logger.debug("@@"+authentication.getDetails()); //null
            /*
             *   @@EnMeUserDetails [authorities=[ENCUESTAME_EDITOR, ENCUESTAME_PUBLISHER, ENCUESTAME_ADMIN, ENCUESTAME_OWNER, ENCUESTAME_USER],
             *    username=juancarlospicado, userAccount=UserAccount [uid=119, completeName=,
             *    userEmail=juanpicado19@gmail.com, lastTimeLogged=2011-05-02 14:45:41.369,
             *    lastIpLogged=null, userProfilePicture=null, enabled=true], password=[PROTECTED], enabled=true,
             *    accountNonExpired=true, accountNonLocked=true, credentialsNonExpired=true, socialCredentials=true,
             *     userEmail=juanpicado19@gmail.com, completeName=]
             */
            logger.debug("@@"+authentication.getPrincipal());
            return createSuccessAuthentication(authentication, (UserDetails) authentication.getPrincipal());
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
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        SocialAuthenticationToken result = new SocialAuthenticationToken(user, user.getAuthorities());
        logger.info("createSuccessAuthentication "+result);
        return result;
    }

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
