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

import java.util.List;

import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.QuestionPattern;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.UnitSurveySection;

import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * Interface for Survey Service.
 * @author Picado, Juan juanATencuestame.org
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
    FolderBean createSurveyFolder(final String folderName, final String username) throws EnMeNoResultsFoundException;

    /**
     * Update Survey Folder.
     * @param folderId
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    FolderBean updateSurveyFolder(final Long folderId, final String folderName, final String username)
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

    /**
     *
     * @param typeSearch
     * @param keyword
     * @param max
     * @param start
     * @param searchResult
     * @return
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion
     */
    List<SurveyBean> filterSurveyItemsByType(final TypeSearch typeSearch,
            String keyword, Integer max, Integer start)
            throws EnMeNoResultsFoundException, EnMeExpcetion;

    /**
     *
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<SurveyBean> searchSurveysByKeyWord(final String username,
            final String keyword, final Integer maxResults, final Integer start)
            throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<SurveyBean> searchSurveysToday(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<SurveyBean> searchSurveysLastWeek(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<SurveyBean> searchSurveysFavourites(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     *
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<SurveyBean> getSurveysByUserName(final String username,
            final Integer maxResults, final Integer start)
            throws EnMeNoResultsFoundException;

    /**
     * Create new survey.
     * @param surveyBean
     * @return
     * @throws EnMeExpcetion
     */
    Survey createSurvey(final SurveyBean surveyBean) throws EnMeExpcetion;

    /**
     * Search surveys by keyword name.
     * @param keyWord
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<SurveyBean> searchSurveysbyKeywordName(final String keyWord, final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search surveys by Account. 
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<SurveyBean> getSurveysByAccount(final Integer maxResults, final Integer start)
            throws EnMeNoResultsFoundException;

    /**
     * Get Survey folders.
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<FolderBean> getFolders() throws EnMeNoResultsFoundException;

    /**
     * Retrieve surveys by folder.
     * @param accountId
     * @param folderId
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<Survey> retrieveSurveyByFolder(final Long accountId,
            final Long folderId) throws EnMeNoResultsFoundException;
    
    /**
     * 
     * @param survey
     * @return
     */
	List<UnitSurveySection> retrieveSectionsBySurvey(final Survey survey);
	
	/**
	 * Create {@link SurveySection}
	 * @param surveySectionBean
	 * @return
	 */
	SurveySection createSurveySection(
			final UnitSurveySection surveySectionBean, final Survey survey);
	
	/**
	 * Add {@link QuestionAnswer}
	 * @param questionName
	 * @param user
	 * @param section
	 * @param questionPattern
	 * @param answers
	 */
	void addQuestionToSurveySection(final String questionName,
			final UserAccount user, final SurveySection section,
			final QuestionPattern questionPattern, final String[] answers);
	
	/**
	 * Retrieve {@link Survey} by id and {@link UserAccount}.
	 * @param surveyId
	 * @return
	 * @throws EnMeNoResultsFoundException
	 */
	Survey getSurveyById(final Long surveyId) throws EnMeNoResultsFoundException;
	
	/**
	 * retrieve {@link SurveyFolder} by id and user.
	 * @param folderId
	 * @return
	 */
	SurveyFolder getSurveyFolderbyId(final Long folderId); 
 }
