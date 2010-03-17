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
package org.encuestame.core.persistence.dao.imp;

import java.util.List;

import org.encuestame.core.persistence.dao.PollDao;
import org.encuestame.core.persistence.pojo.Poll;
import org.hibernate.HibernateException;

/**
 * {@link PollDao} Interface.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  March 15, 2009
 * @version $Id: $
 */
public interface IPoll extends IBaseDao {

      /**
     * Find All Poll.
     * @return list of all poll
     * @throws HibernateException hibernate
     */
     public List<Poll> findAll() throws HibernateException;

     /**
      * Find All Poll.
      * @return list of all poll
      * @throws HibernateException hibernate
      */
     public List<Poll> findAllPollByUserId(final Long userId) throws HibernateException;

     /**
      * Find All Poll.
      * @return list of all poll
      * @throws HibernateException hibernate
      */
     public Poll GetPollById(final Long pollId) throws HibernateException;

     /**
      * Count All Poll.
      * @return list of all poll
      * @throws HibernateException hibernate
      */
     public List<Object[]> retrieveResultPolls(final Long polliId, final Long questionId);
}
