/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import java.util.List;

import org.encuestame.persistence.domain.Client;
import org.hibernate.HibernateException;

/**
 * Clien Interfaces.
 * @author Picado, Juan juan@encuestame.org
 * @since Jan 24, 2010 4:27:52 PM
 * @version $Id$
 */
public interface IClientDao extends IBaseDao {

    /**
     * Retrieve all clients.
     * @return list of clients.
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    List<Client> findAll();



    /**
     * Find All {@link Client} by Project Id.
     * @param projectId project Id.
     * @return clients.
     */
    @SuppressWarnings("unchecked")
    List<Client> findAllClientByProjectId(final Long projectId);


    /**
     * Get {@link Client} by id.
     * @param clientId client id
     * @return client
     * @throws HibernateException exception
     */
    Client getClientById(final Long clientId);

}
