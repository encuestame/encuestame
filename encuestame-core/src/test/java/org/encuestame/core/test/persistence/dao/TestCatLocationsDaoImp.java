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

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationFolder;
import org.encuestame.core.persistence.pojo.LocationFolderType;
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
     * {@link CatLocation}.
     */
    private CatLocation defaultLocation;

    /**
     * Before..
     */
    @Before
    public void initService(){
        this.userPrimary = createUser();
        this.defaultLocation = createCatLocation("Managua", "Capital", 1, this.userPrimary);
        createCatLocation("Esteli", "Esteli", 2, this.userPrimary);
    }

    /**
     * Test Find All.
     */
    @Test
    public void testFindAll(){
        final List<CatLocation> catLocations = getCatLocation().findAll();
        assertEquals("Should be equals",2 , catLocations.size());
    }

    /**
     * getLocationById.
     */
    @Test
    public void testGetLocationById(){
        final CatLocation catLocation = getCatLocationDao().getLocationById(this.defaultLocation.getLocateId(),
                                        this.userPrimary.getUid());
        assertNotNull(catLocation);
        assertEquals("Should be equals", this.defaultLocation.getLocateId() , catLocation.getLocateId());
    }

    /**
     * getLocationFolders.
     */
    @Test
    public void testGetLocationFolders(){
        createCatLocationFolder(LocationFolderType.GROUPING, this.userPrimary, "Managua", null);
        createCatLocationFolder(LocationFolderType.GROUPING, this.userPrimary, "Ocotal", null);
        final List<CatLocationFolder> catLocationFolders = getCatLocationDao().getLocationFolders(this.userPrimary.getUid());
        assertEquals("Should be equals",2 , catLocationFolders.size());
    }

    /**
     * Test for getLocationFoldersByLocationFolderId.
     */
    @Test
    public void testGetLocationFoldersByLocationFolderId(){
        final CatLocationFolder catLocationFolder = createCatLocationFolder(LocationFolderType.GROUPING, this.userPrimary, "Condega", null);
        createCatLocationFolder(LocationFolderType.GROUPING, this.userPrimary, "Wiwili", catLocationFolder);
        final List<CatLocationFolder> catLocationFolders = getCatLocationDao().getLocationFoldersByLocationFolderId(
                                      catLocationFolder.getLocationFolderId(), this.userPrimary.getUid());
        assertEquals("Should be equals",1 , catLocationFolders.size());
    }

    /**
     * Test getLocationByFolder.
     */
    @Test
    public void testGetLocationByFolder(){
        final CatLocationFolder catLocationFolder = createCatLocationFolder(LocationFolderType.GROUPING,
                                                    this.userPrimary, "Matagalpa", null);
        createCatLocation("Location 1", "type", 1, this.userPrimary, catLocationFolder);
        final List<CatLocation> catLocations = getCatLocationDao().getLocationByFolder(catLocationFolder.getLocationFolderId(),
                                         this.userPrimary.getUid());
        assertEquals("Should be equals",1 , catLocations.size());
    }

    /**
     * Test getLocationFolderByIdAndUserId.
     */
    @Test
    public void testGetLocationFolderByIdAndUserId(){
        final CatLocationFolder catLocationFolder = createCatLocationFolder(LocationFolderType.GROUPING,
                this.userPrimary, "Carazo", null);
        final CatLocationFolder catLocationFolder2 = getCatLocationDao().getLocationFolderByIdAndUserId(catLocationFolder.getLocationFolderId(), this.userPrimary.getUid());
        assertEquals("Should be equals", catLocationFolder.getLocationFolderId() , catLocationFolder2.getLocationFolderId());
    }

    /**
     * Test LocationByTypeLocationId..
     **/
    @Test
    public void getLocationByTypeLocationId(){
        final CatLocation catLoc = createCatLocation("Managua","Departamento",1, this.userPrimary);
        final List<CatLocation> retrieveLocations = getCatLocationDao().getLocationByTypeLocationId(catLoc.getTidtype().getLocationTypeId());
        assertEquals("Should be equals ",1 , retrieveLocations.size());
    }
}
