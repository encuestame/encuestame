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

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.HibernateException;

/**
 * Interface to {@link TweetPollDao}.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 17, 2010 8:27:20 PM
 * @version $Id: change to one dolar simbol
 */

public interface ITweetPoll extends IBaseDao{

    /**
     * Get TweetPoll by Id.
     * @param tweetPollId tweetPollId
     * @return {@link TweetPoll}
     * @throws HibernateException exception
     */
    TweetPoll getTweetPollById(final Long tweetPollId) throws HibernateException;

    /**
     * Get published tweetpoll by id.
     * @param tweetPollId
     * @return
     */
    TweetPoll getPublicTweetPollById(final Long tweetPollId);

    /**
     * Retrieve Tweets Poll by User Id.
     * @param userId userId
     * @return list of tweet pools.
     */
     List<TweetPoll> retrieveTweetsByUserId(final Long userId,
             final Integer maxResults,
             final Integer start);

     /**
      * Retrieve Tweets Poll Switch.
      * @param tweetCode tweetCode
      * @return switch
      */
     TweetPollSwitch retrieveTweetsPollSwitch(final String tweetCode);

     /**
      * Validate Vote IP.
      * @param ip ip
      * @param tweetPoll tweetPoll
      * @return {@link TweetPollSwitch}
      */
     TweetPollResult validateVoteIP(final String ip, final TweetPoll tweetPoll);

     /**
      * Get Results By {@link TweetPoll} && {@link QuestionAnswer}.
      * @param tweetPoll {@link TweetPoll}
      * @param answers {@link QuestionAnswer}
      * @return List of {@link TweetPollResult}
      */
      List<Object[]> getResultsByTweetPoll(final TweetPoll tweetPoll, QuestionAnswer answers);

      /**
       * Get TweetPoll by Question Name.
       * @param keyWord keyword
       * @param userId user Id.
       * @return
       */
      List<TweetPoll> retrieveTweetsByQuestionName(final String keyWord, final Long userId,
              final Integer maxResults,
              final Integer start);

      /**
       * Get List of Switch Answers by TweetPoll.
       * @param tweetPoll {@link TweetPoll}.
       * @return List of {@link TweetPollSwitch}
       */
      List<TweetPollSwitch> getListAnswesByTweetPoll(final TweetPoll tweetPoll);

      /**
       * Get Votes By {@link TweetPollSwitch}..
       * @param pollSwitch {@link TweetPollSwitch}..
       * @return
       */
      List<Long> getVotesByAnswer(final TweetPollSwitch pollSwitch);


      /**
       * Get Total Votes By {@link TweetPoll}.
       * @param tweetPoll {@link TweetPoll}.
       * @return List of Votes.
       */
      List<Object[]> getTotalVotesByTweetPoll(final Long tweetPollId);

      /**
       * Retrieve TweetPoll Folder by User Id.
       * @param account {@link Account}.
       * @return
       */
      List<TweetPollFolder> retrieveTweetPollFolderByAccount(final Account account);

      /**
       * Retrieve TweetPoll by Folder Id.
       * @param userId
       * @param folderId
       * @return
       */
      List<TweetPoll> retrieveTweetPollByFolder(final Long userId, final Long folderId);

      /**
       * Get Tweet Poll Folder by Id.
       * @param folderId
       * @return
       */
      TweetPollFolder getTweetPollFolderById(final Long folderId);

      /**
       * Get TweetPoll Folders by Id and UserId.
       * @param folderId
       * @param account
       * @return
       */
      TweetPollFolder getTweetPollFolderByIdandUser(final Long folderId, final Account account);

      /**
       *
       * @param tweetPollId
       * @param userId
       * @return
       */
      TweetPoll getTweetPollByIdandUserId(final Long tweetPollId, final Long userId);

