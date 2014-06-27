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

import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.json.TweetPollScheduledBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.encuestame.utils.web.search.TweetPollSearchBean;

/**
 * Tweet Poll Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  April 02, 2010
 * @version $Id: $
 */
public interface ITweetPollService extends IMasterSurveyService{

    /**
     * Create Tweet Poll.
     * @param tweetPollBean
     * @param question
     * @param answers
     * @param user
     * @return
     * @throws EnMeExpcetion
     */
    TweetPoll createTweetPoll(
            final TweetPollBean tweetPollBean,
            final String question,
            final UserAccount user,
            final HttpServletRequest httpServletRequest) throws EnMeExpcetion;

    /**
     *
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    TweetPollSavedPublishedStatus getTweetPollSavedPublishedStatusById(final Long id) throws EnMeNoResultsFoundException;


    /**
     * Remove ALL {@link QuestionAnswer} on {@link TweetPoll}.
     * @param tweetPoll
     */
   // void removeAllQuestionsAnswers(final TweetPoll tweetPoll);

    /**
     * Remove {@link QuestionAnswer}.
     * @param questionAnswer {@link QuestionAnswer}.
     */
    void removeQuestionAnswer(final QuestionAnswer questionAnswer);

   /**
    * Create TweetPoll answer.
    * @param answerBean
    * @param tp
    * @return
    * @throws EnMeNoResultsFoundException
    */
   TweetPollSwitch createTweetPollQuestionAnswer(
           final QuestionAnswerBean answerBean,
           final TweetPoll tp,
           final HttpServletRequest request)
           throws EnMeNoResultsFoundException;

    /**
     * Get tweetPoll by id and user logged.
     * @throws EnMeTweetPollNotFoundException
     * @throws EnMeNoResultsFoundException
     */
    TweetPoll getTweetPollById(final Long tweetPollId) throws EnMeTweetPollNotFoundException, EnMeNoResultsFoundException;


    /**
     * Get TweetPoll Published.
     * @param tweetPollId
     * @return
     * @throws EnMeTweetPollNotFoundException
     * @throws EnMeNoResultsFoundException
     */
    TweetPoll getTweetPollPublishedById(final Long tweetPollId) throws EnMeTweetPollNotFoundException, EnMeNoResultsFoundException;

    /**
     * Get tweetpoll by id and username
     * @param tweetPollId tweetpoll id
     * @param username username
     * @return {@link TweetPoll}
     * @throws EnMeNoResultsFoundException
     */
    TweetPoll getTweetPollById(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException;

    /**
    * Get {@link TweetPoll} by id and slug name.
    * @param tweetPollId
    * @param username
    * @param slug
    * @return
    * @throws EnMeNoResultsFoundException
    */
    TweetPoll getTweetPollByIdSlugName(final Long tweetPollId, final String slug) throws EnMeNoResultsFoundException;

    /**
     * Get complete list of {@link TweetPollSwitch}/
     * @param tweetPoll {@link TweetPoll}.
     * @return resutls.
     */
    List<TweetPollSwitch> getTweetPollSwitch(final TweetPoll tweetPoll);

    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @param url url
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    String generateTweetPollContent(final TweetPoll tweetPoll) throws EnMeExpcetion;

    /**
     * Search {@link TweetPoll} by Keyword.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @param httpServletRequest
     * @param tpollSearch
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
	List<TweetPollBean> searchTweetsPollsByKeyWord(final String username,
			final String keyword, final HttpServletRequest httpServletRequest,
			final TweetPollSearchBean tpollSearch)
			throws EnMeNoResultsFoundException, EnMeExpcetion;

    /**
     * Save Tweet Id.
     * @param tweetPollBean {@link TweetPollBean}
     * @throws EnMeExpcetion exception
     */
    void saveTweetId(final TweetPollBean tweetPollBean) throws EnMeExpcetion;

    /**
     * Get Tweet Path.
     * @return tweet
     */
    String getTweetPath();

    /**
     * @return the tweetPollDao
     */
    ITweetPoll getTweetPollDao();

    /**
     * Vote on TweetPoll.
     * @param pollSwitch {@link TweetPollSwitch}
     * @param ip ip
     */
    void tweetPollVote(final TweetPollSwitch pollSwitch, final String ip, final Date voteDate);

    /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollResult}
     */
    TweetPollResult validateTweetPollIP(final String ipVote, final TweetPoll tweetPoll);

