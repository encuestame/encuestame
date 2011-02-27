/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import java.util.List;

import org.encuestame.business.service.imp.ILocationService;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.dao.IGeoPoint;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.test.config.AbstractBaseUnitBeans;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link LocationService}.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 10, 2010 11:37:52 AM
 * @version $Id:$
 */
public class TestLocationServices extends AbstractServiceBase{

    /**
     * Location Service.
     */
    @Autowired
    private ILocationService locationService;

    /**
     * Cat Location Dao.
     */
    @Autowired
    private IGeoPoint geoPoint;

    private UserAccount secondary;

    @Before
    public void init(){
        this.secondary = createUserAccount("jota", createUser());
    }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
    public void testupdateGeoPoint() throws EnMeExpcetion{
        final GeoPoint locationBean = createGeoPoint("test", "test", 0, this.secondary.getAccount());
        final UnitLocationBean bean = ConvertDomainBean.convertLocationToBean(locationBean);
        bean.setName("test2");
        this.locationService.updateGeoPoint(bean, this.secondary.getUsername());
        Assert.assertEquals(locationBean.getLocationDescription(), new String("test2"));
    }

    /**
     * test for createGeoPoint.
     * @throws EnMeExpcetion
     */
    @Test
    public void testCreateGeoPoint() throws EnMeExpcetion{
        final UnitLocationBean locationBean = createUnitLocationBean("pozuelo");
        this.locationService.createGeoPoint(locationBean, this.secondary.getUsername());
        Assert.assertNotNull(locationBean.getId());
    }

    /**
     * Test createLocationFolder.
     * @throws EnMeExpcetion
     */
    @Test
    public void testcreateLocationFolder() throws EnMeExpcetion{
        final UnitLocationFolder folder = createUnitLocationFolder("folder");
        this.locationService.createGeoPointFolder(folder, this.secondary.getUsername());
        Assert.assertNotNull(folder.getId());
    }

    /**
     * @throws EnMeNoResultsFoundException
     *
     */
    @Test
    public void testretrieveLocationFolderByUser() throws EnMeNoResultsFoundException{
         final UnitLocationFolder folder1 = createUnitLocationFolder("folder 1");
         this.locationService.createGeoPointFolder(folder1, this.secondary.getUsername());
         final UnitLocationFolder folder2 = createUnitLocationFolder("folder2 ");
         this.locationService.createGeoPointFolder(folder2, this.secondary.getUsername());
         final List<UnitLocationFolder> list = this.locationService.retrieveLocationFolderByUser(this.secondary.getUsername());
         Assert.assertEquals(list.size(), 2);
    }

    /**
     * test retrieveLocationSubFolderByUser.
     * @throws Exception
     */
    @Test
    public void testretrieveLocationSubFolderByUser() throws Exception{
        final GeoPointFolder locationFolder = createGeoPointFolder(GeoPointFolderType.GROUPING, this.secondary.getAccount(), "folder", null);
        createGeoPointFolder(GeoPointFolderType.GROUPING, this.secondary.getAccount(), "folder sub", locationFolder);
        final List<UnitLocationFolder> list = this.locationService.retrieveLocationSubFolderByUser(locationFolder.getLocationFolderId(), this.secondary.getUsername());
        Assert.assertEquals(list.size(), 1);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testupdateLocationMap() throws Exception{
        final GeoPointFolder locationFolder = createGeoPointFolder(GeoPointFolderType.GROUPING, this.secondary.getAccount(), "folder", null);
        //final CatLocation location = createCatLocation("Managua", locTypeName, Level, secUsers)
    }

    /**
     * @return the locationService
     */
    public ILocationService getLocationService() {
        return locationService;
    }

    /**
     * @param locationService the locationService to set
     */
    public void setLocationService(ILocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * @return the geoPoint.
     */
    public IGeoPoint getGeoPoint() {
        return geoPoint;
    }

    /**
     * @param geoPoint the catLocation to set
     */
    public void setGeoPoint(IGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
