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

package org.encuestame.core.persistence.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * Session Filter in View
 * @author Picado, Juan juan@encuestame.org
 * @since 20/12/2009 15:20:19
 * @version $Id$
 */
public class SessionFilter extends OpenSessionInViewFilter {

    /*
     * The default mode is FlushMode.NEVER
     *
     * @see org.springframework.orm.hibernate.support.OpenSessionInViewFilter#getSession(net.sf.hibernate.SessionFactory)
     */
    protected Session getSession(SessionFactory sessionFactory)
        throws DataAccessResourceFailureException {
        Session session = super.getSession(sessionFactory);
        session.setFlushMode(FlushMode.AUTO);
        return session;
    }

}
