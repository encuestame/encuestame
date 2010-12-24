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
package org.encuestame.core.exception;

import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;

/**
 * Indicates no connection exists between a {@link UserAccount} and a service provider with the submitted access token.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 24, 2010 3:59:06 PM
 * @version $Id:$
 */
@SuppressWarnings("serial")
public final class EnMeNoSuchAccountConnectionException extends EnMeDomainNotFoundException {

    private String accessToken;

    public EnMeNoSuchAccountConnectionException(String accessToken) {
        super("invalid access token");
    }

    public String getAccessToken() {
        return accessToken;
    }
}