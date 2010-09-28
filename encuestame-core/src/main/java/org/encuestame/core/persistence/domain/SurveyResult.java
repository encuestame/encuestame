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
package org.encuestame.core.persistence.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SurveyResult.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "survey_result")
public class SurveyResult {

    private Long rid;
    private String resp;
    //private Set<SurveyResultMod> surveyResultMods = new HashSet<SurveyResultMod>();
    private Surveys surveys = new Surveys();

    /**
     * @return rid
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rid", unique = true, nullable = false)
    public Long getRid() {
        return this.rid;
    }

    /**
     * @param rid rid
     */
    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     * @return resp
     */
    @Column(name = "resp", nullable = false)
    public String getResp() {
        return this.resp;
    }

    /**
     * @param resp resp
     */
    public void setResp(String resp) {
        this.resp = resp;
    }

    /**
     * @return the surveys
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "survey_id", nullable = false)
    public Surveys getSurveys() {
        return surveys;
    }

    /**
     * @param surveys the surveys to set
     */
    public void setSurveys(Surveys surveys) {
        this.surveys = surveys;
    }


}
