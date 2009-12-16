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
package org.encuestame.core.persistence.pojos;

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;

/**
 * Test Catalog Location.
 *
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since 02/11/2009 16:18:49
 * @version $Id$
 */
public class TestCatLocation extends AbstractBaseTest{
    /**
     * Test Catalag Location.
     */
    @Test
    public void testCatLocation(){
    final CatLocation catLoc = new CatLocation();
    catLoc.setLocationActive("S");
    catLoc.setlocationDescription("Locate Description");
    catLoc.setLocationLevel(1);
    catLoc.setLocationLatitude(2F);
    catLoc.setLocationLongitude(3F);
    catLoc.setTidtype(createCatLocationType("aldea"));
    getCatLocationDao().saveOrUpdate(catLoc);

    }

}
