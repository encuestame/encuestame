package org.jp.web.beans.admon;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: newGroup.java Date: 11/05/2009 16:34:01
 * 
 * @author juanpicado package: org.jp.web.beans.admon
 * @version 1.0
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
		log.info("get name group");
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
		log.info("get name description");
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
