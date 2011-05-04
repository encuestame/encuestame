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

import java.util.Collection;

import org.encuestame.persistence.domain.social.SocialProvider;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Description.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since May 1, 2011
 */
public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    /**
      *
      */
    private final Object principal;

    private String profileId;

    private SocialProvider provider;

    /**
     *
     */
    private static final long serialVersionUID = 7803686361261212911L;

    public SocialAuthenticationToken(
            Object principal,
            Collection<? extends GrantedAuthority> arg0) {
        super(arg0);
        this.principal = principal;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * @return the profileId
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * @param profileId
     *            the profileId to set
     */
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    /**
     * @return the provider
     */
    public SocialProvider getProvider() {
        return provider;
    }

    /**
     * @param provider
     *            the provider to set
     */
    public void setProvider(SocialProvider provider) {
        this.provider = provider;
    }
}
