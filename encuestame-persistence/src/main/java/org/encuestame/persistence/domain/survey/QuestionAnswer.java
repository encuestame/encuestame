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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.Question;

/**
 * QuestionsAnswers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "questions_answers")
public class QuestionAnswer {

    /** questionAnswerId. **/
    private Long questionAnswerId;

    /** questions. **/
    private Question questions;

    /** answer. **/
    private String answer;

    /** uniqueAnserHash. **/
    private String uniqueAnserHash;

    /** Url Answer. **/
    private String urlAnswer;

    /** Answer Type. **/
    private AnswerType answerType = AnswerType.DEFAULT;

    /** Answer Type Enum. **/
    public enum AnswerType {

    /**
     * Video
     */
    VIDEO,

    /**
     * Image
     */
    IMAGE,

    /**
     * Sounds
     */
    SOUNDS,

    /**
    * Default
    */
    DEFAULT
    ;}


    /**
     * @return the questionAnswerId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "q_answer_id", unique = true, nullable = false)
    public Long getQuestionAnswerId() {
        return questionAnswerId;
    }

    /**
     * @param questionAnswerId
     *            the questionAnswerId to set
     */
    public void setQuestionAnswerId(Long questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    /**
     * @return the questions
     */
    @ManyToOne()
    @JoinColumn(name = "id_question_answer", nullable = false)
    public Question getQuestions() {
        return questions;
    }

    /**
     * @param questions
     *            the questions to set
     */
    public void setQuestions(Question questions) {
        this.questions = questions;
    }

    /**
     * @return the answer
     */
    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer
     *            the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the uniqueAnserHash
     */
    @Column(name = "answer_hash", nullable = true)
    public String getUniqueAnserHash() {
        return uniqueAnserHash;
    }

    /**
     * @param uniqueAnserHash
     *            the uniqueAnserHash to set
     */
    public void setUniqueAnserHash(final String uniqueAnserHash) {
        this.uniqueAnserHash = uniqueAnserHash;
    }

    /**
     * @return the answerType
     */
    @Column(name = "answerType", nullable = true)
    public AnswerType getAnswerType() {
        return answerType;
    }

    /**
     * @param answerType the answerType to set
     */
    public void setAnswerType(final AnswerType answerType) {
        this.answerType = answerType;
    }

    /**
     * @return the urlAnswer
     */
    @Column(name = "answer_url", nullable = true)
    public String getUrlAnswer() {
        return urlAnswer;
    }

    /**
     * @param urlAnswer the urlAnswer to set
     */
    public void setUrlAnswer(String urlAnswer) {
        this.urlAnswer = urlAnswer;
    }
}
