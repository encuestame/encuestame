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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.encuestame.core.search.GlobalSearchItem;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.utils.enums.TypeSearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
     * @api {get} api/search/quick-suggest.json Quick Search
     * @apiName GetQuickSearch
     * @apiGroup QuickSearch
     * @apiDescription Quick suggestion search service.
     * @apiParam {String} keyword - XXXX
     * @apiParam {String} [limitByItem - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/search/quick-suggest.json
     * @apiPermission none
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
            //typesEnabled.add(TypeSearchResult.ATTACHMENT);
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
            //e.printStackTrace();
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
