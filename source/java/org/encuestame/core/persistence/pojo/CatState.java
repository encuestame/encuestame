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
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CatState.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "cat_state")
public class CatState implements Serializable {

    private Long idState;
    private String descState;
    private String image;
    private Set<SurveySection> surveySections = new HashSet<SurveySection>(0);
    private Set<Project> projects = new HashSet<Project>(0);
    private Set<Questions> questionses = new HashSet<Questions>(0);

    public CatState() {
    }

    public CatState(String descState, String image, Set surveySections,
            Set projects, Set questionses) {
        this.descState = descState;
        this.image = image;
        this.surveySections = surveySections;
        this.projects = projects;
        this.questionses = questionses;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_state", unique = true, nullable = false)
    public Long getIdState() {
        return this.idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    @Column(name = "desc_state")
    public String getDescState() {
        return this.descState;
    }

    public void setDescState(String descState) {
        this.descState = descState;
    }

    @Column(name = "image")
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @OneToMany(mappedBy = "catState")
    public Set<SurveySection> getSurveySections() {
        return this.surveySections;
    }

    public void setSurveySections(Set<SurveySection> surveySections) {
        this.surveySections = surveySections;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catState")
    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @OneToMany(mappedBy = "catState")
    public Set<Questions> getQuestionses() {
        return this.questionses;
    }

    public void setQuestionses(Set<Questions> questionses) {
        this.questionses = questionses;
    }

}
