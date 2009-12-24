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

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.web.beans.MasterBean;

/**
 * Unit Group Bean.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since  11/05/2009 16:34:01
 * @version $Id$
 */

public class UnitGroupBean extends MasterBean implements Serializable {


    private static final long serialVersionUID = -3303088512430614308L;
    private Integer id;
    private String groupName;
    private String groupDescription;
    private String stateId;
    private Log log = LogFactory.getLog(this.getClass());



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return the groupDescription
     */
    public String getGroupDescription() {
        return groupDescription;
    }
    /**
     * @param groupDescription the groupDescription to set
     */
    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
    /**
     * @return the stateId
     */
    public String getStateId() {
        return stateId;
    }
    /**
     * @param stateId the stateId to set
     */
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }




}
