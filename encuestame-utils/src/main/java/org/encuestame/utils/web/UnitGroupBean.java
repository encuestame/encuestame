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

import java.io.Serializable;


/**
 * Unit Group Bean.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since  11/05/2009 16:34:01
 * @version $Id$
 */

public class UnitGroupBean implements Serializable {


    private static final long serialVersionUID = -3303088512430614308L;
    private Long id;
    private String groupName;
    private String groupDescription;
    private String stateId;



    public final Long getId() {
        return id;
    }


    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the groupName
     */
    public final String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName the groupName to set
     */
    public final void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return the groupDescription
     */
    public final String getGroupDescription() {
        return groupDescription;
    }
    /**
     * @param groupDescription the groupDescription to set
     */
    public final void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
    /**
     * @return the stateId
     */
    public final String getStateId() {
        return stateId;
    }
    /**
     * @param stateId the stateId to set
     */
    public final void setStateId(String stateId) {
        this.stateId = stateId;
    }
}
