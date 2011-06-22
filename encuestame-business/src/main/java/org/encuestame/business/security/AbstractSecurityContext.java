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
package org.encuestame.business.security;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.security.details.EnMeUserAccountDetails;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Abstract Security Context.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 19, 2010 10:58:37 AM
 * @version $Id:$
 */
public abstract class AbstractSecurityContext {

    /** Log. **/
    protected Logger log = Logger.getLogger(this.getClass());


    /** Obtain {@link SecurityContext}.**/
    private SecurityContext securityContext;

    /**
     * Get Username of user client.
     * @return
     */
    public String getUserPrincipalUsername(){
        String username = "";
        if (getSecCtx().getAuthentication() != null) {
            username = getSecCtx().getAuthentication().getName();
        }
       return username;
    }

    /**
     * Get logged {@link UserAccount}.
     * @return {@link UserAccount}.
     */
    public UserAccount getUserAccountLogged(){
        UserAccount account = null;
        final EnMeUserAccountDetails details = getSecurityDetails();
        if (details != null) {
            account = details.getUserAccount();
            if (log.isDebugEnabled()) {
                log.debug("info logged user account: "+account);
                if (account != null) {
                    log.debug("info logged user account: "+account.getUserEmail());
                    log.debug("info logged user account: "+account.getUsername());
                    log.debug("info logged user account: "+account.getUid());
                }
            }
        }
        return account;
    }

    /**
     * Set Spring Authentication
     * @param username
     * @param password
     */
    public void setSpringSecurityAuthentication(
            final UserAccount account,
            final String password){
         this.setSpringSecurityAuthentication(account, password, Boolean.FALSE);
    }

    /**
     *
     * @param account
     * @param password
     * @param socialSignIn
     */
    public void setSpringSecurityAuthentication(
            final UserAccount account,
            final String password,
            final Boolean socialSignIn){
         log.info("Register login user: "+account.getUsername());
         //building granted authorities
         final Collection<GrantedAuthority> authorities = ConvertDomainsToSecurityContext.convertEnMePermission(account.getSecUserPermissions());
         //create user detail based on user account.
         final EnMeUserAccountDetails details = SecurityUtils.convertUserAccountToUserDetails(account, true);
         //set the social credentials permission.
         details.setSocialCredentials(socialSignIn);
         SecurityContextHolder.getContext().setAuthentication(
                 new UsernamePasswordAuthenticationToken(details, password, authorities));
         if (log.isInfoEnabled()) {
             log.info("Username "+account.getUsername() + " is logged at "+new Date() + " with social account?"+socialSignIn);
         }
    }

    /**
     * @return the secCtx
     */
    public SecurityContext getSecCtx() {
        return this.securityContext = SecurityContextHolder.getContext();
    }

    /**
     * Get Details.
     */
    public EnMeUserAccountDetails getSecurityDetails(){
        EnMeUserAccountDetails details = null;
        log.trace("Authentication Object:{"+getSecCtx().getAuthentication());
        if(getSecCtx().getAuthentication() != null){
            if(getSecCtx().getAuthentication().getPrincipal() instanceof EnMeUserAccountDetails){
                details =  (EnMeUserAccountDetails) getSecCtx().getAuthentication().getPrincipal();
            }
        }
        return details;
    }

}
