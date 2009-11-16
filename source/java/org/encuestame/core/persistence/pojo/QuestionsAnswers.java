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
 */
package org.encuestame.core.persistence.pojo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * QuestionsAnswers.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "questions_answers")
public class QuestionsAnswers implements java.io.Serializable {

    private QuestionsAnswersId id;
    private Questions questions;
    private String answer;

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "idAnswers", column = @Column(name = "id_answers", nullable = false)),
            @AttributeOverride(name = "qid", column = @Column(name = "qid", nullable = false)) })
    public QuestionsAnswersId getId() {
        return this.id;
    }

    public void setId(QuestionsAnswersId id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "qid", nullable = false, insertable = false, updatable = false)
    public Questions getQuestions() {
        return this.questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    @Column(name = "answer", length = 20)
    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
