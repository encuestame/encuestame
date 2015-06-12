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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.ConvertDomainToJson;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.ShortUrlProvider;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.web.CreatePollBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.search.PollSearchBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Poll Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 20, 2010 8:16:38 PM
 */
@Controller
public class PollJsonController extends AbstractJsonControllerV1{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     */
    private final Integer POLL_PUBLISH_STRING_LIMIT = 100;



    /**
     * @api {get} /api/survey/poll/search.json Search polls.
     * @apiName GetSearch
     * @apiGroup Poll
     * @apiDescription Return all comments that will be filtered by type.
     * @apiParam {String} typeSearch - XXXX
     * @apiParam {String} [keyword] Keyword to search related polls.
     * @apiParam {Number} [max] Defines the maximum number of search results.
     * @apiParam {Number} [pollFolderId]  Folder id to organize and store the poll.
     * @apiParam {Number} [start] Defines the starting number of the page of results.
     * @apiParam {String[} [social_networks] Filter tweetpolls by networks accounts.
     * @apiParam {Number[ } [social_account_networks] Filter polls by social networks accounts.
     * @apiParam {Boolean} [_published]  Filter by published polls
     * @apiParam {Boolean} [_complete]  Filter by completed polls.
     * @apiParam {Boolean} [_favourite] Filter by favourites tweetpolls.
     * @apiParam {Boolean} [_scheduled] Filter by polls scheduled.
     * @apiParam {Boolean} [_isHidden] Filter by hidden polls.
     * @apiParam {Boolean} [_isPassprotected] - Filter by tweetpolls with pass protected
     * @apiParam {String} [period] Date range to search results.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll/search.json?typeSearch=all&keyword=What&max=1&start=0
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     * {
		    "error": { },
		    "success": {
		        "poll": [
		            {
		                "hashtags": [
		                    {
		                        "id": 11,
		                        "size": 15,
		                        "hashTagName": "internet",
		                        "hits": 1
		                    }
		                ],
		                "is_password_restriction": false,
		                "owner_username": "demo10",
		                "relative_time": null,
		                "total_votes": 36,
		                "hits": 21,
		                "item_type": "poll",
		                "like_votes": 0,
		                "dislike_votes": 0,
		                "create_date": null,
		                "relevance": 11,
		                "favorite": false,
		                "latitude": 0,
		                "longitude": 0,
		                "additional_info": "",
		                "show_comments": "MODERATE",
		                "is_show_results": true,
		                "folder_id": null,
		                "is_show_additional_info": false,
		                "is_close_after_date": false,
		                "close_date": null,
		                "is_close_after_quota": false,
		                "close_quota": null,
		                "is_ip_restricted": null,
		                "ip_restricted": "",
		                "multiple_response": null,
		                "total_comments": 0,
		                "id": 25,
		                "completed_poll": false,
		                "creation_date": "2012-08-18",
		                "question": {
		                    "question_name": "Which Spice Girl did David Beckham marry?",
		                    "slug": "which-spice-girl-did-david-beckham-marry%3F",
		                    "hits": null,
		                    "version": null,
		                    "state_id": null,
		                    "id": 101,
		                    "uid": 10,
		                    "pattern": "SINGLE_SELECTION",
		                    "widget": "encuestame.org.core.commons.questions.patterns.SingleOptionResponse",
		                    "list_answers": [ ]
		                },
		                "finish_date": null,
		                "published": true,
		                "close_notification": true,
		                "show_resultsPoll": true,
		                "updated_date": null,
		                "url": null,
		                "short_url": null,
		                "hastags_string": "internet,computer,business"
		            }
		        ]
		    }

		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll/search.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap searchPolls(
            @RequestParam(value = "typeSearch", required = true) String typeSearch,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "max", required = false) Integer max,
            @RequestParam(value = "pollFolderId", required = false) Long pollFolderId,
            @RequestParam(value = "start", required = false)Integer start,
            @RequestParam(value = "social_networks", required = false)  List<String> socialNetworks,
            @RequestParam(value = "social_account_networks", required = false) List<Long> socialAccountNetworks,
            @RequestParam(value = "_published", required = false) Boolean isPublished,
            @RequestParam(value = "_complete", required = false) Boolean isCompleted,
            @RequestParam(value = "_favourite", required = false) Boolean isFavourite,
            @RequestParam(value = "_scheduled", required = false) Boolean isScheduled,
            @RequestParam(value = "_isHidden", required = false) Boolean isHidden,
            @RequestParam(value = "_isPassprotected", required = false) Boolean isPassprotected,
            @RequestParam(value = "period", required = false) String period,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        final PollSearchBean tpollSearchBean = new PollSearchBean();
        try {
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
            tpollSearchBean.setIsHidden(isHidden == null ? false : isHidden);
            tpollSearchBean.setIsPasswordProtected(isPassprotected == null ? false : isPassprotected);
            final List<SearchBean> list = (List<SearchBean>) getPollService().filterSearchPollsByType(
                    tpollSearchBean, request);
            //log.debug("/api/survey/poll/search.json---------------->  "+ list.size());
            jsonResponse.put("poll", list);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
             log.error(e);
             setError(e.getMessage(), response);
        }
        return returnData();
     }


    /**
     * @api {post} /api/survey/poll/social/publish.json Publish Poll
     * @apiName PostPublishPoll
     * @apiGroup Poll
     * @apiDescription Publish a {@link Poll} on a list of {@link SocialAccount}
     * @apiParam {Number[]} [twitterAccounts] Array list with the Social networks ID in which will be published on poll.
     * @apiParam {Number} id - Unique id number that identifies the poll that will be published.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll/social/publish.json?id=45
     * @apiPermission none
     */
    @RequestMapping(value = "/api/survey/poll/social/publish.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap publishSocialPolls(
             @RequestParam(value = "twitterAccounts", required = false) final Long[] twitterAccountsId,
             @RequestParam(value = "id", required = true) final Long pollId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try{
             final Poll poll = getPollService().getPollById(pollId, getUserPrincipalUsername());
             final List<SocialAccountBean> accountBeans = new ArrayList<SocialAccountBean>();
             //convert accounts id to real social accounts objects.
             for (int row = 0; row < twitterAccountsId.length; row++) {
                 final SocialAccountBean socialAccount = new SocialAccountBean();
                 socialAccount.setAccountId(twitterAccountsId[row]);
                 accountBeans.add(socialAccount);
             }
             //log.trace("Accounts:{" + accountBeans.size());
             String tweetText = poll.getQuestion().getQuestion();
             final String url = WidgetUtil.createShortUrl(ShortUrlProvider.TINYURL, this.buildPollURL(poll, request));
             if (tweetText.length() > this.POLL_PUBLISH_STRING_LIMIT) {
                 tweetText = tweetText.substring(0, this.POLL_PUBLISH_STRING_LIMIT) + " " + url;
             } else {
                 tweetText = tweetText + " " + url;
             }
             //log.trace("poll tweet text length --> " + tweetText.length());
             final List<TweetPollSavedPublishedStatus> results = getTweetPollService().publishMultiplesOnSocialAccounts(
                   accountBeans, null, tweetText ,TypeSearchResult.POLL, poll, null);
             //log.trace("/api/survey/poll/search.json "+jsonResponse);
             jsonResponse.put("socialPublish", ConvertDomainToJson.convertTweetPollStatusToJson(results));
             setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
       }
        return returnData();
    }

