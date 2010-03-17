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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Tweet Poll Controller.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */

@Controller
public class TweetPollController extends BaseController {

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return template
     */
    @RequestMapping("/tweetPollController")
    public String tweetPollController(ModelMap model,
            @RequestParam(value = "id", required = true) String id) {
        if (id.isEmpty()) {
            model.put("message", "Tweet Not Valid.");
        } else {
            log.info("search code");
            final TweetPollSwitch tweetPoll = getSurveyService()
                    .getTweetPollDao().retrieveTweetsPollSwitch(id);
            if (tweetPoll == null
                    || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                model.put("message", "Tweet Not Valid.");
            } else {
                log.info("Validate Votting");

                final String IP = getIpClient();
                log.info("IP" + IP);
                if (getSurveyService().validateTweetPollIP(IP, tweetPoll.getTweetPoll()) == null) {
                    getSurveyService().tweetPollVote(tweetPoll, IP);
                    model.put("message", "Tweet Poll Voted.");
                }
                else{
                    model.put("message", "Tweet Vote Repeteaded.");
                }
            }
        }
        log.info("redirect template");
        return "helloWorldTemplate";
    }
}
