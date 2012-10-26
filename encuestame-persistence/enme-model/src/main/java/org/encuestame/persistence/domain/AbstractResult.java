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
package org.encuestame.persistence.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.survey.Survey;


/**
 * Abstract Result Survey.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 08, 2012
 */
@MappedSuperclass
public abstract class AbstractResult {

	/** **/
	private Survey survey;

    /** **/
    private QuestionAnswer answer;

    /** **/
    private Question question;
    
    /** **/
    private String txtResponse;
    
    /**
     * @return the surveys
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param surveys the surveys to set
     */
    public void setSurvey(final Survey survey) {
        this.survey = survey;
    }

    /**
     * @return the answer
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public QuestionAnswer getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(final QuestionAnswer answer) {
        this.answer = answer;
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
	 * @return the txtResponse
	 */
    @Column(name = "txtResponse")
	public String getTxtResponse() {
		return txtResponse;
	}

	/**
	 * @param txtResponse the txtResponse to set
	 */
	public void setTxtResponse(final String txtResponse) {
		this.txtResponse = txtResponse;
	}  
}
