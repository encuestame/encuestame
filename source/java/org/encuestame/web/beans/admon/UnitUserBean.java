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
package org.encuestame.web.beans.admon;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.encuestame.web.beans.MasterBean;

/**
 * Unit Use rBean.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
public class UnitUserBean extends MasterBean implements Serializable {

    private static final long serialVersionUID = -6690522000664394521L;
    private Integer id;
    private String email;
    private String name;
    private String username;
    private Boolean status;
    private String password;
    private String inviteCode;
    private Date dateNew;
    private Long primaryUserId;
    private Boolean publisher;
    private Collection<UnitGroupBean> listGroups;
    private Collection<UnitPermission> listPermission;
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the inviteCode
     */
    public String getInviteCode() {
        return inviteCode;
    }
    /**
     * @param inviteCode the inviteCode to set
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
    /**
     * @return the dateNew
     */
    public Date getDateNew() {
        return dateNew;
    }
    /**
     * @param dateNew the dateNew to set
     */
    public void setDateNew(Date dateNew) {
        this.dateNew = dateNew;
    }
    /**
     * @return the publisher
     */
    public Boolean getPublisher() {
        return publisher;
    }
    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(Boolean publisher) {
        this.publisher = publisher;
    }
    /**
     * @return the listGroups
     */
    public Collection<UnitGroupBean> getListGroups() {
        return listGroups;
    }
    /**
     * @param listGroups the listGroups to set
     */
    public void setListGroups(Collection<UnitGroupBean> listGroups) {
        this.listGroups = listGroups;
    }
    /**
     * @return the listPermission
     */
    public Collection<UnitPermission> getListPermission() {
        return listPermission;
    }
    /**
     * @param listPermission the listPermission to set
     */
    public void setListPermission(Collection<UnitPermission> listPermission) {
        this.listPermission = listPermission;
    }
    /**
     * @return the primaryUserId
     */
    public Long getPrimaryUserId() {
        return primaryUserId;
    }
    /**
     * @param primaryUserId the primaryUserId to set
     */
    public void setPrimaryUserId(Long primaryUserId) {
        this.primaryUserId = primaryUserId;
    }

}
