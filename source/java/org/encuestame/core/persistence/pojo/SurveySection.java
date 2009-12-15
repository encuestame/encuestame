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
 * SurveySection.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version  $Id$
 */
@Entity
@Table(name = "survey_section")
public class SurveySection implements java.io.Serializable {

    private Long ssid;
    private CatState catState;
    private String descSection;

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
     * @return catState
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_state", nullable = false)
    public CatState getCatState() {
        return this.catState;
    }

    /**
     * @param catState catState
     */
    public void setCatState(CatState catState) {
        this.catState = catState;
    }

    /**
     * @return descSection
     */
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
}
