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
package org.encuestame.web.beans.location;

import java.io.Serializable;

import org.encuestame.web.beans.MasterBean;

/**
 * Location Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since  26/05/2009 12:58:17
 * @version $Id$
 **/
public class LocationBean extends MasterBean implements Serializable{

    private static final long serialVersionUID = -9098305021342831224L;
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
    public Long getLocateId() {
        return locateId;
    }
    /**
     * @param locateId locateId
     */
    public void setLocateId(Long locateId) {
        this.locateId = locateId;
    }
    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return level
     */
    public Integer getLevel() {
        return level;
    }
    /**
     * @param level level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
    /**
     * @return active
     */
    public String getActive() {
        return active;
    }
    /**
     * @param active active
     */
    public void setActive(String active) {
        this.active = active;
    }
    /**
     * @return lat
     */
    public Float getLat() {
        return lat;
    }
    /**
     * @param lat lat
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }
    /**
     * @return lng
     */
    public Float getLng() {
        return lng;
    }
    /**
     * @param lng lng
     */
    public void setLng(Float lng) {
        this.lng = lng;
    }
    /**
     * @return the tidtype
     */
    public Long getTidtype() {
        return tidtype;
    }
    /**
     * @param tidtype the tidtype to set
     */
    public void setTidtype(Long tidtype) {
        this.tidtype = tidtype;
    }


}
