/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;

/**
 * SurveyResult.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "survey_result")
public class SurveyResult {

    /** **/
    private Long rid;

    /** **/
    private Survey survey;

    /** **/
    private QuestionAnswer answer;

    /** **/
    private Question question;

    /**
     * @return rid
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rid", unique = true, nullable = false)
    public Long getRid() {
        return this.rid;
    }

    /**
     * @param rid rid
     */
    public void setRid(final Long rid) {
        this.rid = rid;
    }

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
}