    /**
     * Get Results By {@link TweetPoll}.
     * @param tweetPollId tweetPoll Id
     */
    List<TweetPollResultsBean> getResultsByTweetPollId(final Long tweetPollId) throws EnMeNoResultsFoundException;

    /**
	 *
	 * @param username
	 *            username
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	List<TweetPollBean> getTweetsPollsByUserName(final String username, final HttpServletRequest httpServletRequest,
			final TweetPollSearchBean tpollSearch)
			throws EnMeNoResultsFoundException;


    /**
     * Public Multiples Tweet Accounts.
     * @param twitterAccounts List of {@link SocialAccount}.
     * @param tweetPoll {@link TweetPoll}.
     * @param tweetText tweet text.
     * @param type {@link TypeSearchResult}
     * @param poll {@link Poll}
     * @param survey {@link Survey}
     * @return
     */
    List<TweetPollSavedPublishedStatus> publishMultiplesOnSocialAccounts(
            final List<SocialAccountBean> twitterAccounts,
            final TweetPoll tweetPoll, final String tweetText,
            final TypeSearchResult type, final Poll poll, final Survey survey);

   /**
    * Publish single {@link TweetPoll}.
    * @param accountId social account id.
    * @param tweetPoll {@link TweetPoll}.
    * @param tweetText tweet text.
    * @param type {@link TypeSearchResult}
    * @param poll {@link Poll}
    * @param survey {@link Survey}
    */
   TweetPollSavedPublishedStatus publishTweetBySocialAccountId(final Long accountId, final TweetPoll tweetPoll,
           final String tweetText, final TypeSearchResult type, final Poll poll, final Survey survey);

    /**
     * Update Question Name.
     * @param questionId
     * @param questionName
     */
    void updateQuestionName(final Long questionId, final String questionName);

    /**
     * Create TweetPoll Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    FolderBean createTweetPollFolder(final String folderName, final String username) throws EnMeNoResultsFoundException;

    /**
     * Update TweetPoll Folder.
     * @param folderId
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    FolderBean updateTweetPollFolder(final Long folderId, final String folderName, final String username) throws EnMeNoResultsFoundException;

    /**
     * Filter {@link TweetPoll}
     * @param tpollSearch
     * @param httpServletRequest
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
//    List<TweetPollBean> filterTweetPollByItemsByType(final TweetPollSearchBean tpollSearch,
//            final HttpServletRequest httpServletRequest)
//            throws EnMeNoResultsFoundException, EnMeExpcetion;

    /**
     * Delete TweetPoll Folder.
     * @param folderId
     * @throws EnMeNoResultsFoundException
     */
    void deleteTweetPollFolder(final Long folderId) throws EnMeNoResultsFoundException;

    /**
     * Add TweetPoll to Folder.
     * @param folderId
     * @param username
     * @param tweetPollId
     * @throws EnMeNoResultsFoundException
     */
    void addTweetPollToFolder(final Long folderId, final String username, final Long tweetPollId) throws EnMeNoResultsFoundException;

    /**
     * Change Status TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void changeStatusTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Change Allow Live Results TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void changeAllowLiveResultsTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Change Allow Captcha TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void changeAllowCaptchaTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Change Resume Live ResultsTweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void changeResumeLiveResultsTweetPoll(final Long tweetPollId, final String username)
         throws EnMeNoResultsFoundException, EnmeFailOperation;


    /**
     * Search Scheduled TweetsPoll.
     * @param username
     * @param maxResults
     * @param start
     * @param httpServletRequest
     * @param tpollSearch
     * @return
     * @throws EnMeExpcetion
     */
	List<SearchBean> searchTweetsPollScheduled(final String username,
			final HttpServletRequest httpServletRequest,
			final TweetPollSearchBean tpollSearch) throws EnMeExpcetion;

