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
package org.encuestame.core.service.imp;

import java.util.List;

import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since May 15, 2010 8:19:02 PM
 */
public interface GeoLocationSupport {


    /**
     * Create Location Folder.
     * @param locationFolder {@link UnitLocationFolder}
     * @return {@link UnitLocationFolder}.
     * @throws EnMeNoResultsFoundException
     */
    UnitLocationFolder createGeoPointFolder(final UnitLocationFolder locationFolder) throws EnMeNoResultsFoundException;

    /**
     * Create Cat LocationType.
     * @param locatTypeBean {@link UnitLocationTypeBean}
     * @return locatTypeBean
     * @throws EnMeExpcetion exception
     */
    UnitLocationTypeBean createGeoPointType(
            final UnitLocationTypeBean locatTypeBean) throws EnMeExpcetion;

    /**
     * @param locationBean locationBean
     * @throws EnMeExpcetion EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    void updateGeoPoint(final UnitLocationBean locationBean) throws EnMeExpcetion, EnMeNoResultsFoundException;

    /**
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    void updateGeoPointType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion, EnMeNoResultsFoundException;

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    UnitLocationBean createGeoPoint(final UnitLocationBean location) throws EnMeExpcetion;

    /**
     * Assign Location to Location Folder.
     * @param location {@link GeoPoint}.
     */
    void assignLocationToLocationFolder(final GeoPoint location, final GeoPointFolder geoPointFolder);

    /**
     * Retrieve Locations Folder Items by Folder Id and User Id.
     * @param locationFolderId location folder id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UnitLocationBean> retrieveLocationFolderItemsById(final Long locationFolderId, final String username) throws EnMeNoResultsFoundException;

    /**
     * Retrieve Location Folders by User.
     * @param currentName username
     * @throws EnMeNoResultsFoundException
     */
    List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName) throws EnMeNoResultsFoundException;

    /**
     * Retrieve Location Sub Folders by User.
     * @param currentName
     * @throws EnMeNoResultsFoundException
     */
    List<UnitLocationFolder> retrieveLocationSubFolderByUser(final Long locationFolderId, final String currentUserName) throws EnMeNoResultsFoundException;

    /**
     * Get Location Item.
     * @param locationId location id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    UnitLocationBean getLocationItem(final Long locationId, final String username) throws EnMeNoResultsFoundException;

    /**
     * Get Folder Location Detail.
     * @param folderLocationId folder location  Id.
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    UnitLocationFolder getFolderLocation(final Long folderLocationId, final String username) throws EnMeNoResultsFoundException;

    /**
     * Update Location Map.
     * @param Latitude
     * @param Longitude
     * @param locationId
     * @param username
     * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
     void updateLocationMap(final UnitLocationBean locationBean, final Long locationId, final String username)
           throws EnMeExpcetion, EnMeNoResultsFoundException;

     /**
      * Update Location Name.
      * @param locationBean {@link UnitLocationBean}.
      * @param username username logged
      * @throws EnMeExpcetion exception
     * @throws EnMeNoResultsFoundException
      */
      void updateLocationName(final UnitLocationBean locationBean, final String username) throws EnMeNoResultsFoundException;

      /**
       * Update Location Folder.
       * @param locationBean
       * @param username
       * @param typeUpdate
       * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
       */
      void updateLocationFolder(final UnitLocationFolder locationFolderBean,
              final String username, final String typeUpdate)
              throws EnMeNoResultsFoundException;

      /**
       * Create Default Location Item.
       * @param locationFolder
       * @param username
       * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
       */
      void createDefaultILocationItem(final UnitLocationFolder locationFolderBean, final String username)
             throws EnMeNoResultsFoundException;

      /**
       * Delete Location Folder.
       * @param unitLocationFolder
       * @param username
       * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
       */
      void deleteLocationFolder(final UnitLocationFolder unitLocationFolder, final String username) throws EnMeNoResultsFoundException;

      /**
       * Delete Location Item.
       * @param unitLocationBean
       * @param username
     * @throws EnMeNoResultsFoundException
       */
      void deleteLocationItem(final UnitLocationBean unitLocationBean, final String username) throws EnMeNoResultsFoundException;

      /**
       * Retrieve Locations Items by Username
       * @param username username
       * @return
     * @throws EnMeNoResultsFoundException
       */
      List<UnitLocationBean> retrieveLocationItemsByUsername(final String username) throws EnMeNoResultsFoundException;
}
