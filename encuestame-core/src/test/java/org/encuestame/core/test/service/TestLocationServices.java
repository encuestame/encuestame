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
package org.encuestame.core.test.service;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.domain.CatLocation;
import org.encuestame.core.persistence.domain.SecUserSecondary;
import org.encuestame.core.service.ILocationService;
import org.encuestame.core.service.LocationService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.test.service.config.AbstractBaseUnitBeans;
import org.encuestame.utils.web.UnitLocationBean;
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
}
