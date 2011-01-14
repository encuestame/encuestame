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

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.QuestionAnswer;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * UserDao.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 27, 2009
 * @version $Id$
 */
public class TestUserDao extends AbstractBase {

	/** {@link Account} **/
    private Account account;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /** {@link SocialAccount} **/
    private SocialAccount socialAccount;

    /** {@link QuestionAnswer}. **/
    private QuestionAnswer questionsAnswers1;

    /** {@link QuestionAnswer}. **/
    private QuestionAnswer questionsAnswers2;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch1;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch2;

    /** {@link TweetPoll}. **/
    private TweetPoll tweetPoll;

    /** {@link Poll} **/
    private Poll poll;

    /** {@link Question} **/
    private Question question;

    /**
     * Before.
     **/
    @Before
    public void initService(){
        this.account = createUser();
        this.userAccount = createSecondaryUser("user 1", this.account);
        this.socialAccount = createDefaultSettedVerifiedTwitterAccount(this.account);
        this.question = createQuestion("What day is today?", "");

    }

    /***
     *Test Create User.
     */
    @Test
    public void testCreateUser() {
       final UserAccount user = createSecondaryUser("user 1", this.account);
       assertNotNull(user);
    }

    /**
     * Test delete user method.
     **/
    @Test
    public void testDeleteUser() {
        final UserAccount user = createSecondaryUser("user 2", this.account);
         getAccountDao().delete(user);
        assertEquals("Should be equals",1, getAccountDao().findAll().size());
    }

    /**
     * Test find all users method.
     */
    @Test
    public void testFindAllUsers() {
        createSecondaryUser("user 1", this.account);
        createSecondaryUser("user 2", this.account);
        assertEquals("Should be equals",3, getAccountDao().findAll().size());
    }

    /**
     * Test Update user.
     **/
    @Test
    public void testUpdateUser(){
        final String newPassword = "67809";
        final String newEmail = "user2@users.com";
        final UserAccount user = createSecondaryUser("user 1", this.account);
        user.setPassword(newPassword);
        user.setUserEmail(newEmail);
        getAccountDao().saveOrUpdate(user);
         final UserAccount retrieveUser = getAccountDao()
         .getSecondaryUserById(Long.valueOf(user.getUid()));
     assertEquals("Password should be",newPassword,
                     retrieveUser.getPassword());
        assertEquals("Email should be",newEmail,
               retrieveUser.getUserEmail());
    }

    /**
     * Test Get User by Username.
     **/
    @Test
    public void testGetUserByUsername(){
        final UserAccount user = createSecondaryUser("user 3", this.account);
        final UserAccount retrieveUser = getAccountDao()
        .getUserByUsername(user.getUsername());
        assertEquals("Username should be",user.getUsername(), retrieveUser.getUsername());
    }

    /**
     * Test Assing Group to User.
     **/
/*    @SuppressWarnings("unchecked")
    @Test
    public void testAssingGroupToUser(){
         final SecUserSecondary user = createSecondaryUser("user 4", this.userPrimary);
         final SecGroup group = super.createGroups("group 1");
         user.getSecGroups().add(group);
         getSecGroup().saveOrUpdate(user);
         assertEquals("Should be equals", 1, user.getSecGroups().size());
    }*/

    /**
     * Test Add Permission to Group.
     */
    @Test
    public void testAddPermissionToGroup(){
        final Permission editor = createPermission("editor");
        final Permission admon = createPermission("publisher");
        final Permission permission = createPermission("administrator");
        final Group group = createGroups("group 1");
        group.getPermissions().add(editor);
        group.getPermissions().add(admon);
        group.getPermissions().add(permission);
        getGroup().saveOrUpdate(group);
        assertEquals("Should be equals", 3, group.getPermissions().size());
    }

    /**
     * Test getSecondaryUsersByUserId.
     */
    public void testGetSecondaryUsersByUserId(){
         createSecondaryUser("user 1", this.account);
         createSecondaryUser("user 2", this.account);
         final List<UserAccount> userList = getAccountDao().getSecondaryUsersByUserId(this.account.getUid());
         assertEquals("Should be equals", 2, userList.size());
    }

