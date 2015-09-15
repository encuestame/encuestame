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

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * Interface Base Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since April 23, 2009
 * @version $Id$
 */
public interface IBaseDao {

    /**
     * 	Creates or updates a record in the table.
     * @param domain domain
     * @throws HibernateException exception
     */
    void saveOrUpdate(final Object domain);

    /**
     *
     * @param obj
     * @throws HibernateException
     */
    void merge(final Object obj);

    /**
     * Deletes a table row.
     * @param domain domain
     * @throws HibernateException  exception
     */
    void delete(final Object domain);
    
    
    /**
     * Offer {@link HibernateTemplate} to services layer, only for test proposes.
     * @return {@link HibernateTemplate}
     */
    HibernateTemplate getHibernateTemplate();

}
