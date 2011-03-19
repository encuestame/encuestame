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
package org.encuestame.mvc.controller.json.survey;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.util.MD5Utils;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.QuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TweetPoll Json Controller.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 2, 2010 10:11:07 PM
 * @version $Id:$
 */
@Controller
public class TweetPollJsonController extends AbstractJsonController {

    /**
     * Get List TweetPoll.
     * @param typeSearch
     * @param keyword
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/search.json", method = RequestMethod.GET)
    public ModelMap getListTweetPoll(
            @RequestParam(value = "typeSearch", required = true) String typeSearch,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "max", required = false)Integer max,
            @RequestParam(value = "start", required = false)Integer start,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        List<UnitTweetPoll> list = new ArrayList<UnitTweetPoll>();
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            if(TypeSearch.KEYWORD.name().equals(typeSearch)){
                list = getTweetPollService().searchTweetsPollsByKeyWord(getUserPrincipalUsername(), keyword, max, start);
            } else if(TypeSearch.ALL.name().equals(typeSearch)){
                list = getTweetPollService().getTweetsPollsByUserName(getUserPrincipalUsername(), max, start);
            } else if(TypeSearch.LASTDAY.name().equals(typeSearch)){
                list = getTweetPollService().searchTweetsPollsToday(getUserPrincipalUsername(), max, start);
            } else if(TypeSearch.LASTWEEK.name().equals(typeSearch)){
                list = getTweetPollService().searchTweetsPollsLastWeek(getUserPrincipalUsername(), max, start);
            } else if(TypeSearch.FAVOURITES.name().equals(typeSearch)){
                list = getTweetPollService().searchTweetsPollFavourites(getUserPrincipalUsername(), max, start);
            } else if(TypeSearch.SCHEDULED.name().equals(typeSearch)){
                list = getTweetPollService().searchTweetsPollScheduled(getUserPrincipalUsername(), max, start);
            } else {
                list = getTweetPollService().getTweetsPollsByUserName(getUserPrincipalUsername(), max, start);
            }
            jsonResponse.put("tweetPolls", list);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
             log.error(e);
             e.printStackTrace();
             setError(e.getMessage(), response);
        }

        return returnData();
    }

    /**
     * Publish tweet on social accout.
     * @param twitterAccountsId
     * @param question
     * @param scheduled
     * @param hashtags
     * @param answers
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/publish.json", method = RequestMethod.GET)
    public ModelMap get(
            @RequestParam(value = "twitterAccounts", required = true) final Long[] twitterAccountsId,
            @RequestParam(value = "question", required = true) final String question,
            @RequestParam(value = "scheduled", required = false) final Boolean scheduled,
            @RequestParam(value = "hashtags", required = false) String[] hashtags,
            @RequestParam(value = "answers", required = true) final String[] answers,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final UnitTweetPoll tweetPoll = new UnitTweetPoll();
        // Get User Logged.
        final UserAccount user = getByUsername(getUserPrincipalUsername());
        log.debug("user " +user.getUsername());
        if (user != null) {
            // set user id to question bean.
            final QuestionBean questionBean = new QuestionBean();
            questionBean.setQuestionName(question);
            questionBean.setUserId(user.getUid());
            log.debug("questionBean.name() " +questionBean.getQuestionName());
            //Add answers
            try {
                //Setting Answers.
                for (int row = 0; row < answers.length; row++) {
                    final UnitAnswersBean answer = new UnitAnswersBean();
                    answer.setAnswers(answers[row].trim());
                    answer.setAnswerHash(MD5Utils.md5(
                            String.valueOf(java.util.Calendar.getInstance().getTimeInMillis())
                            + RandomStringUtils.randomAlphabetic(2)));
                    questionBean.getListAnswers().add(answer);
                }

                //Setting Hash Tags
                hashtags = hashtags == null ?  new String[0] : hashtags;
                for (int row = 0; row < hashtags.length; row++) {
                    final HashTagBean hashTag = new HashTagBean();
                    hashTag.setHashTagName(answers[row].toLowerCase().trim());
                    tweetPoll.getHashTags().add(hashTag);
                }

                log.debug("questionBean.getListAnswers() " +questionBean.getListAnswers().size());
                //save question
                  final Question questionDomain = getSurveyService().createQuestion(questionBean);
                // save create tweet poll
                    tweetPoll.setUserId(user.getAccount().getUid());
                    tweetPoll.setCloseNotification(Boolean.FALSE);
                    tweetPoll.setResultNotification(Boolean.FALSE);
                    tweetPoll.setPublishPoll(Boolean.TRUE); //always TRUE
                    tweetPoll.setSchedule(scheduled);
                    getTweetPollService().createTweetPoll(tweetPoll, questionDomain);
                    // If publish is true and Scheduled is false, because if is
                    // scheduled we want
                    // send later.
                    if (tweetPoll.getPublishPoll()
                            && !tweetPoll.getSchedule()) {
                        String tweetText;
                            tweetText = getTweetPollService()
                                    .generateTweetPollText(tweetPoll, getUrlDomain(request, Boolean.TRUE));
                        final List<SocialAccountBean> accountBeans = new ArrayList<SocialAccountBean>();
                        for (int row = 0; row < twitterAccountsId.length; row++) {
                            final SocialAccountBean twitter = new SocialAccountBean();
                                   twitter.setAccountId(twitterAccountsId[row]);
                                   accountBeans.add(twitter);
                        }
                        log.debug("Accounts " + accountBeans.size());
                        getTweetPollService().publicMultiplesTweetAccounts(accountBeans,
                                              tweetPoll.getId(), tweetText);
                    }
                    log.debug("tweet poll created");
                setSuccesResponse();
            } catch (EnMeExpcetion e) {
                  log.error(e);
                  e.printStackTrace();
                  setError(e.getMessage(), response);
            } catch (NoSuchAlgorithmException e) {
                log.error(e);
                e.printStackTrace();
                setError(e.getMessage(), response);
            }
        } else {
            setError("access denied", response);
        }
        return returnData();
    }

    /**
     * Change TweetPoll Properties.
     * @param propertyType
     * @param tweetPollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value ="/api/survey/tweetpoll/{propertyType}-tweetpoll.json", method = RequestMethod.GET)
    public ModelMap changeTweetPollProperties(
            @PathVariable String propertyType,
            @RequestParam(value = "tweetPollId", required = true) Long tweetPollId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            log.debug("Property Type " + propertyType);
            if ("change-open-status".equals(propertyType)) {
                getTweetPollService().changeStatusTweetPoll(tweetPollId,
                        getUserPrincipalUsername());
            } else if ("resumeliveResults".equals(propertyType)) {
                getTweetPollService().changeResumeLiveResultsTweetPoll(
                        tweetPollId, getUserPrincipalUsername());
            } else if ("captcha".equals(propertyType)) {
                getTweetPollService().changeAllowCaptchaTweetPoll(tweetPollId,
                        getUserPrincipalUsername());
            } else if ("favourite".equals(propertyType)) {
                getTweetPollService().setFavouriteTweetPoll(tweetPollId,
                        getUserPrincipalUsername());
            } else if ("liveResults".equals(propertyType)) {
                getTweetPollService().changeAllowLiveResultsTweetPoll(
                        tweetPollId, getUserPrincipalUsername());
            } else if ("notification".equals(propertyType)) {
                getTweetPollService().changeCloseNotificationTweetPoll(
                        tweetPollId, getUserPrincipalUsername());
            } else if ("repeated".equals(propertyType)) {
                log.debug("Property Type" + propertyType);
                getTweetPollService().changeAllowRepeatedTweetPoll(
                        tweetPollId, getUserPrincipalUsername());
            } else {
                log.warn("Type not valid");
            }
            setSuccesResponse();
        }
        catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                setError(e.getMessage(), response);
        }
        return returnData();
    }
}
