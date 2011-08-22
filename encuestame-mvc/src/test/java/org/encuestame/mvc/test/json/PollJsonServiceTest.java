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

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * {@link PollJsonServiceTest}
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2011
 * @version $Id:$
 */
public class PollJsonServiceTest extends AbstractJsonMvcUnitBeans{

    /** {@link Poll}. **/
    private Poll poll;

    /** {@link Question}. **/
    private Question question;

    private Calendar calendarDate;

    private Date yesterdayDate;

    @Before
    public void initService(){
        this.calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK,-1);
        this.yesterdayDate= calendarDate.getTime();
        final Calendar calendarDate2 = Calendar.getInstance();
        final Date todayDate = calendarDate2.getTime();
        this.question = createQuestion("Where are you from?", "");
        this.poll = createPoll(yesterdayDate, question, getSpringSecurityLoggedUserAccount(), true, true);
    }

    /**
     * Test search polls by date.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    @Ignore //TODO: peding 1.1.37
    public void testCountUsersByGroup() throws ServletException, IOException{
        Assert.assertEquals(retrieveItemsbyDate("date", this.yesterdayDate, 10, 0 ).intValue(), 1);
    }

    /**
     *  Run retrieve polls by date.
     * @param actionType
     * @param date
     * @param maxResults
     * @param start
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public Integer retrieveItemsbyDate(final String actionType, final Date date,
                final Integer maxResults, final Integer start) throws ServletException, IOException{
        initService("/api/survey/poll/searchPollby"+actionType+".json", MethodJson.GET);
        setParameter("maxResults", maxResults.toString());
        setParameter("start", start.toString());
        setParameter("date", date.toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray polls = (JSONArray) success.get("pollsByDate");
        return polls.size();
    }
}
