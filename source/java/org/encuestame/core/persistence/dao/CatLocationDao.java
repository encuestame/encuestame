/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
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
 * this program; if not, writes to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.hibernate.HibernateException;

/**
 * Catalog Location Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since  6/05/2009 14:45:54
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
        return (CatLocation) getSession().get(CatLocation.class,locateId);
    }

    /**
     * @param tidtype tidtype.
     * @return List of {@link CatLocation}
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationByTypeLocationId(final Long tidtype) throws HibernateException{
        return getSession().createQuery("FROM CatLocation WHERE tidtype.id = :typeLocId")
        .setParameter("typeLocId", tidtype)
        .list();
    }

    /**
     * @param locateId  locateId.
     * @return List of {@link CatLocation} by Level
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<CatLocation> getLocationbyLevelId(final Long locateId) throws HibernateException{
        return getSession().createQuery("FROM CatLocation WHERE locationLevel = :locateId")
        .setParameter("locateId", locateId)
        .list();


    }
    }