    /**
     * Create the url to vote a poll
     * @param poll {@link Poll}
     * @param request {@link HttpServletRequest}
     * @return
     */
    private String buildPollURL(final Poll poll, final HttpServletRequest request) {
        //poll/20/which-superhero-gave-the-avengers-their-name%3F
        final StringBuilder stringBuilder = new StringBuilder(WidgetUtil.getDomain(request));
        stringBuilder.append("/poll/vote/");
        stringBuilder.append(poll.getPollId());
        stringBuilder.append("/");
        stringBuilder.append(poll.getQuestion().getSlugQuestion());
        return stringBuilder.toString();
    }


    /**
     * @api {delete} /api/survey/poll Remove Poll
     * @apiName DeletePoll
     * @apiGroup Poll
     * @apiDescription Remove poll
     * @apiParam {Number} pollId Unique number that identifies the poll that will be removed.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll/remove.json?pollId=27
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     		{
    			"error":{

    			},
    			"success":{
            			"r":0
				}
			}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll", method = RequestMethod.DELETE)
    public @ResponseBody ModelMap deletePoll(
            @RequestParam(value = "pollId", required = true) Long pollId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               log.debug("Poll Id"+ pollId);
               getPollService().removePoll(pollId);
               setSuccesResponse();
          } catch (Exception e) {
              //e.printStackTrace();
              log.error(e);
              setError(e.getMessage(), response);
          }
          return returnData();
      }


    /**
     * @api {get} /api/survey/poll/detail.json Results detail
     * @apiName GetDetailPoll
     * @apiGroup Poll
     * @apiDescription Shows a detail of the results of the votes recorded in the poll.A service to retrieve all info of a poll.
     * @apiParam {Number} id Unique number that identifies the poll that will show its detail.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll/detail.json?id=26
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     *  {
    		"error": {

    		},
    		"success": {
        		"poll": {
            		"poll_results": [
                		{
                    		"answer": {
                        		"answer_id": 255,
                        		"answers": "Bank of America Tower",
                        		"url": null,
                        		"short_url": null,
                        		"qid": 102,
                        		"color": "#0697A5",
                        		"provider": null
                    		},
                    "answer_votes": 52,
                    "percent": "4,92%"
                	},
                	{
                    	"answer": {
                        	"answer_id": 254,
                        	"answers": "One World Trade Center",
                        	"url": null,
                        	"short_url": null,
                        	"qid": 102,
                        	"color": "#D4584B",
                        	"provider": null
                    	},
                    "answer_votes": 47,
                    "percent": "4,45%"
                	}
            	],
			"poll_bean": {
                "hashtags": [
                    {
                        "id": 18,
                        "size": 15,
                        "hashTagName": "online",
                        "hits": 1
                   }
                ],
                "is_password_restriction": false,
                "owner_username": "demo10",
                "relative_time": null,
                "total_votes": 39,
                "hits": 21,
                "item_type": "poll",
                "like_votes": 0,
                "dislike_votes": 0,
                "create_date": null,
                "relevance": 12,
                "favorite": false,
                "latitude": 0,
                "longitude": 0,
                "additional_info": "",
                "show_comments": "MODERATE",
                "is_show_results": true,
                "folder_id": null,
                "is_show_additional_info": false,
                "is_close_after_date": false,
                "close_date": null,
                "is_close_after_quota": false,
                "close_quota": null,
                "is_ip_restricted": null,
                "ip_restricted": "",
                "multiple_response": null,
                "total_comments": 0,
                "id": 26,
                "completed_poll": false,
                "creation_date": "2011-10-09",
                "question": {
                    "question_name": "What's the tallest building in New York City?",
                    "slug": "what%27s-the-tallest-building-in-new-york-city%3F",
                    "hits": null,
                    "version": null,
                    "state_id": null,
                    "id": 102,
                    "uid": 10,
                    "pattern": "SINGLE_SELECTION",
                    "widget": "encuestame.org.core.commons.questions.patterns.SingleOptionResponse",
                    "list_answers": [ ]
                },
                "finish_date": null,
                "published": true,
                "close_notification": true,
                "show_resultsPoll": true,
                "updated_date": null,
                "url": null,
                "short_url": null,
                "hastags_string": "online,health,music"
            },
            "poll_list_answers": [
                {
                    "answer_id": 253,
                    "answers": "Empire State Building",
                    "url": null,
                    "short_url": "googl",
                    "qid": 102,
                    "color": "#5BFD0D",
                    "provider": null
               }
            ]
         }
    }
	}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll/detail.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap retrieveDetail(
            @RequestParam(value = "id", required = true) Long pollId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            try {
                jsonResponse.put("poll", getPollService().getPollDetailInfo(pollId));
            } catch (EnMeNoResultsFoundException e) {
                 log.error(e);
                 setError(e.getMessage(), response);
            }
            setItemResponse(jsonResponse);
        return returnData();
    }

    /**
     * @api {get} /api/survey/poll/searchby-{type}.json Search into Folder
     * @apiName GetPollsintoFolder
     * @apiGroup Poll
     * @apiDescription Search Polls into Folders
     * @apiParam {Number} [pollId] - XXXX
     * @apiParam {String} [keyword] Keyword to search related polls
     * @apiParam {Number} [maxResults] Defines the maximum number of search results.
     * @apiParam {Number} [start] Defines the starting number of the page of results.
     * @apiParam {Number} [folderId] Unique Folder identifier of the element where the search is performed.
     * @apiParam {String} [date] Filter results based on that date
     * @apiParam {String="date","folder","keyword"} [type] Search filters.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll/searchby-folder.json?keyword=What&folderId=1
     * @apiPermission ENCUESTAME_USER,
     * @apiSuccessExample
     * {
		  "error": {

		  },
		  "success": {
		    "poll": [
		      {
		        "hashtags": [
		          {
		            "id": 11,
		            "size": 15,
		            "hashTagName": "internet",
		            "hits": 1
		          },
		          {
		            "id": 10,
		            "size": 15,
		            "hashTagName": "computer",
		            "hits": 1
		          }
		        ],
		        "is_password_restriction": false,
		        "owner_username": "demo10",
		        "relative_time": null,
		        "total_votes": 36,
		        "hits": 21,
		        "item_type": "poll",
		        "like_votes": 0,
		        "dislike_votes": 0,
		        "create_date": null,
		        "relevance": 11,
		        "favorite": false,
		        "latitude": 0,
		        "longitude": 0,
		        "additional_info": "",
		        "show_comments": "MODERATE",
		        "is_show_results": true,
		        "folder_id": null,
		        "is_show_additional_info": false,
		        "is_close_after_date": false,
		        "close_date": null,
		        "is_close_after_quota": false,
		        "close_quota": null,
		        "is_ip_restricted": null,
		        "ip_restricted": "",
		        "multiple_response": null,
		        "total_comments": 0,
		        "id": 25,
		        "completed_poll": false,
		        "creation_date": "2012-08-18",
		        "question": {
		          "question_name": "Which Spice Girl did David Beckham marry?",
		          "slug": "which-spice-girl-did-david-beckham-marry%3F",
		          "hits": null,
		          "version": null,
		          "state_id": null,
		          "id": 101,
		          "uid": 10,
		          "pattern": "SINGLE_SELECTION",
		          "widget": "encuestame.org.core.commons.questions.patterns.SingleOptionResponse",
		          "list_answers": [

		          ]
		        },
		        "finish_date": null,
		        "published": true,
		        "close_notification": true,
		        "show_resultsPoll": true,
		        "updated_date": null,
		        "url": null,
		        "short_url": null,
		        "hastags_string": "internet,computer,business"
		      }
		    ]
		  }
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll/searchby-{type}.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap searchPollByType(
              @RequestParam(value = "pollId", required = false) Long pollId,
              @RequestParam(value = "keyword", required = false) String keyword,
              @RequestParam(value = "maxResults", required = false) Integer maxResults,
              @RequestParam(value = "start", required = false) Integer start,
              @RequestParam(value = "folderId", required = false) Long folderId,
              @RequestParam(value = "date", required = false) String date,
              @PathVariable String type,
              HttpServletRequest request,
              HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
              try {
                log.debug("pollId "+pollId);
                log.debug("keyword "+keyword);
                log.debug("start "+start);
                log.debug("folderId "+folderId);
                log.debug("type "+type);
                  final Map<String, Object> sucess = new HashMap<String, Object>();
                  if ("keyword".equals(type)) {
                      sucess.put("pollsbyKey", getPollService().searchPollByKeyword(keyword, maxResults, start));
                      setItemResponse(sucess);
                  } else if ("folder".equals(type)) {
                     log.debug("Folder Id"+ folderId);
                     sucess.put("pollsByFolder", getPollService().searchPollsByFolder(folderId, getUserPrincipalUsername()));
                     setItemResponse(sucess);
                 } else if("date".equals(type)) {
                    log.debug("search polls by date ---> "+ date);
                    List<PollBean> pbean = getPollService().getPollsbyDate(DateUtil.parseFromDojo(date), maxResults, start);
                    sucess.put("pollsByDate", pbean);
                    setItemResponse(sucess);
                  }
              } catch (Exception e) {
                  log.error(e);
                  setError(e.getMessage(), response);
              }
            return returnData();
        }


     /**
     * @api {post} /api/survey/poll Create Poll
     * @apiName PostCreatePoll
     * @apiGroup Poll
     * @apiDescription Create a Poll.
     * @apiParam {Number} questionName - the question string
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll", method = RequestMethod.POST)
    public @ResponseBody ModelMap createPoll(
            @RequestBody CreatePollBean bean,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               final Map<String, Object> jsonResponse = new HashMap<String, Object>();
               jsonResponse.put("pollBean", ConvertDomainBean.convertPollDomainToBean(getPollService().createPoll(bean)));
               setItemResponse(jsonResponse);
          } catch (Exception e) {
              //e.printStackTrace();
              log.error(e);
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    /**
     * @api {post} /api/survey/poll/{propertyType}-poll.json Update Poll
     * @apiName PostPollActions
     * @apiGroup Poll
     * @apiDescription Update poll properties.
     * @apiParam {String} propertyType {String="change-open-status","change-display-results","password-restrictions","additional-info","notifications","ip-protection","notification","close-after-quota"} propertyType Specifies the options or properties available.
     * @apiParam {Number} pollId This is the Poll unique identifier that will be assigned a property.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/poll/change-open-status-poll.json?pollId=45
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value ="/api/survey/poll/{propertyType}-poll.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap changePollProperties(
            @PathVariable String propertyType,
            @RequestParam(value = "pollId", required = true) Long pollId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            log.debug("Property Type " + propertyType);
            if ("change-open-status".equals(propertyType)) {
                getPollService().changeStatusPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("change-display-results".equals(propertyType)) {
                getPollService().setShowResultsPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("password-restrictions".equals(propertyType)) {
                getPollService().setPasswordRestrictionsPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("additional-info".equals(propertyType)) {
                getPollService().setAdditionalInfoPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("notifications".equals(propertyType)) {
                getPollService().enableNotificationsPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("ip-protection".equals(propertyType)) {
                getPollService().ipProtectionPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("close-after-quota".equals(propertyType)) {
                getPollService().closeAfterQuotaPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else  if ("is-hidden".equals(propertyType)) {
                getPollService().hiddenPoll(
                        pollId, getUserPrincipalUsername());
                setSuccesResponse();
            } else {
                log.warn("type not valid");
                setError("type not valid", response);
            }

        }
        catch (Exception e) {
                log.error(e);
                setError(e.getMessage(), response);
        }
        return returnData();
    }
}