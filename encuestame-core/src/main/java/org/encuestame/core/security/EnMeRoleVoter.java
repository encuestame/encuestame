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
package org.encuestame.core.security;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Encuestame Role Voter for Spring Security.
 * @author Picado, Juan juanATencuestame.org
 * @since 08/05/2009 13:17:14
 * @version $Id$
 */
public class EnMeRoleVoter extends RoleVoter {

    /**
     * ROLE ALWAYS.
     */
    public static final String ROLE_ALWAYS = "ENCUESTAME_ALWAYS";
    /**
     * ROLE_ANONYMOUS.
     */
    public static final String ROLE_ANONYMOUS = "ENCUESTAME_ANONYMOUS";

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     */
    public int vote(Authentication authentication, Object object,
            Collection<ConfigAttribute> attributes) {
        int result = ACCESS_GRANTED;
        final Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        log.debug("Authoritiez size "+authorities.size());
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                // Attempt to find a matching granted authority
                log.debug("Attribute" +attribute.getAttribute());
                for (GrantedAuthority authority : authorities) {
                    log.debug("authority.getAuthority())"+authority.getAuthority());
                    if (attribute.getAttribute().equals(
                            authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        log.debug("Result "+result);
        return result;
    }

}
