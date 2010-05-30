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

package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.IClientDao;
import org.encuestame.core.persistence.pojo.Client;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 * {@link Client} Dao.
 * @author Picado, Juan juan@encuestame.org
 * @since January 24, 2010
 * @version $Id$
 */
@Repository
public class ClientDao extends AbstractHibernateDaoSupport implements IClientDao {


    /**
     * Retrieve all clients.
     * @return list of clients.
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List<Client> findAll() throws HibernateException {
        return getHibernateTemplate().find("from Client");
    }

    /**
     * Find All {@link Client} by Project Id.
     * @param projectId project Id.
     * @return clients.
     */
    @SuppressWarnings("unchecked")
    public List<Client> findAllClientByProjectId(final Long projectId){
        return getHibernateTemplate().findByNamedParam("from Client c where c.project.id = :projectId", "projectId", projectId);
    }

    /**
     * Get {@link Client} by id.
     * @param clientId client id
     * @return client
     * @throws HibernateException exception
     */
    public Client getClientById(final Long clientId) throws HibernateException {
        return (Client) getHibernateTemplate().get(Client.class, clientId);
    }
}
