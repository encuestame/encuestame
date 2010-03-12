/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import org.encuestame.core.service.IServiceManager;
import org.encuestame.core.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Tweet Poll Controller.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */

@Controller
@RequestMapping("/tweetPollController")
public class TweetPollController {

    /**
     * {@link ServiceManager}.
     */
    @Autowired
    private IServiceManager serviceManager;

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return template
     * @throws Exception exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    public String tweetPollController(ModelMap model,
            @RequestParam(value = "id", required = true) int id)
            throws Exception {
        System.out.println("test "+getServiceManager().getDataEnMeSource().getState(1L).getDescState());
        model.put("message", "Tweet Poll Voted.");
        return "helloWorldTemplate";
    }

    /**
     * @return the serviceManager
     */
    public IServiceManager getServiceManager() {
        return serviceManager;
    }

    /**
     * @param serviceManager
     *            the serviceManager to set
     */
    public void setServiceManager(IServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
