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
package org.encuestame.utils.web;

import java.io.Serializable;

/**
 * Unit Location Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since May 26 2009 12:40:46
 */
public class UnitLocationBean extends AbstractUnitLocation implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -5463975566165976808L;
    private Long tidtype;
    private Integer level;
    private String status;
    private Float lat;
    private Float lng;
    /** Address. **/
    private String address;
    /** Country Code. **/
    private String countryCode;

    /** Country Name. **/
    private String countryName;

    /** Accurancy. **/
    private Integer accuracy;

    /**
     * @return the tidtype
     */
    public Long getTidtype() {
        return tidtype;
    }
    /**
     * @param tidtype the tidtype to set
     */
    public void setTidtype(final Long tidtype) {
        this.tidtype = tidtype;
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
    public void setLevel(final Integer level) {
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
    public void setStatus(final String status) {
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
    public void setLat(final Float lat) {
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
    public void setLng(final Float lng) {
        this.lng = lng;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }
    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }
    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }
    /**
     * @return the accuracy
     */
    public Integer getAccuracy() {
        return accuracy;
    }
    /**
     * @param accuracy the accuracy to set
     */
    public void setAccuracy(final Integer accuracy) {
        this.accuracy = accuracy;
    }
}
