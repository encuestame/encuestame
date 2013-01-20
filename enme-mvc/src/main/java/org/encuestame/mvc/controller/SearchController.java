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

package org.encuestame.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.search.GlobalSearchItem;
import org.encuestame.utils.enums.TypeSearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Search Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */

@Controller
public class SearchController extends AbstractBaseOperations {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Limits.
     */
    private static final Integer LIMIT_RESULTS = 50;

    /**
     * Search Controller.
     * @param model
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchHomePost(ModelMap model) {
        log.debug("search post");
        return "search";
    }

    /**
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchGet(
           ModelMap model,
            final HttpServletRequest request,
            final HttpServletResponse response) {
        final String keyword = request.getParameter("q");
        String redirect =  "redirect:/search?q=";
        if(keyword != null){
            redirect += keyword;
        } else {
            redirect += "a";
        }
        return redirect;
    }

    /**
     * Search.
     * @param model
     * @return
     */
     @RequestMapping(value = "/search", method = RequestMethod.GET, params = "q")
    public String searchHomeGet(
           ModelMap model,
            final HttpServletRequest request,
            final HttpServletResponse response) {
       final String keyword = request.getParameter("q");
       final List<TypeSearchResult> types = new ArrayList<TypeSearchResult>();
       types.add(TypeSearchResult.TWEETPOLL);
       types.add(TypeSearchResult.POLL);
       types.add(TypeSearchResult.HASHTAG);
       types.add(TypeSearchResult.PROFILE);
       types.add(TypeSearchResult.QUESTION);
       //TODO: add survey to results.
       try {
           model.addAttribute("q", keyword);
           log.debug("search get");
           //keyword stats.
           model.addAttribute("keywordStats", null);
           //search service.
           final Map<String, List<GlobalSearchItem>>  results  = getSearchService().quickSearch(keyword, "", 0, LIMIT_RESULTS, null, types);
           model.addAttribute("results", results);
       }  catch (Exception e) {
           model.addAttribute("results", ListUtils.EMPTY_LIST);
           log.error(e);
       }
       return "search";
     }

}
