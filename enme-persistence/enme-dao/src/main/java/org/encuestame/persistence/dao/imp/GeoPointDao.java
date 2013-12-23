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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.encuestame.persistence.dao.IGeoPoint;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Catalog Location Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since  6/05/2009 14:45:54
 * @version $Id$
 */
@Repository(value = "geoPointDao")
public class GeoPointDao extends AbstractHibernateDaoSupport implements IGeoPoint {

    @Autowired
    public GeoPointDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Find All Location.
     * @return list of all locations
     * @throws HibernateException hibernate
     */
    @SuppressWarnings("unchecked")
    public List findAll() throws HibernateException {
          return getHibernateTemplate().find("from GeoPoint");
    }

    /**
     * Find Location by Id.
     * @param locateId locate id
     * @return {@link GeoPoint}
     * @throws HibernateException excetion
     */
    @SuppressWarnings("unchecked")
    public GeoPoint getLocationById(final Long locateId, final Long userId) {
        final DetachedCriteria criteria = DetachedCriteria.forClass(GeoPoint.class);
        criteria.add(Restrictions.eq("account.uid", userId));
        criteria.add(Restrictions.eq("locateId", locateId));
        return (GeoPoint) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * @param tidtype tidtype.
     * @return List of {@link GeoPoint}
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List getLocationByTypeLocationId(final Long tidType) throws HibernateException{
        final String queryLocation = "FROM GeoPoint WHERE tidtype.id  =?";
       return   getHibernateTemplate().find(queryLocation, tidType);
    }

    /**
     * @param locateId  locateId.
     * @return List of {@link GeoPoint} by Level
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List getLocationbyLevelId(final Integer locateId) throws HibernateException{
        final String queryLocationType ="FROM GeoPoint WHERE locationLevel = ?";
        return getHibernateTemplate().find(queryLocationType, locateId);
    }

    /**
     * Get Location Root Folders.
     * @param userId userId.
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getLocationFolders(final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(GeoPointFolder.class);
         criteria.add(Restrictions.eq("users.id", userId));
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
    public List getLocationFoldersByLocationFolderId(final Long locationFolderId, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(GeoPointFolder.class);
        criteria.add(Restrictions.eq("users.id", userId));
      //  criteria.add(Restrictions.eq("subLocationFolder.locationFolderId", locationFolderId));
      //  criteria.add(Restrictions.isNotNull("subLocationFolder"));
        return getHibernateTemplate().findByCriteria(criteria);
   }

    /**
     * Get Items by Location by Folder Id.
     * @param locationFolderId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getLocationByFolder(final Long locationFolderId, final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(GeoPoint.class);
         criteria.add(Restrictions.eq("geoPointFolder.id", locationFolderId));
         criteria.add(Restrictions.eq("account.uid", userId));
         return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get All Locations by User.
     * @param userId userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getLocationByUser(final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(GeoPoint.class);
        criteria.add(Restrictions.eq("account.uid", userId));
        return getHibernateTemplate().findByCriteria(criteria);
   }

    /**
     * Get LocationFolder by Id and User Id
     * @param locationFolderId folder id
     * @param userId userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public GeoPointFolder getLocationFolderByIdAndUserId(final Long locationFolderId, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(GeoPointFolder.class);
        criteria.add(Restrictions.eq("locationFolderId", locationFolderId));
        criteria.add(Restrictions.eq("users.uid", userId));
        return (GeoPointFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
   }

}
