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
package org.encuestame.rest.api.v1.statistics;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.HashTagStatsBean;
import org.encuestame.utils.web.stats.GenericStatsBean;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.encuestame.utils.web.stats.HashTagRankingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HashTag statistics.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 05, 2012
 */
@Controller
public class HashTagStatsJsonController extends AbstractJsonControllerV1 {

    /** Log. **/
    private static Log log = LogFactory.getLog(HashTagStatsJsonController.class);

            /** **/
    private Integer INIT_RESULTS = 0;


    /**
     * @api {get} /api/common/hashtags/stats/button.json Button stats
     * @apiName GetButtonStats
     * @apiGroup Stats
     * @apiDescription Return all comments that will be filtered by type.
     * @apiParam {String} tagName Hashtag name.
     * @apiParam {String="hashtagrated"} filter Filter search.
     * @apiParam {String} [period] Date range to search results.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/hashtags/stats/button.json?tagName=health&filter=HASHTAGRATED&period=24
     * @apiPermission none
     * @apiSuccessExample
     * {
		  "error": {

		  },
		  "success": {
		    "hashTagButtonStats": {
		      "usage_by_item": {
		        "label": "Usage",
		        "value": 0,
		        "sub_label": "times ",
		        "filter": "HASHTAG"
		      },
		      "total_usage_by_social_network": {
		        "label": "Social Networks",
		        "value": 0,
		        "sub_label": "Tweets ",
		        "filter": "SOCIALNETWORK"
		      },
		      "total_hits": {
		        "label": "Hits",
		        "value": 0,
		        "sub_label": "times ",
		        "filter": "HITS"
		      },
		      "usage_by_votes": {
		        "label": "Voted",
		        "value": 0,
		        "sub_label": "votos",
		        "filter": "VOTES"
		      }
		    }
		  }
		}
     */
    @RequestMapping(value = "/api/common/hashtags/stats/button.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getHashTagButtonStats(
            @RequestParam(value = "tagName", required = true) String tagName,
            @RequestParam(value = "filter", required = true) String filter,
            @RequestParam(value = "period", required = false) String period,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final HashTagStatsBean tagStatsBean = new HashTagStatsBean();
            final TypeSearchResult filterType = TypeSearchResult.getTypeSearchResult(filter);
            final SearchPeriods searchPeriod = SearchPeriods.getPeriodString(period);
            if (filterType == null) {
                throw new EnMeNoResultsFoundException("type not found");
            } else {
                if (filterType.equals(TypeSearchResult.HASHTAGRATED)) {
                    // hits /period
                    tagStatsBean.setTotalHits(getStatisticsService().getHashTagHitsbyName(tagName,
                            filterType, request, searchPeriod));
                    tagStatsBean.getTotalHits().setTypeSearchResult(TypeSearchResult.HITS);
                    // social network use /period
                    tagStatsBean.setTotalUsageBySocialNetwork(getStatisticsService()
                            .getSocialNetworkUseByHashTag(tagName, this.INIT_RESULTS,
                                    null, request, searchPeriod));
                    tagStatsBean.getTotalUsageBySocialNetwork().setTypeSearchResult(TypeSearchResult.SOCIALNETWORK);
                    // usage by / period
                    tagStatsBean.setUsageByItem(getStatisticsService().getTotalUsageByHashTag(tagName,
                            this.INIT_RESULTS, null, filterType, request, searchPeriod
                            ));
                    tagStatsBean.getUsageByItem().setTypeSearchResult(TypeSearchResult.HASHTAG);
                    // votes
                    tagStatsBean.setUsageByVotes(getStatisticsService().getHashTagUsedOnItemsVoted(tagName,
                            this.INIT_RESULTS, null, request, searchPeriod));
                    tagStatsBean.getUsageByVotes().setTypeSearchResult(TypeSearchResult.VOTES);
                    jsonResponse.put("hashTagButtonStats", tagStatsBean);
                    setItemResponse(jsonResponse);
                } else {
                    setError("filter not valid", response);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/common/hashtags/stats/ranking.json Get Ranking stats
     * @apiName GetRanking
     * @apiGroup Stats
     * @apiDescription Hashtag statistics are presented in the form of ranking to show which has been the trend in Timeline.
     * @apiParam {String} tagName Hashtag Name.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/hashtags/stats/ranking.json?tagName=health
     * @apiPermission none
     * @apiSuccessExample
     * 	{
		 	"error": { },
		    "success": {
		        "hashTagRankingStats": [
		            {
		                "position": 19,
		                "tagName": "hosting",
		                "lastPosition": 0
		            },
		            {
		                "position": 18,
		                "tagName": "health",
		                "lastPosition": 0
		            },
		            {
		                "position": 17,
		                "tagName": "games",
		                "lastPosition": 0
		            }
		        ]
		    }
		}
     */
    @RequestMapping(value = "/api/common/hashtags/stats/ranking.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getHashTagRankingStats(
            @RequestParam(value = "tagName", required = true) String tagName,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                final List<HashTagRankingBean> tagRankingBean =  getFrontService().getHashTagRanking(tagName);
            log.debug("HashTagJsonStats size --->" + tagRankingBean.size());
            jsonResponse.put("hashTagRankingStats", getFrontService().getHashTagRanking(tagName));
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * @api {get} /api/common/stats/generic.json Generic stats.
     * @apiName GetGeneric
     * @apiGroup Stats
     * @apiDescription The generic statistics are used to get an overview on using a particular hashtag as: Percentage of positive or negative, number of visits, who created the label as well as the creation date.
     * @apiParam {String} itemId Unique identifier of the item (Ex. Twtpoll ID,  Poll ID, etc.) .
     * @apiParam {String} filter Filter Search
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/stats/generic.json
     * @apiPermission none
     */
    @RequestMapping(value = "/api/common/stats/generic.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getGenericStats(
            @RequestParam(value = "id", required = false) String itemId,
            @RequestParam(value = "filter", required = false) String filter,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             final TypeSearchResult filterType = TypeSearchResult
                        .getTypeSearchResult(filter);
            final GenericStatsBean genericStatsBean =  getFrontService().retrieveGenericStats(
                    itemId, filterType, request);
            jsonResponse.put("generic", genericStatsBean);
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * @api {get} /api/common/hashtags/stats/button/range.json By Date range
     * @apiName GetRange
     * @apiGroup Stats
     * @apiDescription Provides statistics based on two main parameters: The date range and a total usage parameter is given the hashtag, they may include:
     *					number of visits, the use to categorize or mark a type of survey, number of times to use in a social network as well as the number of votes received.
     * @apiParam {String} tagName  Hashtag name to retrieve the statistics.
     * @apiParam {String} [period] Date range to search results.
     * @apiParam {String="hashtag","socialnetwork","hits","votes"} filter - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/hashtags/stats/button/range.json?tagName=health&period=7&filter=HASHTAG
     * @apiPermission none
     * @apiSuccessExample
     * {
		  "error": {

		  },
		  "success": {
		    "statsByRange": [
		      {
		        "label": "7",
		        "value": 2,
		        "sub_label": "Domingo",
		        "filter": null
		      },
		      {
		        "label": "3",
		        "value": 1,
		        "sub_label": "Marzo",
		        "filter": null
		      },
		      {
		        "label": "5",
		        "value": 1,
		        "sub_label": "Mayo",
		        "filter": null
		      }
		    ]
		  }
		}
     */
    @RequestMapping(value = "/api/common/hashtags/stats/button/range.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getHashTagButtonStatsByDateRange(
            @RequestParam(value = "tagName", required = true) String tagName,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "filter", required = true) String filter,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            List<HashTagDetailStats> tagStats = new ArrayList<HashTagDetailStats>();
            final TypeSearchResult filterType = TypeSearchResult
                    .getTypeSearchResult(filter);
            //final SearchPeriods periodSearch = SearchPeriods.getPeriodString(period);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final SearchPeriods searchPeriods = SearchPeriods.getPeriodString(period);
            if (searchPeriods == null) {
                setError("Period not valid", response);
            } else {
                if (filterType.equals(TypeSearchResult.HASHTAG)) {
                    tagStats = getStatisticsService().getTotalUsagebyHashtagAndDateRangeGraph(tagName,
                                    searchPeriods, request);
                } else if (filterType.equals(TypeSearchResult.SOCIALNETWORK)) {
                    tagStats = getStatisticsService()
                            .getTotalSocialLinksbyHashTagUsageAndDateRangeGraph(tagName,
                                    searchPeriods, request);
                } else if (filterType.equals(TypeSearchResult.HITS)) {
                    tagStats = getStatisticsService()
                            .getTotalHitsUsagebyHashTagAndDateRangeGraph(tagName,
                                    searchPeriods, request);
                } else if (filterType.equals(TypeSearchResult.VOTES)) {
                    tagStats = getStatisticsService()
                            .getTotalVotesbyHashTagUsageAndDateRangeGraph(tagName,
                                    searchPeriods, request);
                }
                jsonResponse.put("statsByRange", tagStats);
                setItemResponse(jsonResponse);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }


}