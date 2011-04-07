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

import org.apache.log4j.Logger;
import org.encuestame.core.security.EnMeUserDetails;
import org.encuestame.persistence.domain.security.UserAccount;
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
        final EnMeUserDetails details = getSecurityDetails();
        if (details != null) {
            account = details.getUserAccount();
            log.debug("info logged user account: "+account);
            if (account != null) {
                log.debug("info logged user account: "+account.getUserEmail());
                log.debug("info logged user account: "+account.getUsername());
                log.debug("info logged user account: "+account.getUid());
            }
        }
        return account;
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
    public EnMeUserDetails getSecurityDetails(){
        EnMeUserDetails details = null;
        //log.debug("Authentication Object:{"+getSecCtx().getAuthentication());
        if(getSecCtx().getAuthentication() != null){
            if(getSecCtx().getAuthentication().getPrincipal() instanceof EnMeUserDetails){
                details =  (EnMeUserDetails) getSecCtx().getAuthentication().getPrincipal();
            }
        }
        return details;
    }

}
