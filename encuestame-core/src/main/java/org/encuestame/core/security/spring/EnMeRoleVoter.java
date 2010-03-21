/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.security.spring;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.vote.RoleVoter;

/**
 * Service Manager Bean.
 * @author Picado, Juan juan@encuestame.org
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
     * anonymous Access Allowerd.
     */
    private boolean anonymousAccessAllowed = false;

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @param i_anonymousAccessAllowed i_anonymousAccessAllowed
     */
    public void setAnonymousAccessAllowed(boolean i_anonymousAccessAllowed) {
        anonymousAccessAllowed = i_anonymousAccessAllowed;
    }

    @Override
    public int vote(Authentication authentication, Object object,
            ConfigAttributeDefinition config) {
        int result = ACCESS_ABSTAIN;

        final Iterator iter = config.getConfigAttributes().iterator();
        GrantedAuthority[] authorities = authentication.getAuthorities();
        while (iter.hasNext()) {
            ConfigAttribute attribute = (ConfigAttribute) iter.next();
            //log.info("ConfigAttribute->"+attribute.getAttribute());
            if (this.supports(attribute)) {
                // always grant access to resources, marked with ROLE_ALWAYS
                if (ROLE_ALWAYS.equals(attribute.getAttribute())) {
                    return ACCESS_GRANTED;
                }
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (int i = 0; i < authorities.length; i++) {
                    if (!anonymousAccessAllowed
                            && ROLE_ANONYMOUS.equals(authorities[i]
                                    .getAuthority())) {
                        // skip checking for anonymous role
                        continue;
                    }

                    if (attribute.getAttribute().equals(
                            authorities[i].getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }

        return result;
    }

}
