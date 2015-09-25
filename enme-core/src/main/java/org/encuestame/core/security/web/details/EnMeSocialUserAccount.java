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

import java.util.Collection;

import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.social.SocialProvider;
import org.springframework.security.core.GrantedAuthority;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since May 2, 2011
 */
public class EnMeSocialUserAccount extends EnMeUserAccountDetails{


    private SocialProvider socialProvider;

    private String profileId;

    private String profileUrlPicture;

    /**
     * @param username
     * @param password
     * @param authorities
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param enabled
     * @param accountNonLocked
     * @param completeName
     * @param email
     * @param account
     * @param socialProvider
     * @param profileId
     * @param profileUrlPicture
     */
    public EnMeSocialUserAccount(String username,
            Collection<GrantedAuthority> authorities,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean enabled, boolean accountNonLocked, String completeName,
            String email, UserAccount account, SocialProvider socialProvider,
            String profileId, String profileUrlPicture) {
        super(username, null, authorities, accountNonExpired,
                credentialsNonExpired, enabled, accountNonLocked, completeName,
                email, account);
        this.socialProvider = socialProvider;
        this.profileId = profileId;
        this.profileUrlPicture = profileUrlPicture;
    }

    /**
     * @return the socialProvider
     */
    public SocialProvider getSocialProvider() {
        return socialProvider;
    }


    /**
     * @param socialProvider the socialProvider to set
     */
    public void setSocialProvider(SocialProvider socialProvider) {
        this.socialProvider = socialProvider;
    }


    /**
     * @return the profileId
     */
    public String getProfileId() {
        return profileId;
    }


    /**
     * @param profileId the profileId to set
     */
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }


    /**
     * @return the profileUrlPicture
     */
    public String getProfileUrlPicture() {
        return profileUrlPicture;
    }


    /**
     * @param profileUrlPicture the profileUrlPicture to set
     */
    public void setProfileUrlPicture(String profileUrlPicture) {
        this.profileUrlPicture = profileUrlPicture;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EnMeSocialUserAccount [socialProvider=" + socialProvider
                + ", profileId=" + profileId + ", profileUrlPicture="
                + profileUrlPicture + ", getAuthorities()=" + getAuthorities()
                + ", getPassword()=" + getPassword() + ", getUsername()="
                + getUsername() + ", isAccountNonExpired()="
                + isAccountNonExpired() + ", isAccountNonLocked()="
                + isAccountNonLocked() + ", isCredentialsNonExpired()="
                + isCredentialsNonExpired() + ", isEnabled()=" + isEnabled()
                + ", getCompleteName()=" + getCompleteName()
                + ", getUserEmail()=" + getUserEmail() + ", getUserAccount()="
                + getUserAccount() + ", isSocialCredentials()="
                + isSocialCredentials() + "]";
    }
}
