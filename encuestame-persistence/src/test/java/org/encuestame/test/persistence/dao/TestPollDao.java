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
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.domain.survey.QuestionAnswer;
import org.encuestame.persistence.domain.survey.QuestionPattern;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Poll Dao.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  March 16, 2009
 * @version $Id: $
 */
public class TestPollDao extends AbstractBase {


    /** {@link IPoll} **/
    @Autowired
    IPoll  pollI;

    /** {@link Poll} **/
    Poll poll;

    /** {@link Account}.**/
    Account user;

    /** {@link UserAccount} **/
    private UserAccount secUserSecondary;

    /** {@link Question} **/
    private Question question;

    /** {@link QuestionPattern} **/
    private QuestionPattern questionPattern;

    private PollFolder pollFolder;

     /**
     * Before.
     */
    @Before
    public void initService(){
        //user  = createUser();
       // poll = createPoll();

        this.user = createUser("testEncuesta", "testEncuesta123");
        this.secUserSecondary = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?","html");
        this.questionPattern = createQuestionPattern("html");
        this.poll = createPoll(new Date(), this.question, "FDK125", this.user, Boolean.TRUE, Boolean.TRUE);
        this.pollFolder = createPollFolder("My First Poll Folder", this.user);

    }

     /**
      * Test retrievePollsByUserId.
      **/
    @Test
    public void testFindAllPollByUserId(){
        this.secUserSecondary = createUserAccount("diana1", this.user);
        System.out.println("UID-->"+this.secUserSecondary.getUid());
        final List<Poll> pollList = getiPoll().findAllPollByUserId(this.user.getUid(),5,0);
        assertEquals("Should be equals", 1, pollList.size());
    }

    /**
     * Test retrieve Poll By Id.
    **/
    @Test
    public void testGetPollById(){
        final Poll getpoll = getiPoll().getPollById(this.poll.getPollId());
        assertNotNull(getpoll);
    }

    /**
    * Test retrieve Results Poll By PollId.
    **/
    @Test
    public void testRetrievePollResultsById(){
        final Question quest = createQuestion("Do you like futboll", "Yes/No");
        final QuestionAnswer qansw = createQuestionAnswer("Yes", quest, "2020");
        final QuestionAnswer qansw2 = createQuestionAnswer("No", quest, "2020");
        final PollResult pollResult =createPollResults(qansw, this.poll);
        final PollResult pollResult2 =createPollResults(qansw, this.poll);
        final List<Object[]> polli = getiPoll().retrieveResultPolls(this.poll.getPollId(),qansw.getQuestionAnswerId());
        final Iterator<Object[]> iterator = polli.iterator();
        while (iterator.hasNext()) {
            final Object[] objects = iterator.next();
         }
        assertEquals("Should be equals", 1, polli.size());
    }

    /**
     * Test Get Poll Folder by User.
     */
    @Test
    public void testGetPollFolderByIdandUser(){
        assertNotNull(pollFolder);
        final PollFolder pfolder = getiPoll().getPollFolderByIdandUser(this.pollFolder.getId(), this.user.getUid());
        assertEquals("Should be equals", this.pollFolder.getId(), pfolder.getId());
     }

    /**
     * Test Get Poll By User
     */
    @Test
    public void testGetPollByIdandUserId(){
        assertNotNull(this.poll);
        final Poll poll = getiPoll().getPollByIdandUserId(this.poll.getPollId(), this.user.getUid());
        assertNotNull(poll);
        assertEquals("Should be equals", this.poll.getPollId(), poll.getPollId());
    }

    /**
     * Test Get Polls By Question Keyword.
     */
    @Test
    public void testGetPollsByQuestionKeyword(){
        assertNotNull(this.poll);
        final String keywordQuestion = "roses";
        final List<Poll> listPoll = getiPoll().getPollsByQuestionKeyword(keywordQuestion, this.user.getUid(), 5, 0);
        assertEquals("Should be equals", 1, listPoll.size());
    }

    @Test
    public void testGetPollFolderById(){
        assertNotNull(this.pollFolder);
        final PollFolder pollFolder = getiPoll().getPollFolderById(this.pollFolder.getId());
        assertEquals("Should be equals", this.pollFolder.getId(), pollFolder.getId());
    }

    /**
     * Test Get Polls By PollFolderId.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetPollsByPollFolderId() throws EnMeNoResultsFoundException{
         assertNotNull(this.pollFolder);
         assertNotNull(poll);
         final Poll addPoll = addPollToFolder(this.pollFolder.getId(), this.user.getUid(), this.poll.getPollId());
         assertNotNull(addPoll);
         final List<Poll> pfolder = getiPoll().getPollsByPollFolderId(this.user.getUid(), this.pollFolder);
         assertEquals("Should be equals", 1, pfolder.size());
    }

    @Test
    public void testFindAllPoll(){
        assertNotNull(this.poll);
        final List<Poll> allPoll = getiPoll().findAll();
        assertEquals("Should be equals", 1, allPoll.size());

    }
}
