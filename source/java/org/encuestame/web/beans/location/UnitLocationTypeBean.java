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

import org.encuestame.web.beans.MasterBean;

/**
 * Description.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since 10/12/2009 16:15:07
 **/
public class UnitLocationTypeBean extends MasterBean {

    /** fdsfdsa. */
    private Long idLocType;
    private String locTypeDesc;
    private Integer level;

    /**
     * @return the idLocType
     */
    public Long getIdLocType() {
        return idLocType;
    }

    /**
     * dfsfds.
     * @param idLocType the idLocType to set
     */
    public void setIdLocType(final Long idLocType) {
        this.idLocType = idLocType;
    }

    /**
     * @return the locTypeDesc
     */
    public String getLocTypeDesc() {
        return locTypeDesc;
    }

    /**
     * @param locTypeDesc
     *            the locTypeDesc to set
     */
    public void setLocTypeDesc(String locTypeDesc) {
        this.locTypeDesc = locTypeDesc;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

}
