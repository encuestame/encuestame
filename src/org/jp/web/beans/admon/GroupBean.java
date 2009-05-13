package org.jp.web.beans.admon;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.web.beans.MasterBean;

public class GroupBean extends MasterBean {

	private UnitGroupBean newGroup;
	private Collection<UnitGroupBean> list_unitBeans;
	private Log log = LogFactory.getLog(this.getClass());
	private boolean isOneRow;
	private Integer processedGroupId;

	public GroupBean() {
		isOneRow = true;
	}

	private Collection<UnitGroupBean> getLoadListGroups() {
		try {
			list_unitBeans = new LinkedList<UnitGroupBean>();
			Collection<SecGroups> listGroups = getServicemanagerBean()
					.getSecurityService().getGroupDao().findAllGroups();
			for (Iterator<SecGroups> i = listGroups.iterator(); i.hasNext();) {
				UnitGroupBean newGroup = new UnitGroupBean();
				SecGroups group = i.next();
				newGroup.setId(group.getGroupId());
				newGroup.setGroupName(group.getName());
				newGroup.setGroupDescription(group.getDesInfo());
				newGroup.setStateId(Integer.toString(group.getIdState()));
				list_unitBeans.add(newGroup);
			}
			return list_unitBeans;
		} catch (Exception e) {
			addErrorMessage(getMessageProperties("error_load_groups"), e
					.getMessage());
			log.error("error load groups");
			return list_unitBeans = new LinkedList<UnitGroupBean>();
		}

	}

	/**
	 * new group
	 */
	public void newGroup() {
		try {
			log.info("new group");
			getServicemanagerBean().getSecurityService().createGroup(
					getNewGroup());
			addInfoMessage(getMessageProperties("susCreateNewGroup"), null);
			reset();
		} catch (Exception e) {
			addErrorMessage(getMessageProperties("errorCreateNewGroup"), e
					.getMessage());
			log.error("error new group->"
					+ getMessageProperties("errorCreateNewGroup"));
		}
	}

	public void updateGroup() {
		try {
			log.info("new group");
			getServicemanagerBean().getSecurityService().updateGroup(
					getNewGroup());
			addInfoMessage(getMessageProperties("susCreateNewGroup"), null);
			reset();
		} catch (Exception e) {
			addErrorMessage(getMessageProperties("errorCreateNewGroup"), e
					.getMessage());
			log.error("error new group->"
					+ getMessageProperties("errorCreateNewGroup"));
		}
	}
	
	/**
	 * Delete Group
	 */
	public void deleteGroup() {
		try {
			if (getProcessedGroupId() != null) {
				log.info("deleting group..");
				getServicemanagerBean().getSecurityService().deleteGroup(
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

	public void reset() {
		newGroup.setId(null);
		newGroup.setGroupDescription(null);
		newGroup.setGroupName(null);
		newGroup.setStateId(null);
	}

	private void reset(UnitGroupBean newGroup) {
		newGroup.setId(null);
		newGroup.setGroupDescription(null);
		newGroup.setGroupName(null);
		newGroup.setStateId(null);
	}

	private void fill(Integer idGroup) {
		SecGroups s = getServicemanagerBean().getSecurityService()
				.getGroupDao().find(idGroup);
		try {
			if (s != null) {
				reset();
				this.newGroup.setId(s.getGroupId());
				this.newGroup.setGroupDescription(s.getDesInfo());
				this.newGroup.setGroupName(s.getName());
				this.newGroup.setStateId(Integer.toString(s.getIdState()));
			} else {
				addErrorMessage("No se pudo recuperar Grupo", "");
			}
		} catch (Exception e) {
			addErrorMessage("No se pudo recuperar Grupo" + e.getMessage(), e
					.getMessage());
		}
	}

	public UnitGroupBean getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(UnitGroupBean newGroup) {
		this.newGroup = newGroup;
	}

	public Collection<UnitGroupBean> getList_unitBeans() {
		getLoadListGroups();
		if (list_unitBeans.size() > 0)
			isOneRow = true;
		else
			isOneRow = false;
		return list_unitBeans;
	}

	public void setList_unitBeans(Collection<UnitGroupBean> list_unitBeans) {
		this.list_unitBeans = list_unitBeans;
	}

	public boolean isOneRow() {
		return isOneRow;
	}

	public void setOneRow(boolean isOneRow) {
		this.isOneRow = isOneRow;
	}

	public Integer getProcessedGroupId() {
		log.info("getProcessedGroupId " + processedGroupId);
		return processedGroupId;
	}

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
