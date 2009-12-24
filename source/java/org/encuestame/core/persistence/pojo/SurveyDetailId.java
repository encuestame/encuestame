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

// Generated 17/10/2009 03:12:15 PM by Hibernate Tools 3.2.5.Beta

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SurveyDetailId.
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Embeddable
public class SurveyDetailId implements java.io.Serializable {

    private Long idSd;
    private Long qid;
    private Long idSidFormat;
    private Long ssid;

    public SurveyDetailId() {
    }

    public SurveyDetailId(long idSd, Long qid, Long idSidFormat, Long ssid) {
        this.idSd = idSd;
        this.qid = qid;
        this.idSidFormat = idSidFormat;
        this.ssid = ssid;
    }

    @Column(name = "id_sd", nullable = false)
    public long getIdSd() {
        return this.idSd;
    }

    public void setIdSd(long idSd) {
        this.idSd = idSd;
    }

    @Column(name = "qid", nullable = false)
    public Long getQid() {
        return this.qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    @Column(name = "id_sid_format", nullable = false)
    public Long getIdSidFormat() {
        return this.idSidFormat;
    }

    public void setIdSidFormat(Long idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    @Column(name = "ssid", nullable = false)
    public Long getSsid() {
        return this.ssid;
    }

    public void setSsid(Long ssid) {
        this.ssid = ssid;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SurveyDetailId))
            return false;
        SurveyDetailId castOther = (SurveyDetailId) other;

        return (this.getIdSd() == castOther.getIdSd())
                && (this.getQid() == castOther.getQid())
                && (this.getIdSidFormat() == castOther.getIdSidFormat())
                && (this.getSsid() == castOther.getSsid());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (int) this.getIdSd();
        result = (int) (37 * result + this.getQid());
        result = (int) (37 * result + this.getIdSidFormat());
        result = (int) (37 * result + this.getSsid());
        return result;
    }

}
