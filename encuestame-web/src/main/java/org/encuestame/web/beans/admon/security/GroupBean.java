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
package org.encuestame.web.beans.admon.security;

import java.io.Serializable;
import java.util.List;

import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.web.beans.admon.AdmonBean;

/**
 * Group Administrator Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
  * @version $Id$
 */

public class GroupBean extends AdmonBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7084928777413961605L;

    /** New {@link UnitGroupBean}. **/
    private UnitGroupBean newGroup = new UnitGroupBean();

    /** List of {@link UnitGroupBean}. **/
    private List<UnitGroupBean> listUnitGroupBeans;

    /** Processed Group Id. **/
    private Integer processedGroupId;

    /**
     * Delete Group.
     */
    public final void deleteGroup() {
        try {
            if (getProcessedGroupId() != null) {
                log.info("deleting group..");
                getServicemanager().getApplicationServices().getSecurityService().deleteGroup(
                        getNewGroup().getId());
                reset();
            } else {
                addErrorMessage(getMessageProperties("errorDelteGroup"),
                        "errorDelteGroup");
            }
        } catch (Exception e) {
            addErrorMessage(getMessageProperties("errorDelteGroup"), e
                    .getMessage());
            log.error(e.getMessage());
        }
    }

    /**
     * Reset Bean.
     */
    public final void reset() {
        newGroup.setId(null);
        newGroup.setGroupDescription(null);
        newGroup.setGroupName(null);
        newGroup.setStateId(null);
    }

    /**
     * Getter.
     * @return {@link UnitGroupBean}
     */
    public final UnitGroupBean getNewGroup() {
        return newGroup;
    }

    /**
     * Setter.
     * @param newGroup new group
     */
    public final void setNewGroup(UnitGroupBean newGroup) {
        this.newGroup = newGroup;
    }

    /**
     * Getter.
     * @return list of {@link UnitGroupBean}
     */
    public final List<UnitGroupBean> getListUnitGroupBeans() {
        return listUnitGroupBeans;
    }

    /**
     * Setter.
     * @param listUnitBeans list of {@link UnitGroupBean}
     */
    public final void setListUnitGroupBeans(List<UnitGroupBean> listUnitBeans) {
        this.listUnitGroupBeans = listUnitBeans;
    }



    /**
     * Setter.
     * @return processed group id
     */
    public final Integer getProcessedGroupId() {
        log.info("getProcessedGroupId " + processedGroupId);
        return processedGroupId;
    }

    /**
     * Setter.
     * @param processedGroupId processed group id
     */
    public final void setProcessedGroupId(Integer processedGroupId) {
          this.processedGroupId = processedGroupId;
    }

}
