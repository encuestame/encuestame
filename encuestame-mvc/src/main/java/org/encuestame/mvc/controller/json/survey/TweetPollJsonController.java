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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.TweetPollBean;
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
 */
@Controller
public class TweetPollJsonController extends AbstractJsonController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

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
        List<TweetPollBean> list = new ArrayList<TweetPollBean>();
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
     * Publish tweet on social account.
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
    @RequestMapping(value = "/api/survey/tweetpoll/save.json", method = RequestMethod.GET)
    public ModelMap get(
            @RequestParam(value = "question", required = true) final String question,
            @RequestParam(value = "publish", required = false) Boolean publish,
            @RequestParam(value = "scheduled", required = false) final Boolean scheduled,
            @RequestParam(value = "hashtags", required = false) String[] hashtags,
            @RequestParam(value = "answers", required = true) final String[] answers,
            HttpServletRequest request, HttpServletResponse response,
            final UserAccount user)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final TweetPoll tweetPoll = createTweetPoll(question, hashtags, answers, user);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("tweetPoll", tweetPoll);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Add or Remove new Answer on TweetPoll.
     * @param tweetPollId
     * @param answer
     * @param answerId
     * @param type
     * @param request
     * @param response
     * @param user
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/answer/{type}.json", method = RequestMethod.GET)
    public ModelMap createAnswer(
            @RequestParam(value = "id", required = true) final Long tweetPollId,
            @RequestParam(value = "answer", required = false) final String answer,
            @RequestParam(value = "answerId", required = false) final Long answerId,
            @PathVariable final String type,
            HttpServletRequest request, HttpServletResponse response,
            final UserAccount user)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(
                    tweetPollId, getUserAccountLogged());
            if(!tweetPoll.getPublishTweetPoll()){
            final Question question = tweetPoll.getQuestion();
            if("add".equals(type)) {
                final QuestionAnswer questionAnswer = getTweetPollService().createQuestionAnswer(new QuestionAnswerBean(answer), question);
                jsonResponse.put("answer", questionAnswer);
                setItemResponse(jsonResponse);
            } else if("remove".equals(type)) {
                getTweetPollService().removeQuestionAnswer(getTweetPollService().getQuestionAnswerById(answerId));
                setSuccesResponse();
            } else {
                throw new EnmeFailOperation("operation not valid");
            }
            } else {
                throw new EnMeExpcetion("tweetpoll is published");
            }
        } catch (EnMeExpcetion e) {
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Publish tweetPoll.
     * @param tweetPollId
     * @param twitterAccountsId
     * @param request
     * @param response
     * @param userAccount
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/publish.json", method = RequestMethod.GET)
    public ModelMap publish(
            @RequestParam(value = "tweetPollId", required = true) final Long tweetPollId,
            @RequestParam(value = "twitterAccounts", required = true) final Long[] twitterAccountsId,
            HttpServletRequest request,
            HttpServletResponse response,
            final UserAccount userAccount)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(tweetPollId, userAccount);
            if (tweetPoll == null) {
                setError("tweetpoll id invalid", response);
            } else {
                 String tweetText;
                 tweetText = getTweetPollService().generateTweetPollContent(
                         tweetPoll, getUrlDomain(request, Boolean.TRUE));
                final List<SocialAccountBean> accountBeans = new ArrayList<SocialAccountBean>();
                for (int row = 0; row < twitterAccountsId.length; row++) {
                    final SocialAccountBean twitter = new SocialAccountBean();
                    twitter.setAccountId(twitterAccountsId[row]);
                    accountBeans.add(twitter);
                }
                log.debug("Accounts:{" + accountBeans.size());
                // multi publish social account.
                final List<TweetPollSavedPublishedStatus> results = getTweetPollService()
                        .publicMultiplesTweetAccounts(accountBeans,
                                tweetPoll, tweetText);
                tweetPoll.setCompleted(Boolean.TRUE);
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                jsonResponse.put("socialPublish", results);
                setItemResponse(jsonResponse);
            }
        } catch (Exception e) {
            setError(e, response);
        }
        return returnData();
    }

    /**
     * Update {@link TweetPoll} properties.
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



    /**
     * Short URL.
     * @param type provider short URL
     * @param url URL
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/short/url/{type}.json", method = RequestMethod.GET)
    public ModelMap getShortUrl(
            @PathVariable String type,
            @RequestParam(value = "url", required = true) String url,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            url = filterValue(url);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            if(InternetUtils.validateUrl(url)){
            if("google".equals(type)){
                jsonResponse.put("url", SocialUtils.getGoGl(url, EncuestamePlaceHolderConfigurer.getProperty("short.google.key")));
            } else if("tinyurl".equals(type)){
                jsonResponse.put("url", SocialUtils.getTinyUrl(url));
            }
            setItemResponse(jsonResponse);
            } else {
                setError("url malformed", response);
            }
        } catch (Exception e) {
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}