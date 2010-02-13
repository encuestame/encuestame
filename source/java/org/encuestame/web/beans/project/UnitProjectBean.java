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
package org.encuestame.web.beans.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.encuestame.web.beans.MasterBean;

/**
 * Unit Project Bean.
 *
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 28/05/2009 11:47:51
 * @version $Id$
 */
public class UnitProjectBean extends MasterBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -9098305021342831224L;

    private Long id;
    private String name;
    private Date dateInit;
    private Date dateFinish;
    private Long state;
    private String description;
    private List<SelectItem> clients = new ArrayList<SelectItem>();
    private List<SelectItem> groupList = new ArrayList<SelectItem>();
    private List<SelectItem> locationList = new ArrayList<SelectItem>();
    private List<SelectItem> projectPermissions = new ArrayList<SelectItem>();
    private Long leader;
    private String priority;
    private Long status;
    private Boolean hide = false;
    private Boolean notify = false;
    private Long userId;

    /**
     * List of Users.
     */
    private List<SelectItem> listUsers;


    /**
     * Cat State Bean List.
     */
    private List<SelectItem> catStateBeans;


    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dateInit
     */
    public Date getDateInit() {
        return dateInit;
    }

    /**
     * @param dateInit
     *            the dateInit to set
     */
    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    /**
     * @return the dateFinish
     */
    public Date getDateFinish() {
        return dateFinish;
    }

    /**
     * @param dateFinish
     *            the dateFinish to set
     */
    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    /**
     * @return the state
     */
    public Long getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(Long state) {
        this.state = state;
    }

    /**
     * @return the description
     */
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
     * @return the clients
     */
    public List<SelectItem> getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(final List<SelectItem> clients) {
        this.clients = clients;
    }

    /**
     * @return the catStateBeans
     */
    public List<SelectItem> getCatStateBeans() {
        return catStateBeans;
    }

    /**
     * @param catStateBeans the catStateBeans to set
     */
    public void setCatStateBeans(final List<SelectItem> catStateBeans) {
        this.catStateBeans = catStateBeans;
    }

    /**
     * @return the listUsers
     */
    public List<SelectItem> getListUsers() {
        return listUsers;
    }

    /**
     * @param listUsers the listUsers to set
     */
    public void setListUsers(final List<SelectItem> listUsers) {
        this.listUsers = listUsers;
    }

    /**
     * @return the groupList
     */
    public List<SelectItem> getGroupList() {
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(final List<SelectItem> groupList) {
        this.groupList = groupList;
    }

    /**
     * @return the locationList
     */
    public List<SelectItem> getLocationList() {
        return locationList;
    }

    /**
     * @param locationList the locationList to set
     */
    public void setLocationList(final List<SelectItem> locationList) {
        this.locationList = locationList;
    }

    /**
     * @return the projectPermissions
     */
    public List<SelectItem> getProjectPermissions() {
        return projectPermissions;
    }

    /**
     * @param projectPermissions the projectPermissions to set
     */
    public void setProjectPermissions(final List<SelectItem> projectPermissions) {
        this.projectPermissions = projectPermissions;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(final String priority) {
        this.priority = priority;
    }

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Long status) {
        this.status = status;
    }

    /**
     * @return the hide
     */
    public Boolean getHide() {
        return hide;
    }

    /**
     * @param hide the hide to set
     */
    public void setHide(final Boolean hide) {
        this.hide = hide;
    }

    /**
     * @return the notify
     */
    public Boolean getNotify() {
        return notify;
    }

    /**
     * @param notify the notify to set
     */
    public void setNotify(final Boolean notify) {
        this.notify = notify;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the leader
     */
    public Long getLeader() {
        return leader;
    }

    /**
     * @param leader the leader to set
     */
    public void setLeader(final Long leader) {
        this.leader = leader;
    }

}
