package org.jp.web.beans.survey;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jp.core.exception.EnMeExpcetion;
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
 * Id: BuildSurveyControllerBean.java Date: 02/06/2009 19:36:44
 * 
 * @author juanpicado package: org.jp.web.beans.survey
 * @version 1.0
 */
public class BuildSurveyControllerBean extends MasterBean {

	private String questionSearch;
	private Collection<UnitQuestionBean> questionsList;
	private Collection<UnitSurveySection> sectionList;
	private Log log = LogFactory.getLog(this.getClass());

	private UnitQuestionBean unitQuestionBean;
	private UnitSurveySection unitSurveySection;

	public BuildSurveyControllerBean() {
		log.info("seccion add's");
	}

	/**
	 * 
	 */
	public void createSecction() {
		if (getUnitSurveySection() != null) {
			if (sectionList == null) {
				sectionList = new ArrayList<UnitSurveySection>();
			}
			log.info("Creando 1Seccion->"+getUnitSurveySection().getName());
			log.info("Creando 2Seccion->"+getUnitSurveySection().getId());
			log.info("Creando 3Seccion->"+getUnitSurveySection().getStateId());
			UnitSurveySection sec = new UnitSurveySection(getUnitSurveySection().getId(),getUnitSurveySection().getName(),2);
			sectionList.add(sec);
			addInfoMessage("Seccion Creada", "");
		} else {
			addErrorMessage("No se pudo Crear Seccion", "");
		}

	}

	/**
	 * 
	 */
	public void searchQuestions() {
		try {
			questionsList = getServicemanagerBean().getSurveyService()
					.loadAllQuestions();
		} catch (HibernateException e) {
			addErrorMessage("Error->" + e, "");
		} catch (EnMeExpcetion e) {
			addErrorMessage("Error->" + e, "");
		} catch (Exception e) {
			addErrorMessage("Error->" + e, "");
		}

	}

	public void moveQuestion(Object fm, Object family) {
		ArrayList target = null;
		UnitQuestionBean dd = (UnitQuestionBean) fm;
		log.info("Object Move fm->" + fm);
		log.info("Object family->" + family);
		addInfoMessage("Pregunta ASignada a la Secci�n ->" + family
				+ "La pregunta->" + dd.getQuestionName(), "");

		/*
		 * if ("PHP".equals(family)) target = containerPHP; else if
		 * ("DNET".equals(family)) target = containerDNET; else if
		 * ("CF".equals(family)) target = containerCF;
		 * 
		 * if (target != null) { int ind = frameworks.indexOf(fm); if (ind > -1)
		 * { target.add(frameworks.get(ind)); frameworks.remove(ind); }
		 * 
		 * }
		 */
	}

	/**
	 * @return the questionSearch
	 */
	public String getQuestionSearch() {
		return questionSearch;
	}

	/**
	 * @param questionSearch
	 *            the questionSearch to set
	 */
	public void setQuestionSearch(String questionSearch) {
		this.questionSearch = questionSearch;
	}

	/**
	 * @return the questionsList
	 */
	public Collection<UnitQuestionBean> getQuestionsList() {
		searchQuestions();
		return questionsList;
	}

	/**
	 * @param questionsList
	 *            the questionsList to set
	 */
	public void setQuestionsList(Collection<UnitQuestionBean> questionsList) {
		this.questionsList = questionsList;
	}

	/**
	 * @return the sectionList
	 */
	public Collection<UnitSurveySection> getSectionList() {
		return sectionList;
	}

	/**
	 * @param sectionList
	 *            the sectionList to set
	 */
	public void setSectionList(Collection<UnitSurveySection> sectionList) {
		this.sectionList = sectionList;
	}

	/**
	 * @return the unitQuestionBean
	 */
	public UnitQuestionBean getUnitQuestionBean() {
		return unitQuestionBean;
	}

	/**
	 * @param unitQuestionBean
	 *            the unitQuestionBean to set
	 */
	public void setUnitQuestionBean(UnitQuestionBean unitQuestionBean) {
		this.unitQuestionBean = unitQuestionBean;
	}

	/**
	 * @return the unitSurveySection
	 */
	public UnitSurveySection getUnitSurveySection() {
		return unitSurveySection;
	}

	/**
	 * @param unitSurveySection
	 *            the unitSurveySection to set
	 */
	public void setUnitSurveySection(UnitSurveySection unitSurveySection) {
		this.unitSurveySection = unitSurveySection;
	}

}
