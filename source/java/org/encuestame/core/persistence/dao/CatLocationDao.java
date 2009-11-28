package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.hibernate.HibernateException;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Teams
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: CatLocationDao.java Date: 26/05/2009 14:45:54
 * @author juanpicado
 * package: org.encuestame.core.persistence.dao.imp
 * @version 1.0
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

    public CatLocation getLocationById(final Long locateId) throws HibernateException{
        return (CatLocation) getSession().get(CatLocation.class,locateId);
    }
}
