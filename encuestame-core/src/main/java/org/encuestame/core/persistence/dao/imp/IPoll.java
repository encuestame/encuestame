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
import org.encuestame.core.persistence.domain.Poll;
import org.encuestame.core.persistence.domain.PollFolder;
import org.encuestame.core.persistence.domain.SecUser;
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
     List<Poll> findAll();

     /**
      * Find All Poll.
      * @return list of all poll
      * @throws HibernateException hibernate
      */
     List<Poll> findAllPollByUserId(final Long userId);

     /**
      * Find All Poll.
      * @return list of all poll
      * @throws HibernateException hibernate
      */
     Poll getPollById(final Long pollId);

     /**
      * Count All Poll.
      * @return list of all poll
      * @throws HibernateException hibernate
      */
     List<Object[]> retrieveResultPolls(final Long polliId, final Long questionId);

     /**
      * Get Polls By Question Keyword
      * @param keywordQuestion
      * @return list Polls
      */
     List<Poll> getPollsByQuestionKeyword(final String keywordQuestion);

     /**
      * GetPoll Folder ById.
      * @param folderId
      * @return
      */
     PollFolder getPollFolderById(final Long folderId);

     /**
      * Poll Folder.
      * @param secUser {@link SecUser}.
      * @return list of folders.
      */
     List<IFolder> getPollFolderBySecUser(final SecUser secUser);
}
