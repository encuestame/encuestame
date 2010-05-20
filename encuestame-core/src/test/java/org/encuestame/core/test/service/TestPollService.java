 /************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.IPollService;
import org.encuestame.core.test.service.config.AbstractBase;
import org.encuestame.core.test.service.config.AbstractBaseUnitBeans;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitPoll;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

 /**
 * Description Class.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 17/05/2010 19:35:36
 * @version Id:
 */
public class TestPollService extends AbstractBaseUnitBeans{
     /** {@link SecUsers} **/
    private SecUsers user;

    /** {@link Questions} **/
    private Questions question;

    /** {@link QuestionPattern} **/
    private QuestionPattern questionPattern;

    /** {@link IPollService} **/
    @Autowired
    private IPollService pollService;

    /** {@link Poll} **/
    private Poll poll;

    private SecUserSecondary secUserSecondary;

    @Before
    public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.secUserSecondary = createSecondaryUser("diana", this.user);
        this.question = createQuestion("Why the roses are red?","html");
        this.questionPattern = createQuestionPattern("html");
        poll = createPoll(new Date(), this.question, "FDK125", this.user, Boolean.TRUE);
     }

    /**
     * Test Find Polls By User.
     **/
    @Test
    public void testFindAllPollByUserId(){
        List<UnitPoll> unitPoll =  new ArrayList<UnitPoll>();
        unitPoll=pollService.listPollByUser(this.secUserSecondary.getUsername());
         assertEquals("should be equals",1, unitPoll.size());
    }

    /**
     * Test List Polls by Question Keyword.
     **/
    //FIXME:
    @Test
    public void testListPollbyQuestionKeyword(){
        List<UnitPoll> unitPollList = new ArrayList<UnitPoll>();
        final String keyword = "Why";
        unitPollList = pollService.listPollbyQuestionKeyword(this.secUserSecondary.getUsername(), keyword);
        assertEquals("should be equals",1, unitPollList.size());

    }

}
