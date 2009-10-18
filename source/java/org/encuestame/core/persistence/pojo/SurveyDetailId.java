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

// Generated 17/10/2009 03:12:15 PM by Hibernate Tools 3.2.5.Beta

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SurveyDetailId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class SurveyDetailId implements java.io.Serializable {

    private long idSd;
    private int qid;
    private int idSidFormat;
    private int ssid;

    public SurveyDetailId() {
    }

    public SurveyDetailId(long idSd, int qid, int idSidFormat, int ssid) {
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
    public int getQid() {
        return this.qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    @Column(name = "id_sid_format", nullable = false)
    public int getIdSidFormat() {
        return this.idSidFormat;
    }

    public void setIdSidFormat(int idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    @Column(name = "ssid", nullable = false)
    public int getSsid() {
        return this.ssid;
    }

    public void setSsid(int ssid) {
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
        result = 37 * result + this.getQid();
        result = 37 * result + this.getIdSidFormat();
        result = 37 * result + this.getSsid();
        return result;
    }

}
