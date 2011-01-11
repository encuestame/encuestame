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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.test.config.AbstractBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Catatalog Location Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since 13:00:55
 * @version $Id$
 */
public class TestCatLocationsDaoImp extends AbstractBase{

    private Account userPrimary;

    /**
     * {@link GeoPoint}.
     */
    private GeoPoint defaultLocation;

    /**
     * Before..
     */
    @Before
    public void initService(){
        this.userPrimary = createUser();
        this.defaultLocation = createGeoPoint("Managua", "Capital", 1, this.userPrimary);
        createGeoPoint("Esteli", "Esteli", 2, this.userPrimary);
    }

    /**
     * Test getLocationByUser.
     */
    @Test
    public void testgetLocationByUser(){
        final List<GeoPoint> geoPoint = getGeoPoint().getLocationByUser(this.userPrimary.getUid());
        Assert.assertEquals(2, geoPoint.size());
    }

    /**
     * Test Find All.
     */
    @Test
    public void testFindAll(){
        final List<GeoPoint> geoPoint = getGeoPoint().findAll();
        assertEquals("Should be equals",2 , geoPoint.size());
    }

    /**
     * getLocationById.
     */
    @Test
    public void testGetLocationById(){
        final GeoPoint geoPoint = getGeoPointDao().getLocationById(this.defaultLocation.getLocateId(),
                                        this.userPrimary.getUid());
        assertNotNull(geoPoint);
        assertEquals("Should be equals", this.defaultLocation.getLocateId() , geoPoint.getLocateId());
    }

    /**
     * getLocationFolders.
     */
    @Test
    public void testGetLocationFolders(){
        createGeoPointFolder(GeoPointFolderType.GROUPING, this.userPrimary, "Managua", null);
        createGeoPointFolder(GeoPointFolderType.GROUPING, this.userPrimary, "Ocotal", null);
        final List<GeoPointFolder> geoPointFolders = getGeoPointDao().getLocationFolders(this.userPrimary.getUid());
        assertEquals("Should be equals",2 , geoPointFolders.size());
    }

    /**
     * Test for getLocationFoldersByLocationFolderId.
     */
    @Test
    public void testGetLocationFoldersByLocationFolderId(){
        final GeoPointFolder geoPointFolder = createGeoPointFolder(GeoPointFolderType.GROUPING, this.userPrimary, "Condega", null);
        createGeoPointFolder(GeoPointFolderType.GROUPING, this.userPrimary, "Wiwili", geoPointFolder);
        final List<GeoPointFolder> geoPointFolders = getGeoPointDao().getLocationFoldersByLocationFolderId(
                geoPointFolder.getLocationFolderId(), this.userPrimary.getUid());
        assertEquals("Should be equals",1 , geoPointFolders.size());
    }

    /**
     * Test getLocationByFolder.
     */
    @Test
    public void testGetLocationByFolder(){
        final GeoPointFolder geoPointFolder = createGeoPointFolder(GeoPointFolderType.GROUPING,
                                                    this.userPrimary, "Matagalpa", null);
        createGeoPoint("Location 1", "type", 1, this.userPrimary, geoPointFolder);
        final List<GeoPoint> geoPoint = getGeoPointDao().getLocationByFolder(geoPointFolder.getLocationFolderId(),
                                         this.userPrimary.getUid());
        assertEquals("Should be equals",1 , geoPoint.size());
    }

    /**
     * Test getLocationFolderByIdAndUserId.
     */
    @Test
    public void testGetLocationFolderByIdAndUserId(){
        final GeoPointFolder geoPointFolder = createGeoPointFolder(GeoPointFolderType.GROUPING,
                this.userPrimary, "Carazo", null);
        final GeoPointFolder geoPointFolder2 = getGeoPointDao().getLocationFolderByIdAndUserId(geoPointFolder.getLocationFolderId(), this.userPrimary.getUid());
        assertEquals("Should be equals", geoPointFolder.getLocationFolderId() , geoPointFolder2.getLocationFolderId());
    }

    /**
     * Test LocationByTypeLocationId..
     **/
    @Test
    public void getLocationByTypeLocationId(){
        final GeoPoint catLoc = createGeoPoint("Managua","Departamento",1, this.userPrimary);
        final List<GeoPoint> retrieveLocations = getGeoPointDao().getLocationByTypeLocationId(catLoc.getTidtype().getLocationTypeId());
        assertEquals("Should be equals ",1 , retrieveLocations.size());
    }
}
