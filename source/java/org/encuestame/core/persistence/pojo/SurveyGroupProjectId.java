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
 * SurveyGroupProjectId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class SurveyGroupProjectId implements java.io.Serializable {

    private int sgId;
    private int proyectId;

    public SurveyGroupProjectId() {
    }

    public SurveyGroupProjectId(int sgId, int proyectId) {
        this.sgId = sgId;
        this.proyectId = proyectId;
    }

    @Column(name = "sg_id", nullable = false)
    public int getSgId() {
        return this.sgId;
    }

    public void setSgId(int sgId) {
        this.sgId = sgId;
    }

    @Column(name = "proyect_id", nullable = false)
    public int getProyectId() {
        return this.proyectId;
    }

    public void setProyectId(int proyectId) {
        this.proyectId = proyectId;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SurveyGroupProjectId))
            return false;
        SurveyGroupProjectId castOther = (SurveyGroupProjectId) other;

        return (this.getSgId() == castOther.getSgId())
                && (this.getProyectId() == castOther.getProyectId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSgId();
        result = 37 * result + this.getProyectId();
        return result;
    }

}
