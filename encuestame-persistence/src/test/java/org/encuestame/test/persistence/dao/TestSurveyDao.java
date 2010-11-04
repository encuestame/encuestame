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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.encuestame.persistence.domain.CatState;
import org.encuestame.persistence.domain.security.SecUser;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.survey.Surveys;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link Surveys}..
 * @author Morales, Urbina Diana paola AT encuestame.org
 * @since October 28, 2010
 * @version $Id: $
 */
public class TestSurveyDao extends AbstractBase {

    /** {@link SecUser}.**/
    SecUser user;

    /** {@link SecUserSecondary}. **/
    private SecUserSecondary secondaryUser;

    /** {@link SurveyFolder}. **/
    private SurveyFolder surveyFolder;

    /** {@link CatState}. **/
    private CatState state;

    /** {@link SurveySection}. **/
    private SurveySection surveySection;

    /** {@link SurveyPagination}. **/
    private SurveyPagination surveyPag;

    private Surveys survey;

    @Before
    public void initData(){
        this.user  = createUser();
        this.secondaryUser = createSecondaryUser("paola", this.user);
        this.survey = createDefaultSurvey(user);
        this.surveyFolder = createSurveyFolders("My Survey Folder", user);
        this.state = createState("Enabled");
        this.surveySection = createSurveySection(state, "My Section");
     }

    /**
     * Test Search Survey by Username.
     */
    @Test
    public void testSearchSurveyByName(){
        final List<Surveys> surveyResult = getSurveyDaoImp().searchSurveyByUserId("First", user.getUid());
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
    @SuppressWarnings("unchecked")
    @Test
    public void testRetrieveQuestionsBySurveySection(){

        final List questionList = getSurveyDaoImp().retrieveQuestionsBySurveySection(surveySection.getSsid());
        assertEquals("Should be equals", 3, questionList.size());
     }

    @SuppressWarnings("unchecked")
   // @Test
    public void testRetrieveSectionByPagination(){
         this.surveyPag = createSurveyPagination(1, surveySection,this.survey);
         final SurveySection s2 = createSurveySection(this.state, "Second Section");
         createSurveyPagination(1, s2, this.survey);
         System.out.println(surveyPag.getPageNumber());
         System.out.println(surveyPag.getSurveySection().getSsid());
         System.out.println(surveyPag.getSurveySection().getDescSection());
         System.out.println(surveyPag.getSurvey().getName());

         final List sectionsByPage = getSurveyDaoImp().retrieveSectionByPagination(surveyPag.getPageNumber());
         assertEquals("Should be equals", 2, sectionsByPage.size());
    }

}
