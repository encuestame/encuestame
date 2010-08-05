/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Cat Location Folder.
 * This domain group
 * @author Picado, Juan juan@encuestame.org
 * @since May 15, 2010 10:23:15 PM
 * @version $Id:$
 */
@Entity
@Table(name = "cat_location_folder")
public class CatLocationFolder {

    /**
     * Id.
     */
    private Long locationFolderId;

    /**
     * Name.
     */
    private String locationFolderName;

    /**
     * {@link LocationFolderType}.
     */
    private LocationFolderType folderType;

    /**
     * {@link SecUser}.
     */
    private SecUser secUsers;

    /**
     * SubLocation Folder.
     */
    private CatLocationFolder subLocationFolder;

    /**
     * @return the locationFolderId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locate_folder_id", unique = true, nullable = false)
    public Long getLocationFolderId() {
        return locationFolderId;
    }

    /**
     * @param locationFolderId the locationFolderId to set
     */
    public void setLocationFolderId(final Long locationFolderId) {
        this.locationFolderId = locationFolderId;
    }

    /**
     * @return the locationFolderName
     */
    @Column(name = "name", nullable = false)
    public String getLocationFolderName() {
        return locationFolderName;
    }

    /**
     * @param locationFolderName the locationFolderName to set
     */
    public void setLocationFolderName(final String locationFolderName) {
        this.locationFolderName = locationFolderName;
    }

    /**
     * @return the folderType
     */
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    public LocationFolderType getFolderType() {
        return folderType;
    }

    /**
     * @param folderType the folderType to set
     */
    public void setFolderType(final LocationFolderType folderType) {
        this.folderType = folderType;
    }

    /**
     * @return the secUsers
     */
    @ManyToOne()
    public SecUser getSecUsers() {
        return secUsers;
    }

    /**
     * @param secUsers the secUsers to set
     */
    public void setSecUsers(final SecUser secUsers) {
        this.secUsers = secUsers;
    }

    /**
     * @return the subLocationFolder
     */
    @ManyToOne()
    public CatLocationFolder getSubLocationFolder() {
        return subLocationFolder;
    }

    /**
     * @param subLocationFolder the subLocationFolder to set
     */
    public void setSubLocationFolder(final CatLocationFolder subLocationFolder) {
        this.subLocationFolder = subLocationFolder;
    }
}
