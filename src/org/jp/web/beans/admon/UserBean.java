package org.jp.web.beans.admon;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;
import org.jp.web.beans.ServiceManagerBean;

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
 * Id: UserBean.java Date: 11/05/2009 13:52:28
 * 
 * @author juanpicado package: org.jp.web.beans.admon
 * @version 1.0
 */
public class UserBean extends MasterBean {

	UnitUserBean userBean;

	private Collection<UnitUserBean> list_unitBeans;
	private Log log = LogFactory.getLog(this.getClass());
	private boolean isOneRow;
	private Integer processedUserId;

	
	
	
	/**
	 * @return the list_unitBeans
	 */
	public Collection<UnitUserBean> getList_unitBeans() {
		return list_unitBeans;
	}

	/**
	 * @param list_unitBeans
	 *            the list_unitBeans to set
	 */
	public void setList_unitBeans(Collection<UnitUserBean> list_unitBeans) {
		this.list_unitBeans = list_unitBeans;
	}

	/**
	 * @return the isOneRow
	 */
	public boolean isOneRow() {
		return isOneRow;
	}

	/**
	 * @param isOneRow
	 *            the isOneRow to set
	 */
	public void setOneRow(boolean isOneRow) {
		this.isOneRow = isOneRow;
	}

	/**
	 * @param userBean
	 *            the userBean to set
	 */
	public void setUserBean(UnitUserBean userBean) {
		this.userBean = userBean;
	}

}
