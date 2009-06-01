package org.jp.web.beans.survey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jp.web.beans.MasterBean;


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
 * Id: SurveyFormatBean.java Date: 31/05/2009 22:43:57
 * 
 * @author juanpicado package: org.jp.web.beans.survey
 * @version 1.0
 */
public class SurveyFormatBean extends MasterBean {

	List<String> lista = new ArrayList<String>();
	private String surveyName; 

	public SurveyFormatBean() {
		lista.add("JotaDeveloper");
		lista.add("SwordFishCode");
		lista.add("Juan Ortega");
		lista.add("CNN Noticiero");
		lista.add("Adobe Photoshop");
		lista.add("Adobe Photoshop");
	}

	public List<String> autocompletar(Object suggest) {
		String pref = (String) suggest;
		ArrayList<String> result = new ArrayList<String>();

		Iterator<String> iterator = lista.iterator();
		while (iterator.hasNext()) {
			String elem = ((String) iterator.next());
			if ((elem != null && elem.toLowerCase().indexOf(pref.toLowerCase()) == 0)
					|| "".equals(pref)) {
				result.add(elem);
			}
		}
		return result;

	}

	/**
	 * @return the surveyName
	 */
	public String getSurveyName() {
		return surveyName;
	}

	/**
	 * @param surveyName the surveyName to set
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	
	
}
