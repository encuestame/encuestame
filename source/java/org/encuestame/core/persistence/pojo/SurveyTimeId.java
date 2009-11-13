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
 * SurveyTimeId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class SurveyTimeId implements java.io.Serializable {

    private Integer sequence;
    private Long sid;

    public SurveyTimeId() {
    }

    public SurveyTimeId(Integer sequence, Long sid) {
        this.sequence = sequence;
        this.sid = sid;
    }

    @Column(name = "sequence", nullable = false)
    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Column(name = "sid", nullable = false)
    public Long getSid() {
        return this.sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SurveyTimeId))
            return false;
        SurveyTimeId castOther = (SurveyTimeId) other;

        return (this.getSequence() == castOther.getSequence())
                && (this.getSid() == castOther.getSid());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSequence();
        result = (int) (37 * result +  this.getSid());
        return result;
    }

}
