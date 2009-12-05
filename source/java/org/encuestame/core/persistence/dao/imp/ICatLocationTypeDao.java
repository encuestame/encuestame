/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.hibernate.HibernateException;

/**
 * Interface for Catalog Location Type Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 12:36:43
 */
public interface ICatLocationTypeDao extends IBaseDao {

    /**
     * Find All Location.
     * @return list of all locations
     * @throws HibernateException hibernate
     */
    public List<CatLocationType> findAll() throws HibernateException;

    /**
     * Find Locate type by Id.
     * @param locaTypeId locate type id
     * @return {@link CatLocation}
     * @throws HibernateException excetion
     */
    public CatLocationType getLocationById(final Long locaTypeId) throws HibernateException;
}
