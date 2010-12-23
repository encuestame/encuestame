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
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoFolder;
import org.hibernate.HibernateException;
/**
 * Interface to implement Catalog Location Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since  26/04/2009
 * @version $Id$
 */
public interface IGeoPoint extends IBaseDao {

    /**
     * Find All Location.
     * @return list of all locations
     * @throws HibernateException hibernate
     */
    List<GeoPoint> findAll();


    /**
     * Find Location by Id.
     * @param locateId locate id
     * @return {@link GeoPoint}
     * @throws HibernateException excetion
     */
    GeoPoint getLocationById(final Long locateId, final Long userId);

    /**
     * @param locateId locateId
     * @return aa
      * @throws HibernateException HibernateException
     */
    List<GeoPoint> getLocationbyLevelId(final Integer locateId);

    /**
     * @param tidtype tidtype
     * @return aa
     * @throws HibernateException HibernateException
     */
    List<GeoPoint> getLocationByTypeLocationId(final Long tidtype);

    /**
     * Get Location Folders.
     * @param userId userId.
     * @return
     */
    List<GeoFolder> getLocationFolders(final Long userId);

    /**
     * Get Items by Location by Folder Id.
     * @param locationFolderId location folder id.
     * @return
     */
    List<GeoPoint> getLocationByFolder(final Long locationFolderId, final Long userId);

    /**
     * Get Locations Folders Childrens by Location Folder Id.
     * @param locationFolderId id
     * @param userId userId.
     * @return
     */
    List<GeoFolder> getLocationFoldersByLocationFolderId(final Long locationFolderId, final Long userId);

    /**
     * Get LocationFolder by Id and User Id
     * @param locationFolderId folder id
     * @param userId userId
     * @return
     */
    GeoFolder getLocationFolderByIdAndUserId(final Long locationFolderId, final Long userId);

    /**
     * Get All Locations by User.
     * @param userId userId
     * @return
     */
    List<GeoPoint> getLocationByUser(final Long userId);

}
