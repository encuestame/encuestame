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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SurveyFormatGroupId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class SurveyFormatGroupId implements java.io.Serializable {

    private int sgId;
    private int idSidFormat;

    public SurveyFormatGroupId() {
    }

    public SurveyFormatGroupId(int sgId, int idSidFormat) {
        this.sgId = sgId;
        this.idSidFormat = idSidFormat;
    }

    @Column(name = "sg_id", nullable = false)
    public int getSgId() {
        return this.sgId;
    }

    public void setSgId(int sgId) {
        this.sgId = sgId;
    }

    @Column(name = "id_sid_format", nullable = false)
    public int getIdSidFormat() {
        return this.idSidFormat;
    }

    public void setIdSidFormat(int idSidFormat) {
        this.idSidFormat = idSidFormat;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SurveyFormatGroupId))
            return false;
        SurveyFormatGroupId castOther = (SurveyFormatGroupId) other;

        return (this.getSgId() == castOther.getSgId())
                && (this.getIdSidFormat() == castOther.getIdSidFormat());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSgId();
        result = 37 * result + this.getIdSidFormat();
        return result;
    }

}
