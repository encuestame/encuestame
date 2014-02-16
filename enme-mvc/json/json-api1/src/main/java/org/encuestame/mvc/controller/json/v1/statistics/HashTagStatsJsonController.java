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
package org.encuestame.mvc.controller.json.v1.statistics;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
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

/**
 * HashTag statistics.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 05, 2012
 */
@Controller
public class HashTagStatsJsonController extends AbstractJsonControllerV1 {

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** **/
    private Integer INIT_RESULTS = 0;

    /**
     * Get hashTags button stats.
     *
     * @param tagName
     * @param filter
     * @param limit
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
            e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * Get hashTags ranking stats.
     * @param tagName
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/common/hashtags/stats/ranking.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getHashTagRankingStats(
            @RequestParam(value = "tagName", required = true) String tagName,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            //IFrontEndService service = getFrontService();
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
     * Generic stats.
     * @param itemId
     * @param tagName
     * @param filter
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
     *
     * @param tagName
     * @param period
     * @param filter
     * @param request
     * @param response
     * @return
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
            e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }    
}