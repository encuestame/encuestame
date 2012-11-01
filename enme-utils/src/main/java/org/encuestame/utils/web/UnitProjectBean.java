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
package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Unit Project Bean.
 * @author Picado, Juan Carlos juanATencuestame.org
 * @since May 28 2009 11:47:51
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
    private List<UnitLocationBean> unitLocationBeans = new ArrayList<UnitLocationBean>();
    private Long leader;
    private String priority;
    private Long status;
    private Boolean hide;
    private Boolean notify;
    private Long userId;
    private Boolean published;

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
    public final void setId(final Long id) {
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
    public final void setName(final String name) {
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
    public final void setDateInit(final Date dateInit) {
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
    public final void setDateFinish(final Date dateFinish) {
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
    public final void setState(final String state) {
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
    public final void setDescription(final String description) {
        this.description = description;
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
    public void setProjectInfo(final String projectInfo) {
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
    public void setPublished(final Boolean published) {
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
    public void setFormatedDateInit(final String formatedDateInit) {
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
    public void setFormatedDateFinish(final String formatedDateFinish) {
        this.formatedDateFinish = formatedDateFinish;
    }

}
