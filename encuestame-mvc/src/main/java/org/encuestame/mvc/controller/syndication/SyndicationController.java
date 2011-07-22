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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sun.syndication.feed.rss.Item;

/**
 * Tweet Poll feed controller, display rss or atom tweetpoll feeds.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 2, 2010 1:17:17 PM
 * @version $Id:$
 */
@Controller
public class SyndicationController extends AbstractFeedController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Default Tweet Poll Feed Title.
     */
    final String TWEET_POLL_FEED_TITLE = "TweetPolls published by %s";

    /**
     * Build Feed Body.
     * @param username
     * @param model
     * @param request
     * @throws EnMeNoResultsFoundException
     */
    private void buildTweetPollFeedBody(final String username, final Model model, final HttpServletRequest request,
                 final UserAccount secUserSecondary) throws EnMeNoResultsFoundException{

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
        final UserAccount secUserSecondary = getByUsername(username);
        if(secUserSecondary != null){
            try {
                model.addAttribute("items", this.getEntryAtomTweetPoll(username, request));
            } catch (EnMeExpcetion e) {
                log.error(e);
            }
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
        final UserAccount secUserSecondary = getByUsername(username);
        if(secUserSecondary != null){
             try {
                model.addAttribute("items", this.getItemRssTweetPoll(username, request));
             } catch (EnMeNoResultsFoundException e) {
                 log.error(e);
             }
        }

        return "tweetPollRssFeedView";
    }

    /**
     *
     * @param username
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/feed/{username}/profile.rss", method = RequestMethod.GET)
    public String profileRss(@PathVariable String username, Model model, HttpServletRequest request) {
        final UserAccount secUserSecondary = getByUsername(username);
        if(secUserSecondary != null){
             try {
                final List<Item> globalItems = new ArrayList<Item>();
                globalItems.addAll(this.getItemRssTweetPoll(username, request));
                globalItems.addAll(this.getItemRssTweetPoll(username, request));
                globalItems.addAll(this.getItemRssTweetPoll(username, request));
                model.addAttribute("items", globalItems);
             } catch (EnMeNoResultsFoundException e) {
                 log.error(e);
             }
        }

        return "profileRssFeedView";
    }

    /**
     *
     * @param username
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/feed/{username}/profile.atom", method = RequestMethod.GET)
    public String profileAtom(@PathVariable String username, Model model, HttpServletRequest request) {
        final UserAccount secUserSecondary = getByUsername(username);
        if(secUserSecondary != null){
            try {
                model.addAttribute("items", this.getEntryAtomTweetPoll(username, request));
                this.buildTweetPollFeedBody(username, model, request, secUserSecondary);
            } catch (EnMeExpcetion e) {
                log.error(e);
            }
        }
        return "profileAtomFeedView";
    }


    @RequestMapping(value = "/feed/{username}/survey.atom", method = RequestMethod.GET)
    public String surveyPollAtom(@PathVariable String username, Model model, HttpServletRequest request) {
        final UserAccount secUserSecondary = getByUsername(username);
        if(secUserSecondary != null){
            try {
                model.addAttribute("items", this.getEntryAtomTweetPoll(username, request));
            } catch (EnMeExpcetion e) {
                log.error(e);
            }
        }
        return "surveyAtomFeedView";
    }


    /**
     * Display TweetPoll RSS Feed.
     * @param username username
     * @param model model
     * @param request request
     * @return
     */
    @RequestMapping(value = "/feed/{username}/survey.rss", method = RequestMethod.GET)
    public String surveyPollRss(@PathVariable String username, Model model, HttpServletRequest request) {
        final UserAccount secUserSecondary = getByUsername(username);
        if(secUserSecondary != null){
             try {
                model.addAttribute("items", this.getItemRssTweetPoll(username, request));
             } catch (EnMeNoResultsFoundException e) {
                 log.error(e);
             }
        }
        return "surveyRssFeedView";
    }

}
