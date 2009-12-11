/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
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
     * @return {@link LocationBean}
     */
    public LocationBean createLocationBean(
            final String active,
            final String description,
            final Float lng,
            final Float lat,
            final Integer level){
        final LocationBean locationBean = new LocationBean();
        locationBean.setActive(active);
        locationBean.setDescription(description);
        locationBean.setLat(lat);
        locationBean.setLng(lng);
        locationBean.setLevel(level);
        return locationBean;
    }
}
