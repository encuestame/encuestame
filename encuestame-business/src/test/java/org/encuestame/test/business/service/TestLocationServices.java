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

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.domain.CatLocation;
import org.encuestame.core.persistence.domain.CatLocationFolder;
import org.encuestame.core.persistence.domain.LocationFolderType;
import org.encuestame.core.persistence.domain.security.SecUserSecondary;
import org.encuestame.core.service.LocationService;
import org.encuestame.core.service.imp.ILocationService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.persistence.dao.ICatLocation;
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
public class TestLocationServices extends AbstractBaseUnitBeans{

    /**
     * Location Service.
     */
    @Autowired
    private ILocationService locationService;

    /**
     * Cat Location Dao.
     */
    @Autowired
    private ICatLocation catLocation;

    private SecUserSecondary secondary;

    @Before
    public void init(){
        this.secondary = createSecondaryUser("jota", createUser());
    }

    /**
     *
     * @throws EnMeExpcetion
     */
    @Test
    public void testupdateCatLocation() throws EnMeExpcetion{
        final CatLocation locationBean = createCatLocation("test", "test", 0, this.secondary.getSecUser());
        final UnitLocationBean bean = ConvertDomainBean.convertLocationToBean(locationBean);
        bean.setName("test2");
        this.locationService.updateCatLocation(bean, this.secondary.getUsername());
        Assert.assertEquals(locationBean.getLocationDescription(), new String("test2"));
    }

    /**
     * test for createCatLocation.
     * @throws EnMeExpcetion
     */
    @Test
    public void testcreateCatLocation() throws EnMeExpcetion{
        final UnitLocationBean locationBean = createUnitLocationBean("pozuelo");
        this.locationService.createCatLocation(locationBean, this.secondary.getUsername());
        Assert.assertNotNull(locationBean.getId());
    }

    /**
     * Test createLocationFolder.
     * @throws EnMeExpcetion
     */
    @Test
    public void testcreateLocationFolder() throws EnMeExpcetion{
        final UnitLocationFolder folder = createUnitLocationFolder("folder");
        this.locationService.createLocationFolder(folder, this.secondary.getUsername());
        Assert.assertNotNull(folder.getId());
    }

    /**
     * @throws EnMeDomainNotFoundException
     *
     */
    @Test
    public void testretrieveLocationFolderByUser() throws EnMeDomainNotFoundException{
         final UnitLocationFolder folder1 = createUnitLocationFolder("folder 1");
         this.locationService.createLocationFolder(folder1, this.secondary.getUsername());
         final UnitLocationFolder folder2 = createUnitLocationFolder("folder2 ");
         this.locationService.createLocationFolder(folder2, this.secondary.getUsername());
         final List<UnitLocationFolder> list = this.locationService.retrieveLocationFolderByUser(this.secondary.getUsername());
         Assert.assertEquals(list.size(), 2);
    }

    /**
     * test retrieveLocationSubFolderByUser.
     * @throws Exception
     */
    @Test
    public void testretrieveLocationSubFolderByUser() throws Exception{
        final CatLocationFolder locationFolder = createCatLocationFolder(LocationFolderType.GROUPING, this.secondary.getSecUser(), "folder", null);
        createCatLocationFolder(LocationFolderType.GROUPING, this.secondary.getSecUser(), "folder sub", locationFolder);
        final List<UnitLocationFolder> list = this.locationService.retrieveLocationSubFolderByUser(locationFolder.getLocationFolderId(), this.secondary.getUsername());
        Assert.assertEquals(list.size(), 1);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testupdateLocationMap() throws Exception{
        final CatLocationFolder locationFolder = createCatLocationFolder(LocationFolderType.GROUPING, this.secondary.getSecUser(), "folder", null);
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
     * @return the catLocation
     */
    public ICatLocation getCatLocation() {
        return catLocation;
    }

    /**
     * @param catLocation the catLocation to set
     */
    public void setCatLocation(ICatLocation catLocation) {
        this.catLocation = catLocation;
    }
}
