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
package org.encuestame.core.persistence.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Questions.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Indexed(index="Questions")
@Table(name = "questions")
public class Questions {

    private Long qid;
    private CatState catState;
    private String question;
    private String qidKey;
    private Boolean sharedQuestion;
    private QuestionPattern questionPattern;
    private SecUsers secUsersQuestion;
    private Set<QuestionColettion> questionColettions = new HashSet<QuestionColettion>();

    /** {@link QuestionsAnswers}. **/
    private Set<QuestionsAnswers> questionsAnswers = new HashSet<QuestionsAnswers>();

    /**
     * @return qid
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qid", unique = true, nullable = false)
    public Long getQid() {
        return this.qid;
    }

    /**
     * @param qid qid
     */
    public void setQid(final Long qid) {
        this.qid = qid;
    }


    /**
     * @return catState
     */
    @ManyToOne()
    @JoinColumn(name = "id_state", nullable = true)
    public CatState getCatState() {
        return this.catState;
    }

    /**
     * @param catState catState
     */
    public void setCatState(final CatState catState) {
        this.catState = catState;
    }

    /**
     * @return question
     */
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "question")
    public String getQuestion() {
        return this.question;
    }

    /**
     * @param question question
     */
    public void setQuestion(final String question) {
        this.question = question;
    }

    /**
     * @return qidKey
     */
    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "qid_key")
    public String getQidKey() {
        return this.qidKey;
    }

    /**
     * @param qidKey qidKey
     */
    public void setQidKey(final String qidKey) {
        this.qidKey = qidKey;
    }

    /**
     * @return the questionColettions
     */
    @ManyToMany()
    @JoinTable(name="question_relations",
              joinColumns={@JoinColumn(name="question_id")},
              inverseJoinColumns={@JoinColumn(name="id_q_colection")})
    public Set<QuestionColettion> getQuestionColettions() {
        return questionColettions;
    }

    /**
     * @param questionColettions the questionColettions to set
     */
    public void setQuestionColettions(final Set<QuestionColettion> questionColettions) {
        this.questionColettions = questionColettions;
    }

    /**
     * @return the questionPattern
     */
    @ManyToOne()
    @JoinColumn(name = "id_question_pattern", nullable = true)
    public QuestionPattern getQuestionPattern() {
        return questionPattern;
    }

    /**
     * @param questionPattern the questionPattern to set
     */
    public void setQuestionPattern(final QuestionPattern questionPattern) {
        this.questionPattern = questionPattern;
    }

    /**
     * @return the secUsersQuestion
     */
    @ManyToOne()
    @JoinColumn(name = "uid", nullable = false)
    public SecUsers getSecUsersQuestion() {
        return secUsersQuestion;
    }

    /**
     * @param secUsersQuestion the secUsersQuestion to set
     */
    public void setSecUsersQuestion(final SecUsers secUsersQuestion) {
        this.secUsersQuestion = secUsersQuestion;
    }

    /**
     * @return the sharedQuestion
     */
    @Column(name = "shared_question")
    public Boolean getSharedQuestion() {
        return sharedQuestion;
    }

    /**
     * @param sharedQuestion the sharedQuestion to set
     */
    public void setSharedQuestion(final Boolean sharedQuestion) {
        this.sharedQuestion = sharedQuestion;
    }

    /**
     * @return the questionsAnswers
     */
    @OneToMany(mappedBy = "questions", cascade = CascadeType.REMOVE)
    public Set<QuestionsAnswers> getQuestionsAnswers() {
        return questionsAnswers;
    }

    /**
     * @param questionsAnswers the questionsAnswers to set
     */
    public void setQuestionsAnswers(final Set<QuestionsAnswers> questionsAnswers) {
        this.questionsAnswers = questionsAnswers;
    }
}
