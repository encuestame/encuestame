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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class CatState {

    private Long idState;
    private String descState;
    private String stateImage;
    private Set<SurveySection> surveySections = new HashSet<SurveySection>(0);
    private Set<Project> catStateProject = new HashSet<Project>(0);
    private Set<Questions> questionses = new HashSet<Questions>(0);


    /**
     * @return idState
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_state", unique = true, nullable = false)
    public Long getIdState() {
        return this.idState;
    }

    /**
     * @param idState idState
     */
    public void setIdState(final Long idState) {
        this.idState = idState;
    }

    /**
     * @return descState
     */
    @Column(name = "desc_state")
    public String getDescState() {
        return this.descState;
    }

    /**
     * @param descState descState
     */
    public void setDescState(String descState) {
        this.descState = descState;
    }

    /**
     * @return stateImage
     */
    @Column(name = "image")
    public String getStateImage() {
        return this.stateImage;
    }

    /**
     * @param stateImage stateImage
     */
    public void setStateImage(final String stateImage) {
        this.stateImage = stateImage;
    }

    /**
     * @return surveySections
     */
    @OneToMany(mappedBy = "catState")
    public Set<SurveySection> getSurveySections() {
        return this.surveySections;
    }

    /**
     * @param surveySections surveySections
     */
    public void setSurveySections(Set<SurveySection> surveySections) {
        this.surveySections = surveySections;
    }

    /**
     * @return catStateProject
     */
    @OneToMany(mappedBy = "catStateProject")
    public Set<Project> getCatStateProject() {
        return this.catStateProject;
    }

    /**
     * @param catStateProject catStateProject
     */
    public void setCatStateProject(final Set<Project> catStateProject) {
        this.catStateProject = catStateProject;
    }

    /**
     * @return questionses
     */
    @OneToMany(mappedBy = "catState")
    public Set<Questions> getQuestionses() {
        return this.questionses;
    }

    /**
     * @param questionses questionses
     */
    public void setQuestionses(Set<Questions> questionses) {
        this.questionses = questionses;
    }

}
