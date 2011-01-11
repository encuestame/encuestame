/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.notifications;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.security.Account;

/**
 * Notifications domain.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 5:53:55 PM
 * @version $Id:$
 */

@Entity
@Table(name = "notification")
public class Notification {

    private Long notificationId;

    private NotificationEnum description;

    private String additionalDescription;

    private Account account;

    private Date created = Calendar.getInstance().getTime();

    private Boolean readed = Boolean.FALSE;

    /**
     * @return the notificationId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id", unique = true, nullable = false)
    public Long getNotificationId() {
        return notificationId;
    }

    /**
     * @param notificationId
     *            the notificationId to set
     */
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * @return the description
     */

    @Column(name = "description", nullable = false)
    @Enumerated(EnumType.STRING)
    public NotificationEnum getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(NotificationEnum description) {
        this.description = description;
    }

    /**
     * @return the secUser
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public Account getAccount() {
        return account;
    }

    /**
     * @param secUser
     *            the secUser to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the created
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the rearded
     */

    @Column(name = "readed", nullable = false)
    public Boolean getReaded() {
        return readed;
    }

    /**
     * @param rearded the rearded to set
     */
    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    /**
     * @return the additionalDescription
     */
    @Column(name = "additional_description", nullable = false)
    public String getAdditionalDescription() {
        return additionalDescription;
    }

    /**
     * @param additionalDescription the additionalDescription to set
     */
    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }
}