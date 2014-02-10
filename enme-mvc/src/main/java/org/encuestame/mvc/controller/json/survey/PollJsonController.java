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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.ConvertDomainToJson;
import org.encuestame.mvc.controller.AbstractJsonController;
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
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.search.PollSearchBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Poll Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 20, 2010 8:16:38 PM
 */
@Controller
public class PollJsonController extends AbstractJsonController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     */
    private final Integer POLL_PUBLISH_STRING_LIMIT = 100;
      

    /**
     * Search polls.
     * @param typeSearch
     * @param keyword
     * @param max
     * @param start
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
            @RequestParam(value = "period", required = false) String period,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        final PollSearchBean tpollSearchBean = new PollSearchBean();
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
            final List<SearchBean> list = (List<SearchBean>) getPollService().filterSearchPollsByType(
                    tpollSearchBean, request);
            log.debug("/api/survey/poll/search.json---------------->  "+ list.size());
            jsonResponse.put("poll", list);
            setItemResponse(jsonResponse);

        } catch (EnMeExpcetion e) {
             log.error(e);
             setError(e.getMessage(), response);
        }     
        return returnData();
     }

    /**
     * Publish a {@link Poll} on a list of {@link SocialAccount}
     * @param twitterAccountsId
     * @param pollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
             log.debug("Accounts:{" + accountBeans.size());
             String tweetText = poll.getQuestion().getQuestion();
             final String url = WidgetUtil.createShortUrl(ShortUrlProvider.TINYURL, this.buildPollURL(poll, request));
             if (tweetText.length() > this.POLL_PUBLISH_STRING_LIMIT) {
                 tweetText = tweetText.substring(0, this.POLL_PUBLISH_STRING_LIMIT) + " " + url;
             } else {
                 tweetText = tweetText + " " + url;
             }
             log.debug("poll tweet text length --> " + tweetText.length());
             final List<TweetPollSavedPublishedStatus> results = getTweetPollService().publishMultiplesOnSocialAccounts(
                   accountBeans, null, tweetText ,TypeSearchResult.POLL, poll, null);
             log.debug("/api/survey/poll/search.json "+jsonResponse);
             jsonResponse.put("socialPublish", ConvertDomainToJson.convertTweetPollStatusToJson(results));
             setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
       }
        return returnData();
    }

    /**
     *
     * @param poll
     * @param request
     * @return
     */
    private String buildPollURL(final Poll poll, final HttpServletRequest request) {
        //poll/20/which-superhero-gave-the-avengers-their-name%3F
        final StringBuilder stringBuilder = new StringBuilder(WidgetUtil.getDomain(request));
        stringBuilder.append("/poll/");
        stringBuilder.append(poll.getPollId());
        stringBuilder.append("/");
        stringBuilder.append(poll.getQuestion().getSlugQuestion());
        return stringBuilder.toString();
    }

    /**
     * Remove Poll.
     * @param pollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
              e.printStackTrace();
              log.error(e);
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    /**
     * A service to retrieve all info of a poll.
     * @param pollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
     *
     * @param pollId
     * @param keyword
     * @param maxResults
     * @param start
     * @param folderId
     * @param date
     * @param type
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
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
     *
     * @param questionName
     * @param answers
     * @param showResults
     * @param showComments
     * @param notification
     * @param limitVote
     * @param closeAfter
     * @param blockIp
     * @param actionType
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll", method = RequestMethod.POST)
    public @ResponseBody ModelMap createGroup(
            @RequestParam(value = "questionName", required = true) String questionName,
            @RequestParam(value = "listAnswers", required = true) String[] answers,
            @RequestParam(value = "showResults", required = false) Boolean showResults,//FIXME: replaced by results, please remove
            @RequestParam(value = "results", required = false) String results,//FIXME: this value doesn't exist
            @RequestParam(value = "showComments", required = false) String showComments,
            @RequestParam(value = "notification", required = false) Boolean notification,
            @RequestParam(value = "limitVote", required = false) Boolean limitVote,
            @RequestParam(value = "closeAfter", required = false) Boolean closeAfter,
            @RequestParam(value = "blockIp", required = false) Boolean blockIp,
            @RequestParam(value = "hashtags", required = false) String[] hashtags,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               final Map<String, Object> jsonResponse = new HashMap<String, Object>();
               final List<HashTagBean> tagsBean = createHashTagBeansList(hashtags == null ? new String[]{} : hashtags);
               //FIXME: must be replaced by single bean
               final Poll poll = getPollService().createPoll(
            		   			 questionName, 
            		   			 answers, 
            		   			 results,
                                 showComments, 
                                 notification, 
                                 tagsBean);
               final PollBean pollBean = ConvertDomainBean.convertPollDomainToBean(poll);
               jsonResponse.put("pollBean", pollBean);
               setItemResponse(jsonResponse);
               getPollService().createPollNotification(poll);
          } catch (Exception e) {
              e.printStackTrace();
              log.error(e);
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    /**
     *
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
    @RequestMapping(value ="/api/survey/poll/{propertyType}-poll.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap changeTweetPollProperties(
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