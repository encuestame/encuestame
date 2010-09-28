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

import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.domain.CatState;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 * Catalog State Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since April 26, 2009
 * @version $Id$
 */
@Repository
public class CatStateDaoImp extends AbstractHibernateDaoSupport implements ICatState {


    /**
     * Retrieve all states.
     * @return list of states
     */
    @SuppressWarnings("unchecked")
    public List<CatState> findAll() throws HibernateException {
        return getHibernateTemplate().find("from CatState");
    }

    /**
     * Get catalog state by id.
     * @param stateId id state
     * @return state {@link CatState}
     */
    public CatState getState(final Long stateId) throws HibernateException {
        return (CatState) getHibernateTemplate().get(CatState.class, stateId);
    }
}
