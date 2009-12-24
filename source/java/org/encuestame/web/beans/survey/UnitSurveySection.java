/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.web.beans.MasterBean;
/**
 * Unit Survey Section.
 * @author Picado, Juan juan@encuestame.org
 * @since 01/06/2009 15:21:31
 * @version $Id$
 **/
public class UnitSurveySection extends MasterBean  implements Serializable{



    private static final long serialVersionUID = 3516611324140033097L;
    private Integer id;
    private String name;
    private Integer stateId;
    private Collection<UnitQuestionBean> questions = new ArrayList<UnitQuestionBean>();
    private Log log = LogFactory.getLog(this.getClass());
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
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the stateId
     */

    public Integer getStateId() {
        return stateId;
    }
    /**
     * @param stateId the stateId to set
     */
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    /**
     * @return the questions
     */

    public Collection<UnitQuestionBean> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */

    public void setQuestions(Collection<UnitQuestionBean> questions) {
        this.questions = questions;
    }

    /**
     * @return the showPanel
     */

    public Boolean getShowPanel() {
        return showPanel;
    }

    /**
     * @param showPanel the showPanel to set
     */
    public void setShowPanel(Boolean showPanel) {
        this.showPanel = showPanel;
    }


}
