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

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.UnitSurveySection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Survey Json Controller.
 * @author Morales , Diana Paola paolaATencuestame.org
 * @since October 17, 2011
 * @version $Id:$
 */
@Controller
public class SurveyJsonController extends AbstractJsonController{

     /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/search.json", method = RequestMethod.GET)
    public ModelMap getListSurveys(
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
      
    
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/survey/sections.json", method = RequestMethod.GET)
	public ModelMap retrieveSections(
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
}
