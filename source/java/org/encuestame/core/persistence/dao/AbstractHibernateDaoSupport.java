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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Abstract Base Dao Class extend Spring class {@link HibernateDaoSupport}
 * @author Picado, Juan juan@encuestame.org
 * @since October 30, 2009
 */
public abstract class AbstractHibernateDaoSupport extends HibernateDaoSupport {

     protected Log log = LogFactory.getLog(this.getClass());

     /**
      * Save or Create entity.
      * @param obj obj
      * @throws HibernateException hibernate
      */
     public void saveOrUpdate(final Object obj) throws HibernateException {
         getHibernateTemplate().saveOrUpdate(obj);
     }

     /**
      * Delete object.
      * @param obj
      * @throws HibernateException
      */
     public void delete(Object obj) throws HibernateException {
          getHibernateTemplate().delete(obj);
     }

     /**
      * Find All Users.
      * @return list of all users
      * @throws HibernateException hibernate
      */
     public List findAll(String query) throws HibernateException {
         return getHibernateTemplate().find(query);
     }

}
