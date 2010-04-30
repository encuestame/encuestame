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
package org.encuestame.core.test.service.config;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.utils.web.ResumeResultTweetPoll;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;


/**
 * Abstract Base Unit Beans.
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since 19/04/2010 20:54:56
 * @version $Id:$
 */

public abstract class AbstractBaseUnitBeans extends AbstractBase{

    /**
     * Create Unit Question Helper.
     * @param questionName
     * @param stateId
     * @param userId
     * @param listAnswers
     * @param pattern
     * @return
     */
    @SuppressWarnings("unchecked")
    public UnitQuestionBean createUnitQuestionBean(
            final String questionName,
            final Long stateId,
            final Long userId,
            final List listAnswers,
            final UnitPatternBean pattern){
         final UnitQuestionBean question = new UnitQuestionBean();
         question.setQuestionName(questionName);
         question.setStateId(stateId);
         question.setUserId(userId);
         question.setListAnswers(listAnswers);
         question.setPattern(pattern);
         return question;
    }

    /**
     * Create Pattern Bean Helper.
     * @param classpattern
     * @param descripcionPattern
     * @param levelpattern
     * @param patronType
     * @param template
     * @return
     */
     public UnitPatternBean createPatternBean(
             final String classpattern,
             final String descripcionPattern,
             final String levelpattern,
             final String patronType,
             final String template){
         final UnitPatternBean unitPatternBean = new UnitPatternBean();
         unitPatternBean.setClasspattern(classpattern);
         unitPatternBean.setDescripcion(descripcionPattern);
         unitPatternBean.setLevelpattern(levelpattern);
         unitPatternBean.setPatronType(patronType);
         unitPatternBean.setTemplate(template);
        return unitPatternBean;
     }

     /**
      *
      * @param answerHash
      * @param answers
      * @param questionId
      * @return
      */
     public UnitAnswersBean createAnswersBean(
             final String answerHash,
             final String answers,
             final Long questionId){
         final UnitAnswersBean answerBean = new UnitAnswersBean();
         answerBean.setAnswerHash(answerHash);
         answerBean.setAnswers(answers);
         answerBean.setQuestionId(questionId);
        return answerBean;


     }


    /**
     *
     * @param allowLiveResults
     * @param closeNotification
     * @param completed
     * @param publicationDateTweet
     * @param publishPoll
     * @param resultNotification
     * @param schedule
     * @param scheduleDate
     * @param tweetUrl
     * @param userId
     * @param questionBean
     * @param userTwitterAccount
     * @return
     */

     public UnitTweetPoll createTweetPoll(
             final Boolean allowLiveResults,
             final Boolean closeNotification,
             final Boolean completed,
             final Date publicationDateTweet,
             final Boolean publishPoll,
             final Boolean resultNotification,
             final Boolean schedule,
             final Date scheduleDate,
             final String tweetUrl,
             final Long userId,
             final UnitQuestionBean questionBean,
             final String userTwitterAccount

             ){
         UnitTweetPoll unitTweetPoll = new UnitTweetPoll();
         unitTweetPoll.setUserId(userId);
         unitTweetPoll.setAllowLiveResults(allowLiveResults);
         unitTweetPoll.setCloseNotification(closeNotification);
         unitTweetPoll.setCompleted(completed);
         unitTweetPoll.setPublicationDateTweet(publicationDateTweet);
         unitTweetPoll.setPublishPoll(publishPoll);
         unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L, null, null));
         unitTweetPoll.setResultNotification(resultNotification);
         unitTweetPoll.setResults(null);
         unitTweetPoll.setSchedule(schedule);
         unitTweetPoll.setScheduleDate(scheduleDate);
         unitTweetPoll.setTweetId(null);
         unitTweetPoll.setTweetUrl(tweetUrl);
         unitTweetPoll.setTwitterUserAccount(null);
        return unitTweetPoll;
     }

     /**
      * Helper
      * Create Unit Tweet Poll Publicated
      * @param publicationDateTweet
      * @param publishPoll
      * @param tweetUrl
      * @param userId
      * @param questionBean
      * @param userTwitterAccount
      * @return
      */
      public UnitTweetPoll createUnitTweetPollPublicated(
             final Date publicationDateTweet,
             final Boolean publishPoll,
             final String tweetUrl,
             final Long userId,
             final UnitQuestionBean questionBean,
             final String userTwitterAccount

             ){
     UnitTweetPoll unitTweetPoll = new UnitTweetPoll();
     unitTweetPoll.setUserId(userId);
     unitTweetPoll.setPublicationDateTweet(publicationDateTweet);
     unitTweetPoll.setPublishPoll(publishPoll);
     unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L, null, null));
     unitTweetPoll.setResults(null);
     unitTweetPoll.setTweetId(null);
     unitTweetPoll.setTweetUrl(tweetUrl);
     unitTweetPoll.setTwitterUserAccount(null);
     return unitTweetPoll;
     }

    /**
     * Helper
     * Create Tweet Poll Publicated
     * @param publishTweetPoll
     * @param completed
     * @param scheduleDate
     * @param tweetOwner
     * @param question
     * @return
     */

     public TweetPoll createTweetPollPublicated(
              Boolean publishTweetPoll,
              Boolean completed,
              Date scheduleDate,
              SecUsers tweetOwner,
              Questions question){
         final TweetPoll tweetPoll = new TweetPoll();
         tweetPoll.setPublishTweetPoll(publishTweetPoll);
         tweetPoll.setCompleted(completed);
         tweetPoll.setScheduleDate(scheduleDate);
         tweetPoll.setQuestion(question);
         tweetPoll.setTweetOwner(tweetOwner);
         getTweetPoll().saveOrUpdate(tweetPoll);
         return tweetPoll;
     }

}
