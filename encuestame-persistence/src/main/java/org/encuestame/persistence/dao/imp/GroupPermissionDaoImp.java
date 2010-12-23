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

import org.encuestame.persistence.dao.IGroupPermissionDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Group Permission Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since February 8, 2009
 * @version $Id$
 */
@Repository("groupPermissionDao")
public class GroupPermissionDaoImp extends AbstractHibernateDaoSupport implements
        IGroupPermissionDao {

	@Autowired
	public GroupPermissionDaoImp(SessionFactory sessionFactory) {
	 		setSessionFactory(sessionFactory);
    }
	
    /**
     * Find all Groups Permissions.
     * @return list of groups permissions.
     * @throws HibernateException
     */
    //public List<SecGroupPermission> findAll() throws HibernateException {
    //    return super.findAll("from SecGroupPermission");
    //}
}
