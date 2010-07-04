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
package org.encuestame.mvc.controller.syndication;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.utils.web.UnitTweetPoll;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Tweet Poll feed controller, display rss or atom tweetpoll feeds.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 2, 2010 1:17:17 PM
 * @version $Id:$
 */
@Controller
public class TweetPollFeedController extends AbstractFeedController {

    /**
     * Default Tweet Poll Feed Title.
     */
    final String TWEET_POLL_FEED_TITLE = "TweetPolls published by %s";

    /**
     * Build Feed Body.
     * @param username
     * @param model
     * @param request
     */
    private void buildTweetPollFeedBody(String username, Model model, HttpServletRequest request, SecUserSecondary secUserSecondary){
         model.addAttribute("username", username);
         model.addAttribute("feedTitle", String.format(TWEET_POLL_FEED_TITLE, username));
         model.addAttribute("url", buildDomainWithRequest(request));
         //find and add tweetPolls.
         final List<UnitTweetPoll> tweetPolls = getTweetPolls(secUserSecondary.getUsername());
         log.debug("Tweet Polls size "+tweetPolls.size());
         model.addAttribute("tweetPolls", tweetPolls);
    }

    /**
     * Display TweetPoll Atom Feed.
     * @param username
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/feed/{username}/tweetpoll.atom", method = RequestMethod.GET)
    public String tweetPollAtom(@PathVariable String username, Model model, HttpServletRequest request) {
        final SecUserSecondary secUserSecondary = getByUsername(username);
        if(secUserSecondary == null){
            //TODO: if null do something.
        }
        else{
            this.buildTweetPollFeedBody(username, model, request, secUserSecondary);
        }
        return "tweetPollAtomFeedView";
    }


    /**
     * Display TweetPoll RSS Feed.
     * @param username username
     * @param model model
     * @param request request
     * @return
     */
    @RequestMapping(value = "/feed/{username}/tweetpoll.rss", method = RequestMethod.GET)
    public String tweetPollRss(@PathVariable String username, Model model, HttpServletRequest request) {
        final SecUserSecondary secUserSecondary = getByUsername(username);
        if(secUserSecondary == null){
            //TODO: if null do something.
        }
        else{
            this.buildTweetPollFeedBody(username, model, request, secUserSecondary);
        }
        return "tweetPollRssFeedView";
    }

}