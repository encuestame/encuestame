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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.utils.json.QuestionBean;
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
    @RequestMapping(value = "api/survey/poll/removePoll.json", method = RequestMethod.GET)
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
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/poll/searchPollby{type}.json", method = RequestMethod.GET)
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
                log.debug("maxResults "+maxResults);
                log.debug("start "+start);
                log.debug("folderId "+folderId);
                log.debug("date "+date);
                log.debug("type "+type);
                  final Map<String, Object> sucess = new HashMap<String, Object>();
                  if("keyword".equals(type)){
                      log.debug("Poll Id"+ pollId);
                      sucess.put("pollsbyKey", getPollService().searchPollByKeyword(keyword, getUserPrincipalUsername(), maxResults, start));
                      setItemResponse(sucess);
                  } else if ("folder".equals(type)) {
                     log.debug("Folder Id"+ folderId);
                     sucess.put("pollsByFolder", getPollService().searchPollsByFolder(folderId, getUserPrincipalUsername()));
                 }
                  else if("date".equals(type)) {
                    log.debug("search polls by date ---> "+ date);
                    sucess.put("pollsByDate", getPollService().getPollsbyDate(getUserPrincipalUsername(), date, maxResults, start));
                    setItemResponse(sucess);
                  }
              } catch (Exception e) {
                  log.error(e);
                  e.printStackTrace();
                  setError(e.getMessage(), response);
              }
            return returnData();
        }


    @PreAuthorize("hasRole('ENCUESTAME_USER')")
      @RequestMapping(value = "api/survey/poll/{actionType}Poll.json", method = RequestMethod.GET)
    public ModelMap createGroup(
            @RequestParam(value = "questionName", required = true) String questionName,
            @RequestParam(value = "listAnswers", required = true) String[] answers,
            @RequestParam(value = "creationDate", required = true) Date creationDate,
            @RequestParam(value = "completedPoll", required = true) Boolean completedPoll,
            @RequestParam(value = "closeNotification", required = true) Boolean closeNotification,
            @RequestParam(value = "hashPoll", required = true) String hashPoll,
            @RequestParam(value = "publishPoll", required = true) Boolean publishPoll,
            @PathVariable String actionType,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               final Map<String, Object> sucess = new HashMap<String, Object>();
               if("create".equals(actionType)){
                   final PollBean unitPoll = new PollBean();
                   final Question question =  getSurveyService().createQuestion(new QuestionBean(questionName));
                   unitPoll.setCreationDate(creationDate);
                   unitPoll.setCompletedPoll(completedPoll);
                   unitPoll.setCloseNotification(closeNotification);
                   unitPoll.setHashPoll(hashPoll);
                   unitPoll.setPublishPoll(publishPoll);
                   getPollService().createPoll(unitPoll, getUserPrincipalUsername(), question);
                   setSuccesResponse();
               }
          } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
          return returnData();
      }
}
