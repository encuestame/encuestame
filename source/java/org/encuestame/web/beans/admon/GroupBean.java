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

import java.util.LinkedList;
import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.web.beans.MasterBean;

/**
 * Group Administrator Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
  * @version $Id$
 */

public class GroupBean extends MasterBean {

    /** New {@link UnitGroupBean} **/
    private UnitGroupBean newGroup;
    /** List of {@link UnitGroupBean} **/
    private List<UnitGroupBean> listUnitGroupBeans;
    /** Processed Group Id **/
    private Integer processedGroupId;

    /**
     * Constructor.
     */
    public GroupBean() {
        isOneRow = true;
    }

    /**
     * Load list of Groups.
     * @return List of {@link UnitGroupBean}
     */
    private List<UnitGroupBean> getLoadListGroups() {
        listUnitGroupBeans = new LinkedList<UnitGroupBean>();
        try {
            final List<SecGroups> listGroups = getServicemanager().getApplicationServices()
                    .getSecurityService().getGroupDao().findAllGroups();
            for (SecGroups group : listGroups) {
                final UnitGroupBean newGroup = new UnitGroupBean();
                newGroup.setId(Integer.valueOf(group.getGroupId().toString()));
                newGroup.setGroupName(group.getGroupName());
                newGroup.setGroupDescription(group.getGroupDescriptionInfo());
                newGroup.setStateId((group.getIdState().toString()));
                listUnitGroupBeans.add(newGroup);
            }
        } catch (Exception e) {
            addErrorMessage(getMessageProperties("error_load_groups"), e
                    .getMessage());
            log.error("error load groups "+e.getMessage());
        }
        return listUnitGroupBeans;
    }


    /**
     * Create a Group.
     */
    public void newGroup() {
        try {
            log.info("new group");
            getServicemanager().getApplicationServices().getSecurityService().createGroup(
                    getNewGroup());
            addInfoMessage(getMessageProperties("susCreateNewGroup"), null);
            reset();
        } catch (Exception e) {
            addErrorMessage(getMessageProperties("errorCreateNewGroup"), e
                    .getMessage());
            log.error("error new group: "
                    + getMessageProperties("errorCreateNewGroup"));
        }
    }

    /**
     * Update Group.
     */
    public void updateGroup() {
        try {
            log.info("new group");
            getServicemanager().getApplicationServices().getSecurityService().updateGroup(
                    getNewGroup());
            addInfoMessage(getMessageProperties("susCreateNewGroup"), null);
            reset();
        } catch (Exception e) {
            addErrorMessage(getMessageProperties("errorCreateNewGroup"), e
                    .getMessage());
            log.error("error new group: "
                    + getMessageProperties("errorCreateNewGroup"));
        }
    }

    /**
     * Delete Group.
     */
    public void deleteGroup() {
        try {
            if (getProcessedGroupId() != null) {
                log.info("deleting group..");
                getServicemanager().getApplicationServices().getSecurityService().deleteGroup(
                        getNewGroup());
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
    public void reset() {
        newGroup.setId(null);
        newGroup.setGroupDescription(null);
        newGroup.setGroupName(null);
        newGroup.setStateId(null);
    }

    /**
     * Fill Table.
     * @param idGroup groupd id
     */
    private void fill(Integer idGroup) {
        try {
        final SecGroups secGroups = getServicemanager().getApplicationServices().getSecurityService()
                .getGroupDao().find(Long.valueOf(idGroup));
            if (secGroups != null) {
                reset();
                this.newGroup.setId(Integer.valueOf(secGroups.getGroupId().toString()));
                this.newGroup.setGroupDescription(secGroups.getGroupDescriptionInfo());
                this.newGroup.setGroupName(secGroups.getGroupName());
                this.newGroup.setStateId((secGroups.getIdState().toString()));
            } else {
                addErrorMessage("group can't be created", "");
            }
        } catch (Exception e) {
            addErrorMessage("error on retrieve list of groups: " + e.getMessage(), e
                    .getMessage());
        }
    }

    /**
     * Getter.
     * @return {@link UnitGroupBean}
     */
    public UnitGroupBean getNewGroup() {
        return newGroup;
    }

    /**
     * Setter.
     * @param newGroup new group
     */
    public void setNewGroup(UnitGroupBean newGroup) {
        this.newGroup = newGroup;
    }

    /**
     * Getter.
     * @return list of {@link UnitGroupBean}
     */
    public List<UnitGroupBean> getListUnitGroupBeans() {
        getLoadListGroups();
        if (listUnitGroupBeans.size() > 0) {
            isOneRow = true;
        } else {
            isOneRow = false;
        }
        return listUnitGroupBeans;
    }

    /**
     * Setter.
     * @param listUnitBeans list of {@link UnitGroupBean}
     */
    public void setListUnitGroupBeans(List<UnitGroupBean> listUnitBeans) {
        this.listUnitGroupBeans = listUnitBeans;
    }



    /**
     * Setter.
     * @return processed group id
     */
    public Integer getProcessedGroupId() {
        log.info("getProcessedGroupId " + processedGroupId);
        return processedGroupId;
    }

    /**
     * Setter.
     * @param processedGroupId processed group id
     */
    public void setProcessedGroupId(Integer processedGroupId) {
        try {
            log.info("setProcessedGroupId " + processedGroupId);
            this.processedGroupId = processedGroupId;
            fill(this.processedGroupId);
        } catch (Exception e) {
            addErrorMessage("Error cargando Grupo" + e.getMessage(), e
                    .getMessage());
            log.error("error ->" + e.getMessage());
        }
    }

}
