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
package org.encuestame.core.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 31, 2010 1:56:03 AM
 * @version Id:
 */
public class SecurityUtils {

    /*
     * Log.
     */
    private static Logger log = Logger.getLogger(SecurityUtils.class);

    /**
     * Convert User Account to {@link EnMeUserDetails}.
     * @param user
     * @param roleUserAuth
     * @return
     */
    public static EnMeUserDetails convertUserAccount(final UserAccount user, final Boolean roleUserAuth){
          //log.debug("convertToUserDetails username "+user.getUsername());
        final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // search if authorities if the group are activated
       /* if (this.roleGroupAuth) {
            // search groups of the user
            final Set<SecGroup> groups = user.getSecGroups();
            for (final SecGroup secGroups : groups) {
                authorities.addAll(ConvertDomainsToSecurityContext.convertEnMePermission(secGroups.getSecPermissions()));
            }
        }*/
        // sec permissions
        if (roleUserAuth) {
            authorities.addAll(ConvertDomainsToSecurityContext.convertEnMePermission(user.getSecUserPermissions()));
        }

         //creating user details
         final EnMeUserDetails userDetails = new EnMeUserDetails(
         user.getUsername(),
         user.getPassword(),
         authorities,
         user.isUserStatus() == null ? false : user.isUserStatus(),
         true, // account not expired
         true, // credentials not expired
         true, // account not locked
         user.getUserTwitterAccount() == null ? "" : user.getUserTwitterAccount(), //twitter account
         user.getCompleteName() == null ? "" : user.getCompleteName(), // complete name
         user.getUserEmail() // user email
         );
         userDetails.setAccountNonExpired(true);
         userDetails.setAccountNonLocked(true);
         //log.debug("user details "+userDetails.getPassword());
         //log.debug("user details "+userDetails.getPassword());
         //log.debug("user details "+userDetails.getAuthorities());
         //log.debug("user details "+userDetails.getUserEmail());
         return userDetails;
    }

    /**
     * Check is Session is Expired.
     * @param authentication
     * @return
     */
    public static boolean checkIsSessionIsExpired(final Authentication authentication){
        boolean session = false;
        if(authentication != null){
            session = authentication.isAuthenticated();
            log.debug("checkIsSessionIsExpired NAME "+authentication.getName());
            log.debug("checkIsSessionIsExpired CREDENTAISL "+authentication.getCredentials());
            log.debug("checkIsSessionIsExpired DETAILS "+authentication.getDetails());
        }
        log.debug("checkIsSessionIsExpired->"+session);
        return session;
    }

    /**
     * Check is Session is Expired.
     * @param authentication
     * @return
     */
    public static boolean checkIsSessionIsAnonymousUser(final Authentication authentication){
        boolean anonymous = false;
        if(authentication != null){
            if("anonymousUser".equals(authentication.getName())){
                anonymous = true;
            }
            log.debug("checkIsSessionIsExpired->"+anonymous);
        }
        return anonymous;
    }
}
