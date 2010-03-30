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
package org.encuestame.utils.web;

/**
 * Location Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since  26/05/2009 12:58:17
 * @version $Id$
 **/
public class LocationBean{

    private Long locateId;
    private Long tidtype;
    private String description;
    private Integer level;
    private String active;
    private Float lat;
    private Float lng;


    /**
     * @return locateId
     */
    public final Long getLocateId() {
        return locateId;
    }
    /**
     * @param locateId locateId
     */
    public final void setLocateId(Long locateId) {
        this.locateId = locateId;
    }
    /**
     * @return description
     */
    public final String getDescription() {
        return description;
    }
    /**
     * @param description description
     */
    public final void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return level
     */
    public final Integer getLevel() {
        return level;
    }
    /**
     * @param level level
     */
    public final void setLevel(Integer level) {
        this.level = level;
    }
    /**
     * @return active
     */
    public final String getActive() {
        return active;
    }
    /**
     * @param active active
     */
    public final void setActive(String active) {
        this.active = active;
    }
    /**
     * @return lat
     */
    public final Float getLat() {
        return lat;
    }
    /**
     * @param lat lat
     */
    public final void setLat(Float lat) {
        this.lat = lat;
    }
    /**
     * @return lng
     */
    public final Float getLng() {
        return lng;
    }
    /**
     * @param lng lng
     */
    public final void setLng(Float lng) {
        this.lng = lng;
    }
    /**
     * @return the tidtype
     */
    public final Long getTidtype() {
        return tidtype;
    }
    /**
     * @param tidtype the tidtype to set
     */
    public final void setTidtype(Long tidtype) {
        this.tidtype = tidtype;
    }


}
