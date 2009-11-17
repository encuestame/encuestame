/**
 * encuestame: system online surveys Copyright (C) 2005-2009 encuestame
 * Development Team
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
 * this program; if not, writes to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.web.beans.location;

import java.io.Serializable;
import java.util.Date;

import org.encuestame.web.beans.MasterBean;

/**
 * Description.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since  16/11/2009 21:25:07
 */
public class LocationTypeBean extends MasterBean implements Serializable{

    private static final long serialVersionUID = -9098305021342831224L;
    private String description;
    private Integer level;
    private Long locationTypeId;


    public String getDescription() {
        return this.description;
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



}
