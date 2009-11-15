package org.encuestame.web.beans.project;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.admon.UnitGroupBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.location.UnitLocationBean;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: UnitProjectBean.java Date: 28/05/2009 11:47:51
 * @author juanpicado
 * package: org.encuestame.web.beans.project
 * @version 1.0
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
    private Integer id;
    private Collection<UnitGroupBean> listGroups;
    private Collection<UnitLocationBean> listLocations;
    private Collection<UnitGroupBean> listUsers;
    private Collection<UnitPermission> listGroupsSurveys;





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
     * @return the dateInit
     */
    public Date getDateInit() {
        return dateInit;
    }
    /**
     * @param dateInit the dateInit to set
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
     * @param dateFinish the dateFinish to set
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
     * @param state the state to set
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
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }




}
