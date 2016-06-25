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
package org.encuestame.business.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.security.web.SecurityUtils;
import org.encuestame.core.security.web.details.EnMeUserAccountDetails;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.core.util.WidgetUtil;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.util.exception.EnMeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Abstract Security Context.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 19, 2010 10:58:37 AM
 * @version $Id:$
 */
public abstract class AbstractSecurityContext {

    /** Log. **/
    private Log log = LogFactory.getLog(this.getClass());


    /** Obtain {@link SecurityContext}.**/
    private SecurityContext securityContext;

    /**
     * Get Username of user client.
     * @return
     */
    public String getUserPrincipalUsername() {
        String username = "";
        if (this.isAuthentication()) {
            username = getSecCtx().getAuthentication().getName();
        }
       return username;
    }

    /**
     * Check if the authtentication is present and the user is not anonymous
     * @return
     */
    public Boolean isWellAuthenticated(){
        return this.isAuthentication() && !SecurityUtils.checkIsSessionIsAnonymousUser(getSecCtx().getAuthentication());
    }

    /**
     * Check of the Authtentication is present in the session
     * @return
     */
    public Boolean isAuthentication(){
        return getSecCtx().getAuthentication() != null;
    }

    /**
     * Get logged {@link UserAccount}.
     * @return {@link UserAccount}.
     */
    public UserAccount getUserAccountonSecurityContext(){
        UserAccount account = null;
        final EnMeUserAccountDetails details = getSecurityDetails();
        if (details != null) {
            account = details.getUserAccount();
            if (log.isDebugEnabled()) {
                log.debug("info logged user account: "+account);
                if (account != null) {
                    log.debug("info logged user account: "+ account.getUserEmail());
                    log.debug("info logged user account: "+ account.getUsername());
                    log.debug("info logged user account: "+ account.getUid());
                }
            }
        }
        return account;
    }

    /**
     * Set Spring Authentication
     * @param account
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
         log.info("Register login user: "+ account.getUsername());
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
        //log.debug("SecurityContextHolder.getContext();" + SecurityContextHolder.getContext());
        return this.securityContext = SecurityContextHolder.getContext();
    }

    /**
     * Get Details.
     */
    public EnMeUserAccountDetails getSecurityDetails(){
        EnMeUserAccountDetails details = null;
        log.trace("Authentication Object:{"+getSecCtx().getAuthentication());
        if (this.isWellAuthenticated()) {
            if(getSecCtx().getAuthentication().getPrincipal() instanceof EnMeUserAccountDetails){
                details =  (EnMeUserAccountDetails) getSecCtx().getAuthentication().getPrincipal();
            }
        }
        return details;
    }

    /**
     * Check ip in black list.
     * @param ip
     * @return
     */
    public Boolean checkIPinBlackList(final String ip){
        log.debug("checking ip in blackList --->" + ip);
        Boolean bannedIp = Boolean.FALSE;
        try {
            if(ip!=null){
                final List<String> blackList = WidgetUtil.getBlackListIP("blacklist.inc");
                for (String ipItem : blackList) {
                    if(ipItem.equals(ip)){
                        bannedIp = Boolean.TRUE;
                    }
                }
            }
        } catch (EnMeException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }
        return bannedIp;
    }
}
