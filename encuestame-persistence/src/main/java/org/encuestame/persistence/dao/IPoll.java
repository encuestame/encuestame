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
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.HibernateException;

/**
 * {@link PollDao} Interface.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since  March 15, 2009
 */
public interface IPoll extends IBaseDao {

     /**
      * Find All Polls by User Id.
      * @param userAcc
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> findAllPollByEditorOwner(final UserAccount userAcc, final Integer maxResults, final Integer start);

     /**
      * Retrieve Poll by id.
      * @param pollId
      * @return
      */
     Poll getPollById(final Long pollId);

     /**
      * Get the poll by id
      * @param pollId
      * @param slugQuestion
      * @return
      */
     Poll getPollById(final Long pollId, final String slugQuestion);

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
     List<PollFolder> getPollFolderByUserAccount(final UserAccount secUser);

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
     Poll getPollById(final Long pollId, UserAccount userAcc);

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

     /**
      * Retrieve poll mark as favorites.
      * @param userAccount user account
      * @param maxResults max of results.
      * @param start point start results.
      * @return List of {@link Poll}.
      */
     List<Poll> retrieveFavouritesPoll(
             final UserAccount userAccount,
             final Integer maxResults,
             final Integer start);

     /**
      *
      * @param userId
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> retrievePollToday(
             final Account owner,
             final Integer maxResults,
             final Integer start,
             final Date startDate);

     /**
      * Retrieve poll by date range.
      * @param owner
      * @param initDate
      * @param startDate
      * @param maxResults
      * @param start
      * @return
      */
     List<Poll> retrievePollByDate(
             final Account owner,
             final Date initDate,
             final Date startDate,
             final Integer maxResults,
             final Integer start);

     /**
      *
      * @param owner
      * @param maxResults
      * @param start
      * @param startDate
      * @return
      */
     List<Poll> retrievePollLastWeek(
             final Account owner,
             final Integer maxResults,
             final Integer start,
             final Date startDate);

     /**
      * Get total polls by user.
      * @param user
      * @param publishStatus
      * @return
      */
    Long getTotalPollsbyUser(final UserAccount user, final Boolean publishStatus);

    /**
     * Get poll by hashTag id.
     * @param tagName
     * @param startResults
     * @param limitResults
     * @param filterby
     * @return
     */
    List<Poll> getPollByHashTagName(final String tagName, final Integer startResults,
            final Integer limitResults, final TypeSearchResult filterby);

    /**
     * Get total polls by hashtag and date range.
     * @param tagName
     * @param period
     * @param startResults
     * @param limit
     * @return
     */
    List<Poll> getPollsbyHashTagNameAndDateRange(
            final String tagName, final Integer period,
            final Integer startResults, final Integer limit);
}
