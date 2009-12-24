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
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.hibernate.HibernateException;

/**
 * Catalog Location Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since  6/05/2009 14:45:54
 * @version $Id$
 */
public class CatLocationDao extends AbstractHibernateDaoSupport implements ICatLocation {

    /**
     * Find All Location.
     * @return list of all locations
     * @throws HibernateException hibernate
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> findAll() throws HibernateException {
        return super.findAll("from CatLocation");
    }

    /**
     * Find Location by Id.
     * @param locateId locate id
     * @return {@link CatLocation}
     * @throws HibernateException excetion
     */
    public CatLocation getLocationById(final Long locateId) throws HibernateException {
        return (CatLocation) getHibernateTemplate().get(CatLocation.class,locateId);
    }

    /**
     * @param tidtype tidtype.
     * @return List of {@link CatLocation}
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationByTypeLocationId(final Long tidtype) throws HibernateException{
        final String queryLocation = "FROM CatLocation WHERE tidtype.id  =?";
       return   getHibernateTemplate().find(queryLocation,tidtype);
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

    }
