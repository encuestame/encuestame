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

	public GroupBean() {
		isOneRow = true;
		log.info("init group bean");
	}

	private Collection<UnitGroupBean> getLoadListGroups() {
		list_unitBeans = new LinkedList<UnitGroupBean>();
		Collection<SecGroups> listGroups = getServicemanagerBean()
				.getSecurityService().getGroupDao().findAllGroups();
		for (Iterator<SecGroups> i = listGroups.iterator(); i.hasNext();) {
			SecGroups group = i.next();
			log.info("group->" + group.getName());
			newGroup.setGroupName(group.getName());
			newGroup.setGroupDescription(group.getDesInfo());
			newGroup.setStateId(Integer.toString(group.getIdState()));			
			list_unitBeans.add(newGroup);
			reset();
		}
		return list_unitBeans;

	}

	/**
	 * new group
	 */
	public void newGroup() {
		try {
			log.info("new group");
			getServicemanagerBean().getSecurityService().getGroupDao()
					.newGroup(getNewGroup());
			addInfoMessage(getMessageProperties("susCreateNewGroup"), null);
			reset();
		} catch (Exception e) {
			addErrorMessage(getMessageProperties("errorCreateNewGroup"), e
					.getMessage());
			log.error("error new group->"
					+ getMessageProperties("errorCreateNewGroup"));
		}
	}

	private void reset() {
		newGroup.setGroupDescription(null);
		newGroup.setGroupName(null);
		newGroup.setStateId(null);
	}

	public UnitGroupBean getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(UnitGroupBean newGroup) {
		this.newGroup = newGroup;
	}

	public Collection<UnitGroupBean> getList_unitBeans() {
		getLoadListGroups();
		log.info("list groups->"+list_unitBeans.size());
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

}
