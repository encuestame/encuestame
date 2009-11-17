package org.encuestame.web.beans.location;

import java.io.Serializable;

import org.encuestame.web.beans.MasterBean;

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
 * Id: TerritoryBean.java Date: 26/05/2009 12:38:47
 * @author juanpicado
 * package: org.encuestame.web.beans.territory
 * @version 1.0
 */
public class LocationBean extends MasterBean implements Serializable{

    private static final long serialVersionUID = -9098305021342831224L;
    private Long locateId;
    //private String tidtype;
    private String description;
    private Integer level;
    private String active;
    private Float lat;
    private Float lng;


    public Long getLocateId() {
        return locateId;
    }
    public void setLocateId(Long locateId) {
        this.locateId = locateId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getActive() {
        return active;
    }
    public void setActive(String active) {
        this.active = active;
    }
    public Float getLat() {
        return lat;
    }
    public void setLat(Float lat) {
        this.lat = lat;
    }
    public Float getLng() {
        return lng;
    }
    public void setLng(Float lng) {
        this.lng = lng;
    }


}
