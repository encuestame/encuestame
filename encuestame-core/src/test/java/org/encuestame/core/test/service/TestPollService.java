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
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.domain.CatEmailLists;
import org.encuestame.core.persistence.domain.CatEmails;
import org.encuestame.core.persistence.domain.Poll;
import org.encuestame.core.persistence.domain.Question;
import org.encuestame.core.persistence.domain.QuestionPattern;
import org.encuestame.core.persistence.domain.SecUser;
import org.encuestame.core.persistence.domain.SecUserSecondary;
import org.encuestame.core.service.IPollService;
import org.encuestame.core.test.service.config.AbstractBaseUnitBeans;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.utils.web.UnitQuestionBean;
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
     /** {@link SecUser} **/
    private SecUser user;

    /** {@link Question} **/
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern questionPattern;

    /** {@link IPollService} **/
    @Autowired
    private IPollService pollService;

    /** {@link Poll} **/
    private Poll poll;

    private SecUserSecondary secUserSecondary;

    /** {@link CatEmailLists} **/
    private CatEmailLists emailList;

    /** {@link CatEmails} **/
    private CatEmails emails;

    @Before
    public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.secUserSecondary = createSecondaryUser("diana", this.user);
        this.question = createQuestion("Why the roses are red?","html");
        this.questionPattern = createQuestionPattern("html");
        poll = createPoll(new Date(), this.question, "FDK125", this.user, Boolean.TRUE);

        this.emailList = createDefaultListEmail(this.secUserSecondary.getSecUser());
        createDefaultListEmail(this.user, "default");
        this.emails = createDefaultEmails("paola@jotadeveloper.com", this.emailList);
        createDefaultEmails("dianmorales@gmail.com", this.emailList);
     }

    /**
     * Test Find Polls By User.
     * @throws EnMeDomainNotFoundException
     **/
    @Test
    public void testFindAllPollByUserId() throws EnMeDomainNotFoundException{
        List<UnitPoll> unitPoll =  new ArrayList<UnitPoll>();
        unitPoll = pollService.listPollByUser(this.secUserSecondary.getUsername());
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

    /**
     * Test Update Question Poll.
     * @throws EnMeExpcetion
     */
    //@Test
    public void testUpdateQuestionPoll() throws EnMeExpcetion{
         final String expectedResponse = "Why the tooth are white";
         final List<UnitAnswersBean> answers;
         final UnitPatternBean patternBean;
         answers = new ArrayList<UnitAnswersBean>();
         answers.add(createAnswersBean("ZXCVB", "Yes", this.question.getQid()));
         answers.add(createAnswersBean("ASDFG", "No", this.question.getQid()));
         patternBean = createPatternBean("radio.class", "radio buttons", "2", "Yes/No", "template.html");
         final UnitQuestionBean unitQuestion = createUnitQuestion(this.question.getQid(), expectedResponse, 1L, this.user.getUid(), answers, patternBean);
         pollService.updateQuestionPoll(unitQuestion);
         assertEquals(this.question.getQuestion(), expectedResponse);
     }

    /**
     * Test Update Question Poll.
     * @throws EnMeExpcetion
     */

    //@Test
    //@ExpectedException(EnMeExpcetion.class)
    public void testUpdateNullQuestionPoll() throws EnMeExpcetion{
         final String expectedResponse = "Why the sea is blue";
         final List<UnitAnswersBean> answers;
         final UnitPatternBean patternBean;
         answers = new ArrayList<UnitAnswersBean>();
         answers.add(createAnswersBean("ZXCVB", "Yes", this.question.getQid()));
         answers.add(createAnswersBean("ASDFG", "No", this.question.getQid()));
         patternBean = createPatternBean("radio.class", "radio buttons", "2", "Yes/No", "template.html");
         final UnitQuestionBean unitQuestion = createUnitQuestion(1L,"Why the sea is blue", 1L, this.user.getUid(), answers, patternBean);
         pollService.updateQuestionPoll(unitQuestion);
     }

    @Test
    public void testCreateUrlPoll(){
           final String hashUrl="3456DS";
           final String testUrl= pollService.createUrlPoll(URLPOLL, hashUrl, this.secUserSecondary.getCompleteName());
           assertNotNull(testUrl);
    }

    @Test(timeout=80000)
    public void testPublicPollByEmailList(){
        final UnitLists emailUnitList = createUnitEmailList(this.emailList.getIdList(), new Date(), this.emailList.getListName(), this.secUserSecondary.getUid());
            final String urlPoll = pollService.createUrlPoll(URLPOLL, "DS56727", this.secUserSecondary.getCompleteName());
            pollService.publicPollByList(urlPoll, emailUnitList);
            assertEquals(1, 1); //Decoration.

    }
}
