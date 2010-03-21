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
public class UnitLocationBean{

    private Long tid;
    private Long locationTypeId;
    private String descriptionLocation;
    private Integer level;
    private Integer state;
    private Float latitude;
    private Float longitude;
    private Boolean isGeo;
    private String active;
    /**
     * @return the tid
     */
    public Long getTid() {
        return tid;
    }
    /**
     * @param tid the tid to set
     */
    public void setTid(Long tid) {
        this.tid = tid;
    }
    /**
     * @return the locationTypeId
     */
    public Long getLocationTypeId() {
        return locationTypeId;
    }
    /**
     * @param locationTypeId the locationTypeId to set
     */
    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }
    /**
     * @return the descriptionLocation
     */
    public String getDescriptionLocation() {
        return descriptionLocation;
    }
    /**
     * @param descriptionLocation the descriptionLocation to set
     */
    public void setDescriptionLocation(String descriptionLocation) {
        this.descriptionLocation = descriptionLocation;
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
     * @return the state
     */
    public Integer getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return longitude;
    }
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    /**
     * @return the isGeo
     */
    public Boolean getIsGeo() {
        return isGeo;
    }
    /**
     * @param isGeo the isGeo to set
     */
    public void setIsGeo(Boolean isGeo) {
        this.isGeo = isGeo;
    }
    /**
     * @return  Active State
     */
    public String getActive() {
        return active;
    }
    /**
     * @param active  Active State
     */
    public void setActive(String active) {
        this.active = active;
    }
}
