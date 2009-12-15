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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QuestionsPatron.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "questions_pattern")
public class QuestionPattern implements java.io.Serializable {

    private Long idPatron;
    private String typePatron;
    private String desQid;
    private String labelQid;
    private String finallity;
    private String templatePatron;
    private String class_;
    private Integer nivel;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_patron", unique = true, nullable = false)
    public Long getIdPatron() {
        return this.idPatron;
    }

    public void setIdPatron(Long idPatron) {
        this.idPatron = idPatron;
    }

    @Column(name = "type_patron", nullable = false, length = 25)
    public String getTypePatron() {
        return this.typePatron;
    }

    public void setTypePatron(String typePatron) {
        this.typePatron = typePatron;
    }

    @Column(name = "des_qid", nullable = false, length = 50)
    public String getDesQid() {
        return this.desQid;
    }

    public void setDesQid(String desQid) {
        this.desQid = desQid;
    }

    @Column(name = "label_qid", nullable = false)
    public String getLabelQid() {
        return this.labelQid;
    }

    public void setLabelQid(String labelQid) {
        this.labelQid = labelQid;
    }

    @Column(name = "finallity", length = 16777215)
    public String getFinallity() {
        return this.finallity;
    }

    public void setFinallity(String finallity) {
        this.finallity = finallity;
    }

    @Column(name = "template_patron", length = 25)
    public String getTemplatePatron() {
        return this.templatePatron;
    }

    public void setTemplatePatron(String templatePatron) {
        this.templatePatron = templatePatron;
    }

    @Column(name = "class", nullable = false, length = 50)
    public String getClass_() {
        return this.class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    @Column(name = "nivel")
    public Integer getNivel() {
        return this.nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

}
