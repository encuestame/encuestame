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
package org.encuestame.core.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CatState.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "cat_state")
public class CatState {

    private Long idState;
    private String descState;
    private String stateImage;

    /**
     * @return idState
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_state", unique = true, nullable = false)
    public Long getIdState() {
        return this.idState;
    }

    /**
     * @param idState idState
     */
    public void setIdState(final Long idState) {
        this.idState = idState;
    }

    /**
     * @return descState
     */
    @Column(name = "desc_state")
    public String getDescState() {
        return this.descState;
    }

    /**
     * @param descState descState
     */
    public void setDescState(final String descState) {
        this.descState = descState;
    }

    /**
     * @return stateImage
     */
    @Column(name = "image")
    public String getStateImage() {
        return this.stateImage;
    }

    /**
     * @param stateImage stateImage
     */
    public void setStateImage(final String stateImage) {
        this.stateImage = stateImage;
    }
}
