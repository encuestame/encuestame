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
package org.encuestame.mvc.controller.json.survey;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.exception.EnMeFailSendSocialTweetException;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.ConvertDomainToJson;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.ShortUrlProvider;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TweetPoll Json Controller.
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
            @RequestParam(value = "max", required = true) Integer max,
            @RequestParam(value = "start", required = true) Integer start,
            @RequestParam(value = "social_networks", required = false)  List<String> socialNetworks,
            @RequestParam(value = "social_account_networks", required = false) List<Long> socialAccountNetworks,
            @RequestParam(value = "_published", required = false) Boolean isPublished,
            @RequestParam(value = "_complete", required = false) Boolean isCompleted,
            @RequestParam(value = "_unpublished", required = false) Boolean isUnpublished,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            log.debug("search.json" + typeSearch);
            log.debug("search.json" + keyword);
            log.debug("search.json" + max);
            log.debug("search.json" + start);
            log.debug("search.json socialNetworks" + socialNetworks);
            log.debug("search.json socialAccountNetworks " + socialAccountNetworks);
            log.debug("search.json isCompleted " + isPublished);
            log.debug("search.json" + isCompleted);
            log.debug("search.json isUnpublished" + isUnpublished);
            final List<TweetPollBean> list = (List<TweetPollBean>) getTweetPollService().filterTweetPollByItemsByType(
                    TypeSearch.getSearchString(typeSearch), keyword, max,
                    start, TypeSearchResult.TWEETPOLL, request);
            log.debug("/api/survey/tweetpoll/search.json---------------->"+list.size());
            jsonResponse.put("tweetPolls", list);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
             log.error(e);
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
    @RequestMapping(value = "/api/survey/tweetpoll/save.json", method = RequestMethod.POST)
    public ModelMap get(
            @RequestParam(value = "question", required = true) final String question,
            @RequestParam(value = "hashtags", required = false) String[] hashtags,
            @RequestParam(value = "answers", required = false) final Long[] answers,
            HttpServletRequest request, HttpServletResponse response,
            final UserAccount user)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final TweetPoll tweetPoll = createTweetPoll(question, hashtags, user);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("tweetPoll", tweetPoll);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
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
    public ModelMap manageAnswer(
            @RequestParam(value = "id", required = true) final Long tweetPollId,
            @RequestParam(value = "answer", required = false) final String answer,
            @RequestParam(value = "answerId", required = false) final Long answerId,
            @RequestParam(value = "shortUrl", required = false) final String shortUrl,
            @PathVariable final String type,
            HttpServletRequest request,
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
         log.debug("answer tweetPollId "+tweetPollId);
         log.debug("tweetpoll answer"+answer);
         log.debug("tweetpoll answerId"+answerId);
         log.debug("tweetpoll shortUrl"+shortUrl);
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(
                    tweetPollId);
            log.debug("tweetpoll"+tweetPoll.getTweetPollId());
            if(!tweetPoll.getPublishTweetPoll()){
            log.debug("action ANSWER--->"+type);
            if ("add".equals(type)) {
                if((answer.isEmpty()) || (answer == null)){
                       throw new EnmeFailOperation("Answer can not valid");
                } else {
                     final QuestionAnswerBean answerBean = new QuestionAnswerBean(answer);
                     answerBean.setShortUrlType(ShortUrlProvider.get(shortUrl));
                     log.debug("new answer bean:{ "+answerBean.toString());
                     final TweetPollSwitch tweetPollSwitch = getTweetPollService()
                           .createTweetPollQuestionAnswer(answerBean, tweetPoll, request);
                     log.debug("new answer bean DOMAIN "+tweetPollSwitch.toString());
                     //log.debug("action questionAnswer "+questionAnswer);
                     jsonResponse.put("newAnswer", ConvertDomainBean.convertTweetPollSwitchToBean(tweetPollSwitch, request));
                     setItemResponse(jsonResponse);
                }

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
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     *
     * @param tweetPollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/autosave.json", method = RequestMethod.POST)
    public ModelMap create(
            @RequestParam(value = "tweetPollId", required = false) final Long tweetPollId,
            @RequestParam(value = "question", required = false) final String question,
            @RequestParam(value = "scheduled", required = false) final Boolean isScheduled,
            @RequestParam(value = "live_results", required = false) final Boolean liveResults,
            @RequestParam(value = "scheduled_time", required = false) final String scheduldedTime,
            @RequestParam(value = "scheduled_date", required = false) final String scheduledDate,
            @RequestParam(value = "captcha", required = false) final Boolean captcha,
            @RequestParam(value = "limit_votes", required = false) final Boolean limitVotes,
            @RequestParam(value = "on_live", required = false) final Boolean onLive,
            @RequestParam(value = "on_dashboard", required = false) final Boolean onDashboard,
            @RequestParam(value = "votes_to_limit", required = false) final Integer votesToLimit,
            //@PathVariable final String type,
            HttpServletRequest request,
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final UserAccount user = getUserAccount();
            final Options options = new Options();
            options.setCaptcha(captcha);
            options.setFollowDashBoard(onDashboard);
            options.setLimitVotes(limitVotes);
            options.setLiveResults(liveResults);

              if (tweetPollId == null) {
                  final TweetPollBean tweetPollBean = this.fillTweetPoll(
                          options, question, user, null);
                  //new tweetpoll domain.
                  final TweetPoll tp = createTweetPoll(tweetPollBean);
                  //retrieve answers stored.
                  log.debug("tweet poll created.");
                  final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                  jsonResponse.put("socialPublish", ConvertDomainBean.convertTweetPollToBean(tp));
              } else {
                  //update tweetPoll
                  final TweetPollBean tweetPollBean = this.fillTweetPoll(options, question, user, tweetPollId);
                  //final TweetPoll tweetPoll = updateTweetPoll(tweetPollId, question, hastagsArray.toArray(new String[]{}),
                   //       answerArray.toArray(new Long[]{}));
                  updateTweetPoll(tweetPollBean);
              }
        } catch (Exception e) {
            log.fatal(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     *
     * @param options
     * @param question
     * @param user
     * @param hastagsArray
     * @param tweetPollId
     * @return
     * @throws ParseException
     */
    private TweetPollBean fillTweetPoll(
            final Options options,
            final String question,
            final UserAccount user,
            final Long tweetPollId) throws ParseException{
        final TweetPollBean tweetPollBean = new TweetPollBean();
        if (tweetPollId != null) {
            tweetPollBean.setId(tweetPollId);
        }
        // save create tweet poll
        tweetPollBean.setUserId(user.getAccount().getUid());
        //defined values.
        tweetPollBean.setCloseNotification(Boolean.TRUE); //TOOD: ????
        tweetPollBean.setResultNotification(Boolean.TRUE);
        //resume live results.
        tweetPollBean.setResumeLiveResults(options.getResumeLiveResults());
        //follow on dashboard.
        tweetPollBean.setResumeTweetPollDashBoard(options.getFollowDashBoard());
        //captcha.
        tweetPollBean.setCaptcha(options.getCaptcha());
        //live results
        tweetPollBean.setAllowLiveResults(options.getLiveResults());
        //repeated votes
        tweetPollBean.setAllowRepeatedVotes(options.getRepeatedVotes());
        if (options.getRepeatedVotes()) {
            tweetPollBean.setMaxRepeatedVotes(options.getMaxRepeatedVotes());
        }
        //scheduled
        tweetPollBean.setSchedule(options.getScheduled());
        if (options.getScheduled()) {
            //eg. format 5/25/11 10:45:00
            final StringBuilder builder = new StringBuilder(options.getScheduledDate());
            builder.append(" ");
            builder.append(options.getScheduledTime());
            tweetPollBean.setScheduleDate(DateUtil.parseDate(builder.toString(), DateUtil.COMPLETE_FORMAT_TIME));
        }
        //limit votes
        tweetPollBean.setLimitVotesEnabled(options.getLimitVotes());
        if (options.getLimitVotes()) {
            tweetPollBean.setLimitVotes(options.getMaxLimitVotes());
        }
        //question
        tweetPollBean.setQuestionBean(new QuestionBean(question));
        log.debug("fillTweetPoll: "+tweetPollBean);
        return tweetPollBean;
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
    @RequestMapping(value = "/api/survey/tweetpoll/publish.json", method = RequestMethod.POST)
    public ModelMap publish(
            @RequestParam(value = "id", required = true) final Long tweetPollId,
            @RequestParam(value = "twitterAccounts", required = false) final Long[] twitterAccountsId,
            @RequestParam(value = "ip", required = false) final Boolean ip,
            @RequestParam(value = "limitNumbers", required = false) final Boolean limitNumbers,
            @RequestParam(value = "limitVotes", required = false) final Integer limitVotes,
            @RequestParam(value = "repeatedNumbers", required = false) final Integer repeatedNumbers,
            @RequestParam(value = "scheduled", required = false) final Boolean scheduled,
            @RequestParam(value = "scheduledDate", required = false) final String scheduledDate,
            @RequestParam(value = "scheduledTime", required = false) final String scheduledTime,
            HttpServletRequest request,
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(tweetPollId);
            if (tweetPoll == null) {
                setError("can not publish your tweetpoll", response);
            } else {
                if (twitterAccountsId.length == 0) {
                    throw new EnMeNoResultsFoundException("social accoutns are required to publish");
                }
                 //build text to tweet.
                 final String tweetText = getTweetPollService().generateTweetPollContent(tweetPoll);
                 log.debug("tweet text "+tweetText);
                 //check real lenght if execed limit required.
                 if (tweetText.length() > SocialUtils.TWITTER_LIMIT) {
                     throw new EnMeFailSendSocialTweetException(getMessage("e_020"));
                 }
                final List<SocialAccountBean> accountBeans = new ArrayList<SocialAccountBean>();
                //convert accounts id to real social accounts objects.
                for (int row = 0; row < twitterAccountsId.length; row++) {
                    final SocialAccountBean socialAccount = new SocialAccountBean();
                    socialAccount.setAccountId(twitterAccountsId[row]);
                    accountBeans.add(socialAccount);
                }
                log.debug("Accounts:{" + accountBeans.size());
                // multi publish social account.
                final List<TweetPollSavedPublishedStatus> results = getTweetPollService()
                        .publishMultiplesOnSocialAccounts(accountBeans,
                                tweetPoll, tweetText, TypeSearchResult.TWEETPOLL, null,  null);
                tweetPoll.setPublishTweetPoll(Boolean.TRUE);
                getTweetPollService().saveOrUpdateTweetPoll(tweetPoll);
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                jsonResponse.put("socialPublish", ConvertDomainToJson.convertTweetPollStatusToJson(results));
                setItemResponse(jsonResponse);
                //create notification.
                getTweetPollService().createTweetPollNotification(tweetPoll);
            }
        } catch (Exception e) {
            log.fatal(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
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
            } else if ("scheduled".equals(propertyType)) {
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
                jsonResponse.put("url", SocialUtils.getGoGl(url, EnMePlaceHolderConfigurer.getProperty("short.google.key")));
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

/**
 *
 * @author jpicado
 *
 */
class Options {
    private Boolean repeatedVotes;
    private Boolean resumeLiveResults;
    private Boolean scheduled;
    private Boolean limitVotes;
    private Boolean followDashBoard;
    private Boolean captcha;
    private Boolean liveResults;

    private Integer maxRepeatedVotes;
    private Integer maxLimitVotes;
    private String scheduledDate;
    private String scheduledTime;

    /**
     *
     */
    public Options() {}

    /**
     * @return the repeatedVotes
     */
    public Boolean getRepeatedVotes() {
        return repeatedVotes;
    }

    /**
     * @return the resumeLiveResults
     */
    public Boolean getResumeLiveResults() {
        return resumeLiveResults;
    }

    /**
     * @return the scheduled
     */
    public Boolean getScheduled() {
        return scheduled;
    }

    /**
     * @return the limitVotes
     */
    public Boolean getLimitVotes() {
        return limitVotes;
    }

    /**
     * @return the followDashBoard
     */
    public Boolean getFollowDashBoard() {
        return followDashBoard;
    }

    /**
     * @return the captcha
     */
    public Boolean getCaptcha() {
        return captcha;
    }

    /**
     * @return the liveResults
     */
    public Boolean getLiveResults() {
        return liveResults;
    }

    /**
     * @return the maxRepeatedVotes
     */
    public Integer getMaxRepeatedVotes() {
        return maxRepeatedVotes;
    }

    /**
     * @return the maxLimitVotes
     */
    public Integer getMaxLimitVotes() {
        return maxLimitVotes;
    }

    /**
     * @return the scheduledDate
     */
    public String getScheduledDate() {
        return scheduledDate;
    }

    /**
     * @return the scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * @param repeatedVotes the repeatedVotes to set
     */
    public void setRepeatedVotes(Boolean repeatedVotes) {
        this.repeatedVotes = repeatedVotes;
    }

    /**
     * @param resumeLiveResults the resumeLiveResults to set
     */
    public void setResumeLiveResults(Boolean resumeLiveResults) {
        this.resumeLiveResults = resumeLiveResults;
    }

    /**
     * @param scheduled the scheduled to set
     */
    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * @param limitVotes the limitVotes to set
     */
    public void setLimitVotes(Boolean limitVotes) {
        this.limitVotes = limitVotes;
    }

    /**
     * @param followDashBoard the followDashBoard to set
     */
    public void setFollowDashBoard(Boolean followDashBoard) {
        this.followDashBoard = followDashBoard;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(Boolean captcha) {
        this.captcha = captcha;
    }

    /**
     * @param liveResults the liveResults to set
     */
    public void setLiveResults(Boolean liveResults) {
        this.liveResults = liveResults;
    }

    /**
     * @param maxRepeatedVotes the maxRepeatedVotes to set
     */
    public void setMaxRepeatedVotes(Integer maxRepeatedVotes) {
        this.maxRepeatedVotes = maxRepeatedVotes;
    }

    /**
     * @param maxLimitVotes the maxLimitVotes to set
     */
    public void setMaxLimitVotes(Integer maxLimitVotes) {
        this.maxLimitVotes = maxLimitVotes;
    }

    /**
     * @param scheduledDate the scheduledDate to set
     */
    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    /**
     * @param scheduledTime the scheduledTime to set
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Options [repeatedVotes=" + repeatedVotes
                + ", resumeLiveResults=" + resumeLiveResults
                + ", scheduled=" + scheduled + ", limitVotes=" + limitVotes
                + ", followDashBoard=" + followDashBoard + ", captcha="
                + captcha + ", liveResults=" + liveResults
                + ", maxRepeatedVotes=" + maxRepeatedVotes
                + ", maxLimitVotes=" + maxLimitVotes + ", scheduledDate="
                + scheduledDate + ", scheduledTime=" + scheduledTime + "]";
    }
}