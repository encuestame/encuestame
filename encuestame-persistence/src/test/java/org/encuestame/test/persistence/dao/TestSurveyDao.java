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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveyResult;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.enums.TypeSearchResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link Survey}..
 * @author Morales, Urbina Diana paolaATencuestame.org
 * @since October 28, 2010
 * @version $Id: $
 */
public class TestSurveyDao extends AbstractBase {

    /** {@link Account}.**/
    Account user;

    /** {@link UserAccount}. **/
    private UserAccount secondaryUser;

    /** {@link SurveyFolder}. **/
    private SurveyFolder surveyFolder;

    /** {@link SurveySection}. **/
    private SurveySection surveySection;

    /** {@link SurveyPagination}. **/
    private SurveyPagination surveyPag;

    private Survey survey;

    private Integer MAX_RESULTS = 10;

    private Integer START_RESULTS = 0;

    private Calendar myDate = Calendar.getInstance();


    @Before
    public void initData(){
        this.user  = createAccount();
        this.secondaryUser = createUserAccount("paola", this.user);
        this.survey = createDefaultSurvey(user);
        this.surveyFolder = createSurveyFolders("My Survey Folder", secondaryUser);
        this.surveySection = createSurveySection("My Section");
     }

    /**
     * Test Search Survey by Username.
     */
    @Test
    public void testSearchSurveyByName(){
        final List<Survey> surveyResult = getSurveyDaoImp().searchSurveyByUserId("First", user.getUid());
        assertEquals("Should be equals", 1, surveyResult.size());
       }

    /**
     * Test Retrieve Folders by User Id.
     */
    @Test
    public void testRetrieveFolderByUserId(){
        final List<SurveyFolder> folders = getSurveyDaoImp().retrieveFolderByUserId(user.getUid());
        assertEquals("Should be equals", 1, folders.size());
    }

    /**
     * Test Retrieve Survey Section by Id.
     */
    @Test
    public void testRetrieveSurveySectionById(){
        final SurveySection section = getSurveyDaoImp().retrieveSurveySectionById(surveySection.getSsid());
        assertEquals("Should be equals", "My Section", section.getDescSection());
    }


    /**
     * Test Retrieve Questions by Survey Section.
     */

   /* @Test
    public void testRetrieveQuestionsBySurveySection(){

        final List questionList = getSurveyDaoImp().retrieveQuestionsBySurveySection(surveySection.getSsid());
        assertEquals("Should be equals", 3, questionList.size());
     }*/

    @SuppressWarnings("unchecked")
   // @Test
    public void testRetrieveSectionByPagination(){
         this.surveyPag = createSurveyPagination(1, surveySection,this.survey);
         final SurveySection s2 = createSurveySection("Second Section");
         createSurveyPagination(1, s2, this.survey);
         //log.debug(surveyPag.getPageNumber());
         //log.debug(surveyPag.getSurveySection().getSsid());
         //log.debug(surveyPag.getSurveySection().getDescSection());
         //log.debug(surveyPag.getSurvey().getName());

         final List sectionsByPage = getSurveyDaoImp().retrieveSectionByPagination(surveyPag.getPageNumber());
         assertEquals("Should be equals", 2, sectionsByPage.size());
    }

    /**
     * Test Get Survey by User.
     */
    @Test
    public void testGetSurveyByIdandUserId(){
        assertNotNull(this.survey);
        assertNotNull(this.user);
        final Survey mySurvey = getSurveyDaoImp().getSurveyByIdandUserId(this.survey.getSid(), this.user.getUid());
        assertNotNull(mySurvey.getSid());
        assertEquals("Should be equals", this.survey.getSid(), mySurvey.getSid());
    }

    /**
     * Test Get Survey Folder
     */
    @Test
    public void testGetSurveyFolderByIdandUser(){
        assertNotNull(this.surveyFolder);
        assertNotNull(this.user);
        final SurveyFolder folder = getSurveyDaoImp().getSurveyFolderByIdandUser(this.surveyFolder.getId(), this.user.getUid());
        assertNotNull(folder.getId());
        assertEquals("Should be equals", this.surveyFolder.getId(), surveyFolder.getId());
    }

    /**
     * Test Get Survey by Id.
     */
    @Test
    public void testGetSurveyFolderById(){
        assertNotNull(this.surveyFolder);
        final SurveyFolder folder = getSurveyDaoImp().getSurveyFolderById(this.surveyFolder.getId());
        assertNotNull(folder.getId());
        log.debug("SURVEY FOLDER ID--->"+ this.surveyFolder.getId());
        log.debug("MY SURVEY FOLDER--->"+ surveyFolder.getId());
        assertEquals("Should be equals", this.surveyFolder.getId(), surveyFolder.getId());
    }

