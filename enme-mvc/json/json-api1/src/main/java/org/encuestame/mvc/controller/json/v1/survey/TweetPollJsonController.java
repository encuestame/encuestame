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
package org.encuestame.mvc.controller.json.v1.survey;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.ConvertDomainToJson;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.question.QuestionAnswer;
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
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.search.TweetPollSearchBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TweetPoll Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 2, 2010 10:11:07 PM
 */
@Controller
public class TweetPollJsonController extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * @api {get} /api/survey/tweetpoll/search.json Get TweetPolls
     * @apiName GetTweetpolls
     * @apiGroup Tweetpoll
     * @apiDescription Returns a collection of relevant TweetPolls matching a specified query.
     * @apiParam {String} typeSearch - XXXX
     * @apiParam {String} [keyword] Keyword to search related Tweetpolls.
     * @apiParam {Number} max Defines the maximum number of search results.
     * @apiParam {Number} start Defines the starting number of the page of results.
     * @apiParam {String[]} [social_networks] Filter tweetpolls by social networks accounts.
     * @apiParam {Number[] } [social_account_networks] Filter tweetpolls by social networks accounts.
     * @apiParam {Boolean} [_published] Filter by published tweetpolls.
     * @apiParam {Boolean} [_complete] Filter by completed tweetpolls.
     * @apiParam {Boolean} [_favourite] Filter by favourite.
     * @apiParam {Boolean} [_scheduled] Filter by tweetpolls scheduled
     * @apiParam {String} [period] Date range to search results.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/search.json
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/search.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getListTweetPoll(
            @RequestParam(value = "typeSearch", required = true) String typeSearch,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "max", required = true) Integer max,
            @RequestParam(value = "start", required = true) Integer start,
            @RequestParam(value = "social_networks", required = false)  List<String> socialNetworks,
            @RequestParam(value = "social_account_networks", required = false) List<Long> socialAccountNetworks,
            @RequestParam(value = "_published", required = false) Boolean isPublished,
            @RequestParam(value = "_complete", required = false) Boolean isCompleted,
            @RequestParam(value = "_favourite", required = false) Boolean isFavourite,
            @RequestParam(value = "_scheduled", required = false) Boolean isScheduled,
            @RequestParam(value = "period", required = false) String period,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        final TweetPollSearchBean tpollSearchBean = new TweetPollSearchBean();
        try {
            log.debug("search.json" + typeSearch);
            log.debug("search.json" + keyword);
            log.debug("search.json" + max);
            log.debug("search.json" + start);
            log.debug("search.json socialNetworks" + socialNetworks);
            log.debug("search.json socialAccountNetworks " + socialAccountNetworks);
            log.debug("search.json isCompleted " + isPublished);
            log.debug("search.json" + isCompleted);
            log.debug("search.json favourite" + isFavourite);
            // Create TweetpollSearchBean
            tpollSearchBean.setIsComplete(isCompleted == null ? false : isCompleted);
            tpollSearchBean.setIsFavourite(isFavourite == null ? false : isFavourite);
            tpollSearchBean.setIsPublished(isPublished == null ? false : isPublished);
            tpollSearchBean.setIsScheduled(isScheduled == null ? false : isScheduled);
            tpollSearchBean.setKeyword(keyword == null ? null : keyword.isEmpty() ? null : keyword);
            tpollSearchBean.setMax(max);
            //tpollSearchBean.setPeriod(period); it's not used, will be removed in the future.
            tpollSearchBean.setSearchResult(null);
            tpollSearchBean.setStart(start);
            tpollSearchBean.setTypeSearch(TypeSearch.getSearchString(typeSearch));
            tpollSearchBean.setProviders(socialNetworks == null ? ListUtils.EMPTY_LIST : ConvertDomainBean.convertSocialProviderStringToProvider(socialNetworks));
            tpollSearchBean.setSocialAccounts(socialAccountNetworks == null ? ListUtils.EMPTY_LIST : socialAccountNetworks);
            final List<SearchBean> list = (List<SearchBean>) getTweetPollService().filterTweetPollByItemsByTypeSearch(
                    tpollSearchBean, request);
            log.debug("/api/survey/tweetpoll/search.json---------------->  "+ list.size());
            jsonResponse.put("tweetPolls", list);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
             log.error(e);
             setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {post} /api/survey/tweetpoll/save.json Publish Tweetpoll
     * @apiName PostPublishTweetPoll
     * @apiGroup Tweetpoll
     * @apiDescription Publish tweet on social account.
     * @apiParam {String} question - Free Question text.
     * @apiParam {String[]} [hashtags] Aggregated list of Hashtags.
     * @apiParam {Number[]} [answers] Arraylist with the answers id.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/save.json
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/save.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap get(
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
     * @api {get} /api/survey/tweetpoll/answer/{type}.json Add Tweetpoll answers
     * @apiName GetTweetpollAnswers
     * @apiGroup Tweetpoll
     * @apiDescription Add or Remove new Answer on TweetPoll.
     * @apiParam {String="add","remove"} type Type of operation to be performed
     * @apiParam {Number} id Unique identifier of the element to which an answer will be added.
     * @apiParam {String} [answer]
     * @apiParam {Number} [answerId] Answer unique identifier to which will be removed.
     * @apiParam {String} [shortUrl] url generated with shortener url provider.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/answer/{type}.json
     * @apiPermission none
     * @apiSuccessExample
     *	 {
  			"error": {

  			},
			"success": {
				"newAnswer": {
				     "id": 164,
				     "tweet_poll_id": 83,
				     "answer": {
				     	"answer_id": 271,
				     	"answers": "yes",
				     	"url": null,
				     	"short_url": "yourls",
				     	"qid": 110,
				     	"color": "#A31B80",
				     	"provider": null
			      	},
					"short_url": "http:\/\/localhost:8080\/encuestame\/tweetpoll\/vote\/8df12ef732cd9a326be47fc3f0d6b45a",
			    	"relative_url": "http:\/\/localhost:8080\/encuestame\/tweetpoll\/vote\/8df12ef732cd9a326be47fc3f0d6b45a",
			    	"results": null
				}
  		}
	}
	* @apiSuccessExample
	* Remove
	* @apiErrorExample
		{
			"error":{

					},
			"success":{
				"r":0
			}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/answer/{type}.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap manageAnswer(
            @RequestParam(value = "id", required = true) final Long tweetPollId,
            @RequestParam(value = "answer", required = false) final String answer,
            @RequestParam(value = "answerId", required = false) final Long answerId,
            @RequestParam(value = "shortUrl", required = false) String shortUrl,
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
            final TweetPoll tweetPoll = getTweetPollService().getTweetPollById(tweetPollId);
            log.debug("tweetpoll" + tweetPoll.getTweetPollId());
            if(!tweetPoll.getPublishTweetPoll()){
            log.debug("action ANSWER--->"+type);
            if ("add".equals(type)) {
                if ((answer.isEmpty()) || (answer == null)) {
                       throw new EnmeFailOperation("Answer can not valid");
                } else {
                     final QuestionAnswerBean answerBean = new QuestionAnswerBean(answer);
                     if (shortUrl == null || shortUrl.isEmpty()) {
                         shortUrl = EnMePlaceHolderConfigurer.getProperty("short.default");
                     }
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
                QuestionAnswer answerO = getTweetPollService().getQuestionAnswerById(answerId);
                logPrint(answerO.toString());
                if (answerO != null) {
                    logPrint(answerO.getQuestionAnswerId());
                    getTweetPollService().removeQuestionAnswer(answerO);
                    setSuccesResponse();
                } else {
                    throw new EnMeNoResultsFoundException("answer not found");
                }
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
     * @api {post} /api/survey/tweetpoll/autosave Create Tweetpoll
     * @apiName PostCreateTweetpoll
     * @apiGroup Tweetpoll
     * @apiDescription Return all comments that will be filtered by type.
     * @apiParam {Number} [tweetPollId]
     * @apiParam {String} [question] Free text question.
     * @apiParam {Boolean} [scheduled] Schedule Tweetpoll publication
     * @apiParam {Boolean} [liveResults]  Display the results should be shown live.
     * @apiParam {String} [scheduledTime]  Scheduled time for Tweetpoll publication.
     * @apiParam {String} [scheduledDate] Scheduled date for Tweetpoll publication.
     * @apiParam {Boolean} [captcha] Validate the vote with captcha
     * @apiParam {Boolean} [limitVotes] Limit maximum number of votes
     * @apiParam {Boolean} [repeatedVotes] Allow repeated votes.
     * @apiParam {Number} [maxLimitVotes] Limit the maximum number of votes in total Tweetpoll.
     * @apiParam {Number} [maxRepeatedVotes] Maximum number of votes allowed per IP.
     * @apiParam {Boolean} [resumeLiveResults] Allow display live results.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/autosave
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/autosave", method = RequestMethod.POST)
    public @ResponseBody ModelMap create(
            @RequestParam(value = "tweetPollId", required = false) final Long tweetPollId,
            @RequestParam(value = "question", required = false) final String question,
            @RequestParam(value = "scheduled", required = false) final Boolean isScheduled,
            @RequestParam(value = "liveResults", required = false) final Boolean liveResults,
            @RequestParam(value = "scheduledTime", required = false) final String scheduldedTime,
            @RequestParam(value = "scheduledDate", required = false) final String scheduledDate,
            @RequestParam(value = "captcha", required = false) final Boolean captcha,
            @RequestParam(value = "limitVotes", required = false) final Boolean limitVotes,
            @RequestParam(value = "repeatedVotes", required = false) final Boolean repeatedVotes,
            @RequestParam(value = "maxLimitVotes", required = false) final Integer votesToLimit ,
            @RequestParam(value = "maxRepeatedVotes", required = false) final Integer repeatedVotesNum,
            @RequestParam(value = "resumeLiveResults", required = false) final Boolean resumeLiveResults,
            //@PathVariable final String type,
            HttpServletRequest request,
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final UserAccount user = getUserAccount();
            final Options options = new Options();
            log.debug("Autosave TweetPoll Id --> " + tweetPollId);
            log.debug("Autosave Question --> " + question);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            if (tweetPollId == null && question != null && !question.isEmpty()) {
                  final TweetPollBean tweetPollBean = this.fillTweetPoll(
                          options, question, user, null);
                  //new tweetpoll domain.
                  final TweetPoll tp = createTweetPoll(tweetPollBean);
                  //retrieve answers stored.
                  final TweetPollBean tpB = ConvertDomainBean.convertTweetPollToBean(tp);
                  log.debug("Tweetpoll Created --->" + tpB.toString());
                  jsonResponse.put("tweetPoll", tpB);
             } else if (tweetPollId != null && question != null) {
                 options.setCaptcha(captcha);
                 options.setLimitVotes(limitVotes);
                 options.setLiveResults(liveResults);
                 options.setScheduled(isScheduled);
                 options.setScheduledDate(filterValue(scheduledDate));
                 options.setScheduledTime(scheduldedTime);
                 options.setMaxLimitVotes(votesToLimit);
                 options.setRepeatedVotes(repeatedVotes);
                 options.setMaxRepeatedVotes(repeatedVotesNum);
                 options.setResumeLiveResults(resumeLiveResults);
                  //update tweetPoll
                 final TweetPollBean tweetPollBean = this.fillTweetPoll(options, question, user, tweetPollId);
                 ConvertDomainBean.convertTweetPollToBean(updateTweetPoll(tweetPollBean));
                 log.debug("Tweetpoll Updated --->" + tweetPollBean.toString());
                 jsonResponse.put("tweetPoll", tweetPollBean);
             } else {
                 setError("create tweetpoll bad request", response);
             }
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.fatal(e);
            //e.printStackTrace();
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
        log.debug("fillTweetPoll options" + options.toString());
        log.debug("fillTweetPoll user" +user.toString());
        log.debug("fillTweetPoll question" + question.toString());
        log.debug("fillTweetPoll tweetPollId" + tweetPollId);
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

        //captcha.
        tweetPollBean.setCaptcha(options.getCaptcha());
        //live results
        tweetPollBean.setAllowLiveResults(options.getLiveResults());
        //repeated votes
        tweetPollBean.setAllowRepeatedVotes(options.getRepeatedVotes());
        if (options.getRepeatedVotes() != null && options.getRepeatedVotes()) {
            tweetPollBean.setMaxRepeatedVotes(options.getMaxRepeatedVotes());
        }
        //scheduled
        tweetPollBean.setSchedule(options.getScheduled());
        if (options.getScheduled() != null && options.getScheduled()) {
            //eg. format 5/25/11 10:45:00
            final StringBuilder builder = new StringBuilder(options.getScheduledDate());
            builder.append(" ");
            builder.append(options.getScheduledTime());
            tweetPollBean.setScheduleDate(DateUtil.parseDate(builder.toString(), DateUtil.COMPLETE_FORMAT_TIME));
        }
        //limit votes
        tweetPollBean.setLimitVotesEnabled(options.getLimitVotes());
        if (options.getLimitVotes() != null && options.getLimitVotes()) {
            tweetPollBean.setLimitVotes(options.getMaxLimitVotes());
        }
        //question
        tweetPollBean.setQuestionBean(new QuestionBean(question));
        log.debug("fillTweetPoll: "+tweetPollBean);
        return tweetPollBean;
    }

    /**
     * @api {post} /api/survey/tweetpoll/publish Publish on Twitter
     * @apiName PostPublishonTwitter
     * @apiGroup Tweetpoll
     * @apiDescription Return all comments that will be filtered by type.
     * @apiParam {Number} id This is the Tweetpoll id that will be published.
     * @apiParam {Number[]} [twitterAccounts] Array list with the Social networks ID in which will be published on tweetpoll.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/publish.json?id=15&twitterAccounts
     * @apiPermission none
     * @apiSuccessExample
     *	{
	  		"error": {

	  		},
	  		"success": {
	    		"socialPublish": [
	      			{
	        			"id": 764,
	        			"tweet_id": "513450987986161664",
				        "textTweeted": "Which instrument sounds most like the human voice answer1 http:\/\/tinyurl.com\/ldcx54j answer2 http:\/\/tinyurl.com\/ofsa2fc #voice #instrument"
				        "date_published": "2015-05-11",
				        "social_account": "test",
				        "status_tweet": "SUCCESS",
				        "status_description_tweet": null,
				        "source_tweet": "TWITTER",
				        "tweet_url": "https:\/\/twitter.com\/#!\/test\/status\/510140987986161664",
				        "social_account_id": 4,
				        "type_item": "TWEETPOLL"
	      			}
	    		]
	  		}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/tweetpoll/publish", method = RequestMethod.POST)
    public @ResponseBody ModelMap publish(
            @RequestParam(value = "id", required = true) final Long tweetPollId,
            @RequestParam(value = "twitterAccounts", required = false) final Long[] twitterAccountsId,
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
                //create notification for each TweetPollSavedPublished
                for (TweetPollSavedPublishedStatus tweetPollSavedPublishedStatus : results) {
                    getTweetPollService().createTweetPollNotification(tweetPollSavedPublishedStatus);
                }

            }
        } catch (Exception e) {
            log.fatal(e);
            //e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/survey/tweetpoll/{propertyType}-tweetpoll.json Update Tweetpoll
     * @apiName GetUpdateTweetpoll
     * @apiGroup Tweetpoll
     * @apiDescription Select one or more of the properties that has one tweetpoll before publication to update.
     * @apiParam {String="change-open-status","resume-live-results","captcha","favourite","live-results","scheduled","notification","repeated"} propertyType Specifies the options or properties available.
     * @apiParam {Number} tweetPollId This is the Tweetpoll unique identifier that will be assigned a property.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/favourite-tweetpoll.json?tweetPollId=991
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     *	{
  			"error": {

  			},
  			"success": {
    			"r": 0
  			}
		}
     * @apiSuccess {Object} success
     * @apiError tweet poll invalid with this id <code>id</code>
     * @apiErrorExample
		{
			"error":{
						"message":"tweet poll invalid with this id 991"
					},
			"success":{

			}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value ="/api/survey/tweetpoll/{propertyType}-tweetpoll.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap changeTweetPollProperties(
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
            } else if ("comment".equals(propertyType)) {
                log.debug("Property Type" + propertyType);
                getTweetPollService().chaneCommentStatusTweetPoll(
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
     * @api {get} /api/short/url/{type}.json Short url
     * @apiName GetShortUrl
     * @apiGroup Tweetpoll
     * @apiDescription Changes a big URL into tiny URL.
     * @apiParam {String="Google","Tinyurl"} type Short URL providers.
     * @apiParam {String} url Site Address to be shortened.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/short/url/tinyurl.json?url=http://www.encuestame.org/
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     * 	{
			error: {

			},
			success: {
				url: "http://tinyurl.com/co6lop9"
			}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/short/url/{type}.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getShortUrl(
            @PathVariable String type,
            @RequestParam(value = "url", required = true) String url,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            url = filterValue(url);
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            if (InternetUtils.validateUrl(url)) {
                if ("google".equals(type)) {
                    jsonResponse.put("url", SocialUtils.getGoGl(url, EnMePlaceHolderConfigurer.getProperty("short.google.key")));
                } else if ("tinyurl".equals(type)){
                    jsonResponse.put("url", SocialUtils.getTinyUrl(url));
                } else if ("yourls".equals(type)){
                    jsonResponse.put("url", SocialUtils.getYourls(url));
                }
                setItemResponse(jsonResponse);
            } else {
                setError("url malformed", response);
            }
        } catch (Exception ex) {
            setError(ex.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {delete} /api/survey/tweetpoll/{id} Remove Tweetpoll
     * @apiName DeleteTweetpoll
     * @apiGroup Tweetpoll
     * @apiDescription Remove Tweetpoll.
     * @apiParam {Number} id Unique Tweetpoll Identifier that will be removed.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/tweetpoll/31
     * @apiPermission none
     */
    @RequestMapping(value = "/api/survey/tweetpoll/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    ModelMap getRemoveTweet(
            @PathVariable Long id,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
        final TweetPoll tpoll = getTweetPollService().getTweetPollById(id);
            getTweetPollService().removeTweetPoll(tpoll);
            setSuccesResponse();
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
    private Boolean repeatedVotes = false;
    private Boolean resumeLiveResults = false;
    private Boolean scheduled  = false;
    private Boolean limitVotes  = false;
    private Boolean captcha  = false;
    private Boolean liveResults  = true;

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
                + ", captcha=" + captcha + ", liveResults=" + liveResults
                + ", maxRepeatedVotes=" + maxRepeatedVotes
                + ", maxLimitVotes=" + maxLimitVotes + ", scheduledDate="
                + scheduledDate + ", scheduledTime=" + scheduledTime + "]";
    }
}