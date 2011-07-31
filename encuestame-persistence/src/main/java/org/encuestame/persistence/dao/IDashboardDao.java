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
package org.encuestame.persistence.dao;

import java.util.List;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;

/**
 * Dashboard Interface.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since July 27, 2011
 */
public interface IDashboardDao extends IBaseDao {

	/**
	 * Get dashboard by id.
	 * @param boardId
	 * @return
	 */
	Dashboard getDashboardbyId(final Long boardId);

	/**
	 * Retrieve dashboards by id and user.
	 * @param userBoard
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Dashboard> retrieveDashboards(final Long userBoard, final Integer maxResults,
	        final Integer start);

	/**
	 * Retrieve favorites dashboards.
	 * @param userId
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Dashboard> retrieveFavouritesDashboards(
	            final Long userId,
	            final Integer maxResults,
	            final Integer start);

	/**
	 * Get gadget by id.
	 * @param gadgetId
	 * @return
	 */
	Gadget getGadgetbyId(final Long gadgetId);

	/**
	 * Get gadget by keyword.
	 * @param keyword
	 * @param maxResults
	 * @param start
	 * @return
	 */
	List<Gadget> getGadgetbyKeyword(final String keyword, final Integer maxResults, final Integer start);

	/**
	 * Get all dashboards.
	 * @param boardId
	 * @param userAccId
	 * @return
	 */
	Dashboard getAllDashboards(final Long boardId, final Long userAccId);
}
