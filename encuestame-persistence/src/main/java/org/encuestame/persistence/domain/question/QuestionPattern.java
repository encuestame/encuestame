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
package org.encuestame.persistence.domain.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QuestionsPatron.
 * @author Picado, Juan juanATencuestame.org
 * @since October 17, 2009
 * @version $Id$
 */

@Entity
@Table(name = "questions_pattern")
public class QuestionPattern {

    private Long patternId;
    private String patternType;
    private String desQid;
    private String labelQid;
    private String finallity;
    private String patternTemplate;
    private Integer level;


    /**
     * @return the patternId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pattenr_id", unique = true, nullable = false)
    public Long getPatternId() {
        return patternId;
    }

    /**
     * @param patternId the patternId to set
     */
    public void setPatternId(Long patternId) {
        this.patternId = patternId;
    }

    /**
     * @return the patternType
     */
    @Column(name = "type_pattern")
    public String getPatternType() {
        return patternType;
    }

    /**
     * @param patternType the patternType to set
     */
    public void setPatternType(String patternType) {
        this.patternType = patternType;
    }

    /**
     * @return the patternTemplate
     */
    @Column(name = "template_patron")
    public String getPatternTemplate() {
        return patternTemplate;
    }

    /**
     * @param patternTemplate the patternTemplate to set
     */
    public void setPatternTemplate(String patternTemplate) {
        this.patternTemplate = patternTemplate;
    }


    /**
     * @return desQid
     */
    @Column(name = "des_qid")
    public String getDesQid() {
        return this.desQid;
    }

    /**
     * @param desQid desQid
     */
    public void setDesQid(String desQid) {
        this.desQid = desQid;
    }

    /**
     * @return labelQid
     */
    @Column(name = "label_qid", nullable = false)
    public String getLabelQid() {
        return this.labelQid;
    }

    /**
     * @param labelQid labelQid
     */
    public void setLabelQid(String labelQid) {
        this.labelQid = labelQid;
    }

    /**
     * @return finallity
     */
    @Column(name = "finallity_patter")
    public String getFinallity() {
        return this.finallity;
    }

    /**
     * @param finallity finallity
     */
    public void setFinallity(String finallity) {
        this.finallity = finallity;
    }

    /**
     * @return the level
     */
    @Column(name = "level_patter")
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}
