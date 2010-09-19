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
package org.encuestame.core.persistence.pojo.notifications;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.core.persistence.pojo.SecUser;

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

    private String description;

    private SecUser secUser;

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
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the secUser
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUser getSecUser() {
        return secUser;
    }

    /**
     * @param secUser
     *            the secUser to set
     */
    public void setSecUser(SecUser secUser) {
        this.secUser = secUser;
    }
}