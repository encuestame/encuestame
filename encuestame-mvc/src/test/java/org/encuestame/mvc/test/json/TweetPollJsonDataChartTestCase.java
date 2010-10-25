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
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.controller.json.chart.TweetPollJsonDataChart;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
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
        final Question question = createQuestion("Real Madrid or Barcelona?", getSecondary().getSecUser());
        this.tweetPoll = createTweetPollPublicated(Boolean.TRUE, Boolean.TRUE, new Date(), getSecondary().getSecUser(), question);
        final QuestionsAnswers questionsAnswers1 = createQuestionAnswer("Yes", question, "hash1");
        final QuestionsAnswers questionsAnswers2 = createQuestionAnswer("No", question, "hash2");
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
        initService("/api/"+getSecondary().getUsername()+"/tweetPoll/votes.json", MethodJson.GET);
        setParameter("tweetPollId", "1234");
        final JSONObject response = callJsonService();
        final JSONObject error = getErrors(response);
        Assert.assertEquals("tweetPoll not found", error.get("error"));

        //Valid Vote.
        initService("/api/"+getSecondary().getUsername()+"/tweetPoll/votes.json", MethodJson.GET);
        setParameter("tweetPollId", this.tweetPoll.getTweetPollId().toString());
        final JSONObject response2 = callJsonService();
        final JSONObject sucess2 = getSucess(response2);
        final JSONArray listVotes = (JSONArray) sucess2.get("votesResult");
        Assert.assertEquals(listVotes.size(), 2); //answers
        final JSONObject firstAnswer = (JSONObject) listVotes.get(0);
        Assert.assertEquals(firstAnswer.get("results").toString(), "2");
        final JSONObject secondAnswer = (JSONObject) listVotes.get(1);
        Assert.assertEquals(secondAnswer.get("results").toString(), "3");
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
