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
package org.encuestame.mvc.controller.json.notifications;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Notification Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 5:56:22 PM
 * @version $Id:$
 */
@Controller
public class NotificationsJsonController {

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/{username}/notifications.json", method = RequestMethod.GET)
    public ModelMap get(
            @PathVariable String username,
            @RequestParam(value = "limit") Integer limit,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         //TODO: load notifications.
         return null;
    }
}
