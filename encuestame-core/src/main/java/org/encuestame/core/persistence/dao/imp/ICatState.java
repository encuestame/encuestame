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
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.pojo.CatState;
import org.hibernate.HibernateException;

/**
 * Interface to implement Catalog State Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since April 26, 2009
 * @version $Id$
 */
public interface ICatState extends IBaseDao {


    /**
     * Retrieve all states.
     * @return list of states
     * @throws HibernateException  Exception
     */
     List<CatState> findAll();

     /**
      * Get catalog state by id.
      * @param stateId id state
      * @return state {@link CatState}
     * @throws HibernateException  Exception
      */
     CatState getState(final Long stateId);

}
