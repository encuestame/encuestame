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
package org.encuestame.core.test.persistence.pojos;

import static org.junit.Assert.assertNotNull;

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.Status;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Test;

/**
 * Test Catalog Location.
 *
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since 02/11/2009 16:18:49
 * @version $Id$
 */
public class TestCatLocation extends AbstractBase{
    /**
     * Test Catalag Location.
     */
    @Test
    public void testCatLocation(){
        final CatLocation catLoc = new CatLocation();
        catLoc.setLocationStatus(Status.ACTIVE);
        catLoc.setlocationDescription("Managua");
        catLoc.setLocationLatitude(2F);
        catLoc.setLocationLongitude(3F);
        catLoc.setTidtype(createCatLocationType("aldea"));
        catLoc.getProjects().add(createProject("encuestame", "survey", "open source", createState("active"), createUser()));
        getCatLocationDao().saveOrUpdate(catLoc);
        assertNotNull(catLoc.getLocateId());
    }

}
