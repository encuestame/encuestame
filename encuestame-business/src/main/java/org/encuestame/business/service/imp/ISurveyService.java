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

import java.util.Collection;
import java.util.List;

import org.encuestame.business.service.social.provider.TwitterService;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.QuestionBean;

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
     * @return List of {@link QuestionBean}
     * @throws EnMeExpcetion exception
     */
    List<QuestionBean> loadAllQuestions() throws EnMeExpcetion;

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
     * Getter {@link TwitterService}.
     * @return the twitterService
     */
     ITwitterService getTwitterService();

    /**
     * Create Question.
     * @param questionBean {@link QuestionBean}.
     * @throws EnMeExpcetion exception
     */
     Question createQuestion(final QuestionBean questionBean) throws EnMeExpcetion;

    /**
     * Retrieve Answer By Question Id.
     * @param questionId question Id
     * @return List of Answers
     */
    List<QuestionAnswerBean> retrieveAnswerByQuestionId(final Long questionId);

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


    void createQuestionAnswer(final QuestionAnswerBean answerBean, Question question) throws EnMeExpcetion;

    /**
     * @param rANDOMQUESTIONKEY the rANDOM_QUESTION_KEY to set
     */
    void setRandomQuestionKey(Integer rInteger);

    /**
     * Create Survey Folder.
     * @param folderName
     * @param username
     * @return
     */
    UnitFolder createSurveyFolder(final String folderName, final String username) throws EnMeNoResultsFoundException;

    /**
     * Update Survey Folder.
     * @param folderId
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    UnitFolder updateSurveyFolder(final Long folderId, final String folderName, final String username)
        throws EnMeNoResultsFoundException;

    /**
     * Delete Survey Folder.
     * @param folderId
     * @throws EnMeNoResultsFoundException
     */
    void deleteSurveyFolder(final Long folderId) throws EnMeNoResultsFoundException;

    /**
     * Add Survey to Folder.
     * @param folderId
     * @param username
     * @param surveyId
     * @throws EnMeNoResultsFoundException
     */
    void addSurveyToFolder(final Long folderId, final String username, final Long surveyId) throws EnMeNoResultsFoundException;

 }
