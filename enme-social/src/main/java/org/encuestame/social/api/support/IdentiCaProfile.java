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
package org.encuestame.social.api.support;

import java.io.Serializable;
import java.util.Date;

/**
 * Model class representing a Identica user's profile information.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class IdentiCaProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String screenName;
    private String name;
    private String url;
    private String profileImageUrl;
    private String description;
    private String location;
    private Date createdDate;

    /**
     * The user's Twitter ID
     *
     * @return The user's Twitter ID
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * The user's Twitter screen name
     *
     * @return The user's Twitter screen name
     */
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * The user's full name
     *
     * @return The user's full name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The user's URL
     *
     * @return The user's URL
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * The user's description
     *
     * @return The user's description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The user's location
     *
     * @return The user's location
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * <p>
     * The URL of the user's profile image in "normal" size (48x48).
     * </p>
     *
     * @return The URL of the user's normal-sized profile image.
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * <p>
     * The URL of the user's profile.
     * </p>
     *
     * @return The URL of the user's profile.
     */
    public String getProfileUrl() {
        return "http://www.twitter.com/" + screenName;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * The date that the Twitter profile was created.
     *
     * @return The date that the Twitter profile was created.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IdentiCaProfile [id=" + id + ", screenName=" + screenName
                + ", name=" + name + ", url=" + url + ", profileImageUrl="
                + profileImageUrl + ", description=" + description
                + ", location=" + location + ", createdDate=" + createdDate
                + "]";
    }
}
