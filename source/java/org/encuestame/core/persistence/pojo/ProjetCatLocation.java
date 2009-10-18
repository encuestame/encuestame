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

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ProjetCatLocation.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "projet_cat_location")
public class ProjetCatLocation implements Serializable {

    private ProjetCatLocationId id;
    private Boolean state;

    public ProjetCatLocation() {
    }

    public ProjetCatLocation(ProjetCatLocationId id) {
        this.id = id;
    }

    public ProjetCatLocation(ProjetCatLocationId id, Boolean state) {
        this.id = id;
        this.state = state;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "locateId", column = @Column(name = "locate_id", nullable = false)),
            @AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false)) })
    public ProjetCatLocationId getId() {
        return this.id;
    }

    public void setId(ProjetCatLocationId id) {
        this.id = id;
    }

    @Column(name = "state")
    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
