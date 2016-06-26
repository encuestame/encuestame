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
package org.encuestame.rest.api.v1.survey;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.util.exception.EnMeException;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.SurveyBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Folder Json Service Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since December 07, 2010
 */
@Controller
public class FolderJsonServiceController extends AbstractJsonControllerV1{

    private static Log log = LogFactory.getLog(FolderJsonServiceController.class);

            /**
             * @api {post} /api/survey/folder/{actionType} Create Folder
             * @apiName GetCreateFolder
             * @apiGroup Folder
             * @apiDescription Create Folder to store and organize Tweetpolls, Polls or Surveys.. You must be registered and logged.
             * @apiParam {String="tweetpoll","poll","survey"} actionType Specifies the folder type and the type of items to be stored.
             * @apiParam {String} name This is the name of the new resource. The folder name.
             * @apiVersion 1.0.0
             * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll
             * @apiPermission ENCUESTAME_USER
             * @apiSuccessExample
            {
            "error":{},
            "success":{
            "folder":{
            "id": 382,
            "name": folderTestName,
            "create_date": 1347281933873
            }
            }
            }
             */

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}", method = RequestMethod.POST)
    public @ResponseBody ModelMap createFolder(
            @PathVariable String actionType,
            @RequestParam(value = "name", required = true) String folderName,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               log.debug("FolderName "+folderName);
              final Map<String, Object> sucess = new HashMap<String, Object>();
              if("tweetpoll".equals(actionType)){
                   sucess.put("folder", getTweetPollService().createTweetPollFolder(folderName,
                                  getUserPrincipalUsername()));
                   setItemResponse(sucess);
               } else if("poll".equals(actionType)){
                   sucess.put("folder", getPollService().createPollFolder(folderName));
                   setItemResponse(sucess);
               } else if("survey".equals(actionType)){
                   sucess.put("folder", getSurveyService().createSurveyFolder(folderName, getUserPrincipalUsername()));
                   setItemResponse(sucess);
               } else {
                   setError("operation not valid", response); //if type no exist.
               }
          } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
          }
          return returnData();
      }


    /**
     * @api {put} /api/survey/folder/{actionType} Update folder
     * @apiName GetUpdateFolder
     * @apiGroup Folder
     * @apiDescription Update Folder properties
     * @apiParam {String} actionType Specifies the folder type and the type of items that has been stored.
     * @apiParam {String} folderName This is the name of the resource to update. The folder name.
     * @apiParam {Number} folderId   This is the id(unique identifier ) of the folder that will be updated.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
	{
       "error":{},
        "success":{
            "folder":{
                "id":6,
                "name":"MyFolder",
                "create_date": "2015-04-29"
            }
        }
     }
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}", method = RequestMethod.PUT)
    public @ResponseBody ModelMap updateFolder(
            @PathVariable String actionType,
            @RequestParam(value = "folderName", required = true) String folderName,
            @RequestParam(value = "folderId", required = true) Long folderId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
                try {
                    log.debug("FolderName "+folderName);
                   final Map<String, Object> sucess = new HashMap<String, Object>();
                   if("tweetpoll".equals(actionType)){
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
                   setError(e.getMessage(), response);
               }
               return returnData();
           }

    /**
     * @api {delete} /api/survey/folder/{actionType} Remove Folder
     * @apiName GetRemoveFolder
     * @apiGroup Folder
     * @apiDescription Destroys the folder specified by the required ID parameter.
     * @apiParam {String ="tweetpoll", "poll", "survey"} actionType Specifies the folder type and the type of items that has been stored.
     * @apiParam {Number} folderId Unique number that identifies the  folder that will be removed.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     		{
				"error":{

				},
				"success":{
                	"r":0
              	}
			}
		@apiSuccess {r} Default answer to define a satisfactory response.
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}", method = RequestMethod.DELETE)
    public @ResponseBody ModelMap removeFolder(
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
                   setError(e.getMessage(), response);
               }
               return returnData();
           }


    /**
     * @api {put} /api/survey/folder/{actionType}/move Move Contents Folder
     * @apiName GetMoveContents
     * @apiGroup Folder
     * @apiDescription  Move Contents(Item) Folder to another.
     * @apiParam {String ="tweetpoll", "poll", "survey"} actionType Specifies the folder type.
     * @apiParam {Number} folderId This is the id(unique identifier) of the folder that will be assigned an element
     * @apiParam {Number} itemId Id of the item will be moved to a folder.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll/move
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     		{
				"error":{

				},
				"success":{
                	"r":0
              	}
			}
		@apiSuccess {r} Default answer to define a satisfactory response.
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/move", method = RequestMethod.PUT)
    public @ResponseBody ModelMap addToFolder(
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
                 if("tweetpoll".equals(actionType)){
                     getTweetPollService().addTweetPollToFolder(folderId, getUserPrincipalUsername(), itemId);
                     setSuccesResponse();
                 } else if("poll".equals(actionType)){
                     getPollService().addPollToFolder(folderId, itemId);
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
            setError(e.getMessage(), response);
            }
            return returnData();
    }


    /**
     * @api {get} /api/survey/folder/{actionType}/list Get Folders
     * @apiName GetFoldersList
     * @apiGroup Folder
     * @apiDescription Return a list with all folders created for every user.
     * @apiParam {String ="tweetpoll","poll","survey"} actionType Specifies the folder type and the type of items that will be stored.
     * @apiParam {Boolean} [store] Indicates whether to save the folder.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll/list
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
			{
				"error":{

				},
 				"success":{
            			"folders":[{
							"id":381,
							"name":"test",
							"create_date":"2012-09-10"
							}
	                     ]
            	}
			}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/list", method = RequestMethod.GET)
    public @ResponseBody ModelMap retrieveItemsbyFolder(
             @PathVariable String actionType,
             @RequestParam(value = "store", required = false) Boolean store,
             HttpServletRequest request,
             HttpServletResponse response) {
             store = store == null ? false : store;
             log.debug("type:{ "+actionType);
             try {
                if ("poll".equals(actionType)) {
                    if(store){
                        setItemReadStoreResponse("name","id", getPollService().retrieveFolderPoll());
                    } else {
                        setSingleResponse("folders", getPollService().retrieveFolderPoll());
                    }
                } else if("tweetpoll".equals(actionType)) {
                    if(store){
                        setItemReadStoreResponse("name", "id", getTweetPollService().getFolders());
                    } else {
                        setSingleResponse("folders", getTweetPollService().getFolders());
                    }
                } else if ("survey".equals(actionType)) {
                    setSingleResponse("folders", getSurveyService().getFolders());
                }
            } catch (Exception e) {
               log.error(e);
               setError(e.getMessage(), response);
            }
        return returnData();
    }


    /**
     * @api {get} /api/survey/folder/{actionType}/items Search into Folders
     * @apiName GetSearchResults
     * @apiGroup Folder
     * @apiDescription List of all the elements added and stored in a folder object.
     * @apiParam {String ="tweetpoll","poll", "survey"} actionType Specifies the folder type and its contents.
     * @apiParam {Number} [folderId] The ID of the folder  to retrieve its contents.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll/items?folderId=382
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
			{
				"error":{

				},
 				"success":{
            			"folders":[{
								"id":51,
								"name":"folder__0_",
                        		"create_date":"2012-09-06"
							},
							{
                        		"id":52,
                        		"name":"folder__1_",
                        		"create_date":"2012-09-06"
							}
                    	]
        	}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/folder/{actionType}/items", method = RequestMethod.GET)
    public @ResponseBody ModelMap retrieveItemListbyFolder(@PathVariable String actionType,
                                                           @RequestParam(value = "folderId", required = false) Long folderId,
                                                           HttpServletRequest request, HttpServletResponse response) {
        //log.debug("type:{ " + actionType);
        logPrint(actionType);
        logPrint(folderId);
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        TypeSearchResult type = TypeSearchResult.getTypeSearchResult(actionType);
        try {
            logPrint(type);
            if (type.equals(TypeSearchResult.POLL)) {
                final List<SearchBean> pollsByFolder = getPollService()
                        .searchPollsByFolder(folderId, getUserPrincipalUsername());
                jsonResponse.put("pollsByFolder", pollsByFolder);
                //logPrint("jota::"+pollsByFolder.size());
            } else if (type.equals(TypeSearchResult.TWEETPOLL)) {
                final List<TweetPollBean> tweetPollsByFolder = getTweetPollService()
                        .searchTweetPollsByFolder(folderId, getUserPrincipalUsername());
                jsonResponse.put("tweetPollsByFolder", tweetPollsByFolder);
                //logPrint("jota2::"+tweetPollsByFolder.size());
            } else if (type.equals(TypeSearchResult.SURVEY)) {
                final List<SurveyBean> surveyBeanList = new ArrayList<SurveyBean>();
                final List<Survey> surveysByFolder = getSurveyService().retrieveSurveyByFolder(
                        getUserAccount().getUid(),
                        folderId);
                surveyBeanList.addAll(ConvertDomainBean
                        .convertListSurveyToBean(surveysByFolder));
                jsonResponse.put("surveysByFolder", surveyBeanList);
            } else {
                jsonResponse.put("surveysByFolder", ListUtils.EMPTY_LIST);
                jsonResponse.put("TweetPollsByFolder", ListUtils.EMPTY_LIST);
                jsonResponse.put("PollsByFolder", ListUtils.EMPTY_LIST);
                throw new EnMeException("type not valid");
            }
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            //e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * @api {get} /api/survey/folder/{actionType}/search/keyword Search by keyword
     * @apiName GetSearchbyKeyword
     * @apiGroup Folder
     * @apiDescription Return search results by keyword of all the elements in a folder object.
     * @apiParam {String ="tweetpoll","poll", "survey"} actionType Specifies the folder type and its contents.
     * @apiParam {String} keyword Keyword to suggest a list of results.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/folder/tweetpoll/search/keyword?keyword="My"
     * @apiPermission ENCUESTAME_USER
     */
	@PreAuthorize("hasRole('ENCUESTAME_USER')")
	@RequestMapping(value = "/api/survey/folder/{actionType}/search/keyword", method = RequestMethod.GET)
	public @ResponseBody ModelMap retrieveItemsbyKeyword(
			@PathVariable String actionType,
			@RequestParam(value = "keyword", required = true) String keyword,
			HttpServletRequest request, HttpServletResponse response) {

		final Map<String, Object> jsonResponse = new HashMap<String, Object>();
		 final TypeSearchResult typ = TypeSearchResult.getTypeSearchResult(actionType);
			if (keyword != null) {
		try {

				if (typ.equals(TypeSearchResult.TWEETPOLL)) {
					jsonResponse.put("folders", getTweetPollService()
							.retrieveFoldersbyKeyword( keyword));
					setItemResponse(jsonResponse);
				} else if (typ.equals(TypeSearchResult.POLL)) {
					jsonResponse.put("folders", getPollService()
							.retrieveFoldersbyKeyword(keyword));

				} else if (typ.equals(TypeSearchResult.SURVEY)) {
					// TODO: Missing service to retrieve surveys folder
				}

		} catch (Exception e) {
			log.error(e);
			setError(e.getMessage(), response);
		}
		}
		return returnData();
	}
}