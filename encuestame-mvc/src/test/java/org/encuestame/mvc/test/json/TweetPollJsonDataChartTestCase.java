/*
 ************************************************************************************
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
package org.encuestame.mvc.test.json;

import java.util.Date;

import junit.framework.Assert;

import org.encuestame.business.service.TweetPollService;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.controller.json.chart.TweetPollJsonDataChart;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link TweetPollJsonDataChart}.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 24, 2010 5:00:45 PM
 * @version $Id:$
 */
public class TweetPollJsonDataChartTestCase extends AbstractJsonMvcUnitBeans{

    /**
     * TweetPoll.
     */
    TweetPoll tweetPoll;

    /**
     * {@link TweetPollService}.
     */
    private ITweetPollService tweetPollService;

    /**
     * Answer 1.
     */
    TweetPollSwitch answer1;

    /**
     * Answer 2.
     */
    TweetPollSwitch answer2;

    /**
     * Init
     */
    @Before
    public void init(){
        final Question question = createQuestion("Real Madrid or Barcelona?", getSpringSecurityLoggedUserAccount().getAccount());
        this.tweetPoll = createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), getSpringSecurityLoggedUserAccount(), question);
        final QuestionAnswer questionsAnswers1 = createQuestionAnswer("Yes", question, "hash1");
        final QuestionAnswer questionsAnswers2 = createQuestionAnswer("No", question, "hash2");
        this.answer1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
        this.answer2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
        getTweetPollService().tweetPollVote(answer1, "80.23.43.23");
        getTweetPollService().tweetPollVote(answer2, "80.33.13.23");
        getTweetPollService().tweetPollVote(answer2, "80.13.13.43");
        getTweetPollService().tweetPollVote(answer2, "30.33.13.23");
        getTweetPollService().tweetPollVote(answer1, "80.33.13.13");
    }

    /**
     * Test /{username}/tweetPoll/votes.json.
     * @throws Exception
     */
    @Test
    public void tweetPollJsonDataChartTest() throws Exception {
        //Invalid Vote.
        initService("/api/"+getSpringSecurityLoggedUserAccount().getUsername()+"/tweetpoll/votes.json", MethodJson.GET);
        setParameter("tweetPollId", "1234");
        final JSONObject response = callJsonService();
        final String error = getErrorsMessage(response);
        Assert.assertEquals("tweet poll invalid with this id 1234", error.toString());

        //Valid Vote.
        initService("/api/"+getSpringSecurityLoggedUserAccount().getUsername()+"/tweetpoll/votes.json", MethodJson.GET);
        setParameter("tweetPollId", this.tweetPoll.getTweetPollId().toString());
        final JSONObject response2 = callJsonService();
        final JSONObject sucess2 = getSucess(response2);
        final JSONArray listVotes = (JSONArray) sucess2.get("votesResult");
        // votesResult":[
        //{"id":72297,"question_label":"Yes","percent":"28.57%","color":"#08D0B3","answerId":72297,"votes":2,"answerName":"Yes"},
        //{"id":72298,"question_label":"No","percent":"42.86%","color":"#FFE133","answerId":72298,"votes":3,"answerName":"No"}
        Assert.assertEquals(listVotes.size(), 2); //answers
        final JSONObject firstAnswer = (JSONObject) listVotes.get(0);
        Assert.assertEquals(firstAnswer.get("votes").toString(), "2");
        final JSONObject secondAnswer = (JSONObject) listVotes.get(1);
        Assert.assertEquals(secondAnswer.get("votes").toString(), "3");

        //
        initService("/api/"+getSpringSecurityLoggedUserAccount().getUsername()+"/tweetpoll/"+this.tweetPoll.getTweetPollId()+"/votes.json", MethodJson.GET);
        final JSONObject response3 = callJsonService();
        final JSONObject success3 = getSucess(response3);
        Assert.assertNotNull(response3);
        Assert.assertNotNull(success3);
        //{"error":{},"success":{"votesResult":[
        //{"id":72314,"question_label":"Yes","percent":"28.57%","color":"#954679","answerId":72314,"votes":2,"answerName":"Yes"},
        //{"id":72315,"question_label":"No","percent":"42.86%","color":"#1879BE","answerId":72315,"votes":3,"answerName":"No"}]}}
        final JSONArray listVotes2 = (JSONArray) sucess2.get("votesResult");
        Assert.assertEquals(listVotes2.size(), 2); //answers
        final JSONObject firstAnswer2 = (JSONObject) listVotes2.get(0);
        Assert.assertEquals(firstAnswer2.get("votes").toString(), "2");
        final JSONObject secondAnswer2 = (JSONObject) listVotes2.get(1);
        Assert.assertEquals(secondAnswer2.get("votes").toString(), "3");

        initService("/api/chart/tweetpoll/votes.json", MethodJson.GET);
        setParameter("tweetPollId", this.tweetPoll.getTweetPollId().toString());
        final JSONObject response4 = callJsonService();
        final JSONObject success4 = getSucess(response4);
        Assert.assertNotNull(response4);
        Assert.assertNotNull(success4);
    }

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService the tweetPollService to set
     */
    @Autowired
    public void setTweetPollService(final ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }
}
