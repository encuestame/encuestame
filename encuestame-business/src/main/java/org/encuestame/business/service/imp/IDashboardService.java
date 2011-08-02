/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service.imp;

import java.util.List;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeGadgetNotFoundException;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.DashboardBean;

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
	List<Dashboard> getAllDashboards(final String username,
            final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;

	/**
	 * Get all dashboard by id and username.
	 * @param boardId
	 * @param username
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	Dashboard getAllDashboardbyId(final Long boardId, final String username) throws EnMeNoResultsFoundException;

	/**
	 * Retrieve favourites dashboards.
	 * @param userId
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Dashboard> retrieveFavouritesDashboards(final Long userId,
	        final Integer maxResults,
	        final Integer start);

	/**
	 * Get dashboard by id.
	 * @param boardId
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	Dashboard getDashboardById(final Long boardId) throws EnMeNoResultsFoundException;

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
	List<Gadget> searchGadgetbyKeyword(final String keyword,
            final Integer maxResults,
            final Integer start)throws EnMeExpcetion;

	/**
	 * Create dashboard.
	 * @param dashboardBean
	 * @param user
	 * @return
	 */
	Dashboard createDashboard(final DashboardBean dashboardBean, final UserAccount user);

	/**
	 * Add gadget on dashboard.
	 * @param boardId
	 * @param gadgetId
	 * @throws EnMeGadgetNotFoundException
	 */
	void addGadgetOnDashboard(final Long boardId, final Long gadgetId) throws EnMeGadgetNotFoundException;

	/**
	 * Remove gadget.
	 * @param gadgetId
	 * @throws EnMeGadgetNotFoundException
	 */
	void removeGadget(final Long gadgetId) throws EnMeGadgetNotFoundException;

	/**
	 * Get dashboards by keyword.
	 * @param keyword
	 * @param username
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Dashboard> getDashboardsbyKeyword(final String keyword, final String username,
			final Integer maxResults,
			final Integer start) throws EnMeExpcetion;

	/**
	 * Get gadgets by dashboard.
	 * @param dashboardId
	 * @param username
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	List<Gadget> getGadgetsbyDashboard(final Long dashboardId, final String username)throws EnMeNoResultsFoundException;

	/**
	 * Get gadget properties.
	 * @param gadgetId
	 * @return
	 * @throws EnMeGadgetNotFoundException
	 */
	List<GadgetProperties> getPropertiesbyGadget(final Long gadgetId) throws EnMeGadgetNotFoundException;
}
