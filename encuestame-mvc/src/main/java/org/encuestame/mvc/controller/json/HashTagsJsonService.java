/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.utils.web.HashTagBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * HashTag Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 20, 2010 12:43:46 PM
 * @version $Id:$
 */
@Controller
public class HashTagsJsonService extends AbstractJsonController{


    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Limit of results by default.
     */
    private final static Integer LIMIT_DEFAULT = 10;

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
    public ModelMap get(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "keyword", required = false) String keyword,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                if(limit == null){
                    limit = LIMIT_DEFAULT;
                }
                log.debug("Limit "+limit);
                log.debug("Keyword "+keyword);
                if(keyword == null || keyword.isEmpty()){
                    jsonResponse.put("hashtags", ListUtils.EMPTY_LIST);
                    setItemResponse(jsonResponse);
                } else {
                    final List<HashTagBean> hashTags = getTweetPollService().listSuggestHashTags(keyword, limit);
                    log.debug("List Hash Tags "+hashTags.size());
                    setItemReadStoreResponse("hashTagName", "id", hashTags);
                }
            } catch (Exception e) {
                 log.error(e);
                 e.printStackTrace();
                 setError(e.getMessage(), response);
            }
            return returnData();
        }

}
