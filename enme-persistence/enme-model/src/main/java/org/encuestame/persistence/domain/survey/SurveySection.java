/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * SurveySection.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version  $Id$
 */

@Entity
@Table(name = "survey_section")
@Indexed(index="SurveySection")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SurveySection {

    /** **/
    private Long ssid;

    /** **/
    private String descSection;

    /** **/
    private Survey survey;

    /** **/
    private String sectionName;

    /**
     * @return ssid
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ssid", unique = true, nullable = false)
    public Long getSsid() {
        return this.ssid;
    }

    /**
     * @param ssid ssid
     */
    public void setSsid(Long ssid) {
        this.ssid = ssid;
    }

    /**
     * @return descSection
     */
    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "desc_section")
    public String getDescSection() {
        return this.descSection;
    }

    /**
     * @param descSection descSection
     */
    public void setDescSection(String descSection) {
        this.descSection = descSection;
    }

    /**
     * @return the survey
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * @return the sectionName
     */
    @Column(name = "section_name")
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(final String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the questionSection
     */
    /*@ManyToMany(cascade=CascadeType.ALL)
    public Set<Question> getQuestionSection() {
        return questionSection;
    }

    *//**
     * @param questionSection the questionSection to set
     *//*
    public void setQuestionSection(Set<Question> questionSection) {
        this.questionSection = questionSection;
    }
*/
}
