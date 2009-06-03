package org.jp.web.beans.survey;

import java.util.Collection;
import java.util.List;

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

	public BuildSurveyControllerBean() {

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

}
