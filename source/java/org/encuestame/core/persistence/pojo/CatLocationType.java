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

package org.encuestame.core.persistence.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CatLocationType.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "cat_location_type")
public class CatLocationType implements Serializable {

    private Long locationTypeId;
    private String locationTypeDescription;
    private Integer locationTypeLevel;

    /**
     * @return locationTypeId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loc_id_type", unique = true, nullable = false, length = 10)
    public Long getLocationTypeId() {
        return this.locationTypeId;
    }

    /**
     * @param locationTypeId locationTypeId
     */
    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    /**
     * @return locationTypeDescription
     */
    @Column(name = "description")
    public String getLocationTypeDescription() {
        return this.locationTypeDescription;
    }

    /**
     * @param locationTypeDescription locationTypeDescription
     */
    public void setLocationTypeDescription(String locationTypeDescription) {
        this.locationTypeDescription = locationTypeDescription;
    }

    /**
     * @return locationTypeLevel
     */
    @Column(name = "level")
    public Integer getLocationTypeLevel() {
        return this.locationTypeLevel;
    }

    /**
     * @param locationTypeLevel locationTypeLevel
     */
    public void setLocationTypeLevel(Integer locationTypeLevel) {
        this.locationTypeLevel = locationTypeLevel;
    }

}
