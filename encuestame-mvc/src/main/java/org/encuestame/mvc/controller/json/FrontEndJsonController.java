/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.json.TweetPollBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Frontend json controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 04, 2011
 */

@Controller
public class FrontEndJsonController extends AbstractJsonController{


    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Get frontend items.
     * @param period
     * @param maxResults
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/common/frontend/stream.json", method = RequestMethod.GET)
    public ModelMap getFrontendItems(
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "maxResults", required = false) Integer maxResults,
            @RequestParam(value = "start", required = false) Integer start,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                if (period == null ){
                    throw new EnMeSearchException("search params required.");
                } else {
                    final  List<TweetPollBean> itemList = getFrontService().searchItemsByTweetPoll(period, start, maxResults, request);
                    jsonResponse.put("frontendItems", itemList);
                    setItemResponse(jsonResponse);
                   }
            } catch (Exception e) {
                 log.error(e);
            }
            return returnData();
        }
}
