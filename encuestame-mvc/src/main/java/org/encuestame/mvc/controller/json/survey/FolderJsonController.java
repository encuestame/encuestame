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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Folder Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 20, 2010 8:27:19 PM
 * @version $Id:$
 */
@Controller
public class FolderJsonController extends AbstractJsonController {

    /**
     * Create Folder.
     * @param type
     * @param name
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{type}/create.json", method = RequestMethod.GET)
    public ModelMap createFolder(
            @PathVariable String type,
            @RequestParam(value = "name", required = true) String name,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        if("tweetPoll".equals(type)){
            //create tweetpooll.
        } else if("poll".equals(type)){
            //create poll
        } else if("survey".equals(type)){
            //create survey folder
        } else {
            //set error
        }
        //TODO: return json with new folder data.
        return returnData();
     }


    /**
     * Move Item to other Folder.
     * @param type
     * @param itemId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{type}/move.json", method = RequestMethod.GET)
    public ModelMap addItemToFolder(
            @PathVariable String type,
            @RequestParam(value = "itemId", required = true) Long itemId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        /*
         * One TweetPoll/Survey/Poll ONLY should be in ONE FOLDER. ONLY ONE ! So if
         * itemId (poll) == 2 is assigned to folder "A" and user drag and drop to the other
         * folder, the current relationship should be removed and create new realtionship.
         */
        if("tweetPoll".equals(type)){
            //add tweetpol to folder. itemId == tweetPollId
        } else if("poll".equals(type)){
            //add poll to folder. itemId == pollId
        } else if("survey".equals(type)){
            //add survey to folder. itemId == sid
        } else {
            //set error
        }
        //TODO: return json success response.
        return returnData();
     }


    /**
     * Remove Folder.
     * @param type
     * @param folderId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{type}/remove.json", method = RequestMethod.GET)
    public ModelMap remoFolder(
            @PathVariable String type,
            @RequestParam(value = "folderId", required = true) Long folderId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        /*
         * If user remove folder, all relationships should be removed in cascade.
         */
        if("tweetPoll".equals(type)){
            // remove tweetPoll Folder
        } else if("poll".equals(type)){
            // remove poll Folder
        } else if("survey".equals(type)){
            // remove survey Folder
        } else {
            //set error
        }
        //TODO: return json success response.
        return returnData();
     }

}
