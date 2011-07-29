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
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.IDashboardService;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.exception.EnMeDashboardNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.springframework.stereotype.Service;

/**
 * {@link Dashboard} service support.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
@Service
public class DashboardService extends AbstractBaseService implements IDashboardService{

	 private Log log = LogFactory.getLog(this.getClass());

	 /*
	  * (non-Javadoc)
	  * @see org.encuestame.business.service.imp.IDashboardService#getAllDashboards(java.lang.String, java.lang.Integer, java.lang.Integer)
	  */
	public List<Dashboard> getAllDashboards(final String username,
            final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
		final List<Dashboard> boards = getDashboardDao().retrieveDashboards(getPrimaryUser(username), maxResults, start);
		log.info("dashboards list size "+boards.size());
		return boards;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.business.service.imp.IDashboardService#getAllDashboardbyId(java.lang.Long, java.lang.String)
	 */
	public Dashboard getAllDashboardbyId(final Long boardId, final String username) throws EnMeNoResultsFoundException{
		Dashboard dashboard = null;
	        if (username != null) {
	        	dashboard = getDashboardDao().getAllDashboards(boardId, getPrimaryUser(username));
	        } else {
	        	dashboard = getDashboardDao().getDashboardbyId(boardId);
	        }
	        if (dashboard == null) {
	            log.error("dashboardinvalid with this id "+boardId);
	            throw new EnMeDashboardNotFoundException("tweet poll invalid with this id "+boardId);
	        }
		return dashboard;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.business.service.imp.IDashboardService#retrieveFavouritesDashboards(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	public List<Dashboard> retrieveFavouritesDashboards(final Long userId,
	        final Integer maxResults,
	        final Integer start){
		final List<Dashboard> favoriteBoards = getDashboardDao().retrieveFavouritesDashboards(userId, maxResults, start);
		return favoriteBoards;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.business.service.imp.IDashboardService#getDashboardById(java.lang.Long)
	 */
	public Dashboard getDashboardById(final Long boardId) throws EnMeNoResultsFoundException{
	        return this.getAllDashboardbyId(boardId, getUserPrincipalUsername());
	    }

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.business.service.imp.IDashboardService#getGadgetById(java.lang.Long)
	 */
	public Gadget getGadgetById(final Long gadgetId){
		final Gadget gadget = getDashboardDao().getGadgetbyId(gadgetId);
		return gadget;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.business.service.imp.IDashboardService#searchGadgetbyKeyword(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public List<Gadget> searchGadgetbyKeyword(final String keyword,
            final Integer maxResults,
            final Integer start) throws EnMeExpcetion{
		 	List<Gadget> gadgets  = new ArrayList<Gadget>();
	        if(keyword == null){
	           throw new EnMeExpcetion("keyword is missing");
	        } else {
	        	gadgets = getDashboardDao().getGadgetbyKeyword(keyword, maxResults, start);
	        }
	        log.info("search keyword Gadgets size "+gadgets.size());
	        return gadgets;
	    }
}
