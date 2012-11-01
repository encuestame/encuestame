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
package org.encuestame.persistence.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.encuestame.persistence.domain.security.UserAccount;

/**
 * Rate items.
 * @author Morales Diana, Paola paolaATencuestame.org
 * @since September 13, 2011
 */
@MappedSuperclass
public abstract class Rate {

    /** **/
    private Boolean rate;

    /** **/
    private UserAccount user;

    /**
     * @return the rate
     */
    @Column(name = "status" )
    public Boolean getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(Boolean rate) {
        this.rate = rate;
    }

    /**
     * @return the user
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public UserAccount getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserAccount user) {
        this.user = user;
    }
}
