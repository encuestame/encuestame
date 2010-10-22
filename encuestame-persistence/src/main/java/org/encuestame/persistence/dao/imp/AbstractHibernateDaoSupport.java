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
package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Abstract Base Dao Class extend Spring class {@link HibernateDaoSupport}
 * @author Picado, Juan juan@encuestame.org
 * @since October 30, 2009
 * @version $Id$
 */
public abstract class AbstractHibernateDaoSupport extends HibernateDaoSupport {

     protected Log log = LogFactory.getLog(this.getClass());

     protected Session session = null;

     /**
      * Save or Create entity.
      * @param obj obj
      * @throws HibernateException hibernate
      */
     public void saveOrUpdate(final Object obj) throws HibernateException {
         getHibernateTemplate().saveOrUpdate(obj);
         getSession().flush();
     }

     /**
      * Delete object.
      * @param obj domain
      * @throws HibernateException exception
      */
     public void delete(Object obj) throws HibernateException {
          getHibernateTemplate().delete(obj);
          getSession().flush();
     }
}
