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
package org.encuestame.persistence.dao;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.PollDao;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.hibernate.HibernateException;

/**
 * {@link PollDao} Interface.
 * @author Morales,Diana Paola paolaATencuestame.org
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
      * @param userAcc
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> findAllPollByUserId(final UserAccount userAcc, final Integer maxResults, final Integer start);

     /**
      * Retrieve Poll by id.
      * @param pollId
      * @return
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
      * @param userAcc
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> getPollsByQuestionKeyword(final String keywordQuestion, final UserAccount userAcc,
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
      * @param secUser {@link UserAccount}.
      * @return list of folders.
      */
     List<PollFolder> getPollFolderBySecUser(final UserAccount secUser);

     /**
      * Get Polls by Folder.
      * @param userAcc
      * @param folder
      * @return
      */
     List<Poll> getPollsByPollFolder(final UserAccount userAcc, final PollFolder folder);

     /**
      * Get Polls by Folder Id.
      * @param userId
      * @param folder
      * @return
      */
     List<Poll> getPollsByPollFolderId(final UserAccount userId, final PollFolder folder);

     /**
      * Get PollFolder By poll id and user.
      * @param pollFolderId
      * @param userAcc
      * @return
      */
     PollFolder getPollFolderByIdandUser(final Long pollFolderId, final UserAccount userAcc);

     /**
      * Get Poll by id and user.
      * @param pollId
      * @param userAcc
      * @return
      */
     Poll getPollByIdandUserId(final Long pollId, UserAccount userAcc);

     /**
      * Get polls by creation date.
      * @param date
      * @param userAcc
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> getPollByUserIdDate(final Date date, final UserAccount userAcc,
            final Integer maxResults, final Integer start );

     /**
      * Retrieve polls by userId.
      * @param userAcc
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> retrievePollsByUserId(final UserAccount userAcc, final Integer maxResults, final Integer start);

     /**
      * Get max poll like votes by user.
      * @param userId
      * @param dateFrom
      * @param dateTo
      * @return
      */
     Long getMaxPollLikeVotesbyUser(final Long userId, final Date dateFrom, final Date dateTo);

     /**
      *
      * @param maxResults
      * @param start
      * @param range
      * @return
      */
     List<Poll> getPolls(final Integer maxResults,
             final Integer start, final Date range);
}
