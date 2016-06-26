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
package org.encuestame.rest.api.v1;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.utils.ValidationUtils;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.HashTagBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HashTag Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 20, 2010 12:43:46 PM
 */
@Controller
public class HashTagsJsonController extends AbstractJsonControllerV1{


    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(HashTagsJsonController.class);

            /**
             * Limit of results by default.
             */
    private Integer hashtagsSuggestLimit = EnMePlaceHolderConfigurer.getIntegerProperty("hashtags.suggests.items");

    /**
     * Limit of cloud results.
     */
    private Integer hashtagsCloudLimit = EnMePlaceHolderConfigurer.getIntegerProperty("hashtags.cloud.items");

    /**
     * @api {get} /api/common/hashtags.json Hashtag by keyword
     * @apiVersion 1.0.0
     * @apiName GetHashtags
     * @apiGroup Hashtag
     * @apiPermission user
     * @apiDescription Retrieve  hashtags used to mark or categorize Tweetpolls, Poll or Survey.
     * @apiParam {String} limit The maximum number of hashtags to include in the response
     * @apiParam {String} keyword Keyword to suggest a list of related hashtags.
     * @apiParam {String[]} excludes All Comments Id that will not be included in the search.
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/hashtags.json?limit=10&keyword=f
     * @apiPermission none
     * @apiSuccessExample
     * {
            "error": { },
            "success": {
                "items": [
                    {
                        "id": 3,
                        "size": 16,
                        "hashTagName": "forex",
                        "hits": 1
                    },
                    {
                        "id": 16,
                        "size": 15,
                        "hashTagName": "free",
                        "hits": 1
                    }
                ],
                "label": "hashTagName",
                "identifier": "id"
            }
        }
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/hashtags.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getHashTags(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "excludes", required = false) Long[] excludes,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                if(limit == null){
                    limit = hashtagsSuggestLimit;
                }
                log.debug("Limit "+limit);
                log.debug("Keyword "+keyword);
                log.debug("excludes "+excludes);
                log.debug("Hashtag Suggestion Before Validation :::"+keyword);
                keyword = ValidationUtils.removeNonAlphanumericCharacters(keyword);
                log.debug("Hashtag Suggestion After Validation :::"+keyword);
                if(keyword == null || keyword.isEmpty()){
                    jsonResponse.put("hashtags", ListUtils.EMPTY_LIST);
                    setItemResponse(jsonResponse);
                } else {
                    final List<HashTagBean> hashTags = getTweetPollService().listSuggestHashTags(
                            keyword,
                          limit, excludes);
                    log.debug("List Hash Tags "+hashTags.size());
                    setItemReadStoreResponse("hashTagName", "id", hashTags);
                }
            } catch (Exception e) {
                 log.error(e);
                 setError(e.getMessage(), response);
            }
            return returnData();
        }

    /**
     * @api {get} /api/common/hashtags.json Hashtag Cloud
     * @apiVersion 1.0.0
     * @apiName GetCloudHashtag
     * @apiPermission none
     * @apiGroup Hashtag
     * @apiDescription A tag cloud of hashtags with the top hashtags in post mentioning ordered by frecuency.
     * @apiParam {Number} limit The maximum number of hashtags to include in the response.
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/hashtags/cloud.json
     * @apiPermission none
     * @apiSuccessExample
     *
     * {
       "error": { },
        "success": {
            "cloud": [
                {
                    "id": 2,
                    "size": 16,
                    "hashTagName": "business",
                    "hits": 1
                },
                {
                    "id": 3,
                    "size": 16,
                    "hashTagName": "forex",
                    "hits": 1
                },
                {
                    "id": 8,
                    "size": 16,
                    "hashTagName": "games",
                    "hits": 1
                }
            ]
        }
        }
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @RequestMapping(value = "/api/common/hashtags/cloud.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getHashTagsCloud(
            @RequestParam(value = "limit", required = false) Integer limit,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             final IFrontEndService service = getFrontService();
             final List<HashTagBean> hashTagList;
             //TODO: please replace "hashTagsCloud" by ENUM.
             hashTagList = service.getHashTags( limit == null ? hashtagsCloudLimit : limit , START_DEFAULT, "hashTagsCloud");
             // TODO: ENCUESTAME-347
             jsonResponse.put("cloud", hashTagList);
             setItemResponse(jsonResponse);
            } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/survey/hashtag/{type}/{action}.json Hashtag Operations
     * @apiName GetOperationHashtag
     * @apiGroup Hashtag
     * @apiDescription Allows operations on a hashtag such as: Add / Remove.
     * @apiPermission ENCUESTAME_USER
     * @apiParam {String} hashtag Hashtag name
     * @apiParam {Number} itemId Hashtag id that will be possibly eliminated.
     * @apiParam {String="tweetpoll","poll","survey"} type Type of element it adds a comment.
     * @apiParam {String} action operation to execute.
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/hashtag/test/remove.json
     * @apiVersion 1.0.0
     * @apiSuccessExample
     * @apiSuccess {Object} success
     * @apiSuccess {String} error
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/hashtag/{type}/{action}.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap manageHashtag(
            @RequestParam(value = "id", required = true) final String hashtag,
            @RequestParam(value = "itemId", required = true) final Long id,
            @PathVariable final String action,
            @PathVariable final String type,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final TypeSearchResult typeItem = TypeSearchResult.getTypeSearchResult(type);
            log.debug("***************/api/survey/hashtag/{type}/{action}.json************");
            if (typeItem.equals(TypeSearchResult.TWEETPOLL)) {
                if ("remove".equals(action)) {
                    setSuccesResponse();
                    getTweetPollService().removeHashtagFromTweetPoll(null, null);
                } else if ("add".equals(action)) {
                     final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                    final HashTagBean bean = ConvertDomainBean
                            .convertHashTagDomain(getTweetPollService()
                                    .addHashtagToTweetPoll(
                                            getTweetPollService()
                                                    .getTweetPollById(id),
                                            new HashTagBean(hashtag)));
                    log.debug("New TweetPoll HT Bean: "+bean);
                    if (bean.getHashTagName().isEmpty()) {
                        setFailedResponse();
                    } else {
                        jsonResponse.put("hashtag", bean);
                        setItemResponse(jsonResponse);
                    }
                }
            } else if (typeItem.equals(TypeSearchResult.POLL)) {
                if ("remove".equals(action)) {
                    log.debug("Remove option has been disabled");
                    setSuccesResponse();
                } else if ("add".equals(action)) {
                    final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                    final HashTagBean bean = ConvertDomainBean
                            .convertHashTagDomain(getPollService()
                                    .addHashTagToPoll(
                                            getPollService().getPollById(id),
                                            new HashTagBean(hashtag)));
                    log.debug("New TweetPoll HT Bean: " + bean);
                    if (bean.getHashTagName().isEmpty()) {
                        setFailedResponse();
                    } else {
                        jsonResponse.put("hashtag", bean);
                        setItemResponse(jsonResponse);
                    }
                    setSuccesResponse();
                }
            } else if (typeItem.equals(TypeSearchResult.SURVEY)) {
                //TODO: no yet.
                 setSuccesResponse();
            }
            log.debug("***************/api/survey/hashtag/{type}/{action}.json************");
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
