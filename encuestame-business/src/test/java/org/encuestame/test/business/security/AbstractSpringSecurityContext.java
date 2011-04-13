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
package org.encuestame.test.business.security;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.junit.Before;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Abstract Security Base Test.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 30, 2010 9:03:50 PM
 */
public abstract class AbstractSpringSecurityContext extends AbstractServiceBase {

    /**
     * {@link UserAccount}.
     */
    public UserAccount springSecurityLoggedUserAccount;

    /**
     * Username of User Logged.
     */
    private String usernameLogged;

    /**
     * Administrative user.
     */
    private final String ADMINISTRATIVE_USER = "admin";

    /**
     * Before.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
       this.springSecurityLoggedUserAccount = createUserAccount(this.ADMINISTRATIVE_USER, createAccount());
       createPermission("ENCUESTAME_USER");
       createPermission("ENCUESTAME_ADMIN");
       //TODO:  maybe we need more specific here.
       setAuthentication(this.springSecurityLoggedUserAccount.getUsername(), "12345");
    }


    /**
     * @return the secondary
     */
    public UserAccount getSpringSecurityLoggedUserAccount() {
        return springSecurityLoggedUserAccount;
    }

    /**
     * @param secondary the secondary to set
     */
    public void setSpringSecurityLoggedUserAccount(final UserAccount springSecurityLoggedUserAccount) {
        this.springSecurityLoggedUserAccount = springSecurityLoggedUserAccount;
    }

    /**
     * set Authentication.
     * @param username
     * @param password
     */
    public void setAuthentication(final String username, final String password){
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //Add permissions.
        authorities.add(new GrantedAuthorityImpl(EnMePermission.ENCUESTAME_USER.name()));
        authorities.add(new GrantedAuthorityImpl(EnMePermission.ENCUESTAME_ADMIN.name()));
        TestingAuthenticationToken token = new TestingAuthenticationToken(username, password, authorities);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
        //System.out.println("creando SecurityContextHolder "+SecurityContextHolder.getContext().getAuthentication());
        setUsernameLogged(this.springSecurityLoggedUserAccount.getUsername());
    }

    /**
     * Get {@link Authentication}.
     * @return
     */
    public Authentication getAuthentication() {
        log.debug("Security "+ SecurityContextHolder.getContext().getAuthentication());
        return  SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * @return the usernameLogged
     */
    public String getUsernameLogged() {
        return usernameLogged;
    }


    /**
     * @param usernameLogged the usernameLogged to set
     */
    public void setUsernameLogged(String usernameLogged) {
        this.usernameLogged = usernameLogged;
    }
}
