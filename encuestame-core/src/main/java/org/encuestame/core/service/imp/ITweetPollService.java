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
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollResultsBean;

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
     * @param username username session
     * @param keyword keyword.
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    List<TweetPollBean> searchTweetsPollsByKeyWord(final String username,
            final String keyword,
            final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException, EnMeExpcetion;

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
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<TweetPollBean> getTweetsPollsByUserName(final String username,
            final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException;

 
    
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
     *
     * @param typeSearch
     * @param keyword
     * @param max
     * @param start
     * @param searchResult
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    List<TweetPollBean> filterTweetPollByItemsByType(final TypeSearch typeSearch,
            String keyword, Integer max, Integer start,
            final TypeSearchResult searchResult)
            throws EnMeNoResultsFoundException, EnMeExpcetion;

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
     * @return
     * @throws EnMeExpcetion
     */
    List<TweetPollBean> searchTweetsPollScheduled(final String username,
             final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search Favourites TweetPolls.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<TweetPollBean> searchTweetsPollFavourites(final String username,
             final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search Tweet Polls Last Week.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<TweetPollBean> searchTweetsPollsLastWeek(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search Tweet Polls Today.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<TweetPollBean> searchTweetsPollsToday(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

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
     TweetPollFolder getTweetPollFolderbyId(final Long folderId);
     
     /**
 	 * Search tweetpolls by folder.
 	 * @param folderId
 	 * @param username
 	 * @return
 	 * @throws EnMeNoResultsFoundException
 	 */
 	List<TweetPollBean> searchTweetPollsByFolder(final Long folderId,
 			final String username) throws EnMeNoResultsFoundException;
}