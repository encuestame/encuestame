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

import org.encuestame.persistence.dao.imp.PollDao;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
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
      * Find All Polls by User Id.
      * @param userId
      * @param maxResults
      * @param start
      * @return
      */
     public List<Poll> findAllPollByUserId(final Long userId, final Integer maxResults, final Integer start);

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
      * Get Polls by question keyword.
      * @param keywordQuestion
      * @param UserId
      * @param maxResults
      * @param start
      * @return
      */
      List<Poll> getPollsByQuestionKeyword(final String keywordQuestion, final Long UserId,
             final Integer maxResults,
             final Integer start);

     /**
      * GetPoll Folder ById.
      * @param folderId
      * @return
      */
     PollFolder getPollFolderById(final Long folderId);

     /**
      * Poll Folder.
      * @param secUser {@link Account}.
      * @return list of folders.
      */
     List<IFolder> getPollFolderBySecUser(final Account secUser);

     /**
      * Get Polls by Folder.
      * @param Account
      * @param folder
      * @return
      */
     List<Poll> getPollsByPollFolder(final Account userId, final PollFolder folder);

     /**
      * Get Polls by Folder Id.
      * @param userId
      * @param folder
      * @return
      */
     List<Poll> getPollsByPollFolderId(final Long userId, final PollFolder folder);
}
