/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Poll controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 11, 2010 9:21:37 PM
 * @version $Id: $
 */
@Controller
public class PollController extends AbstractBaseOperations {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Poll Controller.
     * @param model model
     * @return template
     */
    @RequestMapping(value = "/poll/{id}/{slug}", method = RequestMethod.GET)
    public String pollController(final ModelMap model,
            @PathVariable Long id,
            @PathVariable String slug) {
        log.debug("poll Id -->" + id);
        log.debug("poll slug -->" + slug);
        try {
            final Poll poll = getPollService().getPollSlugById(id, slug);
            //final List<QuestionAnswerBean> answer = getPollService().retrieveAnswerByQuestionId(poll.getQuestion().getQid());
            final List<PollBeanResult> results = getPollService().getResultVotes(poll);
            log.debug("Poll Detail Answers "+results.size());
            log.debug("poll--> "+poll);
            //TODO: reuse this code on vote poll.
            model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
            model.addAttribute("answers", results);
            return "poll/detail";
        } catch (Exception e) {
            log.error(e);
            return "404";
        }
    }

    /**
     * Display the succesfully vote interface.
     * @param model
     * @return
     */
    @RequestMapping(value = "/poll/voted", method = RequestMethod.GET)
    public String pollVoted(final ModelMap model) {
        return "poll/voted";
    }

    /**
     * Called when user vote from Vote Interface, validate the vote and
     * return a page for different cases.
     * @param responses
     * @param password
     * @param usernameForm
     * @param req
     * @return
     */
    @RequestMapping(value = "/poll/vote/post", method = RequestMethod.POST)
    public String submit(
            @RequestParam("poll") Long responseId,
            @RequestParam("itemId") Long itemId,
            @RequestParam("type") String type,
            @RequestParam("slugName") String slugName,
            final HttpServletRequest req) {
        log.debug("/poll/vote/post VOTE POLL "+responseId);
        log.debug("/poll/vote/post VOTE POLL "+itemId);
        log.debug("/poll/vote/post VOTE POLL "+type);
        log.debug("/poll/vote/post VOTE POLL "+type);
        //default path
        String pathVote = "redirect:/poll/voted/";
        try {
            type = filterValue(type);
            slugName = filterValue(slugName);
            final String IP = getIpClient(req);
            // validations
            final Boolean checkBannedIp = checkIPinBlackList(IP);
            if (checkBannedIp) {
            	//if banned send to banned view.
                pathVote ="poll/banned";
            } else {
	            //
	            final Poll poll = getPollService().getPollById(itemId);
	            final PollResult result = getPollService().validatePollIP(IP, poll);
	            if (result == null) {
	            	getPollService().vote(itemId, slugName, IP, responseId);
	            } else {
	            	pathVote = "poll/repeated";            
	            }
            }
        } catch (EnMeNoResultsFoundException e) {
            log.error("error poll vote "+e);
            e.printStackTrace();
            pathVote ="poll/bad";
        }
        log.debug("poll vote path: "+pathVote);
        return pathVote;
    }

    /**
     * TweetPoll Redirect.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/poll", method = RequestMethod.GET)
    public String tweetPollControllerRedirect(final ModelMap model) {
        log.debug("tweetpoll");
        return "redirect:/user/poll/list";
    }

    /**
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/poll/list", method = RequestMethod.GET)
    public String pollListController(final ModelMap model) {
        log.debug("poll list render view");
        addItemsManangeMessages(model);
        return "poll/list";
    }

    /**
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/poll/new", method = RequestMethod.GET)
    public String newPollController(final ModelMap model) {
        log.debug("new poll");
        return "poll/new";
    }

    /**
     * Vote a {@link Poll}.
     * @param model
     * @param tweetId
     * @return
     */
    @RequestMapping(value = "/poll/vote/{id}/{slug}", method = RequestMethod.GET)
    public String pollVoteController(
        ModelMap model,
        HttpServletRequest request,
        @PathVariable Long id,
        @PathVariable String slug) {
        try {
            final Poll poll = getPollService().getPollSlugById(id, slug);
            final List<QuestionAnswerBean> answer = getPollService().retrieveAnswerByQuestionId(poll.getQuestion().getQid());
            log.debug("Poll Detail Answers "+answer.size());
            model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
            model.addAttribute("answers", answer);
        } catch (EnMeNoResultsFoundException e) {
            log.error(e);
            e.printStackTrace();
            model.put("message", "Poll not valid.");
        }
        return "poll/vote";
    }


    /**
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/poll/{id}/vote.js", method = RequestMethod.GET)
    public String jsView(
        Model model,
        HttpServletRequest request) {
        //
        return "jsView";
    }
}
