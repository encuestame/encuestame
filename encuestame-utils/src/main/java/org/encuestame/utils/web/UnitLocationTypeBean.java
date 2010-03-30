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


/**
 * Description.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since 10/12/2009 16:15:07
 * @version $Id$
 **/
public class UnitLocationTypeBean {

    /** fdsfdsa. */
    private Long idLocType;
    private String locTypeDesc;
    private Integer level;

    /**
     * @return the idLocType
     */
    public final Long getIdLocType() {
        return idLocType;
    }

    /**
     * dfsfds.
     * @param idLocType the idLocType to set
     */
    public final void setIdLocType(final Long idLocType) {
        this.idLocType = idLocType;
    }

    /**
     * @return the locTypeDesc
     */
    public final String getLocTypeDesc() {
        return locTypeDesc;
    }

    /**
     * @param locTypeDesc
     *            the locTypeDesc to set
     */
    public final void setLocTypeDesc(String locTypeDesc) {
        this.locTypeDesc = locTypeDesc;
    }

    /**
     * @return the level
     */
    public final Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public final void setLevel(Integer level) {
        this.level = level;
    }

}
