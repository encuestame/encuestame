/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link TweetPollDao}..
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 13, 2010 11:57:17 PM
 * @version $Id: change to one dolar simbol
 */
public class TestTweetPollDao  extends AbstractBaseTest{

    /** {@link SecUserSecondary}. **/
    private SecUserSecondary secondary;

    /** {@link QuestionsAnswers}. **/
    private QuestionsAnswers questionsAnswers1;

    /** {@link QuestionsAnswers}. **/
    private QuestionsAnswers questionsAnswers2;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch1;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch2;

    /**
     * Before.
     */
    @Before
    public void initData(){
      this.secondary = createSecondaryUser("jhon", createUser());
      final Questions question = createQuestion("who I am?", "");
      this.questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
      this.questionsAnswers2 = createQuestionAnswer("no", question, "12346");
      final TweetPoll tweetPoll = createPublishedTweetPoll(secondary.getSecUser(), question);
      this.pollSwitch1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
      this.pollSwitch2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
    }

    /**
     * Test retrieveTweetsPollSwitch.
     */
    @Test
    public void testRetrieveTweetsPollSwitch(){
        final TweetPollSwitch pollSwitch = getiTweetPoll().retrieveTweetsPollSwitch(this.pollSwitch1.getCodeTweet());
        assertNotNull(pollSwitch);
    }

}
