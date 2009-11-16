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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SurveyResult.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_result_mod")
public class SurveyResultMod implements java.io.Serializable {

    private Long idMod;
    private SecUsers secUsers;
    private SurveyResult surveyResult;
    private String previousResponse;
    private String newResponse;
    private Date modDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_mod", unique = true, nullable = false)
    public Long getIdMod() {
        return this.idMod;
    }

    public void setIdMod(Long idMod) {
        this.idMod = idMod;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUsers getSecUsers() {
        return this.secUsers;
    }

    public void setSecUsers(SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "rid", nullable = false)
    public SurveyResult getSurveyResult() {
        return this.surveyResult;
    }

    public void setSurveyResult(SurveyResult surveyResult) {
        this.surveyResult = surveyResult;
    }

    @Column(name = "previous_response", nullable = false)
    public String getPreviousResponse() {
        return this.previousResponse;
    }

    public void setPreviousResponse(String previousResponse) {
        this.previousResponse = previousResponse;
    }

    @Column(name = "new_response")
    public String getNewResponse() {
        return this.newResponse;
    }

    public void setNewResponse(String newResponse) {
        this.newResponse = newResponse;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "mod_date", nullable = false, length = 0)
    public Date getModDate() {
        return this.modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

}
