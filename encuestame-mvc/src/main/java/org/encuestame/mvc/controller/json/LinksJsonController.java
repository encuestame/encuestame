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
package org.encuestame.mvc.controller.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.enums.TypeSearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Links json controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 11, 2011
 */
@Controller
public class LinksJsonController extends AbstractJsonController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param id
     * @param type
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/public/social/links/published.json", method = RequestMethod.GET)
    public ModelMap getPublishedSocialLinks(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "type", required = true) String type,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
        	//FUTURE: Add SEARCHPERIODS Filter.
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            if (TypeSearchResult.TWEETPOLL.name().equals(type)) {
                final TweetPoll tweetPoll = getTweetPollService()
                        .getTweetPollById(Long.valueOf(id), null);
                jsonResponse.put("links", getTweetPollService()
                        .getTweetPollLinks(tweetPoll));
            } else if (TypeSearchResult.POLL.name().equals(type)) {
                 //TODO: retrieve social links by POLL
            } else if (TypeSearchResult.SURVEY.name().equals(type)) {
                 //TODO: retrieve social links by SURVEY
            } else if (TypeSearchResult.PROFILE.name().equals(type)) {
                //TODO: retrieve social links by PROFILE
            } else  if (TypeSearchResult.HASHTAG.name().equals(type)) {
                 jsonResponse.put("links", getFrontService().getHashTagLinks(getFrontService().getHashTagItem(id)));
            }
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