    /**
     * Test.
     */
    @Test
    public void testSearchUsersByEmail(){
        final UserAccount secondary = createSecondaryUser("jhon", this.account);
        createSecondaryUser("paola", this.account);
        final List<UserAccount> users = getAccountDao().searchUsersByEmail(secondary.getUserEmail());
        assertEquals("Should be equals", 1, users.size());
    }

    /**
     * Test Retrieve Total Users.
     */
    @Test
    public void testRetrieveTotalUsers(){
    	 final Long totalUserAccount = getAccountDao().retrieveTotalUsers(this.account);
    	 System.out.println("TOTAL USER ACCOUNT --> "+totalUserAccount);
    	 assertEquals("Should be equals", 1, 1);

    }

    /**
     * Test Retrieve List Owner Users.
     */
    @Test
    public void testretRieveListOwnerUsers(){
    	final List<UserAccount> usersAccount = getAccountDao().retrieveListOwnerUsers(this.account, 5, 0);
         assertEquals("Should be equals", 1, usersAccount.size());
    }

    /**
     * Test Get Twitter Account.
     */
    @Test
    public void testGetTwitterAccount(){
    	final SocialAccount social = getAccountDao().getTwitterAccount(this.socialAccount.getId());
        assertEquals("Should be equals", this.socialAccount.getId(), social.getId());
    }

    /**
     * Test Get User by Id.
     */
    @Test
    public void testGetUserById(){
    	final Account userAccount = getAccountDao().getUserById(this.account.getUid());
    	 assertEquals("Should be equals", this.account.getUid(), userAccount.getUid());
     }

    /**
     * Test Get User by Email.
     */
    @Test
    public void testGetUserByEmail(){
    	final UserAccount userAcc = getAccountDao().getUserByEmail(this.userAccount.getUserEmail());
    	assertNotNull(userAcc);
      	assertEquals("Should be equals", this.userAccount.getUserEmail(), userAcc.getUserEmail());
    }

    /**
     * Test get Users By Username.
     */
    @Test
    public void testGetUsersByUsername(){
    	final UserAccount user = getAccountDao().getUserByUsername(this.userAccount.getUsername());
    	assertEquals("Should be equals", this.userAccount.getUsername(), user.getUsername());
    }

    /**
     * Test Get Twitter Verified Account By User.
     */
    @Test
    public void testgetTwitterVerifiedAccountByUser(){
    	final List<SocialAccount> socAccount = getAccountDao().getTwitterVerifiedAccountByUser(this.account);
    	assertEquals("Should be equals", this.socialAccount.getVerfied(), socAccount.get(0).getVerfied());
    	assertEquals("Should be equals", 1, socAccount.size());
    }

    /**
     * Test Get Total TweetPoll by User.
     */
    @Test
    public void testGetTotalTweetPollByUser(){
    	this.questionsAnswers1 = createQuestionAnswer("monday", question, "12345");
        this.questionsAnswers2 = createQuestionAnswer("sunday", question, "12346");
        this.tweetPoll = createPublishedTweetPoll(userAccount.getAccount(), question);
        this.pollSwitch1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
        this.pollSwitch2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
        createTweetPollResult(pollSwitch1, "192.168.0.1");
        createTweetPollResult(pollSwitch1, "192.168.0.2");
        createTweetPollResult(pollSwitch2, "192.168.0.3");
        createTweetPollResult(pollSwitch2, "192.168.0.4");
     	final List<Long> tweets = getAccountDao().getTotalTweetPollByUser(this.account.getUid());
      	assertEquals("Should be equals", 1, tweets.size());
    }

    /**
     * Test Get Total Poll by User.
     */
    @Test
    public void testGetTotalPollByUser(){
    	this.poll = createPoll(new Date(), this.question, "FDK125", this.account, Boolean.TRUE, Boolean.TRUE);
    	final List<Long> polls = getAccountDao().getTotalPollByUser(this.account.getUid());
    	assertEquals("Should be equals", 1, polls.size());
    }
}
