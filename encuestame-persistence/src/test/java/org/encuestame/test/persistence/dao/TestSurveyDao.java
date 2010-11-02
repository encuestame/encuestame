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

import org.encuestame.persistence.domain.security.SecUser;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.persistence.domain.survey.SurveyFolder;
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

    private SurveyFolder surveyFolder;

    @Before
    public void initData(){
        this.user  = createUser();
        this.secondaryUser = createSecondaryUser("paola", this.user);
        createDefaultSurvey(user);
        this.surveyFolder = createSurveyFolders("My Survey Folder", user);
     }

    @Test
    public void testSearchSurveyByName(){
        final List<Surveys> surveyResult = getSurveyDaoImp().searchSurveyByUserId("First", user.getUid());
        assertEquals("Should be equals", 1, surveyResult.size());
       }

    @Test
    public void testRetrieveFolderByUserId(){
        final List<SurveyFolder> folders = getSurveyDaoImp().retrieveFolderByUserId(user.getUid());
        assertEquals("Should be equals", 1, folders.size());
    }

    public void testRetrieveQuestionsBySurveySection(){


    }

}
