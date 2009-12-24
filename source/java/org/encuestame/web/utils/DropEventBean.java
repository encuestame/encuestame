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
package org.encuestame.web.utils;

import org.encuestame.web.beans.survey.BuildSurveyControllerBean;
import org.richfaces.component.Dropzone;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;
/**
 * Drop Event Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since   02/06/2009 18:37:24
 * @version $Id$
 **/
public class DropEventBean implements DropListener {

    public BuildSurveyControllerBean buildSurvey;

    /**
     * Evento de arrastre para mover una pregunta a una sección
     * @author juanpicado
     */
    public void processDrop(DropEvent dropEvent) {
        //Obtiene  la sección a la cual fue movido o la que escuho el movimieto.
        Dropzone dropzone = (Dropzone) dropEvent.getComponent();
        //Mueve pregunta a sección, dropEvent recoge el objeto pregunta
        buildSurvey.moveQuestion(dropEvent.getDragValue(), dropzone
                .getDropValue());
    }

    /**
     * @return the buildSurvey
     */
    public BuildSurveyControllerBean getBuildSurvey() {
        return buildSurvey;
    }

    /**
     * @param buildSurvey
     *            the buildSurvey to set
     */
    public void setBuildSurvey(BuildSurveyControllerBean buildSurvey) {
        this.buildSurvey = buildSurvey;
    }

}
