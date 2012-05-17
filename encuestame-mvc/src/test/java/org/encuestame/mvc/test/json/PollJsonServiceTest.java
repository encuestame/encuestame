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
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link PollJsonServiceTest}
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2011
 */
public class PollJsonServiceTest extends AbstractJsonMvcUnitBeans{

    /** Max results query **/
    private Integer MAX_RESULTS = 10;

    /** Start results.*/
    private Integer START_ON = 0;
    
    /** **/
    private Question initQuestion;

    /** **/
    private Poll initPoll;
    
    /** **/
    private Calendar calDate = Calendar.getInstance();
    
    @Before
    public void initData(){

    	this.initQuestion = createQuestion("What is your favourite season",
				"pattern"); 
		calDate.add(Calendar.DATE, -2);
		this.initPoll = createPoll(calDate.getTime(), initQuestion,
				getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
				Boolean.TRUE);
    }

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
        Assert.assertEquals("Should be equals ", polls.size(), 2);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("KEYWORD", "is", "100", "0").size(), 0);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("LASTDAY", null, "10", "0").size(), 0);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("LASTWEEK", null, "10", "0").size(), 0);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("FAVOURITES", null, "10", "0").size(), 0);
        this.createPoll(
                "Is Obama the best president of Unite States last 50th years?", new String[]{"Yes", "No"});
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("ALL", "is", "10", "0").size(), 2);
        Assert.assertEquals("Should be equals ", this.testSearchJsonService("ALL", "is", "1", "0").size(), 2);
    }

    /**
     * Call the service to change status.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testChangeStatusPollProperties() throws ServletException, IOException{
        // Search poll published today.
        this.changePropertyPoll("change-open-status");
    }

    /**
     * Call the service to password restrictions.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testPasswordRestrictionPollProperties() throws ServletException, IOException{
        // Search poll published today.
        this.changePropertyPoll("password-restrictions");
    }

    /**
     * Call the service to display addtional info.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testAddtionalInfoPollProperties() throws ServletException, IOException{
        // Search poll published today.
        this.changePropertyPoll("additional-info");
    }

    /**
     * Call the service display notifications.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testNotificationsPollProperties() throws ServletException, IOException{
        // Search poll published today.
        this.changePropertyPoll("notifications");
    }

    /**
     * Call the service to change ip protection.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testIPProtectionPollProperties() throws ServletException, IOException{
        // Search poll published today.
        this.changePropertyPoll("ip-protection");
    }

    /**
     * Call the service to close the poll after finish the quota.
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCloseAfterQuotaPollProperties() throws ServletException, IOException{
        // Search poll published today.
        this.changePropertyPoll("close-after-quota");
    }

    /**
     * Call the service to update a property of a poll.
     * @param property
     * @throws ServletException
     * @throws IOException
     */
    private void changePropertyPoll(final String property) throws ServletException, IOException{
         // Search poll published today.
        final Date todayDate = new Date();
        final Question question = createQuestion("What is your favourite movie", "pattern");
        final Poll poll = createPoll(todayDate, question,
                          getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
                          Boolean.TRUE);
        Assert.assertNotNull(poll);
        //change-open-status
        initService("/api/survey/poll/"+property+"-poll.json", MethodJson.POST);
        setParameter("pollId", poll.getPollId().toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final Long polls = (Long) success.get("r");
        Assert.assertEquals("Should be equals ", polls.toString(), "0");
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void changeWrongPropertyPoll() throws ServletException, IOException{
        // Search poll published today.
       final Date todayDate = new Date();
       final Question question = createQuestion("What is your favourite movie", "pattern");
       final Poll poll = createPoll(todayDate, question,
                         getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
                         Boolean.TRUE);
       Assert.assertNotNull(poll);
       //change-open-status
       initService("/api/survey/poll/xxx-poll.json", MethodJson.POST);
       setParameter("pollId", poll.getPollId().toString());
       final JSONObject response = callJsonService();
       final JSONObject error = getErrors(response);
       //System.out.println(error);
       final String message = (String) error.get("message");
       Assert.assertEquals("Should be equals ",message, "type not valid");
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
         setParameter("typeSearch", type);
         setParameter("maxResults", max);
         setParameter("start", start);
         final JSONObject yesterdayResponse = callJsonService();
         final JSONObject yesterdaySuccess = getSucess(yesterdayResponse);
         final JSONArray yesterdayPolls = (JSONArray) yesterdaySuccess
                 .get("poll");
         Assert.assertNotNull(yesterdayPolls);
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
         setParameter("showComments", "APPROVE");
         setParameter("notification", "true");
         final JSONObject response = callJsonService();
         final JSONObject success = getSucess(response);
         return (JSONObject) success.get("pollBean");
    }

    /**
     * Search Polls by Folder.
     * @param folderId
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONArray searchPollByFolder(final Long folderId)
            throws ServletException, IOException {
        initService("/api/survey/poll/searchby-folder.json", MethodJson.GET);
        setParameter("folderId", folderId.toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray polls = (JSONArray) success.get("pollsByFolder");
        return polls;
    }

    /**
     * Search polls by keyword question.
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONArray searchPollByKeyword(final String keyword,
            final Integer maxResults, final Integer start)
            throws ServletException, IOException {
        initService("/api/survey/poll/searchby-keyword.json", MethodJson.GET);
        setParameter("keyword", keyword);
        setParameter("maxResults", maxResults.toString());
        setParameter("start", start.toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray polls = (JSONArray) success.get("pollsbyKey");
        return polls;
    }

    /**
     * Searchs Poll by date.
     * @param date
     * @param maxResults
     * @param start
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONArray searchPollByDate(final Date date,
            final Integer maxResults, final Integer start)
    throws ServletException, IOException {
        initService("/api/survey/poll/searchby-date.json", MethodJson.GET);
        final String convertToStringDate = DateUtil.DOJO_DATE_FORMAT.format(date);
        setParameter("date", convertToStringDate);
        setParameter("maxResults", maxResults.toString());
        setParameter("start", start.toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray polls = (JSONArray) success.get("pollsByDate");
        return polls;
    }

    /**
     * Test Search polls by type (Keyword, folder or date).
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testSearchPollByType() throws ServletException, IOException {
        final Question question = createQuestion(
                "What is your favourite season", "pattern");
        final Poll poll = createPoll(new Date(), question,
                getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
                Boolean.TRUE);
        Assert.assertNotNull(poll);
        final PollFolder pfolder = createPollFolder("My Polls folder",
                getSpringSecurityLoggedUserAccount());
        poll.setPollFolder(pfolder);
        Assert.assertNotNull(pfolder);

        // Search Poll by folder.
        Assert.assertNotNull(this.searchPollByFolder(poll.getPollFolder()
                .getId()));
        Assert.assertEquals("Should be equals ",
                this.searchPollByFolder(poll.getPollFolder().getId()).size(), 1);

        // Search Poll by keyword.
        flushIndexes();
        Assert.assertNotNull(this.searchPollByKeyword("What", this.MAX_RESULTS, this.START_ON));
        Assert.assertEquals("Should be equals ",
                this.searchPollByKeyword("What", this.MAX_RESULTS, this.START_ON).size(), 2);

        // Search Polls by date.
        final Calendar lastWeek = Calendar.getInstance();
        lastWeek.add(Calendar.DATE, -7);
        // Created another poll
           createPoll(new Date(), question,
                getSpringSecurityLoggedUserAccount(), Boolean.TRUE,
                Boolean.TRUE);
        Assert.assertNotNull(this.searchPollByDate(new Date(), this.MAX_RESULTS, this.START_ON));
        Assert.assertEquals("Should be equals ",
                this.searchPollByDate(lastWeek.getTime(), this.MAX_RESULTS, this.START_ON).size(), 2);
    }
    
    /**
     * 
     * @param pollId
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONObject retrievePollDetail(final Long pollId)
            throws ServletException, IOException {
        initService("/api/survey/poll/detail.json", MethodJson.GET);
        setParameter("id", pollId.toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response); 
        final JSONObject detail = (JSONObject) success.get("poll");
        return detail;
    }

    /**
     * 
     * @throws ServletException
     * @throws IOException
     */
	@Test
	public void testRetrievePollDetail() throws ServletException, IOException {
		Assert.assertNotNull(this.initPoll); 
		final JSONObject jObj = this.retrievePollDetail(initPoll.getPollId()); 
		final JSONObject pollbean = (JSONObject) jObj.get("poll_bean");  
		Assert.assertEquals("Should be equals ", pollbean.get("id")
				.toString(), initPoll.getPollId().toString()); 
	}
	
	/**
	 * 
	 * @param pollId
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private JSONObject deletePoll(final Long pollId) throws ServletException, IOException  {
		initService("/api/survey/poll/remove.json", MethodJson.GET);
		setParameter("pollId", pollId.toString());
		final JSONObject response = callJsonService(); 
		return response;
	}
	
	/**
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void testDeletePoll() throws ServletException, IOException{
		Assert.assertNotNull(initPoll); 
		final JSONObject jObj = this.deletePoll(initPoll.getPollId()); 
		assertSuccessResponse(jObj);
	}
}
