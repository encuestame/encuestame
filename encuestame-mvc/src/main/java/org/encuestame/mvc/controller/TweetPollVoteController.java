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

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.persistence.domain.TweetPollSwitch;
import org.encuestame.core.service.ISecurityService;
import org.encuestame.mvc.controller.validation.ControllerValidation;
import org.encuestame.utils.vote.UtilVoteCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

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
        }
        else {
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
                        if(!tweetPoll.getTweetPoll().getCaptcha()){
                            getTweetPollService().tweetPollVote(tweetPoll, IP);
                            model.put("message", "Tweet Poll Voted.");
                            pathVote = "tweetVoted";
                            log.debug("VOTED");
                        }
                        else{
                            final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
                            final UtilVoteCaptcha captchaBean = new UtilVoteCaptcha();
                            captchaBean.setCaptcha(captcha);
                            captchaBean.setCodeVote(tweetId);
                            model.addAttribute("captchaForm", captchaBean);
                            log.debug("VOTE WITH CAPTCHA");
                            pathVote = "voteCaptcha";
                        }
                    }
                    else{
                        log.debug("Tweet Vote Repeteaded.");
                        model.put("message", "Tweet Vote Repeteaded.");
                        pathVote = "repeatedTweetVote";
                    }
                    model.get("message");
            }
        }
        log.info("redirect template WHERE "+pathVote);
        return pathVote;
    }

    /**
     * Process Submit.
     * @param req
     * @param challenge
     * @param response
     * @param code
     * @param vote
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
        HttpServletRequest req,
        @RequestParam("recaptcha_challenge_field") String challenge,
        @RequestParam("recaptcha_response_field") String response,
        @RequestParam("vote_code") String code,
        @ModelAttribute UtilVoteCaptcha vote, BindingResult result, SessionStatus status) {
             log.info("recaptcha_challenge_field "+challenge);
             log.info("recaptcha_rforgotesponse_field "+response);
             log.info("vote_code "+code);
             final ISecurityService securityService = getServiceManager().getApplicationServices().getSecurityService();
             final ReCaptchaResponse reCaptchaResponse = getReCaptcha().checkAnswer(req.getRemoteAddr(), challenge, response);
             final ControllerValidation validation = new ControllerValidation(securityService);
             validation.validateCaptcha(reCaptchaResponse, result);
             log.info("reCaptchaResponse "+reCaptchaResponse.getErrorMessage());
             log.info("reCaptchaResponse "+reCaptchaResponse.isValid());
             log.info("result.hasErrors() "+result.hasErrors());
             if (result.hasErrors()) {
                log.debug("bad captcha");
                return "tweetVoted";
             }
             else {
                 //Find Answer To Vote.
                 final TweetPollSwitch tweetPoll = getTweetPollService().getTweetPollDao().retrieveTweetsPollSwitch(code);
                 //Validate Code.
                 if (tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                     log.debug("tweetpoll answer not found");
                     return "badTweetVote";
                     //model.put("message", "Tweet Not Valid.");
                 } else {
                     //Save Vote.
                     final String IP = getIpClient();
                     log.info("IP" + IP);
                     getTweetPollService().tweetPollVote(tweetPoll, IP);
                     //log.info("password generated "+password);
                     return "tweetVoted";
                 }
            }
    }
}
