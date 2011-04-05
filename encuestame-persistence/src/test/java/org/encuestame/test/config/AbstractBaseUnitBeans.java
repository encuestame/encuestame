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
package org.encuestame.test.config;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitEmails;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.QuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UserAccountBean;


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
    public QuestionBean createUnitQuestionBean(
            final String questionName,
            final Long stateId,
            final Long userId,
            final List listAnswers,
            final UnitPatternBean pattern){
         final QuestionBean question = new QuestionBean();
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
     public QuestionAnswerBean createAnswersBean(
             final String answerHash,
             final String answers,
             final Long questionId){
         final QuestionAnswerBean answerBean = new QuestionAnswerBean();
         answerBean.setAnswerHash(answerHash);
         answerBean.setAnswers(answers);
         answerBean.setQuestionId(questionId);
        return answerBean;
     }


    /**
     * Create Unit Tweet Poll.
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
             final QuestionBean questionBean,
             final String userTwitterAccount

             ){
         UnitTweetPoll unitTweetPoll = new UnitTweetPoll();
         unitTweetPoll.setUserId(userId);
         unitTweetPoll.setAllowLiveResults(allowLiveResults);
         unitTweetPoll.setCloseNotification(closeNotification);
         unitTweetPoll.setCompleted(completed);
         unitTweetPoll.setPublishPoll(publishPoll);
         unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L, null, null));
         unitTweetPoll.setResultNotification(resultNotification);
         unitTweetPoll.setResults(null);
         unitTweetPoll.setSchedule(schedule);
         unitTweetPoll.setScheduleDate(scheduleDate);
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
             final QuestionBean questionBean,
             final String userTwitterAccount

             ){
     UnitTweetPoll unitTweetPoll = new UnitTweetPoll();
     unitTweetPoll.setUserId(userId);
     unitTweetPoll.setPublishPoll(publishPoll);
     unitTweetPoll.setQuestionBean(createUnitQuestionBean("", 1L, 1L, null, null));
     unitTweetPoll.setResults(null);
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
              Account tweetOwner,
              Question question){
         final TweetPoll tweetPoll = new TweetPoll();
         tweetPoll.setPublishTweetPoll(publishTweetPoll);
         tweetPoll.setCompleted(completed);
         tweetPoll.setScheduleDate(scheduleDate);
         tweetPoll.setCreateDate(new Date());
         tweetPoll.setQuestion(question);
         tweetPoll.setTweetOwner(tweetOwner);
         getTweetPoll().saveOrUpdate(tweetPoll);
         return tweetPoll;
     }

     /**
      *
      * @param tweetPoll
      * @param answers
      * @param codeTweet
      * @return
      */
     public TweetPollSwitch createTweetPollSwitch(
             final TweetPoll tweetPoll,
             final QuestionAnswer answers,
             final String codeTweet) {
         final TweetPollSwitch tweetPollSwitch = new TweetPollSwitch();
         tweetPollSwitch.setAnswers(answers);
         tweetPollSwitch.setCodeTweet(codeTweet);
         tweetPollSwitch.setTweetPoll(tweetPoll);
        return tweetPollSwitch;

     }

     /**
      *
      * @param questionId
      * @param questionName
      * @param stateId
      * @param userId
      * @param listAnswers
      * @param pattern
      * @return
      */
     @SuppressWarnings("unchecked")
     public QuestionBean createUnitQuestion(
             final Long questionId,
             final String questionName,
             final Long stateId,
             final Long userId,
             final List listAnswers,
             final UnitPatternBean pattern){
          final QuestionBean question = new QuestionBean();
          question.setId(questionId);
          question.setQuestionName(questionName);
          question.setStateId(stateId);
          question.setUserId(userId);
          question.setListAnswers(listAnswers);
          question.setPattern(pattern);
          return question;
     }

     /**
      * Helper
      * Create Unit Email List.
      * @param emailListId
      * @param createdAt
      * @param listName
      * @param userId
      * @return
      */
     public UnitLists createUnitEmailList(
            final Long emailListId,
            final Date createdAt,
            final String listName,
            final Long userId)
     {
         final UnitLists emailList = new UnitLists();
         emailList.setId(emailListId);
         emailList.setCreatedAt(createdAt);
         emailList.setListName(listName);
         emailList.setUserId(userId);

         return emailList;
     }

     /**
      * Helper
      * Create Unit Emails.
      * @param idEmail Email Id
      * @param emailName Email
      * @param listsId Email List Id
      * @return
      */
     public UnitEmails createUnitEmails(
             final Long idEmail,
             final String emailName,
             final Long listsId){
         final UnitEmails unitEmails = new UnitEmails();
         unitEmails.setIdEmail(idEmail);
         unitEmails.setEmailName(emailName);
         unitEmails.setListsId(listsId);
         return unitEmails;
     }

     /**
      * Create Unit Location Bean.
      * @param name
      * @return
      */
     public UnitLocationBean createUnitLocationBean(final String name){
         final UnitLocationBean locationBean = new UnitLocationBean();
         locationBean.setAccuracy(232);
         locationBean.setAddress("address");
         locationBean.setCountryCode("SP");
         locationBean.setCountryName("spain");
         locationBean.setLat(124.232F);
         locationBean.setLevel(1);
         locationBean.setLng(-2321.23F);
         locationBean.setName(name);
         locationBean.setStatus("ACTIVE");
         locationBean.setTidtype(21L);
         return locationBean;
     }

     /**
      * Create Unit Location Folder.
      * @param name
      * @return
      */
     public UnitLocationFolder createUnitLocationFolder(final String name){
         final UnitLocationFolder folder = new UnitLocationFolder();
         folder.setName(name);
         folder.setType(GeoPointFolderType.GROUPING.name());
         return folder;
     }

     /**
      * Create SignUpBean.
      * @param username username
      * @param email email.
      * @param password password
      * @return {@link SignUpBean}.
      */
     public SignUpBean createSignUpBean(final String username, final String email, final String password){
         final SignUpBean signUpBean = new SignUpBean();
             signUpBean.setCaptcha("12345");
             signUpBean.setEmail(email);
             signUpBean.setFullName(username);
             signUpBean.setPassword(password);
             signUpBean.setUsername(username);
         return signUpBean;
     }

     /**
      * Create {@link UserAccountBean}.
      * @param username
      * @return
      */
     public UserAccountBean createUnitUserBean(final String username, final String email){
         final UserAccountBean bean = new UserAccountBean();
         bean.setDateNew(new Date());
         bean.setEmail(email);
         bean.setUsername(username);
         bean.setPassword("xxxxx");
         return bean;
     }
}
