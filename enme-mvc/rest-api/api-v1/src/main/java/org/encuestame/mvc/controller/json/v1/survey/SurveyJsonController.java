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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyResult;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.enums.QuestionPattern;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.UnitSurveySection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Survey Json Controller.
 * @author Morales , Diana Paola paolaATencuestame.org
 * @since October 17, 2011
 * @version $Id:$
 */
@Controller
public class SurveyJsonController extends AbstractJsonControllerV1{

     /**
     * Log.
     */
     private static Log log = LogFactory.getLog(SurveyJsonController.class);

             /**
              * @api {get} /api/survey/search.json Search on Surveys
              * @apiName GetSearchResults
              * @apiGroup Survey
              * @apiDescription Return all comments that will be filtered by type.
              * @apiParam {Number} typeSearch - XXXX
              * @apiParam {Number} [keyword - XXXX
              * @apiParam {Number} [max - XXXX
              * @apiParam {Number} [start - XXXX
              * @apiVersion 1.0.0
              * @apiSampleRequest http://www.encuestame.org/demo/api/survey/search.json
              * @apiPermission ENCUESTAME_USER
              */

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/search.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getListSurveys(
            @RequestParam(value = "typeSearch", required = true) String typeSearch,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "max", required = false)Integer max,
            @RequestParam(value = "start", required = false)Integer start,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();

        try {
            final List<SurveyBean> list = (List<SurveyBean>) getSurveyService()
                    .filterSurveyItemsByType(
                            TypeSearch.getSearchString(typeSearch), keyword,
                            max, start);
            jsonResponse.put("surveys", list);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/survey/sections.json Retrieve sections
     * @apiName GetSections
     * @apiGroup Survey
     * @apiDescription Retrieves a list of the sections created and assigned to a survey with details of your information.
     * @apiParam {Number} [id] Surveys Id to retrieve all their sections.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/sections.json
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
     * {
		    "error": { },
		    "success": {
		        "sections": [
		            {
		                "name": "About me",
		                "id": null,
		                "description": "Personal information about marital status, age, date of birth, etc.",
		                "stateId": null,
		                "listQuestions": [ ],
		                "showPanel": false
		            }
		        ]
		    }
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/sections.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap retrieveSections(
            @RequestParam(value = "id", required = false) Long surveyId,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        List<UnitSurveySection> surveySections = new ArrayList<UnitSurveySection>();
        try {
            if (surveyId == null) {
                log.debug("survey id is missing");
            } else {
                final Survey survey = getSurveyService().getSurveyById(surveyId);
                surveySections = getSurveyService().retrieveSectionsBySurvey(survey);
            }

            jsonResponse.put("sections", surveySections);
            setItemResponse(jsonResponse);
        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/survey/addquestion.json Add Question
     * @apiName getAddQuestion
     * @apiGroup Survey
     * @apiDescription Add {@link Question} to {@link SurveySection}.
     * @apiParam {Number} [ssid - XXXX
     * @apiParam {String} [pattern - XXXX
     * @apiParam {String} [question - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/addquestion.json
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/addquestion.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap addQuestionToSectioSurvey(
            @RequestParam(value = "ssid", required = false) Long sectionId,
            @RequestParam(value = "pattern", required = false) String pattern,
            @RequestParam(value = "question", required = false) String question,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException,
            NoSuchAlgorithmException {

        try {
            if (sectionId == null) {
                log.debug("survey section id is missing");
            } else {
                final SurveySection section = getSurveyService()
                        .retrieveSurveySectionById(sectionId);
                final QuestionPattern questionPattern = QuestionPattern
                        .getQuestionPattern(pattern);
                final UserAccount account = getUserAccount();
                final Question questionAdded = getSurveyService().addQuestionToSurveySection(question,
                        account, section,
                        questionPattern, null);
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                jsonResponse.put("newQuestion", questionAdded);
                setItemResponse(jsonResponse);
            }

        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/survey/save.json Save Survey
     * @apiName GetSaveSurvey
     * @apiGroup Survey
     * @apiDescription Return all comments that will be filtered by type.
     * @apiParam {Number} sid - XXXX
     * @apiParam {Number} question - XXXX
     * @apiParam {Number} answer - XXXX
     * @apiParam {String} [txtResponse - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/survey/save.json
     * @apiPermission none
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/save.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap saveSurveyResult(
            @RequestParam(value = "sid", required = true) Long surveyId,
            @RequestParam(value = "question", required = true) Long questionId,
            @RequestParam(value = "answer", required = true) Long qanswer,
            @RequestParam(value = "txtResponse", required = false) String txtResponse,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException,
            NoSuchAlgorithmException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            if (surveyId == null)  {
                log.debug("survey section id is missing");
            } else {

                final Survey survey = getSurveyService()
                        .getSurveyById(surveyId);
                final QuestionAnswer qAnswer = getSurveyService()
                        .getQuestionAnswerById(qanswer);
                final Question question = getSurveyService().getQuestionById(
                        questionId);
                final SurveyResult surveyResult = getSurveyService()
                        .saveSurveyResult(qAnswer, survey, question,
                                txtResponse);
                jsonResponse.put("surveyResult", surveyResult);
                setItemResponse(jsonResponse);
            }

        } catch (EnMeExpcetion e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

}
