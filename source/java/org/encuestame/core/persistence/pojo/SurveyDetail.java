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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SurveyDetail.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "survey_detail")
public class SurveyDetail implements java.io.Serializable {

    private SurveyDetailId id;
    private SurveyFormat surveyFormat;
    private Questions questions;
    private SurveySection surveySection;
    private Integer position;
    private String nopreg;

    public SurveyDetail() {
    }

    public SurveyDetail(SurveyDetailId id, SurveyFormat surveyFormat,
            Questions questions, SurveySection surveySection) {
        this.id = id;
        this.surveyFormat = surveyFormat;
        this.questions = questions;
        this.surveySection = surveySection;
    }

    public SurveyDetail(SurveyDetailId id, SurveyFormat surveyFormat,
            Questions questions, SurveySection surveySection, Integer position,
            String nopreg) {
        this.id = id;
        this.surveyFormat = surveyFormat;
        this.questions = questions;
        this.surveySection = surveySection;
        this.position = position;
        this.nopreg = nopreg;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "idSd", column = @Column(name = "id_sd", nullable = false)),
            @AttributeOverride(name = "qid", column = @Column(name = "qid", nullable = false)),
            @AttributeOverride(name = "idSidFormat", column = @Column(name = "id_sid_format", nullable = false)),
            @AttributeOverride(name = "ssid", column = @Column(name = "ssid", nullable = false)) })
    public SurveyDetailId getId() {
        return this.id;
    }

    public void setId(SurveyDetailId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sid_format", nullable = false, insertable = false, updatable = false)
    public SurveyFormat getSurveyFormat() {
        return this.surveyFormat;
    }

    public void setSurveyFormat(SurveyFormat surveyFormat) {
        this.surveyFormat = surveyFormat;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qid", nullable = false, insertable = false, updatable = false)
    public Questions getQuestions() {
        return this.questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ssid", nullable = false, insertable = false, updatable = false)
    public SurveySection getSurveySection() {
        return this.surveySection;
    }

    public void setSurveySection(SurveySection surveySection) {
        this.surveySection = surveySection;
    }

    @Column(name = "position")
    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "nopreg", length = 10)
    public String getNopreg() {
        return this.nopreg;
    }

    public void setNopreg(String nopreg) {
        this.nopreg = nopreg;
    }

}
