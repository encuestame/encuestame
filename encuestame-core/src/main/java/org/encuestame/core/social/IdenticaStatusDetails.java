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
package org.encuestame.core.social;

import java.util.HashMap;
import java.util.Map;

/**
 * Carries optional metadata pertaining to a Twitter status update.
 */
public class IdenticaStatusDetails {
    private Float latitude;
    private Float longitude;
    private boolean displayCoordinates;

    /**
     * Sets the location of the status update in latitude and longitude.
     * @return The {@link IdenticaStatusDetails} object
     */
    public IdenticaStatusDetails setLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

    /**
     * Indicates that Twitter should pinpoint the location precisely when
     * displaying it on a map. By default, Twitter will display the status along
     * with a map showing the general area where the tweet came from. If display
     * coordinates is true, however, it will display a map with a pin indicating
     * the precise location of the status update.
     *
     * @param displayCoordinates
     *            If true, will pinpoint the location of the status update.
     * @return The {@link IdenticaStatusDetails} object
     */
    public IdenticaStatusDetails setDisplayCoordinates(boolean displayCoordinates) {
        this.displayCoordinates = displayCoordinates;
        return this;
    }

    /**
     * Maps the {@link IdenticaStatusDetails} values to a Map of Twitter parameters.
     *
     * @return A {@link Map} of parameters to be passed along in the status
     *         update post to Twitter.
     */
    public Map<String, Object> toParameterMap() {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        if (latitude != null && longitude != null) {
            parameterMap.put("lat", latitude.toString());
            parameterMap.put("long", longitude.toString());
        }

        if (displayCoordinates) {
            parameterMap.put("display_coordinates", "true");
        }
        return parameterMap;
    }
}
