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
package org.encuestame.oauth.security;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecret;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.encuestame.persistence.domain.application.Application;

/**
 * Implementation for OAuth Spring Security {@link ConsumerDetails}.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 11:10:54 AM
 * @version $Id:$
 */
@SuppressWarnings("serial")
public class ApplicationConsumerDetails implements ConsumerDetails {

    /** Reference to {@link Application} . **/
    private Application application;

    /**
     * Constructor.
     * @param application {@link Application}.
     */
    public ApplicationConsumerDetails(Application application) {
        this.application = application;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.oauth.provider.ConsumerDetails#getConsumerName
     * ()
     */
    public String getConsumerName() {
        return application.getName();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.oauth.provider.ConsumerDetails#getConsumerKey
     * ()
     */
    public String getConsumerKey() {
        return application.getApiKey();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.oauth.provider.ConsumerDetails#
     * getSignatureSecret()
     */
    public SignatureSecret getSignatureSecret() {
        return new SharedConsumerSecret(this.application.getSecret());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.oauth.provider.ConsumerDetails#getAuthorities
     * ()
     */
    public List<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
