package org.jp.web.beans.admon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.web.beans.MasterBean;


public class GroupBean extends MasterBean{

	private String obtener;
	private String groupName;
	private String groupDescription;
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public GroupBean() {
		log.info("init group bean");
	}

	public String getObtener() {
		log.info("init getObtener");		
		return obtener = getServicemanagerBean().getSecurityService().getUserDao().getUser("jpicado").getEmail();
	}

	public void setObtener(String obtener) {
		this.obtener = obtener;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void newGroup(){
		SecGroups group = new SecGroups();
		group.setName(getGroupName().trim());
		//group.setDesInfo(getGr)
		
		getServicemanagerBean().getSecurityService().getGroupDao().
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	
	
}
