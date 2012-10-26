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
package org.encuestame.test.persistence.domain;

import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test Survey Format Pojo.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since November 14, 2009
 */
@Category(DefaultTest.class)
public class TestSurveyFormat extends AbstractBase {

    /**
     * Test Survey Format.
     */
    @Test
    public void testSurveyFormat() {
        SurveyFormat surveyformat = new SurveyFormat();
        surveyformat.setDateCreated(null);
        surveyformat.setSurveyFormatName("Education");
        getSurveyFormatDaoImp().saveOrUpdate(surveyformat);
    }

}
