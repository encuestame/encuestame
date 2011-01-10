/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Subscribe Emails Catalog.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  July 20, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "emailSubscribe")
public class EmailSubscribe {

    /****/
    private Long idSubscribe;

    /****/
    private String hashCode;

    /****/
    private Email email;

    /****/
    private EmailList list;

    /**
     * @return the idSubscribe
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_subscribe", unique = true, nullable = false)
    public Long getIdSubscribe() {
        return idSubscribe;
    }

    /**
     * @param idSubscribe the idSubscribe to set
     */
    public void setIdSubscribe(Long idSubscribe) {
        this.idSubscribe = idSubscribe;
    }

    /**
     * @return the hashCode
     */
    @Column(name = "hashCode", nullable = false)
    public String getHashCode() {
        return hashCode;
    }

    /**
     * @param hashCode the hashCode to set
     */
    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    /**
     * @return the emailId
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "email_id", nullable = false)
    public Email getEmail() {
        return email;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * @return the listId
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_list", nullable = false)
    public EmailList getList() {
        return list;
    }

    /**
     * @param listId the listId to set
     */
    public void setList(EmailList list) {
        this.list = list;
    }
}
