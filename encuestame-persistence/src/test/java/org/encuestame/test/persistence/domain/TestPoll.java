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
package org.encuestame.test.persistence.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.test.config.AbstractBase;
import org.junit.Test;

/**
 * Test Poll.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  March 15, 2009
 * @version $Id: $
 */
public class TestPoll extends AbstractBase {


    /** {@link Question} **/
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern questionPattern;

    /** {@link Account}.**/
    private Account user;

    /** {@link UserAccount} **/
    private UserAccount userAcc;

    /**
     * Test Poll.
     */
    @Test
     public void testPoll(){
        final Poll poll = new Poll();
        poll.setCreatedAt(new Date());
        poll.setQuestion(createQuestion("Do you eat pizza", "yesNo"));
        poll.setPollHash("HASH");
        poll.setPollOwner(createUserAccount("diana", createAccount()));
        poll.setPollCompleted(true);
        getiPoll().saveOrUpdate(poll);
        assertNotNull(poll.getPollId());

         }

    /**
     * Test Result Poll.
     */


    public void testPollResult(){
        final PollResult pollResult = new PollResult();
        this.user = createAccount();
        this.userAcc = createUserAccount("diana", this.user);
        pollResult.setAnswer(createQuestionAnswer("Si", createQuestion("Do you like eat vigoron?","yesNo"), "ABC"));
        pollResult.setIpaddress("127.0.0.1");
        pollResult.setPoll(createPoll(new Date(), this.question, "FDK125", userAcc, Boolean.TRUE, Boolean.TRUE));
        pollResult.setVotationDate(new Date());
        getiPoll().saveOrUpdate(pollResult);
        assertNotNull(pollResult.getPollResultId());
    }

}
