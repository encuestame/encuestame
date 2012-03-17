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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String pollController(final ModelMap model, @PathVariable Long id,  @PathVariable String slug) {
        log.debug("poll Id -->" + id);
        try {
            final Poll poll = getPollService().getPollById(id);
            model.addAttribute("poll",
                    ConvertDomainBean.convertPollDomainToBean(poll));
            return "poll/detail";
        } catch (EnMePollNotFoundException e) {
            log.error(e);
            return "404";
        }
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
        log.debug("tweetpoll");
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
        log.debug("tweetpoll");
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
        @PathVariable Long id,
        @PathVariable String slug) {

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
