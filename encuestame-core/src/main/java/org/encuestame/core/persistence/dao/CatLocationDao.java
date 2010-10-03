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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.domain.CatLocation;
import org.encuestame.core.persistence.domain.CatLocationFolder;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Catalog Location Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since  6/05/2009 14:45:54
 * @version $Id$
 */
@Repository
public class CatLocationDao extends AbstractHibernateDaoSupport implements ICatLocation {

    /**
     * Find All Location.
     * @return list of all locations
     * @throws HibernateException hibernate
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> findAll() throws HibernateException {
          return getHibernateTemplate().find("from CatLocation");
    }

    /**
     * Find Location by Id.
     * @param locateId locate id
     * @return {@link CatLocation}
     * @throws HibernateException excetion
     */
    @SuppressWarnings("unchecked")
    public CatLocation getLocationById(final Long locateId, final Long userId) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(CatLocation.class);
        criteria.add(Restrictions.eq("secUsers.uid", userId));
        criteria.add(Restrictions.eq("locateId", locateId));
        return (CatLocation) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * @param tidtype tidtype.
     * @return List of {@link CatLocation}
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationByTypeLocationId(final Long tidType) throws HibernateException{
        final String queryLocation = "FROM CatLocation WHERE tidtype.id  =?";
       return   getHibernateTemplate().find(queryLocation, tidType);
    }

    /**
     * @param locateId  locateId.
     * @return List of {@link CatLocation} by Level
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationbyLevelId(final Integer locateId) throws HibernateException{
        final String queryLocationType ="FROM CatLocation WHERE locationLevel = ?";
        return getHibernateTemplate().find(queryLocationType, locateId);
    }

    /**
     * Get Location Folders.
     * @param userId userId.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatLocationFolder> getLocationFolders(final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(CatLocationFolder.class);
         criteria.add(Restrictions.eq("secUsers.id", userId));
         criteria.add(Restrictions.isNull("subLocationFolder"));
         return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Locations Folders Childrens by Location Folder Id.
     * @param locationFolderId id
     * @param userId userId.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatLocationFolder> getLocationFoldersByLocationFolderId(final Long locationFolderId, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(CatLocationFolder.class);
        criteria.add(Restrictions.eq("secUsers.id", userId));
        criteria.add(Restrictions.eq("subLocationFolder.locationFolderId", locationFolderId));
        criteria.add(Restrictions.isNotNull("subLocationFolder"));
        return getHibernateTemplate().findByCriteria(criteria);
   }

    /**
     * Get Items by Location by Folder Id.
     * @param locationFolderId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationByFolder(final Long locationFolderId, final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(CatLocation.class);
         criteria.add(Restrictions.eq("catLocationFolder.id", locationFolderId));
         criteria.add(Restrictions.eq("secUsers.uid", userId));
         return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get All Locations by User.
     * @param userId userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationByUser(final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(CatLocation.class);
        criteria.add(Restrictions.eq("secUsers.uid", userId));
        return getHibernateTemplate().findByCriteria(criteria);
   }

    /**
     * Get LocationFolder by Id and User Id
     * @param locationFolderId folder id
     * @param userId userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public CatLocationFolder getLocationFolderByIdAndUserId(final Long locationFolderId, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(CatLocationFolder.class);
        criteria.add(Restrictions.eq("locationFolderId", locationFolderId));
        criteria.add(Restrictions.eq("secUsers.uid", userId));
        return (CatLocationFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
   }

}
