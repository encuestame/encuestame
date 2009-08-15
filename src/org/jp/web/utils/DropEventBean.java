package org.jp.web.utils;

import org.jp.web.beans.survey.BuildSurveyControllerBean;
import org.richfaces.component.Dropzone;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: DropEventBean.java Date: 02/06/2009 18:37:24
 * 
 * @author juanpicado package: org.jp.web.utils
 * @version 1.0
 */

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
