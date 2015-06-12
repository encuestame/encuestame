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

package org.encuestame.mvc.page;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnmeNotAllowedException;
import org.encuestame.utils.enums.RequestSourceType;
import org.encuestame.utils.web.PollBeanResult;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.UnitAbstractSurvey.MultipleResponse;
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
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 11, 2010 9:21:37 PM
 */
@Controller
public class PollController extends AbstractViewController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Poll Controller.
     *
     * @param model
     *            model
     * @return template
     */
    @RequestMapping(value = "/poll/{id}/{slug}", method = RequestMethod.GET)
    public String pollController(final ModelMap model, @PathVariable Long id,
            @PathVariable String slug) {
        log.trace("poll Id -->" + id);
        log.trace("poll slug -->" + slug);
        try {
            final Poll poll = getPollService().getPollSlugById(id, slug);
            // final List<QuestionAnswerBean> answer =
            // getPollService().retrieveAnswerByQuestionId(poll.getQuestion().getQid());
            final List<PollBeanResult> results = getPollService()
                    .getResultVotes(poll);
            log.trace("Poll Detail Answers " + results.size());
            log.trace("poll--> " + poll);
            // TODO: reuse this code on vote poll.
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
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/poll/voted", method = RequestMethod.GET)
    public String pollVoted(final ModelMap model) {
        return "poll/voted";
    }

    /**
     *
     * @param responseId
     * @param itemId
     * @param poll_password
     * @param multiplesVotes
     * @param type
     * @param type_form
     * @param slugName
     * @param req
     * @param model
     * @return
     * @throws UnknownHostException
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping(value = "/poll/vote/password/post", method = RequestMethod.POST)
    public String submitPassword(
            @RequestParam(value = "poll", required = false) Long responseId,
            @RequestParam(value = "itemId", required = true) Long itemId,
            @RequestParam(value = "poll_password", required = true) String poll_password,
            @RequestParam(value = "multiplesVotes", required = false) String[] multiplesVotes,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "type_form", required = true) String type_form,
            @RequestParam("slugName") String slugName,
            final HttpServletRequest req, final ModelMap model)
            throws UnknownHostException, EnMeNoResultsFoundException {
        log.debug("*************************************************");
        log.debug("/poll/vote/password/post VOTE POLL " + responseId);
        log.debug("/poll/vote//password/post VOTE POLL " + itemId);
        log.debug("/poll/vote//password/post VOTE POLL " + type);
        log.debug("/poll/vote//password/post VOTE POLL " + multiplesVotes);
        log.debug("/poll/vote//password/post VOTE POLL " + type_form);
        log.debug("/poll/vote//password/post VOTE POLL " + slugName);
        log.debug("/poll/vote//password/poll_password VOTE POLL " + poll_password);
        log.debug("*************************************************");
        // default path
        final RequestSourceType requestSourceType = RequestSourceType.getSource(type_form);
        final String isEmbedded = !requestSourceType.equals(RequestSourceType.EMBEDDED) ? "" : "embedded/";
        log.debug("isEmbedded   * " + isEmbedded);
        String pathVote = "poll/" + isEmbedded + "voted";
        try{
            log.debug("************DEBUG");
            Poll poll = new Poll();
            // Check IP in BlackListFile - Validation
            final String IP = getIpClient(req);
            final Boolean checkBannedIp = checkIPinBlackList(IP);
            if (checkBannedIp) {
                // if banned send to banned view.
                pathVote = "poll/ " + isEmbedded + "banned";
            } else {
                // Item id is poll id
                if (itemId == null) {
                    // Exception musst be removed.
                    throw new EnMePollNotFoundException("poll id has not been found");
                    // if answer id is null return the request
                } else if (responseId == null) {
                    log.debug("************NO ANSWER");
                    log.debug("responseId " + responseId);
                    poll = getPollService().getPollByAnswerId(itemId, responseId, null);
                    log.debug("poll " + poll);
                    model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                    model.put("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                    if (log.isInfoEnabled()) {
                        log.debug("ConvertDomainBean.convertPollDomainToBean(poll) " + ConvertDomainBean.convertPollDomainToBean(poll).toString());
                    }
                    RequestSessionMap.getCurrent(req).put("votePollError", Boolean.TRUE);
                    pathVote = "redirect:/poll/vote/" + itemId + "/" + slugName;
                    log.debug("r****************1*************" + pathVote);
                } else {
                    log.debug("r***************2**************");
                    type = filterValue(type);
                    type_form = filterValue(type_form);
                    slugName = filterValue(slugName);
                    log.debug("itemID::" + itemId);
                    log.debug("responseId::" + responseId);
                    log.debug("poll.getIsPasswordProtected()" + poll.getIsPasswordProtected());
                    poll = getPollService().getPollByAnswerId(itemId, responseId, null);
                    model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                    model.put("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                    // restrictions by date
                    if (poll.getIsPasswordProtected()) {
                        log.debug("Is protected under password");
                        if (poll.getPassword().equals(poll_password)) {
                            final Boolean restrictVotesByDate = getPollService().restrictVotesByDate(poll);
                            log.debug("restrictVotesByDate " + restrictVotesByDate);
                            // restrictions by quota, check the limit of votes
                            final Boolean restrictVotesByQuota = getPollService().restrictVotesByQuota(poll);
                            log.debug("restrictVotesByQuota " + restrictVotesByQuota);
                            // restrictions votes by IP
                            final Boolean limitsOfVotesByIP = getPollService().checkLimitVotesByIP(IP, poll);
                            if (poll == null || !poll.getPublish()) {
                                log.warn("pll answer not found");
                                model.put("message", getMessage("poll.votes.bad"));
                                pathVote = "404";
                            }
                            // Validate properties or options
                            else if (poll.getPollCompleted()) {
                                model.put("message", getMessage("poll.completed"));
                                log.debug("poll has already completed");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else if (restrictVotesByQuota) {
                                model.put("message", getMessage("tweetpoll.votes.limited"));
                                log.debug("poll has reached to the limit of votes");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else if (restrictVotesByDate) {
                                model.put("message", getMessage("poll.closed"));
                                log.debug("poll has been closed");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else if (limitsOfVotesByIP) {
                                model.put("message", getMessage("poll.votes.repeated"));
                                log.debug("limit of votes by IP has reached");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else {
                                try {
                                    model.put("pollAnswer", poll);
                                    final Integer result = getPollService().validatePollIP(IP, poll);
                                    if (result == 0 || (poll.getAllowRepeatedVotes() != null && poll.getAllowRepeatedVotes() && !limitsOfVotesByIP)) {
                                        // FIXME: ENCUESTAME-673
//                                        if (poll.getMultipleResponse() != null && poll.getMultipleResponse().equals(AbstractSurvey.MultipleResponse.MULTIPLE)) {
//                                            log.debug("poll multiple response");
//                                            for (int i = 0; i < multiplesVotes.length; i++) {
//                                                try {
//                                                    final Long responseIdMultiple = Long.valueOf(multiplesVotes[i]);
//                                                    getPollService().vote(poll, slugName, IP, responseIdMultiple);
//                                                } catch (Exception error) {
//                                                    log.error("error on multivote " + error.getMessage());
//                                                    error.printStackTrace();
//                                                }
//                                                model.put("message", getMessage("poll.votes.thanks"));
//                                                pathVote = "poll/" + isEmbedded + "voted";
//                                            }
                                        //} else if (poll.getMultipleResponse().equals(AbstractSurvey.MultipleResponse.SINGLE)) {
                                            log.debug("poll single response");
                                            getPollService().vote(poll, slugName, IP, responseId);
                                            model.put("message", getMessage("poll.votes.thanks"));
                                            pathVote = "poll/" + isEmbedded + "voted";
                                        //}
                                    } else {
                                        model.put("message", getMessage("poll.repeated"));
                                        log.debug("limit of votes by IP has reached");
                                        pathVote = "poll/" + isEmbedded + "repeated";
                                    }
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                    //e.printStackTrace();
                                    model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                                    pathVote = "poll/" + isEmbedded + "bad";
                                }
                            }
                        } else {
                            log.debug("password not valid, try again");
                            model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                            model.addAttribute("type", type);
                            model.addAttribute("type_form", type_form);
                            model.addAttribute("itemId", itemId);
                            model.addAttribute("responseId", responseId);
                            model.addAttribute("multiplesVotes", multiplesVotes);
                            pathVote = "poll/password";
                            model.addAttribute("votePollError", "Password is not valid");
                        }
                        log.debug("redirect template WHERE " + pathVote);
                    } else {
                      throw new EnmeNotAllowedException("not poll valid to vote under password");
                    }
                }
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            pathVote = "poll/" + isEmbedded + "bad";
        }
        return pathVote;
    }


    /**
     * Called when user vote from Vote Interface, validate the vote and return a
     * page for different cases.
     * @param responseId
     * @param itemId
     * @param multiplesVotes
     * @param type
     * @param type_form
     * @param slugName
     * @param req
     * @param model
     * @return
     * @throws UnknownHostException
     * @throws EnMeNoResultsFoundException
     */
    @RequestMapping(value = "/poll/vote/post", method = RequestMethod.POST)
    public String submit(
            @RequestParam(value = "poll", required = false) Long responseId,
            @RequestParam(value = "itemId", required = true) Long itemId,
            @RequestParam(value = "multiplesVotes", required = false) String[] multiplesVotes,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "type_form", required = true) String type_form,
            @RequestParam("slugName") String slugName,
            final HttpServletRequest req, final ModelMap model)
            throws UnknownHostException, EnMeNoResultsFoundException {
        log.debug("*************************************************");
        log.debug("/poll/vote/post VOTE POLL " + responseId);

        log.debug("/poll/vote/post VOTE POLL " + itemId);
        log.debug("/poll/vote/post VOTE POLL " + type);
        log.debug("/poll/vote/post VOTE POLL " + multiplesVotes);
        log.debug("/poll/vote/post VOTE POLL " + type_form);
        log.debug("/poll/vote/post VOTE POLL " + slugName);
        log.debug("*************************************************");
        // default path
        final RequestSourceType requestSourceType = RequestSourceType.getSource(type_form);
        final String isEmbedded = !requestSourceType.equals(RequestSourceType.EMBEDDED) ? "" : "embedded/";
        log.debug("isEmbedded   * " + isEmbedded);
        String pathVote = "poll/" + isEmbedded + "voted";
        try{
            Poll poll = new Poll();
                // Check IP in BlackListFile - Validation
                final String IP = getIpClient(req);
                final Boolean checkBannedIp = checkIPinBlackList(IP);
                if (checkBannedIp) {
                    // if banned send to banned view.
                    pathVote = "poll/ " + isEmbedded + "banned";
                } else {
                    // Item id is poll id
                    if (itemId == null) {
                        // Exception musst be removed.
                        throw new EnMePollNotFoundException("poll id has not been found");
                        // if answer id is null return the request
                    } else if (responseId == null) {
                        log.debug("responseId -- JUAN " + responseId);
                        try {
                            poll = getPollService().getPollByAnswerId(itemId, responseId, null);
                        } catch (EnMeExpcetion e) {
                            //TEMP: temporal solution to manage correctly the bad response
                            pathVote = "poll/" + isEmbedded + "bad";
                            Poll poll_full = getPollService().getPollById(itemId);
                            model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll_full));
                        }
                        log.debug("poll " + poll);
                        model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                        if (log.isInfoEnabled()) {
                            log.debug("ConvertDomainBean.convertPollDomainToBean(poll) " + ConvertDomainBean.convertPollDomainToBean(poll).toString());
                        }
                        RequestSessionMap.getCurrent(req).put("votePollError", Boolean.TRUE);
                        pathVote = "redirect:/poll/vote/" + itemId + "/" + slugName;
                        log.debug("r****************1*************" + pathVote);
                    } else {
                        log.debug("r***************2**************");
                        type = filterValue(type);
                        type_form = filterValue(type_form);
                        slugName = filterValue(slugName);
                        log.debug("itemID::" + itemId);
                        log.debug("responseId::" + responseId);
                        log.debug("poll.getIsPasswordProtected()" + poll.getIsPasswordProtected());
                        poll = getPollService().getPollByAnswerId(itemId, responseId, null);
                        model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                        model.put("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                        if (poll.getIsPasswordProtected()) {
                            model.addAttribute("poll", ConvertDomainBean.convertPollDomainToBean(poll));
                            model.addAttribute("type", type);
                            model.addAttribute("type_form", type_form);
                            model.addAttribute("itemId", itemId);
                            model.addAttribute("responseId", responseId);
                            model.addAttribute("multiplesVotes", multiplesVotes);
                            pathVote = "poll/password";
                        } else {
                            // restrictions by date
                            final Boolean restrictVotesByDate = getPollService().restrictVotesByDate(poll);
                            log.debug("restrictVotesByDate " + restrictVotesByDate);
                            // restrictions by quota, check the limit of votes
                            final Boolean restrictVotesByQuota = getPollService().restrictVotesByQuota(poll);
                            log.debug("restrictVotesByQuota " + restrictVotesByQuota);
                            // restrictions votes by IP
                            final Boolean limitsOfVotesByIP = getPollService().checkLimitVotesByIP(IP, poll);
                            if (poll == null || !poll.getPublish()) {
                                log.warn("pll answer not found");
                                model.put("message", getMessage("poll.votes.bad"));
                                pathVote = "404";
                            }
                            // Validate properties or options
                            else if (poll.getPollCompleted()) {
                                model.put("message", getMessage("poll.completed"));
                                log.debug("poll has already completed");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else if (restrictVotesByQuota) {
                                model.put("message", getMessage("tweetpoll.votes.limited"));
                                log.debug("poll has reached to the limit of votes");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else if (restrictVotesByDate) {
                                model.put("message", getMessage("poll.closed"));
                                log.debug("poll has been closed");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else if (limitsOfVotesByIP) {
                                model.put("message", getMessage("poll.votes.repeated"));
                                log.debug("limit of votes by IP has reached");
                                pathVote = "poll/" + isEmbedded + "completed";
                            } else {
                                try {
                                    model.put("pollAnswer", poll);
                                    final Integer result = getPollService().validatePollIP(IP, poll);
                                    if (result == 0 || (poll.getAllowRepeatedVotes() != null && poll.getAllowRepeatedVotes() && !limitsOfVotesByIP)) {
                                        if (poll.getMultipleResponse().equals(AbstractSurvey.MultipleResponse.MULTIPLE)) {
                                            log.debug("poll multiple response");
                                            for (int i = 0; i < multiplesVotes.length; i++) {
                                                try {
                                                    final Long responseIdMultiple = Long.valueOf(multiplesVotes[i]);
                                                    getPollService().vote(poll, slugName, IP, responseIdMultiple);
                                                } catch (Exception error) {
                                                    log.error("error on multivote " + error.getMessage());
                                                    error.printStackTrace();
                                                }
                                                model.put("message", getMessage("poll.votes.thanks"));
                                                pathVote = "poll/" + isEmbedded + "voted";
                                            }
                                        } else if (poll.getMultipleResponse().equals(AbstractSurvey.MultipleResponse.SINGLE)) {
                                            log.debug("poll single response");
                                            getPollService().vote(poll, slugName, IP, responseId);
                                            model.put("message", getMessage("poll.votes.thanks"));
                                            pathVote = "poll/" + isEmbedded + "voted";
                                        }
                                    } else {
                                        model.put("message", getMessage("poll.repeated"));
                                        log.debug("limit of votes by IP has reached");
                                        pathVote = "poll/" + isEmbedded + "repeated";
                                    }
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                    //e.printStackTrace();
                                    pathVote = "poll/" + isEmbedded + "bad";
                                }
                            }
                            log.debug("redirect template WHERE " + pathVote);
                        }
                    }
                }
        } catch (Exception e) {
            //e.printStackTrace();
            log.debug(e.getMessage());
            pathVote = "poll/" + isEmbedded + "bad";
        }
        return pathVote;
    }

    /**
     * TweetPoll Redirect.
     *
     * @param model
     *            model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/poll", method = RequestMethod.GET)
    public String tweetPollControllerRedirect(final ModelMap model) {
        log.debug("tweetpoll");
        return "redirect:/user/poll/list";
    }

    /**
     * Poll List Controller.
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/poll/list", method = RequestMethod.GET)
    public String pollListController(final ModelMap model,
            HttpServletRequest request,
            HttpServletResponse response) throws EnMeExpcetion {
        log.debug("poll list render view");
        addItemsManangeMessages(model, request, response);
        addi18nProperty(model, "commons_no_results", request, response);
        addi18nProperty(model, "poll_admon_poll_options", request, response);
        addi18nProperty(model, "poll_admon_poll_answers", request, response);
        //
        addi18nProperty(model, "poll_admon_poll_edit", request, response);
        addi18nProperty(model, "poll_admon_poll_preview", request, response);
        addi18nProperty(model, "poll_admon_poll_publish_options", request, response);
        addi18nProperty(model, "poll_admon_poll_embebed", request, response);
        //
        addi18nProperty(model, "commons_success", request, response);

        addi18nProperty(model, "detail_manage_today", request, response);

        addi18nProperty(model, "poll_admon_poll_votes", request, response);
        //
        addi18nProperty(model, "detail_manage_poll_title", request, response);
        addi18nProperty(model, "detail_manage_filters", request, response);
        addi18nProperty(model, "detail_manage_poll_dropdown_title", request, response);
        // menu items
        addi18nProperty(model, "poll_admon_poll_new", request, response);
        addi18nProperty(model, "commons_remove", request, response);

        // poll options
        addi18nProperty(model, "poll_options_close", request, response);
        addi18nProperty(model, "poll_options_quota", request, response);
        addi18nProperty(model, "poll_options_ip", request, response);
        addi18nProperty(model, "poll_options_password", request, response);
        addi18nProperty(model, "poll_options_info", request, response);
        addi18nProperty(model, "poll_options_public", request, response);
        addi18nProperty(model, "poll_options_notifications", request, response);
        addi18nProperty(model, "poll_password_link", request, response);

        // confirm
        addi18nProperty(model, "commons_confirm", request, response);
        addi18nProperty(model, "commons_no", request, response);
        addi18nProperty(model, "commons_yes", request, response);
        addi18nProperty(model, "detail_manage_today", request, response);

        //
        addi18nProperty(model, "publish_social", request, response);
        addi18nProperty(model, "loading_message", request, response);
        addi18nProperty(model, "counter_zero", request, response);
        addi18nProperty(model, "pubication_failure_status", request, response);
        addi18nProperty(model, "button_try_later", request, response);
        addi18nProperty(model, "button_ignore", request, response);
        addi18nProperty(model, "button_try_again", request, response);
        addi18nProperty(model, "button_vote", request, response);
        addi18nProperty(model, "button_voted", request, response);


        addi18nProperty(model, "poll_is_hidden", request, response);

        /** *** *** ** Help Guide ** *** *** **/
        addi18nProperty(model, "help_poll_button_new", request, response);
        addi18nProperty(model, "help_poll_aside_bar", request, response);
        addi18nProperty(model, "help_poll_folder_view", request, response);
        addi18nProperty(model, "help_poll_advanced_search_view", request, response);
        addi18nProperty(model, "help_poll_detail_view", request, response);


       return "poll/list";
    }

    /**
     * Display the view to create a poll.
     *
     * @param model
     *            {@link Model}
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/poll/new", method = RequestMethod.GET)
    public String newPollController(final ModelMap model,
            HttpServletRequest request,
            HttpServletResponse response) {
        //log.debug("new poll render view", request, response);
        addi18nProperty(model, "leave_mesage", request, response);
        addi18nProperty(model, "tp_add_hashtag", request, response);
        addi18nProperty(model, "poll_create_question_title", request, response);
        addi18nProperty(model, "poll_create_build_answers", request, response);
        addi18nProperty(model, "poll_create_add_new_answer", request, response);
        addi18nProperty(model, "poll_create_allow_multiple_selection", request, response);
        addi18nProperty(model, "poll_create_allow_new_responses", request, response);
        addi18nProperty(model, "poll_create_limits", request, response);
        addi18nProperty(model, "poll_create_poll_options", request, response);
        addi18nProperty(model, "poll_create_comments", request, response);
        addi18nProperty(model, "poll_create_results", request, response);
        addi18nProperty(model, "poll_create_button_create", request, response);
        addi18nProperty(model, "widget_folder_select_label", request, response);
        addi18nProperty(model, "pattern_question_single", request, response);
        addi18nProperty(model, "m_025", request, response);
        addi18nProperty(model, "m_026", request, response);
        addi18nProperty(model, "widget_repated_votes", request, response);
        addi18nProperty(model, "widget_limit_votes", request, response);
        addi18nProperty(model, "widget_date_to_close", request, response);
        addi18nProperty(model, "widget_comments_allow", request, response);
        addi18nProperty(model, "widget_comments_moderated", request, response);
        addi18nProperty(model, "widget_comments_no_comments", request, response);
        addi18nProperty(model, "widget_results_options", request, response);
        addi18nProperty(model, "widget_results_only_percents", request, response);
        addi18nProperty(model, "widget_results_all_data", request, response);
        addi18nProperty(model, "widget_question_type", request, response);
        addi18nProperty(model, "commons_cancel", request, response);
        addi18nProperty(model, "social_picker_filter_selected", request, response);
        addi18nProperty(model, "publish_social", request, response);
        addi18nProperty(model, "loading_message", request, response);
        addi18nProperty(model, "counter_zero", request, response);
        addi18nProperty(model, "pubication_failure_status", request, response);
        addi18nProperty(model, "button_try_later", request, response);
        addi18nProperty(model, "button_ignore", request, response);
        addi18nProperty(model, "button_try_again", request, response);
        addi18nProperty(model, "poll_is_hidden", request, response);
        addi18nProperty(model, "poll_password", request, response);
        /** *** *** ** Help Guide ** *** *** **/
        addi18nProperty(model, "help_poll_question_text", request, response);
        addi18nProperty(model, "help_poll_answers_text", request, response);
        addi18nProperty(model, "help_poll_hashtags", request, response);
        addi18nProperty(model, "help_poll_options", request, response);
        addi18nProperty(model, "help_poll_limit_options", request, response);
        addi18nProperty(model, "help_poll_comments_options", request, response);
        addi18nProperty(model, "help_poll_comments_results", request, response);
        addi18nProperty(model, "help_poll_cancel_button", request, response);


        return "poll/new";
    }

    /**
     * Vote a {@link Poll}.
     * @param model
     * @param request
     * @param id
     * @param slug
     * @return
     */
    @RequestMapping(value = "/poll/vote/{id}/{slug}", method = RequestMethod.GET)
    public String pollVoteController(ModelMap model,
            HttpServletRequest request, @PathVariable Long id,
            @PathVariable String slug) {
        try {
            final Poll poll = getPollService().getPollSlugById(id, slug);
            final List<QuestionAnswerBean> answer = getPollService()
                    .retrieveAnswerByQuestionId(poll.getQuestion().getQid());
            log.debug("Poll Detail Answers " + answer.size());
            model.addAttribute("poll",
                    ConvertDomainBean.convertPollDomainToBean(poll));
            model.addAttribute("answers", answer);
            return "poll/vote";
        } catch (EnMeNoResultsFoundException e) {
            log.error(e);
            //e.printStackTrace();
            model.put("message", "Poll not valid.");
            return "404";
        }

    }

//	/**
//	 *
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/poll/{id}/vote.js", method = RequestMethod.GET)
//	public String jsView(Model model, HttpServletRequest request) {
//		return "jsView";
//	}
}
