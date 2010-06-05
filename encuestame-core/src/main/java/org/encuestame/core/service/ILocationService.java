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
package org.encuestame.core.service;

import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationFolder;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;

/**
 * Description Class.
 * @author Picado, Juan juan@encuestame.org
 * @since May 15, 2010 8:19:02 PM
 * @version $Id: $
 */
public interface ILocationService {


    /**
     * Create Location Folder.
     * @param locationFolder {@link UnitLocationFolder}
     * @return {@link UnitLocationFolder}.
     */
    UnitLocationFolder createLocationFolder(final UnitLocationFolder locationFolder, final String username);

    /**
     * Create Cat LocationType.
     * @param locatTypeBean {@link UnitLocationTypeBean}
     * @return locatTypeBean
     * @throws EnMeExpcetion exception
     */
    UnitLocationTypeBean createCatLocationType(
            final UnitLocationTypeBean locatTypeBean, final String username) throws EnMeExpcetion;

    /**
     * @param locationBean locationBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    void updateCatLocation(final UnitLocationBean locationBean, final String username) throws EnMeExpcetion;

    /**
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    void updateCatLocationType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion;

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    UnitLocationBean createCatLocation(final UnitLocationBean location) throws EnMeExpcetion;

    /**
     * Assign Location to Location Folder.
     * @param location {@link CatLocation}.
     */
    void assignLocationToLocationFolder(final CatLocation location, final CatLocationFolder catLocationFolder);

    /**
     * Retrieve Locations Folder Items by Folder Id and User Id.
     * @param locationFolderId location folder id
     * @param username username
     * @return
     */
    List<UnitLocationBean> retrieveLocationFolderItemsById(final Long locationFolderId, final String username);

    /**
     * Retrieve Location Folders by User.
     * @param currentName username
     */
    List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName);

    /**
     * Retrieve Location Sub Folders by User.
     * @param currentName
     */
    List<UnitLocationFolder> retrieveLocationSubFolderByUser(final Long locationFolderId, final String currentUserName);

    /**
     * Get Location Item.
     * @param locationId location id
     * @param username username
     * @return
     */
    UnitLocationBean getLocationItem(final Long locationId, final String username);

    /**
     * Get Folder Location Detail.
     * @param folderLocationId folder location  Id.
     * @param username username
     * @return
     */
    UnitLocationFolder getFolderLocation(final Long folderLocationId, final String username);

    /**
     * Update Location Map.
     * @param Latitude
     * @param Longitude
     * @param locationId
     * @param username
     * @throws EnMeExpcetion
     */
     void updateLocationMap(final UnitLocationBean locationBean, final Long locationId, final String username)
           throws EnMeExpcetion;

     /**
      * Update Location Name.
      * @param locationBean {@link UnitLocationBean}.
      * @param username username logged
      * @throws EnMeExpcetion exception
      */
      void updateLocationName(final UnitLocationBean locationBean, final String username) throws EnMeExpcetion;

      /**
       * Update Location Folder.
       * @param locationBean
       * @param username
       * @param typeUpdate
       * @throws EnMeExpcetion
       */
      void updateLocationFolder(final UnitLocationFolder locationFolderBean,
              final String username, final String typeUpdate)
              throws EnMeExpcetion;
}
