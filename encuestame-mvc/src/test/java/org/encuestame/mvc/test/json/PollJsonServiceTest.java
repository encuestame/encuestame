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

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link PollJsonServiceTest}
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2011
 * @version $Id:$
 */
public class PollJsonServiceTest extends AbstractJsonMvcUnitBeans{

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
    //@Test
    public void retrieveItemsbyDate() throws ServletException, IOException{
        final Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK, -1);
        final Date yesterdayDate= calendarDate.getTime();
        initService("/api/poll/searchby-date.json", MethodJson.GET);
        setParameter("maxResults", "10");
        setParameter("start", "0");
        setParameter("date", yesterdayDate.toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray polls = (JSONArray) success.get("pollsByDate");
       //System.out.println("My Poll size--> " + polls.size());
    }

    @Test
    public void createPoll() throws ServletException, IOException{
        initService("/api/poll/create.json", MethodJson.POST);
         setParameter("questionName", "Who is the winner");
         setParameter("listAnswers", "yes");
         setParameter("showResults", "true");
         setParameter("showComments", "true");
         setParameter("notification", "true");
         final JSONObject response = callJsonService();
         final JSONObject success = getSucess(response);
         final JSONObject pollBean = (JSONObject) success.get("pollBean");
         Assert.assertNotNull(pollBean.get("id"));
    }
}
