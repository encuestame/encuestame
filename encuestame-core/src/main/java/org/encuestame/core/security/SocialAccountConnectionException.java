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

import org.springframework.security.core.AuthenticationException;

/**
 * Social account connection exception.
 * @author Picado, Juan juanATencuestame.org
 * @since May 2, 2011
 */
public class SocialAccountConnectionException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -1761157686032683893L;

    public SocialAccountConnectionException(String msg) {
        super(msg);
    }

    public SocialAccountConnectionException(String msg, Throwable t) {
        super(msg, t);
    }

}
