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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_sid_format", nullable = false, insertable = false, updatable = false)
    public SurveyFormat getSurveyFormat() {
        return this.surveyFormat;
    }

    public void setSurveyFormat(SurveyFormat surveyFormat) {
        this.surveyFormat = surveyFormat;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "qid", nullable = false, insertable = false, updatable = false)
    public Questions getQuestions() {
        return this.questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
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
