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
package org.encuestame.test.business.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.enums.EnMePermission;
import org.junit.Before;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    protected final String ADMINISTRATIVE_USER = "admin";

    /**
     * Before.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
       this.springSecurityLoggedUserAccount = createUserAccount(this.ADMINISTRATIVE_USER, createAccount());
       createPermission("ENCUESTAME_USER");
       createPermission("ENCUESTAME_ADMIN");
       createPermission("ENCUESTAME_OWNER");
       createPermission("ENCUESTAME_WRITE");
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
     * @param springSecurityLoggedUserAccount the secondary to set
     */
    public void setSpringSecurityLoggedUserAccount(final UserAccount springSecurityLoggedUserAccount) {
        this.springSecurityLoggedUserAccount = springSecurityLoggedUserAccount;
    }

    /**
     * set Authentication.
     * @param username
     * @param password
     */
    public void setAuthentication(final String username, final String password) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //Add permissions.
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_USER.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_ADMIN.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_WRITE.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_OWNER.name()));
        TestingAuthenticationToken token = new TestingAuthenticationToken(username, password, authorities);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
        //System.out.println("creando SecurityContextHolder "+SecurityContextHolder.getContext().getAuthentication());
        setUsernameLogged(this.springSecurityLoggedUserAccount.getUsername());
    }

    /**
     *
     * @param username
     * @return
     */
    public UserAccount quickLogin(String username) {
        UserAccount d = createUserAccount(username, createAccount());
        this.login(d);
        return d;
    }

    /**
     *
     * @param userAccount
     */
    public void login(UserAccount userAccount) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //Add permissions.
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_USER.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_ADMIN.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_EDITOR.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_PUBLISHER.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_WRITE.name()));
        authorities.add(new SimpleGrantedAuthority(EnMePermission.ENCUESTAME_OWNER.name()));
        TestingAuthenticationToken token = new TestingAuthenticationToken(userAccount.getUsername(), "12345", authorities);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
        //setUsernameLogged(this.springSecurityLoggedUserAccount.getUsername());
    }

    /**
     *
     */
    public UserAccount quickLogin() {
        return this.quickLogin("quick_user_"+ RandomStringUtils.randomAlphabetic(3));
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
