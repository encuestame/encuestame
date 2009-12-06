/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
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
 * @version $Id
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
