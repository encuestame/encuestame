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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.exception.EnMeCommentNotFoundException;
import org.encuestame.utils.ValidationUtils;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.HashTagBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HashTag Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 20, 2010 12:43:46 PM
 */
@Controller
public class HashTagsJsonController extends AbstractJsonController{


    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Limit of results by default.
     */
    @Value("${hashtags.suggests.items}") private Integer hashtagsSuggestLimit;

    /**
     * Limit of cloud results.
     */
    @Value("${hashtags.cloud.items}") private Integer hashtagsCloudLimit;

     /**
     * Get List of Users.
     * @param username username
     * @param request request
     * @param response response
     * @return list of json users.
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
     * Get hashTags cloud.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
     *
     * @param hashtag
     * @param action
     * @param type
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
