/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.question;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

 

/**
 * Questions Preferences.
 * @author Morales Diana Paola, paolaATencuestame.org
 * @since August 07, 2012
 */
@Entity 
@Table(name = "question_preferences") 
public class QuestionPreferences {
	
	/** **/
	private Long preferenceId;
	
	/** **/
	private Question question;
	
	/** **/
	private String preference;
	
	/** **/
	private String value;

	/**
	 * @return the preferenceId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "preferenceId", unique = true, nullable = false)
	public Long getPreferenceId() {
		return preferenceId;
	}

	/**
	 * @param preferenceId the preferenceId to set
	 */
	public void setPreferenceId(final Long preferenceId) {
		this.preferenceId = preferenceId;
	}
  
	/**
	 * @return the question
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(final Question question) {
		this.question = question;
	}

	/**
	 * @return the preference
	 */
	@Column(name = "preference")
	public String getPreference() {
		return preference;
	}

	/**
	 * @param preference the preference to set
	 */
	public void setPreference(final String preference) {
		this.preference = preference;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}  
	

}
