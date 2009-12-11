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
package org.encuestame.web.beans.location;

import java.io.Serializable;

import org.encuestame.web.beans.MasterBean;

/**
 * Location Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since  26/05/2009 12:58:17
 * File name: $HeadURL$
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
public class LocationBean extends MasterBean implements Serializable{

    private static final long serialVersionUID = -9098305021342831224L;
    private Long locateId;
    //private String tidtype;
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


}
