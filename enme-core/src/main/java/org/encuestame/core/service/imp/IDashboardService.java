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

import java.util.List;

import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeGadgetNotFoundException;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;

/**
 * Dashboard Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 28, 2011
 */
public interface IDashboardService {

    /**
      * Get all dashboards.
      * @param username
      * @param maxResults
      * @param start
      * @return
      * @throws EnMeNoResultsFoundException
      */
    List<DashboardBean> getAllDashboards(final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Get all dashboard by id and username.
     * @param boardId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Dashboard getDashboardbyId(final Long boardId) throws EnMeNoResultsFoundException;

    /**
     * Mark as selected {@link Dashboard}.
     * @param dashBoardId dasboard id.
     * @throws EnMeNoResultsFoundException
     */
    Dashboard markAsSelectedDasboard(
            final  Long dashBoardId)
            throws EnMeNoResultsFoundException;

    /**
     * Retrieve favourites dashboards.
     * @param userId
     * @param maxResults
     * @param start
     * @return
     */
    List<DashboardBean> retrieveFavouritesDashboards(final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Get gadget by id.
     * @param gadgetId
     * @return
     */
    Gadget getGadgetById(final Long gadgetId);

    /**
     * Search gadgets by keyword.
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<GadgetBean> searchGadgetbyKeyword(final String keyword,
            final Integer maxResults,
            final Integer start)throws EnMeExpcetion;

    /**
     * Create dashboard.
     * @param dashboardBean
     * @param user
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Dashboard createDashboard(final DashboardBean dashboardBean) throws EnMeNoResultsFoundException;

    /**
     * Remove gadget.
     * @param gadgetId
     * @throws EnMeGadgetNotFoundException
     */
    void removeGadget(final Long gadgetId, final Long dashboardId) throws EnMeNoResultsFoundException;

    /**
     * Get dashboards by keyword.
     * @param keyword
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<Dashboard> getDashboardsbyKeyword(final String keyword,
            final Integer maxResults,
            final Integer start) throws EnMeExpcetion;

    /**
     * Get gadgets by dashboard.
     * @param dashboardId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<Gadget> getGadgetsbyDashboard(final Long dashboardId)throws EnMeNoResultsFoundException;

    /**
     * Get gadget properties.
     * @param gadgetId
     * @return
     * @throws EnMeGadgetNotFoundException
     */
    List<GadgetProperties> getPropertiesbyGadget(final Long gadgetId) throws EnMeGadgetNotFoundException;

    /**
     * Get all gadgets available.
     * @return
     */
    List<GadgetBean> getAllGadgetsAvailable(final Long boardId) throws EnMeNoResultsFoundException;


    /**
     * Add gadget on dashboard.
     * @param boardId
     * @param gadgetId
     * @throws EnMeNoResultsFoundException
     */
    Gadget addGadgetOnDashboard(final Long boardId, final String gadgetId) throws EnMeNoResultsFoundException;

    /**
     * Create gadget property.
     * @param gadget
     * @param gadgetPropName
     * @param gadgetPropValue
     * @return
     * @throws EnMeNoResultsFoundException
     */
    GadgetProperties createProperty(
            final Gadget gadget,
            final String gadgetPropName,
            final String gadgetPropValue) throws EnMeNoResultsFoundException;

    /**

     * Move gadget
     * @param gadgetId
     * @param position
     * @param column
     * @return
     * @throws EnMeNoResultsFoundException
     */

    void moveGadget(final Long gadgetId, final Long boardId, final Integer position, final Integer column) throws EnMeNoResultsFoundException;

    /**
     * Update dashboard.
     * @param boardId
     * @param boardBean
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Dashboard updateDashboard(final DashboardBean boardBean) throws EnMeNoResultsFoundException;

    /**
     * Update a dashboard.
     * @param dashboardBean
     * @return
     * @throws EnMeNoResultsFoundException
     */
    DashboardBean updateDasbboard(final DashboardBean dashboardBean) throws EnMeNoResultsFoundException;

    /**
     * Delete a dashboard.
     * @param dashboardBean
     * @return
     * @throws EnMeNoResultsFoundException
     */
     void deleteDasbboard(final DashboardBean dashboardBean) throws EnMeNoResultsFoundException;
}
