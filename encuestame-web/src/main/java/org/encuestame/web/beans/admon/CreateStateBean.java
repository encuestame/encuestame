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
package org.encuestame.web.beans.admon;
/**
 * Create State Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009 11:35:01
 * @version $Id$
 */
public class CreateStateBean {

    /**
     * Id estado.
     */
    private Integer idestado;
    private String description;
    private String image;
    /**
     *
     */
    public CreateStateBean() {
        // TODO Auto-generated constructor stub
    }
    /**
     * @return the idestado
     */
    public final Integer getIdestado() {
        return idestado;
    }
    /**
     * @param idestado the idestado to set
     */
    public final void setIdestado(final Integer idestado) {
        this.idestado = idestado;
    }
    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public final void setDescription(final String description) {
        this.description = description;
    }
    /**
     * @return the image
     */
    public final String getImage() {
        return image;
    }
    /**
     * @param image the image to set
     */
    public final void setImage(final String image) {
        this.image = image;
    }

}
