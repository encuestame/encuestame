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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Folder Json Service Controller.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since December 07, 2010
 */
@Controller
public class FolderJsonServiceController extends AbstractJsonController{

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Create Folder for Survey/TweetPoll/Poll.
     * @param actionType Survey/TweetPoll/Poll
     * @param folderName name
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/create.json", method = RequestMethod.GET)
    public ModelMap createFolder(
            @PathVariable String actionType,
            @RequestParam(value = "n", required = true) String folderName,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               log.debug("FolderName "+folderName);
              final Map<String, Object> sucess = new HashMap<String, Object>();
              if("tweetPoll".equals(actionType)){
                   sucess.put("folder", getTweetPollService().createTweetPollFolder(folderName,
                                  getUserPrincipalUsername()));
                   setItemResponse(sucess);
               } else if("poll".equals(actionType)){
                   sucess.put("folder", getPollService().createPollFolder(folderName, getUserPrincipalUsername()));
                   setItemResponse(sucess);
               } else if("survey".equals(actionType)){
                   sucess.put("folder", getSurveyService().createSurveyFolder(folderName, getUserPrincipalUsername()));
                   setItemResponse(sucess);
               } else {
                   setError("operation not valid", response); //if type no exist.
               }
          } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    /**
     *
     * @param actionType
     * @param folderName
     * @param folderId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/update.json", method = RequestMethod.GET)
    public ModelMap updateFolder(
            @PathVariable String actionType,
            @RequestParam(value = "folderName", required = true) String folderName,
            @RequestParam(value = "folderId", required = true) Long folderId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
                try {
                    log.debug("FolderName "+folderName);
                   final Map<String, Object> sucess = new HashMap<String, Object>();
                   if("tweetPoll".equals(actionType)){
                        sucess.put("folder", getTweetPollService().updateTweetPollFolder(folderId,
                                             folderName,getUserPrincipalUsername()));
                        setItemResponse(sucess);
                    } else if("poll".equals(actionType)){
                        sucess.put("folder", getPollService().updateFolderName(
                                   folderId, folderName, getUserPrincipalUsername()));
                        setItemResponse(sucess);
                    } else if("survey".equals(actionType)){
                        sucess.put("folder", getSurveyService().updateSurveyFolder(folderId, folderName, getUserPrincipalUsername()));
                        setItemResponse(sucess);
                    } else {
                        setError("operation not valid", response); //if type no exist.
                    }
               } catch (Exception e) {
                   log.error(e);
                   e.printStackTrace();
                   setError(e.getMessage(), response);
               }
               return returnData();
           }


    /**
     * Remove Folder.
     * @param actionType
     * @param folderId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/remove.json", method = RequestMethod.GET)
    public ModelMap removeFolder(
            @PathVariable String actionType,
            @RequestParam(value = "folderId", required = true) Long folderId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
                try {
                    log.debug("RemoveFolderId "+ folderId);
                    if("tweetPoll".equals(actionType)){
                        getTweetPollService().deleteTweetPollFolder(folderId);
                        setSuccesResponse();
                    } else if("poll".equals(actionType)){
                        getPollService().removePollFolder(folderId);
                        setSuccesResponse();
                    } else if("survey".equals(actionType)){
                        getSurveyService().deleteSurveyFolder(folderId);
                           setSuccesResponse();
                    } else {
                        setError("operation not valid", response); //if type no exist.
                    }
               } catch (Exception e) {
                   log.error(e);
                   e.printStackTrace();
                   setError(e.getMessage(), response);
               }
               return returnData();
           }

    /**
     * Movte Item to another
     * @param actionType
     * @param folderId
     * @param UserId
     * @param tweetPollId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/move.json", method = RequestMethod.GET)
    public ModelMap addToFolder(
             @PathVariable String actionType,
             @RequestParam(value = "folderId", required = true) Long folderId,
             @RequestParam(value = "itemId", required = true) Long itemId,
             HttpServletRequest request,
             HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
              try {
                 /*
                  * One TweetPoll/Survey/Poll ONLY should be in ONE FOLDER. ONLY ONE ! So if
                  * itemId (poll) == 2 is assigned to folder "A" and user drag and drop to the other
                  * folder, the current relationship should be removed and create new realtionship.
                  */
                 if("tweetPoll".equals(actionType)){
                     getTweetPollService().addTweetPollToFolder(folderId, getUserPrincipalUsername(), itemId);
                     setSuccesResponse();
                 } else if("poll".equals(actionType)){
                     getPollService().addPollToFolder(folderId,  getUserPrincipalUsername(), itemId);
                     setSuccesResponse();
                 } else if("survey".equals(actionType)){
                     getSurveyService().addSurveyToFolder(folderId, getUserPrincipalUsername(), itemId);
                     setSuccesResponse();
                 } else {
                     //set error
                     setError("type of folder invalid :{"+actionType, response);
                     log.info("type of folder invalid:{"+actionType);
                 }
            } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
            }
            return returnData();
    }

    /**
     * Retrieve a List of Folders.
     * @param actionType
     * @param folderId
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/list.json", method = RequestMethod.GET)
    public ModelMap retrieveItemsbyFolder(
             @PathVariable String actionType,
             HttpServletRequest request,
             HttpServletResponse response) {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            log.debug("type:{ "+actionType);
            try {
                if ("poll".equals(actionType)) {
                    //FolderBean fbean = new FolderBean(folderId);
                    //list =  getPollService().getPollsByFolder(fbean, getUserPrincipalUsername());
                    //jsonResponse.put("folders", list);
                    //setItemResponse(jsonResponse);
                    jsonResponse.put("folders", ListUtils.EMPTY_LIST);
                } else if("tweetpoll".equals(actionType)) {
                    jsonResponse.put("folders", getTweetPollService().getFolders());
                } else if ("survey".equals(actionType)) {
                    jsonResponse.put("folders", ListUtils.EMPTY_LIST);
                }
                setItemResponse(jsonResponse);
            } catch (Exception e) {
               log.error(e);
               e.printStackTrace();
               setError(e.getMessage(), response);
            }
        return returnData();
    }
}