    /**
     * Search Favourites TweetPolls.
     * @param username
     * @param maxResults
     * @param start
     * @param httpServletRequest
     * @param tpollSearch
     * @return
     * @throws EnMeExpcetion
     */
    List<SearchBean> searchTweetsPollFavourites(final String username,
			final HttpServletRequest httpServletRequest,
			final TweetPollSearchBean tpollSearch) throws EnMeExpcetion;

    /**
     * Search Tweet Polls Last Week.
     * @param username
     * @param maxResults
     * @param start
     * @param httpServletRequest
     * @param tpollSearch
     * @return
     * @throws EnMeExpcetion
     */
	List<SearchBean> searchTweetsPollsLastWeek(final String username,
			final HttpServletRequest httpServletRequest,
			final TweetPollSearchBean tpollSearch) throws EnMeExpcetion;

    /**
     * Search Tweet Polls Today.
     * @param username
     * @param maxResults
     * @param start
     * @param httpServletRequest
     * @param tpollSearch
     * @return
     * @throws EnMeExpcetion
     */
    List<SearchBean> searchTweetsPollsToday(final String username,
			final HttpServletRequest httpServletRequest,
			final TweetPollSearchBean tpollSearch) throws EnMeExpcetion;

    /**
     * Set Favourite TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void setFavouriteTweetPoll(final Long tweetPollId, final String username) throws
           EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Change Allow Repeated TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    void changeAllowRepeatedTweetPoll(final Long tweetPollId, final String username)
         throws EnMeNoResultsFoundException, EnmeFailOperation;

    /**
     * Change Close Notification.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
     void changeCloseNotificationTweetPoll(final Long tweetPollId, final String username)
                throws EnMeNoResultsFoundException, EnmeFailOperation;

     /**
      * Update tweetpoll.
      * @param tweetPollBean {@link TweetPollBean}.
     * @throws EnMeNoResultsFoundException
      */
     TweetPoll updateTweetPoll(final TweetPollBean tweetPollBean) throws EnMeNoResultsFoundException;


     /**
      *
      * @param tweetPoll
      */
     void saveOrUpdateTweetPoll(final TweetPoll tweetPoll);

     /**
     *
     * @param tweetPoll
     * @throws EnMeNoResultsFoundException
     */
     void createTweetPollNotification(final TweetPoll tweetPoll) throws EnMeNoResultsFoundException;

     /**
      * Return tweetpoll total votes.
      * @param tweetPoll
      * @return
      */
     Long getTweetPollTotalVotes(final TweetPoll tweetPoll);

     /**
      * Check and validate if tweetpoll is complete.
      * @param tweetPoll
      */
     void checkTweetPollCompleteStatus(final TweetPoll tweetPoll);

     /**
      * Return list of links published by {@link TweetPoll}.
      * @param tweetPoll
      * @param poll
      * @param survey
      * @param type
      * @return
      */
    List<LinksSocialBean> getTweetPollLinks(final TweetPoll tweetPoll,
            final Poll poll, final Survey survey, final TypeSearchResult type);

     /**
      * Get List of TweetPoll Folders.
      * @return
      * @throws EnMeNoResultsFoundException
      */
     List<FolderBean> getFolders() throws EnMeNoResultsFoundException;

     /**
      * Get published tweetPolls.
      * @param maxResults
      * @param start
      * @param range
      * @return
      */
     List<TweetPoll> getTweetPollsbyRange(final Integer maxResults, final Integer start, final Date range);

     /**
      * Add hashtag to {@link TweetPoll}.
      * @param tweetPoll
      * @param hashTagBean
      * @return {@link HashTag}.
     * @throws EnMeNoResultsFoundException
      */
     HashTag addHashtagToTweetPoll(final TweetPoll tweetPoll,
             final HashTagBean hashTagBean) throws EnMeNoResultsFoundException;

     /**
      * Remove hashtag to {@link TweetPoll}.
      * @param tweetPoll
      * @param hashTag
      */
     void removeHashtagFromTweetPoll(final TweetPoll tweetPoll, final HashTag hashTag);

