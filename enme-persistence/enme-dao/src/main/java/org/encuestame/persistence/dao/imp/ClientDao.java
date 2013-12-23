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

package org.encuestame.persistence.dao.imp;

import java.util.List;

import org.encuestame.persistence.dao.IClientDao;
import org.encuestame.persistence.domain.Client;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * {@link Client} Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since January 24, 2010
 * @version $Id$
 */
@Repository("clientDao")
public class ClientDao extends AbstractHibernateDaoSupport implements IClientDao {

    @Autowired
    public ClientDao(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Retrieve all clients.
     * @return list of clients.
     * @throws HibernateException exception
     */
    @SuppressWarnings("unchecked")
    public List findAll() throws HibernateException {
        return getHibernateTemplate().find("from Client");
    }

    /**
     * Find All {@link Client} by Project Id.
     * @param projectId project Id.
     * @return clients.
     */
    @SuppressWarnings("unchecked")
    public List findAllClientByProjectId(final Long projectId){
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
