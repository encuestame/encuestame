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
package org.encuestame.core.service.imp;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateClasificatedEnum;
import org.encuestame.utils.web.notification.UtilNotification;

/**
 * Implementation for stream operations.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since 12/08/2011
 */
public interface StreamOperations {

    /**
     *
     * @param limit
     * @param request
     * @throws EnMeNoResultsFoundException
     */
    List<UtilNotification> retrieveLastNotifications(final Integer limit,
            final HttpServletRequest request)
            throws EnMeNoResultsFoundException;

    /**
     *
     * @param limit
     * @param onlyReaded
     * @param request
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UtilNotification> retrieveLastNotifications(final Integer limit,
            final Boolean onlyReaded, final HttpServletRequest request)
            throws EnMeNoResultsFoundException;

    /**
     *
     * @param limit
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UtilNotification> loadNotificationByUserAndLimit(final Integer limit,
            final Integer start, final Boolean onlyUnread,
            final HttpServletRequest request)
            throws EnMeNoResultsFoundException;

    /**
     *
     * @param utilNotifications
     * @return
     */
    HashMap<DateClasificatedEnum, List<UtilNotification>> classifyNotificationList(
            final List<UtilNotification> utilNotifications);
}
