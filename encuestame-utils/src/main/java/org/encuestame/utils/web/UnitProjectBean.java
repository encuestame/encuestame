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
package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Unit Project Bean.
 *
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 28/05/2009 11:47:51
 * @version $Id$
 */
public class UnitProjectBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -9098305021342831224L;

    private Long id;
    private String name;
    private Date dateInit;
    private Date dateFinish;
    private String formatedDateInit;
    private String formatedDateFinish;
    private String state;
    private String description;
    private String projectInfo;
    private List<SelectItem> clients = new ArrayList<SelectItem>();
    private List<SelectItem> groupList = new ArrayList<SelectItem>();
    private List<SelectItem> locationList = new ArrayList<SelectItem>();
    private List<SelectItem> projectPermissions = new ArrayList<SelectItem>();
    private List<UnitLocationBean> unitLocationBeans = new ArrayList<UnitLocationBean>();
    private Long leader;
    private String priority;
    private Long status;
    private Boolean hide;
    private Boolean notify;
    private Long userId;
    private Boolean published;

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
    public final Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dateInit
     */
    public final Date getDateInit() {
        return dateInit;
    }

    /**
     * @param dateInit
     *            the dateInit to set
     */
    public final void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    /**
     * @return the dateFinish
     */
    public final Date getDateFinish() {
        return dateFinish;
    }

    /**
     * @param dateFinish
     *            the dateFinish to set
     */
    public final void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    /**
     * @return the state            <h:outputText value="#{pro.description}" />
     */
    public final String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public final void setState(String state) {
        this.state = state;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the clients
     */
    public final List<SelectItem> getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public final void setClients(final List<SelectItem> clients) {
        this.clients = clients;
    }

    /**
     * @return the catStateBeans
     */
    public final List<SelectItem> getCatStateBeans() {
        return catStateBeans;
    }

    /**
     * @param catStateBeans the catStateBeans to set
     */
    public final void setCatStateBeans(final List<SelectItem> catStateBeans) {
        this.catStateBeans = catStateBeans;
    }

    /**
     * @return the listUsers
     */
    public final List<SelectItem> getListUsers() {
        return listUsers;
    }

    /**
     * @param listUsers the listUsers to set
     */
    public final void setListUsers(final List<SelectItem> listUsers) {
        this.listUsers = listUsers;
    }

    /**
     * @return the groupList
     */
    public final List<SelectItem> getGroupList() {
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public final void setGroupList(final List<SelectItem> groupList) {
        this.groupList = groupList;
    }

    /**
     * @return the locationList
     */
    public final List<SelectItem> getLocationList() {
        return locationList;
    }

    /**
     * @param locationList the locationList to set
     */
    public final void setLocationList(final List<SelectItem> locationList) {
        this.locationList = locationList;
    }

    /**
     * @return the projectPermissions
     */
    public final List<SelectItem> getProjectPermissions() {
        return projectPermissions;
    }

    /**
     * @param projectPermissions the projectPermissions to set
     */
    public final void setProjectPermissions(final List<SelectItem> projectPermissions) {
        this.projectPermissions = projectPermissions;
    }

    /**
     * @return the priority
     */
    public final String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public final void setPriority(final String priority) {
        this.priority = priority;
    }

    /**
     * @return the status
     */
    public final Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public final void setStatus(final Long status) {
        this.status = status;
    }

    /**
     * @return the hide
     */
    public final Boolean getHide() {
        return hide;
    }

    /**
     * @param hide the hide to set
     */
    public final void setHide(final Boolean hide) {
        this.hide = hide;
    }

    /**
     * @return the notify
     */
    public final Boolean getNotify() {
        return notify;
    }

    /**
     * @param notify the notify to set
     */
    public final void setNotify(final Boolean notify) {
        this.notify = notify;
    }

    /**
     * @return the userId
     */
    public final Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public final void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the leader
     */
    public final Long getLeader() {
        return leader;
    }

    /**
     * @param leader the leader to set
     */
    public final void setLeader(final Long leader) {
        this.leader = leader;
    }

    /**
     * @return the projectInfo
     */
    public String getProjectInfo() {
        return projectInfo;
    }

    /**
     * @param projectInfo the projectInfo to set
     */
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    /**
     * @return the published
     */
    public Boolean getPublished() {
        return published;
    }

    /**
     * @param published the published to set
     */
    public void setPublished(Boolean published) {
        this.published = published;
    }

    /**
     * @return the formatedDateInit
     */
    public String getFormatedDateInit() {
        return formatedDateInit;
    }

    /**
     * @param formatedDateInit the formatedDateInit to set
     */
    public void setFormatedDateInit(String formatedDateInit) {
        this.formatedDateInit = formatedDateInit;
    }

    /**
     * @return the formatedDateFinish
     */
    public String getFormatedDateFinish() {
        return formatedDateFinish;
    }

    /**
     * @param formatedDateFinish the formatedDateFinish to set
     */
    public void setFormatedDateFinish(String formatedDateFinish) {
        this.formatedDateFinish = formatedDateFinish;
    }

    /**
     * @return the unitLocationBeans
     */
    public List<UnitLocationBean> getUnitLocationBeans() {
        return unitLocationBeans;
    }

    /**
     * @param unitLocationBeans the unitLocationBeans to set
     */
    public void setUnitLocationBeans(List<UnitLocationBean> unitLocationBeans) {
        this.unitLocationBeans = unitLocationBeans;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UnitProjectBean [catStateBeans=").append(catStateBeans)
                .append(", clients=").append(clients).append(", dateFinish=")
                .append(dateFinish).append(", dateInit=").append(dateInit)
                .append(", description=").append(description).append(
                        ", formatedDateFinish=").append(formatedDateFinish)
                .append(", formatedDateInit=").append(formatedDateInit).append(
                        ", groupList=").append(groupList).append(", hide=")
                .append(hide).append(", id=").append(id).append(", leader=")
                .append(leader).append(", listUsers=").append(listUsers)
                .append(", locationList=").append(locationList).append(
                        ", name=").append(name).append(", notify=").append(
                        notify).append(", priority=").append(priority).append(
                        ", projectInfo=").append(projectInfo).append(
                        ", projectPermissions=").append(projectPermissions)
                .append(", published=").append(published).append(", state=")
                .append(state).append(", status=").append(status).append(
                        ", unitLocationBeans=").append(unitLocationBeans)
                .append(", userId=").append(userId).append("]");
        return builder.toString();
    }

}
