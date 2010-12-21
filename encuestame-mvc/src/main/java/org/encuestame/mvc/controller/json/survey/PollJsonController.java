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
package org.encuestame.mvc.controller.json.survey;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Poll Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 20, 2010 8:16:38 PM
 * @version $Id:$
 */
@Controller
public class PollJsonController extends AbstractJsonController{

    /**
     * Search polls.
     * @param typeSearch
     * @param keyword
     * @param max
     * @param start
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll/search.json", method = RequestMethod.GET)
    public ModelMap searchPolls(
            @RequestParam(value = "typeSearch", required = true) String typeSearch,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "max", required = false) Integer max,
            @RequestParam(value = "pollFolderId", required = false) Long pollFolderId,
            @RequestParam(value = "start", required = false)Integer start,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        return returnData();
     }
}