    /**
     * Test Retrieve Survey by Folder.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testRetrieveSurveysByFolder() throws EnMeNoResultsFoundException{
        assertNotNull(surveyFolder);
        assertNotNull(survey);
        final Survey addSurvey = addSurveyToFolder(this.surveyFolder.getId(), this.user.getUid(), this.survey.getSid());
        assertNotNull(addSurvey);
        final List<Survey> sfolder = getSurveyDaoImp().retrieveSurveyByFolder(this.user.getUid(), this.surveyFolder.getId());
        assertEquals("Should be equals", 1, sfolder.size());
    }

    /**
     * Test retrieve all folders.
     */
    @Test
    public void testRetrieveAllFolders(){
         assertNotNull(surveyFolder);
         final List<SurveyFolder> allSurveyFolder = getSurveyDaoImp().retrieveAllFolders(this.user.getUid());
         assertEquals("Should be equals", 1, allSurveyFolder.size());
    }

    /**
     * Test retrieve survey by filter date.
     */
    @Test
    public void testRetrieveSurveyByDate(){
        createDefaultSurvey(user, "My Survey", this.myDate.getTime());
        final List<Survey> surveyList = getSurveyDaoImp().retrieveSurveyByDate(this.user, this.myDate.getTime(), this.MAX_RESULTS, this.START_RESULTS);
        assertEquals("Should be equals", 2, surveyList.size());
    }

    /**
     * Test retrieve survey created today.
     */
    //@Test //TODO:ENMETEST-13
    public void testRetrieveSurveyToday() {
        final Calendar otherHourDate = Calendar.getInstance();
        otherHourDate.add(Calendar.HOUR, 3);
        createDefaultSurvey(user, "My First Survey", this.myDate.getTime());
        createDefaultSurvey(user, "My Second Survey", otherHourDate.getTime());
        final List<Survey> surveyToday = getSurveyDaoImp().retrieveSurveyToday(
                this.user, this.MAX_RESULTS, this.START_RESULTS);
        assertEquals("Should be equals", 2, surveyToday.size());
    }

    /**
     * Test retrieve surveys created one week ago.
     */
    @Test
    public void testRetrieveSurveyLastWeek(){
        final Calendar myLastWeekDate = Calendar.getInstance();
        myLastWeekDate.add(Calendar.DATE, -3);
        // Second Survey created 3 days before today's date.
        createDefaultSurvey(user, "My Second Survey", myLastWeekDate.getTime());
        final List<Survey> surveylastWeek = getSurveyDaoImp()
                .retrieveSurveyLastWeek(this.user, this.MAX_RESULTS,
                        this.START_RESULTS);
        assertEquals("Should be equals", 2, surveylastWeek.size());
    }

    /**
     * Test retrieve surveys created one year ago.
     */
    @Test
    public void testRetrieveSurveyLastYear(){
        final Calendar myLastYearDate = Calendar.getInstance();
        myLastYearDate.add(Calendar.YEAR, -1);
        myLastYearDate.add(Calendar.DATE, 3);
        createDefaultSurvey(user, "My Second Survey", myLastYearDate.getTime());
        final List<Survey> surveyLastYear = getSurveyDaoImp()
                .retrieveSurveyLastYear(this.user, this.MAX_RESULTS,
                        this.START_RESULTS);
        assertEquals("Should be equals", 2, surveyLastYear.size());
    }

    /**
     * Test retrieve marked as favorite surveys.
     */
    @Test
    public void testRetrieveFavouritesSurvey(){
        final Survey mySurvey = createDefaultSurvey(user, "My First Survey",
                this.myDate.getTime());
        mySurvey.setEditorOwner(this.secondaryUser);
        mySurvey.setFavorites(Boolean.TRUE);
        getSurveyDaoImp().saveOrUpdate(mySurvey);
        final List<Survey> favoriteSurveys = getSurveyDaoImp()
                .retrieveFavoritesSurvey(this.secondaryUser, this.MAX_RESULTS,
                        this.START_RESULTS);
        assertEquals("Should be equals", 1, favoriteSurveys.size());
    }

    /**
     * Test Search surveys by name.
     */
    @Test
    public void testSearchbySurveyName() {
        final String keyWord = "Last";
        final Survey mySurvey = createDefaultSurvey(user, "My Last Survey",
                this.myDate.getTime());
        mySurvey.setEditorOwner(this.secondaryUser);
        getSurveyDaoImp().saveOrUpdate(mySurvey);
        final List<Survey> surveyResult = getSurveyDaoImp()
                .retrieveSurveybyName(keyWord, this.secondaryUser.getUid(),
                        this.MAX_RESULTS, this.START_RESULTS);
        assertEquals("Should be equals", 1, surveyResult.size());
    }

