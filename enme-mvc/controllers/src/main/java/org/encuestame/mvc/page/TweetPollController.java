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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.SecurityOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.utils.captcha.ReCaptchaResponse;
import org.encuestame.utils.enums.CommentOptions;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.vote.UtilVoteCaptcha;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Tweet Poll Controller support.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */

@Controller
public class TweetPollController extends AbstractViewController {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Tweet Poll Controller.
     * @param model model
     * @return view to redirect.
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/tweetpoll/vote/{tweetId}", method = RequestMethod.GET)
    public String tweetPollController(
        ModelMap model,
        @PathVariable String tweetId,
        final HttpServletRequest req) throws UnknownHostException {
        log.debug("tweetId: "+tweetId);
        setCss(model, "tweetpoll");
        String pathVote = "badTweetVote";
        String IP = getIpClient(req);
        // Check IP in BlackListFile
            final Boolean checkBannedIp = checkIPinBlackList(IP);
            log.debug("Check Banned IP----> " + checkBannedIp);
            log.info("checkBannedIp::==>"+checkBannedIp);
            try {
                if (checkBannedIp) {
                    pathVote ="404"; // I guess 404 is much better solution than ban message
                    log.debug("ip banned");
                } else {
                    if (tweetId.isEmpty()) {
                        log.debug("tweet is empty");
                        model.put("message", "Tweet Not Valid..");
                    } else {
                        tweetId = filterValue(tweetId);
                        model.put("tweetId", tweetId);
                        log.info("search code->" + tweetId);
                        log.info("tweetId::==>"+tweetId);
                        log.info("tweetId::==>"+IP);
                        final TweetPollSwitch tweetPoll = getTweetPollService().getTweetPollDao().retrieveTweetsPollSwitch(tweetId);
                        //INPROGRESS: ENCUESTAME-676
                        setOgMetadataTweetPollInfo(model, tweetPoll.getTweetPoll(), req);
                        if (tweetPoll == null) {
                            throw new EnMeNoResultsFoundException("tweetpoll answer not found");
                        }
                        model.addAttribute("tp_switch", tweetPoll);
                        final Boolean validateLimitVotes = getTweetPollService().validateLimitVotes(tweetPoll.getTweetPoll());
                        log.info("validateLimitVotes::==>"+validateLimitVotes);
                        final Boolean restrictVotesByDate = getTweetPollService().validateVotesByDate(tweetPoll.getTweetPoll());
                        log.info("restrictVotesByDate::==>"+restrictVotesByDate);
                        //NOTE: tweetpoll should be published to able to vote !!
                        if (tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                            log.debug("tweetpoll answer not found");
                            pathVote = "404";
                            //model.put("message", getMessage("tp_no_valid"));
                        } else if (tweetPoll.getTweetPoll().getCompleted()) {
                            log.debug("tweetpoll is archived");
                            model.put("message", getMessage("tweetpoll.votes.completed"));
                            pathVote = "completeTweetVote";
                            // Validate Limit Votes
                        } else if (validateLimitVotes) {
                            log.debug("tweetpoll reached limit votes");
                            model.put("message", getMessage("tweetpoll.votes.limited"));
                            pathVote = "LimitTweetVote";
                        } else {
                            log.info("Validate Votting");
                            log.info("IP" + IP);
                            try {
                                getTweetPollService().validateIpVote(IP,
                                        tweetPoll.getTweetPoll());
                                if (!tweetPoll.getTweetPoll().getCaptcha()) {
                                    getTweetPollService().tweetPollVote(tweetPoll, IP,
                                            Calendar.getInstance().getTime());
                                    model.put("message", getMessage("tweetpoll.votes.acepted"));
                                    pathVote = "tweetVoted";
                                    log.debug("VOTED");
                                } else {
                                    this.createCaptcha(model, tweetId);
                                    log.debug("VOTE WITH CAPTCHA");
                                    pathVote = "voteCaptcha";
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                //e.printStackTrace();
                                log.error("");
                                pathVote = "repeatedTweetVote";
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        log.info("redirect template WHERE "+pathVote);
        return pathVote;
    }

    /**
     *
     * @param model
     * @param tweetId
     * @return
     */
    public UtilVoteCaptcha createCaptcha(final ModelMap model, final String tweetId){
        final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
        final UtilVoteCaptcha captchaBean = new UtilVoteCaptcha();
        captchaBean.setCaptcha(captcha);
        captchaBean.setCodeVote(tweetId);
        model.addAttribute("captchaForm", captchaBean);
        return captchaBean;
    }

    /**
     * reCAPTCHA validate process.
     * @param req {@link HttpServletRequest}.
     * @param challenge recaptcha_challenge_field parameter
     * @param response recaptcha_response_field paremeter
     * @param code code vote
     * @param result {@link BindingResult}.
     * @param model {@link ModelMap}.
     * @return view to redirect.
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/tweetpoll/vote/process", method = RequestMethod.POST)
    public String processSubmit(
            HttpServletRequest req,
            @RequestParam("recaptcha_challenge_field") String challenge,
            @RequestParam("recaptcha_response_field") String response,
            @RequestParam("vote_code") String code,
            @ModelAttribute("captchaForm") UtilVoteCaptcha vote,
            BindingResult result,
            ModelMap model) throws UnknownHostException {
             setCss(model, "tweetpoll");
             log.info("recaptcha_challenge_field "+challenge);
             log.info("recaptcha_rforgotesponse_field "+response);
             log.info("code "+code.toString());
             log.info("vote "+vote.toString());
             log.info("model " + model.toString());
             challenge = filterValue(challenge);
             response = filterValue(response);
             code = filterValue(code);
             vote = (UtilVoteCaptcha) model.get("captchaForm");
             log.info("vote2--> "+vote.toString());
             final String IP = getIpClient(req);
             //security service
             final SecurityOperations securityService = getServiceManager().getApplicationServices().getSecurityService();
             //check if captcha is valid
             final ReCaptchaResponse reCaptchaResponse = getReCaptcha().checkAnswer(req.getRemoteAddr(), challenge, response);
             //validation layer
             final ValidateOperations validation = new ValidateOperations(securityService);
             validation.validateCaptcha(reCaptchaResponse, result);
             log.info("reCaptchaResponse "+reCaptchaResponse.getErrorMessage());
             log.info("reCaptchaResponse "+reCaptchaResponse.isValid());
             log.info("result.hasErrors() "+result.hasErrors());
             if (result.hasErrors()) {
                //build new reCAPTCHA
                final String errMsg = reCaptchaResponse.getErrorMessage();
                final String html = getReCaptcha().createRecaptchaHtml(errMsg, null);
                vote.setCaptcha(html);
                vote.setCodeVote(code);
                model.addAttribute("captchaForm", vote);
                result.reject("form.problems");
                //reload page.
                return "voteCaptcha";
             } else {
                 //Find Answer To Vote.
                 final TweetPollSwitch tweetPoll = getTweetPollService().getTweetPollDao()
                        .retrieveTweetsPollSwitch(code);
                 model.addAttribute("switch", tweetPoll);
                 //Validate Code.
                 if (tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                     log.debug("tweetpoll answer not found");
                     return "badTweetVote";
                     //model.addAttribute("message", "Tweet Not Valid.");
                     }
                 else {
                     // Vote
                     try {
                         getTweetPollService().validateIpVote(IP, tweetPoll.getTweetPoll());
                         getTweetPollService().tweetPollVote(tweetPoll, IP, Calendar.getInstance().getTime());
                         return "tweetVoted";

                    } catch (Exception e) {
                         log.error("Bad tweetpoll process submit --- >" + e);
                        return "repeatedTweetVote";
                    }
                 }
            }
    }

    /**
     * TweetPoll Controller.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/tweetpoll/list", method = RequestMethod.GET)
    public String tweetPollController(
            final ModelMap model,
            HttpServletRequest request,
            HttpServletResponse response) {
        setCss(model, "tweetpoll");
        addItemsManangeMessages(model, request, response);
        addi18nProperty(model, "loading_messages", request, response);
        addi18nProperty(model, "tweetpoo_detail_tab_detail", request, response);
        addi18nProperty(model, "tweetpoo_detail_tab_stats", request, response);
        addi18nProperty(model, "tweetpoo_detail_tab_comments", request, response);
        addi18nProperty(model, "tweetpoo_detail_tab_social", request, response);
        addi18nProperty(model, "tweetpoo_detail_tab_delete", request, response);
        addi18nProperty(model, "tweetpoo_detail_answers_title_link", request, response);
        addi18nProperty(model, "tweetpoo_detail_answers_title_count", request, response);
        addi18nProperty(model, "tweetpoo_detail_answers_title_percent", request, response);
        addi18nProperty(model, "commons_created_date", request, response);
        addi18nProperty(model, "commons_captcha", request, response);
        addi18nProperty(model, "tp_options_allow_results", request, response);
        addi18nProperty(model, "tp_options_follow_dashboard", request, response);
        addi18nProperty(model, "tp_options_allow_repeated_votes", request, response);
        addi18nProperty(model, "tp_options_notifications", request, response);
        addi18nProperty(model, "related_terms", request, response);
        addi18nProperty(model, "commons_success", request, response);
        addi18nProperty(model, "detail_clean_filters", request, response);
        addi18nProperty(model, "commons_favourite", request, response);
        addi18nProperty(model, "e_023", request, response);
        addi18nProperty(model, "commons_no_results", request, response);
        addi18nProperty(model, "common_open_embebed", request, response);
        addi18nProperty(model, "commons_unfavourite", request, response);
        addi18nProperty(model, "loading_message", request, response);
        /** *** *** **  Help Guide ** *** *** **/
        addi18nProperty(model, "help_tp_button_new", request, response);
        addi18nProperty(model, "help_tp_aside_bar", request, response);
        addi18nProperty(model, "help_tp_folder_view", request, response);
        addi18nProperty(model, "help_tp_advanced_search_view", request, response);
        addi18nProperty(model, "help_tp_detail_view", request, response);
        return "tweetpoll";
    }

    /**
     * TweetPoll Redirect.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/tweetpoll", method = RequestMethod.GET)
    public String tweetPollControllerRedirect(final ModelMap model) {
        setCss(model, "tweetpoll");
        return "redirect:/user/tweetpoll/list";
    }

    /**
     * TweetPoll Controller.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/tweetpoll/new", method = RequestMethod.GET)
    public String newTweetPollController(final ModelMap model, final HttpServletRequest request, HttpServletResponse response) {
        //check social accounts.
        String path = "tweetpoll/new";
        setCss(model, "tweetpoll");
        try {
            final List<SocialAccountBean> socials = getSecurityService().getValidSocialAccounts(SocialProvider.ALL, false);
            if (socials.size() == 0) {
                path = "tweetpoll/social";
            }
        } catch (EnMeNoResultsFoundException e) {
            //e.printStackTrace();
            log.error(e);
            path = "505";
        }
        log.debug("newTweetPollController "+path);
        //log.debug("tweetpoll new");
        addi18nProperty(model, "leave_mesage", request, response);
        addi18nProperty(model, "common_show", request, response);
        addi18nProperty(model, "tp_write_questions", request, response);
        addi18nProperty(model, "tp_add_answer", request, response);
        addi18nProperty(model, "tp_add_hashtag", request, response);
        addi18nProperty(model, "tp_scheduled", request, response);
        addi18nProperty(model, "tp_customize", request, response);
        addi18nProperty(model, "tp_select_publish", request, response);
        addi18nProperty(model, "tp_options_chart", request, response);
        addi18nProperty(model, "tp_options_spam", request, response);
        addi18nProperty(model, "tp_options_report", request, response);
        addi18nProperty(model, "tp_options_scheduled_this_tweetpoll",  request, response);
        addi18nProperty(model, "tp_options_allow_results",  request, response);
        addi18nProperty(model, "tp_options_allow_repeated_votes",  request, response);
        addi18nProperty(model, "tp_options_limit_votes",  request, response);
        addi18nProperty(model, "tp_options_resume_live_results",  request, response);
        addi18nProperty(model, "tp_options_follow_dashboard", request, response);
        addSocialPickerWidgetMessages(model, request, response);
        addi18nProperty(model, "button_add",  request, response);
        addi18nProperty(model, "button_remove",  request, response);
        addi18nProperty(model, "button_close",  request, response);
        addi18nProperty(model, "button_finish", request, response);
        addi18nProperty(model, "button_publish", request, response);
        addi18nProperty(model, "button_try_again", request, response);
        addi18nProperty(model, "button_ignore",  request, response);
        addi18nProperty(model, "button_try_later", request, response);
        addi18nProperty(model, "commons_captcha",  request, response);
        addi18nProperty(model, "tp_publish_error",  request, response);
        addi18nProperty(model, "pubication_failure_status", request, response);
        addi18nProperty(model, "pubication_success_status", request, response);
        addi18nProperty(model, "pubication_inprocess_status", request, response);
        addi18nProperty(model, "e_020",  request, response);
        addi18nProperty(model, "e_021",  request, response);
        addi18nProperty(model, "e_024", request, response);
        addi18nProperty(model, "commons_success",  request, response);
        addi18nProperty(model, "commons_failure",  request, response);
        addi18nProperty(model, "commons_tab_to_save", request, response);
        addi18nProperty(model, "button_scheduled",  request, response);
        //help context
        addi18nProperty(model, "tp_help_question", request, response);
        addi18nProperty(model, "tp_help_1",  request, response);
        addi18nProperty(model, "tp_help_2", request, response);
        addi18nProperty(model, "tp_help_3",  request, response);
        addi18nProperty(model, "tp_help_4", request, response);
        addi18nProperty(model, "help_center_search", request, response);
        /** *** *** ** Help Guide ** *** *** **/
        addi18nProperty(model, "help_tp_quick_help", request, response);
        addi18nProperty(model, "help_tp_hide_help", request, response);
        addi18nProperty(model, "help_tp_counter_characters", request, response);
        addi18nProperty(model, "help_tp_counter_available", request, response);
        addi18nProperty(model, "help_tp_question_text", request, response);
        addi18nProperty(model, "help_tp_counter_available", request, response);
        addi18nProperty(model, "help_tp_new_cancel_button", request, response);
        return path;
    }

    /**
     * Display {@link TweetPoll} detail view.
     * @param model
     * @param id tweetpoll id
     * @param slug slug question name.
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/tweetpoll/{id}/{slug}", method = RequestMethod.GET)
    public String detailTweetPollController(
            final ModelMap model,
            @PathVariable Long id,
            @PathVariable String slug,
            final HttpServletRequest request) throws UnknownHostException {
        log.debug("detailTweetPollController "+id);
        log.debug("detailTweetPollController "+slug);
        setCss(model, "tweetpoll");
        try {
            final String ipAddress = getIpClient(request);
            slug = filterValue(slug);
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollByIdSlugName(id, slug);
            setOgMetadataTweetPollInfo(model, tweetPoll, request);
            // if the tweetpoll is not published we send a 404 page
            if (tweetPoll.getPublishTweetPoll()) {
                this.checkTweetPollStatus(tweetPoll);
                boolean tweetPollVisite = getFrontService().checkPreviousHit(ipAddress, tweetPoll.getTweetPollId(), TypeSearchResult.TWEETPOLL);
                // TODO: Check that previous hash Tag hit has been visited the same day.
                if (!tweetPollVisite) {
                    getFrontService().registerHit(tweetPoll, null, null, null, ipAddress, HitCategory.VISIT);
                }
                model.addAttribute("tweetpoll", ConvertDomainBean.convertTweetPollToBean(tweetPoll));
                final List<HashTag> hashtagsBean = new ArrayList<HashTag>(tweetPoll.getHashTags());
                model.addAttribute("hashtags", ConvertDomainBean.convertListHashTagsToBean(hashtagsBean));
                model.addAttribute("isModerated", tweetPoll.getShowComments() == null ? false : (tweetPoll.getShowComments().equals(CommentOptions.MODERATE) ? true : false));
                //answers.
                final List<TweetPollSwitch> answers = getTweetPollService().getTweetPollSwitch(tweetPoll);
                model.addAttribute("answers", answers);
                return "tweetpoll/detail";
            } else {
                return "404"; //FIXME: replace by ENUM
            }
        } catch (EnMeTweetPollNotFoundException e) {
            //e.printStackTrace();
            log.error(e);
            return "404"; //FIXME: replace by ENUM
        } catch (EnMeNoResultsFoundException e) {
            //e.printStackTrace();
             log.error(e);
             return "404"; //FIXME: replace by ENUM
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            log.error(e);
            return "404"; //FIXME: replace by ENUM
        }
    }
}