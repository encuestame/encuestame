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

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.hibernate.HibernateException;

/**
 * Interface to implement Survey Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since  01/06/2009 13:08:40
 * @version $Id$
 */
public interface ISurvey extends IBaseDao{

    /**
     * @param searchString
     * @param userId
     * @return
     */
    List<Survey> searchSurveyByUserId(final String searchString, final Long userId);

    /**
    *
    * @param userId
    * @return
    */
    List<SurveyFolder> retrieveFolderByUserId(final Long userId);

    /**
    *
    * @param secId
    * @return
    */
    List<SurveySection> retrieveQuestionsBySurveySection(final Long secId);

    /**
    *
    * @param sectionId
    * @return
    */
    SurveySection retrieveSurveySectionById(Long sectionId);

    /**
    *
    * @param pagId
    * @return
    */
    List<SurveyPagination> retrieveSectionByPagination(final Integer pagId);

    /**
    * Get Survey Folder by Id.
    * @param folderId
    * @return
    */
    SurveyFolder getSurveyFolderById(final Long folderId);

    /**
    * Get Survey Folder by Folder Id and User.
    * @param FolderId
    * @param userId
    * @return
    */
    SurveyFolder getSurveyFolderByIdandUser(final Long FolderId, final Long userId);

    /**
    * Get Survey by Id and User.
    * @param surveyId
    * @param userId
    * @return
    */
    Survey getSurveyByIdandUserId(final Long surveyId, final Long userId);

    /**
    * Retrieve Surveys by Folder.
    * @param userId
    * @param folderId
    * @return
    */
    List<Survey> retrieveSurveyByFolder(final Long userId, final Long folderId);

    /**
    * Retrieve All Folders.
    * @param userId
    * @return
    */
    List<SurveyFolder> retrieveAllFolders(final Long userId);

    /**
    * Get Total of TweetPoll By User Editor.
    * @param userSecondary
    * @return
    */
    List<Long> getTotalSurveyByOwner(final Long userId);

    /**
     * Get survey by id.
     * @param surveyId
     * @return
     * @throws HibernateException
     */
    Survey getSurveyById(final Long surveyId) throws HibernateException;

    /**
     * Search surveys by question name.
     * @param keyWord
     * @param userId
     * @param maxResults
     * @param start
     * @return
     */
    List<Survey> retrieveSurveybyQuestionName(final String keyWord, final Long userId,
            final Integer maxResults,
            final Integer start);

    /**
     * retrieve survey by date.
     * @param account
     * @param initDate
     * @param maxResults
     * @param start
     * @return
     */
    List<Survey> retrieveSurveyByDate(
            final Account account,
            final Date initDate,
            final Integer maxResults,
            final Integer start);

    /**
     * Retrieve survey today
     * @param account
     * @param maxResults
     * @param start
     * @return
     */
     List<Survey> retrieveSurveyToday(
            final Account account,
            final Integer maxResults,
            final Integer start);

     /**
      * Retrieve survey by user id.
      * @param userId
      * @param maxResults
      * @param start
      * @return
      */
    List<Survey> retrieveSurveyByUserId(
             final Long userId,
             final Integer maxResults,
             final Integer start);

    /**
     * Retrieve favourites survey.
     * @param account
     * @param maxResults
     * @param start
     * @return
     */
    List<Survey> retrieveFavouritesSurvey(
            final Account account,
            final Integer maxResults,
            final Integer start);

    /**
     * Retrieve scheduled survey.
     * @param userId
     * @param maxResults
     * @param start
     * @return
     */
    List<Survey> retrieveScheduledSurvey(
            final Long userId,
            final Integer maxResults,
            final Integer start);

    /**
     * Retrieve survey last Week.
     * @param account
     * @param maxResults
     * @param start
     * @return
     */
    List<Survey> retrieveSurveyLastWeek(
            final Account account,
            final Integer maxResults,
            final Integer start);
}
