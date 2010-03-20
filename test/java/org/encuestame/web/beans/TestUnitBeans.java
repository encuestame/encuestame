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
package org.encuestame.web.beans;

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.test.config.AbstractBaseTest;
import org.encuestame.test.config.AbstractBeanBaseTest;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPatternBean;
import org.junit.Test;

/**
 * Test Unit Beans.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since 13/03/2010 16:18:10
 * @version $Id: $
 **/
public class TestUnitBeans extends AbstractBeanBaseTest{

       /**
     * Test Unit Answers Bean.
     */
    @Test
    public void testUnitAnswerBean(){
       final UnitAnswersBean unitAnswer = createUnitAnswerBean(1L, "yes", "HASH", 2L);
       assertNotNull(unitAnswer.getAnswerHash());
       assertNotNull(unitAnswer.getAnswers());
       assertNotNull(unitAnswer.getAnswerId());
       assertNotNull(unitAnswer.getQuestionId());
         }

    /**
     * Test Unit Pattern Bean.
     */
    @Test
    public void testUnitPatternBean(){
        final UnitPatternBean unitPattern = createUnitPatternBean("a", "", "", 2L, "", "", "", 1,"");

    }

}
