package org.jp.web.beans.admon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;

public class GroupBean extends MasterBean {

	private String obtener;
	private NewGroupBean newGroup;

	private Log log = LogFactory.getLog(this.getClass());

	public GroupBean() {
		log.info("init group bean");
	}

	public String getObtener() {
		log.info("init getObtener");
		return obtener = getServicemanagerBean().getSecurityService()
				.getUserDao().getUser("jpicado").getEmail();
	}

	public void setObtener(String obtener) {
		this.obtener = obtener;
	}

	/**
	 * new group
	 */
	public void newGroup() {
		try {
			log.info("new group->" + getNewGroup());
			getServicemanagerBean().getSecurityService().getGroupDao()
					.newGroup(getNewGroup());
			addInfoMessage(getMessageProperties("susCreateNewGroup"), null);
		} catch (Exception e) {
			addErrorMessage(getMessageProperties("errorCreateNewGroup"), e
					.getMessage());
		}
	}

	/**
	 * @return the newGroup
	 */
	public NewGroupBean getNewGroup() {
		return newGroup;
	}

	/**
	 * @param newGroup
	 *            the newGroup to set
	 */
	public void setNewGroup(NewGroupBean newGroup) {
		this.newGroup = newGroup;
	}

}
