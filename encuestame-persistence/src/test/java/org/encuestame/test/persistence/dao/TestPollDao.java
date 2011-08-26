/*
 ************************************************************************************
 * Copyright (C) 2001-20011 encuestame: system online surveys Copyright (C) 2009
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

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Poll Dao.
 * @author Morales,Diana Paola paolaATencuestame.org
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

    /** {@link PollFolder} **/
    private PollFolder pollFolder;

    /** Max Results**/
    private Integer MAX_RESULTS = 10;

    /** **/
    private Integer START = 0;

     /** Before.
     * @throws EnMeNoResultsFoundException
     **/
    @Before
    public void initService() throws EnMeNoResultsFoundException{
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.secUserSecondary = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?", "html");
        this.poll = createPoll(new Date(), this.question, "FDK125", this.secUserSecondary, Boolean.TRUE, Boolean.TRUE);
        this.pollFolder = createPollFolder("My First Poll Folder", this.secUserSecondary);
        addPollToFolder(this.pollFolder.getId(), this.secUserSecondary, this.poll.getPollId());
    }

     /** Test retrievePollsByUserId. **/
    @Test
    public void testFindAllPollByUserId(){
        final List<Poll> pollList = getiPoll().findAllPollByUserId(this.secUserSecondary, this.MAX_RESULTS, this.START);
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
        createQuestionAnswer("No", quest, "2020");
        createPollResults(qansw, this.poll);
        createPollResults(qansw, this.poll);
        final List<Object[]> polli = getiPoll().retrieveResultPolls(this.poll.getPollId(), qansw.getQuestionAnswerId());
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
        final PollFolder pfolder = getiPoll().getPollFolderByIdandUser(this.pollFolder.getId(), this.secUserSecondary);
        assertEquals("Should be equals", this.pollFolder.getId(), pfolder.getId());
     }

    /**
     * Test Get Poll By User
     */
    @Test
    public void testGetPollByIdandUserId(){
        assertNotNull(this.poll);
        final Poll poll = getiPoll().getPollByIdandUserId(this.poll.getPollId(), this.secUserSecondary);
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
        final List<Poll> listPoll = getiPoll().getPollsByQuestionKeyword(keywordQuestion, this.secUserSecondary, this.MAX_RESULTS, this.START);
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
         final Poll addPoll = addPollToFolder(this.pollFolder.getId(), this.secUserSecondary, this.poll.getPollId());
         assertNotNull(addPoll);
         final List<Poll> pfolder = getiPoll().getPollsByPollFolderId(this.secUserSecondary, this.pollFolder);
         assertEquals("Should be equals", 1, pfolder.size());
    }

    @Test
    public void testFindAllPoll(){
        assertNotNull(this.poll);
        final List<Poll> allPoll = getiPoll().findAll();
        assertEquals("Should be equals", 1, allPoll.size());

    }

    /**
     * Test Get Polls by creation date.
     */
    @Test
    public void testGetPollByIdandCreationDate(){
    	final Question question2 = createQuestion("Why the sky is blue?", "html");
        final Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK,-1);
        final Date yesterdayDate= calendarDate.getTime();
        final Calendar calendarDate2 = Calendar.getInstance();
        final Date todayDate = calendarDate2.getTime();
        createPoll(yesterdayDate, this.question, "FDK135", this.secUserSecondary, Boolean.TRUE, Boolean.TRUE);
        createPoll(todayDate, this.question, "FDK456", this.secUserSecondary, Boolean.TRUE, Boolean.TRUE);
        final List<Poll> pollList = getiPoll().getPollByIdandCreationDate(todayDate, this.secUserSecondary, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, pollList.size());
    }

    @Test
    public void testRetrievePollsByUserId(){
    	final Question question2 =  createQuestion("Why the sea is blue?","html");
    	createPoll(new Date(), question2, "FDK126", this.secUserSecondary, Boolean.TRUE, Boolean.TRUE);
    	final List<Poll> pollbyUser = getiPoll().retrievePollsByUserId(this.secUserSecondary, this.MAX_RESULTS, this.START);
    	assertEquals("Should be equals", 2, pollbyUser.size());
    }

    @Test
    public void testGetPollFolderBySecUser(){
    	createPollFolder("My Second Poll Folder", this.secUserSecondary);
    	createPollFolder("My Third Poll Folder", this.secUserSecondary);
    	final List<PollFolder> pollFolderbyUser = getiPoll().getPollFolderBySecUser(this.secUserSecondary);
    	assertEquals("Should be equals", 3, pollFolderbyUser.size());
    }

    @Test
    public void testPollsByPollFolder(){
    	final List<Poll> pollsbyFolder = getiPoll().getPollsByPollFolder(this.secUserSecondary, this.pollFolder);
    	assertEquals("Should be equals", 1, pollsbyFolder.size());
    }
}
