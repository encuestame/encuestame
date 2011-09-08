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

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.AbstractHit;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * Survey hits.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */

@Entity
@Table(name = "survey_hits")
@Indexed(index="SurveyHits")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SurveyHits extends AbstractHit {


    /** Id. **/
    private Long id;

    /** {@link TweetPoll} **/
    private Survey survey;

    /**
     * @return the id
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "survey_hit_id", unique = true, nullable = true)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
}
