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
package org.encuestame.core.persistence.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    private Set<Project> projects = new HashSet<Project>();


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

    /**
     * @return the projects
     */
    @ManyToMany()
    @JoinTable(name="project_locations",
              joinColumns={@JoinColumn(name="cat_id_loc")},
              inverseJoinColumns={@JoinColumn(name="cat_id_project")})
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(final Set<Project> projects) {
        this.projects = projects;
    }

    /**
     * @param locationDescription the locationDescription to set
     */
    public void setLocationDescription(final String locationDescription) {
        this.locationDescription = locationDescription;
    }

    /**
     * @param locationLevel the locationLevel to set
     */
    public void setLocationLevel(final Integer locationLevel) {
        this.locationLevel = locationLevel;
    }
}
