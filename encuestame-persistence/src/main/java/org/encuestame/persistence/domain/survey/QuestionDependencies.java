/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Questions Dependencies.
 *
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since August 10, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "questions_dependencies")
public class QuestionDependencies {

    /****/
    private Long questionDependenceId;

    /****/
    private Long questionId_to;

    /****/
    private Long questionId_from;

    /****/
    private QuestionAnswer answers;

    /****/
    private String descriptionDependence;


    /**
     * @return the questionDependenceId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_dependenceId", unique = true, nullable = false)
    public Long getQuestionDependenceId() {
        return questionDependenceId;
    }

    /**
     * @param questionDependenceId the questionDependenceId to set
     */
    public void setQuestionDependenceId(Long questionDependenceId) {
        this.questionDependenceId = questionDependenceId;
    }


    /**
     * @return the questionId_to
     */
    @Column(name = "questionId_to", unique = true, nullable = false)
    public Long getQuestionId_to() {
        return questionId_to;
    }

    /**
     * @param questionIdTo the questionId_to to set
     */
    public void setQuestionId_to(Long questionIdTo) {
        questionId_to = questionIdTo;
    }

    /**
     * @return the questionId_from
     */
    @Column(name = "questionId_from", unique = true, nullable = false)
    public Long getQuestionId_from() {
        return questionId_from;
    }

    /**
     * @param questionIdFrom the questionId_from to set
     */
    public void setQuestionId_from(Long questionIdFrom) {
        questionId_from = questionIdFrom;
    }

    /**
     * @return the answers
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "q_answer_id", nullable = false)
    public QuestionAnswer getAnswers() {
        return answers;
    }

    /**
     * @param answers the answers to set
     */
    public void setAnswers(QuestionAnswer answers) {
        this.answers = answers;
    }

    /**
     * @return the descriptionDependence
     */
    @Column(name = "descriptionDependence", unique = true, nullable = false)
     public String getDescriptionDependence() {
        return descriptionDependence;
    }

    /**
     * @param descriptionDependence the descriptionDependence to set
     */
    public void setDescriptionDependence(String descriptionDependence) {
        this.descriptionDependence = descriptionDependence;
    }

}
