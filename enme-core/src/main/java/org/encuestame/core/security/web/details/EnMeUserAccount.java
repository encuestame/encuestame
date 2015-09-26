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

import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * EnMe UserAccount.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public interface EnMeUserAccount extends UserDetails{

    /**
     * Get User Email.
     * @return email
     */
    String getUserEmail();

    /**
     * Complete Name.
     */
    String getCompleteName();


    /**
     * Get {@link UserAccount}.
     * @return
     */
    UserAccount getUserAccount();

    /**
     *
     * @return
     */
    boolean isSocialCredentials();

}
