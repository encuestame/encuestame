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
package org.encuestame.mvc.controller.json.chart;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.TweetPollResultsBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TweetPoll Json Data Chart.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 15, 2010 10:30:29 AM
 */
@Controller
public class JsonDataChart extends AbstractJsonController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());
    
    /**
     * JSON service to retrieve vote of a {@link Poll}.
     * @param username username
     * @param id poll id.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/{username}/poll/votes.json", method = RequestMethod.GET)
    public ModelMap getPollVotes(
    		@PathVariable String username,
            @RequestParam(value = "id") Long pollId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        log.debug("PollId " + pollId);
        log.debug("PollId " + (pollId instanceof Long));
        // TODO: we need check if user able to display this tweetpoll. eg. If is
        // published or if is public
        //this.getTweetPollVotesStore(pollId, response);
        try {
            final Map<String, Object> jsonResult = new HashMap<String, Object>();
            //results by tweetpoll id.
            final Poll poll = getPollService().getPollById(pollId, username);
            final List<PollBeanResult> results =getPollService().getResultVotes(poll);
            jsonResult.put("votesResult", results);
            log.debug("Poll results " + results.size());
            setItemResponse(jsonResult);
        } catch (EnMeNoResultsFoundException e) {
            log.equals(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }    


    /**
     * Get votes for {@link TweetPoll}.
     * @param username
     * @param tweetPollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/{username}/tweetpoll/votes.json", method = RequestMethod.GET)
    public ModelMap get(@PathVariable String username,
            @RequestParam(value = "tweetPollId") Long tweetPollId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        log.debug("TweetPollId " + tweetPollId);
        log.debug("TweetPollId " + (tweetPollId instanceof Long));
        // TODO: we need check if user able to display this tweetpoll. eg. If is
        // published or if is public
        this.getTweetPollVotesStore(tweetPollId, response);
        return returnData();
    }

    /**
     *
     * @param username
     * @param id
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/{username}/tweetpoll/{id}/votes.json", method = RequestMethod.GET)
    public ModelMap getVotes(
            @PathVariable String username,
            @PathVariable Long id,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        // TODO: we need check if user able to display this tweetpoll. eg. If is
        // published or if is public
        this.getTweetPollVotesStore(id, response);
        return returnData();
    }

    /**
     *
     * @param username
     * @param id
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/tweetpoll/{username}/answer/{id}/votes.json", method = RequestMethod.GET)
    public ModelMap getAnswerVotes(
            @PathVariable String username,
            @PathVariable Long id,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        // TODO: we need check if user able to display this tweetpoll. eg. If is
        // published or if is public
        //this.getVotesStore(id, response); //TODO: response votes for only 1 answer.
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        setItemResponse(jsonResponse);
        return returnData();
    }

    /**
     * Retrieve a list of votes of one tweetpoll. 
     * @param tweetPollId tweetpoll id.
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return 
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/chart/tweetpoll/votes.json", method = RequestMethod.GET)
    public ModelMap getVotes(
            @RequestParam(value = "tweetPollId") Long tweetPollId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        log.debug("TweetPollId " + tweetPollId);
        log.debug("TweetPollId " + (tweetPollId instanceof Long));
        // TODO: we need check if user able to display this tweetpoll. eg. If is
        // published or if is public
        this.getTweetPollVotesStore(tweetPollId, response);
        return returnData();
    }

    /**
     * Get tweetpoll votes store.
     * @param tweetPollId tweetpoll id
     * @param response {@link HttpServletResponse}.
     */
    private void getTweetPollVotesStore(
            final Long tweetPollId,
            final HttpServletResponse response) {
        try {
            final Map<String, Object> jsonResult = new HashMap<String, Object>();
            //results by tweetpoll id.
            final List<TweetPollResultsBean> results = getTweetPollService().getResultsByTweetPollId(tweetPollId);
            jsonResult.put("votesResult", results);
            log.debug("TweetPoll results " + results.size());
            setItemResponse(jsonResult);
        } catch (EnMeNoResultsFoundException e) {
            log.equals(e);
            setError(e.getMessage(), response);
        }
    }
}