    /**
     * Test get surveys by folder.
     */
    @Test
    public void testGetSurveyFolder() {
        final SurveyFolder folder = createSurveyFolders("My Folder",
                this.secondaryUser);
        final Survey mySurvey = createDefaultSurvey(user, "My Last Survey",
                this.myDate.getTime());
        mySurvey.setSurveysfolder(folder);
        getSurveyDaoImp().saveOrUpdate(mySurvey);
        final List<Survey> mySurveysByFolder = getSurveyDaoImp()
                .retrieveSurveyByFolder(this.user.getUid(), folder.getId());
        assertEquals("Should be equals", 1, mySurveysByFolder.size());
    }

    /**
     *
     */
    @Test
    public void testCreateSurvey(){

        // 1- Create Survey Container
        final Survey mySurvey = createDefaultSurvey(this.user, "My Survey test", new Date());
        Assert.assertNotNull(mySurvey);

        // 2- Add survey sections
        final SurveySection section = createDefaultSection("Overview", mySurvey);
        Assert.assertNotNull(mySurvey);

        // 4- Add question to survey sections.
        /** Fist question**/
        final Question question = addQuestionSection("First Question", section, this.user);
        final Question question2 = addQuestionSection("Second Question", section, this.user);
        Assert.assertNotNull(question);
        Assert.assertNotNull(question2);

        // 5- Add answer to the questions.
        final QuestionAnswer answerYes = createQuestionAnswer("Yes", question, "123");
        final QuestionAnswer answerNo = createQuestionAnswer("No", question, "456");
        final QuestionAnswer answerText = createQuestionAnswer("125", question2, "678");
        Assert.assertNotNull(answerYes);
        Assert.assertNotNull(answerNo);
        Assert.assertNotNull(answerText);

        // 6- Answer the survey.
        createSurveyResult(answerYes, question, mySurvey);
        createSurveyResult(answerNo, question, mySurvey);
        createSurveyResult(answerText, question2, mySurvey);

        // 7- Get responses.
        final List<SurveyResult> myResponses = getSurveyDaoImp().getSurveyResponseBySurvey(mySurvey, null);
        assertEquals("Should be equals", 3, myResponses.size());

        final List<SurveyResult> myResponsesByQuestion = getSurveyDaoImp().getSurveyResponseBySurvey(mySurvey, question);
        assertEquals("Should be equals", 2, myResponsesByQuestion.size());
    }

    /**
     * Test get survey sections.
     */
    @Test
    public void testGetSurveySection(){
        final Survey survey = createDefaultSurvey(this.user, "Survey test",
                new Date());
        createDefaultSection("First Section", survey );
        createDefaultSection("Second Section", survey);
        final List<SurveySection> surveySections = getSurveyDaoImp()
                .getSurveySection(survey);
        assertEquals("Should be equals", 2, surveySections.size());
    }

    /**
     * Test Get total surveys by hashTags.
     */
    @Test
    public void testGetSurveysByHashTags() {
        final HashTag hashtag1 = createHashTag("home");
        final HashTag hashtag2 = createHashTag("technology");
        final HashTag hashtag3 = createHashTag("internet");

        final Survey mySurvey = createDefaultSurvey(this.user, "Survey test",
                new Date());

        mySurvey.getHashTags().add(hashtag1);
        mySurvey.getHashTags().add(hashtag2);
        mySurvey.getHashTags().add(hashtag3);
        getSurveyDaoImp().saveOrUpdate(mySurvey);

        final Survey mySurvey2 = createDefaultSurvey(this.user,
                "Survey test 2", new Date());
        mySurvey2.getHashTags().add(hashtag1);
        getSurveyDaoImp().saveOrUpdate(mySurvey);

        final List<Survey> totalSurveys = getSurveyDaoImp()
                .getSurveysByHashTagName(hashtag1.getHashTag(),
                        this.START_RESULTS, this.MAX_RESULTS, TypeSearchResult.HASHTAG);
        assertEquals("Should be equals", 2, totalSurveys.size());
    }

    /**
     * Test get total surveys by hashtag and date range.
     */
    @Test
    public void testGetSurveysbyHashTagNameAndDateRange(){
        final Calendar myDate = Calendar.getInstance();
        final HashTag hashtag1 = createHashTag("home");
        final Survey mySurvey = createDefaultSurvey(this.user, "Survey test",
                myDate.getTime());
        mySurvey.getHashTags().add(hashtag1);
        getSurveyDaoImp().saveOrUpdate(mySurvey);

        myDate.add(Calendar.DATE, -4);
        final Survey mySurvey2 = createDefaultSurvey(this.user, "Survey test 2",
                myDate.getTime());
        mySurvey2.getHashTags().add(hashtag1);
        getSurveyDaoImp().saveOrUpdate(mySurvey2);

        final List<Survey> getTotalSurveysbyHashTag = getSurveyDaoImp().getSurveysbyHashTagNameAndDateRange(hashtag1.getHashTag(), 7);
        assertEquals("Should be equals", 2, getTotalSurveysbyHashTag.size());
    }
}
