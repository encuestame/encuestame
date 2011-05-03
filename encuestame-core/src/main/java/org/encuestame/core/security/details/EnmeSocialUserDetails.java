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
package org.encuestame.core.security.details;

import java.util.ArrayList;
import java.util.Collection;

import org.encuestame.persistence.domain.security.AccountConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since May 1, 2011
 */
public class EnmeSocialUserDetails implements UserDetails {


    private final AccountConnection accountConnection;

    private final UserAccount userAccount;

    /**
     * Authorities.
     */
    private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();


    public EnmeSocialUserDetails(AccountConnection accountConnection,
            UserAccount userAccount, Collection<GrantedAuthority> authorities) {
        super();
        this.accountConnection = accountConnection;
        this.userAccount = userAccount;
        this.authorities = authorities;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
         for (GrantedAuthority auth : authorities) {
             if(auth == null){
                 throw new IllegalArgumentException("Granted authorities not should be contains null items");
             }
         }
         return this.authorities;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
       return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EnmeSocialUserDetails [accountConnection=" + accountConnection
                + ", userAccount=" + userAccount + ", authorities="
                + authorities + "]";
    }
}
