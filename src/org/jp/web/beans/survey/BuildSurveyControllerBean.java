package org.jp.web.beans.survey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
	private List<UnitQuestionBean> questionsList;
	private Boolean showQuestionForm = false;
	private Boolean showSectionForm = false;
	private UnitPatternBean unitPatterBean;
	private List<UnitSurveySection> sectionList;
	private Log log = LogFactory.getLog(this.getClass());
	private Integer idCounterSection = 1;
	private Integer idCounterQuestion = 1;
	private Integer sectionSelected;

	private UnitQuestionBean unitQuestionBean;
	private UnitSurveySection unitSurveySection;

	public BuildSurveyControllerBean() {
		log.info("seccion add's");
	}

	/**
	 * create new section
	 */
	public void createSecction() {
		if (getUnitSurveySection() != null) {
			if (sectionList == null) {
				sectionList = new ArrayList<UnitSurveySection>();
			}
			UnitSurveySection sec;
			try {
				sec = new UnitSurveySection(getNewIdCounter(1),
						getUnitSurveySection().getName(), 2);
				sectionList.add(sec);
				addInfoMessage("Seccion Creada", "");
				cleanSecctionBean();
			} catch (EnMeExpcetion e) {
				addErrorMessage("error->" + e.getMessage(), "");
			}
		} else {
			addErrorMessage("No se pudo Crear Seccion", "");
		}
	}

	/**
	 * create a question
	 */
	public void createQuestion() {
		if (getUnitQuestionBean() != null) {
			if (questionsList == null) {
				questionsList = new ArrayList<UnitQuestionBean>();
			}
			UnitQuestionBean question;
			try {
				int d = getNewIdCounter(2);
				question = new UnitQuestionBean();
				question.setId(d);
				question.setPattern(getUnitPatterBean());
				question.setQuestionName(getUnitQuestionBean().getQuestionName());
				question.setVersion("1");
				question.setIdState(2);
				addInfoMessage("Pregunta Creada", "");
				questionsList.add(question);
				//cleanSecctionBean();
			} catch (EnMeExpcetion e) {
				addErrorMessage("error->" + e.getMessage(), "");
			}
		}
	}
	

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	private Integer getNewIdCounter(Integer op) throws EnMeExpcetion {
		Integer idRe = null;
		switch (op) {
		case 1:
			if (idCounterSection != null) {
				idRe = idCounterSection;
				idCounterSection = idCounterSection + 1;
			} else {
				idRe = idCounterSection = 1;
			}
			break;
		case 2:
			if (idCounterQuestion != null) {
				idRe = idCounterQuestion;
				idCounterQuestion = idCounterQuestion + 1;
			} else {
				idRe = idCounterQuestion = 1;
			}
			break;
		default:
			throw new EnMeExpcetion("counter error");
		}
		return idRe;
	}

	/**
	 * 
	 */
	private void cleanSecctionBean() {
		getUnitSurveySection().setName(null);
		getUnitSurveySection().setId(null);
		getUnitSurveySection().setStateId(null);
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

	/**
	 * move question to section
	 * 
	 * @param fm
	 * @param family
	 */
	public void moveQuestion(Object fm, Object family) {
		ArrayList target = null;
		UnitQuestionBean dd = (UnitQuestionBean) fm;
		log.info("Object Move fm->" + fm);
		log.info("Object family->" + family);
		addInfoMessage("Pregunta ASignada a la Sección ->" + family
				+ "La pregunta->" + dd.getQuestionName(), "");
		int ind = sectionList.indexOf(family);
		UnitSurveySection d = sectionList.get(ind);
		d.getQuestions().add((UnitQuestionBean) fm);
		log.info("Posicion Encontrada->" + ind);
		log.info("Posicion sectionList->" + sectionList);
		UnitSurveySection ddedd = sectionList.get(ind);
		log.info("Posicion Preguntas Totales->" + ddedd.getQuestions());
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
	public List<UnitQuestionBean> getQuestionsList() {
		//searchQuestions();
		return questionsList;
	}

	/**
	 * @param questionsList
	 *            the questionsList to set
	 */
	public void setQuestionsList(List<UnitQuestionBean> questionsList) {
		this.questionsList = questionsList;
	}

	/**
	 * @return the sectionList
	 */
	public List<UnitSurveySection> getSectionList() {
		return sectionList;
	}

	/**
	 * @param sectionList
	 *            the sectionList to set
	 */
	public void setSectionList(List<UnitSurveySection> sectionList) {
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

	/**
	 * @return the showQuestionForm
	 */
	public Boolean getShowQuestionForm() {
		return showQuestionForm;
	}

	/**
	 * @param showQuestionForm
	 *            the showQuestionForm to set
	 */
	public void setShowQuestionForm(Boolean showQuestionForm) {
		this.showQuestionForm = showQuestionForm;
	}

	/**
	 * @return the showSectionForm
	 */
	public Boolean getShowSectionForm() {
		return showSectionForm;
	}

	/**
	 * @param showSectionForm
	 *            the showSectionForm to set
	 */
	public void setShowSectionForm(Boolean showSectionForm) {
		this.showSectionForm = showSectionForm;
	}

	/**
	 * @return the sectionSelected
	 */
	public Integer getSectionSelected() {
		return sectionSelected;
	}

	/**
	 * change rendered section
	 * 
	 * @param selected
	 */
	public void changeRenderedSection() {
		if (getSectionSelected() != null) {
			UnitSurveySection ind = sectionList.get(getSectionSelected()
					.intValue() - 1);
			changeShowedSection(ind);
		} else {
			addErrorMessage("getSectionSelected() is null", "");
		}
	}

	/**
	 * 
	 * @param sec
	 */
	private void changeShowedSection(UnitSurveySection sec) {

		try {
			Iterator<UnitSurveySection> i = sectionList.iterator();
			while (i.hasNext()) {
				UnitSurveySection unitSurveySection = (UnitSurveySection) i
						.next();
				unitSurveySection.setShowPanel(false);
			}
			if (sec != null) {
				sec.setShowPanel(true);

			} else {
				addErrorMessage("No se pudo cambiar la sección", "");
			}
		} catch (Exception e) {
			addErrorMessage("No se pudo cambiar la sección", "");
		}
	}

	/**
	 * @param sectionSelected
	 *            the sectionSelected to set
	 */
	public void setSectionSelected(Integer sectionSelected) {
		this.sectionSelected = sectionSelected;
	}

	public UnitPatternBean getUnitPatterBean() {
		return unitPatterBean;
	}

	public void setUnitPatterBean(UnitPatternBean unitPatterBean) {
		this.unitPatterBean = unitPatterBean;
	}

	
	

}
