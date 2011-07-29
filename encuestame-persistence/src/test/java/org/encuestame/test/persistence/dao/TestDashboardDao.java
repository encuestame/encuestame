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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.encuestame.persistence.dao.IDashboardDao;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Dashboard Dao.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  July 28, 2011
 */
public class TestDashboardDao extends AbstractBase {

	 /** {@link IDashboardDao} **/
	@Autowired
	IDashboardDao dashboard;

	/** {@link UserAccount} **/
	private UserAccount userAccount;

	/** {@link Account} **/
	private Account account;

	/** {@link Dashboard} **/
	private Dashboard board;

	/** {@link Gadget} **/
	private Gadget gadget;

	@Before
    public void initService(){
		this.account = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.account);
        this.board = createDashboard("First board", Boolean.TRUE, this.userAccount);
        createDashboard("Second board", Boolean.TRUE, this.userAccount);
	    createDashboard("Third board", Boolean.TRUE, this.userAccount);
	    this.gadget = createGadgetDefault();
	    createGadget("gadget 1", "P");
	    createGadget("gadget 2", "P");
	}

	/**
	 * Test retrieve dashboard by id.
	 */
	@Test
	public void testGetDashboardbyId(){
		final Dashboard dashboard = getDashboardDao().getDashboardbyId(this.board.getBoardId());
		  assertNotNull(dashboard);
		  assertEquals("Should be equals", dashboard.getBoardId(), this.board.getBoardId());
	}

	/**
	 * Test retrieve dashboard pages.
	 */
	@Test
	public void testRetrieveDashboardsPage(){
		final List<Dashboard> boardPages = getDashboardDao().retrieveDashboards(this.userAccount);
	    assertEquals("Should be equals", boardPages.size(), 3);
	}

	/**
	 * Test Retrieve favorite dashboards.
	 */
	@Test
	public void testRetrieveFavouritesDashboards(){
		final List<Dashboard> favoriteBoards = getDashboardDao().retrieveFavouritesDashboards(this.userAccount.getUid(), 10, 0);
		assertEquals("Should be equals", favoriteBoards.size(), 3);
	}

	/**
	 * Test Retrieve gadgets by keyword.
	 */
	@Test
	public void testGetGadgetbyKeyword(){
		final List<Gadget> gadgets = getDashboardDao().getGadgetbyKeyword("gadget", 10, 0);
		assertEquals("Should be equals", gadgets.size(), 2);
	}

	/**
	 * Test get gadget by id.
	 */
	@Test
	public void testGetGadgetbyId(){
		final Gadget gad = getDashboardDao().getGadgetbyId(this.gadget.getGadgetId());
		assertEquals("Should be equals", gad.getGadgetId(), this.gadget.getGadgetId());
	}
}
