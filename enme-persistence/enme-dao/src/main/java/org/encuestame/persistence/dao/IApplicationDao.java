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
package org.encuestame.persistence.dao;

import org.encuestame.persistence.domain.application.Application;
import org.encuestame.persistence.domain.application.ApplicationConnection;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.util.exception.EnMeNoResultsFoundException;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 6:05:34 PM
 * @version Id:
 */
public interface IApplicationDao extends IBaseDao {

    /**
     * Get Application By Key.
     * @param key
     * @return
     * @throws Exception
     */
    Application getApplicationByKey(final String key) throws Exception;

    /**
     * Search Connection by Application Id and Account Id.
     * @param account
     * @param application
     * @return
     */
    ApplicationConnection searchConnectionByAppIdAndUserId(
            final UserAccount account, final Application application);

    /**
     * Create Application Connection.
     * @param accountId user account id
     * @param apiKey key application.
     * @return
     * @throws Exception
     */
    ApplicationConnection
           connectApplication(final Long accountId, final String apiKey, final UserAccount userAccount) throws Exception;


    /**
     * Find App Connection by Access Token.
     * @throws EnMeNoResultsFoundException
     */
    ApplicationConnection findAppConnection(final String accessToken) throws EnMeNoResultsFoundException;

}
