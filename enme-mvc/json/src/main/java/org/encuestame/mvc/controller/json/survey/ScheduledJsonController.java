package org.encuestame.mvc.controller.json.survey;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.TweetPollScheduledBean;
import org.encuestame.utils.web.ScheduledTweetPoll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScheduledJsonController extends AbstractJsonController {
	
	
	 /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());
	

    /**
     * Publish tweet on social account.
     * @param twitterAccountsId
     * @param question
     * @param scheduled
     * @param hashtags
     * @param answers
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{type}/schedule/{id}", method = RequestMethod.POST)
    public @ResponseBody ModelMap scheduleTweetpoll(
    		@RequestBody ScheduledTweetPoll bean,
    		@PathVariable final Long id,
    		@PathVariable final String type,
            HttpServletRequest request, HttpServletResponse response,
            final UserAccount user)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
        	final TweetPollSavedPublishedStatus tweetPoll = getTweetPollService().getTweetPollSavedPublishedStatusById(id);
            final Schedule schedule = this.getTweetPollService().createTweetPollPublishedStatusScheduled(
            		bean.getScheduledDate(),
            		TypeSearchResult.getTypeSearchResult(type),
            		tweetPoll);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("schedulded_item", ConvertDomainBean.convertScheduletoBean(schedule));
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     *
     * @param bean
     * @param request
     * @param response
     * @param user
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{type}/schedule", method = RequestMethod.POST)
    public @ResponseBody ModelMap scheduleUnPublishTweetpoll(
    		@RequestBody TweetPollScheduledBean bean,
    		@PathVariable final String type,
            HttpServletRequest request, HttpServletResponse response,
            final UserAccount user)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
        	final List<Schedule> schedules = getTweetPollService().createTweetPollScheduled(bean,
        			TypeSearchResult.getTypeSearchResult(type));
        	final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("schedulded_items", ConvertDomainBean.convertListScheduletoBean(schedules));
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
