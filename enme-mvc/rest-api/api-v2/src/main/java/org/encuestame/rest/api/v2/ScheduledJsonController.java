package org.encuestame.rest.api.v2;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.Schedule;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.util.exception.EnMeException;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.TweetPollScheduledBean;
import org.encuestame.utils.web.ScheduledTweetPoll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScheduledJsonController extends AbstractJsonControllerV1 {
	
	
	 /**
     * Log.
     */
     private static Log log = LogFactory.getLog(ScheduledJsonController.class);


             /**
              * Publish tweet on social account.
              * @param request
              * @param response
              * @return
              * @throws JsonGenerationException
              * @throws JsonMappingException
              * @throws IOException
              */

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{type}/schedule/{id}", method = RequestMethod.POST)
    public ModelMap scheduleTweetpoll(
    		ScheduledTweetPoll bean,
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
        } catch (EnMeException e) {
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
    public ModelMap scheduleUnPublishTweetpoll(
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
        } catch (EnMeException e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