     /**
      * Retrieve {@link TweetPollFolder} by id and user.
      * @param folderId
      * @return
      */
     TweetPollFolder getTweetPollFolderbyId(final Long folderId) throws EnMeNoResultsFoundException;

     /**
      * Search tweetpolls by folder.
      * @param folderId
      * @param username
      * @return
      * @throws EnMeNoResultsFoundException
      */
     List<TweetPollBean> searchTweetPollsByFolder(final Long folderId,
             final String username) throws EnMeNoResultsFoundException;

     /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
      * @return
      * @throws EnmeFailOperation
      */
	List<TweetPollResult> validateIpVote(final String ipVote, final TweetPoll tweetPoll)
            throws EnmeFailOperation;

      /**
       * Remove {@link TweetPoll}
       * @param tpoll
       * @throws EnMeNoResultsFoundException
       */
	void removeTweetPoll (final TweetPoll tpoll) throws EnMeNoResultsFoundException ;

      /**
       *
       * @param tpollSearch
       * @param httpServletRequest
       * @return
       * @throws EnMeNoResultsFoundException
       * @throws EnMeExpcetion
       */
	List<SearchBean> filterTweetPollByItemsByTypeSearch(final TweetPollSearchBean tpollSearch,
              final HttpServletRequest httpServletRequest)
              throws EnMeNoResultsFoundException, EnMeExpcetion;

	/**
	 * Create a Scheduled {@link TweetPoll} , {@link Poll} or {@link Survey}.
	 * @param scheduleDate
	 * @param typeSearch
	 * @param tpollSaved
	 * @return
	 */
	Schedule createTweetPollPublishedStatusScheduled(
			final Date scheduleDate,
			final TypeSearchResult typeSearch,
			final TweetPollSavedPublishedStatus tpollSaved);

	/**
	 *
	 * @param bean
	 * @return
	 * @throws EnMeNoResultsFoundException
	 * @throws EnMeExpcetion
	 */
	List<Schedule> createTweetPollScheduled(
			final TweetPollScheduledBean bean, 
			final TypeSearchResult searchResult) throws EnMeNoResultsFoundException, EnMeExpcetion;

	/**
	 * Publish scheduled items
	 * @param status
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	void publishScheduledItems(final Status status, final Date minimumDate) throws EnMeNoResultsFoundException;

	/**
	 * Remove Scheduled items.
	 * @param status
	 * @param attempts
	 */
	void removeScheduledItems(final Status status, final Integer attempts);

	/**
	 * Validate Limit votes by date.
	 * @param tweetPoll
	 * @return
	 */
	Boolean validateLimitVotes(final TweetPoll tweetPoll);

	/**
	 *
	 * @param tweetPoll
	 * @return
	 */
	Boolean validateVotesByDate	(final TweetPoll tweetPoll);


	/**
	 *
	 * @param tweetPollId
	 * @param username
	 * @throws EnMeNoResultsFoundException
	 * @throws EnmeFailOperation
	 */
	 void chaneCommentStatusTweetPoll(final Long tweetPollId, final String username)
	            throws EnMeNoResultsFoundException, EnmeFailOperation;

	 /**
	  * Create Notification for each social network published.
	  * @param tweetPollPublished
	  * @throws EnMeNoResultsFoundException
	  */
	 void createTweetPollNotification(final TweetPollSavedPublishedStatus tweetPollPublished) throws EnMeNoResultsFoundException;

	 /**
	  * Retrieve all {@link TweetPollSavedPublishedStatus} by {@link TweetPoll}
	  * @param tweetPoll
	  * @return
	  * @throws EnMeNoResultsFoundException
	  */
	 List<TweetPollSavedPublishedStatus> retrieveTweetPollSavedPublished(final TweetPoll tweetPoll) throws EnMeNoResultsFoundException;

	 /**
	  * Retrieve Folders by keyword.
	  * @param user
	  * @param keyword
	  * @return
	  */
	 List<TweetPollFolder> retrieveFoldersbyKeyword(
				final UserAccount user, final String keyword);

}