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
package org.encuestame.core.service;

import java.util.Collection;
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.IMasterSurveyService;
import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollResult;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * Interface for Survey Service.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
public interface ISurveyService extends IMasterSurveyService {


    /**
     * Load all questions.
     * @return List of {@link UnitQuestionBean}
     * @throws EnMeExpcetion exception
     */
    List<UnitQuestionBean> loadAllQuestions() throws EnMeExpcetion;

    /**
     * Load pattern info.
     * @param unitPatternBean {@link UnitPatternBean}
     * @return {@link UnitPatternBean}
     * @throws EnMeExpcetion exception
     */
    UnitPatternBean loadPatternInfo(UnitPatternBean unitPatternBean)
    throws EnMeExpcetion;

    /**
     * Load all Patrons.
     * @return List of {@link UnitPatternBean}
     * @throws EnMeExpcetion exception
     */
    Collection<UnitPatternBean> loadAllPatrons()
    throws EnMeExpcetion;

    /**
     * @param serviceMail serviceMail
     */
    void setServiceMail(MailServiceImpl serviceMail);

    /**
     * @return serviceMail
     */
    MailServiceImpl getServiceMail();


    /**
     * Getter {@link TwitterService}.
     * @return the twitterService
     */
     ITwitterService getTwitterService();

    /**
     * Create Question.
     * @param questionBean {@link UnitQuestionBean}.
     * @throws EnMeExpcetion exception
     */
     void createQuestion(final UnitQuestionBean questionBean) throws EnMeExpcetion;

    /**
     * Retrieve Answer By Question Id.
     * @param questionId question Id
     * @return List of Answers
     */
    List<UnitAnswersBean> retrieveAnswerByQuestionId(final Long questionId);

    /**
     * Update Answer Name by Answer Id.
     * @param answerId answer Id
     * @param nameUpdated new name for answer
     * @throws EnMeExpcetion exception
     */
    void updateAnswerByAnswerId(final Long answerId, String nameUpdated) throws EnMeExpcetion;

    /**
     * Get Twitter Token.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @return {@link RequestToken}
     * @throws TwitterException exception
     */
    RequestToken getTwitterToken(final String consumerKey, final String consumerSecret) throws TwitterException;


    void saveAnswer(final UnitAnswersBean answerBean) throws EnMeExpcetion;
}
