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

package org.encuestame.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.security.Account;

/**
 * GeoPointType.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "geoPoint_type")
public class GeoPointType {

    /** Location Type Id. */
    private Long locationTypeId;

    /** Type Description **/
    private String locationTypeDescription;

    /** Type Level. **/
    private Integer locationTypeLevel;

    /** {@link Account}. **/
    private Account users;

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
    public void setLocationTypeId(final Long locationTypeId) {
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
    public void setLocationTypeDescription(final String locationTypeDescription) {
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
    public void setLocationTypeLevel(final Integer locationTypeLevel) {
        this.locationTypeLevel = locationTypeLevel;
    }

    /**
     * @return the users
     */
    @ManyToOne()
    public Account getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(final Account users) {
        this.users = users;
    }
}
