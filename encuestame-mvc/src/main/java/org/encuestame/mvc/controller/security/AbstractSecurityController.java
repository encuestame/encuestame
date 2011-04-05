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
package org.encuestame.mvc.controller.security;

import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 14, 2010 8:44:12 PM
 * @version Id:
 */
public abstract class AbstractSecurityController extends AbstractBaseOperations{

    /**
     * {@link SecurityOperations}.
     */
    private SecurityOperations securityService;

    /**
     * @return the securityService
     */
    public SecurityOperations getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    @Autowired
    public void setSecurityService(final SecurityOperations securityService) {
        this.securityService = securityService;
    }
}
