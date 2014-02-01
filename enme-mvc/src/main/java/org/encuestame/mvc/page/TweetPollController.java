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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.imp.SecurityOperations;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @param id id tweet
     * @return view to redirect.
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/tweetpoll/vote/{tweetId}", method = RequestMethod.GET)
    public String tweetPollController(
        ModelMap model,
        @PathVariable String tweetId,
        final HttpServletRequest req) throws UnknownHostException {
        log.debug("tweetId: "+tweetId);
        String pathVote = "badTweetVote";
        String IP = getIpClient(req);
        // Check IP in BlackListFile
            final Boolean checkBannedIp = checkIPinBlackList(IP);
            log.debug("Check Banned IP----> " + checkBannedIp);
            if (checkBannedIp) {
                pathVote ="banned";
                log.debug("ip banned");
            } else {
                if (tweetId.isEmpty()) {
                    log.debug("tweet is empty");
                    model.put("message", "Tweet Not Valid..");
                } else {
                    tweetId = filterValue(tweetId);
                    model.put("tweetId", tweetId);
                    log.info("search code->"+tweetId);
                    final TweetPollSwitch tweetPoll = getTweetPollService()
                            .getTweetPollDao().retrieveTweetsPollSwitch(tweetId);
                    model.addAttribute("tp_switch", tweetPoll);
                    final Boolean validateLimitVotes = getTweetPollService().validateLimitVotes(tweetPoll.getTweetPoll());
                    final Boolean restrictVotesByDate = getTweetPollService().validateVotesByDate(tweetPoll.getTweetPoll());
                     //NOTE: tweetpoll should be published to able to vote !!
                    if (tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                        log.debug("tweetpoll answer not found");
                        model.put("message", getMessage("tp_no_valid"));
                    } else  if (tweetPoll.getTweetPoll().getCompleted()) {
                        log.debug("tweetpoll is archived");
                        model.put("message", getMessage("tweetpoll.votes.completed"));
                        pathVote = "completeTweetVote";
                        // Validate Limit Votes
                    } else if(validateLimitVotes) {
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
						log.error("");
						pathVote = "repeatedTweetVote";
					}
				}
			}
            log.info("redirect template WHERE "+pathVote);
        }
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
            Model model) throws UnknownHostException {
             log.info("recaptcha_challenge_field "+challenge);
             log.info("recaptcha_rforgotesponse_field "+response);
             log.info("code "+code.toString());
             log.info("vote "+vote.toString());
             log.info("model "+model.asMap().toString());
             challenge = filterValue(challenge);
             response = filterValue(response);
             code = filterValue(code);
             vote = (UtilVoteCaptcha) model.asMap().get("captchaForm");
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
    public String tweetPollController(final ModelMap model) {
        addItemsManangeMessages(model);
        addi18nProperty(model, "tweetpoo_detail_tab_detail", getMessage("tweetpoo_detail_tab_detail"));
        addi18nProperty(model, "tweetpoo_detail_tab_stats", getMessage("tweetpoo_detail_tab_stats"));
        addi18nProperty(model, "tweetpoo_detail_tab_comments", getMessage("tweetpoo_detail_tab_comments"));
        addi18nProperty(model, "tweetpoo_detail_tab_social", getMessage("tweetpoo_detail_tab_social"));
        addi18nProperty(model, "tweetpoo_detail_tab_delete", getMessage("tweetpoo_detail_tab_delete"));
        addi18nProperty(model, "tweetpoo_detail_answers_title_link", getMessage("tweetpoo_detail_answers_title_link"));
        addi18nProperty(model, "tweetpoo_detail_answers_title_count", getMessage("tweetpoo_detail_answers_title_count"));
        addi18nProperty(model, "tweetpoo_detail_answers_title_percent", getMessage("tweetpoo_detail_answers_title_percent"));
        addi18nProperty(model, "commons_created_date", getMessage("commons_created_date"));
        addi18nProperty(model, "commons_captcha", getMessage("commons_captcha"));
        addi18nProperty(model, "tp_options_allow_results", getMessage("tp_options_allow_results"));
        addi18nProperty(model, "tp_options_follow_dashboard", getMessage("tp_options_follow_dashboard"));
        addi18nProperty(model, "tp_options_allow_repeated_votes", getMessage("tp_options_allow_repeated_votes"));
        addi18nProperty(model, "tp_options_notifications", getMessage("tp_options_notifications"));
        addi18nProperty(model, "related_terms", getMessage("related_terms"));
        addi18nProperty(model, "commons_success", getMessage("commons_success"));
        addi18nProperty(model, "detail_clean_filters", getMessage("detail_clean_filters"));
        addi18nProperty(model, "commons_favourite");
        addi18nProperty(model, "e_023");
        addi18nProperty(model, "commons_no_results");
        addi18nProperty(model, "commons_unfavourite");
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
        log.debug("tweetpoll");
        return "redirect:/user/tweetpoll/list";
    }

    /**
     * TweetPoll Controller.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/tweetpoll/new", method = RequestMethod.GET)
    public String newTweetPollController(final ModelMap model) {
        //check social accounts.
        String path = "tweetpoll/new";
        try {
            final List<SocialAccountBean> socials = getSecurityService().getValidSocialAccounts(SocialProvider.ALL, false);
            if (socials.size() == 0) {
                path = "tweetpoll/social";
            }
        } catch (EnMeNoResultsFoundException e) {
            log.error(e);
            path = "505";
        }
        log.debug("newTweetPollController "+path);
        //log.debug("tweetpoll new");
        addi18nProperty(model, "leave_mesage");
        addi18nProperty(model, "tp_write_questions", getMessage("tp_write_questions"));
        addi18nProperty(model, "tp_add_answer", getMessage("tp_add_answer"));
        addi18nProperty(model, "tp_add_hashtag", getMessage("tp_add_hashtag"));
        addi18nProperty(model, "tp_scheduled", getMessage("tp_scheduled"));
        addi18nProperty(model, "tp_customize", getMessage("tp_customize"));
        addi18nProperty(model, "tp_select_publish", getMessage("tp_select_publish"));
        addi18nProperty(model, "tp_options_chart", getMessage("tp_options_chart"));
        addi18nProperty(model, "tp_options_spam", getMessage("tp_options_spam"));
        addi18nProperty(model, "tp_options_report", getMessage("tp_options_report"));
        addi18nProperty(model, "tp_options_scheduled_this_tweetpoll", getMessage("tp_options_scheduled_this_tweetpoll"));
        addi18nProperty(model, "tp_options_allow_results", getMessage("tp_options_allow_results"));
        addi18nProperty(model, "tp_options_allow_repeated_votes", getMessage("tp_options_allow_repeated_votes"));
        addi18nProperty(model, "tp_options_limit_votes", getMessage("tp_options_limit_votes"));
        addi18nProperty(model, "tp_options_resume_live_results", getMessage("tp_options_resume_live_results"));
        addi18nProperty(model, "tp_options_follow_dashboard", getMessage("tp_options_follow_dashboard"));
        addSocialPickerWidgetMessages(model);
        addi18nProperty(model, "button_add", getMessage("button_add"));
        addi18nProperty(model, "button_remove", getMessage("button_remove"));
        addi18nProperty(model, "button_close", getMessage("button_close"));
        addi18nProperty(model, "button_finish", getMessage("button_finish"));
        addi18nProperty(model, "button_publish", getMessage("button_publish"));
        addi18nProperty(model, "button_try_again", getMessage("button_try_again"));
        addi18nProperty(model, "button_ignore", getMessage("button_ignore"));
        addi18nProperty(model, "button_try_later", getMessage("button_try_later"));
        addi18nProperty(model, "commons_captcha", getMessage("commons_captcha"));
        addi18nProperty(model, "tp_publish_error", getMessage("tp_publish_error"));
        addi18nProperty(model, "pubication_failure_status", getMessage("pubication_failure_status"));
        addi18nProperty(model, "pubication_success_status", getMessage("pubication_success_status"));
        addi18nProperty(model, "pubication_inprocess_status", getMessage("pubication_inprocess_status"));
        addi18nProperty(model, "e_020", getMessage("e_020"));
        addi18nProperty(model, "e_021", getMessage("e_021"));
        addi18nProperty(model, "e_024", getMessage("e_024"));
        addi18nProperty(model, "commons_success", getMessage("commons_success"));
        addi18nProperty(model, "commons_failure", getMessage("commons_failure"));
        addi18nProperty(model, "commons_tab_to_save", getMessage("commons_tab_to_save"));
        addi18nProperty(model, "button_scheduled", getMessage("button_scheduled"));
        //help context
        addi18nProperty(model, "tp_help_question", getMessage("tp_help_question"));
        addi18nProperty(model, "tp_help_1", getMessage("tp_help_1"));
        addi18nProperty(model, "tp_help_2", getMessage("tp_help_2"));
        addi18nProperty(model, "tp_help_3", getMessage("tp_help_3"));
        addi18nProperty(model, "tp_help_4", getMessage("tp_help_4"));
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
        try {
            final String ipAddress = getIpClient(request);
            slug = filterValue(slug);
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollByIdSlugName(id, slug);
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
            log.error(e);
            return "404"; //FIXME: replace by ENUM
        } catch (EnMeNoResultsFoundException e) {
             log.error(e);
             return "404"; //FIXME: replace by ENUM
        } catch (UnknownHostException e) {
            e.printStackTrace();
            log.error(e);
            return "404"; //FIXME: replace by ENUM
        }
    }
}