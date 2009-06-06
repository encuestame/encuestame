package org.jp.web.beans.survey;

import java.util.Collection;
import java.util.LinkedList;

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
 * Id: UnitQuestionBean.java Date: 01/06/2009 15:22:10
 * 
 * @author juanpicado package: org.jp.web.beans.survey
 * @version 1.0
 */
public class UnitQuestionBean extends MasterBean {

	public String questionName;
	public String version;
	public Integer idPatron;
	public Integer idState;
	public Integer id;
	public Collection<UnitAnswersBean> listAnswers = new LinkedList<UnitAnswersBean>();

	public UnitQuestionBean() {
	}

	public UnitQuestionBean(String questionName, String version,
			Integer idPatron, Integer idState, Integer id,
			Collection<UnitAnswersBean> listAnswers) {
		super();
		this.questionName = questionName;
		this.version = version;
		this.idPatron = idPatron;
		this.idState = idState;
		this.id = id;
		this.listAnswers = listAnswers;
	}

	/**
	 * @return the questionName
	 */
	public String getQuestionName() {
		return questionName;
	}

	/**
	 * @param questionName
	 *            the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the idPatron
	 */
	public Integer getIdPatron() {
		return idPatron;
	}

	/**
	 * @param idPatron
	 *            the idPatron to set
	 */
	public void setIdPatron(Integer idPatron) {
		this.idPatron = idPatron;
	}

	/**
	 * @return the idState
	 */
	public Integer getIdState() {
		return idState;
	}

	/**
	 * @param idState
	 *            the idState to set
	 */
	public void setIdState(Integer idState) {
		this.idState = idState;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the listAnswers
	 */
	public Collection<UnitAnswersBean> getListAnswers() {
		return listAnswers;
	}

	/**
	 * @param listAnswers
	 *            the listAnswers to set
	 */
	public void setListAnswers(Collection<UnitAnswersBean> listAnswers) {
		this.listAnswers = listAnswers;
	}

}
