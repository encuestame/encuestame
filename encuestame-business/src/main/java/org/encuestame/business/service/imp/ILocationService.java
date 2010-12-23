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
package org.encuestame.business.service.imp;

import java.util.List;

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoFolder;
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
     * @throws EnMeDomainNotFoundException
     */
    UnitLocationFolder createLocationFolder(final UnitLocationFolder locationFolder, final String username) throws EnMeDomainNotFoundException;

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
    UnitLocationBean createCatLocation(final UnitLocationBean location, final String username) throws EnMeExpcetion;

    /**
     * Assign Location to Location Folder.
     * @param location {@link GeoPoint}.
     */
    void assignLocationToLocationFolder(final GeoPoint location, final GeoFolder catLocationFolder);

    /**
     * Retrieve Locations Folder Items by Folder Id and User Id.
     * @param locationFolderId location folder id
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    List<UnitLocationBean> retrieveLocationFolderItemsById(final Long locationFolderId, final String username) throws EnMeDomainNotFoundException;

    /**
     * Retrieve Location Folders by User.
     * @param currentName username
     * @throws EnMeDomainNotFoundException
     */
    List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName) throws EnMeDomainNotFoundException;

    /**
     * Retrieve Location Sub Folders by User.
     * @param currentName
     * @throws EnMeDomainNotFoundException
     */
    List<UnitLocationFolder> retrieveLocationSubFolderByUser(final Long locationFolderId, final String currentUserName) throws EnMeDomainNotFoundException;

    /**
     * Get Location Item.
     * @param locationId location id
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    UnitLocationBean getLocationItem(final Long locationId, final String username) throws EnMeDomainNotFoundException;

    /**
     * Get Folder Location Detail.
     * @param folderLocationId folder location  Id.
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    UnitLocationFolder getFolderLocation(final Long folderLocationId, final String username) throws EnMeDomainNotFoundException;

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

      /**
       * Create Default Location Item.
       * @param locationFolder
       * @param username
       * @throws EnMeExpcetion
       */
      void createDefaultILocationItem(final UnitLocationFolder locationFolderBean, final String username)
             throws EnMeExpcetion;

      /**
       * Delete Location Folder.
       * @param unitLocationFolder
       * @param username
       * @throws EnMeExpcetion
       */
      void deleteLocationFolder(final UnitLocationFolder unitLocationFolder, final String username) throws EnMeExpcetion;

      /**
       * Delete Location Item.
       * @param unitLocationBean
       * @param username
       */
      void deleteLocationItem(final UnitLocationBean unitLocationBean, final String username) throws EnMeExpcetion;

      /**
       * Retrieve Locations Items by Username
       * @param username username
       * @return
     * @throws EnMeDomainNotFoundException
       */
      List<UnitLocationBean> retrieveLocationItemsByUsername(final String username) throws EnMeDomainNotFoundException;
}
