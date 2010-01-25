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
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.location.UnitLocationBean;

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
    private String name;
    private Date dateInit;
    private Date dateFinish;
    private Long state;
    private String description;
    private Long id;
    private List<SelectItem> clients = new ArrayList<SelectItem>();
    private Collection<UnitGroupBean> listGroups;
    private Collection<UnitLocationBean> listLocations;
    private Collection<UnitGroupBean> listUsers;
    private Collection<UnitPermission> listGroupsSurveys;

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

}
