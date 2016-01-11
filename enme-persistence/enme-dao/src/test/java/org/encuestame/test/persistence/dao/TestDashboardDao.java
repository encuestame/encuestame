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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.encuestame.persistence.dao.IDashboardDao;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.persistence.domain.dashboard.GadgetProperties;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.persistence.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.GadgetType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Dashboard Dao.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since  July 28, 2011
 */
@Category(DefaultTest.class)
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

    /** Max results. **/
    private Integer MAX_RESULTS;

    /** Start results. **/
    private Integer START;

    @Before
    public void initService(){
        this.account = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.account);
        this.board = createDashboard("First board", Boolean.TRUE, this.userAccount);
        createDashboard("Second board", Boolean.TRUE, this.userAccount);
        createDashboard("Third board", Boolean.TRUE, this.userAccount);
        this.gadget = createGadgetDefault(this.board );
        createGadget("gadget 1", this.board);
        createGadget("gadget 2", this.board);
        createGadgetProperties("reference1", "1", this.gadget, this.userAccount);
        createGadgetProperties("reference2", "11", this.gadget, this.userAccount);
        createGadgetProperties("reference3", "3", this.gadget, this.userAccount);
    }

    /**
     *
     */
    @Test
    public void testTetrieveGadgets(){
        final List<Gadget> sd = getDashboardDao().retrieveGadgets(this.board);
        assertEquals("Should be equals", sd.size(), 3);
        createGadget("gadget 2", this.board);
        createGadget("gadget 3", this.board);
        createGadget("gadget 4", this.board);
        createGadget("gadget 5", this.board);
        createGadget("gadget 6", this.board);
        createGadget("gadget 7", this.board);
        final List<Gadget> sd2 = getDashboardDao().retrieveGadgets(this.board);
        assertEquals("Should be equals", sd2.size(), 9);
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
        final List<Dashboard> boardPages = getDashboardDao().retrieveDashboardsbyUser(this.userAccount,
                this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", boardPages.size(), 3);
    }

    /**
     * Test Retrieve favorite dashboards.
     */
    @Test
    public void testRetrieveFavouritesDashboards(){
        final List<Dashboard> favoriteBoards = getDashboardDao().retrieveFavouritesDashboards(this.userAccount,
                this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", favoriteBoards.size(), 3);
    }

    /**
     * Test Retrieve gadgets by keyword.
     */
    @Test
    public void testGetGadgetbyKeyword(){
        final List<Gadget> gadgets = getDashboardDao().getGadgetbyKeyword("gadget", this.MAX_RESULTS, this.START);
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

    /**
     * Test get dashboard by id and user.
     */
    @Test
    public void testGetDashboardbyIdandUser(){
        final Dashboard board = getDashboardDao().getDashboardbyIdandUser(this.board.getBoardId(), this.userAccount);
        assertNotNull(board);
    }

    /**
     * Test retrieve dashboards by keyword.
     */
    @Test
    public void testRetrieveDashboardbyKeyword(){
        final String keyword="board";
        final List<Dashboard> boardList = getDashboardDao().retrieveDashboardbyKeyword(keyword, this.userAccount,
                this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 3, boardList.size());
    }

    /**
     * Test retrieve properties by Gadget.
     */
    @Test
    public void testRetrievePropertiesbyGadget(){
        final List<GadgetProperties> properties = getDashboardDao().retrievePropertiesbyGadget(this.gadget.getGadgetId());
        assertEquals("Should be equals", 3, properties.size());
    }

    /**
     * Test retrieve gadget by dashboard.
     */
    //@Test
    public void testRetrieveGadgetsbyDashboard(){
         final List<Gadget> gadgets = getDashboardDao().retrieveGadgetsbyDashboard(this.board.getBoardId());
         assertEquals("Should be equals", 3, gadgets.size());
    }

    /**
     * Test retrieve gadget by type.
     */
    @Test
    public void testRetrieveGadgetsbyType(){
        final List<Gadget> gadget = getDashboardDao().retrieveGadgetsbyType(GadgetType.ACTIVITY_STREAM);
        assertEquals("Should be equals", 3, gadget.size());
    }
}
