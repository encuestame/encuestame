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
package org.encuestame.persistence.dao;

import java.util.List;

import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointType;
import org.hibernate.HibernateException;

/**
 * Interface for Catalog Location Type Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since 05/12/2009 12:36:43
 * @version $Id$
 */
public interface IGeoPointTypeDao extends IBaseDao {

    /**
     * Find All Location.
     * @return list of all locations
     * @throws HibernateException hibernate
     */
    List<GeoPointType> findAll() ;

    /**
     * Find Locate type by Id.
     * @param locaTypeId locate type id
     * @return {@link GeoPoint}
     * @throws HibernateException excetion
     */
     GeoPointType getLocationById(final Long locaTypeId);
}
