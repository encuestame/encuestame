package org.encuestame.core.security.spring;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EnMeUserDetails implements UserDetails, EnMeUserDetailsDataAccount {

    /**
     * Authorities.
     */
    private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    /**
     * Username.
     */
    private String username = null;

    /**
     * Password;
     */
    private String password = null;

    /**
     * Account Enabled.
     */
    private boolean enabled = true;

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
     * Serializaded.
     */
    private static final long serialVersionUID = -4232011056933663058L;

    /**
     * Twitter Account.
     */
    private String twitterAccount = null;

    /**
     * User Email.
     */
    private String userEmail;

    /**
     * Is Owner.
     */
    private boolean isOwner;

    /**
     * Complete Name.
     */
    private String completeName;


    public EnMeUserDetails(final String username,
            final String password,
            Collection<GrantedAuthority> authorities,
            final boolean accountNonExpired,
            final boolean credentialsNonExpired,
            final boolean enabled,
            final boolean accountNonLocked,
            final String twitterAccount,
            final boolean isOwner,
            final String completeName,
            final String email) {
        this.username = username;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        if(authorities == null){
           authorities = new ArrayList<GrantedAuthority>();
        }
        this.authorities = authorities;
        this.twitterAccount = twitterAccount;
        this.completeName = completeName;
        this.userEmail = email;
        this.isOwner = isOwner;
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
     * Twitter Account.
     */
    public String getTwitterAccount() {
        return this.twitterAccount;
    }

    /**
     * User Email.
     */
    public String getUserEmail() {
        return this.userEmail;
    }

    /**
     * Owner Account.
     */
    public boolean isOwnerAccount() {
        return this.isOwner;
    }
}
