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
package org.encuestame.core.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class ConvertDomainsToSecurityContext {

     private static Log log = LogFactory.getLog(ConvertDomainsToSecurityContext.class);

     /**
      * Convert {@link SecPermission} to {@link GrantedAuthority}.
      * @param permissions
      * @return
      */
     public static final  List<GrantedAuthority> convertEnMePermission(final Set<SecPermission> permissions){
            final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            for (SecPermission secPermission : permissions) {
                authorities.add(new GrantedAuthorityImpl(secPermission.getPermission()));
            }
            log.info("list granted "+authorities.size());
            return authorities;
        }
}
