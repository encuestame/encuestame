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

import java.util.List;

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Catatalog Location Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since 13:00:55
 * @version $Id$
 */
public class TestCatLocationsDaoImp extends AbstractBase{

    private SecUsers userPrimary;

    /**
     * Before.
     */
    @Before
    public void initService(){
        this.userPrimary = createUser();
    }

    /**
     *
     */
   /* @Test
    public void testloadPermissionByUserId(){
        final SecUserSecondary user = super.createSecondaryUser("user 1",this.userPrimary);
        final SecPermission permission1 = super.createPermission("admon");
        final SecPermission permission2 = super.createPermission("editor");
        final Collection<SecUserPermission> listofPermissions = getSecPermissionDaoImp().loadPermissionByUserId(Integer.valueOf(user.getUid().toString()));
    }
*/
    /**
     * Test LocationByTypeLocationId.
     **/
    @Test
    public void getLocationByTypeLocationId()
    {
        final CatLocation catLoc = createCatLocation("Managua","Departamento",1);
        final List<CatLocation> retrieveLocations = getCatLocationDao().getLocationByTypeLocationId(catLoc.getTidtype().getLocationTypeId());
       assertEquals("Should be equals ", 1 , retrieveLocations.size());
    }

    /**
     * Test Location By Level Id.
     **/
    @Test
    public void getLocationbyLevelId(){
        final CatLocation catLocation = createCatLocation("Tipitapa","Municipio",1);
        createCatLocation("Villa el Carme", "Municipio",1);
        final Integer locateId = catLocation.getLocationLevel();
        final List<CatLocation> retrieveLocLevel = getCatLocationDao().getLocationbyLevelId(locateId);
       assertEquals("Should be eq", 2, retrieveLocLevel.size());

    }

}
