/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
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

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link PollJsonServiceTest}
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2011
 */
public class PollJsonServiceTest extends AbstractJsonMvcUnitBeans{

    /**
     * Run retrieve polls by date.
     * @param actionType
     * @param date
     * @param maxResults
     * @param start
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void retrieveItemsbyDate() throws ServletException, IOException{
        // Search poll published today.
        final Date todayDate = new Date();
        final Question question = createQuestion("What is your favourite movie", "pattern");
        final Poll poll = createPoll(todayDate, question,
                          getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
                          Boolean.TRUE);
        Assert.assertNotNull(poll);
        initService("/api/survey/poll/search.json", MethodJson.GET);
        setParameter("typeSearch", "BYOWNER");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray polls = (JSONArray) success.get("poll");
        Assert.assertEquals("Should be equals ", polls.size(), 1);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("KEYWORD", "is", "100", "0").size(), 0);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("LASTDAY", null, "10", "0").size(), 0);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("LASTWEEK", null, "10", "0").size(), 0);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("FAVOURITES", null, "10", "0").size(), 0);
        this.createPoll(
                "Is Obama the best president of Unite States last 50th years?", new String[]{"Yes", "No"});
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("ALL", "is", "10", "0").size(), 0);
    }

    /**
     *
     * @param type
     * @param keyword
     * @param max
     * @param start
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONArray testSearchJsonService(final String type,
            final String keyword, final String max, final String start) throws ServletException, IOException {
         initService("/api/survey/poll/search.json", MethodJson.GET);
         if (keyword != null) {
             setParameter("keyword", keyword);
         }
         setParameter("typeSearch", "KEYWORD");
         setParameter("maxResults", max);
         setParameter("start", start);
         final JSONObject yesterdayResponse = callJsonService();
         //System.out.println("******************************************");
         final JSONObject yesterdaySuccess = getSucess(yesterdayResponse);
         final JSONArray yesterdayPolls = (JSONArray) yesterdaySuccess
                 .get("poll");
         return yesterdayPolls;
    }

    /**
     * Create poll json.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createPoll() throws ServletException, IOException{
        Assert.assertNotNull(this.createPoll("Who is the winner?", new String[]{"Yes", "No"}).get("id"));
        Assert.assertNotNull(this.createPoll(
                "Is Obama the best president of Unite States last 50th years?", new String[]{"Yes", "No"})
                .get("id"));
    }

    /**
     *
     * @param question
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONObject createPoll(final String question, final String[] answers) throws ServletException, IOException{
         initService("/api/survey/poll/create.json", MethodJson.POST);
         setParameter("questionName",question);
         for (int i = 0; i < answers.length; i++) {
             setParameter("listAnswers", answers[i]);
         }
         setParameter("showResults", "true");
         setParameter("showComments", "true");
         setParameter("notification", "true");
         final JSONObject response = callJsonService();
         final JSONObject success = getSucess(response);
         return (JSONObject) success.get("pollBean");
    }
}
