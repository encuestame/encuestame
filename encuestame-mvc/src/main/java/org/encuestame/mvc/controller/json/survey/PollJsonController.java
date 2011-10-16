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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.utils.web.PollBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Poll Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 20, 2010 8:16:38 PM
 * @version $Id:$
 */
@Controller
public class PollJsonController extends AbstractJsonController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

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
    public ModelMap searchPolls(
            @RequestParam(value = "typeSearch", required = true) String typeSearch,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "max", required = false) Integer max,
            @RequestParam(value = "pollFolderId", required = false) Long pollFolderId,
            @RequestParam(value = "start", required = false)Integer start,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {

        return returnData();
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
    @RequestMapping(value = "/api/survey/poll/remove.json", method = RequestMethod.GET)
    public ModelMap deleteGroup(
            @RequestParam(value = "pollId", required = true) Long pollId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               log.debug("Poll Id"+ pollId);
               getPollService().removePollFolder(pollId);
               setSuccesResponse();
          } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
          }
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
    public ModelMap countUsersByGroup(
              @RequestParam(value = "pollId", required = false) Long pollId,
              @RequestParam(value = "keyword", required = false) String keyword,
              @RequestParam(value = "maxResults", required = false) Integer maxResults,
              @RequestParam(value = "start", required = false) Integer start,
              @RequestParam(value = "folderId", required = false) Long folderId,
              @RequestParam(value = "date", required = false) Date date,
              @PathVariable String type,
              HttpServletRequest request,
              HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
              try {
                log.debug("pollId "+pollId);
                log.debug("keyword "+keyword);
                log.debug("start "+start);
                log.debug("folderId "+folderId);
                //System.out.println("date "+date);
                log.debug("type "+type);
                  final Map<String, Object> sucess = new HashMap<String, Object>();
                  if("keyword".equals(type)){
                      log.debug("Poll Id"+ pollId);
                      sucess.put("pollsbyKey", getPollService().searchPollByKeyword(keyword, maxResults, start));
                      setItemResponse(sucess);
                  } else if ("folder".equals(type)) {
                     log.debug("Folder Id"+ folderId);
                     sucess.put("pollsByFolder", getPollService().searchPollsByFolder(folderId, getUserPrincipalUsername()));
                 }
                  else if("date".equals(type)) {
                    log.debug("search polls by date ---> "+ date);
                    List<PollBean> pbean = getPollService().getPollsbyDate(date, maxResults, start);
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
    @RequestMapping(value = "/api/survey/poll/{actionType}.json", method = RequestMethod.POST)
    public ModelMap createGroup(
            @RequestParam(value = "questionName", required = true) String questionName,
            @RequestParam(value = "listAnswers", required = true) String[] answers,
            @RequestParam(value = "showResults", required = false) Boolean showResults,
            @RequestParam(value = "showComments", required = false) String showComments,
            @RequestParam(value = "notification", required = false) Boolean notification,
            @RequestParam(value = "limitVote", required = false) Boolean limitVote,
            @RequestParam(value = "closeAfter", required = false) Boolean closeAfter,
            @RequestParam(value = "blockIp", required = false) Boolean blockIp,

            @PathVariable String actionType,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               final Map<String, Object> jsonResponse = new HashMap<String, Object>();
               if ("create".equals(actionType)) {
                   final Poll poll = getPollService().createPoll(questionName, answers, showResults,
                                     showComments, notification);
                   final PollBean pollBean = ConvertDomainBean.convertPollDomainToBean(poll);
                   jsonResponse.put("pollBean", pollBean);
                   setItemResponse(jsonResponse);
                   getPollService().createPollNotification(poll);
               }
          } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
          }
          return returnData();
      }
}