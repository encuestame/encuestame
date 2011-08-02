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

import static org.junit.Assert.*;

import java.util.List;

import org.encuestame.business.service.DashboardService;
import org.encuestame.business.service.imp.IDashboardService;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.service.config.AbstractServiceBase;
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


	@Before
    public void initService(){
		this.account = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.account);
        this.dashboard = createDashboard("First board", Boolean.TRUE, this.userAccount);
        createDashboard("Second board", Boolean.TRUE, this.userAccount);
	    createDashboard("Third board", Boolean.TRUE, this.userAccount);

	}

	@Test
	public void testGetAllDashboards() throws EnMeNoResultsFoundException{
		System.out.println("testing");
		final List<Dashboard> allBoards = dashboardService.getAllDashboards(this.userAccount.getUsername(), 10, 0);
		assertEquals("Should be equals", 3, allBoards.size());
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
