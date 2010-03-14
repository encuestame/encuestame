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

import org.encuestame.core.persistence.pojo.TweetPollSwitch;
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
public class TweetPollController extends BaseController{

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return template
     */
    @RequestMapping("/tweetPollController")
    public String tweetPollController(ModelMap model,
            @RequestParam(value = "id", required = true) String id) {
        log.info("tweetCode "+id);
        if (id.isEmpty()) {
            model.put("message", "Tweet Not Valid.");
        }
        else {
            log.info("search code");
            final TweetPollSwitch tweetPoll = getSurveyService().getTweetPollDao().retrieveTweetsPollSwitch(id);
            log.info("tweetPoll "+tweetPoll);
            if(tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()){
                model.put("message", "Tweet Not Valid.");
            }
            else{
                model.put("message", "Tweet Poll Voted.");
            }
        }
        log.info("redirect template");
        return "helloWorldTemplate";
    }



}
