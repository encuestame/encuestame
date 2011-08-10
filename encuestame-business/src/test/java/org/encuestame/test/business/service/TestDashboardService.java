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
package org.encuestame.test.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.encuestame.business.service.DashboardService;
import org.encuestame.core.service.imp.IDashboardService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.LayoutEnum;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link DashboardService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public class TestDashboardService extends AbstractSpringSecurityContext{

    /** {@link IDashboardService} **/
    @Autowired
    public IDashboardService dashboardService;

    /** {@link Dashboard} **/
    private Dashboard dashboard;

    /** Max Results. **/
    private Integer MAX_RESULTS = 10;

    /** Start Results. **/
    private Integer START = 0;

    /** {@link Gadget} **/
    private Gadget gadget;

    /** {@link DashboardBean} **/
    private DashboardBean boardBean;

    @Before
    public void initService(){
        this.dashboard = createDashboard("First board", Boolean.TRUE, getSpringSecurityLoggedUserAccount());
        createDashboard("Second board", Boolean.TRUE, getSpringSecurityLoggedUserAccount());
	    createDashboard("Third board", Boolean.TRUE, getSpringSecurityLoggedUserAccount());
	    this.gadget = createGadgetDefault(this.dashboard);
	    createGadget("gadget 1", this.dashboard);
	    createGadget("gadget 2", this.dashboard);
	    createGadgetProperties("name", "Notifications", this.gadget, getSpringSecurityLoggedUserAccount());
	    createGadgetProperties("maxResults", "20", this.gadget,getSpringSecurityLoggedUserAccount());
	    createGadgetProperties("order", "Asc", this.gadget, getSpringSecurityLoggedUserAccount());
	    this.boardBean = ConvertDomainBean.convertDashboardDomaintoBean(this.dashboard);

	    //createDashboardBean("Notifications", "Notifications board", Boolean.TRUE , LayoutEnum.AAA_COLUMNS , 1, 0);
	}

	//@Test
	public void test(){
		System.out.println("--------");
	}

	@Test
	public void testGetAllDashboards() throws EnMeNoResultsFoundException{
		final List<DashboardBean> allBoards = dashboardService.getAllDashboards(this.MAX_RESULTS, this.START);
		assertEquals("Should be equals", 3, allBoards.size());
	}

	/**
	 * Test get dashboard by id and user.
	 * @throws EnMeNoResultsFoundException
	 */
	@Test
	public void testGetDashboardbyIdandUser() throws EnMeNoResultsFoundException{
		final Dashboard board = dashboardService.getDashboardbyId(this.dashboard.getBoardId());
		assertNotNull(board);
		assertEquals("Should be equals", this.dashboard.getBoardId(), board.getBoardId());
	}

	 /**
	 * Test retrieve favourites Dashboards.
	 * @throws EnMeNoResultsFoundException
	 */
	@Test
	public void testRetrieveFavouritesDashboards() throws EnMeNoResultsFoundException{
		final List<DashboardBean> boardList = dashboardService.retrieveFavouritesDashboards(this.MAX_RESULTS, this.START);
		//System.out.println(" Board List ----> " + boardList.size());
		assertEquals("Should be equals", 3, boardList.size());
	}

	 /**
	 * Test get dashboard by id.
	 * @throws EnMeNoResultsFoundException
	 */
/*	@Test
	public void testGetDashboardById() throws EnMeNoResultsFoundException{
		final Dashboard board = dashboardService.getDashboardById(this.dashboard.getBoardId());
		assertEquals("Should be equals", this.dashboard.getBoardId(), board.getBoardId());
	}*/

	 /**
	 * Test get gadget by id.
	 */
	@Test
	public void testGetGadgetById(){
		final Gadget gad = dashboardService.getGadgetById(this.gadget.getGadgetId());
		assertNotNull(gadget);
		assertEquals("Should be equals", this.gadget.getGadgetId(), gad.getGadgetId());
	}

	/**
	 * Test Search gadgets by keyword.
	 * @throws EnMeExpcetion
	 */
	@Test
	public void testSearchGadgetbyKeyword() throws EnMeExpcetion {
		final String keyword = "gadget";
		final List<GadgetBean> gadgetList = dashboardService.searchGadgetbyKeyword(keyword, this.MAX_RESULTS, this.START);
		assertEquals("Should be equals", 2, gadgetList.size());
	}

	 /**
	 * Test create dashboard.
	 * @throws EnMeNoResultsFoundException
	 */
	@Test
	public void testCreateDashboard() throws EnMeNoResultsFoundException{
		final Dashboard board = dashboardService.createDashboard(this.boardBean);
		assertNotNull(board.getBoardId());
	}

	@Test
	public void removeGadgetFromDashboard() throws EnMeNoResultsFoundException{
		final List<Gadget> gadgets = dashboardService.getGadgetsbyDashboard(this.dashboard.getBoardId());
		assertEquals("Should be equals", 3, gadgets.size());
		dashboardService.removeGadget(this.gadget.getGadgetId(), this.dashboard.getBoardId());
		final List<Gadget> gadgets2 = dashboardService.getGadgetsbyDashboard(this.dashboard.getBoardId());
		assertEquals("Should be equals", 2, gadgets2.size());
	}

	@Test
	public void testUpdateDashboard() throws EnMeNoResultsFoundException{
		final String newName = "Notifications2";
		this.boardBean.setDashboardName(newName);
		final Dashboard boardUpdate = getDashboardService().updateDashboard(this.dashboard.getBoardId() , boardBean);
		System.out.println("EL NUEVO NAME ES ----> " + boardUpdate.getPageBoardName());
	}

	/**
	 * @return the dashboardService
	 */
	public IDashboardService getDashboardService() {
		return dashboardService;
	}

	/**
	 * @param dashboardService the dashboardService to set
	 */
	public void setDashboardService(IDashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
}
