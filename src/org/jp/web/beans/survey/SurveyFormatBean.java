package org.jp.web.beans.survey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.pojo.SurveyFormat;
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

	Collection<SurveyFormat> lista = new ArrayList<SurveyFormat>();
	Collection<UnitSurveySection> sections = new LinkedList<UnitSurveySection>();
	private String surveyName;
	private Log log = LogFactory.getLog(this.getClass());
	private Boolean listSuggest = false;

	public SurveyFormatBean() {

	}


	/**
	 * 
	 * @return
	 */
	public List<SurveyFormat> suggestSurveysNames() {
		ArrayList<SurveyFormat> result = new ArrayList<SurveyFormat>();
		try {
			String pref = getSurveyName();
			loadSurveySuggestion();
			log.info("before loadSurveySuggestion->");
			Iterator<SurveyFormat> iterator = lista.iterator();
			while (iterator.hasNext()) {
				SurveyFormat elem = ((SurveyFormat) iterator.next());
				if ((elem.getName() != null && elem.getName().toLowerCase()
						.indexOf(pref.toLowerCase()) == 0)
						|| "".equals(pref)) {
					result.add(elem);
				}
			}

			log.info("suggest bolean->" + getListSuggest());

		} catch (Exception e) {
			log.info("Exception->" + e);
			addErrorMessage("Error autocomplentar->" + e.getMessage(), e
					.getMessage());
		}
		return result;
	}

	/**
	 * 
	 */
	private void loadSurveySuggestion() {
		log.info("loadSurveySuggestion-->" + getSurveyName());
		if (getSurveyName() != null && !getSurveyName().trim().isEmpty()) {
			lista = getServicemanagerBean().getSurveyService()
					.getSurveyDaoImp().searchSurveyByName(
							getSurveyName().trim());
			if (lista.size() > 0)
				setListSuggest(true);
			else
				setListSuggest(false);
		} else {
			setListSuggest(false);
			log.info("getSurveyName empty->" + getSurveyName());
		}
	}

	/**
	 * @return the surveyName
	 */
	public String getSurveyName() {
		return surveyName;
	}

	/**
	 * @param surveyName
	 *            the surveyName to set
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName.trim();
	}

	/**
	 * @return the lista
	 */
	public Collection<SurveyFormat> getLista() {
		log.info("lista->" + lista.size());
		return lista;
	}

	/**
	 * @param lista
	 *            the lista to set
	 */
	public void setLista(Collection<SurveyFormat> lista) {
		this.lista = lista;
	}

	/**
	 * @return the listSuggest
	 */
	public Boolean getListSuggest() {
		return listSuggest;
	}

	/**
	 * @param listSuggest
	 *            the listSuggest to set
	 */
	public void setListSuggest(Boolean listSuggest) {
		this.listSuggest = listSuggest;
	}

}
