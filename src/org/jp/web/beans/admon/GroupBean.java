package org.jp.web.beans.admon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;


public class GroupBean extends MasterBean{

	private String obtener;
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
	
	
	
}
