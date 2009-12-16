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
package org.encuestame.test.config;

import java.util.Date;

import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.location.LocationTypeBean;
import org.encuestame.web.beans.project.UnitProjectBean;

/**
 * Abstract class for beans.
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 11:16:31
 * @version $Id$
 */
public abstract class AbstractBeanBaseTest extends AbstractBaseTest{

    /**
     * Create Project Bean.
     * @param projectName project bean.
     * @return {@link UnitProjectBean}
     */
    public UnitProjectBean createProjectBean(final String projectName){
        final UnitProjectBean projectBean = new UnitProjectBean();
        projectBean.setDateFinish(new Date());
        projectBean.setDateInit(new Date());
        projectBean.setDescription("description");
        projectBean.setState(createState("active").getIdState());
        projectBean.setName(projectName);
        return projectBean;
    }

    /**
     * Create Location Type Bean.
     * @param locationName name
     * @param level level
     * @return {@link LocationTypeBean}
     */
    public LocationTypeBean createLocationTypeBean(
            final String locationName,
            final Integer level){
        final LocationTypeBean locationTypeBean = new  LocationTypeBean();
      //  locationTypeBean.setDescription(locationName);
       // locationTypeBean.setLevel(level);
        return locationTypeBean;
    }


    /**
     * Create Location Bean.
     * @param active active
     * @param description description
     * @param lng longitud
     * @param lat latitud
     * @param level level
     * @param desc desc
     * @return {@link LocationBean}
     */
    public LocationBean createLocationBean(
            final String active,
            final String description,
            final Float lng,
            final Float lat,
            final Integer level,
            final String desc){
        final LocationBean locationBean = new LocationBean();
        locationBean.setActive(active);
        locationBean.setDescription(description);
        locationBean.setLat(lat);
        locationBean.setLng(lng);
        locationBean.setLevel(level);
        locationBean.setTidtype(createCatLocationType(desc).getLocationTypeId());
        return locationBean;
    }
}
