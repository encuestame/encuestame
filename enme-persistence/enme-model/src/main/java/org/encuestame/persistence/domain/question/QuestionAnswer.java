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
package org.encuestame.persistence.domain.question;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.utils.ShortUrlProvider;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

/**
 * QuestionsAnswers.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "questions_answers")
@Indexed(index="QuestionAnswer")
public class QuestionAnswer {

    /** questionAnswerId. **/
    private Long questionAnswerId;

    /** questions. **/
    private Question questions;

    /** answer. **/
    private String answer;

    /** uniqueAnserHash. **/
    private String uniqueAnserHash;

    /** default color. **/
    private String color;

    /** Url Answer. **/
    private String urlAnswer;

    /** Answer Type. **/
    private AnswerType answerType = AnswerType.DEFAULT;

    /** Default short url provider. **/
    private ShortUrlProvider provider = ShortUrlProvider.GOOGL;

    /**
     *
     */
    private Date createdDate =  Calendar.getInstance().getTime();

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
    @DocumentId
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
    @Field(index = Index.YES, store = Store.YES)
    @Column(name = "answer", nullable = false)
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
    public void setUrlAnswer(final String urlAnswer) {
        this.urlAnswer = urlAnswer;
    }

    /**
     * @return the provider
     */
    @Column(name = "short_url_provider", nullable = true, length = 400)
    public ShortUrlProvider getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(final ShortUrlProvider provider) {
        this.provider = provider;
    }

    /**
     * @return the createdDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateBridge(resolution = Resolution.DAY)
    @Column(name = "created_date", nullable = true)
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }



    /**
     * @return the color
     */
    @Column(name = "color", nullable = false)
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(final String color) {
        this.color = color;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "QuestionAnswer [questionAnswerId=" + questionAnswerId
                + ", questions=" + questions + ", answer=" + answer
                + ", uniqueAnserHash=" + uniqueAnserHash + ", urlAnswer="
                + urlAnswer + ", answerType=" + answerType + ", provider="
                + provider + ", createdDate=" + createdDate + "]";
    }
}
