/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Provides geo locations support domain.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 25, 2011
 */
@MappedSuperclass
public abstract class AbstractGeoPoint {

    /** Latitude **/
    private Float locationLatitude;

    /** Longitude **/
    private Float locationLongitude;

    /**
     * @return locationLatitude
     */
    @Column(name = "lat", precision = 10, scale = 6)
    public Float getLocationLatitude() {
        return this.locationLatitude;
    }

    /**
     * @param locationLatitude locationLatitude
     */
    public void setLocationLatitude(final Float locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    /**
     * @return locationLongitude
     */
    @Column(name = "lng", precision = 10, scale = 6)
    public Float getLocationLongitude() {
        return this.locationLongitude;
    }

    /**
     * @param locationLongitude locationLongitude
     */
    public void setLocationLongitude(final Float locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

}
