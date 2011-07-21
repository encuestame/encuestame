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
package org.encuestame.business.service.imp;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.FolderBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPoll;

/**
 * Poll Service Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since May 16, 2010
 * @version $Id: $
 */
public interface IPollService extends IMasterSurveyService{

   /**
    *	Create Poll
    * @param pollBean
    * @param currentUser
    * @param Question
    * @throws Exception
    */
    void createPoll(final UnitPoll pollBean, final String currentUser,
                final Question question) throws EnMeExpcetion;

    /**
    * List Poll by Question.
    * @param currentUser currentUser
    * @param keyword Question keyword
    * @return
    */
    List<UnitPoll> listPollbyQuestionKeyword(final String currentUser, final String keyword, final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException ;

   /**
    * List Poll by User Id.
    * @param currentUser
    * @param maxResults
    * @param start
    * @return
    * @throws EnMeNoResultsFoundException
    */
    List<UnitPoll> listPollByUser(final String currentUser,
        final Integer maxResults,
        final Integer start) throws EnMeNoResultsFoundException;

    /**
    * Update Question Poll.
    * @param unitQuestionPoll
    * @throws EnMeExpcetion exception
    */
    void updateQuestionPoll(final QuestionBean unitQuestionPoll) throws EnMeExpcetion;

    /**
    * Create Url Poll.
    * @param domain
    * @param hashUrl
    * @param currentUser
    * @return
    */
    String createUrlPoll(final String domain, final String hashUrl, final String currentUser);

    /**
    * Public Poll by List.
    * @param urlPoll
    * @param emailList
    */
    void publicPollByList(final String urlPoll , final UnitLists emailList);

    /**
    * Remove PollFolder.
    * @param folderId
    * @throws EnMeNoResultsFoundException
    */
    void removePollFolder(final Long folderId) throws EnMeNoResultsFoundException;

    /**
    * Create Poll Folder.
    * @param folderName
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException
    */
    FolderBean createPollFolder(final String folderName, final String username) throws EnMeNoResultsFoundException;

    /**
    * Update FolderName.
    * @param folderId
    * @param newFolderName
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException
    */
    public FolderBean updateFolderName(final Long folderId,
          final String newFolderName,
          final String username) throws EnMeNoResultsFoundException;

    /**
    * Retrieve Folder Poll.
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException exception
    */
    List<FolderBean> retrieveFolderPoll(final String username) throws EnMeNoResultsFoundException;

    /**
    * Get Polls by Folder.
    * @param folder
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException
    */
  List<UnitPoll> getPollsByFolder(final FolderBean folder, final String username) throws EnMeNoResultsFoundException;

      /**
    *
    * @param keywordQuestion
    * @param username
    * @param maxResults
    * @param start
    * @return
    * @throws EnMeExpcetion
    */
    List<UnitPoll> searchPollByKeyword(final String keywordQuestion, final String username, final Integer maxResults,
        final Integer start) throws EnMeExpcetion;

    /**
     * Search Polls By Folder Id.
     * @param folderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UnitPoll> searchPollsByFolder(final Long folderId, final String username) throws EnMeNoResultsFoundException;

    /**
     * Add poll to folder.
     * @param folderId
     * @param username
     * @param pollId
     * @throws EnMeNoResultsFoundException
     */
    void addPollToFolder(final Long folderId, final String username, final Long pollId) throws EnMeNoResultsFoundException;

    /**
     *
     * @param folderId
     * @param userId
     * @return
     */
    PollFolder getPollFolderByFolderIdandUser(final Long folderId, final Long userId);

    /**
     * Get polls by date.
     * @param username
     * @param date
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<UnitPoll> getPollsbyDate(final String username, final Date date,
            final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Get poll by poll id and User id.
     * @param pollId
     * @param account
     * @return
     * @throws EnMeTweetPollNotFoundException
     */
    Poll getPollById(final Long pollId, final UserAccount account) throws EnMePollNotFoundException;

    /**
     * Get poll by pollId.
     * @param pollId
     * @return
     * @throws EnMeTweetPollNotFoundException
     */
    Poll getPollById(final Long pollId) throws EnMePollNotFoundException;
}
