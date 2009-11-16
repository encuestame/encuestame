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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CatLocationUser.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Entity
@Table(name = "cat_location_user")
public class CatLocationUser implements Serializable {

    private CatLocationUserId id;
    private SecUsers secUsers;
    private Boolean state;

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "locationId", column = @Column(name = "location_id", nullable = false)),
            @AttributeOverride(name = "uid", column = @Column(name = "uid", nullable = false)) })
    public CatLocationUserId getId() {
        return this.id;
    }

    public void setId(CatLocationUserId id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "uid", nullable = false, insertable = false, updatable = false)
    public SecUsers getSecUsers() {
        return this.secUsers;
    }

    public void setSecUsers(SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    @Column(name = "state")
    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
