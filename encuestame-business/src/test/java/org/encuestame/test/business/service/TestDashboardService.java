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

import org.encuestame.business.service.DashboardService;
import org.encuestame.core.service.imp.IDashboardService;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.LayoutEnum;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.web.DashboardBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link DashboardService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public class TestDashboardService extends AbstractServiceBase{

	/** {@link IDashboardService} **/
	@Autowired
	public IDashboardService dashboardService;

	/** {@link Dashboard} **/
	private Dashboard dashboard;

	/** {@link Account} **/
	private Account account;

	/** {@link UserAccount} **/
	private UserAccount userAccount;

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
		this.account = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.account);
        this.dashboard = createDashboard("First board", Boolean.TRUE, this.userAccount);
        createDashboard("Second board", Boolean.TRUE, this.userAccount);
	    createDashboard("Third board", Boolean.TRUE, this.userAccount);
	    this.gadget = createGadgetDefault();
	    createGadget("gadget 1");
	    createGadget("gadget 2");
	    createGadgetProperties("name", "Notifications", this.gadget, this.userAccount);
	    createGadgetProperties("maxResults", "20", this.gadget, this.userAccount);
	    createGadgetProperties("order", "Asc", this.gadget, this.userAccount);
	    this.boardBean = createDashboardBean("Notifications", "Notifications board", Boolean.TRUE , LayoutEnum.AAA_COLUMNS , 1, 0);
	}

	@Test
	public void test(){
		System.out.println("--------");
	}

	/*//@Test
	public void testGetAllDashboards() throws EnMeNoResultsFoundException{
		System.out.println("testing");
		final List<Dashboard> allBoards = dashboardService.getAllDashboards(this.userAccount.getUsername(), this.MAX_RESULTS, this.START);
		assertEquals("Should be equals", 3, allBoards.size());
	}

	*//**
	 * Test get dashboard by id and user.
	 * @throws EnMeNoResultsFoundException
	 *//*
	//@Test
	public void testGetDashboardbyIdandUser() throws EnMeNoResultsFoundException{
		final Dashboard board = dashboardService.getDashboardbyIdandUser(this.dashboard.getBoardId(), this.userAccount.getUsername());
		assertNotNull(board);
		assertEquals("Should be equals", this.dashboard.getBoardId(), board.getBoardId());
	}

	*//**
	 * Test retrieve favourites Dashboards.
	 *//*
	@Test
	public void testRetrieveFavouritesDashboards(){
		final List<Dashboard> boardList = dashboardService.retrieveFavouritesDashboards(this.userAccount.getUid(), this.MAX_RESULTS, this.START);
		System.out.println(" BOARD LIST ----> " + boardList.size());
		assertEquals("Should be equals", 3, boardList.size());
	}

	*//**
	 * Test get dashboard by id.
	 * @throws EnMeNoResultsFoundException
	 *//*
	@Test
	public void testGetDashboardById() throws EnMeNoResultsFoundException{
		final Dashboard board = dashboardService.getDashboardById(this.dashboard.getBoardId());
		assertEquals("Should be equals", this.dashboard.getBoardId(), board.getBoardId());
	}

	*//**
	 * Test get gadget by id.
	 *//*
	@Test
	public void testGetGadgetById(){
		final Gadget gad = dashboardService.getGadgetById(this.gadget.getGadgetId());
		assertNotNull(gadget);
		assertEquals("Should be equals", this.gadget.getGadgetId(), gad.getGadgetId());
	}

	*//**
	 * Test Search gadgets by keyword.
	 * @throws EnMeExpcetion
	 *//*
	//@Test
	public void testSearchGadgetbyKeyword() throws EnMeExpcetion{
		final String keyword = "Second board";
		final List<Gadget> gadgetList = dashboardService.searchGadgetbyKeyword(keyword, this.MAX_RESULTS, this.START);
		assertEquals("Should be equals", 3, gadgetList.size());
	}

	*//**
	 * Test create dashboard.
	 *//*
	//@Test
	public void testCreateDashboard(){
		final Dashboard board = dashboardService.createDashboard(this.boardBean, this.userAccount);
		assertNotNull(board.getBoardId());
	}*/

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
