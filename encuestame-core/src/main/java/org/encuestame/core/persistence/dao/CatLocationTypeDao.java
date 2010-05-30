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

import org.encuestame.core.persistence.dao.imp.ICatLocationTypeDao;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 * Catalog Location Type Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 12:36:22
 * @version $Id$
 */
@Repository
public class CatLocationTypeDao extends AbstractHibernateDaoSupport implements ICatLocationTypeDao {

    /**
     * Find All {@link CatLocationType}
     * @return List of {@link CatLocationType}
     * @throws HibernateException hibernate exception.
     */
    @SuppressWarnings("unchecked")
    public List<CatLocationType> findAll() throws HibernateException {
        return getHibernateTemplate().find("from CatLocationType");
    }

    /**
     * Find Locate type by Id.
     * @param locaTypeId locate type id
     * @return {@link CatLocation}
     * @throws HibernateException excetion
     */
    public CatLocationType getLocationById(final Long locaTypeId) throws HibernateException {
        return (CatLocationType) getHibernateTemplate().get(CatLocationType.class,locaTypeId);
    }
}
