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

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QuestionsRelations.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "questions_relations")
public class QuestionsRelations implements java.io.Serializable {

    private QuestionsRelationsId id;
    private QuestionColettion questionColettion;
    private Questions questions;
    private Date relationDate;

    public QuestionsRelations() {
    }

    public QuestionsRelations(QuestionsRelationsId id,
            QuestionColettion questionColettion, Questions questions,
            Date relationDate) {
        this.id = id;
        this.questionColettion = questionColettion;
        this.questions = questions;
        this.relationDate = relationDate;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "idQidRel", column = @Column(name = "id_qid_rel", nullable = false)),
            @AttributeOverride(name = "qid", column = @Column(name = "qid", nullable = false)) })
    public QuestionsRelationsId getId() {
        return this.id;
    }

    public void setId(QuestionsRelationsId id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "id_q_colection", nullable = false)
    public QuestionColettion getQuestionColettion() {
        return this.questionColettion;
    }

    public void setQuestionColettion(QuestionColettion questionColettion) {
        this.questionColettion = questionColettion;
    }

    @ManyToOne()
    @JoinColumn(name = "qid", nullable = false, insertable = false, updatable = false)
    public Questions getQuestions() {
        return this.questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "relation_date", nullable = false, length = 0)
    public Date getRelationDate() {
        return this.relationDate;
    }

    public void setRelationDate(Date relationDate) {
        this.relationDate = relationDate;
    }

}
