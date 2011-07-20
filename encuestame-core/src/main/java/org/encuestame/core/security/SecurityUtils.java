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
import java.util.Date;

import org.apache.log4j.Logger;
import org.encuestame.core.security.details.EnMeSocialUserAccount;
import org.encuestame.core.security.details.EnMeUserAccountDetails;
import org.encuestame.core.security.token.EnMeSecurityToken;
import org.encuestame.core.security.token.SocialAuthenticationToken;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
     *
     * @param user
     * @return
     */
    public static EnMeSocialUserAccount convertUserAccountToUserDetails(final SocialAccount connection) {
        final UserAccount user = connection.getUserOwner();
        final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.addAll(ConvertDomainsToSecurityContext.convertEnMePermission(user.getSecUserPermissions()));
        final EnMeSocialUserAccount enMeSocialUserAccount = new EnMeSocialUserAccount(user.getUsername(),
                authorities,
                user.isUserStatus() == null ? false : user.isUserStatus(),
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                user.getCompleteName() == null ? "" : user.getCompleteName(), // complete name
                user.getUserEmail(), // user email
                user, connection.getAccounType(),
                connection.getSocialProfileId(),
                connection.getProfilePictureUrl());
        return enMeSocialUserAccount;
    }

    /**
     * Convert User Account to {@link EnMeUserAccountDetails}.
     * @param user
     * @param roleUserAuth
     * @return
     */
    public static EnMeUserAccountDetails convertUserAccountToUserDetails(
            final UserAccount user, final Boolean roleUserAuth) {
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

        // permissions
        if (roleUserAuth) {
            authorities.addAll(ConvertDomainsToSecurityContext.convertEnMePermission(user.getSecUserPermissions()));
            log.debug("EnMeUserDetails: Authorities size :"+authorities.size());
        }

         //creating user details
         final EnMeUserAccountDetails userDetails = new EnMeUserAccountDetails(
         user.getUsername(),
         user.getPassword(),
         authorities,
         user.isUserStatus() == null ? false : user.isUserStatus(),
         true, // account not expired
         true, // credentials not expired
         true, // account not locked
         user.getCompleteName() == null ? "" : user.getCompleteName(), // complete name
         user.getUserEmail(), // user email
         user
         );
         userDetails.setSocialCredentials(false);
         userDetails.setAccountNonExpired(true);
         userDetails.setAccountNonLocked(true);
         log.debug("EnMeUserDetails : "+userDetails.toString());
         return userDetails;
    }

    /**
     *
     * @param account
     * @param password
     * @param socialSignIn
     */
    public static void socialAuthentication(final SocialAccount accountConnection) {
        final UserAccount account = accountConnection.getUserOwner();
        log.info("Register SOCIAL LOGIN USER: " + account.getUsername());
        // building granted authorities
        final Collection<GrantedAuthority> authorities = ConvertDomainsToSecurityContext
                .convertEnMePermission(account.getSecUserPermissions());
        // create user detail based on user account.
        final EnMeSocialUserAccount details = SecurityUtils.convertUserAccountToUserDetails(accountConnection);
        // set the social credentials permission.
        details.setSocialCredentials(true);
        final SocialAuthenticationToken token = new SocialAuthenticationToken(details, authorities);
        token.setProfileId(accountConnection.getSocialProfileId());
        token.setProvider(accountConnection.getAccounType());
        //clear the context.
        SecurityContextHolder.clearContext();
        //set new authentication.
        SecurityContextHolder.getContext().setAuthentication(token);
        if (log.isInfoEnabled()) {
            log.info("Username " + account.getUsername() + " is logged at "
                    + new Date());
            log.debug("created EnMeSocialUserAccount" +details);
        }
    }

    /**
     * Authenticate {@link UserAccount}.
     * @param account {@link UserAccount}.
     */
    public static void authenticate(final UserAccount account){
        final EnMeUserAccountDetails details = SecurityUtils.convertUserAccountToUserDetails(account, true);
        final Collection<GrantedAuthority> authorities = ConvertDomainsToSecurityContext
        .convertEnMePermission(account.getSecUserPermissions());
        final EnMeSecurityToken securityToken = new EnMeSecurityToken(account);
         //clear the context.
        SecurityContextHolder.clearContext();
        //set new authentication.
        SecurityContextHolder.getContext().setAuthentication(securityToken);
        if (log.isInfoEnabled()) {
            log.info("Username " + account.getUsername() + " is logged at "
                    + new Date());
            log.debug("created EnMeUserAccountDetails" +details);
        }
    }

    /**
     * Check is Session is Expired.
     * @param authentication
     * @return
     */
    public static boolean checkIsSessionIsExpired(final Authentication authentication){
        boolean session = true;
        if(authentication != null){
            session = authentication.isAuthenticated();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                SimpleGrantedAuthority auth = (SimpleGrantedAuthority) authority;
                if(auth.getAuthority().toString() == EnMePermission.ENCUESTAME_USER.toString()){
                    session = false;
                    break;
                }
            }
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
