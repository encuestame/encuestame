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
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.IDashboardService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.dashboard.LayoutEnum;
import org.encuestame.persistence.exception.EnMeDashboardNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeGadgetNotFoundException;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;
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
    public List<DashboardBean> getAllDashboards(final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        final List<DashboardBean> boardBean = new ArrayList<DashboardBean>();
        final List<Dashboard> boards = getDashboardDao().retrieveDashboardsbyUser(getUserAccount(getUserPrincipalUsername()), maxResults, start);
        log.info("dashboards list size "+boards.size());
        boardBean.addAll(ConvertDomainBean.convertListDashboardToBean(boards));
        return boardBean;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getAllDashboardbyId(java.lang.Long, java.lang.String)
     */
    public Dashboard getDashboardbyIdandUser(final Long boardId) throws EnMeNoResultsFoundException {
        Dashboard dashboard = null;
            if ( (boardId == null)) {
            	 throw new EnMeDashboardNotFoundException("dashboard id is missing");
            } else {
            	dashboard = getDashboardDao().getDashboardbyIdandUser(boardId, getUserAccount(getUserPrincipalUsername()));
            }

        return dashboard;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#retrieveFavouritesDashboards(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    public List<DashboardBean> retrieveFavouritesDashboards(final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        final List<DashboardBean> boardBean = new ArrayList<DashboardBean>();
        final List<Dashboard> favoriteBoards = getDashboardDao().retrieveFavouritesDashboards(getUserAccount(getUserPrincipalUsername()), maxResults, start);
        boardBean.addAll(ConvertDomainBean.convertListDashboardToBean(favoriteBoards));
        return boardBean;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getDashboardById(java.lang.Long)
     */

	private Dashboard getDashboardById(final Long boardId) throws EnMeNoResultsFoundException{
            return this.getDashboardbyIdandUser(boardId);
        }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getAllDashboardbyId(java.lang.Long, java.lang.String)
     */
    public Dashboard getAllDashboardbyId(final Long boardId) throws EnMeNoResultsFoundException{
        Dashboard dashboard = null;
            if (boardId == null) {
            	  throw new EnMeDashboardNotFoundException("dashboard Id poll is missing "+boardId);
            } else {
            	 dashboard = this.getDashboardById(boardId);
            }
       return dashboard;
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
    public List<GadgetBean> searchGadgetbyKeyword(final String keyword,
            final Integer maxResults,
            final Integer start) throws EnMeExpcetion{
             List<Gadget> gadgets  = new ArrayList<Gadget>();
            List<GadgetBean> gadgetBean = new ArrayList<GadgetBean>();
            if(keyword == null){
               throw new EnMeExpcetion("keyword is missing");
            } else {
                gadgets = getDashboardDao().getGadgetbyKeyword(keyword, maxResults, start);
                gadgetBean.addAll(ConvertDomainBean.convertListGadgetToBean(gadgets));
                System.out.println("------------"+gadgets.size());
            }
            log.info("search keyword Gadgets size "+gadgets.size());
            return gadgetBean;
        }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#createDashboard(org.encuestame.utils.web.DashboardBean, org.encuestame.persistence.domain.security.UserAccount)
     */
    public Dashboard createDashboard(final DashboardBean dashboardBean) throws EnMeNoResultsFoundException{
        final Dashboard board = new Dashboard();
            board.setPageBoardName(dashboardBean.getDashboardName());
            board.setDescription(dashboardBean.getDashboardDesc());
            board.setFavorite(dashboardBean.getFavorite());
            board.setBoardSequence(dashboardBean.getSequence());
            board.setFavoriteCounter(dashboardBean.getFavoriteCounter());
            //board.setGadgetDashboard(gadgetDashboard);
            board.setPageLayout(LayoutEnum.getDashboardLayout(dashboardBean.getLayout()));
            board.setUserBoard(getUserAccount(getUserPrincipalUsername()));
            getDashboardDao().saveOrUpdate(board);
        return board;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#addGadgetOnDashboard(java.lang.Long, java.lang.Long)
     */
    public void addGadgetOnDashboard(final Long boardId, final Long gadgetId) throws EnMeGadgetNotFoundException{
        final Gadget gadget = getDashboardDao().getGadgetbyId(gadgetId);
        if(gadget!=null){
            final Dashboard dashboard = getDashboardDao().getDashboardbyId(boardId);
            //dashboard.getGadgetDashboard().add(gadget);

        }else{
            throw new EnMeGadgetNotFoundException("gadget not found");
        }
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#removeGadget(java.lang.Long)
     */
    public void removeGadget(final Long gadgetId, final Long dashboardId) throws EnMeNoResultsFoundException{
    	final Dashboard board = this.getDashboardById(dashboardId);
    	final Gadget gadget= this.getGadget(gadgetId, board);
    	final List<GadgetProperties> prop = getDashboardDao().retrievePropertiesbyGadget(gadget.getGadgetId());
    	if(prop.size() > 0){
    		for (GadgetProperties gadgetProperties : prop) {
				getDashboardDao().delete(gadgetProperties);
			}
    	}
        getDashboardDao().delete(gadget);

    	}

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getDashboardsbyKeyword(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<Dashboard> getDashboardsbyKeyword(final String keyword,
            final Integer maxResults,
            final Integer start) throws EnMeExpcetion{
        List<Dashboard> dashboardList = new ArrayList<Dashboard>();
        if(keyword!= null){
            throw new EnMeExpcetion("keyword is missing");
        }
        else {
          dashboardList = getDashboardDao().retrieveDashboardbyKeyword(keyword, getUserAccount(getUserPrincipalUsername()), maxResults, start);
        }
        return dashboardList;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getGadgetsbyDashboard(java.lang.Long, java.lang.String)
     */
    public List<Gadget> getGadgetsbyDashboard(final Long dashboardId) throws EnMeNoResultsFoundException{
        final Dashboard board = this.getDashboardById(dashboardId);
        List<Gadget> gadgets = new ArrayList<Gadget>();
        if(board==null){
            throw new EnMeDashboardNotFoundException("dashboard not found");
        }
        else{
            gadgets = getDashboardDao().retrieveGadgetsbyDashboard(dashboardId);
        }
        return gadgets;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getPropertiesbyGadget(java.lang.Long)
     */
    public List<GadgetProperties> getPropertiesbyGadget(final Long gadgetId) throws EnMeGadgetNotFoundException{
        final Gadget gadget = getDashboardDao().getGadgetbyId(gadgetId);
        List<GadgetProperties> properties = new ArrayList<GadgetProperties>();
        if(gadget==null){
            throw new EnMeGadgetNotFoundException("gadget not found");
        }
        else{
            properties = getDashboardDao().retrievePropertiesbyGadget(gadgetId);
        }
        return properties;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IDashboardService#getAllGadgetsAvailable()
     */
    public List<GadgetBean> getAllGadgetsAvailable(final Long boardId) throws EnMeNoResultsFoundException{
    		final Dashboard dashboard = this.getDashboardById(boardId);
            List<GadgetBean> gadgetBean = new ArrayList<GadgetBean>();
            final List<Gadget> gadgets = getDashboardDao().retrieveGadgets(dashboard);
            gadgetBean.addAll(ConvertDomainBean.convertListGadgetToBean(gadgets));
        return gadgetBean;
    }

    /**
     * Move gadget
     * @param gadgetId
     * @param position
     * @param column
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public void moveGadget(final Long gadgetId, final Long boardId, final Integer position, final Integer column) throws EnMeNoResultsFoundException{
    	final Dashboard board = this.getDashboardById(boardId);
    	final Gadget gadget = this.getGadget(gadgetId, board);
    	gadget.setGadgetPosition(position);
    	gadget.setGadgetColumn(column);
    }

    /**
     * Get gadget.
     * @param gadgetId
     * @return
     * @throws EnMeNoResultsFoundException
     */
	private Gadget getGadget(final Long gadgetId, final Dashboard board) throws EnMeNoResultsFoundException{
		 Gadget gadget = null;
         if ( (gadgetId == null)) {
         	 throw new EnMeGadgetNotFoundException("gadget id is missing");
         } else {
        	 gadget = getDashboardDao().getGadgetbyIdandBoard(gadgetId, board);
         }
     return gadget;
    }
}
