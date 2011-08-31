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
package org.encuestame.utils.web;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.utils.json.QuestionBean;

/**
 * Unit Survey Section.
 * @author Picado, Juan juanATencuestame.org
 * @since June 01 2009 15:21:31
 * @version $Id$
 **/
public class UnitSurveySection{


    private Integer id;
    private String name;
    private Integer stateId;
    private List<QuestionBean> listQuestions = new ArrayList<QuestionBean>();
    private Boolean showPanel = false;

    public UnitSurveySection() {}

    public UnitSurveySection(Integer id, String name, Integer stateId) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
    }

    /**
     * @return the id
     */
    public final Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */

    public final void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */

    public final String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */

    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the stateId
     */

    public final Integer getStateId() {
        return stateId;
    }

    /**
     * @param stateId the stateId to set
     */
    public final void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    /**
     * @return the listQuestions
     */
    public List<QuestionBean> getListQuestions() {
        return listQuestions;
    }

    /**
     * @param listQuestions the listQuestions to set
     */
    public void setListQuestions(List<QuestionBean> listQuestions) {
        this.listQuestions = listQuestions;
    }

    /**
     * @return the showPanel
     */

    public final Boolean getShowPanel() {
        return showPanel;
    }

    /**
     * @param showPanel the showPanel to set
     */
    public final void setShowPanel(Boolean showPanel) {
        this.showPanel = showPanel;
    }


}
