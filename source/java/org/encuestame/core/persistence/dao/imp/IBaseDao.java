/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
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
 */
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.hibernate.HibernateException;
/**
 * Interface Base Dao.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since April 23, 2009
 */
public interface IBaseDao {

    /**
     * 	Creates or updates a record in the table.
     * @param domain domain
     * @throws HibernateException exception
     */
    public void saveOrUpdate(final Object domain) throws HibernateException;

    /**
     * Deletes a table row.
     * @param domain domain
     * @throws HibernateException  exception
     */
    public void delete(final Object domain) throws HibernateException;

    /**
     * 	Return all the elements of an entity in Table.
     * @param query hql query
     * @return {@link List} of {@link Object}
     * @throws HibernateException  exception
     */
    public List<Object> findAll(final String query) throws HibernateException;


}
