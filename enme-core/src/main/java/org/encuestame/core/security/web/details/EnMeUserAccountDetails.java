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
package org.encuestame.core.security.web.details;

import java.util.ArrayList;
import java.util.Collection;

import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.core.GrantedAuthority;

/**
 * User account details.
 * @author Picado, Juan juanATencuestame.org
 * @since May 2, 2011
 */
public class EnMeUserAccountDetails implements EnMeUserAccount {

    /**
     * Authorities.
     */
    private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    /**
     * Username.
     */
    private String username = null;

    /**
     * {@link UserAccount}.
     */
    private UserAccount userAccount;

    /**
     * Password;
     */
    private String password = null;

    /**
     * Account Enabled.
     */
    private boolean enabled = true;
    
    /**
     * Default language.
     */
    private String language = "en_US";

    /**
     * Account Non Expired.
     */
    private boolean accountNonExpired = true;

    /**
     * Account Non Locked.
     */
    private boolean accountNonLocked = true;

    /**
     * Credentials Non Expired.
     */
    private boolean credentialsNonExpired = true;

    /**
     * This property should be used if user logged with social account.
     */
    private boolean socialCredentials = false;

    /**
     * Serializaded.
     */
    private static final long serialVersionUID = -4232011056933663058L;

    /**
     * User Email.
     */
    private String userEmail;

    /**
     * Complete Name.
     */
    private String completeName;

    /**
     * Default constructor.
     * @param username
     * @param password
     * @param authorities
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param enabled
     * @param accountNonLocked
     * @param isOwner
     * @param completeName
     * @param email
     */
    public EnMeUserAccountDetails(final String username,
            final String password,
            Collection<GrantedAuthority> authorities,
            final boolean accountNonExpired,
            final boolean credentialsNonExpired,
            final boolean enabled,
            final boolean accountNonLocked,
            final String completeName,
            final String email,
            final UserAccount account) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        if(authorities == null){
           authorities = new ArrayList<GrantedAuthority>();
        }
        this.authorities = authorities;
        this.completeName = completeName;
        this.userEmail = email;
        this.userAccount = account;
    }

    /**
     * Authorities
     */
    public Collection<GrantedAuthority> getAuthorities() {
        for (GrantedAuthority auth : authorities) {
            if(auth == null){
                throw new IllegalArgumentException("Granted authorities not should be contains null items");
            }
        }
        return this.authorities;
    }

    /**
     * Password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Is Account Non Expired.
     */
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * Is Account Non  Locked.
     */
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * Is Credentials Non Expired.
     */
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * Is Enabled.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Complete Name.
     */
    public String getCompleteName() {
        return this.completeName;
    }

    /**
     * User Email.
     */
    public String getUserEmail() {
        return this.userEmail;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param accountNonExpired the accountNonExpired to set
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * @param accountNonLocked the accountNonLocked to set
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * @param credentialsNonExpired the credentialsNonExpired to set
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @param completeName the completeName to set
     */
    public void setCompleteName(final String completeName) {
        this.completeName = completeName;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.security.EnMeUserDetailsDataAccount#getUserAccount()
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return the socialCredentials
     */
    public boolean isSocialCredentials() {
        return socialCredentials;
    }

    /**
     * @param socialCredentials the socialCredentials to set
     */
    public void setSocialCredentials(boolean socialCredentials) {
        this.socialCredentials = socialCredentials;
    }       

    /**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EnMeUserDetails [authorities=" + authorities + ", username="
                + username + ", userAccount=" + userAccount + ", password="
                + "[PROTECTED]" + ", enabled=" + enabled + ", accountNonExpired="
                + accountNonExpired + ", accountNonLocked=" + accountNonLocked
                + ", credentialsNonExpired=" + credentialsNonExpired
                + ", socialCredentials=" + socialCredentials + ", userEmail="
                + userEmail + ", completeName=" + completeName + "]";
    }
}
