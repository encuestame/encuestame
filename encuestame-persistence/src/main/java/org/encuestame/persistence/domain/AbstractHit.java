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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * Abstract folder.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */
@MappedSuperclass
public abstract class AbstractHit {

    /** **/
    private Date hitDate;

    /** **/
    private String ipAddress;

    /**
     * @return the hitDate
     */
    public Date getHitDate() {
        return hitDate;
    }

    /**
     * @param hitDate the hitDate to set
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    public void setHitDate(final Date hitDate) {
        this.hitDate = hitDate;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "hits_ip_address", nullable = false, length = 100)
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
