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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.ProfileRatedTopBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Frontend json controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 04, 2011
 */

@Controller
public class FrontEndJsonController extends AbstractJsonControllerV1{

    /** Log. **/
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @api {get} /api/common/frontend/stream.json Stream Home Items
     * @apiName GetHomeItems
     * @apiGroup FrontEnd
     * @apiDescription Return all items that will be displayed on the main page (Tweetpoll, Poll, Survey)
     * @apiParam {String} period SearchPeriods are Search by date range.  All periods defined in SearchPeriods are available.
     * @apiParam {Number} maxResults Maximum number of comments to be displayed as search result.
     * @apiParam {Number} start The minimum number of comments to show in the response.
     * @apiParam {String} typeFilter
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/frontend/stream.json
     * @apiPermission none
     * @apiSuccessExample
            {
              "error": {

              },
              "success": {
                "frontendItems": [
                  {
                    "hashtags": [
                      {
                        "id": 17,
                        "size": 15,
                        "hashTagName": "realestate",
                        "hits": 1
                      },
                      {
                        "id": 18,
                        "size": 15,
                        "hashTagName": "online",
                        "hits": 1
                      }
                    ],
                    "is_password_restriction": null,
                    "owner_username": "demo10",
                    "relative_time": null,
                    "total_votes": 38,
                    "hits": 21,
                    "item_type": "poll",
                    "like_votes": null,
                    "dislike_votes": null,
                    "create_date": "2011-12-04",
                    "relevance": 12,
                    "favorite": null,
                    "latitude": null,
                    "longitude": null,
                    "additional_info": null,
                    "show_comments": null,
                    "is_show_results": null,
                    "folder_id": null,
                    "is_show_additional_info": null,
                    "is_close_after_date": null,
                    "close_date": null,
                    "is_close_after_quota": null,
                    "close_quota": null,
                    "is_ip_restricted": null,
                    "ip_restricted": null,
                    "multiple_response": null,
                    "total_comments": 0,
                    "id": 24,
                    "question": {
                      "question_name": "Who has hosted every season of Dancing with the Stars?",
                      "slug": "who-has-hosted-every-season-of-dancing-with-the-stars%3F",
                      "hits": null,
                      "version": null,
                      "state_id": null,
                      "id": 100,
                      "uid": 10,
                      "pattern": "SINGLE_SELECTION",
                      "widget": "encuestame.org.core.commons.questions.patterns.SingleOptionResponse",
                      "list_answers": [

                      ]
                    },
                    "userId": null,
                    "hastags_string": "realestate,online,internet"
                  }
                ]
              }
            }
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/common/frontend/stream.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getFrontendItems(
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "maxResults", required = false) Integer maxResults,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "type", required = false) String typeFilter,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                if (period == null ) {
                    throw new EnMeSearchException("search params required.");
                } else {
                    if (typeFilter == null ) {
                        typeFilter = "all";
                    }
                    jsonResponse.put("frontendItems", this.filterHomeItems(typeFilter, period, start, maxResults, request));
                    setItemResponse(jsonResponse);
                   }
            } catch (Exception e) {
                 e.printStackTrace();
                 log.error(e);
            }
            return returnData();
        }

    /**
     * @api {get} /api/common/frontend/topusers.json List Top Users
     * @apiVersion 1.0.0
     * @apiName GetTopUsers
     * @apiGroup FrontEnd
     * @apiDescription Get user rated top.
     * @apiParam {Boolean} status
     * @apiParam {Number} end
     * @apiParam {Number} start
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/frontend/topusers.json
     * @apiPermission none
     * @apiSuccessExample
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/common/frontend/topusers.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getUserRatedTop(
        @RequestParam(value = "status", required = false) Boolean status,
        @RequestParam(value = "end", required = false) Integer end,
        @RequestParam(value = "start", required = false) Integer start,
        HttpServletRequest request,
        HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            if (status == null ){
                throw new EnMeSearchException("search params required.");
            } else {
                List<ProfileRatedTopBean> itemList = getFrontService().getTopRatedProfile(status);
                if (start != null && end != null && start < end) {
                    if (end <= itemList.size()) {
                        itemList = itemList.subList(start, end);
                    } else if (start <= itemList.size()) {
                        itemList = itemList.subList(start, itemList.size());
                    } else if (start > itemList.size()) {
                        itemList = ListUtils.EMPTY_LIST;
                    }
                }
                jsonResponse.put("profile", itemList);
                setItemResponse(jsonResponse);
               }
        } catch (Exception e) {
             e.printStackTrace();
             log.error(e);
        }
        return returnData();
    }


//    /*
//     * Retrieve the stats for each component hastahg/twpoll/poll/survey.
//     * @param id id to identify the item
//     * @param type type of component
//     * @param request {@link HttpServletRequest}
//     * @param response {@link HttpServletResponse}
//     * @return json string.
//     * @throws JsonGenerationException
//     * @throws JsonMappingException
//     * @throws IOException
//     */

    /*
     * @deprecated
     * @api {get} /api/common/frontend/:type/stats.json HashtagStats
     * @apiVersion 1.0.0
     * @apiName GetTopUsers
     * @apiGroup FrontEnd
     * @apiDescription Get user rated top.
     * @apiParam {Boolean} status
     * @apiParam {Number} end
     * @apiParam {Number} start
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/frontend/hashtag/stats.json
     * @apiPermission none
     * @apiSuccessExample
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/common/frontend/{type}/stats.json", method = RequestMethod.GET )
    public @ResponseBody ModelMap getGenericStats(
            @RequestParam(value = "id", required = false) String id,
            @PathVariable final String type,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        try {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final TypeSearchResult filter = TypeSearchResult
                    .getTypeSearchResult(type);
            final Map<String, String> stats = new HashMap<String, String>();
            if (filter.equals(TypeSearchResult.HASHTAG)) {
                stats.put("Created By", "admin");
                stats.put("Created", "4 years ago");
                stats.put("Hits", "1,500");
                stats.put("Likes / Dislike Rate", "34");
                stats.put("Average", "1.4");
            }
            jsonResponse.put("stats", stats);
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return returnData();
    }

    /**
     * @api {put} /api/frontend/home/:type/vote Vote Items
     * @apiName PutVoteItem
     * @apiGroup FrontEnd
     * @apiPermission user
     * @apiDescription API JSON service to vote a published item.
     * @apiParam {Number} id
     * @apiParam {String} type
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/frontend/stream.json

     * @apiSuccessExample
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/frontend/home/{type}/vote", method = RequestMethod.PUT)
    public @ResponseBody ModelMap voteHome(
            @RequestParam(value = "id", required = true) Long id,
            @PathVariable final String type,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            try {
                final String ip = EnMeUtils.getIP(request, EnMePlaceHolderConfigurer.getBooleanProperty("application.proxyPass"));
                final Status status = getFrontService().registerVote(id, TypeSearchResult.getTypeSearchResult(type), ip);
                jsonResponse.put("status", status.name());
                setItemResponse(jsonResponse);
            } catch (Exception ex) {
                setError(ex.getMessage(), response);
            }
            return returnData();
    }

    /**
     * @api {put} /api/help/status Change Status Help
     * @apiVersion 1.0.0
     * @apiName PutHelpStatus
     * @apiGroup FrontEnd
     * @apiDescription Update the help status of a page
     * @apiParam {String} path
     * @apiParam {Boolean} status
     * @apiSampleRequest http://www.encuestame.org/demo/api/help/status
     * @apiPermission none
     * @apiSuccessExample
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/help/status", method = RequestMethod.PUT)
    public @ResponseBody ModelMap voteHome(
            @RequestParam(value = "path", required = true) String path,
            @RequestParam(value = "status", required = true) Boolean status) throws IOException {
        try {
            if(getListPaths().indexOf(path) != -1)
            getSecurityService().updateHelpStatus(path, getUserAccount(), status);
            setFailedResponse();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            setFailedResponse();
        }
        return returnData();
    }
}
