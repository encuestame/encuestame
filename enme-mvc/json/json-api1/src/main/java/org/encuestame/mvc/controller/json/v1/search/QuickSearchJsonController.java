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
package org.encuestame.mvc.controller.json.v1.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.encuestame.core.search.GlobalSearchItem;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.search.TweetPollSearchBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Quick search json controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 5, 2010 10:32:22 PM
 * @version $Id:$
 */
@Controller
public class QuickSearchJsonController extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Default limit results.
     */
    private final Integer LIMIT_RESULTS = 10;

    /**
     * Quick suggestion search service.
     *
     * @param keyword
     * @param type
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "api/search/quick-suggest.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap quickSuggestionSearchService(
            @RequestParam(value = "keyword", required = true) String keyword,
            @RequestParam(value = "limitByItem", required = false) Integer limitByItem,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        try {
            final List<GlobalSearchItem> results = new ArrayList<GlobalSearchItem>();
            keyword = filterValue(keyword);
            //
            final List<TypeSearchResult> typesEnabled = new ArrayList<TypeSearchResult>();
            typesEnabled.add(TypeSearchResult.QUESTION);
            typesEnabled.add(TypeSearchResult.ATTACHMENT);
            typesEnabled.add(TypeSearchResult.HASHTAG);
            typesEnabled.add(TypeSearchResult.POLL);
            typesEnabled.add(TypeSearchResult.PROFILE);
            typesEnabled.add(TypeSearchResult.TWEETPOLL); 
            typesEnabled.add(TypeSearchResult.COMMENT);  
            if (!keyword.isEmpty()) {
                 setItemReadStoreResponse("itemSearchTitle", "id", getSearchService()
                        .quickSearch(keyword, "English", 0, LIMIT_RESULTS, limitByItem, typesEnabled));
            } else {
                log.debug("keyword is empty");
            }
            log.debug("GlobalSearchItem results " + results.size());
            //setItemReadStoreResponse("itemSearchTitle", "id", results);
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{type}/search.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap advancedSearch(
    		  @RequestParam(value = "typeSearch", required = true) String typeSearch,
              @RequestParam(value = "keyword", required = false) String keyword,
              @RequestParam(value = "max", required = true) Integer max,
              @RequestParam(value = "start", required = true) Integer start,
              @RequestParam(value = "social_networks", required = false)  List<String> socialNetworks,
              @RequestParam(value = "social_account_networks", required = false) List<Long> socialAccountNetworks,
              @RequestParam(value = "_published", required = false) Boolean isPublished,
              @RequestParam(value = "_complete", required = false) Boolean isCompleted,
              @RequestParam(value = "_favourite", required = false) Boolean isFavourite,
              @RequestParam(value = "_scheduled", required = false) Boolean isScheduled,
              @RequestParam(value = "period", required = false) String period,
              @PathVariable final String type,
              HttpServletRequest request, HttpServletResponse response)
              throws JsonGenerationException, JsonMappingException, IOException {
          final Map<String, Object> jsonResponse = new HashMap<String, Object>();
          final TweetPollSearchBean tpollSearchBean = new TweetPollSearchBean();
          try {
              log.debug("search.json" + typeSearch);
              log.debug("search.json" + keyword);
              log.debug("search.json" + max);
              log.debug("search.json" + start);
              log.debug("search.json socialNetworks" + socialNetworks);
              log.debug("search.json socialAccountNetworks " + socialAccountNetworks);
              log.debug("search.json isCompleted " + isPublished);
              log.debug("search.json" + isCompleted);
              log.debug("search.json favourite" + isFavourite);
              // Create TweetpollSearchBean
              tpollSearchBean.setIsComplete(isCompleted == null ? false : isCompleted);
              tpollSearchBean.setIsFavourite(isFavourite == null ? false : isFavourite);
              tpollSearchBean.setIsPublished(isPublished == null ? false : isPublished);
              tpollSearchBean.setIsScheduled(isScheduled == null ? false : isScheduled);
              tpollSearchBean.setKeyword(keyword == null ? null : keyword.isEmpty() ? null : keyword);
              tpollSearchBean.setMax(max);
              //tpollSearchBean.setPeriod(period); it's not used, will be removed in the future.
              tpollSearchBean.setSearchResult(null);
              tpollSearchBean.setStart(start);
              tpollSearchBean.setTypeSearch(TypeSearch.getSearchString(typeSearch));
              tpollSearchBean.setProviders(socialNetworks == null ? ListUtils.EMPTY_LIST : ConvertDomainBean.convertSocialProviderStringToProvider(socialNetworks));
              tpollSearchBean.setSocialAccounts(socialAccountNetworks == null ? ListUtils.EMPTY_LIST : socialAccountNetworks);
                      //socialNetworks.size() == 0 ? null : ConvertDomainBean.convertSocialProviderStringToProvider(socialNetworks));
            final TypeSearchResult typeResult = TypeSearchResult.getTypeSearchResult(type);
			if (typeResult.equals(TypeSearchResult.TWEETPOLL)) {
				final List<SearchBean> list = (List<SearchBean>) getTweetPollService().filterTweetPollByItemsByTypeSearch(
	                      tpollSearchBean, request);
			    jsonResponse.put("tweetPolls", list);
			    log.debug("/api/survey/tweetpoll/search.json---------------->  "
						+ list.size());
			 // Convert Tweetpoll or Survey to SearchBean
			} else if (typeResult.equals(TypeSearchResult.POLL)) {
				final List<PollBean> pollBeanList = getPollService().filterPollByItemsByType(null, keyword, max, start);

			} else {
				throw new EnmeFailOperation("operation not valid");
			}
			setItemResponse(jsonResponse);
		} catch (EnMeExpcetion e) {
			log.error(e);
			setError(e.getMessage(), response);
		}
		return returnData();
	}
}
