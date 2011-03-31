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
package org.encuestame.persistence.domain.question;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;

/**
 * QuestionLibrary.
 *
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since September 27, 2010
 * @version $Id: $
 */
@Entity
@Table(name = "question_category")
public class CatQuestionCategory {

    private Long questionCategoryId;
    private String category;
    private Set<Question> questionLibrary = new HashSet<Question>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qCategory", unique = true, nullable = false)
    public Long getQuestionCategoryId() {
        return questionCategoryId;
    }
    /**
     * @param questionCategoryId the questionCategoryId to set
     */
    public void setQuestionCategoryId(Long questionCategoryId) {
        this.questionCategoryId = questionCategoryId;
    }
    /**
     * @return the category
     */
    @Column(name = "category", nullable = true, length = 18)
    public String getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * @return the questionLibrary
     */
    @ManyToMany(cascade=CascadeType.ALL)
    public Set<Question> getQuestionLibrary() {
        return questionLibrary;
    }
    /**
     * @param questionLibrary the questionLibrary to set
     */
    public void setQuestionLibrary(Set<Question> questionLibrary) {
        this.questionLibrary = questionLibrary;
    }
}
