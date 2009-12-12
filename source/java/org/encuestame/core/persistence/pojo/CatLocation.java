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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CatLocation.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "cat_location")
public class CatLocation {

    private Long locateId;
    private CatLocationType tidtype;
    private String locationDescription;
    private Integer locationLevel;
    private String locationActive;
    private Float locationLatitude;
    private Float locationLongitude;

    /**
     * @return locateId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locate_id", unique = true, nullable = false)
    public Long getLocateId() {
        return this.locateId;
    }

    /**
     * @param locateId locateId
     */
    public void setLocateId(Long locateId) {
        this.locateId = locateId;
    }

    /**
     * @return tidtype
     */
    @ManyToOne()
    @JoinColumn(name = "loc_id_type", nullable = false)
    public CatLocationType getTidtype() {
        return this.tidtype;
    }

    /**
     * @param tidtype tidtype
     */
    public void setTidtype(CatLocationType tidtype) {
        this.tidtype = tidtype;
    }

    /**
     * @return locationDescription
     */
    @Column(name = "description", nullable = false)
    public String getLocationDescription() {
        return this.locationDescription;
    }

    /**
     * @param locationDescription locationDescription
     */
    public void setlocationDescription(final String locationDescription) {
        this.locationDescription = locationDescription;
    }

    /**
     * @return locationLevel
     */
    @Column(name = "level", nullable = false)
    public int getLocationLevel() {
        return this.locationLevel;
    }

    /**
     * @param locationLevel locationLevel
     */
    public void setLocationLevel(final  int locationLevel) {
        this.locationLevel = locationLevel;
    }

    /**
     * @return locationActive
     */
    @Column(name = "active", length = 2)
    public String getLocationActive() {
        return this.locationActive;
    }

    /**
     * @param locationActive locationActive
     */
    public void setLocationActive(final String locationActive) {
        this.locationActive = locationActive;
    }

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
