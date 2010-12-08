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
package org.encuestame.mvc.controller.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.utils.web.UnitFolder;
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
 * @version $Id:$
 */
@Controller
public class FolderJsonServiceController extends AbstractJsonController{

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{actionType}/folder/create.json", method = RequestMethod.GET)
    public ModelMap createFolder(
            @PathVariable String actionType,
            @RequestParam(value = "folderName", required = true) String folderName,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               log.debug("FolderName "+folderName);

              final Map<String, Object> sucess = new HashMap<String, Object>();
              if("tweetPoll".equals(actionType)){
                  final UnitFolder f = getTweetPollService().createTweetPollFolder(folderName, getUserPrincipalUsername());
                   sucess.put("folder", f);
               }
               else if("poll".equals(actionType)){
                   final UnitFolder f = getPollService().createPollFolder(folderName, getUserPrincipalUsername());
                   sucess.put("folder", f);
               }
              setItemResponse(sucess);
          } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{actionType}/folder/update.json", method = RequestMethod.GET)
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
                       final UnitFolder f = getTweetPollService().updateTweetPollFolder(folderId, folderName,getUserPrincipalUsername());
                        sucess.put("folder", f);
                    }
                    else if("poll".equals(actionType)){
                        final UnitFolder f = getPollService().updateFolderName(folderId,folderName, getUserPrincipalUsername());
                        sucess.put("folder", f);
                    }
                   setItemResponse(sucess);
               } catch (Exception e) {
                   log.error(e);
                   e.printStackTrace();
                   setError(e.getMessage(), response);
               }
               return returnData();
           }


    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/{actionType}/folder/remove.json", method = RequestMethod.GET)
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
                    }

                    else if("poll".equals(actionType)){
                        getPollService().removePollFolder(folderId);
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
