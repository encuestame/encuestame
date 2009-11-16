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
 */
@Entity
@Table(name = "questions_patron")
public class QuestionsPatron implements java.io.Serializable {

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
