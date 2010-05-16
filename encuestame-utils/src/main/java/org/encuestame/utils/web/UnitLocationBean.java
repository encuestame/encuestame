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

import java.io.Serializable;


/**
 * Unit Location Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 26/05/2009 12:40:46
 * @version $Id$
 */
public class UnitLocationBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -5463975566165976808L;
    private Long locateId;
    private Long tidtype;
    private String description;
    private Integer level;
    private String status;
    private Float lat;
    private Float lng;

    /**
     * @return the locateId
     */
    public Long getLocateId() {
        return locateId;
    }
    /**
     * @param locateId the locateId to set
     */
    public void setLocateId(Long locateId) {
        this.locateId = locateId;
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
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }
    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
    /**
     * @return the active
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param active the active to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the lat
     */
    public Float getLat() {
        return lat;
    }
    /**
     * @param lat the lat to set
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }
    /**
     * @return the lng
     */
    public Float getLng() {
        return lng;
    }
    /**
     * @param lng the lng to set
     */
    public void setLng(Float lng) {
        this.lng = lng;
    }
}
