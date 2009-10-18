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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SurveyFormat.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_format")
public class SurveyFormat implements java.io.Serializable {

    private int idSidFormat;
    private String name;
    private Date dateCreated;
    private Set<SurveyFormatGroup> surveyFormatGroups = new HashSet<SurveyFormatGroup>(
            0);
    private Set<SurveyDetail> surveyDetails = new HashSet<SurveyDetail>(0);

    public SurveyFormat() {
    }

    public SurveyFormat(int idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    public SurveyFormat(int idSidFormat, String name, Date dateCreated,
            Set<SurveyFormatGroup> surveyFormatGroups,
            Set<SurveyDetail> surveyDetails) {
        this.idSidFormat = idSidFormat;
        this.name = name;
        this.dateCreated = dateCreated;
        this.surveyFormatGroups = surveyFormatGroups;
        this.surveyDetails = surveyDetails;
    }

    @Id
    @Column(name = "id_sid_format", unique = true, nullable = false)
    public int getIdSidFormat() {
        return this.idSidFormat;
    }

    public void setIdSidFormat(int idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    @Column(name = "name", length = 60)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", length = 0)
    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyFormat")
    public Set<SurveyFormatGroup> getSurveyFormatGroups() {
        return this.surveyFormatGroups;
    }

    public void setSurveyFormatGroups(Set<SurveyFormatGroup> surveyFormatGroups) {
        this.surveyFormatGroups = surveyFormatGroups;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surveyFormat")
    public Set<SurveyDetail> getSurveyDetails() {
        return this.surveyDetails;
    }

    public void setSurveyDetails(Set<SurveyDetail> surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

}
