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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SurveySection.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_section")
public class SurveySection implements java.io.Serializable {

    private Long ssid;
    private CatState catState;
    private String descSection;
    private Set<SurveyDetail> surveyDetails = new HashSet<SurveyDetail>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ssid", unique = true, nullable = false)
    public Long getSsid() {
        return this.ssid;
    }

    public void setSsid(Long ssid) {
        this.ssid = ssid;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_state", nullable = false)
    public CatState getCatState() {
        return this.catState;
    }

    public void setCatState(CatState catState) {
        this.catState = catState;
    }

    @Column(name = "desc_section")
    public String getDescSection() {
        return this.descSection;
    }

    public void setDescSection(String descSection) {
        this.descSection = descSection;
    }

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "surveySection")
    public Set<SurveyDetail> getSurveyDetails() {
        return this.surveyDetails;
    }

    public void setSurveyDetails(Set<SurveyDetail> surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

}
