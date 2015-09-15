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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.enums.Status;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * Abstract folder.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since
 */
@MappedSuperclass
public abstract class AbstractFolder {

    /**
     * Folder Name.
     * **/
    private String folderName;

    /**
     * Context of users.
     * */
    private Account users;

    /**
     * Date of creation.
     * **/
    private Date createdAt;

    /**
     * Create by.
     */
    private UserAccount createdBy;

    /**
     * Status of folder.
     */
    private Status status;

    /**
     * @return the folderName
     */
    @Field(index = Index.YES, store = Store.YES)
    @Column(name = "folderName", nullable = false)
    public String getFolderName() {
        return folderName;
    }

    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * @return the users
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public Account getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Account users) {
        this.users = users;
    }

    /**
     * @return the createdAt
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the createdBy
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public UserAccount getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final UserAccount createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the status
     */
    @Column(name="folder_status")
    @Enumerated(EnumType.ORDINAL)
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Status status) {
        this.status = status;
    }
}
