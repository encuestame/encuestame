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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Questions.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "questions")
public class Questions implements Serializable {

    private Integer qid;
    private int version;
    private CatState catState;
    private String question;
    private String qidKey;
    private Set<QuestionsRelations> questionsRelationses = new HashSet<QuestionsRelations>(
            0);
    private Set<SurveyDetail> surveyDetails = new HashSet<SurveyDetail>(0);
    private Set<QuestionsAnswers> questionsAnswerses = new HashSet<QuestionsAnswers>(
            0);

    public Questions() {
    }

    public Questions(CatState catState) {
        this.catState = catState;
    }

    public Questions(CatState catState, String question, String qidKey,
            Set<QuestionsRelations> questionsRelationses,
            Set<SurveyDetail> surveyDetails,
            Set<QuestionsAnswers> questionsAnswerses) {
        this.catState = catState;
        this.question = question;
        this.qidKey = qidKey;
        this.questionsRelationses = questionsRelationses;
        this.surveyDetails = surveyDetails;
        this.questionsAnswerses = questionsAnswerses;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "qid", unique = true, nullable = false)
    public Integer getQid() {
        return this.qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    @Version
    @Column(name = "version", nullable = false)
    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_state", nullable = false)
    public CatState getCatState() {
        return this.catState;
    }

    public void setCatState(CatState catState) {
        this.catState = catState;
    }

    @Column(name = "question")
    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Column(name = "qid_key")
    public String getQidKey() {
        return this.qidKey;
    }

    public void setQidKey(String qidKey) {
        this.qidKey = qidKey;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questions")
    public Set<QuestionsRelations> getQuestionsRelationses() {
        return this.questionsRelationses;
    }

    public void setQuestionsRelationses(
            Set<QuestionsRelations> questionsRelationses) {
        this.questionsRelationses = questionsRelationses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questions")
    public Set<SurveyDetail> getSurveyDetails() {
        return this.surveyDetails;
    }

    public void setSurveyDetails(Set<SurveyDetail> surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questions")
    public Set<QuestionsAnswers> getQuestionsAnswerses() {
        return this.questionsAnswerses;
    }

    public void setQuestionsAnswerses(Set<QuestionsAnswers> questionsAnswerses) {
        this.questionsAnswerses = questionsAnswerses;
    }

}
