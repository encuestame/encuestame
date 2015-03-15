package org.encuestame.mvc.controller.json.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.stats.HashTagListGraphData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HashtagGraphData extends AbstractJsonControllerV1{



    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

      /**
     *
     * @param tagName
     * @param period
     * @param filter
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/2/common/hashtags/stats/button/{tagName}", method = RequestMethod.POST)
    public @ResponseBody ModelMap getHashtagListGraphData(
            @PathVariable(value = "tagName") String tagName,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "filter", required = true) String filter,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            List<HashTagListGraphData> tagStats = new ArrayList<HashTagListGraphData>();
            final TypeSearchResult filterType = TypeSearchResult
                    .getTypeSearchResult(filter);
            //final SearchPeriods periodSearch = SearchPeriods.getPeriodString(period);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final SearchPeriods searchPeriods = SearchPeriods.getPeriodString(period);
            if (searchPeriods == null) {
                setError("Period not valid", response);
            } else {
                if (filterType.equals(TypeSearchResult.HASHTAG)) {
                    tagStats = getStatisticsService().getTotalUsagebyHashtagAndDateRangeListGraph(tagName,
                                    searchPeriods, request);
                } else if (filterType.equals(TypeSearchResult.SOCIALNETWORK)) {
                    tagStats = getStatisticsService().getTotalSocialLinksbyHashTagUsageAndDateRangeListGraph(tagName, searchPeriods, request);
                } else if (filterType.equals(TypeSearchResult.HITS)) {
                    tagStats = getStatisticsService().getTotalHitsUsagebyHashTagAndDateRangeListGraph(tagName, searchPeriods, request);
                } else if (filterType.equals(TypeSearchResult.VOTES)) {
                    tagStats = getStatisticsService().getTotalVotesbyHashTagUsageAndDateRangeListGraph(tagName, searchPeriods, request);
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
