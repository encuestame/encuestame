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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.IPoll;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.PollResult;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Poll Dao.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  March 16, 2009
 * @version $Id: $
 */
public class TestPollDao extends AbstractBaseTest {


    /** {@link IPoll} **/
    @Autowired
    IPoll  pollI;

    /** {@link Poll} **/
    Poll poll;

    /** {@link SecUsers}.**/
    SecUsers user;

    /**
     * Before.
     */
    @Before
    public void initService(){
        user  = createUser();
        poll = createPoll();

    }

     /**
      * Test retrievePollsByUserId.
      **/
    @Test
    public void testFindAllPollByUserId(){
        final SecUsers user = createUser();
        final Poll pollnew = createPoll();
        System.out.println("UID-->"+user.getUid());
        final List<Poll> pollList = getiPoll().findAllPollByUserId(pollnew.getPollOwner().getUid());
        assertEquals("Should be equals", 1, pollList.size());
    }

    /**
     * Test retrieve Poll By Id.
     **/
   @Test
   public void testGetPollById(){
       final Poll getpoll = getiPoll().GetPollById(this.poll.getPollId());
       assertNotNull(getpoll);
   }

   /**
    * Test retrieve Results Poll By PollId.
    **/
  @Test
  public void testRetrievePollResultsById(){
      final PollResult pollResult = createPollResults(createQuestionAnswer("Yes", createQuestion("Do you like futboll", "Yes/No"), "45D3AS"), this.poll);


  }

}
