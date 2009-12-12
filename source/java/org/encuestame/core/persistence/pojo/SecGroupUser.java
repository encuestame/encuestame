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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SecGroupUser.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "sec_group_user")
public class SecGroupUser {

    private SecGroupUserId secGroupUserId;
    private SecGroups secGroups;
    private SecUserSecondary secUsers;
    private Boolean state;

    /**
     * @return id
     */
    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "groupId", column = @Column(name = "group_id", nullable = false)),
            @AttributeOverride(name = "uid", column = @Column(name = "uid", nullable = false)) })
    public SecGroupUserId getSecGroupUserId() {
        return this.secGroupUserId;
    }

    /**
     * @param secGroupUserId secGroupUserId
     */
    public void setSecGroupUserId(final SecGroupUserId secGroupUserId) {
        this.secGroupUserId = secGroupUserId;
    }

    /**
     * @return secGroups
     */
    @ManyToOne()
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    public SecGroups getSecGroups() {
        return this.secGroups;
    }

    /**
     * @param secGroups secGroups
     */
    public void setSecGroups(final SecGroups secGroups) {
        this.secGroups = secGroups;
    }

    /**
     * @return secUsers
     */
    @ManyToOne()
    @JoinColumn(name = "uid", nullable = false, insertable = false, updatable = false)
    public SecUserSecondary getSecUsers() {
        return this.secUsers;
    }

    /**
     * @param secUsers secUsers
     */
    public void setSecUsers(final SecUserSecondary secUsers) {
        this.secUsers = secUsers;
    }

    /**
     * @return state
     */
    @Column(name = "state")
    public Boolean getState() {
        return this.state;
    }

    /**
     * @param state state
     */
    public void setState(final Boolean state) {
        this.state = state;
    }

}
