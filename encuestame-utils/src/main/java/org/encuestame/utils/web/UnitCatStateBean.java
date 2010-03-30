/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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
 * Unit Cat State.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 6, 2010 2:35:57 AM
 * @version $Id: change to one dolar simbol
 */
public class UnitCatStateBean {

    /**
     * Id.
     */
    private Long id;

    /**
     * State.
     */
    private String state;

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
     * @return the state
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
}
