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
package org.encuestame.core.service.imp;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.web.CreatePollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.PollDetailBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.search.PollSearchBean;

/**
 * Poll Service Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since May 16, 2010
 */
public interface IPollService extends IMasterSurveyService{

   /**
    * Create poll.
    * @param questionName
    * @param answers
    * @param showResults
    * @param commentOption
    * @param notification
    * @param hashtags
    * @return
    * @throws EnMeExpcetion
    */
     Poll createPoll(final CreatePollBean createPollBean) throws EnMeExpcetion;

   /**
    * List Poll by User Id.
    * @param maxResults
    * @param start
    * @return
    * @throws EnMeNoResultsFoundException
    */
    List<PollBean> listPollByUser(final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Update Question Poll.
     * @param pollId
     * @param question
     * @return
     * @throws EnMeExpcetion
     */
    PollBean updateQuestionPoll(final Long pollId, final Question question)
    throws EnMeExpcetion;

    /**
    * Public Poll by List.
    * @param Poll
    * @param emailList
    */
    void publicPollByList(final Poll Poll , final UnitLists emailList);

    /**
    * Remove PollFolder.
    * @param folderId
    * @throws EnMeNoResultsFoundException
    */
    void removePollFolder(final Long folderId) throws EnMeNoResultsFoundException;

    /**
     * Create Poll Folder.
     * @param folderName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    FolderBean createPollFolder(final String folderName) throws EnMeNoResultsFoundException;

    /**
    * Update FolderName.
    * @param folderId
    * @param newFolderName
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException
    */
    FolderBean updateFolderName(final Long folderId,
          final String newFolderName,
          final String username) throws EnMeNoResultsFoundException;

    /**
    * Retrieve Folder Poll.
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException exception
    */
    List<FolderBean> retrieveFolderPoll() throws EnMeNoResultsFoundException;

    /**
    * Get Polls by Folder.
    * @param folder
    * @return
    * @throws EnMeNoResultsFoundException
    */
    List<PollBean> getPollsByFolder(final FolderBean folder) throws EnMeNoResultsFoundException;

    /**
    *
    * @param keywordQuestion
    * @param maxResults
    * @param start
    * @return
    * @throws EnMeExpcetion
    */
    List<PollBean> searchPollByKeyword(final String keywordQuestion, final Integer maxResults,
        final Integer start) throws EnMeExpcetion;

    /**
     * Search Polls By Folder Id.
     * @param folderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollBean> searchPollsByFolder(final Long folderId, final String username) throws EnMeNoResultsFoundException;

    /**
     * Add poll to folder.
     * @param folderId
     * @param pollId
     * @throws EnMeNoResultsFoundException
     */
    void addPollToFolder(final Long folderId, final Long pollId) throws EnMeNoResultsFoundException;

    /**
     * Get poll foder by id and user.
     * @param folderId
     * @param userAccount
     * @return
     */
    PollFolder getPollFolderByFolderIdandUser(final Long folderId, final UserAccount userAccount);

    /**
     * Get polls by date.
     * @param date
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollBean> getPollsbyDate(final Date date,
            final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Get poll by poll id and User id.
     * @param pollId
     * @param account
     * @return
     * @throws EnMeTweetPollNotFoundException
     */
    Poll getPollById(final Long pollId, final UserAccount account) throws EnMeNoResultsFoundException;

    /**
     * Get polls by userName.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollBean> getPollsByUserName(final String username, final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;


    /**
     * Create Poll published Notification.
     * @param tweetPoll
     * @throws EnMeNoResultsFoundException
     */
    void createPollNotification(final Poll poll) throws EnMeNoResultsFoundException;

    /**
     * Get published polls.
     * @param maxResults
     * @param start
     * @param range
     * @return
     * @throws EnMeTweetPollNotFoundException
     * @throws EnMePollNotFoundException
     */
    List<Poll> getPollsByRange(final Integer maxResults, final Integer start, final SearchPeriods range);

    /**
     * Filter a list of poll by keyword and TypeSearch.
     * @param typeSearch
     * @param keyword
     * @param max
     * @param start
     * @param searchResult
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     * @deprecated in favor of IPollService.filterSearchPollsByType
     */
    List<PollBean> filterPollByItemsByType(
            final TypeSearch typeSearch,
            String keyword, Integer max, Integer start)
            throws EnMeNoResultsFoundException, EnMeExpcetion;

    /**
     * Change the poll's status.
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void changeStatusPoll(final Long pollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     *
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void closeAfterQuotaPoll(final Long pollId, final String username)
    throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Set true / false the ip protection.
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void ipProtectionPoll(final Long pollId, final String username)
    throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Enable /Disable notifications.
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void enableNotificationsPoll(final Long pollId,
            final String username) throws EnMeNoResultsFoundException,
            EnmeFailOperation;

    /**
     * Enable /Disable additional info.
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void setAdditionalInfoPoll(final Long pollId, final String username)
    throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Enable /Disable password restrictions.
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void setPasswordRestrictionsPoll(final Long pollId,
            final String username) throws EnMeNoResultsFoundException,
            EnmeFailOperation;

    /**
     * Enable /Disable displar results in a poll.
     * @param pollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void setShowResultsPoll(final Long pollId, final String username)
    throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     *
     * @return
     * @throws EnMeNoResultsFoundException
     */
    PollDetailBean getPollDetailInfo(final Long pollId) throws EnMeNoResultsFoundException;

   /**
    * User vote a {@link Poll}.
    * @param poll
    * @param slug
    * @param ipAddress
    * @param answerId
    * @throws EnMeNoResultsFoundException
    */
    void vote(final Poll poll, final String slug, final String ipAddress, final Long answerId) throws EnMeNoResultsFoundException;

    /**
     * Get poll by id and slug.
     * @param pollId
     * @param slug
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Poll getPollSlugById(final Long pollId, final String slug) throws EnMeNoResultsFoundException;

    /**
     *
     * @param pollId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Poll getPollById(final Long pollId) throws EnMeNoResultsFoundException;

    /**
     * Validate the poll id.
     * @param ip ip address.
     * @param poll {@link Poll}.
     * @return
     */
    Integer validatePollIP(final String ip, final Poll poll);

    /**
     * Return a list of {@link PollBeanResult} with votes of a {@link Poll}.
     * @param poll
     * @return
     */
    List<PollBeanResult> getResultVotes(final Poll poll);

    /**
     * Return {@link Poll} based on Id and String account.
     * @param pollId
     * @param account
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Poll getPollById(final Long pollId, final String account) throws EnMeNoResultsFoundException;

    /**
     * Remove {@link Poll}
     * @param pollId
     * @throws EnMeNoResultsFoundException
     */
    void removePoll(final Long pollId) throws EnMeNoResultsFoundException;

    /**
     * Add {@link HashTag} to {@link Poll}.
     * @param poll
     * @param tagBean
     * @return
     * @throws EnMeNoResultsFoundException
     */
    HashTag addHashTagToPoll(final Poll poll, final HashTagBean tagBean)
            throws EnMeNoResultsFoundException;

    /**
     * Update {@link Poll}
     * @param pollBean
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Poll updatePoll(final PollBean pollBean)
            throws EnMeNoResultsFoundException;

    /**
     *
     * @param pollId
     * @param answerId
     * @param account
     * @return
     * @throws EnMeNoResultsFoundException
     */
    Poll getPollByAnswerId(final Long pollId, final Long answerId,
            final UserAccount account) throws EnMeNoResultsFoundException;

    /**
     *
     * @param pollSearch
     * @param httpServletRequest
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    List<SearchBean> filterSearchPollsByType(
            final PollSearchBean pollSearch,
            final HttpServletRequest httpServletRequest)
            throws EnMeNoResultsFoundException, EnMeExpcetion;

    /**
     *
     * @param username
     * @param httpServletRequest
     * @param pollSearch
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<SearchBean> getPollsByUserNameSearch(
                final String username,
                final HttpServletRequest httpServletRequest,
                final PollSearchBean pollSearch)
                throws EnMeNoResultsFoundException;

    /**
     *
     * @param username
     * @param httpServletRequest
     * @param pollSearchBean
     * @return
     * @throws EnMeExpcetion
     */
    List<SearchBean> searchPollsToday(
                final String username,
                final HttpServletRequest httpServletRequest, final PollSearchBean pollSearchBean) throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param httpServletRequest
     * @param pollSearchBean
     * @return
     * @throws EnMeExpcetion
     */
    List<SearchBean> searchPollsLastWeek(final String username,
            final HttpServletRequest httpServletRequest,
            final PollSearchBean pollSearchBean) throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param httpServletRequest
     * @param pollSearchBean
     * @return
     * @throws EnMeExpcetion
     */
    List<SearchBean> searchPollFavourites(final String username,
            final HttpServletRequest httpServletRequest,
            final PollSearchBean pollSearchBean) throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param httpServletRequest
     * @param pollSearchBean
     * @return
     * @throws EnMeExpcetion
     */
    List<SearchBean> searchPollScheduled(
            final String username,
            final HttpServletRequest httpServletRequest,
            final PollSearchBean pollSearchBean) throws EnMeExpcetion;

    /**
     * Restrict votes by date.
     * @param poll
     * @return
     */
    Boolean restrictVotesByDate(final Poll poll);

    /**
     * Restrict votation by Quota.
     * @param poll
     * @return
     */
    Boolean restrictVotesByQuota(final Poll poll);


    /**
     * Return a flag if the IP is allowed to post a vote
     * @param ip user IP
     * @param poll {@link Poll}
     * @return
     */
    public Boolean checkLimitVotesByIP(final String ip, final Poll poll);

    /**
     * Retrieve all folders searched by keyword.
     * @param keyword
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollFolder> retrieveFoldersbyKeyword(final String keyword) throws EnMeNoResultsFoundException;

    /**
     *
     * @param pollsResults
     * @return
     */
    List<PollBean> getAnswersVotesByPoll(final List<Poll> pollsResults);
}
