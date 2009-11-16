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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QuestionColettion.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "question_colettion")
public class QuestionColettion implements Serializable {

    private Long idQColection;
    private SecUsers secUsers;
    private String desColeccion;
    private Date creationDate;
    private Set<QuestionsRelations> questionsRelationses = new HashSet<QuestionsRelations>(
            0);

    public QuestionColettion() {
    }

    public QuestionColettion(SecUsers secUsers, String desColeccion,
            Date creationDate) {
        this.secUsers = secUsers;
        this.desColeccion = desColeccion;
        this.creationDate = creationDate;
    }

    public QuestionColettion(SecUsers secUsers, String desColeccion,
            Date creationDate, Set<QuestionsRelations> questionsRelationses) {
        this.secUsers = secUsers;
        this.desColeccion = desColeccion;
        this.creationDate = creationDate;
        this.questionsRelationses = questionsRelationses;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_q_colection", unique = true, nullable = false)
    public Long getIdQColection() {
        return this.idQColection;
    }

    public void setIdQColection(Long idQColection) {
        this.idQColection = idQColection;
    }

    @ManyToOne()
    @JoinColumn(name = "uid", nullable = false)
    public SecUsers getSecUsers() {
        return this.secUsers;
    }

    public void setSecUsers(SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    @Column(name = "des_coleccion", nullable = false)
    public String getDesColeccion() {
        return this.desColeccion;
    }

    public void setDesColeccion(String desColeccion) {
        this.desColeccion = desColeccion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, length = 0)
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @OneToMany(mappedBy = "questionColettion")
    public Set<QuestionsRelations> getQuestionsRelationses() {
        return this.questionsRelationses;
    }

    public void setQuestionsRelationses(
            Set<QuestionsRelations> questionsRelationses) {
        this.questionsRelationses = questionsRelationses;
    }

}
