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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Emails Catalog.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "email",
        uniqueConstraints = {@UniqueConstraint(columnNames={"email"})})
public class Email {

    private Long idEmail;
     private String email;
     private EmailList idListEmail;
     private Boolean subscribed = false;
     private Date created_at;
     private String emailAccount;

     /**
     * @return the idEmail
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "email_id", unique = true, nullable = false)
    public Long getIdEmail() {
        return this.idEmail;
    }

    /**
     * @param idEmail the idEmail to set
     */
    public void setIdEmail(Long idEmail) {
        this.idEmail = idEmail;
    }

    /**
     * @return the email
     */
     @Column(name = "email", nullable = false)
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the idListEmail
     */
       @ManyToOne(cascade = CascadeType.MERGE)
       @JoinColumn(name = "id_list", nullable = true)
    public EmailList getIdListEmail() {
        return this.idListEmail;
    }

    /**
     * @param idListEmail the idListEmail to set
     */
    public void setIdListEmail(EmailList idListEmail) {
        this.idListEmail = idListEmail;
    }

    /**
     * @return the subscribed
     */
    @Column(name = "subscribed", nullable = false)
    public Boolean getSubscribed() {
        return subscribed;
    }

    /**
     * @param subscribed the subscribed to set
     */
    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    /**
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * @param createdAt the created_at to set
     */
    public void setCreated_at(Date createdAt) {
        created_at = createdAt;
    }

    /**
     * @return the emailAccount
     */
    public String getEmailAccount() {
        return emailAccount;
    }

    /**
     * @param emailAccount the emailAccount to set
     */
    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

}
