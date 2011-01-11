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
package org.encuestame.persistence.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import org.encuestame.persistence.domain.security.Account;

/**
 * CatLocation.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "geoPoint")
public class GeoPoint {

    /** Id. **/
    private Long locateId;
    /** Type **/
    private GeoPointType tidtype;

    /** Description. **/
    private String locationDescription;

    /** Status. **/
    private Status locationStatus;

    /** Latitude **/
    private Float locationLatitude;

    /** Longitude **/
    private Float locationLongitude;

    /** Address. **/
    private String locationAddress;

    /** Country Code. **/
    private String locationCountryCode;

    /** Country Name. **/
    private String locationCountryName;

    /** Accurancy. **/
    private Integer locationAccuracy;

    /** User. **/
    private Account account;

    /** Location Folder. **/
    private GeoPointFolder catLocationFolder;

    /** Projects. **/
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
    @JoinColumn(name = "loc_id_type")
    public GeoPointType getTidtype() {
        return this.tidtype;
    }

    /**
     * @param tidtype tidtype
     */
    public void setTidtype(GeoPointType tidtype) {
        this.tidtype = tidtype;
    }

    /**
     * @return locationDescription
     */
    @Column(name = "description")
    public String getLocationDescription() {
        return this.locationDescription;
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
     * @return the secUsers
     */
    @ManyToOne()
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the locationStatus
     */
    @Column(name="location_status")
    @Enumerated(EnumType.STRING)
    public Status getLocationStatus() {
        return locationStatus;
    }

    /**
     * @param locationStatus the locationStatus to set
     */
    public void setLocationStatus(Status locationStatus) {
        this.locationStatus = locationStatus;
    }

    /**
     * @return the catLocationFolder
     */
    @ManyToOne(cascade=CascadeType.MERGE)
    public GeoPointFolder getCatLocationFolder() {
        return catLocationFolder;
    }

    /**
     * @param catLocationFolder the catLocationFolder to set
     */
    public void setCatLocationFolder(GeoPointFolder catLocationFolder) {
        this.catLocationFolder = catLocationFolder;
    }

    /**
     * @return the locationAddress
     */
    @Column(name = "address")
    public String getLocationAddress() {
        return locationAddress;
    }

    /**
     * @param locationAddress the locationAddress to set
     */
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    /**
     * @return the locationCountryCode
     */
    @Column(name = "country_code")
    public String getLocationCountryCode() {
        return locationCountryCode;
    }

    /**
     * @param locationCountryCode the locationCountryCode to set
     */
    public void setLocationCountryCode(String locationCountryCode) {
        this.locationCountryCode = locationCountryCode;
    }

    /**
     * @return the locationCountryName
     */
    @Column(name = "country_name")
    public String getLocationCountryName() {
        return locationCountryName;
    }

    /**
     * @param locationCountryName the locationCountryName to set
     */
    public void setLocationCountryName(String locationCountryName) {
        this.locationCountryName = locationCountryName;
    }

    /**
     * @return the locationAccuracy
     */
    @Column(name = "accuracy")
    public Integer getLocationAccuracy() {
        return locationAccuracy;
    }

    /**
     * @param locationAccuracy the locationAccuracy to set
     */
    public void setLocationAccuracy(Integer locationAccuracy) {
        this.locationAccuracy = locationAccuracy;
    }

    /**
     * @param locationDescription the locationDescription to set
     */
    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }
}
