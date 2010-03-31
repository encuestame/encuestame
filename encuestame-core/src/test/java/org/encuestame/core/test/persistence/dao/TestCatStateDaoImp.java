/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.persistence.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Test;

/**
 * CatStaeDaoImp
 * @author Picado, Juan juan@encuestame.org
 * @since October 15, 2009
 * @version $Id$
 */

public class TestCatStateDaoImp extends AbstractBase{

    /**
     * Test Find All States.
     **/
    @Test
    public void testListCatState(){
        super.createState("state1");
        final List<CatState> listStates = getCatStateDaoImp().findAll();
        assertNotNull(listStates);
        assertEquals(listStates.size(),listStates.size());
    }

    /**
     * Test Get State By Id.
     **/
    @Test
    public void testGroupById(){
        final CatState state = super.createState("state 2");
        final CatState retrieveState = getCatStateDaoImp()
        .getState(Long.valueOf(state.getIdState().toString()));
        assertNotNull(retrieveState);
    }
}
