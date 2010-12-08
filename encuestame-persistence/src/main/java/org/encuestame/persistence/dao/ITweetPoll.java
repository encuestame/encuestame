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

import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollFolder;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.hibernate.HibernateException;

/**
 * Interface to {@link TweetPollDao}.
 * @author Picado, Juan juan@encuestame.org
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
     * Retrieve Tweets Poll by User Id.
     * @param userId userId
     * @return list of tweet pools.
     */
     List<TweetPoll> retrieveTweetsByUserId(final Long userId);

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
      * Get Results By {@link TweetPoll} && {@link QuestionsAnswers}.
      * @param tweetPoll {@link TweetPoll}
      * @param answers {@link QuestionsAnswers}
      * @return List of {@link TweetPollResult}
      */
      List<Object[]> getResultsByTweetPoll(final TweetPoll tweetPoll, QuestionsAnswers answers);

      /**
       * Get TweetPoll by Question Name.
       * @param keyWord keyword
       * @param userId user Id.
       * @return
       */
      List<TweetPoll> retrieveTweetsByQuestionName(final String keyWord, final Long userId);

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
      List<Object[]> getVotesByAnswer(final TweetPollSwitch pollSwitch);


      /**
       * Get Total Votes By {@link TweetPoll}.
       * @param tweetPoll {@link TweetPoll}.
       * @return List of Votes.
       */
      List<Object[]> getTotalVotesByTweetPoll(final Long tweetPollId);

      /**
       * Retrieve TweetPoll Folder by User Id.
       * @param userId
       * @return
       */
      List<TweetPollFolder> retrieveTweetPollFolderByUserId(final Long userId);

      /**
       * Retrieve TweetPoll by Folder Id.
       * @param userId
       * @param folderId
       * @return
       */
      List<TweetPollFolder> retrieveTweetPollByFolder(final Long userId, final Long folderId);

      /**
       * Get Tweet Poll Folder by Id.
       * @param folderId
       * @return
       */
      TweetPollFolder getTweetPollFolderById(final Long folderId);
}