      /**
       * Get {@link TweetPoll} by id, userid and slug name.
       * @param tweetPollId tweet poll id.
       * @param userId user id.
       * @param slugName slug name.
       * @return
     * @throws UnsupportedEncodingException
       */
      TweetPoll getTweetPollByIdandSlugName(final Long tweetPollId, final String slugName) throws UnsupportedEncodingException;

      /**
       * Retrieve TweetPoll Today.
       * @param keyWord
       * @param userId
       * @return
       */
      List<TweetPoll> retrieveTweetPollToday(
                final Account account,
                final Integer maxResults,
                final Integer start);

      /**
       * Retrieve TweetPoll Last Week.
       * @param keyWord
       * @param userId
       * @return
       */
      List<TweetPoll> retrieveTweetPollLastWeek(
              final Account account,
              final Integer maxResults,
              final Integer start);

      /**
       * Retrieve Favourites TweetPolls.
       * @param keyWord
       * @param userId
       * @return
       */
      List<TweetPoll> retrieveScheduledTweetPoll(
              final Long userId,
              final Integer maxResults,
              final Integer start);

      /**
       * Retrieve Favourites TweetPolls.
       * @param keyWord
       * @param userId
       * @return
       */
      List<TweetPoll> retrieveFavouritesTweetPoll(
              final Account account,
              final Integer maxResults,
              final Integer start);

      /**
       * Retrieve Total Votes by TweetPoll Id.
       * @param tweetPollId
     * @return
       * @return
       */
     Long getTotalVotesByTweetPollId(final Long tweetPollId);

     /**
      * Retrieve TweetPoll by Date
      * @param userId
      * @param initDate
      * @param maxResults
      * @param start
      * @return
      */
     List<TweetPoll> retrieveTweetPollByDate(
             final Account account,
             final Date initDate,
             final Integer maxResults,
             final Integer start);

    /**
     * Retrieve {@link TweetPollSwitch} by tweetpoll and answer. (should be unique)
     * @param tweetPoll {@link TweetPoll}
     * @param questionAnswer {@link QuestionAnswer}
     * @return {@link TweetPollSwitch}.
     */
    TweetPollSwitch getAnswerTweetSwitch(final TweetPoll tweetPoll, final QuestionAnswer questionAnswer);

    /**
     * Retrieve all {@link TweetPollSwitch} by {@link QuestionAnswer}.
     * @param questionAnswer {@link QuestionAnswer}.
     * @return
     */
    List<TweetPollSwitch> getAnswerTweetSwitch(final QuestionAnswer questionAnswer);

     /**
     * Get tweetPoll by top rated.
     * @param hashTagId
     * @param limit
     * @param limit
     * @return
     */
   List<TweetPoll> getTweetpollByHashTagId(final Long hashTagId, final Integer startResults, final Integer limit, final String filterby);


  /**
   * Get results by tweetpoll.
   * @param tweetPollId
   * @param answerId
   * @return
   */
   List<Object[]> getResultsByTweetPoll(final Long tweetPollId, final Long answerId);

   /**
    * Return all links published by {@link TweetPoll}, {@link Survey}, {@link Poll}.
    * @param tweetPoll
    * @param survey
    * @param poll
    * @param itemType
    * @return
    */
   public List<TweetPollSavedPublishedStatus> getLinksByTweetPoll(final TweetPoll tweetPoll, final Survey survey, final Poll poll, final TypeSearchResult itemType);

   /**
    * Get max tweetPoll like votes by user.
    * @param userId
    * @return
    */
   Long getMaxTweetPollLikeVotesbyUser(final Long userId);

   /**
    * Get tweetPolls.
    * @param maxResults
    * @param start
    * @param range
    * @return
    */
    List<TweetPoll> getTweetPolls(final Integer maxResults,
            final Integer start, final Date range);

    /**
     * Get total tweetpolls by user.
     * @param user
     * @param publishTweetPoll
     * @return
     */
    Long getTotalTweetPoll(final UserAccount user,
            final Boolean publishTweetPoll);
}