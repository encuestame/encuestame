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
package org.encuestame.test.persistence.domain;

import org.encuestame.persistence.domain.CatState;
import org.encuestame.test.config.AbstractBase;
import org.junit.Test;

/**
* Test Catalog State Pojo.
* @author Morales, Diana Paola paola@encuestame.org
* @since November 06, 2009
* @version $Id$
*/
public class TestCatState extends AbstractBase {
    /**
     * Test Catalog State.
     */
    @Test
    public void testCatState(){
        final CatState catState = new CatState();
        catState.setDescState("State Description");
        catState.setStateImage("Image");
        getCatStateDaoImp().saveOrUpdate(catState);
     }
}
