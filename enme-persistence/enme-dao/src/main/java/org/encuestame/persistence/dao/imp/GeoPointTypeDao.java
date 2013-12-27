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

import org.encuestame.persistence.dao.IGeoPointTypeDao;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointType;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Catalog Location Type Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since 05/12/2009 12:36:22
 * @version $Id$
 */
@Repository("geoPointTypeDao")
public class GeoPointTypeDao extends AbstractHibernateDaoSupport implements IGeoPointTypeDao {

    @Autowired
    public GeoPointTypeDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Find All {@link GeoPointType}
     * @return List of {@link GeoPointType}
     * @throws HibernateException hibernate exception.
     */
    @SuppressWarnings("unchecked")
    public List findAll() throws HibernateException {
        return getHibernateTemplate().find("from GeoPointType");
    }

    /**
     * Find Locate type by Id.
     * @param locaTypeId locate type id
     * @return {@link GeoPoint}
     * @throws HibernateException excetion
     */
    public GeoPointType getLocationById(final Long locaTypeId) throws HibernateException {
        return (GeoPointType) getHibernateTemplate().get(GeoPointType.class,locaTypeId);
    }
}
