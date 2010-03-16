/**
 *
 */
package org.encuestame.core.persistence.pojos;

import java.util.Date;

import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.PollResult;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ITwitterService;
import org.encuestame.core.service.TwitterService;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author paola
 *
 */
public class TestPoll extends AbstractBaseTest {

    /**
     * Test Poll.
     */
    @Test
     public void testPoll(){
        final Poll poll = new Poll();
        poll.setCreatedAt(new Date());
        poll.setQuestion(createQuestion("Do you eat pizza", "yesNo"));
        poll.setPollHash("HASH");
        poll.setPollOwner(createUser());
        poll.setPollCompleted(true);
        getiPoll().saveOrUpdate(poll);
        assertNotNull(poll.getPollId());

         }

    /**
     * Test Result Poll.
     */

    @Test
    public void testPollResult(){
        final PollResult pollResult = new PollResult();
        pollResult.setAnswer(createQuestionAnswer("Si", createQuestion("Do you like eat vigoron?","yesNo"), "ABC"));
        pollResult.setIpaddress("127.0.0.1");
        pollResult.setPoll(createPoll());
        pollResult.setVotationDate(new Date());
        getiPoll().saveOrUpdate(pollResult);
        assertNotNull(pollResult.getPollResultId());

    }

}
