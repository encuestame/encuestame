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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Tweet Poll Controller.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 * @version $Id: $
 */

@Controller
public class TweetPollVoteController extends BaseController {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return template
     */
    @RequestMapping(value = "/tweet/{tweetId}/vote.html", method = RequestMethod.GET)
    public String tweetPollController(ModelMap model,
            @PathVariable String tweetId) {
        log.debug("tweetId: "+tweetId);
        String pathVote = "badTweetVote";
        if (tweetId.isEmpty()) {
            log.debug("tweet is empty");
            model.put("message", "Tweet Not Valid..");
        } else {
            log.info("search code");
            final TweetPollSwitch tweetPoll = getTweetPollService()
                    .getTweetPollDao().retrieveTweetsPollSwitch(tweetId);
            if (tweetPoll == null
                    || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                log.debug("tweetpoll answer not found");
                model.put("message", "Tweet Not Valid.");
            } else {
                log.info("Validate Votting");
                final String IP = getIpClient();
                log.info("IP" + IP);
                if (getTweetPollService().validateTweetPollIP(IP, tweetPoll.getTweetPoll()) == null) {
                    getTweetPollService().tweetPollVote(tweetPoll, IP);
                    model.put("message", "Tweet Poll Voted.");
                    pathVote = "tweetVoted";
                    log.debug("VOTED");
                }
                else{
                    log.debug("Tweet Vote Repeteaded.");
                    model.put("message", "Tweet Vote Repeteaded.");
                    pathVote = "repeatedTweetVote";
                }
                model.get("message");
            }
        }
        log.info("redirect template");
        return pathVote;
    }
}
