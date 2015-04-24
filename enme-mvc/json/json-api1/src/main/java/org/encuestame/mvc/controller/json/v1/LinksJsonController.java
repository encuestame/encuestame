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
package org.encuestame.mvc.controller.json.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.enums.TypeSearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Links json controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 11, 2011
 */
@Controller
public class LinksJsonController extends AbstractJsonControllerV1{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * @api {get} /api/public/social/links/published.json Get Social links
     * @apiName GetSocialLinks
     * @apiGroup Links
     * @apiDescription Retrieve the social links where items has been published.
     * @apiParam {String} id - XXXX
     * @apiParam {String} type - XXXX
     * @apiParam {Number} [start - XXXX
     * @apiParam {Number} [max - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/public/social/links/published.json
     * @apiPermission none
     */
    @RequestMapping(value = "/api/public/social/links/published.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getPublishedSocialLinks(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "max", required = false) Integer max,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            //FUTURE: Add SEARCHPERIODS Filter.
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final TypeSearchResult searchResult = TypeSearchResult.getTypeSearchResult(type);
            // by tweetpoll
            if (TypeSearchResult.TWEETPOLL.equals(searchResult) && !id.isEmpty()) {
                final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(Long.valueOf(id), null);
                jsonResponse.put("links", getTweetPollService()
                        .getTweetPollLinks(tweetPoll, null, null, TypeSearchResult.getTypeSearchResult(type)));

            // by poll
            } else if (TypeSearchResult.POLL.equals(searchResult) && !id.isEmpty()) {
                final Poll poll = getPollService().getPollById(Long.valueOf(id));
                jsonResponse.put(
                        "links",
                        getTweetPollService().getTweetPollLinks(null, poll,
                                null,
                                TypeSearchResult.getTypeSearchResult(type)));
            } else if (TypeSearchResult.SURVEY.equals(searchResult) && !id.isEmpty()) {
                 //TODO: retrieve social links by SURVEY
            } else if (TypeSearchResult.PROFILE.equals(searchResult) && !id.isEmpty()) {
                //TODO: retrieve social links by PROFILE
            // by hashtag
            } else if (TypeSearchResult.HASHTAG.equals(searchResult) && !id.isEmpty()) {
                 jsonResponse.put("links", getFrontService().getHashTagLinks(getFrontService().getHashTagItem(id), start, max));
            } else {
                 // if not exist a type, send emtpy list.
                 jsonResponse.put("links", ListUtils.EMPTY_LIST);
            }
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
